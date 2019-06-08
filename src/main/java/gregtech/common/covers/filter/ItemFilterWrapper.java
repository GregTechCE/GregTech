package gregtech.common.covers.filter;

import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.ServerWidgetGroup;
import gregtech.api.gui.widgets.ToggleButtonWidget;
import net.minecraft.item.ItemStack;

import java.util.function.Consumer;

public class ItemFilterWrapper {

    private boolean isBlacklistFilter = false;
    private int maxStackSize = 1;
    private AbstractItemFilter currentItemFilter;

    public void initUI(int y, Consumer<Widget> widgetGroup) {
        ServerWidgetGroup blacklistButton = new ServerWidgetGroup(() -> getItemFilter() != null);
        blacklistButton.addWidget(new ToggleButtonWidget(146, y, 20, 20, GuiTextures.BUTTON_BLACKLIST,
            this::isBlacklistFilter, this::setBlacklistFilter).setTooltipText("cover.filter.blacklist"));
        widgetGroup.accept(blacklistButton);
        widgetGroup.accept(new WidgetGroupItemFilter(y, this::getItemFilter));
    }

    public void setItemFilter(AbstractItemFilter itemFilter) {
        this.currentItemFilter = itemFilter;
    }

    public AbstractItemFilter getItemFilter() {
        return currentItemFilter;
    }

    public void onFilterInstanceChange() {
        if(currentItemFilter instanceof ISlottedItemFilter) {
            ISlottedItemFilter filter = (ISlottedItemFilter) currentItemFilter;
            filter.setMaxStackSize(maxStackSize);
        }
    }

    public void setMaxStackSize(int maxStackSize) {
        this.maxStackSize = maxStackSize;
        onFilterInstanceChange();
    }

    public void setBlacklistFilter(boolean blacklistFilter) {
        isBlacklistFilter = blacklistFilter;
    }

    public boolean isBlacklistFilter() {
        return isBlacklistFilter;
    }

    public int getMaxStackSize() {
        return maxStackSize;
    }

    public int getMaxMatchSlots() {
        if (!isBlacklistFilter && currentItemFilter instanceof ISlottedItemFilter) {
            ISlottedItemFilter filter = (ISlottedItemFilter) currentItemFilter;
            return filter.getMaxMatchSlots();
        }
        return 1;
    }

    public int getSlotStackSize(int slotIndex) {
        if (!isBlacklistFilter && currentItemFilter instanceof ISlottedItemFilter) {
            ISlottedItemFilter filter = (ISlottedItemFilter) currentItemFilter;
            return filter.getSlotStackSize(slotIndex);
        }
        return -1;
    }

    public int matchItemStack(ItemStack itemStack) {
        int originalSlot = 0;
        if(currentItemFilter instanceof ISlottedItemFilter) {
            ISlottedItemFilter filter = (ISlottedItemFilter) currentItemFilter;
            originalSlot = filter.matchItemStack(itemStack);
        } else if (currentItemFilter != null) {
            originalSlot = currentItemFilter.testItemStack(itemStack) ? 0 : -1;
        }
        if(isBlacklistFilter) {
            originalSlot = (originalSlot >= 0) ? -1 : 0;
        }
        return originalSlot;
    }

    public boolean testItemStack(ItemStack itemStack) {
        return matchItemStack(itemStack) >= 0;
    }
}
