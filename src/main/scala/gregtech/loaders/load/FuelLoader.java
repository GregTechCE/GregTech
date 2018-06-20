package gregtech.loaders.load;

import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Materials;

public class FuelLoader {

    public static void registerFuels() {
        //128000EU
        RecipeMaps.DIESEL_GENERATOR_FUELS.recipeBuilder()
            .fluidInputs(Materials.Fuel.getFluid(1))
            .duration(4)
            .EUt(-32)
            .buildAndRegister();
        //6000EU
        RecipeMaps.DIESEL_GENERATOR_FUELS.recipeBuilder()
            .fluidInputs(Materials.BioFuel.getFluid(1))
            .duration(1)
            .EUt(-6)
            .buildAndRegister();
        //32000EU
        RecipeMaps.DIESEL_GENERATOR_FUELS.recipeBuilder()
            .fluidInputs(Materials.SulfuricLightFuel.getFluid(1))
            .duration(2)
            .EUt(-16)
            .buildAndRegister();
        //32000EU
        RecipeMaps.DIESEL_GENERATOR_FUELS.recipeBuilder()
            .fluidInputs(Materials.SulfuricNaphtha.getFluid(1))
            .duration(2)
            .EUt(-16)
            .buildAndRegister();
        //256000EU
        RecipeMaps.DIESEL_GENERATOR_FUELS.recipeBuilder()
            .fluidInputs(Materials.SulfuricHeavyFuel.getFluid(1))
            .duration(4)
            .EUt(-64)
            .buildAndRegister();
        //148000EU
        RecipeMaps.DIESEL_GENERATOR_FUELS.recipeBuilder()
            .fluidInputs(Materials.Ethanol.getFluid(1))
            .duration(1)
            .EUt(-148)
            .buildAndRegister();
        //256000EU
        RecipeMaps.DIESEL_GENERATOR_FUELS.recipeBuilder()
            .fluidInputs(Materials.LightFuel.getFluid(1))
            .duration(8)
            .EUt(-32)
            .buildAndRegister();
        //256000EU
        RecipeMaps.DIESEL_GENERATOR_FUELS.recipeBuilder()
            .fluidInputs(Materials.Naphtha.getFluid(1))
            .duration(2)
            .EUt(-128)
            .buildAndRegister();
        //192000EU
        RecipeMaps.DIESEL_GENERATOR_FUELS.recipeBuilder()
            .fluidInputs(Materials.HeavyFuel.getFluid(1))
            .duration(1)
            .EUt(-192)
            .buildAndRegister();
        //256000EU
        RecipeMaps.DIESEL_GENERATOR_FUELS.recipeBuilder()
            .fluidInputs(Materials.LPG.getFluid(1))
            .duration(2)
            .EUt(-128)
            .buildAndRegister();
        //512000EU
        RecipeMaps.DIESEL_GENERATOR_FUELS.recipeBuilder()
            .fluidInputs(Materials.NitroFuel.getFluid(1))
            .duration(1)
            .EUt(-512)
            .buildAndRegister();

        RecipeMaps.STEAM_TURBINE_FUELS.recipeBuilder()
            .fluidInputs(Materials.Steam.getFluid(10))
            .duration(3).EUt(-32)
            .buildAndRegister();

        RecipeMaps.GAS_TURBINE_FUELS.recipeBuilder()
            .fluidInputs(Materials.Hydrogen.getFluid(16))
            .duration(5).EUt(-16)
            .buildAndRegister();

        RecipeMaps.GAS_TURBINE_FUELS.recipeBuilder()
            .fluidInputs(Materials.Methane.getFluid(8))
            .duration(10).EUt(-24)
            .buildAndRegister();
    }


}