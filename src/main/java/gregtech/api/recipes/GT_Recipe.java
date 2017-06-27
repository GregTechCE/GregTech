package gregtech.api.recipes;

import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.util.GT_Log;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static gregtech.api.enums.GT_Values.D1;
import static gregtech.api.enums.GT_Values.W;

/**
 * NEVER INCLUDE THIS FILE IN YOUR MOD!!!
 * <p/>
 * This File contains the functions used for Recipes. Please do not include this File AT ALL in your Moddownload as it ruins compatibility
 * This is just the Core of my Recipe System, if you just want to GET the Recipes I add, then you can access this File.
 * Do NOT add Recipes using the Constructors inside this Class, The GregTech_API File calls the correct Functions for these Constructors.
 * <p/>
 * I know this File causes some Errors, because of missing Main Functions, but if you just need to compile Stuff, then remove said erroreous Functions.
 */
public class GT_Recipe {
    /**
     * If you want to change the Output, feel free to modify or even replace the whole ItemStack Array, for Inputs, please add a new Recipe, because of the HashMaps.
     */
    public ItemStack[] mInputs, mOutputs;
    /**
     * If you want to change the Output, feel free to modify or even replace the whole ItemStack Array, for Inputs, please add a new Recipe, because of the HashMaps.
     */
    public FluidStack[] mFluidInputs, mFluidOutputs;
    /**
     * If you changed the amount of Array-Items inside the Output Array then the length of this Array must be larger or equal to the Output Array. A chance of 10000 equals 100%
     */
    public int[] mChances;
    /**
     * An Item that needs to be inside the Special Slot, like for example the Copy Slot inside the Printer. This is only useful for Fake Recipes in NEI, since findRecipe() and containsInput() don't give a shit about this Field. Lists are also possible.
     */
    public Object mSpecialItems;
    public int mDuration, mEUt, mSpecialValue;
    /**
     * Use this to just disable a specific Recipe, but the Configuration enables that already for every single Recipe.
     */
    public boolean mEnabled = true;
    /**
     * If this Recipe is hidden from NEI
     */
    public boolean mHidden = false;
    /**
     * If this Recipe is Fake and therefore doesn't get found by the findRecipe Function (It is still in the HashMaps, so that containsInput does return T on those fake Inputs)
     */
    public boolean mFakeRecipe = false;
    /**
     * If this Recipe can be stored inside a Machine in order to make Recipe searching more Efficient by trying the previously used Recipe first. In case you have a Recipe Map overriding things and returning one time use Recipes, you have to set this to F.
     */
    public boolean mCanBeBuffered = true;
    /**
     * If this Recipe needs the Output Slots to be completely empty. Needed in case you have randomised Outputs
     */
    public boolean mNeedsEmptyOutput = false;

    private GT_Recipe(GT_Recipe aRecipe) {
        mInputs = Arrays.copyOf(aRecipe.mInputs, aRecipe.mInputs.length);
        mOutputs = Arrays.copyOf(aRecipe.mOutputs, aRecipe.mOutputs.length);
        mSpecialItems = aRecipe.mSpecialItems;
        mChances = aRecipe.mChances;
        mFluidInputs = GT_Utility.copyFluidArray(aRecipe.mFluidInputs);
        mFluidOutputs = GT_Utility.copyFluidArray(aRecipe.mFluidOutputs);
        mDuration = aRecipe.mDuration;
        mSpecialValue = aRecipe.mSpecialValue;
        mEUt = aRecipe.mEUt;
        mNeedsEmptyOutput = aRecipe.mNeedsEmptyOutput;
        mCanBeBuffered = aRecipe.mCanBeBuffered;
        mFakeRecipe = aRecipe.mFakeRecipe;
        mEnabled = aRecipe.mEnabled;
        mHidden = aRecipe.mHidden;
    }

