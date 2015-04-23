/*   1:    */ package gregtech.common.tools;
/*   2:    */ 
/*   3:    */ import gregtech.api.GregTech_API;
/*   4:    */ import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
/*   5:    */ import gregtech.api.enums.Textures.ItemIcons;
/*   6:    */ import gregtech.api.interfaces.IIconContainer;
/*   7:    */ import gregtech.api.interfaces.IItemBehaviour;
/*   8:    */ import gregtech.api.items.GT_MetaGenerated_Tool;
/*   9:    */ import gregtech.api.util.GT_Utility;

/*  10:    */ import java.util.Map;

/*  11:    */ import net.minecraft.block.Block;
/*  12:    */ import net.minecraft.block.material.Material;
/*  13:    */ import net.minecraft.entity.EntityLivingBase;
/*  14:    */ import net.minecraft.item.ItemStack;
/*  15:    */ import net.minecraft.util.ChatComponentText;
/*  16:    */ import net.minecraft.util.EnumChatFormatting;
/*  17:    */ import net.minecraft.util.IChatComponent;
/*  18:    */ 
/*  19:    */ public class GT_Tool_Scoop
/*  20:    */   extends GT_Tool
/*  21:    */ {
/*  22:    */   public static Material sBeeHiveMaterial;
/*  23:    */   
/*  24:    */   public int getToolDamagePerBlockBreak()
/*  25:    */   {
/*  26: 23 */     return 200;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public int getToolDamagePerDropConversion()
/*  30:    */   {
/*  31: 28 */     return 100;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public int getToolDamagePerContainerCraft()
/*  35:    */   {
/*  36: 33 */     return 800;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public int getToolDamagePerEntityAttack()
/*  40:    */   {
/*  41: 38 */     return 200;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public int getBaseQuality()
/*  45:    */   {
/*  46: 43 */     return 0;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public float getBaseDamage()
/*  50:    */   {
/*  51: 48 */     return 1.0F;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public float getSpeedMultiplier()
/*  55:    */   {
/*  56: 53 */     return 1.0F;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public float getMaxDurabilityMultiplier()
/*  60:    */   {
/*  61: 58 */     return 1.0F;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public String getCraftingSound()
/*  65:    */   {
/*  66: 63 */     return null;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public String getEntityHitSound()
/*  70:    */   {
/*  71: 68 */     return null;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public String getBreakingSound()
/*  75:    */   {
/*  76: 73 */     return (String)GregTech_API.sSoundList.get(Integer.valueOf(0));
/*  77:    */   }
/*  78:    */   
/*  79:    */   public String getMiningSound()
/*  80:    */   {
/*  81: 78 */     return null;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public boolean canBlock()
/*  85:    */   {
/*  86: 83 */     return false;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public boolean isCrowbar()
/*  90:    */   {
/*  91: 88 */     return false;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public boolean isMinableBlock(Block aBlock, byte aMetaData)
/*  95:    */   {
/*  96: 93 */     String tTool = aBlock.getHarvestTool(aMetaData);
/*  97: 94 */     return ((tTool != null) && (tTool.equals("scoop"))) || (aBlock.getMaterial() == sBeeHiveMaterial);
/*  98:    */   }
/*  99:    */   
/* 100:    */   public ItemStack getBrokenItem(ItemStack aStack)
/* 101:    */   {
/* 102: 99 */     return null;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack)
/* 106:    */   {
/* 107:104 */     return aIsToolHead ? Textures.ItemIcons.SCOOP : null;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public short[] getRGBa(boolean aIsToolHead, ItemStack aStack)
/* 111:    */   {
/* 112:109 */     return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mRGBa : GT_MetaGenerated_Tool.getSecondaryMaterial(aStack).mRGBa;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void onStatsAddedToTool(GT_MetaGenerated_Tool aItem, int aID)
/* 116:    */   {
/* 117:    */     try
/* 118:    */     {
/* 119:115 */       Object tObject = GT_Utility.callConstructor("gregtech.common.items.behaviors.Behaviour_Scoop", 0, null, false, new Object[] { Integer.valueOf(200) });
/* 120:116 */       if ((tObject instanceof IItemBehaviour)) {
/* 121:116 */         aItem.addItemBehavior(aID, (IItemBehaviour)tObject);
/* 122:    */       }
/* 123:    */     }
/* 124:    */     catch (Throwable e) {}
/* 125:    */   }
/* 126:    */   
/* 127:    */   public IChatComponent getDeathMessage(EntityLivingBase aPlayer, EntityLivingBase aEntity)
/* 128:    */   {
/* 129:122 */     return new ChatComponentText(EnumChatFormatting.RED + aEntity.getCommandSenderName() + EnumChatFormatting.WHITE + " got scooped up by " + EnumChatFormatting.GREEN + aPlayer.getCommandSenderName() + EnumChatFormatting.WHITE);
/* 130:    */   }
/* 131:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tools.GT_Tool_Scoop
 * JD-Core Version:    0.7.0.1
 */