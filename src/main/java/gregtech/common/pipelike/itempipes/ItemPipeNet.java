package gregtech.common.pipelike.itempipes;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import gregtech.api.util.GTUtility;
import gregtech.api.worldentries.pipenet.PipeNet;
import gregtech.api.worldentries.pipenet.RoutePath;
import gregtech.api.worldentries.pipenet.WorldPipeNet;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.*;
import java.util.stream.Collectors;

import static net.minecraftforge.common.util.Constants.NBT.TAG_COMPOUND;

public class ItemPipeNet extends PipeNet<TypeItemPipe, ItemPipeProperties, IItemHandler> {

    private Map<BlockPos, Pipe> pipes = Maps.newHashMap();
    private Collection<BlockPos> inserted = Sets.newHashSet();
    private boolean capacityUpdated = true;

    public ItemPipeNet(WorldPipeNet worldNet) {
        super(ItemPipeFactory.INSTANCE, worldNet);
    }

    @Override
    protected void transferNodeDataTo(Collection<? extends BlockPos> nodeToTransfer, PipeNet<TypeItemPipe, ItemPipeProperties, IItemHandler> toNet) {
        ItemPipeNet net = (ItemPipeNet) toNet;
        nodeToTransfer.forEach(node -> {
            if (pipes.containsKey(node)) net.pipes.put(node, pipes.get(node));
            if (inserted.contains(node)) net.inserted.add(node);
        });
    }

    @Override
    protected void removeData(BlockPos pos) {
        pipes.remove(pos);
        inserted.remove(pos);
    }

    @Override
    protected void serializeNodeData(BlockPos pos, NBTTagCompound nodeTag) {
        Pipe pipe = pipes.get(pos);
        if (pipe != null) {
            nodeTag.setInteger("BufferCounter", pipe.counter);
            if (pipe.getBufferedItemCount() > 0) {
                NBTTagList list = new NBTTagList();
                for (BufferedItem bufferedItem : pipe.bufferedItems) if (!bufferedItem.isEmpty()) {
                    NBTTagCompound item = bufferedItem.bufferedStack.serializeNBT();
                    item.setIntArray("GTItemPipeBuffered", new int[]{bufferedItem.fromDir == null ? -1 : bufferedItem.fromDir.getIndex(), bufferedItem.countDown});
                    list.appendTag(item);
                }
                nodeTag.setTag("BufferedItems", list);
            }
        }
    }

    @Override
    protected void deserializeNodeData(BlockPos pos, NBTTagCompound nodeTag) {
        if (nodeTag.hasKey("BufferCounter")) {
            Pipe pipe = new Pipe(allNodes.get(pos).property);
            pipes.put(pos, pipe);
            pipe.counter = nodeTag.getInteger("BufferCounter");
            if (nodeTag.hasKey("BufferedItems")) {
                NBTTagList list = nodeTag.getTagList("BufferedItems", TAG_COMPOUND);
                list.forEach(nbtBase -> {
                    if (nbtBase.getId() == TAG_COMPOUND) {
                        NBTTagCompound compound = (NBTTagCompound) nbtBase;
                        int[] data = compound.getIntArray("GTItemPipeBuffered");
                        int index = pipe.addItem(new ItemStack(compound), data[0] < 0 ? null : EnumFacing.VALUES[data[0]]);
                        if (index >= 0) pipe.bufferedItems[index].countDown = data[1];
                    }
                });
            }
        }
    }

    @Override
    public void onConnectionUpdate() {
        super.onConnectionUpdate();
        capacityUpdated = true;
    }

    @Override
    public void onWeakUpdate() {
        super.onWeakUpdate();
        capacityUpdated = true;
    }

    public ItemStack insertItem(ItemPipeHandler handler, ItemStack stack, boolean simulate, EnumFacing ignoredFacing) {
        BlockPos startPos = handler.tile.getTilePos();
        Pipe pipe = getOrCreatePipe(startPos);
        int remaining = pipe.getRemainingCapacity();
        if (remaining > 0) {
            if (!simulate) {
                pipe.addItem(stack, ignoredFacing);
                pipe.onItemPassingBy();
                inserted.add(handler.tile.getTilePos());
            }
            return ItemStack.EMPTY;
        }
        return stack;
    }

