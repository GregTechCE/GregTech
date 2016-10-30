package gregtech.common.items.armor.components;

import gregtech.common.items.armor.ArmorData;
import net.minecraft.item.ItemStack;

public class ArmorComponentFunction extends ArmorComponent{
	StatType mType;
	public ArmorComponentFunction(ItemStack aStack, boolean aElectric, float aWeight, StatType aType, float aProcessingUsed) {
		super(aStack, aElectric, aWeight);
		mType = aType;
		mStat.put(StatType.WEIGHT, aWeight);
		mStat.put(StatType.PROCESSINGPOWERUSED, aProcessingUsed);
	}

	@Override
	public void calculateArmor(ArmorData aArmorData) {
		addVal(StatType.WEIGHT, aArmorData);
		addVal(StatType.PROCESSINGPOWERUSED, aArmorData);
		if(!aArmorData.mBStat.containsKey(mType))aArmorData.mBStat.put(mType, true);
	}

}
