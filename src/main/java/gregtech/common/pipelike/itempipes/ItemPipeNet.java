package gregtech.common.pipelike.itempipes;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
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
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.stream.Collectors;

import static net.minecraftforge.common.util.Constants.NBT.TAG_COMPOUND;

public class ItemPipeNet extends PipeNet<TypeItemPipe, ItemPipeProperties, IItemHandler> {

    private Map<BlockPos, Pipe> pipes = Maps.newHashMap();
    private Map<BlockPos, PathsCache> pathCaches = Maps.newHashMap();
    private boolean tryMoveBufferedItems = true;

    public ItemPipeNet(WorldPipeNet worldNet) {
        super(ItemPipeFactory.INSTANCE, worldNet);
    }

    @Override
    protected void transferNodeDataTo(Collection<? extends BlockPos> nodeToTransfer, PipeNet<TypeItemPipe, ItemPipeProperties, IItemHandler> toNet) {
        ItemPipeNet net = (ItemPipeNet) toNet;
        nodeToTransfer.forEach(node -> {
            if (pipes.containsKey(node)) net.pipes.put(node, pipes.get(node));
        });
    }

    @Override
    protected void removeData(BlockPos pos) {
        pipes.remove(pos);
    }

    @Override
    protected void serializeNodeData(BlockPos pos, NBTTagCompound nodeTag) {
        Pipe pipe = pipes.get(pos);
        if (pipe != null) {
            if (pipe.getBufferedItemCount() > 0) {
                NBTTagList list = new NBTTagList();
                for (BufferedItem bufferedItem : pipe.bufferedItems) if (!bufferedItem.isEmpty()) {
                    NBTTagCompound item = bufferedItem.bufferedStack.serializeNBT();
                    item.setIntArray("GTItemPipeBuffered", new int[]{bufferedItem.fromDir == null ? -1 : bufferedItem.fromDir.getIndex(), bufferedItem.moveCountDown, bufferedItem.dirCountDown});
                    list.appendTag(item);
                }
                nodeTag.setTag("BufferedItems", list);
            }
        }
    }

    @Override
    protected void deserializeNodeData(BlockPos pos, NBTTagCompound nodeTag) {
        if (nodeTag.hasKey("BufferedItems")) {
            Pipe pipe = new Pipe(allNodes.get(pos).property);
            pipes.put(pos, pipe);
            NBTTagList list = nodeTag.getTagList("BufferedItems", TAG_COMPOUND);
            list.forEach(nbtBase -> {
                if (nbtBase.getId() == TAG_COMPOUND) {
                    NBTTagCompound compound = (NBTTagCompound) nbtBase;
                    int[] data = compound.getIntArray("GTItemPipeBuffered");
                    int index = pipe.addItem(new ItemStack(compound), data[0] < 0 ? null : EnumFacing.VALUES[data[0]]);
                    if (index >= 0) {
                        pipe.bufferedItems[index].moveCountDown = data[1];
                        pipe.bufferedItems[index].dirCountDown = data[2];
                    }
                }
            });
        }
    }

    @Override
    public void onConnectionUpdate() {
        super.onConnectionUpdate();
        tryMoveBufferedItems = true;
    }

    @Override
    public void onWeakUpdate() {
        super.onWeakUpdate();
        tryMoveBufferedItems = true;
    }

    public ItemStack insertItem(ItemPipeHandler handler, ItemStack stack, boolean simulate, EnumFacing ignoredFacing) {
        BlockPos startPos = handler.tile.getTilePos();
        Pipe pipe = getOrCreatePipe(startPos);
        int remaining = pipe.getRemainingCapacity();
        if (remaining > 0) {
            if (!simulate) {
                pipe.addItem(stack, ignoredFacing);
            }
            return ItemStack.EMPTY;
        }
        return stack;
    }

    private Multimap<BlockPos, BufferedItem> toMove = HashMultimap.create();

