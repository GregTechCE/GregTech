package gregtech.common.pipelike.fluidpipe;

import java.util.Objects;

public class FluidPipeProperties {

    public final int capacity;
    public final int maxFluidTemperature;
    public final int throughput;
    public final boolean gasProof;
    public final boolean opaque;

    public FluidPipeProperties(int capacity, int maxFluidTemperature, int throughput, boolean gasProof, boolean opaque) {
        this.capacity = capacity;
        this.maxFluidTemperature = maxFluidTemperature;
        this.throughput = throughput;
        this.gasProof = gasProof;
        this.opaque = opaque;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FluidPipeProperties)) return false;
        FluidPipeProperties that = (FluidPipeProperties) o;
        return capacity == that.capacity &&
            maxFluidTemperature == that.maxFluidTemperature &&
            throughput == that.throughput &&
            opaque == that.opaque;
    }

    @Override
    public int hashCode() {
        return Objects.hash(capacity, maxFluidTemperature, throughput, opaque);
    }

    @Override
    public String toString() {
        return "FluidPipeProperties{" +
            "capacity=" + capacity +
            ", maxFluidTemperature=" + maxFluidTemperature +
            ", throughput=" + throughput +
            ", opaque=" + opaque +
            '}';
    }
}
