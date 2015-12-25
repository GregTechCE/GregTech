package gregtech.common.items.armor;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import gregtech.api.damagesources.GT_DamageSources;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_Utility;
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

	public boolean fullArmor;
	public boolean fullRadiationDef;
	public boolean fullElectricDef;

	public float fallDef;
	public float physicalDef;
	public float projectileDef;
	public float fireDef;
	public float magicDef;
	public float explosionDef;
	public float radiationDef;
	public float electricDef;
	public float witherDef;

	public float thorns;
	public float thornsSingle;
	public int magnet;
	public int magnetSingle;

	public int partsCharge;
	public int maxCharge;
	public int charge;
	public boolean thaumicGoggles;
	public boolean nightVision;
	public boolean potionInjector;
	public boolean autoFeeder;

	public FluidStack fluid;
	public int tankCap;

	public int weight;
	public int maxWeight;
	public int processingPower;
	public int processingPowerUsed;
	public int partProcessing;
	public int partProcessingUsed;

	public int motorPower;
	public int motorEUusage;
	public int pistonJumpboost;
	public int pistonEUusage;
	public int electrolyzerProd;
	public int electrolyzerEUusage;
	public int fieldGenCap;
	public int fieldGenEUusage;

	public int jetpackMaxWeight;
	public int antiGravMaxWeight;

	public ArmorData(EntityPlayer player, ItemStack stack, int type, int tier) {
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
		float[] def = ArmorCalculation.calculateArmor(parts);
		weight = (int) def[0];
		physicalDef = def[1];
		projectileDef = def[2];
		fireDef = def[3];
		magicDef = def[4];
		explosionDef = def[5];
		radiationDef = def[6];
		electricDef = def[7];
		witherDef = def[8];
		fallDef = def[9];
		thornsSingle = def[10];
		magnetSingle = (int) def[11];
		if (armorTier > 0) {
			partsCharge = (int) def[12];
			thaumicGoggles = def[13] > 0.5f;
			nightVision = def[14] > 0.5f;
			tankCap = (int) def[15]; // tankCap
			motorPower = (int) def[16]; // motorPower
			motorEUusage = (int) def[17]; // motorEU
			pistonJumpboost = (int) def[18]; // pistonPower
			fallDef += pistonJumpboost;
			pistonEUusage = (int) def[19]; // pistonEU
			electrolyzerProd = (int) def[20]; // ElectrolyzerPower
			electrolyzerEUusage = (int) def[21]; // ElectrolyzerEU
			fieldGenCap = (int) def[22]; // FieldEmmiterPower
			fieldGenEUusage = (int) def[23]; // FieldEmmiterEU
			jetpackMaxWeight = (int) def[24] + (int) def[26]; // JetpackFuelPower// JetpackEUPower
			def[25] = 0; // FuelUsage
			def[27] = 0; // JetpackEU
			antiGravMaxWeight = (int) def[28]; // AntiGravPower
			def[29] = 0; // AntiGravEU
			if (armorTier == 2) {
				partProcessing = (int) def[30] + 500; // ProcessingPower
			} else {
				partProcessing = (int) def[30] + 300; // ProcessingPower
			}
			partProcessingUsed = (int) def[31]; // ProcessingPowerUsed
		}
		updateTooltip();
		return this;
	}

	public void updateTooltip() {
		List<String> tagList = new ArrayList<String>();
		String tmp2 = "";
		if (maxWeight > 4000) {
			tmp2 = " " + GT_LanguageManager.getTranslation("hav");
		}
		if (maxCharge != 0) {
			DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
			DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
			symbols.setGroupingSeparator(' ');
			if (type == 0) {
				if (thaumicGoggles) {
					tagList.add(GT_LanguageManager.getTranslation("thaum"));
				}
				if (nightVision) {
					tagList.add(GT_LanguageManager.getTranslation("night"));
				}
			}
			tagList.add("EU: " + formatter.format(charge + partsCharge));
			if (type == 2) {
				tagList.add(GT_LanguageManager.getTranslation("jum") + ": " + pistonJumpboost / 3 + "m");
			}
			if (type == 2 && pistonJumpboost > 0) {
				tagList.add(GT_LanguageManager.getTranslation("uph"));
			}
			if (type == 2 && motorPower > 0) {
				tagList.add(GT_LanguageManager.getTranslation("speass") + ": " + motorPower);
				tagList.add(GT_LanguageManager.getTranslation("motuse") + ": " + motorEUusage + " EU");
				if (maxWeight > 4000) {
					tagList.add(GT_LanguageManager.getTranslation("over"));
				}
			}
			tagList.add(GT_LanguageManager.getTranslation("pro1") + " " + partProcessing + " " + GT_LanguageManager.getTranslation("pro3"));
			tagList.add(GT_LanguageManager.getTranslation("pro2") + ": " + partProcessingUsed + " " + GT_LanguageManager.getTranslation("pro3"));
			if (type == 0 && electrolyzerProd > 0) {
				tagList.add(GT_LanguageManager.getTranslation("elec1") + " " + electrolyzerProd / 2 + GT_LanguageManager.getTranslation("elec2"));
			}
			if (tankCap > 0) {
				if (fluid != null) {
					tagList.add(GT_LanguageManager.getTranslation("tank") + ": " + fluid.getLocalizedName() + " " + fluid.amount + "L (" + tankCap + ")");
				} else {
					tagList.add(GT_LanguageManager.getTranslation("tankcap") + ": " + tankCap);
				}
			}
		}
		tagList.add(GT_LanguageManager.getTranslation("weight") + ": " + weight + tmp2);
		tagList.add(GT_LanguageManager.getTranslation("phydef") + ": " + (Math.round(physicalDef * 1000) / 10.0) + "%");
		tagList.add(GT_LanguageManager.getTranslation("prodef") + ": " + (Math.round(projectileDef * 1000) / 10.0) + "%");
		tagList.add(GT_LanguageManager.getTranslation("firedef") + ": " + (Math.round(fireDef * 1000) / 10.0) + "%");
		tagList.add(GT_LanguageManager.getTranslation("magdef") + ": " + (Math.round(magicDef * 1000) / 10.0) + "%");
		tagList.add(GT_LanguageManager.getTranslation("expdef") + ": " + (Math.round(explosionDef * 1000) / 10.0) + "%");
		if (fallDef > 0 && type == 3) {
			tagList.add(GT_LanguageManager.getTranslation("abs1") + " " + fallDef + GT_LanguageManager.getTranslation("abs2"));
		}
		if (thorns > 0) {
			tagList.add(GT_LanguageManager.getTranslation("thorns") + ": " + (int) thorns);
		}
		if (magnet > 0) {
			tagList.add(GT_LanguageManager.getTranslation("magnet") + ": " + magnet + "m");
		}
		if (fullRadiationDef) {
			tagList.add(GT_LanguageManager.getTranslation("radim"));
		} else {
			if (radiationDef > 0.01d) {
				tagList.add(GT_LanguageManager.getTranslation("raddef") + ": " + (Math.round(radiationDef * 1000) / 10.0) + "%");
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
					fluid = ArmorCalculation.getFluid(tmp2.mInvArmor.parts, tankCap);
				}
				if (maxCharge > 0 && charge < maxCharge) {
					int loaded = ArmorCalculation.deChargeBatterys(tmp2.mInvArmor.parts, maxCharge - charge);
					charge = charge + loaded;
					tmp.data.partsCharge -= loaded;

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
			fullArmor = true;
		} else {
			fullArmor = false;
		}
		fullRadiationDef = fullArmor && helmet.radiationDef > 0.9f && chestplate.radiationDef > 0.9f && leggings.radiationDef > 0.9f && boots.radiationDef > 0.9f;
		fullElectricDef = fullArmor && helmet.electricDef > 0.9f && chestplate.electricDef > 0.9f && leggings.electricDef > 0.9f && boots.electricDef > 0.9f;
		magnet = 0;
		thorns = 0;
		processingPower = 0;
		processingPowerUsed = 0;
		if (helmet != null) {
			magnet += helmet.magnetSingle;
			thorns += helmet.thornsSingle;
			processingPower += helmet.partProcessing;
			processingPowerUsed += helmet.partProcessingUsed;
		}
		if (chestplate != null) {
			magnet += chestplate.magnetSingle;
			thorns += chestplate.thornsSingle;
			processingPower += chestplate.partProcessing;
			processingPowerUsed += chestplate.partProcessingUsed;
		}
		if (leggings != null) {
			magnet += leggings.magnetSingle;
			thorns += leggings.thornsSingle;
			processingPower += leggings.partProcessing;
			processingPowerUsed += leggings.partProcessingUsed;
		}
		if (boots != null) {
			magnet += boots.magnetSingle;
			thorns += boots.thornsSingle;
			processingPower += boots.partProcessing;
			processingPowerUsed += boots.partProcessingUsed;
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
			maxWeight = helmet.weight;
		}
		if (chestplate != null) {
			maxWeight += chestplate.weight;
		}
		if (leggings != null) {
			maxWeight += leggings.weight;
		}
		if (boots != null) {
			maxWeight += boots.weight;
		}
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
