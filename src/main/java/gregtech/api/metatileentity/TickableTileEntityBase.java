package gregtech.api.metatileentity;

import gregtech.api.capability.internal.IHasWorldObjectAndCoords;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class TickableTileEntityBase extends TileEntity implements IHasWorldObjectAndCoords, ITickable {

    private long timer = 0L;

    @Override
    public long getTimer() {
        return timer;
    }

    @Override
    public void update() {
        timer++;
    }

}
