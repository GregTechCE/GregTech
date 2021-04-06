package gregtech.common.pipelike.inventory.net;

import gregtech.api.pipenet.block.simple.EmptyNodeData;
import gregtech.api.pipenet.tickable.TickableWorldPipeNet;
import net.minecraft.world.World;

public class WorldInventoryPipeNet extends TickableWorldPipeNet<EmptyNodeData, InventoryPipeNet> {

    private static final String DATA_ID_BASE = "gregtech.inventory_pipe_net";

    // Review: HACK. Used to make the world available during initial loading of the pipe nets
    //         Without it you get an NPE when isChunkLoaded is invoked in the parent class during pipenet deserialization
    //         i.e. before setWorldAndInit is done.
    //         A proper (more complicated) fix would be to defer some of the PipeNet.addNodeSilently processing in deserializeAllNodes 
    //         to setWorldAndInit
    static final ThreadLocal<World> loadingWorld = new ThreadLocal<World>();
    
    public static WorldInventoryPipeNet getWorldPipeNet(World world) {
        final String DATA_ID = getDataID(DATA_ID_BASE, world);
        WorldInventoryPipeNet netWorldData;
        loadingWorld.set(world);
        try {
            netWorldData = (WorldInventoryPipeNet) world.loadData(WorldInventoryPipeNet.class, DATA_ID);
            if (netWorldData == null) {
                netWorldData = new WorldInventoryPipeNet(DATA_ID);
                world.setData(DATA_ID, netWorldData);
            }
        }
        finally
        {
            loadingWorld.set(null);
        }
        netWorldData.setWorldAndInit(world);
        return netWorldData;
    }

    public WorldInventoryPipeNet(String name) {
        super(name);
    }

    @Override
    public World getWorld()
    {
        final World result = loadingWorld.get();
        return result != null ? result : super.getWorld();
    }

    @Override
    protected int getUpdateRate() {
        return 20;
    }

    @Override
    protected InventoryPipeNet createNetInstance() {
        return new InventoryPipeNet(this);
    }
}
