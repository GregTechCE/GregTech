package gregtech.common.items.behaviors;

import gregtech.api.GregTech_API;
import gregtech.api.interfaces.IItemBehaviour;
import gregtech.api.items.GT_MetaBase_Item;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_Utility;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Behaviour_Scanner
  extends Behaviour_None
{
  public static final IItemBehaviour<GT_MetaBase_Item> INSTANCE = new Behaviour_Scanner();
  
  public boolean onItemUseFirst(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide, float hitX, float hitY, float hitZ)
  {
    if (((aPlayer instanceof EntityPlayerMP)) && (aItem.canUse(aStack, 20000.0D)))
    {
      ArrayList<String> tList = new ArrayList();
      if (aItem.use(aStack, GT_Utility.getCoordinateScan(tList, aPlayer, aWorld, 1, aX, aY, aZ, aSide, hitX, hitY, hitZ), aPlayer)) {
        for (int i = 0; i < tList.size(); i++) {
          GT_Utility.sendChatToPlayer(aPlayer, (String)tList.get(i));
        }
      }
      return true;
    }
    GT_Utility.doSoundAtClient((String)GregTech_API.sSoundList.get(Integer.valueOf(108)), 1, 1.0F, aX, aY, aZ);
    return aPlayer instanceof EntityPlayerMP;
  }
  
  private final String mTooltip = GT_LanguageManager.addStringLocalization("gt.behaviour.scanning", "Can scan Blocks in World");
  
  public List<String> getAdditionalToolTips(GT_MetaBase_Item aItem, List<String> aList, ItemStack aStack)
  {
    aList.add(this.mTooltip);
    return aList;
  }
}


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.items.behaviors.Behaviour_Scanner
 * JD-Core Version:    0.7.0.1
 */