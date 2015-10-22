package gregtech.common.tileentities.storage;

import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_TieredMachineBlock;
import gregtech.api.objects.GT_ItemStack;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class GT_MetaTileEntity_Locker
        extends GT_MetaTileEntity_TieredMachineBlock {
    public byte mType = 0;

    public GT_MetaTileEntity_Locker(int aID, String aName, String aNameRegional, int aTier) {
        super(aID, aName, aNameRegional, aTier, 4, "Stores and recharges Armor", new ITexture[0]);
    }

    public GT_MetaTileEntity_Locker(String aName, int aTier, String aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, 4, aDescription, aTextures);
    }

    public String[] getDescription() {
        return new String[]{this.mDescription, "Click with Screwdriver to change Style"};
    }

    public ITexture[][][] getTextureSet(ITexture[] aTextures) {
        ITexture[][][] rTextures = new ITexture[3][17][];
        for (byte i = -1; i < 16; i = (byte) (i + 1)) {
            ITexture[] tmp0 = {Textures.BlockIcons.MACHINE_CASINGS[this.mTier][(i + 1)]};
            rTextures[0][(i + 1)] = tmp0;
            ITexture[] tmp1 = {Textures.BlockIcons.MACHINE_CASINGS[this.mTier][(i + 1)], Textures.BlockIcons.OVERLAYS_ENERGY_IN[this.mTier]};
            rTextures[1][(i + 1)] = tmp1;
            ITexture[] tmp2 = {Textures.BlockIcons.MACHINE_CASINGS[this.mTier][(i + 1)], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_LOCKER)};
            rTextures[2][(i + 1)] = tmp2;
        }
        return rTextures;
    }

    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
        if (aSide == aFacing) {
            return new ITexture[]{this.mTextures[2][(aColorIndex + 1)][0], this.mTextures[2][(aColorIndex + 1)][1], Textures.BlockIcons.LOCKERS[java.lang.Math.abs(this.mType % Textures.BlockIcons.LOCKERS.length)]};
        }
        return this.mTextures[0][(aColorIndex + 1)];
    }

    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_Locker(this.mName, this.mTier, this.mDescription, this.mTextures);
    }

    public boolean isSimpleMachine() {
        return false;
    }

    public boolean isElectric() {
        return true;
    }

    public boolean isValidSlot(int aIndex) {
        return true;
    }

    public boolean isFacingValid(byte aFacing) {
        return aFacing > 1;
    }

    public boolean isEnetInput() {
        return true;
    }

    public boolean isInputFacing(byte aSide) {
        return aSide == getBaseMetaTileEntity().getBackFacing();
    }

    public boolean isTeleporterCompatible() {
        return false;
    }

    public long maxEUStore() {
        return gregtech.api.enums.GT_Values.V[this.mTier] * maxAmperesIn();
    }

    public long maxEUInput() {
        return gregtech.api.enums.GT_Values.V[this.mTier];
    }

    public long maxAmperesIn() {
        return this.mInventory.length * 2;
    }

    public int rechargerSlotStartIndex() {
        return 0;
    }

    public int rechargerSlotCount() {
        return getBaseMetaTileEntity().isAllowedToWork() ? this.mInventory.length : 0;
    }

    public boolean isAccessAllowed(EntityPlayer aPlayer) {
        return true;
    }

    public void saveNBTData(NBTTagCompound aNBT) {
        aNBT.setByte("mType", this.mType);
    }

    public void loadNBTData(NBTTagCompound aNBT) {
        this.mType = aNBT.getByte("mType");
    }

    public void onValueUpdate(byte aValue) {
        this.mType = aValue;
    }

    public byte getUpdateData() {
        return this.mType;
    }

    public void doSound(byte aIndex, double aX, double aY, double aZ) {
        if (aIndex == 16) {
            GT_Utility.doSoundAtClient((String) GregTech_API.sSoundList.get(Integer.valueOf(3)), 1, 1.0F);
        }
    }

    public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        if (aSide == getBaseMetaTileEntity().getFrontFacing()) {
            this.mType = ((byte) (this.mType + 1));
        }
    }

    public boolean allowCoverOnSide(byte aSide, GT_ItemStack aStack) {
        return aSide != getBaseMetaTileEntity().getFrontFacing();
    }

    public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer, byte aSide, float aX, float aY, float aZ) {
        if ((aBaseMetaTileEntity.isServerSide()) && (aSide == aBaseMetaTileEntity.getFrontFacing())) {
            for (int i = 0; i < 4; i++) {
                ItemStack tSwapStack = this.mInventory[i];
                this.mInventory[i] = aPlayer.inventory.armorInventory[i];
                aPlayer.inventory.armorInventory[i] = tSwapStack;
            }
            aPlayer.inventoryContainer.detectAndSendChanges();
            sendSound((byte) 16);
        }
        return true;
    }

    public boolean allowPullStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
        return false;
    }

    public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
        return false;
    }
}
