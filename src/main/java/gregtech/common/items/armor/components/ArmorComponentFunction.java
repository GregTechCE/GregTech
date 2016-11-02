package gregtech.common.items.armor.components;

import gregtech.api.GregTech_API;
import gregtech.common.items.armor.ArmorData;
import net.minecraft.item.ItemStack;

public class ArmorComponentFunction extends ArmorComponent{
	StatType mType;
	public ArmorComponentFunction(String aName, ItemStack aStack, boolean aElectric, float aWeight, StatType aType, float aProcessingUsed) {
		super(aName, aStack, aElectric, aWeight);
		mType = StatType.valueOf(GregTech_API.sModularArmor.get(mConfigName, "StatType", aType.toString()));
		mStat.put(StatType.PROCESSINGPOWERUSED, (float) GregTech_API.sModularArmor.get( mConfigName, "ProcessingUsed", aProcessingUsed));
	}

	@Override
	public void calculateArmor(ArmorData aArmorData) {
		addVal(StatType.PROCESSINGPOWERUSED, aArmorData);
		if(!aArmorData.mBStat.containsKey(mType))aArmorData.mBStat.put(mType, true);
	}

}
