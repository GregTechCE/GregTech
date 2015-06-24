package gregtech.common.covers;

import gregtech.api.interfaces.tileentity.ICoverable;
import gregtech.api.util.GT_CoverBehavior;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fluids.Fluid;

public class GT_Cover_EUMeter
  extends GT_CoverBehavior
{
  public int doCoverThings(byte aSide, byte aInputRedstone, int aCoverID, int aCoverVariable, ICoverable aTileEntity, long aTimer)
  {
    long tScale = 0L;
    if (aCoverVariable < 2)
    {
      tScale = aTileEntity.getUniversalEnergyCapacity() / 15L;
      if (tScale > 0L) {
        aTileEntity.setOutputRedstoneSignal(aSide, aCoverVariable % 2 == 0 ? (byte)(int)(aTileEntity.getUniversalEnergyStored() / tScale) : (byte)(int)(15L - aTileEntity.getUniversalEnergyStored() / tScale));
      } else {
        aTileEntity.setOutputRedstoneSignal(aSide, (byte)(aCoverVariable % 2 == 0 ? 0 : 15));
      }
    }
    else if (aCoverVariable < 4)
    {
      tScale = aTileEntity.getEUCapacity() / 15L;
      if (tScale > 0L) {
        aTileEntity.setOutputRedstoneSignal(aSide, aCoverVariable % 2 == 0 ? (byte)(int)(aTileEntity.getStoredEU() / tScale) : (byte)(int)(15L - aTileEntity.getStoredEU() / tScale));
      } else {
        aTileEntity.setOutputRedstoneSignal(aSide, (byte)(aCoverVariable % 2 == 0 ? 0 : 15));
      }
    }
    else if (aCoverVariable < 6)
    {
      tScale = aTileEntity.getSteamCapacity() / 15L;
      if (tScale > 0L) {
        aTileEntity.setOutputRedstoneSignal(aSide, aCoverVariable % 2 == 0 ? (byte)(int)(aTileEntity.getStoredSteam() / tScale) : (byte)(int)(15L - aTileEntity.getStoredSteam() / tScale));
      } else {
        aTileEntity.setOutputRedstoneSignal(aSide, (byte)(aCoverVariable % 2 == 0 ? 0 : 15));
      }
    }
    else if (aCoverVariable < 8)
    {
      tScale = aTileEntity.getInputVoltage() * aTileEntity.getInputAmperage() / 15L;
      if (tScale > 0L) {
        aTileEntity.setOutputRedstoneSignal(aSide, aCoverVariable % 2 == 0 ? (byte)(int)(aTileEntity.getAverageElectricInput() / tScale) : (byte)(int)(15L - aTileEntity.getAverageElectricInput() / tScale));
      } else {
        aTileEntity.setOutputRedstoneSignal(aSide, (byte)(aCoverVariable % 2 == 0 ? 0 : 15));
      }
    }
    else if (aCoverVariable < 10)
    {
      tScale = aTileEntity.getOutputVoltage() * aTileEntity.getOutputAmperage() / 15L;
      if (tScale > 0L) {
        aTileEntity.setOutputRedstoneSignal(aSide, aCoverVariable % 2 == 0 ? (byte)(int)(aTileEntity.getAverageElectricOutput() / tScale) : (byte)(int)(15L - aTileEntity.getAverageElectricOutput() / tScale));
      } else {
        aTileEntity.setOutputRedstoneSignal(aSide, (byte)(aCoverVariable % 2 == 0 ? 0 : 15));
      }
    }
    return aCoverVariable;
  }
  
  public int onCoverScrewdriverclick(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, EntityPlayer aPlayer, float aX, float aY, float aZ)
  {
    aCoverVariable = (aCoverVariable + 1) % 10;
    if (aCoverVariable == 0) {
      GT_Utility.sendChatToPlayer(aPlayer, "Normal Universal Storage");
    }
    if (aCoverVariable == 1) {
      GT_Utility.sendChatToPlayer(aPlayer, "Inverted Universal Storage");
    }
    if (aCoverVariable == 2) {
      GT_Utility.sendChatToPlayer(aPlayer, "Normal Electricity Storage");
    }
    if (aCoverVariable == 3) {
      GT_Utility.sendChatToPlayer(aPlayer, "Inverted Electricity Storage");
    }
    if (aCoverVariable == 4) {
      GT_Utility.sendChatToPlayer(aPlayer, "Normal Steam Storage");
    }
    if (aCoverVariable == 5) {
      GT_Utility.sendChatToPlayer(aPlayer, "Inverted Steam Storage");
    }
    if (aCoverVariable == 6) {
      GT_Utility.sendChatToPlayer(aPlayer, "Normal Average Electric Input");
    }
    if (aCoverVariable == 7) {
      GT_Utility.sendChatToPlayer(aPlayer, "Inverted Average Electric Input");
    }
    if (aCoverVariable == 8) {
      GT_Utility.sendChatToPlayer(aPlayer, "Normal Average Electric Output");
    }
    if (aCoverVariable == 9) {
      GT_Utility.sendChatToPlayer(aPlayer, "Inverted Average Electric Output");
    }
    return aCoverVariable;
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
    return true;
  }
  
  public boolean letsItemsOut(byte aSide, int aCoverID, int aCoverVariable, int aSlot, ICoverable aTileEntity)
  {
    return true;
  }
  
  public boolean manipulatesSidedRedstoneOutput(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
  {
    return true;
  }
  
  public int getTickRate(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
  {
    return 5;
  }
}


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.covers.GT_Cover_EUMeter
 * JD-Core Version:    0.7.0.1
 */