package gregtech.api.recipes.builders;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.EnumValidationResult;
import gregtech.api.util.GTLog;
import gregtech.api.util.GTUtility;
import gregtech.api.util.ValidationResult;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.builder.ToStringBuilder;
import stanhebben.zenscript.annotations.ZenMethod;

public class ImplosionRecipeBuilder extends RecipeBuilder<ImplosionRecipeBuilder> {

    protected int explosivesAmount;
    protected ItemStack explosivesType;

    public ImplosionRecipeBuilder() {
    }

    public ImplosionRecipeBuilder(Recipe recipe, RecipeMap<ImplosionRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public ImplosionRecipeBuilder(RecipeBuilder<ImplosionRecipeBuilder> recipeBuilder) {
        super(recipeBuilder);
    }

    @Override
    public ImplosionRecipeBuilder copy() {
        return new ImplosionRecipeBuilder(this);
    }

    @Override
    public boolean applyProperty(String key, Object value) {
        if (key.equals("explosives")) {
            explosivesAmount((int) value);
            return true;
        }
        return false;
    }

    @Override
    public boolean applyProperty(String key, ItemStack exploType) {
        if (key.equals("explosives")) {
            explosivesAmount(exploType.getCount());
            explosivesType = exploType;
            return true;
        }
        return false;
    }

    @ZenMethod
    public ImplosionRecipeBuilder explosivesAmount(int explosivesAmount) {
        if (!GTUtility.isBetweenInclusive(1, 64, explosivesAmount)) {
            GTLog.logger.error("Amount of explosives should be from 1 to 64 inclusive", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }
        this.explosivesAmount = explosivesAmount;
        return this;
    }

    @ZenMethod
    public ImplosionRecipeBuilder explosivesType(ItemStack explosivesType) {
        this.explosivesType = explosivesType;
        return this;
    }

    @Override
    public void buildAndRegister() {
        int amount = Math.max(1, explosivesAmount / 2);
        if (explosivesType == null) {
            explosivesType = new ItemStack(Blocks.TNT, amount);
        } else {
            explosivesType = new ItemStack(explosivesType.getItem(), amount, explosivesType.getMetadata());
        }
        recipeMap.addRecipe(this.copy().inputs(explosivesType).build());
    }

    public ValidationResult<Recipe> build() {
        return ValidationResult.newResult(finalizeAndValidate(),
                new Recipe(inputs, outputs, chancedOutputs, fluidInputs, fluidOutputs, duration, EUt, hidden));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("explosivesAmount", explosivesAmount)
                .toString();
    }
}
