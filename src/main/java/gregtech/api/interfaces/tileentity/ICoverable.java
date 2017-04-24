package gregtech.api.interfaces.tileentity;

import gregtech.api.util.GT_CoverBehavior;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public interface ICoverable extends IRedstoneTileEntity, IHasInventory, IBasicEnergyContainer {
    public boolean canPlaceCoverIDAtSide(EnumFacing aSide, int aID);

    public boolean canPlaceCoverItemAtSide(EnumFacing aSide, ItemStack aCover);

    public boolean dropCover(EnumFacing aSide, EnumFacing aDroppedSide, boolean aForced);

    public void setCoverDataAtSide(EnumFacing aSide, int aData);

    public void setCoverIDAtSide(EnumFacing aSide, int aID);

    public void setCoverItemAtSide(EnumFacing aSide, ItemStack aCover);

    public int getCoverDataAtSide(EnumFacing aSide);

    public int getCoverIDAtSide(EnumFacing aSide);

    public GT_CoverBehavior getCoverBehaviorAtSide(EnumFacing aSide);

    /**
     * For use by the regular MetaTileEntities. Returns the Cover Manipulated input Redstone.
     * Don't use this if you are a Cover Behavior. Only for MetaTileEntities.
     */
    public byte getInternalInputRedstoneSignal(EnumFacing aSide);

    /**
     * For use by the regular MetaTileEntities. This makes it not conflict with Cover based Redstone Signals.
     * Don't use this if you are a Cover Behavior. Only for MetaTileEntities.
     */
    public void setInternalOutputRedstoneSignal(EnumFacing aSide, byte aStrength);

    /**
     * Causes a general Cover Texture update.
     * Sends 6 Integers to Client + causes @issueTextureUpdate()
     */
    public void issueCoverUpdate(EnumFacing aSide);
}