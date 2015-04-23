/*  1:   */ package gregtech.common.tools;
/*  2:   */ 
/*  3:   */ import gregtech.api.GregTech_API;
/*  4:   */ import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
/*  5:   */ import gregtech.api.enums.Textures.ItemIcons;
/*  6:   */ import gregtech.api.interfaces.IIconContainer;
/*  7:   */ import gregtech.api.items.GT_MetaGenerated_Tool;

/*  8:   */ import java.util.Map;

/*  9:   */ import net.minecraft.block.Block;
/* 10:   */ import net.minecraft.entity.EntityLivingBase;
/* 11:   */ import net.minecraft.item.ItemStack;
/* 12:   */ import net.minecraft.util.ChatComponentText;
/* 13:   */ import net.minecraft.util.EnumChatFormatting;
/* 14:   */ import net.minecraft.util.IChatComponent;
/* 15:   */ 
/* 16:   */ public class GT_Tool_BuzzSaw
/* 17:   */   extends GT_Tool_Saw
/* 18:   */ {
/* 19:   */   public int getToolDamagePerContainerCraft()
/* 20:   */   {
/* 21:18 */     return 100;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public int getToolDamagePerEntityAttack()
/* 25:   */   {
/* 26:23 */     return 300;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public float getBaseDamage()
/* 30:   */   {
/* 31:28 */     return 1.0F;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public float getMaxDurabilityMultiplier()
/* 35:   */   {
/* 36:33 */     return 1.0F;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public String getCraftingSound()
/* 40:   */   {
/* 41:38 */     return (String)GregTech_API.sSoundList.get(Integer.valueOf(104));
/* 42:   */   }
/* 43:   */   
/* 44:   */   public String getEntityHitSound()
/* 45:   */   {
/* 46:43 */     return (String)GregTech_API.sSoundList.get(Integer.valueOf(105));
/* 47:   */   }
/* 48:   */   
/* 49:   */   public String getBreakingSound()
/* 50:   */   {
/* 51:48 */     return (String)GregTech_API.sSoundList.get(Integer.valueOf(0));
/* 52:   */   }
/* 53:   */   
/* 54:   */   public String getMiningSound()
/* 55:   */   {
/* 56:53 */     return (String)GregTech_API.sSoundList.get(Integer.valueOf(104));
/* 57:   */   }
/* 58:   */   
/* 59:   */   public boolean isMinableBlock(Block aBlock, byte aMetaData)
/* 60:   */   {
/* 61:58 */     return false;
/* 62:   */   }
/* 63:   */   
/* 64:   */   public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack)
/* 65:   */   {
/* 66:63 */     return !aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mIconSet.mTextures[gregtech.api.enums.OrePrefixes.toolHeadBuzzSaw.mTextureIndex] : Textures.ItemIcons.HANDLE_BUZZSAW;
/* 67:   */   }
/* 68:   */   
/* 69:   */   public short[] getRGBa(boolean aIsToolHead, ItemStack aStack)
/* 70:   */   {
/* 71:68 */     return !aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mRGBa : GT_MetaGenerated_Tool.getSecondaryMaterial(aStack).mRGBa;
/* 72:   */   }
/* 73:   */   
/* 74:   */   public IChatComponent getDeathMessage(EntityLivingBase aPlayer, EntityLivingBase aEntity)
/* 75:   */   {
/* 76:73 */     return new ChatComponentText(EnumChatFormatting.RED + aEntity.getCommandSenderName() + EnumChatFormatting.WHITE + " got buzzed by " + EnumChatFormatting.GREEN + aPlayer.getCommandSenderName() + EnumChatFormatting.WHITE);
/* 77:   */   }
/* 78:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tools.GT_Tool_BuzzSaw
 * JD-Core Version:    0.7.0.1
 */