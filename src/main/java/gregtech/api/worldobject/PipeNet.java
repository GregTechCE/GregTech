package gregtech.api.worldobject;

import com.google.common.collect.*;
import gregtech.api.pipelike.IBaseProperty;
import gregtech.api.pipelike.IPipeLikeTileProperty;
import gregtech.api.pipelike.ITilePipeLike;
import gregtech.api.pipelike.PipeLikeObjectFactory;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraftforge.common.util.INBTSerializable;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.commons.lang3.tuple.MutableTriple;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static gregtech.api.pipelike.ITilePipeLike.MASK_INPUT_DISABLED;
import static gregtech.api.pipelike.ITilePipeLike.MASK_OUTPUT_DISABLED;
import static net.minecraftforge.common.util.Constants.NBT.TAG_COMPOUND;

//TODO Client Sync
//TODO Covers
public abstract class PipeNet<Q extends Enum<Q> & IBaseProperty & IStringSerializable, P extends IPipeLikeTileProperty, C> implements INBTSerializable<NBTTagCompound> {

    protected PipeLikeObjectFactory<Q, P, C> factory;
    protected WorldPipeNet worldNets;
    private ListMultimap<ChunkPos, BlockPos> area = ArrayListMultimap.create();

    /**
     * property, connection mask, color
     */
    protected final Map<BlockPos, MutableTriple<P, Integer, Integer>> allNodes = Maps.newHashMap();
    protected final Map<BlockPos, Integer> activeNodes = Maps.newHashMap();

    protected PipeNet(PipeLikeObjectFactory<Q, P, C> factory, WorldPipeNet worldNets) {
        this.factory = factory;
        this.worldNets = worldNets;
    }

    public PipeLikeObjectFactory<Q, P, C> getFactory() {
        return factory;
    }

    boolean isAnyAreaLoaded() {
        for (ChunkPos chunkPos : area.keySet()) {
            List<BlockPos> list = area.get(chunkPos);
            if (list.isEmpty()) {
                area.removeAll(chunkPos);
            } else if (worldNets.getWorld().isBlockLoaded(list.get(0))) {
                return true;
            }
        }
        return false;
    }

    private void addPosToArea(BlockPos pos) {
        area.put(new ChunkPos(pos), pos);
    }

    class MutableChunkPos extends ChunkPos {
        int x, z;

        MutableChunkPos() {
            super(0,0);
        }

        MutableChunkPos(int x, int z) {
            this();
            this.x = x;
            this.z = z;
        }

        MutableChunkPos(BlockPos pos) {
            this();
            x = pos.getX() >> 4;
            z = pos.getZ() >> 4;
        }

        void setPos(int x, int z) {
            this.x = x;
            this.z = z;
        }

        void setPos(BlockPos pos) {
            x = pos.getX() >> 4;
            z = pos.getZ() >> 4;
        }

        ChunkPos toImmutable() {
            return new ChunkPos(x, z);
        }

        @Override
        public int hashCode()
        {
            int i = 1664525 * x + 1013904223;
            int j = 1664525 * (z ^ -559038737) + 1013904223;
            return i ^ j;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            else if (!(obj instanceof ChunkPos)) return false;
            else {
                ChunkPos chunkpos = (ChunkPos)obj;
                return this.x == chunkpos.x && this.z == chunkpos.z;
            }
        }

        @Override
        public double getDistanceSq(Entity entityIn) {
            double d0 = (double)(x * 16 + 8);
            double d1 = (double)(z * 16 + 8);
            double d2 = d0 - entityIn.posX;
            double d3 = d1 - entityIn.posZ;
            return d2 * d2 + d3 * d3;
        }

        @Override
        public int getXStart() {
            return x << 4;
        }

        @Override
        public int getZStart() {
            return z << 4;
        }

        @Override
        public int getXEnd() {
            return (x << 4) + 15;
        }

        @Override
        public int getZEnd() {
            return (this.z << 4) + 15;
        }

        @Override
        public BlockPos getBlock(int x, int y, int z) {
            return new BlockPos((x << 4) + x, y, (z << 4) + z);
        }

        @Override
        public String toString()
        {
            return "[" + x + ", " + z + "]";
        }
    }

    private ThreadLocal<MutableChunkPos> chunkPosThreadLocal = ThreadLocal.withInitial(MutableChunkPos::new);
    private void removePosFromArea(BlockPos pos) {
        MutableChunkPos chunkPos = chunkPosThreadLocal.get();
        chunkPos.setPos(pos);
        area.remove(chunkPos, pos);
        if (area.get(chunkPos).isEmpty()) area.removeAll(chunkPos);
    }

    protected abstract void onPostTick(long tickTimer);

    private long tickTimer = 0;
    protected long lastUpdate = 0;

    public void onPreTick() {

    }

