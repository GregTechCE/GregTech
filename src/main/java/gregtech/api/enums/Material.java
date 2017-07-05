package gregtech.api.enums;

import gregtech.GT_Mod;
import gregtech.api.GregTech_API;
import gregtech.api.interfaces.IMaterialHandler;
import gregtech.api.interfaces.ISubTagContainer;
import gregtech.api.objects.MaterialStack;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import gregtech.loaders.materialprocessing.ProcessingConfig;
import gregtech.loaders.materialprocessing.ProcessingModSupport;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Loader;

import java.util.*;

import static gregtech.api.enums.GT_Values.M;

public class Material implements ISubTagContainer, Comparable<Material> {

	private static Material[] MATERIALS_ARRAY = new Material[]{};
	private static final Map<String, Material> MATERIALS_MAP = new LinkedHashMap<>();
	public static final List<IMaterialHandler> mMaterialHandlers = new ArrayList<>();
    
	public final int materialRGB, materialRGBMolten;
	public final MaterialType materialType;
	public final int metaItemSubId;

	public boolean mUnificatable;
	public Material mMaterialInto;
	public List<MaterialStack> mMaterialList = new ArrayList<MaterialStack>();
	public List<Material> mOreByProducts = new ArrayList<Material>(), mOreReRegistrations = new ArrayList<Material>();
	public List<TC_Aspects.TC_AspectStack> mAspects = new ArrayList<TC_Aspects.TC_AspectStack>();
	public ArrayList<ItemStack> mMaterialItems = new ArrayList<ItemStack>();
	public Collection<SubTag> mSubTags = new LinkedHashSet<SubTag>();
	public Enchantment toolEnchantment = null;
	public int toolEnchantmentLevel = 0;
	public boolean mBlastFurnaceRequired = false, mTransparent = false;
	public float toolSpeed = 1.0F, heatDamage = 0.0F;
	public String mChemicalFormula = "?", mName = "null", mDefaultLocalName = "null", mCustomID = "null", mConfigSection = "null";
	public Dyes mColor = Dyes._NULL;
	public short mMeltingPoint = 0, mBlastFurnaceTemp = 0, mGasTemp = 0;
	public int mTypes = 0;
	public int durability = 16, mFuelPower = 0, mFuelType = 0, mExtraData = 0, mOreValue = 0, mOreMultiplier = 1, mByProductMultiplier = 1, mSmeltingMultiplier = 1;
	public int mDensityMultiplier = 1, mDensityDivider = 1;
	public long mDensity = M;
	public Element mElement = null;
	public Material mDirectSmelting = this, mOreReplacement = this, mMacerateInto = this, mSmeltInto = this, mArcSmeltInto = this, mHandleMaterial = this;
	public byte toolQuality = 0;
	public boolean mHasParentMod = true, mHasPlasma = false, mHasGas = false, mCustomOre = false;
	public Fluid mFluid = null, mGas = null, mPlasma = null;

	public static void init() {
		new ProcessingConfig();
		if (!GT_Mod.gregtechproxy.mEnableAllMaterial) new ProcessingModSupport();
		for (IMaterialHandler aRegistrator : mMaterialHandlers) {
			aRegistrator.onMaterialInit(); //This is where addon mods can add/manipulate materials
		}
		initMaterialProperties(); //No more material addition or manipulation should be done past this point!
		MATERIALS_ARRAY = MATERIALS_MAP.values().toArray(new Material[MATERIALS_MAP.size()]); //Generate standard object array. This is a lot faster to loop over.
		if (!GT_Mod.gregtechproxy.mEnableAllComponents) OrePrefixes.initMaterialComponents();
		for (Material aMaterial : MATERIALS_ARRAY) {
			if (aMaterial.mMetaItemSubID >= 0) {
				if (aMaterial.mMetaItemSubID < 1000) {
					if (aMaterial.mHasParentMod) {
						if (GregTech_API.sGeneratedMaterial[aMaterial.mMetaItemSubID] == null) {
							GregTech_API.sGeneratedMaterial[aMaterial.mMetaItemSubID] = aMaterial;
						} else throw new IllegalArgumentException("The Material Index " + aMaterial.mMetaItemSubID + " for " + aMaterial.mName + " is already used!");
					}
				} else throw new IllegalArgumentException("The Material Index " + aMaterial.mMetaItemSubID + " for " + aMaterial.mName + " is/over the maximum of 1000");
			}
		}
		// Fills empty spaces with materials, causes horrible load times.
        /*for (int i = 0; i < GregTech_API.sGeneratedMaterial.length; i++) {
            if (GregTech_API.sGeneratedMaterial[i] == null) {
                GregTech_API.sGeneratedMaterial[i] = new Material(i, MaterialType.NONE, 1.0F, 0, 2, 1|2|4|8|16|32|64|128, 92, 0, 168, 0, "TestMat" + i, 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL, "testmat");
            }
        }*/
	}

