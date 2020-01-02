package gregtech.api.items.metaitem;

import gregtech.api.capability.impl.SimpleThermalFluidHandlerItemStack;
import gregtech.api.capability.impl.ThermalFluidHandlerItemStack;
import gregtech.api.items.metaitem.stats.IItemCapabilityProvider;
import gregtech.api.items.metaitem.stats.IItemComponent;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class FluidStats implements IItemComponent, IItemCapabilityProvider {

    public final int maxCapacity;
    public final int minFluidTemperature;
    public final int maxFluidTemperature;
    public final boolean allowPartlyFill;

    public FluidStats(int maxCapacity, int minFluidTemperature, int maxFluidTemperature, boolean allowPartlyFill) {
        this.maxCapacity = maxCapacity;
        this.minFluidTemperature = minFluidTemperature;
        this.maxFluidTemperature = maxFluidTemperature;
        this.allowPartlyFill = allowPartlyFill;
    }

    @Override
    public ICapabilityProvider createProvider(ItemStack itemStack) {
        if (allowPartlyFill) {
            return new ThermalFluidHandlerItemStack(itemStack, maxCapacity, minFluidTemperature, maxFluidTemperature);
        }
        return new SimpleThermalFluidHandlerItemStack(itemStack, maxCapacity, minFluidTemperature, maxFluidTemperature);
    }
}
