package gregtech.common.covers;

import gregtech.api.capability.ICoverable;
import gregtech.api.metatileentity.GT_CoverBehavior;

public class GT_Cover_Blastproof
        extends GT_CoverBehavior {
    private final float mLevel;

    public GT_Cover_Blastproof(float aLevel) {
        this.mLevel = aLevel;
    }

    public float getBlastProofLevel(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return this.mLevel;
    }

    public boolean isSimpleCover() {
        return true;
    }
}
