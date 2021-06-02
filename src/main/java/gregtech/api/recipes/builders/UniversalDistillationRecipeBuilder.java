package gregtech.api.recipes.builders;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.util.ValidationResult;
import net.minecraftforge.fluids.FluidStack;

public class UniversalDistillationRecipeBuilder extends RecipeBuilder<UniversalDistillationRecipeBuilder> {

    public UniversalDistillationRecipeBuilder() {
    }

    public UniversalDistillationRecipeBuilder(Recipe recipe, RecipeMap<UniversalDistillationRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public UniversalDistillationRecipeBuilder(RecipeBuilder<UniversalDistillationRecipeBuilder> recipeBuilder) {
        super(recipeBuilder);
    }

    @Override
    public UniversalDistillationRecipeBuilder copy() {
        return new UniversalDistillationRecipeBuilder(this);
    }

    @Override
    public void buildAndRegister() {
        IntCircuitRecipeBuilder builder = RecipeMaps.DISTILLERY_RECIPES.recipeBuilder()
                .fluidInputs(this.fluidInputs.toArray(new FluidStack[0]))
                .duration(this.duration * 2)
                .EUt(this.EUt / 4);

        for (int i = 0; i < fluidOutputs.size(); i++) {
            builder.copy().circuitMeta(i).fluidOutputs(this.fluidOutputs.get(i)).buildAndRegister();
        }

        super.buildAndRegister();
    }

    public ValidationResult<Recipe> build() {
        return ValidationResult.newResult(finalizeAndValidate(),
                new Recipe(inputs, outputs, chancedOutputs, fluidInputs, fluidOutputs, duration, EUt, hidden));
    }

}
