package gregtech.api.recipes.builders;

import com.google.common.collect.ImmutableMap;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import gregtech.api.GTValues;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.recipes.CountableIngredient;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.EnumValidationResult;
import gregtech.api.util.GTLog;
import gregtech.api.util.ValidationResult;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fml.common.Optional.Method;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass
@ZenRegister
public class NotConsumableInputRecipeBuilder extends RecipeBuilder<NotConsumableInputRecipeBuilder> {

    public NotConsumableInputRecipeBuilder() {
    }

    public NotConsumableInputRecipeBuilder(Recipe recipe, RecipeMap<NotConsumableInputRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public NotConsumableInputRecipeBuilder(RecipeBuilder<NotConsumableInputRecipeBuilder> recipeBuilder) {
        super(recipeBuilder);
    }

    @Override
    protected NotConsumableInputRecipeBuilder getThis() {
        return this;
    }

    @Override
    public NotConsumableInputRecipeBuilder copy() {
        return new NotConsumableInputRecipeBuilder(this);
    }

    @ZenMethod
    @Method(modid = GTValues.MODID_CC)
    public NotConsumableInputRecipeBuilder notConsumable(IIngredient ingredient) {
        return notConsumable(new CraftTweakerIngredientWrapper(ingredient));
    }

    public NotConsumableInputRecipeBuilder notConsumable(ItemStack itemStack) {
        if (itemStack == null) {
            GTLog.logger.error("Not consumable input cannot be null.");
            GTLog.logger.error("Stacktrace:", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        } else {
            ItemStack stack = itemStack.copy();
            inputs.add(CountableIngredient.from(stack, 0));
        }
        return this;
    }

    public NotConsumableInputRecipeBuilder notConsumable(OrePrefix prefix, Material material) {
        if (prefix == null) {
            GTLog.logger.error("Ore prefix input cannot be null.");
            GTLog.logger.error("Stacktrace:", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        } else {
            inputs.add(CountableIngredient.from(prefix, material, 0));
        }
        return this;
    }

    public NotConsumableInputRecipeBuilder notConsumable(Ingredient ingredient) {
        if (ingredient == null) {
            GTLog.logger.error("Not consumable input cannot be null.");
            GTLog.logger.error("Stacktrace:", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        } else {
            inputs.add(new CountableIngredient(ingredient, 0));
        }
        return this;
    }

    public NotConsumableInputRecipeBuilder notConsumable(MetaItem<?>.MetaValueItem item) {
        if (item == null) {
            GTLog.logger.error("Not consumable input cannot be null.", inputs);
            GTLog.logger.error("Stacktrace:", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        } else {
            inputs.add(CountableIngredient.from(item.getStackForm(1), 0));
        }
        return this;
    }

    public ValidationResult<Recipe> build() {
        return ValidationResult.newResult(finalizeAndValidate(),
            new Recipe(inputs, outputs, chancedOutputs, fluidInputs, fluidOutputs,
                ImmutableMap.of(), duration, EUt, hidden, canBeBuffered, needsEmptyOutput));
    }
}
