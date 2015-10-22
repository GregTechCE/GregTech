package gregtech.common.items.behaviors;

import gregtech.api.items.GT_MetaBase_Item;
import gregtech.api.util.GT_Utility;
import gregtech.api.util.GT_Utility.ItemNBT;
import java.util.List;
import net.minecraft.item.ItemStack;

public class Behaviour_DataStick
  extends Behaviour_None
{
  public List<String> getAdditionalToolTips(GT_MetaBase_Item aItem, List<String> aList, ItemStack aStack)
  {
    String tString = GT_Utility.ItemNBT.getBookTitle(aStack);
    if (GT_Utility.isStringValid(tString)) {
      aList.add(tString);
    }
    tString = GT_Utility.ItemNBT.getBookAuthor(aStack);
    if (GT_Utility.isStringValid(tString)) {
      aList.add("by " + tString);
    }
    short tMapID = GT_Utility.ItemNBT.getMapID(aStack);
    if (tMapID >= 0) {
      aList.add("Map ID: " + tMapID);
    }
    tString = GT_Utility.ItemNBT.getPunchCardData(aStack);
    if (GT_Utility.isStringValid(tString))
    {
      aList.add("Punch Card Data");
      int i = 0;
      for (int j = tString.length(); i < j; i += 64) {
        aList.add(tString.substring(i, Math.min(i + 64, j)));
      }
    }
    return aList;
  }
}


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.items.behaviors.Behaviour_DataStick
 * JD-Core Version:    0.7.0.1
 */