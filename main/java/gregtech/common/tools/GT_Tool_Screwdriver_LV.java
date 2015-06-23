package gregtech.common.tools;

import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.enums.Textures.ItemIcons;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.items.GT_MetaGenerated_Tool;
import net.minecraft.item.ItemStack;

public class GT_Tool_Screwdriver_LV
  extends GT_Tool_Screwdriver
{
  public float getMaxDurabilityMultiplier()
  {
    return 1.0F;
  }
  
  public int getToolDamagePerContainerCraft()
  {
    return 200;
  }
  
  public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack)
  {
    return !aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mIconSet.mTextures[gregtech.api.enums.OrePrefixes.toolHeadScrewdriver.mTextureIndex] : Textures.ItemIcons.HANDLE_ELECTRIC_SCREWDRIVER;
  }
  
  public short[] getRGBa(boolean aIsToolHead, ItemStack aStack)
  {
    return !aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mRGBa : GT_MetaGenerated_Tool.getSecondaryMaterial(aStack).mRGBa;
  }
}


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tools.GT_Tool_Screwdriver_LV
 * JD-Core Version:    0.7.0.1
 */