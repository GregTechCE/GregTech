package gregtech.api.capability;


import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public interface ITurnable {

    @CapabilityInject(ITurnable.class)
    public static final Capability<ITurnable> CAPABILITY_TURNABLE = null;

    /**
     * Get the block's facing.
     *
     * @return front Block facing
     */
    EnumFacing getFrontFacing();

    /**
     * Set the block's facing
     *
     * @param side facing to set the block to
     */
    void setFrontFacing(EnumFacing side);

    /**
     * Determine if the wrench can be used to set the block's facing.
     */
    boolean isValidFacing(EnumFacing side);

}