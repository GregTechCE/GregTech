package gregtech.api.recipes.builders;

import com.google.common.collect.ImmutableMap;
import gregtech.api.recipes.CountableIngredient;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.EnumValidationResult;
import gregtech.api.util.GTLog;
import gregtech.api.util.GTUtility;
import gregtech.api.util.ValidationResult;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class ImplosionRecipeBuilder extends RecipeBuilder<ImplosionRecipeBuilder> {

    protected int explosivesAmount;

    public ImplosionRecipeBuilder() {
    }

    public ImplosionRecipeBuilder(Recipe recipe, RecipeMap<ImplosionRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public ImplosionRecipeBuilder(RecipeBuilder<ImplosionRecipeBuilder> recipeBuilder) {
        super(recipeBuilder);
    }

    @Override
    protected ImplosionRecipeBuilder getThis() {
        return this;
    }

    @Override
    public ImplosionRecipeBuilder copy() {
        return new ImplosionRecipeBuilder(this);
    }

    public ImplosionRecipeBuilder explosivesAmount(int explosivesAmount) {
        if (!GTUtility.isBetweenInclusive(1, 64, explosivesAmount)) {
            GTLog.logger.error("Amount of explosives should be from 1 to 64 inclusive", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }
        this.explosivesAmount = explosivesAmount;
        return this;
    }

    @Override
    public void buildAndRegister() {
        int gunpowder = explosivesAmount * 2;
        int dynamite = explosivesAmount * 4;
        int TNT = Math.max(1, explosivesAmount / 2);
        int ITNT = Math.max(1, explosivesAmount / 4);

        CountableIngredient input = inputs.get(0);
        if (gunpowder < 65) {
//				recipeMap.addRecipe(this.copy().inputs(input, ItemList.Block_Powderbarrel.get(gunpowder)).build());
        }
        if (dynamite < 17) {
//				recipeMap.addRecipe(this.copy().inputs(input, ModHandler.IC2.getIC2Item(ItemName.dynamite, dynamite)).build());
        }
        recipeMap.addRecipe(this.copy()
            .inputs(new ItemStack(Blocks.TNT, TNT))
            .build());
//			recipeMap.addRecipe(this.copy().inputs(input, ModHandler.IC2.getIC2Item(BlockName.te, TeBlock.itnt, ITNT)).build());
    }

    public ValidationResult<Recipe> build() {
        return ValidationResult.newResult(finalizeAndValidate(),
            new Recipe(inputs, outputs, chancedOutputs, fluidInputs, fluidOutputs,
                ImmutableMap.of(), duration, EUt, hidden, canBeBuffered, needsEmptyOutput));
    }
}
