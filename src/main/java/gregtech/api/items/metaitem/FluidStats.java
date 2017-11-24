package gregtech.api.items.metaitem;

import gregtech.api.items.metaitem.stats.IFluidStats;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.FluidStack;

public class FluidStats implements IFluidStats {

    public static final FluidStats EMPTY = new FluidStats(0, Integer.MIN_VALUE, Integer.MAX_VALUE);

    public final int maxCapacity;
    public final int minFluidTemperature;
    public final int maxFluidTemperature;

    public FluidStats(int maxCapacity, int minFluidTemperature, int maxFluidTemperature) {
        this.maxCapacity = maxCapacity;
        this.minFluidTemperature = minFluidTemperature;
        this.maxFluidTemperature = maxFluidTemperature;
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
