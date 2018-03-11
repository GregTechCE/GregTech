package gregtech.api.capability.impl;

import gregtech.api.damagesources.DamageSources;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.XSTR;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.IFluidTank;

public class SteamRecipeMapWorkableHandler extends RecipeMapWorkableHandler {

    private final IFluidTank steamFluidTank;
    private final double conversionRate; //energy units per millibucket
    private final long maxVoltage;

    private boolean needsVenting;
    private EnumFacing ventingSide;

    public SteamRecipeMapWorkableHandler(RecipeMap<?> recipeMap, long maxVoltage, IFluidTank steamFluidTank, double conversionRate) {
        super(recipeMap);
        this.steamFluidTank = steamFluidTank;
        this.conversionRate = conversionRate;
        this.maxVoltage = maxVoltage;
        this.ventingSide = EnumFacing.NORTH;
    }

    public boolean isNeedsVenting() {
        return needsVenting;
    }

    public EnumFacing getVentingSide() {
        return ventingSide;
    }

    public void setVentingSide(EnumFacing ventingSide) {
        this.ventingSide = ventingSide;
    }

    protected void tryDoVenting() {
        BlockPos machinePos = metaTileEntity.getPos();
        BlockPos ventingBlockPos = machinePos.offset(ventingSide);
        IBlockState blockOnPos = metaTileEntity.getWorld().getBlockState(ventingBlockPos);
        if(blockOnPos.getCollisionBoundingBox(metaTileEntity.getWorld(), ventingBlockPos) == Block.NULL_AABB) {
            metaTileEntity.getWorld()
                .getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(ventingBlockPos), EntitySelectors.CAN_AI_TARGET)
                .forEach(entity -> entity.attackEntityFrom(DamageSources.getHeatDamage(), 6.0f));
            metaTileEntity.getWorld().spawnParticle(EnumParticleTypes.SMOKE_LARGE,
                machinePos.getX() - 0.5f + (new XSTR()).nextFloat(),
                machinePos.getY() - 0.5f + (new XSTR()).nextFloat(),
                machinePos.getZ() - 0.5f + (new XSTR()).nextFloat(),
                ventingSide.getFrontOffsetX() / 5.0,
                ventingSide.getFrontOffsetY() / 5.0,
                ventingSide.getFrontOffsetZ() / 5.0);
            //TODO some good sound for venting
            this.needsVenting = false;
        }
    }

    @Override
    public void update() {
        if (this.needsVenting && metaTileEntity.getTimer() % 10 == 0) {
            tryDoVenting();
        }
        super.update();
    }

    @Override
    protected boolean setupAndConsumeRecipeInputs(Recipe recipe) {
        return !this.needsVenting && super.setupAndConsumeRecipeInputs(recipe);
    }

    @Override
    protected void completeRecipe() {
        super.completeRecipe();
        this.needsVenting = true;
    }

    @Override
    protected long getEnergyStored() {
        return (long) Math.ceil(steamFluidTank.getFluidAmount() * conversionRate);
    }

    @Override
    protected long getEnergyCapacity() {
        return (long) Math.floor(steamFluidTank.getCapacity() * conversionRate);
    }

    @Override
    protected boolean drawEnergy(int recipeEUt) {
        int resultDraw = (int) Math.floor(recipeEUt / conversionRate);
        return resultDraw >= 0 && steamFluidTank.getFluidAmount() >= resultDraw &&
            steamFluidTank.drain(resultDraw, true) != null;
    }

    @Override
    protected long getMaxVoltage() {
        return maxVoltage;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = super.serializeNBT();
        compound.setInteger("VentingSide", ventingSide.getIndex());
        compound.setBoolean("NeedsVenting", needsVenting);
        return compound;
    }

    @Override
    public void deserializeNBT(NBTTagCompound compound) {
        super.deserializeNBT(compound);
        this.ventingSide = EnumFacing.VALUES[compound.getInteger("VentingSide")];
        this.needsVenting = compound.getBoolean("NeedsVenting");
    }
}
