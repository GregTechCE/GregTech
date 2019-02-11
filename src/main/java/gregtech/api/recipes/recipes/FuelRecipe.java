package gregtech.api.recipes.recipes;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mc1120.liquid.MCLiquidStack;
import gregtech.api.GTValues;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Optional.Method;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.gregtech.recipe.FuelRecipe")
@ZenRegister
public class FuelRecipe {

    private final FluidStack recipeFluid;
    private final int duration;
    private final long minVoltage;

    public FuelRecipe(FluidStack recipeFluid, int duration, long minVoltage) {
        this.recipeFluid = recipeFluid.copy();
        this.duration = duration;
        this.minVoltage = minVoltage;
    }

    @ZenMethod("create")
    @Method(modid = GTValues.MODID_CT)
    public static FuelRecipe craftTweakerCreate(ILiquidStack liquidStack, int duration, long minVoltage) {
        return new FuelRecipe(CraftTweakerMC.getLiquidStack(liquidStack), duration, minVoltage);
    }

    public FluidStack getRecipeFluid() {
        return recipeFluid.copy();
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
    public ILiquidStack ctGetFluid() {
        return new MCLiquidStack(getRecipeFluid());
    }
}
