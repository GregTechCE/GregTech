package gregtech.api.recipes.ingredients.fluid;

import java.util.Arrays;
import java.util.Objects;

public class AmountFluidIngredient {

    private FluidIngredient ingredient;
    private int amount;

    public AmountFluidIngredient(FluidIngredient ingredient, int amount) {
        this.ingredient = ingredient;
        this.amount = amount;
    }

    public FluidIngredient getIngredient() {
        return ingredient;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AmountFluidIngredient that = (AmountFluidIngredient) o;
        return amount == that.amount &&
            Objects.equals(ingredient, that.ingredient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ingredient, amount);
    }

    @Override
    public String toString() {
        return "CountableIngredient{" +
            "ingredient=" + Arrays.toString(ingredient.getMatchingStacks()) +
            ", count=" + amount +
            '}';
    }

}