    public void onPostTick() {
        onPostTick(tickTimer++);
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    public void onConnectionUpdate() {
        lastUpdate++;
        worldNets.markDirty();
    }

    protected abstract void serializeNodeData(BlockPos pos, NBTTagCompound nodeTag);
    protected abstract void deserializeNodeData(BlockPos pos, NBTTagCompound nodeTag);

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        NBTTagList nodeList = new NBTTagList();
        NBTTagList propertyList = new NBTTagList();
        BiMap<P, Integer> properties = HashBiMap.create();
        final MutableInt index = new MutableInt(0);

        allNodes.forEach((pos, triple) -> {
            NBTTagCompound node = new NBTTagCompound();
            node.setInteger("x", pos.getX());
            node.setInteger("y", pos.getY());
            node.setInteger("z", pos.getZ());
            node.setInteger("m", triple.getMiddle());
            node.setInteger("c", triple.getRight());
            node.setInteger("p", properties.computeIfAbsent(triple.getLeft(), p -> index.getAndIncrement()));
            Optional.ofNullable(activeNodes.get(pos)).ifPresent(mask -> node.setInteger("a", mask));
            serializeNodeData(pos, node);
            nodeList.appendTag(node);
        });

        for (int i = 0; i < properties.size(); i++) {
            propertyList.appendTag(properties.inverse().get(i).serializeNBT());
        }

        nbt.setTag("nodes", nodeList);
        nbt.setTag("properties", propertyList);

        return nbt;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        allNodes.clear();
        activeNodes.clear();

        NBTTagList nodeList = nbt.getTagList("nodes", TAG_COMPOUND);
        NBTTagList propertyList = nbt.getTagList("properties", TAG_COMPOUND);

        P[] properties = (P[]) Array.newInstance(factory.classTileProperty, propertyList.tagCount());
        for (int i = 0; i < propertyList.tagCount(); i++) {
            properties[i] = factory.createEmptyProperty();
            properties[i].deserializeNBT(propertyList.getCompoundTagAt(i));
        }

        nodeList.forEach(nbtBase -> {
            if (nbtBase.getId() == TAG_COMPOUND) {
                NBTTagCompound node = (NBTTagCompound) nbtBase;
                BlockPos pos = new BlockPos(node.getInteger("x"), node.getInteger("y"), node.getInteger("z"));
                allNodes.put(pos, MutableTriple.of(properties[node.getInteger("p")], node.getInteger("m"), node.getInteger("c")));
                int mask = node.getInteger("a");
                if (mask != 0) activeNodes.put(pos, mask);
                deserializeNodeData(pos, node);
                addPosToArea(pos);
            }
        });
        onConnectionUpdate();
    }

    public boolean containsNode(BlockPos pos) {
        return allNodes.containsKey(pos);
    }

    public void updateNode(BlockPos pos, ITilePipeLike<Q, P> tile) {
        MutableTriple<P, Integer, Integer> data = allNodes.get(pos);
        if (data != null) {
            int connectionMask = factory.getConnectionMask(tile, worldNets.getWorld(), pos);
            int color = tile.getColor();
            if (data.getMiddle() != connectionMask || data.getRight() != color) {
                data.setMiddle(connectionMask);
                data.setRight(color);
                onConnectionUpdate();
            }
        }
        int activeMask = factory.getActiveSideMask(tile);
        if (activeMask != 0) activeNodes.put(pos, activeMask);
    }

    protected abstract void transferNodeDataTo(Collection<BlockPos> nodeToTransfer, PipeNet<Q, P, C> toNet);
    protected abstract void removeData(BlockPos pos);

    public void addNode(BlockPos blockPos, ITilePipeLike<Q, P> tile) {
        allNodes.put(blockPos, MutableTriple.of(tile.getTileProperty(), factory.getConnectionMask(tile, worldNets.getWorld(), blockPos), tile.getColor()));
        addPosToArea(blockPos);
        onConnectionUpdate();
    }

    /**
     * Assert that all given nets are in the same world.
     * @return merged result; or null if the list is empty.
     */
    static <Q extends Enum<Q> & IBaseProperty & IStringSerializable, P extends IPipeLikeTileProperty, C>
    PipeNet<Q, P, C> mergeNets(List<PipeNet<Q, P, C>> nets) {
        if (nets.size() == 0) return null;
        PipeNet<Q, P, C> result = nets.get(0);
        long lastUpdate = result.lastUpdate;
        for (PipeNet<Q, P, C> net : nets) if (net != result) {
            result.allNodes.putAll(net.allNodes);
            result.activeNodes.putAll(net.activeNodes);
            result.area.putAll(net.area);
            net.transferNodeDataTo(net.allNodes.keySet(), result);
            result.worldNets.removePipeNet(net);
            if (net.lastUpdate > lastUpdate) lastUpdate = net.lastUpdate;
        }
        result.lastUpdate = ++lastUpdate;
        result.onConnectionUpdate();
        return result;
    }

