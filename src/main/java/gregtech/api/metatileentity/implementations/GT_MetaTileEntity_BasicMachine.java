package gregtech.api.metatileentity.implementations;

import gregtech.api.GregTech_API;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_Container_BasicMachine;
import gregtech.api.gui.GT_GUIContainer_BasicMachine;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.GT_ItemStack;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Recipe.GT_Recipe_Map;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;

import java.util.Arrays;

import static gregtech.api.enums.GT_Values.V;

/**
 * NEVER INCLUDE THIS FILE IN YOUR MOD!!!
 * <p/>
 * This is the main construct for my Basic Machines such as the Automatic Extractor
 * Extend this class to make a simple Machine
 */
public abstract class GT_MetaTileEntity_BasicMachine extends GT_MetaTileEntity_BasicTank {
    /**
     * return values for checkRecipe()
     */
    protected static final int
            DID_NOT_FIND_RECIPE = 0,
            FOUND_RECIPE_BUT_DID_NOT_MEET_REQUIREMENTS = 1,
            FOUND_AND_SUCCESSFULLY_USED_RECIPE = 2;
    private static final int OTHER_SLOT_COUNT = 4;
    public final ItemStack[] mOutputItems;
    public final int mInputSlotCount, mAmperage;
    public boolean mAllowInputFromOutputSide = false, mFluidTransfer = false, mItemTransfer = false, mHasBeenUpdated = false, mStuttering = false, mCharge = false, mDecharge = false;
    public int mMainFacing = -1, mProgresstime = 0, mMaxProgresstime = 0, mEUt = 0, mOutputBlocked = 0;
    public FluidStack mOutputFluid;
    public String mGUIName = "", mNEIName = "";
    /**
     * Contains the Recipe which has been previously used, or null if there was no previous Recipe, which could have been buffered
     */
    protected GT_Recipe mLastRecipe = null;
    private FluidStack mFluidOut;

    /**
     * @param aOverlays 0 = SideFacingActive
     *                  1 = SideFacingInactive
     *                  2 = FrontFacingActive
     *                  3 = FrontFacingInactive
     *                  4 = TopFacingActive
     *                  5 = TopFacingInactive
     *                  6 = BottomFacingActive
     *                  7 = BottomFacingInactive
     *                  ----- Not all Array Elements have to be initialised, you can also just use 8 Parameters for the Default Pipe Texture Overlays -----
     *                  8 = BottomFacingPipeActive
     *                  9 = BottomFacingPipeInactive
     *                  10 = TopFacingPipeActive
     *                  11 = TopFacingPipeInactive
     *                  12 = SideFacingPipeActive
     *                  13 = SideFacingPipeInactive
     */
    public GT_MetaTileEntity_BasicMachine(int aID, String aName, String aNameRegional, int aTier, int aAmperage, String aDescription, int aInputSlotCount, int aOutputSlotCount, String aGUIName, String aNEIName, ITexture... aOverlays) {
        super(aID, aName, aNameRegional, aTier, OTHER_SLOT_COUNT + aInputSlotCount + aOutputSlotCount + 1, aDescription, aOverlays);
        mInputSlotCount = Math.max(0, aInputSlotCount);
        mOutputItems = new ItemStack[Math.max(0, aOutputSlotCount)];
        mAmperage = aAmperage;
        mGUIName = aGUIName;
        mNEIName = aNEIName;
    }

    public GT_MetaTileEntity_BasicMachine(String aName, int aTier, int aAmperage, String aDescription, ITexture[][][] aTextures, int aInputSlotCount, int aOutputSlotCount, String aGUIName, String aNEIName) {
        super(aName, aTier, OTHER_SLOT_COUNT + aInputSlotCount + aOutputSlotCount + 1, aDescription, aTextures);
        mInputSlotCount = Math.max(0, aInputSlotCount);
        mOutputItems = new ItemStack[Math.max(0, aOutputSlotCount)];
        mAmperage = aAmperage;
        mGUIName = aGUIName;
        mNEIName = aNEIName;
    }

