package gregtech.loaders.postload;

import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.GTUtility;
import ic2.core.item.type.CraftingItemType;
import ic2.core.item.type.MiscResourceType;
import ic2.core.ref.ItemName;
import net.minecraft.item.ItemStack;

public class MachineRecipeLoader implements Runnable {

    @Override
    public void run() {
        registerWoodSealingRecipes();
        registerRubberWoodRecipes();
    }

    private void registerWoodSealingRecipes() {
        for(OrePrefix prefix : OrePrefix.values()) {
            if(prefix.generationCondition != null && prefix.doGenerateItem(Materials.Wood)) {
                RecipeMap.CHEMICAL_BATH_RECIPES.recipeBuilder()
                        .inputs(OreDictUnifier.get(prefix, Materials.Wood))
                        .outputs(OreDictUnifier.get(prefix, Materials.WoodSealed))
                        .fluidInputs(Materials.Creosote.getFluid(GTUtility.mat2FlAmount(prefix.materialAmount)))
                        .duration(10)
                        .EUt(8)
                        .buildAndRegister();
            }
        }
    }

    private static void registerRubberWoodRecipes() {
        ItemStack rubberLog = OreDictUnifier.get(OrePrefix.log, Materials.Rubber);
        RecipeMap.CENTRIFUGE_RECIPES.recipeBuilder()
                .inputs(rubberLog)
                .chancedOutput(ModHandler.IC2.getIC2Item(ItemName.misc_resource, MiscResourceType.resin, 1), 5000)
                .chancedOutput(ModHandler.IC2.getIC2Item(ItemName.crafting, CraftingItemType.plant_ball, 1), 3750)
                .chancedOutput(OreDictUnifier.get(OrePrefix.dust, Materials.Carbon), 2500)
                .chancedOutput(OreDictUnifier.get(OrePrefix.dust, Materials.Wood), 2500)
                .fluidOutputs(Materials.Methane.getFluid(60))
                .duration(200)
                .EUt(20)
                .buildAndRegister();

        RecipeMap.EXTRACTOR_RECIPES.recipeBuilder()
                .inputs(rubberLog)
                .outputs(OreDictUnifier.get(OrePrefix.dust, Materials.RawRubber))
                .buildAndRegister();
        RecipeMap.MACERATOR_RECIPES.recipeBuilder()
                .inputs(rubberLog)
                .outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Wood, 6))
                .chancedOutput(ModHandler.IC2.getIC2Item(ItemName.misc_resource, MiscResourceType.resin, 1), 3300)
                .buildAndRegister();
    }

}
