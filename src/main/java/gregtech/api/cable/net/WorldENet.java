package gregtech.api.cable.net;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants.NBT;

import java.util.ArrayList;
import java.util.List;

public class WorldENet extends WorldSavedData {

    private static final String DATA_ID = "gregtech:e_net";
    private World world;
    private List<EnergyNet> energyNets = new ArrayList<>();

    public static WorldENet getWorldENet(World world) {
        WorldENet eNetWorldData = (WorldENet) world.loadData(WorldENet.class, DATA_ID);
        if(eNetWorldData == null) {
            eNetWorldData = new WorldENet(DATA_ID);
            world.setData(DATA_ID, eNetWorldData);
        }
        eNetWorldData.world = world;
        return eNetWorldData;
    }

    public WorldENet(String name) {
        super(name);
    }

    public World getWorld() {
        return world;
    }

    public EnergyNet getNetFromPos(BlockPos blockPos) {
        for(EnergyNet energyNet : energyNets) {
            if(energyNet.containsNode(blockPos))
                return energyNet;
        }
        return null;
    }

    public void addEnergyNet(EnergyNet energyNet) {
        this.energyNets.add(energyNet);
    }

    public void removeEnergyNet(EnergyNet energyNet) {
        this.energyNets.remove(energyNet);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        this.energyNets = new ArrayList<>();
        NBTTagList allEnergyNets = nbt.getTagList("EnergyNets", NBT.TAG_COMPOUND);
        for(int i = 0; i < allEnergyNets.tagCount(); i++) {
            NBTTagCompound eNetTag = allEnergyNets.getCompoundTagAt(i);
            EnergyNet eNet = new EnergyNet(this);
            eNet.deserializeNBT(eNetTag);
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagList allEnergyNets = new NBTTagList();
        for (EnergyNet energyNet : energyNets) {
            NBTTagCompound eNetTag = energyNet.serializeNBT();
            allEnergyNets.appendTag(eNetTag);
        }
        compound.setTag("EnergyNets", allEnergyNets);
        return compound;
    }
}
