package gregtech.api.recipes;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import gregtech.api.enums.ItemList;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import org.apache.commons.lang3.Validate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static gregtech.api.enums.GT_Values.W;

/**
 * Class that represent machine recipe.<p>
 *
 * Recipes are created using {@link RecipeBuilder} or its subclasses in builder-alike pattern. To get RecipeBuilder use {@link RecipeMap#recipeBuilder()}.<p>
 *
 * Example:
 * 		RecipeMap.POLARIZER_RECIPES.recipeBuilder().inputs(new ItemStack(Items.APPLE)).outputs(new ItemStack(Items.GOLDEN_APPLE)).duration(256).EUt(480).buildAndRegister();<p>
 * 	This will create and register Polarizer recipe with Apple as input and Golden apple as output, duration - 256 ticks and energy consumption of 480 EU/t.<p>
 *	To get example for particular RecipeMap see {@link RecipeMap}<p>
 *
 * Recipes are immutable.
 */
//TODO CraftTweaker support
public class Recipe {

	private final List<ItemStack> inputs;
	private final List<ItemStack> outputs;

	/**
	 * A chance of 10000 equals 100%
	 */
	private final Map<ItemStack, Integer> chancedOutputs;

	private final List<FluidStack> fluidInputs;
	private final List<FluidStack> fluidOutputs;

	/**
	 * An Item that needs to be inside the Special Slot, like for example the Copy Slot inside the Printer.
	 * This is only useful for Fake Recipes in JEI, since findRecipe() and containsInput() don't care about this field.
	 */
	@Nullable
	private final ItemStack specialItem;

	private final int duration;

	/**
	 * if > 0 means EU/t consumed, if < 0 - produced
	 */
	private final int EUt;

	/**
	 * If this Recipe is hidden from JEI
	 */
	private final boolean hidden;

	/**
	 * If this Recipe can be stored inside a Machine in order to make Recipe searching more efficient
	 * by trying the previously used Recipe first. In case you have a Recipe Map overriding things
	 * and returning one time use Recipes, you have to set this to false.
	 */
	private final boolean canBeBuffered;
	/**
	 * If this Recipe needs the Output Slots to be completely empty. Needed in case you have randomised Outputs
	 */
	private final boolean needsEmptyOutput;

	private Recipe(List<ItemStack> inputs, List<ItemStack> outputs, Map<ItemStack, Integer> chancedOutputs,
				   List<FluidStack> fluidInputs, List<FluidStack> fluidOutputs, ItemStack specialItem,
				   int duration, int EUt, boolean hidden, boolean canBeBuffered, boolean needsEmptyOutput) {
		this.inputs = ImmutableList.copyOf(inputs);
		this.outputs = ImmutableList.copyOf(outputs);
		this.chancedOutputs = ImmutableMap.copyOf(chancedOutputs);
		this.fluidInputs = ImmutableList.copyOf(fluidInputs);
		this.fluidOutputs = ImmutableList.copyOf(fluidOutputs);
		this.specialItem = specialItem;
		this.duration = duration;
		this.EUt = EUt;
		this.hidden = hidden;
		this.canBeBuffered = canBeBuffered;
		this.needsEmptyOutput = needsEmptyOutput;
	}

//    public ItemStack getRepresentativeInput(int aIndex) {
//        if (aIndex < 0 || aIndex >= inputs.length) return null;
//        return GT_Utility.copy(inputs[aIndex]);
//    }
//
//    public ItemStack getJEIAdaptedInput(int aIndex) {
//        ItemStack input = getRepresentativeInput(aIndex);
//        if(input != null && input.stackSize == 0) {
//            input.stackSize = 1;
//        }
//        return input;
//    }
//
//
//    public ItemStack getOutput(int aIndex) {
//        if (aIndex < 0 || aIndex >= outputs.length) return null;
//        return GT_Utility.copy(outputs[aIndex]);
//    }
//
//    public int getOutputChance(int aIndex) {
//        if (aIndex < 0 || aIndex >= chances.length) return 10000;
//        return chances[aIndex];
//    }
//
//    public FluidStack getRepresentativeFluidInput(int aIndex) {
//        if (aIndex < 0 || aIndex >= fluidInputs.length || fluidInputs[aIndex] == null) return null;
//        return fluidInputs[aIndex].copy();
//    }
//
//    public FluidStack getFluidOutput(int aIndex) {
//        if (aIndex < 0 || aIndex >= fluidOutputs.length || fluidOutputs[aIndex] == null) return null;
//        return fluidOutputs[aIndex].copy();
//    }
//
//    public void checkCellBalance() {
//        if (!D1 || inputs.length < 1) return;
//
//        int tInputAmount = GT_ModHandler.getCapsuleCellContainerCountMultipliedWithStackSize(inputs);
//        int tOutputAmount = GT_ModHandler.getCapsuleCellContainerCountMultipliedWithStackSize(outputs);
//
//        if (tInputAmount < tOutputAmount) {
//            if (!Materials.Tin.contains(inputs)) {
//                GT_Log.err.println("You get more Cells, than you put in? There must be something wrong.");
//                new Exception().printStackTrace(GT_Log.err);
//            }
//        } else if (tInputAmount > tOutputAmount) {
//            if (!Materials.Tin.contains(outputs)) {
//                GT_Log.err.println("You get less Cells, than you put in? GT Machines usually don't destroy Cells.");
//                new Exception().printStackTrace(GT_Log.err);
//            }
//        }
//    }

