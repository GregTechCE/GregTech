package gregtech.common.covers;

import gregtech.api.interfaces.tileentity.ICoverable;
import gregtech.api.interfaces.tileentity.IMachineProgress;
import gregtech.api.util.GT_CoverBehavior;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fluids.Fluid;

public class GT_Cover_ControlsWork
        extends GT_CoverBehavior {
    public int doCoverThings(byte aSide, byte aInputRedstone, int aCoverID, int aCoverVariable, ICoverable aTileEntity, long aTimer) {
        if (aTileEntity instanceof IMachineProgress) {
            if ((aInputRedstone > 0) == (aCoverVariable == 0) && aCoverVariable != 2)
                ((IMachineProgress) aTileEntity).enableWorking();
            else
                ((IMachineProgress) aTileEntity).disableWorking();
            ((IMachineProgress) aTileEntity).setWorkDataValue(aInputRedstone);
        }
        return aCoverVariable;
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

    public boolean onCoverRemoval(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, boolean aForced) {
        if ((aTileEntity instanceof IMachineProgress)) {
            ((IMachineProgress) aTileEntity).enableWorking();
            ((IMachineProgress) aTileEntity).setWorkDataValue((byte) 0);
        }
        return true;
    }

    public int onCoverScrewdriverclick(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        aCoverVariable = (aCoverVariable + (aPlayer.isSneaking()? -1 : 1)) % 3;
        if(aCoverVariable <0){aCoverVariable = 2;}
        if (aCoverVariable == 0) {
            GT_Utility.sendChatToPlayer(aPlayer, "Normal");
        }
        if (aCoverVariable == 1) {
            GT_Utility.sendChatToPlayer(aPlayer, "Inverted");
        }
        if (aCoverVariable == 2) {
            GT_Utility.sendChatToPlayer(aPlayer, "No Work at all");
        }
        return aCoverVariable;
    }

    public int getTickRate(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return 1;
    }
}
