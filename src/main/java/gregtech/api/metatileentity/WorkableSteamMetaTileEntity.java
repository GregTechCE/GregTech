package gregtech.api.metatileentity;

import gregtech.api.GTValues;
import gregtech.api.capability.internal.IWorkable;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.Constants;

public abstract class WorkableSteamMetaTileEntity<T extends Recipe> extends SteamMetaTileEntity implements IWorkable {

    public final RecipeMap<T, ?> recipeMap;
    protected T previousRecipe;

    protected int progressTime;
    protected int maxProgressTime;
    protected int recipeEUt;

    protected NonNullList<ItemStack> itemOutputs;

    private boolean isActive;
    private boolean workingEnabled = true;

    public WorkableSteamMetaTileEntity(IMetaTileEntityFactory factory, RecipeMap<T, ?> recipeMap) {
        super(factory);
        this.recipeMap = recipeMap;
    }

    @Override
    public void onPostTick(long tickTimer) {
        super.onPostTick(tickTimer);
        if (getWorld().isRemote) {
            return;
        }
        if(progressTime == 0) {
            T pickedRecipe = recipeMap.findRecipe(holder, previousRecipe, true, GTValues.V[1], importItems, importFluids);
            if(pickedRecipe != null && setupAndConsumeRecipeInputs(pickedRecipe)) {
                if(pickedRecipe.canBeBuffered()) {
                    this.previousRecipe = pickedRecipe;
                } else this.previousRecipe = null;
                setupRecipe(pickedRecipe);
            }
        } else if(workingEnabled) {
            if(this.steamFluidTank.drain(this.recipeEUt, false) != null) {
                this.steamFluidTank.drain(this.recipeEUt, true);
                if(++progressTime >= maxProgressTime) {
                    completeRecipe();
                }
            }
        }
    }

    protected boolean setupAndConsumeRecipeInputs(T recipe) {
        int totalEUt = recipe.getEUt() * recipe.getDuration();
        return totalEUt >= 0 &&
            this.steamFluidTank.drain(totalEUt, false) != null &&
            addItemsToItemHandler(exportItems, true, recipe.getOutputs()) &&
            addFluidsToFluidHandler(exportFluids, true, recipe.getFluidOutputs()) &&
            recipe.isRecipeInputEqual(true, false, importItems, importFluids);
    }

    protected void setupRecipe(T recipe) {
        this.progressTime = 1;
        setMaxProgress(recipe.getDuration());
        this.recipeEUt = recipe.getEUt();
        this.itemOutputs = recipe.getOutputs();
        setActive(true);
    }

    protected void completeRecipe() {
        addItemsToItemHandler(exportItems, false, itemOutputs);
        this.progressTime = 0;
        setMaxProgress(0);
        this.recipeEUt = 0;
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
        if(!getWorld().isRemote) {
            markDirty();
            holder.writeCustomData(5, buf -> buf.writeInt(maxProgress));
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
        if(!getWorld().isRemote) {
            markDirty();
            holder.writeCustomData(4, buf -> buf.writeBoolean(active));
        }
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        switch (dataId) {
            case 4:
                this.isActive = buf.readBoolean();
                markBlockForRenderUpdate(); // FIXME this rerenders chunk every time recipe is completed
                break;
            case 5:
                this.maxProgressTime = buf.readInt();
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
            data.setTag("ItemOutputs", itemOutputsList);
        }
    }

    @Override
    public void loadNBTData(NBTTagCompound data) {
        super.loadNBTData(data);
        this.isActive = data.getBoolean("Active");
        this.workingEnabled = data.getBoolean("WorkEnabled");
        this.progressTime = data.getInteger("Progress");
        if (progressTime > 0) {
            this.maxProgressTime = data.getInteger("MaxProgress");
            this.recipeEUt = data.getInteger("RecipeEUt");
            NBTTagList itemOutputsList = data.getTagList("ItemOutputs", Constants.NBT.TAG_COMPOUND);
            this.itemOutputs = NonNullList.create();
            for(int i = 0; i < itemOutputsList.tagCount(); i++) {
                this.itemOutputs.add(new ItemStack(itemOutputsList.getCompoundTagAt(i)));
            }
        }
    }
}
