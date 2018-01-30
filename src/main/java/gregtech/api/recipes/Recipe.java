package gregtech.api.recipes;

import com.google.common.collect.ImmutableList;
import gnu.trove.impl.unmodifiable.TUnmodifiableObjectIntMap;
import gnu.trove.map.TObjectIntMap;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.util.GTUtility;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
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

	private final NonNullList<ItemStack> inputs;
	private final NonNullList<ItemStack> outputs;

	/**
	 * A chance of 10000 equals 100%
	 */
	private final TObjectIntMap<ItemStack> chancedOutputs;

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

    private final Map<String, Object> recipeProperties;

	protected Recipe(List<ItemStack> inputs, List<ItemStack> outputs, TObjectIntMap<ItemStack> chancedOutputs,
                     List<FluidStack> fluidInputs, List<FluidStack> fluidOutputs,
                     Map<String, Object> recipeProperties, int duration, int EUt, boolean hidden, boolean canBeBuffered, boolean needsEmptyOutput) {
        this.recipeProperties = recipeProperties;
        this.inputs = NonNullList.create();
		this.inputs.addAll(inputs);
		this.outputs = NonNullList.create();
		this.outputs.addAll(outputs);
		this.chancedOutputs = new TUnmodifiableObjectIntMap<>(chancedOutputs);
		this.fluidInputs = ImmutableList.copyOf(fluidInputs);
		this.fluidOutputs = ImmutableList.copyOf(fluidOutputs);
		this.duration = duration;
		this.EUt = EUt;
		this.hidden = hidden;
		this.canBeBuffered = canBeBuffered;
		this.needsEmptyOutput = needsEmptyOutput;
	}

	public boolean isRecipeInputEqual(boolean decreaseStacksizeBySuccess, boolean dontCheckStackSizes, IItemHandlerModifiable inputs, IMultipleTankHandler fluidInputs) {
        List<FluidStack> fluidStacks = new ArrayList<>(fluidInputs.getTanks());
        for (int i = 0; i < fluidInputs.getTanks(); i++) {
            fluidStacks.add(fluidInputs.getFluidInTank(i));
        }
        NonNullList<ItemStack> stacks = NonNullList.create();
        for (int i = 0; i < inputs.getSlots(); i++) {
            stacks.add(inputs.getStackInSlot(i));
        }
		return isRecipeInputEqual(decreaseStacksizeBySuccess, dontCheckStackSizes, stacks, fluidStacks);
	}

	public boolean isRecipeInputEqual(boolean decreaseStacksizeBySuccess, NonNullList<ItemStack> inputs, List<FluidStack> fluidInputs) {
		return isRecipeInputEqual(decreaseStacksizeBySuccess, false, inputs, fluidInputs);
	}

	public boolean isRecipeInputEqual(boolean decreaseStacksizeBySuccess, boolean dontCheckStackSizes, NonNullList<ItemStack> inputs, List<FluidStack> fluidInputs) {
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
			amount = stackInput.getCount();
			boolean temp = true;
			for (ItemStack stack : inputs) {
				if ((GTUtility.areUnificationEqual(stack, stackInput)
						|| GTUtility.areUnificationEqual(OreDictUnifier.getUnificated(stack), stackInput))) {
					if (dontCheckStackSizes) {
						temp = false;
						break;
					}
					amount -= stack.getCount();
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
					amount = stack.getCount();
					for (ItemStack tmpStack : inputs) {
						if ((GTUtility.areUnificationEqual(tmpStack, stack)
								|| GTUtility.areUnificationEqual(OreDictUnifier.getUnificated(tmpStack), stack))) {
							if (dontCheckStackSizes) {
								tmpStack.shrink(amount);
								break;
							}
							if (tmpStack.getCount() < amount) {
								amount -= tmpStack.getCount();
								tmpStack.setCount(0);
							} else {
								tmpStack.shrink(amount);
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

	public NonNullList<ItemStack> getInputs() {
		return inputs;
	}

	public NonNullList<ItemStack> getOutputs() {
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
	
	public boolean getBooleanProperty(String key) {
        Validate.notNull(key);
        Object o = this.recipeProperties.get(key);
        if (!(o instanceof Boolean)) {
            throw new IllegalArgumentException();
        }
        return (boolean) o;
    }

    public int getIntegerProperty(String key) {
        Validate.notNull(key);
        Object o = this.recipeProperties.get(key);
        if (!(o instanceof Integer)) {
            throw new IllegalArgumentException();
        }
        return (int) o;
    }

    @SuppressWarnings("unchecked")
    public <T> T getProperty(String key, Class<T> targetClass) {
        Validate.notNull(key);
        Object o = this.recipeProperties.get(key);
        if (o == null) {
            throw new IllegalArgumentException();
        }
        return (T) o;
    }

	public String getStringProperty(String key) {
        Validate.notNull(key);
        Object o = this.recipeProperties.get(key);
        if (!(o instanceof String)) {
            throw new IllegalArgumentException();
        }
        return (String) o;
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
			this.inputs = ImmutableList.copyOf(GTUtility.copyStackList(inputs));
			this.fluidInputs = ImmutableList.copyOf(GTUtility.copyFluidList(fluidInputs));
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
