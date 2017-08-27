package gregtech.loaders.oreprocessing;

import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.FluidMaterial;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import gregtech.common.items.MetaItems;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import static gregtech.api.GTValues.L;
import static gregtech.api.GTValues.W;
import static gregtech.api.unification.material.type.DustMaterial.MatFlags.NO_WORKING;
import static gregtech.api.unification.material.type.Material.MatFlags.NO_UNIFICATION;

public class ProcessingGear implements IOreRegistrationHandler {
	public ProcessingGear() {
		OrePrefix.gearGt.addProcessingHandler(this);
		OrePrefix.gearGtSmall.addProcessingHandler(this);
	}

	public void registerOre(UnificationEntry entry, String modName, SimpleItemStack simpleStack) {
		ItemStack stack = simpleStack.asItemStack();
		switch (entry.orePrefix) {
			case gearGt:
				if (entry.material instanceof FluidMaterial)
					RecipeMap.FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
							.notConsumable(MetaItems.SHAPE_MOLD_GEAR)
							.fluidInputs(((FluidMaterial) entry.material).getFluid(L * 4))
							.outputs(GTUtility.copyAmount(1, stack))
							.duration(128)
							.EUt(8)
							.buildAndRegister();

				if (!entry.material.hasFlag(NO_WORKING | NO_UNIFICATION)) {
					if (entry.material == Materials.Wood) {
						ModHandler.addShapedRecipe(OreDictionaryUnifier.get(OrePrefix.gearGt, entry.material),
								"SPS",
								"PsP",
								"SPS",
								'P', OreDictionaryUnifier.get(OrePrefix.plank, entry.material, 2),
								'S', OreDictionaryUnifier.get(OrePrefix.stick, entry.material, 2));

					} else if (entry.material == Materials.Stone) {
						ModHandler.addShapedRecipe(OreDictionaryUnifier.get(OrePrefix.gearGt, entry.material),
								"SPS",
								"PfP",
								"SPS",
								'P', OrePrefix.stoneSmooth,
								'S', new ItemStack(Blocks.STONE_BUTTON, 1, W));
					} else {
						ModHandler.addShapedRecipe(OreDictionaryUnifier.get(OrePrefix.gearGt, entry.material),
								"SPS",
								"PwP",
								"SPS",
								'P', OreDictionaryUnifier.get(OrePrefix.plate, entry.material, 2),
								'S', OreDictionaryUnifier.get(OrePrefix.stick, entry.material, 2));
					}
				}
				break;
			case gearGtSmall:
				if (entry.material instanceof FluidMaterial)
					RecipeMap.FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
							.notConsumable(MetaItems.SHAPE_MOLD_GEAR_SMALL)
							.fluidInputs(((FluidMaterial) entry.material).getFluid(L))
							.outputs(GTUtility.copyAmount(1, stack))
							.duration(16)
							.EUt(8)
							.buildAndRegister();
				if (!entry.material.hasFlag(NO_WORKING | NO_UNIFICATION)) {

					if (entry.material == Materials.Wood || entry.material == Materials.WoodSealed) {
						ModHandler.addShapedRecipe(OreDictionaryUnifier.get(OrePrefix.gearGtSmall, entry.material),
								"P ",
								" s",
								'P', OreDictionaryUnifier.get(OrePrefix.plank, entry.material, 2));
					} else if (entry.material == Materials.Stone) {
						ModHandler.addShapedRecipe(OreDictionaryUnifier.get(OrePrefix.gearGtSmall, entry.material),
								"P ",
								" f",
								'P', OrePrefix.stoneSmooth);
					} else {
						ModHandler.addShapedRecipe(OreDictionaryUnifier.get(OrePrefix.gearGtSmall, entry.material),
								"P ",
								" h",
								'P', OreDictionaryUnifier.get(OrePrefix.plate, entry.material, 2));
					}
				}
				break;
		}
	}
}
