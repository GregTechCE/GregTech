package gregtech.common.items.armor.components;

import gregtech.api.GregTech_API;
import gregtech.common.items.armor.ArmorData;
import net.minecraft.item.ItemStack;

public class ArmorPlating extends ArmorComponent{
	StatType mType;
	public ArmorPlating(String aName, ItemStack aStack, float aWeight, float aPhysicalDef, float aProjectileDef, float aFireDef, float aMagicDef, float aExplosionDef, float aFallDef, float aRadiationDef, float aElectricDef, float aWitherDef) {		
		super(aName, aStack, false, aWeight);
		aPhysicalDef = (float) GregTech_API.sModularArmor.get( mConfigName, "PhysicalDef", aPhysicalDef);
		aProjectileDef = (float) GregTech_API.sModularArmor.get( mConfigName, "ProjectileDef", aProjectileDef);
		aFireDef = (float) GregTech_API.sModularArmor.get( mConfigName, "FireDef", aFireDef);
		aMagicDef = (float) GregTech_API.sModularArmor.get( mConfigName, "MagicDef", aMagicDef);
		aExplosionDef = (float) GregTech_API.sModularArmor.get( mConfigName, "ExplosionDef", aExplosionDef);
		aFallDef = (float) GregTech_API.sModularArmor.get( mConfigName, "FallDef", aFallDef);
		aRadiationDef = (float) GregTech_API.sModularArmor.get( mConfigName, "RadiationDef", aRadiationDef);
		aElectricDef = (float) GregTech_API.sModularArmor.get( mConfigName, "ElectricalDef", aElectricDef);
		aWitherDef = (float) GregTech_API.sModularArmor.get( mConfigName, "WitherDef", aWitherDef);
		addStats(aPhysicalDef, aProjectileDef, aFireDef, aMagicDef, aExplosionDef, aFallDef, aRadiationDef, aElectricDef, aWitherDef);
	}
	
	public ArmorPlating(String aName, String aOreDict, float aWeight, float aPhysicalDef, float aProjectileDef, float aFireDef, float aMagicDef, float aExplosionDef, float aFallDef, float aRadiationDef, float aElectricDef, float aWitherDef) {
		super(aName, aOreDict, false, aWeight);
		aPhysicalDef = (float) GregTech_API.sModularArmor.get( mConfigName, "PhysicalDef", aPhysicalDef);
		aProjectileDef = (float) GregTech_API.sModularArmor.get( mConfigName, "ProjectileDef", aProjectileDef);
		aFireDef = (float) GregTech_API.sModularArmor.get( mConfigName, "FireDef", aFireDef);
		aMagicDef = (float) GregTech_API.sModularArmor.get( mConfigName, "MagicDef", aMagicDef);
		aExplosionDef = (float) GregTech_API.sModularArmor.get( mConfigName, "ExplosionDef", aExplosionDef);
		aFallDef = (float) GregTech_API.sModularArmor.get( mConfigName, "FallDef", aFallDef);
		aRadiationDef = (float) GregTech_API.sModularArmor.get( mConfigName, "RadiationDef", aRadiationDef);
		aElectricDef = (float) GregTech_API.sModularArmor.get( mConfigName, "ElectricalDef", aElectricDef);
		aWitherDef = (float) GregTech_API.sModularArmor.get( mConfigName, "WitherDef", aWitherDef);
		addStats(aPhysicalDef, aProjectileDef, aFireDef, aMagicDef, aExplosionDef, aFallDef, aRadiationDef, aElectricDef, aWitherDef);
	}
	
	public ArmorPlating(String aName, String aOreDict, float aWeight, float aPhysicalDef, float aProjectileDef, float aFireDef, float aMagicDef, float aExplosionDef, float aFallDef, float aRadiationDef, float aElectricDef, float aWitherDef, StatType aType, float aSpecial) {
		super(aName, aOreDict, false, aWeight);
		aPhysicalDef = (float) GregTech_API.sModularArmor.get( mConfigName, "PhysicalDef", aPhysicalDef);
		aProjectileDef = (float) GregTech_API.sModularArmor.get( mConfigName, "ProjectileDef", aProjectileDef);
		aFireDef = (float) GregTech_API.sModularArmor.get( mConfigName, "FireDef", aFireDef);
		aMagicDef = (float) GregTech_API.sModularArmor.get( mConfigName, "MagicDef", aMagicDef);
		aExplosionDef = (float) GregTech_API.sModularArmor.get( mConfigName, "ExplosionDef", aExplosionDef);
		aFallDef = (float) GregTech_API.sModularArmor.get( mConfigName, "FallDef", aFallDef);
		aRadiationDef = (float) GregTech_API.sModularArmor.get( mConfigName, "RadiationDef", aRadiationDef);
		aElectricDef = (float) GregTech_API.sModularArmor.get( mConfigName, "ElectricalDef", aElectricDef);
		aWitherDef = (float) GregTech_API.sModularArmor.get( mConfigName, "WitherDef", aWitherDef);
		addStats(aPhysicalDef, aProjectileDef, aFireDef, aMagicDef, aExplosionDef, aFallDef, aRadiationDef, aElectricDef, aWitherDef);
		mType = StatType.valueOf(GregTech_API.sModularArmor.get(mConfigName, "StatType", aType.toString()));
		mStat.put(mType, (float) GregTech_API.sModularArmor.get( mConfigName, "SpecialType", aSpecial));
	}
	
	public ArmorPlating(String aName, String aOreDict,float aWeight, float aPhysicalDef, float aProjectileDef, float aFireDef, float aMagicDef, float aExplosionDef) {
		super(aName, aOreDict, false, aWeight);
		aPhysicalDef = (float) GregTech_API.sModularArmor.get( mConfigName, "PhysicalDef", aPhysicalDef);
		aProjectileDef = (float) GregTech_API.sModularArmor.get( mConfigName, "ProjectileDef", aProjectileDef);
		aFireDef = (float) GregTech_API.sModularArmor.get( mConfigName, "FireDef", aFireDef);
		aMagicDef = (float) GregTech_API.sModularArmor.get( mConfigName, "MagicDef", aMagicDef);
		aExplosionDef = (float) GregTech_API.sModularArmor.get( mConfigName, "ExplosionDef", aExplosionDef);
		float aFallDef = (float) GregTech_API.sModularArmor.get( mConfigName, "FallDef", 0.0f);
		float aRadiationDef = (float) GregTech_API.sModularArmor.get( mConfigName, "RadiationDef", 0.0f);
		float aElectricDef = (float) GregTech_API.sModularArmor.get( mConfigName, "ElectricalDef", 0.0f);
		float aWitherDef = (float) GregTech_API.sModularArmor.get( mConfigName, "WitherDef", 0.0f);
		addStats(aPhysicalDef, aProjectileDef, aFireDef, aMagicDef, aExplosionDef, aFallDef, aRadiationDef, aElectricDef, aWitherDef);
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
	}

}
