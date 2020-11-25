package gregtech.api.recipes.machines;

import gregtech.api.recipes.MatchingMode;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.builders.SimpleRecipeBuilder;
import gregtech.api.recipes.ingredients.NBTIngredient;
import gregtech.api.util.GTUtility;
import gregtech.common.items.MetaItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.List;

public class RecipeMapFormingPress extends RecipeMap<SimpleRecipeBuilder> {

    public RecipeMapFormingPress(String unlocalizedName, int minInputs, int maxInputs, int minOutputs, int maxOutputs, int minFluidInputs, int maxFluidInputs, int minFluidOutputs, int maxFluidOutputs, int amperage, SimpleRecipeBuilder defaultRecipe) {
        super(unlocalizedName, minInputs, maxInputs, minOutputs, maxOutputs, minFluidInputs, maxFluidInputs, minFluidOutputs, maxFluidOutputs, defaultRecipe);
    }

    @Override
    @Nullable
    public Recipe findRecipe(long voltage, List<ItemStack> inputs, List<FluidStack> fluidInputs, int outputFluidTankCapacity, MatchingMode mode) {
        Recipe recipe = super.findRecipe(voltage, inputs, fluidInputs, outputFluidTankCapacity, mode);
        if (inputs.size() < 2 || inputs.get(0).isEmpty() || inputs.get(1).isEmpty()) {
            return recipe;
        }
        if (recipe == null) {
            ItemStack moldStack = ItemStack.EMPTY;
            ItemStack itemStack = ItemStack.EMPTY;
            for (ItemStack inputStack : inputs) {
                if (MetaItems.SHAPE_MOLD_NAME.getStackForm().isItemEqual(moldStack)) {
                    moldStack = inputStack;
                } else {
                    itemStack = inputStack;
                }
            }
            if (!moldStack.isEmpty() && !itemStack.isEmpty()) {
                ItemStack output = GTUtility.copyAmount(1, itemStack);
                output.setStackDisplayName(inputs.get(0).getDisplayName());
                return this.recipeBuilder()
                    .notConsumable(new NBTIngredient(moldStack)) //recipe is reusable as long as mold stack matches
                    .inputs(GTUtility.copyAmount(1, itemStack))
                    .outputs(output)
                    .duration(40).EUt(4)
                    .build().getResult();
            }
            return null;
        }
        return recipe;
    }
}
