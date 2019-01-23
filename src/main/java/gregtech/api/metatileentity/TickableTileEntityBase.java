package gregtech.api.metatileentity;

import net.minecraft.util.ITickable;

public abstract class TickableTileEntityBase extends SyncedTileEntityBase implements ITickable {

    private long timer = 0L;

    public long getTimer() {
        return timer;
    }

    @Override
    public void update() {
        if(timer == 0) {
            onFirstTick();
        }
        timer++;
    }

    protected void onFirstTick() {
    }

}
