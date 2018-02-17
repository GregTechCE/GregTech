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

public class ProcessingPure  {

    private ProcessingPure() {}

    public static void register() {
        ProcessingPure processing = new ProcessingPure();
        //OrePrefix.crushedPurified.addProcessingHandler(processing);
        //OrePrefix.cleanGravel.addProcessingHandler(processing);
       // OrePrefix.reduced.addProcessingHandler(processing);
    }
    
    public void registerOre(UnificationEntry entry, String modName, SimpleItemStack simpleStack) {
        if (entry.material instanceof SolidMaterial) {
            SolidMaterial material = (SolidMaterial) entry.material;
            ItemStack stack = simpleStack.asItemStack();
            DustMaterial byproductMaterial = GTUtility.selectItemInList(1, material, material.oreByProducts, DustMaterial.class);
            ItemStack pureDustStack = OreDictUnifier.get(OrePrefix.dustPure, material);

            if(pureDustStack.isEmpty()) { //fallback for reduced & cleanGravel
                pureDustStack = OreDictUnifier.get(OrePrefix.dust, material);
            }

            RecipeMap.HAMMER_RECIPES.recipeBuilder()
                    .inputs(stack)
                    .outputs(OreDictUnifier.get(OrePrefix.dustPure, material))
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
