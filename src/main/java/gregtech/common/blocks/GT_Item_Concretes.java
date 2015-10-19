package gregtech.common.blocks;

import gregtech.api.util.GT_LanguageManager;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class GT_Item_Concretes
  extends GT_Item_Stones_Abstract
{
  public GT_Item_Concretes(Block par1)
  {
    super(par1);
  }
  
  private final String mRunFasterToolTip = GT_LanguageManager.addStringLocalization("gt.runfastertooltip", "You can walk faster on this Block");
  
  public void addInformation(ItemStack aStack, EntityPlayer aPlayer, List aList, boolean aF3_H)
  {
    super.addInformation(aStack, aPlayer, aList, aF3_H);
    aList.add(this.mRunFasterToolTip);
  }
}
