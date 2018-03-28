package gregtech.api.recipes;

import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.recipes.machines.RecipeMapFluidCanner;
import gregtech.api.recipes.machines.RecipeMapFormingPress;
import gregtech.api.recipes.machines.RecipeMapFurnace;
import gregtech.api.recipes.machines.RecipeMapPrinter;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.EnumValidationResult;
import gregtech.api.util.GTLog;
import gregtech.api.util.GTUtility;
import gregtech.api.util.ValidationResult;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nullable;
import java.util.*;

public class RecipeMap<R extends RecipeBuilder<R>> {

	public static final Collection<RecipeMap<?>> RECIPE_MAPS = new ArrayList<>();

	public final String unlocalizedName;

	private final R recipeBuilderSample;
	private final int minInputs, maxInputs;
	private final int minOutputs, maxOutputs;
	private final int minFluidInputs, maxFluidInputs;
	private final int minFluidOutputs, maxFluidOutputs;
	private final int amperage;

    private final Map<Fluid, Collection<Recipe>> recipeFluidMap = new HashMap<>();
    private final Collection<Recipe> recipeList;

	public RecipeMap(String unlocalizedName,
                     int minInputs, int maxInputs, int minOutputs, int maxOutputs,
                     int minFluidInputs, int maxFluidInputs, int minFluidOutputs, int maxFluidOutputs,
                     int amperage, R defaultRecipe) {
        this.unlocalizedName = unlocalizedName;
		this.recipeList = new HashSet<>();
		this.amperage = amperage;

		this.minInputs = minInputs;
		this.minFluidInputs = minFluidInputs;
		this.minOutputs = minOutputs;
		this.minFluidOutputs = minFluidOutputs;

		this.maxInputs = maxInputs;
		this.maxFluidInputs = maxFluidInputs;
		this.maxOutputs = maxOutputs;
		this.maxFluidOutputs = maxFluidOutputs;

        defaultRecipe.setRecipeMap(this);
        this.recipeBuilderSample = defaultRecipe;
        RECIPE_MAPS.add(this);
	}

	public static boolean foundInvalidRecipe = false;

	//internal usage only, use buildAndRegister()
	public void addRecipe(ValidationResult<Recipe> validationResult) {
		switch (validationResult.getType()) {
			case SKIP:
				return;
			case INVALID:
				foundInvalidRecipe = true;
				return;
		}
		Recipe recipe = validationResult.getResult();
		recipeList.add(recipe);

		for (FluidStack fluid : recipe.getFluidInputs()) {
			recipeFluidMap.computeIfAbsent(fluid.getFluid(), k -> new HashSet<>(1)).add(recipe);
		}
	}

    @Nullable
    public Recipe findRecipe(long voltage, IItemHandlerModifiable inputs, IMultipleTankHandler fluidInputs) {
        return this.findRecipe(voltage, GTUtility.itemHandlerToList(inputs), GTUtility.fluidHandlerToList(fluidInputs));
    }

	/**
	 * Finds a Recipe matching the Fluid and ItemStack Inputs.
	 *
	 * @param voltage       Voltage of the Machine or Long.MAX_VALUE if it has no Voltage
	 * @param inputs        the Item Inputs
	 * @param fluidInputs   the Fluid Inputs
	 * @return the Recipe it has found or null for no matching Recipe
	 */
	@Nullable
	public Recipe findRecipe(long voltage, NonNullList<ItemStack> inputs, List<FluidStack> fluidInputs) {
        if (recipeList.isEmpty())
            return null;
        if (minFluidInputs > 0 && GTUtility.amountOfNonNullElements(fluidInputs) < minFluidInputs) {
            return null;
        }
        if (minInputs > 0 && GTUtility.amountOfNonEmptyStacks(inputs) < minInputs) {
            return null;
        }
        if (maxInputs > 0) {
            return findByInputs(voltage, inputs, fluidInputs);
        } else {
            return findByFluidInputs(voltage, inputs, fluidInputs);
        }
    }

    @Nullable
    private Recipe findByFluidInputs(long voltage, NonNullList<ItemStack> inputs, List<FluidStack> fluidInputs) {
        for (FluidStack fluid : fluidInputs) {
            if (fluid == null) continue;
            Collection<Recipe> recipes = recipeFluidMap.get(fluid.getFluid());
            if (recipes == null) continue;
            for (Recipe tmpRecipe : recipes) {
                if (tmpRecipe.matches(false, false, inputs, fluidInputs)) {
                    return voltage * amperage >= tmpRecipe.getEUt() ? tmpRecipe : null;
                }
            }
        }
        return null;
    }

	@Nullable
	private Recipe findByInputs(long voltage, NonNullList<ItemStack> inputs, List<FluidStack> fluidInputs) {
        for (Recipe recipe : recipeList) {
            if (recipe.matches(false, false, inputs, fluidInputs)) {
                return voltage * amperage >= recipe.getEUt() ? recipe : null;
            }
        }
		return null;
	}

	public R recipeBuilder() {
		return recipeBuilderSample.copy();
	}

	public int getMinInputs() {
		return minInputs;
	}

	public int getMaxInputs() {
		return maxInputs;
	}

	public int getMinOutputs() {
		return minOutputs;
	}

	public int getMaxOutputs() {
		return maxOutputs;
	}

	public int getMinFluidInputs() {
		return minFluidInputs;
	}

	public int getMaxFluidInputs() {
		return maxFluidInputs;
	}

	public int getMinFluidOutputs() {
		return minFluidOutputs;
	}

	public int getMaxFluidOutputs() {
		return maxFluidOutputs;
	}


}