	public static void initMaterialProperties() {
		GT_Mod.gregtechproxy.mChangeHarvestLevels = GregTech_API.sMaterialProperties.get("harvestlevel", "ActivateHarvestLevelChange", false);
		GT_Mod.gregtechproxy.mMaxHarvestLevel = Math.min(15, GregTech_API.sMaterialProperties.get("harvestlevel", "MaxHarvestLevel",7));
		GT_Mod.gregtechproxy.mGraniteHavestLevel = GregTech_API.sMaterialProperties.get("harvestlevel", "GraniteHarvestLevel", 3);
		StringBuilder aConfigPathSB = new StringBuilder();
		for (Material aMaterial : MATERIALS_MAP.values()) { /** The only place where MATERIALS_MAP should be used to loop over all materials. **/
			if (aMaterial != null && aMaterial != Material._NULL && aMaterial != Material.Empty) {
				aConfigPathSB.append("materials.").append(aMaterial.mConfigSection).append(".").append(aMaterial.mCustomOre ? aMaterial.mCustomID : aMaterial.mName);
				String aConfigPath = aConfigPathSB.toString();
				aMaterial.mMetaItemSubID = GregTech_API.sMaterialProperties.get(aConfigPath, "MaterialID", aMaterial.mCustomOre ? -1 : aMaterial.mMetaItemSubID);
				aMaterial.mDefaultLocalName = GregTech_API.sMaterialProperties.get(aConfigPath, "MaterialName", aMaterial.mCustomOre ? "CustomOre" + aMaterial.mCustomID : aMaterial.mDefaultLocalName);
				aMaterial.mMeltingPoint = (short) GregTech_API.sMaterialProperties.get(aConfigPath, "MeltingPoint", aMaterial.mMeltingPoint);
				aMaterial.mBlastFurnaceRequired = GregTech_API.sMaterialProperties.get(aConfigPath, "BlastFurnaceRequired", aMaterial.mBlastFurnaceRequired);
				aMaterial.mBlastFurnaceTemp = (short) GregTech_API.sMaterialProperties.get(aConfigPath, "BlastFurnaceTemp", aMaterial.mBlastFurnaceTemp);
				if (GT_Mod.gregtechproxy.mTEMachineRecipes && aMaterial.mBlastFurnaceRequired && aMaterial.mBlastFurnaceTemp < 1500) GT_ModHandler.ThermalExpansion.addSmelterBlastOre(aMaterial);
				aMaterial.mFuelPower = GregTech_API.sMaterialProperties.get(aConfigPath, "FuelPower", aMaterial.mFuelPower);
				aMaterial.mFuelType = GregTech_API.sMaterialProperties.get(aConfigPath, "FuelType", aMaterial.mFuelType);
				aMaterial.mOreValue = GregTech_API.sMaterialProperties.get(aConfigPath, "OreValue", aMaterial.mOreValue);
				aMaterial.mDensityMultiplier = GregTech_API.sMaterialProperties.get(aConfigPath, "DensityMultiplier", aMaterial.mDensityMultiplier);
				aMaterial.mDensityDivider = GregTech_API.sMaterialProperties.get(aConfigPath, "DensityDivider", aMaterial.mDensityDivider);
				aMaterial.mDensity = (long) GregTech_API.sMaterialProperties.get(aConfigPath, "Density", (M * aMaterial.mDensityMultiplier) / aMaterial.mDensityDivider);
				aMaterial.durability = GregTech_API.sMaterialProperties.get(aConfigPath, "ToolDurability", aMaterial.durability);
				aMaterial.toolSpeed = (float) GregTech_API.sMaterialProperties.get(aConfigPath, "ToolSpeed", aMaterial.toolSpeed);
				aMaterial.toolQuality = (byte) GregTech_API.sMaterialProperties.get(aConfigPath, "ToolQuality", aMaterial.toolQuality);
				//aMaterial.mIconSet = MaterialType.valueOf(GregTech_API.sMaterialProperties.get(aConfigPath.toString(), "IconSet", aMaterial.mIconSet.mSetName));
				aMaterial.mTransparent = GregTech_API.sMaterialProperties.get(aConfigPath, "Transparent", aMaterial.mTransparent);
				String aColor = GregTech_API.sMaterialProperties.get(aConfigPath, "DyeColor", aMaterial.mColor == Dyes._NULL ? "None" : aMaterial.mColor.toString());
				aMaterial.mColor = aColor.equals("None") ? Dyes._NULL : Dyes.get(aColor);
				String[] aRGBA = GregTech_API.sMaterialProperties.get(aConfigPath, "MatRGBA", String.valueOf(aMaterial.mRGBa[0] + "," + aMaterial.mRGBa[1] + "," + aMaterial.mRGBa[2] + "," + aMaterial.mRGBa[3] + ",")).split(",");
				aMaterial.mRGBa[0] = Short.parseShort(aRGBA[0]);
				aMaterial.mRGBa[1] = Short.parseShort(aRGBA[1]);
				aMaterial.mRGBa[2] = Short.parseShort(aRGBA[2]);
				aMaterial.mRGBa[3] = Short.parseShort(aRGBA[3]);
				aMaterial.mTypes = GregTech_API.sMaterialProperties.get(aConfigPath, "MaterialTypes", aMaterial.mCustomOre ? 1|2|4|8|16|32|64|128 : aMaterial.mTypes);
				aMaterial.mUnificatable = GregTech_API.sMaterialProperties.get(aConfigPath, "Unificatable", aMaterial.mUnificatable);
				aMaterial.mChemicalFormula = GregTech_API.sMaterialProperties.get(aConfigPath, "ChemicalFormula", aMaterial.mChemicalFormula);
				aMaterial.mGasTemp = (short) GregTech_API.sMaterialProperties.get(aConfigPath, "GasTemp", aMaterial.mGasTemp);
				aMaterial.setOreMultiplier(GregTech_API.sMaterialProperties.get(aConfigPath, "OreMultiplier", aMaterial.mOreMultiplier));
				aMaterial.setSmeltingMultiplier(GregTech_API.sMaterialProperties.get(aConfigPath, "OreSmeltingMultiplier", aMaterial.mSmeltingMultiplier));
				aMaterial.setByProductMultiplier(GregTech_API.sMaterialProperties.get(aConfigPath, "OreByProductMultiplier", aMaterial.mByProductMultiplier));
				aMaterial.setHeatDamage((float) GregTech_API.sMaterialProperties.get(aConfigPath, "HeatDamage", aMaterial.heatDamage));
				aMaterial.mSmeltInto = MATERIALS_MAP.get(GregTech_API.sMaterialProperties.get(aConfigPath, "MaterialSmeltInto", aMaterial.mSmeltInto.mName));
				aMaterial.mMacerateInto = MATERIALS_MAP.get(GregTech_API.sMaterialProperties.get(aConfigPath, "MaterialMacerateInto", aMaterial.mMacerateInto.mName));
				aMaterial.mArcSmeltInto = MATERIALS_MAP.get(GregTech_API.sMaterialProperties.get(aConfigPath, "MaterialArcSmeltInto", aMaterial.mArcSmeltInto.mName));
				aMaterial.mDirectSmelting = MATERIALS_MAP.get(GregTech_API.sMaterialProperties.get(aConfigPath, "MaterialDirectSmeltInto", aMaterial.mDirectSmelting.mName));
				aMaterial.mHasParentMod = GregTech_API.sMaterialProperties.get(aConfigPath, "HasParentMod", aMaterial.mHasParentMod);
				if (aMaterial.mHasPlasma = GregTech_API.sMaterialProperties.get(aConfigPath, "AddPlasma", aMaterial.mHasPlasma)) GT_Mod.gregtechproxy.addAutogeneratedPlasmaFluid(aMaterial);
				if (aMaterial.mHasGas = GregTech_API.sMaterialProperties.get(aConfigPath, "AddGas", aMaterial.mHasGas)) GT_Mod.gregtechproxy.addFluid(aMaterial.mName.toLowerCase(), aMaterial.mDefaultLocalName, aMaterial, 2, aMaterial.mGasTemp);
				//TODO
				/*aMaterial.toolEnchantmentLevel = (byte) GregTech_API.sMaterialProperties.get(aConfigPath, "EnchantmentLevel", aMaterial.toolEnchantmentLevel);
				String aEnchantmentName = GregTech_API.sMaterialProperties.get(aConfigPath, "Enchantment", aMaterial.toolEnchantment != null ? aMaterial.toolEnchantment.getName() : "");
				if (aMaterial.toolEnchantment != null && !aEnchantmentName.equals(aMaterial.toolEnchantment.getName())) {
					for (int i = 0; i < getEnchantment(enchantmentsList.length; i++) {
						if (aEnchantmentName.equals(getEnchantment(enchantmentsList[i].getName())) aMaterial.toolEnchantment = getEnchantment(enchantmentsList[i]);
					}
				}*/
				/**
				 * Converts the pre-defined list of SubTags from a material into a list of SubTag names for setting/getting to/from the config.
				 * It is then converted to a String[] and finally to a singular String for insertion into the config
				 * If the config string is different from the default, we then want to clear the Material SubTags and insert new ones from the config string.
				 */
				List<String> aSubTags = new ArrayList<>();
				for (SubTag aTag : aMaterial.mSubTags) aSubTags.add(aTag.mName);
				String aDefaultTagString = "," + aSubTags.toString().replace(" ", "").replace("[", "").replace("]", "");
				String aConfigTagString = GregTech_API.sMaterialProperties.get(aConfigPath, "ListSubTags", aDefaultTagString);
				if (!aConfigTagString.equals(aDefaultTagString)) {
					aMaterial.mSubTags.clear();
					if (aConfigTagString.length() > 0) {
						aSubTags = new ArrayList<>(Arrays.asList(aConfigTagString.split(",")));
						for (String aTagString : aSubTags) {
							SubTag aTag = SubTag.sSubTags.get(aTagString);
							if (aTag != null) aMaterial.mSubTags.add(aTag);
						}
					}
				}
				/** Same principal as SubTags **/
				List<String> aOreByProducts = new ArrayList<>();
				for (Material aMat : aMaterial.mOreByProducts) aOreByProducts.add(aMat.mName);
				String aDefaultMatByProString = "," + aOreByProducts.toString().replace(" ", "").replace("[", "").replace("]", "");
				String aConfigMatByProString = GregTech_API.sMaterialProperties.get(aConfigPath, "ListMaterialByProducts", aDefaultMatByProString);
				if (!aConfigMatByProString.equals(aDefaultMatByProString)) {
					aMaterial.mOreByProducts.clear();
					if (aConfigMatByProString.length() > 0) {
						aOreByProducts = new ArrayList<>(Arrays.asList(aConfigMatByProString.split(",")));
						for (String aMaterialString : aOreByProducts) {
							Material aMat = MATERIALS_MAP.get(aMaterialString);
							if (aMat != null) aMaterial.mOreByProducts.add(aMat);
						}
					}
				}
				/** Same principal as SubTags **/
				List<String> aOreReRegistrations = new ArrayList<>();
				for (Material aMat : aMaterial.mOreReRegistrations) aOreReRegistrations.add(aMat.mName);
				String aDefaultMatReRegString = "," + aOreReRegistrations.toString().replace(" ", "").replace("[", "").replace("]", "");
				String aConfigMatMatReRegString = GregTech_API.sMaterialProperties.get(aConfigPath, "ListMaterialReRegistrations", aDefaultMatReRegString);
				if (!aConfigMatMatReRegString.equals(aDefaultMatReRegString)) {
					aMaterial.mOreReRegistrations.clear();
					if (aConfigMatMatReRegString.length() > 0) {
						aOreReRegistrations = new ArrayList<>(Arrays.asList(aConfigMatMatReRegString.split(",")));
						for (String aMaterialString : aOreReRegistrations) {
							Material aMat = MATERIALS_MAP.get(aMaterialString);
							if (aMat != null) aMaterial.mOreReRegistrations.add(aMat);
						}
					}
				}
				/** Same principal as SubTags but with two values **/
				List<String> aAspects = new ArrayList<>();
				ArrayList<String> aAspectAmounts = new ArrayList<>();
				for (TC_Aspects.TC_AspectStack aAspectStack : aMaterial.mAspects) {
					aAspects.add(aAspectStack.mAspect.toString());
					aAspectAmounts.add(String.valueOf(aAspectStack.mAmount));
				}
				String aDefaultAspectString = "," + aAspects.toString().replace(" ", "").replace("[", "").replace("]", "");
				String aDefaultAspectAmountString = "," + aAspectAmounts.toString().replace(" ", "").replace("[", "").replace("]", "");
				String aConfigAspectString = GregTech_API.sMaterialProperties.get(aConfigPath, "ListTCAspects", aDefaultAspectString);
				String aConfigAspectAmountString = GregTech_API.sMaterialProperties.get(aConfigPath, "ListTCAspectAmounts", aDefaultAspectAmountString);
				if (!aConfigAspectString.equals(aDefaultAspectString) || !aConfigAspectAmountString.equals(aDefaultAspectAmountString)) {
					aMaterial.mAspects.clear();
					if (aConfigAspectString.length() > 0) {
						aAspects = new ArrayList<>(Arrays.asList(aConfigAspectString.split(",")));
						for (int i = 0; i < aAspects.size(); i++) {
							String aAspectString = aAspects.get(i);
							long aAspectAmount = Long.parseLong(aAspectAmounts.get(i));
							TC_Aspects.TC_AspectStack aAspectStack = new TC_Aspects.TC_AspectStack(TC_Aspects.valueOf(aAspectString), aAspectAmount);
							if (aAspectStack != null) aMaterial.mAspects.add(aAspectStack);
						}
					}
				}
				/** Moved the harvest level changes from GT_Mod to have less things iterating over MATERIALS_ARRAY **/
				if (GT_Mod.gregtechproxy.mChangeHarvestLevels && aMaterial.toolQuality > 0 && aMaterial.mMetaItemSubID < GT_Mod.gregtechproxy.mHarvestLevel.length && aMaterial.mMetaItemSubID >= 0) {
					GT_Mod.gregtechproxy.mHarvestLevel[aMaterial.mMetaItemSubID] = GregTech_API.sMaterialProperties.get(aConfigPath, "HarvestLevel", aMaterial.toolQuality);
				}
				/** Moved from GT_Proxy? (Not sure)**/
				aMaterial.mHandleMaterial = (aMaterial == Desh ? aMaterial.mHandleMaterial : aMaterial == Diamond || aMaterial == Thaumium ? Wood : aMaterial.contains(SubTag.BURNING) ? Blaze : aMaterial.contains(SubTag.MAGICAL) && aMaterial.contains(SubTag.CRYSTAL) && Loader.isModLoaded(GT_Values.MOD_ID_TC) ? Thaumium : aMaterial.getMass() > Element.Tc.getMass() * 2 ? TungstenSteel : aMaterial.getMass() > Element.Tc.getMass() ? Steel : Wood);
			}
			aConfigPathSB.setLength(0);
		}
	}

