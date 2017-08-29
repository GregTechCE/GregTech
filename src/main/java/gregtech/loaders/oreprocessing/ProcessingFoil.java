package gregtech.loaders.oreprocessing;

import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;

public class ProcessingFoil implements IOreRegistrationHandler {

	public void init() {
		OrePrefix.foil.addProcessingHandler(this);
	}

	public void registerOre(UnificationEntry entry, String modName, SimpleItemStack simpleStack) {
		if (!entry.material.hasFlag(DustMaterial.MatFlags.NO_SMASHING)) {
			RecipeMap.BENDER_RECIPES.recipeBuilder()
					.inputs(OreDictUnifier.get(OrePrefix.plate, entry.material, 4))
					.outputs(OreDictUnifier.get(OrePrefix.foil, entry.material, 4))
					.duration((int) entry.material.getMass())
					.EUt(24)
					.buildAndRegister();
		}
	}

}
