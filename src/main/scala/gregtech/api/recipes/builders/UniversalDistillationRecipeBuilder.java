package gregtech.api.recipes.builders;

import com.google.common.collect.ImmutableMap;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.util.ValidationResult;
import net.minecraftforge.fluids.FluidStack;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class UniversalDistillationRecipeBuilder extends RecipeBuilder<UniversalDistillationRecipeBuilder> {

    protected boolean universal = false;

    public UniversalDistillationRecipeBuilder() {
    }

    public UniversalDistillationRecipeBuilder(Recipe recipe, RecipeMap<gregtech.api.recipes.builders.UniversalDistillationRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public UniversalDistillationRecipeBuilder(RecipeBuilder<gregtech.api.recipes.builders.UniversalDistillationRecipeBuilder> recipeBuilder) {
        super(recipeBuilder);
    }

    @Override
    protected gregtech.api.recipes.builders.UniversalDistillationRecipeBuilder getThis() {
        return this;
    }

    @Override
    public gregtech.api.recipes.builders.UniversalDistillationRecipeBuilder copy() {
        return new gregtech.api.recipes.builders.UniversalDistillationRecipeBuilder(this);
    }

    public gregtech.api.recipes.builders.UniversalDistillationRecipeBuilder universal() {
        this.universal = true;
        return getThis();
    }

    @Override
    public void buildAndRegister() {
        if (universal) {
            IntCircuitRecipeBuilder builder = RecipeMaps.DISTILLERY_RECIPES.recipeBuilder()
                .fluidInputs(this.fluidInputs.toArray(new FluidStack[0]))
                .duration(this.duration * 2)
                .EUt(this.EUt / 4);

            for (int i = 0; i < fluidOutputs.size(); i++) {
                builder.copy().circuitMeta(i).fluidOutputs(this.fluidOutputs.get(i)).buildAndRegister();
            }
        }

        super.buildAndRegister();
    }

    public ValidationResult<Recipe> build() {
        return ValidationResult.newResult(finalizeAndValidate(),
            new Recipe(inputs, outputs, chancedOutputs, fluidInputs, fluidOutputs,
                ImmutableMap.of(), duration, EUt, hidden, canBeBuffered, needsEmptyOutput));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("universal", universal)
            .toString();
    }
}
