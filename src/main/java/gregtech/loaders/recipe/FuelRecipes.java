package gregtech.loaders.recipe;

import gregtech.api.GTValues;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.recipes.FuelRecipe;
import gregtech.api.unification.material.Materials;
import net.minecraftforge.fluids.FluidStack;

public class FuelRecipes {

    public static void registerFuels() {
        //low-tier diesel generator fuels
        registerDieselGeneratorFuel(Materials.Naphtha.getFluid(1), 8, GTValues.LV);
        registerDieselGeneratorFuel(Materials.Oil.getFluid(2), 1, GTValues.LV);
        registerDieselGeneratorFuel(Materials.SulfuricLightFuel.getFluid(4), 5, GTValues.LV);
        registerDieselGeneratorFuel(Materials.Methanol.getFluid(8), 21, GTValues.LV);
        registerDieselGeneratorFuel(Materials.Ethanol.getFluid(1), 6, GTValues.LV);
        registerDieselGeneratorFuel(Materials.BioDiesel.getFluid(1), 8, GTValues.LV);
        registerDieselGeneratorFuel(Materials.LightFuel.getFluid(1), 10, GTValues.LV);
        registerDieselGeneratorFuel(Materials.Fuel.getFluid(1), 15, GTValues.LV);
        registerDieselGeneratorFuel(Materials.NitroFuel.getFluid(2), 45, GTValues.LV);

        //steam generator fuels
        registerSteamGeneratorFuel(Materials.Steam.getFluid(60), 1, GTValues.LV);

        //low-tier gas turbine fuels
        registerGasGeneratorFuel(Materials.NaturalGas.getFluid(8), 5, GTValues.LV);
        registerGasGeneratorFuel(Materials.Hydrogen.getFluid(8), 5, GTValues.LV);
        registerGasGeneratorFuel(Materials.CarbonMonoxde.getFluid(8), 6, GTValues.LV);
        registerGasGeneratorFuel(Materials.WoodGas.getFluid(8), 6, GTValues.LV);
        registerGasGeneratorFuel(Materials.SulfuricGas.getFluid(32), 25, GTValues.LV);
        registerGasGeneratorFuel(Materials.SulfuricNaphtha.getFluid(4), 5, GTValues.LV);
        registerGasGeneratorFuel(Materials.Methane.getFluid(4), 14, GTValues.LV);
        registerGasGeneratorFuel(Materials.Ethylene.getFluid(1), 4, GTValues.LV);
        registerGasGeneratorFuel(Materials.Gas.getFluid(1), 5, GTValues.LV);
        registerGasGeneratorFuel(Materials.Ethane.getFluid(4), 21, GTValues.LV);
        registerGasGeneratorFuel(Materials.Propene.getFluid(1), 6, GTValues.LV);
        registerGasGeneratorFuel(Materials.Butadiene.getFluid(16), 103, GTValues.LV);
        registerGasGeneratorFuel(Materials.Propane.getFluid(4), 29, GTValues.LV);
        registerGasGeneratorFuel(Materials.Butene.getFluid(1), 8, GTValues.LV);
        registerGasGeneratorFuel(Materials.Phenol.getFluid(1), 9, GTValues.LV);
        registerGasGeneratorFuel(Materials.Benzene.getFluid(1), 9, GTValues.LV);
        registerGasGeneratorFuel(Materials.Butane.getFluid(4), 37, GTValues.LV);
        registerGasGeneratorFuel(Materials.LPG.getFluid(1), 10, GTValues.LV);
        registerGasGeneratorFuel(Materials.Naphtha.getFluid(1), 10, GTValues.LV);
        registerGasGeneratorFuel(Materials.Toluene.getFluid(4), 41, GTValues.LV);

        //semi-fluid fuels, like creosote
        registerSemiFluidGeneratorFuel(Materials.Creosote.getFluid(14), 1, GTValues.LV);
        registerSemiFluidGeneratorFuel(Materials.Biomass.getFluid(14), 1, GTValues.LV);
        registerSemiFluidGeneratorFuel(Materials.OilLight.getFluid(64), 5, GTValues.LV);
        registerSemiFluidGeneratorFuel(Materials.OilMedium.getFluid(32), 15, GTValues.LV);
        registerSemiFluidGeneratorFuel(Materials.OilHeavy.getFluid(16), 5, GTValues.LV);
        registerSemiFluidGeneratorFuel(Materials.SulfuricHeavyFuel.getFluid(16), 5, GTValues.LV);
        registerSemiFluidGeneratorFuel(Materials.HeavyFuel.getFluid(8), 15, GTValues.LV);

        //plasma turbine
        registerPlasmaFuel(Materials.Helium.getPlasma(1), 2560, GTValues.LV);
        registerPlasmaFuel(Materials.Nitrogen.getPlasma(1), 4032, GTValues.LV);
        registerPlasmaFuel(Materials.Oxygen.getPlasma(1), 4096, GTValues.LV);
        registerPlasmaFuel(Materials.Iron.getPlasma(16), 103219, GTValues.LV);
        registerPlasmaFuel(Materials.Nickel.getPlasma(16), 106905, GTValues.LV);

    }

    public static void registerPlasmaFuel(FluidStack fuelStack, int duration, int tier) {
        RecipeMaps.PLASMA_GENERATOR_FUELS.addRecipe(new FuelRecipe(fuelStack, duration, GTValues.V[tier]));
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