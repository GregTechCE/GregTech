package gregtech.loaders.load;

import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Materials;

public class FuelLoader {

    public static void registerFuels() {
        RecipeMaps.DIESEL_GENERATOR_FUELS.recipeBuilder()
            .fluidInputs(Materials.Fuel.getFluid(1))
            .duration(200).EUt(-30)
            .buildAndRegister();

        RecipeMaps.DIESEL_GENERATOR_FUELS.recipeBuilder()
            .fluidInputs(Materials.BioFuel.getFluid(1))
            .duration(200)
            .EUt(-6)
            .buildAndRegister();

        RecipeMaps.DIESEL_GENERATOR_FUELS.recipeBuilder()
            .fluidInputs(Materials.SulfuricLightFuel.getFluid(1))
            .duration(200).EUt(-32)
            .buildAndRegister();

        RecipeMaps.DIESEL_GENERATOR_FUELS.recipeBuilder()
            .fluidInputs(Materials.Ethanol.getFluid(1))
            .duration(200).EUt(-146)
            .buildAndRegister();

        RecipeMaps.DIESEL_GENERATOR_FUELS.recipeBuilder()
            .fluidInputs(Materials.LightFuel.getFluid(1))
            .duration(200).EUt(-256)
            .buildAndRegister();

        RecipeMaps.STEAM_TURBINE_FUELS.recipeBuilder()
            .fluidInputs(Materials.Steam.getFluid(10))
            .duration(5).EUt(-15)
            .buildAndRegister();
    }


}