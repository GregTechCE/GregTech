package gregtech.loaders.recipe;

import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.common.items.MetaItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.recipes.RecipeMaps.*;

public class ChemistryRecipes {

    public static void init() {

        //Cracking Unit
        CRACKING_RECIPES.recipeBuilder()
            .fluidInputs(Hydrogen.getFluid(2000))
            .fluidInputs(Ethane.getFluid(1000))
            .fluidOutputs(HydroCrackedEthane.getFluid(1000))
            .duration(40).EUt(120).buildAndRegister();

        CRACKING_RECIPES.recipeBuilder()
            .fluidInputs(Hydrogen.getFluid(2000))
            .fluidInputs(Ethylene.getFluid(1000))
            .fluidOutputs(HydroCrackedEthylene.getFluid(1000))
            .duration(40).EUt(120).buildAndRegister();

        CRACKING_RECIPES.recipeBuilder()
            .fluidInputs(Hydrogen.getFluid(2000))
            .fluidInputs(Propene.getFluid(1000))
            .fluidOutputs(HydroCrackedPropene.getFluid(1000))
            .duration(40).EUt(120).buildAndRegister();

        CRACKING_RECIPES.recipeBuilder()
            .fluidInputs(Hydrogen.getFluid(2000))
            .fluidInputs(Propane.getFluid(1000))
            .fluidOutputs(HydroCrackedPropane.getFluid(1000))
            .duration(40).EUt(120).buildAndRegister();

        CRACKING_RECIPES.recipeBuilder()
            .fluidInputs(Hydrogen.getFluid(2000))
            .fluidInputs(LightFuel.getFluid(1000))
            .fluidOutputs(HydroCrackedLightFuel.getFluid(1000))
            .duration(40).EUt(120).buildAndRegister();

        CRACKING_RECIPES.recipeBuilder()
            .fluidInputs(Hydrogen.getFluid(2000))
            .fluidInputs(Butane.getFluid(1000))
            .fluidOutputs(HydroCrackedButane.getFluid(1000))
            .duration(40).EUt(120).buildAndRegister();

        CRACKING_RECIPES.recipeBuilder()
            .fluidInputs(Hydrogen.getFluid(2000))
            .fluidInputs(Naphtha.getFluid(1000))
            .fluidOutputs(HydroCrackedNaphtha.getFluid(1000))
            .duration(40).EUt(120).buildAndRegister();

        CRACKING_RECIPES.recipeBuilder()
            .fluidInputs(Hydrogen.getFluid(2000))
            .fluidInputs(HeavyFuel.getFluid(1000))
            .fluidOutputs(HydroCrackedHeavyFuel.getFluid(1000))
            .duration(40).EUt(120).buildAndRegister();

        CRACKING_RECIPES.recipeBuilder()
            .fluidInputs(Hydrogen.getFluid(2000))
            .fluidInputs(Gas.getFluid(1000))
            .fluidOutputs(HydroCrackedGas.getFluid(1000))
            .duration(40).EUt(120).buildAndRegister();

        CRACKING_RECIPES.recipeBuilder()
            .fluidInputs(Hydrogen.getFluid(2000))
            .fluidInputs(Butene.getFluid(1000))
            .fluidOutputs(HydroCrackedButene.getFluid(1000))
            .duration(40).EUt(120).buildAndRegister();

        CRACKING_RECIPES.recipeBuilder()
            .fluidInputs(Hydrogen.getFluid(2000))
            .fluidInputs(Butadiene.getFluid(1000))
            .fluidOutputs(HydroCrackedButadiene.getFluid(1000))
            .duration(40).EUt(120).buildAndRegister();

        CRACKING_RECIPES.recipeBuilder()
            .fluidInputs(Steam.getFluid(2000))
            .fluidInputs(Ethane.getFluid(1000))
            .fluidOutputs(SteamCrackedEthane.getFluid(1000))
            .duration(40).EUt(120).buildAndRegister();

        CRACKING_RECIPES.recipeBuilder()
            .fluidInputs(Steam.getFluid(2000))
            .fluidInputs(Ethylene.getFluid(1000))
            .fluidOutputs(SteamCrackedEthylene.getFluid(1000))
            .duration(40).EUt(120).buildAndRegister();

        CRACKING_RECIPES.recipeBuilder()
            .fluidInputs(Steam.getFluid(2000))
            .fluidInputs(Propene.getFluid(1000))
            .fluidOutputs(SteamCrackedPropene.getFluid(1000))
            .duration(40).EUt(120).buildAndRegister();

        CRACKING_RECIPES.recipeBuilder()
            .fluidInputs(Steam.getFluid(2000))
            .fluidInputs(Propane.getFluid(1000))
            .fluidOutputs(SteamCrackedPropane.getFluid(1000))
            .duration(40).EUt(120).buildAndRegister();

        CRACKING_RECIPES.recipeBuilder()
            .fluidInputs(Steam.getFluid(2000))
            .fluidInputs(LightFuel.getFluid(1000))
            .fluidOutputs(CrackedLightFuel.getFluid(1000))
            .duration(40).EUt(120).buildAndRegister();

        CRACKING_RECIPES.recipeBuilder()
            .fluidInputs(Steam.getFluid(2000))
            .fluidInputs(Butane.getFluid(1000))
            .fluidOutputs(SteamCrackedButane.getFluid(1000))
            .duration(40).EUt(120).buildAndRegister();

        CRACKING_RECIPES.recipeBuilder()
            .fluidInputs(Steam.getFluid(2000))
            .fluidInputs(Naphtha.getFluid(1000))
            .fluidOutputs(SteamCrackedNaphtha.getFluid(1000))
            .duration(40).EUt(120).buildAndRegister();

        CRACKING_RECIPES.recipeBuilder()
            .fluidInputs(Steam.getFluid(2000))
            .fluidInputs(HeavyFuel.getFluid(1000))
            .fluidOutputs(CrackedHeavyFuel.getFluid(1000))
            .duration(40).EUt(120).buildAndRegister();

        CRACKING_RECIPES.recipeBuilder()
            .fluidInputs(Steam.getFluid(2000))
            .fluidInputs(Gas.getFluid(1000))
            .fluidOutputs(SteamCrackedGas.getFluid(1000))
            .duration(40).EUt(120).buildAndRegister();

        CRACKING_RECIPES.recipeBuilder()
            .fluidInputs(Steam.getFluid(2000))
            .fluidInputs(Butene.getFluid(1000))
            .fluidOutputs(SteamCrackedButene.getFluid(1000))
            .duration(40).EUt(120).buildAndRegister();

        CRACKING_RECIPES.recipeBuilder()
            .fluidInputs(Steam.getFluid(2000))
            .fluidInputs(Butadiene.getFluid(1000))
            .fluidOutputs(SteamCrackedButadiene.getFluid(1000))
            .duration(40).EUt(120).buildAndRegister();


        //Distillation Tower / Distillery
        DISTILLATION_RECIPES.recipeBuilder()
            .fluidInputs(Creosote.getFluid(24))
            .fluidOutputs(Lubricant.getFluid(12))
            .duration(16).EUt(96).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .fluidInputs(HydroCrackedEthane.getFluid(1000))
            .fluidOutputs(Methane.getFluid(2000))
            .duration(120).EUt(120).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .fluidInputs(SteamCrackedEthane.getFluid(1000))
            .output(dustSmall, Carbon, 2)
            .fluidOutputs(Methane.getFluid(1500))
            .duration(120).EUt(120).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .fluidInputs(HydroCrackedEthylene.getFluid(1000))
            .fluidOutputs(Ethane.getFluid(1000))
            .duration(120).EUt(120).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .fluidInputs(SteamCrackedEthylene.getFluid(1000))
            .output(dust, Carbon)
            .fluidOutputs(Methane.getFluid(1000))
            .duration(120).EUt(120).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .fluidInputs(HydroCrackedPropene.getFluid(1000))
            .fluidOutputs(Propane.getFluid(500))
            .fluidOutputs(Ethylene.getFluid(500))
            .fluidOutputs(Methane.getFluid(500))
            .duration(120).EUt(120).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .fluidInputs(SteamCrackedPropene.getFluid(1000))
            .output(dustSmall, Carbon, 6)
            .fluidOutputs(Methane.getFluid(1500))
            .duration(180).EUt(120).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .fluidInputs(HydroCrackedPropane.getFluid(1000))
            .fluidOutputs(Ethane.getFluid(1000))
            .fluidOutputs(Methane.getFluid(1000))
            .duration(120).EUt(120).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .fluidInputs(SteamCrackedPropane.getFluid(2000))
            .output(dustSmall, Carbon, 3)
            .fluidOutputs(Ethylene.getFluid(500))
            .fluidOutputs(Methane.getFluid(3500))
            .duration(240).EUt(120).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .fluidInputs(HydroCrackedLightFuel.getFluid(1000))
            .fluidOutputs(Naphtha.getFluid(800))
            .fluidOutputs(Butane.getFluid(150))
            .fluidOutputs(Propane.getFluid(200))
            .fluidOutputs(Ethane.getFluid(125))
            .fluidOutputs(Methane.getFluid(125))
            .duration(120).EUt(120).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .fluidInputs(CrackedLightFuel.getFluid(1000))
            .output(dustTiny, Carbon)
            .fluidOutputs(HeavyFuel.getFluid(50))
            .fluidOutputs(Naphtha.getFluid(100))
            .fluidOutputs(Toluene.getFluid(30))
            .fluidOutputs(Benzene.getFluid(150))
            .fluidOutputs(Butene.getFluid(65))
            .fluidOutputs(Butadiene.getFluid(50))
            .fluidOutputs(Propane.getFluid(50))
            .fluidOutputs(Propene.getFluid(250))
            .fluidOutputs(Ethane.getFluid(50))
            .fluidOutputs(Ethylene.getFluid(250))
            .fluidOutputs(Methane.getFluid(250))
            .duration(120).EUt(120).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .fluidInputs(HydroCrackedButane.getFluid(750))
            .fluidOutputs(Propane.getFluid(500))
            .fluidOutputs(Ethane.getFluid(500))
            .fluidOutputs(Methane.getFluid(500))
            .duration(90).EUt(120).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .fluidInputs(SteamCrackedButane.getFluid(2000))
            .output(dustSmall, Carbon, 9)
            .fluidOutputs(Propane.getFluid(250))
            .fluidOutputs(Ethane.getFluid(250))
            .fluidOutputs(Ethylene.getFluid(250))
            .fluidOutputs(Methane.getFluid(4000))
            .duration(240).EUt(120).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .fluidInputs(HydroCrackedNaphtha.getFluid(1000))
            .fluidOutputs(Butane.getFluid(800))
            .fluidOutputs(Propane.getFluid(300))
            .fluidOutputs(Ethane.getFluid(250))
            .fluidOutputs(Methane.getFluid(250))
            .duration(120).EUt(120).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .fluidInputs(SteamCrackedNaphtha.getFluid(1000))
            .output(dustTiny, Carbon, 3)
            .fluidOutputs(HeavyFuel.getFluid(25))
            .fluidOutputs(LightFuel.getFluid(50))
            .fluidOutputs(Toluene.getFluid(20))
            .fluidOutputs(Benzene.getFluid(100))
            .fluidOutputs(Butene.getFluid(50))
            .fluidOutputs(Butadiene.getFluid(50))
            .fluidOutputs(Propane.getFluid(15))
            .fluidOutputs(Propene.getFluid(300))
            .fluidOutputs(Ethane.getFluid(65))
            .fluidOutputs(Ethylene.getFluid(500))
            .fluidOutputs(Methane.getFluid(500))
            .duration(120).EUt(120).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .fluidInputs(HydroCrackedHeavyFuel.getFluid(1000))
            .fluidOutputs(LightFuel.getFluid(600))
            .fluidOutputs(Naphtha.getFluid(100))
            .fluidOutputs(Butane.getFluid(100))
            .fluidOutputs(Propane.getFluid(100))
            .fluidOutputs(Ethane.getFluid(75))
            .fluidOutputs(Methane.getFluid(75))
            .duration(120).EUt(120).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .fluidInputs(CrackedHeavyFuel.getFluid(1000))
            .output(dustTiny, Carbon, 3)
            .fluidOutputs(LightFuel.getFluid(100))
            .fluidOutputs(Naphtha.getFluid(125))
            .fluidOutputs(Toluene.getFluid(80))
            .fluidOutputs(Benzene.getFluid(400))
            .fluidOutputs(Butene.getFluid(80))
            .fluidOutputs(Butadiene.getFluid(50))
            .fluidOutputs(Propane.getFluid(10))
            .fluidOutputs(Propene.getFluid(100))
            .fluidOutputs(Ethane.getFluid(15))
            .fluidOutputs(Ethylene.getFluid(150))
            .fluidOutputs(Methane.getFluid(150))
            .duration(120).EUt(120).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .fluidInputs(HydroCrackedGas.getFluid(1000))
            .fluidOutputs(Methane.getFluid(1400))
            .fluidOutputs(Hydrogen.getFluid(1340))
            .fluidOutputs(Helium.getFluid(20))
            .duration(120).EUt(120).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .fluidInputs(SteamCrackedGas.getFluid(800))
            .output(dustTiny, Carbon)
            .fluidOutputs(Propene.getFluid(6))
            .fluidOutputs(Ethane.getFluid(6))
            .fluidOutputs(Ethylene.getFluid(20))
            .fluidOutputs(Methane.getFluid(914))
            .fluidOutputs(Helium.getFluid(16))
            .duration(96).EUt(120).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .fluidInputs(HydroCrackedButene.getFluid(750))
            .fluidOutputs(Butane.getFluid(250))
            .fluidOutputs(Propene.getFluid(250))
            .fluidOutputs(Ethane.getFluid(250))
            .fluidOutputs(Methane.getFluid(250))
            .duration(90).EUt(120).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .fluidInputs(SteamCrackedButene.getFluid(2000))
            .output(dust, Carbon, 3)
            .fluidOutputs(Propene.getFluid(250))
            .fluidOutputs(Ethylene.getFluid(625))
            .fluidOutputs(Methane.getFluid(3000))
            .duration(240).EUt(120).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .fluidInputs(HydroCrackedButadiene.getFluid(750))
            .fluidOutputs(Butene.getFluid(500))
            .fluidOutputs(Ethylene.getFluid(500))
            .duration(90).EUt(120).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .fluidInputs(SteamCrackedButadiene.getFluid(2000))
            .output(dust, Carbon, 2)
            .fluidOutputs(Propene.getFluid(250))
            .fluidOutputs(Ethylene.getFluid(375))
            .fluidOutputs(Methane.getFluid(2250))
            .duration(240).EUt(120).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .fluidInputs(OilLight.getFluid(150))
            .fluidOutputs(SulfuricHeavyFuel.getFluid(10))
            .fluidOutputs(SulfuricLightFuel.getFluid(20))
            .fluidOutputs(SulfuricNaphtha.getFluid(30))
            .fluidOutputs(SulfuricGas.getFluid(240))
            .duration(20).EUt(96).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .fluidInputs(OilMedium.getFluid(100))
            .fluidOutputs(SulfuricHeavyFuel.getFluid(15))
            .fluidOutputs(SulfuricLightFuel.getFluid(50))
            .fluidOutputs(SulfuricNaphtha.getFluid(20))
            .fluidOutputs(SulfuricGas.getFluid(60))
            .duration(20).EUt(96).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .fluidInputs(OilHeavy.getFluid(150))
            .fluidOutputs(SulfuricHeavyFuel.getFluid(250))
            .fluidOutputs(SulfuricLightFuel.getFluid(45))
            .fluidOutputs(SulfuricNaphtha.getFluid(15))
            .fluidOutputs(SulfuricGas.getFluid(600))
            .duration(20).EUt(288).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .fluidInputs(Oil.getFluid(50))
            .fluidOutputs(SulfuricHeavyFuel.getFluid(15))
            .fluidOutputs(SulfuricLightFuel.getFluid(50))
            .fluidOutputs(SulfuricNaphtha.getFluid(20))
            .fluidOutputs(SulfuricGas.getFluid(60))
            .duration(20).EUt(96).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .fluidInputs(DilutedHydrochloricAcid.getFluid(2000))
            .fluidOutputs(Water.getFluid(1000))
            .fluidOutputs(HydrochloricAcid.getFluid(1000))
            .duration(600).EUt(64).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .fluidInputs(DilutedSulfuricAcid.getFluid(3000))
            .fluidOutputs(SulfuricAcid.getFluid(2000))
            .fluidOutputs(Water.getFluid(1000))
            .duration(600).EUt(120).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .fluidInputs(CharcoalByproducts.getFluid(1000))
            .output(dustSmall, Charcoal)
            .fluidOutputs(WoodTar.getFluid(250))
            .fluidOutputs(WoodVinegar.getFluid(500))
            .fluidOutputs(WoodGas.getFluid(250))
            .duration(40).EUt(256).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .fluidInputs(WoodTar.getFluid(1000))
            .fluidOutputs(Creosote.getFluid(500))
            .fluidOutputs(Phenol.getFluid(75))
            .fluidOutputs(Benzene.getFluid(350))
            .fluidOutputs(Toluene.getFluid(75))
            .duration(40).EUt(256).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .fluidInputs(WoodGas.getFluid(1000))
            .fluidOutputs(CarbonDioxide.getFluid(490))
            .fluidOutputs(Ethylene.getFluid(20))
            .fluidOutputs(Methane.getFluid(130))
            .fluidOutputs(CarbonMonoxde.getFluid(340))
            .fluidOutputs(Hydrogen.getFluid(20))
            .duration(40).EUt(256).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .fluidInputs(Water.getFluid(576))
            .fluidOutputs(DistilledWater.getFluid(520))
            .duration(160).EUt(120).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .fluidInputs(Acetone.getFluid(1000))
            .fluidOutputs(Ethenone.getFluid(1000))
            .fluidOutputs(Methane.getFluid(1000))
            .duration(80).EUt(640).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .fluidInputs(CalciumAcetate.getFluid(1000))
            .output(dust, Quicklime, 2)
            .fluidOutputs(Acetone.getFluid(1000))
            .fluidOutputs(CarbonDioxide.getFluid(1000))
            .fluidOutputs(Water.getFluid(1000))
            .duration(80).EUt(120).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .fluidInputs(SeedOil.getFluid(24))
            .fluidOutputs(Lubricant.getFluid(12))
            .duration(16).EUt(96).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .fluidInputs(WoodVinegar.getFluid(1000))
            .fluidOutputs(AceticAcid.getFluid(100))
            .fluidOutputs(Water.getFluid(500))
            .fluidOutputs(Ethanol.getFluid(10))
            .fluidOutputs(Methanol.getFluid(300))
            .fluidOutputs(Acetone.getFluid(50))
            .fluidOutputs(MethylAcetate.getFluid(10))
            .duration(40).EUt(256).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .fluidInputs(FermentedBiomass.getFluid(1000))
            .fluidOutputs(AceticAcid.getFluid(25))
            .fluidOutputs(Water.getFluid(375))
            .fluidOutputs(Ethanol.getFluid(150))
            .fluidOutputs(Methanol.getFluid(150))
            .fluidOutputs(Ammonia.getFluid(100))
            .fluidOutputs(CarbonDioxide.getFluid(400))
            .fluidOutputs(Methane.getFluid(600))
            .duration(75).EUt(180).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .fluidInputs(Biomass.getFluid(1000))
            .output(dustSmall, Wood, 2)
            .fluidOutputs(Ethanol.getFluid(600))
            .fluidOutputs(Water.getFluid(300))
            .duration(32).EUt(400).buildAndRegister();

        DISTILLERY_RECIPES.recipeBuilder()
            .circuitMeta(1)
            .fluidInputs(Toluene.getFluid(30))
            .fluidOutputs(LightFuel.getFluid(30))
            .duration(160).EUt(24).buildAndRegister();

        DISTILLERY_RECIPES.recipeBuilder()
            .circuitMeta(1)
            .fluidInputs(HeavyFuel.getFluid(10))
            .fluidOutputs(Toluene.getFluid(4))
            .duration(16).EUt(24).buildAndRegister();

        DISTILLERY_RECIPES.recipeBuilder()
            .circuitMeta(2)
            .fluidInputs(HeavyFuel.getFluid(10))
            .fluidOutputs(Benzene.getFluid(4))
            .duration(16).EUt(24).buildAndRegister();

        DISTILLERY_RECIPES.recipeBuilder()
            .circuitMeta(3)
            .fluidInputs(HeavyFuel.getFluid(20))
            .fluidOutputs(Phenol.getFluid(5))
            .duration(32).EUt(24).buildAndRegister();

        DISTILLERY_RECIPES.recipeBuilder()
            .circuitMeta(4)
            .fluidInputs(OilLight.getFluid(300))
            .fluidOutputs(Oil.getFluid(100))
            .duration(16).EUt(24).buildAndRegister();

        DISTILLERY_RECIPES.recipeBuilder()
            .circuitMeta(4)
            .fluidInputs(OilMedium.getFluid(200))
            .fluidOutputs(Oil.getFluid(100))
            .duration(16).EUt(24).buildAndRegister();

        DISTILLERY_RECIPES.recipeBuilder()
            .circuitMeta(4)
            .fluidInputs(OilHeavy.getFluid(100))
            .fluidOutputs(Oil.getFluid(100))
            .duration(16).EUt(24).buildAndRegister();


        // Centrifuge
        CENTRIFUGE_RECIPES.recipeBuilder()
            .fluidInputs(Gas.getFluid(8000))
            .fluidOutputs(Methane.getFluid(4000))
            .fluidOutputs(LPG.getFluid(4000))
            .duration(200).EUt(5).buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
            .fluidInputs(LiquidAir.getFluid(53000))
            .fluidOutputs(Nitrogen.getFluid(32000))
            .fluidOutputs(Nitrogen.getFluid(8000))
            .fluidOutputs(Oxygen.getFluid(11000))
            .fluidOutputs(Argon.getFluid(1000))
            .fluidOutputs(NobleGases.getFluid(1000))
            .duration(1484).EUt(5).buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
            .fluidInputs(NobleGases.getFluid(34000))
            .fluidOutputs(CarbonDioxide.getFluid(21000))
            .fluidOutputs(Helium.getFluid(9000))
            .fluidOutputs(Methane.getFluid(3000))
            .fluidOutputs(Deuterium.getFluid(1000))
            .duration(680).EUt(5).buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
            .fluidInputs(Butane.getFluid(320))
            .fluidOutputs(LPG.getFluid(370))
            .duration(20).EUt(5).buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
            .fluidInputs(Propane.getFluid(320))
            .fluidOutputs(LPG.getFluid(290))
            .duration(20).EUt(5).buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
            .fluidInputs(NitrationMixture.getFluid(2000))
            .fluidOutputs(NitricAcid.getFluid(1000))
            .fluidOutputs(SulfuricAcid.getFluid(1000))
            .duration(192).EUt(30).buildAndRegister();


        // Mixer
        MIXER_RECIPES.recipeBuilder()
            .fluidInputs(NitricAcid.getFluid(1000))
            .fluidInputs(SulfuricAcid.getFluid(1000))
            .fluidOutputs(NitrationMixture.getFluid(2000))
            .duration(500).EUt(2).buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
            .input(dust, Sodium, 2)
            .input(dust, Sulfur)
            .output(dust, SodiumSulfide, 3)
            .duration(60).EUt(30).buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
            .fluidInputs(PolyvinylAcetate.getFluid(1000))
            .fluidInputs(Acetone.getFluid(1500))
            .fluidOutputs(Glue.getFluid(2500))
            .duration(50).EUt(8).buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
            .fluidInputs(PolyvinylAcetate.getFluid(1000))
            .fluidInputs(MethylAcetate.getFluid(1500))
            .fluidOutputs(Glue.getFluid(2500))
            .duration(50).EUt(8).buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
            .input(dust, Wood, 4)
            .fluidInputs(SulfuricAcid.getFluid(1000))
            .outputs(new ItemStack(Items.COAL, 1, 1))
            .fluidOutputs(DilutedSulfuricAcid.getFluid(1000))
            .duration(1200).EUt(2).buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Items.SUGAR, 4))
            .fluidInputs(SulfuricAcid.getFluid(1000))
            .outputs(new ItemStack(Items.COAL, 1, 1))
            .fluidOutputs(DilutedSulfuricAcid.getFluid(1000))
            .duration(1200).EUt(2).buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
            .input(dust, Gallium)
            .input(dust, Arsenic)
            .output(dust, GalliumArsenide, 2)
            .duration(300).EUt(30).buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
            .input(dust, Salt, 2)
            .fluidInputs(Water.getFluid(1000))
            .fluidOutputs(SaltWater.getFluid(1000))
            .duration(40).EUt(8).buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
            .fluidInputs(BioDiesel.getFluid(1000))
            .fluidInputs(Tetranitromethane.getFluid(40))
            .fluidOutputs(NitroFuel.getFluid(750))
            .duration(20).EUt(480).buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
            .fluidInputs(Fuel.getFluid(1000))
            .fluidInputs(Tetranitromethane.getFluid(20))
            .fluidOutputs(NitroFuel.getFluid(1000))
            .duration(20).EUt(480).buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
            .fluidInputs(Oxygen.getFluid(1000))
            .fluidInputs(Dimethylhydrazine.getFluid(1000))
            .fluidOutputs(RocketFuel.getFluid(3000))
            .duration(60).EUt(16).buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
            .fluidInputs(DinitrogenTetroxide.getFluid(1000))
            .fluidInputs(Dimethylhydrazine.getFluid(1000))
            .fluidOutputs(RocketFuel.getFluid(6000))
            .duration(60).EUt(16).buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
            .fluidInputs(LightFuel.getFluid(5000))
            .fluidInputs(HeavyFuel.getFluid(1000))
            .fluidOutputs(Fuel.getFluid(6000))
            .duration(16).EUt(120).buildAndRegister();

        for (DustMaterial dustMaterial : new DustMaterial[]{Talc, Soapstone, Redstone}) {
            MIXER_RECIPES.recipeBuilder()
                .input(dust, dustMaterial)
                .fluidInputs(Oil.getFluid(750))
                .fluidOutputs(Lubricant.getFluid(750))
                .duration(128).EUt(4).buildAndRegister();

            MIXER_RECIPES.recipeBuilder()
                .input(dust, dustMaterial)
                .fluidInputs(Creosote.getFluid(750))
                .fluidOutputs(Lubricant.getFluid(750))
                .duration(128).EUt(4).buildAndRegister();

            MIXER_RECIPES.recipeBuilder()
                .input(dust, dustMaterial)
                .fluidInputs(SeedOil.getFluid(750))
                .fluidOutputs(Lubricant.getFluid(750))
                .duration(128).EUt(4).buildAndRegister();
        }


        // Electrolyzer
        ELECTROLYZER_RECIPES.recipeBuilder()
            .input(dust, SodiumBisulfate, 14)
            .fluidOutputs(SodiumPersulfate.getFluid(1000))
            .fluidOutputs(Hydrogen.getFluid(2000))
            .duration(448).EUt(60).buildAndRegister();

        ELECTROLYZER_RECIPES.recipeBuilder()
            .fluidInputs(SaltWater.getFluid(1000))
            .output(dust, SodiumHydroxide, 3)
            .fluidOutputs(Chlorine.getFluid(1000))
            .fluidOutputs(Hydrogen.getFluid(1000))
            .duration(720).EUt(30).buildAndRegister();

        ELECTROLYZER_RECIPES.recipeBuilder()
            .input(dust, Sphalerite, 2)
            .output(dust, Zinc)
            .output(dust, Sulfur)
            .chancedOutput(OreDictUnifier.get(dustTiny, Gallium), 2500, 1000)
            .duration(200).EUt(30).buildAndRegister();

        ELECTROLYZER_RECIPES.recipeBuilder()
            .input(dust, Bauxite, 39)
            .output(dust, Rutile, 6)
            .output(dust, Aluminium, 16)
            .fluidOutputs(Hydrogen.getFluid(10000))
            .fluidOutputs(Oxygen.getFluid(11000))
            .duration(2496).EUt(60).buildAndRegister();

        ELECTROLYZER_RECIPES.recipeBuilder()
            .fluidInputs(Water.getFluid(1000))
            .fluidOutputs(Hydrogen.getFluid(2000))
            .fluidOutputs(Oxygen.getFluid(1000))
            .duration(1500).EUt(30).buildAndRegister();

        // TODO Make sure recipe right below this works
        ELECTROLYZER_RECIPES.recipeBuilder()
            .fluidInputs(DistilledWater.getFluid(1000))
            .fluidOutputs(Hydrogen.getFluid(2000))
            .fluidOutputs(Oxygen.getFluid(1000))
            .duration(1500).EUt(30).buildAndRegister();

        ELECTROLYZER_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Items.DYE, 3))
            .output(dust, Calcium)
            .duration(96).EUt(26).buildAndRegister();

        ELECTROLYZER_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Blocks.SAND, 8))
            .output(dust, SiliconDioxide)
            .duration(500).EUt(25).buildAndRegister();

        ELECTROLYZER_RECIPES.recipeBuilder()
            .input(dust, Graphite)
            .output(dust, Carbon, 4)
            .duration(100).EUt(26).buildAndRegister();

        ELECTROLYZER_RECIPES.recipeBuilder()
            .fluidInputs(AceticAcid.getFluid(2000))
            .fluidOutputs(Ethane.getFluid(1000))
            .fluidOutputs(CarbonDioxide.getFluid(2000))
            .fluidOutputs(Hydrogen.getFluid(2000))
            .duration(512).EUt(60).buildAndRegister();

        ELECTROLYZER_RECIPES.recipeBuilder()
            .fluidInputs(Chloromethane.getFluid(2000))
            .fluidOutputs(Ethane.getFluid(1000))
            .fluidOutputs(Chlorine.getFluid(2000))
            .duration(400).EUt(60).buildAndRegister();

        ELECTROLYZER_RECIPES.recipeBuilder()
            .fluidInputs(MethylAcetate.getFluid(1000))
            .output(dust, Carbon)
            .fluidOutputs(AceticAcid.getFluid(1000))
            .duration(264).EUt(60).buildAndRegister();

        ELECTROLYZER_RECIPES.recipeBuilder()
            .fluidInputs(Acetone.getFluid(2000))
            .output(dust, Carbon, 3)
            .fluidOutputs(Propane.getFluid(1000))
            .fluidOutputs(Water.getFluid(2000))
            .duration(480).EUt(60).buildAndRegister();


        // Brewing
        BREWING_RECIPES.recipeBuilder()
            .inputs(MetaItems.PLANT_BALL.getStackForm())
            .fluidInputs(Honey.getFluid(180))
            .fluidOutputs(Biomass.getFluid(270))
            .duration(1440).EUt(3).buildAndRegister();

        BREWING_RECIPES.recipeBuilder()
            .input("treeSapling", 1)
            .fluidInputs(Honey.getFluid(100))
            .fluidOutputs(Biomass.getFluid(150))
            .duration(600).EUt(3).buildAndRegister();

        BREWING_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Items.POTATO))
            .fluidInputs(Honey.getFluid(20))
            .fluidOutputs(Biomass.getFluid(30))
            .duration(160).EUt(3).buildAndRegister();

        BREWING_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Items.CARROT))
            .fluidInputs(Honey.getFluid(20))
            .fluidOutputs(Biomass.getFluid(30))
            .duration(160).EUt(3).buildAndRegister();

        BREWING_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Blocks.CACTUS))
            .fluidInputs(Honey.getFluid(20))
            .fluidOutputs(Biomass.getFluid(30))
            .duration(160).EUt(3).buildAndRegister();

        BREWING_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Items.REEDS))
            .fluidInputs(Honey.getFluid(20))
            .fluidOutputs(Biomass.getFluid(30))
            .duration(160).EUt(3).buildAndRegister();

        BREWING_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Blocks.BROWN_MUSHROOM))
            .fluidInputs(Honey.getFluid(20))
            .fluidOutputs(Biomass.getFluid(30))
            .duration(160).EUt(3).buildAndRegister();

        BREWING_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Blocks.RED_MUSHROOM))
            .fluidInputs(Honey.getFluid(20))
            .fluidOutputs(Biomass.getFluid(30))
            .duration(160).EUt(3).buildAndRegister();

        BREWING_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Items.BEETROOT))
            .fluidInputs(Honey.getFluid(20))
            .fluidOutputs(Biomass.getFluid(30))
            .duration(160).EUt(3).buildAndRegister();

        BREWING_RECIPES.recipeBuilder()
            .inputs(MetaItems.PLANT_BALL.getStackForm())
            .fluidInputs(Juice.getFluid(180))
            .fluidOutputs(Biomass.getFluid(270))
            .duration(1440).EUt(3).buildAndRegister();

        BREWING_RECIPES.recipeBuilder()
            .input("treeSapling", 1)
            .fluidInputs(Juice.getFluid(100))
            .fluidOutputs(Biomass.getFluid(150))
            .duration(600).EUt(3).buildAndRegister();

        BREWING_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Items.POTATO))
            .fluidInputs(Juice.getFluid(20))
            .fluidOutputs(Biomass.getFluid(30))
            .duration(160).EUt(3).buildAndRegister();

        BREWING_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Items.CARROT))
            .fluidInputs(Juice.getFluid(20))
            .fluidOutputs(Biomass.getFluid(30))
            .duration(160).EUt(3).buildAndRegister();

        BREWING_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Blocks.CACTUS))
            .fluidInputs(Juice.getFluid(20))
            .fluidOutputs(Biomass.getFluid(30))
            .duration(160).EUt(3).buildAndRegister();

        BREWING_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Items.REEDS))
            .fluidInputs(Juice.getFluid(20))
            .fluidOutputs(Biomass.getFluid(30))
            .duration(160).EUt(3).buildAndRegister();

        BREWING_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Blocks.BROWN_MUSHROOM))
            .fluidInputs(Juice.getFluid(20))
            .fluidOutputs(Biomass.getFluid(30))
            .duration(160).EUt(3).buildAndRegister();

        BREWING_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Blocks.RED_MUSHROOM))
            .fluidInputs(Juice.getFluid(20))
            .fluidOutputs(Biomass.getFluid(30))
            .duration(160).EUt(3).buildAndRegister();

        BREWING_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Items.BEETROOT))
            .fluidInputs(Juice.getFluid(20))
            .fluidOutputs(Biomass.getFluid(30))
            .duration(160).EUt(3).buildAndRegister();


        // A Few Random Recipes
        FLUID_HEATER_RECIPES.recipeBuilder()
            .circuitMeta(1)
            .fluidInputs(Acetone.getFluid(100))
            .fluidOutputs(Ethenone.getFluid(100))
            .duration(16).EUt(30).buildAndRegister();

        FLUID_HEATER_RECIPES.recipeBuilder()
            .circuitMeta(1)
            .fluidInputs(CalciumAcetate.getFluid(200))
            .fluidOutputs(Acetone.getFluid(200))
            .duration(16).EUt(30).buildAndRegister();

        VACUUM_RECIPES.recipeBuilder()
            .fluidInputs(Water.getFluid(1000))
            .fluidOutputs(Ice.getFluid(1000))
            .duration(50).EUt(30).buildAndRegister();

        VACUUM_RECIPES.recipeBuilder()
            .fluidInputs(Air.getFluid(4000))
            .fluidOutputs(LiquidAir.getFluid(4000))
            .duration(400).EUt(30).buildAndRegister();

        BLAST_RECIPES.recipeBuilder()
            .input(dust, FerriteMixture)
            .fluidInputs(Oxygen.getFluid(2000))
            .output(ingot, NickelZincFerrite)
            .blastFurnaceTemp(1500)
            .duration(600).EUt(120).buildAndRegister();

        FERMENTING_RECIPES.recipeBuilder()
            .fluidInputs(Biomass.getFluid(100))
            .fluidOutputs(FermentedBiomass.getFluid(100))
            .duration(150).EUt(2).buildAndRegister();

        WIREMILL_RECIPES.recipeBuilder()
            .input(ingot, Polycaprolactam)
            .outputs(new ItemStack(Items.STRING, 32))
            .duration(80).EUt(48).buildAndRegister();
    }
}
