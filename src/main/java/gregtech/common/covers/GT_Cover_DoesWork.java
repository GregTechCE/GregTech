package gregtech.common.covers;

import gregtech.api.interfaces.tileentity.ICoverable;
import gregtech.api.interfaces.tileentity.IMachineProgress;
import gregtech.api.util.GT_CoverBehavior;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fluids.Fluid;

public class GT_Cover_DoesWork
        extends GT_CoverBehavior {
    public int doCoverThings(byte aSide, byte aInputRedstone, int aCoverID, int aCoverVariable, ICoverable aTileEntity, long aTimer) {
        if ((aTileEntity instanceof IMachineProgress)) {
            if (aCoverVariable < 2) {
                int tScale = ((IMachineProgress) aTileEntity).getMaxProgress() / 15;
                if ((tScale > 0) && (((IMachineProgress) aTileEntity).hasThingsToDo())) {
                    aTileEntity.setOutputRedstoneSignal(aSide, aCoverVariable % 2 == 0 ? (byte) (((IMachineProgress) aTileEntity).getProgress() / tScale) : (byte) (15 - ((IMachineProgress) aTileEntity).getProgress() / tScale));
                } else {
                    aTileEntity.setOutputRedstoneSignal(aSide, (byte) (aCoverVariable % 2 == 0 ? 0 : 15));
                }
            } else {
                aTileEntity.setOutputRedstoneSignal(aSide, (byte) ((aCoverVariable % 2 == 0 ? 1 : 0) != (((IMachineProgress) aTileEntity).getMaxProgress() == 0 ? 1 : 0) ? 0 : 15));
            }
        } else {
            aTileEntity.setOutputRedstoneSignal(aSide, (byte) 0);
        }
        return aCoverVariable;
    }

    public int onCoverScrewdriverclick(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        aCoverVariable = (aCoverVariable + (aPlayer.isSneaking()? -1 : 1)) % 4;
        if(aCoverVariable <0){aCoverVariable = 3;}
        switch(aCoverVariable) {
            case 0: GT_Utility.sendChatToPlayer(aPlayer, "Normal"); break;
            case 1: GT_Utility.sendChatToPlayer(aPlayer, "Inverted"); break;
            case 2: GT_Utility.sendChatToPlayer(aPlayer, "Ready to work"); break;
            case 3: GT_Utility.sendChatToPlayer(aPlayer, "Not ready to work"); break;
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

    public boolean manipulatesSidedRedstoneOutput(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return true;
    }

    public int getTickRate(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return 5;
    }
}
