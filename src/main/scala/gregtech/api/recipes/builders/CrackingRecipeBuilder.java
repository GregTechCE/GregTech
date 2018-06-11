package gregtech.api.recipes.builders;

import com.google.common.collect.ImmutableMap;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.ValidationResult;
import net.minecraftforge.fluids.FluidStack;

public class CrackingRecipeBuilder extends RecipeBuilder<CrackingRecipeBuilder> {

    public CrackingRecipeBuilder() {
    }

    public CrackingRecipeBuilder(Recipe recipe, RecipeMap<CrackingRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public CrackingRecipeBuilder(RecipeBuilder<CrackingRecipeBuilder> recipeBuilder) {
        super(recipeBuilder);
    }

    @Override
    public CrackingRecipeBuilder copy() {
        return new CrackingRecipeBuilder(this);
    }

    @Override
    protected CrackingRecipeBuilder getThis() {
        return this;
    }

    @Override
    public ValidationResult<Recipe> build() {
        return ValidationResult.newResult(finalizeAndValidate(),
            new Recipe(inputs, outputs, chancedOutputs, fluidInputs, fluidOutputs,
                ImmutableMap.of(), duration, EUt, hidden, canBeBuffered, needsEmptyOutput));
    }
}
