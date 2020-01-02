package gregtech.api.util;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public abstract class WatchedFluidTank extends FluidTank {

    private FluidStack oldFluidStack;

    public WatchedFluidTank(int capacity) {
        super(capacity);
        this.oldFluidStack = null;
    }

    @Override
    public final void onContentsChanged() {
        FluidStack newFluidStack = getFluid();
        if (hasFluidChanged(newFluidStack, oldFluidStack)) {
            onFluidChanged(newFluidStack, oldFluidStack);
            if (oldFluidStack != null && oldFluidStack.isFluidEqual(newFluidStack)) {
                //noinspection ConstantConditions
                oldFluidStack.amount = newFluidStack.amount;
            } else {
                this.oldFluidStack = newFluidStack == null ? null : newFluidStack.copy();
            }
        }
    }

    private static boolean hasFluidChanged(FluidStack newFluid, FluidStack oldFluid) {
        if (newFluid == null && oldFluid == null) {
            return false; //both fluid stacks are null - no changes
        } else if (newFluid == null || oldFluid == null) {
            return true; //one of fluid stacks is null, and other is not - content changed
        } else {
            //both fluid stacks are not null - compare by fluid type and amount
            return !newFluid.isFluidEqual(oldFluid) || newFluid.amount != oldFluid.amount;
        }
    }

    protected abstract void onFluidChanged(FluidStack newFluidStack, FluidStack oldFluidStack);

}
