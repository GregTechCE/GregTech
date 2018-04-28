package gregtech.common.cable.net;

import gregtech.common.cable.RoutePath;
import gregtech.common.cable.WireProperties;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.material.type.MetalMaterial;
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

public class EnergyNet implements INBTSerializable<NBTTagCompound> {

    private final WorldENet worldData;
    private Map<BlockPos, WireProperties> allNodes = new HashMap<>();
    private Map<BlockPos, Integer> blockedConnections = new HashMap<>();
    private Set<BlockPos> activeNodes = new HashSet<>();
    private long lastUpdatedTime;

    public EnergyNet(WorldENet world) {
        this.worldData = world;
    }

    private EnergyNet(WorldENet worldENet, Map<BlockPos, WireProperties> allNodesCopy, Map<BlockPos, Integer> blockedConnections, Set<BlockPos> activeNodes) {
        this.worldData = worldENet;
        this.allNodes = allNodesCopy;
        this.activeNodes = new HashSet<>(activeNodes);
        this.blockedConnections = new HashMap<>(blockedConnections);
        this.blockedConnections.keySet().removeIf(p -> !allNodes.containsKey(p));
        this.activeNodes.removeIf(p -> !allNodes.containsKey(p));
    }

    public Map<BlockPos, WireProperties> getAllNodes() {
        return Collections.unmodifiableMap(allNodes);
    }

    public Set<BlockPos> getActiveNodes() {
        return Collections.unmodifiableSet(activeNodes);
    }

    public World getWorldData() {
        return worldData.getWorld();
    }

    public void removeNode(BlockPos nodePos) {
        if(allNodes.containsKey(nodePos)) {
            activeNodes.remove(nodePos);
            blockedConnections.remove(nodePos);
            boolean needToUpdateRoutes = allNodes.remove(nodePos) != null;
            int amountOfConnectedSides = 0;
            for(EnumFacing facing : EnumFacing.values()) {
                BlockPos offsetPos = nodePos.offset(facing);
                if(allNodes.containsKey(offsetPos))
                    amountOfConnectedSides++;
            }
            //if we are connected only on one side or not connected at all, we don't need to find connected blocks
            //because they are only on on side or doesn't exist at all
            //this saves a lot of performance in big networks, which are quite big to depth-first them fastly
            if(amountOfConnectedSides >= 2) {
                for(EnumFacing facing : EnumFacing.VALUES) {
                    BlockPos offsetPos = nodePos.offset(facing);
                    if(!containsNotBlocked(offsetPos, facing.getOpposite()))
                        continue;
                    HashMap<BlockPos, WireProperties> thisENet = findAllConnectedBlocks(offsetPos);
                    if(allNodes.equals(thisENet)) {
                        //if cable on some direction contains all nodes of this network
                        //the network didn't change so keep it as is
                        break;
                    } else {
                        //and use them to create new network with caching active nodes set
                        EnergyNet energyNet = new EnergyNet(worldData, thisENet, blockedConnections, activeNodes);
                        //remove blocks that aren't connected with this network
                        allNodes.keySet().removeAll(thisENet.keySet());
                        blockedConnections.keySet().removeAll(thisENet.keySet());
                        activeNodes.removeAll(thisENet.keySet());
                        worldData.addEnergyNet(energyNet);
                        needToUpdateRoutes = true;
                    }
                }
            }
            if(allNodes.isEmpty()) {
                //if this energy net is empty now, remove it
                worldData.removeEnergyNet(this);
            }
            if(needToUpdateRoutes) {
                //update routes only if needed, since it's quite expensive operation too
                lastUpdatedTime = System.currentTimeMillis();
            }
            worldData.markDirty();
        }
    }

    //used to update paths cache in TileEntityCable
    public long getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public boolean containsNode(BlockPos blockPos) {
        return allNodes.containsKey(blockPos);
    }

    public void addNode(BlockPos nodePos, WireProperties wireProperties, int blockedConnectionsMask) {
        allNodes.put(nodePos, wireProperties);
        blockedConnections.put(nodePos, blockedConnectionsMask);
        worldData.markDirty();
    }

    public void markNodeAsActive(BlockPos nodePos) {
        if(allNodes.containsKey(nodePos)) {
            activeNodes.add(nodePos);
            worldData.markDirty();
            lastUpdatedTime = System.currentTimeMillis();
        }
    }

