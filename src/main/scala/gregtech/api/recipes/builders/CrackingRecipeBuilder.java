package gregtech.api.recipes.builders;

import com.google.common.collect.ImmutableMap;
import crafttweaker.annotations.ZenRegister;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.ValidationResult;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;

@ZenClass
@ZenRegister
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

    @Override
    public void buildAndRegister() {
        super.buildAndRegister();
        FluidStack fluidInput = fluidInputs.get(0);
        FluidStack fluidOutput = fluidOutputs.get(0);
        fluidOutputs.clear(); //clear fluid outputs because we add them again with multipliers
        recipeMap.addRecipe(this.copy()
            .fluidInputs(ModHandler.getSteam(fluidInput.amount))
            .fluidOutputs(fluidOutput, Materials.Hydrogen.getFluid(fluidInput.amount))
            .build());
        recipeMap.addRecipe(this.copy()
            .fluidInputs(Materials.Hydrogen.getFluid(fluidInput.amount))
            .fluidOutputs(new FluidStack(fluidOutput.getFluid(), (int) (fluidOutput.amount * 1.3)))
            .build());
    }
}
