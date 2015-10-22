package gregtech.common.blocks;

import gregtech.api.GregTech_API;
import gregtech.api.util.GT_LanguageManager;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public abstract class GT_Item_Casings_Abstract
  extends ItemBlock
{
  public GT_Item_Casings_Abstract(Block par1)
  {
    super(par1);
    setMaxDamage(0);
    setHasSubtypes(true);
    setCreativeTab(GregTech_API.TAB_GREGTECH_MATERIALS);
  }
  
  public int getMetadata(int aMeta)
  {
    return aMeta;
  }
  
  protected final String mNoMobsToolTip = GT_LanguageManager.addStringLocalization("gt.nomobspawnsonthisblock", "Mobs cannot Spawn on this Block");
  protected final String mNoTileEntityToolTip = GT_LanguageManager.addStringLocalization("gt.notileentityinthisblock", "This is NOT a TileEntity!");
  protected final String mCoil01Tooltip = GT_LanguageManager.addStringLocalization("gt.coil01tooltip", "Base Heating Capacity = 1800 Kelvin");
  protected final String mCoil02Tooltip = GT_LanguageManager.addStringLocalization("gt.coil02tooltip", "Base Heating Capacity = 2700 Kelvin");
  protected final String mCoil03Tooltip = GT_LanguageManager.addStringLocalization("gt.coil03tooltip", "Base Heating Capacity = 3600 Kelvin");
  protected final String mBlastProofTooltip = GT_LanguageManager.addStringLocalization("gt.blastprooftooltip", "This Block is Blast Proof");
  
  public String getUnlocalizedName(ItemStack aStack)
  {
    return this.field_150939_a.getUnlocalizedName() + "." + getDamage(aStack);
  }
  
  public void addInformation(ItemStack aStack, EntityPlayer aPlayer, List aList, boolean aF3_H)
  {
    super.addInformation(aStack, aPlayer, aList, aF3_H);
    aList.add(this.mNoMobsToolTip);
    aList.add(this.mNoTileEntityToolTip);
  }
}


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.blocks.GT_Item_Casings_Abstract
 * JD-Core Version:    0.7.0.1
 */