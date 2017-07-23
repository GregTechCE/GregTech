package gregtech.api.recipes;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

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

	protected Recipe(List<ItemStack> inputs, List<ItemStack> outputs, Map<ItemStack, Integer> chancedOutputs,
				   List<FluidStack> fluidInputs, List<FluidStack> fluidOutputs,
				   int duration, int EUt, boolean hidden, boolean canBeBuffered, boolean needsEmptyOutput) {
		this.inputs = ImmutableList.copyOf(inputs);
		this.outputs = ImmutableList.copyOf(outputs);
		this.chancedOutputs = ImmutableMap.copyOf(chancedOutputs);
		this.fluidInputs = ImmutableList.copyOf(fluidInputs);
		this.fluidOutputs = ImmutableList.copyOf(fluidOutputs);
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

	public boolean isRecipeInputEqual(boolean decreaseStacksizeBySuccess, boolean dontCheckStackSizes, FluidStack[] fluidInputs, ItemStack[] inputs) {
		if (this.fluidInputs.size() > 0 && fluidInputs == null) return false;
		int amount;
		for (FluidStack fluidInput : this.fluidInputs) {
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

		if (this.inputs.size() > 0 && inputs == null) {
			return false;
		}

		for (ItemStack stackInput : this.inputs) {
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
		if (decreaseStacksizeBySuccess) {
			if (fluidInputs != null) {
				for (FluidStack fluid : this.fluidInputs) {
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

			if (inputs != null) {
				for (ItemStack stack : this.inputs) {
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

	public boolean needsEmptyOutput() {
		return needsEmptyOutput;
	}

	public static class BlastRecipe extends Recipe {
		private final int blastFurnaceTemp;

		protected BlastRecipe(List<ItemStack> inputs, List<ItemStack> outputs, Map<ItemStack, Integer> chancedOutputs, List<FluidStack> fluidInputs, List<FluidStack> fluidOutputs, int duration, int EUt, boolean hidden, boolean canBeBuffered, boolean needsEmptyOutput, int blastFurnaceTemp) {
			super(inputs, outputs, chancedOutputs, fluidInputs, fluidOutputs, duration, EUt, hidden, canBeBuffered, needsEmptyOutput);

			this.blastFurnaceTemp = blastFurnaceTemp;
		}

		public int getBlastFurnaceTemp() {
			return blastFurnaceTemp;
		}
	}

	public static class AmplifierRecipe extends Recipe {
		private final int amplifierAmountOutputted;

		protected AmplifierRecipe(List<ItemStack> inputs, List<ItemStack> outputs, Map<ItemStack, Integer> chancedOutputs, List<FluidStack> fluidInputs, List<FluidStack> fluidOutputs, int duration, int EUt, boolean hidden, boolean canBeBuffered, boolean needsEmptyOutput, int amplifierAmountOutputted) {
			super(inputs, outputs, chancedOutputs, fluidInputs, fluidOutputs, duration, EUt, hidden, canBeBuffered, needsEmptyOutput);

			this.amplifierAmountOutputted = amplifierAmountOutputted;
		}

		public int getAmplifierAmountOutputted() {
			return amplifierAmountOutputted;
		}
	}

	public static class FusionRecipe extends Recipe {
		private final int EUToStart;

		protected FusionRecipe(List<ItemStack> inputs, List<ItemStack> outputs, Map<ItemStack, Integer> chancedOutputs, List<FluidStack> fluidInputs, List<FluidStack> fluidOutputs, int duration, int EUt, boolean hidden, boolean canBeBuffered, boolean needsEmptyOutput, int EUToStart) {
			super(inputs, outputs, chancedOutputs, fluidInputs, fluidOutputs, duration, EUt, hidden, canBeBuffered, needsEmptyOutput);

			this.EUToStart = EUToStart;
		}

		public int getEUToStart() {
			return EUToStart;
		}
	}

	public static class AssemblyLineRecipe {

		private final ItemStack researchItem;
		private final int researchTime;

		private final List<ItemStack> inputs;
		private final List<FluidStack> fluidInputs;
		private final ItemStack output;

		private final int duration;
		private final int EUt;

		public AssemblyLineRecipe(ItemStack researchItem, int researchTime, List<ItemStack> inputs, List<FluidStack> fluidInputs, ItemStack output, int duration, int EUt) {
			this.researchItem = researchItem.copy();
			this.researchTime = researchTime;
			this.inputs = ImmutableList.copyOf(GT_Utility.copyStackList(inputs));
			this.fluidInputs = ImmutableList.copyOf(GT_Utility.copyFluidList(fluidInputs));
			this.output = output;
			this.duration = duration;
			this.EUt = EUt;
		}

		public ItemStack getResearchItem() {
			return researchItem;
		}

		public int getResearchTime() {
			return researchTime;
		}

		public List<ItemStack> getInputs() {
			return inputs;
		}

		public List<FluidStack> getFluidInputs() {
			return fluidInputs;
		}

		public ItemStack getOutput() {
			return output;
		}

		public int getDuration() {
			return duration;
		}

		public int getEUt() {
			return EUt;
		}
	}
}
