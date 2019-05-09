package gregtech.api.pipenet;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants.NBT;

import java.util.ArrayList;
import java.util.List;

public abstract class WorldPipeNet<NodeDataType, T extends PipeNet<NodeDataType>> extends WorldSavedData {

    protected World world;
    protected List<T> pipeNets = new ArrayList<>();

    public WorldPipeNet(String name) {
        super(name);
    }

    public World getWorld() {
        return world;
    }

    public void addNode(BlockPos nodePos, NodeDataType nodeData, int mark, int blockedConnections, boolean isActive) {
        T myPipeNet = null;
        Node<NodeDataType> node = new Node<>(nodeData, blockedConnections, mark, isActive);
        for (EnumFacing facing : EnumFacing.VALUES) {
            BlockPos offsetPos = nodePos.offset(facing);
            T pipeNet = getNetFromPos(offsetPos);
            Node<NodeDataType> secondNode = pipeNet == null ? null : pipeNet.getAllNodes().get(offsetPos);
            if (pipeNet != null && pipeNet.canAttachNode(nodeData) &&
                pipeNet.canNodesConnect(secondNode, facing.getOpposite(), node, null)) {
                if (myPipeNet == null) {
                    myPipeNet = pipeNet;
                    myPipeNet.addNode(nodePos, node);
                } else if (myPipeNet != pipeNet) {
                    myPipeNet.uniteNetworks(pipeNet);
                }
            }

        }
        if (myPipeNet == null) {
            myPipeNet = createNetInstance();
            myPipeNet.addNode(nodePos, node);
            addPipeNet(myPipeNet);
            markDirty();
        }
    }

    public void removeNode(BlockPos nodePos) {
        T pipeNet = getNetFromPos(nodePos);
        if (pipeNet != null) {
            pipeNet.removeNode(nodePos);
        }
    }

    public void updateBlockedConnections(BlockPos nodePos, EnumFacing side, boolean isBlocked) {
        T pipeNet = getNetFromPos(nodePos);
        if (pipeNet != null) {
            pipeNet.updateBlockedConnections(nodePos, side, isBlocked);
        }
    }

    public void updateMark(BlockPos nodePos, int newMark) {
        T pipeNet = getNetFromPos(nodePos);
        if (pipeNet != null) {
            pipeNet.updateMark(nodePos, newMark);
        }
    }

    public T getNetFromPos(BlockPos blockPos) {
        for (T pipeNet : pipeNets) {
            if (pipeNet.containsNode(blockPos))
                return pipeNet;
        }
        return null;
    }

    protected void addPipeNet(T pipeNet) {
        this.pipeNets.add(pipeNet);
        pipeNet.isValid = true;
    }

    protected void removePipeNet(T pipeNet) {
        this.pipeNets.remove(pipeNet);
        pipeNet.isValid = false;
    }

    protected abstract T createNetInstance();

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        this.pipeNets = new ArrayList<>();
        NBTTagList allEnergyNets = nbt.getTagList("PipeNets", NBT.TAG_COMPOUND);
        for (int i = 0; i < allEnergyNets.tagCount(); i++) {
            NBTTagCompound pNetTag = allEnergyNets.getCompoundTagAt(i);
            T pipeNet = createNetInstance();
            pipeNets.add(pipeNet);
            pipeNet.isValid = true;
            pipeNet.deserializeNBT(pNetTag);
        }
        this.pipeNets.forEach(PipeNet::onConnectionsUpdate);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagList allPipeNets = new NBTTagList();
        for (T pipeNet : pipeNets) {
            NBTTagCompound pNetTag = pipeNet.serializeNBT();
            allPipeNets.appendTag(pNetTag);
        }
        compound.setTag("PipeNets", allPipeNets);
        return compound;
    }
}
