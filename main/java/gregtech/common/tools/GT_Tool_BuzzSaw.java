package gregtech.common.tools;

import gregtech.api.GregTech_API;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.enums.Textures.ItemIcons;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.items.GT_MetaGenerated_Tool;

import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class GT_Tool_BuzzSaw
  extends GT_Tool_Saw
{
  public int getToolDamagePerContainerCraft()
  {
    return 100;
  }
  
  public int getToolDamagePerEntityAttack()
  {
    return 300;
  }
  
  public float getBaseDamage()
  {
    return 1.0F;
  }
  
  public float getMaxDurabilityMultiplier()
  {
    return 1.0F;
  }
  
  public String getCraftingSound()
  {
    return (String)GregTech_API.sSoundList.get(Integer.valueOf(104));
  }
  
  public String getEntityHitSound()
  {
    return (String)GregTech_API.sSoundList.get(Integer.valueOf(105));
  }
  
  public String getBreakingSound()
  {
    return (String)GregTech_API.sSoundList.get(Integer.valueOf(0));
  }
  
  public String getMiningSound()
  {
    return (String)GregTech_API.sSoundList.get(Integer.valueOf(104));
  }
  
  public boolean isMinableBlock(Block aBlock, byte aMetaData)
  {
    return false;
  }
  
  public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack)
  {
    return !aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mIconSet.mTextures[gregtech.api.enums.OrePrefixes.toolHeadBuzzSaw.mTextureIndex] : Textures.ItemIcons.HANDLE_BUZZSAW;
  }
  
  public short[] getRGBa(boolean aIsToolHead, ItemStack aStack)
  {
    return !aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mRGBa : GT_MetaGenerated_Tool.getSecondaryMaterial(aStack).mRGBa;
  }
  
  public IChatComponent getDeathMessage(EntityLivingBase aPlayer, EntityLivingBase aEntity)
  {
    return new ChatComponentText(EnumChatFormatting.RED + aEntity.getCommandSenderName() + EnumChatFormatting.WHITE + " got buzzed by " + EnumChatFormatting.GREEN + aPlayer.getCommandSenderName() + EnumChatFormatting.WHITE);
  }
}


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tools.GT_Tool_BuzzSaw
 * JD-Core Version:    0.7.0.1
 */