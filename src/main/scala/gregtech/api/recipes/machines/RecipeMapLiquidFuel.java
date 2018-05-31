package gregtech.api.recipes.machines;

import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.builders.DefaultRecipeBuilder;

public class RecipeMapLiquidFuel extends RecipeMap<DefaultRecipeBuilder> {

    public RecipeMapLiquidFuel(String unlocalizedName, int minFluidOutputs, int maxFluidOutputs, int amperage, DefaultRecipeBuilder defaultRecipe) {
        super(unlocalizedName, 0, 0, 0, 0, 1, 1, minFluidOutputs, maxFluidOutputs, amperage, defaultRecipe);
    }

}
