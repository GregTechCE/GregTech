package gregtech.api.recipes;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.FluidTankHandler;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.resources.TextureArea;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.gui.widgets.ProgressWidget.MoveType;
import gregtech.api.gui.widgets.SlotWidget;
import gregtech.api.gui.widgets.TankWidget;
import gregtech.api.recipes.builders.IntCircuitRecipeBuilder;
import gregtech.api.util.GTUtility;
import gregtech.api.util.ValidationResult;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.DoubleSupplier;

public class RecipeMap<R extends RecipeBuilder<R>> {

	public static final Collection<RecipeMap<?>> RECIPE_MAPS = new ArrayList<>();

	public final String unlocalizedName;

	private final R recipeBuilderSample;
	private final int minInputs, maxInputs;
	private final int minOutputs, maxOutputs;
	private final int minFluidInputs, maxFluidInputs;
	private final int minFluidOutputs, maxFluidOutputs;
	private final int amperage;
	private final TByteObjectMap<TextureArea> slotOverlays;
	private TextureArea progressBarTexture;
	private MoveType moveType;

    private final Map<Fluid, Collection<Recipe>> recipeFluidMap = new HashMap<>();
    private final Collection<Recipe> recipeList;

	public RecipeMap(String unlocalizedName,
                     int minInputs, int maxInputs, int minOutputs, int maxOutputs,
                     int minFluidInputs, int maxFluidInputs, int minFluidOutputs, int maxFluidOutputs,
                     int amperage, R defaultRecipe) {
        this.unlocalizedName = unlocalizedName;
		this.recipeList = new HashSet<>();
		this.amperage = amperage;
		this.slotOverlays = new TByteObjectHashMap<>();
		this.progressBarTexture = GuiTextures.PROGRESS_BAR_ARROW;
		this.moveType = MoveType.HORIZONTAL;

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

	public RecipeMap<R> setProgressBar(TextureArea progressBar, MoveType moveType) {
        this.progressBarTexture = progressBar;
        this.moveType = moveType;
        return this;
    }

    public RecipeMap<R> setSlotOverlay(boolean isOutput, boolean isFluid, TextureArea slotOverlay) {
	    return this
            .setSlotOverlay(isOutput, isFluid, false, slotOverlay)
            .setSlotOverlay(isOutput, isFluid, true, slotOverlay);
    }

    public RecipeMap<R> setSlotOverlay(boolean isOutput, boolean isFluid, boolean isLast, TextureArea slotOverlay) {
	    this.slotOverlays.put((byte) ((isOutput ? 2 : 0) + (isFluid ? 1 : 0) + (isLast ? 4 : 0)), slotOverlay);
	    return this;
    }

    public Collection<Recipe> getRecipesForFluid(Fluid fluid) {
        return recipeFluidMap.getOrDefault(fluid, Collections.emptySet());
    }

    public Collection<Recipe> getRecipeList() {
        return Collections.unmodifiableCollection(recipeList);
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

	//this DOES NOT add machine control widgets or binds player inventory
	public ModularUI.Builder createUITemplate(DoubleSupplier progressSupplier, IItemHandlerModifiable importItems, IItemHandlerModifiable exportItems, FluidTankHandler importFluids, FluidTankHandler exportFluids) {
        ModularUI.Builder builder = ModularUI.defaultBuilder();
        builder.widget(new ProgressWidget(progressSupplier, 77, 23, 20, 15, progressBarTexture, moveType));
        addInventorySlotGroup(builder, importItems, importFluids, false);
        addInventorySlotGroup(builder, exportItems, exportFluids, true);
        return builder;
    }

    private void addInventorySlotGroup(ModularUI.Builder builder, IItemHandlerModifiable itemHandler, FluidTankHandler fluidHandler, boolean isOutputs) {
        int itemInputsCount = itemHandler.getSlots();
        int fluidInputsCount = fluidHandler.getTanks();
        boolean invertFluids = false;
        if(itemInputsCount == 0) {
            itemInputsCount = fluidInputsCount;
            fluidInputsCount = 0;
            invertFluids = true;
        }
        int[] inputSlotGrid = determineSlotsGrid(itemInputsCount);
        int itemSlotsToLeft = inputSlotGrid[0];
        int itemSlotsToDown = inputSlotGrid[1];
        int startInputsX = isOutputs ? 106 : 69 - itemSlotsToLeft * 18;
        int startInputsY = 32 - (int) (itemSlotsToDown / 2.0 * 18);
        for(int i = 0; i < itemSlotsToDown; i++) {
            for (int j = 0; j < itemSlotsToLeft; j++) {
                int slotIndex = i * itemSlotsToDown + j;
                addSlot(builder, startInputsX + 18 * j, startInputsY + 18 * i, slotIndex, itemHandler, fluidHandler, invertFluids, isOutputs);
            }
        }
        if(fluidInputsCount > 0) {
            int startSpecX = isOutputs ? 158 : 1;
            int startSpecY = 32 - (int) (fluidInputsCount / 2.0 * 18);
            for(int i = 0; i < fluidInputsCount; i++) {
                addSlot(builder, startSpecX, startSpecY + 18 * i, i, itemHandler, fluidHandler, !invertFluids, isOutputs);
            }
        }
    }

    protected void addSlot(ModularUI.Builder builder, int x, int y, int slotIndex, IItemHandlerModifiable itemHandler, FluidTankHandler fluidHandler, boolean isFluid, boolean isOutputs) {
        if(!isFluid) {
            builder.widget(new SlotWidget(itemHandler, slotIndex, x, y, true, !isOutputs)
                .setBackgroundTexture(getOverlaysForSlot(isOutputs, false,slotIndex == itemHandler.getSlots() - 1)));
        } else {
            builder.widget(new TankWidget(fluidHandler.getTankAt(slotIndex), x, y - 1, 18, 18)
                .setAlwaysShowFull(true)
                .setBackgroundTexture(getOverlaysForSlot(isOutputs, true, slotIndex == fluidHandler.getTanks() - 1)));
        }
    }

    private TextureArea[] getOverlaysForSlot(boolean isOutput, boolean isFluid, boolean isLast) {
	    TextureArea base = isFluid ? GuiTextures.FLUID_SLOT : GuiTextures.SLOT;
	    if(!isOutput && !isFluid && isLast && recipeBuilderSample instanceof IntCircuitRecipeBuilder) {
	        //automatically add int circuit overlay to last item input slot
            return new TextureArea[]{base, GuiTextures.INT_CIRCUIT_OVERLAY};
        }
        byte overlayKey = (byte) ((isOutput ? 2 : 0) + (isFluid ? 1 : 0) + (isLast ? 4 : 0));
	    if(slotOverlays.containsKey(overlayKey)) {
	        return new TextureArea[] {base, slotOverlays.get(overlayKey)};
        }
        return new TextureArea[] {base};
    }

    private static int[] determineSlotsGrid(int itemInputsCount) {
        int itemSlotsToLeft = 0;
        int itemSlotsToDown = 0;
        double sqrt = Math.sqrt(itemInputsCount);
        if (sqrt % 1 == 0) { //check if square root is integer
            //case for 1, 4, 9 slots - it's square inputs (the most common case)
            itemSlotsToLeft = itemSlotsToDown = (int) sqrt;
        } else if (itemInputsCount % 3 == 0) {
            //case for 3 and 6 slots - 3 by horizontal and i / 3 by vertical (common case too)
            itemSlotsToDown = itemInputsCount / 3;
            itemSlotsToLeft = 3;
        } else if (itemInputsCount % 2 == 0) {
            //case for 2 inputs - 2 by horizontal and i / 3 by vertical (for 2 slots)
            itemSlotsToDown = itemInputsCount / 2;
            itemSlotsToLeft = 2;
        }
        return new int[] {itemSlotsToLeft, itemSlotsToDown};
    }

    @SideOnly(Side.CLIENT)
    public String getLocalizedName() {
	    return I18n.format("recipemap." + unlocalizedName + ".name");
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

    public int getAmperage() {
        return amperage;
    }

}
