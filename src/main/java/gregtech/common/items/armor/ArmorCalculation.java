package gregtech.common.items.armor;

import cpw.mods.fml.common.Loader;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.objects.ItemData;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import thaumcraft.api.nodes.IRevealer;

public class ArmorCalculation {
	public static float[] calculateArmor(ItemStack[] parts) {
		float[] def = new float[32];
		def[0] = 0; // Weight
		def[1] = 1; // physical Def
		def[2] = 1; // projectileDef
		def[3] = 1; // fireDef
		def[4] = 1; // magicDef
		def[5] = 1; // explosionDef
		def[6] = 0; // radiationDef
		def[7] = 0; // electricDef
		def[8] = 0; // witherDef
		def[9] = 0; // fallDef
		def[10] = 0; // Thorns
		def[11] = 0; // ItemMagnet
		def[12] = 0; // ItemCharge
		def[13] = 0; // Thaumcraft goggles
		def[14] = 0; // Nightvision
		def[15] = 0; // tankCap
		def[16] = 0; // motorPower
		def[17] = 0; // motorEU
		def[18] = 0; // pistonPower
		def[19] = 0; // pistonEU
		def[20] = 0; // ElectrolyzerPower
		def[21] = 0; // ElectrolyzerEU
		def[22] = 0; // FieldEmmiterPower
		def[23] = 0; // FieldEmmiterEU
		def[24] = 0; // JetpackFuelPower
		def[25] = 0; // FuelUsage
		def[26] = 0; // JetpackEUPower
		def[27] = 0; // JetpackEU
		def[28] = 0; // AntiGravPower
		def[29] = 0; // AntiGravEU
		def[30] = 0; // ProcessingPower
		def[31] = 0; // ProcessingPowerUsed

		if (parts != null) {
			def[12] = 0.0f;
			for (int i = 0; i < parts.length; i++) {
				if (parts[i] != null) {
					ItemData data = GT_OreDictUnificator.getItemData(parts[i]);
					if (data != null && (data.mPrefix == OrePrefixes.plate || data.mPrefix == OrePrefixes.plateAlloy)) {
						// Weight
						def[0] = def[0] + Values.INSTANCE.getValues(data.mMaterial.mMaterial).weight;
						if (data.mPrefix == OrePrefixes.plateAlloy && data.mMaterial.mMaterial == Materials.Iridium) {
							def[0] = def[0] - 20;
						}
						// physicalDef
						float tmp = Values.INSTANCE.getValues(data.mMaterial.mMaterial).physicalDef;
						if (data.mPrefix == OrePrefixes.plateAlloy && data.mMaterial.mMaterial == Materials.Iridium) {
							tmp = 0.27f;
						}
						if (tmp > 0.0f) {
							def[1] = def[1] - (tmp * def[1]);
						}
						// projectileDef
						tmp = Values.INSTANCE.getValues(data.mMaterial.mMaterial).projectileDef;
						if (data.mPrefix == OrePrefixes.plateAlloy && data.mMaterial.mMaterial == Materials.Iridium) {
							tmp = 0.27f;
						}
						if (tmp > 0.0f) {
							def[2] = def[2] - (tmp * def[2]);
						}
						// fireDef
						tmp = Values.INSTANCE.getValues(data.mMaterial.mMaterial).fireDef;
						if (data.mPrefix == OrePrefixes.plateAlloy && data.mMaterial.mMaterial == Materials.Iridium) {
							tmp = 0.25f;
						}
						if (tmp > 0.0f) {
							def[3] = def[3] - (tmp * def[3]);
						}
						// magicDef
						tmp = Values.INSTANCE.getValues(data.mMaterial.mMaterial).magicDef;
						if (data.mPrefix == OrePrefixes.plateAlloy && data.mMaterial.mMaterial == Materials.Iridium) {
							tmp = 0.25f;
						}
						if (tmp > 0.0f) {
							def[4] = def[4] - (tmp * def[4]);
						}
						// explosionDef
						tmp = Values.INSTANCE.getValues(data.mMaterial.mMaterial).explosionDef;
						if (data.mPrefix == OrePrefixes.plateAlloy && data.mMaterial.mMaterial == Materials.Iridium) {
							tmp = 0.27f;
						}
						if (tmp > 0.0f) {
							def[5] = def[5] - (tmp * def[5]);
						}
						if (data.mPrefix == OrePrefixes.plate && data.mMaterial.mMaterial == Materials.Rubber) {
							def[7] = def[7] + 0.25f;
							def[9] = def[9] + 2.0f;
						}
						if (data.mPrefix == OrePrefixes.plate && data.mMaterial.mMaterial == Materials.Lead) {
							def[6] = def[6] + 0.30f;
						}
						if (data.mPrefix == OrePrefixes.plate && data.mMaterial.mMaterial == Materials.Plastic) {
							def[7] = def[7] + 0.25f;
						}
						if (data.mPrefix == OrePrefixes.plate && data.mMaterial.mMaterial == Materials.NeodymiumMagnetic) {
							def[11] = def[11] + 2.0f;
						}
						if (data.mPrefix == OrePrefixes.plate && data.mMaterial.mMaterial == Materials.NetherStar) {
							def[8] = def[8] + 0.20f;
						}
						if (data.mPrefix == OrePrefixes.plate && data.mMaterial.mMaterial == Materials.InfusedFire) {
							def[10] = def[10] + 3.0f;
						}
						if (data.mPrefix == OrePrefixes.plate && data.mMaterial.mMaterial == Materials.InfusedEntropy) {
							def[10] = def[10] + 4.0f;
						}
					} else if (GT_ModHandler.isChargerItem(parts[i])) {
						def[12] = def[12] + (float) ic2.api.item.ElectricItem.manager.getCharge(parts[i]);
						def[0] = (float) (def[0] + Math.pow(ic2.api.item.ElectricItem.manager.getCharge(parts[i]), 0.33f));
					}
					else if (Loader.isModLoaded("Thaumcraft") && parts[i].getItem() instanceof IRevealer) {
						def[13] = 1;
						def[31] += 100;
					}
					else if (parts[i].getItem().getUnlocalizedName().equals("ic2.itemNightvisionGoggles")) {
						def[14] = 1;
						def[31] += 100;
					} else if (parts[i].getItem().getUnlocalizedName().equals("gt.meta.spring")) {// Once readded: GT Motors
						switch (parts[i].getItem().getDamage(parts[i])) {
						case 8630:
							def[16] += 200; // motorPower
							def[17] += 50;
							def[31] += 10;
							break;
						case 8631:
							def[16] += 300; // motorPower
							def[17] += 100;
							def[31] += 20;
							break;
						case 8632:
							def[16] += 400; // motorPower
							def[17] += 200;
							def[31] += 50;
							break;
						case 8633:
							def[16] += 500; // motorPower
							def[17] += 400;
							def[31] += 100;
							break;
						case 8634:
							def[16] += 600; // motorPower
							def[17] += 800;
							def[31] += 200;
							break;
						}
					} else if (parts[i].getItem().getUnlocalizedName().equals("gt.meta.springSmall")) {// Once Readded: GT Electric Pistons
						switch (parts[i].getItem().getDamage(parts[i])) {
						case 8630:
							def[18] += 3;
							def[19] += 200;
							def[31] += 10;
							break;
						case 8631:
							def[18] += 4;
							def[19] += 300;
							def[31] += 20;
							break;
						case 8632:
							def[18] += 5;
							def[19] += 450;
							def[31] += 50;
							break;
						case 8633:
							def[18] += 6;
							def[19] += 800;
							def[31] += 100;
							break;
						case 8634:
							def[18] += 7;
							def[19] += 1600;
							def[31] += 200;
							break;
						}
					} else if (parts[i].getItem().getUnlocalizedName().equals("gt.meta.Electrolyzer")) {// Once Readded: GT Electrolyzer
						switch (parts[i].getItem().getDamage(parts[i])) {
						case 8630:
							def[20] += 10; // ElectrolyzerPower
							def[21] += 1; // ElectrolyzerEU
							def[31] += 50;
							break;
						case 8631:
							def[20] += 20; // ElectrolyzerPower
							def[21] += 4; // ElectrolyzerEU
							def[31] += 100;
							break;
						case 8632:
							def[20] += 40; // ElectrolyzerPower
							def[21] += 16; // ElectrolyzerEU
							def[31] += 150;
							break;
						case 8633:
							def[20] += 80; // ElectrolyzerPower
							def[21] += 64; // ElectrolyzerEU
							def[31] += 200;
							break;
						case 8634:
							def[20] += 160; // ElectrolyzerPower
							def[21] += 256; // ElectrolyzerEU
							def[31] += 250;
							break;
						}
					} else if (parts[i].getItem().equals(ItemList.Cell_Empty.getItem())) {
						def[15] += 8000;
					} else if (parts[i].getItem().getUnlocalizedName().equals("gt.meta.cell")) {// Once Readded: GT Fluid Cells (tank)
						switch (parts[i].getItem().getDamage(parts[i])) {
						case 8630: // steel fluid cell
							def[15] += 16000;
							break;
						case 8631: // tungsten fluid cell
							def[15] += 64000;
							break;
						}
					} else if (parts[i].getItem().getUnlocalizedName().equals("gt.meta.emmiter")) {// Once Readded: GT Field Emmiter
						switch (parts[i].getItem().getDamage(parts[i])) {
						case 8630:
							def[22] += 1; // FieldEmmiterPower
							def[23] += 1; // FieldEmmiterEU
							def[31] += 100;
							break;
						case 8631:
							def[22] += 2; // FieldEmmiterPower
							def[23] += 4; // FieldEmmiterEU
							def[31] += 200;
							break;
						case 8632:
							def[22] += 3; // FieldEmmiterPower
							def[23] += 16; // FieldEmmiterEU
							def[31] += 300;
							break;
						case 8633:
							def[22] += 4; // FieldEmmiterPower
							def[23] += 64; // FieldEmmiterEU
							def[31] += 400;
							break;
						case 8634:
							def[22] += 5; // FieldEmmiterPower
							def[23] += 512; // FieldEmmiterEU
							def[31] += 500;
							break;
						}
					} else if (data !=null && data.mPrefix == OrePrefixes.circuit) {// processing power stuff
						if (data.mMaterial.mMaterial == Materials.Basic) {
							def[30] += 100;
						} else if (data.mMaterial.mMaterial == Materials.Good) {
							def[30] += 200;
						} else if (data.mMaterial.mMaterial == Materials.Advanced) {
							def[30] += 300;
						} else if (data.mMaterial.mMaterial == Materials.Data) {
							def[30] += 400;
						} else if (data.mMaterial.mMaterial == Materials.Elite) {
							def[30] += 500;
						} else if (data.mMaterial.mMaterial == Materials.Master) {
							def[30] += 600;
						}
					} else if (parts[i].getItem().getUnlocalizedName().equals("gte.meta.jetpack")) {// jeptack parts
						switch (parts[i].getItem().getDamage(parts[i])) {
						case 0:
							def[24] += 50; // JetpackFuelPower
							def[25] += 1; // FuelUsage
							def[31] += 10;
							break;
						case 1:
							def[24] += 75; // JetpackFuelPower
							def[25] += 2; // FuelUsage
							def[31] += 20;
							break;
						case 2:
							def[24] += 100; // JetpackFuelPower
							def[25] += 4; // FuelUsage
							def[31] += 30;
							break;
						case 3:
							def[24] += 125; // JetpackFuelPower
							def[25] += 8; // FuelUsage
							def[31] += 40;
							break;
						case 4:
							def[24] += 150; // JetpackFuelPower
							def[25] += 16; // FuelUsage
							def[31] += 50;
							break;
						case 5:
							def[26] += 20; // JetpackEUPower
							def[27] += 8; // JetpackEU
							def[31] += 30;
							break;
						case 6:
							def[26] += 30; // JetpackEUPower
							def[27] += 16; // JetpackEU
							def[31] += 60;
							break;
						case 7:
							def[26] += 40; // JetpackEUPower
							def[27] += 32; // JetpackEU
							def[31] += 90;
							break;
						case 8:
							def[26] += 50; // JetpackEUPower
							def[27] += 64; // JetpackEU
							def[31] += 120;
							break;
						case 9:
							def[26] += 60; // JetpackEUPower
							def[27] += 128; // JetpackEU
							def[31] += 150;
							break;
						case 10:
							def[28] += 100; // AntiGravPower
							def[29] += 32; // AntiGravEU
							def[31] += 100;
							break;
						case 11:
							def[28] += 133; // AntiGravPower
							def[29] += 64; // AntiGravEU
							def[31] += 200;
							break;
						case 12:
							def[28] += 166; // AntiGravPower
							def[29] += 128; // AntiGravEU
							def[31] += 300;
							break;
						case 13:
							def[28] += 200; // AntiGravPower
							def[29] += 256; // AntiGravEU
							def[31] += 400;
							break;
						case 14:
							def[28] += 233; // AntiGravPower
							def[29] += 512; // AntiGravEU
							def[31] += 500;
							break;
						}
					} else if (true) {
						 //System.out.println("Unknown Item: " + parts[i].getItem().getUnlocalizedName());
					}
				}
			}
		}
		def[1] = 1 - def[1];
		def[2] = 1 - def[2];
		def[3] = 1 - def[3];
		def[4] = 1 - def[4];
		def[5] = 1 - def[5];
		if (def[7] > 0.95) {
			def[7] = 1.0f;
		}
		if (def[8] > 0.98) {
			def[8] = 1.0f;
		}
		return def;
	}