	public boolean isRecipeInputEqual(boolean decreaseStacksizeBySuccess, FluidStack[] fluidInputs, ItemStack[] inputs) {
		return isRecipeInputEqual(decreaseStacksizeBySuccess, false, fluidInputs, inputs);
	}

	//TODO check this
	public boolean isRecipeInputEqual(boolean decreaseStacksizeBySuccess, boolean dontCheckStackSizes, FluidStack[] fluidInputs, ItemStack[] inputs) {
		if (this.fluidInputs.size() > 0 && fluidInputs == null) return false;
		int amount;
		for (FluidStack fluidInput : this.fluidInputs) {
			if (fluidInput != null) {
				boolean temp = true;
				amount = fluidInput.amount;
				for (FluidStack fluid : fluidInputs) {
					if (fluid != null && fluid.isFluidEqual(fluidInput)) {
						if (dontCheckStackSizes) {
							temp = false;
							break;
						}
						amount -= fluid.amount;
						if (amount < 1) {
							temp = false;
							break;
						}
					}
				}
				if (temp) {
					return false;
				}
			}
		}

		if (this.inputs.size() > 0 && inputs == null) {
			return false;
		}

		for (ItemStack stackInput : this.inputs) {
			if (stackInput != null) {
				amount = stackInput.stackSize;
				boolean temp = true;
				for (ItemStack stack : inputs) {
					if ((GT_Utility.areUnificationsEqual(stack, stackInput, true) || GT_Utility.areUnificationsEqual(GT_OreDictUnificator.get(false, stack), stackInput, true))) {
						if (dontCheckStackSizes) {
							temp = false;
							break;
						}
						amount -= stack.stackSize;
						if (amount < 1) {
							temp = false;
							break;
						}
					}
				}
				if (temp) {
					return false;
				}
			}
		}
		if (decreaseStacksizeBySuccess) {
			if (fluidInputs != null) {
				for (FluidStack fluid : this.fluidInputs) {
					if (fluid != null) {
						amount = fluid.amount;
						for (FluidStack tmpFluid : fluidInputs) {
							if (tmpFluid != null && tmpFluid.isFluidEqual(fluid)) {
								if (dontCheckStackSizes) {
									tmpFluid.amount -= amount;
									break;
								}
								if (tmpFluid.amount < amount) {
									amount -= tmpFluid.amount;
									tmpFluid.amount = 0;
								} else {
									tmpFluid.amount -= amount;
									amount = 0;
									break;
								}
							}
						}
					}
				}
			}

			if (inputs != null) {
				for (ItemStack stack : this.inputs) {
					if (stack != null) {
						amount = stack.stackSize;
						for (ItemStack tmpStack : inputs) {
							if ((GT_Utility.areUnificationsEqual(tmpStack, stack, true) || GT_Utility.areUnificationsEqual(GT_OreDictUnificator.get(false, tmpStack), stack, true))) {
								if (dontCheckStackSizes) {
									tmpStack.stackSize -= amount;
									break;
								}
								if (tmpStack.stackSize < amount) {
									amount -= tmpStack.stackSize;
									tmpStack.stackSize = 0;
								} else {
									tmpStack.stackSize -= amount;
									amount = 0;
									break;
								}
							}
						}
					}
				}
			}
		}

		return true;
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

	@Nullable
	public Object getSpecialItem() {
		return specialItem;
	}

	public int getDuration() {
		return duration;
	}

	public int getEUt() {
		return EUt;
	}

	public boolean isHidden() {
		return hidden;
	}

	public boolean canBeBuffered() {
		return canBeBuffered;
	}

	public boolean doesNeedEmptyOutput() {
		return needsEmptyOutput;
	}

	public static abstract class RecipeBuilder<E extends Recipe, R extends RecipeBuilder<E,R>> {

		@Nullable
		protected RecipeMap<?,?> recipeMap;

		protected List<ItemStack> inputs = new ArrayList<>(0);
		protected List<ItemStack> outputs = new ArrayList<>(0);
		protected Map<ItemStack, Integer> chancedOutputs = new HashMap<>(0);

		protected List<FluidStack> fluidInputs = new ArrayList<>(0);
		protected List<FluidStack> fluidOutputs = new ArrayList<>(0);

		protected ItemStack specialItem;

		protected int duration, EUt;

		protected boolean hidden = false;

		protected boolean canBeBuffered = true;

		protected boolean needsEmptyOutput = false;

		protected boolean optimized = true;

		public RecipeBuilder() {
			this.inputs = new ArrayList<>(0);
			this.outputs = new ArrayList<>(0);
			this.chancedOutputs = new HashMap<>(0);

			this.fluidInputs = new ArrayList<>(0);
			this.fluidOutputs = new ArrayList<>(0);
		}

		public RecipeBuilder(Recipe recipe) {
			this.inputs = new ArrayList<>(recipe.inputs);
			this.outputs = new ArrayList<>(recipe.outputs);
			this.chancedOutputs = new HashMap<>(recipe.chancedOutputs);
			this.fluidInputs = new ArrayList<>(recipe.fluidInputs);
			this.fluidOutputs = new ArrayList<>(recipe.fluidOutputs);

			this.specialItem = recipe.specialItem;
			this.duration = recipe.duration;
			this.EUt = recipe.EUt;
			this.hidden = recipe.hidden;
			this.canBeBuffered = recipe.canBeBuffered;
			this.needsEmptyOutput = recipe.needsEmptyOutput;
		}

		public RecipeBuilder(RecipeBuilder<?,?> recipeBuilder) {
			this.recipeMap = recipeBuilder.recipeMap;
			this.inputs = new ArrayList<>(recipeBuilder.inputs);
			this.outputs = new ArrayList<>(recipeBuilder.outputs);
			this.chancedOutputs = new HashMap<>(recipeBuilder.chancedOutputs);
			this.fluidInputs = new ArrayList<>(recipeBuilder.fluidInputs);
			this.fluidOutputs = new ArrayList<>(recipeBuilder.fluidOutputs);
			this.specialItem = recipeBuilder.specialItem;
			this.duration = recipeBuilder.duration;
			this.EUt = recipeBuilder.EUt;
			this.hidden = recipeBuilder.hidden;
			this.canBeBuffered = recipeBuilder.canBeBuffered;
			this.needsEmptyOutput = recipeBuilder.needsEmptyOutput;
			this.optimized = recipeBuilder.optimized;
		}

		/**
		 * Calling this method second time will override existing item inputs
		 */
		public R inputs(@Nonnull ItemStack... inputs) {
			Validate.notNull(inputs, "Input array cannot be null");
			Validate.noNullElements(inputs, "Input cannot contain null ItemStacks");

			this.inputs.clear();

			Collections.addAll(this.inputs, inputs);
			return getThis();
		}

		/**
		 * Calling this method second time will override existing item outputs
		 */
		public R outputs(@Nonnull ItemStack... outputs) {
			Validate.notNull(outputs, "Input array cannot be null");
			Validate.noNullElements(outputs, "Output cannot contain null ItemStacks");

			this.outputs.clear();

			Collections.addAll(this.outputs, outputs);
			return getThis();
		}

		/**
		 * Calling this method second time will override existing fluid inputs
		 */
		public R fluidInputs(@Nonnull FluidStack... inputs) {
			Validate.notNull(inputs, "Input array cannot be null");
			Validate.noNullElements(inputs, "Fluid input cannot contain null FluidStacks");

			this.fluidInputs.clear();

			Collections.addAll(this.fluidInputs, inputs);
			return getThis();
		}

		/**
		 * Calling this method second time will override existing fluid outputs
		 */
		public R fluidOutputs(@Nonnull FluidStack... outputs) {
			Validate.notNull(outputs, "Input array cannot be null");
			Validate.noNullElements(outputs, "Fluid output cannot contain null FluidStacks");

			this.fluidOutputs.clear();

			Collections.addAll(this.fluidOutputs, outputs);
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

		public R clearChancedOutputs() {
			this.chancedOutputs.clear();
			return getThis();
		}

		public R specialItem(ItemStack specialItem) {
			this.specialItem = specialItem;
			return getThis();
		}

		public R duration(int duration) {
			if (duration <= 0) {
				throw new IllegalArgumentException("Duration cannot be less or equal to 0");
			}

			this.duration = duration;
			return getThis();
		}

		public R EUt(int EUt) {
//			if (EUt <= 0) {
//				throw new IllegalArgumentException("EUt cannot be less or equal to 0");
//			}

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

		public R setRecipeMap(RecipeMap<?,?> recipeMap) {
			this.recipeMap = recipeMap;
			return getThis();
		}

		public abstract R copy();

		// To get rid of "unchecked cast" warning
		protected abstract R getThis();

		// todo better name?
		protected void fill() {
//			GT_OreDictUnificator.setStackArray(true, inputs);
//			GT_OreDictUnificator.setStackArray(true, outputs);
//			GT_OreDictUnificator.setStackArray(true, chancedOutputs);

//			for (ItemStack tStack : outputs) GT_Utility.updateItemStack(tStack);

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
		}

		public Recipe build() {
			fill();
			return new Recipe(inputs, outputs, chancedOutputs, fluidInputs, fluidOutputs,
					specialItem, duration, EUt, hidden, canBeBuffered, needsEmptyOutput);
		}

		protected R validate() {
			Validate.exclusiveBetween(recipeMap.getMinInputs(), recipeMap.getMaxInputs(), inputs.size());
			Validate.exclusiveBetween(recipeMap.getMinOutputs(), recipeMap.getMaxOutputs(), outputs.size() + chancedOutputs.size());
			Validate.exclusiveBetween(recipeMap.getMinFluidInputs(), recipeMap.getMaxFluidInputs(), fluidInputs.size());
			Validate.exclusiveBetween(recipeMap.getMinFluidOutputs(), recipeMap.getMaxFluidOutputs(), fluidOutputs.size());

			//For fakeRecipes don't do check for collisions, regular recipes do check, do not check for recipes that are not registered(i.e. created after postinit stage)
			Validate.isTrue(!(recipeMap instanceof RecipeMap.FakeRecipeMap) &&
							recipeMap.findRecipe(null, false, Long.MAX_VALUE, this.fluidInputs.toArray(new FluidStack[0]), this.inputs.toArray(new ItemStack[0])) != null,
					"Found recipe with same input (inputs: {}, fluid inputs: {}, recipe map: {}) as another one.",
					this.inputs, this.fluidInputs, recipeMap.unlocalizedName);

			return getThis();
		}

		public void buildAndRegister() {
			recipeMap.addRecipe(validate().build());
		}
	}

	public static class DefaultRecipeBuilder extends RecipeBuilder<Recipe, DefaultRecipeBuilder> {

		public DefaultRecipeBuilder() {}

		public DefaultRecipeBuilder(Recipe recipe) {
			super(recipe);
		}

		public DefaultRecipeBuilder(RecipeBuilder<?,?> recipeBuilder) {
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
	}

//	RecipeMap.BENDER_RECIPES.recipeBuilder().inputs(new ItemStack(Items.APPLE)).circuitMeta(1).outputs(new ItemStack(Items.GOLDEN_APPLE)).buildAndRegister();
	public static class IntCircuitRecipeBuilder extends RecipeBuilder<Recipe, IntCircuitRecipeBuilder> {

		protected int circuitMeta;

		public IntCircuitRecipeBuilder() {
		}

		public IntCircuitRecipeBuilder(Recipe recipe) {
			super(recipe);
		}

		public IntCircuitRecipeBuilder(RecipeBuilder<?,?> recipeBuilder) {
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
		protected void fill() {
			inputs.add(ItemList.Circuit_Integrated.getWithDamage(0, circuitMeta));
			super.fill();
		}
	}

	//	RecipeMap.EXTRUDER_RECIPES.recipeBuilder().inputs(new ItemStack(Items.APPLE)).notConsumable(ItemList.Shape_Extruder_Ring).outputs(new ItemStack(Items.GOLDEN_APPLE)).buildAndRegister();
	public static class NotConsumableInputRecipeBuilder extends RecipeBuilder<Recipe, NotConsumableInputRecipeBuilder> {

		public NotConsumableInputRecipeBuilder() {
		}

		public NotConsumableInputRecipeBuilder(Recipe recipe) {
			super(recipe);
		}

		public NotConsumableInputRecipeBuilder(RecipeBuilder<?,?> recipeBuilder) {
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
			Validate.exclusiveBetween(0, Short.MAX_VALUE, metadata, "Metadata cannot be less or equal to 0 or more than Short.MAX_VALUE");
			inputs.add(new ItemStack(item, 0, metadata));
			return this;
		}

		public NotConsumableInputRecipeBuilder notConsumable(ItemList item) {
			Validate.notNull(item, "Not consumable Item cannot be null");
			inputs.add(item.get(0));
			return this;
		}
	}

	public static class GT_Recipe_AssemblyLine {
		public static final ArrayList<GT_Recipe_AssemblyLine> ASSEMBLYLINE_RECIPES = new ArrayList<>();

		public ItemStack researchItem;
		public int researchTime;
		public ItemStack[] inputs;
		public FluidStack[] fluidInputs;
		public ItemStack output;
		public int duration;
		public int EUt;

		public GT_Recipe_AssemblyLine(ItemStack aResearchItem, int aResearchTime, ItemStack[] aInputs, FluidStack[] aFluidInputs, ItemStack aOutput, int aDuration, int aEUt) {
			researchItem = aResearchItem;
			researchTime = aResearchTime;
			inputs = aInputs;
			fluidInputs = aFluidInputs;
			output = aOutput;
			duration = aDuration;
			EUt = aEUt;
		}

	}

	//TODO delete these
	public static class DistillationTowerRecipe extends Recipe {

		public DistillationTowerRecipe(FluidStack aInput, FluidStack[] aOutputs, ItemStack aOutput2, int aDuration, int aEUt) {
			super(false, null, new ItemStack[]{aOutput2}, null, null, new FluidStack[]{aInput}, aOutputs, Math.max(1, aDuration), Math.max(1, aEUt), 0);

			if (inputs.length > 0 && outputs[0] != null) {
				RecipeMap.DISTILLATION_RECIPES.addRecipe(this);
			}
		}
	}

	public static class ImplosionRecipe extends Recipe {

		public ImplosionRecipe(ItemStack aInput1, ItemStack aInput2, ItemStack aOutput1, ItemStack aOutput2) {
			super(true, new ItemStack[]{aInput1, aInput2}, new ItemStack[]{aOutput1, aOutput2}, null, null, null, null, 20, 30, 0);
			if (inputs.length > 0 && outputs[0] != null) {
				RecipeMap.IMPLOSION_RECIPES.addRecipe(this);
			}
		}
	}
}
