package gregtech.loaders.recipe;

import gregtech.api.GTValues;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.recipes.FuelRecipe;
import gregtech.api.unification.material.Materials;
import net.minecraftforge.fluids.FluidStack;

public class FuelLoader {

    public static void registerFuels() {
        //low-tier diesel generator fuels
        registerDieselGeneratorFuel(Materials.BioFuel.getFluid(4), 1, GTValues.LV);
        registerDieselGeneratorFuel(Materials.LightFuel.getFluid(1), 8, GTValues.LV);
        registerDieselGeneratorFuel(Materials.Naphtha.getFluid(1), 8, GTValues.LV);
        registerDieselGeneratorFuel(Materials.Fuel.getFluid(1), 4, GTValues.LV);
        registerDieselGeneratorFuel(Materials.Oil.getFluid(2), 1, GTValues.LV);
        registerDieselGeneratorFuel(Materials.SulfuricLightFuel.getFluid(4), 5, GTValues.LV);
        //registerDieselGeneratorFuel(GAMaterials.Methanol.getFluid(8), 21, GTValues.LV);

        //high-tier diesel generator fuels
        registerDieselGeneratorFuel(Materials.Ethanol.getFluid(2), 1, GTValues.HV);
        registerDieselGeneratorFuel(Materials.NitroFuel.getFluid(1), 2, GTValues.HV);

        //steam generator fuels
        registerSteamGeneratorFuel(Materials.Steam.getFluid(54), 1, GTValues.LV);

        //low-tier gas turbine fuels
        registerGasGeneratorFuel(Materials.Hydrogen.getFluid(1), 1, GTValues.LV);
        registerGasGeneratorFuel(Materials.Methane.getFluid(1), 2, GTValues.LV);

        //high-tier gas turbine fuels
        registerGasGeneratorFuel(Materials.LPG.getFluid(1), 3, GTValues.MV);
        registerGasGeneratorFuel(Materials.NaturalGas.getFluid(1), 1, GTValues.MV);

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

        /*
        registerGasGeneratorFuel(Materials.NaturalGas.getFluid(8), 5, GTValues.LV);
        registerGasGeneratorFuel(Materials.Hydrogen.getFluid(8), 5, GTValues.LV);
        registerGasGeneratorFuel(GAMaterials.CarbonMonoxde.getFluid(8), 6, GTValues.LV);
        registerGasGeneratorFuel(GAMaterials.WoodGas.getFluid(8), 6, GTValues.LV);
        registerGasGeneratorFuel(Materials.SulfuricGas.getFluid(32), 25, GTValues.LV);
        registerGasGeneratorFuel(Materials.SulfuricNaphtha.getFluid(4), 5, GTValues.LV);
        registerGasGeneratorFuel(GAMaterials.BioGas.getFluid(4), 45, GTValues.LV);
        registerGasGeneratorFuel(Materials.Methane.getFluid(4), 14, GTValues.LV);
        registerGasGeneratorFuel(GAMaterials.Ethylene.getFluid(1), 4, GTValues.LV);
        registerGasGeneratorFuel(Materials.Gas.getFluid(1), 5, GTValues.LV);
        registerGasGeneratorFuel(GAMaterials.Ethane.getFluid(4), 21, GTValues.LV);
        registerGasGeneratorFuel(GAMaterials.Propene.getFluid(1), 6, GTValues.LV);
        registerGasGeneratorFuel(GAMaterials.Butadiene.getFluid(16), 103, GTValues.LV);
        registerGasGeneratorFuel(GAMaterials.Propane.getFluid(4), 29, GTValues.LV);
        registerGasGeneratorFuel(GAMaterials.Butene.getFluid(1), 8, GTValues.LV);
        registerGasGeneratorFuel(GAMaterials.Phenol.getFluid(1), 9, GTValues.LV);
        registerGasGeneratorFuel(GAMaterials.Benzene.getFluid(1), 9, GTValues.LV);
        registerGasGeneratorFuel(GAMaterials.Butane.getFluid(4), 37, GTValues.LV);
        registerGasGeneratorFuel(Materials.LPG.getFluid(1), 10, GTValues.LV);
        registerGasGeneratorFuel(Materials.Naphtha.getFluid(1), 10, GTValues.LV);
        registerGasGeneratorFuel(Materials.Toluene.getFluid(4), 41, GTValues.LV);
         */
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