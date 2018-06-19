package gregtech.api.recipes.builders;

import com.google.common.collect.ImmutableMap;
import crafttweaker.annotations.ZenRegister;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.EnumValidationResult;
import gregtech.api.util.GTLog;
import gregtech.api.util.ValidationResult;
import org.apache.commons.lang3.builder.ToStringBuilder;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.gregtech.builders.AmplifierRecipeBuilder")
@ZenRegister
public class AmplifierRecipeBuilder extends RecipeBuilder<AmplifierRecipeBuilder> {

    private int amplifierAmountOutputted = -1;

    public AmplifierRecipeBuilder() {
    }

    public AmplifierRecipeBuilder(Recipe recipe, RecipeMap<AmplifierRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
        this.amplifierAmountOutputted = recipe.getIntegerProperty("amplifierAmountOutputted");
    }

    public AmplifierRecipeBuilder(RecipeBuilder<AmplifierRecipeBuilder> recipeBuilder) {
        super(recipeBuilder);
    }

    @Override
    protected AmplifierRecipeBuilder getThis() {
        return this;
    }

    @Override
    public AmplifierRecipeBuilder copy() {
        return new AmplifierRecipeBuilder(this);
    }

    @ZenMethod
    public AmplifierRecipeBuilder amplifierAmountOutputted(int amplifierAmountOutputted) {
        if (amplifierAmountOutputted <= 0) {
            GTLog.logger.error("Outputted Amplifier Amount cannot be less than or equal to 0", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }
        this.amplifierAmountOutputted = amplifierAmountOutputted;
        return getThis();
    }

    @Override
    protected EnumValidationResult finalizeAndValidate() {
        if (amplifierAmountOutputted > 0) {
            this.fluidOutputs(Materials.UUAmplifier.getFluid(amplifierAmountOutputted));
        }
        return super.finalizeAndValidate();
    }

    public ValidationResult<Recipe> build() {
        return ValidationResult.newResult(finalizeAndValidate(),
            new Recipe(inputs, outputs, chancedOutputs, fluidInputs, fluidOutputs,
                ImmutableMap.of("amplifierAmountOutputted", amplifierAmountOutputted),
                duration, EUt, hidden, canBeBuffered, needsEmptyOutput));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("amplifierAmountOutputted", amplifierAmountOutputted)
            .toString();
    }
}
