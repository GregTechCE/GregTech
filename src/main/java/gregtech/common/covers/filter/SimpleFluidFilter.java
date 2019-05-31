package gregtech.common.covers.filter;

import gregtech.api.gui.widgets.AbstractWidgetGroup;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

public class SimpleFluidFilter extends AbstractFluidFilter {

    @Override
    public boolean testFluid(FluidStack fluidStack) {
        return false;
    }

    @Override
    public int initUI(int y, AbstractWidgetGroup widgetGroup) {
        return 0;
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {

    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {

    }
}
