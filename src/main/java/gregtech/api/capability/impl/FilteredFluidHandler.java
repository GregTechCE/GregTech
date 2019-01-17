package gregtech.api.capability.impl;

import gregtech.api.util.watch.WatchedFluidTank;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class FilteredFluidHandler extends WatchedFluidTank {

    private Predicate<FluidStack> fillPredicate;

    public FilteredFluidHandler(int capacity) {
        super(capacity);
    }

    public FilteredFluidHandler(@Nullable FluidStack fluidStack, int capacity) {
        super(fluidStack, capacity);
    }

    public FilteredFluidHandler(Fluid fluid, int amount, int capacity) {
        super(new FluidStack(fluid, amount), capacity);
    }

    public FilteredFluidHandler setFillPredicate(Predicate<FluidStack> predicate) {
        this.fillPredicate = predicate;
        return this;
    }

    @Override
    public boolean canFillFluidType(FluidStack fluid) {
        return canFill() && (fillPredicate == null || fillPredicate.test(fluid));
    }
}
