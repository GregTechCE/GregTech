package gregtech.api.worldentries;

import com.google.common.collect.*;
import gregtech.api.pipelike.IBaseProperty;
import gregtech.api.pipelike.IPipeLikeTileProperty;
import gregtech.api.pipelike.ITilePipeLike;
import gregtech.api.pipelike.PipeFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraftforge.common.util.INBTSerializable;
import org.apache.commons.lang3.mutable.MutableInt;

import javax.annotation.Nullable;
import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;

import static gregtech.api.pipelike.ITilePipeLike.MASK_INPUT_DISABLED;
import static gregtech.api.pipelike.ITilePipeLike.MASK_OUTPUT_DISABLED;
import static net.minecraftforge.common.util.Constants.NBT.TAG_COMPOUND;

//TODO Covers
public abstract class PipeNet<Q extends Enum<Q> & IBaseProperty & IStringSerializable, P extends IPipeLikeTileProperty, C> implements INBTSerializable<NBTTagCompound> {

    final long uid;
    protected PipeFactory<Q, P, C> factory;
    protected WorldPipeNet worldNets;
    private ListMultimap<ChunkPos, BlockPos> area = ArrayListMultimap.create();
    protected final Map<BlockPos, Node<P>> allNodes = Maps.newHashMap();

    protected final Collection<BlockPos> removedNodes = Sets.newHashSet();

    protected PipeNet(PipeFactory<Q, P, C> factory, WorldPipeNet worldNets) {
        this.factory = factory;
        this.worldNets = worldNets;
        uid = worldNets.getUID();
        worldNets.uids.put(this, uid);
    }

