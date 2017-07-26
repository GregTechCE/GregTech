package gregtech.api.recipes;

import gregtech.api.enums.ItemList;
import gregtech.api.enums.material.Materials;
import gregtech.api.util.GT_Utility;
import ic2.core.ref.BlockName;
import ic2.core.ref.ItemName;
import ic2.core.ref.TeBlock;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.apache.commons.lang3.Validate;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static gregtech.api.enums.GT_Values.W;

/**
 * @see Recipe
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class RecipeBuilder<T extends Recipe, R extends RecipeBuilder<T, R>> {

	protected RecipeMap<T, R> recipeMap;

	protected List<ItemStack> inputs = new ArrayList<>(0);
	protected List<ItemStack> outputs = new ArrayList<>(0);
	protected Map<ItemStack, Integer> chancedOutputs = new HashMap<>(0);

	protected List<FluidStack> fluidInputs = new ArrayList<>(0);
	protected List<FluidStack> fluidOutputs = new ArrayList<>(0);

	protected int duration, EUt;

	protected boolean hidden = false;

	protected boolean canBeBuffered = true;

	protected boolean needsEmptyOutput = false;

	protected boolean optimized = true;

	protected RecipeBuilder() {
		this.inputs = new ArrayList<>(0);
		this.outputs = new ArrayList<>(0);
		this.chancedOutputs = new HashMap<>(0);

		this.fluidInputs = new ArrayList<>(0);
		this.fluidOutputs = new ArrayList<>(0);
	}

	protected RecipeBuilder(T recipe, RecipeMap<T, R> recipeMap) {
		this.recipeMap = recipeMap;
		this.inputs = GT_Utility.copyStackList(recipe.getInputs());
		this.outputs = GT_Utility.copyStackList(recipe.getOutputs());

		this.chancedOutputs = new HashMap<>();
		for (Map.Entry<ItemStack, Integer> entry : recipe.getChancedOutputs().entrySet()) {
			chancedOutputs.put(entry.getKey().copy(), entry.getValue());
		}

		this.fluidInputs = GT_Utility.copyFluidList(recipe.getFluidInputs());
		this.fluidOutputs = GT_Utility.copyFluidList(recipe.getFluidOutputs());

		this.duration = recipe.getDuration();
		this.EUt = recipe.getEUt();
		this.hidden = recipe.isHidden();
		this.canBeBuffered = recipe.canBeBuffered();
		this.needsEmptyOutput = recipe.needsEmptyOutput();
	}

	protected RecipeBuilder(RecipeBuilder<T, R> recipeBuilder) {
		this.recipeMap = recipeBuilder.recipeMap;
		this.inputs = GT_Utility.copyStackList(recipeBuilder.getInputs());
		this.outputs = GT_Utility.copyStackList(recipeBuilder.getOutputs());

		this.chancedOutputs = new HashMap<>();
		for (Map.Entry<ItemStack, Integer> entry : recipeBuilder.getChancedOutputs().entrySet()) {
			chancedOutputs.put(entry.getKey().copy(), entry.getValue());
		}

		this.fluidInputs = GT_Utility.copyFluidList(recipeBuilder.getFluidInputs());
		this.fluidOutputs = GT_Utility.copyFluidList(recipeBuilder.getFluidOutputs());
		this.duration = recipeBuilder.duration;
		this.EUt = recipeBuilder.EUt;
		this.hidden = recipeBuilder.hidden;
		this.canBeBuffered = recipeBuilder.canBeBuffered;
		this.needsEmptyOutput = recipeBuilder.needsEmptyOutput;
		this.optimized = recipeBuilder.optimized;
	}

	public R inputs(@Nonnull ItemStack... inputs) {
		Validate.notNull(inputs, "Input array cannot be null");
		if (inputs.length != 0) {
			Validate.noNullElements(inputs, "Input cannot contain null ItemStacks");

			Collections.addAll(this.inputs, inputs);
		}
		return getThis();
	}

	public R outputs(@Nonnull ItemStack... outputs) {
		Validate.notNull(outputs, "Output array cannot be null");
		if (outputs.length != 0) {
			Validate.noNullElements(outputs, "Output cannot contain null ItemStacks");

			Collections.addAll(this.outputs, outputs);
		}
		return getThis();
	}

	public R fluidInputs(@Nonnull FluidStack... inputs) {
		Validate.notNull(inputs, "Fluid input array cannot be null");
		if (inputs.length != 0) {
			Validate.noNullElements(inputs, "Fluid input cannot contain null FluidStacks");

			Collections.addAll(this.fluidInputs, inputs);
		}
		return getThis();
	}

	public R fluidOutputs(@Nonnull FluidStack... outputs) {
		Validate.notNull(outputs, "Fluid output array cannot be null");
		if (outputs.length != 0) {
			Validate.noNullElements(outputs, "Fluid output cannot contain null FluidStacks");

			Collections.addAll(this.fluidOutputs, outputs);
		}
		return getThis();
	}

	public R chancedOutput(@Nonnull ItemStack stack, int chance) {
		Validate.notNull(stack, "Chanced output ItemStack cannot be null");
		Validate.exclusiveBetween(0, 10001, chance, "Chance cannot be less or equal to 0 or more than 10000");

		if (this.chancedOutputs.containsKey(stack)) {
			throw new IllegalArgumentException("Chanced output map already contains " + stack);
		}

		this.chancedOutputs.put(stack, chance);
		return getThis();
	}

	public R duration(int duration) {
		Validate.isTrue(duration > 0, "Duration cannot be less or equal to 0");

		this.duration = duration;
		return getThis();
	}

	public R EUt(int EUt) {
		Validate.isTrue(EUt > 0, "EUt cannot be less or equal to 0");

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

	public R nonOptimized() {
		this.optimized = false;
		return getThis();
	}

	public R needsEmptyOutput() {
		this.needsEmptyOutput = true;
		return getThis();
	}

	public R setRecipeMap(RecipeMap<T, R> recipeMap) {
		this.recipeMap = recipeMap;
		return getThis();
	}

	public R fromRecipe(T recipe) {
		this.inputs = new ArrayList<>(recipe.getInputs());
		this.outputs = new ArrayList<>(recipe.getOutputs());
		this.chancedOutputs = new HashMap<>(recipe.getChancedOutputs());
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

	protected void finalizeAndValidate() {
//			GT_OreDictUnificator.setStackArray(true, inputs);
//			GT_OreDictUnificator.setStackArray(true, outputs);
//			GT_OreDictUnificator.setStackArray(true, chancedOutputs);

		for (ItemStack stack : inputs) {
			if (Items.FEATHER.getDamage(stack) != W) {
				for (int j = 0; j < outputs.size(); j++) {
					if (GT_Utility.areStacksEqual(stack, outputs.get(j))) {
						if (stack.stackSize >= outputs.get(j).stackSize) {
							stack.stackSize -= outputs.get(j).stackSize;
							outputs.remove(j);
						} else {
							outputs.get(j).stackSize -= stack.stackSize;
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
						if (itemStacks.get(j).stackSize % i != 0) temp = false;
					for (int j = 0; temp && j < fluidInputs.size(); j++)
						if (fluidInputs.get(j).amount % i != 0) temp = false;
					for (int j = 0; temp && j < fluidOutputs.size(); j++)
						if (fluidOutputs.get(j).amount % i != 0) temp = false;
					if (temp) {
						for (ItemStack stack : itemStacks) stack.stackSize /= i;
						for (FluidStack fluidInput : fluidInputs) fluidInput.amount /= i;
						for (FluidStack fluidOutput : fluidOutputs) fluidOutput.amount /= i;
						duration /= i;
					}
				}
		}

		validate();
	}

	public abstract T build();

	protected R validate() {
		Validate.notNull(recipeMap);
		Validate.exclusiveBetween(recipeMap.getMinInputs(), recipeMap.getMaxInputs(), inputs.size());
		Validate.exclusiveBetween(recipeMap.getMinOutputs(), recipeMap.getMaxOutputs(), outputs.size() + chancedOutputs.size());
		Validate.exclusiveBetween(recipeMap.getMinFluidInputs(), recipeMap.getMaxFluidInputs(), fluidInputs.size());
		Validate.exclusiveBetween(recipeMap.getMinFluidOutputs(), recipeMap.getMaxFluidOutputs(), fluidOutputs.size());

//			Validate.isTrue(EUt > 0, "EU/t cannot be less of equal to 0");
		Validate.isTrue(duration > 0, "Duration cannot be less or equal to 0");

		//For fakeRecipes don't do check for collisions, regular recipes do check, do not check for recipes that are not registered(i.e. created after postinit stage)
		Validate.isTrue(!(recipeMap instanceof RecipeMap.FakeRecipeMap) &&
						recipeMap.findRecipe(null, false, Long.MAX_VALUE, this.fluidInputs.toArray(new FluidStack[0]), this.inputs.toArray(new ItemStack[0])) != null,
				"Found recipe with same input (inputs: {}, fluid inputs: {}, recipe map: {}) as another one.",
				this.inputs, this.fluidInputs, recipeMap.unlocalizedName);

		return getThis();
	}

	public void buildAndRegister() {
		recipeMap.addRecipe(build());
	}

	///////////////////
	//    Getters    //
	///////////////////

	public List<ItemStack> getInputs() {
		return inputs;
	}

	public List<ItemStack> getOutputs() {
		return outputs;
	}

	public Map<ItemStack, Integer> getChancedOutputs() {
		return chancedOutputs;
	}

	public List<FluidStack> getFluidInputs() {
		return fluidInputs;
	}

	public List<FluidStack> getFluidOutputs() {
		return fluidOutputs;
	}

	public static class DefaultRecipeBuilder extends RecipeBuilder<Recipe, DefaultRecipeBuilder> {

		public DefaultRecipeBuilder() {
		}

		public DefaultRecipeBuilder(Recipe recipe, RecipeMap<Recipe, DefaultRecipeBuilder> recipeMap) {
			super(recipe, recipeMap);
		}

		public DefaultRecipeBuilder(RecipeBuilder<Recipe, DefaultRecipeBuilder> recipeBuilder) {
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

		public Recipe build() {
			finalizeAndValidate();
			return new Recipe(inputs, outputs, chancedOutputs, fluidInputs, fluidOutputs,
					duration, EUt, hidden, canBeBuffered, needsEmptyOutput);
		}
	}

	public static class IntCircuitRecipeBuilder extends RecipeBuilder<Recipe, IntCircuitRecipeBuilder> {

		protected int circuitMeta = -1;

		public IntCircuitRecipeBuilder() {
		}

		public IntCircuitRecipeBuilder(Recipe recipe, RecipeMap<Recipe, RecipeBuilder.IntCircuitRecipeBuilder> recipeMap) {
			super(recipe, recipeMap);
		}

		public IntCircuitRecipeBuilder(RecipeBuilder<Recipe, RecipeBuilder.IntCircuitRecipeBuilder> recipeBuilder) {
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

		public IntCircuitRecipeBuilder circuitMeta(int meta) {
			if (meta < 0) {
				throw new IllegalArgumentException("Integrated Circuit Metadata cannot be less than 0"); // TODO cannot be more than what?
			}

			this.circuitMeta = meta;
			return this;
		}

		@Override
		protected void finalizeAndValidate() {
			if (circuitMeta >= 0) {
				inputs.add(ItemList.Circuit_Integrated.getWithDamage(0, circuitMeta));
			}
			super.finalizeAndValidate();
		}

		public Recipe build() {
			finalizeAndValidate();
			return new Recipe(inputs, outputs, chancedOutputs, fluidInputs, fluidOutputs,
					duration, EUt, hidden, canBeBuffered, needsEmptyOutput);
		}
	}

	public static class NotConsumableInputRecipeBuilder extends RecipeBuilder<Recipe, NotConsumableInputRecipeBuilder> {

		public NotConsumableInputRecipeBuilder() {
		}

		public NotConsumableInputRecipeBuilder(Recipe recipe, RecipeMap<Recipe, NotConsumableInputRecipeBuilder> recipeMap) {
			super(recipe, recipeMap);
		}

		public NotConsumableInputRecipeBuilder(RecipeBuilder<Recipe, NotConsumableInputRecipeBuilder> recipeBuilder) {
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
			return notConsumable(item, 0);
		}

		public NotConsumableInputRecipeBuilder notConsumable(Item item, int metadata) {
			Validate.notNull(item, "Not consumable Item cannot be null");
			Validate.exclusiveBetween(0, Short.MAX_VALUE + 1, metadata);
			inputs.add(new ItemStack(item, 0, metadata));
			return this;
		}

		public NotConsumableInputRecipeBuilder notConsumable(ItemList item) {
			Validate.notNull(item, "Not consumable Item cannot be null");
			inputs.add(item.get(0));
			return this;
		}

		public Recipe build() {
			finalizeAndValidate();
			return new Recipe(inputs, outputs, chancedOutputs, fluidInputs, fluidOutputs,
					duration, EUt, hidden, canBeBuffered, needsEmptyOutput);
		}
	}


	public static class CellInputRecipeBuilder extends RecipeBuilder<Recipe, CellInputRecipeBuilder> {

		protected int cellAmount;
		protected int fuelCanAmount;

		public CellInputRecipeBuilder() {
		}

		public CellInputRecipeBuilder(Recipe recipe, RecipeMap<Recipe, CellInputRecipeBuilder> recipeMap) {
			super(recipe, recipeMap);
		}

		public CellInputRecipeBuilder(RecipeBuilder<Recipe, CellInputRecipeBuilder> recipeBuilder) {
			super(recipeBuilder);
		}

		@Override
		protected CellInputRecipeBuilder getThis() {
			return this;
		}

		@Override
		public CellInputRecipeBuilder copy() {
			return new CellInputRecipeBuilder(this);
		}

		public CellInputRecipeBuilder cellAmount(int emptyCellCount) {
			if (emptyCellCount <= 0) {
				throw new IllegalArgumentException("Cell amount cannot be less than or equal to 0");
			}

			this.cellAmount = emptyCellCount;
			return this;
		}

		public CellInputRecipeBuilder fuelCanAmount(int emptyCanCount) {
			if (emptyCanCount <= 0) {
				throw new IllegalArgumentException("Fuel Can amount cannot be less than or equal to 0");
			}

			this.fuelCanAmount = emptyCanCount;
			return this;
		}

		@Override
		protected void finalizeAndValidate() {
			if (fuelCanAmount > 0 && cellAmount > 0) {
				throw new IllegalArgumentException("Recipe cannot contain both cells and Fuel Cans inputs at the time");
			}

			if (cellAmount > 0) {
				inputs.add(ItemList.Cell_Empty.get(cellAmount));
			} else if (fuelCanAmount > 0) {
				inputs.add(ItemList.IC2_Fuel_Can_Empty.get(fuelCanAmount));
			}
			super.finalizeAndValidate();
		}

		public Recipe build() {
			finalizeAndValidate();
			return new Recipe(inputs, outputs, chancedOutputs, fluidInputs, fluidOutputs,
					duration, EUt, hidden, canBeBuffered, needsEmptyOutput);
		}
	}

	public static class BlastRecipeBuilder extends RecipeBuilder<Recipe.BlastRecipe, BlastRecipeBuilder> {

		private int blastFurnaceTemp;

		public BlastRecipeBuilder() {
		}

		public BlastRecipeBuilder(Recipe.BlastRecipe recipe, RecipeMap<Recipe.BlastRecipe, BlastRecipeBuilder> recipeMap) {
			super(recipe, recipeMap);
			this.blastFurnaceTemp = recipe.getBlastFurnaceTemp();
		}

		public BlastRecipeBuilder(RecipeBuilder<Recipe.BlastRecipe, BlastRecipeBuilder> recipeBuilder) {
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
				throw new IllegalArgumentException("Blast Furnace Temperature cannot be less than or equal to 0");
			}

			this.blastFurnaceTemp = blastFurnaceTemp;
			return getThis();
		}

		public Recipe.BlastRecipe build() {
			finalizeAndValidate();
			return new Recipe.BlastRecipe(inputs, outputs, chancedOutputs, fluidInputs, fluidOutputs,
					duration, EUt, hidden, canBeBuffered, needsEmptyOutput, blastFurnaceTemp);
		}
	}

	public static class ArcFurnaceRecipeBuilder extends RecipeBuilder<Recipe, ArcFurnaceRecipeBuilder> {

		protected boolean simple = false;

		public ArcFurnaceRecipeBuilder() {
		}

		public ArcFurnaceRecipeBuilder(Recipe recipe, RecipeMap<Recipe, ArcFurnaceRecipeBuilder> recipeMap) {
			super(recipe, recipeMap);
		}

		public ArcFurnaceRecipeBuilder(RecipeBuilder<Recipe, ArcFurnaceRecipeBuilder> recipeBuilder) {
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
			if (simple) { //addSimpleArcFurnaceRecipe
//				RecipeMap.ARC_FURNACE_RECIPES.addRecipe(true, new ItemStack[]{aInput}, aOutputs, null, aChances, new FluidStack[]{aFluidInput}, null, Math.max(1, aDuration), Math.max(1, aEUt), 0);
			} else { //addArcFurnaceRecipe
//				Recipe sRecipe = RecipeMap.ARC_FURNACE_RECIPES.addRecipe(true, new ItemStack[]{aInput}, aOutputs, null, aChances, new FluidStack[]{Materials.Oxygen.getGas(aDuration)}, null, Math.max(1, aDuration), Math.max(1, aEUt), 0);
//				if ((hidden) && (sRecipe != null)) {
//					sRecipe.hidden = true;
//				}
//				for (Materials tMaterial : new Materials[]{Materials.Argon, Materials.Nitrogen}) {
//					if (tMaterial. != null) {
//						int tPlasmaAmount = (int) Math.max(1L, aDuration / (tMaterial.getMass() * 16L));
//						Recipe tRecipe = RecipeMap.PLASMA_ARC_FURNACE_RECIPES.addRecipe(true, new ItemStack[]{aInput}, aOutputs, null, aChances, new FluidStack[]{tMaterial.getPlasma(tPlasmaAmount)}, new FluidStack[]{tMaterial.getGas(tPlasmaAmount)}, Math.max(1, aDuration / 16), Math.max(1, aEUt / 3), 0);
//						if ((hidden) && (tRecipe != null)) {
//							tRecipe.hidden = true;
//						}
//					}
//				}
			}
		}

		public Recipe build() {
			finalizeAndValidate();
			return new Recipe(inputs, outputs, chancedOutputs, fluidInputs, fluidOutputs,
					duration, EUt, hidden, canBeBuffered, needsEmptyOutput);
		}
	}

	public static class ImplosionRecipeBuilder extends RecipeBuilder<Recipe, ImplosionRecipeBuilder> {

		protected int explosivesAmount;

		public ImplosionRecipeBuilder() {
		}

		public ImplosionRecipeBuilder(Recipe recipe, RecipeMap<Recipe, ImplosionRecipeBuilder> recipeMap) {
			super(recipe, recipeMap);
		}

		public ImplosionRecipeBuilder(RecipeBuilder<Recipe, ImplosionRecipeBuilder> recipeBuilder) {
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
			Validate.inclusiveBetween(1, 64, explosivesAmount);

			this.explosivesAmount = explosivesAmount;
			return this;
		}

		@Override
		public void buildAndRegister() {
			int gunpowder = explosivesAmount * 2;
			int dynamite = explosivesAmount * 4;
			int TNT = Math.max(1, explosivesAmount / 2);
			int ITNT = Math.max(1, explosivesAmount / 4);

			ItemStack input = inputs.get(0);
			if (gunpowder < 65) {
				recipeMap.addRecipe(this.copy().inputs(input, ItemList.Block_Powderbarrel.get(gunpowder)).build());
			}
			if (dynamite < 17) {
				recipeMap.addRecipe(this.copy().inputs(input, ModHandler.getIC2Item(ItemName.dynamite, dynamite)).build());
			}
			recipeMap.addRecipe(this.copy().inputs(input, new ItemStack(Blocks.TNT, TNT)).build());
			recipeMap.addRecipe(this.copy().inputs(input, ModHandler.getIC2Item(BlockName.te, TeBlock.itnt, ITNT)).build());
		}

		public Recipe build() {
			finalizeAndValidate();
			return new Recipe(inputs, outputs, chancedOutputs, fluidInputs, fluidOutputs,
					duration, EUt, hidden, canBeBuffered, needsEmptyOutput);
		}
	}

	public static class UniversalDistillationRecipeBuilder extends RecipeBuilder<Recipe, UniversalDistillationRecipeBuilder> {

		protected boolean universal = false;

		public UniversalDistillationRecipeBuilder() {
		}

		public UniversalDistillationRecipeBuilder(Recipe recipe, RecipeMap<Recipe, UniversalDistillationRecipeBuilder> recipeMap) {
			super(recipe, recipeMap);
		}

		public UniversalDistillationRecipeBuilder(RecipeBuilder<Recipe, UniversalDistillationRecipeBuilder> recipeBuilder) {
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

		public Recipe build() {
			finalizeAndValidate();
			return new Recipe(inputs, outputs, chancedOutputs, fluidInputs, fluidOutputs,
					duration, EUt, hidden, canBeBuffered, needsEmptyOutput);
		}
	}

	public static class AmplifierRecipeBuilder extends RecipeBuilder<Recipe.AmplifierRecipe, AmplifierRecipeBuilder> {

		private int amplifierAmountOutputted = -1;

		public AmplifierRecipeBuilder() {}

		public AmplifierRecipeBuilder(Recipe.AmplifierRecipe recipe, RecipeMap<Recipe.AmplifierRecipe, AmplifierRecipeBuilder> recipeMap) {
			super(recipe, recipeMap);
		}

		public AmplifierRecipeBuilder(RecipeBuilder<Recipe.AmplifierRecipe, AmplifierRecipeBuilder> recipeBuilder) {
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
				throw new IllegalArgumentException("Outputted Amplifier Amount cannot be less than or equal to 0");
			}

			this.amplifierAmountOutputted = amplifierAmountOutputted;
			return getThis();
		}

		@Override
		protected void finalizeAndValidate() {
			if (amplifierAmountOutputted >= 0) {
				this.fluidOutputs(Materials.UUAmplifier.getFluid(amplifierAmountOutputted));
			}
			super.finalizeAndValidate();
		}

		public Recipe.AmplifierRecipe build() {
			finalizeAndValidate();
			return new Recipe.AmplifierRecipe(inputs, outputs, chancedOutputs, fluidInputs, fluidOutputs,
					duration, EUt, hidden, canBeBuffered, needsEmptyOutput, amplifierAmountOutputted);
		}
	}

	public static class BrewingRecipeBuilder extends RecipeBuilder<Recipe, BrewingRecipeBuilder> {

		public BrewingRecipeBuilder() {}

		public BrewingRecipeBuilder(Recipe recipe, RecipeMap<Recipe, BrewingRecipeBuilder> recipeMap) {
			super(recipe, recipeMap);
		}

		public BrewingRecipeBuilder(RecipeBuilder<Recipe, BrewingRecipeBuilder> recipeBuilder) {
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
			Validate.notNull(inputs, "Fluid input cannot be null");

			this.fluidInputs.clear();
			this.fluidInputs.add(new FluidStack(input, 750));

			return getThis();
		}

		public BrewingRecipeBuilder fluidOutput(@Nonnull Fluid output) {
			Validate.notNull(inputs, "Fluid output cannot be null");

			this.fluidOutputs.clear();
			this.fluidOutputs.add(new FluidStack(output, 750));

			return getThis();
		}

		public Recipe build() {
			finalizeAndValidate();
			return new Recipe(inputs, outputs, chancedOutputs, fluidInputs, fluidOutputs,
					duration, EUt, hidden, canBeBuffered, needsEmptyOutput);
		}
	}

	public static class FusionRecipeBuilder extends RecipeBuilder<Recipe.FusionRecipe, FusionRecipeBuilder> {

		private int EUToStart;

		public FusionRecipeBuilder() {}

		public FusionRecipeBuilder(Recipe.FusionRecipe recipe, RecipeMap<Recipe.FusionRecipe, FusionRecipeBuilder> recipeMap) {
			super(recipe, recipeMap);
			this.EUToStart = recipe.getEUToStart();

		}

		public FusionRecipeBuilder(RecipeBuilder<Recipe.FusionRecipe, FusionRecipeBuilder> recipeBuilder) {
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
				throw new IllegalArgumentException("EU to start cannot be less than or equal to 0");
			}

			this.EUToStart = EUToStart;
			return getThis();
		}

		public Recipe.FusionRecipe build() {
			finalizeAndValidate();
			return new Recipe.FusionRecipe(inputs, outputs, chancedOutputs, fluidInputs, fluidOutputs,
					duration, EUt, hidden, canBeBuffered, needsEmptyOutput, EUToStart);
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

		public AssemblyLineRecipeBuilder() {
		}

		public AssemblyLineRecipeBuilder researchItem(ItemStack researchItem) {
			Validate.notNull(researchItem, "Research Item array cannot be null");
			this.researchItem = researchItem;
			return this;
		}

		public AssemblyLineRecipeBuilder researchTime(int researchTime) {
			if (researchTime <= 0) {
				throw new IllegalArgumentException("Research Time cannot be less or equal to 0");
			}

			this.researchTime = researchTime;
			return this;
		}

		public AssemblyLineRecipeBuilder inputs(@Nonnull ItemStack... inputs) {
			Validate.notNull(inputs, "Input array cannot be null");
			if (inputs.length != 0) {
				Validate.noNullElements(inputs, "Input cannot contain null ItemStacks");

				Collections.addAll(this.inputs, inputs);
			}
			return this;
		}

		public AssemblyLineRecipeBuilder fluidInputs(@Nonnull FluidStack... inputs) {
			Validate.notNull(inputs, "Input array cannot be null");
			if (inputs.length != 0) {
				Validate.noNullElements(inputs, "Fluid input cannot contain null FluidStacks");

				Collections.addAll(this.fluidInputs, inputs);
			}
			return this;
		}

		public AssemblyLineRecipeBuilder output(ItemStack output) {
			Validate.notNull(output, "Output ItemStack cannot be null");
			this.output = output;
			return this;
		}

		public AssemblyLineRecipeBuilder duration(int duration) {
			Validate.isTrue(duration > 0, "Duration cannot be less or equal to 0");

			this.duration = duration;
			return this;
		}

		public AssemblyLineRecipeBuilder EUt(int EUt) {
			Validate.isTrue(EUt > 0, "EUt cannot be less or equal to 0");

			this.EUt = EUt;
			return this;
		}

		public Recipe.AssemblyLineRecipe build() {
			validate();
			return new Recipe.AssemblyLineRecipe(researchItem, researchTime, inputs, fluidInputs, output, duration, EUt);
		}

		protected void validate() {
			Validate.isTrue(researchTime > 0, "Research Time cannot be less or equal to 0");
			Validate.isTrue(duration > 0, "Duration cannot be less or equal to 0");
			Validate.isTrue(EUt > 0, "EUt cannot be less or equal to 0");

			Validate.notNull(researchItem, "Research Item array cannot be null");

			Validate.inclusiveBetween(4, 16, inputs.size());
			Validate.inclusiveBetween(0, 4, fluidInputs.size());
			Validate.notNull(output, "Output ItemStack cannot be null");
		}

		public void buildAndRegister() {
			RecipeMap.ASSEMBLYLINE_RECIPES.add(build());
		}
	}
}
