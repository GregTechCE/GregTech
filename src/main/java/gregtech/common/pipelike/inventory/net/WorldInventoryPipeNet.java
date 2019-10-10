package gregtech.common.pipelike.inventory.net;

import gregtech.api.pipenet.block.simple.EmptyNodeData;
import gregtech.api.pipenet.tickable.TickableWorldPipeNet;
import net.minecraft.world.World;

public class WorldInventoryPipeNet extends TickableWorldPipeNet<EmptyNodeData, InventoryPipeNet> {

    private static final String DATA_ID = "gregtech.inventory_pipe_net";

    public static WorldInventoryPipeNet getWorldPipeNet(World world) {
        WorldInventoryPipeNet netWorldData = (WorldInventoryPipeNet) world.loadData(WorldInventoryPipeNet.class, DATA_ID);
        if (netWorldData == null) {
            netWorldData = new WorldInventoryPipeNet(DATA_ID);
            world.setData(DATA_ID, netWorldData);
        }
        netWorldData.setWorldAndInit(world);
        return netWorldData;
    }

    public WorldInventoryPipeNet(String name) {
        super(name);
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
