package gregtech.api.util.watch;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidTank;

import javax.annotation.Nullable;

public class WatchedFluidTankWrapper extends WatchedFluidTank {
    private IFluidTank delegate;

    public WatchedFluidTankWrapper(IFluidTank delegate) {
        super(delegate.getCapacity());
        this.delegate = delegate;
    }

    @Override
    @Nullable
    public FluidStack getFluid() {
        return delegate.getFluid();
    }

    @Override
    public int getFluidAmount() {
        return delegate.getFluidAmount();
    }

    @Override
    public int getCapacity() {
        return delegate.getCapacity();
    }

    @Override
    public FluidTankInfo getInfo() {
        return delegate.getInfo();
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        int fill = delegate.fill(resource, doFill);
        if (doFill)
            onContentsChanged();
        return fill;
    }

    @Override
    @Nullable
    public FluidStack drain(int maxDrain, boolean doDrain) {
        FluidStack drain = delegate.drain(maxDrain, doDrain);
        if (doDrain)
            onContentsChanged();
        return drain;
    }
}
