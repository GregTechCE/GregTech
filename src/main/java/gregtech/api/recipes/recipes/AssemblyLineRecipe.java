package gregtech.api.recipes.recipes;

import com.google.common.collect.ImmutableList;
import gregtech.api.util.GTUtility;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public class AssemblyLineRecipe {

    private final ItemStack researchItem;
    private final int researchTime;

    private final List<ItemStack> inputs;
    private final List<FluidStack> fluidInputs;
    private final ItemStack output;

    private final int duration;
    private final int EUt;

    public AssemblyLineRecipe(ItemStack researchItem, int researchTime, List<ItemStack> inputs, List<FluidStack> fluidInputs, ItemStack output, int duration, int EUt) {
        this.researchItem = researchItem.copy();
        this.researchTime = researchTime;
        this.inputs = ImmutableList.copyOf(GTUtility.copyStackList(inputs));
        this.fluidInputs = ImmutableList.copyOf(GTUtility.copyFluidList(fluidInputs));
        this.output = output;
        this.duration = duration;
        this.EUt = EUt;
    }

    public ItemStack getResearchItem() {
        return researchItem;
    }

    public int getResearchTime() {
        return researchTime;
    }

    public List<ItemStack> getInputs() {
        return inputs;
    }

    public List<FluidStack> getFluidInputs() {
        return fluidInputs;
    }

    public ItemStack getOutput() {
        return output;
    }

    public int getDuration() {
        return duration;
    }

    public int getEUt() {
        return EUt;
    }
}
