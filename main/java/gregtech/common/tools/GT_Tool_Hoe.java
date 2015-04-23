/*   1:    */ package gregtech.common.tools;
/*   2:    */ 
/*   3:    */ import gregtech.api.GregTech_API;
/*   4:    */ import gregtech.api.enums.Materials;
/*   5:    */ import gregtech.api.interfaces.IIconContainer;
/*   6:    */ import gregtech.api.items.GT_MetaGenerated_Tool;
/*   7:    */ import gregtech.common.items.behaviors.Behaviour_Hoe;
/*   8:    */ import java.util.Map;
/*   9:    */ import net.minecraft.block.Block;
/*  10:    */ import net.minecraft.block.material.Material;
/*  11:    */ import net.minecraft.entity.EntityLivingBase;
/*  12:    */ import net.minecraft.entity.player.EntityPlayer;
/*  13:    */ import net.minecraft.item.ItemStack;
/*  14:    */ import net.minecraft.stats.AchievementList;
/*  15:    */ import net.minecraft.util.ChatComponentText;
/*  16:    */ import net.minecraft.util.EnumChatFormatting;
/*  17:    */ import net.minecraft.util.IChatComponent;
/*  18:    */ 
/*  19:    */ public class GT_Tool_Hoe
/*  20:    */   extends GT_Tool
/*  21:    */ {
/*  22:    */   public int getToolDamagePerBlockBreak()
/*  23:    */   {
/*  24: 21 */     return 50;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public int getToolDamagePerDropConversion()
/*  28:    */   {
/*  29: 26 */     return 100;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public int getToolDamagePerContainerCraft()
/*  33:    */   {
/*  34: 31 */     return 100;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public int getToolDamagePerEntityAttack()
/*  38:    */   {
/*  39: 36 */     return 200;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public int getBaseQuality()
/*  43:    */   {
/*  44: 41 */     return 0;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public float getBaseDamage()
/*  48:    */   {
/*  49: 46 */     return 1.75F;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public float getSpeedMultiplier()
/*  53:    */   {
/*  54: 51 */     return 1.0F;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public float getMaxDurabilityMultiplier()
/*  58:    */   {
/*  59: 56 */     return 1.0F;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public String getCraftingSound()
/*  63:    */   {
/*  64: 61 */     return null;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public String getEntityHitSound()
/*  68:    */   {
/*  69: 66 */     return null;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public String getBreakingSound()
/*  73:    */   {
/*  74: 71 */     return (String)GregTech_API.sSoundList.get(Integer.valueOf(0));
/*  75:    */   }
/*  76:    */   
/*  77:    */   public String getMiningSound()
/*  78:    */   {
/*  79: 76 */     return null;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public boolean canBlock()
/*  83:    */   {
/*  84: 81 */     return false;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public boolean isCrowbar()
/*  88:    */   {
/*  89: 86 */     return false;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public boolean isMinableBlock(Block aBlock, byte aMetaData)
/*  93:    */   {
/*  94: 91 */     String tTool = aBlock.getHarvestTool(aMetaData);
/*  95: 92 */     return ((tTool != null) && (tTool.equals("hoe"))) || (aBlock.getMaterial() == Material.gourd);
/*  96:    */   }
/*  97:    */   
/*  98:    */   public ItemStack getBrokenItem(ItemStack aStack)
/*  99:    */   {
/* 100: 97 */     return null;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack)
/* 104:    */   {
/* 105:102 */     return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mIconSet.mTextures[gregtech.api.enums.OrePrefixes.toolHeadHoe.mTextureIndex] : GT_MetaGenerated_Tool.getSecondaryMaterial(aStack).mIconSet.mTextures[gregtech.api.enums.OrePrefixes.stick.mTextureIndex];
/* 106:    */   }
/* 107:    */   
/* 108:    */   public short[] getRGBa(boolean aIsToolHead, ItemStack aStack)
/* 109:    */   {
/* 110:107 */     return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mRGBa : GT_MetaGenerated_Tool.getSecondaryMaterial(aStack).mRGBa;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void onStatsAddedToTool(GT_MetaGenerated_Tool aItem, int aID)
/* 114:    */   {
/* 115:112 */     aItem.addItemBehavior(aID, new Behaviour_Hoe(100));
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void onToolCrafted(ItemStack aStack, EntityPlayer aPlayer)
/* 119:    */   {
/* 120:117 */     super.onToolCrafted(aStack, aPlayer);
/* 121:118 */     aPlayer.triggerAchievement(AchievementList.buildHoe);
/* 122:    */   }
/* 123:    */   
/* 124:    */   public IChatComponent getDeathMessage(EntityLivingBase aPlayer, EntityLivingBase aEntity)
/* 125:    */   {
/* 126:123 */     return new ChatComponentText(EnumChatFormatting.RED + aEntity.getCommandSenderName() + EnumChatFormatting.WHITE + " has been called a stupid Hoe by " + EnumChatFormatting.GREEN + aPlayer.getCommandSenderName() + EnumChatFormatting.WHITE);
/* 127:    */   }
/* 128:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tools.GT_Tool_Hoe
 * JD-Core Version:    0.7.0.1
 */