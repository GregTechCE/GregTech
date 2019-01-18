package gregtech.api.capability.impl;

import gregtech.api.GTValues;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.IWorkable;
import gregtech.api.metatileentity.MTETrait;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.recipes.machines.FuelRecipeMap;
import gregtech.api.recipes.recipes.FuelRecipe;
import gregtech.api.util.GTUtility;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

import java.util.function.Supplier;

public class FuelRecipeMapWorkableHandler extends MTETrait implements IWorkable {

    public final FuelRecipeMap recipeMap;
    protected FuelRecipe previousRecipe;

    protected final Supplier<IEnergyContainer> energyContainer;
    protected final Supplier<IMultipleTankHandler> fluidTank;
    public final long maxVoltage;

    private int recipeDurationLeft;
    private long recipeOutputVoltage;

    private boolean isActive;
    private boolean workingEnabled = true;
    private boolean wasActiveAndNeedsUpdate = false;

    public FuelRecipeMapWorkableHandler(MetaTileEntity metaTileEntity, FuelRecipeMap recipeMap, Supplier<IEnergyContainer> energyContainer, Supplier<IMultipleTankHandler> fluidTank, long maxVoltage) {
        super(metaTileEntity);
        this.recipeMap = recipeMap;
        this.energyContainer = energyContainer;
        this.fluidTank = fluidTank;
        this.maxVoltage = maxVoltage;
    }

    public long getRecipeOutputVoltage() {
        return recipeOutputVoltage;
    }

    @Override
    public String getName() {
        return "FuelRecipeMapWorkable";
    }

    @Override
    public int getNetworkID() {
        return 2;
    }

    @Override
    public Capability<?> getImplementingCapability() {
        return GregtechCapabilities.CAPABILITY_WORKABLE;
    }

    @Override
    public void update() {
        if(getMetaTileEntity().getWorld().isRemote) return;
        if(workingEnabled) {
            if(recipeDurationLeft > 0) {
                if(energyContainer.get().getEnergyCanBeInserted() >=
                    recipeOutputVoltage || shouldVoidExcessiveEnergy()) {
                    energyContainer.get().addEnergy(recipeOutputVoltage);
                    if(--this.recipeDurationLeft == 0) {
                        this.wasActiveAndNeedsUpdate = true;
                    }
                }
            }
            if(recipeDurationLeft == 0) {
                tryAcquireNewRecipe();
            }
        }
        if(wasActiveAndNeedsUpdate) {
            setActive(false);
            this.wasActiveAndNeedsUpdate = false;
        }
    }

    protected boolean shouldVoidExcessiveEnergy() {
        return false;
    }

    private void tryAcquireNewRecipe() {
        IMultipleTankHandler fluidTanks = this.fluidTank.get();
        for(IFluidTank fluidTank : fluidTanks) {
            FluidStack tankContents = fluidTank.getFluid();
            if(tankContents != null && tankContents.amount > 0) {
                int fuelAmountUsed = tryAcquireNewRecipe(tankContents);
                if(fuelAmountUsed > 0) {
                    fluidTank.drain(fuelAmountUsed, true);
                    break; //recipe is found and ready to use
                }
            }
        }
    }

    private int tryAcquireNewRecipe(FluidStack fluidStack) {
        FuelRecipe currentRecipe;
        if(previousRecipe != null && previousRecipe.matches(maxVoltage, fluidStack)) {
            //if previous recipe still matches inputs, try to use it
            currentRecipe = previousRecipe;
        } else {
            //else, try searching new recipe for given inputs
            currentRecipe = recipeMap.findRecipe(maxVoltage, fluidStack);
            //if we found recipe that can be buffered, buffer it
            if(currentRecipe != null) {
                this.previousRecipe = currentRecipe;
            }
        }
        if(currentRecipe != null && checkRecipe(currentRecipe)) {
            int fuelAmountToUse = calculateFuelAmount(currentRecipe);
            if(fluidStack.amount >= fuelAmountToUse) {
                this.recipeDurationLeft = calculateRecipeDuration(currentRecipe);
                this.recipeOutputVoltage = startRecipe(currentRecipe, fuelAmountToUse, recipeDurationLeft);
                if(wasActiveAndNeedsUpdate) {
                    this.wasActiveAndNeedsUpdate = false;
                } else {
                    setActive(true);
                }
                return fuelAmountToUse;
            }
        }
        return 0;
    }

    protected boolean checkRecipe(FuelRecipe recipe) {
        return true;
    }

    protected int calculateFuelAmount(FuelRecipe currentRecipe) {
        return currentRecipe.getRecipeFluid().amount * getVoltageMultiplier(maxVoltage, currentRecipe.getMinVoltage());
    }

    protected int calculateRecipeDuration(FuelRecipe currentRecipe) {
        return currentRecipe.getDuration();
    }

    /**
     * Performs preparations for starting given recipe and determines it's output voltage
     * @return recipe's output voltage
     */
    protected long startRecipe(FuelRecipe currentRecipe, int fuelAmountUsed, int recipeDuration) {
        return maxVoltage;
    }

    public static int getVoltageMultiplier(long maxVoltage, long minVoltage) {
        return (int) (getTieredVoltage(maxVoltage) / getTieredVoltage(minVoltage));
    }

    public static long getTieredVoltage(long voltage) {
        return GTValues.V[GTUtility.getTierByVoltage(voltage)];
    }

    @Override
    public void setActive(boolean active) {
        this.isActive = active;
        if(!metaTileEntity.getWorld().isRemote) {
            metaTileEntity.markDirty();
            writeCustomData(1, buf -> buf.writeBoolean(active));
        }
    }

    @Override
    public int getProgress() {
        return 0; //throw new UnsupportedOperationException();
    }

    @Override
    public int getMaxProgress() {
        return 0; //throw new UnsupportedOperationException();
    }

    @Override
    public void increaseProgress(int progress) {
        //throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasWorkToDo() {
        return isActive();
    }

    @Override
    public void setWorkingEnabled(boolean workingEnabled) {
        this.workingEnabled = workingEnabled;
        if(!metaTileEntity.getWorld().isRemote) {
            metaTileEntity.markDirty();
        }
    }

    @Override
    public boolean isWorkingEnabled() {
        return workingEnabled;
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        if(dataId == 1) {
            this.isActive = buf.readBoolean();
            getMetaTileEntity().getHolder().scheduleChunkForRenderUpdate();
        }
    }

    @Override
    public void writeInitialData(PacketBuffer buf) {
        buf.writeBoolean(this.isActive);
    }

    @Override
    public void receiveInitialData(PacketBuffer buf) {
        this.isActive = buf.readBoolean();
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setBoolean("WorkEnabled", this.workingEnabled);
        compound.setInteger("RecipeDurationLeft", this.recipeDurationLeft);
        if(recipeDurationLeft > 0) {
            compound.setLong("RecipeOutputVoltage", this.recipeOutputVoltage);
        }
        return compound;
    }

    @Override
    public void deserializeNBT(NBTTagCompound compound) {
        if(!compound.hasKey("WorkEnabled", NBT.TAG_BYTE)) {
            //change working mode only if there is a tag compound with it's value
            this.workingEnabled = compound.getBoolean("WorkEnabled");
        }
        this.recipeDurationLeft = compound.getInteger("RecipeDurationLeft");
        if(recipeDurationLeft > 0) {
            this.recipeOutputVoltage = compound.getLong("RecipeOutputVoltage");
        }
        this.isActive = recipeDurationLeft > 0;
    }

}
