package gregtech.integration.multipart;

import gregtech.common.pipelike.fluidpipe.FluidPipeProperties;
import gregtech.common.pipelike.fluidpipe.FluidPipeType;
import net.minecraft.util.ResourceLocation;

public class FluidPipeMultiPart extends PipeMultiPart<FluidPipeType, FluidPipeProperties> {

    FluidPipeMultiPart() {
    }

    @Override
    public ResourceLocation getType() {
        return GTMultipartFactory.FLUID_PIPE_PART_KEY;
    }
}
