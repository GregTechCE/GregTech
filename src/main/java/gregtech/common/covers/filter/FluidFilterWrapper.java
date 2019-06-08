package gregtech.common.covers.filter;

import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.ToggleButtonWidget;
import gregtech.api.util.IDirtyNotifiable;
import net.minecraftforge.fluids.FluidStack;

import java.util.function.Consumer;

public class FluidFilterWrapper {

    private final IDirtyNotifiable dirtyNotifiable;
    private boolean isBlacklistFilter = false;
    private AbstractFluidFilter currentFluidFilter;

    public FluidFilterWrapper(IDirtyNotifiable dirtyNotifiable) {
        this.dirtyNotifiable = dirtyNotifiable;
    }

    public void initUI(int y, Consumer<Widget> widgetGroup) {
        widgetGroup.accept(new ToggleButtonWidget(146, y, 20, 20, GuiTextures.BUTTON_BLACKLIST,
            this::isBlacklistFilter, this::setBlacklistFilter).setTooltipText("cover.filter.blacklist"));
        widgetGroup.accept(new WidgetGroupFluidFilter(y, this::getFluidFilter));
    }

    public void setFluidFilter(AbstractFluidFilter fluidFilter) {
        this.currentFluidFilter = fluidFilter;
        if(currentFluidFilter != null) {
            currentFluidFilter.setDirtyNotifiable(dirtyNotifiable);
        }
    }

    public AbstractFluidFilter getFluidFilter() {
        return currentFluidFilter;
    }

    public void onFilterInstanceChange() {
        dirtyNotifiable.markAsDirty();
    }

    public void setBlacklistFilter(boolean blacklistFilter) {
        isBlacklistFilter = blacklistFilter;
        dirtyNotifiable.markAsDirty();
    }

    public boolean isBlacklistFilter() {
        return isBlacklistFilter;
    }

    public boolean testFluidStack(FluidStack fluidStack) {
        boolean result = true;
        if(currentFluidFilter != null) {
            result = currentFluidFilter.testFluid(fluidStack);
        }
        if(isBlacklistFilter) {
            result = !result;
        }
        return result;
    }
}
