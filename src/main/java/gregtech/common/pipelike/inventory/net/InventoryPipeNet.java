package gregtech.common.pipelike.inventory.net;

import com.google.common.base.Preconditions;
import gregtech.api.pipenet.Node;
import gregtech.api.pipenet.PipeNet;
import gregtech.api.pipenet.WorldPipeNet;
import gregtech.api.pipenet.block.simple.EmptyNodeData;
import gregtech.common.pipelike.inventory.network.ItemStorageNetwork;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

import java.util.Map;

public class InventoryPipeNet extends PipeNet<EmptyNodeData> implements ITickable {

    private ItemStorageNetwork storageNetwork;

    public InventoryPipeNet(WorldPipeNet<EmptyNodeData, ? extends PipeNet> world) {
        super(world);
    }

    @Override
    public void update() {
        ItemStorageNetwork storageNetwork = getStorageNetwork();
        storageNetwork.update();
    }

    @Override
    protected void updateBlockedConnections(BlockPos nodePos, EnumFacing facing, boolean isBlocked) {
        super.updateBlockedConnections(nodePos, facing, isBlocked);
        getStorageNetwork().handleBlockedConnectionChange(nodePos, facing, isBlocked);
    }

    public void nodeNeighbourChanged(BlockPos nodePos) {
        if (containsNode(nodePos)) {
            int blockedConnections = getNodeAt(nodePos).blockedConnections;
            getStorageNetwork().checkForItemHandlers(nodePos, blockedConnections);
        }
    }

    @Override
    protected void transferNodeData(Map<BlockPos, Node<EmptyNodeData>> transferredNodes, PipeNet<EmptyNodeData> parentNet) {
        super.transferNodeData(transferredNodes, parentNet);
        InventoryPipeNet parentInventoryNet = (InventoryPipeNet) parentNet;
        if (parentInventoryNet.storageNetwork != null) {
            parentInventoryNet.storageNetwork.transferItemHandlers(transferredNodes.keySet(), getStorageNetwork());
        }
    }

    @Override
    protected void addNodeSilently(BlockPos nodePos, Node<EmptyNodeData> node) {
        super.addNodeSilently(nodePos, node);
        // Review: Correct place? Looks for adjacent inventories during deserialisation 
        getStorageNetwork().checkForItemHandlers(nodePos, node.blockedConnections);
    }

    @Override
    protected Node<EmptyNodeData> removeNodeWithoutRebuilding(BlockPos nodePos) {
        getStorageNetwork().removeItemHandlers(nodePos);
        return super.removeNodeWithoutRebuilding(nodePos);
    }

    public ItemStorageNetwork getStorageNetwork() {
        if (storageNetwork == null) {
            Preconditions.checkNotNull(getWorldData(), "World is null at the time getStorageNetwork is called!");
            this.storageNetwork = new ItemStorageNetwork(getWorldData());
        }
        return storageNetwork;
    }

    @Override
    protected void writeNodeData(EmptyNodeData nodeData, NBTTagCompound tagCompound) {
    }

    @Override
    protected EmptyNodeData readNodeData(NBTTagCompound tagCompound) {
        return EmptyNodeData.INSTANCE;
    }
}
