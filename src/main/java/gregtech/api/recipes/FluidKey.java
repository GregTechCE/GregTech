package gregtech.api.recipes;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.util.Objects;

public class FluidKey {

    public final Fluid fluid;
    public final NBTTagCompound tag;

    public FluidKey(FluidStack fluidStack) {
        this.fluid = fluidStack.getFluid();
        this.tag = fluidStack.tag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FluidKey)) return false;
        FluidKey fluidKey = (FluidKey) o;
        return Objects.equals(fluid, fluidKey.fluid) &&
            Objects.equals(tag, fluidKey.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fluid, tag);
    }

    @Override
    public String toString() {
        return "FluidKey{" +
            "fluid=" + fluid.getName() +
            ", tag=" + tag +
            '}';
    }
}
