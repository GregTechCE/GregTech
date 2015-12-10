package gregtech.common.covers;

import gregtech.api.interfaces.tileentity.ICoverable;
import gregtech.api.util.GT_CoverBehavior;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fluids.Fluid;

public class GT_Cover_RedstoneConductor
        extends GT_CoverBehavior {
    public int doCoverThings(byte aSide, byte aInputRedstone, int aCoverID, int aCoverVariable, ICoverable aTileEntity, long aTimer) {
        if (aCoverVariable == 0) {
            aTileEntity.setOutputRedstoneSignal(aSide, aTileEntity.getStrongestRedstone());
        } else if (aCoverVariable < 7) {
            aTileEntity.setOutputRedstoneSignal(aSide, aTileEntity.getInternalInputRedstoneSignal((byte) (aCoverVariable - 1)));
        }
        return aCoverVariable;
    }

    public int onCoverScrewdriverclick(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        aCoverVariable = (aCoverVariable + (aPlayer.isSneaking()? -1 : 1)) % 7;
        if(aCoverVariable <0){aCoverVariable = 6;}
        switch (aCoverVariable) {
            case 0: GT_Utility.sendChatToPlayer(aPlayer, "Conducts strongest Input"); break;
            case 1: GT_Utility.sendChatToPlayer(aPlayer, "Conducts from bottom Input"); break;
            case 2: GT_Utility.sendChatToPlayer(aPlayer, "Conducts from top Input"); break;
            case 3: GT_Utility.sendChatToPlayer(aPlayer, "Conducts from north Input"); break;
            case 4: GT_Utility.sendChatToPlayer(aPlayer, "Conducts from south Input"); break;
            case 5: GT_Utility.sendChatToPlayer(aPlayer, "Conducts from west Input"); break;
            case 6: GT_Utility.sendChatToPlayer(aPlayer, "Conducts from east Input"); break;
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
        return 1;
    }
}