    @Override
    protected void update(long tickTimer) {
        World world = worldNets.getWorld();
        if (!world.isRemote) {
            if (!pipes.isEmpty()) {
                Multimap<ItemPipeHandler, BufferedItem> toTransfer = HashMultimap.create();
                pipes.forEach((pos, pipe) -> {
                    if (pipe.getBufferedItemCount() > 0 && (capacityUpdated || allNodes.get(pos).isActive() || inserted.contains(pos))) {
                        for (BufferedItem bufferedItem : pipe.bufferedItems) if (!bufferedItem.isEmpty()) {
                            ItemPipeHandler handler = (ItemPipeHandler) factory.getTile(world, pos)
                                .getCapabilityInternal(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
                            toTransfer.put(handler, bufferedItem);
                        }
                    }
                });
                toTransfer.forEach((handler, bufferedItem) -> {
                    ItemStack stack = bufferedItem.bufferedStack;
                    bufferedItem.bufferedStack = ItemStack.EMPTY;
                    bufferedItem.bufferedStack = transferItem(handler, stack, bufferedItem.fromDir);
                    if (bufferedItem.bufferedStack.getCount() < stack.getCount()) worldNets.markDirty();
                });
                capacityUpdated = false;
            }
        }
        inserted.clear();
    }

    @Override
    protected void onPostTick(long tickTimer) {
        if (!pipes.isEmpty()) {
            for (Iterator<Pipe> itr = pipes.values().iterator(); itr.hasNext();) {
                Pipe pipe = itr.next();
                if (pipe.tick()) capacityUpdated = true;
                if (pipe.getBufferedItemCount() == 0) {
                    if (pipe.getRemainingCapacity() == pipe.capacity) {
                        itr.remove();
                    } else {
                        pipe.bufferedItems = null;
                    }
                }
            }
            worldNets.markDirty();
        }
    }

    private ItemStack transferItem(ItemPipeHandler handler, ItemStack stack, EnumFacing ignoredFacing) {
        BlockPos startPos = handler.tile.getTilePos();
        if (handler.pathsCache == null || handler.lastCachedPathTime < lastUpdate) {
            handler.lastCachedPathTime = lastUpdate;
            handler.lastWeakUpdate = lastWeakUpdate;
            handler.pathsCache = computeRoutePaths(startPos, checkConnectionMask(),
                Collectors.summingLong(node -> (long) node.property.getRoutingValue()), null)
                .stream().collect(Collectors.groupingBy(RoutePath::getAccumulatedVal, Maps::newTreeMap, Collectors.toList()));
        } else if (handler.lastWeakUpdate < lastWeakUpdate) {
            handler.lastWeakUpdate = lastWeakUpdate;
            handler.pathsCache.values().forEach(this::updateNodeChain);
        }
        ItemStack result = stack;
        for (List<RoutePath<ItemPipeProperties, ?, Long>> paths : handler.pathsCache.values()) {
            if ((result = transferItem(paths, stack, ignoredFacing)).getCount() < stack.getCount()) break;
        }
        return result;
    }

    private ItemStack transferItem(List<RoutePath<ItemPipeProperties, ?, Long>> paths, ItemStack stack, EnumFacing ignoredFacing) {
        // Collect all available handlers on each equivalent paths
        Multimap<RoutePath<ItemPipeProperties, ?, Long>, IItemHandler> handlers = HashMultimap.create();
        BlockPos.PooledMutableBlockPos pos = BlockPos.PooledMutableBlockPos.retain();
        World world = worldNets.getWorld();
        for (RoutePath<ItemPipeProperties, ?, Long> path : paths) {
            BlockPos startPos = path.getStartNode();
            Node<ItemPipeProperties> destination = path.getEndNode();
            int tileMask = destination.getActiveMask();
            if (ignoredFacing != null) {
                BlockPos secondPos = path.getStartNode().getTarget();
                if (secondPos != null && secondPos.equals(pos.setPos(startPos).move(ignoredFacing.getOpposite()))) continue;
                if (destination.equals(startPos)) tileMask &= ~(1 << ignoredFacing.getOpposite().getIndex());
            }
            if (tileMask != 0) for (EnumFacing facing : EnumFacing.VALUES) if (0 != (tileMask & 1 << facing.getIndex())) {
                pos.setPos(destination).move(facing);
                if (!world.isBlockLoaded(pos)) continue; //do not allow pipes to load chunks
                TileEntity tile = world.getTileEntity(pos);
                if (tile == null) continue;
                IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing.getOpposite());
                if (itemHandler == null) continue;
                int slotCount = itemHandler.getSlots();
                for (int i = 0; i < slotCount; i++) if (itemHandler.insertItem(i, stack, true).getCount() < stack.getCount()) {
                    handlers.put(path, itemHandler);
                    break;
                }
            }
        }
        pos.release();

        //insert into a random selected handler
        //if there're items remaining, insert into other handlers at the same end
        //if there're still items remaining, just put it into the destination node
        //or, items might be stuck on the half way to the target tile if the transfer capacity is exhausted.
        if (!handlers.isEmpty()) {
            RoutePath<ItemPipeProperties, ?, Long> path = ((Map.Entry<RoutePath<ItemPipeProperties, ?, Long>, IItemHandler>) handlers.entries().toArray()[WorldPipeNet.rnd.nextInt(handlers.size())]).getKey();
            BlockPos[] lastNode = new BlockPos[2];
            Pipe[] pipe = new Pipe[2];
            pipe[0] = null;
            boolean doTransfer = true;
            for (Node<ItemPipeProperties> node : path) {
                pipe[1] = getOrCreatePipe(node);
                if (!(doTransfer = pipe[0] == null || pipe[1].onItemPassingBy())) {
                    if (pipe[0].addItem(stack, getRelativeDirection(lastNode[1], node, ignoredFacing)) >= 0) {
                        stack = ItemStack.EMPTY;
                    }
                    break;
                }
                pipe[0] = pipe[1];
                lastNode[0] = lastNode[1];
                lastNode[1] = node;
            }
            if (doTransfer) {
                IItemHandler[] selectedHandlers = handlers.get(path).toArray(new IItemHandler[0]);
                int[] indices = GTUtility.getIncrementingIntArray(selectedHandlers.length);
                for (int i = indices.length, r; i > 0 && !stack.isEmpty(); indices[r] = indices[--i]) {
                    r = WorldPipeNet.rnd.nextInt(i);
                    stack = insertItem(stack, selectedHandlers[indices[r]]);
                }
                if (!stack.isEmpty() && lastNode[0] != null && pipe[1].addItem(stack, getRelativeDirection(lastNode[0], lastNode[1], ignoredFacing)) >= 0) {
                    stack = ItemStack.EMPTY;
                }
            }
        }
        return stack;
    }

