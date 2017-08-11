package gregtech.common.covers;

import gregtech.api.capability.ICoverable;
import gregtech.api.capability.IWorkable;
import gregtech.api.metatileentity.GT_CoverBehavior;
import gregtech.api.util.GT_Utility;
import net.minecraft.util.EnumFacing;

public class GT_Cover_Vent
        extends GT_CoverBehavior {
    private final int mEfficiency;

    public GT_Cover_Vent(int aEfficiency) {
        this.mEfficiency = aEfficiency;
    }

    public int doCoverThings(byte aSide, byte aInputRedstone, int aCoverID, int aCoverVariable, ICoverable aTileEntity, long aTimer) {
        if ((aTileEntity instanceof IWorkable)) {
            if ((((IWorkable) aTileEntity).hasThingsToDo()) && (aCoverVariable != ((IWorkable) aTileEntity).getProgress()) &&
                    (!GT_Utility.hasBlockHitBox(aTileEntity.getWorldObj(), aTileEntity.getWorldPos().offset(EnumFacing.VALUES[aSide])))) {
                ((IWorkable) aTileEntity).increaseProgress(this.mEfficiency);
            }
            return ((IWorkable) aTileEntity).getProgress();
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
