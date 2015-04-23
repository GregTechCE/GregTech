/*   1:    */ package gregtech.common.tools;
/*   2:    */ 
/*   3:    */ import gregtech.GT_Mod;
/*   4:    */ import gregtech.api.GregTech_API;
/*   5:    */ import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
/*   6:    */ import gregtech.api.enums.Textures.ItemIcons;
/*   7:    */ import gregtech.api.interfaces.IIconContainer;
/*   8:    */ import gregtech.api.items.GT_MetaGenerated_Tool;
/*   9:    */ import gregtech.common.GT_Proxy;

/*  10:    */ import java.util.Map;

/*  11:    */ import net.minecraft.block.Block;
/*  12:    */ import net.minecraft.block.material.Material;
/*  13:    */ import net.minecraft.entity.EntityLivingBase;
/*  14:    */ import net.minecraft.entity.player.EntityPlayer;
/*  15:    */ import net.minecraft.item.ItemStack;
/*  16:    */ import net.minecraft.stats.AchievementList;
/*  17:    */ import net.minecraft.util.ChatComponentText;
/*  18:    */ import net.minecraft.util.EnumChatFormatting;
/*  19:    */ import net.minecraft.util.IChatComponent;
/*  20:    */ 
/*  21:    */ public class GT_Tool_Drill_LV
/*  22:    */   extends GT_Tool
/*  23:    */ {
/*  24:    */   public int getToolDamagePerBlockBreak()
/*  25:    */   {
/*  26: 22 */     return GT_Mod.gregtechproxy.mHardRock ? 25 : 50;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public int getToolDamagePerDropConversion()
/*  30:    */   {
/*  31: 27 */     return 100;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public int getToolDamagePerContainerCraft()
/*  35:    */   {
/*  36: 32 */     return 100;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public int getToolDamagePerEntityAttack()
/*  40:    */   {
/*  41: 37 */     return 200;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public int getBaseQuality()
/*  45:    */   {
/*  46: 42 */     return 0;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public float getBaseDamage()
/*  50:    */   {
/*  51: 47 */     return 2.0F;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public float getSpeedMultiplier()
/*  55:    */   {
/*  56: 52 */     return 3.0F;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public float getMaxDurabilityMultiplier()
/*  60:    */   {
/*  61: 57 */     return 1.0F;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public String getCraftingSound()
/*  65:    */   {
/*  66: 62 */     return (String)GregTech_API.sSoundList.get(Integer.valueOf(106));
/*  67:    */   }
/*  68:    */   
/*  69:    */   public String getEntityHitSound()
/*  70:    */   {
/*  71: 67 */     return (String)GregTech_API.sSoundList.get(Integer.valueOf(106));
/*  72:    */   }
/*  73:    */   
/*  74:    */   public String getBreakingSound()
/*  75:    */   {
/*  76: 72 */     return (String)GregTech_API.sSoundList.get(Integer.valueOf(106));
/*  77:    */   }
/*  78:    */   
/*  79:    */   public String getMiningSound()
/*  80:    */   {
/*  81: 77 */     return (String)GregTech_API.sSoundList.get(Integer.valueOf(106));
/*  82:    */   }
/*  83:    */   
/*  84:    */   public boolean canBlock()
/*  85:    */   {
/*  86: 82 */     return false;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public boolean isCrowbar()
/*  90:    */   {
/*  91: 87 */     return false;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public boolean isMinableBlock(Block aBlock, byte aMetaData)
/*  95:    */   {
/*  96: 92 */     String tTool = aBlock.getHarvestTool(aMetaData);
/*  97: 93 */     return ((tTool != null) && ((tTool.equals("pickaxe")) || (tTool.equals("shovel")))) || (aBlock.getMaterial() == Material.rock) || (aBlock.getMaterial() == Material.iron) || (aBlock.getMaterial() == Material.anvil) || (aBlock.getMaterial() == Material.sand) || (aBlock.getMaterial() == Material.grass) || (aBlock.getMaterial() == Material.ground) || (aBlock.getMaterial() == Material.snow) || (aBlock.getMaterial() == Material.clay) || (aBlock.getMaterial() == Material.glass);
/*  98:    */   }
/*  99:    */   
/* 100:    */   public ItemStack getBrokenItem(ItemStack aStack)
/* 101:    */   {
/* 102: 98 */     return null;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack)
/* 106:    */   {
/* 107:103 */     return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mIconSet.mTextures[gregtech.api.enums.OrePrefixes.toolHeadDrill.mTextureIndex] : Textures.ItemIcons.POWER_UNIT_LV;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public short[] getRGBa(boolean aIsToolHead, ItemStack aStack)
/* 111:    */   {
/* 112:108 */     return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mRGBa : GT_MetaGenerated_Tool.getSecondaryMaterial(aStack).mRGBa;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void onStatsAddedToTool(GT_MetaGenerated_Tool aItem, int aID) {}
/* 116:    */   
/* 117:    */   public void onToolCrafted(ItemStack aStack, EntityPlayer aPlayer)
/* 118:    */   {
/* 119:118 */     super.onToolCrafted(aStack, aPlayer);
/* 120:119 */     aPlayer.triggerAchievement(AchievementList.buildPickaxe);
/* 121:120 */     aPlayer.triggerAchievement(AchievementList.buildBetterPickaxe);
/* 122:    */   }
/* 123:    */   
/* 124:    */   public IChatComponent getDeathMessage(EntityLivingBase aPlayer, EntityLivingBase aEntity)
/* 125:    */   {
/* 126:125 */     return new ChatComponentText(EnumChatFormatting.RED + aEntity.getCommandSenderName() + EnumChatFormatting.WHITE + " got the Drill! (by " + EnumChatFormatting.GREEN + aPlayer.getCommandSenderName() + EnumChatFormatting.WHITE + ")");
/* 127:    */   }
/* 128:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tools.GT_Tool_Drill_LV
 * JD-Core Version:    0.7.0.1
 */