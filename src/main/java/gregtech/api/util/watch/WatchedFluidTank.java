package gregtech.api.util.watch;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import javax.annotation.Nullable;
import java.util.function.BiConsumer;

public class WatchedFluidTank extends FluidTank {

    private FluidStack oldFluidStack;
    private BiConsumer<FluidStack, FluidStack> onFluidChanged;

    public WatchedFluidTank(int capacity) {
        super(capacity);
        this.oldFluidStack = null;
    }

    public WatchedFluidTank(@Nullable FluidStack fluidStack, int capacity) {
        super(fluidStack, capacity);
        this.oldFluidStack = fluidStack == null ? null : fluidStack.copy();
    }

    public BiConsumer<FluidStack, FluidStack> getOnFluidChanged() {
        return onFluidChanged;
    }

    public WatchedFluidTank setOnFluidChanged(BiConsumer<FluidStack, FluidStack> onFluidChanged) {
        this.onFluidChanged = onFluidChanged;
        return this;
    }

    @Override
    public final void onContentsChanged() {
        FluidStack newFluidStack = getFluid();
        if(hasFluidChanged(newFluidStack, oldFluidStack)) {
            if (getOnFluidChanged() != null)
                getOnFluidChanged().accept(newFluidStack, oldFluidStack);
            if(oldFluidStack != null && oldFluidStack.isFluidEqual(newFluidStack)) {
                //noinspection ConstantConditions
                oldFluidStack.amount = newFluidStack.amount;
            } else {
                this.oldFluidStack = newFluidStack == null ? null : newFluidStack.copy();
            }
        }
    }

    private static boolean hasFluidChanged(FluidStack newFluid, FluidStack oldFluid) {
        if(newFluid == null && oldFluid == null) {
            return false; //both fluid stacks are null - no changes
        } else if(newFluid == null || oldFluid == null) {
            return true; //one of fluid stacks is null, and other is not - content changed
        } else {
            //both fluid stacks are not null - compare by fluid type and amount
            return !newFluid.isFluidEqual(oldFluid) || newFluid.amount != oldFluid.amount;
        }
    }

}
