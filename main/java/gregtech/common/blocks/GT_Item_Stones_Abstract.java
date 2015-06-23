package gregtech.common.blocks;

import gregtech.api.GregTech_API;
import gregtech.api.util.GT_LanguageManager;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class GT_Item_Stones_Abstract
  extends ItemBlock
{
  public GT_Item_Stones_Abstract(Block par1)
  {
    super(par1);
    setMaxDamage(0);
    setHasSubtypes(true);
    setCreativeTab(GregTech_API.TAB_GREGTECH_MATERIALS);
  }
  
  public String getUnlocalizedName(ItemStack aStack)
  {
    return this.field_150939_a.getUnlocalizedName() + "." + getDamage(aStack);
  }
  
  public int getMetadata(int aMeta)
  {
    return aMeta;
  }
  
  private final String mNoMobsToolTip = GT_LanguageManager.addStringLocalization("gt.nomobspawnsonthisblock", "Mobs cannot Spawn on this Block");
  
  public void addInformation(ItemStack aStack, EntityPlayer aPlayer, List aList, boolean aF3_H)
  {
    super.addInformation(aStack, aPlayer, aList, aF3_H);
    if (aStack.getItemDamage() % 8 >= 3) {
      aList.add(this.mNoMobsToolTip);
    }
  }
}


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.blocks.GT_Item_Stones_Abstract
 * JD-Core Version:    0.7.0.1
 */