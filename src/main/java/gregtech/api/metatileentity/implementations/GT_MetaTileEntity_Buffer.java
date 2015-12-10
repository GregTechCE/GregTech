package gregtech.api.metatileentity.implementations;

import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import static gregtech.api.enums.GT_Values.V;

public abstract class GT_MetaTileEntity_Buffer extends GT_MetaTileEntity_TieredMachineBlock {
    public boolean bOutput = false, bRedstoneIfFull = false, bInvert = false;
    public int mSuccess = 0, mTargetStackSize = 0;

    public GT_MetaTileEntity_Buffer(int aID, String aName, String aNameRegional, int aTier, int aInvSlotCount, String aDescription) {
        super(aID, aName, aNameRegional, aTier, aInvSlotCount, aDescription);
    }

    public GT_MetaTileEntity_Buffer(String aName, int aTier, int aInvSlotCount, String aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, aInvSlotCount, aDescription, aTextures);
    }

    @Override
    public ITexture[][][] getTextureSet(ITexture[] aTextures) {
        ITexture[][][] rTextures = new ITexture[6][17][];
        ITexture tIcon = getOverlayIcon(), tOut = new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_PIPE_OUT), tUp = new GT_RenderedTexture(Textures.BlockIcons.ARROW_UP), tDown = new GT_RenderedTexture(Textures.BlockIcons.ARROW_DOWN), tLeft = new GT_RenderedTexture(Textures.BlockIcons.ARROW_LEFT), tRight = new GT_RenderedTexture(Textures.BlockIcons.ARROW_RIGHT);
        for (byte i = -1; i < 16; i++) {
            rTextures[0][i + 1] = new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][i + 1], tOut};
            rTextures[1][i + 1] = new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][i + 1], tRight, tIcon};
            rTextures[2][i + 1] = new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][i + 1], tDown, tIcon};
            rTextures[3][i + 1] = new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][i + 1], tLeft, tIcon};
            rTextures[4][i + 1] = new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][i + 1], tUp, tIcon};
            rTextures[5][i + 1] = new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][i + 1], tIcon};
        }
        return rTextures;
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
        if (aSide == aFacing) return mTextures[5][aColorIndex + 1];
        if (GT_Utility.getOppositeSide(aSide) == aFacing) return mTextures[0][aColorIndex + 1];
        switch (aFacing) {
            case 0:
                return mTextures[4][aColorIndex + 1];
            case 1:
                return mTextures[2][aColorIndex + 1];
            case 2:
                switch (aSide) {
                    case 0:
                        return mTextures[2][aColorIndex + 1];
                    case 1:
                        return mTextures[2][aColorIndex + 1];
                    case 4:
                        return mTextures[1][aColorIndex + 1];
                    case 5:
                        return mTextures[3][aColorIndex + 1];
                }
            case 3:
                switch (aSide) {
                    case 0:
                        return mTextures[4][aColorIndex + 1];
                    case 1:
                        return mTextures[4][aColorIndex + 1];
                    case 4:
                        return mTextures[3][aColorIndex + 1];
                    case 5:
                        return mTextures[1][aColorIndex + 1];
                }
            case 4:
                switch (aSide) {
                    case 0:
                        return mTextures[3][aColorIndex + 1];
                    case 1:
                        return mTextures[1][aColorIndex + 1];
                    case 2:
                        return mTextures[3][aColorIndex + 1];
                    case 3:
                        return mTextures[1][aColorIndex + 1];
                }
            case 5:
                switch (aSide) {
                    case 0:
                        return mTextures[1][aColorIndex + 1];
                    case 1:
                        return mTextures[3][aColorIndex + 1];
                    case 2:
                        return mTextures[1][aColorIndex + 1];
                    case 3:
                        return mTextures[3][aColorIndex + 1];
                }
        }
        return mTextures[5][aColorIndex + 1];
    }

    @Override
    public boolean isSimpleMachine() {
        return false;
    }

    @Override
    public boolean isValidSlot(int aIndex) {
        return aIndex < mInventory.length - 1;
    }

    @Override
    public boolean isFacingValid(byte aFacing) {
        return true;
    }

    @Override
    public boolean isEnetInput() {
        return true;
    }

    @Override
    public boolean isEnetOutput() {
        return true;
    }

    @Override
    public boolean isInputFacing(byte aSide) {
        return !isOutputFacing(aSide);
    }

    @Override
    public boolean isOutputFacing(byte aSide) {
        return getBaseMetaTileEntity().getBackFacing() == aSide;
    }

    @Override
    public boolean isTeleporterCompatible() {
        return false;
    }

    @Override
    public long getMinimumStoredEU() {
        return 512;
    }

    @Override
    public long maxEUStore() {
        return 512 + V[mTier] * 50;
    }

    @Override
    public long maxEUInput() {
        return V[mTier];
    }

    @Override
    public long maxEUOutput() {
        return bOutput ? V[mTier] : 0;
    }

    @Override
    public long maxAmperesIn() {
        return 2;
    }

    @Override
    public long maxAmperesOut() {
        return 2;
    }

    @Override
    public boolean isAccessAllowed(EntityPlayer aPlayer) {
        return true;
    }

    public abstract ITexture getOverlayIcon();

    @Override
    public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer) {
        if (aBaseMetaTileEntity.isClientSide()) return true;
        aBaseMetaTileEntity.openGUI(aPlayer);
        return true;
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        aNBT.setBoolean("bInvert", bInvert);
        aNBT.setBoolean("bOutput", bOutput);
        aNBT.setBoolean("bRedstoneIfFull", bRedstoneIfFull);
        aNBT.setInteger("mTargetStackSize", mTargetStackSize);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        bInvert = aNBT.getBoolean("bInvert");
        bOutput = aNBT.getBoolean("bOutput");
        bRedstoneIfFull = aNBT.getBoolean("bRedstoneIfFull");
        mTargetStackSize = aNBT.getInteger("mTargetStackSize");
    }

    @Override
    public void setItemNBT(NBTTagCompound aNBT) {
        super.setItemNBT(aNBT);
        if (mTargetStackSize > 0) aNBT.setInteger("mTargetStackSize", mTargetStackSize);
    }

    @Override
    public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        if (aSide == getBaseMetaTileEntity().getBackFacing()) {
        	
            mTargetStackSize = (byte) ((mTargetStackSize + (aPlayer.isSneaking()? -1 : 1)) % 65);
            if(mTargetStackSize <0){mTargetStackSize = 64;}
            if (mTargetStackSize == 0) {
                GT_Utility.sendChatToPlayer(aPlayer, "Do not regulate Item Stack Size");
            } else {
                GT_Utility.sendChatToPlayer(aPlayer, "Regulate Item Stack Size to: " + mTargetStackSize);
            }
        }
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTimer) {
        if (aBaseMetaTileEntity.isAllowedToWork() && aBaseMetaTileEntity.isServerSide() && aBaseMetaTileEntity.isUniversalEnergyStored(getMinimumStoredEU()) && (aBaseMetaTileEntity.hasWorkJustBeenEnabled() || aBaseMetaTileEntity.hasInventoryBeenModified() || aTimer % 200 == 0 || mSuccess > 0)) {
            mSuccess--;
            moveItems(aBaseMetaTileEntity, aTimer);
            aBaseMetaTileEntity.setGenericRedstoneOutput(bInvert);
            if (bRedstoneIfFull) {
                aBaseMetaTileEntity.setGenericRedstoneOutput(!bInvert);
                for (int i = 0; i < mInventory.length; i++)
                    if (isValidSlot(i)) {
                        if (mInventory[i] == null) {
                            aBaseMetaTileEntity.setGenericRedstoneOutput(bInvert);
                            aBaseMetaTileEntity.decreaseStoredEnergyUnits(1, true);
                            break;
                        }
                    }
            }
        }
    }

    protected void moveItems(IGregTechTileEntity aBaseMetaTileEntity, long aTimer) {
        int tCost = GT_Utility.moveOneItemStack(aBaseMetaTileEntity, aBaseMetaTileEntity.getTileEntityAtSide(aBaseMetaTileEntity.getBackFacing()), aBaseMetaTileEntity.getBackFacing(), aBaseMetaTileEntity.getFrontFacing(), null, false, mTargetStackSize == 0 ? 64 : (byte) mTargetStackSize, mTargetStackSize == 0 ? 1 : (byte) mTargetStackSize, (byte) 64, (byte) 1);
        if (tCost > 0 || aBaseMetaTileEntity.hasInventoryBeenModified()) {
            mSuccess = 50;
            aBaseMetaTileEntity.decreaseStoredEnergyUnits(Math.abs(tCost), true);
        }
    }

    @Override
    public boolean allowPullStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
        return true;
    }

    @Override
    public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
        return aSide != aBaseMetaTileEntity.getBackFacing();
    }
}