	public Material(int aMetaItemSubID, MaterialType aIconSet, float aToolSpeed, int aDurability, int aToolQuality, boolean aUnificatable, String aName, String aDefaultLocalName) {
		this(aMetaItemSubID, aIconSet, aToolSpeed, aDurability, aToolQuality, aUnificatable, aName, aDefaultLocalName, "ore", false, "null");
	}

	public Material(int aMetaItemSubID, MaterialType aIconSet, float aToolSpeed, int aDurability, int aToolQuality, boolean aUnificatable, String aName, String aDefaultLocalName, String aConfigSection, boolean aCustomOre, String aCustomID) {
		mMetaItemSubID = aMetaItemSubID;
		mDefaultLocalName = aDefaultLocalName;
		mName = aName;
		MATERIALS_MAP.put(mName, this);
		mCustomOre = aCustomOre;
		mCustomID = aCustomID;
		mConfigSection = aConfigSection;
		mUnificatable = aUnificatable;
		durability = aDurability;
		toolSpeed = aToolSpeed;
		toolQuality = (byte) aToolQuality;
		mMaterialInto = this;
		mIconSet = aIconSet;
	}

	public Material(Material aMaterialInto, boolean aReRegisterIntoThis) {
		mUnificatable = false;
		mDefaultLocalName = aMaterialInto.mDefaultLocalName;
		mName = aMaterialInto.mName;
		mMaterialInto = aMaterialInto.mMaterialInto;
		if (aReRegisterIntoThis) mMaterialInto.mOreReRegistrations.add(this);
		mChemicalFormula = aMaterialInto.mChemicalFormula;
		mMetaItemSubID = -1;
		mIconSet = MaterialType.NONE;
	}

