package gregtech.api.recipes.recipes;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public class PrimitiveBlastFurnaceRecipe {

    private final Ingredient input;
    private final ItemStack output;

    private final int duration;
    private final int fuelAmount;

    public PrimitiveBlastFurnaceRecipe(Ingredient input, ItemStack output, int duration, int fuelAmount) {
        this.input = input;
        this.output = output;
        this.duration = duration;
        this.fuelAmount = fuelAmount;
    }

    public Ingredient getInput() {
        return input;
    }

    public int getDuration() {
        return duration;
    }

    public int getFuelAmount() {
        return fuelAmount;
    }

    public ItemStack getOutput() {
        return output;
    }
}
