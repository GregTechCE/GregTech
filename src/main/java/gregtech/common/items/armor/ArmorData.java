package gregtech.common.items.armor;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import gregtech.api.damagesources.GT_DamageSources;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_Utility;
import gregtech.common.items.armor.components.ArmorComponent;
import gregtech.common.items.armor.components.StatType;
import gregtech.common.items.armor.gui.ContainerBasicArmor;
import gregtech.common.items.armor.gui.ContainerModularArmor;
import gregtech.common.items.armor.gui.InventoryArmor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class ArmorData {

	public int type; // 0 = helmet; 1 = chestplate; 2 = leggings; 3 = boots;
	public int armorTier; // 0 = Basic Modular Armor; 1 = Modular Exoskeleton; 2= Modular Nanosuit; 3 = Heavy Power Armor
	public List info; // needs Localization
	public boolean isTopItem;
	public int tooltipUpdate;
	public boolean openGui;

	public ArmorData helmet;
	public ArmorData chestplate;
	public ArmorData leggings;
	public ArmorData boots;	

	public Map<StatType,Float> mStat = new HashMap<StatType,Float>();
	public Map<StatType,Boolean> mBStat = new HashMap<StatType,Boolean>();
	static ArrayList<StatType> updateArmorStatTypeList;
//	public boolean fullArmor;
//	public boolean fullRadiationDef;
//	public boolean fullElectricDef;
//
//	public float fallDef;
//	public float physicalDef;
//	public float projectileDef;
//	public float fireDef;
//	public float magicDef;
//	public float explosionDef;
//	public float radiationDef;
//	public float electricDef;
//	public float witherDef;
//
//	public float thorns;
//	public float thornsSingle;
//	public int magnet;
//	public int magnetSingle;
//
//	public int partsCharge;
	public int maxCharge;
	public int charge;
//	public boolean thaumicGoggles;
//	public boolean nightVision;
//	public boolean potionInjector;
//	public boolean autoFeeder;

	public FluidStack fluid;
//	public int tankCap;
//
//	public int weight;
	public float maxWeight;
//	public int processingPower;
//	public int processingPowerUsed;
//	public int partProcessing;
//	public int partProcessingUsed;
//
//	public int motorPower;
//	public int motorEUusage;
//	public int pistonJumpboost;
//	public int pistonEUusage;
//	public int electrolyzerProd;
//	public int electrolyzerEUusage;
//	public int fieldGenCap;
//	public int fieldGenEUusage;
//
//	public int jetpackMaxWeight;
//	public int antiGravMaxWeight;

	public ArmorData(EntityPlayer player, ItemStack stack, int type, int tier) {
		if(updateArmorStatTypeList == null)
		{
			updateArmorStatTypeList = new ArrayList<StatType>();
			updateArmorStatTypeList.add(StatType.MAGNET);
			updateArmorStatTypeList.add(StatType.THORNS);
			updateArmorStatTypeList.add(StatType.PROCESSINGPOWER);
			updateArmorStatTypeList.add(StatType.PROCESSINGPOWERUSED);
		}
		this.type = type;
		this.armorTier = tier;
		ContainerModularArmor tmp = new ContainerBasicArmor((EntityPlayer) player, new InventoryArmor(ModularArmor_Item.class, stack));
		calculateArmor(tmp.mInvArmor.parts);
		switch (tier) {
		case 0:
			maxCharge = 0;
			break;
		case 1:
			maxCharge = 250000;
			break;
		case 2:
			maxCharge = 1000000;
		}
		readNBT(stack.getTagCompound());
	}

	private void readNBT(NBTTagCompound nbt) {
		if (nbt == null) {
			return;
		}
		if (nbt.hasKey("Charge")) {
			this.charge = nbt.getInteger("Charge");
		}
	}

	public void writeToNBT(NBTTagCompound nbt) {
		if (nbt == null) {
			return;
		}
		nbt.setInteger("Charge", this.charge);
	}

	public ArmorData calculateArmor(ItemStack[] parts) {
		mStat.clear();
		mBStat.clear();
		for(ItemStack tPart : parts){
			if(tPart!=null && ArmorComponent.mStacks.containsKey(tPart.getUnlocalizedName()))
				ArmorComponent.mStacks.get(tPart.getUnlocalizedName()).calculateArmor(this);
		}
		for(StatType tType : StatType.values())if(!mStat.containsKey(tType))mStat.put(tType, .0f);
		for(StatType tType : StatType.values())if(!mBStat.containsKey(tType))mBStat.put(tType, false);
		updateTooltip();
		return this;
	}

	public void updateTooltip() {
		List<String> tagList = new ArrayList<String>();
		String tmp2 = "";
		if (maxWeight > 4000) {
			tmp2 = " " + GT_LanguageManager.getTranslation("Too Heavy");
		}
		if (maxCharge != 0) {
			DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
			DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
			symbols.setGroupingSeparator(' ');
			if (type == 0) {
				if (mBStat.get(StatType.THAUMICGOGGLES)) {
					tagList.add(GT_LanguageManager.getTranslation("Thaumic Goggles installed"));
				}
				if (mBStat.get(StatType.NIGHTVISION)) {
					tagList.add(GT_LanguageManager.getTranslation("Nightvision installed"));
				}
			}
			tagList.add("EU: " + formatter.format(charge + mStat.get(StatType.PARTSCHARGE)));
			if (type == 2) {
				tagList.add(GT_LanguageManager.getTranslation("Jumpboost") + ": " + mStat.get(StatType.PISTONJUMPBOOST) / 3 + "m");
			}
			if (type == 2 && mStat.get(StatType.PISTONJUMPBOOST) > 0) {
				tagList.add(GT_LanguageManager.getTranslation("Uphill step assist active"));
			}
			if (type == 2 && mStat.get(StatType.MOTPRPOWER) > 0) {
				tagList.add(GT_LanguageManager.getTranslation("Speedup") + ": " + mStat.get(StatType.MOTPRPOWER));
				tagList.add(GT_LanguageManager.getTranslation("Motor energy usage") + ": " + mStat.get(StatType.MOTOREUUSAGE) + " EU");
				if (maxWeight > 4000) {
					tagList.add(GT_LanguageManager.getTranslation("Too Heavy!!!"));
				}
			}
			tagList.add(GT_LanguageManager.getTranslation("Processing power ") + " " + mStat.get(StatType.PARTPROCESSING) + " " + GT_LanguageManager.getTranslation(""));
			tagList.add(GT_LanguageManager.getTranslation("Processing power used") + ": " + mStat.get(StatType.PARTPROCESSINGUSED) + " " + GT_LanguageManager.getTranslation(""));
			if (type == 0 && mStat.get(StatType.ELECTROLYZERPROD) > 0) {
				tagList.add(GT_LanguageManager.getTranslation("Electrolyzer produces ") + " " + mStat.get(StatType.ELECTROLYZERPROD) / 2 + GT_LanguageManager.getTranslation("per second"));
			}
			if (mStat.get(StatType.TANKCAP) > 0) {
				if (fluid != null) {
					tagList.add(GT_LanguageManager.getTranslation("Tank Capacity") + ": " + fluid.getLocalizedName() + " " + fluid.amount + "L (" + mStat.get(StatType.TANKCAP) + ")");
				} else {
					tagList.add(GT_LanguageManager.getTranslation("tankcap") + ": " + mStat.get(StatType.TANKCAP));
				}
			}
		}
		tagList.add(GT_LanguageManager.getTranslation("Weight") + ": " + mStat.get(StatType.WEIGHT) + tmp2);
		tagList.add(GT_LanguageManager.getTranslation("Physical Defence") + ": " + (Math.round(mStat.get(StatType.PHYSICALDEFENCE) * 1000) / 10.0) + "%");
		tagList.add(GT_LanguageManager.getTranslation("Projectile Defence") + ": " + (Math.round(mStat.get(StatType.PROJECTILEDEFENCE) * 1000) / 10.0) + "%");
		tagList.add(GT_LanguageManager.getTranslation("Fire Defence") + ": " + (Math.round(mStat.get(StatType.FIREDEFENCE) * 1000) / 10.0) + "%");
		tagList.add(GT_LanguageManager.getTranslation("Magic Defence") + ": " + (Math.round(mStat.get(StatType.MAGICDEFENCE) * 1000) / 10.0) + "%");
		tagList.add(GT_LanguageManager.getTranslation("Explosive Defence") + ": " + (Math.round(mStat.get(StatType.EXPLOSIONDEFENCE) * 1000) / 10.0) + "%");
		if (mStat.get(StatType.FALLDEFENCE) > 0 && type == 3) {
			tagList.add(GT_LanguageManager.getTranslation("Absorbs") + " " + mStat.get(StatType.FALLDEFENCE) + GT_LanguageManager.getTranslation(" m of Fall Defence"));
		}
		if (mStat.get(StatType.THORNS) > 0) {
			tagList.add(GT_LanguageManager.getTranslation("Thorns") + ": " + mStat.get(StatType.THORNS));
		}
		if (mStat.get(StatType.MAGNET) > 0) {
			tagList.add(GT_LanguageManager.getTranslation("Magnet") + ": " + mStat.get(StatType.MAGNET) + "m");
		}
		if (mBStat.get(StatType.FULLRADIATIONARMOR)) {
			tagList.add(GT_LanguageManager.getTranslation("Is Full Radiation Defence"));
		} else {
			if (mStat.get(StatType.RADIATIONDEFENCE) > 0.01d) {
				tagList.add(GT_LanguageManager.getTranslation("Radiation Defence") + ": " + (Math.round(mStat.get(StatType.RADIATIONDEFENCE) * 1000) / 10.0) + "%");
			}
		}
		info = tagList;
	}

	public void armorPartsEquipped(EntityPlayer aPlayer) {
		helmet = null;
		chestplate = null;
		leggings = null;
		boots = null;
		for (int i = 1; i < 5; i++) {
			ItemStack stack = aPlayer.getEquipmentInSlot(i);
			if (stack != null && stack.getItem() instanceof ModularArmor_Item) {
				ModularArmor_Item tmp = (ModularArmor_Item) stack.getItem();
				ContainerModularArmor tmp2 = new ContainerBasicArmor(aPlayer, new InventoryArmor(ModularArmor_Item.class, stack));
				if ((this.type + i) == 4) {
					fluid = ArmorCalculation.getFluid(tmp2.mInvArmor.parts, Math.round(mStat.get(StatType.TANKCAP)));
				}
				if (maxCharge > 0 && charge < maxCharge) {
					int loaded = ArmorCalculation.deChargeBatterys(tmp2.mInvArmor.parts, maxCharge - charge);
					charge = charge + loaded;
					change(mStat, StatType.PARTSCHARGE, -loaded);

				}
				switch (tmp.armorType) {
				case 0:
					helmet = tmp.data;
					break;
				case 1:
					chestplate = tmp.data;
					break;
				case 2:
					leggings = tmp.data;
					break;
				case 3:
					boots = tmp.data;
					break;
				default:
					break;
				}
				writeToNBT(stack.getTagCompound());
			}
		}
		if (helmet != null && chestplate != null && leggings != null && boots != null) {
			set(mBStat, StatType.FULLARMOR, true);
			boolean helmHasFullRadiationDefence=false;			
			boolean chestplateHasFullRadiationDefence=false;
			boolean leggingsHasFullRadiationDefence=false;
			boolean bootsHasFullRadiationDefence=false;
			
			boolean helmHasFullElectricalDefenceDefence=false;
			boolean chestplateHasFullElectricalDefenceDefence=false;
			boolean leggingsHasFullElectricalDefenceDefence=false;
			boolean bootsHasFullElectricalDefenceDefence=false;
			//Check each armor pieces for valid mStat value and verify that the Hash contains the StatType key
			if(helmet.mStat!= null)
			{
				if(helmet.mStat.containsKey(StatType.RADIATIONDEFENCE))
				{
					helmHasFullRadiationDefence = helmet.mStat.get(StatType.RADIATIONDEFENCE)> 0.9f;
				}
				if(helmet.mStat.containsKey(StatType.ELECTRICALDEFENCE))
				{
					helmHasFullElectricalDefenceDefence = helmet.mStat.get(StatType.RADIATIONDEFENCE)> 0.9f;
				}
			}
			if(chestplate.mStat!= null)
			{
				if(chestplate.mStat.containsKey(StatType.RADIATIONDEFENCE))
				{
					chestplateHasFullRadiationDefence = chestplate.mStat.get(StatType.RADIATIONDEFENCE)> 0.9f;
				}
				if(chestplate.mStat.containsKey(StatType.ELECTRICALDEFENCE))
				{
					chestplateHasFullElectricalDefenceDefence = chestplate.mStat.get(StatType.RADIATIONDEFENCE)> 0.9f;
				}
			}
			if(leggings.mStat!= null)
			{
				if(leggings.mStat.containsKey(StatType.RADIATIONDEFENCE))
				{
					leggingsHasFullRadiationDefence = leggings.mStat.get(StatType.RADIATIONDEFENCE)> 0.9f;
				}
				if(leggings.mStat.containsKey(StatType.ELECTRICALDEFENCE))
				{
					leggingsHasFullElectricalDefenceDefence = leggings.mStat.get(StatType.RADIATIONDEFENCE)> 0.9f;
				}
			}
			if(boots.mStat!= null)
			{
				if(boots.mStat.containsKey(StatType.RADIATIONDEFENCE))
				{
					bootsHasFullRadiationDefence = boots.mStat.get(StatType.RADIATIONDEFENCE)> 0.9f;
				}
				if(boots.mStat.containsKey(StatType.ELECTRICALDEFENCE))
				{
					bootsHasFullElectricalDefenceDefence = boots.mStat.get(StatType.RADIATIONDEFENCE)> 0.9f;
				}
			}
			//Set Status of Full Armor types
			set(mBStat, StatType.FULLRADIATIONARMOR, helmHasFullRadiationDefence&&chestplateHasFullRadiationDefence&&leggingsHasFullRadiationDefence &&bootsHasFullRadiationDefence);
			set(mBStat, StatType.FULLELECTRICARMOR, helmHasFullElectricalDefenceDefence&&chestplateHasFullElectricalDefenceDefence&&leggingsHasFullElectricalDefenceDefence &&bootsHasFullElectricalDefenceDefence);	
		} else {
			//Reset Full armor type status to false for all types if StatType.FULLARMOR is false
			set(mBStat, StatType.FULLARMOR, false);
			set(mBStat, StatType.FULLRADIATIONARMOR, false);
			set(mBStat, StatType.FULLELECTRICARMOR, false);
		}
		
		
		set(mBStat, StatType.MAGNET, 0);
		set(mBStat, StatType.THORNS, 0);
		set(mBStat, StatType.PROCESSINGPOWER, 0);
		set(mBStat, StatType.PROCESSINGPOWERUSED, 0);
		
		if (helmet != null) {			
			updateArmorStats(helmet,updateArmorStatTypeList );
		}
		if (chestplate != null) {
			updateArmorStats(chestplate,updateArmorStatTypeList );

		}
		if (leggings != null) {
			updateArmorStats(leggings,updateArmorStatTypeList );

		}
		if (boots != null) {
			updateArmorStats(boots,updateArmorStatTypeList );

		}
		isTopItem = false;
		if (type == 0) {
			isTopItem = true;
		} else if (helmet == null && type == 1) {
			isTopItem = true;
		} else if (helmet == null && chestplate == null && type == 2) {
			isTopItem = true;
		} else if (helmet == null && chestplate == null && leggings == null && type == 3) {
			isTopItem = true;
		}
		if (helmet != null) {
			maxWeight = helmet.mStat.get(StatType.WEIGHT);
		}
		if (chestplate != null) {
			maxWeight += chestplate.mStat.get(StatType.WEIGHT);
		}
		if (leggings != null) {
			maxWeight += leggings.mStat.get(StatType.WEIGHT);
		}
		if (boots != null) {
			maxWeight += boots.mStat.get(StatType.WEIGHT);
		}
	}

	private void updateArmorStats(ArmorData armorData, ArrayList<StatType> statTypes) {
		for (StatType statType : statTypes) {
			if(armorData == null || armorData.mStat == null || !armorData.mStat.containsKey(statType))
				continue;
//			if(armorData != null && armorData.mStat != null && armorData.mStat.containsKey(statType))
//			{
				set(mStat, statType, armorData.mStat.get(statType));
//			}
			/*change(mStat, StatType.MAGNET, armorData.mStat.get(StatType.MAGNET));
			change(mStat, StatType.THORNS, armorData.mStat.get(StatType.THORNS));
			change(mStat, StatType.PROCESSINGPOWER, armorData.mStat.get(StatType.PROCESSINGPOWER));
			change(mStat, StatType.PROCESSINGPOWERUSED, armorData.mStat.get(StatType.PROCESSINGPOWERUSED));*/			
		}
		
		
	}
	
	public void set(Map aMap, StatType aType, boolean aSet){
		if(aMap.containsKey(aType))aMap.remove(aType);
		aMap.put(aType, aSet);
	}
	
	public void set(Map aMap, StatType aType, float aSet){
		if(aMap.containsKey(aType))aMap.remove(aType);
		aMap.put(aType, aSet);
	}
	
	public void change(Map aMap, StatType aType, float aChange){
		float tChange = 0;
		if(aMap==null)System.out.println("changeMapnull");
		if(aMap.containsKey(aType)){
			Object value = aMap.get(aType);
			tChange = value != null ? (float) aMap.get(aType) : 0.0f;
		aMap.remove(aType);
		}
		aMap.put(aType, (tChange + aChange));		
	}
	
	public void dechargeComponents(int aCharge){
		
	}

	public double getBaseAbsorptionRatio() {
		switch (this.type) {
		case 0:
			return 0.15;
		case 1:
			return 0.40;
		case 2:
			return 0.30;
		case 3:
			return 0.15;
		default:
			return 0.00;
		}
	}
}
