package gregtech.api.metatileentity;

import gregtech.api.util.GTUtility;
import net.minecraft.util.ITickable;

public abstract class TickableTileEntityBase extends SyncedTileEntityBase implements ITickable {

    private long timer = 0L;

    // Create an offset [0,20) to distribute ticks more evenly
    private final int offset = GTUtility.getRandomIntXSTR(20);

    /**
     * @deprecated This method distributes ticks unevenly.
     * Use {@link TickableTileEntityBase#getOffsetTimer()} instead.
     */
    @Deprecated
    public long getTimer() {
        return timer;
    }

    /**
     * Replacement for old {@link TickableTileEntityBase#getTimer()}.
     * @return Timer value with a random offset of [0,20].
     */
    public long getOffsetTimer() {
        return timer + offset;
    }

    @Override
    public void update() {
        if (timer == 0) {
            onFirstTick();
        }
        timer++;
    }

    protected void onFirstTick() {
    }

}
