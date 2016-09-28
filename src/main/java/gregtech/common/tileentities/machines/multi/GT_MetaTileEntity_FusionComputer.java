package gregtech.common.tileentities.machines.multi;

import gregtech.GT_Mod;
import gregtech.api.enums.Dyes;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_Container_MultiMachine;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.*;
import gregtech.api.objects.GT_ItemStack;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import gregtech.common.gui.GT_GUIContainer_FusionReactor;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;

public abstract class GT_MetaTileEntity_FusionComputer extends GT_MetaTileEntity_MultiBlockBase {

    public GT_Recipe mLastRecipe;
    public int mEUStore;

    public GT_MetaTileEntity_FusionComputer(int aID, String aName, String aNameRegional, int tier) {
        super(aID, aName, aNameRegional);
    }

    public GT_MetaTileEntity_FusionComputer(String aName) {
        super(aName);
    }

    public abstract int tier();

    public abstract long maxEUStore();

    @Override
    public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_Container_MultiMachine(aPlayerInventory, aBaseMetaTileEntity);
    }

    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_FusionReactor(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "FusionComputer.png", GT_Recipe.GT_Recipe_Map.sFusionRecipes.mNEIName);
    }

    public abstract MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity);

    public boolean allowCoverOnSide(byte aSide, GT_ItemStack aStack) {

        return aSide != getBaseMetaTileEntity().getFrontFacing();
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        int xCenter = getBaseMetaTileEntity().getXCoord() + ForgeDirection.getOrientation(getBaseMetaTileEntity().getFrontFacing()).offsetX * 5;
        int yCenter = getBaseMetaTileEntity().getYCoord();
        int zCenter = getBaseMetaTileEntity().getZCoord() + ForgeDirection.getOrientation(getBaseMetaTileEntity().getFrontFacing()).offsetZ * 5;
        if (((isAdvancedMachineCasing(xCenter + 5, yCenter, zCenter)) || (xCenter + 5 == getBaseMetaTileEntity().getXCoord()))
                && ((isAdvancedMachineCasing(xCenter - 5, yCenter, zCenter)) || (xCenter - 5 == getBaseMetaTileEntity().getXCoord()))
                && ((isAdvancedMachineCasing(xCenter, yCenter, zCenter + 5)) || (zCenter + 5 == getBaseMetaTileEntity().getZCoord()))
                && ((isAdvancedMachineCasing(xCenter, yCenter, zCenter - 5)) || (zCenter - 5 == getBaseMetaTileEntity().getZCoord())) && (checkCoils(xCenter, yCenter, zCenter))
                && (checkHulls(xCenter, yCenter, zCenter)) && (checkUpperOrLowerHulls(xCenter, yCenter + 1, zCenter)) && (checkUpperOrLowerHulls(xCenter, yCenter - 1, zCenter))
                && (addIfEnergyInjector(xCenter + 4, yCenter, zCenter + 3, aBaseMetaTileEntity)) && (addIfEnergyInjector(xCenter + 4, yCenter, zCenter - 3, aBaseMetaTileEntity))
                && (addIfEnergyInjector(xCenter + 4, yCenter, zCenter + 5, aBaseMetaTileEntity)) && (addIfEnergyInjector(xCenter + 4, yCenter, zCenter - 5, aBaseMetaTileEntity))
                && (addIfEnergyInjector(xCenter - 4, yCenter, zCenter + 3, aBaseMetaTileEntity)) && (addIfEnergyInjector(xCenter - 4, yCenter, zCenter - 3, aBaseMetaTileEntity))
                && (addIfEnergyInjector(xCenter - 4, yCenter, zCenter + 5, aBaseMetaTileEntity)) && (addIfEnergyInjector(xCenter - 4, yCenter, zCenter - 5, aBaseMetaTileEntity))
                && (addIfEnergyInjector(xCenter + 3, yCenter, zCenter + 4, aBaseMetaTileEntity)) && (addIfEnergyInjector(xCenter - 3, yCenter, zCenter + 4, aBaseMetaTileEntity))
                && (addIfEnergyInjector(xCenter + 5, yCenter, zCenter + 4, aBaseMetaTileEntity)) && (addIfEnergyInjector(xCenter - 5, yCenter, zCenter + 4, aBaseMetaTileEntity))
                && (addIfEnergyInjector(xCenter + 3, yCenter, zCenter - 4, aBaseMetaTileEntity)) && (addIfEnergyInjector(xCenter - 3, yCenter, zCenter - 4, aBaseMetaTileEntity))
                && (addIfEnergyInjector(xCenter + 5, yCenter, zCenter - 4, aBaseMetaTileEntity)) && (addIfEnergyInjector(xCenter - 5, yCenter, zCenter - 4, aBaseMetaTileEntity))
                && (addIfExtractor(xCenter + 1, yCenter, zCenter - 5, aBaseMetaTileEntity)) && (addIfExtractor(xCenter + 1, yCenter, zCenter + 5, aBaseMetaTileEntity))
                && (addIfExtractor(xCenter - 1, yCenter, zCenter - 5, aBaseMetaTileEntity)) && (addIfExtractor(xCenter - 1, yCenter, zCenter + 5, aBaseMetaTileEntity))
                && (addIfExtractor(xCenter + 1, yCenter, zCenter - 7, aBaseMetaTileEntity)) && (addIfExtractor(xCenter + 1, yCenter, zCenter + 7, aBaseMetaTileEntity))
                && (addIfExtractor(xCenter - 1, yCenter, zCenter - 7, aBaseMetaTileEntity)) && (addIfExtractor(xCenter - 1, yCenter, zCenter + 7, aBaseMetaTileEntity))
                && (addIfExtractor(xCenter + 5, yCenter, zCenter - 1, aBaseMetaTileEntity)) && (addIfExtractor(xCenter + 5, yCenter, zCenter + 1, aBaseMetaTileEntity))
                && (addIfExtractor(xCenter - 5, yCenter, zCenter - 1, aBaseMetaTileEntity)) && (addIfExtractor(xCenter - 5, yCenter, zCenter + 1, aBaseMetaTileEntity))
                && (addIfExtractor(xCenter + 7, yCenter, zCenter - 1, aBaseMetaTileEntity)) && (addIfExtractor(xCenter + 7, yCenter, zCenter + 1, aBaseMetaTileEntity))
                && (addIfExtractor(xCenter - 7, yCenter, zCenter - 1, aBaseMetaTileEntity)) && (addIfExtractor(xCenter - 7, yCenter, zCenter + 1, aBaseMetaTileEntity))
                && (addIfInjector(xCenter + 1, yCenter + 1, zCenter - 6, aBaseMetaTileEntity)) && (addIfInjector(xCenter + 1, yCenter + 1, zCenter + 6, aBaseMetaTileEntity))
                && (addIfInjector(xCenter - 1, yCenter + 1, zCenter - 6, aBaseMetaTileEntity)) && (addIfInjector(xCenter - 1, yCenter + 1, zCenter + 6, aBaseMetaTileEntity))
                && (addIfInjector(xCenter - 6, yCenter + 1, zCenter + 1, aBaseMetaTileEntity)) && (addIfInjector(xCenter + 6, yCenter + 1, zCenter + 1, aBaseMetaTileEntity))
                && (addIfInjector(xCenter - 6, yCenter + 1, zCenter - 1, aBaseMetaTileEntity)) && (addIfInjector(xCenter + 6, yCenter + 1, zCenter - 1, aBaseMetaTileEntity))
                && (addIfInjector(xCenter + 1, yCenter - 1, zCenter - 6, aBaseMetaTileEntity)) && (addIfInjector(xCenter + 1, yCenter - 1, zCenter + 6, aBaseMetaTileEntity))
                && (addIfInjector(xCenter - 1, yCenter - 1, zCenter - 6, aBaseMetaTileEntity)) && (addIfInjector(xCenter - 1, yCenter - 1, zCenter + 6, aBaseMetaTileEntity))
                && (addIfInjector(xCenter - 6, yCenter - 1, zCenter + 1, aBaseMetaTileEntity)) && (addIfInjector(xCenter + 6, yCenter - 1, zCenter + 1, aBaseMetaTileEntity))
                && (addIfInjector(xCenter - 6, yCenter - 1, zCenter - 1, aBaseMetaTileEntity)) && (addIfInjector(xCenter + 6, yCenter - 1, zCenter - 1, aBaseMetaTileEntity))
                && (this.mEnergyHatches.size() >= 1) && (this.mOutputHatches.size() >= 1) && (this.mInputHatches.size() >= 2)) {
            int mEnergyHatches_sS = this.mEnergyHatches.size();
            for (int i = 0; i < mEnergyHatches_sS; i++) {
                if (this.mEnergyHatches.get(i).mTier < tier())
                    return false;
            }
            int mOutputHatches_sS = this.mOutputHatches.size();
            for (int i = 0; i < mOutputHatches_sS; i++) {
                if (this.mOutputHatches.get(i).mTier < tier())
                    return false;
            }
            int mInputHatches_sS = this.mInputHatches.size();
            for (int i = 0; i < mInputHatches_sS; i++) {
                if (this.mInputHatches.get(i).mTier < tier())
                    return false;
            }
            mWrench = true;
            mScrewdriver = true;
            mSoftHammer = true;
            mHardHammer = true;
            mSolderingTool = true;
            mCrowbar = true;
            return true;
        }
        return false;
    }

    private boolean checkCoils(int aX, int aY, int aZ) {
        return (isFusionCoil(aX + 6, aY, aZ - 1)) && (isFusionCoil(aX + 6, aY, aZ)) && (isFusionCoil(aX + 6, aY, aZ + 1)) && (isFusionCoil(aX + 5, aY, aZ - 3)) && (isFusionCoil(aX + 5, aY, aZ - 2))
                && (isFusionCoil(aX + 5, aY, aZ + 2)) && (isFusionCoil(aX + 5, aY, aZ + 3)) && (isFusionCoil(aX + 4, aY, aZ - 4)) && (isFusionCoil(aX + 4, aY, aZ + 4))
                && (isFusionCoil(aX + 3, aY, aZ - 5)) && (isFusionCoil(aX + 3, aY, aZ + 5)) && (isFusionCoil(aX + 2, aY, aZ - 5)) && (isFusionCoil(aX + 2, aY, aZ + 5))
                && (isFusionCoil(aX + 1, aY, aZ - 6)) && (isFusionCoil(aX + 1, aY, aZ + 6)) && (isFusionCoil(aX, aY, aZ - 6)) && (isFusionCoil(aX, aY, aZ + 6)) && (isFusionCoil(aX - 1, aY, aZ - 6))
                && (isFusionCoil(aX - 1, aY, aZ + 6)) && (isFusionCoil(aX - 2, aY, aZ - 5)) && (isFusionCoil(aX - 2, aY, aZ + 5)) && (isFusionCoil(aX - 3, aY, aZ - 5))
                && (isFusionCoil(aX - 3, aY, aZ + 5)) && (isFusionCoil(aX - 4, aY, aZ - 4)) && (isFusionCoil(aX - 4, aY, aZ + 4)) && (isFusionCoil(aX - 5, aY, aZ - 3))
                && (isFusionCoil(aX - 5, aY, aZ - 2)) && (isFusionCoil(aX - 5, aY, aZ + 2)) && (isFusionCoil(aX - 5, aY, aZ + 3)) && (isFusionCoil(aX - 6, aY, aZ - 1))
                && (isFusionCoil(aX - 6, aY, aZ)) && (isFusionCoil(aX - 6, aY, aZ + 1));
    }

    private boolean checkUpperOrLowerHulls(int aX, int aY, int aZ) {
        return (isAdvancedMachineCasing(aX + 6, aY, aZ)) && (isAdvancedMachineCasing(aX + 5, aY, aZ - 3)) && (isAdvancedMachineCasing(aX + 5, aY, aZ - 2))
                && (isAdvancedMachineCasing(aX + 5, aY, aZ + 2)) && (isAdvancedMachineCasing(aX + 5, aY, aZ + 3)) && (isAdvancedMachineCasing(aX + 4, aY, aZ - 4))
                && (isAdvancedMachineCasing(aX + 4, aY, aZ + 4)) && (isAdvancedMachineCasing(aX + 3, aY, aZ - 5)) && (isAdvancedMachineCasing(aX + 3, aY, aZ + 5))
                && (isAdvancedMachineCasing(aX + 2, aY, aZ - 5)) && (isAdvancedMachineCasing(aX + 2, aY, aZ + 5)) && (isAdvancedMachineCasing(aX, aY, aZ - 6))
                && (isAdvancedMachineCasing(aX, aY, aZ + 6)) && (isAdvancedMachineCasing(aX - 2, aY, aZ - 5)) && (isAdvancedMachineCasing(aX - 2, aY, aZ + 5))
                && (isAdvancedMachineCasing(aX - 3, aY, aZ - 5)) && (isAdvancedMachineCasing(aX - 3, aY, aZ + 5)) && (isAdvancedMachineCasing(aX - 4, aY, aZ - 4))
                && (isAdvancedMachineCasing(aX - 4, aY, aZ + 4)) && (isAdvancedMachineCasing(aX - 5, aY, aZ - 3)) && (isAdvancedMachineCasing(aX - 5, aY, aZ - 2))
                && (isAdvancedMachineCasing(aX - 5, aY, aZ + 2)) && (isAdvancedMachineCasing(aX - 5, aY, aZ + 3)) && (isAdvancedMachineCasing(aX - 6, aY, aZ));
    }

    private boolean checkHulls(int aX, int aY, int aZ) {
        return (isAdvancedMachineCasing(aX + 6, aY, aZ - 3)) && (isAdvancedMachineCasing(aX + 6, aY, aZ - 2)) && (isAdvancedMachineCasing(aX + 6, aY, aZ + 2))
                && (isAdvancedMachineCasing(aX + 6, aY, aZ + 3)) && (isAdvancedMachineCasing(aX + 3, aY, aZ - 6)) && (isAdvancedMachineCasing(aX + 3, aY, aZ + 6))
                && (isAdvancedMachineCasing(aX + 2, aY, aZ - 6)) && (isAdvancedMachineCasing(aX + 2, aY, aZ + 6)) && (isAdvancedMachineCasing(aX - 2, aY, aZ - 6))
                && (isAdvancedMachineCasing(aX - 2, aY, aZ + 6)) && (isAdvancedMachineCasing(aX - 3, aY, aZ - 6)) && (isAdvancedMachineCasing(aX - 3, aY, aZ + 6))
                && (isAdvancedMachineCasing(aX - 7, aY, aZ)) && (isAdvancedMachineCasing(aX + 7, aY, aZ)) && (isAdvancedMachineCasing(aX, aY, aZ - 7)) && (isAdvancedMachineCasing(aX, aY, aZ + 7))
                && (isAdvancedMachineCasing(aX - 6, aY, aZ - 3)) && (isAdvancedMachineCasing(aX - 6, aY, aZ - 2)) && (isAdvancedMachineCasing(aX - 6, aY, aZ + 2))
                && (isAdvancedMachineCasing(aX - 6, aY, aZ + 3)) && (isAdvancedMachineCasing(aX - 4, aY, aZ - 2)) && (isAdvancedMachineCasing(aX - 4, aY, aZ + 2))
                && (isAdvancedMachineCasing(aX + 4, aY, aZ - 2)) && (isAdvancedMachineCasing(aX + 4, aY, aZ + 2)) && (isAdvancedMachineCasing(aX - 2, aY, aZ - 4))
                && (isAdvancedMachineCasing(aX - 2, aY, aZ + 4)) && (isAdvancedMachineCasing(aX + 2, aY, aZ - 4)) && (isAdvancedMachineCasing(aX + 2, aY, aZ + 4));
    }

    private boolean addIfEnergyInjector(int aX, int aY, int aZ, IGregTechTileEntity aBaseMetaTileEntity) {
        if (addEnergyInputToMachineList(aBaseMetaTileEntity.getIGregTechTileEntity(aX, aY, aZ), 53)) {
            return true;
        }
        return isAdvancedMachineCasing(aX, aY, aZ);
    }

    private boolean addIfInjector(int aX, int aY, int aZ, IGregTechTileEntity aTileEntity) {
        if (addInputToMachineList(aTileEntity.getIGregTechTileEntity(aX, aY, aZ), 53)) {
            return true;
        }
        return isAdvancedMachineCasing(aX, aY, aZ);
    }

    private boolean addIfExtractor(int aX, int aY, int aZ, IGregTechTileEntity aTileEntity) {
        if (addOutputToMachineList(aTileEntity.getIGregTechTileEntity(aX, aY, aZ), 53)) {
            return true;
        }
        return isAdvancedMachineCasing(aX, aY, aZ);
    }

    private boolean isAdvancedMachineCasing(int aX, int aY, int aZ) {
        return (getBaseMetaTileEntity().getBlock(aX, aY, aZ) == getCasing()) && (getBaseMetaTileEntity().getMetaID(aX, aY, aZ) == getCasingMeta());
    }

    public abstract Block getCasing();

    public abstract int getCasingMeta();

    private boolean isFusionCoil(int aX, int aY, int aZ) {
        return (getBaseMetaTileEntity().getBlock(aX, aY, aZ) == getFusionCoil() && (getBaseMetaTileEntity().getMetaID(aX, aY, aZ) == getFusionCoilMeta()));
    }

    public abstract Block getFusionCoil();

    public abstract int getFusionCoilMeta();

    public abstract String[] getDescription();

    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
        ITexture[] sTexture;
        if (aSide == aFacing) {
            sTexture = new ITexture[]{new GT_RenderedTexture(Textures.BlockIcons.MACHINE_CASING_FUSION_GLASS, Dyes.getModulation(-1, Dyes._NULL.mRGBa)), new GT_RenderedTexture(getIconOverlay())};
        } else {
            if (!aActive) {
                sTexture = new ITexture[]{new GT_RenderedTexture(Textures.BlockIcons.MACHINE_CASING_FUSION_GLASS, Dyes.getModulation(-1, Dyes._NULL.mRGBa))};
            } else {
                sTexture = new ITexture[]{new GT_RenderedTexture(Textures.BlockIcons.MACHINE_CASING_FUSION_GLASS_YELLOW, Dyes.getModulation(-1, Dyes._NULL.mRGBa))};
            }
        }
        return sTexture;
    }

    public abstract IIconContainer getIconOverlay();

    @Override
    public boolean isCorrectMachinePart(ItemStack aStack) {
        return true;
    }

    public int overclock(int mStartEnergy) {
        if (tierOverclock() == 1) {
            return 1;
        }
        if (tierOverclock() == 2) {
            return mStartEnergy < 160000000 ? 2 : 1;
        }
        return mStartEnergy < 160000000 ? 4 : mStartEnergy < 320000000 ? 2 : 1;
    }

    @Override
    public boolean checkRecipe(ItemStack aStack) {
        ArrayList<FluidStack> tFluidList = getStoredFluids();
        int tFluidList_sS=tFluidList.size();
        for (int i = 0; i < tFluidList_sS - 1; i++) {
            for (int j = i + 1; j < tFluidList_sS; j++) {
                if (GT_Utility.areFluidsEqual((FluidStack) tFluidList.get(i), (FluidStack) tFluidList.get(j))) {
                    if (((FluidStack) tFluidList.get(i)).amount >= ((FluidStack) tFluidList.get(j)).amount) {
                        tFluidList.remove(j--); tFluidList_sS=tFluidList.size();
                    } else {
                        tFluidList.remove(i--); tFluidList_sS=tFluidList.size();
                        break;
                    }
                }
            }
        }
        if (tFluidList.size() > 1) {
            FluidStack[] tFluids = tFluidList.toArray(new FluidStack[tFluidList.size()]);
            GT_Recipe tRecipe = GT_Recipe.GT_Recipe_Map.sFusionRecipes.findRecipe(this.getBaseMetaTileEntity(), this.mLastRecipe, false, GT_Values.V[8], tFluids, new ItemStack[]{});
            if (tRecipe == null && !mRunningOnLoad) {
                turnCasingActive(false);
                this.mLastRecipe = null;
                return false;
            }
            if (mRunningOnLoad || tRecipe.isRecipeInputEqual(true, tFluids, new ItemStack[]{})) {
            this.mLastRecipe = tRecipe;
            this.mEUt = (this.mLastRecipe.mEUt * overclock(this.mLastRecipe.mSpecialValue));
            this.mMaxProgresstime = this.mLastRecipe.mDuration / overclock(this.mLastRecipe.mSpecialValue);
            this.mEfficiencyIncrease = 10000;
            this.mOutputFluids = this.mLastRecipe.mFluidOutputs;
            turnCasingActive(true);
            mRunningOnLoad = false;
            return true;
            }
        }
        return false;
    }

    public abstract int tierOverclock();

    public boolean turnCasingActive(boolean status) {
        if (this.mEnergyHatches != null) {
            for (GT_MetaTileEntity_Hatch_Energy hatch : this.mEnergyHatches) {
                hatch.mMachineBlock = status ? (byte) 52 : (byte) 53;
            }
        }
        if (this.mOutputHatches != null) {
            for (GT_MetaTileEntity_Hatch_Output hatch : this.mOutputHatches) {
                hatch.mMachineBlock = status ? (byte) 52 : (byte) 53;
            }
        }
        if (this.mInputHatches != null) {
            for (GT_MetaTileEntity_Hatch_Input hatch : this.mInputHatches) {
                hatch.mMachineBlock = status ? (byte) 52 : (byte) 53;
            }
        }
        return true;
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        if (aBaseMetaTileEntity.isServerSide()) {
            if (mEfficiency < 0)
                mEfficiency = 0;
            if (mRunningOnLoad && checkMachine(aBaseMetaTileEntity, mInventory[1])) {
                this.mEUStore = (int) aBaseMetaTileEntity.getStoredEU();
                checkRecipe(mInventory[1]);
            }
            if (--mUpdate == 0 || --mStartUpCheck == 0) {
                mInputHatches.clear();
                mInputBusses.clear();
                mOutputHatches.clear();
                mOutputBusses.clear();
                mDynamoHatches.clear();
                mEnergyHatches.clear();
                mMufflerHatches.clear();
                mMaintenanceHatches.clear();
                mMachine = checkMachine(aBaseMetaTileEntity, mInventory[1]);
            }
            if (mStartUpCheck < 0) {
                if (mMachine) {
                    if (this.mEnergyHatches != null) {
                        for (GT_MetaTileEntity_Hatch_Energy tHatch : mEnergyHatches)
                            if (isValidMetaTileEntity(tHatch)) {
                                if (aBaseMetaTileEntity.getStoredEU() + (2048 * tierOverclock()) < maxEUStore()
                                        && tHatch.getBaseMetaTileEntity().decreaseStoredEnergyUnits(2048 * tierOverclock(), false)) {
                                    aBaseMetaTileEntity.increaseStoredEnergyUnits(2048 * tierOverclock(), true);
                                }
                            }
                    }
                    if (this.mEUStore <= 0 && mMaxProgresstime > 0) {
                        stopMachine();
                    }
                    if (getRepairStatus() > 0) {
                        if (mMaxProgresstime > 0 && doRandomMaintenanceDamage()) {
                            this.getBaseMetaTileEntity().decreaseStoredEnergyUnits(mEUt, true);
                            if (mMaxProgresstime > 0 && ++mProgresstime >= mMaxProgresstime) {
                                if (mOutputItems != null)
                                    for (ItemStack tStack : mOutputItems) if (tStack != null) addOutput(tStack);
                                if (mOutputFluids != null)
                                    for (FluidStack tStack : mOutputFluids) if (tStack != null) addOutput(tStack);
                                mEfficiency = Math.max(0, Math.min(mEfficiency + mEfficiencyIncrease, getMaxEfficiency(mInventory[1]) - ((getIdealStatus() - getRepairStatus()) * 1000)));
                                mOutputItems = null;
                                mProgresstime = 0;
                                mMaxProgresstime = 0;
                                mEfficiencyIncrease = 0;
                                if (mOutputFluids != null && mOutputFluids.length > 0) {
                                    try {
                                        GT_Mod.instance.achievements.issueAchivementHatchFluid(aBaseMetaTileEntity.getWorld().getPlayerEntityByName(aBaseMetaTileEntity.getOwnerName()), mOutputFluids[0]);
                                    } catch (Exception e) {
                                    }
                                }
                                this.mEUStore = (int) aBaseMetaTileEntity.getStoredEU();
                                if (aBaseMetaTileEntity.isAllowedToWork())
                                    checkRecipe(mInventory[1]);
                            }
                        } else {
                            if (aTick % 100 == 0 || aBaseMetaTileEntity.hasWorkJustBeenEnabled() || aBaseMetaTileEntity.hasInventoryBeenModified()) {
                                turnCasingActive(mMaxProgresstime > 0);
                                if (aBaseMetaTileEntity.isAllowedToWork()) {
                                    this.mEUStore = (int) aBaseMetaTileEntity.getStoredEU();
                                    if (checkRecipe(mInventory[1])) {
                                        if (this.mEUStore < this.mLastRecipe.mSpecialValue) {
                                            mMaxProgresstime = 0;
                                            turnCasingActive(false);
                                        }
                                        aBaseMetaTileEntity.decreaseStoredEnergyUnits(this.mLastRecipe.mSpecialValue, true);
                                    }
                                }
                                if (mMaxProgresstime <= 0)
                                    mEfficiency = Math.max(0, mEfficiency - 1000);
                            }
                        }
                    } else {
                        this.mLastRecipe = null;
                        stopMachine();
                    }
                } else {
                    turnCasingActive(false);
                    this.mLastRecipe = null;
                    stopMachine();
                }
            }
            aBaseMetaTileEntity.setErrorDisplayID((aBaseMetaTileEntity.getErrorDisplayID() & ~127) | (mWrench ? 0 : 1) | (mScrewdriver ? 0 : 2) | (mSoftHammer ? 0 : 4) | (mHardHammer ? 0 : 8)
                    | (mSolderingTool ? 0 : 16) | (mCrowbar ? 0 : 32) | (mMachine ? 0 : 64));
            aBaseMetaTileEntity.setActive(mMaxProgresstime > 0);
        }
    }

    @Override
    public boolean onRunningTick(ItemStack aStack) {
        if (mEUt < 0) {
            if (!drainEnergyInput(((long) -mEUt * 10000) / Math.max(1000, mEfficiency))) {
                this.mLastRecipe = null;
                stopMachine();
                return false;
            }
        }
        if (this.mEUStore <= 0) {
            this.mLastRecipe = null;
            stopMachine();
            return false;
        }
        return true;
    }

    public boolean drainEnergyInput(long aEU) {
        return false;
    }

    @Override
    public int getMaxEfficiency(ItemStack aStack) {
        return 10000;
    }

    @Override
    public int getPollutionPerTick(ItemStack aStack) {
        return 0;
    }

    @Override
    public int getDamageToComponent(ItemStack aStack) {
        return 0;
    }

    @Override
    public int getAmountOfOutputs() {
        return 0;
    }

    @Override
    public boolean explodesOnComponentBreak(ItemStack aStack) {
        return false;
    }

    @Override
    public String[] getInfoData() {
        String tier = tier() == 6 ? "I" : tier() == 7 ? "II" : "III";
        float plasmaOut = 0;
        int powerRequired = 0;
        if (this.mLastRecipe != null) {
            powerRequired = this.mLastRecipe.mEUt;
            if (this.mLastRecipe.getFluidOutput(0) != null) {
                plasmaOut = (float)this.mLastRecipe.getFluidOutput(0).amount / (float)this.mLastRecipe.mDuration;
            }
        }

        return new String[]{
                "Fusion Reactor MK "+tier,
                "EU Required: "+powerRequired+"EU/t",
                "Stored EU: "+mEUStore+" / "+maxEUStore(),
                "Plasma Output: "+plasmaOut+"L/t"};
    }

    @Override
    public boolean isGivingInformation() {
        return true;
    }
}