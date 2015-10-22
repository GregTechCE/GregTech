package gregtech.common.covers;

import gregtech.api.interfaces.tileentity.ICoverable;
import gregtech.api.interfaces.tileentity.IMachineProgress;
import gregtech.api.util.GT_CoverBehavior;
import gregtech.api.util.GT_Utility;

public class GT_Cover_Vent
        extends GT_CoverBehavior {
    private final int mEfficiency;

    public GT_Cover_Vent(int aEfficiency) {
        this.mEfficiency = aEfficiency;
    }

    public int doCoverThings(byte aSide, byte aInputRedstone, int aCoverID, int aCoverVariable, ICoverable aTileEntity, long aTimer) {
        if ((aTileEntity instanceof IMachineProgress)) {
            if ((((IMachineProgress) aTileEntity).hasThingsToDo()) && (aCoverVariable != ((IMachineProgress) aTileEntity).getProgress()) &&
                    (!GT_Utility.hasBlockHitBox(aTileEntity.getWorld(), aTileEntity.getOffsetX(aSide, 1), aTileEntity.getOffsetY(aSide, 1), aTileEntity.getOffsetZ(aSide, 1)))) {
                ((IMachineProgress) aTileEntity).increaseProgress(this.mEfficiency);
            }
            return ((IMachineProgress) aTileEntity).getProgress();
        }
        return 0;
    }

    public boolean alwaysLookConnected(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return true;
    }

    public int getTickRate(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return 60;
    }
}
