package gregtech.common.cable.net;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.*;

public class EnergyNet implements INBTSerializable<NBTTagCompound> {

    private WorldENet worldData;
    private Set<BlockPos> allNodes = new HashSet<>();
    private Set<BlockPos> activeNodes = new HashSet<>();

    public EnergyNet(WorldENet world) {
        this.worldData = world;
    }

    public EnergyNet(Set<BlockPos> allNodesCopy, Set<BlockPos> activeNodes) {
        this.allNodes = allNodesCopy;
        this.activeNodes = new HashSet<>(activeNodes);
        this.activeNodes.removeIf(p -> !allNodes.contains(p));
    }

    public World getWorldData() {
        return worldData.getWorld();
    }

    public void removeNode(BlockPos nodePos) {
        if(allNodes.contains(nodePos)) {
            activeNodes.remove(nodePos);
            allNodes.remove(nodePos);
            for(EnumFacing facing : EnumFacing.values()) {
                BlockPos offsetPos = nodePos.offset(facing);
                if(!allNodes.contains(offsetPos))
                    continue;
                HashSet<BlockPos> thisENet = findAllConnectedBlocks(offsetPos);
                if(allNodes.equals(thisENet)) {
                    //if cable on some direction contains all nodes of this network
                    //the network didn't change so keep it as is
                    break;
                } else {
                    //remove blocks that aren't connected with this network
                    allNodes.removeAll(thisENet);
                    activeNodes.removeAll(thisENet);
                    //and use them to create new network with caching active nodes set
                    EnergyNet energyNet = new EnergyNet(thisENet, activeNodes);
                    worldData.addEnergyNet(energyNet);
                }
            }
            if(allNodes.isEmpty()) {
                //if this energy net is empty now, remove it
                worldData.removeEnergyNet(this);
            }
            worldData.markDirty();
        }
    }

    public boolean containsNode(BlockPos blockPos) {
        return allNodes.contains(blockPos);
    }

    public void addNode(BlockPos nodePos) {
        allNodes.add(nodePos);
        worldData.markDirty();
    }

    public void markNodeAsActive(BlockPos nodePos) {
        if(allNodes.contains(nodePos)) {
            activeNodes.add(nodePos);
            worldData.markDirty();
        }
    }

    public void markNodeAsInactive(BlockPos nodePos) {
        if(allNodes.contains(nodePos)) {
            activeNodes.remove(nodePos);
            worldData.markDirty();
        }
    }

    public void uniteNetworks(EnergyNet energyNet) {
        worldData.removeEnergyNet(energyNet);
        allNodes.addAll(energyNet.allNodes);
        activeNodes.addAll(energyNet.activeNodes);
        worldData.markDirty();
    }

    //we need to search only this network
    private HashSet<BlockPos> findAllConnectedBlocks(BlockPos startPos) {
        HashSet<BlockPos> observedSet = new HashSet<>();
        observedSet.add(startPos);
        MutableBlockPos currentPos = new MutableBlockPos(startPos);
        Stack<EnumFacing> moveStack = new Stack<>();
        main: while(true) {
            for(EnumFacing facing : EnumFacing.VALUES) {
                currentPos.move(facing);
                if(allNodes.contains(currentPos) && !observedSet.contains(currentPos)) {
                    observedSet.add(currentPos.toImmutable());
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
        compound.setTag("AllNodes", serializeNodeList(allNodes));
        compound.setTag("ActiveNodes", serializeNodeList(activeNodes));
        return compound;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        this.allNodes = deserializeNodeList(nbt.getTagList("AllNodes", NBT.TAG_COMPOUND));
        this.activeNodes = deserializeNodeList(nbt.getTagList("ActiveNodes", NBT.TAG_COMPOUND));
    }

    private static NBTTagList serializeNodeList(Set<BlockPos> allNodes) {
        NBTTagList allNodesList = new NBTTagList();
        for(BlockPos nodePos : allNodes) {
            NBTTagCompound nodeTag = new NBTTagCompound();
            nodeTag.setInteger("x", nodePos.getX());
            nodeTag.setInteger("y", nodePos.getY());
            nodeTag.setInteger("z", nodePos.getZ());
            allNodesList.appendTag(nodeTag);
        }
        return allNodesList;
    }

    private static HashSet<BlockPos> deserializeNodeList(NBTTagList allNodeList) {
        HashSet<BlockPos> allNodes = new HashSet<>();
        for(int i = 0; i < allNodeList.tagCount(); i++) {
            NBTTagCompound nodeTag = allNodeList.getCompoundTagAt(i);
            int x = nodeTag.getInteger("x");
            int y = nodeTag.getInteger("y");
            int z = nodeTag.getInteger("z");
            allNodes.add(new BlockPos(x, y, z));
        }
        return allNodes;
    }

}
