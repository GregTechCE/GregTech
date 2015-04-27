/*   1:    */ package gregtech.common.tools;
/*   2:    */ 
/*   3:    */ import gregtech.api.GregTech_API;
/*   4:    */ import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
/*   5:    */ import gregtech.api.enums.Textures.ItemIcons;
/*   6:    */ import gregtech.api.interfaces.IIconContainer;
/*   7:    */ import gregtech.api.interfaces.IToolStats;
/*   8:    */ import gregtech.api.items.GT_MetaGenerated_Tool;
/*   9:    */ import gregtech.common.items.GT_MetaGenerated_Tool_01;
/*  10:    */ import gregtech.common.items.behaviors.Behaviour_Crowbar;

/*  11:    */ import java.util.Collection;
/*  12:    */ import java.util.HashMap;
/*  13:    */ import java.util.Iterator;
/*  14:    */ import java.util.Map;

/*  15:    */ import net.minecraft.block.Block;
/*  16:    */ import net.minecraft.block.material.Material;
/*  17:    */ import net.minecraft.entity.EntityLivingBase;
/*  18:    */ import net.minecraft.item.ItemStack;
/*  19:    */ import net.minecraft.util.ChatComponentText;
/*  20:    */ import net.minecraft.util.EnumChatFormatting;
/*  21:    */ import net.minecraft.util.IChatComponent;
/*  22:    */ 
/*  23:    */ public class GT_Tool_Crowbar
/*  24:    */   extends GT_Tool
/*  25:    */ {
/*  26:    */   public int getToolDamagePerBlockBreak()
/*  27:    */   {
/*  28: 21 */     return 50;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public int getToolDamagePerDropConversion()
/*  32:    */   {
/*  33: 26 */     return 100;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public int getToolDamagePerContainerCraft()
/*  37:    */   {
/*  38: 31 */     return 100;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public int getToolDamagePerEntityAttack()
/*  42:    */   {
/*  43: 36 */     return 200;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public int getBaseQuality()
/*  47:    */   {
/*  48: 41 */     return 0;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public float getBaseDamage()
/*  52:    */   {
/*  53: 46 */     return 2.0F;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public float getSpeedMultiplier()
/*  57:    */   {
/*  58: 51 */     return 1.0F;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public float getMaxDurabilityMultiplier()
/*  62:    */   {
/*  63: 56 */     return 1.0F;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public String getCraftingSound()
/*  67:    */   {
/*  68: 61 */     return (String)GregTech_API.sSoundList.get(Integer.valueOf(0));
/*  69:    */   }
/*  70:    */   
/*  71:    */   public String getEntityHitSound()
/*  72:    */   {
/*  73: 66 */     return (String)GregTech_API.sSoundList.get(Integer.valueOf(0));
/*  74:    */   }
/*  75:    */   
/*  76:    */   public String getBreakingSound()
/*  77:    */   {
/*  78: 71 */     return (String)GregTech_API.sSoundList.get(Integer.valueOf(0));
/*  79:    */   }
/*  80:    */   
/*  81:    */   public String getMiningSound()
/*  82:    */   {
/*  83: 76 */     return (String)GregTech_API.sSoundList.get(Integer.valueOf(0));
/*  84:    */   }
/*  85:    */   
/*  86:    */   public boolean canBlock()
/*  87:    */   {
/*  88: 81 */     return true;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public boolean isCrowbar()
/*  92:    */   {
/*  93: 86 */     return true;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public boolean isWeapon()
/*  97:    */   {
/*  98: 91 */     return true;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public boolean isMinableBlock(Block aBlock, byte aMetaData)
/* 102:    */   {
/* 103: 96 */     if (aBlock.getMaterial() == Material.circuits) {
/* 104: 96 */       return true;
/* 105:    */     }
/* 106: 97 */     String tTool = aBlock.getHarvestTool(aMetaData);
/* 107: 98 */     if ((tTool == null) || (tTool.equals("")))
/* 108:    */     {
/* 109: 99 */       for (Iterator i$ = GT_MetaGenerated_Tool_01.INSTANCE.mToolStats.values().iterator(); i$.hasNext(); i$.next())
/* 110:    */       {
/* 112: 99 */         if (((i$ instanceof GT_Tool_Crowbar)) || (!((IToolStats)i$).isMinableBlock(aBlock, aMetaData))) {return false;}
/* 113:    */       }
/* 114:100 */       return true;
/* 115:    */     }
/* 116:102 */     return tTool.equals("crowbar");
/* 117:    */   }
/* 118:    */   
/* 119:    */   public ItemStack getBrokenItem(ItemStack aStack)
/* 120:    */   {
/* 121:107 */     return null;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack)
/* 125:    */   {
/* 126:112 */     return aIsToolHead ? Textures.ItemIcons.CROWBAR : null;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public short[] getRGBa(boolean aIsToolHead, ItemStack aStack)
/* 130:    */   {
/* 131:117 */     return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mRGBa : null;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void onStatsAddedToTool(GT_MetaGenerated_Tool aItem, int aID)
/* 135:    */   {
/* 136:122 */     aItem.addItemBehavior(aID, new Behaviour_Crowbar(1, 1000));
/* 137:    */   }
/* 138:    */   
/* 139:    */   public IChatComponent getDeathMessage(EntityLivingBase aPlayer, EntityLivingBase aEntity)
/* 140:    */   {
/* 141:127 */     return new ChatComponentText(EnumChatFormatting.RED + aEntity.getCommandSenderName() + EnumChatFormatting.WHITE + " was removed by " + EnumChatFormatting.GREEN + aPlayer.getCommandSenderName() + EnumChatFormatting.WHITE);
/* 142:    */   }
/* 143:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tools.GT_Tool_Crowbar
 * JD-Core Version:    0.7.0.1
 */