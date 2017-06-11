package gregtech.api.interfaces.tileentity;

import net.minecraft.util.EnumFacing;

public interface ICoverable extends IRedstoneTileEntity, IHasInventory, IBasicEnergyContainer {

    /**
     * If a Cover of that Type can be placed on this Side.
     */
    boolean allowCoverOnSide(EnumFacing side, int coverId);

    boolean dropCover(EnumFacing side, EnumFacing droppedSide, boolean forced);

    void setCoverDataAtSide(EnumFacing side, int coverData);

    void setCoverIDAtSide(EnumFacing side, int coverId);

    int getCoverDataAtSide(EnumFacing side);

    int getCoverIDAtSide(EnumFacing side);

    /**
     * For use by the regular MetaTileEntities. Returns the Cover Manipulated input Redstone.
     * Don't use this if you are a Cover Behavior. Only for MetaTileEntities.
     */
    byte getInternalInputRedstoneSignal(EnumFacing side);

    /**
     * For use by the regular MetaTileEntities. This makes it not conflict with Cover based Redstone Signals.
     * Don't use this if you are a Cover Behavior. Only for MetaTileEntities.
     */
    void setInternalOutputRedstoneSignal(EnumFacing side, byte strength);

    /**
     * Causes a general Cover Texture update.
     * Sends 6 Integers to Client + causes @issueTextureUpdate()
     */
    void issueCoverUpdate();


}