    @Override
    protected void onPreTick(long tickTimer) {
        if (!pipes.isEmpty()) {
            BlockPos.PooledMutableBlockPos pos = BlockPos.PooledMutableBlockPos.retain();
            Multimap<BufferedItem, Pair<BlockPos, EnumFacing>> moving = HashMultimap.create();
            pipes.forEach((pipePos, pipe) -> {
                Node<ItemPipeProperties> node = allNodes.get(pipePos);
                if (pipe.getBufferedItemCount() > 0 && (tryMoveBufferedItems || node.isActive())) {
                    for (BufferedItem bufferedItem : pipe.bufferedItems) if (bufferedItem.canMove()) {
                        int tileMask = node.getActiveMask();
                        if (bufferedItem.fromDir != null) tileMask &= ~(1 << bufferedItem.fromDir.getOpposite().getIndex());
                        if (tileMask == 0) {
                            EnumFacing toDir = null;
                            boolean locallyAmbiguous = false;
                            for (EnumFacing dir : EnumFacing.VALUES) if (dir.getOpposite() != bufferedItem.fromDir) {
                                if (allNodes.containsKey(pos.setPos(pipePos).move(dir))) {
                                    if (toDir == null) toDir = dir;
                                    else {
                                        locallyAmbiguous = true;
                                        break;
                                    }
                                }
                                pos.move(dir.getOpposite());
                            }
                            if (locallyAmbiguous) {
                                toMove.put(pipePos, bufferedItem);
                                getPathsCache(pipePos);
                            } else if (toDir != null) {
                                moving.put(bufferedItem, Pair.of(pos.setPos(pipePos).move(toDir).toImmutable(), toDir));
                            }
                        } else {
                            toMove.put(pipePos, bufferedItem);
                            getPathsCache(pipePos);
                        }
                    }
                }
            });
            tryMoveBufferedItems = false;
            moving.forEach((bufferedItem, pair) -> {
                Pipe pipe = getOrCreatePipe(pair.getLeft());
                if (pipe.addItem(bufferedItem.bufferedStack, pair.getRight()) >= 0) {
                    bufferedItem.bufferedStack = ItemStack.EMPTY;
                    tryMoveBufferedItems = true;
                }
            });
            moving.clear();
            pos.release();
        }
    }

    @Override
    protected void update(long tickTimer) {
        World world = worldNets.getWorld();
        if (!world.isRemote) {
            if (!toMove.isEmpty()) {
                BlockPos.PooledMutableBlockPos mutablePos = BlockPos.PooledMutableBlockPos.retain();
                toMove.forEach((pos, bufferedItem) -> {
                    boolean moved = false;
                    for (List<RoutePath<ItemPipeProperties, ?, Long>> paths : getPathsCache(pos).values()) {
                        ItemStack result = moveItem(paths, bufferedItem.bufferedStack, bufferedItem.fromDir);
                        if (result.getCount() < bufferedItem.bufferedStack.getCount()) {
                            bufferedItem.bufferedStack = ItemStack.EMPTY;
                            if (!result.isEmpty()) getOrCreatePipe(pos).addItem(result, bufferedItem.fromDir);
                            tryMoveBufferedItems = true;
                            moved = true;
                            break;
                        }
                    }
                    if (!moved) {
                        int[] indices = {0, 1, 2, 3, 4, 5};
                        for (int i = 6, r; i > 0 && !bufferedItem.isEmpty(); indices[r] = indices[--i]) {
                            r = WorldPipeNet.rnd.nextInt(i);
                            EnumFacing dir = EnumFacing.VALUES[indices[r]];
                            if (allNodes.containsKey(mutablePos.setPos(pos).move(dir))) {
                                if (getOrCreatePipe(mutablePos).addItem(bufferedItem.bufferedStack, dir) >= 0) {
                                    bufferedItem.bufferedStack = ItemStack.EMPTY;
                                    tryMoveBufferedItems = true;
                                }
                            }
                        }
                    }
                });
                mutablePos.release();
            }
            toMove.clear();
        }
    }

    @Override
    protected void onPostTick(long tickTimer) {
        if (!pipes.isEmpty()) {
            for (Iterator<Pipe> itr = pipes.values().iterator(); itr.hasNext();) {
                Pipe pipe = itr.next();
                if (pipe.tick()) tryMoveBufferedItems = true;
                if (pipe.getBufferedItemCount() == 0) {
                    itr.remove();
                }
            }
            worldNets.markDirty();
        }
    }

