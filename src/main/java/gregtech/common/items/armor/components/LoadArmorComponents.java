package gregtech.common.items.armor.components;

import gregtech.api.GregTech_API;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import ic2.core.Ic2Items;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class LoadArmorComponents {
	public static void init(){
		
		new ArmorPlating("plateRubber", "plateRubber", 					 60, 0.06f, 0.06f, 0.02f, 0.10f, 0.10f, 2f, 0f, .25f, 0f);
		new ArmorPlating("plateWood", "plateWood", 						 80, 0.08f, 0.09f, 0.02f, 0.08f, 0.08f);
		new ArmorPlating("plateBrass", "plateBrass", 					140, 0.12f, 0.12f, 0.10f, 0.10f, 0.12f);
		new ArmorPlating("plateCopper", "plateCopper", 					140, 0.11f, 0.11f, 0.10f, 0.10f, 0.11f);
		new ArmorPlating("plateLead", "plateLead", 						280, 0.05f, 0.05f, 0.05f, 0.05f, 0.05f, 0, .3f, 0, 0);
		new ArmorPlating("platePlastic", "platePlastic", 			 	60, 0.10f, 0.10f, 0.02f, 0.02f, 0.10f, 0, 0, .25f, 0);
		new ArmorPlating("plateAluminium", "plateAluminium",			120, 0.14f, 0.14f, 0.12f, 0.12f, 0.14f);
		new ArmorPlating("plateAstralSilver", "plateAstralSilver", 		180, 0.10f, 0.10f, 0.10f, 0.18f, 0.10f);
		new ArmorPlating("plateBismuthBronze", "plateBismuthBronze", 	160, 0.12f, 0.12f, 0.10f, 0.10f, 0.12f);
		new ArmorPlating("plateBlackBronze", "plateBlackBronze", 		160, 0.13f, 0.13f, 0.10f, 0.10f, 0.13f);
		new ArmorPlating("plateBlackSteel", "plateBlackSteel", 			200, 0.19f, 0.19f, 0.17f, 0.17f, 0.19f);
		new ArmorPlating("plateBlueSteel", "plateBlueSteel", 			200, 0.21f, 0.21f, 0.19f, 0.19f, 0.21f);
		new ArmorPlating("plateBronze", "plateBronze", 					160, 0.13f, 0.13f, 0.12f, 0.12f, 0.13f);
		new ArmorPlating("plateCobaltBrass", "plateCobaltBrass", 		180, 0.15f, 0.15f, 0.14f, 0.14f, 0.15f);
		new ArmorPlating("plateDamascusSteel", "plateDamascusSteel", 	200, 0.22f, 0.22f, 0.20f, 0.20f, 0.22f);
		new ArmorPlating("plateElectrum", "plateElectrum", 				250, 0.11f, 0.11f, 0.10f, 0.10f, 0.11f);
		new ArmorPlating("plateEmerald", "plateEmerald", 				160, 0.10f, 0.10f, 0.14f, 0.14f, 0.10f);
		new ArmorPlating("plateGold", "plateGold", 						300, 0.09f, 0.09f, 0.05f, 0.25f, 0.09f);
		new ArmorPlating("plateGreenSapphire", "plateGreenSapphire", 	160, 0.10f, 0.10f, 0.14f, 0.14f, 0.10f);
		new ArmorPlating("plateInvar", "plateInvar", 					190, 0.10f, 0.10f, 0.22f, 0.22f, 0.10f);
		new ArmorPlating("plateIron", "plateIron", 						200, 0.12f, 0.12f, 0.10f, 0.10f, 0.12f);
		new ArmorPlating("plateIronWood", "plateIronWood", 				150, 0.17f, 0.17f, 0.02f, 0.02f, 0.17f);
		new ArmorPlating("plateMagnalium", "plateMagnalium", 			120, 0.15f, 0.15f, 0.17f, 0.17f, 0.15f);
		new ArmorPlating("plateNeodymiumMagnetic","plateNeodymiumMagnetic",220, 0.14f, 0.14f, 0.14f, 0.14f, 0.14f, 0, 0, 0, 0, StatType.MAGNETSINGLE, 2.0f);
		new ArmorPlating("plateManganese", "plateManganese", 			180, 0.15f, 0.15f, 0.14f, 0.14f, 0.15f);
		new ArmorPlating("plateMeteoricIron", "plateMeteoricIron", 		200, 0.18f, 0.18f, 0.16f, 0.16f, 0.18f);
		new ArmorPlating("plateMeteoricSteel", "plateMeteoricSteel", 	200, 0.21f, 0.21f, 0.19f, 0.19f, 0.21f);
		new ArmorPlating("plateMolybdenum", "plateMolybdenum", 			140, 0.14f, 0.14f, 0.14f, 0.14f, 0.14f);
		new ArmorPlating("plateNickel", "plateNickel", 					180, 0.12f, 0.12f, 0.15f, 0.15f, 0.12f);
		new ArmorPlating("plateOlivine", "plateOlivine", 				180, 0.10f, 0.10f, 0.14f, 0.14f, 0.10f);
		new ArmorPlating("plateOpal", "plateOpal", 						180, 0.10f, 0.10f, 0.14f, 0.14f, 0.10f);
		new ArmorPlating("platePalladium", "platePalladium", 			280, 0.14f, 0.14f, 0.12f, 0.12f, 0.14f);
		new ArmorPlating("platePlatinum", "platePlatinum", 				290, 0.15f, 0.15f, 0.13f, 0.13f, 0.15f);
		new ArmorPlating("plateGarnetRed", "plateGarnetRed", 			180, 0.10f, 0.10f, 0.14f, 0.14f, 0.10f);
		new ArmorPlating("plateRedSteel", "plateRedSteel", 				200, 0.20f, 0.20f, 0.18f, 0.18f, 0.20f);
		new ArmorPlating("plateRoseGold", "plateRoseGold", 				240, 0.10f, 0.10f, 0.08f, 0.18f, 0.10f);
		new ArmorPlating("plateRuby", "plateRuby", 						180, 0.10f, 0.10f, 0.20f, 0.20f, 0.10f);
		new ArmorPlating("plateSapphire", "plateSapphire", 				180, 0.10f, 0.10f, 0.14f, 0.14f, 0.10f);
		new ArmorPlating("plateSilver", "plateSilver", 					220, 0.11f, 0.11f, 0.07f, 0.24f, 0.11f);
		new ArmorPlating("plateStainlessSteel", "plateStainlessSteel", 	200, 0.16f, 0.16f, 0.21f, 0.21f, 0.16f);
		new ArmorPlating("plateSteel", "plateSteel", 					200, 0.18f, 0.18f, 0.16f, 0.16f, 0.18f);
		new ArmorPlating("plateSterlingSilver", "plateSterlingSilver", 	210, 0.15f, 0.15f, 0.13f, 0.13f, 0.15f);
		new ArmorPlating("plateTanzanite", "plateTanzanite", 			180, 0.10f, 0.10f, 0.14f, 0.14f, 0.10f);
		new ArmorPlating("plateThorium", "plateThorium", 				280, 0.13f, 0.13f, 0.16f, 0.16f, 0.13f);
		new ArmorPlating("plateWroughtIron", "plateWroughtIron", 		200, 0.14f, 0.14f, 0.12f, 0.12f, 0.14f);
		new ArmorPlating("plateGarnetYellow", "plateGarnetYellow", 		180, 0.10f, 0.10f, 0.14f, 0.14f, 0.10f);
		new ArmorPlating("plateAlloyCarbon", "plateAlloyCarbon", 		 60, 0.06f, 0.23f, 0.05f, 0.05f, 0.06f);
		new ArmorPlating("plateInfusedAir", "plateInfusedAir", 			 10, 0.10f, 0.10f, 0.10f, 0.10f, 0.10f);
		new ArmorPlating("plateAmethyst", "plateAmethyst", 				180, 0.10f, 0.10f, 0.14f, 0.14f, 0.10f);
		new ArmorPlating("plateInfusedWater", "plateInfusedWater", 		150, 0.10f, 0.10f, 0.20f, 0.20f, 0.10f);
		new ArmorPlating("plateBlueTopaz", "plateBlueTopaz", 			180, 0.10f, 0.10f, 0.14f, 0.14f, 0.10f);
		new ArmorPlating("plateChrome", "plateChrome", 					200, 0.12f, 0.12f, 0.21f, 0.21f, 0.12f);
		new ArmorPlating("plateCobalt", "plateCobalt", 					220, 0.16f, 0.16f, 0.14f, 0.14f, 0.16f);
		new ArmorPlating("plateDarkIron", "plateDarkIron", 				200, 0.21f, 0.21f, 0.19f, 0.19f, 0.21f);
		new ArmorPlating("plateDiamond", "plateDiamond", 				200, 0.20f, 0.20f, 0.22f, 0.22f, 0.20f);
		new ArmorPlating("plateEnderium", "plateEnderium", 				250, 0.22f, 0.22f, 0.21f, 0.21f, 0.22f);
		new ArmorPlating("plateElectrumFlux", "plateElectrumFlux", 		180, 0.19f, 0.19f, 0.16f, 0.16f, 0.19f);
		new ArmorPlating("plateForce", "plateForce", 					180, 0.10f, 0.10f, 0.20f, 0.20f, 0.10f);
		new ArmorPlating("plateHSLA", "plateHSLA", 						200, 0.21f, 0.21f, 0.17f, 0.17f, 0.21f);
		new ArmorPlating("plateInfusedFire", "plateInfusedFire", 		150, 0.12f, 0.10f, 0.30f, 0.30f, 0.12f, 0, 0, 0, 0, StatType.THORNSSINGLE, 3.0f);
		new ArmorPlating("plateInfusedGold", "plateInfusedGold", 		300, 0.15f, 0.15f, 0.05f, 0.05f, 0.15f);
		new ArmorPlating("plateMithril", "plateMithril", 				200, 0.25f, 0.25f, 0.10f, 0.30f, 0.25f);
		new ArmorPlating("plateInfusedOrder", "plateInfusedOrder", 		150, 0.18f, 0.22f, 0.22f, 0.25f, 0.22f);
		new ArmorPlating("plateSteeleaf", "plateSteeleaf", 				120, 0.16f, 0.16f, 0.06f, 0.06f, 0.16f);
		new ArmorPlating("plateInfusedEarth", "plateInfusedEarth", 		350, 0.30f, 0.30f, 0.30f, 0.30f, 0.30f);
		new ArmorPlating("plateThaumium", "plateThaumium", 				200, 0.18f, 0.19f, 0.20f, 0.30f, 0.18f);
		new ArmorPlating("plateTitanium", "plateTitanium", 				140, 0.20f, 0.20f, 0.18f, 0.18f, 0.20f);
		new ArmorPlating("plateTungsten", "plateTungsten", 				270, 0.27f, 0.26f, 0.23f, 0.26f, 0.26f);
		new ArmorPlating("plateUltimet", "plateTopaz", 					180, 0.10f, 0.10f, 0.14f, 0.14f, 0.10f);
		new ArmorPlating("plateUltimet", "plateUltimet", 				180, 0.21f, 0.21f, 0.19f, 0.19f, 0.21f);
		new ArmorPlating("plateUranium", "plateUranium", 				290, 0.27f, 0.23f, 0.20f, 0.15f, 0.21f);
		new ArmorPlating("plateVinteum", "plateVinteum", 				180, 0.10f, 0.12f, 0.14f, 0.28f, 0.12f);
		new ArmorPlating("plateDuranium", "plateDuranium", 				140, 0.24f, 0.24f, 0.24f, 0.24f, 0.24f);
		new ArmorPlating("plateAlloyIridium", "plateAlloyIridium", 		220, 0.24f, 0.24f, 0.22f, 0.22f, 0.24f);
		new ArmorPlating("plateOsmiridium", "plateOsmiridium", 			240, 0.18f, 0.18f, 0.16f, 0.16f, 0.18f);
		new ArmorPlating("plateOsmium", "plateOsmium", 					250, 0.12f, 0.12f, 0.10f, 0.10f, 0.12f);
		new ArmorPlating("plateNaquadah", "plateNaquadah", 				250, 0.27f, 0.27f, 0.25f, 0.25f, 0.27f);
		new ArmorPlating("plateNetherStar", "plateNetherStar", 			140, 0.22f, 0.22f, 0.24f, 0.24f, 0.22f, 0, 0, 0, .2f);
		new ArmorPlating("plateInfusedEntropy", "plateInfusedEntropy", 	150, 0.10f, 0.10f, 0.10f, 0.10f, 0.10f, 0, 0, 0, 0, StatType.THORNSSINGLE, 4.0f);
		new ArmorPlating("plateTritanium", "plateTritanium", 			140, 0.26f, 0.26f, 0.26f, 0.26f, 0.26f);
		new ArmorPlating("plateTungstenSteel", "plateTungstenSteel", 	270, 0.30f, 0.28f, 0.25f, 0.28f, 0.30f);
		new ArmorPlating("plateAdamantium", "plateAdamantium", 			200, 0.28f, 0.28f, 0.26f, 0.30f, 0.30f);
		new ArmorPlating("plateNaquadahAlloy", "plateNaquadahAlloy", 	300, 0.33f, 0.33f, 0.33f, 0.33f, 0.33f);
		new ArmorPlating("plateNeutronium", "plateNeutronium", 			600, 0.50f, 0.50f, 0.50f, 0.50f, 0.50f);
		
		new ArmorComponentFunction("componentnightvision", GT_ModHandler.getIC2Item("nightvisionGoggles", 1), true, 100, StatType.NIGHTVISION, 100);
		if(GT_ModHandler.getModItem("Thaumcraft", "ItemGoggles", 1)!=null)new ArmorComponentFunction("componentthaumicgoggles", GT_ModHandler.getModItem("Thaumcraft", "ItemGoggles", 1), true, 100, StatType.THAUMICGOGGLES, 100);
		new ArmorComponentBattery("batteryLVLI", ItemList.Battery_RE_LV_Lithium.get(1, new Object[]{}), true, 100, 100000);
		new ArmorComponentBattery("batteryMVLI", ItemList.Battery_RE_MV_Lithium.get(1, new Object[]{}), true, 100, 400000);
		new ArmorComponentBattery("batteryHVLI", ItemList.Battery_RE_HV_Lithium.get(1, new Object[]{}), true, 100, 1600000);

		new ArmorComponentBattery("batteryLVCA", ItemList.Battery_RE_LV_Cadmium.get(1, new Object[]{}), true, 100, 75000);
		new ArmorComponentBattery("batteryMVCA", ItemList.Battery_RE_MV_Cadmium.get(1, new Object[]{}), true, 100, 300000);
		new ArmorComponentBattery("batteryHVCA", ItemList.Battery_RE_HV_Cadmium.get(1, new Object[]{}), true, 100, 1200000);

		new ArmorComponentBattery("batteryLVSO", ItemList.Battery_RE_LV_Sodium.get(1, new Object[]{}), true, 100, 50000);
		new ArmorComponentBattery("batteryMVSO", ItemList.Battery_RE_MV_Sodium.get(1, new Object[]{}), true, 100, 200000);
		new ArmorComponentBattery("batteryHVSO", ItemList.Battery_RE_HV_Sodium.get(1, new Object[]{}), true, 100, 800000);
		
		new ArmorComponentBattery("batterycystal", ItemList.IC2_EnergyCrystal.get(1, new Object[]{}), true, 100, 1000000);
		new ArmorComponentBattery("batterylapotron", ItemList.IC2_LapotronCrystal.get(1, new Object[]{}), true, 100, 10000000);
		new ArmorComponentBattery("batterylapoorb", ItemList.Energy_LapotronicOrb.get(1, new Object[]{}), true, 100, 100000000);
		new ArmorComponentBattery("batteryorbcluster", ItemList.Energy_LapotronicOrb2.get(1, new Object[]{}), true, 100, 1000000000);
		
		new ArmorElectricComponent("motorlv", ItemList.Electric_Motor_LV.get(1, new Object[]{}), 20, StatType.MOTPRPOWER, 200f, StatType.MOTOREUUSAGE,  50f, StatType.PROCESSINGPOWERUSED,  10f);
		new ArmorElectricComponent("motormv", ItemList.Electric_Motor_MV.get(1, new Object[]{}), 40, StatType.MOTPRPOWER, 300f, StatType.MOTOREUUSAGE, 100f, StatType.PROCESSINGPOWERUSED,  20f);
		new ArmorElectricComponent("motorhv", ItemList.Electric_Motor_HV.get(1, new Object[]{}), 60, StatType.MOTPRPOWER, 400f, StatType.MOTOREUUSAGE, 200f, StatType.PROCESSINGPOWERUSED,  50f);
		new ArmorElectricComponent("motorev", ItemList.Electric_Motor_EV.get(1, new Object[]{}), 80, StatType.MOTPRPOWER, 500f, StatType.MOTOREUUSAGE, 400f, StatType.PROCESSINGPOWERUSED, 100f);
		new ArmorElectricComponent("motoriv", ItemList.Electric_Motor_IV.get(1, new Object[]{}),100, StatType.MOTPRPOWER, 600f, StatType.MOTOREUUSAGE, 800f, StatType.PROCESSINGPOWERUSED, 200f);

		new ArmorElectricComponent("pistonlv", ItemList.Electric_Piston_LV.get(1, new Object[]{}), 20, StatType.PISTONJUMPBOOST, 3, StatType.PISTONEUUSAGE, 200f, StatType.PROCESSINGPOWERUSED,  10f);
		new ArmorElectricComponent("pistonmv", ItemList.Electric_Piston_MV.get(1, new Object[]{}), 40, StatType.PISTONJUMPBOOST, 4, StatType.PISTONEUUSAGE, 300f, StatType.PROCESSINGPOWERUSED,  20f);
		new ArmorElectricComponent("pistonhv", ItemList.Electric_Piston_HV.get(1, new Object[]{}), 60, StatType.PISTONJUMPBOOST, 5, StatType.PISTONEUUSAGE, 450f, StatType.PROCESSINGPOWERUSED,  50f);
		new ArmorElectricComponent("pistonev", ItemList.Electric_Piston_EV.get(1, new Object[]{}), 80, StatType.PISTONJUMPBOOST, 6, StatType.PISTONEUUSAGE, 800f, StatType.PROCESSINGPOWERUSED, 100f);
		new ArmorElectricComponent("pistoniv", ItemList.Electric_Piston_IV.get(1, new Object[]{}),100, StatType.PISTONJUMPBOOST, 7, StatType.PISTONEUUSAGE,1600f, StatType.PROCESSINGPOWERUSED, 200f);
		
		new ArmorElectricComponent("electrolyzerlv", ItemList.Machine_LV_Electrolyzer.get(1, new Object[]{}), 20, StatType.ELECTROLYZERPROD, 10, StatType.ELECTROLYZEREUUSAGE, 1, StatType.PROCESSINGPOWERUSED,  50f);
		new ArmorElectricComponent("electrolyzermv", ItemList.Machine_MV_Electrolyzer.get(1, new Object[]{}), 40, StatType.ELECTROLYZERPROD, 20, StatType.ELECTROLYZEREUUSAGE, 4, StatType.PROCESSINGPOWERUSED,  100f);
		new ArmorElectricComponent("electrolyzerhv", ItemList.Machine_HV_Electrolyzer.get(1, new Object[]{}), 60, StatType.ELECTROLYZERPROD, 40, StatType.ELECTROLYZEREUUSAGE, 16, StatType.PROCESSINGPOWERUSED,  150f);
		new ArmorElectricComponent("electrolyzerev", ItemList.Machine_EV_Electrolyzer.get(1, new Object[]{}), 80, StatType.ELECTROLYZERPROD, 80, StatType.ELECTROLYZEREUUSAGE, 64, StatType.PROCESSINGPOWERUSED,  200f);
		new ArmorElectricComponent("electrolyzeriv", ItemList.Machine_IV_Electrolyzer.get(1, new Object[]{}),100, StatType.ELECTROLYZERPROD,160, StatType.ELECTROLYZEREUUSAGE, 128, StatType.PROCESSINGPOWERUSED,  250f);
		
		new ArmorElectricComponent("fieldgenlv", ItemList.Field_Generator_LV.get(1, new Object[]{}), 20, StatType.FIELDGENCAP, 1, StatType.FIELDGENEUUSAGE, 1, StatType.PROCESSINGPOWERUSED,  100f);
		new ArmorElectricComponent("fieldgenmv", ItemList.Field_Generator_MV.get(1, new Object[]{}), 40, StatType.FIELDGENCAP, 2, StatType.FIELDGENEUUSAGE, 4, StatType.PROCESSINGPOWERUSED,  200f);
		new ArmorElectricComponent("fieldgenhv", ItemList.Field_Generator_HV.get(1, new Object[]{}), 60, StatType.FIELDGENCAP, 3, StatType.FIELDGENEUUSAGE, 16, StatType.PROCESSINGPOWERUSED,  300f);
		new ArmorElectricComponent("fieldgenev", ItemList.Field_Generator_EV.get(1, new Object[]{}), 80, StatType.FIELDGENCAP, 4, StatType.FIELDGENEUUSAGE, 64, StatType.PROCESSINGPOWERUSED,  400f);
		new ArmorElectricComponent("fieldgeniv", ItemList.Field_Generator_IV.get(1, new Object[]{}),100, StatType.FIELDGENCAP, 5, StatType.FIELDGENEUUSAGE, 512, StatType.PROCESSINGPOWERUSED,  500f);

		new ArmorElectricComponent("cell", ItemList.Cell_Empty.get(1, new Object[]{}), 20, StatType.TANKCAP, 8000, null, 1, null,  0);
		new ArmorElectricComponent("cellsteel", ItemList.Large_Fluid_Cell_Steel.get(1, new Object[]{}), 40, StatType.TANKCAP, 16000, null, 1, null,  0);
		new ArmorElectricComponent("cellts", ItemList.Large_Fluid_Cell_TungstenSteel.get(1, new Object[]{}), 80, StatType.TANKCAP, 64000, null, 1, null,  0);

		new ArmorElectricComponent("circuitbasic", GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Basic, 1),   10, StatType.PROCESSINGPOWER, 100, null, 1, null,  1);
		new ArmorElectricComponent("circuitgood", GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Good, 1),    20, StatType.PROCESSINGPOWER, 200, null, 1, null,  1);
		new ArmorElectricComponent("circuitadv", GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 1),30, StatType.PROCESSINGPOWER, 300, null, 1, null,  1);
		new ArmorElectricComponent("circuitdata", GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 1),    40, StatType.PROCESSINGPOWER, 400, null, 1, null,  1);
		new ArmorElectricComponent("cicuitelite", GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 1),   50, StatType.PROCESSINGPOWER, 500, null, 1, null,  1);
		new ArmorElectricComponent("cicuitmaster", GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 1),  60, StatType.PROCESSINGPOWER, 600, null, 1, null,  1);
		
