package gregtech.common.items.armor.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotFluid extends Slot {

    public SlotFluid(IInventory inventory, int slot_index, int x, int y) {
        super(inventory, slot_index, x, y);
    }

    @Override
    public boolean canTakeStack(EntityPlayer p_82869_1_) {
        return false;
    }

    @Override
    public boolean isItemValid(ItemStack p_75214_1_) {
        return false;
    }

}
