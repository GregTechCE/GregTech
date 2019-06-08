package gregtech.common.covers.filter;

import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.IUIHolder;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.LabelWidget;
import gregtech.api.gui.widgets.SlotWidget;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class ItemFilterContainer implements INBTSerializable<NBTTagCompound> {

    private final IUIHolder holder;
    private final ItemStackHandler filterInventory;
    private final ItemFilterWrapper filterWrapper;

    public ItemFilterContainer(IUIHolder holder) {
        this.holder = holder;
        this.filterWrapper = new ItemFilterWrapper();
        this.filterInventory = new ItemStackHandler(1) {
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return FilterTypeRegistry.getItemFilterForStack(stack) != null;
            }

            @Override
            public int getSlotLimit(int slot) {
                return 1;
            }

            @Override
            protected void onLoad() {
                onFilterSlotChange(false);
            }

            @Override
            protected void onContentsChanged(int slot) {
                onFilterSlotChange(true);
            }
        };
    }

    public ItemStackHandler getFilterInventory() {
        return filterInventory;
    }

    public void initUI(int y, Consumer<Widget> widgetGroup) {
        widgetGroup.accept(new LabelWidget(10, y, "cover.conveyor.item_filter.title"));
        widgetGroup.accept(new SlotWidget(filterInventory, 0, 10, y + 15)
            .setBackgroundTexture(GuiTextures.SLOT, GuiTextures.FILTER_SLOT_OVERLAY));
        this.filterWrapper.initUI(y + 15 + 18 + 5, widgetGroup);
    }

    protected void onFilterSlotChange(boolean notify) {
        ItemStack filterStack = filterInventory.getStackInSlot(0);
        AbstractItemFilter newItemFilter = FilterTypeRegistry.getItemFilterForStack(filterStack);
        AbstractItemFilter currentItemFilter = filterWrapper.getItemFilter();
        if(newItemFilter == null) {
            if(currentItemFilter != null) {
                filterWrapper.setItemFilter(null);
                filterWrapper.setBlacklistFilter(false);
                if (notify) filterWrapper.onFilterInstanceChange();
            }
        } else if (currentItemFilter == null ||
            newItemFilter.getClass() != currentItemFilter.getClass()) {
            newItemFilter.setHolder(holder);
            filterWrapper.setItemFilter(newItemFilter);
            if (notify) filterWrapper.onFilterInstanceChange();
        }
    }

    public void setMaxStackSize(int maxStackSize) {
        this.filterWrapper.setMaxStackSize(maxStackSize);
    }

    public int getMaxMatchSlots() {
        return filterWrapper.getMaxMatchSlots();
    }

    public int getSlotStackSize(int slotIndex) {
        return filterWrapper.getSlotStackSize(slotIndex);
    }

    public int matchItemStack(ItemStack itemStack) {
        return filterWrapper.matchItemStack(itemStack);
    }

    public boolean testItemStack(ItemStack itemStack) {
        return matchItemStack(itemStack) >= 0;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound tagCompound = new NBTTagCompound();
        tagCompound.setTag("FilterInventory", filterInventory.serializeNBT());
        tagCompound.setBoolean("IsBlacklist", filterWrapper.isBlacklistFilter());
        tagCompound.setInteger("MaxStackSize", filterWrapper.getMaxStackSize());
        if(filterWrapper.getItemFilter() != null) {
            NBTTagCompound filterInventory = new NBTTagCompound();
            filterWrapper.getItemFilter().writeToNBT(filterInventory);
            tagCompound.setTag("Filter", filterInventory);
        }
        return tagCompound;
    }

    @Override
    public void deserializeNBT(NBTTagCompound tagCompound) {
        this.filterInventory.deserializeNBT(tagCompound.getCompoundTag("FilterInventory"));
        this.filterWrapper.setBlacklistFilter(tagCompound.getBoolean("IsBlacklist"));
        if(tagCompound.hasKey("MaxStackSize")) {
            this.filterWrapper.setMaxStackSize(tagCompound.getInteger("MaxStackSize"));
        }
        if(filterWrapper.getItemFilter() != null) {
            NBTTagCompound filterInventory = tagCompound.getCompoundTag("Filter");
            this.filterWrapper.getItemFilter().readFromNBT(filterInventory);
        }
    }
}
