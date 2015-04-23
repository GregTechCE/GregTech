/*  1:   */ package gregtech.common.tools;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.Dyes;
/*  4:   */ import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
/*  5:   */ import gregtech.api.enums.Textures.ItemIcons;
/*  6:   */ import gregtech.api.interfaces.IIconContainer;
/*  7:   */ import gregtech.api.items.GT_MetaGenerated_Tool;
/*  8:   */ import net.minecraft.block.Block;
/*  9:   */ import net.minecraft.entity.EntityLivingBase;
/* 10:   */ import net.minecraft.item.ItemStack;
/* 11:   */ import net.minecraft.util.ChatComponentText;
/* 12:   */ import net.minecraft.util.EnumChatFormatting;
/* 13:   */ import net.minecraft.util.IChatComponent;
/* 14:   */ 
/* 15:   */ public class GT_Tool_RollingPin
/* 16:   */   extends GT_Tool
/* 17:   */ {
/* 18:   */   public int getToolDamagePerBlockBreak()
/* 19:   */   {
/* 20:17 */     return 50;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public int getToolDamagePerDropConversion()
/* 24:   */   {
/* 25:22 */     return 100;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public int getToolDamagePerContainerCraft()
/* 29:   */   {
/* 30:27 */     return 400;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public int getToolDamagePerEntityAttack()
/* 34:   */   {
/* 35:32 */     return 200;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public float getBaseDamage()
/* 39:   */   {
/* 40:37 */     return 2.0F;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public boolean isMinableBlock(Block aBlock, byte aMetaData)
/* 44:   */   {
/* 45:42 */     return false;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public short[] getRGBa(boolean aIsToolHead, ItemStack aStack)
/* 49:   */   {
/* 50:47 */     return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mRGBa : Dyes._NULL.mRGBa;
/* 51:   */   }
/* 52:   */   
/* 53:   */   public void onStatsAddedToTool(GT_MetaGenerated_Tool aItem, int aID) {}
/* 54:   */   
/* 55:   */   public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack)
/* 56:   */   {
/* 57:57 */     return aIsToolHead ? Textures.ItemIcons.ROLLING_PIN : null;
/* 58:   */   }
/* 59:   */   
/* 60:   */   public IChatComponent getDeathMessage(EntityLivingBase aPlayer, EntityLivingBase aEntity)
/* 61:   */   {
/* 62:62 */     return new ChatComponentText(EnumChatFormatting.RED + aEntity.getCommandSenderName() + EnumChatFormatting.WHITE + " got flattened by " + EnumChatFormatting.GREEN + aPlayer.getCommandSenderName() + EnumChatFormatting.WHITE);
/* 63:   */   }
/* 64:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tools.GT_Tool_RollingPin
 * JD-Core Version:    0.7.0.1
 */