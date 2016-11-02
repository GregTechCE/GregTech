package gregtech.common.items.armor.components;

import gregtech.api.GregTech_API;
import gregtech.common.items.armor.ArmorData;
import net.minecraft.item.ItemStack;

public class ArmorComponentBattery extends ArmorComponent{
	public ArmorComponentBattery(String aName, ItemStack aStack, boolean aElectric, float aWeight, float aBatteryCapacity) {
		super(aName, aStack, aElectric, aWeight);
		mStat.put(StatType.BATTERYCAPACITY, (float) GregTech_API.sModularArmor.get( mConfigName, "ProcessingUsed", aBatteryCapacity));
	}

	@Override
	public void calculateArmor(ArmorData aArmorData) {
		addVal(StatType.BATTERYCAPACITY, aArmorData);
	}

}
