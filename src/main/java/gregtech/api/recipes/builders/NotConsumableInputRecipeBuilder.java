package gregtech.api.recipes.builders;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableMap;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.recipes.CountableIngredient;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.EnumValidationResult;
import gregtech.api.util.GTLog;
import gregtech.api.util.GTUtility;
import gregtech.api.util.ValidationResult;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import java.util.Collection;
import java.util.Objects;

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


    @Override
    public NotConsumableInputRecipeBuilder inputs(Collection<ItemStack> inputs) {
        if (GTUtility.iterableContains(inputs, Predicates.or(Objects::isNull, GTUtility::isEmptyIgnoringSize))) {
            GTLog.logger.error("Input cannot contain null or empty ItemStacks. Inputs: {}", inputs);
            GTLog.logger.error("Stacktrace:", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }
        inputs.forEach(stack -> {
            if(stack != null) {
                //workaround for old recipes using zero-size stacks as non-consumed
                int originalCount = stack.getCount();
                if(originalCount == 0)
                    stack.setCount(1);
                if (!stack.isEmpty()) {
                    this.inputs.add(CountableIngredient.from(stack, originalCount));
                }
            }
        });
        return getThis();
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
