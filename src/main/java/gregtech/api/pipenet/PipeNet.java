package gregtech.api.pipenet;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.*;
import java.util.Map.Entry;

public abstract class PipeNet<NodeDataType> implements INBTSerializable<NBTTagCompound> {

    protected final WorldPipeNet<NodeDataType, PipeNet<NodeDataType>> worldData;
    protected Map<BlockPos, Node<NodeDataType>> allNodes = new HashMap<>();
    private long lastUpdate;
    protected boolean isValid;

    public PipeNet(WorldPipeNet<NodeDataType, ? extends PipeNet> world) {
        //noinspection unchecked
        this.worldData = (WorldPipeNet<NodeDataType, PipeNet<NodeDataType>>) world;
    }

    public Map<BlockPos, Node<NodeDataType>> getAllNodes() {
        return Collections.unmodifiableMap(allNodes);
    }

    public World getWorldData() {
        return worldData.getWorld();
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    public boolean isValid() {
        return isValid;
    }

    protected void onConnectionsUpdate() {
        this.lastUpdate = System.currentTimeMillis();
    }

    public boolean containsNode(BlockPos blockPos) {
        return allNodes.containsKey(blockPos);
    }

    protected void addNode(BlockPos nodePos, Node<NodeDataType> node) {
        allNodes.put(nodePos, node);
        worldData.markDirty();
        onConnectionsUpdate();
    }

    protected void removeNode(BlockPos nodePos) {
        if (allNodes.containsKey(nodePos)) {
            Node<NodeDataType> selfNode = allNodes.remove(nodePos);
            removeNodeInternal(nodePos, selfNode);
            worldData.markDirty();
        }
    }

    protected void updateBlockedConnections(BlockPos nodePos, EnumFacing facing, boolean isBlocked) {
        if(!allNodes.containsKey(nodePos)) {
            return;
        }
        Node<NodeDataType> selfNode = allNodes.get(nodePos);
        boolean wasBlocked = (selfNode.blockedConnections & 1 << facing.getIndex()) > 0;
        if(wasBlocked == isBlocked) {
            return;
        }
        setBlocked(selfNode, facing, isBlocked);

        BlockPos offsetPos = nodePos.offset(facing);
        //noinspection unchecked
        PipeNet<NodeDataType> pipeNetAtOffset = worldData.getNetFromPos(offsetPos);
        if (pipeNetAtOffset == null) {
            //if there is no any pipe net at this side,
            //updating blocked status of it won't change anything in any net
            return;
        }
        //if we are on that side of node too
        //and it is blocked now
        if (pipeNetAtOffset == this) {
            //if side was unblocked, well, there is really nothing changed in this e-net
            //if it is blocked now, but was able to connect with neighbour node before, try split networks
            if(isBlocked && canNodesConnect(selfNode, facing, allNodes.get(offsetPos), this)) {
                setBlocked(selfNode, facing, true); //update current status before querying findAllConnectedBlocks
                HashMap<BlockPos, Node<NodeDataType>> thisENet = findAllConnectedBlocks(nodePos);
                if (!allNodes.equals(thisENet)) {
                    //node visibility has changed, split network into 2
                    //node that code below is similar to removeNodeInternal, but only for 2 networks, and without node removal
                    //noinspection unchecked
                    PipeNet<NodeDataType> newPipeNet = worldData.createNetInstance();
                    newPipeNet.transferNodeData(thisENet, this);
                    allNodes.keySet().removeAll(thisENet.keySet());
                    worldData.addPipeNet(newPipeNet);
                }
            }
        //there is another network on that side
        //if this is an unblock, and we can connect with their node, merge them
        } else if(!isBlocked) {
            Node<NodeDataType> neighbourNode = pipeNetAtOffset.allNodes.get(offsetPos);
            //check connection availability from both networks
            if(canNodesConnect(selfNode, facing, neighbourNode, pipeNetAtOffset) &&
                pipeNetAtOffset.canNodesConnect(neighbourNode, facing.getOpposite(), selfNode, this)) {
                //so, side is unblocked now, and nodes can connect, merge two networks
                //our network consumes other one
                uniteNetworks(pipeNetAtOffset);
            }
        }
        onConnectionsUpdate();
        worldData.markDirty();
    }

    protected void updateMark(BlockPos nodePos, int newMark) {
        if(!allNodes.containsKey(nodePos)) {
            return;
        }
        HashMap<BlockPos, Node<NodeDataType>> selfConnectedBlocks = null;
        Node<NodeDataType> selfNode = allNodes.get(nodePos);
        int oldMark = selfNode.mark;
        selfNode.mark = newMark;
        for (EnumFacing facing : EnumFacing.VALUES) {
            BlockPos offsetPos = nodePos.offset(facing);
            PipeNet<NodeDataType> otherPipeNet = worldData.getNetFromPos(offsetPos);
            Node<NodeDataType> secondNode = otherPipeNet == null ? null : otherPipeNet.allNodes.get(offsetPos);
            if(secondNode == null)
                continue; //there is noting here
            if(!areNodeBlockedConnectionsCompatible(selfNode, facing, secondNode) ||
                !areNodesCustomContactable(selfNode.data, secondNode.data, otherPipeNet))
                continue; //if connections aren't compatible, skip them
            if(areMarksCompatible(oldMark, secondNode.mark) == areMarksCompatible(newMark, secondNode.mark))
                continue; //if compatibility didn't change, skip it
            if(areMarksCompatible(newMark, secondNode.mark)) {
                //if marks are compatible now, and offset network is different network, merge them
                //if it is same network, just update mask and paths
                if (otherPipeNet != this) {
                    uniteNetworks(otherPipeNet);
                }
            //marks are incompatible now, and this net is connected with it
            } else if(otherPipeNet == this) {
                //search connected nodes from newly marked node
                //populate self connected blocks lazily only once
                if(selfConnectedBlocks == null) {
                    selfConnectedBlocks = findAllConnectedBlocks(nodePos);
                }
                if(allNodes.equals(selfConnectedBlocks)) {
                    continue; //if this node is still connected to this network, just continue
                }
                //otherwise, it is not connected
                HashMap<BlockPos, Node<NodeDataType>> offsetConnectedBlocks = findAllConnectedBlocks(offsetPos);
                //if in the result of remarking offset node has separated from main network,
                //and it is also separated from current cable too, form new network for it
                if(!offsetConnectedBlocks.equals(selfConnectedBlocks)) {
                    allNodes.keySet().removeAll(offsetConnectedBlocks.keySet());
                    PipeNet<NodeDataType> offsetPipeNet = worldData.createNetInstance();
                    offsetPipeNet.transferNodeData(offsetConnectedBlocks, this);
                    worldData.addPipeNet(offsetPipeNet);
                }
            }
        }
        onConnectionsUpdate();
        worldData.markDirty();
    }

    private void setBlocked(Node<NodeDataType> selfNode, EnumFacing facing, boolean isBlocked) {
        if(isBlocked) {
            selfNode.blockedConnections |= 1 << facing.getIndex();
        } else {
            selfNode.blockedConnections &= ~(1 << facing.getIndex());
        }
    }

    public boolean markNodeAsActive(BlockPos nodePos, boolean isActive) {
        if(allNodes.containsKey(nodePos) && allNodes.get(nodePos).isActive != isActive) {
            allNodes.get(nodePos).isActive = isActive;
            worldData.markDirty();
            onConnectionsUpdate();
            return true;
        }
        return false;
    }

    protected final void uniteNetworks(PipeNet<NodeDataType> energyNet) {
        worldData.removePipeNet(energyNet);
        //noinspection unchecked
        //this is needed to conform to transferNodeData specification
        Map<BlockPos, Node<NodeDataType>> allNodes = new HashMap<>(energyNet.allNodes);
        energyNet.allNodes.clear();
        transferNodeData(allNodes, energyNet);
    }

    private boolean areNodeBlockedConnectionsCompatible(Node<NodeDataType> first, EnumFacing firstFacing, Node<NodeDataType> second) {
        return (first.blockedConnections & 1 << firstFacing.getIndex()) == 0 &&
            (second.blockedConnections & 1 << firstFacing.getOpposite().getIndex()) == 0;
    }

    private boolean areMarksCompatible(int mark1, int mark2) {
        return mark1 == mark2 || mark1 == Node.DEFAULT_MARK || mark2 == Node.DEFAULT_MARK;
    }

    /**
     * Checks if given nodes can connect
     * Note that this logic should equal with block connection logic
     * for proper work of network
     */
    protected final boolean canNodesConnect(Node<NodeDataType> first, EnumFacing firstFacing, Node<NodeDataType> second, PipeNet<NodeDataType> secondPipeNet) {
        return areNodeBlockedConnectionsCompatible(first, firstFacing, second) &&
            areMarksCompatible(first.mark, second.mark) &&
            areNodesCustomContactable(first.data, second.data, secondPipeNet);
    }

    //we need to search only this network
    protected HashMap<BlockPos, Node<NodeDataType>> findAllConnectedBlocks(BlockPos startPos) {
        HashMap<BlockPos, Node<NodeDataType>> observedSet = new HashMap<>();
        observedSet.put(startPos, allNodes.get(startPos));
        Node<NodeDataType> firstNode = allNodes.get(startPos);
        MutableBlockPos currentPos = new MutableBlockPos(startPos);
        Stack<EnumFacing> moveStack = new Stack<>();
        main: while(true) {
            for(EnumFacing facing : EnumFacing.VALUES) {
                currentPos.move(facing);
                Node<NodeDataType> secondNode = allNodes.get(currentPos);
                //if there is node, and it can connect with previous node, add it to list, and set previous node as current
                if(secondNode != null && canNodesConnect(firstNode, facing, secondNode, this) && !observedSet.containsKey(currentPos)) {
                    observedSet.put(currentPos.toImmutable(), allNodes.get(currentPos));
                    firstNode = secondNode;
                    moveStack.push(facing.getOpposite());
                    continue main;
                } else currentPos.move(facing.getOpposite());
            }
            if(!moveStack.isEmpty()) {
                currentPos.move(moveStack.pop());
                firstNode = allNodes.get(currentPos);
            } else break;
        }
        return observedSet;
    }

    //called when node is removed to rebuild network
    protected void removeNodeInternal(BlockPos nodePos, Node<NodeDataType> selfNode) {
        int amountOfConnectedSides = 0;
        for (EnumFacing facing : EnumFacing.values()) {
            BlockPos offsetPos = nodePos.offset(facing);
            if (allNodes.containsKey(offsetPos))
                amountOfConnectedSides++;
        }
        //if we are connected only on one side or not connected at all, we don't need to find connected blocks
        //because they are only on on side or doesn't exist at all
        //this saves a lot of performance in big networks, which are quite big to depth-first them fastly
        if (amountOfConnectedSides >= 2) {
            for (EnumFacing facing : EnumFacing.VALUES) {
                BlockPos offsetPos = nodePos.offset(facing);
                Node<NodeDataType> secondNode = allNodes.get(offsetPos);
                if (secondNode == null || !canNodesConnect(selfNode, facing, secondNode, this)) {
                    //if there isn't any neighbour node, or it wasn't connected with us, just skip it
                    continue;
                }
                HashMap<BlockPos, Node<NodeDataType>> thisENet = findAllConnectedBlocks(offsetPos);
                if (allNodes.equals(thisENet)) {
                    //if cable on some direction contains all nodes of this network
                    //the network didn't change so keep it as is
                    break;
                } else {
                    //and use them to create new network with caching active nodes set
                    PipeNet<NodeDataType> energyNet = worldData.createNetInstance();
                    //noinspection unchecked
                    //remove blocks that aren't connected with this network
                    allNodes.keySet().removeAll(thisENet.keySet());
                    energyNet.transferNodeData(thisENet, this);
                    worldData.addPipeNet(energyNet);
                }
            }
        }
        if (allNodes.isEmpty()) {
            //if this energy net is empty now, remove it
            worldData.removePipeNet(this);
        }
        onConnectionsUpdate();
        worldData.markDirty();
    }

    protected boolean areNodesCustomContactable(NodeDataType first, NodeDataType second, PipeNet<NodeDataType> secondNodePipeNet) {
        return true;
    }

    protected boolean canAttachNode(NodeDataType nodeData) {
        return true;
    }

    /**
     * Called during network split when one net needs to transfer some of it's nodes to another one
     * Use this for diving old net contents according to node amount of new network
     * For example, for fluid pipes it would remove amount of fluid contained in old nodes
     * from parent network and add it to it's own tank, keeping network contents when old network is split
     * Note that it should be called when parent net doesn't have transferredNodes in allNodes already
     */
    protected void transferNodeData(Map<BlockPos, Node<NodeDataType>> transferredNodes, PipeNet<NodeDataType> parentNet) {
        this.allNodes.putAll(transferredNodes);
        onConnectionsUpdate();
        worldData.markDirty();
    }

    /**
     * Serializes node data into specified tag compound
     * Used for writing persistent node data
     */
    protected abstract void writeNodeData(NodeDataType nodeData, NBTTagCompound tagCompound);

    /**
     * Deserializes node data from specified tag compound
     * Used for reading persistent node data
     */
    protected abstract NodeDataType readNodeData(NBTTagCompound tagCompound);

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setTag("Nodes", serializeAllNodeList(allNodes));
        return compound;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        this.allNodes = deserializeAllNodeList(nbt.getCompoundTag("Nodes"));
    }

