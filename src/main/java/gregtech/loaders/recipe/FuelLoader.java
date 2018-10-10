package gregtech.loaders.recipe;

import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Materials;

public class FuelLoader {

    public static void registerFuels() {
        //128000EU
        RecipeMaps.DIESEL_GENERATOR_FUELS.recipeBuilder()
            .fluidInputs(Materials.Fuel.getFluid(12))
            .duration(48)
            .EUt(-32)
            .buildAndRegister();

        //6400EU
        RecipeMaps.DIESEL_GENERATOR_FUELS.recipeBuilder()
            .fluidInputs(Materials.BioFuel.getFluid(240))
            .duration(48)
            .EUt(-32)
            .buildAndRegister();

        //32000EU
        RecipeMaps.DIESEL_GENERATOR_FUELS.recipeBuilder()
            .fluidInputs(Materials.SulfuricLightFuel.getFluid(48))
            .duration(48)
            .EUt(-32)
            .buildAndRegister();

        //32000EU
        RecipeMaps.DIESEL_GENERATOR_FUELS.recipeBuilder()
            .fluidInputs(Materials.SulfuricNaphtha.getFluid(48))
            .duration(48)
            .EUt(-32)
            .buildAndRegister();

        //256000EU
        RecipeMaps.DIESEL_GENERATOR_FUELS.recipeBuilder()
            .fluidInputs(Materials.SulfuricHeavyFuel.getFluid(48))
            .duration(48)
            .EUt(-128)
            .buildAndRegister();

        //148000EU
        RecipeMaps.DIESEL_GENERATOR_FUELS.recipeBuilder()
            .fluidInputs(Materials.Ethanol.getFluid(120))
            .duration(60)
            .EUt(-512)
            .buildAndRegister();

        //256000EU
        RecipeMaps.DIESEL_GENERATOR_FUELS.recipeBuilder()
            .fluidInputs(Materials.LightFuel.getFluid(12))
            .duration(96)
            .EUt(-32)
            .buildAndRegister();

        //256000EU
        RecipeMaps.DIESEL_GENERATOR_FUELS.recipeBuilder()
            .fluidInputs(Materials.Naphtha.getFluid(12))
            .duration(96)
            .EUt(-32)
            .buildAndRegister();

        //512000EU
        RecipeMaps.DIESEL_GENERATOR_FUELS.recipeBuilder()
            .fluidInputs(Materials.NitroFuel.getFluid(24))
            .duration(48)
            .EUt(-512)
            .buildAndRegister();

        //8 SteamUnit -> 5EU/t
        //52 SteamUnit -> 32EU/t
        RecipeMaps.STEAM_TURBINE_FUELS.recipeBuilder()
            .fluidInputs(Materials.Steam.getFluid(2500))
            .duration(64)
            .EUt(-32)
            .buildAndRegister();

        //20000EU
        RecipeMaps.GAS_TURBINE_FUELS.recipeBuilder()
            .fluidInputs(Materials.Hydrogen.getFluid(120))
            .duration(75)
            .EUt(-32)
            .buildAndRegister();

        //208000EU  // actually seems to produce 104000
        RecipeMaps.GAS_TURBINE_FUELS.recipeBuilder()
            .fluidInputs(Materials.Methane.getFluid(60))
            .duration(52)
            .EUt(-128)
            .buildAndRegister();

        //256000EU
        RecipeMaps.GAS_TURBINE_FUELS.recipeBuilder()
            .fluidInputs(Materials.LPG.getFluid(24))
            .duration(48)
            .EUt(-128)
            .buildAndRegister();

        //9600EU // actually seems to produce 72000
        RecipeMaps.GAS_TURBINE_FUELS.recipeBuilder()
            .fluidInputs(Materials.NaturalGas.getFluid(120))
            .duration(68)
            .EUt(-128)
            .buildAndRegister();
    }
}