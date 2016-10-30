package gregtech.common.items.armor.components;

import gregtech.common.items.armor.ArmorData;
import net.minecraft.item.ItemStack;

public class ArmorComponentBattery extends ArmorComponent{
	public ArmorComponentBattery(ItemStack aStack, boolean aElectric, float aWeight, float aBatteryCapacity) {
		super(aStack, aElectric, aWeight);
		mStat.put(StatType.WEIGHT, aWeight);
		mStat.put(StatType.BATTERYCAPACITY, aBatteryCapacity);
	}

	@Override
	public void calculateArmor(ArmorData aArmorData) {
		addVal(StatType.WEIGHT, aArmorData);
		addVal(StatType.BATTERYCAPACITY, aArmorData);
	}

}
