package gregtech.common.covers;

import gregtech.api.interfaces.tileentity.ICoverable;
import gregtech.api.interfaces.tileentity.IMachineProgress;
import gregtech.api.util.GT_CoverBehavior;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.Fluid;

public class GT_Cover_Conveyor
  extends GT_CoverBehavior
{
  public final int mTickRate;
  
  public GT_Cover_Conveyor(int aTickRate)
  {
    this.mTickRate = aTickRate;
  }
  
  public int doCoverThings(byte aSide, byte aInputRedstone, int aCoverID, int aCoverVariable, ICoverable aTileEntity, long aTimer)
  {
    if ((aCoverVariable % 6 > 1) && ((aTileEntity instanceof IMachineProgress))) {
      if (((IMachineProgress)aTileEntity).isAllowedToWork() != aCoverVariable % 6 < 4) {
        return aCoverVariable;
      }
    }
    TileEntity tTileEntity = aTileEntity.getTileEntityAtSide(aSide);
    aTileEntity.decreaseStoredEnergyUnits(1L, true);
    if (((aCoverVariable % 2 != 1) || (aSide != 1)) && ((aCoverVariable % 2 != 0) || (aSide != 0)) && (aTileEntity.getUniversalEnergyCapacity() >= 128L))
    {
      if (aTileEntity.isUniversalEnergyStored(256L)) {
        aTileEntity.decreaseStoredEnergyUnits(4 * GT_Utility.moveOneItemStack(aCoverVariable % 2 == 0 ? aTileEntity : tTileEntity, aCoverVariable % 2 != 0 ? aTileEntity : tTileEntity, aCoverVariable % 2 != 0 ? GT_Utility.getOppositeSide(aSide) : aSide, aCoverVariable % 2 == 0 ? GT_Utility.getOppositeSide(aSide) : aSide, null, false, (byte)64, (byte)1, (byte)64, (byte)1), true);
      }
    }
    else {
      GT_Utility.moveOneItemStack(aCoverVariable % 2 == 0 ? aTileEntity : tTileEntity, aCoverVariable % 2 != 0 ? aTileEntity : tTileEntity, aCoverVariable % 2 != 0 ? GT_Utility.getOppositeSide(aSide) : aSide, aCoverVariable % 2 == 0 ? GT_Utility.getOppositeSide(aSide) : aSide, null, false, (byte)64, (byte)1, (byte)64, (byte)1);
    }
    return aCoverVariable;
  }
  
  public int onCoverScrewdriverclick(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, EntityPlayer aPlayer, float aX, float aY, float aZ)
  {
    aCoverVariable = (aCoverVariable + 1) % 12;
    if (aCoverVariable == 0) {
      GT_Utility.sendChatToPlayer(aPlayer, "Export");
    }
    if (aCoverVariable == 1) {
      GT_Utility.sendChatToPlayer(aPlayer, "Import");
    }
    if (aCoverVariable == 2) {
      GT_Utility.sendChatToPlayer(aPlayer, "Export (conditional)");
    }
    if (aCoverVariable == 3) {
      GT_Utility.sendChatToPlayer(aPlayer, "Import (conditional)");
    }
    if (aCoverVariable == 4) {
      GT_Utility.sendChatToPlayer(aPlayer, "Export (invert cond)");
    }
    if (aCoverVariable == 5) {
      GT_Utility.sendChatToPlayer(aPlayer, "Import (invert cond)");
    }
    if (aCoverVariable == 6) {
      GT_Utility.sendChatToPlayer(aPlayer, "Export allow Input");
    }
    if (aCoverVariable == 7) {
      GT_Utility.sendChatToPlayer(aPlayer, "Import allow Output");
    }
    if (aCoverVariable == 8) {
      GT_Utility.sendChatToPlayer(aPlayer, "Export allow Input (conditional)");
    }
    if (aCoverVariable == 9) {
      GT_Utility.sendChatToPlayer(aPlayer, "Import allow Output (conditional)");
    }
    if (aCoverVariable == 10) {
      GT_Utility.sendChatToPlayer(aPlayer, "Export allow Input (invert cond)");
    }
    if (aCoverVariable == 11) {
      GT_Utility.sendChatToPlayer(aPlayer, "Import allow Output (invert cond)");
    }
    return aCoverVariable;
  }
  
  public boolean letsRedstoneGoIn(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
  {
    return true;
  }
  
  public boolean letsRedstoneGoOut(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
  {
    return true;
  }
  
  public boolean letsEnergyIn(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
  {
    return true;
  }
  
  public boolean letsEnergyOut(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
  {
    return true;
  }
  
  public boolean letsFluidIn(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity)
  {
    return true;
  }
  
  public boolean letsFluidOut(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity)
  {
    return true;
  }
  
  public boolean letsItemsIn(byte aSide, int aCoverID, int aCoverVariable, int aSlot, ICoverable aTileEntity)
  {
    return (aCoverVariable >= 6) || (aCoverVariable % 2 != 0);
  }
  
  public boolean letsItemsOut(byte aSide, int aCoverID, int aCoverVariable, int aSlot, ICoverable aTileEntity)
  {
    return (aCoverVariable >= 6) || (aCoverVariable % 2 == 0);
  }
  
  public boolean alwaysLookConnected(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
  {
    return true;
  }
  
  public int getTickRate(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
  {
    return this.mTickRate;
  }
}


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.covers.GT_Cover_Conveyor
 * JD-Core Version:    0.7.0.1
 */