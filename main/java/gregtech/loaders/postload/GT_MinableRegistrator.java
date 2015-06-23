package gregtech.loaders.postload;

import gregtech.api.util.GT_Log;
import gregtech.api.util.GT_ModHandler;
import java.io.PrintStream;
import net.minecraft.init.Blocks;

public class GT_MinableRegistrator
  implements Runnable
{
  public void run()
  {
    GT_Log.out.println("GT_Mod: Adding Blocks to the Miners Valuable List.");
    GT_ModHandler.addValuableOre(Blocks.glowstone, 0, 1);
    GT_ModHandler.addValuableOre(Blocks.soul_sand, 0, 1);
  }
}


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.postload.GT_MinableRegistrator
 * JD-Core Version:    0.7.0.1
 */