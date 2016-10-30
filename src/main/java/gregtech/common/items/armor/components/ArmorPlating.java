package gregtech.common.items.armor.components;

import gregtech.common.items.armor.ArmorData;
import net.minecraft.item.ItemStack;

public class ArmorPlating extends ArmorComponent{
	StatType mType;
	public ArmorPlating(ItemStack aStack, float aWeight, float aPhysicalDef, float aProjectileDef, float aFireDef, float aMagicDef, float aExplosionDef, float aFallDef, float aRadiationDef, float aElectricDef, float aWitherDef) {
		super(aStack, false, aWeight);
		addStats(aPhysicalDef, aProjectileDef, aFireDef, aMagicDef, aExplosionDef, aFallDef, aRadiationDef, aElectricDef, aWitherDef);
	}
	
	public ArmorPlating(String aOreDict, float aWeight, float aPhysicalDef, float aProjectileDef, float aFireDef, float aMagicDef, float aExplosionDef, float aFallDef, float aRadiationDef, float aElectricDef, float aWitherDef) {
		super(aOreDict, false, aWeight);
		addStats(aPhysicalDef, aProjectileDef, aFireDef, aMagicDef, aExplosionDef, aFallDef, aRadiationDef, aElectricDef, aWitherDef);
	}
	
	public ArmorPlating(String aOreDict, float aWeight, float aPhysicalDef, float aProjectileDef, float aFireDef, float aMagicDef, float aExplosionDef, float aFallDef, float aRadiationDef, float aElectricDef, float aWitherDef, StatType aType, float aSpecial) {
		super(aOreDict, false, aWeight);
		addStats(aPhysicalDef, aProjectileDef, aFireDef, aMagicDef, aExplosionDef, aFallDef, aRadiationDef, aElectricDef, aWitherDef);
		mType = aType;
		mStat.put(mType, aSpecial);
	}
	
	public ArmorPlating(String aOreDict,float aWeight, float aPhysicalDef, float aProjectileDef, float aFireDef, float aMagicDef, float aExplosionDef) {
		super(aOreDict, false, aWeight);
		addStats(aPhysicalDef, aProjectileDef, aFireDef, aMagicDef, aExplosionDef, 0, 0, 0, 0);
	}
	
	public void addStats(float aPhysicalDef, float aProjectileDef, float aFireDef, float aMagicDef, float aExplosionDef, float aFallDef, float aRadiationDef, float aElectricDef, float aWitherDef){
		mStat.put(StatType.FALLDEFENCE, aFallDef);
		mStat.put(StatType.PHYSICALDEFENCE, aPhysicalDef);
		mStat.put(StatType.PROJECTILEDEFENCE, aProjectileDef);
		mStat.put(StatType.FIREDEFENCE, aFireDef);
		mStat.put(StatType.MAGICDEFENCE, aMagicDef);
		mStat.put(StatType.EXPLOSIONDEFENCE, aExplosionDef);
		mStat.put(StatType.RADIATIONDEFENCE, aRadiationDef);
		mStat.put(StatType.ELECTRICALDEFENCE, aElectricDef);
		mStat.put(StatType.WITHERDEFENCE, aWitherDef);
	}

	@Override
	public void calculateArmor(ArmorData aArmorData) {
		calDef(StatType.FALLDEFENCE, aArmorData);
		calDef(StatType.PHYSICALDEFENCE, aArmorData);
		calDef(StatType.PROJECTILEDEFENCE, aArmorData);
		calDef(StatType.FIREDEFENCE, aArmorData);
		calDef(StatType.MAGICDEFENCE, aArmorData);
		calDef(StatType.EXPLOSIONDEFENCE, aArmorData);
		calDef(StatType.RADIATIONDEFENCE, aArmorData);
		calDef(StatType.ELECTRICALDEFENCE, aArmorData);
		calDef(StatType.WITHERDEFENCE, aArmorData);
		addVal(StatType.WEIGHT, aArmorData);
		if(mType!=null)addVal(mType, aArmorData);
	}
	
	public void calDef(StatType aType, ArmorData aArmorData){
		float tArmorDef = 0.0f;
		if(aArmorData.mStat.containsKey(aType)){
		tArmorDef = aArmorData.mStat.get(aType);
		aArmorData.mStat.remove(aType);}
		float tComponentDef = mStat.get(aType);
		aArmorData.mStat.put(aType, tArmorDef + ((1.0f -tArmorDef) * tComponentDef));
//		System.out.println("calDef: "+aType.name()+" "+tArmorDef+" "+(tArmorDef + ((1.0f -tArmorDef) * tComponentDef)));
	}

}
