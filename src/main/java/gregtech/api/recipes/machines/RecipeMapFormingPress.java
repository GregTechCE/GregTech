package gregtech.api.recipes.machines;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.builders.SimpleRecipeBuilder;
import gregtech.api.util.GTUtility;
import gregtech.common.items.MetaItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.List;

public class RecipeMapFormingPress extends RecipeMap<SimpleRecipeBuilder> {

    public RecipeMapFormingPress(String unlocalizedName, int minInputs, int maxInputs, int minOutputs, int maxOutputs, int minFluidInputs, int maxFluidInputs, int minFluidOutputs, int maxFluidOutputs, int amperage, SimpleRecipeBuilder defaultRecipe) {
        super(unlocalizedName, minInputs, maxInputs, minOutputs, maxOutputs, minFluidInputs, maxFluidInputs, minFluidOutputs, maxFluidOutputs, amperage, defaultRecipe);
    }

    @Override
    @Nullable
    public Recipe findRecipe(long voltage, List<ItemStack> inputs, List<FluidStack> fluidInputs) {
        Recipe recipe = super.findRecipe(voltage, inputs, fluidInputs);
        if (inputs.size() < 2 || inputs.get(0).isEmpty() || inputs.get(1).isEmpty())
            return recipe;
        if (recipe == null) {
            if (MetaItems.SHAPE_MOLD_NAME.getStackForm().isItemEqual(inputs.get(0))) {
                ItemStack output = GTUtility.copyAmount(1, inputs.get(1));
                output.setStackDisplayName(inputs.get(0).getDisplayName());

                return this.recipeBuilder()
                    .cannotBeBuffered().notOptimized()
                    .notConsumable(MetaItems.SHAPE_MOLD_NAME)
                    .inputs(GTUtility.copyAmount(1, inputs.get(1)))
                    .outputs(output)
                    .duration(128).EUt(8)
                    .build().getResult();
            }
            if (MetaItems.SHAPE_MOLD_NAME.getStackForm().isItemEqual(inputs.get(1))) {
                ItemStack output = GTUtility.copyAmount(1, inputs.get(0));
                output.setStackDisplayName(inputs.get(1).getDisplayName());

                return this.recipeBuilder()
                    .cannotBeBuffered().notOptimized()
                    .notConsumable(MetaItems.SHAPE_MOLD_NAME)
                    .inputs(GTUtility.copyAmount(1, inputs.get(0)))
                    .outputs(output)
                    .duration(128).EUt(8)
                    .build().getResult();
            }
            return null;
        }
        return recipe;
    }
}
