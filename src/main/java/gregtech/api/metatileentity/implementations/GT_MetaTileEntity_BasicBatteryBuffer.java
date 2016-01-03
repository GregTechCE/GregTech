package gregtech.api.metatileentity.implementations;

import gregtech.api.enums.Textures;
import gregtech.api.gui.*;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.items.GT_MetaBase_Item;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import ic2.api.item.IElectricItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import static gregtech.api.enums.GT_Values.V;

/**
 * NEVER INCLUDE THIS FILE IN YOUR MOD!!!
 * <p/>
 * This is the main construct for my Basic Machines such as the Automatic Extractor
 * Extend this class to make a simple Machine
 */
public class GT_MetaTileEntity_BasicBatteryBuffer extends GT_MetaTileEntity_TieredMachineBlock {
    public boolean mCharge = false, mDecharge = false;
    public int mBatteryCount = 0, mChargeableCount = 0;
    private long count = 0;
    private long mStored = 0;
    private long mMax = 0;

    public GT_MetaTileEntity_BasicBatteryBuffer(int aID, String aName, String aNameRegional, int aTier, String aDescription, int aSlotCount) {
        super(aID, aName, aNameRegional, aTier, aSlotCount, aDescription);
    }

    public GT_MetaTileEntity_BasicBatteryBuffer(String aName, int aTier, String aDescription, ITexture[][][] aTextures, int aSlotCount) {
        super(aName, aTier, aSlotCount, aDescription, aTextures);
    }

    @Override
    public String[] getDescription() {
        return new String[]{mDescription, mInventory.length + " Slots"};
    }

