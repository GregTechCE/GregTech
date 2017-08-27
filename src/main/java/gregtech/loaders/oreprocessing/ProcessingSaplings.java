package gregtech.loaders.oreprocessing;

import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import net.minecraft.item.ItemStack;

public class ProcessingSaplings implements IOreRegistrationHandler {

    public ProcessingSaplings() {
        OrePrefix.treeSapling.addProcessingHandler(this);
    }

    public void registerOre(UnificationEntry entry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();

        RecipeMap.MACERATOR_RECIPES.recipeBuilder()
                .inputs(GTUtility.copyAmount(1, stack))
                .outputs(OreDictionaryUnifier.get(OrePrefix.dustSmall, Materials.Wood, 2))
                .buildAndRegister();

        RecipeMap.LATHE_RECIPES.recipeBuilder()
                .inputs(GTUtility.copyAmount(1, stack))
                .outputs(OreDictionaryUnifier.get(OrePrefix.stick, Materials.Wood, 1), OreDictionaryUnifier.get(OrePrefix.dustTiny, Materials.Wood, 1))
                .duration(16)
                .EUt(8)
                .buildAndRegister();
    }
}