	public Material(int aMetaItemSubID, MaterialType aIconSet, float aToolSpeed, int aDurability, int aToolQuality, int aTypes, int aR, int aG, int aB, int aA, String aName, String aDefaultLocalName, int aFuelType, int aFuelPower, int aMeltingPoint, int aBlastFurnaceTemp, boolean aBlastFurnaceRequired, boolean aTransparent, int aOreValue, int aDensityMultiplier, int aDensityDivider, Dyes aColor) {
		this(aMetaItemSubID, aIconSet, aToolSpeed, aDurability, aToolQuality, aTypes, aR, aG, aB, aA, aName, aDefaultLocalName, aFuelType, aFuelPower, aMeltingPoint, aBlastFurnaceTemp, aBlastFurnaceRequired, aTransparent, aOreValue, aDensityMultiplier, aDensityDivider, aColor, "ore", false, "null");
	}

	public Material(int aMetaItemSubID, MaterialType aIconSet, float aToolSpeed, int aDurability, int aToolQuality, int aTypes, int aR, int aG, int aB, int aA, String aName, String aDefaultLocalName, int aFuelType, int aFuelPower, int aMeltingPoint, int aBlastFurnaceTemp, boolean aBlastFurnaceRequired, boolean aTransparent, int aOreValue, int aDensityMultiplier, int aDensityDivider, Dyes aColor, String aConfigSection) {
		this(aMetaItemSubID, aIconSet, aToolSpeed, aDurability, aToolQuality, aTypes, aR, aG, aB, aA, aName, aDefaultLocalName, aFuelType, aFuelPower, aMeltingPoint, aBlastFurnaceTemp, aBlastFurnaceRequired, aTransparent, aOreValue, aDensityMultiplier, aDensityDivider, aColor, aConfigSection, false, "null");
	}

