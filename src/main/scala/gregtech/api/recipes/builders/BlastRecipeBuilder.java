package gregtech.api.recipes.builders;

import com.google.common.collect.ImmutableMap;
import crafttweaker.annotations.ZenRegister;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.EnumValidationResult;
import gregtech.api.util.GTLog;
import gregtech.api.util.ValidationResult;
import org.apache.commons.lang3.builder.ToStringBuilder;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.gregtech.builders.BlastRecipeBuilder")
@ZenRegister
public class BlastRecipeBuilder extends RecipeBuilder<BlastRecipeBuilder> {

    private int blastFurnaceTemp;

    public BlastRecipeBuilder() {
    }

    public BlastRecipeBuilder(Recipe recipe, RecipeMap<BlastRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
        this.blastFurnaceTemp = recipe.getIntegerProperty("blastFurnaceTemp");
    }

    public BlastRecipeBuilder(RecipeBuilder<BlastRecipeBuilder> recipeBuilder) {
        super(recipeBuilder);
    }

    @Override
    protected BlastRecipeBuilder getThis() {
        return this;
    }

    @Override
    public BlastRecipeBuilder copy() {
        return new BlastRecipeBuilder(this);
    }

    @ZenMethod
    public BlastRecipeBuilder blastFurnaceTemp(int blastFurnaceTemp) {
        if (blastFurnaceTemp <= 0) {
            GTLog.logger.error("Blast Furnace Temperature cannot be less than or equal to 0", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }
        this.blastFurnaceTemp = blastFurnaceTemp;
        return getThis();
    }

    public ValidationResult<Recipe> build() {
        return ValidationResult.newResult(finalizeAndValidate(),
            new Recipe(inputs, outputs, chancedOutputs, fluidInputs, fluidOutputs,
                ImmutableMap.of("blast_furnace_temperature", blastFurnaceTemp),
                duration, EUt, hidden, canBeBuffered, needsEmptyOutput));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("blast_furnace_temperature", blastFurnaceTemp)
            .toString();
    }
}
