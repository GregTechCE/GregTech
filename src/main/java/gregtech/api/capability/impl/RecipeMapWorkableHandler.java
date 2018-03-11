package gregtech.api.capability.impl;

import gregtech.api.capability.IWorkable;
import gregtech.api.metatileentity.MTETrait;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public abstract class RecipeMapWorkableHandler extends MTETrait implements IWorkable {

    public final RecipeMap<?> recipeMap;
    protected Recipe previousRecipe;

    protected int progressTime;
    protected int maxProgressTime;
    protected int recipeEUt;
    protected List<FluidStack> fluidOutputs;
    protected NonNullList<ItemStack> itemOutputs;

    private boolean isActive;
    private boolean workingEnabled = true;

    public RecipeMapWorkableHandler(RecipeMap<?> recipeMap) {
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
        return IWorkable.CAPABILITY_WORKABLE;
    }

    @Override
    public void update() {
        if (getMetaTileEntity().getWorld().isRemote)
            return;
        if(progressTime == 0) {
            long maxVoltage = getMaxVoltage();
            Recipe pickedRecipe = recipeMap.findRecipe(previousRecipe, maxVoltage, metaTileEntity.getImportItems(), metaTileEntity.getImportFluids());
            if(pickedRecipe != null && setupAndConsumeRecipeInputs(pickedRecipe)) {
                if(pickedRecipe.canBeBuffered()) {
                    this.previousRecipe = pickedRecipe;
                } else this.previousRecipe = null;
                setupRecipe(pickedRecipe);
            }
        } else if(workingEnabled) {
            if(drawEnergy(recipeEUt)) {
                if(++progressTime >= maxProgressTime) {
                    completeRecipe();
                }
            }
        }
    }

    protected boolean setupAndConsumeRecipeInputs(Recipe recipe) {
        int totalEUt = recipe.getEUt() * recipe.getDuration();
        return (totalEUt >= 0 ? getEnergyStored() >= totalEUt : getEnergyStored() - totalEUt <= getEnergyCapacity()) &&
            MetaTileEntity.addItemsToItemHandler(metaTileEntity.getExportItems(), true, recipe.getOutputs()) &&
            MetaTileEntity.addFluidsToFluidHandler(metaTileEntity.getExportFluids(), true, recipe.getFluidOutputs()) &&
            recipe.matches(true, false, metaTileEntity.getImportItems(), metaTileEntity.getImportFluids());
    }

    protected void setupRecipe(Recipe recipe) {
        this.progressTime = 1;
        setMaxProgress(recipe.getDuration());
        this.recipeEUt = recipe.getEUt();
        this.fluidOutputs = recipe.getFluidOutputs();
        this.itemOutputs = recipe.getOutputs();
        setActive(true);
    }

    protected void completeRecipe() {
        MetaTileEntity.addItemsToItemHandler(metaTileEntity.getExportItems(), false, itemOutputs);
        MetaTileEntity.addFluidsToFluidHandler(metaTileEntity.getExportFluids(), false, fluidOutputs);
        this.progressTime = 0;
        setMaxProgress(0);
        this.recipeEUt = 0;
        this.fluidOutputs = null;
        this.itemOutputs = null;
        setActive(false);
    }

    @Override
    public int getProgress() {
        return progressTime;
    }

    @Override
    public int getMaxProgress() {
        return maxProgressTime;
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
    }

    @Override
    public boolean hasWorkToDo() {
        return progressTime > 0;
    }

    @Override
    public void setWorkingEnabled(boolean workingEnabled) {
        this.workingEnabled = workingEnabled;
        metaTileEntity.markDirty();
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
        compound.setBoolean("Active", this.isActive);
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
        this.isActive = compound.getBoolean("Active");
        this.workingEnabled = compound.getBoolean("WorkEnabled");
        this.progressTime = compound.getInteger("Progress");
        if (progressTime > 0) {
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