    protected Map<BlockPos, Node<NodeDataType>> deserializeAllNodeList(NBTTagCompound compound) {
        NBTTagList allNodesList = compound.getTagList("NodeIndexes", NBT.TAG_COMPOUND);
        NBTTagList wirePropertiesList = compound.getTagList("WireProperties", NBT.TAG_COMPOUND);
        TIntObjectMap<NodeDataType> readProperties = new TIntObjectHashMap<>();
        HashMap<BlockPos, Node<NodeDataType>> allNodes = new HashMap<>();

        for(int i = 0; i < wirePropertiesList.tagCount(); i++) {
            NBTTagCompound propertiesTag = wirePropertiesList.getCompoundTagAt(i);
            int wirePropertiesIndex = propertiesTag.getInteger("index");
            NodeDataType nodeData = readNodeData(propertiesTag);
            readProperties.put(wirePropertiesIndex, nodeData);
        }

        for(int i = 0; i < allNodesList.tagCount(); i++) {
            NBTTagCompound nodeTag = allNodesList.getCompoundTagAt(i);
            int x = nodeTag.getInteger("x");
            int y = nodeTag.getInteger("y");
            int z = nodeTag.getInteger("z");
            int wirePropertiesIndex = nodeTag.getInteger("index");
            BlockPos blockPos = new BlockPos(x, y, z);
            NodeDataType nodeData = readProperties.get(wirePropertiesIndex);
            int blockedConnections = nodeTag.getInteger("blocked");
            int mark = nodeTag.getInteger("mark");
            boolean isNodeActive = nodeTag.getBoolean("active");
            allNodes.put(blockPos, new Node<>(nodeData, blockedConnections, mark, isNodeActive));
        }

        return allNodes;
    }

