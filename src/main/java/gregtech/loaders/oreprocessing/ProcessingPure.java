package gregtech.loaders.oreprocessing;

import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.SolidMaterial;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import net.minecraft.item.ItemStack;

public class ProcessingPure implements IOreRegistrationHandler {

    public void register() {
        OrePrefix.crushedPurified.addProcessingHandler(this);
        OrePrefix.cleanGravel.addProcessingHandler(this);
        OrePrefix.reduced.addProcessingHandler(this);
    }
    
    public void registerOre(UnificationEntry entry, String modName, SimpleItemStack simpleStack) {
        if (entry.material instanceof SolidMaterial) {
            SolidMaterial material = (SolidMaterial) entry.material;
            ItemStack stack = simpleStack.asItemStack();
            DustMaterial byproductMaterial = GTUtility.selectItemInList(1, material.macerateInto, material.oreByProducts);
            ItemStack pureDustStack = OreDictUnifier.get(OrePrefix.dustPure, material.macerateInto);

            if(pureDustStack == null) { //fallback for reduced & cleanGravel
                pureDustStack = OreDictUnifier.get(OrePrefix.dust, material.macerateInto);
            }

            RecipeMap.HAMMER_RECIPES.recipeBuilder()
                    .inputs(stack)
                    .outputs(OreDictUnifier.get(OrePrefix.dustPure, material.macerateInto))
                    .duration(10)
                    .EUt(16)
                    .buildAndRegister();

            RecipeMap.MACERATOR_RECIPES.recipeBuilder()
                    .inputs(stack)
                    .outputs(pureDustStack)
                    .chancedOutput(OreDictUnifier.get(OrePrefix.dust, byproductMaterial), 1000)
                    .buildAndRegister();
        }
    }

}
