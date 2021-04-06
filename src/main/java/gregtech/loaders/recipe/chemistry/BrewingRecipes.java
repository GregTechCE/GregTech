package gregtech.loaders.recipe.chemistry;

import gregtech.common.items.MetaItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;

import static gregtech.api.recipes.RecipeMaps.BREWING_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.Materials.Biomass;

public class BrewingRecipes {

    public static void init() {
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
            .input(Items.POTATO)
            .fluidInputs(Honey.getFluid(20))
            .fluidOutputs(Biomass.getFluid(30))
            .duration(160).EUt(3).buildAndRegister();

        BREWING_RECIPES.recipeBuilder()
            .input(Items.CARROT)
            .fluidInputs(Honey.getFluid(20))
            .fluidOutputs(Biomass.getFluid(30))
            .duration(160).EUt(3).buildAndRegister();

        BREWING_RECIPES.recipeBuilder()
            .input(Blocks.CACTUS)
            .fluidInputs(Honey.getFluid(20))
            .fluidOutputs(Biomass.getFluid(30))
            .duration(160).EUt(3).buildAndRegister();

        BREWING_RECIPES.recipeBuilder()
            .input(Items.REEDS)
            .fluidInputs(Honey.getFluid(20))
            .fluidOutputs(Biomass.getFluid(30))
            .duration(160).EUt(3).buildAndRegister();

        BREWING_RECIPES.recipeBuilder()
            .input(Blocks.BROWN_MUSHROOM)
            .fluidInputs(Honey.getFluid(20))
            .fluidOutputs(Biomass.getFluid(30))
            .duration(160).EUt(3).buildAndRegister();

        BREWING_RECIPES.recipeBuilder()
            .input(Blocks.RED_MUSHROOM)
            .fluidInputs(Honey.getFluid(20))
            .fluidOutputs(Biomass.getFluid(30))
            .duration(160).EUt(3).buildAndRegister();

        BREWING_RECIPES.recipeBuilder()
            .input(Items.BEETROOT)
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
            .input(Items.POTATO)
            .fluidInputs(Juice.getFluid(20))
            .fluidOutputs(Biomass.getFluid(30))
            .duration(160).EUt(3).buildAndRegister();

        BREWING_RECIPES.recipeBuilder()
            .input(Items.CARROT)
            .fluidInputs(Juice.getFluid(20))
            .fluidOutputs(Biomass.getFluid(30))
            .duration(160).EUt(3).buildAndRegister();

        BREWING_RECIPES.recipeBuilder()
            .input(Blocks.CACTUS)
            .fluidInputs(Juice.getFluid(20))
            .fluidOutputs(Biomass.getFluid(30))
            .duration(160).EUt(3).buildAndRegister();

        BREWING_RECIPES.recipeBuilder()
            .input(Items.REEDS)
            .fluidInputs(Juice.getFluid(20))
            .fluidOutputs(Biomass.getFluid(30))
            .duration(160).EUt(3).buildAndRegister();

        BREWING_RECIPES.recipeBuilder()
            .input(Blocks.BROWN_MUSHROOM)
            .fluidInputs(Juice.getFluid(20))
            .fluidOutputs(Biomass.getFluid(30))
            .duration(160).EUt(3).buildAndRegister();

        BREWING_RECIPES.recipeBuilder()
            .input(Blocks.RED_MUSHROOM)
            .fluidInputs(Juice.getFluid(20))
            .fluidOutputs(Biomass.getFluid(30))
            .duration(160).EUt(3).buildAndRegister();

        BREWING_RECIPES.recipeBuilder()
            .input(Items.BEETROOT)
            .fluidInputs(Juice.getFluid(20))
            .fluidOutputs(Biomass.getFluid(30))
            .duration(160).EUt(3).buildAndRegister();
    }
}
