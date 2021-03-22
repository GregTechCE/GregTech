package gregtech.loaders.recipe.chemistry;

import gregtech.api.unification.OreDictUnifier;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import static gregtech.api.recipes.RecipeMaps.CENTRIFUGE_RECIPES;
import static gregtech.api.recipes.RecipeMaps.ELECTROLYZER_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static gregtech.api.unification.ore.OrePrefix.dustTiny;

public class SeparationRecipes {

    public static void init() {

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
            .fluidInputs(Acetone.getFluid(2000))
            .output(dust, Carbon, 3)
            .fluidOutputs(Propane.getFluid(1000))
            .fluidOutputs(Water.getFluid(2000))
            .duration(480).EUt(60).buildAndRegister();
    }
}
