package gregtech.api.gui;

import gregtech.api.items.ItemList;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotDataOrb extends Slot {

    public SlotDataOrb(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return ItemList.Tool_DataOrb.isStackEqual(stack, false, true);
    }

}