	/**
	 * @param aMetaItemSubID        the Sub-ID used in my own MetaItems. Range 0-1000. -1 for no Material
	 * @param aTypes                which kind of Items should be generated. Bitmask as follows:
	 *                              1 = Dusts of all kinds.
	 *                              2 = Dusts, Ingots, Plates, Rods/Sticks, Machine Components and other Metal specific things.
	 *                              4 = Dusts, Gems, Plates, Lenses (if transparent).
	 *                              8 = Dusts, Impure Dusts, crushed Ores, purified Ores, centrifuged Ores etc.
	 *                              16 = Cells
	 *                              32 = Plasma Cells
	 *                              64 = Tool Heads
	 *                              128 = Gears
	 * @param aR,                   aG, aB Color of the Material 0-255 each.
	 * @param aA                    transparency of the Material Texture. 0 = fully visible, 255 = Invisible.
	 * @param aName                 The Name used as Default for localization.
	 * @param aFuelType             Type of Generator to get Energy from this Material.
	 * @param aFuelPower            EU generated. Will be multiplied by 1000, also additionally multiplied by 2 for Gems.
	 * @param aMeltingPoint         Used to determine the smelting Costs in Furnii.
	 * @param aBlastFurnaceTemp     Used to determine the needed Heat capactiy Costs in Blast Furnii.
	 * @param aBlastFurnaceRequired If this requires a Blast Furnace.
	 * @param aColor                Vanilla MC Wool Color which comes the closest to this.
	 */
	public Material(int aMetaItemSubID, MaterialType aIconSet, float aToolSpeed, int aDurability, int aToolQuality, int aTypes, int aR, int aG, int aB, int aA, String aName, String aDefaultLocalName, int aFuelType, int aFuelPower, int aMeltingPoint, int aBlastFurnaceTemp, boolean aBlastFurnaceRequired, boolean aTransparent, int aOreValue, int aDensityMultiplier, int aDensityDivider, Dyes aColor, String aConfigSection, boolean aCustomOre, String aCustomID) {
		this(aMetaItemSubID, aIconSet, aToolSpeed, aDurability, aToolQuality, true, aName, aDefaultLocalName, aConfigSection, aCustomOre, aCustomID);
		mMeltingPoint = (short) aMeltingPoint;
		mBlastFurnaceRequired = aBlastFurnaceRequired;
		mBlastFurnaceTemp = (short) aBlastFurnaceTemp;
		mTransparent = aTransparent;
		mFuelPower = aFuelPower;
		mFuelType = aFuelType;
		mOreValue = aOreValue;
		mDensityMultiplier = aDensityMultiplier;
		mDensityDivider = aDensityDivider;
		mDensity = (M * aDensityMultiplier) / aDensityDivider;
		mColor = aColor;
		mRGBa[0] = mMoltenRGBa[0] = (short) aR;
		mRGBa[1] = mMoltenRGBa[1] = (short) aG;
		mRGBa[2] = mMoltenRGBa[2] = (short) aB;
		mRGBa[3] = mMoltenRGBa[3] = (short) aA;
		mTypes = aTypes;
		if (mColor != null) add(SubTag.HAS_COLOR);
		if (mTransparent) add(SubTag.TRANSPARENT);
		if ((mTypes & 2) != 0) add(SubTag.SMELTING_TO_FLUID);
	}

