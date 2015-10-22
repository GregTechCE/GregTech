package gregtech.common.items.behaviors;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.items.GT_MetaBase_Item;
import gregtech.api.util.GT_Utility;
import gregtech.api.util.GT_Utility.ItemNBT;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Behaviour_WrittenBook
  extends Behaviour_None
{
  @SideOnly(Side.CLIENT)
  public boolean onItemUse(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide, float hitX, float hitY, float hitZ)
  {
    if ((GT_Utility.isStringValid(GT_Utility.ItemNBT.getBookTitle(aStack))) && ((aPlayer instanceof EntityPlayerSP))) {
      Minecraft.getMinecraft().displayGuiScreen(new GuiScreenBook(aPlayer, aStack, false));
    }
    return true;
  }
  
  public List<String> getAdditionalToolTips(GT_MetaBase_Item aItem, List<String> aList, ItemStack aStack)
  {
    String tTitle = GT_Utility.ItemNBT.getBookTitle(aStack);
    if (GT_Utility.isStringValid(tTitle))
    {
      aList.add(tTitle);
      aList.add("by " + GT_Utility.ItemNBT.getBookAuthor(aStack));
    }
    return aList;
  }
}


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.items.behaviors.Behaviour_WrittenBook
 * JD-Core Version:    0.7.0.1
 */