package gregtech.api.unification.stack;

import com.google.common.collect.ImmutableList;

public class ItemMaterialInfo {

    public final MaterialStack material;
    public final ImmutableList<MaterialStack> additionalComponents;

    public ItemMaterialInfo(MaterialStack material, MaterialStack... additionalComponents) {
        this.material = material;
        this.additionalComponents = ImmutableList.copyOf(additionalComponents);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemMaterialInfo that = (ItemMaterialInfo) o;

        if (!material.equals(that.material)) return false;
        return additionalComponents.equals(that.additionalComponents);
    }

    @Override
    public int hashCode() {
        int result = material.hashCode();
        result = 31 * result + additionalComponents.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return material.material.toCamelCaseString();
    }

}