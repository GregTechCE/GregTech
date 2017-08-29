package gregtech.loaders.oreprocessing;

import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import net.minecraft.item.ItemStack;

import static gregtech.api.GTValues.M;

public class ProcessingRecycling implements IOreRegistrationHandler {

    public ProcessingRecycling() {
        for (OrePrefix prefix : OrePrefix.values())
            if (prefix.mIsMaterialBased && prefix.materialAmount > 0L && prefix.containerItem != null)
                prefix.addProcessingHandler(this);
    }
    
    public void registerOre(UnificationEntry entry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();
        if (entry.material != null && GTUtility.getFluidForFilledItem(stack, true) == null) {

			RecipeMap.CANNER_RECIPES.recipeBuilder()
					.inputs(stack)
					.outputs(stack.getItem().getContainerItem(stack)
						OreDictUnifier.get(OrePrefix.dust, entry.material, (int) (entry.orePrefix.materialAmount / M)))
					.duration((int) Math.max(entry.material.getMass() / 2L, 1L))
					.EUt(2)
					.buildAndRegister();
		}
    }
}