    GT_Recipe(boolean aOptimize, ItemStack[] aInputs, ItemStack[] aOutputs, Object aSpecialItems, int[] aChances, FluidStack[] aFluidInputs, FluidStack[] aFluidOutputs, int aDuration, int aEUt, int aSpecialValue) {
        if (aInputs == null) aInputs = new ItemStack[0];
        if (aOutputs == null) aOutputs = new ItemStack[0];
        if (aFluidInputs == null) aFluidInputs = new FluidStack[0];
        if (aFluidOutputs == null) aFluidOutputs = new FluidStack[0];
        if (aChances == null) aChances = new int[aOutputs.length];
        if (aChances.length < aOutputs.length) aChances = Arrays.copyOf(aChances, aOutputs.length);

        aInputs = GT_Utility.getArrayListWithoutTrailingNulls(aInputs).toArray(new ItemStack[0]);
        aOutputs = GT_Utility.getArrayListWithoutTrailingNulls(aOutputs).toArray(new ItemStack[0]);
        aFluidInputs = GT_Utility.getArrayListWithoutNulls(aFluidInputs).toArray(new FluidStack[0]);
//        List<FluidStack> stacks = Arrays.asList(aFluidInputs);
//        stacks.removeIf(Objects::isNull);
//        aFluidInputs = (FluidStack[]) stacks.toArray();
        aFluidOutputs = GT_Utility.getArrayListWithoutNulls(aFluidOutputs).toArray(new FluidStack[0]);

        GT_OreDictUnificator.setStackArray(true, aInputs);
        GT_OreDictUnificator.setStackArray(true, aOutputs);

        for (ItemStack tStack : aOutputs) GT_Utility.updateItemStack(tStack);

        for (int i = 0; i < aChances.length; i++) if (aChances[i] <= 0) aChances[i] = 10000;
        System.arraycopy(aFluidInputs, 0, aFluidInputs, 0, aFluidInputs.length);
        System.arraycopy(aFluidOutputs, 0, aFluidOutputs, 0, aFluidOutputs.length);

        for (ItemStack aInput : aInputs) {
			if (aInput != null && Items.FEATHER.getDamage(aInput) != W)
				for (int j = 0; j < aOutputs.length; j++) {
					if (GT_Utility.areStacksEqual(aInput, aOutputs[j])) {
						if (aInput.stackSize >= aOutputs[j].stackSize) {
							aInput.stackSize -= aOutputs[j].stackSize;
							aOutputs[j] = null;
						} else {
							aOutputs[j].stackSize -= aInput.stackSize;
						}
					}
				}
		}

        if (aOptimize && aDuration >= 32) {
            ArrayList<ItemStack> tList = new ArrayList<ItemStack>();
            tList.addAll(Arrays.asList(aInputs));
            tList.addAll(Arrays.asList(aOutputs));
            for (int i = 0; i < tList.size(); i++) if (tList.get(i) == null) tList.remove(i--);

            for (byte i = (byte) Math.min(64, aDuration / 16); i > 1; i--)
                if (aDuration / i >= 16) {
                    boolean temp = true;
                    for (int j = 0, k = tList.size(); temp && j < k; j++)
                        if (tList.get(j).stackSize % i != 0) temp = false;
                    for (int j = 0; temp && j < aFluidInputs.length; j++)
                        if (aFluidInputs[j].amount % i != 0) temp = false;
                    for (int j = 0; temp && j < aFluidOutputs.length; j++)
                        if (aFluidOutputs[j].amount % i != 0) temp = false;
                    if (temp) {
						for (ItemStack aTList : tList) aTList.stackSize /= i;
						for (FluidStack aFluidInput : aFluidInputs) aFluidInput.amount /= i;
						for (FluidStack aFluidOutput : aFluidOutputs) aFluidOutput.amount /= i;
                        aDuration /= i;
                    }
                }
        }

        mInputs = aInputs;
        mOutputs = aOutputs;
        mSpecialItems = aSpecialItems;
        mChances = aChances;
        mFluidInputs = aFluidInputs;
        mFluidOutputs = aFluidOutputs;
        mDuration = aDuration;
        mSpecialValue = aSpecialValue;
        mEUt = aEUt;

//		checkCellBalance();
    }

    public static void reInit() {
        GT_Log.out.println("GT_Mod: Re-Unificating Recipes.");
        for (GT_Recipe_Map tMapEntry : GT_Recipe_Map.sMappings) tMapEntry.reInit();
    }

    // -----
    // Old Constructors, do not use!
    // -----

    public ItemStack getRepresentativeInput(int aIndex) {
        if (aIndex < 0 || aIndex >= mInputs.length) return null;
        return GT_Utility.copy(mInputs[aIndex]);
    }

    public ItemStack getNEIAdaptedInput(int aIndex) {
        ItemStack input = getRepresentativeInput(aIndex);
        if(input != null && input.stackSize == 0) {
            input.stackSize = 1;
        }
        return input;
    }


    public ItemStack getOutput(int aIndex) {
        if (aIndex < 0 || aIndex >= mOutputs.length) return null;
        return GT_Utility.copy(mOutputs[aIndex]);
    }

