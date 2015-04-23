/*   1:    */ package gregtech.common.tools;
/*   2:    */ 
/*   3:    */ import gregtech.api.GregTech_API;
/*   4:    */ import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
/*   5:    */ import gregtech.api.enums.Textures.ItemIcons;
/*   6:    */ import gregtech.api.interfaces.IIconContainer;
/*   7:    */ import gregtech.api.items.GT_MetaGenerated_Tool;
/*   8:    */ import gregtech.common.items.behaviors.Behaviour_Screwdriver;

/*   9:    */ import java.util.Arrays;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Map;

/*  12:    */ import net.minecraft.block.Block;
/*  13:    */ import net.minecraft.block.material.Material;
/*  14:    */ import net.minecraft.entity.Entity;
/*  15:    */ import net.minecraft.entity.EntityLivingBase;
/*  16:    */ import net.minecraft.entity.monster.EntityCaveSpider;
/*  17:    */ import net.minecraft.entity.monster.EntitySpider;
/*  18:    */ import net.minecraft.entity.player.EntityPlayer;
/*  19:    */ import net.minecraft.item.ItemStack;
/*  20:    */ import net.minecraft.util.ChatComponentText;
/*  21:    */ import net.minecraft.util.EnumChatFormatting;
/*  22:    */ import net.minecraft.util.IChatComponent;
/*  23:    */ 
/*  24:    */ public class GT_Tool_Screwdriver
/*  25:    */   extends GT_Tool
/*  26:    */ {
/*  27: 26 */   public static final List<String> mEffectiveList = Arrays.asList(new String[] { EntityCaveSpider.class.getName(), EntitySpider.class.getName(), "EntityTFHedgeSpider", "EntityTFKingSpider", "EntityTFSwarmSpider", "EntityTFTowerBroodling" });
/*  28:    */   
/*  29:    */   public float getNormalDamageAgainstEntity(float aOriginalDamage, Entity aEntity, ItemStack aStack, EntityPlayer aPlayer)
/*  30:    */   {
/*  31: 37 */     String tName = aEntity.getClass().getName();
/*  32: 38 */     tName = tName.substring(tName.lastIndexOf(".") + 1);
/*  33: 39 */     return mEffectiveList.contains(tName) ? aOriginalDamage * 2.0F : aOriginalDamage;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public int getToolDamagePerBlockBreak()
/*  37:    */   {
/*  38: 44 */     return 200;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public int getToolDamagePerDropConversion()
/*  42:    */   {
/*  43: 49 */     return 100;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public int getToolDamagePerContainerCraft()
/*  47:    */   {
/*  48: 54 */     return 400;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public int getToolDamagePerEntityAttack()
/*  52:    */   {
/*  53: 59 */     return 200;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public int getBaseQuality()
/*  57:    */   {
/*  58: 64 */     return 0;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public float getBaseDamage()
/*  62:    */   {
/*  63: 69 */     return 1.5F;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public float getSpeedMultiplier()
/*  67:    */   {
/*  68: 74 */     return 1.0F;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public float getMaxDurabilityMultiplier()
/*  72:    */   {
/*  73: 79 */     return 1.0F;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public String getCraftingSound()
/*  77:    */   {
/*  78: 84 */     return (String)GregTech_API.sSoundList.get(Integer.valueOf(100));
/*  79:    */   }
/*  80:    */   
/*  81:    */   public String getEntityHitSound()
/*  82:    */   {
/*  83: 89 */     return null;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public String getBreakingSound()
/*  87:    */   {
/*  88: 94 */     return (String)GregTech_API.sSoundList.get(Integer.valueOf(0));
/*  89:    */   }
/*  90:    */   
/*  91:    */   public String getMiningSound()
/*  92:    */   {
/*  93: 99 */     return null;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public boolean canBlock()
/*  97:    */   {
/*  98:104 */     return true;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public boolean isCrowbar()
/* 102:    */   {
/* 103:109 */     return false;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public boolean isMiningTool()
/* 107:    */   {
/* 108:114 */     return false;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public boolean isMinableBlock(Block aBlock, byte aMetaData)
/* 112:    */   {
/* 113:119 */     String tTool = aBlock.getHarvestTool(aMetaData);
/* 114:120 */     return ((tTool != null) && (tTool.equals("screwdriver"))) || (aBlock.getMaterial() == Material.circuits);
/* 115:    */   }
/* 116:    */   
/* 117:    */   public ItemStack getBrokenItem(ItemStack aStack)
/* 118:    */   {
/* 119:125 */     return null;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack)
/* 123:    */   {
/* 124:130 */     return !aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mIconSet.mTextures[gregtech.api.enums.OrePrefixes.toolHeadScrewdriver.mTextureIndex] : Textures.ItemIcons.HANDLE_SCREWDRIVER;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public short[] getRGBa(boolean aIsToolHead, ItemStack aStack)
/* 128:    */   {
/* 129:135 */     return !aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mRGBa : GT_MetaGenerated_Tool.getSecondaryMaterial(aStack).mRGBa;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void onStatsAddedToTool(GT_MetaGenerated_Tool aItem, int aID)
/* 133:    */   {
/* 134:140 */     aItem.addItemBehavior(aID, new Behaviour_Screwdriver(1, 200));
/* 135:    */   }
/* 136:    */   
/* 137:    */   public IChatComponent getDeathMessage(EntityLivingBase aPlayer, EntityLivingBase aEntity)
/* 138:    */   {
/* 139:145 */     return new ChatComponentText(EnumChatFormatting.RED + aEntity.getCommandSenderName() + EnumChatFormatting.WHITE + " is screwed! (by " + EnumChatFormatting.GREEN + aPlayer.getCommandSenderName() + EnumChatFormatting.WHITE + ")");
/* 140:    */   }
/* 141:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tools.GT_Tool_Screwdriver
 * JD-Core Version:    0.7.0.1
 */