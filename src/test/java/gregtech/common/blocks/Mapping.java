package gregtech.common.blocks;

import gregtech.api.unification.material.type.*;

/**
 * Helper class for tracking MetaBlock sub-block mapping data and printing them nicely.
 */
public class Mapping implements Comparable<Mapping> {
    /** Index of the MetaBlock within its category */
    public final int metaBlockNumber;
    /** Index of a material within the MetaBlock */
    public final int materialIndex;
    /** The Material this mapping pertains to */
    public final Material material;

    public Mapping(int metaBlockNumber, int materialIndex, Material material) {
        this.metaBlockNumber = metaBlockNumber;
        this.materialIndex = materialIndex;
        this.material = material;
    }

    public String toString() {
        return String.format("_%d:%d \"%s\"", metaBlockNumber, materialIndex, material);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Mapping))
            return false;
        Mapping o = (Mapping) other;
        return (other == this ||
            (o.metaBlockNumber == this.metaBlockNumber &&
                o.materialIndex == this.materialIndex &&
                o.material.compareTo(this.material) == 0));
    }

    @Override public int compareTo(Mapping other) {
        int order = Integer.compare(this.metaBlockNumber, other.metaBlockNumber);
        if(order == 0)
            order = Integer.compare(this.materialIndex, other.materialIndex);
        if(order == 0)
            order = this.material.compareTo(other.material);
        return order;
    }
}