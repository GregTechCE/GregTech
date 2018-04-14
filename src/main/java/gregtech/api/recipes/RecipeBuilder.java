package gregtech.api.recipes;

import com.google.common.base.Predicates;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.EnumValidationResult;
import gregtech.api.util.GTLog;
import gregtech.api.util.GTUtility;
import gregtech.api.util.ValidationResult;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidStack;

import java.util.*;

/**
 * @see Recipe
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class RecipeBuilder<R extends RecipeBuilder<R>> {

	protected RecipeMap<R> recipeMap;

	protected List<CountableIngredient> inputs;
	protected NonNullList<ItemStack> outputs;
	protected TObjectIntMap<ItemStack> chancedOutputs;

	protected List<FluidStack> fluidInputs;
	protected List<FluidStack> fluidOutputs;

	protected int duration, EUt;

	protected boolean hidden = false;

	protected boolean canBeBuffered = true;

	protected boolean needsEmptyOutput = false;

	protected boolean optimized = true;

	protected boolean unificate = true;

	protected EnumValidationResult recipeStatus = EnumValidationResult.VALID;

	protected RecipeBuilder() {
		this.inputs = NonNullList.create();
		this.outputs = NonNullList.create();
		this.chancedOutputs = new TObjectIntHashMap<>(0);

		this.fluidInputs = new ArrayList<>(0);
		this.fluidOutputs = new ArrayList<>(0);
	}

	protected RecipeBuilder(Recipe recipe, RecipeMap<R> recipeMap) {
		this.recipeMap = recipeMap;
		this.inputs = NonNullList.create();
		this.inputs.addAll(recipe.getInputs());
		this.outputs = NonNullList.create();
		this.outputs.addAll(GTUtility.copyStackList(recipe.getOutputs()));

		this.chancedOutputs = new TObjectIntHashMap<>();
		recipe.getChancedOutputs().forEachEntry((key, value) -> {
			chancedOutputs.put(key.copy(), value);
			return true;
		});

		this.fluidInputs = GTUtility.copyFluidList(recipe.getFluidInputs());
		this.fluidOutputs = GTUtility.copyFluidList(recipe.getFluidOutputs());

		this.duration = recipe.getDuration();
		this.EUt = recipe.getEUt();
		this.hidden = recipe.isHidden();
		this.canBeBuffered = recipe.canBeBuffered();
		this.needsEmptyOutput = recipe.needsEmptyOutput();
	}

	protected RecipeBuilder(RecipeBuilder<R> recipeBuilder) {
		this.recipeMap = recipeBuilder.recipeMap;
        this.inputs = NonNullList.create();
        this.inputs.addAll(recipeBuilder.getInputs());
        this.outputs = NonNullList.create();
        this.outputs.addAll(GTUtility.copyStackList(recipeBuilder.getOutputs()));

		this.chancedOutputs = new TObjectIntHashMap<>();
		recipeBuilder.getChancedOutputs().forEachEntry((key, value) -> {
			chancedOutputs.put(key.copy(), value);
			return true;
		});

		this.fluidInputs = GTUtility.copyFluidList(recipeBuilder.getFluidInputs());
		this.fluidOutputs = GTUtility.copyFluidList(recipeBuilder.getFluidOutputs());
		this.duration = recipeBuilder.duration;
		this.EUt = recipeBuilder.EUt;
		this.hidden = recipeBuilder.hidden;
		this.canBeBuffered = recipeBuilder.canBeBuffered;
		this.needsEmptyOutput = recipeBuilder.needsEmptyOutput;
		this.optimized = recipeBuilder.optimized;
	}

	public R inputs(ItemStack... inputs) {
		return inputs(Arrays.asList(inputs));
	}

	public R inputs(Collection<ItemStack> inputs) {
		if (GTUtility.iterableContains(inputs, Predicates.or(Objects::isNull, ItemStack::isEmpty))) {
			GTLog.logger.error("Input cannot contain null or empty ItemStacks. Inputs: {}", inputs);
            GTLog.logger.error("Stacktrace:", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
		}
		inputs.forEach(stack -> {
		    if (!(stack == null || stack.isEmpty())) {
                this.inputs.add(CountableIngredient.from(stack));
            }
        });
        return getThis();
    }

    public R input(String oredict, int count) {
	    return inputs(CountableIngredient.from(oredict, count));
    }

    public R input(OrePrefix orePrefix, Material material, int count) {
	    return inputs(CountableIngredient.from(orePrefix, material, count));
    }

    public R inputs(CountableIngredient... inputs) {
	    List<CountableIngredient> ingredients = new ArrayList<>();
        for (CountableIngredient input : inputs) {
            if (input.getCount() < 0){
                GTLog.logger.error("Count cannot be less than 0. Actual: {}.", input.getCount());
                GTLog.logger.error("Stacktrace:", new IllegalArgumentException());
            } else {
                ingredients.add(input);
            }
        }

        return inputsIngredients(ingredients);
    }

    public R inputsIngredients(Collection<CountableIngredient> ingredients) {
	    this.inputs.addAll(ingredients);
        return getThis();
    }

	public R outputs(ItemStack... outputs) {
		return outputs(Arrays.asList(outputs));
	}

    public R outputs(Collection<ItemStack> outputs) {
		outputs = new ArrayList<>(outputs);
		if(GTUtility.iterableContains(outputs, Predicates.or(Objects::isNull, ItemStack::isEmpty))) {
            outputs.removeIf(Predicates.or(Objects::isNull, ItemStack::isEmpty));
        }
		this.outputs.addAll(outputs);
        return getThis();
    }

	public R fluidInputs(FluidStack... inputs) {
		return fluidInputs(Arrays.asList(inputs));
	}

    public R fluidInputs(Collection<FluidStack> inputs) {
		if (inputs.contains(null)) {
			GTLog.logger.error("Fluid input cannot contain null FluidStacks. Inputs: {}", inputs);
            GTLog.logger.error("Stacktrace:", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
		}
		this.fluidInputs.addAll(inputs);
        this.fluidInputs.removeIf(Objects::isNull);
        return getThis();
    }

	public R fluidOutputs(FluidStack... outputs) {
		return fluidOutputs(Arrays.asList(outputs));
	}

    public R fluidOutputs(Collection<FluidStack> outputs) {
		outputs = new ArrayList<>(outputs);
		if(outputs.contains(null)) outputs.removeIf(Objects::isNull);
		this.fluidOutputs.addAll(outputs);
        return getThis();
    }

	public R chancedOutput(ItemStack stack, int chance) {
		if (stack == null || stack.isEmpty()) {
			return getThis();
		}

		if (0 >= chance || chance > Recipe.getMaxChancedValue()){
			GTLog.logger.error("Chance cannot be less or equal to 0 or more than {}. Actual: {}.", Recipe.getMaxChancedValue(), chance);
            GTLog.logger.error("Stacktrace:", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
			return getThis();
		}

		this.chancedOutputs.put(stack, chance);
		return getThis();
	}

	public R duration(int duration) {
		this.duration = duration;
		return getThis();
	}

	public R noUnification() {
		this.unificate = false;
		return getThis();
	}

	public R EUt(int EUt) {
		this.EUt = EUt;
		return getThis();
	}

	public R hidden() {
		this.hidden = true;
		return getThis();
	}

	public R cannotBeBuffered() {
		this.canBeBuffered = false;
		return getThis();
	}

	public R notOptimized() {
		this.optimized = false;
		return getThis();
	}

	public R needsEmptyOutput() {
		this.needsEmptyOutput = true;
		return getThis();
	}

	public R setRecipeMap(RecipeMap<R> recipeMap) {
		this.recipeMap = recipeMap;
		return getThis();
	}

	public R fromRecipe(Recipe recipe) {
		this.inputs = recipe.getInputs();
		this.outputs = NonNullList.from(ItemStack.EMPTY, recipe.getOutputs().toArray(new ItemStack[0]));
		this.chancedOutputs = new TObjectIntHashMap<>(recipe.getChancedOutputs());
		this.fluidInputs = new ArrayList<>(recipe.getFluidInputs());
		this.fluidOutputs = new ArrayList<>(recipe.getFluidOutputs());

		this.duration = recipe.getDuration();
		this.EUt = recipe.getEUt();
		this.hidden = recipe.isHidden();
		this.canBeBuffered = recipe.canBeBuffered();
		this.needsEmptyOutput = recipe.needsEmptyOutput();
		return getThis();
	}

	public abstract R copy();

	// To get rid of "unchecked cast" warning
	protected abstract R getThis();

	protected EnumValidationResult finalizeAndValidate() {

		if (unificate) {
			outputs.replaceAll(OreDictUnifier::getUnificated);

			ItemStack[] keys = chancedOutputs.keys(new ItemStack[0]);
			int[] values = chancedOutputs.values();

            for (int i = 0; i < keys.length; i++) {
                keys[i] = OreDictUnifier.getUnificated(keys[i]);
            }

            chancedOutputs = new TObjectIntHashMap<>();
            for (int i = 0; i < keys.length; i++) {
                chancedOutputs.put(keys[i], values[i]);
            }
		}

		/* TODO maybe some day
		for (CountableIngredient ingredient : inputs) {
			if (Items.FEATHER.getDamage(ingredient) != W) {
				for (int j = 0; j < outputs.size(); j++) {
					if (ItemStack.areItemStacksEqual(ingredient, outputs.get(j))) {
						if (ingredient.getCount() >= outputs.get(j).getCount()) {
							ingredient.shrink(outputs.get(j).getCount());
							outputs.remove(j);
						} else {
							outputs.get(j).shrink(ingredient.getCount());
						}
					}
				}
			}
		}

		if (optimized && duration >= 32) {
			ArrayList<ItemStack> itemStacks = new ArrayList<>();
			itemStacks.addAll(inputs);
			itemStacks.addAll(outputs);

			for (byte i = (byte) Math.min(64, duration / 16); i > 1; i--)
				if (duration / i >= 16) {
					boolean temp = true;
					for (int j = 0, k = itemStacks.size(); temp && j < k; j++)
						if (itemStacks.get(j).getCount() % i != 0) temp = false;
					for (int j = 0; temp && j < fluidInputs.size(); j++)
						if (fluidInputs.get(j).amount % i != 0) temp = false;
					for (int j = 0; temp && j < fluidOutputs.size(); j++)
						if (fluidOutputs.get(j).amount % i != 0) temp = false;
					if (temp) {
						for (ItemStack stack : itemStacks) stack.setCount(stack.getCount() / i);
						for (FluidStack fluidInput : fluidInputs) fluidInput.amount /= i;
						for (FluidStack fluidOutput : fluidOutputs) fluidOutput.amount /= i;
						duration /= i;
					}
				}
		}
		*/

		return validate();
	}

	public abstract ValidationResult<Recipe> build();

	protected EnumValidationResult validate() {

		if (recipeMap == null) {
			GTLog.logger.error("RecipeMap cannot be null", new IllegalArgumentException());
			recipeStatus = EnumValidationResult.INVALID;
		}

        if (!GTUtility.isBetweenInclusive(recipeMap.getMinInputs(), recipeMap.getMaxInputs(), inputs.size())){
            GTLog.logger.error("Invalid amount of recipe inputs. Actual: {}. Should be between {} and {} inclusive.", inputs.size(), recipeMap.getMinInputs(), recipeMap.getMaxInputs());
            GTLog.logger.error("Stacktrace:", new IllegalArgumentException());
			recipeStatus = EnumValidationResult.INVALID;
		}
		if (!GTUtility.isBetweenInclusive(recipeMap.getMinOutputs(), recipeMap.getMaxOutputs(), outputs.size() + chancedOutputs.size())){
            GTLog.logger.error("Invalid amount of recipe outputs. Actual: {}. Should be between {} and {} inclusive.", outputs.size() + chancedOutputs.size(), recipeMap.getMinOutputs(), recipeMap.getMaxOutputs());
            GTLog.logger.error("Stacktrace:", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
		}
		if (!GTUtility.isBetweenInclusive(recipeMap.getMinFluidInputs(), recipeMap.getMaxFluidInputs(), fluidInputs.size())){
            GTLog.logger.error("Invalid amount of recipe fluid inputs. Actual: {}. Should be between {} and {} inclusive.", fluidInputs.size(), recipeMap.getMinFluidInputs(), recipeMap.getMaxFluidInputs());
            GTLog.logger.error("Stacktrace:", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
		}
		if (!GTUtility.isBetweenInclusive(recipeMap.getMinFluidOutputs(), recipeMap.getMaxFluidOutputs(), fluidOutputs.size())){
            GTLog.logger.error("Invalid amount of recipe fluid outputs. Actual: {}. Should be between {} and {} inclusive.", fluidOutputs.size(), recipeMap.getMinFluidOutputs(), recipeMap.getMaxFluidOutputs());
            GTLog.logger.error("Stacktrace:", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
		}

//			Validate.isTrue(EUt > 0, "EU/t cannot be less of equal to 0");

        if (duration <= 0){
            GTLog.logger.error("Duration cannot be less or equal to 0", new IllegalArgumentException());
			recipeStatus = EnumValidationResult.INVALID;
        }

		return recipeStatus;
	}

	public void buildAndRegister() {
		recipeMap.addRecipe(build());
	}

	///////////////////
	//    Getters    //
	///////////////////

	public List<CountableIngredient> getInputs() {
		return inputs;
	}

	public List<ItemStack> getOutputs() {
		return outputs;
	}

	public TObjectIntMap<ItemStack> getChancedOutputs() {
		return chancedOutputs;
	}

	public List<FluidStack> getFluidInputs() {
		return fluidInputs;
	}

	public List<FluidStack> getFluidOutputs() {
		return fluidOutputs;
	}

}