    protected NBTTagCompound serializeAllNodeList(Map<BlockPos, Node<NodeDataType>> allNodes) {
        NBTTagCompound compound = new NBTTagCompound();
        NBTTagList allNodesList = new NBTTagList();
        NBTTagList wirePropertiesList = new NBTTagList();
        TObjectIntMap<NodeDataType> alreadyWritten = new TObjectIntHashMap<>(10, 0.5f, -1);
        int currentIndex = 0;

        for(Entry<BlockPos, Node<NodeDataType>> entry : allNodes.entrySet()) {
            BlockPos nodePos = entry.getKey();
            Node<NodeDataType> node = entry.getValue();
            NBTTagCompound nodeTag = new NBTTagCompound();
            nodeTag.setInteger("x", nodePos.getX());
            nodeTag.setInteger("y", nodePos.getY());
            nodeTag.setInteger("z", nodePos.getZ());
            int wirePropertiesIndex = alreadyWritten.get(node.data);
            if(wirePropertiesIndex == -1) {
                wirePropertiesIndex = currentIndex;
                alreadyWritten.put(node.data, wirePropertiesIndex);
                currentIndex++;
            }
            nodeTag.setInteger("index", wirePropertiesIndex);
            if(node.mark != Node.DEFAULT_MARK) {
                nodeTag.setInteger("mark", node.mark);
            }
            if(node.blockedConnections > 0) {
                nodeTag.setInteger("blocked", node.blockedConnections);
            }
            if(node.isActive) {
                nodeTag.setBoolean("active", true);
            }
            allNodesList.appendTag(nodeTag);
        }

        for(NodeDataType nodeData : alreadyWritten.keySet()) {
            int wirePropertiesIndex = alreadyWritten.get(nodeData);
            NBTTagCompound propertiesTag = new NBTTagCompound();
            propertiesTag.setInteger("index", wirePropertiesIndex);
            writeNodeData(nodeData, propertiesTag);
            wirePropertiesList.appendTag(propertiesTag);
        }

        compound.setTag("NodeIndexes", allNodesList);
        compound.setTag("WireProperties", wirePropertiesList);
        return compound;
    }

}
