package gregtech.loaders.oreprocessing;

import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingOrePoor implements IOreRegistrationHandler {
    public ProcessingOrePoor() {
        OrePrefix.orePoor.addProcessingHandler(this);
        OrePrefix.oreSmall.addProcessingHandler(this);
        OrePrefix.oreNormal.addProcessingHandler(this);
        OrePrefix.oreRich.addProcessingHandler(this);
    }

    public void registerOre(UnificationEntry uEntry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();
        int aMultiplier = 1;
        switch (uEntry.orePrefix) {
            case oreSmall:
                aMultiplier = 1;
                break;
            case orePoor:
                aMultiplier = 2;
                break;
            case oreNormal:
                aMultiplier = 3;
                break;
            case oreRich:
                aMultiplier = 4;
        }
        if (uEntry.material != null) {
            RecipeMap.HAMMER_RECIPES.recipeBuilder()
                    .inputs(GT_Utility.copyAmount(1, stack))
                    .outputs(OreDictionaryUnifier.get(OrePrefix.dustTiny, uEntry.material, aMultiplier))
                    .duration(16)
                    .EUt(10)
                    .buildAndRegister();
            ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dustTiny, uEntry.material, 2 * aMultiplier), OreDictionaryUnifier.get(OrePrefix.dustTiny, GT_Utility.selectItemInList(0, uEntry.material, uEntry.material.mOreByProducts), 1L), 5 * aMultiplier, OreDictionaryUnifier.getDust(aPrefix.mSecondaryMaterial), 100, true);
            if (uEntry.material.hasFlag(DustMaterial.MatFlags.NO_SMELTING))
                ModHandler.addSmeltingRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.nugget, uEntry.material.mDirectSmelting, aMultiplier));
        }
    }
}