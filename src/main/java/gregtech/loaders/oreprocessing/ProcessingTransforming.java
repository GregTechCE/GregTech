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

import static gregtech.api.GTValues.M;

public class ProcessingTransforming implements IOreRegistrationHandler {

    public ProcessingTransforming() {
        for (OrePrefix prefix : OrePrefix.values()) {
            if (prefix.materialAmount > 0L && prefix.containerItem != null || prefix == OrePrefix.plank) {
                prefix.addProcessingHandler(this);
            }
        }
    }

    public void registerOre(UnificationEntry entry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();

        if (entry.material == Materials.Iron
                || entry.material == Materials.WroughtIron
                || entry.material == Materials.Steel
                || entry.material == Materials.Neodymium) {

            RecipeMap.POLARIZER_RECIPES.recipeBuilder()
                    .inputs(GTUtility.copyAmount(1, stack))
                    .outputs(OreDictionaryUnifier.get(entry.orePrefix,entry.material, 1))
                    .duration((int) Math.max(16, entry.orePrefix.materialAmount * 128L / M))
                    .EUt(16)
                    .buildAndRegister();

        } else if (entry.material == Materials.Wood){

            RecipeMap.CHEMICAL_BATH_RECIPES.recipeBuilder()
                    .inputs(GTUtility.copyAmount(1, stack))
                    .fluidInputs(Materials.SeedOil.getFluid((int) GTUtility.translateMaterialToAmount(entry.orePrefix.materialAmount, 120L, true)))
                    .outputs(OreDictionaryUnifier.get(entry.orePrefix, Materials.WoodSealed, 1))
                    .duration(100)
                    .EUt(8)
                    .buildAndRegister();
        }
    }
}
