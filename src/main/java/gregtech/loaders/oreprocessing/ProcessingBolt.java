package gregtech.loaders.oreprocessing;

import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;

import static gregtech.api.unification.material.type.DustMaterial.MatFlags.NO_WORKING;
import static gregtech.api.unification.material.type.Material.MatFlags.NO_UNIFICATION;

public class ProcessingBolt implements IOreRegistrationHandler {
	public ProcessingBolt() {
		OrePrefix.bolt.addProcessingHandler(this);
	}

	public void registerOre(UnificationEntry entry, String modName, SimpleItemStack simpleStack) {
		if (!entry.material.hasFlag(NO_UNIFICATION | NO_WORKING)) {
			ModHandler.addShapedRecipe(GTUtility.copyAmount(2, simpleStack.asItemStack()),
					"s ",
					" X",
					'X', OreDictionaryUnifier.get(OrePrefix.stick, entry.material));
		}
	}
}
