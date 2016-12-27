package gregtech.common.items.armor.gui;

import gregtech.common.items.armor.components.ArmorComponent;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotArmor extends Slot{

	public SlotArmor(IInventory par1iInventory, int par2, int par3, int par4) {
		super(par1iInventory, par2, par3, par4);
	}

	@Override
	public boolean isItemValid(ItemStack par1ItemStack) {
	return ArmorComponent.mStacks.containsKey(par1ItemStack.getUnlocalizedName());
	}
}
