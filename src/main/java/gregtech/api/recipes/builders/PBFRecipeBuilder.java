package gregtech.api.recipes.builders;

import gregtech.api.recipes.recipes.PrimitiveBlastFurnaceRecipe;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.EnumValidationResult;
import gregtech.api.util.GTLog;
import gregtech.api.util.ValidationResult;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.oredict.OreIngredient;

public class PBFRecipeBuilder {

    private Ingredient input;
    private ItemStack output;

    private int duration = -1;
    private int fuelAmount = -1;

	private PBFRecipeBuilder() {
	}

	public static PBFRecipeBuilder start() {
		return new PBFRecipeBuilder();
	}

    public PBFRecipeBuilder input(Ingredient input) {
        this.input = input;
        return this;
	}

	public PBFRecipeBuilder input(ItemStack itemStack) {
	    this.input = Ingredient.fromStacks(itemStack);
	    return this;
    }

    public PBFRecipeBuilder input(OrePrefix orePrefix, Material material) {
	    this.input = new OreIngredient(orePrefix.name() + material.toCamelCaseString());
	    return this;
    }

    public PBFRecipeBuilder duration(int duration) {
        this.duration = duration;
        return this;
    }

    public PBFRecipeBuilder fuelAmount(int fuelAmount) {
        this.fuelAmount = fuelAmount;
        return this;
    }

    public PBFRecipeBuilder output(ItemStack output) {
        this.output = output;
        return this;
    }

    public ValidationResult<PrimitiveBlastFurnaceRecipe> build() {
		return ValidationResult.newResult(validate(),
				new PrimitiveBlastFurnaceRecipe(input, output, duration, fuelAmount));
	}

	protected EnumValidationResult validate() {
		EnumValidationResult result = EnumValidationResult.VALID;

		if(input == null) {
            GTLog.logger.error("Input Ingredient cannot be null", new IllegalArgumentException());
            result = EnumValidationResult.INVALID;
        }

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
		ValidationResult<PrimitiveBlastFurnaceRecipe> result = build();

		if (result.getType() == EnumValidationResult.VALID) {
            PrimitiveBlastFurnaceRecipe recipe = result.getResult();
            RecipeMaps.PRIMITIVE_BLAST_FURNACE_RECIPES.add(recipe);
		}
	}
}
