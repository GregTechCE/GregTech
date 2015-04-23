/*  1:   */ package gregtech.common.tools;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
/*  4:   */ import gregtech.api.enums.Textures.ItemIcons;
/*  5:   */ import gregtech.api.interfaces.IIconContainer;
/*  6:   */ import gregtech.api.items.GT_MetaGenerated_Tool;
/*  7:   */ import net.minecraft.block.Block;
/*  8:   */ import net.minecraft.enchantment.Enchantment;
/*  9:   */ import net.minecraft.entity.Entity;
/* 10:   */ import net.minecraft.entity.EntityLivingBase;
/* 11:   */ import net.minecraft.item.ItemStack;
/* 12:   */ import net.minecraft.util.ChatComponentText;
/* 13:   */ import net.minecraft.util.EnumChatFormatting;
/* 14:   */ import net.minecraft.util.IChatComponent;
/* 15:   */ 
/* 16:   */ public class GT_Tool_ButcheryKnife
/* 17:   */   extends GT_Tool
/* 18:   */ {
/* 19:   */   public int getToolDamagePerBlockBreak()
/* 20:   */   {
/* 21:18 */     return 200;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public int getToolDamagePerDropConversion()
/* 25:   */   {
/* 26:23 */     return 100;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public int getToolDamagePerContainerCraft()
/* 30:   */   {
/* 31:28 */     return 100;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public int getToolDamagePerEntityAttack()
/* 35:   */   {
/* 36:33 */     return 400;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public float getBaseDamage()
/* 40:   */   {
/* 41:38 */     return 1.0F;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public int getHurtResistanceTime(int aOriginalHurtResistance, Entity aEntity)
/* 45:   */   {
/* 46:43 */     return aOriginalHurtResistance * 2;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public float getSpeedMultiplier()
/* 50:   */   {
/* 51:48 */     return 0.1F;
/* 52:   */   }
/* 53:   */   
/* 54:   */   public float getMaxDurabilityMultiplier()
/* 55:   */   {
/* 56:53 */     return 1.0F;
/* 57:   */   }
/* 58:   */   
/* 59:   */   public boolean isWeapon()
/* 60:   */   {
/* 61:58 */     return true;
/* 62:   */   }
/* 63:   */   
/* 64:   */   public boolean isMiningTool()
/* 65:   */   {
/* 66:63 */     return false;
/* 67:   */   }
/* 68:   */   
/* 69:   */   public Enchantment[] getEnchantments(ItemStack aStack)
/* 70:   */   {
/* 71:68 */     return LOOTING_ENCHANTMENT;
/* 72:   */   }
/* 73:   */   
/* 74:   */   public int[] getEnchantmentLevels(ItemStack aStack)
/* 75:   */   {
/* 76:73 */     return new int[] { (2 + GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mToolQuality) / 2 };
/* 77:   */   }
/* 78:   */   
/* 79:   */   public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack)
/* 80:   */   {
/* 81:78 */     return aIsToolHead ? Textures.ItemIcons.BUTCHERYKNIFE : null;
/* 82:   */   }
/* 83:   */   
/* 84:   */   public short[] getRGBa(boolean aIsToolHead, ItemStack aStack)
/* 85:   */   {
/* 86:83 */     return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mRGBa : GT_MetaGenerated_Tool.getSecondaryMaterial(aStack).mRGBa;
/* 87:   */   }
/* 88:   */   
/* 89:   */   public IChatComponent getDeathMessage(EntityLivingBase aPlayer, EntityLivingBase aEntity)
/* 90:   */   {
/* 91:88 */     return new ChatComponentText(EnumChatFormatting.GREEN + aPlayer.getCommandSenderName() + EnumChatFormatting.WHITE + " has butchered " + EnumChatFormatting.RED + aEntity.getCommandSenderName() + EnumChatFormatting.WHITE);
/* 92:   */   }
/* 93:   */   
/* 94:   */   public boolean isMinableBlock(Block aBlock, byte aMetaData)
/* 95:   */   {
/* 96:93 */     return false;
/* 97:   */   }
/* 98:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tools.GT_Tool_ButcheryKnife
 * JD-Core Version:    0.7.0.1
 */