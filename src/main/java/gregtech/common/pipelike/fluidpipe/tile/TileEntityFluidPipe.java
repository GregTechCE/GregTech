package gregtech.common.pipelike.fluidpipe.tile;

import gregtech.api.pipenet.tile.TileEntityPipeBase;
import gregtech.common.pipelike.fluidpipe.LeakableFluidPipeTile;
import gregtech.common.pipelike.fluidpipe.BlockFluidPipe;
import gregtech.common.pipelike.fluidpipe.FluidPipeProperties;
import gregtech.common.pipelike.fluidpipe.FluidPipeType;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nullable;

public class TileEntityFluidPipe extends TileEntityPipeBase<FluidPipeType, FluidPipeProperties> implements LeakableFluidPipeTile {

    private IFluidHandler fluidHandler;

    protected IFluidHandler getFluidHandler() {
        if(fluidHandler == null) {
            this.fluidHandler = new FluidPipeFluidHandler(this);
        }
        return fluidHandler;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(getFluidHandler());
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void markAsBurning() {
        getWorld().setBlockState(getPos(), getBlockState().withProperty(BlockFluidPipe.IS_MELTING, true));
        getWorld().scheduleUpdate(getPos(), getPipeBlock(), 60 + world.rand.nextInt(80));
    }

    @Override
    public void markAsLeaking() {
        getWorld().setBlockState(getPos(), getBlockState().withProperty(BlockFluidPipe.IS_GAS_LEAKING, true));
        getWorld().scheduleUpdate(getPos(), getPipeBlock(), 80 + world.rand.nextInt(120));
    }
}
