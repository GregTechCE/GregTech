package gregtech.api.recipes.builders;

import com.google.common.collect.ImmutableMap;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.ValidationResult;

public class DefaultRecipeBuilder extends RecipeBuilder<DefaultRecipeBuilder> {

    public DefaultRecipeBuilder() {
    }

    public DefaultRecipeBuilder(Recipe recipe, RecipeMap<DefaultRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public DefaultRecipeBuilder(RecipeBuilder<DefaultRecipeBuilder> recipeBuilder) {
        super(recipeBuilder);
    }

    @Override
    protected DefaultRecipeBuilder getThis() {
        return this;
    }

    @Override
    public DefaultRecipeBuilder copy() {
        return new DefaultRecipeBuilder(this);
    }

    public ValidationResult<Recipe> build() {
        return ValidationResult.newResult(finalizeAndValidate(),
            new Recipe(inputs, outputs, chancedOutputs, fluidInputs, fluidOutputs,
                ImmutableMap.of(), duration, EUt, hidden, canBeBuffered, needsEmptyOutput));
    }
}
