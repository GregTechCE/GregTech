package gregtech.api.recipes.machines;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.builders.SimpleRecipeBuilder;
import gregtech.api.util.GTUtility;
import gregtech.common.items.PotionFluids;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.List;

public class RecipeMapBrewer extends RecipeMap<SimpleRecipeBuilder> {

    private static final int POTION_PER_INGREDIENT = PotionFluids.POTION_ITEM_FLUID_AMOUNT * 10;

    public RecipeMapBrewer(String unlocalizedName, int minInputs, int maxInputs, int minOutputs, int maxOutputs, int minFluidInputs, int maxFluidInputs, int minFluidOutputs, int maxFluidOutputs, int amperage, SimpleRecipeBuilder defaultRecipe) {
        super(unlocalizedName, minInputs, maxInputs, minOutputs, maxOutputs, minFluidInputs, maxFluidInputs, minFluidOutputs, maxFluidOutputs, amperage, defaultRecipe);
    }

    @Override
    public boolean canInputFluidForce(Fluid fluid) {
        return PotionFluids.getPotionForFluid(fluid) != null;
    }

    @Nullable
    @Override
    public Recipe findRecipe(long voltage, List<ItemStack> inputs, List<FluidStack> fluidInputs) {
        Recipe recipe = super.findRecipe(voltage, inputs, fluidInputs);
        if (recipe != null || GTUtility.amountOfNonNullElements(fluidInputs) < 1 || GTUtility.amountOfNonEmptyStacks(inputs) < 1) {
            return recipe;
        }

        ItemStack ingredientStack = inputs.get(0);
        FluidStack potionFluid = fluidInputs.get(0);
        PotionType potionType = PotionFluids.getPotionForFluid(potionFluid.getFluid());

        if (potionType == null || potionFluid.amount < POTION_PER_INGREDIENT) {
            return null; //do not return recipes if not enough fluid or fluid doesn't match
        }

        ItemStack potionStack = new ItemStack(Items.POTIONITEM);
        PotionUtils.addPotionToItemStack(potionStack, potionType);
        ItemStack resultStack = BrewingRecipeRegistry.getOutput(potionStack, ingredientStack);

        if (resultStack.isEmpty() || resultStack.getItem() != Items.POTIONITEM) {
            return null; //if no recipe matches, or output is not a simple potion, return null
        }

        PotionType resultingType = PotionUtils.getPotionFromItem(resultStack);

        if (resultingType == null || resultingType == PotionTypes.EMPTY) {
            return null; //if output is not a simple potion or empty potion, return null
        }

        Fluid outputFluid = PotionFluids.getFluidForPotion(resultingType);

        //otherwise, return recipe for fluid potion + ingredient -> new fluid potion
        return recipeBuilder()
            .inputs(GTUtility.copyAmount(1, ingredientStack))
            .fluidInputs(GTUtility.copyAmount(POTION_PER_INGREDIENT, potionFluid))
            .fluidOutputs(new FluidStack(outputFluid, POTION_PER_INGREDIENT))
            .cannotBeBuffered()
            .build().getResult();
    }
}
