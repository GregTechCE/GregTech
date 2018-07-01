package gregtech.loaders.load;

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
            .fluidInputs(Materials.BioFuel.getFluid(60))
            .duration(12)
            .EUt(-32)
            .buildAndRegister();

        //32000EU
        RecipeMaps.DIESEL_GENERATOR_FUELS.recipeBuilder()
            .fluidInputs(Materials.SulfuricLightFuel.getFluid(12))
            .duration(12)
            .EUt(-32)
            .buildAndRegister();

        //32000EU
        RecipeMaps.DIESEL_GENERATOR_FUELS.recipeBuilder()
            .fluidInputs(Materials.SulfuricNaphtha.getFluid(12))
            .duration(12)
            .EUt(-32)
            .buildAndRegister();

        //256000EU
        RecipeMaps.DIESEL_GENERATOR_FUELS.recipeBuilder()
            .fluidInputs(Materials.SulfuricHeavyFuel.getFluid(12))
            .duration(24)
            .EUt(-128)
            .buildAndRegister();

        //148000EU
        RecipeMaps.DIESEL_GENERATOR_FUELS.recipeBuilder()
            .fluidInputs(Materials.Ethanol.getFluid(48))
            .duration(14)
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

        //192000EU
        RecipeMaps.DIESEL_GENERATOR_FUELS.recipeBuilder()
            .fluidInputs(Materials.HeavyFuel.getFluid(13))
            .duration(5)
            .EUt(-512)
            .buildAndRegister();

        //512000EU
        RecipeMaps.DIESEL_GENERATOR_FUELS.recipeBuilder()
            .fluidInputs(Materials.NitroFuel.getFluid(12))
            .duration(12)
            .EUt(-512)
            .buildAndRegister();

        //8 SteamUnit -> 5EU/t
        //52 SteamUnit -> 32EU/t
        RecipeMaps.STEAM_TURBINE_FUELS.recipeBuilder()
            .fluidInputs(Materials.Steam.getFluid(624))
            .duration(12)
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
            .duration(49)
            .EUt(-128)
            .buildAndRegister();

        //256000EU
        RecipeMaps.GAS_TURBINE_FUELS.recipeBuilder()
            .fluidInputs(Materials.LPG.getFluid(12))
            .duration(24)
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