    public boolean removeNode(BlockPos blockPos) {
        if (null == allNodes.remove(blockPos)) return false;
        activeNodes.remove(blockPos);
        removeData(blockPos);
        removePosFromArea(blockPos);

        for (EnumFacing facing : EnumFacing.VALUES) {
            Collection<BlockPos> searchResult = dfs(blockPos.offset(facing), adjacent, p -> {}, noshortCircuit).keySet();
            if (searchResult.size() == allNodes.size()) break;
            if (!searchResult.isEmpty()) {
                PipeNet<Q, P, C> newNet = factory.createPipeNet(worldNets);
                transferNodeDataTo(searchResult, newNet);
                for (BlockPos pos : searchResult) {
                    newNet.allNodes.put(pos, allNodes.get(pos));
                    if (activeNodes.containsKey(pos)) newNet.activeNodes.put(pos, activeNodes.get(pos));
                    newNet.addPosToArea(pos);
                    allNodes.remove(pos);
                    activeNodes.remove(pos);
                    removePosFromArea(pos);
                    removeData(pos);
                }
                newNet.lastUpdate = ++lastUpdate;
                worldNets.addPipeNet(newNet);
            }
            searchResult.clear();
        }

        if (allNodes.isEmpty()) worldNets.removePipeNet(this);
        onConnectionUpdate();
        return true;
    }

    @FunctionalInterface
    interface PassingThroughCondition {
        boolean test(BlockPos fromPos, EnumFacing dir, BlockPos toPos);
    }

    protected static final Predicate<BlockPos> noshortCircuit = pos -> false;
    protected final PassingThroughCondition adjacent = (fromPos, dir, toPos) -> true;
    protected final PassingThroughCondition checkConnectionMask = (fromPos, dir, toPos) ->
        ((allNodes.get(fromPos).getMiddle() & MASK_OUTPUT_DISABLED << dir.getIndex()) == 0)
        && ((allNodes.get(toPos).getMiddle() & MASK_INPUT_DISABLED << dir.getIndex()) == 0);

    /**
     * Collect all nodes connecting to the start pos
     * @return All reachable nodes, with each of them mapping to its parent node, and the start pos mapping to null;
     *          Will be empty if start pos is not in this net.
     */
    public Map<BlockPos, BlockPos> dfs(BlockPos startPos, PassingThroughCondition condition, Consumer<BlockPos> onActiveNode, Predicate<BlockPos> shortCircuit) {
        Stack<BlockPos> buffer = new Stack<>();
        return search(startPos, condition, onActiveNode, shortCircuit, buffer, buffer::push, buffer::pop);
    }

    /**
     * Collect all nodes connecting to the start pos
     * @return All reachable nodes, with each of them mapping to its parent node, and the start pos mapping to null;
     *          Will be empty if start pos is not in this net.
     */
    public Map<BlockPos, BlockPos> bfs(BlockPos startPos, PassingThroughCondition condition, Consumer<BlockPos> onActiveNode, Predicate<BlockPos> shortCircuit) {
        LinkedList<BlockPos> buffer = Lists.newLinkedList();
        return search(startPos, condition, onActiveNode, shortCircuit, buffer, buffer::offer, buffer::poll);
    }

    private Map<BlockPos, BlockPos> search(BlockPos startPos, PassingThroughCondition condition, Consumer<BlockPos> onActiveNode, Predicate<BlockPos> shortCircuit,
                                           Collection<BlockPos> buffer, Consumer<BlockPos> putIntoBuffer, Supplier<BlockPos> getFromBuffer) {
        Map<BlockPos, BlockPos> result = Maps.newHashMap();
        if (allNodes.containsKey(startPos)) {
            result.put(startPos, null);
            if (activeNodes.containsKey(startPos)) onActiveNode.accept(startPos);
            if (shortCircuit.test(startPos)) return result;
            BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
            putIntoBuffer.accept(startPos);
            while (!buffer.isEmpty()) {
                BlockPos currentPos = getFromBuffer.get();
                pos.setPos(currentPos);
                for (EnumFacing facing : EnumFacing.VALUES) {
                    pos.move(facing);
                    if (!result.containsKey(pos) && allNodes.containsKey(pos)) {
                        if (condition.test(currentPos, facing, pos)) {
                            BlockPos sidePos = pos.toImmutable();
                            result.put(sidePos, currentPos);
                            putIntoBuffer.accept(sidePos);
                            if (activeNodes.containsKey(sidePos)) onActiveNode.accept(sidePos);
                            if (shortCircuit.test(sidePos)) return result;
                        }
                    }
                    pos.move(facing.getOpposite());
                }
            }
        }
        return result;
    }

}
