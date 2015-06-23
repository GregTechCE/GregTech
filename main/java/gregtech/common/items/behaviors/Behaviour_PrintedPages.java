package gregtech.common.items.behaviors;

import gregtech.api.items.GT_MetaBase_Item;
import gregtech.api.util.GT_Utility;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class Behaviour_PrintedPages
  extends Behaviour_None
{
  public List<String> getAdditionalToolTips(GT_MetaBase_Item aItem, List<String> aList, ItemStack aStack)
  {
    if (GT_Utility.isStringValid(getTitle(aStack))) {
      aList.add(getTitle(aStack));
    }
    if (GT_Utility.isStringValid(getAuthor(aStack))) {
      aList.add("by " + getAuthor(aStack));
    }
    return aList;
  }
  
  public static String getTitle(ItemStack aStack)
  {
    NBTTagCompound tNBT = aStack.getTagCompound();
    if (tNBT == null) {
      return "";
    }
    return tNBT.getString("title");
  }
  
  public static String getAuthor(ItemStack aStack)
  {
    NBTTagCompound tNBT = aStack.getTagCompound();
    if (tNBT == null) {
      return "";
    }
    return tNBT.getString("author");
  }
}


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.items.behaviors.Behaviour_PrintedPages
 * JD-Core Version:    0.7.0.1
 */