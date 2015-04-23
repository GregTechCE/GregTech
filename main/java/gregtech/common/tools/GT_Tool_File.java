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
/*  10:    */ import net.minecraft.entity.EntityLivingBase;
/*  11:    */ import net.minecraft.item.ItemStack;
/*  12:    */ import net.minecraft.util.ChatComponentText;
/*  13:    */ import net.minecraft.util.EnumChatFormatting;
/*  14:    */ import net.minecraft.util.IChatComponent;
/*  15:    */ 
/*  16:    */ public class GT_Tool_File
/*  17:    */   extends GT_Tool
/*  18:    */ {
/*  19:    */   public int getToolDamagePerBlockBreak()
/*  20:    */   {
/*  21: 18 */     return 50;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public int getToolDamagePerDropConversion()
/*  25:    */   {
/*  26: 23 */     return 100;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public int getToolDamagePerContainerCraft()
/*  30:    */   {
/*  31: 28 */     return 400;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public int getToolDamagePerEntityAttack()
/*  35:    */   {
/*  36: 33 */     return 200;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public int getBaseQuality()
/*  40:    */   {
/*  41: 38 */     return 0;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public float getBaseDamage()
/*  45:    */   {
/*  46: 43 */     return 1.5F;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public float getSpeedMultiplier()
/*  50:    */   {
/*  51: 48 */     return 1.0F;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public float getMaxDurabilityMultiplier()
/*  55:    */   {
/*  56: 53 */     return 1.0F;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public String getCraftingSound()
/*  60:    */   {
/*  61: 58 */     return null;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public String getEntityHitSound()
/*  65:    */   {
/*  66: 63 */     return null;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public String getBreakingSound()
/*  70:    */   {
/*  71: 68 */     return (String)GregTech_API.sSoundList.get(Integer.valueOf(0));
/*  72:    */   }
/*  73:    */   
/*  74:    */   public String getMiningSound()
/*  75:    */   {
/*  76: 73 */     return null;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public boolean canBlock()
/*  80:    */   {
/*  81: 78 */     return true;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public boolean isCrowbar()
/*  85:    */   {
/*  86: 83 */     return false;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public boolean isMiningTool()
/*  90:    */   {
/*  91: 88 */     return false;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public boolean isMinableBlock(Block aBlock, byte aMetaData)
/*  95:    */   {
/*  96: 93 */     String tTool = aBlock.getHarvestTool(aMetaData);
/*  97: 94 */     return (tTool != null) && (tTool.equals("file"));
/*  98:    */   }
/*  99:    */   
/* 100:    */   public ItemStack getBrokenItem(ItemStack aStack)
/* 101:    */   {
/* 102: 99 */     return null;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack)
/* 106:    */   {
/* 107:104 */     return !aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mIconSet.mTextures[gregtech.api.enums.OrePrefixes.toolHeadFile.mTextureIndex] : Textures.ItemIcons.HANDLE_FILE;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public short[] getRGBa(boolean aIsToolHead, ItemStack aStack)
/* 111:    */   {
/* 112:109 */     return !aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mRGBa : GT_MetaGenerated_Tool.getSecondaryMaterial(aStack).mRGBa;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void onStatsAddedToTool(GT_MetaGenerated_Tool aItem, int aID) {}
/* 116:    */   
/* 117:    */   public IChatComponent getDeathMessage(EntityLivingBase aPlayer, EntityLivingBase aEntity)
/* 118:    */   {
/* 119:119 */     return new ChatComponentText(EnumChatFormatting.RED + aEntity.getCommandSenderName() + EnumChatFormatting.WHITE + " has been filed D for 'Dead' by " + EnumChatFormatting.GREEN + aPlayer.getCommandSenderName() + EnumChatFormatting.WHITE);
/* 120:    */   }
/* 121:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tools.GT_Tool_File
 * JD-Core Version:    0.7.0.1
 */