	public Material(int aMetaItemSubID, MaterialType aIconSet, float aToolSpeed, int aDurability, int aToolQuality, int aTypes, int aR, int aG, int aB, int aA, String aName, String aDefaultLocalName, int aFuelType, int aFuelPower, int aMeltingPoint, int aBlastFurnaceTemp, boolean aBlastFurnaceRequired, boolean aTransparent, int aOreValue, int aDensityMultiplier, int aDensityDivider, Dyes aColor, List<TC_Aspects.TC_AspectStack> aAspects) {
		this(aMetaItemSubID, aIconSet, aToolSpeed, aDurability, aToolQuality, aTypes, aR, aG, aB, aA, aName, aDefaultLocalName, aFuelType, aFuelPower, aMeltingPoint, aBlastFurnaceTemp, aBlastFurnaceRequired, aTransparent, aOreValue, aDensityMultiplier, aDensityDivider, aColor);
		mAspects.addAll(aAspects);
	}

	public Material(int aMetaItemSubID, MaterialType aIconSet, float aToolSpeed, int aDurability, int aToolQuality, int aTypes, int aR, int aG, int aB, int aA, String aName, String aDefaultLocalName, int aFuelType, int aFuelPower, int aMeltingPoint, int aBlastFurnaceTemp, boolean aBlastFurnaceRequired, boolean aTransparent, int aOreValue, int aDensityMultiplier, int aDensityDivider, Dyes aColor, Element aElement, List<TC_Aspects.TC_AspectStack> aAspects) {
		this(aMetaItemSubID, aIconSet, aToolSpeed, aDurability, aToolQuality, aTypes, aR, aG, aB, aA, aName, aDefaultLocalName, aFuelType, aFuelPower, aMeltingPoint, aBlastFurnaceTemp, aBlastFurnaceRequired, aTransparent, aOreValue, aDensityMultiplier, aDensityDivider, aColor);
		mElement = aElement;
		mElement.mLinkedMaterial.add(this);
		if (aElement == Element._NULL) {
			mChemicalFormula = "Empty";
		} else {
			mChemicalFormula = aElement.toString();
			mChemicalFormula = mChemicalFormula.replaceAll("_", "-");
		}
		mAspects.addAll(aAspects);
	}

	public Material(int aMetaItemSubID, MaterialType aIconSet, float aToolSpeed, int aDurability, int aToolQuality, int aTypes, int aR, int aG, int aB, int aA, String aName, String aDefaultLocalName, int aFuelType, int aFuelPower, int aMeltingPoint, int aBlastFurnaceTemp, boolean aBlastFurnaceRequired, boolean aTransparent, int aOreValue, int aDensityMultiplier, int aDensityDivider, Dyes aColor, int aExtraData, List<MaterialStack> aMaterialList) {
		this(aMetaItemSubID, aIconSet, aToolSpeed, aDurability, aToolQuality, aTypes, aR, aG, aB, aA, aName, aDefaultLocalName, aFuelType, aFuelPower, aMeltingPoint, aBlastFurnaceTemp, aBlastFurnaceRequired, aTransparent, aOreValue, aDensityMultiplier, aDensityDivider, aColor, aExtraData, aMaterialList, null);
	}

	public Material(int aMetaItemSubID, MaterialType aIconSet, float aToolSpeed, int aDurability, int aToolQuality, int aTypes, int aR, int aG, int aB, int aA, String aName, String aDefaultLocalName, int aFuelType, int aFuelPower, int aMeltingPoint, int aBlastFurnaceTemp, boolean aBlastFurnaceRequired, boolean aTransparent, int aOreValue, int aDensityMultiplier, int aDensityDivider, Dyes aColor, int aExtraData, List<MaterialStack> aMaterialList, List<TC_Aspects.TC_AspectStack> aAspects) {
		this(aMetaItemSubID, aIconSet, aToolSpeed, aDurability, aToolQuality, aTypes, aR, aG, aB, aA, aName, aDefaultLocalName, aFuelType, aFuelPower, aMeltingPoint, aBlastFurnaceTemp, aBlastFurnaceRequired, aTransparent, aOreValue, aDensityMultiplier, aDensityDivider, aColor);
		mExtraData = aExtraData;
		mMaterialList.addAll(aMaterialList);
		mChemicalFormula = "";
		for (MaterialStack tMaterial : mMaterialList) mChemicalFormula += tMaterial.toString();
		mChemicalFormula = mChemicalFormula.replaceAll("_", "-");

		int tAmountOfComponents = 0, tMeltingPoint = 0;
		for (MaterialStack tMaterial : mMaterialList) {
			tAmountOfComponents += tMaterial.mAmount;
			if (tMaterial.mMaterial.mMeltingPoint > 0)
				tMeltingPoint += tMaterial.mMaterial.mMeltingPoint * tMaterial.mAmount;
			if (aAspects == null)
				for (TC_Aspects.TC_AspectStack tAspect : tMaterial.mMaterial.mAspects) tAspect.addToAspectList(mAspects);
		}

		if (mMeltingPoint < 0) mMeltingPoint = (short) (tMeltingPoint / tAmountOfComponents);

		tAmountOfComponents *= aDensityMultiplier;
		tAmountOfComponents /= aDensityDivider;
		if (aAspects == null) for (TC_Aspects.TC_AspectStack tAspect : mAspects)
			tAspect.mAmount = Math.max(1, tAspect.mAmount / Math.max(1, tAmountOfComponents));
		else mAspects.addAll(aAspects);
	}

