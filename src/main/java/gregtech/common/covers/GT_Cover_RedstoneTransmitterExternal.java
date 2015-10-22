package gregtech.common.covers;

import gregtech.api.GregTech_API;
import gregtech.api.interfaces.tileentity.ICoverable;

public class GT_Cover_RedstoneTransmitterExternal
        extends GT_Cover_RedstoneWirelessBase {
    public int doCoverThings(byte aSide, byte aInputRedstone, int aCoverID, int aCoverVariable, ICoverable aTileEntity, long aTimer) {
        GregTech_API.sWirelessRedstone.put(Integer.valueOf(aCoverVariable), Byte.valueOf(aInputRedstone));
        return aCoverVariable;
    }

    public boolean letsRedstoneGoIn(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return true;
    }

    public int getTickRate(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return 1;
    }
}
