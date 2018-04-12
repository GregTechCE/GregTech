package gregtech.api.cable;

import gregtech.api.unification.material.type.MetalMaterial;

import java.util.Objects;

public class WireProperties {

    public final MetalMaterial material;
    public final int voltage;
    public final int amperage;
    public final int lossPerBlock;

    public WireProperties(MetalMaterial material, int voltage, int baseAmperage, int lossPerBlock) {
        this.material = material;
        this.voltage = voltage;
        this.amperage = baseAmperage;
        this.lossPerBlock = lossPerBlock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WireProperties)) return false;
        WireProperties that = (WireProperties) o;
        return voltage == that.voltage &&
            amperage == that.amperage &&
            lossPerBlock == that.lossPerBlock &&
            material == that.material;
    }

    @Override
    public int hashCode() {
        return hashCode(material.toString(), voltage, amperage, lossPerBlock);
    }

    public static int hashCode(String material, int voltage, int amperage, int lossPerBlock) {
        return Objects.hash(material, voltage, amperage, lossPerBlock);
    }
}
