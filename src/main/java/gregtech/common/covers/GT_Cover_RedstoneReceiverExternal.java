package gregtech.common.covers;

import gregtech.api.GregTech_API;
import gregtech.api.interfaces.tileentity.ICoverable;

public class GT_Cover_RedstoneReceiverExternal
        extends GT_Cover_RedstoneWirelessBase {
    public int doCoverThings(byte aSide, byte aInputRedstone, int aCoverID, int aCoverVariable, ICoverable aTileEntity, long aTimer) {
        aTileEntity.setOutputRedstoneSignal(aSide, GregTech_API.sWirelessRedstone.get(Integer.valueOf(aCoverVariable)) == null ? 0 : ((Byte) GregTech_API.sWirelessRedstone.get(Integer.valueOf(aCoverVariable))).byteValue());
        return aCoverVariable;
    }

    public boolean manipulatesSidedRedstoneOutput(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return true;
    }

    public int getTickRate(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return 1;
    }
}
