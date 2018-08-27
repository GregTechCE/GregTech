package gregtech.common.pipelike.cable.net;

import gregtech.api.pipenet.WorldPipeNet;
import gregtech.common.pipelike.cable.WireProperties;
import net.minecraft.world.World;

public class WorldENet extends WorldPipeNet<WireProperties, EnergyNet> {

    private static final String DATA_ID = "gregtech.e_net";

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

    @Override
    protected EnergyNet createNetInstance() {
        return new EnergyNet(this);
    }

}
