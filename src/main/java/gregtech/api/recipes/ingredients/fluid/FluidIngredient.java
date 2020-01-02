package gregtech.api.recipes.ingredients.fluid;

import net.minecraftforge.fluids.FluidStack;

import java.util.function.Predicate;

public class FluidIngredient implements Predicate<FluidStack> {

    private final FluidStack[] matchingStacks;

    public FluidIngredient(FluidStack... matchingStacks) {
        this.matchingStacks = matchingStacks;
    }

    public FluidStack[] getMatchingStacks() {
        return matchingStacks;
    }

    @Override
    public boolean test(FluidStack fluidStack) {
        for (FluidStack matchingStack : getMatchingStacks()) {
            if (matchingStack.isFluidEqual(fluidStack))
                return true;
        }
        return false;
    }

}
