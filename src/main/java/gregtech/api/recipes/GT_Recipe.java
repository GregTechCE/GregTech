package gregtech.api.recipes;

import gregtech.api.enums.ItemList;
import gregtech.api.util.GT_Log;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import org.apache.commons.lang3.Validate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This File contains the functions used for Recipes. Please do not include this File AT ALL in your Moddownload as it ruins compatibility
 * This is just the Core of my Recipe System, if you just want to GET the Recipes I add, then you can access this File.
 * Do NOT add Recipes using the Constructors inside this Class, The GregTech_API File calls the correct Functions for these Constructors.
 * <p>
 * TODO CraftTweaker support
 */
public class GT_Recipe {
	/**
	 * If you want to change the Output, feel free to modify or even replace the whole ItemStack Array, for Inputs, please add a new Recipe, because of the HashMaps.
	 */
	@Nonnull
	public final ItemStack[] inputs, outputs;

	/**
	 * If you want to change the Output, feel free to modify or even replace the whole ItemStack Array, for Inputs, please add a new Recipe, because of the HashMaps.
	 */
	@Nonnull
	public final FluidStack[] fluidInputs, fluidOutputs;

	/**
	 * If you changed the amount of Array-Items inside the Output Array then the length of this Array must be larger or equal to the Output Array. A chance of 10000 equals 100%
	 */
	public final int[] chances;

	/**
	 * An Item that needs to be inside the Special Slot, like for example the Copy Slot inside the Printer. This is only useful for Fake Recipes in JEI, since findRecipe() and containsInput() don't give a shit about this Field. Lists are also possible.
	 */
	@Nullable
	public final Object specialItems; // ItemStack or List ?
	public final int specialValue;

	public final int duration, EUt;
	/**
	 * Use this to just disable a specific Recipe, but the Configuration enables that already for every single Recipe.
	 */
	public boolean enabled = true; // delete as not needed
	/**
	 * If this Recipe is hidden from JEI
	 */
	public boolean hidden = false;
	/**
	 * If this Recipe is Fake and therefore doesn't get found by the findRecipe Function (It is still in the HashMaps, so that containsInput does return T on those fake Inputs)
	 */
	public boolean fakeRecipe = false; // make fake recipe maps instead of fake recipes
	/**
	 * If this Recipe can be stored inside a Machine in order to make Recipe searching more Efficient by trying the previously used Recipe first. In case you have a Recipe Map overriding things and returning one time use Recipes, you have to set this to F.
	 */
	public boolean canBeBuffered = true;
	/**
	 * If this Recipe needs the Output Slots to be completely empty. Needed in case you have randomised Outputs
	 */
	public boolean needsEmptyOutput = false; // replace with RecipeMap::maxOutputs ?


	//RecipeMap.centrifugeRecipes.add(
	// new RecipeBuilder()
	// .inputs(ItemStack1, ItemStackN...)
	// .cellInputs(10) // ?
	// .fluidInputs(FluidStack1, FluidStackN...)
	// .outputs(ItemStack1, ItemStackN...)
	// .chancedOutput(ItemStack, chance)
	// .fluidOutputs(FluidStack1, FluidStackN...)
	// .duration(100)
	// .voltage(N)  // EU/t ?
	// .amperage(N)  // EU/t ?
	// .requiresCleanroom() ???
	// .hidden()
	// .fake() // ???
	// .build()
	// );

	//TODO default recipes
	public static class RecipeBuilder {

		private final List<ItemStack> inputs = new ArrayList<>(0);
		private final List<ItemStack> outputs = new ArrayList<>(0);

		private final List<FluidStack> fluidInputs = new ArrayList<>(0);
		private final List<FluidStack> fluidOutputs = new ArrayList<>(0);

		private final IntList chances = new IntArrayList(0);

		private int duration, EUt;

		private boolean hidden = false;

		private boolean canBeBuffered = true;

		/////
		private boolean optimized = true;

		private Object specialItems; // FIXME ItemStack or List?
		private int specialValue;
		/////

