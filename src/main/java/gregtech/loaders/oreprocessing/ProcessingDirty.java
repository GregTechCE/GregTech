package gregtech.loaders.oreprocessing;

import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.FluidMaterial;
import gregtech.api.unification.material.type.SolidMaterial;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import net.minecraft.item.ItemStack;

public class ProcessingDirty implements IOreRegistrationHandler {
	public ProcessingDirty() {
		OrePrefix.clump.addProcessingHandler(this);
		OrePrefix.shard.addProcessingHandler(this);
		OrePrefix.crushed.addProcessingHandler(this);
		OrePrefix.dirtyGravel.addProcessingHandler(this);
	}

	public void registerOre(UnificationEntry entry, String modName, SimpleItemStack simpleStack) {
		ItemStack stack = simpleStack.asItemStack();
		if (entry.material instanceof SolidMaterial) {
			DustMaterial macerateInto = ((SolidMaterial) entry.material).macerateInto;

			RecipeMap.HAMMER_RECIPES.recipeBuilder()
					.inputs(GTUtility.copyAmount(1, stack))
					.outputs(OreDictionaryUnifier.get(OrePrefix.dustImpure, macerateInto, 1))
					.duration(10)
					.EUt(16)
					.buildAndRegister();

			RecipeBuilder.DefaultRecipeBuilder builder = RecipeMap.MACERATOR_RECIPES.recipeBuilder()
					.inputs(GTUtility.copyAmount(1, stack))
					.chancedOutput(OreDictionaryUnifier.get(OrePrefix.dust, GTUtility.selectItemInList(0, macerateInto, ((SolidMaterial) entry.material).oreByProducts)), 1000);

			ItemStack impureDustStack = OreDictionaryUnifier.get(OrePrefix.dustImpure, macerateInto);
			if (impureDustStack != null) {
				builder.outputs(impureDustStack);
			} else {
				builder.outputs(OreDictionaryUnifier.get(OrePrefix.dust, macerateInto));
			}
			builder.buildAndRegister();

			RecipeMap.ORE_WASHER_RECIPES.recipeBuilder()
					.inputs(GTUtility.copyAmount(1, stack))
					.fluidInputs(ModHandler.getWater(1000))
					.outputs(OreDictionaryUnifier.get(entry.orePrefix == OrePrefix.crushed ? OrePrefix.crushedPurified : OrePrefix.dustPure, entry.material, 1),
							OreDictionaryUnifier.get(OrePrefix.dustTiny, GTUtility.selectItemInList(0, macerateInto, ((SolidMaterial) entry.material).oreByProducts), 1),
							OreDictionaryUnifier.get(OrePrefix.dust, Materials.Stone, 1))
					.buildAndRegister();

			ModHandler.addThermalCentrifugeRecipe(GTUtility.copyAmount(1, stack),
					(int) Math.min(5000, Math.abs(entry.material.getMass() * 20)),
					OreDictionaryUnifier.get(entry.orePrefix == OrePrefix.crushed ? OrePrefix.crushedCentrifuged : OrePrefix.dust, entry.material),
					OreDictionaryUnifier.get(OrePrefix.dustTiny, GTUtility.selectItemInList(1, macerateInto, ((SolidMaterial) entry.material).oreByProducts)),
					OreDictionaryUnifier.get(OrePrefix.dust, Materials.Stone));

			FluidMaterial washedIn = ((DustMaterial) entry.material).washedIn;
			if (washedIn == Materials.Mercury || washedIn == Materials.SodiumPersulfate) {

				RecipeMap.CHEMICAL_BATH_RECIPES.recipeBuilder()
						.inputs(GTUtility.copyAmount(1, stack))
						.fluidInputs(washedIn.getFluid(1000))
						.outputs(OreDictionaryUnifier.get(entry.orePrefix == OrePrefix.crushed ? OrePrefix.crushedPurified : OrePrefix.dustPure, entry.material))
						.chancedOutput(OreDictionaryUnifier.get(OrePrefix.dust, macerateInto), 7000)
						.chancedOutput(OreDictionaryUnifier.get(OrePrefix.dust, Materials.Stone), 4000)
						.duration(800)
						.EUt(8)
						.buildAndRegister();
			}

			for (FluidMaterial material : ((SolidMaterial) entry.material).oreByProducts) {
				if (material instanceof SolidMaterial) {
					washedIn = ((DustMaterial) material).washedIn;
					macerateInto = ((SolidMaterial) material).macerateInto;

					if (washedIn == Materials.Mercury || washedIn == Materials.SodiumPersulfate) {

						RecipeMap.CHEMICAL_BATH_RECIPES.recipeBuilder()
								.inputs(GTUtility.copyAmount(1, stack))
								.fluidInputs(washedIn.getFluid(1000))
								.outputs(OreDictionaryUnifier.get(entry.orePrefix == OrePrefix.crushed ? OrePrefix.crushedPurified : OrePrefix.dustPure, entry.material))
								.chancedOutput(OreDictionaryUnifier.get(OrePrefix.dust, macerateInto), 7000)
								.chancedOutput(OreDictionaryUnifier.get(OrePrefix.dust, Materials.Stone), 4000)
								.duration(800)
								.EUt(8)
								.buildAndRegister();
					}
				}
			}
		}
	}
}
