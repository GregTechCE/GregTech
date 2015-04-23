/*   1:    */ package gregtech.common.tools;
/*   2:    */ 
/*   3:    */ import gregtech.api.GregTech_API;
/*   4:    */ import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
/*   5:    */ import gregtech.api.enums.Textures.ItemIcons;
/*   6:    */ import gregtech.api.interfaces.IIconContainer;
/*   7:    */ import gregtech.api.items.GT_MetaGenerated_Tool;

/*   8:    */ import java.util.Map;

/*   9:    */ import net.minecraft.block.Block;
/*  10:    */ import net.minecraft.block.material.Material;
/*  11:    */ import net.minecraft.entity.player.EntityPlayer;
/*  12:    */ import net.minecraft.item.ItemStack;
/*  13:    */ import net.minecraft.stats.AchievementList;
/*  14:    */ 
/*  15:    */ public class GT_Tool_Sword
/*  16:    */   extends GT_Tool
/*  17:    */ {
/*  18:    */   public int getToolDamagePerBlockBreak()
/*  19:    */   {
/*  20: 17 */     return 200;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public int getToolDamagePerDropConversion()
/*  24:    */   {
/*  25: 22 */     return 100;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public int getToolDamagePerContainerCraft()
/*  29:    */   {
/*  30: 27 */     return 100;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public int getToolDamagePerEntityAttack()
/*  34:    */   {
/*  35: 32 */     return 100;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public int getBaseQuality()
/*  39:    */   {
/*  40: 37 */     return 0;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public float getBaseDamage()
/*  44:    */   {
/*  45: 42 */     return 4.0F;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public float getSpeedMultiplier()
/*  49:    */   {
/*  50: 47 */     return 1.0F;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public float getMaxDurabilityMultiplier()
/*  54:    */   {
/*  55: 52 */     return 1.0F;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public String getCraftingSound()
/*  59:    */   {
/*  60: 57 */     return null;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String getEntityHitSound()
/*  64:    */   {
/*  65: 62 */     return null;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public String getBreakingSound()
/*  69:    */   {
/*  70: 67 */     return (String)GregTech_API.sSoundList.get(Integer.valueOf(0));
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String getMiningSound()
/*  74:    */   {
/*  75: 72 */     return null;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public boolean canBlock()
/*  79:    */   {
/*  80: 77 */     return true;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public boolean isCrowbar()
/*  84:    */   {
/*  85: 82 */     return false;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public boolean isWeapon()
/*  89:    */   {
/*  90: 87 */     return true;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public boolean isMinableBlock(Block aBlock, byte aMetaData)
/*  94:    */   {
/*  95: 92 */     String tTool = aBlock.getHarvestTool(aMetaData);
/*  96: 93 */     return ((tTool != null) && (tTool.equals("sword"))) || (aBlock.getMaterial() == Material.leaves) || (aBlock.getMaterial() == Material.gourd) || (aBlock.getMaterial() == Material.vine) || (aBlock.getMaterial() == Material.web) || (aBlock.getMaterial() == Material.cloth) || (aBlock.getMaterial() == Material.carpet) || (aBlock.getMaterial() == Material.plants) || (aBlock.getMaterial() == Material.cactus) || (aBlock.getMaterial() == Material.cake) || (aBlock.getMaterial() == Material.tnt) || (aBlock.getMaterial() == Material.sponge);
/*  97:    */   }
/*  98:    */   
/*  99:    */   public ItemStack getBrokenItem(ItemStack aStack)
/* 100:    */   {
/* 101: 98 */     return null;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack)
/* 105:    */   {
/* 106:103 */     return !aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mIconSet.mTextures[gregtech.api.enums.OrePrefixes.toolHeadSword.mTextureIndex] : Textures.ItemIcons.HANDLE_SWORD;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public short[] getRGBa(boolean aIsToolHead, ItemStack aStack)
/* 110:    */   {
/* 111:108 */     return !aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mRGBa : GT_MetaGenerated_Tool.getSecondaryMaterial(aStack).mRGBa;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void onToolCrafted(ItemStack aStack, EntityPlayer aPlayer)
/* 115:    */   {
/* 116:113 */     super.onToolCrafted(aStack, aPlayer);
/* 117:114 */     aPlayer.triggerAchievement(AchievementList.buildSword);
/* 118:    */   }
/* 119:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tools.GT_Tool_Sword
 * JD-Core Version:    0.7.0.1
 */