    public int getOutputChance(int aIndex) {
        if (aIndex < 0 || aIndex >= mChances.length) return 10000;
        return mChances[aIndex];
    }

    public FluidStack getRepresentativeFluidInput(int aIndex) {
        if (aIndex < 0 || aIndex >= mFluidInputs.length || mFluidInputs[aIndex] == null) return null;
        return mFluidInputs[aIndex].copy();
    }

    public FluidStack getFluidOutput(int aIndex) {
        if (aIndex < 0 || aIndex >= mFluidOutputs.length || mFluidOutputs[aIndex] == null) return null;
        return mFluidOutputs[aIndex].copy();
    }

    public void checkCellBalance() {
        if (!D1 || mInputs.length < 1) return;

        int tInputAmount = GT_ModHandler.getCapsuleCellContainerCountMultipliedWithStackSize(mInputs);
        int tOutputAmount = GT_ModHandler.getCapsuleCellContainerCountMultipliedWithStackSize(mOutputs);

        if (tInputAmount < tOutputAmount) {
            if (!Materials.Tin.contains(mInputs)) {
                GT_Log.err.println("You get more Cells, than you put in? There must be something wrong.");
                new Exception().printStackTrace(GT_Log.err);
            }
        } else if (tInputAmount > tOutputAmount) {
            if (!Materials.Tin.contains(mOutputs)) {
                GT_Log.err.println("You get less Cells, than you put in? GT Machines usually don't destroy Cells.");
                new Exception().printStackTrace(GT_Log.err);
            }
        }
    }

    public GT_Recipe copy() {
        return new GT_Recipe(this);
    }

    public boolean isRecipeInputEqual(boolean aDecreaseStacksizeBySuccess, FluidStack[] aFluidInputs, ItemStack... aInputs) {
        return isRecipeInputEqual(aDecreaseStacksizeBySuccess, false, aFluidInputs, aInputs);
    }