    private ItemStack insertItem(ItemStack stack, IItemHandler handler) {
        for (int i = 0; i < handler.getSlots() && !stack.isEmpty(); stack = handler.insertItem(i++, stack, false));
        return stack;
    }

    private EnumFacing getRelativeDirection(BlockPos from, BlockPos to, EnumFacing defaultDir) {
        return from == null ? defaultDir : GTUtility.getRelativeDirection(from, to);
    }

    private Pipe getOrCreatePipe(BlockPos pos) {
        return pipes.computeIfAbsent(pos, p -> new Pipe(allNodes.get(p).property));
    }

    public ItemStack getItemStackInSlot(BlockPos pos, int slot) {
        Pipe buffer = pipes.get(pos);
        return buffer != null && buffer.bufferedItems != null && slot >= 0 && slot < buffer.bufferedItems.length ? buffer.bufferedItems[slot].bufferedStack : ItemStack.EMPTY;
    }

    public ItemStack extractItem(BlockPos pos, int slot, int amount, boolean simulate) {
        Pipe buffer = pipes.get(pos);
        if (buffer == null || buffer.bufferedItems == null || slot < 0 || slot >= buffer.bufferedItems.length) return ItemStack.EMPTY;
        ItemStack existing = buffer.bufferedItems[slot].bufferedStack;

        if (existing.isEmpty()) return ItemStack.EMPTY;

        int toExtract = Math.min(amount, existing.getMaxStackSize());

        if (existing.getCount() <= toExtract) {
            if (!simulate) {
                buffer.bufferedItems[slot] = BufferedItem.EMPTY;
                worldNets.markDirty();
            }
            return existing;
        } else {
            if (!simulate) {
                buffer.bufferedItems[slot].bufferedStack = ItemHandlerHelper.copyStackWithSize(existing, existing.getCount() - toExtract);
                worldNets.markDirty();
            }
            return ItemHandlerHelper.copyStackWithSize(existing, toExtract);
        }
    }

