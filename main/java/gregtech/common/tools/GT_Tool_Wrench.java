/*   1:    */ package gregtech.common.tools;
/*   2:    */ 
/*   3:    */ import gregtech.api.GregTech_API;
/*   4:    */ import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
/*   5:    */ import gregtech.api.enums.Textures.ItemIcons;
/*   6:    */ import gregtech.api.interfaces.IIconContainer;
/*   7:    */ import gregtech.api.items.GT_MetaGenerated_Tool;
/*   8:    */ import gregtech.common.items.behaviors.Behaviour_Wrench;

/*   9:    */ import java.util.Arrays;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Map;

/*  12:    */ import net.minecraft.block.Block;
/*  13:    */ import net.minecraft.block.material.Material;
/*  14:    */ import net.minecraft.entity.Entity;
/*  15:    */ import net.minecraft.entity.EntityLivingBase;
/*  16:    */ import net.minecraft.entity.monster.EntityIronGolem;
/*  17:    */ import net.minecraft.entity.player.EntityPlayer;
/*  18:    */ import net.minecraft.init.Blocks;
/*  19:    */ import net.minecraft.item.ItemStack;
/*  20:    */ import net.minecraft.util.ChatComponentText;
/*  21:    */ import net.minecraft.util.EnumChatFormatting;
/*  22:    */ import net.minecraft.util.IChatComponent;
/*  23:    */ 
/*  24:    */ public class GT_Tool_Wrench
/*  25:    */   extends GT_Tool
/*  26:    */ {
/*  27: 25 */   public static final List<String> mEffectiveList = Arrays.asList(new String[] { EntityIronGolem.class.getName(), "EntityTowerGuardian" });
/*  28:    */   
/*  29:    */   public float getNormalDamageAgainstEntity(float aOriginalDamage, Entity aEntity, ItemStack aStack, EntityPlayer aPlayer)
/*  30:    */   {
/*  31: 32 */     String tName = aEntity.getClass().getName();
/*  32: 33 */     tName = tName.substring(tName.lastIndexOf(".") + 1);
/*  33: 34 */     return (mEffectiveList.contains(tName)) || (tName.contains("Golem")) ? aOriginalDamage * 2.0F : aOriginalDamage;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public int getToolDamagePerBlockBreak()
/*  37:    */   {
/*  38: 39 */     return 50;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public int getToolDamagePerDropConversion()
/*  42:    */   {
/*  43: 44 */     return 100;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public int getToolDamagePerContainerCraft()
/*  47:    */   {
/*  48: 49 */     return 800;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public int getToolDamagePerEntityAttack()
/*  52:    */   {
/*  53: 54 */     return 200;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public int getBaseQuality()
/*  57:    */   {
/*  58: 59 */     return 0;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public float getBaseDamage()
/*  62:    */   {
/*  63: 64 */     return 3.0F;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public int getHurtResistanceTime(int aOriginalHurtResistance, Entity aEntity)
/*  67:    */   {
/*  68: 69 */     return aOriginalHurtResistance * 2;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public float getSpeedMultiplier()
/*  72:    */   {
/*  73: 74 */     return 1.0F;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public float getMaxDurabilityMultiplier()
/*  77:    */   {
/*  78: 79 */     return 1.0F;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public String getCraftingSound()
/*  82:    */   {
/*  83: 84 */     return (String)GregTech_API.sSoundList.get(Integer.valueOf(100));
/*  84:    */   }
/*  85:    */   
/*  86:    */   public String getEntityHitSound()
/*  87:    */   {
/*  88: 89 */     return null;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public String getBreakingSound()
/*  92:    */   {
/*  93: 94 */     return (String)GregTech_API.sSoundList.get(Integer.valueOf(0));
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String getMiningSound()
/*  97:    */   {
/*  98: 99 */     return (String)GregTech_API.sSoundList.get(Integer.valueOf(100));
/*  99:    */   }
/* 100:    */   
/* 101:    */   public boolean canBlock()
/* 102:    */   {
/* 103:104 */     return false;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public boolean isCrowbar()
/* 107:    */   {
/* 108:109 */     return false;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public boolean isMinableBlock(Block aBlock, byte aMetaData)
/* 112:    */   {
/* 113:114 */     String tTool = aBlock.getHarvestTool(aMetaData);
/* 114:115 */     return ((tTool != null) && (tTool.equals("wrench"))) || (aBlock.getMaterial() == Material.piston) || (aBlock == Blocks.hopper) || (aBlock == Blocks.dispenser) || (aBlock == Blocks.dropper);
/* 115:    */   }
/* 116:    */   
/* 117:    */   public ItemStack getBrokenItem(ItemStack aStack)
/* 118:    */   {
/* 119:120 */     return null;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack)
/* 123:    */   {
/* 124:125 */     return aIsToolHead ? Textures.ItemIcons.WRENCH : null;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public short[] getRGBa(boolean aIsToolHead, ItemStack aStack)
/* 128:    */   {
/* 129:130 */     return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mRGBa : null;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void onStatsAddedToTool(GT_MetaGenerated_Tool aItem, int aID)
/* 133:    */   {
/* 134:135 */     aItem.addItemBehavior(aID, new Behaviour_Wrench(100));
/* 135:    */   }
/* 136:    */   
/* 137:    */   public IChatComponent getDeathMessage(EntityLivingBase aPlayer, EntityLivingBase aEntity)
/* 138:    */   {
/* 139:140 */     return new ChatComponentText(EnumChatFormatting.GREEN + aPlayer.getCommandSenderName() + EnumChatFormatting.WHITE + " threw a Monkey Wrench into the Plans of " + EnumChatFormatting.RED + aEntity.getCommandSenderName() + EnumChatFormatting.WHITE);
/* 140:    */   }
/* 141:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tools.GT_Tool_Wrench
 * JD-Core Version:    0.7.0.1
 */