    public boolean isRecipeInputEqual(boolean aDecreaseStacksizeBySuccess, boolean aDontCheckStackSizes, FluidStack[] aFluidInputs, ItemStack... aInputs) {
        if (mFluidInputs.length > 0 && aFluidInputs == null) return false;
        int amt;
        for (FluidStack tFluid : mFluidInputs)
            if (tFluid != null) {
                boolean temp = true;
                amt = tFluid.amount;
                for (FluidStack aFluid : aFluidInputs)
                    if (aFluid != null && aFluid.isFluidEqual(tFluid)){
                        if (aDontCheckStackSizes ){
                            temp = false;
                            break;
                        }
                        amt -= aFluid.amount;
                        if (amt<1){
                            temp = false;
                            break;
                        }
                    }
                if (temp) return false;
            }

        if (mInputs.length > 0 && aInputs == null) return false;

        for (ItemStack tStack : mInputs) {
            if (tStack != null) {
                amt = tStack.stackSize;
                boolean temp = true;
                for (ItemStack aStack : aInputs) {
                    if ((GT_Utility.areUnificationsEqual(aStack, tStack, true) || GT_Utility.areUnificationsEqual(GT_OreDictUnificator.get(false, aStack), tStack, true))) {
                        if (aDontCheckStackSizes) {
                            temp = false;
                            break;
                        }
                        amt -= aStack.stackSize;
                        if (amt < 1) {
                            temp = false;
                            break;
                        }
                    }
                }
                if (temp) return false;
            }
        }
        if (aDecreaseStacksizeBySuccess) {
            if (aFluidInputs != null) {
                for (FluidStack tFluid : mFluidInputs) {
                    if (tFluid != null) {
                        amt = tFluid.amount;
                        for (FluidStack aFluid : aFluidInputs) {
                            if (aFluid != null && aFluid.isFluidEqual(tFluid)) {
                                if (aDontCheckStackSizes) {
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

            if (aInputs != null) {
                for (ItemStack tStack : mInputs) {
                    if (tStack != null) {
                        amt = tStack.stackSize;
                        for (ItemStack aStack : aInputs) {
                            if ((GT_Utility.areUnificationsEqual(aStack, tStack, true) || GT_Utility.areUnificationsEqual(GT_OreDictUnificator.get(false, aStack), tStack, true))) {
                                if (aDontCheckStackSizes){
                                    aStack.stackSize -= amt;
                                    break;
                                }
                                if (aStack.stackSize < amt){
                                    amt -= aStack.stackSize;
                                    aStack.stackSize = 0;
                                }else{
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
    
    public static class GT_Recipe_AssemblyLine{
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

			if (mInputs.length > 0 && aSpecialValue > 0) {
				switch (aType) {
					// Diesel Generator
					case 0:
						GT_Recipe_Map.sDieselFuels.addRecipe(this);
						break;
					// Gas Turbine
					case 1:
						GT_Recipe_Map.sTurbineFuels.addRecipe(this);
						break;
					// Thermal Generator
					case 2:
						GT_Recipe_Map.sHotFuels.addRecipe(this);
						break;
					// Fluid Generator
					case 3:
						GT_Recipe_Map.sDenseLiquidFuels.addRecipe(this);
						break;
					// Plasma Generator
					case 4:
						GT_Recipe_Map.sPlasmaFuels.addRecipe(this);
						break;
					// Magic Generator
					case 5:
						GT_Recipe_Map.sMagicFuels.addRecipe(this);
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
			if (mInputs.length > 0 && mOutputs[0] != null) {
				GT_Recipe_Map.sLatheRecipes.addRecipe(this);
			}
		}
	}

	public static class DistillationTowerRecipe extends GT_Recipe {

		public DistillationTowerRecipe(FluidStack aInput, FluidStack[] aOutputs, ItemStack aOutput2, int aDuration, int aEUt) {
			super(false, null, new ItemStack[]{aOutput2}, null, null, new FluidStack[]{aInput}, aOutputs, Math.max(1, aDuration), Math.max(1, aEUt), 0);

			if (mInputs.length > 0 && mOutputs[0] != null) {
				GT_Recipe_Map.sDistillationRecipes.addRecipe(this);
			}
		}
	}

	public static class ImplosionRecipe extends GT_Recipe {

		public ImplosionRecipe(ItemStack aInput1, ItemStack aInput2, ItemStack aOutput1, ItemStack aOutput2) {
			super(true, new ItemStack[]{aInput1, aInput2}, new ItemStack[]{aOutput1, aOutput2}, null, null, null, null, 20, 30, 0);
			if (mInputs.length > 0 && mOutputs[0] != null) {
				GT_Recipe_Map.sImplosionRecipes.addRecipe(this);
			}
		}
	}

	public static class BenderRecipe extends GT_Recipe {

		public BenderRecipe(int aEUt, int aDuration, ItemStack aInput1, ItemStack aOutput1) {
			super(true, new ItemStack[]{aInput1, ItemList.Circuit_Integrated.getWithDamage(0, aInput1.stackSize)}, new ItemStack[]{aOutput1}, null, null, null, null, Math.max(aDuration, 1), Math.max(aEUt, 1), 0);
			if (mInputs.length > 0 && mOutputs[0] != null) {
				GT_Recipe_Map.sBenderRecipes.addRecipe(this);
			}
		}
	}

	public static class AlloySmelterRecipe extends GT_Recipe {

		public AlloySmelterRecipe(ItemStack aInput1, ItemStack aInput2, int aEUt, int aDuration, ItemStack aOutput1) {
			super(true, aInput2 == null ? new ItemStack[]{aInput1} : new ItemStack[]{aInput1, aInput2}, new ItemStack[]{aOutput1}, null, null, null, null, Math.max(aDuration, 1), Math.max(aEUt, 1), 0);
			if (mInputs.length > 0 && mOutputs[0] != null) {
				GT_Recipe_Map.sAlloySmelterRecipes.addRecipe(this);
			}
		}
	}

	public static class CannerRecipe extends GT_Recipe {

		public CannerRecipe(ItemStack aInput1, int aEUt, ItemStack aInput2, int aDuration, ItemStack aOutput1, ItemStack aOutput2) {
			super(true, aInput2 == null ? new ItemStack[]{aInput1} : new ItemStack[]{aInput1, aInput2}, new ItemStack[]{aOutput1, aOutput2}, null, null, null, null, Math.max(aDuration, 1), Math.max(aEUt, 1), 0);
			if (mInputs.length > 0 && mOutputs[0] != null) {
				GT_Recipe_Map.sCannerRecipes.addRecipe(this);
			}
		}
	}

	public static class VacuumRecipe extends GT_Recipe {

		public VacuumRecipe(ItemStack aInput1, ItemStack aOutput1, int aDuration) {
			super(true, new ItemStack[]{aInput1}, new ItemStack[]{aOutput1}, null, null, null, null, Math.max(aDuration, 1), 120, 0);
			if (mInputs.length > 0 && mOutputs[0] != null) {
				GT_Recipe_Map.sVacuumRecipes.addRecipe(this);
			}
		}
	}
}
