package gregtech.api.items.metaitem;

import gregtech.api.items.metaitem.stats.IFluidStats;
import net.minecraft.item.ItemStack;

public class FluidStats implements IFluidStats {

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
    public boolean allowPartiallyFilled() {
        return allowPartlyFill;
    }

    @Override
    public int getCapacity(ItemStack container) {
        return maxCapacity;
    }

    @Override
    public int getMinFluidTemperature(ItemStack container) {
        return minFluidTemperature;
    }

    @Override
    public int getMaxFluidTemperature(ItemStack container) {
        return maxFluidTemperature;
    }
}
