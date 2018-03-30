package gregtech.api.recipes.builders;

import com.google.common.collect.ImmutableMap;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.ValidationResult;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;

public class BrewingRecipeBuilder extends RecipeBuilder<BrewingRecipeBuilder> {

    public BrewingRecipeBuilder() {
    }

    public BrewingRecipeBuilder(Recipe recipe, RecipeMap<BrewingRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public BrewingRecipeBuilder(RecipeBuilder<BrewingRecipeBuilder> recipeBuilder) {
        super(recipeBuilder);
    }

    @Override
    protected BrewingRecipeBuilder getThis() {
        return this;
    }

    @Override
    public BrewingRecipeBuilder copy() {
        return new BrewingRecipeBuilder(this);
    }

    @Deprecated // Use BrewingRecipeBuilder#fluidInput(Fluid)
    @Override
    public BrewingRecipeBuilder fluidInputs(@Nonnull FluidStack... inputs) {
        throw new UnsupportedOperationException("This method should not get called. Use BrewingRecipeBuilder#fluidInput(Fluid)");
    }

    @Deprecated // Use BrewingRecipeBuilder#fluidOutput(Fluid)
    @Override
    public BrewingRecipeBuilder fluidOutputs(@Nonnull FluidStack... outputs) {
        throw new UnsupportedOperationException("This method should not get called. Use BrewingRecipeBuilder#fluidOutput(Fluid)");
    }

    public BrewingRecipeBuilder fluidInput(@Nonnull Fluid input) {
        this.fluidInputs.add(new FluidStack(input, 750));
        return getThis();
    }

    public BrewingRecipeBuilder fluidOutput(@Nonnull Fluid output) {
        this.fluidOutputs.add(new FluidStack(output, 750));
        return getThis();
    }

    public ValidationResult<Recipe> build() {
        return ValidationResult.newResult(finalizeAndValidate(),
            new Recipe(inputs, outputs, chancedOutputs, fluidInputs, fluidOutputs,
                ImmutableMap.of(), duration, EUt, hidden, canBeBuffered, needsEmptyOutput));
    }
}
