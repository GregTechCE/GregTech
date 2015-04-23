/*   1:    */ package gregtech.common.tools;
/*   2:    */ 
/*   3:    */ import gregtech.api.GregTech_API;
/*   4:    */ import gregtech.api.enums.Dyes;
/*   5:    */ import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
/*   6:    */ import gregtech.api.enums.Textures.ItemIcons;
/*   7:    */ import gregtech.api.interfaces.IIconContainer;
/*   8:    */ import gregtech.api.items.GT_MetaGenerated_Tool;

/*   9:    */ import java.util.Map;

/*  10:    */ import net.minecraft.block.Block;
/*  11:    */ import net.minecraft.entity.EntityLivingBase;
/*  12:    */ import net.minecraft.item.ItemStack;
/*  13:    */ import net.minecraft.util.ChatComponentText;
/*  14:    */ import net.minecraft.util.EnumChatFormatting;
/*  15:    */ import net.minecraft.util.IChatComponent;
/*  16:    */ 
/*  17:    */ public class GT_Tool_Mortar
/*  18:    */   extends GT_Tool
/*  19:    */ {
/*  20:    */   public int getToolDamagePerBlockBreak()
/*  21:    */   {
/*  22: 18 */     return 50;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public int getToolDamagePerDropConversion()
/*  26:    */   {
/*  27: 23 */     return 100;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public int getToolDamagePerContainerCraft()
/*  31:    */   {
/*  32: 28 */     return 400;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public int getToolDamagePerEntityAttack()
/*  36:    */   {
/*  37: 33 */     return 200;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public int getBaseQuality()
/*  41:    */   {
/*  42: 38 */     return 0;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public float getBaseDamage()
/*  46:    */   {
/*  47: 43 */     return 2.0F;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public float getSpeedMultiplier()
/*  51:    */   {
/*  52: 48 */     return 1.0F;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public float getMaxDurabilityMultiplier()
/*  56:    */   {
/*  57: 53 */     return 1.0F;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public String getCraftingSound()
/*  61:    */   {
/*  62: 58 */     return null;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public String getEntityHitSound()
/*  66:    */   {
/*  67: 63 */     return null;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public String getBreakingSound()
/*  71:    */   {
/*  72: 68 */     return (String)GregTech_API.sSoundList.get(Integer.valueOf(0));
/*  73:    */   }
/*  74:    */   
/*  75:    */   public String getMiningSound()
/*  76:    */   {
/*  77: 73 */     return null;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public boolean canBlock()
/*  81:    */   {
/*  82: 78 */     return false;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public boolean isCrowbar()
/*  86:    */   {
/*  87: 83 */     return false;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public boolean isMiningTool()
/*  91:    */   {
/*  92: 88 */     return false;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public boolean isMinableBlock(Block aBlock, byte aMetaData)
/*  96:    */   {
/*  97: 93 */     return false;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public ItemStack getBrokenItem(ItemStack aStack)
/* 101:    */   {
/* 102: 98 */     return null;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack)
/* 106:    */   {
/* 107:103 */     return aIsToolHead ? Textures.ItemIcons.MORTAR : null;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public short[] getRGBa(boolean aIsToolHead, ItemStack aStack)
/* 111:    */   {
/* 112:108 */     return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mRGBa : Dyes._NULL.mRGBa;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void onStatsAddedToTool(GT_MetaGenerated_Tool aItem, int aID) {}
/* 116:    */   
/* 117:    */   public IChatComponent getDeathMessage(EntityLivingBase aPlayer, EntityLivingBase aEntity)
/* 118:    */   {
/* 119:118 */     return new ChatComponentText(EnumChatFormatting.RED + aEntity.getCommandSenderName() + EnumChatFormatting.WHITE + " was grounded by " + EnumChatFormatting.GREEN + aPlayer.getCommandSenderName() + EnumChatFormatting.WHITE);
/* 120:    */   }
/* 121:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tools.GT_Tool_Mortar
 * JD-Core Version:    0.7.0.1
 */