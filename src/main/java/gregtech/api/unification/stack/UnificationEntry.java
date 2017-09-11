package gregtech.api.unification.stack;

import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.material.type.Material;

import javax.annotation.Nullable;

public class UnificationEntry {

    public final OrePrefix orePrefix;
    @Nullable
    public final Material material;

    public UnificationEntry(OrePrefix orePrefix, @Nullable Material material) {
        this.orePrefix = orePrefix;
        this.material = material;
    }

    public UnificationEntry(OrePrefix orePrefix) {
        this.orePrefix = orePrefix;
        this.material = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UnificationEntry that = (UnificationEntry) o;

        if (orePrefix != that.orePrefix) return false;
        return material != null ? material.equals(that.material) : that.material == null;
    }

    @Override
    public int hashCode() {
        int result = orePrefix.hashCode();
        result = 31 * result + (material != null ? material.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return orePrefix.name() + (material != null ? material.toCamelCaseString() : "");
    }

}