    @Override
    public ITexture[][][] getTextureSet(ITexture[] aTextures) {
        ITexture[][][] rTextures = new ITexture[14][17][];
        aTextures = Arrays.copyOf(aTextures, 14);

        for (int i = 0; i < aTextures.length; i++)
            if (aTextures[i] != null) for (byte c = -1; c < 16; c++) {
                if (rTextures[i][c + 1] == null)
                    rTextures[i][c + 1] = new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][c + 1], aTextures[i]};
            }

        for (byte c = -1; c < 16; c++) {
            if (rTextures[0][c + 1] == null) rTextures[0][c + 1] = getSideFacingActive(c);
            if (rTextures[1][c + 1] == null) rTextures[1][c + 1] = getSideFacingInactive(c);
            if (rTextures[2][c + 1] == null) rTextures[2][c + 1] = getFrontFacingActive(c);
            if (rTextures[3][c + 1] == null) rTextures[3][c + 1] = getFrontFacingInactive(c);
            if (rTextures[4][c + 1] == null) rTextures[4][c + 1] = getTopFacingActive(c);
            if (rTextures[5][c + 1] == null) rTextures[5][c + 1] = getTopFacingInactive(c);
            if (rTextures[6][c + 1] == null) rTextures[6][c + 1] = getBottomFacingActive(c);
            if (rTextures[7][c + 1] == null) rTextures[7][c + 1] = getBottomFacingInactive(c);
            if (rTextures[8][c + 1] == null) rTextures[8][c + 1] = getBottomFacingPipeActive(c);
            if (rTextures[9][c + 1] == null) rTextures[9][c + 1] = getBottomFacingPipeInactive(c);
            if (rTextures[10][c + 1] == null) rTextures[10][c + 1] = getTopFacingPipeActive(c);
            if (rTextures[11][c + 1] == null) rTextures[11][c + 1] = getTopFacingPipeInactive(c);
            if (rTextures[12][c + 1] == null) rTextures[12][c + 1] = getSideFacingPipeActive(c);
            if (rTextures[13][c + 1] == null) rTextures[13][c + 1] = getSideFacingPipeInactive(c);
        }
        return rTextures;
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
        return mTextures[mMainFacing < 2 ? aSide == aFacing ? aActive ? 2 : 3 : aSide == 0 ? aActive ? 6 : 7 : aSide == 1 ? aActive ? 4 : 5 : aActive ? 0 : 1 : aSide == mMainFacing ? aActive ? 2 : 3 : (showPipeFacing() && aSide == aFacing) ? aSide == 0 ? aActive ? 8 : 9 : aSide == 1 ? aActive ? 10 : 11 : aActive ? 12 : 13 : aSide == 0 ? aActive ? 6 : 7 : aSide == 1 ? aActive ? 4 : 5 : aActive ? 0 : 1][aColorIndex + 1];
    }

    @Override
    public boolean isSimpleMachine() {
        return false;
    }

    @Override
    public boolean isOverclockerUpgradable() {
        return false;
    }

    @Override
    public boolean isTransformerUpgradable() {
        return false;
    }

    @Override
    public boolean isElectric() {
        return true;
    }

    @Override
    public boolean isValidSlot(int aIndex) {
        return aIndex > 0 && super.isValidSlot(aIndex) && aIndex != OTHER_SLOT_COUNT + mInputSlotCount + mOutputItems.length;
    }

    @Override
    public boolean isFacingValid(byte aFacing) {
        return mMainFacing > 1 || aFacing > 1;
    }

    @Override
    public boolean isEnetInput() {
        return true;
    }

    @Override
    public boolean isInputFacing(byte aSide) {
        return aSide != mMainFacing;
    }

    @Override
    public boolean isOutputFacing(byte aSide) {
        return false;
    }

    @Override
    public boolean isTeleporterCompatible() {
        return false;
    }

    @Override
    public boolean isLiquidInput(byte aSide) {
        return aSide != mMainFacing && (mAllowInputFromOutputSide || aSide != getBaseMetaTileEntity().getFrontFacing());
    }

    @Override
    public boolean isLiquidOutput(byte aSide) {
        return aSide != mMainFacing;
    }

    @Override
    public long getMinimumStoredEU() {
        return V[mTier] * 16;
    }

    @Override
    public long maxEUStore() {
        return V[mTier] * 64;
    }

    @Override
    public long maxEUInput() {
        return V[mTier];
    }

    @Override
    public long maxSteamStore() {
        return maxEUStore();
    }

    @Override
    public long maxAmperesIn() {
        return (mEUt * 2) / V[mTier] + 1;
    }

    @Override
    public int getInputSlot() {
        return OTHER_SLOT_COUNT;
    }

    @Override
    public int getOutputSlot() {
        return OTHER_SLOT_COUNT + mInputSlotCount;
    }

    @Override
    public int getStackDisplaySlot() {
        return 2;
    }

    @Override
    public int rechargerSlotStartIndex() {
        return 1;
    }

    @Override
    public int dechargerSlotStartIndex() {
        return 1;
    }

    @Override
    public int rechargerSlotCount() {
        return mCharge ? 1 : 0;
    }

    @Override
    public int dechargerSlotCount() {
        return mDecharge ? 1 : 0;
    }

    @Override
    public boolean isAccessAllowed(EntityPlayer aPlayer) {
        return true;
    }

    @Override
    public int getProgresstime() {
        return mProgresstime;
    }

    @Override
    public int maxProgresstime() {
        return mMaxProgresstime;
    }

    @Override
    public int increaseProgress(int aProgress) {
        mProgresstime += aProgress;
        return mMaxProgresstime - mProgresstime;
    }

    @Override
    public boolean isFluidInputAllowed(FluidStack aFluid) {
        return getFillableStack() != null || (getRecipeList() != null && getRecipeList().containsInput(aFluid));
    }

    @Override
    public boolean isFluidChangingAllowed() {
        return true;
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
        return true;
    }

    @Override
    public boolean canTankBeEmptied() {
        return true;
    }

    @Override
    public boolean displaysItemStack() {
        return true;
    }

    @Override
    public boolean displaysStackSize() {
        return true;
    }

    @Override
    public FluidStack getDisplayedFluid() {
        return displaysOutputFluid() ? getDrainableStack() : null;
    }

    @Override
    public FluidStack getDrainableStack() {
        return mFluidOut;
    }

    @Override
    public FluidStack setDrainableStack(FluidStack aFluid) {
        mFluidOut = aFluid;
        return mFluidOut;
    }

    @Override
    public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer) {
        if (aBaseMetaTileEntity.isClientSide()) return true;
        aBaseMetaTileEntity.openGUI(aPlayer);
        return true;
    }

    @Override
    public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_Container_BasicMachine(aPlayerInventory, aBaseMetaTileEntity);
    }

    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_BasicMachine(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), mGUIName, GT_Utility.isStringValid(mNEIName) ? mNEIName : getRecipeList() != null ? getRecipeList().mUnlocalizedName : "");
    }

    @Override
    public void initDefaultModes(NBTTagCompound aNBT) {
        mMainFacing = -1;
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setBoolean("mFluidTransfer", mFluidTransfer);
        aNBT.setBoolean("mItemTransfer", mItemTransfer);
        aNBT.setBoolean("mHasBeenUpdated", mHasBeenUpdated);
        aNBT.setBoolean("mAllowInputFromOutputSide", mAllowInputFromOutputSide);
        aNBT.setInteger("mEUt", mEUt);
        aNBT.setInteger("mMainFacing", mMainFacing);
        aNBT.setInteger("mProgresstime", mProgresstime);
        aNBT.setInteger("mMaxProgresstime", mMaxProgresstime);
        if (mOutputFluid != null) aNBT.setTag("mOutputFluid", mOutputFluid.writeToNBT(new NBTTagCompound()));
        if (mFluidOut != null) aNBT.setTag("mFluidOut", mFluidOut.writeToNBT(new NBTTagCompound()));

        for (int i = 0; i < mOutputItems.length; i++)
            if (mOutputItems[i] != null)
                aNBT.setTag("mOutputItem" + i, mOutputItems[i].writeToNBT(new NBTTagCompound()));
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        mFluidTransfer = aNBT.getBoolean("mFluidTransfer");
        mItemTransfer = aNBT.getBoolean("mItemTransfer");
        mHasBeenUpdated = aNBT.getBoolean("mHasBeenUpdated");
        mAllowInputFromOutputSide = aNBT.getBoolean("mAllowInputFromOutputSide");
        mEUt = aNBT.getInteger("mEUt");
        mMainFacing = aNBT.getInteger("mMainFacing");
        mProgresstime = aNBT.getInteger("mProgresstime");
        mMaxProgresstime = aNBT.getInteger("mMaxProgresstime");
        mOutputFluid = FluidStack.loadFluidStackFromNBT(aNBT.getCompoundTag("mOutputFluid"));
        mFluidOut = FluidStack.loadFluidStackFromNBT(aNBT.getCompoundTag("mFluidOut"));

        for (int i = 0; i < mOutputItems.length; i++) mOutputItems[i] = GT_Utility.loadItem(aNBT, "mOutputItem" + i);
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        super.onPostTick(aBaseMetaTileEntity, aTick);

        if (aBaseMetaTileEntity.isServerSide()) {
            mCharge = aBaseMetaTileEntity.getStoredEU() / 2 > aBaseMetaTileEntity.getEUCapacity() / 3;
            mDecharge = aBaseMetaTileEntity.getStoredEU() < aBaseMetaTileEntity.getEUCapacity() / 3;

            doDisplayThings();

            boolean tSucceeded = false;

            if (mMaxProgresstime > 0 && (mProgresstime >= 0 || aBaseMetaTileEntity.isAllowedToWork())) {
                aBaseMetaTileEntity.setActive(true);
                if (mProgresstime < 0 || drainEnergyForProcess(mEUt)) {
                    if (++mProgresstime >= mMaxProgresstime) {
                        for (int i = 0; i < mOutputItems.length; i++)
                            for (int j = 0; j < mOutputItems.length; j++)
                                if (aBaseMetaTileEntity.addStackToSlot(getOutputSlot() + ((j + i) % mOutputItems.length), mOutputItems[i]))
                                    break;
                        if (mOutputFluid != null)
                            if (getDrainableStack() == null) setDrainableStack(mOutputFluid.copy());
                            else if (mOutputFluid.isFluidEqual(getDrainableStack()))
                                getDrainableStack().amount += mOutputFluid.amount;
                        for (int i = 0; i < mOutputItems.length; i++) mOutputItems[i] = null;
                        mOutputFluid = null;
                        mEUt = 0;
                        mProgresstime = 0;
                        mMaxProgresstime = 0;
                        mStuttering = false;
                        tSucceeded = true;
                        endProcess();
                    }
                    if (mProgresstime > 5) mStuttering = false;
                } else {
                    if (!mStuttering) {
                        stutterProcess();
                        if (canHaveInsufficientEnergy()) mProgresstime = -100;
                        mStuttering = true;
                    }
                }
            } else {
                aBaseMetaTileEntity.setActive(false);
            }

            boolean tRemovedOutputFluid = false;

            if (doesAutoOutputFluids() && getDrainableStack() != null && aBaseMetaTileEntity.getFrontFacing() != mMainFacing && (tSucceeded || aTick % 20 == 0)) {
                IFluidHandler tTank = aBaseMetaTileEntity.getITankContainerAtSide(aBaseMetaTileEntity.getFrontFacing());
                if (tTank != null) {
                    FluidStack tDrained = drain(1000, false);
                    if (tDrained != null) {
                        int tFilledAmount = tTank.fill(ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()), tDrained, false);
                        if (tFilledAmount > 0)
                            tTank.fill(ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()), drain(tFilledAmount, true), true);
                    }
                }
                if (getDrainableStack() == null) tRemovedOutputFluid = true;
            }

            if (doesAutoOutput() && !isOutputEmpty() && aBaseMetaTileEntity.getFrontFacing() != mMainFacing && (tSucceeded || mOutputBlocked % 300 == 1 || aBaseMetaTileEntity.hasInventoryBeenModified() || aTick % 600 == 0)) {
                TileEntity tTileEntity2 = aBaseMetaTileEntity.getTileEntityAtSide(aBaseMetaTileEntity.getFrontFacing());
                for (int i = 0, tCosts = 1; i < mOutputItems.length && tCosts > 0 && aBaseMetaTileEntity.isUniversalEnergyStored(128); i++) {
                    tCosts = GT_Utility.moveOneItemStack(aBaseMetaTileEntity, tTileEntity2, aBaseMetaTileEntity.getFrontFacing(), aBaseMetaTileEntity.getBackFacing(), null, false, (byte) 64, (byte) 1, (byte) 64, (byte) 1);
                    if (tCosts > 0) aBaseMetaTileEntity.decreaseStoredEnergyUnits(tCosts, true);
                }
            }

            if (mOutputBlocked != 0) if (isOutputEmpty()) mOutputBlocked = 0;
            else mOutputBlocked++;

            if (allowToCheckRecipe()) {
                if (mMaxProgresstime <= 0 && aBaseMetaTileEntity.isAllowedToWork() && (tRemovedOutputFluid || tSucceeded || aBaseMetaTileEntity.hasInventoryBeenModified() || aTick % 600 == 0 || aBaseMetaTileEntity.hasWorkJustBeenEnabled()) && hasEnoughEnergyToCheckRecipe()) {
                    if (checkRecipe() == 2) {
                        if (mInventory[3] != null && mInventory[3].stackSize <= 0) mInventory[3] = null;
                        for (int i = getInputSlot(), j = i + mInputSlotCount; i < j; i++)
                            if (mInventory[i] != null && mInventory[i].stackSize <= 0) mInventory[i] = null;
                        for (int i = 0; i < mOutputItems.length; i++) {
                            mOutputItems[i] = GT_Utility.copy(mOutputItems[i]);
                            if (mOutputItems[i] != null && mOutputItems[i].stackSize > 64)
                                mOutputItems[i].stackSize = 64;
                            mOutputItems[i] = GT_OreDictUnificator.get(true, mOutputItems[i]);
                        }
                        if (mFluid != null && mFluid.amount <= 0) mFluid = null;
                        mMaxProgresstime = Math.max(1, mMaxProgresstime);
                        if (GT_Utility.isDebugItem(mInventory[dechargerSlotStartIndex()])) {
                            mEUt = mMaxProgresstime = 1;
                        }
                        startProcess();
                    } else {
                        mMaxProgresstime = 0;
                        for (int i = 0; i < mOutputItems.length; i++) mOutputItems[i] = null;
                        mOutputFluid = null;
                    }
                }
            } else {
                if (!mStuttering) {
                    stutterProcess();
                    mStuttering = true;
                }
            }
        }
    }

    protected void doDisplayThings() {
        if (mMainFacing < 2 && getBaseMetaTileEntity().getFrontFacing() > 1) {
            mMainFacing = getBaseMetaTileEntity().getFrontFacing();
        }
        if (mMainFacing >= 2 && !mHasBeenUpdated) {
            mHasBeenUpdated = true;
            getBaseMetaTileEntity().setFrontFacing(getBaseMetaTileEntity().getBackFacing());
        }

        if (displaysInputFluid()) {
            int tDisplayStackSlot = OTHER_SLOT_COUNT + mInputSlotCount + mOutputItems.length;
            if (getFillableStack() == null) {
                if (ItemList.Display_Fluid.isStackEqual(mInventory[tDisplayStackSlot], true, true))
                    mInventory[tDisplayStackSlot] = null;
            } else {
                mInventory[tDisplayStackSlot] = GT_Utility.getFluidDisplayStack(getFillableStack(), displaysStackSize());
            }
        }
    }

    protected boolean hasEnoughEnergyToCheckRecipe() {
        return getBaseMetaTileEntity().isUniversalEnergyStored(getMinimumStoredEU() / 2);
    }

    protected boolean drainEnergyForProcess(long aEUt) {
        return getBaseMetaTileEntity().decreaseStoredEnergyUnits(aEUt, false);
    }

    protected void calculateOverclockedNess(GT_Recipe aRecipe) {
        calculateOverclockedNess(aRecipe.mEUt, aRecipe.mDuration);
    }

    protected void calculateOverclockedNess(int aEUt, int aDuration) {
        if (aEUt <= 16) {
            mEUt = aEUt * (1 << (mTier - 1)) * (1 << (mTier - 1));
            mMaxProgresstime = aDuration / (1 << (mTier - 1));
        } else {
            mEUt = aEUt;
            mMaxProgresstime = aDuration;
            while (mEUt <= V[mTier - 1] * mAmperage) {
                mEUt *= 4;
                mMaxProgresstime /= 2;
            }
        }
    }

    protected ItemStack getSpecialSlot() {
        return mInventory[3];
    }

    protected ItemStack getOutputAt(int aIndex) {
        return mInventory[getOutputSlot() + aIndex];
    }

    protected ItemStack[] getAllOutputs() {
        ItemStack[] rOutputs = new ItemStack[mOutputItems.length];
        for (int i = 0; i < mOutputItems.length; i++) rOutputs[i] = getOutputAt(i);
        return rOutputs;
    }

    protected boolean canOutput(GT_Recipe aRecipe) {
        return aRecipe != null && (aRecipe.mNeedsEmptyOutput ? isOutputEmpty() && getDrainableStack() == null : canOutput(aRecipe.getFluidOutput(0)) && canOutput(aRecipe.mOutputs));
    }

    protected boolean canOutput(ItemStack... aOutputs) {
        if (aOutputs == null) return true;
        ItemStack[] tOutputSlots = getAllOutputs();
        for (int i = 0; i < tOutputSlots.length && i < aOutputs.length; i++)
            if (tOutputSlots[i] != null && aOutputs[i] != null && (!GT_Utility.areStacksEqual(tOutputSlots[i], aOutputs[i], false) || tOutputSlots[i].stackSize + aOutputs[i].stackSize > tOutputSlots[i].getMaxStackSize())) {
                mOutputBlocked++;
                return false;
            }
        return true;
    }

    protected boolean canOutput(FluidStack aOutput) {
        return getDrainableStack() == null || aOutput == null || (getDrainableStack().isFluidEqual(aOutput) && (getDrainableStack().amount <= 0 || getDrainableStack().amount + aOutput.amount <= getCapacity()));
    }

    protected ItemStack getInputAt(int aIndex) {
        return mInventory[getInputSlot() + aIndex];
    }

    protected ItemStack[] getAllInputs() {
        ItemStack[] rInputs = new ItemStack[mInputSlotCount];
        for (int i = 0; i < mInputSlotCount; i++) rInputs[i] = getInputAt(i);
        return rInputs;
    }

    protected boolean isOutputEmpty() {
        boolean rIsEmpty = true;
        for (ItemStack tOutputSlotContent : getAllOutputs()) if (tOutputSlotContent != null) rIsEmpty = false;
        return rIsEmpty;
    }

    protected boolean displaysInputFluid() {
        return true;
    }

    protected boolean displaysOutputFluid() {
        return true;
    }

    @Override
    public void onValueUpdate(byte aValue) {
        mMainFacing = aValue;
    }

    @Override
    public byte getUpdateData() {
        return (byte) mMainFacing;
    }

    @Override
    public void doSound(byte aIndex, double aX, double aY, double aZ) {
        super.doSound(aIndex, aX, aY, aZ);
        if (aIndex == 8) GT_Utility.doSoundAtClient(GregTech_API.sSoundList.get(210), 100, 1.0F, aX, aY, aZ);
    }

    public boolean doesAutoOutput() {
        return mItemTransfer;
    }

    public boolean doesAutoOutputFluids() {
        return mFluidTransfer;
    }

    public boolean allowToCheckRecipe() {
        return true;
    }

    public boolean showPipeFacing() {
        return true;
    }

    /**
     * Called whenever the Machine successfully started a Process, useful for Sound Effects
     */
    public void startProcess() {
        //
    }

    /**
     * Called whenever the Machine successfully finished a Process, useful for Sound Effects
     */
    public void endProcess() {
        //
    }

    /**
     * Called whenever the Machine aborted a Process, useful for Sound Effects
     */
    public void abortProcess() {
        //
    }

    /**
     * Called whenever the Machine aborted a Process but still works on it, useful for Sound Effects
     */
    public void stutterProcess() {
        if (useStandardStutterSound()) sendSound((byte) 8);
    }

    /**
     * If this Machine can have the Insufficient Energy Line Problem
     */
    public boolean canHaveInsufficientEnergy() {
        return true;
    }

    public boolean useStandardStutterSound() {
        return true;
    }

    @Override
    public String[] getInfoData() {
        return new String[]{
                mNEIName,
                "Progress:", (mProgresstime / 20) + " secs",
                (mMaxProgresstime / 20) + " secs",
                "Stored Energy:",
                getBaseMetaTileEntity().getStoredEU() + "EU",
                getBaseMetaTileEntity().getEUCapacity() + "EU"};
    }

    @Override
    public boolean isGivingInformation() {
        return true;
    }

    @Override
    public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        if (aSide == getBaseMetaTileEntity().getFrontFacing() || aSide == mMainFacing) {
            mAllowInputFromOutputSide = !mAllowInputFromOutputSide;
            GT_Utility.sendChatToPlayer(aPlayer, mAllowInputFromOutputSide ? "Input from Output Side allowed" : "Input from Output Side forbidden");
        }
    }

    @Override
    public boolean allowCoverOnSide(byte aSide, GT_ItemStack aCoverID) {
        return (aSide != mMainFacing || GregTech_API.getCoverBehavior(aCoverID.toStack()).isGUIClickable(aSide, GT_Utility.stackToInt(aCoverID.toStack()), 0, getBaseMetaTileEntity()));
    }

    @Override
    public boolean allowPullStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
        return aSide != mMainFacing && aIndex >= getOutputSlot() && aIndex < getOutputSlot() + mOutputItems.length;
    }

    @Override
    public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
        if (aSide == mMainFacing || aIndex < getInputSlot() || aIndex >= getInputSlot() + mInputSlotCount || (!mAllowInputFromOutputSide && aSide == aBaseMetaTileEntity.getFrontFacing()))
            return false;
        for (int i = getInputSlot(), j = i + mInputSlotCount; i < j; i++)
            if (GT_Utility.areStacksEqual(GT_OreDictUnificator.get(aStack), mInventory[i])) return i == aIndex;
        return true;
    }

    /**
     * @return the Recipe List which is used for this Machine, this is a useful Default Handler
     */
    public GT_Recipe_Map getRecipeList() {
        return null;
    }

    /**
     * Override this to check the Recipes yourself, super calls to this could be useful if you just want to add a special case
     * <p/>
     * I thought about Enum too, but Enum doesn't add support for people adding other return Systems.
     * <p/>
     * Funny how Eclipse marks the word Enum as not correctly spelled.
     *
     * @return see constants above
     */
    public int checkRecipe() {
        GT_Recipe_Map tMap = getRecipeList();
        if (tMap == null) return DID_NOT_FIND_RECIPE;
        GT_Recipe tRecipe = tMap.findRecipe(getBaseMetaTileEntity(), mLastRecipe, false, V[mTier], new FluidStack[]{getFillableStack()}, getSpecialSlot(), getAllInputs());
        if (tRecipe == null) return DID_NOT_FIND_RECIPE;
        if (tRecipe.mCanBeBuffered) mLastRecipe = tRecipe;
        if (!canOutput(tRecipe)) {
            mOutputBlocked++;
            return FOUND_RECIPE_BUT_DID_NOT_MEET_REQUIREMENTS;
        }
        if (!tRecipe.isRecipeInputEqual(true, new FluidStack[]{getFillableStack()}, getAllInputs()))
            return FOUND_RECIPE_BUT_DID_NOT_MEET_REQUIREMENTS;

        for (int i = 0; i < mOutputItems.length; i++)
            if (getBaseMetaTileEntity().getRandomNumber(10000) < tRecipe.getOutputChance(i))
                mOutputItems[i] = tRecipe.getOutput(i);
        mOutputFluid = tRecipe.getFluidOutput(0);
        calculateOverclockedNess(tRecipe);
        return FOUND_AND_SUCCESSFULLY_USED_RECIPE;
    }

    public ITexture[] getSideFacingActive(byte aColor) {
        return new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][aColor + 1]};
    }

    public ITexture[] getSideFacingInactive(byte aColor) {
        return new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][aColor + 1]};
    }

    public ITexture[] getFrontFacingActive(byte aColor) {
        return new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][aColor + 1]};
    }

    public ITexture[] getFrontFacingInactive(byte aColor) {
        return new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][aColor + 1]};
    }

    public ITexture[] getTopFacingActive(byte aColor) {
        return new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][aColor + 1]};
    }

    public ITexture[] getTopFacingInactive(byte aColor) {
        return new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][aColor + 1]};
    }

    public ITexture[] getBottomFacingActive(byte aColor) {
        return new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][aColor + 1]};
    }

    public ITexture[] getBottomFacingInactive(byte aColor) {
        return new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][aColor + 1]};
    }

    public ITexture[] getBottomFacingPipeActive(byte aColor) {
        return new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][aColor + 1], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_PIPE_OUT)};
    }

    public ITexture[] getBottomFacingPipeInactive(byte aColor) {
        return new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][aColor + 1], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_PIPE_OUT)};
    }

    public ITexture[] getTopFacingPipeActive(byte aColor) {
        return new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][aColor + 1], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_PIPE_OUT)};
    }

    public ITexture[] getTopFacingPipeInactive(byte aColor) {
        return new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][aColor + 1], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_PIPE_OUT)};
    }

    public ITexture[] getSideFacingPipeActive(byte aColor) {
        return new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][aColor + 1], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_PIPE_OUT)};
    }

    public ITexture[] getSideFacingPipeInactive(byte aColor) {
        return new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][aColor + 1], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_PIPE_OUT)};
    }
}