		public RecipeBuilder inputs(@Nonnull ItemStack... inputs) {
			if (this.inputs.isEmpty()) {
				Validate.notNull(inputs, "Input array cannot be null");
				Validate.notEmpty(inputs, "Input array cannot be empty");
				Validate.noNullElements(inputs, "Input cannot contain null ItemStacks");
				Collections.addAll(this.inputs, inputs);
			} else {
				throw new IllegalArgumentException("Inputs were already set"); // TODO recipe builders based on another recipe
			}
			return this;
		}

		public RecipeBuilder outputs(@Nonnull ItemStack... outputs) {
			if (this.outputs.isEmpty()) {
				Validate.notNull(outputs, "Input array cannot be null");
				Validate.notEmpty(outputs, "Input array cannot be empty");
				Validate.noNullElements(outputs, "Output cannot contain null ItemStacks");
				Collections.addAll(this.outputs, outputs);
			} else {
				throw new IllegalArgumentException("Outputs were already set");
			}
			return this;
		}

		public RecipeBuilder fluidInputs(@Nonnull FluidStack... inputs) {
			if (this.fluidInputs.isEmpty()) {
				Validate.notNull(inputs, "Input array cannot be null");
				Validate.notEmpty(inputs, "Input array cannot be empty");
				Validate.noNullElements(inputs, "Fluid input cannot contain null FluidStacks");
				Collections.addAll(this.fluidInputs, inputs);
			} else {
				throw new IllegalArgumentException("Fluid inputs were already set");
			}
			return this;
		}

		public RecipeBuilder fluidOutputs(@Nonnull FluidStack... outputs) {
			if (this.fluidOutputs.isEmpty()) {
				Validate.notNull(outputs, "Input array cannot be null");
				Validate.notEmpty(outputs, "Input array cannot be empty");
				Validate.noNullElements(outputs, "Fluid output cannot contain null FluidStacks");
				Collections.addAll(this.fluidOutputs, outputs);
			} else {
				throw new IllegalArgumentException("Fluid outputs were already set");
			}
			return this;
		}

		public RecipeBuilder chancedOutput(@Nonnull ItemStack stack, int chance) {
			Validate.notNull(stack, "Chanced output ItemStack cannot be null");

			if (chance == 0) {
				throw new IllegalArgumentException("Chance cannot be equal to 0");
			}

			if (outputs.contains(stack)) {
				throw new IllegalArgumentException("Output list already contains " + stack);
			}

			this.outputs.add(stack);
			this.chances.size(this.outputs.size());
			this.chances.set(this.outputs.indexOf(stack), chance);
			return this;
		}

		public RecipeBuilder duration(int duration) {
			this.duration = duration;
			return this;
		}

		public RecipeBuilder UEt(int EUt) {
			this.EUt = EUt;
			return this;
		}

		public RecipeBuilder hidden() {
			this.hidden = true;
			return this;
		}

		public RecipeBuilder cannotBeBuffered() {
			this.canBeBuffered = false;
			return this;
		}

