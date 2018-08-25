package gregtech.api.capability.impl;

import gregtech.api.GTValues;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.IWorkable;
import gregtech.api.metatileentity.MTETrait;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.GTUtility;
import gregtech.api.util.XSTR;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.IItemHandlerModifiable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class RecipeMapWorkableHandler extends MTETrait implements IWorkable {

    public final RecipeMap<?> recipeMap;
    protected Recipe previousRecipe;

    protected int progressTime;
    protected int maxProgressTime;
    protected int recipeEUt;
    protected List<FluidStack> fluidOutputs;
    protected NonNullList<ItemStack> itemOutputs;
    protected final Random random = new XSTR();

    private boolean isActive;
    private boolean workingEnabled = true;
    private boolean hasNotEnoughEnergy;
    private boolean wasActiveAndNeedsUpdate;

    public RecipeMapWorkableHandler(MetaTileEntity tileEntity, RecipeMap<?> recipeMap) {
        super(tileEntity);
        this.recipeMap = recipeMap;
    }

    protected abstract long getEnergyStored();
    protected abstract long getEnergyCapacity();
    protected abstract boolean drawEnergy(int recipeEUt);
    protected abstract long getMaxVoltage();

    @Override
    public String getName() {
        return "RecipeMapWorkable";
    }

    @Override
    public Capability<?> getImplementingCapability() {
        return GregtechCapabilities.CAPABILITY_WORKABLE;
    }

    @Override
    public void update() {
        if (getMetaTileEntity().getWorld().isRemote)
            return;
        if(progressTime > 0 && workingEnabled) {
            boolean drawEnergy = drawEnergy(recipeEUt);
            if (drawEnergy || (recipeEUt < 0 && ignoreTooMuchEnergy())) {
                if (++progressTime >= maxProgressTime) {
                    completeRecipe();
                }
            } else {
                if (recipeEUt > 0) {
                    //only set hasNotEnoughEnergy if this recipe is consuming recipe
                    //generators always have enough energy
                    this.hasNotEnoughEnergy = true;
                }
            }
        }

        if(progressTime == 0 && workingEnabled) {
            long maxVoltage = getMaxVoltage();
            Recipe currentRecipe;
            if(previousRecipe != null && previousRecipe.matches(false,
                metaTileEntity.getImportItems(), metaTileEntity.getImportFluids())) {
                //if previous recipe still matches inputs, try to use it
                currentRecipe = previousRecipe;
            } else {
                //else, try searching new recipe for given inputs
                currentRecipe = findRecipe(maxVoltage, metaTileEntity.getImportItems(), metaTileEntity.getImportFluids());
                //if we found recipe that can be buffered, buffer it
                if(currentRecipe != null && currentRecipe.canBeBuffered()) {
                    this.previousRecipe = currentRecipe;
                }
            }
            if(currentRecipe != null && setupAndConsumeRecipeInputs(currentRecipe)) {
                setupRecipe(currentRecipe);
            }
        }

        if (wasActiveAndNeedsUpdate) {
            this.wasActiveAndNeedsUpdate = false;
            setActive(false);
        }
    }

    protected Recipe findRecipe(long maxVoltage, IItemHandlerModifiable inputs, IMultipleTankHandler fluidInputs) {
        return recipeMap.findRecipe(maxVoltage, inputs, fluidInputs);
    }

    protected boolean setupAndConsumeRecipeInputs(Recipe recipe) {
        int[] resultOverclock = calculateOverclock(recipe.getEUt(), getMaxVoltage(), recipeMap.getAmperage(), recipe.getDuration(), false);
        int totalEUt = resultOverclock[0] * resultOverclock[1];
        return (totalEUt >= 0 ? getEnergyStored() >= (totalEUt > getEnergyCapacity() / 2 ? resultOverclock[0] : totalEUt) :
            (ignoreTooMuchEnergy() || getEnergyStored() - resultOverclock[0] <= getEnergyCapacity())) &&
            (!recipe.needsEmptyOutput() || MetaTileEntity.isItemHandlerEmpty(metaTileEntity.getExportItems())) &&
            MetaTileEntity.addItemsToItemHandler(metaTileEntity.getExportItems(), true, recipe.getOutputs()) &&
            MetaTileEntity.addFluidsToFluidHandler(metaTileEntity.getExportFluids(), true, recipe.getFluidOutputs()) &&
            recipe.matches(true, metaTileEntity.getImportItems(), metaTileEntity.getImportFluids());
    }

    protected boolean ignoreTooMuchEnergy() {
        return false;
    }

    protected int[] calculateOverclock(int EUt, long voltage, long amperage, int duration, boolean consumeInputs) {
        boolean negativeEU = EUt < 0;
        int tier = GTUtility.getTierByVoltage(voltage);
        if(GTValues.V[tier] <= EUt || tier == 0)
            return new int[] {EUt, duration};
        if(negativeEU)
            EUt = -EUt;
        if (EUt <= 16) {
            int resultEUt = EUt * (1 << (tier - 1)) * (1 << (tier - 1));
            int resultDuration = duration / (1 << (tier - 1));
            return new int[] {negativeEU ? -resultEUt : resultEUt, resultDuration};
        } else {
            int resultEUt = EUt;
            int resultDuration = duration;
            while (resultEUt <= GTValues.V[tier - 1] * amperage) {
                resultEUt *= 4;
                resultDuration /= 2;
            }
            return new int[] {negativeEU ? -resultEUt : resultEUt, resultDuration};
        }
    }

    protected void setupRecipe(Recipe recipe) {
        int[] resultOverclock = calculateOverclock(recipe.getEUt(), getMaxVoltage(), recipeMap.getAmperage(), recipe.getDuration(), true);
        this.progressTime = 1;
        setMaxProgress(resultOverclock[1]);
        this.recipeEUt = resultOverclock[0];
        this.fluidOutputs = GTUtility.copyFluidList(recipe.getFluidOutputs());
        this.itemOutputs = GTUtility.copyStackList(recipe.getResultItemOutputs(random));
        if(this.wasActiveAndNeedsUpdate) {
            this.wasActiveAndNeedsUpdate = false;
        } else {
            this.setActive(true);
        }
    }

    protected void completeRecipe() {
        MetaTileEntity.addItemsToItemHandler(metaTileEntity.getExportItems(), false, itemOutputs);
        MetaTileEntity.addFluidsToFluidHandler(metaTileEntity.getExportFluids(), false, fluidOutputs);
        this.progressTime = 0;
        setMaxProgress(0);
        this.recipeEUt = 0;
        this.fluidOutputs = null;
        this.itemOutputs = null;
        this.hasNotEnoughEnergy = false;
        this.wasActiveAndNeedsUpdate = true;
    }

    public double getProgressPercent() {
        return getMaxProgress() == 0 ? 0.0 : getProgress() / (getMaxProgress() * 1.0);
    }

    public int getTicksTimeLeft() {
        return maxProgressTime == 0 ? 0 : (maxProgressTime - progressTime);
    }

    @Override
    public int getProgress() {
        return progressTime;
    }

    @Override
    public int getMaxProgress() {
        return maxProgressTime;
    }

    public int getRecipeEUt() {
        return recipeEUt;
    }

    public void setMaxProgress(int maxProgress) {
        this.maxProgressTime = maxProgress;
        if(!metaTileEntity.getWorld().isRemote) {
            metaTileEntity.markDirty();
            writeCustomData(0, buf -> buf.writeInt(maxProgress));
        }
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
    public void increaseProgress(int progress) {
        this.progressTime = Math.min(progressTime + progress, maxProgressTime);
        if(!metaTileEntity.getWorld().isRemote) {
            metaTileEntity.markDirty();
        }
    }

    @Override
    public boolean hasWorkToDo() {
        return progressTime > 0;
    }

    @Override
    public void setWorkingEnabled(boolean workingEnabled) {
        this.workingEnabled = workingEnabled;
        if(!metaTileEntity.getWorld().isRemote) {
            metaTileEntity.markDirty();
        }
    }

    public boolean isHasNotEnoughEnergy() {
        return hasNotEnoughEnergy;
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
        if(dataId == 0) {
            this.maxProgressTime = buf.readInt();
        } else if(dataId == 1) {
            this.isActive = buf.readBoolean();
            getMetaTileEntity().getHolder().scheduleChunkForRenderUpdate();
        }
    }

    @Override
    public void writeInitialData(PacketBuffer buf) {
        buf.writeInt(this.maxProgressTime);
        buf.writeBoolean(this.isActive);
    }

    @Override
    public void receiveInitialData(PacketBuffer buf) {
        this.maxProgressTime = buf.readInt();
        this.isActive = buf.readBoolean();
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setBoolean("WorkEnabled", this.workingEnabled);
        if(progressTime > 0) {
            compound.setInteger("Progress", progressTime);
            compound.setInteger("MaxProgress", maxProgressTime);
            compound.setInteger("RecipeEUt", this.recipeEUt);
            NBTTagList itemOutputsList = new NBTTagList();
            for(ItemStack itemOutput : itemOutputs) {
                itemOutputsList.appendTag(itemOutput.writeToNBT(new NBTTagCompound()));
            }
            NBTTagList fluidOutputsList = new NBTTagList();
            for(FluidStack fluidOutput : fluidOutputs) {
                fluidOutputsList.appendTag(fluidOutput.writeToNBT(new NBTTagCompound()));
            }
            compound.setTag("ItemOutputs", itemOutputsList);
            compound.setTag("FluidOutputs", fluidOutputsList);
        }
        return compound;
    }

    @Override
    public void deserializeNBT(NBTTagCompound compound) {
        this.workingEnabled = compound.getBoolean("WorkEnabled");
        this.progressTime = compound.getInteger("Progress");
        this.isActive = false;
        if (progressTime > 0) {
            this.isActive = true;
            this.maxProgressTime = compound.getInteger("MaxProgress");
            this.recipeEUt = compound.getInteger("RecipeEUt");
            NBTTagList itemOutputsList = compound.getTagList("ItemOutputs", Constants.NBT.TAG_COMPOUND);
            this.itemOutputs = NonNullList.create();
            for(int i = 0; i < itemOutputsList.tagCount(); i++) {
                this.itemOutputs.add(new ItemStack(itemOutputsList.getCompoundTagAt(i)));
            }
            NBTTagList fluidOutputsList = compound.getTagList("FluidOutputs", Constants.NBT.TAG_COMPOUND);
            this.fluidOutputs = new ArrayList<>();
            for(int i = 0; i < fluidOutputsList.tagCount(); i++) {
                this.fluidOutputs.add(FluidStack.loadFluidStackFromNBT(fluidOutputsList.getCompoundTagAt(i)));
            }
        }
    }

}