	/**
	 * This is for keeping compatibility with addons mods (Such as TinkersGregworks etc) that looped over the old materials enum
	 */
	@Deprecated
	public String name() {
		return mName;
	}

	/**
	 * This is for keeping compatibility with addons mods (Such as TinkersGregworks etc) that looped over the old materials enum
	 */
	@Deprecated
	public static Material valueOf(String aMaterialName) {
		return getMaterialMap().get(aMaterialName);
	}

	/**
	 * This is for keeping compatibility with addons mods (Such as TinkersGregworks etc) that looped over the old materials enum
	 */
	public static Material[] values() {
		return MATERIALS_ARRAY;
	}

	/**
	 * This should only be used for getting a Material by its name as a String. Do not loop over this map, use values().
	 */
	public static Map<String, Material> getMaterialMap() {
		return MATERIALS_MAP;
	}

	public static Material get(String aMaterialName) {
		Material aMaterial = getMaterialMap().get(aMaterialName);
		if (aMaterial != null) return aMaterial;
		return Material._NULL;
	}

	public static Material getRealMaterial(String aMaterialName) {
		return get(aMaterialName).mMaterialInto;
	}

	public int getColorInt() {
		return ((mRGBa[3] & 0xFF) << 24) |
				((mRGBa[0] & 0xFF) << 16) |
				((mRGBa[1] & 0xFF) << 8)  |
				((mRGBa[2] & 0xFF));
	}

	public boolean isRadioactive() {
		if (mElement != null) return mElement.mHalfLifeSeconds >= 0;
		for (MaterialStack tMaterial : mMaterialList) if (tMaterial.mMaterial.isRadioactive()) return true;
		return false;
	}

	public long getProtons() {
		if (mElement != null) return mElement.getProtons();
		if (mMaterialList.size() <= 0) return Element.Tc.getProtons();
		long rAmount = 0, tAmount = 0;
		for (MaterialStack tMaterial : mMaterialList) {
			tAmount += tMaterial.mAmount;
			rAmount += tMaterial.mAmount * tMaterial.mMaterial.getProtons();
		}
		return (getDensity() * rAmount) / (tAmount * M);
	}

	public long getNeutrons() {
		if (mElement != null) return mElement.getNeutrons();
		if (mMaterialList.size() <= 0) return Element.Tc.getNeutrons();
		long rAmount = 0, tAmount = 0;
		for (MaterialStack tMaterial : mMaterialList) {
			tAmount += tMaterial.mAmount;
			rAmount += tMaterial.mAmount * tMaterial.mMaterial.getNeutrons();
		}
		return (getDensity() * rAmount) / (tAmount * M);
	}

	public long getMass() {
		if (mElement != null) return mElement.getMass();
		if (mMaterialList.size() <= 0) return Element.Tc.getMass();
		long rAmount = 0, tAmount = 0;
		for (MaterialStack tMaterial : mMaterialList) {
			tAmount += tMaterial.mAmount;
			rAmount += tMaterial.mAmount * tMaterial.mMaterial.getMass();
		}
		return (getDensity() * rAmount) / (tAmount * M);
	}

	public long getDensity() {
		return mDensity;
	}

	public String getToolTip() {
		return getToolTip(1, false);
	}

	public String getToolTip(boolean aShowQuestionMarks) {
		return getToolTip(1, aShowQuestionMarks);
	}

	public String getToolTip(long aMultiplier) {
		return getToolTip(aMultiplier, false);
	}

	public String getToolTip(long aMultiplier, boolean aShowQuestionMarks) {
		if (!aShowQuestionMarks && mChemicalFormula.equals("?")) return "";
		if (aMultiplier >= M * 2 && !mMaterialList.isEmpty()) {
			return ((mElement != null || (mMaterialList.size() < 2 && mMaterialList.get(0).mAmount == 1)) ? mChemicalFormula : "(" + mChemicalFormula + ")") + aMultiplier;
		}
		return mChemicalFormula;
	}

	/**
	 * Adds a Class implementing IMaterialRegistrator to the master list
	 */
	public static boolean add(IMaterialHandler aRegistrator) {
		if (aRegistrator == null) return false;
		return mMaterialHandlers.add(aRegistrator);
	}

	/**
	 * Adds an ItemStack to this Material.
	 */
	public Material add(ItemStack aStack) {
		if (aStack != null && !contains(aStack)) mMaterialItems.add(aStack);
		return this;
	}

	/**
	 * This is used to determine if any of the ItemStacks belongs to this Material.
	 */
	public boolean contains(ItemStack... aStacks) {
		if (aStacks == null || aStacks.length <= 0) return false;
		for (ItemStack tStack : mMaterialItems)
			for (ItemStack aStack : aStacks)
				if (GT_Utility.areStacksEqual(aStack, tStack, !tStack.hasTagCompound())) return true;
		return false;
	}

	/**
	 * This is used to determine if an ItemStack belongs to this Material.
	 */
	public boolean remove(ItemStack aStack) {
		if (aStack == null) return false;
		boolean temp = false;
		int mMaterialItems_sS=mMaterialItems.size();
		for (int i = 0; i < mMaterialItems_sS; i++)
			if (GT_Utility.areStacksEqual(aStack, mMaterialItems.get(i))) {
				mMaterialItems.remove(i--);
				temp = true;
			}
		return temp;
	}

