package gregtech.common.pipelike.fluidpipe.net;

import gregtech.api.pipenet.WorldPipeNet;
import gregtech.common.pipelike.fluidpipe.FluidPipeProperties;
import net.minecraft.world.World;

public class WorldFluidPipeNet extends WorldPipeNet<FluidPipeProperties, FluidPipeNet> {

    private static final String DATA_ID_BASE = "gregtech.fluid_pipe_net";

    public static WorldFluidPipeNet getWorldPipeNet(World world) {
        final String DATA_ID = getDataID(DATA_ID_BASE, world);
        // First look for per dimension data
        WorldFluidPipeNet netWorldData = (WorldFluidPipeNet) world.loadData(WorldFluidPipeNet.class, DATA_ID);
        if (netWorldData == null) {
            // Next look for the old shared data
            netWorldData = (WorldFluidPipeNet) world.loadData(WorldFluidPipeNet.class, DATA_ID_BASE);
            if (netWorldData != null)
                netWorldData.old = true;
        }
        // No saved data, create it and queue it to be saved
        if (netWorldData == null) {
            netWorldData = new WorldFluidPipeNet(DATA_ID);
            world.setData(DATA_ID, netWorldData);
        }
        netWorldData.setWorldAndInit(world);
        return netWorldData;
    }

    public WorldFluidPipeNet(String name) {
        super(name);
    }

    @Override
    protected void removePipeNet(FluidPipeNet pipeNet) {
        super.removePipeNet(pipeNet);
    }

    @Override
    protected FluidPipeNet createNetInstance() {
        return new FluidPipeNet(this);
    }

}
