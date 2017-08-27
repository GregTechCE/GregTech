package gregtech.loaders.oreprocessing;

import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.type.SolidMaterial;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import net.minecraft.item.ItemStack;

public class ProcessingPure implements IOreRegistrationHandler {

    public ProcessingPure() {
        OrePrefix.crushedPurified.addProcessingHandler(this);
        OrePrefix.cleanGravel.addProcessingHandler(this);
        OrePrefix.reduced.addProcessingHandler(this);
    }
    
    public void registerOre(UnificationEntry entry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();

        if (entry.material instanceof SolidMaterial) {
            RecipeMap.HAMMER_RECIPES.recipeBuilder()
                    .inputs(GTUtility.copyAmount(1, stack))
                    .outputs(OreDictionaryUnifier.get(OrePrefix.dustPure, ((SolidMaterial) entry.material).macerateInto, 1))
                    .duration(10)
                    .EUt(16)
                    .buildAndRegister();

            RecipeBuilder.DefaultRecipeBuilder builder = RecipeMap.MACERATOR_RECIPES.recipeBuilder()
                    .inputs(GTUtility.copyAmount(1, stack))
                    .chancedOutput(OreDictionaryUnifier.get(OrePrefix.dust, GTUtility.selectItemInList(1, ((SolidMaterial) entry.material).macerateInto, ((SolidMaterial) entry.material).oreByProducts)), 1000);

            ItemStack itemStack = OreDictionaryUnifier.get(OrePrefix.dustPure, ((SolidMaterial) entry.material).macerateInto);
            if (itemStack != null) {
                builder.outputs(itemStack);
            } else {
                builder.outputs(OreDictionaryUnifier.get(OrePrefix.dust, ((SolidMaterial) entry.material).macerateInto));
            }

            builder.buildAndRegister();
        }
    }
}
