package gregtech.loaders.oreprocessing;

import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictionaryUnifier;
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
        RecipeMap.HAMMER_RECIPES.recipeBuilder().inputs(GT_Utility.copyAmount(1, stack)).outputs(OreDictionaryUnifier.get(OrePrefix.dust, uEntry.material.mMacerateInto)).duration(10).EUt(16);
        ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dust, uEntry.material.mMacerateInto, 1L), null, 10, false);
    }
}