	/**
	 * Adds a SubTag to this Material
	 */
	@Override
	public ISubTagContainer add(SubTag... aTags) {
		if (aTags != null) for (SubTag aTag : aTags)
			if (aTag != null && !contains(aTag)) {
				aTag.addContainerToList(this);
				mSubTags.add(aTag);
			}
		return this;
	}

	/**
	 * If this Material has this exact SubTag
	 */
	@Override
	public boolean contains(SubTag aTag) {
		return mSubTags.contains(aTag);
	}

	/**
	 * Removes a SubTag from this Material
	 */
	@Override
	public boolean remove(SubTag aTag) {
		return mSubTags.remove(aTag);
	}

	/**
	 * Sets the Heat Damage for this Material (negative = frost)
	 */
	public Material setHeatDamage(float aHeatDamage) {
		heatDamage = aHeatDamage;
		return this;
	}

	/**
	 * Adds a Material to the List of Byproducts when grinding this Ore.
	 * Is used for more precise Ore grinding, so that it is possible to choose between certain kinds of Material.
	 */
	public Material addOreByProduct(Material aMaterial) {
		if (!mOreByProducts.contains(aMaterial.mMaterialInto)) mOreByProducts.add(aMaterial.mMaterialInto);
		return this;
	}

	/**
	 * Adds multiple Material to the List of Byproducts when grinding this Ore.
	 * Is used for more precise Ore grinding, so that it is possible to choose between certain kinds of Material.
	 */
	public Material addOreByProducts(Material... aMaterial) {
		for (Material tMaterial : aMaterial) if (tMaterial != null) addOreByProduct(tMaterial);
		return this;
	}

	/**
	 * If this Ore gives multiple drops of its Main Material.
	 * Lapis Ore for example gives about 6 drops.
	 */
	public Material setOreMultiplier(int aOreMultiplier) {
		if (aOreMultiplier > 0) mOreMultiplier = aOreMultiplier;
		return this;
	}

	/**
	 * If this Ore gives multiple drops of its Byproduct Material.
	 */
	public Material setByProductMultiplier(int aByProductMultiplier) {
		if (aByProductMultiplier > 0) mByProductMultiplier = aByProductMultiplier;
		return this;
	}

	/**
	 * If this Ore gives multiple drops of its Main Material.
	 * Lapis Ore for example gives about 6 drops.
	 */
	public Material setSmeltingMultiplier(int aSmeltingMultiplier) {
		if (aSmeltingMultiplier > 0) mSmeltingMultiplier = aSmeltingMultiplier;
		return this;
	}

	/**
	 * This Ore should be smolten directly into an Ingot of this Material instead of an Ingot of itself.
	 */
	public Material setDirectSmelting(Material aMaterial) {
		if (aMaterial != null) mDirectSmelting = aMaterial.mMaterialInto.mDirectSmelting;
		return this;
	}

	/**
	 * This Material should be the Main Material this Ore gets ground into.
	 * Example, Chromite giving Chrome or Tungstate giving Tungsten.
	 */
	public Material setOreReplacement(Material aMaterial) {
		if (aMaterial != null) mOreReplacement = aMaterial.mMaterialInto.mOreReplacement;
		return this;
	}

	/**
	 * This Material smelts always into an instance of aMaterial. Used for Magnets.
	 */
	public Material setSmeltingInto(Material aMaterial) {
		if (aMaterial != null) mSmeltInto = aMaterial.mMaterialInto.mSmeltInto;
		return this;
	}

	/**
	 * This Material arc smelts always into an instance of aMaterial. Used for Wrought Iron.
	 */
	public Material setArcSmeltingInto(Material aMaterial) {
		if (aMaterial != null) mArcSmeltInto = aMaterial.mMaterialInto.mArcSmeltInto;
		return this;
	}

	/**
	 * This Material macerates always into an instance of aMaterial.
	 */
	public Material setMaceratingInto(Material aMaterial) {
		if (aMaterial != null) mMacerateInto = aMaterial.mMaterialInto.mMacerateInto;
		return this;
	}

	public Material setEnchantmentForTools(Enchantment aEnchantment, int aEnchantmentLevel) {
		toolEnchantment = aEnchantment;
		toolEnchantmentLevel = (byte) aEnchantmentLevel;
		return this;
	}

	public Material setEnchantmentForArmors(Enchantment aEnchantment, int aEnchantmentLevel) {
		mEnchantmentArmors = aEnchantment;
		mEnchantmentArmorsLevel = (byte) aEnchantmentLevel;
		return this;
	}

	public FluidStack getSolid(long aAmount) {
		if (mSolid == null) return null;
		return new FluidStack(mSolid, (int) aAmount);
	}

	public FluidStack getFluid(long aAmount) {
		if (mFluid == null) return null;
		return new FluidStack(mFluid, (int) aAmount);
	}

	public FluidStack getGas(long aAmount) {
		if (mGas == null) return null;
		return new FluidStack(mGas, (int) aAmount);
	}

	public FluidStack getPlasma(long aAmount) {
		if (mPlasma == null) return null;
		return new FluidStack(mPlasma, (int) aAmount);
	}

	public FluidStack getMolten(long aAmount) {
		if (mStandardMoltenFluid == null) return null;
		return new FluidStack(mStandardMoltenFluid, (int) aAmount);
	}

	public int getRGBA() {
		return mRGBa;
	}

	@Override
	public String toString() {
		return this.mName;
	}

	@Override
	public int compareTo(Material o) {
		return Integer.compare(mMetaItemSubID, o.mMetaItemSubID);
	}
}