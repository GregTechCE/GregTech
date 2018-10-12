package gregtech.loaders.recipe;

import gregtech.api.GTValues;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.recipes.FuelRecipe;
import gregtech.api.unification.material.Materials;
import net.minecraftforge.fluids.FluidStack;

public class FuelLoader {

    public static void registerFuels() {
        //low-tier diesel generator fuels
        registerDieselGeneratorFuel(Materials.BioFuel.getFluid(2), 1, GTValues.LV);
        registerDieselGeneratorFuel(Materials.LightFuel.getFluid(1), 8, GTValues.LV);
        registerDieselGeneratorFuel(Materials.Naphtha.getFluid(1), 8, GTValues.LV);
        registerDieselGeneratorFuel(Materials.Fuel.getFluid(1), 4, GTValues.LV);
        //high-tier diesel generator fuels
        registerDieselGeneratorFuel(Materials.Ethanol.getFluid(2), 1, GTValues.HV);
        registerDieselGeneratorFuel(Materials.NitroFuel.getFluid(1), 2, GTValues.HV);
        //steam generator fuels TODO super-heated steam
        registerSteamGeneratorFuel(Materials.Steam.getFluid(32), 1, GTValues.LV);

        //low-tier gas turbine fuels
        registerGasGeneratorFuel(Materials.Hydrogen.getFluid(1), 1, GTValues.LV);
        registerGasGeneratorFuel(Materials.Methane.getFluid(1), 2, GTValues.LV);
        //high-tier gas turbine fuels
        registerGasGeneratorFuel(Materials.LPG.getFluid(1), 3, GTValues.MV);
        registerGasGeneratorFuel(Materials.NaturalGas.getFluid(1), 1, GTValues.MV);

        //semi-fluid fuels, like creosote
        registerSemiFluidGeneratorFuel(Materials.Creosote.getFluid(2), 1, GTValues.LV);
    }

    public static void registerDieselGeneratorFuel(FluidStack fuelStack, int duration, int tier) {
        RecipeMaps.DIESEL_GENERATOR_FUELS.addRecipe(new FuelRecipe(fuelStack, duration, GTValues.V[tier]));
    }

    public static void registerSteamGeneratorFuel(FluidStack fuelStack, int duration, int tier) {
        RecipeMaps.STEAM_TURBINE_FUELS.addRecipe(new FuelRecipe(fuelStack, duration, GTValues.V[tier]));
    }

    public static void registerGasGeneratorFuel(FluidStack fuelStack, int duration, int tier) {
        RecipeMaps.GAS_TURBINE_FUELS.addRecipe(new FuelRecipe(fuelStack, duration, GTValues.V[tier]));
    }

    public static void registerSemiFluidGeneratorFuel(FluidStack fuelStack, int duration, int tier) {
        RecipeMaps.SEMI_FLUID_GENERATOR_FUELS.addRecipe(new FuelRecipe(fuelStack, duration, GTValues.V[tier]));
    }
}