package gregtech.loaders.recipe;

import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.common.items.MetaItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import static gregtech.api.GTValues.L;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.recipes.RecipeMaps.*;

// TODO Fit in Ruler
public class ChemistryRecipes {

    public static void init() {

        //Cracking Unit
        CRACKING_RECIPES.recipeBuilder()
            .duration(40).EUt(120)
            .fluidInputs(Hydrogen.getFluid(2000), Ethane.getFluid(1000))
            .fluidOutputs(HydroCrackedEthane.getFluid(1000))
            .buildAndRegister();

        CRACKING_RECIPES.recipeBuilder()
            .duration(40).EUt(120)
            .fluidInputs(Hydrogen.getFluid(2000), Ethylene.getFluid(1000))
            .fluidOutputs(HydroCrackedEthylene.getFluid(1000))
            .buildAndRegister();

        CRACKING_RECIPES.recipeBuilder()
            .duration(40).EUt(120)
            .fluidInputs(Hydrogen.getFluid(2000), Propene.getFluid(1000))
            .fluidOutputs(HydroCrackedPropene.getFluid(1000))
            .buildAndRegister();

        CRACKING_RECIPES.recipeBuilder()
            .duration(40).EUt(120)
            .fluidInputs(Hydrogen.getFluid(2000), Propane.getFluid(1000))
            .fluidOutputs(HydroCrackedPropane.getFluid(1000))
            .buildAndRegister();

        CRACKING_RECIPES.recipeBuilder()
            .duration(40).EUt(120)
            .fluidInputs(Hydrogen.getFluid(2000), LightFuel.getFluid(1000))
            .fluidOutputs(HydroCrackedLightFuel.getFluid(1000))
            .buildAndRegister();

        CRACKING_RECIPES.recipeBuilder()
            .duration(40).EUt(120)
            .fluidInputs(Hydrogen.getFluid(2000), Butane.getFluid(1000))
            .fluidOutputs(HydroCrackedButane.getFluid(1000))
            .buildAndRegister();

        CRACKING_RECIPES.recipeBuilder()
            .duration(40).EUt(120)
            .fluidInputs(Hydrogen.getFluid(2000), Naphtha.getFluid(1000))
            .fluidOutputs(HydroCrackedNaphtha.getFluid(1000))
            .buildAndRegister();

        CRACKING_RECIPES.recipeBuilder()
            .duration(40).EUt(120)
            .fluidInputs(Hydrogen.getFluid(2000), HeavyFuel.getFluid(1000))
            .fluidOutputs(HydroCrackedHeavyFuel.getFluid(1000))
            .buildAndRegister();

        CRACKING_RECIPES.recipeBuilder()
            .duration(40).EUt(120)
            .fluidInputs(Hydrogen.getFluid(2000), Gas.getFluid(1000))
            .fluidOutputs(HydroCrackedGas.getFluid(1000))
            .buildAndRegister();

        CRACKING_RECIPES.recipeBuilder()
            .duration(40).EUt(120)
            .fluidInputs(Hydrogen.getFluid(2000), Butene.getFluid(1000))
            .fluidOutputs(HydroCrackedButene.getFluid(1000))
            .buildAndRegister();

        CRACKING_RECIPES.recipeBuilder()
            .duration(40).EUt(120)
            .fluidInputs(Hydrogen.getFluid(2000), Butadiene.getFluid(1000))
            .fluidOutputs(HydroCrackedButadiene.getFluid(1000))
            .buildAndRegister();

        CRACKING_RECIPES.recipeBuilder()
            .duration(40).EUt(120)
            .fluidInputs(Steam.getFluid(2000), Ethane.getFluid(1000))
            .fluidOutputs(SteamCrackedEthane.getFluid(1000))
            .buildAndRegister();

        CRACKING_RECIPES.recipeBuilder()
            .duration(40).EUt(120)
            .fluidInputs(Steam.getFluid(2000), Ethylene.getFluid(1000))
            .fluidOutputs(SteamCrackedEthylene.getFluid(1000))
            .buildAndRegister();

        CRACKING_RECIPES.recipeBuilder()
            .duration(40).EUt(120)
            .fluidInputs(Steam.getFluid(2000), Propene.getFluid(1000))
            .fluidOutputs(SteamCrackedPropene.getFluid(1000))
            .buildAndRegister();

        CRACKING_RECIPES.recipeBuilder()
            .duration(40).EUt(120)
            .fluidInputs(Steam.getFluid(2000), Propane.getFluid(1000))
            .fluidOutputs(SteamCrackedPropane.getFluid(1000))
            .buildAndRegister();

        CRACKING_RECIPES.recipeBuilder()
            .duration(40).EUt(120)
            .fluidInputs(Steam.getFluid(2000), LightFuel.getFluid(1000))
            .fluidOutputs(CrackedLightFuel.getFluid(1000))
            .buildAndRegister();

        CRACKING_RECIPES.recipeBuilder()
            .duration(40).EUt(120)
            .fluidInputs(Steam.getFluid(2000), Butane.getFluid(1000))
            .fluidOutputs(SteamCrackedButane.getFluid(1000))
            .buildAndRegister();

        CRACKING_RECIPES.recipeBuilder()
            .duration(40).EUt(120)
            .fluidInputs(Steam.getFluid(2000), Naphtha.getFluid(1000))
            .fluidOutputs(SteamCrackedNaphtha.getFluid(1000))
            .buildAndRegister();

        CRACKING_RECIPES.recipeBuilder()
            .duration(40).EUt(120)
            .fluidInputs(Steam.getFluid(2000), HeavyFuel.getFluid(1000))
            .fluidOutputs(CrackedHeavyFuel.getFluid(1000))
            .buildAndRegister();

        CRACKING_RECIPES.recipeBuilder()
            .duration(40).EUt(120)
            .fluidInputs(Steam.getFluid(2000), Gas.getFluid(1000))
            .fluidOutputs(SteamCrackedGas.getFluid(1000))
            .buildAndRegister();

        CRACKING_RECIPES.recipeBuilder()
            .duration(40).EUt(120)
            .fluidInputs(Steam.getFluid(2000), Butene.getFluid(1000))
            .fluidOutputs(SteamCrackedButene.getFluid(1000))
            .buildAndRegister();

        CRACKING_RECIPES.recipeBuilder()
            .duration(40).EUt(120)
            .fluidInputs(Steam.getFluid(2000), Butadiene.getFluid(1000))
            .fluidOutputs(SteamCrackedButadiene.getFluid(1000))
            .buildAndRegister();


        //Distillation Tower / Distillery
        DISTILLATION_RECIPES.recipeBuilder()
            .duration(16).EUt(96)
            .fluidInputs(Creosote.getFluid(24))
            .fluidOutputs(Lubricant.getFluid(12))
            .buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .duration(120).EUt(120)
            .fluidInputs(HydroCrackedEthane.getFluid(1000))
            .fluidOutputs(Methane.getFluid(2000))
            .buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .duration(120).EUt(120)
            .fluidInputs(SteamCrackedEthane.getFluid(1000))
            .outputs(OreDictUnifier.get(dustSmall, Carbon, 2))
            .fluidOutputs(Methane.getFluid(1500))
            .buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .duration(120).EUt(120)
            .fluidInputs(HydroCrackedEthylene.getFluid(1000))
            .fluidOutputs(Ethane.getFluid(1000))
            .buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .duration(120).EUt(120)
            .fluidInputs(SteamCrackedEthylene.getFluid(1000))
            .outputs(OreDictUnifier.get(dust, Carbon))
            .fluidOutputs(Methane.getFluid(1000))
            .buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .duration(120).EUt(120)
            .fluidInputs(HydroCrackedPropene.getFluid(1000))
            .fluidOutputs(Propane.getFluid(500), Ethylene.getFluid(500), Methane.getFluid(500))
            .buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .duration(180).EUt(120)
            .fluidInputs(SteamCrackedPropene.getFluid(1000))
            .outputs(OreDictUnifier.get(dustSmall, Carbon, 6))
            .fluidOutputs(Methane.getFluid(1500))
            .buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .duration(120).EUt(120)
            .fluidInputs(HydroCrackedPropane.getFluid(1000))
            .fluidOutputs(Ethane.getFluid(1000), Methane.getFluid(1000))
            .buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .duration(240).EUt(120)
            .fluidInputs(SteamCrackedPropane.getFluid(2000))
            .outputs(OreDictUnifier.get(dustSmall, Carbon, 3))
            .fluidOutputs(Ethylene.getFluid(500), Methane.getFluid(3500))
            .buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .duration(120).EUt(120)
            .fluidInputs(HydroCrackedLightFuel.getFluid(1000))
            .fluidOutputs(Naphtha.getFluid(800), Butane.getFluid(150), Propane.getFluid(200), Ethane.getFluid(125), Methane.getFluid(125))
            .buildAndRegister();

        // TODO Make cleaner
        DISTILLATION_RECIPES.recipeBuilder()
            .duration(120).EUt(120)
            .fluidInputs(CrackedLightFuel.getFluid(1000))
            .outputs(OreDictUnifier.get(dustTiny, Carbon))
            .fluidOutputs(HeavyFuel.getFluid(50), Naphtha.getFluid(100), Toluene.getFluid(30), Benzene.getFluid(150), Butene.getFluid(65), Butadiene.getFluid(50), Propane.getFluid(50), Propene.getFluid(250), Ethane.getFluid(50), Ethylene.getFluid(250), Methane.getFluid(250))
            .buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .duration(90).EUt(120)
            .fluidInputs(HydroCrackedButane.getFluid(750))
            .fluidOutputs(Propane.getFluid(500), Ethane.getFluid(500), Methane.getFluid(500))
            .buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .duration(240).EUt(120)
            .fluidInputs(SteamCrackedButane.getFluid(2000))
            .outputs(OreDictUnifier.get(dustSmall, Carbon, 9))
            .fluidOutputs(Propane.getFluid(250), Ethane.getFluid(250), Ethylene.getFluid(250), Methane.getFluid(4000))
            .buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .duration(120).EUt(120)
            .fluidInputs(HydroCrackedNaphtha.getFluid(1000))
            .fluidOutputs(Butane.getFluid(800), Propane.getFluid(300), Ethane.getFluid(250), Methane.getFluid(250))
            .buildAndRegister();

        // TODO Make cleaner
        DISTILLATION_RECIPES.recipeBuilder()
            .duration(120).EUt(120)
            .fluidInputs(SteamCrackedNaphtha.getFluid(1000))
            .outputs(OreDictUnifier.get(dustTiny, Carbon, 3))
            .fluidOutputs(HeavyFuel.getFluid(25), LightFuel.getFluid(50), Toluene.getFluid(20), Benzene.getFluid(100), Butene.getFluid(50), Butadiene.getFluid(50), Propane.getFluid(15), Propene.getFluid(300), Ethane.getFluid(65), Ethylene.getFluid(500), Methane.getFluid(500))
            .buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .duration(120).EUt(120)
            .fluidInputs(HydroCrackedHeavyFuel.getFluid(1000))
            .fluidOutputs(LightFuel.getFluid(600), Naphtha.getFluid(100), Butane.getFluid(100), Propane.getFluid(100), Ethane.getFluid(75), Methane.getFluid(75))
            .buildAndRegister();

        // TODO make cleaner
        DISTILLATION_RECIPES.recipeBuilder()
            .duration(120).EUt(120)
            .fluidInputs(CrackedHeavyFuel.getFluid(1000))
            .outputs(OreDictUnifier.get(dustTiny, Carbon, 3))
            .fluidOutputs(LightFuel.getFluid(100), Naphtha.getFluid(125), Toluene.getFluid(80), Benzene.getFluid(400), Butene.getFluid(80), Butadiene.getFluid(50), Propane.getFluid(10), Propene.getFluid(100), Ethane.getFluid(15), Ethylene.getFluid(150), Methane.getFluid(150))
            .buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .duration(120).EUt(120)
            .fluidInputs(HydroCrackedGas.getFluid(1000))
            .fluidOutputs(Methane.getFluid(1400), Hydrogen.getFluid(1340), Helium.getFluid(20))
            .buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .duration(96).EUt(120)
            .fluidInputs(SteamCrackedGas.getFluid(800))
            .outputs(OreDictUnifier.get(dustTiny, Carbon))
            .fluidOutputs(Propene.getFluid(6), Ethane.getFluid(6), Ethylene.getFluid(20), Methane.getFluid(914), Helium.getFluid(16))
            .buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .duration(90).EUt(120)
            .fluidInputs(HydroCrackedButene.getFluid(750))
            .fluidOutputs(Butane.getFluid(250), Propene.getFluid(250), Ethane.getFluid(250), Methane.getFluid(250))
            .buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .duration(240).EUt(120)
            .fluidInputs(SteamCrackedButene.getFluid(2000))
            .outputs(OreDictUnifier.get(dust, Carbon, 3))
            .fluidOutputs(Propene.getFluid(250), Ethylene.getFluid(625), Methane.getFluid(3000))
            .buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .duration(90).EUt(120)
            .fluidInputs(HydroCrackedButadiene.getFluid(750))
            .fluidOutputs(Butene.getFluid(500), Ethylene.getFluid(500))
            .buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .duration(240).EUt(120)
            .fluidInputs(SteamCrackedButadiene.getFluid(2000))
            .outputs(OreDictUnifier.get(dust, Carbon, 2))
            .fluidOutputs(Propene.getFluid(250), Ethylene.getFluid(375), Methane.getFluid(2250))
            .buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .duration(20).EUt(96)
            .fluidInputs(OilLight.getFluid(150))
            .fluidOutputs(SulfuricHeavyFuel.getFluid(10), SulfuricLightFuel.getFluid(20), SulfuricNaphtha.getFluid(30), SulfuricGas.getFluid(240))
            .buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .duration(20).EUt(96)
            .fluidInputs(OilMedium.getFluid(100))
            .fluidOutputs(SulfuricHeavyFuel.getFluid(15), SulfuricLightFuel.getFluid(50), SulfuricNaphtha.getFluid(20), SulfuricGas.getFluid(60))
            .buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .duration(20).EUt(288)
            .fluidInputs(OilHeavy.getFluid(150))
            .fluidOutputs(SulfuricHeavyFuel.getFluid(250), SulfuricLightFuel.getFluid(45), SulfuricNaphtha.getFluid(15), SulfuricGas.getFluid(600))
            .buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .duration(20).EUt(96)
            .fluidInputs(Oil.getFluid(50))
            .fluidOutputs(SulfuricHeavyFuel.getFluid(15), SulfuricLightFuel.getFluid(50), SulfuricNaphtha.getFluid(20), SulfuricGas.getFluid(60))
            .buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .duration(600).EUt(64)
            .fluidInputs(DilutedHydrochloricAcid.getFluid(2000))
            .fluidOutputs(Water.getFluid(1000), HydrochloricAcid.getFluid(1000))
            .buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .duration(600).EUt(120)
            .fluidInputs(DilutedSulfuricAcid.getFluid(3000))
            .fluidOutputs(SulfuricAcid.getFluid(2000), Water.getFluid(1000))
            .buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .duration(40).EUt(256)
            .fluidInputs(CharcoalByproducts.getFluid(1000))
            .outputs(OreDictUnifier.get(dustSmall, Charcoal))
            .fluidOutputs(WoodTar.getFluid(250), WoodVinegar.getFluid(500), WoodGas.getFluid(250))
            .buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .duration(40).EUt(256)
            .fluidInputs(WoodTar.getFluid(1000))
            .fluidOutputs(Creosote.getFluid(500), Phenol.getFluid(75), Benzene.getFluid(350), Toluene.getFluid(75))
            .buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .duration(40).EUt(256)
            .fluidInputs(WoodGas.getFluid(1000))
            .fluidOutputs(CarbonDioxide.getFluid(490), Ethylene.getFluid(20), Methane.getFluid(130), CarbonMonoxde.getFluid(340), Hydrogen.getFluid(20))
            .buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .duration(160).EUt(120)
            .fluidInputs(Water.getFluid(576))
            .fluidOutputs(DistilledWater.getFluid(520))
            .buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .duration(80).EUt(640)
            .fluidInputs(Acetone.getFluid(1000))
            .fluidOutputs(Ethenone.getFluid(1000), Methane.getFluid(1000))
            .buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .duration(80).EUt(120)
            .fluidInputs(CalciumAcetate.getFluid(1000))
            .outputs(OreDictUnifier.get(dust, Quicklime, 2))
            .fluidOutputs(Acetone.getFluid(1000), CarbonDioxide.getFluid(1000), Water.getFluid(1000))
            .buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .duration(16).EUt(96)
            .fluidInputs(SeedOil.getFluid(24))
            .fluidOutputs(Lubricant.getFluid(12))
            .buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .duration(40).EUt(256)
            .fluidInputs(WoodVinegar.getFluid(1000))
            .fluidOutputs(AceticAcid.getFluid(100), Water.getFluid(500), Ethanol.getFluid(10), Methanol.getFluid(300), Acetone.getFluid(50), MethylAcetate.getFluid(10))
            .buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .duration(75).EUt(180)
            .fluidInputs(FermentedBiomass.getFluid(1000))
            .fluidOutputs(AceticAcid.getFluid(25), Water.getFluid(375), Ethanol.getFluid(150), Methanol.getFluid(150), Ammonia.getFluid(100), CarbonDioxide.getFluid(400), Methane.getFluid(600))
            .buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
            .duration(32).EUt(400)
            .fluidInputs(Biomass.getFluid(1000))
            .outputs(OreDictUnifier.get(dustSmall, Wood, 2))
            .fluidOutputs(Ethanol.getFluid(600), Water.getFluid(300))
            .buildAndRegister();

        DISTILLERY_RECIPES.recipeBuilder()
            .duration(160).EUt(24)
            .circuitMeta(1)
            .fluidInputs(Toluene.getFluid(30))
            .fluidOutputs(LightFuel.getFluid(30))
            .buildAndRegister();

        DISTILLERY_RECIPES.recipeBuilder()
            .duration(16).EUt(24)
            .circuitMeta(1)
            .fluidInputs(HeavyFuel.getFluid(10))
            .fluidOutputs(Toluene.getFluid(4))
            .buildAndRegister();

        DISTILLERY_RECIPES.recipeBuilder()
            .duration(16).EUt(24)
            .circuitMeta(2)
            .fluidInputs(HeavyFuel.getFluid(10))
            .fluidOutputs(Benzene.getFluid(4))
            .buildAndRegister();

        DISTILLERY_RECIPES.recipeBuilder()
            .duration(32).EUt(24)
            .circuitMeta(3)
            .fluidInputs(HeavyFuel.getFluid(20))
            .fluidOutputs(Phenol.getFluid(5))
            .buildAndRegister();

        DISTILLERY_RECIPES.recipeBuilder()
            .duration(16).EUt(24)
            .circuitMeta(4)
            .fluidInputs(OilLight.getFluid(300))
            .fluidOutputs(Oil.getFluid(100))
            .buildAndRegister();

        DISTILLERY_RECIPES.recipeBuilder()
            .duration(16).EUt(24)
            .circuitMeta(4)
            .fluidInputs(OilMedium.getFluid(200))
            .fluidOutputs(Oil.getFluid(100))
            .buildAndRegister();

        DISTILLERY_RECIPES.recipeBuilder()
            .duration(16).EUt(24)
            .circuitMeta(4)
            .fluidInputs(OilHeavy.getFluid(100))
            .fluidOutputs(Oil.getFluid(100))
            .buildAndRegister();


        // Centrifuge
        CENTRIFUGE_RECIPES.recipeBuilder()
            .duration(200).EUt(5)
            .fluidInputs(Gas.getFluid(8000))
            .fluidOutputs(Methane.getFluid(4000), LPG.getFluid(4000))
            .buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
            .duration(1484).EUt(5)
            .fluidInputs(LiquidAir.getFluid(53000))
            .fluidOutputs(Nitrogen.getFluid(32000), Nitrogen.getFluid(8000), Oxygen.getFluid(11000), Argon.getFluid(1000), NobleGases.getFluid(1000))
            .buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
            .duration(680).EUt(5)
            .fluidInputs(NobleGases.getFluid(34000))
            .fluidOutputs(CarbonDioxide.getFluid(21000), Helium.getFluid(9000), Methane.getFluid(3000), Deuterium.getFluid(1000))
            .buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
            .duration(20).EUt(5)
            .fluidInputs(Butane.getFluid(320))
            .fluidOutputs(LPG.getFluid(370))
            .buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
            .duration(20).EUt(5)
            .fluidInputs(Propane.getFluid(320))
            .fluidOutputs(LPG.getFluid(290))
            .buildAndRegister();


        // Mixer
        MIXER_RECIPES.recipeBuilder()
            .duration(500).EUt(2)
            .fluidInputs(NitricAcid.getFluid(1000), SulfuricAcid.getFluid(1000))
            .fluidOutputs(NitrationMixture.getFluid(1000))
            .buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
            .duration(60).EUt(30)
            .inputs(OreDictUnifier.get(dust, Sodium, 2), OreDictUnifier.get(dust, Sulfur))
            .outputs(OreDictUnifier.get(dust, SodiumSulfide, 3))
            .buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
            .duration(50).EUt(8)
            .fluidInputs(PolyvinylAcetate.getFluid(1000), Acetone.getFluid(1500))
            .fluidOutputs(Glue.getFluid(2500))
            .buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
            .duration(50).EUt(8)
            .fluidInputs(PolyvinylAcetate.getFluid(1000), MethylAcetate.getFluid(1500))
            .fluidOutputs(Glue.getFluid(2500))
            .buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
            .duration(1200).EUt(2)
            .input(dust, Wood, 4)
            .fluidInputs(SulfuricAcid.getFluid(1000))
            .outputs(new ItemStack(Items.COAL, 1, 1))
            .fluidOutputs(DilutedSulfuricAcid.getFluid(1000))
            .buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
            .duration(1200).EUt(2)
            .inputs(new ItemStack(Items.SUGAR, 4))
            .fluidInputs(SulfuricAcid.getFluid(1000))
            .outputs(new ItemStack(Items.COAL, 1, 1))
            .fluidOutputs(DilutedSulfuricAcid.getFluid(1000))
            .buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
            .duration(300).EUt(30)
            .input(dust, Gallium).input(dust, Arsenic)
            .outputs(OreDictUnifier.get(dust, GalliumArsenide, 2))
            .buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
            .duration(40).EUt(8)
            .input(dust, Salt, 2)
            .fluidInputs(Water.getFluid(1000))
            .fluidOutputs(SaltWater.getFluid(1000))
            .buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
            .duration(20).EUt(480)
            .fluidInputs(BioDiesel.getFluid(1000), Tetranitromethane.getFluid(40))
            .fluidOutputs(NitroFuel.getFluid(750))
            .buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
            .duration(20).EUt(480)
            .fluidInputs(Fuel.getFluid(1000), Tetranitromethane.getFluid(20))
            .fluidOutputs(NitroFuel.getFluid(1000))
            .buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
            .duration(60).EUt(16)
            .fluidInputs(Oxygen.getFluid(1000), Dimethylhydrazine.getFluid(1000))
            .fluidOutputs(RocketFuel.getFluid(3000))
            .buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
            .duration(60).EUt(16)
            .fluidInputs(DinitrogenTetroxide.getFluid(1000), Dimethylhydrazine.getFluid(1000))
            .fluidOutputs(RocketFuel.getFluid(6000))
            .buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
            .duration(16).EUt(120)
            .fluidInputs(LightFuel.getFluid(5000), HeavyFuel.getFluid(1000))
            .fluidOutputs(Fuel.getFluid(6000))
            .buildAndRegister();

        for (DustMaterial dustMaterial : new DustMaterial[]{Talc, Soapstone, Redstone}) {
            MIXER_RECIPES.recipeBuilder()
                .duration(128).EUt(4)
                .input(dust, dustMaterial)
                .fluidInputs(Oil.getFluid(750))
                .fluidOutputs(Lubricant.getFluid(750))
                .buildAndRegister();

            MIXER_RECIPES.recipeBuilder()
                .duration(128).EUt(4)
                .input(dust, dustMaterial)
                .fluidInputs(Creosote.getFluid(750))
                .fluidOutputs(Lubricant.getFluid(750))
                .buildAndRegister();

            MIXER_RECIPES.recipeBuilder()
                .duration(128).EUt(4)
                .input(dust, dustMaterial)
                .fluidInputs(SeedOil.getFluid(750))
                .fluidOutputs(Lubricant.getFluid(750))
                .buildAndRegister();
        }


        // Electrolyzer
        ELECTROLYZER_RECIPES.recipeBuilder()
            .duration(448).EUt(60)
            .input(dust, SodiumBisulfate, 14)
            .fluidOutputs(SodiumPersulfate.getFluid(1000), Hydrogen.getFluid(2000))
            .buildAndRegister();

        ELECTROLYZER_RECIPES.recipeBuilder()
            .duration(720).EUt(30)
            .fluidInputs(SaltWater.getFluid(1000))
            .outputs(OreDictUnifier.get(dust, SodiumHydroxide, 3))
            .fluidOutputs(Chlorine.getFluid(1000), Hydrogen.getFluid(1000))
            .buildAndRegister();

        ELECTROLYZER_RECIPES.recipeBuilder()
            .duration(200).EUt(30)
            .input(dust, Sphalerite, 2)
            .outputs(OreDictUnifier.get(dust, Zinc), OreDictUnifier.get(dust, Sulfur))
            .chancedOutput(OreDictUnifier.get(dustTiny, Gallium), 2500, 1000)
            .buildAndRegister();

        ELECTROLYZER_RECIPES.recipeBuilder()
            .duration(2496).EUt(60)
            .input(dust, Bauxite, 39)
            .outputs(OreDictUnifier.get(dust, Rutile, 6), OreDictUnifier.get(dust, Aluminium, 16))
            .fluidOutputs(Hydrogen.getFluid(10000), Oxygen.getFluid(11000))
            .buildAndRegister();

        ELECTROLYZER_RECIPES.recipeBuilder()
            .duration(1500).EUt(30)
            .fluidInputs(Water.getFluid(1000))
            .fluidOutputs(Hydrogen.getFluid(2000), Oxygen.getFluid(1000))
            .buildAndRegister();

        // TODO Make sure recipe right below this works
        ELECTROLYZER_RECIPES.recipeBuilder()
            .duration(1500).EUt(30)
            .fluidInputs(DistilledWater.getFluid(1000))
            .fluidOutputs(Hydrogen.getFluid(2000), Oxygen.getFluid(1000))
            .buildAndRegister();

        ELECTROLYZER_RECIPES.recipeBuilder()
            .duration(96).EUt(26)
            .inputs(new ItemStack(Items.DYE, 3))
            .outputs(OreDictUnifier.get(dust, Calcium))
            .buildAndRegister();

        ELECTROLYZER_RECIPES.recipeBuilder()
            .duration(500).EUt(25)
            .inputs(new ItemStack(Blocks.SAND, 8))
            .outputs(OreDictUnifier.get(dust, SiliconDioxide))
            .buildAndRegister();

        ELECTROLYZER_RECIPES.recipeBuilder()
            .duration(100).EUt(26)
            .input(dust, Graphite)
            .outputs(OreDictUnifier.get(dust, Carbon, 4))
            .buildAndRegister();


        // Brewing
        BREWING_RECIPES.recipeBuilder()
            .duration(1440).EUt(3)
            .inputs(MetaItems.PLANT_BALL.getStackForm())
            .fluidInputs(Honey.getFluid(180))
            .fluidOutputs(Biomass.getFluid(270))
            .buildAndRegister();

        BREWING_RECIPES.recipeBuilder()
            .duration(600).EUt(3)
            .input("treeSapling", 1)
            .fluidInputs(Honey.getFluid(100))
            .fluidOutputs(Biomass.getFluid(150))
            .buildAndRegister();

        BREWING_RECIPES.recipeBuilder()
            .duration(160).EUt(3)
            .inputs(new ItemStack(Items.POTATO))
            .fluidInputs(Honey.getFluid(20))
            .fluidOutputs(Biomass.getFluid(30))
            .buildAndRegister();

        BREWING_RECIPES.recipeBuilder()
            .duration(160).EUt(3)
            .inputs(new ItemStack(Items.CARROT))
            .fluidInputs(Honey.getFluid(20))
            .fluidOutputs(Biomass.getFluid(30))
            .buildAndRegister();

        BREWING_RECIPES.recipeBuilder()
            .duration(160).EUt(3)
            .inputs(new ItemStack(Blocks.CACTUS))
            .fluidInputs(Honey.getFluid(20))
            .fluidOutputs(Biomass.getFluid(30))
            .buildAndRegister();

        BREWING_RECIPES.recipeBuilder()
            .duration(160).EUt(3)
            .inputs(new ItemStack(Items.REEDS))
            .fluidInputs(Honey.getFluid(20))
            .fluidOutputs(Biomass.getFluid(30))
            .buildAndRegister();

        BREWING_RECIPES.recipeBuilder()
            .duration(160).EUt(3)
            .inputs(new ItemStack(Blocks.BROWN_MUSHROOM))
            .fluidInputs(Honey.getFluid(20))
            .fluidOutputs(Biomass.getFluid(30))
            .buildAndRegister();

        BREWING_RECIPES.recipeBuilder()
            .duration(160).EUt(3)
            .inputs(new ItemStack(Blocks.RED_MUSHROOM))
            .fluidInputs(Honey.getFluid(20))
            .fluidOutputs(Biomass.getFluid(30))
            .buildAndRegister();

        BREWING_RECIPES.recipeBuilder()
            .duration(160).EUt(3)
            .inputs(new ItemStack(Items.BEETROOT))
            .fluidInputs(Honey.getFluid(20))
            .fluidOutputs(Biomass.getFluid(30))
            .buildAndRegister();

        BREWING_RECIPES.recipeBuilder()
            .duration(1440).EUt(3)
            .inputs(MetaItems.PLANT_BALL.getStackForm())
            .fluidInputs(Juice.getFluid(180))
            .fluidOutputs(Biomass.getFluid(270))
            .buildAndRegister();

        BREWING_RECIPES.recipeBuilder()
            .duration(600).EUt(3)
            .input("treeSapling", 1)
            .fluidInputs(Juice.getFluid(100))
            .fluidOutputs(Biomass.getFluid(150))
            .buildAndRegister();

        BREWING_RECIPES.recipeBuilder()
            .duration(160).EUt(3)
            .inputs(new ItemStack(Items.POTATO))
            .fluidInputs(Juice.getFluid(20))
            .fluidOutputs(Biomass.getFluid(30))
            .buildAndRegister();

        BREWING_RECIPES.recipeBuilder()
            .duration(160).EUt(3)
            .inputs(new ItemStack(Items.CARROT))
            .fluidInputs(Juice.getFluid(20))
            .fluidOutputs(Biomass.getFluid(30))
            .buildAndRegister();

        BREWING_RECIPES.recipeBuilder()
            .duration(160).EUt(3)
            .inputs(new ItemStack(Blocks.CACTUS))
            .fluidInputs(Juice.getFluid(20))
            .fluidOutputs(Biomass.getFluid(30))
            .buildAndRegister();

        BREWING_RECIPES.recipeBuilder()
            .duration(160).EUt(3)
            .inputs(new ItemStack(Items.REEDS))
            .fluidInputs(Juice.getFluid(20))
            .fluidOutputs(Biomass.getFluid(30))
            .buildAndRegister();

        BREWING_RECIPES.recipeBuilder()
            .duration(160).EUt(3)
            .inputs(new ItemStack(Blocks.BROWN_MUSHROOM))
            .fluidInputs(Juice.getFluid(20))
            .fluidOutputs(Biomass.getFluid(30))
            .buildAndRegister();

        BREWING_RECIPES.recipeBuilder()
            .duration(160).EUt(3)
            .inputs(new ItemStack(Blocks.RED_MUSHROOM))
            .fluidInputs(Juice.getFluid(20))
            .fluidOutputs(Biomass.getFluid(30))
            .buildAndRegister();
        
        BREWING_RECIPES.recipeBuilder()
            .duration(160).EUt(3)
            .inputs(new ItemStack(Items.BEETROOT))
            .fluidInputs(Juice.getFluid(20))
            .fluidOutputs(Biomass.getFluid(30))
            .buildAndRegister();


        // A Few Random Recipes
        FLUID_HEATER_RECIPES.recipeBuilder()
            .duration(16).EUt(30)
            .circuitMeta(1)
            .fluidInputs(Acetone.getFluid(100))
            .fluidOutputs(Ethenone.getFluid(100))
            .buildAndRegister();

        FLUID_HEATER_RECIPES.recipeBuilder()
            .duration(16).EUt(30)
            .circuitMeta(1)
            .fluidInputs(CalciumAcetate.getFluid(200))
            .fluidOutputs(Acetone.getFluid(200))
            .buildAndRegister();

        VACUUM_RECIPES.recipeBuilder()
            .duration(50)
            .fluidInputs(Water.getFluid(1000))
            .fluidOutputs(Ice.getFluid(1000))
            .buildAndRegister();

        VACUUM_RECIPES.recipeBuilder()
            .duration(400)
            .fluidInputs(Air.getFluid(4000))
            .fluidOutputs(LiquidAir.getFluid(4000))
            .buildAndRegister();

        BLAST_RECIPES.recipeBuilder()
            .duration(600).EUt(120)
            .input(dust, FerriteMixture)
            .fluidInputs(Oxygen.getFluid(2000))
            .outputs(OreDictUnifier.get(ingot, NickelZincFerrite))
            .blastFurnaceTemp(1500)
            .buildAndRegister();

        FERMENTING_RECIPES.recipeBuilder()
            .duration(150).EUt(2)
            .fluidInputs(Biomass.getFluid(100))
            .fluidOutputs(FermentedBiomass.getFluid(100))
            .buildAndRegister();

        WIREMILL_RECIPES.recipeBuilder()
            .duration(80).EUt(48)
            .input(ingot, Polycaprolactam)
            .outputs(new ItemStack(Items.STRING, 32))
            .buildAndRegister();




        CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).fluidInputs(Isoprene.getFluid(144), Air.getFluid(2000)).outputs(OreDictUnifier.get(dust, RawRubber)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).fluidInputs(Isoprene.getFluid(144), Oxygen.getFluid(2000)).outputs(OreDictUnifier.get(dust, RawRubber, 3)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(240).fluidInputs(Butadiene.getFluid(108), Styrene.getFluid(36), Air.getFluid(2000)).outputs(OreDictUnifier.get(dust, RawStyreneButadieneRubber)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(240).fluidInputs(Butadiene.getFluid(108), Styrene.getFluid(36), Oxygen.getFluid(2000)).outputs(OreDictUnifier.get(dust, RawStyreneButadieneRubber, 3)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(120).EUt(30).notConsumable(new IntCircuitIngredient(2)).fluidInputs(Propene.getFluid(2000)).fluidOutputs(Methane.getFluid(1000), Isoprene.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(3500).EUt(30).input(dust, Carbon).notConsumable(new IntCircuitIngredient(0)).fluidInputs(Hydrogen.getFluid(4000)).fluidOutputs(Methane.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(120).EUt(30).fluidInputs(Ethylene.getFluid(1000), Propene.getFluid(1000)).fluidOutputs(Hydrogen.getFluid(2000), Isoprene.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(600).EUt(30).input(dust, RawStyreneButadieneRubber, 9).input(dust, Sulfur).fluidOutputs(StyreneButadieneRubber.getFluid(1296)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(240).EUt(360).input(dust, SodiumSulfide, 3).fluidInputs(Dichlorobenzene.getFluid(1000), Air.getFluid(16000)).outputs(OreDictUnifier.get(dust, Salt, 2)).fluidOutputs(PolyphenyleneSulfide.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(240).EUt(360).input(dust, SodiumSulfide, 3).fluidInputs(Dichlorobenzene.getFluid(1000), Oxygen.getFluid(8000)).outputs(OreDictUnifier.get(dust, Salt, 2)).fluidOutputs(PolyphenyleneSulfide.getFluid(1500)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).notConsumable(new IntCircuitIngredient(0)).fluidInputs(Air.getFluid(1000), Ethylene.getFluid(144)).fluidOutputs(Plastic.getFluid(144)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).notConsumable(new IntCircuitIngredient(0)).fluidInputs(Oxygen.getFluid(1000), Ethylene.getFluid(144)).fluidOutputs(Plastic.getFluid(216)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(800).EUt(30).notConsumable(new IntCircuitIngredient(1)).fluidInputs(Air.getFluid(7500), Ethylene.getFluid(2160), TitaniumTetrachloride.getFluid(100)).fluidOutputs(Plastic.getFluid(3240)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(800).EUt(30).notConsumable(new IntCircuitIngredient(1)).fluidInputs(Oxygen.getFluid(7500), Ethylene.getFluid(2160), TitaniumTetrachloride.getFluid(100)).fluidOutputs(Plastic.getFluid(4320)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).notConsumable(new IntCircuitIngredient(0)).fluidInputs(Air.getFluid(1000), VinylChloride.getFluid(144)).fluidOutputs(PolyvinylChloride.getFluid(144)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).notConsumable(new IntCircuitIngredient(0)).fluidInputs(Oxygen.getFluid(1000), VinylChloride.getFluid(144)).fluidOutputs(PolyvinylChloride.getFluid(216)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(800).EUt(30).notConsumable(new IntCircuitIngredient(1)).fluidInputs(Air.getFluid(7500), VinylChloride.getFluid(2160), TitaniumTetrachloride.getFluid(100)).fluidOutputs(PolyvinylChloride.getFluid(3240)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(800).EUt(30).notConsumable(new IntCircuitIngredient(1)).fluidInputs(Oxygen.getFluid(7500), VinylChloride.getFluid(2160), TitaniumTetrachloride.getFluid(100)).fluidOutputs(PolyvinylChloride.getFluid(4320)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(600).EUt(30).input(dust, Polydimethylsiloxane, 9).input(dust, Sulfur).fluidOutputs(SiliconeRubber.getFluid(1296)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).fluidInputs(HydrochloricAcid.getFluid(1000), Acetone.getFluid(1000), Phenol.getFluid(2000)).fluidOutputs(BisphenolA.getFluid(1000), DilutedHydrochloricAcid.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(320).EUt(8).fluidInputs(SulfurTrioxide.getFluid(1000), Water.getFluid(1000)).fluidOutputs(SulfuricAcid.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).notConsumable(new IntCircuitIngredient(0)).fluidInputs(Air.getFluid(1000), Tetrafluoroethylene.getFluid(144)).fluidOutputs(Polytetrafluoroethylene.getFluid(144)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).notConsumable(new IntCircuitIngredient(0)).fluidInputs(Oxygen.getFluid(1000), Tetrafluoroethylene.getFluid(144)).fluidOutputs(Polytetrafluoroethylene.getFluid(216)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(800).EUt(30).notConsumable(new IntCircuitIngredient(1)).fluidInputs(Air.getFluid(7500), Tetrafluoroethylene.getFluid(2160), TitaniumTetrachloride.getFluid(100)).fluidOutputs(Polytetrafluoroethylene.getFluid(3240)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(800).EUt(30).notConsumable(new IntCircuitIngredient(1)).fluidInputs(Oxygen.getFluid(7500), Tetrafluoroethylene.getFluid(2160), TitaniumTetrachloride.getFluid(100)).fluidOutputs(Polytetrafluoroethylene.getFluid(4320)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(200).EUt(30).input(dust, SodiumHydroxide, 3).fluidInputs(Epichlorhydrin.getFluid(1000), BisphenolA.getFluid(1000)).fluidOutputs(Epoxid.getFluid(1000), SaltWater.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(500).EUt(480).input(dust, Carbon, 2).input(dust, Rutile, 3).fluidInputs(Chlorine.getFluid(4000)).fluidOutputs(CarbonMonoxde.getFluid(2000), TitaniumTetrachloride.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(240).EUt(96).fluidInputs(Dimethyldichlorosilane.getFluid(1000), Water.getFluid(1000)).outputs(OreDictUnifier.get(dust, Polydimethylsiloxane, 3)).fluidOutputs(DilutedHydrochloricAcid.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(480).EUt(96).input(dust, Silicon).fluidInputs(HydrochloricAcid.getFluid(2000), Methanol.getFluid(2000)).outputs(OreDictUnifier.get(dust, Polydimethylsiloxane, 3)).fluidOutputs(DilutedHydrochloricAcid.getFluid(2000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(480).EUt(96).input(dust, Silicon).notConsumable(new IntCircuitIngredient(2)).fluidInputs(Water.getFluid(1000), Chlorine.getFluid(4000), Methane.getFluid(2000)).outputs(OreDictUnifier.get(dust, Polydimethylsiloxane, 3)).fluidOutputs(HydrochloricAcid.getFluid(2000), DilutedHydrochloricAcid.getFluid(2000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(60).EUt(8).fluidInputs(Chlorine.getFluid(1000), Hydrogen.getFluid(1000)).fluidOutputs(HydrochloricAcid.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(60).EUt(30).input(dust, Salt, 2).fluidInputs(SulfuricAcid.getFluid(1000)).outputs(OreDictUnifier.get(dust, SodiumBisulfate, 7)).fluidOutputs(HydrochloricAcid.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(80).EUt(30).notConsumable(new IntCircuitIngredient(1)).fluidInputs(Chlorine.getFluid(6000), Methane.getFluid(1000)).fluidOutputs(HydrochloricAcid.getFluid(3000), Chloroform.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(80).EUt(30).notConsumable(new IntCircuitIngredient(3)).fluidInputs(Chlorine.getFluid(2000), Methane.getFluid(1000)).fluidOutputs(HydrochloricAcid.getFluid(1000), Chloromethane.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(120).EUt(30).fluidInputs(Chlorine.getFluid(3000), Benzene.getFluid(1000)).fluidOutputs(HydrochloricAcid.getFluid(2000), Dichlorobenzene.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).fluidInputs(Propene.getFluid(1000), Chlorine.getFluid(2000)).fluidOutputs(HydrochloricAcid.getFluid(1000), AllylChloride.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).fluidInputs(Chlorine.getFluid(2000), Ethylene.getFluid(1000)).fluidOutputs(VinylChloride.getFluid(1000), HydrochloricAcid.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(320).EUt(30).input(dust, Apatite, 9).fluidInputs(SulfuricAcid.getFluid(5000), Water.getFluid(10000)).outputs(OreDictUnifier.get(dust, Gypsum, 40)).fluidOutputs(HydrochloricAcid.getFluid(1000), PhosphoricAcid.getFluid(3000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(200).EUt(30).fluidInputs(SulfurDioxide.getFluid(1000), Oxygen.getFluid(1000)).fluidOutputs(SulfurTrioxide.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(280).EUt(30).input(dust, Sulfur).notConsumable(new IntCircuitIngredient(3)).fluidInputs(Oxygen.getFluid(3000)).fluidOutputs(SulfurTrioxide.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(240).EUt(256).fluidInputs(Chloroform.getFluid(2000), HydrofluoricAcid.getFluid(4000)).fluidOutputs(HydrochloricAcid.getFluid(6000), Tetrafluoroethylene.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(40).EUt(8).input(dust, Sodium, 1).notConsumable(new IntCircuitIngredient(1)).fluidInputs(Water.getFluid(1000)).outputs(OreDictUnifier.get(dust, SodiumHydroxide, 3)).fluidOutputs(Hydrogen.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).notConsumable(new IntCircuitIngredient(0)).fluidInputs(Air.getFluid(1000), Styrene.getFluid(144)).fluidOutputs(Polystyrene.getFluid(144)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).notConsumable(new IntCircuitIngredient(0)).fluidInputs(Oxygen.getFluid(1000), Styrene.getFluid(144)).fluidOutputs(Polystyrene.getFluid(216)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(800).EUt(30).notConsumable(new IntCircuitIngredient(1)).fluidInputs(Air.getFluid(7500), Styrene.getFluid(2160), TitaniumTetrachloride.getFluid(100)).fluidOutputs(Polystyrene.getFluid(3240)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(800).EUt(30).notConsumable(new IntCircuitIngredient(1)).fluidInputs(Oxygen.getFluid(7500), Styrene.getFluid(2160), TitaniumTetrachloride.getFluid(100)).fluidOutputs(Polystyrene.getFluid(4320)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).notConsumable(new IntCircuitIngredient(3)).fluidInputs(Oxygen.getFluid(1000), HydrochloricAcid.getFluid(1000), Ethylene.getFluid(1000)).fluidOutputs(Water.getFluid(1000), VinylChloride.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).fluidInputs(Oxygen.getFluid(2000), Cumene.getFluid(1000)).fluidOutputs(Phenol.getFluid(1000), Acetone.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(180).EUt(30).fluidInputs(NitrationMixture.getFluid(3000), Glycerol.getFluid(1000)).fluidOutputs(Glyceryl.getFluid(1000), DilutedSulfuricAcid.getFluid(3000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(120).fluidInputs(SulfuricAcid.getFluid(1000), AceticAcid.getFluid(1000)).fluidOutputs(Ethenone.getFluid(1000), DilutedSulfuricAcid.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(200).EUt(120).input(dust, Calcite, 5).fluidInputs(AceticAcid.getFluid(2000)).fluidOutputs(CalciumAcetate.getFluid(1000), CarbonDioxide.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(400).EUt(380).input(dust, Quicklime, 2).fluidInputs(AceticAcid.getFluid(2000)).fluidOutputs(CalciumAcetate.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(400).EUt(380).input(dust, Calcium).fluidInputs(AceticAcid.getFluid(2000), Oxygen.getFluid(1000)).fluidOutputs(CalciumAcetate.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(240).EUt(30).fluidInputs(Methanol.getFluid(1000), AceticAcid.getFluid(1000)).fluidOutputs(MethylAcetate.getFluid(1000), Water.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(480).EUt(30).fluidInputs(Glycerol.getFluid(1000), HydrochloricAcid.getFluid(1000)).fluidOutputs(Water.getFluid(2000), Epichlorhydrin.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(480).EUt(30).input(dust, SodiumHydroxide, 3).fluidInputs(AllylChloride.getFluid(1000), HypochlorousAcid.getFluid(1000)).fluidOutputs(SaltWater.getFluid(1000), Epichlorhydrin.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(60).EUt(8).input(dust, Sulfur).fluidInputs(Hydrogen.getFluid(2000)).fluidOutputs(HydrogenSulfide.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).fluidInputs(SulfuricLightFuel.getFluid(12000), Hydrogen.getFluid(2000)).fluidOutputs(HydrogenSulfide.getFluid(1000), LightFuel.getFluid(12000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).fluidInputs(SulfuricHeavyFuel.getFluid(8000), Hydrogen.getFluid(2000)).fluidOutputs(HydrogenSulfide.getFluid(1000), HeavyFuel.getFluid(8000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).fluidInputs(SulfuricNaphtha.getFluid(12000), Hydrogen.getFluid(2000)).fluidOutputs(HydrogenSulfide.getFluid(1000), Naphtha.getFluid(12000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).fluidInputs(SulfuricGas.getFluid(16000), Hydrogen.getFluid(2000)).fluidOutputs(HydrogenSulfide.getFluid(1000), Gas.getFluid(16000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).fluidInputs(NaturalGas.getFluid(16000), Hydrogen.getFluid(2000)).fluidOutputs(HydrogenSulfide.getFluid(1000), Gas.getFluid(16000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(1250).EUt(30).notConsumable(new IntCircuitIngredient(1)).fluidInputs(Nitrogen.getFluid(1000), Oxygen.getFluid(2000)).fluidOutputs(NitrogenDioxide.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).notConsumable(new IntCircuitIngredient(0)).fluidInputs(Air.getFluid(1000), VinylAcetate.getFluid(144)).fluidOutputs(PolyvinylAcetate.getFluid(144)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).notConsumable(new IntCircuitIngredient(0)).fluidInputs(Oxygen.getFluid(1000), VinylAcetate.getFluid(144)).fluidOutputs(PolyvinylAcetate.getFluid(216)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(800).EUt(30).notConsumable(new IntCircuitIngredient(1)).fluidInputs(Air.getFluid(7500), VinylAcetate.getFluid(2160), TitaniumTetrachloride.getFluid(100)).fluidOutputs(PolyvinylAcetate.getFluid(3240)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(800).EUt(30).notConsumable(new IntCircuitIngredient(1)).fluidInputs(Oxygen.getFluid(7500), VinylAcetate.getFluid(2160), TitaniumTetrachloride.getFluid(100)).fluidOutputs(PolyvinylAcetate.getFluid(4320)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(120).EUt(96).fluidInputs(Hydrogen.getFluid(6000), CarbonDioxide.getFluid(1000)).fluidOutputs(Water.getFluid(1000), Methanol.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(120).EUt(96).notConsumable(new IntCircuitIngredient(0)).fluidInputs(Hydrogen.getFluid(4000), CarbonMonoxde.getFluid(1000)).fluidOutputs(Methanol.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(320).EUt(96).input(dust, Carbon).notConsumable(new IntCircuitIngredient(3)).fluidInputs(Hydrogen.getFluid(4000), Oxygen.getFluid(1000)).fluidOutputs(Methanol.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(600).EUt(8).fluidInputs(Mercury.getFluid(1000), Water.getFluid(10000), Chlorine.getFluid(10000)).fluidOutputs(HypochlorousAcid.getFluid(10000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(120).EUt(30).notConsumable(new IntCircuitIngredient(1)).fluidInputs(Water.getFluid(1000), Chlorine.getFluid(2000)).fluidOutputs(HydrochloricAcid.getFluid(1000), HypochlorousAcid.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(960).EUt(480).fluidInputs(Dimethylamine.getFluid(1000), Chloramine.getFluid(1000)).fluidOutputs(Dimethylhydrazine.getFluid(1000), HydrochloricAcid.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(1040).EUt(480).fluidInputs(Methanol.getFluid(2000), Ammonia.getFluid(2000), HypochlorousAcid.getFluid(1000)).fluidOutputs(Dimethylhydrazine.getFluid(1000), DilutedHydrochloricAcid.getFluid(2000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(60).EUt(8).input(dust, Sulfur).notConsumable(new IntCircuitIngredient(2)).fluidInputs(Oxygen.getFluid(2000)).fluidOutputs(SulfurDioxide.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(120).EUt(30).fluidInputs(Oxygen.getFluid(3000), HydrogenSulfide.getFluid(1000)).fluidOutputs(Water.getFluid(1000), SulfurDioxide.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(60).EUt(8).fluidInputs(Hydrogen.getFluid(1000), Fluorine.getFluid(1000)).fluidOutputs(HydrofluoricAcid.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(120).EUt(30).fluidInputs(Ethylene.getFluid(1000), Benzene.getFluid(1000)).fluidOutputs(Hydrogen.getFluid(2000), Styrene.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(1920).EUt(30).fluidInputs(PhosphoricAcid.getFluid(1000), Benzene.getFluid(8000), Propene.getFluid(8000)).fluidOutputs(Cumene.getFluid(8000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(240).EUt(96).input(dust, Silicon).fluidInputs(Chloromethane.getFluid(2000)).fluidOutputs(Dimethyldichlorosilane.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(100).EUt(30).notConsumable(new IntCircuitIngredient(2)).fluidInputs(Oxygen.getFluid(2000), Ethylene.getFluid(1000)).fluidOutputs(AceticAcid.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(300).EUt(30).fluidInputs(CarbonMonoxde.getFluid(1000), Methanol.getFluid(1000)).fluidOutputs(AceticAcid.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(320).EUt(30).notConsumable(new IntCircuitIngredient(1)).fluidInputs(Hydrogen.getFluid(4000), CarbonMonoxde.getFluid(2000)).fluidOutputs(AceticAcid.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(480).EUt(30).input(dust, Carbon, 2).notConsumable(new IntCircuitIngredient(4)).fluidInputs(Oxygen.getFluid(2000), Hydrogen.getFluid(4000)).fluidOutputs(AceticAcid.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(50).EUt(600).input(dust, Aluminium, 4).fluidInputs(IndiumConcentrate.getFluid(1000)).outputs(OreDictUnifier.get(dustTiny, Indium)).fluidOutputs(LeadZincSolution.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(30).EUt(240).notConsumable(new IntCircuitIngredient(3)).fluidInputs(NitrogenDioxide.getFluid(3000), Water.getFluid(1000)).fluidOutputs(NitricOxide.getFluid(1000), NitricAcid.getFluid(2000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(320).EUt(30).notConsumable(new IntCircuitIngredient(1)).fluidInputs(Oxygen.getFluid(10000), Ammonia.getFluid(4000)).fluidOutputs(NitricOxide.getFluid(4000), Water.getFluid(6000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(180).EUt(30).notConsumable(new IntCircuitIngredient(3)).fluidInputs(Oxygen.getFluid(1000), AceticAcid.getFluid(1000), Ethylene.getFluid(1000)).fluidOutputs(Water.getFluid(1000), VinylAcetate.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(40).EUt(8).input(dust, Carbon).notConsumable(new IntCircuitIngredient(1)).fluidInputs(Oxygen.getFluid(1000)).fluidOutputs(CarbonMonoxde.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(80).EUt(8).input(gem, Charcoal).notConsumable(new IntCircuitIngredient(1)).fluidInputs(Oxygen.getFluid(1000)).outputs(OreDictUnifier.get(dustTiny, Ash)).fluidOutputs(CarbonMonoxde.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(80).EUt(8).input(gem, Coal).notConsumable(new IntCircuitIngredient(1)).fluidInputs(Oxygen.getFluid(1000)).outputs(OreDictUnifier.get(dustTiny, Ash)).fluidOutputs(CarbonMonoxde.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(80).EUt(8).input(dust, Charcoal).notConsumable(new IntCircuitIngredient(1)).fluidInputs(Oxygen.getFluid(1000)).outputs(OreDictUnifier.get(dustTiny, Ash)).fluidOutputs(CarbonMonoxde.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(80).EUt(8).input(dust, Coal).notConsumable(new IntCircuitIngredient(1)).fluidInputs(Oxygen.getFluid(1000)).outputs(OreDictUnifier.get(dustTiny, Ash)).fluidOutputs(CarbonMonoxde.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(800).EUt(8).input(dust, Carbon).fluidInputs(CarbonDioxide.getFluid(1000)).fluidOutputs(CarbonMonoxde.getFluid(2000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(320).EUt(384).notConsumable(new IntCircuitIngredient(1)).fluidInputs(Hydrogen.getFluid(3000), Nitrogen.getFluid(1000)).fluidOutputs(Ammonia.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).fluidInputs(HypochlorousAcid.getFluid(1000), Ammonia.getFluid(1000)).fluidOutputs(Water.getFluid(1000), Chloramine.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(240).EUt(120).fluidInputs(Ammonia.getFluid(1000), Methanol.getFluid(2000)).fluidOutputs(Water.getFluid(2000), Dimethylamine.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(40).EUt(30).input(dust, PhosphorousPentoxide, 14).fluidInputs(Water.getFluid(6000)).fluidOutputs(PhosphoricAcid.getFluid(4000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(320).EUt(30).input(dust, Phosphorus, 2).fluidInputs(Water.getFluid(3000), Oxygen.getFluid(5000)).fluidOutputs(PhosphoricAcid.getFluid(2000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).notConsumable(new IntCircuitIngredient(1)).fluidInputs(HydrochloricAcid.getFluid(1000), Methanol.getFluid(1000)).fluidOutputs(Water.getFluid(1000), Chloromethane.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(60).EUt(150).input(crushedPurified, Sphalerite).input(crushedPurified, Galena).fluidInputs(SulfuricAcid.getFluid(4000)).fluidOutputs(IndiumConcentrate.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(40).EUt(30).input(dust, Phosphorus, 4).fluidInputs(Oxygen.getFluid(10000)).outputs(OreDictUnifier.get(dust, PhosphorousPentoxide, 14)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(40).EUt(8).input(dust, Carbon).notConsumable(new IntCircuitIngredient(2)).fluidInputs(Oxygen.getFluid(2000)).fluidOutputs(CarbonDioxide.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(80).EUt(8).input(gem, Charcoal).notConsumable(new IntCircuitIngredient(2)).fluidInputs(Oxygen.getFluid(2000)).outputs(OreDictUnifier.get(dustTiny, Ash)).fluidOutputs(CarbonDioxide.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(80).EUt(8).input(gem, Coal).notConsumable(new IntCircuitIngredient(2)).fluidInputs(Oxygen.getFluid(2000)).outputs(OreDictUnifier.get(dustTiny, Ash)).fluidOutputs(CarbonDioxide.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(80).EUt(8).input(dust, Charcoal).notConsumable(new IntCircuitIngredient(2)).fluidInputs(Oxygen.getFluid(2000)).outputs(OreDictUnifier.get(dustTiny, Ash)).fluidOutputs(CarbonDioxide.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(80).EUt(8).input(dust, Coal).notConsumable(new IntCircuitIngredient(2)).fluidInputs(Oxygen.getFluid(2000)).outputs(OreDictUnifier.get(dustTiny, Ash)).fluidOutputs(CarbonDioxide.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(150).EUt(480).notConsumable(new IntCircuitIngredient(1)).fluidInputs(Water.getFluid(2000), Methane.getFluid(1000)).fluidOutputs(Hydrogen.getFluid(8000), CarbonDioxide.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(480).EUt(120).fluidInputs(AceticAcid.getFluid(4000), Ethenone.getFluid(4000), NitricAcid.getFluid(4000)).fluidOutputs(Tetranitromethane.getFluid(1000), AceticAcid.getFluid(7000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(480).EUt(30).notConsumable(new IntCircuitIngredient(3)).fluidInputs(Oxygen.getFluid(7000), Ammonia.getFluid(2000)).fluidOutputs(DinitrogenTetroxide.getFluid(1000), Water.getFluid(3000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(640).EUt(30).notConsumable(new IntCircuitIngredient(2)).fluidInputs(NitrogenDioxide.getFluid(2000)).fluidOutputs(DinitrogenTetroxide.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(1100).EUt(480).notConsumable(new IntCircuitIngredient(23)).fluidInputs(Oxygen.getFluid(7000), Nitrogen.getFluid(2000), Hydrogen.getFluid(6000)).fluidOutputs(DinitrogenTetroxide.getFluid(1000), Water.getFluid(3000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(320).EUt(30).notConsumable(new IntCircuitIngredient(2)).fluidInputs(Oxygen.getFluid(4000), Ammonia.getFluid(1000)).fluidOutputs(NitricAcid.getFluid(1000), Water.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(240).EUt(30).notConsumable(new IntCircuitIngredient(4)).fluidInputs(Water.getFluid(1000), Oxygen.getFluid(1000), NitrogenDioxide.getFluid(2000)).fluidOutputs(NitricAcid.getFluid(2000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(320).EUt(480).notConsumable(new IntCircuitIngredient(24)).fluidInputs(Oxygen.getFluid(4000), Nitrogen.getFluid(1000), Hydrogen.getFluid(3000)).fluidOutputs(NitricAcid.getFluid(1000), Water.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(600).EUt(30).input(dustTiny, SodiumHydroxide).fluidInputs(SeedOil.getFluid(6000), Methanol.getFluid(1000)).fluidOutputs(Glycerol.getFluid(1000), BioDiesel.getFluid(6000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(600).EUt(30).input(dustTiny, SodiumHydroxide).fluidInputs(SeedOil.getFluid(6000), Ethanol.getFluid(1000)).fluidOutputs(Glycerol.getFluid(1000), BioDiesel.getFluid(6000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(1200).EUt(120).fluidInputs(SulfuricAcid.getFluid(1000), Ethanol.getFluid(1000)).fluidOutputs(Ethylene.getFluid(1000), DilutedSulfuricAcid.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(60).EUt(30).fluidInputs(Water.getFluid(1000)).input(dust, SodiumBisulfate, 7).outputs(OreDictUnifier.get(dust, SodiumHydroxide, 3)).fluidOutputs(SulfuricAcid.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(140).EUt(192).inputs(new ItemStack(Items.SUGAR)).input(dustTiny, Plastic, 1).fluidInputs(Toluene.getFluid(133)).outputs(MetaItems.GELLED_TOLUENE.getStackForm(2)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(320).fluidInputs(HydrogenSulfide.getFluid(1000), Oxygen.getFluid(4000)).fluidOutputs(SulfuricAcid.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(640).input(dust, Saltpeter, 1).fluidInputs(Naphtha.getFluid(576)).outputs(OreDictUnifier.get(dustTiny, Potassium, 1)).fluidOutputs(Polycaprolactam.getFluid(1296)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(240).EUt(30).fluidInputs(Epichlorhydrin.getFluid(144), Naphtha.getFluid(3000), NitrogenDioxide.getFluid(1000)).fluidOutputs(Epoxid.getFluid(L * 2)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(1000).input(dust, Uraninite, 1).input(dust, Aluminium, 1).outputs(OreDictUnifier.get(dust, Uranium, 1)).fluidOutputs(Aluminium.getFluid(144), Oxygen.getFluid(2000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(1000).input(dust, Uraninite, 1).input(dust, Magnesium, 1).outputs(OreDictUnifier.get(dust, Uranium, 1)).fluidOutputs(Magnesium.getFluid(144), Oxygen.getFluid(2000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(500).input(dust, Calcium, 1).input(dust, Carbon, 1).fluidInputs(Oxygen.getFluid(3000)).outputs(OreDictUnifier.get(dust, Calcite, 5)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(1150).input(dust, Sulfur, 1).fluidInputs(Water.getFluid(4000)).fluidOutputs(SulfuricAcid.getFluid(1000), Hydrogen.getFluid(6000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(50).EUt(30).input(crushedPurified, Chalcopyrite).fluidInputs(NitricAcid.getFluid(1000)).outputs(OreDictUnifier.get(dustTiny, PlatinumGroupSludge)).fluidOutputs(CopperSulfateSolution.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(50).EUt(30).input(crushedPurified, Pentlandite).fluidInputs(NitricAcid.getFluid(1000)).outputs(OreDictUnifier.get(dustTiny, PlatinumGroupSludge)).fluidOutputs(NickelSulfateSolution.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(80).EUt(30).input(dust, Quicklime, 2).fluidInputs(CarbonDioxide.getFluid(1000)).outputs(OreDictUnifier.get(dust, Calcite, 5)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(80).EUt(30).input(dust, Magnesia, 2).fluidInputs(CarbonDioxide.getFluid(1000)).outputs(OreDictUnifier.get(dust, Magnesite, 5)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(240).EUt(30).input(dust, Calcite, 5).notConsumable(new IntCircuitIngredient(1)).outputs(OreDictUnifier.get(dust, Quicklime, 2)).fluidOutputs(CarbonDioxide.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(240).EUt(30).input(dust, Magnesite, 5).outputs(OreDictUnifier.get(dust, Magnesia, 2)).fluidOutputs(CarbonDioxide.getFluid(1000)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(600).EUt(16).input(dust, RawRubber, 9).input(dust, Sulfur, 1).fluidOutputs(Rubber.getFluid(1296)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(50).inputs(new ItemStack(Items.MELON, 1, OreDictionary.WILDCARD_VALUE)).input(nugget, Gold, 8).outputs(new ItemStack(Items.SPECKLED_MELON)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(50).inputs(new ItemStack(Items.CARROT, 1, OreDictionary.WILDCARD_VALUE)).input(nugget, Gold, 8).outputs(new ItemStack(Items.GOLDEN_CARROT)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(50).inputs(new ItemStack(Items.APPLE, 1, OreDictionary.WILDCARD_VALUE)).input(ingot, Gold, 8).outputs(new ItemStack(Items.GOLDEN_APPLE)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(50).inputs(new ItemStack(Items.APPLE, 1, OreDictionary.WILDCARD_VALUE)).input(block, Gold, 8).outputs(new ItemStack(Items.GOLDEN_APPLE, 1, 1)).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(200).EUt(24).inputs(MetaItems.GELLED_TOLUENE.getStackForm(4)).fluidInputs(SulfuricAcid.getFluid(250)).outputs(new ItemStack(Blocks.TNT)).buildAndRegister();
    }
}
