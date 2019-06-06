package gregtech.common.covers.filter;

import gregtech.api.gui.IUIHolder;
import gregtech.api.gui.Widget;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

import java.util.function.Consumer;

public abstract class AbstractFluidFilter {

    private IUIHolder holder;

    public abstract boolean testFluid(FluidStack fluidStack);

    public abstract int getMaxOccupiedHeight();

    public abstract void initUI(int y, Consumer<Widget> widgetGroup);

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
