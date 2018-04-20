package gregtech.api.recipes.builders;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.util.EnumValidationResult;
import gregtech.api.util.GTLog;
import gregtech.api.util.ValidationResult;
import net.minecraft.item.ItemStack;

public class PrimitiveBlastFurnaceRecipeBuilder {

    private int duration = -1;
    private int fuelAmount = -1;

    private ItemStack output;

	private PrimitiveBlastFurnaceRecipeBuilder() {
	}

	public static PrimitiveBlastFurnaceRecipeBuilder start() {
		return new PrimitiveBlastFurnaceRecipeBuilder();
	}

    public PrimitiveBlastFurnaceRecipeBuilder setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    public PrimitiveBlastFurnaceRecipeBuilder setFuelAmount(int fuelAmount) {
        this.fuelAmount = fuelAmount;
        return this;
    }

    public PrimitiveBlastFurnaceRecipeBuilder setOutput(ItemStack output) {
        this.output = output;
        return this;
    }

    public ValidationResult<Recipe.PrimitiveBlastFurnaceRecipe> build() {
		return ValidationResult.newResult(validate(),
				new Recipe.PrimitiveBlastFurnaceRecipe(duration, fuelAmount, output));
	}

	protected EnumValidationResult validate() {
		EnumValidationResult result = EnumValidationResult.VALID;

		if (output == null || output.isEmpty()) {
			GTLog.logger.error("Output ItemStack cannot be null or empty", new IllegalArgumentException());
			result = EnumValidationResult.INVALID;
		}

		if (fuelAmount <= 0) {
			GTLog.logger.error("Research Time cannot be less or equal to 0", new IllegalArgumentException());
			result = EnumValidationResult.INVALID;
		}
		if (duration <= 0) {
			GTLog.logger.error("Duration cannot be less or equal to 0", new IllegalArgumentException());
			result = EnumValidationResult.INVALID;
		}

		return result;
	}

	public void buildAndRegister() {
		ValidationResult<Recipe.PrimitiveBlastFurnaceRecipe> result = build();

		if (result.getType() == EnumValidationResult.VALID) {
            Recipe.PrimitiveBlastFurnaceRecipe recipe = result.getResult();
            RecipeMaps.PRIMITIVE_BLAST_FURNACE.put(new SimpleItemStack(recipe.getOutput()), recipe);
		}
	}
}
