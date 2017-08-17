package gregtech.loaders.oreprocessing;

import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.material.type.SolidMaterial;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingCrystallized implements IOreRegistrationHandler {
    public ProcessingCrystallized() {
        OrePrefix.crystal.addProcessingHandler(this);
        OrePrefix.crystalline.addProcessingHandler(this);
    }
    
    public void registerOre(UnificationEntry uEntry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();
        if (uEntry.material instanceof SolidMaterial) {
            Material macerateInto = ((SolidMaterial) uEntry.material).macerateInto;
            RecipeMap.HAMMER_RECIPES.recipeBuilder()
                    .inputs(GT_Utility.copyAmount(1, stack))
                    .outputs(OreDictionaryUnifier.get(OrePrefix.dust, macerateInto))
                    .duration(10)
                    .EUt(16)
                    .buildAndRegister();
            ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dust, macerateInto, 1), null, 10, false);
        }
    }
}
