package gregtech.common.covers;

import gregtech.api.interfaces.tileentity.ICoverable;
import gregtech.api.interfaces.tileentity.IMachineProgress;
import gregtech.api.util.GT_CoverBehavior;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.Fluid;

public class GT_Cover_Arm
        extends GT_CoverBehavior {
    public final int mTickRate;

    public GT_Cover_Arm(int aTickRate) {
        this.mTickRate = aTickRate;
    }

    public int doCoverThings(byte aSide, byte aInputRedstone, int aCoverID, int aCoverVariable, ICoverable aTileEntity, long aTimer) {
        if ((aCoverVariable == 0) || (((aTileEntity instanceof IMachineProgress)) && (!((IMachineProgress) aTileEntity).isAllowedToWork()))) {
            return aCoverVariable;
        }
        TileEntity tTileEntity = aTileEntity.getTileEntityAtSide(aSide);
        //aTileEntity.decreaseStoredEnergyUnits(1L, true);
        if (aTileEntity.getUniversalEnergyCapacity() >= 128L) {
            if (aTileEntity.isUniversalEnergyStored(256L)) {
                aTileEntity.decreaseStoredEnergyUnits(4 * GT_Utility.moveOneItemStackIntoSlot(aCoverVariable > 0 ? aTileEntity : tTileEntity, aCoverVariable > 0 ? tTileEntity : aTileEntity, aCoverVariable > 0 ? aSide : GT_Utility.getOppositeSide(aSide), Math.abs(aCoverVariable) - 1, null, false, (byte) 64, (byte) 1, (byte) 64, (byte) 1), true);
            }
        } else {
            GT_Utility.moveOneItemStackIntoSlot(aCoverVariable > 0 ? aTileEntity : tTileEntity, aCoverVariable > 0 ? tTileEntity : aTileEntity, aCoverVariable > 0 ? aSide : GT_Utility.getOppositeSide(aSide), Math.abs(aCoverVariable) - 1, null, false, (byte) 64, (byte) 1, (byte) 64, (byte) 1);
        }
        return aCoverVariable;
    }

    public int onCoverScrewdriverclick(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        if (GT_Utility.getClickedFacingCoords(aSide, aX, aY, aZ)[0] >= 0.5F) {
            aCoverVariable += 16;
        } else {
            aCoverVariable -= 16;
        }
        GT_Utility.sendChatToPlayer(aPlayer, (aCoverVariable > 0 ? "Puts out into adjacent Slot #" : "Grabs in for own Slot #") + (Math.abs(aCoverVariable) - 1));
        return aCoverVariable;
    }

    public boolean onCoverRightclick(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        if (GT_Utility.getClickedFacingCoords(aSide, aX, aY, aZ)[0] >= 0.5F) {
            aCoverVariable++;
        } else {
            aCoverVariable--;
        }
        GT_Utility.sendChatToPlayer(aPlayer, (aCoverVariable > 0 ? "Puts out into adjacent Slot #" : "Grabs in for own Slot #") + (Math.abs(aCoverVariable) - 1));
        aTileEntity.setCoverDataAtSide(aSide, aCoverVariable);
        return true;
    }

    public boolean letsRedstoneGoIn(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return true;
    }

    public boolean letsRedstoneGoOut(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
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

    public boolean alwaysLookConnected(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return true;
    }

    public int getTickRate(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return this.mTickRate;
    }
}
