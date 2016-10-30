package gregtech.common.items.armor.components;

import gregtech.common.items.armor.ArmorData;
import net.minecraft.item.ItemStack;

public class ArmorElectricComponent extends ArmorComponent{
	StatType mType1;
	StatType mType2;
	StatType mType3;
	
	public ArmorElectricComponent(ItemStack aStack, float aWeight, StatType aType1, float aValue1, StatType aType2, float aValue2, StatType aType3, float aValue3) {
		super(aStack, true, aWeight);
		mType1 = aType1;
		mType2 = aType2;
		mType3 = aType3;
		mStat.put(StatType.WEIGHT, aWeight);
		mStat.put(aType1, aValue1);
		if(mType2!=null)mStat.put(mType2, aValue2);
		if(mType3!=null)mStat.put(mType3, aValue3);
	}

	@Override
	public void calculateArmor(ArmorData aArmorData) {		
		addVal(mType1, aArmorData);	
		if(mType2!=null)addVal(mType2, aArmorData);	
		if(mType3!=null)addVal(mType3, aArmorData);
		addVal(StatType.WEIGHT, aArmorData);
	}

}