		public GT_Recipe build() {

			for (int i = 0; i < chances.size(); i++) {
				if (chances.get(i) <= 0) {
					chances.set(i, 10000);
				}
			}

//			GT_OreDictUnificator.setStackArray(true, inputs);
//			GT_OreDictUnificator.setStackArray(true, outputs);

//			for (ItemStack tStack : outputs) GT_Utility.updateItemStack(tStack);

//			for (ItemStack aInput : inputs) {
//				if (aInput != null && Items.FEATHER.getDamage(aInput) != W)
//					for (int j = 0; j < outputs.length; j++) {
//						if (GT_Utility.areStacksEqual(aInput, outputs[j])) {
//							if (aInput.stackSize >= outputs[j].stackSize) {
//								aInput.stackSize -= outputs[j].stackSize;
//								outputs[j] = null;
//							} else {
//								outputs[j].stackSize -= aInput.stackSize;
//							}
//						}
//					}
//			}
//
//			if (aOptimize && duration >= 32) {
//				ArrayList<ItemStack> tList = new ArrayList<ItemStack>();
//				tList.addAll(inputs);
//				tList.addAll(outputs);
//				for (int i = 0; i < tList.size(); i++) if (tList.get(i) == null) tList.remove(i--);
//
//				for (byte i = (byte) Math.min(64, duration / 16); i > 1; i--)
//					if (duration / i >= 16) {
//						boolean temp = true;
//						for (int j = 0, k = tList.size(); temp && j < k; j++)
//							if (tList.get(j).stackSize % i != 0) temp = false;
//						for (int j = 0; temp && j < fluidInputs.length; j++)
//							if (fluidInputs[j].amount % i != 0) temp = false;
//						for (int j = 0; temp && j < fluidOutputs.length; j++)
//							if (fluidOutputs[j].amount % i != 0) temp = false;
//						if (temp) {
//							for (ItemStack aTList : tList) aTList.stackSize /= i;
//							for (FluidStack aFluidInput : fluidInputs) aFluidInput.amount /= i;
//							for (FluidStack aFluidOutput : fluidOutputs) aFluidOutput.amount /= i;
//							duration /= i;
//						}
//					}
//			}

			return new GT_Recipe(inputs.toArray(new ItemStack[0]), outputs.toArray(new ItemStack[0]), chances.toArray(new int[0]), fluidInputs.toArray(new FluidStack[0]),
					fluidOutputs.toArray(new FluidStack[0]), duration, EUt, specialItems, specialValue, hidden, canBeBuffered);
		}
	}

	private GT_Recipe(@Nonnull ItemStack[] inputs, @Nonnull ItemStack[] outputs, int[] chances, @Nonnull FluidStack[] fluidInputs, @Nonnull FluidStack[] fluidOutputs, int duration, int EUt, Object specialItems, int specialValue, boolean hidden, boolean canBeBuffered) {
		this.inputs = inputs;
		this.outputs = outputs;
		this.fluidInputs = fluidInputs;
		this.fluidOutputs = fluidOutputs;
		this.chances = chances;
		this.specialItems = specialItems;
		this.specialValue = specialValue;
		this.duration = duration;
		this.EUt = EUt;
		this.hidden = hidden;
		this.canBeBuffered = canBeBuffered;
	}

