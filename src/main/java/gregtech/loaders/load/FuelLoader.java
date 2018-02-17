package gregtech.loaders.load;

import gregtech.api.GTValues;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;

public class FuelLoader {

    public static void registerFuels() {
        RecipeMap.SMALL_NAQUADAH_REACTOR_FUELS.recipeBuilder()
                .inputs(OreDictUnifier.get(OrePrefix.bolt, Materials.Naquadah))
                .EUt((int) GTValues.V[4])
                .duration(12)
                .buildAndRegister();

        RecipeMap.SMALL_NAQUADAH_REACTOR_FUELS.recipeBuilder()
                .inputs(OreDictUnifier.get(OrePrefix.bolt, Materials.NaquadahEnriched))
                .EUt((int) GTValues.V[4])
                .duration(18)
                .buildAndRegister();

        RecipeMap.LARGE_NAQUADAH_REACTOR_FUELS.recipeBuilder()
                .inputs(OreDictUnifier.get(OrePrefix.ingot, Materials.Naquadah))
                .EUt((int) GTValues.V[5])
                .duration(24)
                .buildAndRegister();

        RecipeMap.LARGE_NAQUADAH_REACTOR_FUELS.recipeBuilder()
                .inputs(OreDictUnifier.get(OrePrefix.ingot, Materials.NaquadahEnriched))
                .EUt((int) GTValues.V[5])
                .duration(30)
                .buildAndRegister();

        RecipeMap.FLUID_NAQUADAH_REACTOR_FUELS.recipeBuilder()
                .fluidInputs(Materials.Naquadah.getFluid(144))
                .EUt((int) GTValues.V[6])
                .duration(42)
                .buildAndRegister();

        RecipeMap.FLUID_NAQUADAH_REACTOR_FUELS.recipeBuilder()
                .fluidInputs(Materials.NaquadahEnriched.getFluid(144))
                .EUt((int) GTValues.V[6])
                .duration(48)
                .buildAndRegister();

    }

}