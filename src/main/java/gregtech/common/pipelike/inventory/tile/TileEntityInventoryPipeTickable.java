package gregtech.common.pipelike.inventory.tile;

import net.minecraft.util.ITickable;

public class TileEntityInventoryPipeTickable extends TileEntityInventoryPipe implements ITickable {

    @Override
    public void update() {
        getCoverableImplementation().update();
    }

    @Override
    public boolean supportsTicking() {
        return true;
    }
}
