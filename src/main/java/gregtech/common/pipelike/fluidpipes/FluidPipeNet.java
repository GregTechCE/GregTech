package gregtech.common.pipelike.fluidpipes;

import gregtech.api.worldentries.pipenet.PipeNet;
import gregtech.api.worldentries.pipenet.WorldPipeNet;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.capability.IFluidHandler;

import java.util.Collection;

public class FluidPipeNet extends PipeNet<TypeFluidPipe, FluidPipeProperties, IFluidHandler> {

    public FluidPipeNet(WorldPipeNet worldNet) {
        super(FluidPipeFactory.INSTANCE, worldNet);
    }

    @Override
    protected void transferNodeDataTo(Collection<? extends BlockPos> nodeToTransfer, PipeNet<TypeFluidPipe, FluidPipeProperties, IFluidHandler> toNet) {

    }

    @Override
    protected void removeData(BlockPos pos) {

    }
}
