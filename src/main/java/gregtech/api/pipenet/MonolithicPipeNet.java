package gregtech.api.pipenet;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

import java.util.Map;

/**
 * Represents a pipe net where all nodes have same type
 * and cannot connect when their types are not equal
 * useful for fluid and item-based pipes, which assume that all nodes have same type,
 * saving performance very much during iterations and item/fluid dispatching
 */
public abstract class MonolithicPipeNet<NodeDataType> extends PipeNet<NodeDataType> {

    /**
     * Represents type of nodes in this network
     * all nodes are guaranteed to have this type and node.data.equals(nodeData)
     * note that it is null until first node is added to this network
     */
    protected NodeDataType nodeData;

    public MonolithicPipeNet(WorldPipeNet<NodeDataType, ? extends PipeNet> world) {
        super(world);
    }

    public NodeDataType getNodeData() {
        return nodeData;
    }

    @Override
    protected void addNode(BlockPos nodePos, Node<NodeDataType> node) {
        if (nodeData == null) {
            this.nodeData = node.data;
        } else if (!nodeData.equals(node.data)) {
            throw new IllegalArgumentException("Attempted to add node with different type to monolithic net!");
        }
        super.addNode(nodePos, node);
    }

    @Override
    protected void transferNodeData(Map<BlockPos, Node<NodeDataType>> transferredNodes, PipeNet<NodeDataType> parentNet) {
        if (nodeData == null && !transferredNodes.isEmpty()) {
            this.nodeData = transferredNodes.values().iterator().next().data;
        }
        super.transferNodeData(transferredNodes, parentNet);
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        super.deserializeNBT(nbt);
        //since net cannot exist in world without at least one node
        this.nodeData = getAllNodes().values().iterator().next().data;
    }

    @Override
    protected boolean areNodesCustomContactable(NodeDataType first, NodeDataType second, PipeNet<NodeDataType> secondNodeNet) {
        return nodeData == null || (first.equals(nodeData) && second.equals(nodeData));
    }

    @Override
    protected boolean canAttachNode(NodeDataType nodeData) {
        return this.nodeData == null || nodeData.equals(this.nodeData);
    }
}