    public void markNodeAsInactive(BlockPos nodePos) {
        if(allNodes.containsKey(nodePos)) {
            activeNodes.remove(nodePos);
            worldData.markDirty();
            lastUpdatedTime = System.currentTimeMillis();
        }
    }

    public void uniteNetworks(EnergyNet energyNet) {
        worldData.removeEnergyNet(energyNet);
        allNodes.putAll(energyNet.allNodes);
        activeNodes.addAll(energyNet.activeNodes);
        blockedConnections.putAll(energyNet.blockedConnections);
        worldData.markDirty();
        lastUpdatedTime = System.currentTimeMillis();
    }

    public boolean containsNotBlocked(BlockPos blockPos, EnumFacing fromSide) {
        return allNodes.containsKey(blockPos) &&
            (blockedConnections.get(blockPos) & 1 << fromSide.getIndex()) == 0;
    }

    public List<RoutePath> computePatches(BlockPos startPos) {
        ArrayList<RoutePath> readyPaths = new ArrayList<>();
        RoutePath currentPath = new RoutePath();
        HashSet<BlockPos> observedSet = new HashSet<>();
        observedSet.add(startPos);
        MutableBlockPos currentPos = new MutableBlockPos(startPos);
        Stack<EnumFacing> moveStack = new Stack<>();
        main: while(true) {
            for(EnumFacing facing : EnumFacing.VALUES) {
                currentPos.move(facing);
                if(containsNotBlocked(currentPos, facing.getOpposite()) && !observedSet.contains(currentPos)) {
                    BlockPos immutablePos = currentPos.toImmutable();
                    observedSet.add(immutablePos);
                    moveStack.push(facing.getOpposite());
                    currentPath.path.put(immutablePos, allNodes.get(immutablePos));
                    if(activeNodes.contains(currentPos)) {
                        //if we are on active node, this is end of our path
                        RoutePath finalizedPath = currentPath.cloneAndCompute(immutablePos);
                        readyPaths.add(finalizedPath);
                    }
                    continue main;
                } else {
                    currentPos.move(facing.getOpposite());
                }
            }
            if(!moveStack.isEmpty()) {
                currentPos.move(moveStack.pop());
                //also remove already visited block from path
                currentPath.path.remove(currentPos);
            } else break;
        }
        return readyPaths;
    }

    //we need to search only this network
    private HashMap<BlockPos, WireProperties> findAllConnectedBlocks(BlockPos startPos) {
        HashMap<BlockPos, WireProperties> observedSet = new HashMap<>();
        observedSet.put(startPos, allNodes.get(startPos));
        MutableBlockPos currentPos = new MutableBlockPos(startPos);
        Stack<EnumFacing> moveStack = new Stack<>();
        main: while(true) {
            for(EnumFacing facing : EnumFacing.VALUES) {
                currentPos.move(facing);
                if(containsNotBlocked(currentPos, facing.getOpposite()) && !observedSet.containsKey(currentPos)) {
                    observedSet.put(currentPos.toImmutable(), allNodes.get(currentPos));
                    moveStack.push(facing.getOpposite());
                    continue main;
                } else currentPos.move(facing.getOpposite());
            }
            if(!moveStack.isEmpty()) {
                currentPos.move(moveStack.pop());
            } else break;
        }
        return observedSet;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setTag("Nodes", serializeAllNodeList(allNodes, blockedConnections, activeNodes));
        return compound;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        Result result = deserializeAllNodeList(nbt.getCompoundTag("Nodes"));
        this.allNodes = result.allNodes;
        this.blockedConnections = result.blockedConnections;
        this.activeNodes = result.activeNodes;
    }

