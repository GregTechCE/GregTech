package gregtech.api.metatileentity;

import gregtech.api.capability.IWorkable;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public abstract class WorkableMetaTileEntity<T extends Recipe> extends TieredMetaTileEntity implements IWorkable {

    public final RecipeMap<T, ?> recipeMap;
    protected T previousRecipe;

    protected int progressTime;
    protected int maxProgressTime;
    protected int recipeEUt;
    protected List<FluidStack> fluidOutputs;
    protected List<ItemStack> itemOutputs;

    private boolean isActive;
    private boolean workingEnabled;

    public WorkableMetaTileEntity(IMetaTileEntityFactory factory, int tier, RecipeMap<T, ?> recipeMap) {
        super(factory, tier);
        this.recipeMap = recipeMap;
    }

    @Override
    public <R> boolean hasCapability(Capability<R> capability, EnumFacing side) {
        return super.hasCapability(capability, side) || capability == IWorkable.CAPABILITY_WORKABLE;
    }

    @Override
    public <R> R getCapability(Capability<R> capability, EnumFacing side) {
        if(capability == IWorkable.CAPABILITY_WORKABLE) {
            return IWorkable.CAPABILITY_WORKABLE.cast(this);
        }
        return super.getCapability(capability, side);
    }

    @Override
    public void onPostTick(long tickTimer) {
        super.onPostTick(tickTimer);
        if(progressTime == 0) {
            long maxVoltage = Math.max(getInputVoltage(), getOutputVoltage());
            T pickedRecipe = recipeMap.findRecipe(holder, previousRecipe, true, maxVoltage, fluidInventory, itemInventory);
            if(pickedRecipe != null && setupAndConsumeRecipeInputs(pickedRecipe)) {
                if(pickedRecipe.canBeBuffered()) {
                    this.previousRecipe = pickedRecipe;
                } else this.previousRecipe = null;
                setupRecipe(pickedRecipe);
            }
        } else if(workingEnabled) {
            if(getEnergyStored() >= recipeEUt) {
                setEnergyStored(getEnergyStored() - recipeEUt);
                if(++progressTime >= maxProgressTime) {
                    completeRecipe();
                }
            }
        }
    }

    protected boolean setupAndConsumeRecipeInputs(T recipe) {
        int totalEUt = recipe.getEUt() * recipe.getDuration();
        return (totalEUt >= 0 ? getEnergyStored() >= totalEUt : getEnergyStored() - totalEUt <= getEnergyCapacity()) &&
                recipe.isRecipeInputEqual(true, fluidInventory, itemInventory) &&
                addItemsToMachine(true, recipe.getOutputs()) &&
                addFluidsToMachine(true, recipe.getFluidOutputs());
    }

    protected void setupRecipe(T recipe) {
        this.progressTime = 1;
        this.maxProgressTime = recipe.getDuration();
        this.recipeEUt = recipe.getEUt();
        this.fluidOutputs = recipe.getFluidOutputs();
        this.itemOutputs = recipe.getOutputs();
        setActive(true);
    }

    protected void completeRecipe() {
        addItemsToMachine(false, itemOutputs);
        addFluidsToMachine(false, fluidOutputs);
        this.progressTime = 0;
        this.maxProgressTime = 0;
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

    @Override
    public void increaseProgress(int progress) {
        this.progressTime = Math.min(progressTime + progress, maxProgressTime);
    }

    @Override
    public boolean hasWorkToDo() {
        return progressTime > 0;
    }

    @Override
    public void enableWorking() {
        this.workingEnabled = true;
        markDirty();
    }

    @Override
    public void disableWorking() {
        this.workingEnabled = false;
        markDirty();
    }

    @Override
    public boolean isAllowedToWork() {
        return workingEnabled;
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public void setActive(boolean active) {
        this.isActive = active;

    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        switch (dataId) {
            case 5:
                this.isActive = buf.readBoolean();
                break;
            default:
                super.receiveCustomData(dataId, buf);
        }
    }

    @Override
    public void writeInitialData(PacketBuffer buf) {
        super.writeInitialData(buf);
        buf.writeBoolean(isActive);
    }

    @Override
    public void receiveInitialData(PacketBuffer buf) {
        super.receiveInitialData(buf);
        this.isActive = buf.readBoolean();
    }

    @Override
    public void saveNBTData(NBTTagCompound data) {
        super.saveNBTData(data);
        data.setBoolean("Active", this.isActive);
        data.setBoolean("WorkEnabled", this.workingEnabled);
        if(progressTime > 0) {
            data.setInteger("Progress", progressTime);
            data.setInteger("MaxProgress", maxProgressTime);
            data.setInteger("RecipeEUt", this.recipeEUt);
            NBTTagList itemOutputsList = new NBTTagList();
            for(ItemStack itemOutput : itemOutputs) {
                itemOutputsList.appendTag(itemOutput.writeToNBT(new NBTTagCompound()));
            }
            NBTTagList fluidOutputsList = new NBTTagList();
            for(FluidStack fluidOutput : fluidOutputs) {
                fluidOutputsList.appendTag(fluidOutput.writeToNBT(new NBTTagCompound()));
            }
            data.setTag("ItemOutputs", itemOutputsList);
            data.setTag("FluidOutputs", fluidOutputsList);
        }
    }

    @Override
    public void loadNBTData(NBTTagCompound data) {
        super.loadNBTData(data);
        this.isActive = data.getBoolean("Active");
        this.workingEnabled = data.getBoolean("WorkEnabled");
        if(progressTime > 0) {
            this.progressTime = data.getInteger("Progress");
            this.maxProgressTime = data.getInteger("MaxProgress");
            this.recipeEUt = data.getInteger("RecipeEUt");
            NBTTagList itemOutputsList = data.getTagList("ItemOutputs", Constants.NBT.TAG_COMPOUND);
            this.itemOutputs = new ArrayList<>();
            for(int i = 0; i < itemOutputsList.tagCount(); i++) {
                this.itemOutputs.add(ItemStack.loadItemStackFromNBT(itemOutputsList.getCompoundTagAt(i)));
            }
            NBTTagList fluidOutputsList = data.getTagList("FluidOutputs", Constants.NBT.TAG_COMPOUND);
            this.fluidOutputs = new ArrayList<>();
            for(int i = 0; i < fluidOutputsList.tagCount(); i++) {
                this.fluidOutputs.add(FluidStack.loadFluidStackFromNBT(fluidOutputsList.getCompoundTagAt(i)));
            }
        }
    }

}
