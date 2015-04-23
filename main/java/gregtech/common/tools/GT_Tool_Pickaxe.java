/*   1:    */ package gregtech.common.tools;
/*   2:    */ 
/*   3:    */ import gregtech.GT_Mod;
/*   4:    */ import gregtech.api.GregTech_API;
/*   5:    */ import gregtech.api.enums.Materials;
/*   6:    */ import gregtech.api.interfaces.IIconContainer;
/*   7:    */ import gregtech.api.items.GT_MetaGenerated_Tool;
/*   8:    */ import gregtech.common.GT_Proxy;
/*   9:    */ import java.util.Map;
/*  10:    */ import net.minecraft.block.Block;
/*  11:    */ import net.minecraft.block.material.Material;
/*  12:    */ import net.minecraft.entity.EntityLivingBase;
/*  13:    */ import net.minecraft.entity.player.EntityPlayer;
/*  14:    */ import net.minecraft.item.ItemStack;
/*  15:    */ import net.minecraft.stats.AchievementList;
/*  16:    */ import net.minecraft.util.ChatComponentText;
/*  17:    */ import net.minecraft.util.EnumChatFormatting;
/*  18:    */ import net.minecraft.util.IChatComponent;
/*  19:    */ 
/*  20:    */ public class GT_Tool_Pickaxe
/*  21:    */   extends GT_Tool
/*  22:    */ {
/*  23:    */   public int getToolDamagePerBlockBreak()
/*  24:    */   {
/*  25: 21 */     return GT_Mod.gregtechproxy.mHardRock ? 25 : 50;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public int getToolDamagePerDropConversion()
/*  29:    */   {
/*  30: 26 */     return 100;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public int getToolDamagePerContainerCraft()
/*  34:    */   {
/*  35: 31 */     return 100;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public int getToolDamagePerEntityAttack()
/*  39:    */   {
/*  40: 36 */     return 200;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public int getBaseQuality()
/*  44:    */   {
/*  45: 41 */     return 0;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public float getBaseDamage()
/*  49:    */   {
/*  50: 46 */     return 1.5F;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public float getSpeedMultiplier()
/*  54:    */   {
/*  55: 51 */     return 1.0F;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public float getMaxDurabilityMultiplier()
/*  59:    */   {
/*  60: 56 */     return 1.0F;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String getCraftingSound()
/*  64:    */   {
/*  65: 61 */     return null;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public String getEntityHitSound()
/*  69:    */   {
/*  70: 66 */     return null;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String getBreakingSound()
/*  74:    */   {
/*  75: 71 */     return (String)GregTech_API.sSoundList.get(Integer.valueOf(0));
/*  76:    */   }
/*  77:    */   
/*  78:    */   public String getMiningSound()
/*  79:    */   {
/*  80: 76 */     return null;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public boolean canBlock()
/*  84:    */   {
/*  85: 81 */     return false;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public boolean isCrowbar()
/*  89:    */   {
/*  90: 86 */     return false;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public boolean isMinableBlock(Block aBlock, byte aMetaData)
/*  94:    */   {
/*  95: 91 */     String tTool = aBlock.getHarvestTool(aMetaData);
/*  96: 92 */     return ((tTool != null) && (tTool.equals("pickaxe"))) || (aBlock.getMaterial() == Material.rock) || (aBlock.getMaterial() == Material.iron) || (aBlock.getMaterial() == Material.anvil) || (aBlock.getMaterial() == Material.glass);
/*  97:    */   }
/*  98:    */   
/*  99:    */   public ItemStack getBrokenItem(ItemStack aStack)
/* 100:    */   {
/* 101: 97 */     return null;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack)
/* 105:    */   {
/* 106:102 */     return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mIconSet.mTextures[gregtech.api.enums.OrePrefixes.toolHeadPickaxe.mTextureIndex] : GT_MetaGenerated_Tool.getSecondaryMaterial(aStack).mIconSet.mTextures[gregtech.api.enums.OrePrefixes.stick.mTextureIndex];
/* 107:    */   }
/* 108:    */   
/* 109:    */   public short[] getRGBa(boolean aIsToolHead, ItemStack aStack)
/* 110:    */   {
/* 111:107 */     return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mRGBa : GT_MetaGenerated_Tool.getSecondaryMaterial(aStack).mRGBa;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void onStatsAddedToTool(GT_MetaGenerated_Tool aItem, int aID) {}
/* 115:    */   
/* 116:    */   public void onToolCrafted(ItemStack aStack, EntityPlayer aPlayer)
/* 117:    */   {
/* 118:117 */     super.onToolCrafted(aStack, aPlayer);
/* 119:118 */     aPlayer.triggerAchievement(AchievementList.buildPickaxe);
/* 120:119 */     aPlayer.triggerAchievement(AchievementList.buildBetterPickaxe);
/* 121:    */   }
/* 122:    */   
/* 123:    */   public IChatComponent getDeathMessage(EntityLivingBase aPlayer, EntityLivingBase aEntity)
/* 124:    */   {
/* 125:124 */     return new ChatComponentText(EnumChatFormatting.RED + aEntity.getCommandSenderName() + EnumChatFormatting.WHITE + " got mined by " + EnumChatFormatting.GREEN + aPlayer.getCommandSenderName() + EnumChatFormatting.WHITE);
/* 126:    */   }
/* 127:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tools.GT_Tool_Pickaxe
 * JD-Core Version:    0.7.0.1
 */