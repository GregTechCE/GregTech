/*  1:   */ package gregtech.common.tools;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.Textures;
import gregtech.api.enums.Textures.ItemIcons;
/*  4:   */ import gregtech.api.interfaces.IIconContainer;
/*  5:   */ import net.minecraft.entity.EntityLivingBase;
/*  6:   */ import net.minecraft.item.ItemStack;
/*  7:   */ import net.minecraft.util.ChatComponentText;
/*  8:   */ import net.minecraft.util.EnumChatFormatting;
/*  9:   */ import net.minecraft.util.IChatComponent;
/* 10:   */ 
/* 11:   */ public class GT_Tool_Knife
/* 12:   */   extends GT_Tool_Sword
/* 13:   */ {
/* 14:   */   public int getToolDamagePerBlockBreak()
/* 15:   */   {
/* 16:14 */     return 100;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public int getToolDamagePerDropConversion()
/* 20:   */   {
/* 21:19 */     return 100;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public int getToolDamagePerContainerCraft()
/* 25:   */   {
/* 26:24 */     return 100;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public int getToolDamagePerEntityAttack()
/* 30:   */   {
/* 31:29 */     return 200;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public float getBaseDamage()
/* 35:   */   {
/* 36:34 */     return 2.0F;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public float getSpeedMultiplier()
/* 40:   */   {
/* 41:39 */     return 0.5F;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public float getMaxDurabilityMultiplier()
/* 45:   */   {
/* 46:44 */     return 1.0F;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack)
/* 50:   */   {
/* 51:49 */     return aIsToolHead ? Textures.ItemIcons.KNIFE : null;
/* 52:   */   }
/* 53:   */   
/* 54:   */   public IChatComponent getDeathMessage(EntityLivingBase aPlayer, EntityLivingBase aEntity)
/* 55:   */   {
/* 56:54 */     return new ChatComponentText("<" + EnumChatFormatting.RED + aEntity.getCommandSenderName() + EnumChatFormatting.WHITE + "> " + EnumChatFormatting.GREEN + aPlayer.getCommandSenderName() + EnumChatFormatting.WHITE + " what are you doing?, " + EnumChatFormatting.GREEN + aPlayer.getCommandSenderName() + EnumChatFormatting.WHITE + "?!? STAHP!!!");
/* 57:   */   }
/* 58:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tools.GT_Tool_Knife
 * JD-Core Version:    0.7.0.1
 */