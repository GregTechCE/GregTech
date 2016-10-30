package gregtech.common.items.armor.components;

import gregtech.common.items.armor.ArmorData;
import net.minecraft.item.ItemStack;

public interface IArmorComponent {

	boolean isArmorComponent(ItemStack aStack);
	
	void calculateArmor(ArmorData aArmorData);
}
