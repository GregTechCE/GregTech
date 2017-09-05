package gregtech.loaders.oreprocessing;

import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;

import static gregtech.api.unification.material.type.DustMaterial.MatFlags.NO_WORKING;
import static gregtech.api.unification.material.type.Material.MatFlags.NO_UNIFICATION;

public class ProcessingRotor implements IOreRegistrationHandler {

	public void register() {
		OrePrefix.rotor.addProcessingHandler(this);
	}

	public void registerOre(UnificationEntry entry, String modName, SimpleItemStack simpleStack) {
		if (!entry.material.hasFlag(NO_UNIFICATION | NO_WORKING)) {

			ModHandler.addShapedRecipe(OreDictUnifier.get(OrePrefix.rotor, entry.material, 1),
					"PhP",
					"SRf",
					"PdP",
					'P', entry.material == Materials.Wood ? new UnificationEntry(OrePrefix.plank, entry.material) : new UnificationEntry(OrePrefix.plate, entry.material),
					'R', new UnificationEntry(OrePrefix.ring, entry.material),
					'S', new UnificationEntry(OrePrefix.screw, entry.material));

			RecipeBuilder.IntCircuitRecipeBuilder builder = RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
					.inputs(OreDictUnifier.get(OrePrefix.plate, entry.material, 4),
							OreDictUnifier.get(OrePrefix.ring, entry.material))
					.outputs(OreDictUnifier.get(OrePrefix.rotor, entry.material))
					.duration(240)
					.EUt(24);

			builder.copy().fluidInputs(Materials.Tin.getFluid(32)).buildAndRegister();
			builder.copy().fluidInputs(Materials.Lead.getFluid(32)).buildAndRegister();
			builder.copy().fluidInputs(Materials.SolderingAlloy.getFluid(32)).buildAndRegister();
		}
	}

}
