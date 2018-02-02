package gregtech.api.recipes;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.oredict.OreIngredient;

import java.util.Objects;

public class CountableIngredient {

    public static CountableIngredient from(ItemStack stack) {
        return new CountableIngredient(Ingredient.fromStacks(stack), stack.getCount());
    }

    public static CountableIngredient from(String oredict, int count) {
        return new CountableIngredient(new OreIngredient(oredict), count);
    }

    private Ingredient ingredient;
    private int count;

    public CountableIngredient(Ingredient ingredient, int count) {
        this.ingredient = ingredient;
        this.count = count;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public int getCount() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CountableIngredient that = (CountableIngredient) o;
        return count == that.count &&
            Objects.equals(ingredient, that.ingredient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ingredient, count);
    }
}