	public static void reInit() {
		GT_Log.out.println("GT_Mod: Re-Unificating Recipes.");
		for (RecipeMap map : RecipeMap.RECIPE_MAPS) map.reInit();
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

	public GT_Recipe copy() {
		return new GT_Recipe(this);
	}

	public boolean isRecipeInputEqual(boolean decreaseStacksizeBySuccess, FluidStack[] fluidInputs, ItemStack[] inputs) {
		return isRecipeInputEqual(decreaseStacksizeBySuccess, false, fluidInputs, inputs);
	}

	public boolean isRecipeInputEqual(boolean decreaseStacksizeBySuccess, boolean dontCheckStackSizes, FluidStack[] fluidInputs, ItemStack[] inputs) {
		if (this.fluidInputs.length > 0 && fluidInputs == null) return false;
		int amt;
		for (FluidStack fluidInput : this.fluidInputs) {
			if (fluidInput != null) {
				boolean temp = true;
				amt = fluidInput.amount;
				for (FluidStack fluid : fluidInputs) {
					if (fluid != null && fluid.isFluidEqual(fluidInput)) {
						if (dontCheckStackSizes) {
							temp = false;
							break;
						}
						amt -= fluid.amount;
						if (amt < 1) {
							temp = false;
							break;
						}
					}
				}
				if (temp) return false;
			}
		}

		if (this.inputs.length > 0 && inputs == null) return false;

		for (ItemStack stackInput : this.inputs) {
			if (stackInput != null) {
				amt = stackInput.stackSize;
				boolean temp = true;
				for (ItemStack stack : inputs) {
					if ((GT_Utility.areUnificationsEqual(stack, stackInput, true) || GT_Utility.areUnificationsEqual(GT_OreDictUnificator.get(false, stack), stackInput, true))) {
						if (dontCheckStackSizes) {
							temp = false;
							break;
						}
						amt -= stack.stackSize;
						if (amt < 1) {
							temp = false;
							break;
						}
					}
				}
				if (temp) return false;
			}
		}
		if (decreaseStacksizeBySuccess) {
			if (fluidInputs != null) {
				for (FluidStack tFluid : this.fluidInputs) {
					if (tFluid != null) {
						amt = tFluid.amount;
						for (FluidStack aFluid : fluidInputs) {
							if (aFluid != null && aFluid.isFluidEqual(tFluid)) {
								if (dontCheckStackSizes) {
									aFluid.amount -= amt;
									break;
								}
								if (aFluid.amount < amt) {
									amt -= aFluid.amount;
									aFluid.amount = 0;
								} else {
									aFluid.amount -= amt;
									amt = 0;
									break;
								}
							}
						}
					}
				}
			}

			if (inputs != null) {
				for (ItemStack tStack : this.inputs) {
					if (tStack != null) {
						amt = tStack.stackSize;
						for (ItemStack aStack : inputs) {
							if ((GT_Utility.areUnificationsEqual(aStack, tStack, true) || GT_Utility.areUnificationsEqual(GT_OreDictUnificator.get(false, aStack), tStack, true))) {
								if (dontCheckStackSizes) {
									aStack.stackSize -= amt;
									break;
								}
								if (aStack.stackSize < amt) {
									amt -= aStack.stackSize;
									aStack.stackSize = 0;
								} else {
									aStack.stackSize -= amt;
									amt = 0;
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

	public static class GT_Recipe_AssemblyLine {
		public static final ArrayList<GT_Recipe_AssemblyLine> sAssemblylineRecipes = new ArrayList<>();

		public ItemStack mResearchItem;
		public int mResearchTime;
		public ItemStack[] mInputs;
		public FluidStack[] mFluidInputs;
		public ItemStack mOutput;
		public int mDuration;
		public int mEUt;

		public GT_Recipe_AssemblyLine(ItemStack aResearchItem, int aResearchTime, ItemStack[] aInputs, FluidStack[] aFluidInputs, ItemStack aOutput, int aDuration, int aEUt) {
			mResearchItem = aResearchItem;
			mResearchTime = aResearchTime;
			mInputs = aInputs;
			mFluidInputs = aFluidInputs;
			mOutput = aOutput;
			mDuration = aDuration;
			mEUt = aEUt;
		}

	}

	public static class FuelRecipe extends GT_Recipe {

		public FuelRecipe(ItemStack aInput1, ItemStack aOutput1, int aFuelValue, int aType) {
			this(aInput1, aOutput1, null, null, null, aFuelValue, aType);
		}

		// aSpecialValue = EU per Liter! If there is no Liquid for this Object, then it gets multiplied with 1000!
		public FuelRecipe(ItemStack aInput1, ItemStack aOutput1, ItemStack aOutput2, ItemStack aOutput3, ItemStack aOutput4, int aSpecialValue, int aType) {
			super(true, new ItemStack[]{aInput1}, new ItemStack[]{aOutput1, aOutput2, aOutput3, aOutput4}, null, null, null, null, 0, 0, Math.max(1, aSpecialValue));

			if (inputs.length > 0 && aSpecialValue > 0) {
				switch (aType) {
					// Diesel Generator
					case 0:
						RecipeMap.DIESEL_FUELS.addRecipe(this);
						break;
					// Gas Turbine
					case 1:
						RecipeMap.TURBINE_FUELS.addRecipe(this);
						break;
					// Thermal Generator
					case 2:
						RecipeMap.HOT_FUELS.addRecipe(this);
						break;
					// Fluid Generator
					case 3:
						RecipeMap.DENSE_LIQUID_FUELS.addRecipe(this);
						break;
					// Plasma Generator
					case 4:
						RecipeMap.PLASMA_FUELS.addRecipe(this);
						break;
					// Magic Generator
					case 5:
						RecipeMap.MAGIC_FUELS.addRecipe(this);
						break;
					default:
						throw new IllegalArgumentException("Wrong recipe type: " + aType + ", should be from 0 to 5");
				}
			}
		}
	}

	public static class LatheRecipe extends GT_Recipe {

		public LatheRecipe(ItemStack aInput1, ItemStack aOutput1, ItemStack aOutput2, int aDuration, int aEUt) {
			super(true, new ItemStack[]{aInput1}, new ItemStack[]{aOutput1, aOutput2}, null, null, null, null, aDuration, aEUt, 0);
			if (inputs.length > 0 && outputs[0] != null) {
				RecipeMap.LATHE_RECIPES.addRecipe(this);
			}
		}
	}

	public static class DistillationTowerRecipe extends GT_Recipe {

		public DistillationTowerRecipe(FluidStack aInput, FluidStack[] aOutputs, ItemStack aOutput2, int aDuration, int aEUt) {
			super(false, null, new ItemStack[]{aOutput2}, null, null, new FluidStack[]{aInput}, aOutputs, Math.max(1, aDuration), Math.max(1, aEUt), 0);

			if (inputs.length > 0 && outputs[0] != null) {
				RecipeMap.DISTILLATION_RECIPES.addRecipe(this);
			}
		}
	}

	public static class ImplosionRecipe extends GT_Recipe {

		public ImplosionRecipe(ItemStack aInput1, ItemStack aInput2, ItemStack aOutput1, ItemStack aOutput2) {
			super(true, new ItemStack[]{aInput1, aInput2}, new ItemStack[]{aOutput1, aOutput2}, null, null, null, null, 20, 30, 0);
			if (inputs.length > 0 && outputs[0] != null) {
				RecipeMap.IMPLOSION_RECIPES.addRecipe(this);
			}
		}
	}

	public static class BenderRecipe extends GT_Recipe {

		public BenderRecipe(int aEUt, int aDuration, ItemStack aInput1, ItemStack aOutput1) {
			super(true, new ItemStack[]{aInput1, ItemList.Circuit_Integrated.getWithDamage(0, aInput1.stackSize)}, new ItemStack[]{aOutput1}, null, null, null, null, Math.max(aDuration, 1), Math.max(aEUt, 1), 0);
			if (inputs.length > 0 && outputs[0] != null) {
				RecipeMap.BENDER_RECIPES.addRecipe(this);
			}
		}
	}

	public static class AlloySmelterRecipe extends GT_Recipe {

		public AlloySmelterRecipe(ItemStack aInput1, ItemStack aInput2, int aEUt, int aDuration, ItemStack aOutput1) {
			super(true, aInput2 == null ? new ItemStack[]{aInput1} : new ItemStack[]{aInput1, aInput2}, new ItemStack[]{aOutput1}, null, null, null, null, Math.max(aDuration, 1), Math.max(aEUt, 1), 0);
			if (inputs.length > 0 && outputs[0] != null) {
				RecipeMap.ALLOY_SMELTER_RECIPES.addRecipe(this);
			}
		}
	}

	public static class CannerRecipe extends GT_Recipe {

		public CannerRecipe(ItemStack aInput1, int aEUt, ItemStack aInput2, int aDuration, ItemStack aOutput1, ItemStack aOutput2) {
			super(true, aInput2 == null ? new ItemStack[]{aInput1} : new ItemStack[]{aInput1, aInput2}, new ItemStack[]{aOutput1, aOutput2}, null, null, null, null, Math.max(aDuration, 1), Math.max(aEUt, 1), 0);
			if (inputs.length > 0 && outputs[0] != null) {
				RecipeMap.CANNER_RECIPES.addRecipe(this);
			}
		}
	}

	public static class VacuumRecipe extends GT_Recipe {

		public VacuumRecipe(ItemStack aInput1, ItemStack aOutput1, int aDuration) {
			super(true, new ItemStack[]{aInput1}, new ItemStack[]{aOutput1}, null, null, null, null, Math.max(aDuration, 1), 120, 0);
			if (inputs.length > 0 && outputs[0] != null) {
				RecipeMap.VACUUM_RECIPES.addRecipe(this);
			}
		}
	}
}
