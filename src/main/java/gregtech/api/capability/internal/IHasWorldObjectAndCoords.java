package gregtech.api.capability.internal;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IHasWorldObjectAndCoords {

    World getWorld();

    BlockPos getPos();

    default boolean isServerSide() {
        return !getWorld().isRemote;
    }

    default boolean isClientSide() {
        return getWorld().isRemote;
    }

    /**
     * @return the time this TileEntity has been loaded.
     */
    long getTimer();

    default int getRandomNumber(int range) {
        return getWorld().rand.nextInt(range);
    }

    void markDirty();
}