package gregtech.api.capability.internal;

import gregtech.api.gui.IUIHolder;
import net.minecraftforge.fluids.FluidStack;

public interface ISimpleFluidInventory extends IUIHolder {

    int getTanksCount();
    int getTankCapacity(int tankIndex);

    /**
     * @return a COPY of stack in slot. Actual stack won't change.
     */
    FluidStack getFluidInTank(int tankIndex);
    void setFluidInTank(int index, FluidStack fluidStack);
    boolean isValidFluidTank(int tankIndex);

}
