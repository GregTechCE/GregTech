package gregtech.common.pipelike.inventory.net;

import com.google.common.base.Preconditions;

import gregtech.api.capability.IEnergyContainer;
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

    private InventoryPipeNetEnergyContainer energyContainer;
    
    public InventoryPipeNet(WorldPipeNet<EmptyNodeData, ? extends PipeNet> world) {
        super(world);
        this.energyContainer = new InventoryPipeNetEnergyContainer();
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
        InventoryPipeNetEnergyContainer parentEnergyContainer = parentInventoryNet.energyContainer;
        long parentEnergy = parentEnergyContainer.getEnergyStored();
        if (parentEnergy > 0) {
            if (parentNet.getAllNodes().isEmpty()) {
                //if this is a merge of pipe nets, just add all the energy
                energyContainer.addEnergy(parentEnergy);
            } else {
                //otherwise, it is donating of some nodes to our net in result of split
                //so, we should estabilish equal amount of energy in networks
                long firstNetCapacity = energyContainer.getEnergyCapacity();
                long secondNetCapacity = parentInventoryNet.energyContainer.getEnergyCapacity();
                long totalEnergy = energyContainer.getEnergyStored() + parentEnergy;
                long energy1 = totalEnergy * firstNetCapacity / (firstNetCapacity + secondNetCapacity);
                long energy2 = totalEnergy - energy1;

                energyContainer.setEnergyStored(energy1);
                parentEnergyContainer.setEnergyStored(energy2);
            }
        }
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
            this.storageNetwork = new ItemStorageNetwork(this);
        }
        return storageNetwork;
    }

    public IEnergyContainer getEnergyContainer() {
        return energyContainer;
    }

    @Override
    protected void writeNodeData(EmptyNodeData nodeData, NBTTagCompound tagCompound) {
    }

    @Override
    protected EmptyNodeData readNodeData(NBTTagCompound tagCompound) {
        return EmptyNodeData.INSTANCE;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        final NBTTagCompound nbt = super.serializeNBT();
        nbt.setTag("EnergyContainer", this.energyContainer.serializeNBT());
        return nbt;
    }

    @Override
    public void deserializeNBT(final NBTTagCompound nbt) {
        super.deserializeNBT(nbt);
        final NBTTagCompound energyData = nbt.getCompoundTag("EnergyContainer");
        this.energyContainer.deserializeNBT(energyData);
    }
}
