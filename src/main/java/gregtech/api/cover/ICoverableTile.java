package gregtech.api.cover;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface ICoverableTile extends ICapabilityProvider {

    World getTileWorld();
    BlockPos getTilePos();

    boolean hasCapabilityInternal(@Nonnull Capability<?> capability, @Nullable EnumFacing facing);
    <T> T getCapabilityInternal(@Nonnull Capability<T> capability, @Nullable EnumFacing facing);

    @Override
    default boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return filterHasCapabilityResult(capability, facing, hasCapabilityInternal(capability, facing), false);
    }

    @Nullable
    @Override
    default <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return filterGetCapabilityResult(capability, facing, getCapabilityInternal(capability, facing), false);
    }

    /**
     * Used by internal actions. This will consider the covers on the tile entity.
     */
    default boolean hasCapabilityAtSide(@Nonnull Capability<?> capability, @Nonnull EnumFacing facing) {
        ICapabilityProvider capabilityProvider = getCapabilityProviderAtSide(facing);
        return capabilityProvider != null && filterHasCapabilityResult(capability, facing, capabilityProvider.hasCapability(capability, facing.getOpposite()), true);
    }

    /**
     * Used by internal actions. This will consider the covers on the tile entity.
     */
    @Nullable
    default <T> T getCapabilityAtSide(@Nonnull Capability<T> capability, @Nonnull EnumFacing facing) {
        ICapabilityProvider capabilityProvider = getCapabilityProviderAtSide(facing);
        return capabilityProvider != null ? filterGetCapabilityResult(capability, facing, capabilityProvider.getCapability(capability, facing.getOpposite()), true) : null;
    }

    default boolean filterHasCapabilityResult(@Nonnull Capability<?> capability, @Nullable EnumFacing facing, boolean result, boolean internal) {
        return result;//TODO
    }

    default <T> T filterGetCapabilityResult(@Nonnull Capability<T> capability, @Nullable EnumFacing facing, T result, boolean internal) {
        return result;//TODO
    }

    /**
     * Used by {@link #hasCapability(Capability, EnumFacing)} and {@link #getCapabilityAtSide(Capability, EnumFacing)}
     */
    @Nullable
    default ICapabilityProvider getCapabilityProviderAtSide(@Nonnull EnumFacing facing) {
        return getTileWorld().getTileEntity(getTilePos().offset(facing));
    }
}
