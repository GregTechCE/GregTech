package gregtech.api.recipes.recipes;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mc1120.liquid.MCLiquidStack;
import gregtech.api.GTValues;
import gregtech.api.recipes.CountableIngredient;
import gregtech.api.recipes.crafttweaker.InputIngredient;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Optional.Method;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

@ZenClass("mods.gregtech.recipe.CokeOvenRecipe")
@ZenRegister
public class CokeOvenRecipe {

    private final CountableIngredient input;
    private final ItemStack output;
    private final FluidStack fluidOutput;
    private final int duration;

    public CokeOvenRecipe(CountableIngredient input, ItemStack output, FluidStack fluidOutput, int duration) {
        this.input = input;
        this.output = output;
        this.fluidOutput = fluidOutput;
        this.duration = duration;
    }

    public CountableIngredient getInput() {
        return input;
    }

    public ItemStack getOutput() {
        return output;
    }

    public FluidStack getFluidOutput() {
        return fluidOutput;
    }

    @ZenGetter("duration")
    public int getDuration() {
        return duration;
    }

    @ZenGetter("input")
    @Method(modid = GTValues.MODID_CT)
    public InputIngredient ctGetInput() {
        return new InputIngredient(getInput());
    }

    @ZenGetter("output")
    @Method(modid = GTValues.MODID_CT)
    public IItemStack ctGetOutput() {
        return CraftTweakerMC.getIItemStack(getOutput());
    }

    @ZenGetter("fluidOutput")
    @Method(modid = GTValues.MODID_CT)
    public ILiquidStack ctGetLiquidOutput() {
        return new MCLiquidStack(getFluidOutput());
    }

}
