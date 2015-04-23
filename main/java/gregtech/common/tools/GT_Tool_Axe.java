/*   1:    */ package gregtech.common.tools;
/*   2:    */ 
/*   3:    */ import gregtech.api.GregTech_API;
/*   4:    */ import gregtech.api.enums.Materials;
/*   5:    */ import gregtech.api.enums.OrePrefixes;
/*   6:    */ import gregtech.api.interfaces.IIconContainer;
/*   7:    */ import gregtech.api.items.GT_MetaGenerated_Tool;

/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;

/*  10:    */ import net.minecraft.block.Block;
/*  11:    */ import net.minecraft.block.material.Material;
/*  12:    */ import net.minecraft.entity.EntityLivingBase;
/*  13:    */ import net.minecraft.entity.player.EntityPlayer;
/*  14:    */ import net.minecraft.item.ItemStack;
/*  15:    */ import net.minecraft.util.ChatComponentText;
/*  16:    */ import net.minecraft.util.EnumChatFormatting;
/*  17:    */ import net.minecraft.util.IChatComponent;
/*  18:    */ import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
/*  19:    */ import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
/*  20:    */ 
/*  21:    */ public class GT_Tool_Axe
/*  22:    */   extends GT_Tool
/*  23:    */ {
/*  24:    */   public int getToolDamagePerBlockBreak()
/*  25:    */   {
/*  26: 23 */     return 50;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public int getToolDamagePerDropConversion()
/*  30:    */   {
/*  31: 28 */     return 100;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public int getToolDamagePerContainerCraft()
/*  35:    */   {
/*  36: 33 */     return 100;
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
/*  51: 48 */     return 3.0F;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public float getSpeedMultiplier()
/*  55:    */   {
/*  56: 53 */     return 2.0F;
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
/*  94:    */   public boolean isWeapon()
/*  95:    */   {
/*  96: 93 */     return true;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public boolean isMinableBlock(Block aBlock, byte aMetaData)
/* 100:    */   {
/* 101: 98 */     String tTool = aBlock.getHarvestTool(aMetaData);
/* 102: 99 */     return ((tTool != null) && (tTool.equals("axe"))) || (aBlock.getMaterial() == Material.wood);
/* 103:    */   }
/* 104:    */   
/* 105:    */   public int convertBlockDrops(List<ItemStack> aDrops, ItemStack aStack, EntityPlayer aPlayer, Block aBlock, int aX, int aY, int aZ, byte aMetaData, int aFortune, boolean aSilkTouch, BlockEvent.HarvestDropsEvent aEvent)
/* 106:    */   {
/* 107:104 */     int rAmount = 0;
/* 108:105 */     if ((GregTech_API.sTimber) && (!aPlayer.isSneaking()) && (OrePrefixes.log.contains(new ItemStack(aBlock, 1, aMetaData))))
/* 109:    */     {
/* 110:106 */       int tY = aY + 1;
/* 111:106 */       for (int tH = aPlayer.worldObj.getHeight(); tY < tH; tY++)
/* 112:    */       {
/* 113:107 */         if ((aPlayer.worldObj.getBlock(aX, tY, aZ) != aBlock) || (!aPlayer.worldObj.func_147480_a(aX, tY, aZ, true))) {
/* 114:    */           break;
/* 115:    */         }
/* 116:107 */         rAmount++;
/* 117:    */       }
/* 118:    */     }
/* 119:110 */     return rAmount;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public ItemStack getBrokenItem(ItemStack aStack)
/* 123:    */   {
/* 124:115 */     return null;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack)
/* 128:    */   {
/* 129:120 */     return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mIconSet.mTextures[OrePrefixes.toolHeadAxe.mTextureIndex] : GT_MetaGenerated_Tool.getSecondaryMaterial(aStack).mIconSet.mTextures[OrePrefixes.stick.mTextureIndex];
/* 130:    */   }
/* 131:    */   
/* 132:    */   public short[] getRGBa(boolean aIsToolHead, ItemStack aStack)
/* 133:    */   {
/* 134:125 */     return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mRGBa : GT_MetaGenerated_Tool.getSecondaryMaterial(aStack).mRGBa;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void onStatsAddedToTool(GT_MetaGenerated_Tool aItem, int aID) {}
/* 138:    */   
/* 139:    */   public IChatComponent getDeathMessage(EntityLivingBase aPlayer, EntityLivingBase aEntity)
/* 140:    */   {
/* 141:135 */     return new ChatComponentText(EnumChatFormatting.RED + aEntity.getCommandSenderName() + EnumChatFormatting.WHITE + " has been chopped by " + EnumChatFormatting.GREEN + aPlayer.getCommandSenderName() + EnumChatFormatting.WHITE);
/* 142:    */   }
/* 143:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tools.GT_Tool_Axe
 * JD-Core Version:    0.7.0.1
 */