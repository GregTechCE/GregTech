package gregtech.loaders.oreprocessing;

import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.FluidMaterial;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import net.minecraft.item.ItemStack;

public class ProcessingOrePoor implements IOreRegistrationHandler {

    public ProcessingOrePoor() {
        OrePrefix.orePoor.addProcessingHandler(this);
        OrePrefix.oreSmall.addProcessingHandler(this);
        OrePrefix.oreNormal.addProcessingHandler(this);
        OrePrefix.oreRich.addProcessingHandler(this);
    }

    public void registerOre(UnificationEntry entry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();
        int multiplier = 1;
        switch (entry.orePrefix) {
            case oreSmall:
                multiplier = 1;
                break;
            case orePoor:
                multiplier = 2;
                break;
            case oreNormal:
                multiplier = 3;
                break;
            case oreRich:
                multiplier = 4;
        }
        if (entry.material != null) {
            RecipeMap.HAMMER_RECIPES.recipeBuilder()
                    .inputs(GTUtility.copyAmount(1, stack))
                    .outputs(OreDictionaryUnifier.get(OrePrefix.dustTiny, entry.material, multiplier))
                    .duration(16)
                    .EUt(10)
                    .buildAndRegister();

            if (entry.material instanceof DustMaterial) {
                RecipeBuilder.DefaultRecipeBuilder builder = RecipeMap.MACERATOR_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack))
                        .outputs(OreDictionaryUnifier.get(OrePrefix.dustTiny, entry.material, 2 * multiplier))
                        .chancedOutput(OreDictionaryUnifier.get(OrePrefix.dustTiny, GTUtility.selectItemInList(0, (FluidMaterial) entry.material, ((DustMaterial) entry.material).oreByProducts), 1), 5 * multiplier);

                if (entry.orePrefix.secondaryMaterial.material instanceof DustMaterial) {
                    builder.chancedOutput(OreDictionaryUnifier.getDust((DustMaterial) entry.orePrefix.secondaryMaterial.material, entry.orePrefix.secondaryMaterial.amount), 100);
                }
                builder.buildAndRegister();

                if (entry.material.hasFlag(DustMaterial.MatFlags.NO_SMELTING)) {
                    ModHandler.addSmeltingRecipe(GTUtility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.nugget, ((DustMaterial) entry.material).directSmelting, multiplier));
                }
            }

        }
    }
}