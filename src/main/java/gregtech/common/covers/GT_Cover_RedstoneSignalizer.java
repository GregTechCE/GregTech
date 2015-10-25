package gregtech.common.covers;

import gregtech.api.interfaces.tileentity.ICoverable;
import gregtech.api.interfaces.tileentity.IMachineProgress;
import gregtech.api.util.GT_CoverBehavior;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fluids.Fluid;

public class GT_Cover_RedstoneSignalizer
        extends GT_CoverBehavior {
    public int onCoverScrewdriverclick(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        aCoverVariable = (aCoverVariable + 1) % 48;
        switch(aCoverVariable / 16) {
            case 0: GT_Utility.sendChatToPlayer(aPlayer, "Signal = " + (aCoverVariable & 0xF)); break;
            case 1: GT_Utility.sendChatToPlayer(aPlayer, "Conditional Signal = " + (aCoverVariable & 0xF)); break;
            case 2: GT_Utility.sendChatToPlayer(aPlayer, "Inverted Conditional Signal = " + (aCoverVariable & 0xF)); break;
        }
        return aCoverVariable;
    }

    public boolean letsRedstoneGoIn(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return true;
    }

    public boolean letsEnergyIn(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return true;
    }

    public boolean letsEnergyOut(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return true;
    }

    public boolean letsFluidIn(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity) {
        return true;
    }

    public boolean letsFluidOut(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity) {
        return true;
    }

    public boolean letsItemsIn(byte aSide, int aCoverID, int aCoverVariable, int aSlot, ICoverable aTileEntity) {
        return true;
    }

    public boolean letsItemsOut(byte aSide, int aCoverID, int aCoverVariable, int aSlot, ICoverable aTileEntity) {
        return true;
    }

    public byte getRedstoneInput(byte aSide, byte aInputRedstone, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        if (aCoverVariable < 16) {
            return (byte) (aCoverVariable & 0xF);
        }
        if ((aTileEntity instanceof IMachineProgress)) {
            if (((IMachineProgress) aTileEntity).isAllowedToWork()) {
                if (aCoverVariable / 16 == 1) {
                    return (byte) (aCoverVariable & 0xF);
                }
            } else if (aCoverVariable / 16 == 2) {
                return (byte) (aCoverVariable & 0xF);
            }
            return 0;
        }
        return (byte) (aCoverVariable & 0xF);
    }
}
