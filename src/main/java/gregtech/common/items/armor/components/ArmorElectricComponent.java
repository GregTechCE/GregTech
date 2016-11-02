package gregtech.common.items.armor.components;

import gregtech.api.GregTech_API;
import gregtech.common.items.armor.ArmorData;
import net.minecraft.item.ItemStack;

public class ArmorElectricComponent extends ArmorComponent{
	StatType mType1;
	StatType mType2;
	StatType mType3;
	
	public ArmorElectricComponent(String aName, ItemStack aStack, float aWeight, StatType aType1, float aValue1, StatType aType2, float aValue2, StatType aType3, float aValue3) {
		super(aName, aStack, true, aWeight);
		mType1 = StatType.valueOf(GregTech_API.sModularArmor.get(mConfigName, "StatType", aType1.toString()));
		String tType2 = GregTech_API.sModularArmor.get(mConfigName, "StatType", aType2==null ? "null" : aType2.toString());
		mType2 = tType2.equals("null") ? null : StatType.valueOf(tType2);
		String tType3 = GregTech_API.sModularArmor.get(mConfigName, "StatType", aType3==null ? "null" : aType3.toString());
		mType3 = tType3.equals("null") ? null : StatType.valueOf(tType3);
		mStat.put(aType1, (float) GregTech_API.sModularArmor.get( mConfigName, "Value1", aValue1));
		if(mType2!=null)mStat.put(mType2, (float) GregTech_API.sModularArmor.get( mConfigName, "Value2", aValue2));
		if(mType3!=null)mStat.put(mType3, (float) GregTech_API.sModularArmor.get( mConfigName, "Value3", aValue3));
	}

	@Override
	public void calculateArmor(ArmorData aArmorData) {		
		addVal(mType1, aArmorData);	
		if(mType2!=null)addVal(mType2, aArmorData);	
		if(mType3!=null)addVal(mType3, aArmorData);
	}

}
