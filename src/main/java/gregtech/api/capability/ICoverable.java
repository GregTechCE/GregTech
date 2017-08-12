package gregtech.api.capability;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public interface ICoverable {

    @CapabilityInject(ICoverable.class)
    public static final Capability<ICoverable> CAPABILITY_COVERABLE = null;

    boolean allowCoverOnSide(int coverId);

    boolean dropCover(EnumFacing droppedSide, boolean forced);

    void setCoverIDAtSide(int coverId);

    int getCoverID();

    void setCoverRedstoneOutput(byte strength);

}