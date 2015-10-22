package gregtech.common.tileentities.automation;

import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Buffer;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Utility;
import gregtech.common.gui.GT_Container_Regulator;
import gregtech.common.gui.GT_GUIContainer_Regulator;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Arrays;

public class GT_MetaTileEntity_Regulator
        extends GT_MetaTileEntity_Buffer {
    public int[] mTargetSlots = {0, 0, 0, 0, 0, 0, 0, 0, 0};

    public GT_MetaTileEntity_Regulator(int aID, String aName, String aNameRegional, int aTier) {
        super(aID, aName, aNameRegional, aTier, 19, "Regulating incoming Items");
    }

    public GT_MetaTileEntity_Regulator(String aName, int aTier, int aInvSlotCount, String aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, aInvSlotCount, aDescription, aTextures);
    }

    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_Regulator(this.mName, this.mTier, this.mInventory.length, this.mDescription, this.mTextures);
    }

    public ITexture getOverlayIcon() {
        return new GT_RenderedTexture(Textures.BlockIcons.AUTOMATION_REGULATOR);
    }

    public boolean isValidSlot(int aIndex) {
        return aIndex < 9;
    }

    public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_Container_Regulator(aPlayerInventory, aBaseMetaTileEntity);
    }

    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_Regulator(aPlayerInventory, aBaseMetaTileEntity);
    }

    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setInteger("mTargetSlot1", this.mTargetSlots[0]);
        aNBT.setInteger("mTargetSlot2", this.mTargetSlots[1]);
        aNBT.setInteger("mTargetSlot3", this.mTargetSlots[2]);
        aNBT.setInteger("mTargetSlot4", this.mTargetSlots[3]);
        aNBT.setInteger("mTargetSlot5", this.mTargetSlots[4]);
        aNBT.setInteger("mTargetSlot6", this.mTargetSlots[5]);
        aNBT.setInteger("mTargetSlot7", this.mTargetSlots[6]);
        aNBT.setInteger("mTargetSlot8", this.mTargetSlots[7]);
        aNBT.setInteger("mTargetSlot9", this.mTargetSlots[8]);
    }

    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        this.mTargetSlots[0] = aNBT.getInteger("mTargetSlot1");
        this.mTargetSlots[1] = aNBT.getInteger("mTargetSlot2");
        this.mTargetSlots[2] = aNBT.getInteger("mTargetSlot3");
        this.mTargetSlots[3] = aNBT.getInteger("mTargetSlot4");
        this.mTargetSlots[4] = aNBT.getInteger("mTargetSlot5");
        this.mTargetSlots[5] = aNBT.getInteger("mTargetSlot6");
        this.mTargetSlots[6] = aNBT.getInteger("mTargetSlot7");
        this.mTargetSlots[7] = aNBT.getInteger("mTargetSlot8");
        this.mTargetSlots[8] = aNBT.getInteger("mTargetSlot9");
    }

    public void moveItems(IGregTechTileEntity aBaseMetaTileEntity, long aTimer) {
        int i = 0;
        for (int tCosts = 0; i < 9; i++) {
            if (this.mInventory[(i + 9)] != null) {
                tCosts = GT_Utility.moveOneItemStackIntoSlot(getBaseMetaTileEntity(), getBaseMetaTileEntity().getTileEntityAtSide(getBaseMetaTileEntity().getBackFacing()), getBaseMetaTileEntity().getBackFacing(), this.mTargetSlots[i], Arrays.asList(new ItemStack[]{this.mInventory[(i + 9)]}), false, (byte) this.mInventory[(i + 9)].stackSize, (byte) this.mInventory[(i + 9)].stackSize, (byte) 64, (byte) 1) * 3;
                if (tCosts > 0) {
                    this.mSuccess = 50;
                    getBaseMetaTileEntity().decreaseStoredEnergyUnits(tCosts, true);
                    break;
                }
            }
        }
    }

    public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
        return (super.allowPutStack(aBaseMetaTileEntity, aIndex, aSide, aStack)) && (GT_Utility.areStacksEqual(aStack, this.mInventory[(aIndex + 9)]));
    }
}
