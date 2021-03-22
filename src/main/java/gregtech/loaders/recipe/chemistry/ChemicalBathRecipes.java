package gregtech.loaders.recipe.chemistry;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;

import static gregtech.api.recipes.RecipeMaps.CHEMICAL_BATH_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;

public class ChemicalBathRecipes {

    public static void init() {
        CHEMICAL_BATH_RECIPES.recipeBuilder()
            .input(dust, Coal)
            .fluidInputs(Water.getFluid(125))
            .output(dust, HydratedCoal)
            .duration(12).EUt(4).buildAndRegister();

        CHEMICAL_BATH_RECIPES.recipeBuilder()
            .input(dust, Wood)
            .fluidInputs(Water.getFluid(100))
            .output(Items.PAPER)
            .duration(200).EUt(4).buildAndRegister();

        CHEMICAL_BATH_RECIPES.recipeBuilder()
            .input(dust, Paper)
            .fluidInputs(Water.getFluid(100))
            .output(Items.PAPER)
            .duration(100).EUt(4).buildAndRegister();

        CHEMICAL_BATH_RECIPES.recipeBuilder()
            .input(Items.REEDS, 1, true)
            .fluidInputs(Water.getFluid(100))
            .output(Items.PAPER)
            .duration(100).EUt(8).buildAndRegister();

        CHEMICAL_BATH_RECIPES.recipeBuilder()
            .input(dust, Coal)
            .fluidInputs(DistilledWater.getFluid(125))
            .output(dust, HydratedCoal)
            .duration(12).EUt(4).buildAndRegister();

        CHEMICAL_BATH_RECIPES.recipeBuilder()
            .input(dust, Wood)
            .fluidInputs(DistilledWater.getFluid(100))
            .output(Items.PAPER)
            .duration(200).EUt(4).buildAndRegister();

        CHEMICAL_BATH_RECIPES.recipeBuilder()
            .input(dust, Paper)
            .fluidInputs(DistilledWater.getFluid(100))
            .output(Items.PAPER)
            .duration(100).EUt(4).buildAndRegister();

        CHEMICAL_BATH_RECIPES.recipeBuilder()
            .input(Items.REEDS, 1, true)
            .fluidInputs(DistilledWater.getFluid(100))
            .output(Items.PAPER)
            .duration(100).EUt(8).buildAndRegister();

        CHEMICAL_BATH_RECIPES.recipeBuilder()
            .input(Blocks.WOOL, 1, true)
            .fluidInputs(Chlorine.getFluid(50))
            .output(Blocks.WOOL)
            .duration(400).EUt(2).buildAndRegister();

        CHEMICAL_BATH_RECIPES.recipeBuilder()
            .input(Blocks.CARPET, 1, true)
            .fluidInputs(Chlorine.getFluid(25))
            .output(Blocks.CARPET)
            .duration(400).EUt(2).buildAndRegister();

        CHEMICAL_BATH_RECIPES.recipeBuilder()
            .input(Blocks.STAINED_HARDENED_CLAY, 1, true)
            .fluidInputs(Chlorine.getFluid(50))
            .output(Blocks.HARDENED_CLAY)
            .duration(400).EUt(2).buildAndRegister();

        CHEMICAL_BATH_RECIPES.recipeBuilder()
            .input(Blocks.STAINED_GLASS, 1, true)
            .fluidInputs(Chlorine.getFluid(50))
            .output(Blocks.GLASS)
            .duration(400).EUt(2).buildAndRegister();

        CHEMICAL_BATH_RECIPES.recipeBuilder()
            .input(Blocks.STAINED_GLASS_PANE, 1, true)
            .fluidInputs(Chlorine.getFluid(20))
            .output(Blocks.GLASS_PANE)
            .duration(400).EUt(2).buildAndRegister();
    }
}
