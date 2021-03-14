package gregtech.api.capability;

import net.minecraftforge.fluids.FluidStack;

import java.util.function.Predicate;

public interface IFluidVoiding {

    int voidingAmount();

    Predicate<FluidStack> checkInputFluid();
}
