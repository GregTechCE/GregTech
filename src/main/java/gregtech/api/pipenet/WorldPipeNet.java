package gregtech.api.pipenet;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants.NBT;

import java.lang.ref.WeakReference;
import java.util.*;

import gregtech.api.util.GTLog;

public abstract class WorldPipeNet<NodeDataType, T extends PipeNet<NodeDataType>> extends WorldSavedData {

    private WeakReference<World> worldRef = new WeakReference<>(null);
    protected List<T> pipeNets = new ArrayList<>();
    protected Map<ChunkPos, List<T>> pipeNetsByChunk = new HashMap<>();
    protected WorldPipeNet<NodeDataType, T> oldData = null;
    protected boolean checkedForOldData = false;

    public static String getDataID(final String baseID, final World world) {
        if (world == null || world.isRemote)
            throw new RuntimeException("WorldPipeNet should only be created on the server!");
        final int dimension = world.provider.getDimension();
        return baseID + '.' + dimension;
    }

    public WorldPipeNet(String name) {
        super(name);
    }

    public World getWorld() {
        return this.worldRef.get();
    }

    protected void setWorldAndInit(World world) {
        // Reset the world as the dimensions are loaded/unloaded
        if (world != this.worldRef.get()) {
            this.worldRef = new WeakReference<World>(world);
            onWorldSet();
        }
    }

    protected void onWorldSet() {
        this.pipeNets.forEach(PipeNet::onConnectionsUpdate);
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

    protected void addPipeNetToChunk(ChunkPos chunkPos, T pipeNet) {
        this.pipeNetsByChunk.computeIfAbsent(chunkPos, any -> new ArrayList<>()).add(pipeNet);
    }

    protected void removePipeNetFromChunk(ChunkPos chunkPos, T pipeNet) {
        List<T> list = this.pipeNetsByChunk.get(chunkPos);
        if (list != null) list.remove(pipeNet);
        if (list.isEmpty()) this.pipeNetsByChunk.remove(chunkPos);
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
        List<T> pipeNetsInChunk = pipeNetsByChunk.getOrDefault(new ChunkPos(blockPos), Collections.emptyList());
        for (T pipeNet : pipeNetsInChunk) {
            if (pipeNet.containsNode(blockPos))
                return pipeNet;
        }
        return checkForOldData(blockPos);
    }

    protected void addPipeNet(T pipeNet) {
        addPipeNetSilently(pipeNet);
    }

    protected void addPipeNetSilently(T pipeNet) {
        this.pipeNets.add(pipeNet);
        pipeNet.getContainedChunks().forEach(chunkPos -> addPipeNetToChunk(chunkPos, pipeNet));
        pipeNet.isValid = true;
    }

    protected void removePipeNet(T pipeNet) {
        this.pipeNets.remove(pipeNet);
        pipeNet.getContainedChunks().forEach(chunkPos -> removePipeNetFromChunk(chunkPos, pipeNet));
        pipeNet.isValid = false;
    }

    protected abstract T createNetInstance();

    /*
     * This method is used when data cannot be found at the given position.
     *
     * It's purpose is to move pipenets from the old data file to new one based on matching block position
     */
    private T checkForOldData(final BlockPos blockPos) {
        // No old data
        if (this.oldData == null || this.oldData.pipeNets.isEmpty())
            return null;

        // See if we have a pipenet for this block pos in the old data
        T foundOldData = null;
        final List<T> oldPipeNets = this.oldData.pipeNetsByChunk.getOrDefault(new ChunkPos(blockPos), Collections.emptyList());
        for (T pipeNet : oldPipeNets) {
            if (pipeNet.containsNode(blockPos)) {
                if (foundOldData != null) {
                    // We have 2 pipenets at this position?
                    GTLog.logger.warn("Found duplicate pipenets in old data at {} [{},{}]", blockPos, foundOldData, pipeNet);
                    return null;
                }
                foundOldData = pipeNet;
            }
        }
        // Nothing found
        if (foundOldData == null)
            return null;
        // Move the old data into the new data
        GTLog.logger.info("Fixing old data for {} found at {}", foundOldData, blockPos);
        this.oldData.removePipeNet(foundOldData);
        this.oldData.markDirty();
        this.addPipeNetSilently(foundOldData);
        this.markDirty();
        foundOldData.setWorldData(this);
        return foundOldData;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        this.pipeNets = new ArrayList<>();
        NBTTagList allEnergyNets = nbt.getTagList("PipeNets", NBT.TAG_COMPOUND);
        for (int i = 0; i < allEnergyNets.tagCount(); i++) {
            NBTTagCompound pNetTag = allEnergyNets.getCompoundTagAt(i);
            T pipeNet = createNetInstance();
            pipeNet.deserializeNBT(pNetTag);
            addPipeNetSilently(pipeNet);
        }
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
