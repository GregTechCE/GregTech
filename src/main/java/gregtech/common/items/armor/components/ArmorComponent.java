package gregtech.common.items.armor.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gregtech.api.objects.ItemData;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import gregtech.common.items.armor.ArmorData;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public abstract class ArmorComponent implements IArmorComponent {
	public ItemStack mStack;
	public String mOreDict;
	public static Map<String, ArmorComponent> mOreDicts = new HashMap<String, ArmorComponent>();
	public static Map<String, ArmorComponent> mStacks = new HashMap<String, ArmorComponent>();
	public Map<StatType,Float> mStat = new HashMap<StatType,Float>();
	public Map<StatType,Boolean> mBStat = new HashMap<StatType,Boolean>();
	
	public ArmorComponent(String aOreDict, boolean aElectric, float aWeight){
		mOreDict = aOreDict;
		mBStat.put(StatType.ELECTRIC, aElectric);
		mOreDicts.put(aOreDict, this);
		for(ItemStack tStack : OreDictionary.getOres(aOreDict))if(tStack!=null)mStacks.put(tStack.getUnlocalizedName(), this);
		mStat.put(StatType.WEIGHT, aWeight);
	}
	
	public ArmorComponent(ItemStack aStack, boolean aElectric, float aWeight){
		mStack = aStack;
		mBStat.put(StatType.ELECTRIC, aElectric);
		mStacks.put(aStack.getUnlocalizedName(), this);
		mStat.put(StatType.WEIGHT, aWeight);
	}

	@Override
	public boolean isArmorComponent(ItemStack aStack) {
		if(mStack!=null && GT_Utility.areStacksEqual(mStack, aStack, true)){return true;}
		if(mOreDict!=null){
			for(ItemStack tStack : OreDictionary.getOres(mOreDict))
				if(GT_Utility.areStacksEqual(tStack, aStack, true))return true;}
		return false;
	}
	
	public void addVal(StatType aType, ArmorData aArmorData){
		float tArmorDef = 0.0f;
		if(aArmorData.mStat.containsKey(aType)){
		tArmorDef = aArmorData.mStat.get(aType);
		aArmorData.mStat.remove(aType);}
		aArmorData.mStat.put(aType, tArmorDef + mStat.get(aType));		
	}

	public abstract void calculateArmor(ArmorData aArmorData);

}
