package gregtech.api.metatileentity;

import gregtech.api.GTValues;
import gregtech.api.capability.internal.IWorkable;
import gregtech.api.damagesources.DamageSources;
import gregtech.api.metatileentity.factory.WorkableSteamMetaTileEntityFactory;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.GTUtility;
import gregtech.api.util.XSTR;
import gregtech.common.blocks.machines.BlockMachine;
import gregtech.common.blocks.machines.BlockSteamMachine;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.Constants;

public abstract class WorkableSteamMetaTileEntity extends SteamMetaTileEntity implements IWorkable {

    public final RecipeMap<?> recipeMap;
    protected Recipe previousRecipe;

    protected int progressTime;
    protected int maxProgressTime;
    protected int recipeEUt;

    protected NonNullList<ItemStack> itemOutputs;

    private boolean isActive;
    private boolean workingEnabled = true;

    private EnumFacing ventingFace = EnumFacing.NORTH;
    private boolean needsVenting = false;

    public WorkableSteamMetaTileEntity(WorkableSteamMetaTileEntityFactory<? extends WorkableSteamMetaTileEntity> factory) {
        super(factory);
        this.recipeMap = factory.getRecipeMap();
    }

    @Override
    public void onPostTick(long tickTimer) {
        super.onPostTick(tickTimer);
        if (getWorld().isRemote) {
            return;
        }

        if (needsVenting) {
            BlockPos pos = getPos().offset(this.ventingFace);
            if (getTimer() % 10 == 0 && getWorld().getBlockState(pos).getCollisionBoundingBox(getWorld(), pos) == Block.NULL_AABB) {
                holder.writeCustomData(7, GTUtility::noop);
                getWorld()
                    .getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(pos), EntitySelectors.CAN_AI_TARGET)
                    .forEach(entity -> entity.attackEntityFrom(DamageSources.getHeatDamage(), 6));
                needsVenting = false;
            }
        }
        if(progressTime == 0) {
            Recipe pickedRecipe = recipeMap.findRecipe(previousRecipe, GTValues.V[1], importItems, importFluids);
            if(pickedRecipe != null && setupAndConsumeRecipeInputs(pickedRecipe)) {
                if(pickedRecipe.canBeBuffered()) {
                    this.previousRecipe = pickedRecipe;
                } else this.previousRecipe = null;
                setupRecipe(pickedRecipe);
            }
        } else if(workingEnabled && !needsVenting) {
            if(this.steamFluidTank.drain(this.recipeEUt, false) != null) {
                this.steamFluidTank.drain(this.recipeEUt, true);
                if(++progressTime >= maxProgressTime) {
                    completeRecipe();
                }
            }
        }
    }

    protected boolean setupAndConsumeRecipeInputs(Recipe recipe) {
        int totalEUt = recipe.getEUt() * recipe.getDuration();
        return totalEUt >= 0 &&
            this.steamFluidTank.drain(totalEUt, false) != null &&
            addItemsToItemHandler(exportItems, true, recipe.getOutputs()) &&
            addFluidsToFluidHandler(exportFluids, true, recipe.getFluidOutputs()) &&
            recipe.matches(true, false, importItems, importFluids);
    }

    protected void setupRecipe(Recipe recipe) {
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
        this.needsVenting = true;
    }

    @Override
    public boolean onScrewdriverRightClick(EnumFacing side, EntityPlayer player, EnumHand hand, float clickX, float clickY, float clickZ) {
        return false;
    }

    @Override
    public boolean onWrenchRightClick(EnumFacing side, EntityPlayer player, EnumHand hand, float clickX, float clickY, float clickZ) {
        if (this.getFrontFacing() != side) {
            setVentingFace(side);
        } //TODO SOUND
        return true;
    }

    @Override
    public boolean onRightClick(EnumFacing side, EntityPlayer player, EnumHand hand, float clickX, float clickY, float clickZ) {
        if (player instanceof EntityPlayerMP) {
            MetaTileEntityUIFactory.INSTANCE.openUI(this, (EntityPlayerMP) player);
        }
        return true;
    }

    public EnumFacing getVentingFace() {
        return ventingFace;
    }

    public void setVentingFace(EnumFacing facing) {
        this.ventingFace = facing;
        if(!getWorld().isRemote) {
            markDirty();
            holder.writeCustomData(6, buf -> buf.writeByte(facing.getIndex()));
        }
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
            case 6:
                this.ventingFace = EnumFacing.getFront(buf.readByte());
                markBlockForRenderUpdate();
                break;
            case 7:
                for (int i = 0; i < 8; i++) {
                    // TODO SOUND
                    getHolder().getWorld().spawnParticle(EnumParticleTypes.SMOKE_LARGE,
                        getPos().getX() - 0.5f + (new XSTR()).nextFloat(),
                        getPos().getY() - 0.5f + (new XSTR()).nextFloat(),
                        getPos().getZ() - 0.5f + (new XSTR()).nextFloat(),
                        this.ventingFace.getFrontOffsetX() / 5.0,
                        this.ventingFace.getFrontOffsetY() / 5.0,
                        this.ventingFace.getFrontOffsetZ() / 5.0);
                }
                break;
            default:
                super.receiveCustomData(dataId, buf);
        }
    }

    @Override
    public IBlockState getActualBlockState(IMetaTileEntity metaTileEntity, IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        state = super.getActualBlockState(metaTileEntity, state, worldIn, pos);
        if (metaTileEntity instanceof IWorkable) {
            state = state.withProperty(BlockMachine.ACTIVE, ((IWorkable) metaTileEntity).isActive());
        }

        if (metaTileEntity instanceof WorkableSteamMetaTileEntity) {
            state = state.withProperty(BlockSteamMachine.VENTING_FACE, ventingFace);
        }

        return state;
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
        data.setBoolean("NeedsVenting", this.needsVenting);
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
        this.needsVenting = data.getBoolean("NeedsVenting");
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
