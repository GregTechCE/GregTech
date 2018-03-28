package gregtech.api.recipes.machines;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeBuilder.NotConsumableInputRecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.GTUtility;
import gregtech.common.items.MetaItems;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.List;

public class RecipeMapFormingPress extends RecipeMap<NotConsumableInputRecipeBuilder> {

public RecipeMapFormingPress(String unlocalizedName, int minInputs, int maxInputs, int minOutputs, int maxOutputs, int minFluidInputs, int maxFluidInputs, int minFluidOutputs, int maxFluidOutputs, int amperage, NotConsumableInputRecipeBuilder defaultRecipe) {
super(unlocalizedName, minInputs, maxInputs, minOutputs, maxOutputs, minFluidInputs, maxFluidInputs, minFluidOutputs, maxFluidOutputs, amperage, defaultRecipe);
}

@Override
    @Nullable
    public Recipe findRecipe(long voltage, NonNullList<ItemStack> inputs, List<FluidStack> fluidInputs) {
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
        for (ItemStack mold : inputs) {
            if (MetaItems.SCHEMATIC_CRAFTING.getStackForm().isItemEqual(mold)) {
                NBTTagCompound tag = mold.getTagCompound();
                if (tag == null) tag = new NBTTagCompound();
                if (!tag.hasKey("credit_security_id")) tag.setLong("credit_security_id", System.nanoTime());
                mold.setTagCompound(tag);

                RecipeBuilder<?> builder = this.recipeBuilder()
                        .fromRecipe(recipe)
                        .cannotBeBuffered();

                List<ItemStack> outputs = builder.getOutputs();
                ItemStack stack = outputs.get(0);
                stack.setTagCompound(tag);
                outputs.set(0, stack);

                return builder.build().getResult();
            }
        }
        return recipe;
    }
}
