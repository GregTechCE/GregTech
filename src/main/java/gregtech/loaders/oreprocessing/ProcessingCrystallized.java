package gregtech.loaders.oreprocessing;

import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.type.SolidMaterial;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import net.minecraft.item.ItemStack;

public class ProcessingCrystallized implements IOreRegistrationHandler {
	public ProcessingCrystallized() {
		OrePrefix.crystal.addProcessingHandler(this);
		OrePrefix.crystalline.addProcessingHandler(this);
	}

	public void registerOre(UnificationEntry entry, String modName, SimpleItemStack simpleStack) {
		if (entry.material instanceof SolidMaterial) {
			ItemStack stack = simpleStack.asItemStack();
			ItemStack dustStack = OreDictUnifier.get(OrePrefix.dust, ((SolidMaterial) entry.material).macerateInto);

			RecipeMap.HAMMER_RECIPES.recipeBuilder()
					.inputs(GTUtility.copyAmount(1, stack))
					.outputs(dustStack)
					.duration(10)
					.EUt(10)
					.buildAndRegister();

			RecipeMap.MACERATOR_RECIPES.recipeBuilder()
					.inputs(GTUtility.copyAmount(1, stack))
					.outputs(dustStack)
					.duration(20)
					.EUt(16)
					.buildAndRegister();
		}
	}

}
