package gregtech.api.recipes.builders;

import com.google.common.collect.ImmutableMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.FluidMaterial;
import gregtech.api.util.ValidationResult;

public class ArcFurnaceRecipeBuilder extends RecipeBuilder<ArcFurnaceRecipeBuilder> {

    protected boolean simple = false;

    public ArcFurnaceRecipeBuilder() {
    }

    public ArcFurnaceRecipeBuilder(Recipe recipe, RecipeMap<ArcFurnaceRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public ArcFurnaceRecipeBuilder(RecipeBuilder<ArcFurnaceRecipeBuilder> recipeBuilder) {
        super(recipeBuilder);
    }

    @Override
    protected ArcFurnaceRecipeBuilder getThis() {
        return this;
    }

    @Override
    public ArcFurnaceRecipeBuilder copy() {
        return new ArcFurnaceRecipeBuilder(this);
    }

    public ArcFurnaceRecipeBuilder simple() {
        this.simple = true;
        return getThis();
    }

    @Override
    public void buildAndRegister() {
        if (simple) {
            this.copy().buildAndRegister();
        } else {
            this.copy().fluidInputs(Materials.Oxygen.getFluid(this.duration)).buildAndRegister();

            for (FluidMaterial material : new FluidMaterial[]{Materials.Argon, Materials.Nitrogen}) {
                int plasmaAmount = (int) Math.max(1L, this.duration / (material.getMass() * 16L));

                DefaultRecipeBuilder builder = RecipeMaps.PLASMA_ARC_FURNACE_RECIPES.recipeBuilder()
                    .inputsIngredients(this.inputs)
                    .outputs(this.outputs)
                    .duration(this.duration / 16)
                    .EUt(this.EUt / 3)
                    .fluidInputs(material.getPlasma(plasmaAmount))
                    .fluidOutputs(material.getFluid(plasmaAmount));

                this.getChancedOutputs().forEachEntry((key, val) -> {
                   builder.chancedOutput(key, val);
                   return true;
                });

                builder.buildAndRegister();
            }
        }
    }

    public ValidationResult<Recipe> build() {
        return ValidationResult.newResult(finalizeAndValidate(),
            new Recipe(inputs, outputs, chancedOutputs, fluidInputs, fluidOutputs,
                ImmutableMap.of(), duration, EUt, hidden, canBeBuffered, needsEmptyOutput));
    }
}
