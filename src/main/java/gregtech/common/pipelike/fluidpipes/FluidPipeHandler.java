package gregtech.common.pipelike.fluidpipes;

import gregtech.api.pipelike.ITilePipeLike;
import gregtech.common.pipelike.fluidpipes.pipenet.FluidPipeNet;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import javax.annotation.Nullable;

public class FluidPipeHandler implements IFluidHandler {

    final ITilePipeLike<TypeFluidPipe, FluidPipeProperties> tile;
    EnumFacing facing = null;

    public FluidPipeHandler(ITilePipeLike<TypeFluidPipe, FluidPipeProperties> tile) {
        this.tile = tile;
    }

    @Override
    public IFluidTankProperties[] getTankProperties() {
        FluidPipeNet net = getPipeNet();
        return net == null ? FluidPipeNet.EMPTY : net.getTankProperties(tile.getTilePos(), facing);
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        if (resource == null || resource.amount <= 0) return 0;
        FluidPipeNet net = getPipeNet();
        return net == null ? 0 : net.fill(tile.getTilePos(), facing, resource, doFill);
    }

    @Nullable
    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        if (resource == null || resource.amount <= 0) return null;
        FluidPipeNet net = getPipeNet();
        return net == null ? null : net.drain(tile.getTilePos(), facing, resource, doDrain);
    }

    @Nullable
    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        if (maxDrain <= 0) return null;
        FluidPipeNet net = getPipeNet();
        return net == null ? null : net.drain(tile.getTilePos(), facing, maxDrain, doDrain);
    }

    private FluidPipeNet getPipeNet() {
        return FluidPipeFactory.INSTANCE.getPipeNetAt(tile);
    }

    static class SidedHandler extends FluidPipeHandler {
        SidedHandler (FluidPipeHandler handler, EnumFacing facing) {
            super(handler.tile);
            this.facing = facing;
        }
    }
}
