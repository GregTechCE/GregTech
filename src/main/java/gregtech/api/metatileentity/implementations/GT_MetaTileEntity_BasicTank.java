package gregtech.api.metatileentity.implementations;

import gregtech.api.enums.ItemList;
import gregtech.api.gui.GT_Container_BasicTank;
import gregtech.api.gui.GT_GUIContainer_BasicTank;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

/**
 * NEVER INCLUDE THIS FILE IN YOUR MOD!!!
 * <p/>
 * This is the main construct for my generic Tanks. Filling and emptying behavior have to be implemented manually
 */
public abstract class GT_MetaTileEntity_BasicTank extends GT_MetaTileEntity_TieredMachineBlock {

    public FluidStack mFluid;

    /**
     * @param aInvSlotCount should be 3
     */
    public GT_MetaTileEntity_BasicTank(int aID, String aName, String aNameRegional, int aTier, int aInvSlotCount, String aDescription, ITexture... aTextures) {
        super(aID, aName, aNameRegional, aTier, aInvSlotCount, aDescription, aTextures);
    }

    public GT_MetaTileEntity_BasicTank(String aName, int aTier, int aInvSlotCount, String aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, aInvSlotCount, aDescription, aTextures);
    }

    @Override
    public boolean isSimpleMachine() {
        return false;
    }

    @Override
    public boolean isValidSlot(int aIndex) {
        return aIndex != getStackDisplaySlot();
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        if (mFluid != null) aNBT.setTag("mFluid", mFluid.writeToNBT(new NBTTagCompound()));
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        mFluid = FluidStack.loadFluidStackFromNBT(aNBT.getCompoundTag("mFluid"));
    }

    public abstract boolean doesFillContainers();

    public abstract boolean doesEmptyContainers();

    public abstract boolean canTankBeFilled();

    public abstract boolean canTankBeEmptied();

    public abstract boolean displaysItemStack();

    public abstract boolean displaysStackSize();

    public int getInputSlot() {
        return 0;
    }

    public int getOutputSlot() {
        return 1;
    }

    public int getStackDisplaySlot() {
        return 2;
    }

    public boolean isFluidInputAllowed(FluidStack aFluid) {
        return true;
    }

    public boolean isFluidChangingAllowed() {
        return true;
    }

    public FluidStack getFillableStack() {
        return mFluid;
    }

    public FluidStack setFillableStack(FluidStack aFluid) {
        mFluid = aFluid;
        return mFluid;
    }

    public FluidStack getDrainableStack() {
        return mFluid;
    }

    public FluidStack setDrainableStack(FluidStack aFluid) {
        mFluid = aFluid;
        return mFluid;
    }

    public FluidStack getDisplayedFluid() {
        return getDrainableStack();
    }

    @Override
    public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_Container_BasicTank(aPlayerInventory, aBaseMetaTileEntity);
    }

    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_BasicTank(aPlayerInventory, aBaseMetaTileEntity, getLocalName());
    }

    @Override
    public void onPreTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        if (aBaseMetaTileEntity.isServerSide()) {
            if (isFluidChangingAllowed() && getFillableStack() != null && getFillableStack().amount <= 0)
                setFillableStack(null);

            if (displaysItemStack() && getStackDisplaySlot() >= 0 && getStackDisplaySlot() < mInventory.length) {
                if (getDisplayedFluid() == null) {
                    if (ItemList.Display_Fluid.isStackEqual(mInventory[getStackDisplaySlot()], true, true))
                        mInventory[getStackDisplaySlot()] = null;
                } else {
                    mInventory[getStackDisplaySlot()] = GT_Utility.getFluidDisplayStack(getDisplayedFluid(), displaysStackSize());
                }
            }

            if (doesEmptyContainers()) {
                FluidStack tFluid = GT_Utility.getFluidForFilledItem(mInventory[getInputSlot()], true);
                if (tFluid != null && isFluidInputAllowed(tFluid)) {
                    if (getFillableStack() == null) {
                        if (isFluidInputAllowed(tFluid) && tFluid.amount <= getCapacity()) {
                            if (aBaseMetaTileEntity.addStackToSlot(getOutputSlot(), GT_Utility.getContainerItem(mInventory[getInputSlot()], true), 1)) {
                                setFillableStack(tFluid.copy());
                                aBaseMetaTileEntity.decrStackSize(getInputSlot(), 1);
                            }
                        }
                    } else {
                        if (tFluid.isFluidEqual(getFillableStack()) && tFluid.amount + getFillableStack().amount <= getCapacity()) {
                            if (aBaseMetaTileEntity.addStackToSlot(getOutputSlot(), GT_Utility.getContainerItem(mInventory[getInputSlot()], true), 1)) {
                                getFillableStack().amount += tFluid.amount;
                                aBaseMetaTileEntity.decrStackSize(getInputSlot(), 1);
                            }
                        }
                    }
                }
            }

            if (doesFillContainers()) {
                ItemStack tOutput = GT_Utility.fillFluidContainer(getDrainableStack(), mInventory[getInputSlot()], false, true);
                if (tOutput != null && aBaseMetaTileEntity.addStackToSlot(getOutputSlot(), tOutput, 1)) {
                    FluidStack tFluid = GT_Utility.getFluidForFilledItem(tOutput, true);
                    aBaseMetaTileEntity.decrStackSize(getInputSlot(), 1);
                    if (tFluid != null) getDrainableStack().amount -= tFluid.amount;
                    if (getDrainableStack().amount <= 0 && isFluidChangingAllowed()) setDrainableStack(null);
                }
            }
        }
    }

    @Override
    public FluidStack getFluid() {
        return getDrainableStack();
    }

    @Override
    public int getFluidAmount() {
        return getDrainableStack() != null ? getDrainableStack().amount : 0;
    }

    @Override
    public int fill(FluidStack aFluid, boolean doFill) {
        if (aFluid == null || aFluid.getFluid().getID() <= 0 || aFluid.amount <= 0 || !canTankBeFilled() || !isFluidInputAllowed(aFluid))
            return 0;

        if (getFillableStack() == null || getFillableStack().getFluid().getID() <= 0) {
            if (aFluid.amount <= getCapacity()) {
                if (doFill) {
                    setFillableStack(aFluid.copy());
                    getBaseMetaTileEntity().markDirty();
                }
                return aFluid.amount;
            }
            if (doFill) {
                setFillableStack(aFluid.copy());
                getFillableStack().amount = getCapacity();
                getBaseMetaTileEntity().markDirty();
            }
            return getCapacity();
        }

        if (!getFillableStack().isFluidEqual(aFluid))
            return 0;

        int space = getCapacity() - getFillableStack().amount;
        if (aFluid.amount <= space) {
            if (doFill) {
                getFillableStack().amount += aFluid.amount;
                getBaseMetaTileEntity().markDirty();
            }
            return aFluid.amount;
        }
        if (doFill)
            getFillableStack().amount = getCapacity();
        return space;
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        if (getDrainableStack() == null || !canTankBeEmptied()) return null;
        if (getDrainableStack().amount <= 0 && isFluidChangingAllowed()) {
            setDrainableStack(null);
            getBaseMetaTileEntity().markDirty();
            return null;
        }

        int used = maxDrain;
        if (getDrainableStack().amount < used)
            used = getDrainableStack().amount;

        if (doDrain) {
            getDrainableStack().amount -= used;
            getBaseMetaTileEntity().markDirty();
        }

        FluidStack drained = getDrainableStack().copy();
        drained.amount = used;

        if (getDrainableStack().amount <= 0 && isFluidChangingAllowed()) {
            setDrainableStack(null);
            getBaseMetaTileEntity().markDirty();
        }

        return drained;
    }

    @Override
    public boolean allowPullStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
        return aIndex == getOutputSlot();
    }

    @Override
    public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
        return aIndex == getInputSlot();
    }
}