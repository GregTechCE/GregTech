package gregtech.api.capability.internal;

import gregtech.api.gui.IUIHolder;
import net.minecraft.item.ItemStack;

public interface ISimpleSlotInventory extends IUIHolder {

    int getSlotsCount();
    int getMaxStackSize(int index);

    /**
     * @return a COPY of stack in slot. Actual stack won't change.
     */
    ItemStack getStackInSlot(int index);
    void setStackInSlot(int index, ItemStack stack);
    boolean isValidSlot(int index);

}
