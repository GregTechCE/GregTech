package gregtech.api.recipes.machines;

import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.builders.DefaultRecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.GTUtility;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.List;

public class RecipeMapPrinter extends RecipeMap<DefaultRecipeBuilder> {

    public RecipeMapPrinter(String unlocalizedName, int minInputs, int maxInputs, int minOutputs, int maxOutputs, int minFluidInputs, int maxFluidInputs, int minFluidOutputs, int maxFluidOutputs, int amperage, DefaultRecipeBuilder defaultRecipe) {
        super(unlocalizedName, minInputs, maxInputs, minOutputs, maxOutputs, minFluidInputs, maxFluidInputs, minFluidOutputs, maxFluidOutputs, amperage, defaultRecipe);
    }

    @Override
    @Nullable
    public Recipe findRecipe(long voltage, NonNullList<ItemStack> inputs, List<FluidStack> fluidInputs) {
        Recipe recipe = super.findRecipe(voltage, inputs, fluidInputs);
        if (recipe != null || inputs.size() == 0 || inputs.get(0).isEmpty())
            return recipe;
        ItemStack firstInputItem = inputs.get(0);

        if (fluidInputs.size() != 0 && fluidInputs.get(0) != null) {
            FluidStack fluidStack = fluidInputs.get(0);
            EnumDyeColor dyeColor = GregTechAPI.LIQUID_DYE_MAP.get(fluidStack.getFluid());
            if (dyeColor != null && fluidStack.amount >= GTValues.L) {
                ItemStack dyeItemStack = new ItemStack(Items.DYE, 1, dyeColor.getDyeDamage());
                ItemStack colouredItem = ModHandler.getRecipeOutput(null, dyeItemStack, firstInputItem);
                if (colouredItem.isEmpty()) return null;
                return recipeBuilder()
                    .inputs(GTUtility.copyAmount(1, firstInputItem))
                    .fluidInputs(GTUtility.copyAmount(GTValues.L, fluidStack))
                    .outputs(colouredItem)
                    .EUt(2).duration(32)
                    .build().getResult();
            }
        }

        return null;
    }

}
