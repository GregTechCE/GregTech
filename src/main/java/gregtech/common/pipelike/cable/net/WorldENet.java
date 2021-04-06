package gregtech.common.pipelike.cable.net;

import gregtech.api.pipenet.WorldPipeNet;
import gregtech.common.pipelike.cable.WireProperties;
import net.minecraft.world.World;

public class WorldENet extends WorldPipeNet<WireProperties, EnergyNet> {

    private static final String DATA_ID_BASE = "gregtech.e_net";

    public static WorldENet getWorldENet(World world) {
        final String DATA_ID = getDataID(DATA_ID_BASE, world);
        // First look for per dimension data
        WorldENet eNetWorldData = (WorldENet) world.loadData(WorldENet.class, DATA_ID);
        if (eNetWorldData == null) {
            // Next look for the old shared data
            eNetWorldData = (WorldENet) world.loadData(WorldENet.class, DATA_ID_BASE);
            if (eNetWorldData != null)
                eNetWorldData.old = true;
        }
        // No saved data, create it and queue it to be saved
        if (eNetWorldData == null) {
            eNetWorldData = new WorldENet(DATA_ID);
            world.setData(DATA_ID, eNetWorldData);
        }
        eNetWorldData.setWorldAndInit(world);
        return eNetWorldData;
    }

    public WorldENet(String name) {
        super(name);
    }

    @Override
    protected EnergyNet createNetInstance() {
        return new EnergyNet(this);
    }

}