    public void cleanBufferedItems(BlockPos pos, NonNullList<ItemStack> list) {
        Pipe buffer = pipes.get(pos);
        if (buffer != null && buffer.bufferedItems != null) {
            for (BufferedItem bufferedItem : buffer.bufferedItems) if (!bufferedItem.isEmpty()) {
                list.add(bufferedItem.bufferedStack);
            }
            buffer.bufferedItems = null;
            worldNets.markDirty();
        }
    }

    static class Pipe {
        int counter = 0;
        final int max;
        final int itemIncrease;
        final int tickReduce;
        final int capacity;
        final int tickRate;
        BufferedItem[] bufferedItems;

        Pipe(ItemPipeProperties properties) {
            capacity = properties.getTransferCapacity();
            tickRate = properties.getTickRate();
            max = GTUtility.lcm(capacity, tickRate * 20);
            itemIncrease = max / capacity;
            tickReduce = max / tickRate / 20;
        }

        boolean tick() {
            if (counter == 0 && bufferedItems == null) return false;
            int oldCapacity = getRemainingCapacity();
            counter -= tickReduce;
            int min = getBufferedItemCount() * itemIncrease;
            if (counter < min) counter = min;
            if (bufferedItems != null) for (BufferedItem bufferedItem : bufferedItems) bufferedItem.tick();
            return getRemainingCapacity() != oldCapacity;
        }

        boolean onItemPassingBy() {
            int itemCount = getBufferedItemCount();
            if (counter < itemCount) counter = itemCount * itemIncrease;
            if (counter + itemIncrease <= max){
                counter += itemIncrease;
                return true;
            }
            return false;
        }

        int addItem(ItemStack stack, EnumFacing facing) {
            if (stack.isEmpty()) return -1;
            if (bufferedItems == null) {
                bufferedItems = new BufferedItem[capacity];
                Arrays.fill(bufferedItems, BufferedItem.EMPTY);
            }
            for (int i = 0; i < bufferedItems.length; i++) {
                if (bufferedItems[i].isEmpty()) {
                    bufferedItems[i] = new BufferedItem(stack, facing, tickRate * 80);
                    return i;
                }
            }
            return -1;
        }

        int getRemainingCapacity() {
            return (max - counter) / itemIncrease;
        }

        int getBufferedItemCount() {
            int itemCount = 0;
            if (bufferedItems != null) for (BufferedItem bufferedItem : bufferedItems) if (!bufferedItem.isEmpty()) itemCount++;
            return itemCount;
        }
    }

    static class BufferedItem {
        static BufferedItem EMPTY = new BufferedItem(ItemStack.EMPTY, null, 0);

        ItemStack bufferedStack;
        EnumFacing fromDir;
        int countDown;

        BufferedItem(ItemStack stack, EnumFacing fromDir, int countDown) {
            this.bufferedStack = stack;
            this.fromDir = fromDir;
            this.countDown = countDown;
        }

        void tick() {
            if (countDown > 0 && --countDown == 0) {
                fromDir = null;
            }
        }

        boolean isEmpty() {
            return bufferedStack.isEmpty();
        }
    }
}
