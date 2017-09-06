package gregtech.api.unification.stack;

import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.material.type.Material;

public class UnificationEntry {

    public final OrePrefix orePrefix;
    public final Material material;

    public UnificationEntry(OrePrefix orePrefix, Material material) {
        this.orePrefix = orePrefix;
        this.material = material;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UnificationEntry that = (UnificationEntry) o;

        if (orePrefix != that.orePrefix) return false;
        return material.equals(that.material);
    }

    @Override
    public int hashCode() {
        int result = orePrefix.hashCode();
        result = 31 * result + material.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return orePrefix.name() + material.toCamelCaseString();
    }

}
