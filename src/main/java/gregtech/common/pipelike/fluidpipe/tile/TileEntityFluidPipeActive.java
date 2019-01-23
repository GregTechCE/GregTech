package gregtech.common.pipelike.fluidpipe.tile;

import gregtech.api.pipenet.tile.IPipeTile;
import gregtech.common.covers.CoverPump;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos.PooledMutableBlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

import java.util.function.Predicate;

public class TileEntityFluidPipeActive extends TileEntityFluidPipe implements ITickable {

    private static final Predicate<FluidStack> FLUID_FILTER_ALWAYS_TRUE = (fluid) -> true;
    private boolean isActive;

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public void update() {
        getCoverableImplementation().update();
        if(isActive) {
            pushFluidsFromTank(this);
        }
    }

    @Override
    public boolean supportsTicking() {
        return true;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setBoolean("ActiveNode", isActive);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.isActive = compound.getBoolean("ActiveNode");
    }

    public static void pushFluidsFromTank(IPipeTile<?, ?> pipeTile) {
        PooledMutableBlockPos blockPos = PooledMutableBlockPos.retain();
        for(EnumFacing side : EnumFacing.VALUES) {
            blockPos.setPos(pipeTile.getPipePos()).move(side);
            TileEntity tileEntity = pipeTile.getPipeWorld().getTileEntity(blockPos);
            IFluidHandler sourceHandler = pipeTile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side);
            IFluidHandler receiverHandler = tileEntity == null ? null : tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side.getOpposite());
            if(sourceHandler == null || receiverHandler == null) {
                continue;
            }
            CoverPump.moveHandlerFluids(sourceHandler, receiverHandler, Integer.MAX_VALUE, FLUID_FILTER_ALWAYS_TRUE);
        }
        blockPos.release();
    }

}
