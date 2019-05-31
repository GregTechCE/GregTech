package gregtech.common.covers.filter;

import gregtech.api.gui.IUIHolder;
import gregtech.api.gui.widgets.AbstractWidgetGroup;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

public abstract class AbstractFluidFilter {

    private IUIHolder holder;

    public abstract boolean testFluid(FluidStack fluidStack);

    /**
     * @return total occupied height
     */
    public abstract int initUI(int y, AbstractWidgetGroup widgetGroup);

    public abstract void writeToNBT(NBTTagCompound tagCompound);

    public abstract void readFromNBT(NBTTagCompound tagCompound);

    public final void setHolder(IUIHolder holder) {
        this.holder = holder;
    }

    public IUIHolder getHolder() {
        return holder;
    }

    public final void markDirty() {
        if(holder != null) {
            holder.markAsDirty();
        }
    }
}
