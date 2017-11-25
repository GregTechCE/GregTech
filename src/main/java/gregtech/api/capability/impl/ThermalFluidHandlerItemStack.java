package gregtech.api.capability.impl;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStackSimple;

import javax.annotation.Nonnull;

public class ThermalFluidHandlerItemStack extends FluidHandlerItemStackSimple {

    public final int minFluidTemperature;
    public final int maxFluidTemperature;

    /**
     * @param container The container itemStack, data is stored on it directly as NBT.
     * @param capacity  The maximum capacity of this fluid tank.
     */
    public ThermalFluidHandlerItemStack(@Nonnull ItemStack container, int capacity, int minFluidTemperature, int maxFluidTemperature) {
        super(container, capacity);
        this.minFluidTemperature = minFluidTemperature;
        this.maxFluidTemperature = maxFluidTemperature;
    }

    @Override
    public boolean canFillFluidType(FluidStack fluid) {
        int liquidTemperature = fluid.getFluid().getTemperature();
        return liquidTemperature >= minFluidTemperature && liquidTemperature <= maxFluidTemperature;
    }
}
