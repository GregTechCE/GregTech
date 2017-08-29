package gregtech.loaders.postload;

import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.GTUtility;

public class MachineRecipeLoader implements Runnable {

    @Override
    public void run() {
        registerChemicalBathRecipes();
    }

    private void registerChemicalBathRecipes() {
        for(OrePrefix prefix : OrePrefix.values()) {
            if(prefix.generationCondition != null && prefix.doGenerateItem(Materials.Wood)) {
                RecipeMap.CHEMICAL_BATH_RECIPES.recipeBuilder()
                        .inputs(OreDictUnifier.get(prefix, Materials.Wood))
                        .outputs(OreDictUnifier.get(prefix, Materials.WoodSealed))
                        .fluidInputs(Materials.Creosote.getFluid(GTUtility.translateMaterialToFluidAmount(prefix.materialAmount, false)))
                        .duration(10)
                        .EUt(8)
                        .buildAndRegister();
            }
        }
    }

}