    public PipeFactory<Q, P, C> getFactory() {
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

    public Collection<ChunkPos> getArea() {
        return area.keySet();
    }

    public boolean isPlayerWatching(EntityPlayerMP player) {
        return area.keySet().stream()
            .anyMatch(chunkPos -> player.getServerWorld().getPlayerChunkMap().isPlayerWatchingChunk(player, chunkPos.x, chunkPos.z));
    }

    private void addPosToArea(BlockPos pos) {
        area.put(new ChunkPos(pos), pos);
    }

    private ThreadLocal<MutableChunkPos> chunkPosThreadLocal = ThreadLocal.withInitial(MutableChunkPos::new);
    private void removePosFromArea(BlockPos pos) {
        MutableChunkPos chunkPos = chunkPosThreadLocal.get();
        chunkPos.setPos(pos);
        area.remove(chunkPos, pos);
        if (area.get(chunkPos).isEmpty()) area.removeAll(chunkPos);
    }

    protected void onPreTick(long tickTimer) {}//pre calculations here
    protected void update(long tickTimer) {}// world interactions here
    protected void onPostTick(long tickTimer) {}//post calculations here

    private long tickTimer = 0;
    protected long lastUpdate = 1;

    public final void onPreTick() {
        onPreTick(tickTimer);
    }

    public final void update() {
        update(tickTimer);
    }

    public final void onPostTick() {
        onPostTick(tickTimer++);
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    public void onConnectionUpdate() {
        lastUpdate++;
        worldNets.markDirty();
    }

    protected void serializeNodeData(BlockPos pos, NBTTagCompound nodeTag) {}
    protected void deserializeNodeData(BlockPos pos, NBTTagCompound nodeTag) {}

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        NBTTagList nodeList = new NBTTagList();
        NBTTagList propertyList = new NBTTagList();
        BiMap<P, Integer> properties = HashBiMap.create();
        final MutableInt index = new MutableInt(0);

        allNodes.values().forEach(node -> {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setIntArray("node", new int[]{node.getX(), node.getY(), node.getZ(),
                properties.computeIfAbsent(node.property, p -> index.getAndIncrement()),
                node.connectionMask, node.color, node.activeMask});
            serializeNodeData(node, tag);
            nodeList.appendTag(tag);
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
                int[] data = node.getIntArray("node");
                BlockPos pos = new BlockPos(data[0], data[1], data[2]);
                allNodes.put(pos, new Node<>(pos, properties[data[3]], data[4], data[5], data[6]));
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
        Node<P> data = allNodes.get(pos);
        if (data != null) {
            int connectionMask = factory.getConnectionMask(tile, worldNets.getWorld(), pos);
            int color = tile.getColor();
            if (data.connectionMask != connectionMask || data.color != color) {
                data.connectionMask = connectionMask;
                data.color = color;
                onConnectionUpdate();
            }
            data.activeMask = factory.getActiveSideMask(tile);
        }
    }

    protected abstract void transferNodeDataTo(Collection<? extends BlockPos> nodeToTransfer, PipeNet<Q, P, C> toNet);
    protected abstract void removeData(BlockPos pos);

    public void addNode(BlockPos blockPos, ITilePipeLike<Q, P> tile) {
        allNodes.put(blockPos, new Node<>(blockPos, tile.getTileProperty(),
            factory.getConnectionMask(tile, worldNets.getWorld(), blockPos), tile.getColor(), factory.getActiveSideMask(tile)));
        removedNodes.remove(blockPos);
        addPosToArea(blockPos);
        onConnectionUpdate();
    }

    public boolean removeNode(BlockPos blockPos) {
        if (null == allNodes.remove(blockPos)) return false;
        removeData(blockPos);
        removePosFromArea(blockPos);
        removedNodes.add(blockPos);
        onConnectionUpdate();
        return true;
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
            result.area.putAll(net.area);
            net.transferNodeDataTo(net.allNodes.keySet(), result);
            net.allNodes.clear(); // will be removed in WorldPipeNet::update()
            net.area.clear();     // not tickable anymore.
            if (net.lastUpdate > lastUpdate) lastUpdate = net.lastUpdate;
        }
        result.lastUpdate = ++lastUpdate;
        result.onConnectionUpdate();
        return result;
    }

    void trySplitPipeNet() {
        if (removedNodes.isEmpty()) return;
        Collection<BlockPos> sides = Sets.newHashSet();
        removedNodes.forEach(pos -> {for (EnumFacing facing : EnumFacing.VALUES) Optional.ofNullable(allNodes.get(pos.offset(facing))).ifPresent(sides::add);});
        for (BlockPos startPos : sides) {
            Collection<? extends BlockPos> searchResult = dfs(startPos, adjacent, null, null, null);
            if (searchResult.size() == allNodes.size()) break;
            if (!searchResult.isEmpty()) {
                PipeNet<Q, P, C> newNet = factory.createPipeNet(worldNets);
                transferNodeDataTo(searchResult, newNet);
                for (BlockPos pos : searchResult) {
                    newNet.allNodes.put(pos, allNodes.get(pos));
                    newNet.addPosToArea(pos);
                    allNodes.remove(pos);
                    removePosFromArea(pos);
                    removeData(pos);
                }
                newNet.lastUpdate = lastUpdate + 1;
                worldNets.addPipeNet(newNet);
            }
        }
        onConnectionUpdate();
    }

    protected static final PassingThroughCondition adjacent = (fromNode, dir, toNode) -> true;
    protected static final PassingThroughCondition checkConnectionMask = (fromNode, dir, toNode) ->
        ((fromNode.connectionMask & MASK_OUTPUT_DISABLED << dir.getIndex()) == 0)
        && ((toNode.connectionMask & MASK_INPUT_DISABLED << dir.getOpposite().getIndex()) == 0);

    /**
     * Compute all route paths from start pos to each reachable active nodes, using bfs.
     * @param collector Collect the cable loss, the route value or sth else. Result will be accumulated from the destination to the start pos.
     */
    public <R> List<RoutePath<P, ?, R>> computeRoutePaths(BlockPos startPos, PassingThroughCondition condition, Collector<LinkedNode<P>, ?, R> collector, Predicate<LinkedNode<P>> shortCircuit) {
        List<RoutePath<P, ?, R>> result = Lists.newArrayList();
        bfs(startPos, condition, null, activeNode -> {
            RoutePath<P, ?, R> routePath = new RoutePath<>(collector);
            new NodeChain<>(activeNode).forEach(routePath::extend);
            result.add(routePath);
        }, shortCircuit);
        return result;
    }

    /**
     * Collect all nodes connecting to the start pos
     * @return All reachable nodes, with each of them linked to its parent node, and the start pos linked to null;
     *          Will be empty if start pos is not in this net.
     */
    public Collection<LinkedNode<P>> dfs(BlockPos startPos, PassingThroughCondition<P> condition, @Nullable Consumer<LinkedNode<P>> onNode, @Nullable Consumer<LinkedNode<P>> onActiveNode, @Nullable Predicate<LinkedNode<P>> shortCircuit) {
        return search(startPos, condition, onNode, onActiveNode, shortCircuit, SearchType.DFS);
    }

    /**
     * Collect all nodes connecting to the start pos
     * @return All reachable nodes, with each of them linked to its parent node, and the start pos linked to null;
     *          Will be empty if start pos is not in this net.
     */
    public Collection<LinkedNode<P>> bfs(BlockPos startPos, PassingThroughCondition<P> condition, @Nullable Consumer<LinkedNode<P>> onNode, @Nullable Consumer<LinkedNode<P>> onActiveNode, @Nullable Predicate<LinkedNode<P>> shortCircuit) {
        return search(startPos, condition, onNode, onActiveNode, shortCircuit, SearchType.BFS);
    }

    private enum SearchType {
        BFS, DFS
    }

    private Collection<LinkedNode<P>> search(BlockPos startPos, PassingThroughCondition<P> condition, @Nullable Consumer<LinkedNode<P>> onNode, @Nullable Consumer<LinkedNode<P>> onActiveNode, @Nullable Predicate<LinkedNode<P>> shortCircuit, SearchType type) {
        Collection<LinkedNode<P>> result = Sets.newHashSet();
        if (allNodes.containsKey(startPos)) {
            LinkedNode<P> startNode = new LinkedNode<>(allNodes.get(startPos), null);
            result.add(startNode);
            if (onNode != null) onNode.accept(startNode);
            if (onActiveNode != null && startNode.isActive()) onActiveNode.accept(startNode);
            if (shortCircuit != null && shortCircuit.test(startNode)) return result;
            BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
            LinkedList<LinkedNode<P>> buffer = Lists.newLinkedList();
            Consumer<LinkedNode<P>> putter = null;
            Supplier<LinkedNode<P>> getter = null;
            switch (type) {
                case BFS: putter = buffer::offerLast; getter = buffer::pollFirst; break;
                case DFS: putter = buffer::offerLast; getter = buffer::pollLast; break;
            }
            putter.accept(startNode);
            while (!buffer.isEmpty()) {
                LinkedNode<P> current = getter.get();
                pos.setPos(current);
                for (EnumFacing facing : EnumFacing.VALUES) {
                    pos.move(facing);
                    if (!result.contains(pos) && allNodes.containsKey(pos)) {
                        Node<P> side = allNodes.get(pos);
                        if (condition.test(current, facing, side)) {
                            LinkedNode<P> sideNode = new LinkedNode<>(side, current);
                            result.add(sideNode);
                            putter.accept(sideNode);
                            if (onNode != null) onNode.accept(sideNode);
                            if (onActiveNode != null && sideNode.isActive()) onActiveNode.accept(sideNode);
                            if (shortCircuit != null && shortCircuit.test(sideNode)) return result;
                        }
                    }
                    pos.move(facing.getOpposite());
                }
            }
        }
        return result;
    }

    boolean clientSync = false;

    public void issueClientSync() {
        clientSync = true;
    }

    boolean needsClientSync() {
        return clientSync;
    }

    @Nullable
    protected NBTTagCompound getTagForPacket() {
        return null;
    }

    protected void onPacketTag(NBTTagCompound tag) {}




    public static class Node<P> extends BlockPos {
        public final P property;
        int connectionMask, color, activeMask;

        Node(BlockPos pos, P property, int connectionMask, int color, int activeMask) {
            super(pos);
            this.property = property;
            this.connectionMask = connectionMask;
            this.color = color;
            this.activeMask = activeMask;
        }

        public int getConnectionMask() {
            return connectionMask;
        }

        public int getColor() {
            return color;
        }

        public int getActiveMask() {
            return activeMask;
        }

        public boolean isActive() {
            return activeMask != 0;
        }
    }

    public static class LinkedNode<P> extends Node<P> {
        LinkedNode<P> target;
        LinkedNode(Node<P> node, LinkedNode<P> target) {
            super(node, node.property, node.connectionMask, node.color, node.activeMask);
            this.target = target;
        }

        public LinkedNode<P> getTarget() {
            return target;
        }
    }

    public static class NodeChain<P> implements Iterable<Node<P>> {
        LinkedNode<P> startNode = null;
        public NodeChain(){}
        public NodeChain(LinkedNode<P> startNode) {
            this.startNode = startNode;
        }

        public NodeChain<P> extend(Node<P> node) {
            LinkedNode<P> newNode = new LinkedNode<>(node, startNode);
            return this;
        }

        public LinkedNode<P> getStartNode() {
            return startNode;
        }

        @Override
        public Iterator<Node<P>> iterator() {
            return new NodeChainIterator<>(this);
        }

        static class NodeChainIterator<P> implements Iterator<Node<P>> {
            private NodeChain<P> chain;
            private LinkedNode<P> current = null;
            private NodeChainIterator(NodeChain<P> chain) {
                this.chain = chain;
            }

            @Override
            public LinkedNode<P> next() {
                return current = current == null ? chain.startNode : current.target;
            }

            @Override
            public boolean hasNext() {
                return (current == null ? chain.startNode : current.target) != null;
            }
        }

    }

    public static class RoutePath<P, A, R> extends NodeChain<P> {
        LinkedNode<P> endNode = null;
        private final Collector<LinkedNode<P>, A, R> collector;
        private A accumulator;
        public RoutePath(Collector<LinkedNode<P>, A, R> collector) {
            this.collector = collector;
            this.accumulator = collector.supplier().get();
        }

        public RoutePath(LinkedNode<P> startNode, Collector<LinkedNode<P>, A, R> collector) {
            super(startNode);
            this.collector = collector;
            this.accumulator = collector.supplier().get();
            if (startNode != null) {
                for (endNode = startNode; endNode.target != null; endNode = endNode.target) {
                    collector.accumulator().accept(accumulator, endNode);
                }
                collector.accumulator().accept(accumulator, endNode);
            }
        }

        @Override
        public RoutePath<P, A, R> extend(Node<P> node) {
            LinkedNode<P> newNode = new LinkedNode<>(node, startNode);
            startNode = newNode;
            if (endNode == null) endNode = newNode;
            collector.accumulator().accept(accumulator, newNode);
            return this;
        }

        public RoutePath<P, A, R> extend(RoutePath<P, A, R> routePath) {
            accumulator = collector.combiner().apply(accumulator, routePath.accumulator);
            routePath.endNode.target = startNode;
            startNode = routePath.startNode;
            return this;
        }

        public LinkedNode<P> getEndNode() {
            return endNode;
        }

        public R getAccumulatedVal() {
            return collector.finisher().apply(accumulator);
        }
    }

    @FunctionalInterface
    interface PassingThroughCondition<P> {
        boolean test(Node<P> fromNode, EnumFacing dir, Node<P> toNode);
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

}