//		if (parts[i].getItem().getUnlocalizedName().equals("gte.meta.jetpack")) {// jeptack parts
//			switch (parts[i].getItem().getDamage(parts[i])) {
//			case 0:
//				def[24] += 50; // JetpackFuelPower
//				def[25] += 1; // FuelUsage
//				def[31] += 10;
//				break;
//			case 1:
//				def[24] += 75; // JetpackFuelPower
//				def[25] += 2; // FuelUsage
//				def[31] += 20;
//				break;
//			case 2:
//				def[24] += 100; // JetpackFuelPower
//				def[25] += 4; // FuelUsage
//				def[31] += 30;
//				break;
//			case 3:
//				def[24] += 125; // JetpackFuelPower
//				def[25] += 8; // FuelUsage
//				def[31] += 40;
//				break;
//			case 4:
//				def[24] += 150; // JetpackFuelPower
//				def[25] += 16; // FuelUsage
//				def[31] += 50;
//				break;
//			case 5:
//				def[26] += 20; // JetpackEUPower
//				def[27] += 8; // JetpackEU
//				def[31] += 30;
//				break;
//			case 6:
//				def[26] += 30; // JetpackEUPower
//				def[27] += 16; // JetpackEU
//				def[31] += 60;
//				break;
//			case 7:
//				def[26] += 40; // JetpackEUPower
//				def[27] += 32; // JetpackEU
//				def[31] += 90;
//				break;
//			case 8:
//				def[26] += 50; // JetpackEUPower
//				def[27] += 64; // JetpackEU
//				def[31] += 120;
//				break;
//			case 9:
//				def[26] += 60; // JetpackEUPower
//				def[27] += 128; // JetpackEU
//				def[31] += 150;
//				break;
//			case 10:
//				def[28] += 100; // AntiGravPower
//				def[29] += 32; // AntiGravEU
//				def[31] += 100;
//				break;
//			case 11:
//				def[28] += 133; // AntiGravPower
//				def[29] += 64; // AntiGravEU
//				def[31] += 200;
//				break;
//			case 12:
//				def[28] += 166; // AntiGravPower
//				def[29] += 128; // AntiGravEU
//				def[31] += 300;
//				break;
//			case 13:
//				def[28] += 200; // AntiGravPower
//				def[29] += 256; // AntiGravEU
//				def[31] += 400;
//				break;
//			case 14:
//				def[28] += 233; // AntiGravPower
//				def[29] += 512; // AntiGravEU
//				def[31] += 500;
//				break;
//			}
		

		int tCustomPlatings = GregTech_API.sModularArmor.get("custom", "CustomPlatings", 0);
		int tCustomElectronicComponent = GregTech_API.sModularArmor.get("custom", "CustomElectronicComponent", 0);
		int tCustomBattery = GregTech_API.sModularArmor.get("custom", "CustomBattery", 0);
		int tCustomFunction = GregTech_API.sModularArmor.get("custom", "CustomFunction", 0);
		for(int i = 0; i<tCustomPlatings; i++)
		new ArmorPlating("customPlate"+i, "platenull_", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, null, 0);
		
		for(int i = 0; i<tCustomElectronicComponent; i++)
		new ArmorElectricComponent("customElectricComponent"+i,new ItemStack(Blocks.stone),0,null,0, null, 0, null, 0);
		
		for(int i = 0; i<tCustomBattery; i++)
		new ArmorComponentBattery("customBattery"+i, new ItemStack(Blocks.stone),true, 0, 0);
		
		for(int i = 0; i<tCustomFunction; i++)
		new ArmorComponentFunction("customFunction"+i, new ItemStack(Blocks.stone), true, 0, null, 0);
	}
}
