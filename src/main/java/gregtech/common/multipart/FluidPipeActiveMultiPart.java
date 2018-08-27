package gregtech.common.multipart;

import gregtech.common.pipelike.fluidpipe.FluidPipeProperties;
import gregtech.common.pipelike.fluidpipe.FluidPipeType;
import gregtech.common.pipelike.fluidpipe.tile.TileEntityFluidPipeActive;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;

public class FluidPipeActiveMultiPart extends FluidPipeMultiPart implements ITickable {

    FluidPipeActiveMultiPart() {
    }

    public FluidPipeActiveMultiPart(PipeMultiPart<FluidPipeType, FluidPipeProperties> sourceTile) {
        super(sourceTile);
    }

    public FluidPipeActiveMultiPart(IBlockState blockState, TileEntity tile) {
        super(blockState, tile);
    }

    @Override
    public ResourceLocation getType() {
        return GTMultipartFactory.FLUID_PIPE_ACTIVE_PART_KEY;
    }

    @Override
    public void update() {
        TileEntityFluidPipeActive.pushFluidsFromTank(this, getFluidHandler());
    }
}
