package gregtech.nei;

import codechicken.nei.api.IConfigureNEI;
import gregtech.api.util.GT_Recipe;

public class NEI_GT_Config
  implements IConfigureNEI
{
  public static boolean sIsAdded = true;
  
  public void loadConfig()
  {
    sIsAdded = false;
    for (GT_Recipe.GT_Recipe_Map tMap : GT_Recipe.GT_Recipe_Map.sMappings) {
      if (tMap.mNEIAllowed) {
        new GT_NEI_DefaultHandler(tMap);
      }
    }
    sIsAdded = true;
  }
  
  public String getName()
  {
    return "GregTech NEI Plugin";
  }
  
  public String getVersion()
  {
    return "(5.03a)";
  }
}


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.nei.NEI_GT_Config
 * JD-Core Version:    0.7.0.1
 */