package gregtech.api.capability.impl;

import net.minecraftforge.fluids.FluidStack;

/**
 * Fluid Fuel information
 */
public class FluidFuelInfo extends AbstractFuelInfo {

    private final FluidStack fluidStack;

    public FluidFuelInfo(FluidStack fluidStack, int fuelRemaining, int fuelCapacity, int fuelBurnTime) {
        super(fuelRemaining, fuelCapacity, fuelBurnTime);
        this.fluidStack = fluidStack;
    }

    public String getFuelName() {
        return fluidStack.getLocalizedName();
    }
}
