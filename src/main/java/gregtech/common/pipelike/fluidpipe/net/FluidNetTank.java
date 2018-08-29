package gregtech.common.pipelike.fluidpipe.net;

import com.google.common.base.Preconditions;
import gregtech.common.pipelike.fluidpipe.FluidPipeProperties;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class FluidNetTank extends FluidTank {

    private final FluidPipeNet handle;

    public FluidNetTank(FluidPipeNet handle) {
        super(0);
        this.handle = handle;
    }

    private int getMaxThroughput() {
        return handle.getMaxThroughput();
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        Preconditions.checkNotNull(resource, "resource");
        FluidStack copyStack = resource.copy();
        copyStack.amount = Math.min(copyStack.amount, getMaxThroughput());
        FluidPipeProperties properties = handle.getNodeData();
        boolean fakeFilled = false;
        if(copyStack.getFluid().isGaseous(copyStack) && !properties.gasProof) {
            if(doFill) {
                //only fire leaking in real fill event
                this.handle.markNodesAsLeaking(false);
            }
            fakeFilled = true;
        }
        if(copyStack.getFluid().getTemperature(copyStack) > properties.maxFluidTemperature) {
            if(doFill) {
                //only fire burning in real fill event
                this.handle.markNodesAsLeaking(true);
            }
            fakeFilled = true;
        }
        return fakeFilled ? copyStack.amount : super.fill(copyStack, doFill);
    }

    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        Preconditions.checkNotNull(resource, "resource");
        FluidStack copyStack = resource.copy();
        copyStack.amount = Math.min(copyStack.amount, getMaxThroughput());
        return super.drain(copyStack, doDrain);
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        maxDrain = Math.min(maxDrain, getMaxThroughput());
        return super.drain(maxDrain, doDrain);
    }

    public void updateTankCapacity(int newTankCapacity) {
        this.capacity = newTankCapacity;
        if(this.fluid != null) {
            this.fluid.amount = Math.min(this.fluid.amount, newTankCapacity);
        }
    }

}
