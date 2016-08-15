package gregtech.api.objects;

import gregtech.api.enums.Materials;

public class MaterialStack implements Cloneable {
    public long mAmount;
    public Materials mMaterial;

    public MaterialStack(Materials aMaterial, long aAmount) {
        mMaterial = aMaterial == null ? Materials._NULL : aMaterial;
        mAmount = aAmount;
    }

    public MaterialStack copy(long aAmount) {
        return new MaterialStack(mMaterial, aAmount);
    }

    @Override
    public MaterialStack clone() {
        try { return (MaterialStack) super.clone(); } catch (Exception e) { return new MaterialStack(mMaterial, mAmount); }
    }

    @Override
    public boolean equals(Object aObject) {
        if (aObject == this) return true;
        if (aObject == null) return false;
        if (aObject instanceof Materials) return aObject == mMaterial;
        if (aObject instanceof MaterialStack)
            return ((MaterialStack) aObject).mMaterial == mMaterial && (mAmount < 0 || ((MaterialStack) aObject).mAmount < 0 || ((MaterialStack) aObject).mAmount == mAmount);
        return false;
    }

    @Override
    public String toString() {
         String temp1 = "", temp2 = mMaterial.getToolTip(true), temp3 = "", temp4 = "";
         if (mAmount > 1) {
             temp4 = String.valueOf(mAmount);
             if (mMaterial.mMaterialList.size() > 1) {
                temp1 = "(";
                temp3 = ")";
             }
         }
        return String.valueOf(new StringBuilder().append(temp1).append(temp2).append(temp3).append(temp4));
    }

    @Override
    public int hashCode() {
        return mMaterial.hashCode();
    }
}