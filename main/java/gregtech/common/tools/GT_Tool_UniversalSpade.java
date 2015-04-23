/*   1:    */ package gregtech.common.tools;
/*   2:    */ 
/*   3:    */ import gregtech.api.GregTech_API;
/*   4:    */ import gregtech.api.enums.Materials;
/*   5:    */ import gregtech.api.interfaces.IIconContainer;
/*   6:    */ import gregtech.api.items.GT_MetaGenerated_Tool;
/*   7:    */ import gregtech.common.items.behaviors.Behaviour_Crowbar;
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
/*  19:    */ public class GT_Tool_UniversalSpade
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
/*  34: 31 */     return 400;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public int getToolDamagePerEntityAttack()
/*  38:    */   {
/*  39: 36 */     return 100;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public int getBaseQuality()
/*  43:    */   {
/*  44: 41 */     return 0;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public float getBaseDamage()
/*  48:    */   {
/*  49: 46 */     return 3.0F;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public float getSpeedMultiplier()
/*  53:    */   {
/*  54: 51 */     return 0.75F;
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
/*  84: 81 */     return true;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public boolean isCrowbar()
/*  88:    */   {
/*  89: 86 */     return true;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public boolean isWeapon()
/*  93:    */   {
/*  94: 91 */     return true;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public boolean isMinableBlock(Block aBlock, byte aMetaData)
/*  98:    */   {
/*  99: 96 */     String tTool = aBlock.getHarvestTool(aMetaData);
/* 100: 97 */     return ((tTool != null) && ((tTool.equals("shovel")) || (tTool.equals("axe")) || (tTool.equals("saw")) || (tTool.equals("sword")) || (tTool.equals("crowbar")))) || (aBlock.getMaterial() == Material.sand) || (aBlock.getMaterial() == Material.grass) || (aBlock.getMaterial() == Material.ground) || (aBlock.getMaterial() == Material.snow) || (aBlock.getMaterial() == Material.clay) || (aBlock.getMaterial() == Material.leaves) || (aBlock.getMaterial() == Material.vine) || (aBlock.getMaterial() == Material.wood) || (aBlock.getMaterial() == Material.cactus) || (aBlock.getMaterial() == Material.circuits) || (aBlock.getMaterial() == Material.gourd) || (aBlock.getMaterial() == Material.web) || (aBlock.getMaterial() == Material.cloth) || (aBlock.getMaterial() == Material.carpet) || (aBlock.getMaterial() == Material.plants) || (aBlock.getMaterial() == Material.cake) || (aBlock.getMaterial() == Material.tnt) || (aBlock.getMaterial() == Material.sponge);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public ItemStack getBrokenItem(ItemStack aStack)
/* 104:    */   {
/* 105:102 */     return null;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack)
/* 109:    */   {
/* 110:107 */     return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mIconSet.mTextures[gregtech.api.enums.OrePrefixes.toolHeadUniversalSpade.mTextureIndex] : GT_MetaGenerated_Tool.getSecondaryMaterial(aStack).mIconSet.mTextures[gregtech.api.enums.OrePrefixes.stick.mTextureIndex];
/* 111:    */   }
/* 112:    */   
/* 113:    */   public short[] getRGBa(boolean aIsToolHead, ItemStack aStack)
/* 114:    */   {
/* 115:112 */     return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mRGBa : GT_MetaGenerated_Tool.getSecondaryMaterial(aStack).mRGBa;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void onStatsAddedToTool(GT_MetaGenerated_Tool aItem, int aID)
/* 119:    */   {
/* 120:117 */     aItem.addItemBehavior(aID, new Behaviour_Crowbar(2, 2000));
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void onToolCrafted(ItemStack aStack, EntityPlayer aPlayer)
/* 124:    */   {
/* 125:122 */     super.onToolCrafted(aStack, aPlayer);
/* 126:123 */     aPlayer.triggerAchievement(AchievementList.buildSword);
/* 127:    */   }
/* 128:    */   
/* 129:    */   public IChatComponent getDeathMessage(EntityLivingBase aPlayer, EntityLivingBase aEntity)
/* 130:    */   {
/* 131:128 */     return new ChatComponentText(EnumChatFormatting.RED + aEntity.getCommandSenderName() + EnumChatFormatting.WHITE + " has been digged by " + EnumChatFormatting.GREEN + aPlayer.getCommandSenderName() + EnumChatFormatting.WHITE);
/* 132:    */   }
/* 133:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tools.GT_Tool_UniversalSpade
 * JD-Core Version:    0.7.0.1
 */