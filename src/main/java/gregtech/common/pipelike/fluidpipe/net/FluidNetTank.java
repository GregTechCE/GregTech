package gregtech.common.pipelike.fluidpipe.net;

import com.google.common.base.Preconditions;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import javax.annotation.Nullable;

public class FluidNetTank extends FluidTank {

    private final FluidPipeNet handle;

    public FluidNetTank(FluidPipeNet handle) {
        super(0);
        this.handle = handle;
    }

    @Override
    public boolean canFillFluidType(FluidStack fluid) {
        return handle.getNodeData().maxFluidTemperature >= fluid.getFluid().getTemperature();
    }

    private int getMaxThroughput() {
        return handle.getMaxThroughput();
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        Preconditions.checkNotNull(resource, "resource");
        FluidStack copyStack = resource.copy();
        copyStack.amount = Math.min(copyStack.amount, getMaxThroughput());
        return super.fill(copyStack, doFill);
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
