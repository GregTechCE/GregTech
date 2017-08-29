package gregtech.loaders.oreprocessing;

import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.type.MetalMaterial;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import gregtech.common.items.MetaItems;
import net.minecraft.item.ItemStack;

import static gregtech.api.unification.material.type.DustMaterial.MatFlags.NO_WORKING;

public class ProcessingBolt implements IOreRegistrationHandler {

	public void register() {
		OrePrefix.bolt.addProcessingHandler(this);
	}

	public void registerOre(UnificationEntry entry, String modName, SimpleItemStack simpleStack) {
		if (entry.material instanceof MetalMaterial && !entry.material.hasFlag(NO_WORKING)) {
			ItemStack boltStack = simpleStack.asItemStack();
			ItemStack screwStack = OreDictUnifier.get(OrePrefix.screw, entry.material);
			ItemStack ingotStack = OreDictUnifier.get(OrePrefix.ingot, entry.material);

			ModHandler.addShapedRecipe(boltStack,
					"fS ",
					"S  ",
					'S', screwStack);

			RecipeMap.CUTTER_RECIPES.recipeBuilder()
					.inputs(screwStack)
					.outputs(boltStack)
					.duration(20)
					.EUt(24)
					.buildAndRegister();

			RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
					.notConsumable(MetaItems.SHAPE_EXTRUDER_BOLT)
					.inputs(ingotStack)
					.outputs(GTUtility.copyAmount(16, boltStack))
					.duration(15)
					.EUt(120)
					.buildAndRegister();
		}
	}

}
