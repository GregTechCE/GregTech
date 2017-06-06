package gregtech.api.interfaces.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public interface ICoverable extends IRedstoneTileEntity, IHasInventory, IBasicEnergyContainer {

    boolean canPlaceCoverIDAtSide(EnumFacing aSide, int aID);

    boolean canPlaceCoverItemAtSide(EnumFacing aSide, ItemStack aCover);

    boolean dropCover(EnumFacing aSide, EnumFacing aDroppedSide, boolean aForced);

    void setCoverDataAtSide(EnumFacing aSide, int aData);

    void setCoverIDAtSide(EnumFacing aSide, int aID);

    void setCoverItemAtSide(EnumFacing aSide, ItemStack aCover);

    int getCoverDataAtSide(EnumFacing aSide);

    int getCoverIDAtSide(EnumFacing aSide);

    /**
     * For use by the regular MetaTileEntities. Returns the Cover Manipulated input Redstone.
     * Don't use this if you are a Cover Behavior. Only for MetaTileEntities.
     */
    byte getInternalInputRedstoneSignal(EnumFacing aSide);

    /**
     * For use by the regular MetaTileEntities. This makes it not conflict with Cover based Redstone Signals.
     * Don't use this if you are a Cover Behavior. Only for MetaTileEntities.
     */
    void setInternalOutputRedstoneSignal(EnumFacing aSide, byte aStrength);

    /**
     * Causes a general Cover Texture update.
     * Sends 6 Integers to Client + causes @issueTextureUpdate()
     */
    void issueCoverUpdate(EnumFacing aSide);

}