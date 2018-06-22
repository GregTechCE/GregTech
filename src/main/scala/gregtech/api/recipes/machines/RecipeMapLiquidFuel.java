package gregtech.api.recipes.machines;

import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.builders.SimpleRecipeBuilder;

public class RecipeMapLiquidFuel extends RecipeMap<SimpleRecipeBuilder> {

    public RecipeMapLiquidFuel(String unlocalizedName, int minFluidOutputs, int maxFluidOutputs, int amperage, SimpleRecipeBuilder defaultRecipe) {
        super(unlocalizedName, 0, 0, 0, 0, 1, 1, minFluidOutputs, maxFluidOutputs, amperage, defaultRecipe);
    }

}
