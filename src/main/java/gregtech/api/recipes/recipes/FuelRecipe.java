package gregtech.api.recipes.recipes;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.liquid.ILiquidDefinition;
import crafttweaker.api.minecraft.CraftTweakerMC;
import gregtech.api.GTValues;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Optional.Method;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

@ZenClass("mods.gregtech.recipe.FuelRecipe")
@ZenRegister
public class FuelRecipe {

    private final FluidStack recipeFluid;

    private final int duration;

    private final long minVoltage;

    public FuelRecipe(FluidStack recipeFluid, int duration, long minVoltage) {
        this.recipeFluid = recipeFluid;
        this.duration = duration;
        this.minVoltage = minVoltage;
    }

    public FluidStack getRecipeFluid() {
        return recipeFluid;
    }

    @ZenGetter("duration")
    public int getDuration() {
        return duration;
    }

    @ZenGetter("minVoltage")
    public long getMinVoltage() {
        return minVoltage;
    }

    public boolean matches(long maxVoltage, FluidStack inputFluid) {
        return maxVoltage >= getMinVoltage() && getRecipeFluid().isFluidEqual(inputFluid);
    }

    @ZenGetter("fluid")
    @Method(modid = GTValues.MODID_CT)
    public ILiquidDefinition ctGetFluid() {
        return CraftTweakerMC.getILiquidDefinition(recipeFluid.getFluid());
    }
}
