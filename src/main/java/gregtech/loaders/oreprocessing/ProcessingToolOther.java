package gregtech.loaders.oreprocessing;

import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.SolidMaterial;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.items.MetaItems;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;

import static gregtech.api.unification.material.type.DustMaterial.MatFlags.NO_SMASHING;

public class ProcessingToolOther implements IOreRegistrationHandler {

    private ProcessingToolOther() { }

    public static void register() {
		OrePrefix.toolHeadHammer.addProcessingHandler(new ProcessingToolOther());
	}

	@Override
	public void registerOre(UnificationEntry entry, String modName, SimpleItemStack simpleStack) {
		if (entry.material != Materials.Stone && entry.material != Materials.Flint) {
			if (entry.material != Materials.Rubber) {
				ModHandler.addShapedRecipe("plunger_" + entry.material + "_" + entry.material,
                    MetaItems.PLUNGER.getStackForm(entry.material, entry.material),
						"xRR",
						" SR",
						"S f",
						'S', new UnificationEntry(OrePrefix.stick, entry.material),
						'R', new UnificationEntry(OrePrefix.plate, Materials.Rubber));
			}
			if (!ModHandler.isMaterialWood(entry.material)
					&& !entry.material.hasFlag(NO_SMASHING)) {

				if (entry.material instanceof SolidMaterial) {
					SolidMaterial solidMaterial = (SolidMaterial) entry.material;
					ModHandler.addShapedRecipe("screwdriver_" + solidMaterial + "_" + solidMaterial.handleMaterial,
                        MetaItems.SCREWDRIVER.getStackForm(solidMaterial, solidMaterial.handleMaterial),
							" fS",
							" Sh",
							"W  ",
							'S', OreDictUnifier.get(OrePrefix.stick, solidMaterial),
							'W', OreDictUnifier.get(OrePrefix.stick, solidMaterial));
				}
				ModHandler.addShapedRecipe("wrench_" + entry.material + "_" + entry.material,
                    MetaItems.WRENCH.getStackForm(entry.material, entry.material),
						"IhI",
						"III",
						" I ",
						'I', OreDictUnifier.get(OrePrefix.ingot, entry.material));
				ModHandler.addShapedRecipe("crowbar_" + entry.material + "_" + entry.material,
                    MetaItems.CROWBAR.getStackForm(entry.material, entry.material),
						"hDS",
						"DSD",
						"SDf",
						'S', OreDictUnifier.get(OrePrefix.stick, entry.material),
						'D', EnumDyeColor.BLUE);
				ModHandler.addShapedRecipe("wirecutter_" + entry.material + "_" + entry.material,
                    MetaItems.WIRECUTTER.getStackForm(entry.material, entry.material),
						"PfP",
						"hPd",
						"STS",
						'S', OreDictUnifier.get(OrePrefix.stick, entry.material),
						'P', OreDictUnifier.get(OrePrefix.plate, entry.material),
						'T', OreDictUnifier.get(OrePrefix.screw, entry.material));
				ModHandler.addShapedRecipe("scoop_" + entry.material + "_" + entry.material,
                    MetaItems.SCOOP.getStackForm(entry.material, entry.material),
						"SWS",
						"SSS",
						"xSh",
						'S', OreDictUnifier.get(OrePrefix.stick, entry.material),
						'W', new ItemStack(Blocks.WOOL, 1, 32767));
				ModHandler.addShapedRecipe("branchcutter_" + entry.material + "_" + entry.material,
                    MetaItems.BRANCHCUTTER.getStackForm(entry.material, entry.material),
						"PfP",
						"PdP",
						"STS",
						'S', OreDictUnifier.get(OrePrefix.stick, entry.material),
						'P', OreDictUnifier.get(OrePrefix.plate, entry.material),
						'T', OreDictUnifier.get(OrePrefix.screw, entry.material));
				ModHandler.addShapedRecipe("knife_" + entry.material + "_" + entry.material,
                    MetaItems.KNIFE.getStackForm(entry.material, entry.material),
						"fPh", " S ",
						'S', OreDictUnifier.get(OrePrefix.stick, entry.material),
						'P', OreDictUnifier.get(OrePrefix.plate, entry.material));
				ModHandler.addShapedRecipe("butchknife_" + entry.material + "_" + entry.material,
                    MetaItems.BUTCHERYKNIFE.getStackForm(entry.material, entry.material),
						"PPf", "PP ", "Sh ",
						'S', OreDictUnifier.get(OrePrefix.stick, entry.material),
						'P', OreDictUnifier.get(OrePrefix.plate, entry.material));
				ModHandler.addShapedRecipe("soldiron_lv_" + entry.material + "_" + Materials.Rubber,
                    MetaItems.SOLDERING_IRON_LV.getStackForm(entry.material, Materials.Rubber/*, long[]{100000L, 32L, 1L, -1L}*/), // TODO electric tools
						"LBf",
						"Sd ",
						"P  ",
						'B', OreDictUnifier.get(OrePrefix.bolt, entry.material),
						'P', OreDictUnifier.get(OrePrefix.plate, entry.material),
						'S', OreDictUnifier.get(OrePrefix.stick, Materials.Iron),
						'L', MetaItems.BATTERY_RE_LV_LITHIUM.getStackForm());
			}
		}
	}
}
