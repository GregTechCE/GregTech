package gregtech.loaders.oreprocessing;

import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.MetalMaterial;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import net.minecraft.item.ItemStack;

public class ProcessingOreSmelting implements IOreRegistrationHandler {

    public void register() {
        OrePrefix.crushed.addProcessingHandler(this);
        OrePrefix.crushedPurified.addProcessingHandler(this);
        OrePrefix.crushedCentrifuged.addProcessingHandler(this);
        OrePrefix.dustImpure.addProcessingHandler(this);
        OrePrefix.dustPure.addProcessingHandler(this);
        OrePrefix.dustRefined.addProcessingHandler(this);
    }
    
    public void registerOre(UnificationEntry entry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();
        ModHandler.removeFurnaceSmelting(stack);

        if (entry.material instanceof MetalMaterial && !entry.material.hasFlag(DustMaterial.MatFlags.NO_SMELTING)) {
            if (((MetalMaterial) entry.material).blastFurnaceTemperature > 0) {
                RecipeBuilder.BlastRecipeBuilder builder = RecipeMap.BLAST_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack))
                        .duration((int) Math.max(entry.material.getMass() / 4L, 1L) * ((MetalMaterial) entry.material).blastFurnaceTemperature)
                        .EUt(120)
                        .blastFurnaceTemp(((MetalMaterial) entry.material).blastFurnaceTemperature);

                if (((MetalMaterial) entry.material).blastFurnaceTemperature > 1750) {
                    ItemStack ingotHot = OreDictUnifier.get(OrePrefix.ingotHot, entry.material);
                     if (ingotHot != null) {
                         builder.outputs(ingotHot);
                     } else {
                         builder.outputs(OreDictUnifier.get(OrePrefix.ingot, entry.material));
                     }
                } else {
                    builder.outputs(OreDictUnifier.get(OrePrefix.ingot, entry.material));
                }

                switch (entry.orePrefix) {
                    case crushed:
                    case crushedPurified:
                    case crushedCentrifuged:
                        ItemStack itemStack = OreDictUnifier.get(OrePrefix.nugget, ((DustMaterial) entry.material).directSmelting, ((DustMaterial) entry.material).directSmelting == entry.material ? 10 : 3);
                        if (itemStack == null) {
                            itemStack = OreDictUnifier.get(entry.material.hasFlag(SMELTING_TO_GEM) ? OrePrefix.gem : OrePrefix.ingot, ((DustMaterial) entry.material).directSmelting);
                        }
                        if (itemStack == null && !entry.material.hasFlag(SMELTING_TO_GEM)) {
                            itemStack = OreDictUnifier.get(OrePrefix.ingot, ((DustMaterial) entry.material).directSmelting);
                        }
                        ModHandler.addSmeltingRecipe(stack, itemStack);
                        break;
                    default:
                        ModHandler.addSmeltingRecipe(stack, OreDictUnifier.get(OrePrefix.ingot, ((DustMaterial) entry.material).directSmelting));
                }
            }
        }
    }
}
