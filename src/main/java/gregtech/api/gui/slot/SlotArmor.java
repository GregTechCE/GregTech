package gregtech.api.gui.slot;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotArmor extends Slot {
    final EntityEquipmentSlot armorType;
    final EntityPlayer player;

    public SlotArmor(IInventory inventoryIn, int index, int xPosition, int yPosition, EntityEquipmentSlot armorType, EntityPlayer player) {
        super(inventoryIn, index, xPosition, yPosition);
        this.armorType = armorType;
        this.player = player;
    }

    @Override
    public int getSlotStackLimit() {
        return 1;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return stack != null && stack.getItem().isValidArmor(stack, armorType, player);
    }

}
