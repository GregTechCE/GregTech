package gregtech.api.unification.stack;

import com.google.common.collect.ImmutableList;

public class ItemMaterialInfo {

    public final MaterialStack material;
    public final ImmutableList<MaterialStack> byProducts;

    public ItemMaterialInfo(MaterialStack material, MaterialStack... byProducts) {
        this.material = material;
        this.byProducts = ImmutableList.copyOf(byProducts);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemMaterialInfo that = (ItemMaterialInfo) o;

        if (!material.equals(that.material)) return false;
        return byProducts.equals(that.byProducts);
    }

    @Override
    public int hashCode() {
        int result = material.hashCode();
        result = 31 * result + byProducts.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return material.material.toCamelCaseString();
    }

}