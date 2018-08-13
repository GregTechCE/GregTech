package gregtech.common.pipelike.fluidpipes;

import gregtech.api.pipelike.IPipeLikeTileProperty;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;
import java.util.Objects;

public class FluidPipeProperties implements IPipeLikeTileProperty {

    private int fluidCapacity;
    private int multiple;
    private int heatLimit;
    private boolean isGasProof;

    /**
     * Create an empty property for nbt deserialization
     */
    protected FluidPipeProperties() {}

    public FluidPipeProperties(int fluidCapacity, int heatLimit, boolean isGasProof) {
        this.fluidCapacity = fluidCapacity;
        this.multiple = 1;
        this.heatLimit = heatLimit;
        this.isGasProof = isGasProof;
    }

    FluidPipeProperties(int fluidCapacity, int multiple, int heatLimit, boolean isGasProof) {
        this.fluidCapacity = fluidCapacity;
        this.multiple = multiple;
        this.heatLimit = heatLimit;
        this.isGasProof = isGasProof;
    }

    public int getFluidCapacity() {
        return fluidCapacity;
    }

    public int getMultiple() {
        return multiple;
    }

    public int getHeatLimit() {
        return heatLimit;
    }

    public boolean isGasProof() {
        return isGasProof;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof FluidPipeProperties)) return false;
        FluidPipeProperties that = (FluidPipeProperties) obj;
        return this.fluidCapacity == that.fluidCapacity
            && this.multiple == that.multiple
            && this.heatLimit == that.heatLimit
            && this.isGasProof == that.isGasProof;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fluidCapacity, multiple, heatLimit, isGasProof);
    }

    @Override
    public void addInformation(List<String> tooltip) {
        tooltip.add(multiple == 1 ? I18n.format("gregtech.fluid_pipe.capacity1", fluidCapacity)
            : I18n.format("gregtech.fluid_pipe.capacity2", multiple, fluidCapacity));
        tooltip.add(I18n.format("gregtech.fluid_pipe.heat_limit", heatLimit));
        if (isGasProof) tooltip.add(I18n.format("gregtech.fluid_pipe.gas_proof"));
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setIntArray("FluidPipeProperties", new int[]{fluidCapacity, multiple, heatLimit, isGasProof ? 1 : 0});
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        int[] data = nbt.getIntArray("FluidPipeProperties");
        fluidCapacity = data[0];
        multiple = data[1];
        heatLimit = data[2];
        isGasProof = data[3] != 0;
    }
}
