package gregtech.loaders.oreprocessing;

import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;

public class ProcessingFoil implements IOreRegistrationHandler {

	public ProcessingFoil() {
		OrePrefix.foil.addProcessingHandler(this);
	}

	public void registerOre(UnificationEntry entry, String modName, SimpleItemStack simpleStack) {

		if (!entry.material.hasFlag(DustMaterial.MatFlags.NO_SMASHING)) {
			RecipeMap.BENDER_RECIPES.recipeBuilder()
					.inputs(OreDictionaryUnifier.get(OrePrefix.plate, entry.material, 4))
					.outputs(OreDictionaryUnifier.get(OrePrefix.foil, entry.material, 4))
					.duration((int) Math.max(entry.material.getMass(), 1L))
					.EUt(24)
					.buildAndRegister();
		}
	}
}
