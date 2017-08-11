package gregtech.api.items.metaitem;

import gregtech.api.items.metaitem.stats.IFluidStats;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.FluidStack;

public class FluidStats implements IFluidStats {

    public static final FluidStats EMPTY = new FluidStats(0, Integer.MAX_VALUE, Integer.MAX_VALUE);

    public final int maxCapacity;
    public final int minFluidTemperature;
    public final int maxFluidTemperature;

    public FluidStats(int maxCapacity, int minFluidTemperature, int maxFluidTemperature) {
        this.maxCapacity = maxCapacity;
        this.minFluidTemperature = minFluidTemperature;
        this.maxFluidTemperature = maxFluidTemperature;
    }

    @Override
    public FluidStack getFluid(ItemStack container) {
        if(!container.hasTagCompound() || !container.getTagCompound().hasKey("GT.Fluid", Constants.NBT.TAG_COMPOUND))
            return null;
        return FluidStack.loadFluidStackFromNBT(container.getTagCompound().getCompoundTag("GT.Fluid"));
    }

    private void setFluid(ItemStack container, FluidStack fluidStack) {
        if(!container.hasTagCompound()) {
            container.setTagCompound(new NBTTagCompound());
        }
        if(fluidStack != null) {
            container.getTagCompound().setTag("GT.Fluid", fluidStack.writeToNBT(new NBTTagCompound()));
        } else {
            container.getTagCompound().removeTag("GT.Fluid");
        }
    }

    @Override
    public int getCapacity(ItemStack container) {
        return maxCapacity;
    }

    @Override
    public int fill(ItemStack container, FluidStack resource, boolean doFill) {
        int liquidTemperature = resource.getFluid().getTemperature(resource);
        if(minFluidTemperature <= liquidTemperature && maxFluidTemperature >= liquidTemperature && maxCapacity > 0) {
            FluidStack fluidInside = getFluid(container);
            if(fluidInside == null || fluidInside.isFluidEqual(resource)) {
                int canReceive = fluidInside == null ? maxCapacity : maxCapacity - fluidInside.amount;
                int filled = canReceive > resource.amount ? resource.amount : canReceive;
                if(filled > 0 && doFill) {
                    FluidStack fillStack = resource.copy();
                    fillStack.amount = fluidInside == null ? filled : fluidInside.amount + filled;
                    setFluid(container, fillStack);
                }
                return filled;
            }
        }

        return 0;
    }

    @Override
    public FluidStack drain(ItemStack container, int maxDrain, boolean doDrain) {
        FluidStack fluidInside = getFluid(container);
        if(fluidInside != null && maxDrain > 0 && maxCapacity > 0) {
            int drained = fluidInside.amount > maxDrain ? maxDrain : fluidInside.amount;
            if(drained > 0) {
                if(doDrain) {
                    fluidInside.amount -= drained;
                    setFluid(container, fluidInside.amount > 0 ? fluidInside : null);
                }
                FluidStack drainedStack = fluidInside.copy();
                drainedStack.amount = drained;
                return drainedStack;
            }
        }
        return null;
    }

}
