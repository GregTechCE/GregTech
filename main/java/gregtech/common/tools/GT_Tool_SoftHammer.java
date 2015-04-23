/*   1:    */ package gregtech.common.tools;
/*   2:    */ 
/*   3:    */ import gregtech.api.GregTech_API;
/*   4:    */ import gregtech.api.enums.Materials;
/*   5:    */ import gregtech.api.interfaces.IIconContainer;
/*   6:    */ import gregtech.api.items.GT_MetaGenerated_Tool;
/*   7:    */ import gregtech.common.items.behaviors.Behaviour_SoftHammer;
/*   8:    */ import java.util.Map;
/*   9:    */ import net.minecraft.block.Block;
/*  10:    */ import net.minecraft.entity.Entity;
/*  11:    */ import net.minecraft.entity.EntityLivingBase;
/*  12:    */ import net.minecraft.item.ItemStack;
/*  13:    */ import net.minecraft.util.ChatComponentText;
/*  14:    */ import net.minecraft.util.EnumChatFormatting;
/*  15:    */ import net.minecraft.util.IChatComponent;
/*  16:    */ 
/*  17:    */ public class GT_Tool_SoftHammer
/*  18:    */   extends GT_Tool
/*  19:    */ {
/*  20:    */   public int getToolDamagePerBlockBreak()
/*  21:    */   {
/*  22: 19 */     return 50;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public int getToolDamagePerDropConversion()
/*  26:    */   {
/*  27: 24 */     return 100;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public int getToolDamagePerContainerCraft()
/*  31:    */   {
/*  32: 29 */     return 800;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public int getToolDamagePerEntityAttack()
/*  36:    */   {
/*  37: 34 */     return 200;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public int getBaseQuality()
/*  41:    */   {
/*  42: 39 */     return 0;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public float getBaseDamage()
/*  46:    */   {
/*  47: 44 */     return 3.0F;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public int getHurtResistanceTime(int aOriginalHurtResistance, Entity aEntity)
/*  51:    */   {
/*  52: 49 */     return aOriginalHurtResistance * 2;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public float getSpeedMultiplier()
/*  56:    */   {
/*  57: 54 */     return 0.1F;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public float getMaxDurabilityMultiplier()
/*  61:    */   {
/*  62: 59 */     return 8.0F;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public String getCraftingSound()
/*  66:    */   {
/*  67: 64 */     return (String)GregTech_API.sSoundList.get(Integer.valueOf(101));
/*  68:    */   }
/*  69:    */   
/*  70:    */   public String getEntityHitSound()
/*  71:    */   {
/*  72: 69 */     return (String)GregTech_API.sSoundList.get(Integer.valueOf(101));
/*  73:    */   }
/*  74:    */   
/*  75:    */   public String getBreakingSound()
/*  76:    */   {
/*  77: 74 */     return (String)GregTech_API.sSoundList.get(Integer.valueOf(0));
/*  78:    */   }
/*  79:    */   
/*  80:    */   public String getMiningSound()
/*  81:    */   {
/*  82: 79 */     return (String)GregTech_API.sSoundList.get(Integer.valueOf(101));
/*  83:    */   }
/*  84:    */   
/*  85:    */   public boolean canBlock()
/*  86:    */   {
/*  87: 84 */     return true;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public boolean isCrowbar()
/*  91:    */   {
/*  92: 89 */     return false;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public boolean isMiningTool()
/*  96:    */   {
/*  97: 94 */     return false;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public boolean isWeapon()
/* 101:    */   {
/* 102: 99 */     return true;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public boolean isMinableBlock(Block aBlock, byte aMetaData)
/* 106:    */   {
/* 107:104 */     String tTool = aBlock.getHarvestTool(aMetaData);
/* 108:105 */     return (tTool != null) && (tTool.equals("softhammer"));
/* 109:    */   }
/* 110:    */   
/* 111:    */   public ItemStack getBrokenItem(ItemStack aStack)
/* 112:    */   {
/* 113:110 */     return null;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack)
/* 117:    */   {
/* 118:115 */     return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mIconSet.mTextures[gregtech.api.enums.OrePrefixes.toolHeadHammer.mTextureIndex] : GT_MetaGenerated_Tool.getSecondaryMaterial(aStack).mIconSet.mTextures[gregtech.api.enums.OrePrefixes.stick.mTextureIndex];
/* 119:    */   }
/* 120:    */   
/* 121:    */   public short[] getRGBa(boolean aIsToolHead, ItemStack aStack)
/* 122:    */   {
/* 123:120 */     return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mRGBa : GT_MetaGenerated_Tool.getSecondaryMaterial(aStack).mRGBa;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void onStatsAddedToTool(GT_MetaGenerated_Tool aItem, int aID)
/* 127:    */   {
/* 128:125 */     aItem.addItemBehavior(aID, new Behaviour_SoftHammer(100));
/* 129:    */   }
/* 130:    */   
/* 131:    */   public IChatComponent getDeathMessage(EntityLivingBase aPlayer, EntityLivingBase aEntity)
/* 132:    */   {
/* 133:130 */     return new ChatComponentText(EnumChatFormatting.RED + aEntity.getCommandSenderName() + EnumChatFormatting.WHITE + " was hammered to death by " + EnumChatFormatting.GREEN + aPlayer.getCommandSenderName() + EnumChatFormatting.WHITE);
/* 134:    */   }
/* 135:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tools.GT_Tool_SoftHammer
 * JD-Core Version:    0.7.0.1
 */