package gregtech.common.blocks;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class GT_Item_Casings2
  extends GT_Item_Casings_Abstract
{
  public GT_Item_Casings2(Block par1)
  {
    super(par1);
  }
  
  public void addInformation(ItemStack aStack, EntityPlayer aPlayer, List aList, boolean aF3_H)
  {
    super.addInformation(aStack, aPlayer, aList, aF3_H);
    switch (getDamage(aStack))
    {
    case 8: 
      aList.add(this.mBlastProofTooltip);
    }
  }
}


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.blocks.GT_Item_Casings2
 * JD-Core Version:    0.7.0.1
 */