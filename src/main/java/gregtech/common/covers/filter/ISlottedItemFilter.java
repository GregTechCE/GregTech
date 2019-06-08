package gregtech.common.covers.filter;

import net.minecraft.item.ItemStack;

public interface ISlottedItemFilter {

    int getMaxMatchSlots();

    void setMaxStackSize(int maxStackSize);

    int getMaxStackSize();

    int getSlotStackSize(int slotIndex);

    /**
     * @return number in [0..maxMatchSlots) bounds, presuming match slot of item stack,
     * or -1 if it didn't match. Match slots are used for advanced supplying
     * techniques like robotic arm modes, but are mostly ignored for other covers
     */
    int matchItemStack(ItemStack itemStack);
}
