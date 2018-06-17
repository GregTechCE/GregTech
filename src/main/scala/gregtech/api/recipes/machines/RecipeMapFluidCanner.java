package gregtech.api.recipes.machines;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.builders.DefaultRecipeBuilder;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

import javax.annotation.Nullable;
import java.util.List;

public class RecipeMapFluidCanner extends RecipeMap<DefaultRecipeBuilder> {

    public RecipeMapFluidCanner(String unlocalizedName, int minInputs, int maxInputs, int minOutputs, int maxOutputs, int minFluidInputs, int maxFluidInputs, int minFluidOutputs, int maxFluidOutputs, int amperage, DefaultRecipeBuilder defaultRecipe) {
        super(unlocalizedName, minInputs, maxInputs, minOutputs, maxOutputs, minFluidInputs, maxFluidInputs, minFluidOutputs, maxFluidOutputs, amperage, defaultRecipe);
    }

    @Override
    public boolean canInputFluidForce(Fluid fluid) {
        return true;
    }

    @Override
    @Nullable
    public Recipe findRecipe(long voltage, List<ItemStack> inputs, List<FluidStack> fluidInputs) {
        Recipe recipe = super.findRecipe(voltage, inputs, fluidInputs);
        if (inputs.size() == 0 || inputs.get(0).isEmpty() || recipe != null)
            return recipe;
        ItemStack inputStack = inputs.get(0);
        //we call inputStack.copy() because interacting with capability changes stack itself
        IFluidHandlerItem fluidHandlerItem = inputStack.copy()
            .getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
        if(fluidHandlerItem != null) {
            FluidStack containerFluid = fluidHandlerItem.drain(Integer.MAX_VALUE, true);
            if(containerFluid != null) {
                //if we actually drained something, then it's draining recipe
                return recipeBuilder()
                    .inputs(inputStack)
                    .outputs(fluidHandlerItem.getContainer())
                    .fluidOutputs(containerFluid)
                    .duration(Math.max(1, containerFluid.amount / 10)).EUt(8)
                    .cannotBeBuffered()
                    .build().getResult();
            }
            //if we didn't drain anything, try filling container
            if(!fluidInputs.isEmpty() && fluidInputs.get(0) != null) {
                FluidStack inputFluid = fluidInputs.get(0).copy();
                inputFluid.amount = fluidHandlerItem.fill(inputFluid, true);
                if(inputFluid.amount > 0) {
                    return recipeBuilder()
                        .inputs(inputStack)
                        .fluidInputs(inputFluid)
                        .outputs(fluidHandlerItem.getContainer())
                        .duration(Math.max(1, inputFluid.amount / 10)).EUt(8)
                        .cannotBeBuffered()
                        .build().getResult();
                }

            }
        }
        return null;
    }
}
