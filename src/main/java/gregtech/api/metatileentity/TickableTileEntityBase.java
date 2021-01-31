package gregtech.api.metatileentity;

import net.minecraft.util.ITickable;

import java.util.Random;

public abstract class TickableTileEntityBase extends SyncedTileEntityBase implements ITickable {

    private long timer = 0L;

    // Create an offset [0,20) to distribute ticks more evenly
    private Random random = new Random();
    private int offset = random.nextInt(20);

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
