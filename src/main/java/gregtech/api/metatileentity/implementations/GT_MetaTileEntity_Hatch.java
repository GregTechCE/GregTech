package gregtech.api.metatileentity.implementations;

import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.nbt.NBTTagCompound;

public abstract class GT_MetaTileEntity_Hatch extends GT_MetaTileEntity_BasicTank {

    public byte mMachineBlock = 0;

    public GT_MetaTileEntity_Hatch(int aID, String aName, String aNameRegional, int aTier, int aInvSlotCount, String aDescription, ITexture... aTextures) {
        super(aID, aName, aNameRegional, aTier, aInvSlotCount, aDescription, aTextures);
    }

    public GT_MetaTileEntity_Hatch(String aName, int aTier, int aInvSlotCount, String aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, aInvSlotCount, aDescription, aTextures);
    }

    public static int getSlots(int aTier) {
        return aTier < 1 ? 1 : aTier == 1 ? 4 : aTier == 2 ? 9 : 16;
    }

    @Override
    public ITexture[][][] getTextureSet(ITexture[] aTextures) {
        return new ITexture[0][0][0];
    }

    public abstract ITexture[] getTexturesActive(ITexture aBaseTexture);

    public abstract ITexture[] getTexturesInactive(ITexture aBaseTexture);

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
        return aSide != aFacing ? mMachineBlock > 0 ? new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[mMachineBlock]} : new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][aColorIndex + 1]} : mMachineBlock > 0 ? aActive ? getTexturesActive(Textures.BlockIcons.CASING_BLOCKS[mMachineBlock]) : getTexturesInactive(Textures.BlockIcons.CASING_BLOCKS[mMachineBlock]) : aActive ? getTexturesActive(Textures.BlockIcons.MACHINE_CASINGS[mTier][aColorIndex + 1]) : getTexturesInactive(Textures.BlockIcons.MACHINE_CASINGS[mTier][aColorIndex + 1]);
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setByte("mMachineBlock", mMachineBlock);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        mMachineBlock = aNBT.getByte("mMachineBlock");
    }

    @Override
    public final void onValueUpdate(byte aValue) {
        mMachineBlock = (byte) (aValue & 127);
    }

    @Override
    public final byte getUpdateData() {
        return (byte) (mMachineBlock & 127);
    }

    @Override
    public boolean doesFillContainers() {
        return false;
    }

    @Override
    public boolean doesEmptyContainers() {
        return false;
    }

    @Override
    public boolean canTankBeFilled() {
        return false;
    }

    @Override
    public boolean canTankBeEmptied() {
        return false;
    }

    @Override
    public boolean displaysItemStack() {
        return false;
    }

    @Override
    public boolean displaysStackSize() {
        return false;
    }
}
