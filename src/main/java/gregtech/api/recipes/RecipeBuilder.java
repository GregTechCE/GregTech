package gregtech.api.recipes;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableMap;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.FluidMaterial;
import gregtech.api.util.EnumValidationResult;
import gregtech.api.util.GTLog;
import gregtech.api.util.GTUtility;
import gregtech.api.util.ValidationResult;
import gregtech.common.items.MetaItems;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
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
		    if (!Predicates.or(Objects::isNull, ItemStack::isEmpty).apply(stack)) {
                this.inputs.add(CountableIngredient.from(stack));
            }
        });
        return getThis();
    }

    public R input(String oredict, int count) {
	    return inputs(CountableIngredient.from(oredict, count));
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

    protected R inputsIngredients(Collection<CountableIngredient> ingredients) {
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

		if (0 >= chance || chance > 10000){
			GTLog.logger.error("Chance cannot be less or equal to 0 or more than 10000. Actual: {}.", chance);
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

	public static class DefaultRecipeBuilder extends RecipeBuilder<DefaultRecipeBuilder> {

		public DefaultRecipeBuilder() {
		}

		public DefaultRecipeBuilder(Recipe recipe, RecipeMap<DefaultRecipeBuilder> recipeMap) {
			super(recipe, recipeMap);
		}

		public DefaultRecipeBuilder(RecipeBuilder<DefaultRecipeBuilder> recipeBuilder) {
			super(recipeBuilder);
		}

		@Override
		protected DefaultRecipeBuilder getThis() {
			return this;
		}

		@Override
		public DefaultRecipeBuilder copy() {
			return new DefaultRecipeBuilder(this);
		}

		public ValidationResult<Recipe> build() {
			return ValidationResult.newResult(finalizeAndValidate(),
						new Recipe(inputs, outputs, chancedOutputs, fluidInputs, fluidOutputs,
                            ImmutableMap.of(), duration, EUt, hidden, canBeBuffered, needsEmptyOutput));
		}
	}

	public static class IntCircuitRecipeBuilder extends RecipeBuilder<IntCircuitRecipeBuilder> {

		protected int circuitMeta = -1;

		public IntCircuitRecipeBuilder() {
		}

		public IntCircuitRecipeBuilder(Recipe recipe, RecipeMap<IntCircuitRecipeBuilder> recipeMap) {
			super(recipe, recipeMap);
		}

		public IntCircuitRecipeBuilder(RecipeBuilder<IntCircuitRecipeBuilder> recipeBuilder) {
			super(recipeBuilder);
		}

		@Override
		protected IntCircuitRecipeBuilder getThis() {
			return this;
		}

		@Override
		public IntCircuitRecipeBuilder copy() {
			return new IntCircuitRecipeBuilder(this);
		}

		public IntCircuitRecipeBuilder circuitMeta(int circuitMeta) {
			if (circuitMeta < 0) {
				GTLog.logger.error("Integrated Circuit Metadata cannot be less than 0", new IllegalArgumentException()); // TODO cannot be more than what?
				recipeStatus = EnumValidationResult.INVALID;
			}
			this.circuitMeta = circuitMeta;
			return this;
		}

		@Override
		protected EnumValidationResult finalizeAndValidate() {
			if (circuitMeta >= 0) {
				inputs.add(CountableIngredient.from(MetaItems.getIntegratedCircuit(circuitMeta)));
			}
			return super.finalizeAndValidate();
		}

		public ValidationResult<Recipe> build() {
			return ValidationResult.newResult(finalizeAndValidate(),
					new Recipe(inputs, outputs, chancedOutputs, fluidInputs, fluidOutputs,
                        ImmutableMap.of(), duration, EUt, hidden, canBeBuffered, needsEmptyOutput));
		}
	}

	public static class NotConsumableInputRecipeBuilder extends RecipeBuilder<NotConsumableInputRecipeBuilder> {

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

		public NotConsumableInputRecipeBuilder notConsumable(Item item) {
            if (item == null) {
                GTLog.logger.error("Not consumable input cannot be null.");
                GTLog.logger.error("Stacktrace:", new IllegalArgumentException());
                recipeStatus = EnumValidationResult.INVALID;
            } else {
                inputs.add(CountableIngredient.from(new ItemStack(item, 0)));
            }
			return this;
		}

		public NotConsumableInputRecipeBuilder notConsumable(ItemStack itemStack) {
            if (itemStack == null) {
                GTLog.logger.error("Not consumable input cannot be null.");
                GTLog.logger.error("Stacktrace:", new IllegalArgumentException());
                recipeStatus = EnumValidationResult.INVALID;
            } else {
                ItemStack stack = itemStack.copy();
                stack.setCount(0);
                inputs.add(CountableIngredient.from(stack));
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
                inputs.add(CountableIngredient.from(item.getStackForm(0)));
            }
			return this;
		}

		public ValidationResult<Recipe> build() {
			return ValidationResult.newResult(finalizeAndValidate(),
					new Recipe(inputs, outputs, chancedOutputs, fluidInputs, fluidOutputs,
                        ImmutableMap.of(), duration, EUt, hidden, canBeBuffered, needsEmptyOutput));
		}
	}

	public static class BlastRecipeBuilder extends RecipeBuilder<BlastRecipeBuilder> {

		private int blastFurnaceTemp;

		public BlastRecipeBuilder() {
		}

		public BlastRecipeBuilder(Recipe recipe, RecipeMap<BlastRecipeBuilder> recipeMap) {
			super(recipe, recipeMap);
			this.blastFurnaceTemp = recipe.getIntegerProperty("blastFurnaceTemp");
		}

		public BlastRecipeBuilder(RecipeBuilder<BlastRecipeBuilder> recipeBuilder) {
			super(recipeBuilder);
		}

		@Override
		protected BlastRecipeBuilder getThis() {
			return this;
		}

		@Override
		public BlastRecipeBuilder copy() {
			return new BlastRecipeBuilder(this);
		}

		public BlastRecipeBuilder blastFurnaceTemp(int blastFurnaceTemp) {
			if (blastFurnaceTemp <= 0) {
				GTLog.logger.error("Blast Furnace Temperature cannot be less than or equal to 0", new IllegalArgumentException());
				recipeStatus = EnumValidationResult.INVALID;
			}
			this.blastFurnaceTemp = blastFurnaceTemp;
			return getThis();
		}

		public ValidationResult<Recipe> build() {
			return ValidationResult.newResult(finalizeAndValidate(),
					new Recipe(inputs, outputs, chancedOutputs, fluidInputs, fluidOutputs,
                        ImmutableMap.of("blastFurnaceTemp", blastFurnaceTemp),
                        duration, EUt, hidden, canBeBuffered, needsEmptyOutput));
		}
	}

	public static class ArcFurnaceRecipeBuilder extends RecipeBuilder<ArcFurnaceRecipeBuilder> {

		protected boolean simple = false;

		public ArcFurnaceRecipeBuilder() {
		}

		public ArcFurnaceRecipeBuilder(Recipe recipe, RecipeMap<ArcFurnaceRecipeBuilder> recipeMap) {
			super(recipe, recipeMap);
		}

		public ArcFurnaceRecipeBuilder(RecipeBuilder<ArcFurnaceRecipeBuilder> recipeBuilder) {
			super(recipeBuilder);
		}

		@Override
		protected ArcFurnaceRecipeBuilder getThis() {
			return this;
		}

		@Override
		public ArcFurnaceRecipeBuilder copy() {
			return new ArcFurnaceRecipeBuilder(this);
		}

		public ArcFurnaceRecipeBuilder simple() {
			this.simple = true;
			return getThis();
		}

		@Override
		public void buildAndRegister() {
			if (simple) {
				this.copy().buildAndRegister();
			} else {
				this.copy().fluidInputs(Materials.Oxygen.getFluid(this.duration)).buildAndRegister();

				for (FluidMaterial material : new FluidMaterial[]{Materials.Argon, Materials.Nitrogen}) {
					int plasmaAmount = (int) Math.max(1L, this.duration / (material.getMass() * 16L));

					DefaultRecipeBuilder builder = RecipeMap.PLASMA_ARC_FURNACE_RECIPES.recipeBuilder()
							.inputsIngredients(this.inputs)
							.outputs(this.outputs)
							.duration(this.duration / 16)
							.EUt(this.EUt / 3)
							.fluidInputs(material.getPlasma(plasmaAmount))
							.fluidOutputs(material.getFluid(plasmaAmount));

					builder.chancedOutputs = new TObjectIntHashMap<>(this.getChancedOutputs());

					builder.buildAndRegister();
				}
			}
		}

		public ValidationResult<Recipe> build() {
			return ValidationResult.newResult(finalizeAndValidate(),
					new Recipe(inputs, outputs, chancedOutputs, fluidInputs, fluidOutputs,
                        ImmutableMap.of(), duration, EUt, hidden, canBeBuffered, needsEmptyOutput));
		}
	}

	public static class ImplosionRecipeBuilder extends RecipeBuilder<ImplosionRecipeBuilder> {

		protected int explosivesAmount;

		public ImplosionRecipeBuilder() {
		}

		public ImplosionRecipeBuilder(Recipe recipe, RecipeMap<ImplosionRecipeBuilder> recipeMap) {
			super(recipe, recipeMap);
		}

		public ImplosionRecipeBuilder(RecipeBuilder<ImplosionRecipeBuilder> recipeBuilder) {
			super(recipeBuilder);
		}

		@Override
		protected ImplosionRecipeBuilder getThis() {
			return this;
		}

		@Override
		public ImplosionRecipeBuilder copy() {
			return new ImplosionRecipeBuilder(this);
		}

		public ImplosionRecipeBuilder explosivesAmount(int explosivesAmount) {
			if (!GTUtility.isBetweenInclusive(1, 64, explosivesAmount)) {
				GTLog.logger.error("Amount of explosives should be from 1 to 64 inclusive", new IllegalArgumentException());
				recipeStatus = EnumValidationResult.INVALID;
			}
			this.explosivesAmount = explosivesAmount;
			return this;
		}

		@Override
		public void buildAndRegister() {
			int gunpowder = explosivesAmount * 2;
			int dynamite = explosivesAmount * 4;
			int TNT = Math.max(1, explosivesAmount / 2);
			int ITNT = Math.max(1, explosivesAmount / 4);

			CountableIngredient input = inputs.get(0);
			if (gunpowder < 65) {
//				recipeMap.addRecipe(this.copy().inputs(input, ItemList.Block_Powderbarrel.get(gunpowder)).build());
			}
			if (dynamite < 17) {
//				recipeMap.addRecipe(this.copy().inputs(input, ModHandler.IC2.getIC2Item(ItemName.dynamite, dynamite)).build());
			}
			recipeMap.addRecipe(this.copy()
                .inputsIngredients(Collections.singleton(input))
                .inputs(new ItemStack(Blocks.TNT, TNT))
                .build());
//			recipeMap.addRecipe(this.copy().inputs(input, ModHandler.IC2.getIC2Item(BlockName.te, TeBlock.itnt, ITNT)).build());
		}

		public ValidationResult<Recipe> build() {
			return ValidationResult.newResult(finalizeAndValidate(),
					new Recipe(inputs, outputs, chancedOutputs, fluidInputs, fluidOutputs,
                        ImmutableMap.of(), duration, EUt, hidden, canBeBuffered, needsEmptyOutput));
		}
	}

	public static class UniversalDistillationRecipeBuilder extends RecipeBuilder<UniversalDistillationRecipeBuilder> {

		protected boolean universal = false;

		public UniversalDistillationRecipeBuilder() {
		}

		public UniversalDistillationRecipeBuilder(Recipe recipe, RecipeMap<UniversalDistillationRecipeBuilder> recipeMap) {
			super(recipe, recipeMap);
		}

		public UniversalDistillationRecipeBuilder(RecipeBuilder<UniversalDistillationRecipeBuilder> recipeBuilder) {
			super(recipeBuilder);
		}

		@Override
		protected UniversalDistillationRecipeBuilder getThis() {
			return this;
		}

		@Override
		public UniversalDistillationRecipeBuilder copy() {
			return new UniversalDistillationRecipeBuilder(this);
		}

		public UniversalDistillationRecipeBuilder universal() {
			this.universal = true;
			return getThis();
		}

		@Override
		public void buildAndRegister() {
			if (universal) {
				IntCircuitRecipeBuilder builder = RecipeMap.DISTILLERY_RECIPES.recipeBuilder()
						.fluidInputs(this.fluidInputs.toArray(new FluidStack[0]))
						.duration(this.duration * 2)
						.EUt(this.EUt / 4);

				for (int i = 0; i < fluidOutputs.size(); i++) {
					builder.copy().circuitMeta(i).fluidOutputs(this.fluidOutputs.get(i)).buildAndRegister();
				}
			}

			super.buildAndRegister();
		}

		public ValidationResult<Recipe> build() {
			return ValidationResult.newResult(finalizeAndValidate(),
					new Recipe(inputs, outputs, chancedOutputs, fluidInputs, fluidOutputs,
                        ImmutableMap.of(), duration, EUt, hidden, canBeBuffered, needsEmptyOutput));
		}
	}

	public static class AmplifierRecipeBuilder extends RecipeBuilder<AmplifierRecipeBuilder> {

		private int amplifierAmountOutputted = -1;

		public AmplifierRecipeBuilder() {}

		public AmplifierRecipeBuilder(Recipe recipe, RecipeMap<AmplifierRecipeBuilder> recipeMap) {
			super(recipe, recipeMap);
            this.amplifierAmountOutputted  = recipe.getIntegerProperty("amplifierAmountOutputted");
        }

		public AmplifierRecipeBuilder(RecipeBuilder<AmplifierRecipeBuilder> recipeBuilder) {
			super(recipeBuilder);
		}

		@Override
		protected AmplifierRecipeBuilder getThis() {
			return this;
		}

		@Override
		public AmplifierRecipeBuilder copy() {
			return new AmplifierRecipeBuilder(this);
		}

		public AmplifierRecipeBuilder amplifierAmountOutputted(int amplifierAmountOutputted) {
			if (amplifierAmountOutputted <= 0) {
				GTLog.logger.error("Outputted Amplifier Amount cannot be less than or equal to 0", new IllegalArgumentException());
				recipeStatus = EnumValidationResult.INVALID;
			}
			this.amplifierAmountOutputted = amplifierAmountOutputted;
			return getThis();
		}

		@Override
		protected EnumValidationResult finalizeAndValidate() {
			if (amplifierAmountOutputted > 0) {
				this.fluidOutputs(Materials.UUAmplifier.getFluid(amplifierAmountOutputted));
			}
			return super.finalizeAndValidate();
		}

		public ValidationResult<Recipe> build() {
			return ValidationResult.newResult(finalizeAndValidate(),
					new Recipe(inputs, outputs, chancedOutputs, fluidInputs, fluidOutputs,
                        ImmutableMap.of("amplifierAmountOutputted", amplifierAmountOutputted),
						duration, EUt, hidden, canBeBuffered, needsEmptyOutput));
		}
	}

	public static class BrewingRecipeBuilder extends RecipeBuilder<BrewingRecipeBuilder> {

		public BrewingRecipeBuilder() {}

		public BrewingRecipeBuilder(Recipe recipe, RecipeMap<BrewingRecipeBuilder> recipeMap) {
			super(recipe, recipeMap);
		}

		public BrewingRecipeBuilder(RecipeBuilder<BrewingRecipeBuilder> recipeBuilder) {
			super(recipeBuilder);
		}

		@Override
		protected BrewingRecipeBuilder getThis() {
			return this;
		}

		@Override
		public BrewingRecipeBuilder copy() {
			return new BrewingRecipeBuilder(this);
		}

		@Deprecated // Use BrewingRecipeBuilder#fluidInput(Fluid)
		@Override
		public BrewingRecipeBuilder fluidInputs(@Nonnull FluidStack... inputs) {
			throw new UnsupportedOperationException("This method should not get called. Use BrewingRecipeBuilder#fluidInput(Fluid)");
		}

		@Deprecated // Use BrewingRecipeBuilder#fluidOutput(Fluid)
		@Override
		public BrewingRecipeBuilder fluidOutputs(@Nonnull FluidStack... outputs) {
			throw new UnsupportedOperationException("This method should not get called. Use BrewingRecipeBuilder#fluidOutput(Fluid)");
		}

		public BrewingRecipeBuilder fluidInput(@Nonnull Fluid input) {
			this.fluidInputs.add(new FluidStack(input, 750));
			return getThis();
		}

		public BrewingRecipeBuilder fluidOutput(@Nonnull Fluid output) {
			this.fluidOutputs.add(new FluidStack(output, 750));
			return getThis();
		}

		public ValidationResult<Recipe> build() {
			return ValidationResult.newResult(finalizeAndValidate(),
					new Recipe(inputs, outputs, chancedOutputs, fluidInputs, fluidOutputs,
                        ImmutableMap.of(), duration, EUt, hidden, canBeBuffered, needsEmptyOutput));
		}
	}

	public static class FusionRecipeBuilder extends RecipeBuilder<FusionRecipeBuilder> {

		private int EUToStart;

		public FusionRecipeBuilder() {}

		public FusionRecipeBuilder(Recipe recipe, RecipeMap<FusionRecipeBuilder> recipeMap) {
			super(recipe, recipeMap);
			this.EUToStart = recipe.getIntegerProperty("EUToStart");
		}

		public FusionRecipeBuilder(RecipeBuilder<FusionRecipeBuilder> recipeBuilder) {
			super(recipeBuilder);
		}

		@Override
		protected FusionRecipeBuilder getThis() {
			return this;
		}

		@Override
		public FusionRecipeBuilder copy() {
			return new FusionRecipeBuilder(this);
		}

		public FusionRecipeBuilder EUToStart(int EUToStart) {
			if (EUToStart <= 0) {
				GTLog.logger.error("EU to start cannot be less than or equal to 0", new IllegalArgumentException());
				recipeStatus = EnumValidationResult.INVALID;
			}
			this.EUToStart = EUToStart;
			return getThis();
		}

		public ValidationResult<Recipe> build() {
			return ValidationResult.newResult(finalizeAndValidate(),
                new Recipe(inputs, outputs, chancedOutputs, fluidInputs, fluidOutputs,
                    ImmutableMap.of("EUToStart", EUToStart),
                    duration, EUt, hidden, canBeBuffered, needsEmptyOutput));
        }
    }

	public static class AssemblyLineRecipeBuilder {

		private ItemStack researchItem;
		private int researchTime;

		private List<ItemStack> inputs;
		private List<FluidStack> fluidInputs;
		private ItemStack output;

		private int duration;
		private int EUt;

		private AssemblyLineRecipeBuilder() {
		}

		public AssemblyLineRecipeBuilder start() {
			return new AssemblyLineRecipeBuilder();
		}

		public AssemblyLineRecipeBuilder researchItem(ItemStack researchItem) {
			this.researchItem = researchItem;
			return this;
		}

		public AssemblyLineRecipeBuilder researchTime(int researchTime) {
			this.researchTime = researchTime;
			return this;
		}

		public AssemblyLineRecipeBuilder inputs(@Nonnull ItemStack... inputs) {
			Collections.addAll(this.inputs, inputs);
			return this;
		}

		public AssemblyLineRecipeBuilder fluidInputs(@Nonnull FluidStack... inputs) {
			Collections.addAll(this.fluidInputs, inputs);
			return this;
		}

		public AssemblyLineRecipeBuilder output(ItemStack output) {
			this.output = output;
			return this;
		}

		public AssemblyLineRecipeBuilder duration(int duration) {
			this.duration = duration;
			return this;
		}

		public AssemblyLineRecipeBuilder EUt(int EUt) {
			this.EUt = EUt;
			return this;
		}

		public ValidationResult<Recipe.AssemblyLineRecipe> build() {
			return ValidationResult.newResult(validate(),
					new Recipe.AssemblyLineRecipe(researchItem, researchTime, inputs, fluidInputs, output, duration, EUt));
		}

		protected EnumValidationResult validate() {
		    EnumValidationResult result = EnumValidationResult.VALID;

            if (inputs.contains(null)) {
                GTLog.logger.error("Input cannot contain null ItemStacks", new IllegalArgumentException());
                result = EnumValidationResult.INVALID;
            }
            if (fluidInputs.contains(null)) {
                GTLog.logger.error("Fluid input cannot contain null FluidStacks", new IllegalArgumentException());
                result = EnumValidationResult.INVALID;
            }

            if (output == null) {
                GTLog.logger.error("Output ItemStack cannot be null", new IllegalArgumentException());
                result = EnumValidationResult.INVALID;
            }
            if (researchItem == null) {
                GTLog.logger.error("Research ItemStack cannot be null", new IllegalArgumentException());
                result = EnumValidationResult.INVALID;
            }

            if (researchTime <= 0) {
                GTLog.logger.error("Research Time cannot be less or equal to 0", new IllegalArgumentException());
                result = EnumValidationResult.INVALID;
            }
            if (duration <= 0) {
                GTLog.logger.error("Duration cannot be less or equal to 0", new IllegalArgumentException());
                result = EnumValidationResult.INVALID;
            }
            if (EUt <= 0) {
                GTLog.logger.error("EUt cannot be less or equal to 0", new IllegalArgumentException());
                result = EnumValidationResult.INVALID;
            }

            if (!GTUtility.isBetweenInclusive(4, 16, inputs.size())) {
                GTLog.logger.error("Invalid amount of recipe inputs. Should be between {} and {} inclusive", 4, 16);
                GTLog.logger.error("", new IllegalArgumentException());
                result = EnumValidationResult.INVALID;
            }
            if (!GTUtility.isBetweenInclusive(0, 4, fluidInputs.size())) {
                GTLog.logger.error("Invalid amount of recipe fluid inputs. Should be between {} and {} inclusive", 0, 4);
                GTLog.logger.error("", new IllegalArgumentException());
                result = EnumValidationResult.INVALID;
            }

			return result;
		}

		public void buildAndRegister() {
			ValidationResult<Recipe.AssemblyLineRecipe> result = build();

			if (result.getType() == EnumValidationResult.VALID) {
				RecipeMap.ASSEMBLYLINE_RECIPES.add(result.getResult());
			}
		}
	}
}