    private static NBTTagCompound serializeAllNodeList(Map<BlockPos, WireProperties> allNodes, Map<BlockPos, Integer> blockedConnections, Set<BlockPos> activeNodes) {
        NBTTagCompound compound = new NBTTagCompound();
        NBTTagList allNodesList = new NBTTagList();
        NBTTagList wirePropertiesList = new NBTTagList();
        HashMap<WireProperties, Integer> alreadyWritten = new HashMap<>();
        int currentIndex = 0;

        for(Entry<BlockPos, WireProperties> entry : allNodes.entrySet()) {
            BlockPos nodePos = entry.getKey();
            WireProperties wireProperties = entry.getValue();
            NBTTagCompound nodeTag = new NBTTagCompound();
            nodeTag.setInteger("x", nodePos.getX());
            nodeTag.setInteger("y", nodePos.getY());
            nodeTag.setInteger("z", nodePos.getZ());
            int wirePropertiesIndex = alreadyWritten.getOrDefault(wireProperties, -1);
            if(wirePropertiesIndex == -1) {
                wirePropertiesIndex = currentIndex;
                alreadyWritten.put(wireProperties, wirePropertiesIndex);
                currentIndex++;
            }
            nodeTag.setInteger("index", wirePropertiesIndex);
            if(blockedConnections.containsKey(nodePos)) {
                nodeTag.setInteger("blocked", blockedConnections.get(nodePos));
            }
            if(activeNodes.contains(nodePos)) {
                nodeTag.setBoolean("active", true);
            }
            allNodesList.appendTag(nodeTag);
        }

        for(Entry<WireProperties, Integer> entry : alreadyWritten.entrySet()) {
            WireProperties wireProperties = entry.getKey();
            int wirePropertiesIndex = entry.getValue();
            NBTTagCompound propertiesTag = new NBTTagCompound();
            propertiesTag.setInteger("index", wirePropertiesIndex);
            propertiesTag.setInteger("voltage", wireProperties.voltage);
            propertiesTag.setInteger("amperage", wireProperties.amperage);
            propertiesTag.setInteger("loss_per_block", wireProperties.lossPerBlock);
            wirePropertiesList.appendTag(propertiesTag);
        }

        compound.setTag("NodeIndexes", allNodesList);
        compound.setTag("WireProperties", wirePropertiesList);
        return compound;
    }

    private static class Result {
        HashMap<BlockPos, WireProperties> allNodes;
        HashMap<BlockPos, Integer> blockedConnections;
        HashSet<BlockPos> activeNodes;

        public Result(HashMap<BlockPos, WireProperties> allNodes, HashMap<BlockPos, Integer> blockedConnections, HashSet<BlockPos> activeNodes) {
            this.allNodes = allNodes;
            this.blockedConnections = blockedConnections;
            this.activeNodes = activeNodes;
        }
    }

    private static Result deserializeAllNodeList(NBTTagCompound compound) {
        NBTTagList allNodesList = compound.getTagList("NodeIndexes", NBT.TAG_COMPOUND);
        NBTTagList wirePropertiesList = compound.getTagList("WireProperties", NBT.TAG_COMPOUND);
        HashMap<Integer, WireProperties> readProperties = new HashMap<>();
        HashMap<BlockPos, WireProperties> allNodes = new HashMap<>();
        HashMap<BlockPos, Integer> blockedConnections = new HashMap<>();
        HashSet<BlockPos> activeNodes = new HashSet<>();

        for(int i = 0; i < wirePropertiesList.tagCount(); i++) {
            NBTTagCompound propertiesTag = wirePropertiesList.getCompoundTagAt(i);
            int wirePropertiesIndex = propertiesTag.getInteger("index");
            int voltage = propertiesTag.getInteger("voltage");
            int amperage = propertiesTag.getInteger("amperage");
            int lossPerBlock = propertiesTag.getInteger("loss_per_block");
            readProperties.put(wirePropertiesIndex, new WireProperties(voltage, amperage, lossPerBlock));
        }

        for(int i = 0; i < allNodesList.tagCount(); i++) {
            NBTTagCompound nodeTag = allNodesList.getCompoundTagAt(i);
            int x = nodeTag.getInteger("x");
            int y = nodeTag.getInteger("y");
            int z = nodeTag.getInteger("z");
            int wirePropertiesIndex = nodeTag.getInteger("index");
            BlockPos blockPos = new BlockPos(x, y, z);
            allNodes.put(blockPos, readProperties.get(wirePropertiesIndex));
            blockedConnections.put(blockPos, nodeTag.getInteger("blocked"));
            if(nodeTag.getBoolean("active")) {
                activeNodes.add(blockPos);
            }
        }

        return new Result(allNodes, blockedConnections, activeNodes);
    }


}
