package gregtech.common.covers;

import gregtech.api.interfaces.tileentity.ICoverable;
import gregtech.api.util.GT_CoverBehavior;

public class GT_Cover_SolarPanel
        extends GT_CoverBehavior {
    private final int mVoltage;

    public GT_Cover_SolarPanel(int aVoltage) {
        this.mVoltage = aVoltage;
    }

    public int doCoverThings(byte aSide, byte aInputRedstone, int aCoverID, int aCoverVariable, ICoverable aTileEntity, long aTimer) {
        if (aTimer % 100L == 0L) {
            if ((aSide != 1) || (aTileEntity.getWorld().isThundering())) {
                aCoverVariable = 0;
            } else {
                boolean bRain = (aTileEntity.getWorld().isRaining()) && (aTileEntity.getBiome().rainfall > 0.0F);
                aCoverVariable = bRain && aTileEntity.getWorld().skylightSubtracted >= 4 || !aTileEntity.getSkyAtSide(aSide) ? 0 : ((int) (!bRain && aTileEntity.getWorld().isDaytime() ? 1 : 2));
            }
        }
        if ((aCoverVariable == 1) || ((aCoverVariable == 2) && (aTimer % 8L == 0L))) {
            aTileEntity.injectEnergyUnits((byte) 6, this.mVoltage, 1L);
        }
        return aCoverVariable;
    }

    public int getTickRate(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return 1;
    }
}