package gregtech.common.covers;

import gregtech.api.interfaces.tileentity.ICoverable;
import gregtech.api.util.GT_CoverBehavior;

public class GT_Cover_Lens
        extends GT_CoverBehavior {
    private final byte mColor;

    public GT_Cover_Lens(byte aColor) {
        this.mColor = aColor;
    }

    public byte getLensColor(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return this.mColor;
    }
}