	public static int deChargeBatterys(ItemStack[] parts, int amount) {
		// System.out.println("deCharge " + amount);
		int decharged = 0;
		for (int i = 0; decharged < amount && i < parts.length; i++) {
			if (GT_ModHandler.isChargerItem(parts[i])) {
				decharged = (int) (decharged + ic2.api.item.ElectricItem.manager.discharge(parts[i], amount - decharged, 10, false, false, false));
			}
		}
		return decharged;
	}

	public static FluidStack getFluid(ItemStack[] parts, int tankCap) {
		int fluidAmount;
		if (parts.length > 12 && parts[12] != null) {
			NBTTagCompound nbt = parts[12].getTagCompound();
			if (nbt != null) {
				fluidAmount = (int) nbt.getLong("mFluidDisplayAmount");
				if (fluidAmount > tankCap) {
					nbt.setLong("mFluidDisplayAmount", tankCap);
					parts[12].setTagCompound(nbt);
					fluidAmount = tankCap;
				}
				return new FluidStack(parts[12].getItemDamage(), fluidAmount);
			}

		}
		return null;
	}

	public static void useFluid(ItemStack[] parts, int usedAmount) {
		int fluidAmount;
		if (parts.length > 12 && parts[12] != null) {
			NBTTagCompound nbt = parts[12].getTagCompound();
			if (nbt != null) {
				fluidAmount = (int) nbt.getLong("mFluidDisplayAmount");
				nbt.setLong("mFluidDisplayAmount", fluidAmount - usedAmount);
				parts[12].setTagCompound(nbt);
			}
		}
	}
}
