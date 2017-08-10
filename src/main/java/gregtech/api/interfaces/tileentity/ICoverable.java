package gregtech.api.interfaces.tileentity;

import net.minecraft.util.EnumFacing;

public interface ICoverable extends IRedstoneTileEntity {

    /**
     * If a Cover of that Type can be placed on this Side.
     */
    boolean allowCoverOnSide(EnumFacing side, int coverId);

    boolean dropCover(EnumFacing side, EnumFacing droppedSide, boolean forced);

    void setCoverDataAtSide(EnumFacing side, int coverData);

    void setCoverIDAtSide(EnumFacing side, int coverId);

    int getCoverDataAtSide(EnumFacing side);

    int getCoverIDAtSide(EnumFacing side);

    void setCoverRedstoneOutput(EnumFacing side, byte strength);

}