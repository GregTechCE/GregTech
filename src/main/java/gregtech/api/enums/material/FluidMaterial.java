package gregtech.api.enums.material;

import net.minecraftforge.fluids.FluidStack;

public interface FluidMaterial {

    default boolean hasFluid() {
        return getFluid(1) != null;
    }

    default boolean hasPlasma() {
        return getPlasma(1) != null;
    }

    FluidStack getFluid(int amount);
    FluidStack getPlasma(int amount);

}
