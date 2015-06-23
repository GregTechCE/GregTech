package gregtech.common.covers;

import gregtech.api.interfaces.tileentity.ICoverable;
import gregtech.api.util.GT_CoverBehavior;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class GT_Cover_SolarPanel
  extends GT_CoverBehavior
{
  private final int mVoltage;
  
  public GT_Cover_SolarPanel(int aVoltage)
  {
    this.mVoltage = aVoltage;
  }
  
  public int doCoverThings(byte aSide, byte aInputRedstone, int aCoverID, int aCoverVariable, ICoverable aTileEntity, long aTimer)
  {
    if (aTimer % 100L == 0L) {
      if ((aSide != 1) || (aTileEntity.getWorld().isThundering()))
      {
        aCoverVariable = 0;
      }
      else
      {
        boolean bRain = (aTileEntity.getWorld().isRaining()) && (aTileEntity.getBiome().rainfall > 0.0F);
        aCoverVariable = ((!bRain) || (aTileEntity.getWorld().skylightSubtracted < 4)) && (aTileEntity.getSkyAtSide(aSide)) ? 1 : (bRain) || (!aTileEntity.getWorld().isDaytime()) ? 2 : 0;
      }
    }
    if ((aCoverVariable == 1) || ((aCoverVariable == 2) && (aTimer % 8L == 0L))) {
      aTileEntity.injectEnergyUnits((byte)6, this.mVoltage, 1L);
    }
    return aCoverVariable;
  }
  
  public int getTickRate(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
  {
    return 1;
  }
}


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.covers.GT_Cover_SolarPanel
 * JD-Core Version:    0.7.0.1
 */