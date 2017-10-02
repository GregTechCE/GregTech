package gregtech.api.capability;


import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nullable;

public interface IMultipleTankHandler extends IFluidHandler {

    int getTanks();

    @Nullable
    FluidStack getFluidInTank(int tank);

    void setFluidInTank(int tank, @Nullable FluidStack stack);
}
