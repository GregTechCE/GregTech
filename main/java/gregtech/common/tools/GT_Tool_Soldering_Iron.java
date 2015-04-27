package gregtech.common.tools;

import java.util.Arrays;
import java.util.List;

import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.items.GT_MetaGenerated_Tool;
import gregtech.common.items.behaviors.Behaviour_Screwdriver;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class GT_Tool_Soldering_Iron extends GT_Tool{
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
	/*  38: 44 */     return 1000;
	/*  39:    */   }
	/*  40:    */   
	/*  41:    */   public int getToolDamagePerDropConversion()
	/*  42:    */   {
	/*  43: 49 */     return 500;
	/*  44:    */   }
	/*  45:    */   
	/*  46:    */   public int getToolDamagePerContainerCraft()
	/*  47:    */   {
	/*  48: 54 */     return 1000;
	/*  49:    */   }
	/*  50:    */   
	/*  51:    */   public int getToolDamagePerEntityAttack()
	/*  52:    */   {
	/*  53: 59 */     return 500;
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
	/* 114:120 */     return ((tTool != null) && (tTool.equals("soldering_iron"))) || (aBlock.getMaterial() == Material.circuits);
	/* 115:    */   }
	/* 116:    */   
	/* 117:    */   public ItemStack getBrokenItem(ItemStack aStack)
	/* 118:    */   {
	/* 119:125 */     return null;
	/* 120:    */   }
	/* 121:    */   
	/* 122:    */   public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack)
	/* 123:    */   {
	/* 124:130 */     return !aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mIconSet.mTextures[49] : Textures.ItemIcons.HANDLE_SOLDERING;
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
	/* 139:145 */     return new ChatComponentText(EnumChatFormatting.RED + aEntity.getCommandSenderName() + EnumChatFormatting.WHITE + " got soldert! (by " + EnumChatFormatting.GREEN + aPlayer.getCommandSenderName() + EnumChatFormatting.WHITE + ")");
	/* 140:    */   }
	/* 141:    */ }
