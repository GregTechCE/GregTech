package gregtech.api.capability.internal;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IHasWorldObjectAndCoords {

    World getWorldObj();

    BlockPos getWorldPos();

    default boolean isServerSide() {
        return !getWorldObj().isRemote;
    }

    default boolean isClientSide() {
        return getWorldObj().isRemote;
    }

    /**
     * @return the time this TileEntity has been loaded.
     */
    long getTimer();

    default int getRandomNumber(int range) {
        return getWorldObj().rand.nextInt(range);
    }

    void markDirty();

}