    @Override
    public ITexture[][][] getTextureSet(ITexture[] aTextures) {
        ITexture[][][] rTextures = new ITexture[2][17][];
        for (byte i = -1; i < 16; i++) {
            rTextures[0][i + 1] = new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][i + 1]};
            rTextures[1][i + 1] = new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][i + 1], mInventory.length > 4 ? Textures.BlockIcons.OVERLAYS_ENERGY_OUT_MULTI[mTier] : Textures.BlockIcons.OVERLAYS_ENERGY_OUT[mTier]};
        }
        return rTextures;
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
        return mTextures[aSide == aFacing ? 1 : 0][aColorIndex + 1];
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_BasicBatteryBuffer(mName, mTier, mDescription, mTextures, mInventory.length);
    }

    @Override
    public boolean isSimpleMachine() {
        return false;
    }

    @Override
    public boolean isElectric() {
        return true;
    }

    @Override
    public boolean isValidSlot(int aIndex) {
        return true;
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
        return aSide != getBaseMetaTileEntity().getFrontFacing();
    }

    @Override
    public boolean isOutputFacing(byte aSide) {
        return aSide == getBaseMetaTileEntity().getFrontFacing();
    }

    @Override
    public boolean isTeleporterCompatible() {
        return false;
    }

    @Override
    public long getMinimumStoredEU() {
        return V[mTier] * 16 * mInventory.length;
    }

    @Override
    public long maxEUStore() {
        return V[mTier] * 64 * mInventory.length;
    }

    @Override
    public long maxEUInput() {
        return V[mTier];
    }

    @Override
    public long maxEUOutput() {
        return V[mTier];
    }

    @Override
    public long maxAmperesIn() {
        return mChargeableCount * 2;
    }

    @Override
    public long maxAmperesOut() {
        return mBatteryCount;
    }

    @Override
    public int rechargerSlotStartIndex() {
        return 0;
    }

    @Override
    public int dechargerSlotStartIndex() {
        return 0;
    }

    @Override
    public int rechargerSlotCount() {
        return mCharge ? mInventory.length : 0;
    }

    @Override
    public int dechargerSlotCount() {
        return mDecharge ? mInventory.length : 0;
    }

    @Override
    public int getProgresstime() {
        return (int) getBaseMetaTileEntity().getUniversalEnergyStored();
    }

    @Override
    public int maxProgresstime() {
        return (int) getBaseMetaTileEntity().getUniversalEnergyCapacity();
    }

    @Override
    public boolean isAccessAllowed(EntityPlayer aPlayer) {
        return true;
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        //
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        //
    }

    @Override
    public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer) {
        if (aBaseMetaTileEntity.isClientSide()) return true;
        aBaseMetaTileEntity.openGUI(aPlayer);
        return true;
    }

    @Override
    public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        switch (mInventory.length) {
            case 1:
                return new GT_Container_1by1(aPlayerInventory, aBaseMetaTileEntity);
            case 4:
                return new GT_Container_2by2(aPlayerInventory, aBaseMetaTileEntity);
            case 9:
                return new GT_Container_3by3(aPlayerInventory, aBaseMetaTileEntity);
            case 16:
                return new GT_Container_4by4(aPlayerInventory, aBaseMetaTileEntity);
        }
        return new GT_Container_1by1(aPlayerInventory, aBaseMetaTileEntity);
    }

    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        switch (mInventory.length) {
            case 1:
                return new GT_GUIContainer_1by1(aPlayerInventory, aBaseMetaTileEntity, getLocalName());
            case 4:
                return new GT_GUIContainer_2by2(aPlayerInventory, aBaseMetaTileEntity, getLocalName());
            case 9:
                return new GT_GUIContainer_3by3(aPlayerInventory, aBaseMetaTileEntity, getLocalName());
            case 16:
                return new GT_GUIContainer_4by4(aPlayerInventory, aBaseMetaTileEntity, getLocalName());
        }
        return new GT_GUIContainer_1by1(aPlayerInventory, aBaseMetaTileEntity, getLocalName());
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        if (aBaseMetaTileEntity.isServerSide()) {
            mCharge = aBaseMetaTileEntity.getStoredEU() / 2 > aBaseMetaTileEntity.getEUCapacity() / 3;
            mDecharge = aBaseMetaTileEntity.getStoredEU() < aBaseMetaTileEntity.getEUCapacity() / 3;
            mBatteryCount = 0;
            mChargeableCount = 0;
            for (ItemStack tStack : mInventory)
                if (GT_ModHandler.isElectricItem(tStack, mTier)) {
                    if (GT_ModHandler.isChargerItem(tStack)) mBatteryCount++;
                    mChargeableCount++;
                }
        }
    }

    @Override
    public boolean allowPullStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
        if (GT_ModHandler.isElectricItem(aStack) && aStack.getUnlocalizedName().startsWith("gt.metaitem.01.")) {
            String name = aStack.getUnlocalizedName();
            if (name.equals("gt.metaitem.01.32510") ||
                    name.equals("gt.metaitem.01.32511") ||
                    name.equals("gt.metaitem.01.32520") ||
                    name.equals("gt.metaitem.01.32521") ||
                    name.equals("gt.metaitem.01.32530") ||
                    name.equals("gt.metaitem.01.32531")) {
            	if(ic2.api.item.ElectricItem.manager.getCharge(aStack)==0){
                return true;}
            }
        }
        return false;
    }

    @Override
    public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
        if (!GT_Utility.isStackValid(aStack)) {
            return false;
        }
        if (mInventory[aIndex]==null && GT_ModHandler.isElectricItem(aStack, this.mTier)) {
            return true;
        }
        return false;
    }

    public long[] getStoredEnergy() {
    	boolean scaleOverflow =false;
    	boolean storedOverflow = false;
        long tScale = getBaseMetaTileEntity().getEUCapacity();
        long tStored = getBaseMetaTileEntity().getStoredEU();
        long tStep = 0;
        if (mInventory != null) {
            for (ItemStack aStack : mInventory) {
                if (GT_ModHandler.isElectricItem(aStack)) {

                    if (aStack.getItem() instanceof GT_MetaBase_Item) {
                        Long[] stats = ((GT_MetaBase_Item) aStack.getItem()).getElectricStats(aStack);
                        if (stats != null) {
                        	if(stats[0]>Long.MAX_VALUE/2){scaleOverflow=true;}
                            tScale = tScale + stats[0];
                            tStep = ((GT_MetaBase_Item) aStack.getItem()).getRealCharge(aStack);
                            if(tStep > Long.MAX_VALUE/2){storedOverflow=true;}
                            tStored = tStored + tStep;
                        }
                    } else if (aStack.getItem() instanceof IElectricItem) {
                        tStored = tStored + (long) ic2.api.item.ElectricItem.manager.getCharge(aStack);
                        tScale = tScale + (long) ((IElectricItem) aStack.getItem()).getMaxCharge(aStack);
                    }
                }
            }

        }
        if(scaleOverflow){tScale=Long.MAX_VALUE;}
        if(storedOverflow){tStored=Long.MAX_VALUE;}
        return new long[]{tStored, tScale};
    }

    @Override
    public String[] getInfoData() {
        count++;
        if (mMax == 0 || count % 20 == 0) {
            long[] tmp = getStoredEnergy();
            mStored = tmp[0];
            mMax = tmp[1];
        }

        return new String[]{
                getLocalName(),
                "Stored Items:",
                GT_Utility.formatNumbers(mStored) + " EU /",
                GT_Utility.formatNumbers(mMax) + " EU"};
    }

    @Override
    public boolean isGivingInformation() {
        return true;
    }
}