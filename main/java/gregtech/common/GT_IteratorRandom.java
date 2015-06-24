package gregtech.common;

import java.util.Random;

public class GT_IteratorRandom
  extends Random
{
  private static final long serialVersionUID = 1L;
  public int mIterationStep = 2147483647;
  
  public int nextInt(int aParameter)
  {
    if ((this.mIterationStep == 0) || (this.mIterationStep > aParameter)) {
      this.mIterationStep = aParameter;
    }
    return --this.mIterationStep;
  }
}


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.GT_IteratorRandom
 * JD-Core Version:    0.7.0.1
 */