package gregtech.common.covers;

import gregtech.api.interfaces.tileentity.ICoverable;
import gregtech.api.util.GT_CoverBehavior;

public class GT_Cover_Blastproof
  extends GT_CoverBehavior
{
  private final float mLevel;
  
  public GT_Cover_Blastproof(float aLevel)
  {
    this.mLevel = aLevel;
  }
  
  public float getBlastProofLevel(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
  {
    return this.mLevel;
  }
  
  public boolean isSimpleCover()
  {
    return true;
  }
}


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.covers.GT_Cover_Blastproof
 * JD-Core Version:    0.7.0.1
 */