    private ItemStack moveItem(List<RoutePath<ItemPipeProperties, ?, Long>> paths, ItemStack stack, EnumFacing ignoredFacing) {
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

        if (!handlers.isEmpty()) {
            @SuppressWarnings("unchecked")
            RoutePath<ItemPipeProperties, ?, Long> path = ((Map.Entry<RoutePath<ItemPipeProperties, ?, Long>, IItemHandler>) handlers.entries().toArray()[WorldPipeNet.rnd.nextInt(handlers.size())]).getKey();
            LinkedNode<ItemPipeProperties> startNode = path.getStartNode();
            if (startNode.getTarget() == null) {
                IItemHandler[] selectedHandlers = handlers.get(path).toArray(new IItemHandler[0]);
                int[] indices = new int[selectedHandlers.length];
                for (int i = 0; i < indices.length; i++) indices[i] = i;
                for (int i = indices.length, r; i > 0 && !stack.isEmpty(); indices[r] = indices[--i]) {
                    r = WorldPipeNet.rnd.nextInt(i);
                    stack = insertItem(stack, selectedHandlers[indices[r]]);
                }
            } else {
                Pipe pipe = getOrCreatePipe(startNode.getTarget());
                if (pipe.addItem(stack, getRelativeDirection(startNode, startNode.getTarget(), ignoredFacing)) >= 0) {
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
        return pipes.computeIfAbsent(pos.toImmutable(), p -> new Pipe(allNodes.get(p).property));
    }

    private Map<Long, List<RoutePath<ItemPipeProperties, ?, Long>>> getPathsCache(BlockPos startPos) {
        return pathCaches.computeIfAbsent(startPos.toImmutable(), p -> new PathsCache()).getCachedPaths(this, startPos);
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
        final int capacity;
        final int tickRate;
        BufferedItem[] bufferedItems;

        Pipe(ItemPipeProperties properties) {
            capacity = properties.getTransferCapacity();
            tickRate = properties.getTickRate();
        }

        boolean tick() {
            if (bufferedItems == null) return false;
            int oldCapacity = getRemainingCapacity();
            boolean result = false;
            if (bufferedItems != null) for (BufferedItem bufferedItem : bufferedItems) result |= bufferedItem.tick();
            return result || getRemainingCapacity() != oldCapacity;
        }

        int addItem(ItemStack stack, EnumFacing facing) {
            if (stack.isEmpty()) return -1;
            if (bufferedItems == null) {
                bufferedItems = new BufferedItem[capacity];
                Arrays.fill(bufferedItems, BufferedItem.EMPTY);
            }
            for (int i = 0; i < bufferedItems.length; i++) {
                if (bufferedItems[i].isEmpty()) {
                    bufferedItems[i] = new BufferedItem(stack, facing, tickRate * 20);
                    return i;
                }
            }
            return -1;
        }

        int getRemainingCapacity() {
            return capacity - getBufferedItemCount();
        }

        int getBufferedItemCount() {
            int itemCount = 0;
            if (bufferedItems != null) for (BufferedItem bufferedItem : bufferedItems) if (!bufferedItem.isEmpty()) itemCount++;
            return itemCount;
        }
    }

    static class BufferedItem {
        static final BufferedItem EMPTY = new BufferedItem(ItemStack.EMPTY, null, 0);

        ItemStack bufferedStack;
        EnumFacing fromDir;
        int moveCountDown;
        int dirCountDown;

        BufferedItem(ItemStack stack, EnumFacing fromDir, int countDown) {
            this.bufferedStack = stack;
            this.fromDir = fromDir;
            this.moveCountDown = countDown;
            this.dirCountDown = countDown * 4;
        }

        boolean tick() {
            boolean result = false;
            if (moveCountDown > 0) result = --moveCountDown == 0;
            if (dirCountDown > 0 && --dirCountDown == 0) {
                fromDir = null;
                result = true;
            }
            return result;
        }

        boolean isEmpty() {
            return bufferedStack.isEmpty();
        }

        boolean canMove() {
            return !bufferedStack.isEmpty() && moveCountDown == 0;
        }
    }

    static class PathsCache {
        long lastCachedPathTime = 0;
        long lastWeakUpdate = 0;
        Map<Long, List<RoutePath<ItemPipeProperties, ?, Long>>> pathsCache;

        Map<Long, List<RoutePath<ItemPipeProperties, ?, Long>>> getCachedPaths(ItemPipeNet pipeNet, BlockPos startPos) {
            if (pathsCache == null || lastCachedPathTime < pipeNet.lastUpdate) {
                lastCachedPathTime = pipeNet.lastUpdate;
                lastWeakUpdate = pipeNet.lastWeakUpdate;
                pathsCache = pipeNet.computeRoutePaths(startPos, checkConnectionMask(),
                    Collectors.summingLong(node -> (long) node.property.getRoutingValue()), null)
                    .stream().collect(Collectors.groupingBy(RoutePath::getAccumulatedVal, Maps::newTreeMap, Collectors.toList()));
            } else if (lastWeakUpdate < pipeNet.lastWeakUpdate) {
                lastWeakUpdate = pipeNet.lastWeakUpdate;
                pathsCache.values().forEach(pipeNet::updateNodeChain);
            }
            return pathsCache;
        }
    }
}
