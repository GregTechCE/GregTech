/*   1:    */ package gregtech.common.tools;
/*   2:    */ 
/*   3:    */ import gregtech.GT_Mod;
import gregtech.api.GregTech_API;
/*   4:    */ import gregtech.api.enums.Materials;
/*   5:    */ import gregtech.api.interfaces.IIconContainer;
/*   6:    */ import gregtech.api.items.GT_MetaGenerated_Tool;
/*   7:    */ import gregtech.api.util.GT_Recipe;
/*   8:    */ import gregtech.api.util.GT_Recipe.GT_Recipe_Map;
/*   9:    */ import gregtech.api.util.GT_Utility;
/*  10:    */ import gregtech.common.items.behaviors.Behaviour_Prospecting;

/*  11:    */ import java.util.Arrays;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.Map;

/*  14:    */ import net.minecraft.block.Block;
/*  15:    */ import net.minecraft.block.material.Material;
/*  16:    */ import net.minecraft.entity.Entity;
/*  17:    */ import net.minecraft.entity.EntityLivingBase;
/*  18:    */ import net.minecraft.entity.monster.EntityIronGolem;
/*  19:    */ import net.minecraft.entity.player.EntityPlayer;
/*  20:    */ import net.minecraft.item.ItemStack;
import net.minecraft.stats.AchievementList;
/*  21:    */ import net.minecraft.util.ChatComponentText;
/*  22:    */ import net.minecraft.util.EnumChatFormatting;
/*  23:    */ import net.minecraft.util.IChatComponent;
import net.minecraftforge.event.world.BlockEvent;
/*  24:    */ import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
/*  25:    */ 
/*  26:    */ public class GT_Tool_HardHammer
/*  27:    */   extends GT_Tool
/*  28:    */ {
/*  29: 28 */   public static final List<String> mEffectiveList = Arrays.asList(new String[] { EntityIronGolem.class.getName(), "EntityTowerGuardian" });
/*  30:    */   
/*  31:    */   public float getNormalDamageAgainstEntity(float aOriginalDamage, Entity aEntity, ItemStack aStack, EntityPlayer aPlayer)
/*  32:    */   {
/*  33: 35 */     String tName = aEntity.getClass().getName();
/*  34: 36 */     tName = tName.substring(tName.lastIndexOf(".") + 1);
/*  35: 37 */     return (mEffectiveList.contains(tName)) || (tName.contains("Golem")) ? aOriginalDamage * 2.0F : aOriginalDamage;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public int getToolDamagePerBlockBreak()
/*  39:    */   {
/*  40: 42 */     return 50;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public int getToolDamagePerDropConversion()
/*  44:    */   {
/*  45: 47 */     return 200;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public int getToolDamagePerContainerCraft()
/*  49:    */   {
/*  50: 52 */     return 400;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public int getToolDamagePerEntityAttack()
/*  54:    */   {
/*  55: 57 */     return 200;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public int getBaseQuality()
/*  59:    */   {
/*  60: 62 */     return 0;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public float getBaseDamage()
/*  64:    */   {
/*  65: 67 */     return 3.0F;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public int getHurtResistanceTime(int aOriginalHurtResistance, Entity aEntity)
/*  69:    */   {
/*  70: 72 */     return aOriginalHurtResistance * 2;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public float getSpeedMultiplier()
/*  74:    */   {
/*  75: 77 */     return 0.75F;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public float getMaxDurabilityMultiplier()
/*  79:    */   {
/*  80: 82 */     return 1.0F;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public String getCraftingSound()
/*  84:    */   {
/*  85: 87 */     return (String)GregTech_API.sSoundList.get(Integer.valueOf(1));
/*  86:    */   }
/*  87:    */   
/*  88:    */   public String getEntityHitSound()
/*  89:    */   {
/*  90: 92 */     return null;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public String getBreakingSound()
/*  94:    */   {
/*  95: 97 */     return (String)GregTech_API.sSoundList.get(Integer.valueOf(2));
/*  96:    */   }
/*  97:    */   
/*  98:    */   public String getMiningSound()
/*  99:    */   {
/* 100:102 */     return null;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public boolean canBlock()
/* 104:    */   {
/* 105:107 */     return true;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public boolean isCrowbar()
/* 109:    */   {
/* 110:112 */     return false;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public boolean isWeapon()
/* 114:    */   {
/* 115:117 */     return true;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public boolean isMinableBlock(Block aBlock, byte aMetaData)
/* 119:    */   {
/* 120:122 */     String tTool = aBlock.getHarvestTool(aMetaData);
/* 121:123 */     return ((tTool != null) && ((tTool.equals("hammer")) || (tTool.equals("pickaxe")))) || (aBlock.getMaterial() == Material.rock) || (aBlock.getMaterial() == Material.glass) || (aBlock.getMaterial() == Material.ice) || (aBlock.getMaterial() == Material.packedIce) || (GT_Recipe.GT_Recipe_Map.sHammerRecipes.containsInput(new ItemStack(aBlock, 1, aMetaData)));
/* 122:    */   }
/* 123:    */   
/* 124:    */   public int convertBlockDrops(List<ItemStack> aDrops, ItemStack aStack, EntityPlayer aPlayer, Block aBlock, int aX, int aY, int aZ, byte aMetaData, int aFortune, boolean aSilkTouch, BlockEvent.HarvestDropsEvent aEvent)
/* 125:    */   {
/* 126:128 */     int rConversions = 0;
/* 127:129 */     GT_Recipe tRecipe = GT_Recipe.GT_Recipe_Map.sHammerRecipes.findRecipe(null, true, 2147483647L, null, new ItemStack[] { new ItemStack(aBlock, 1, aMetaData) });
/* 128:130 */     if ((tRecipe == null) || (aBlock.hasTileEntity(aMetaData)))
/* 129:    */     {
/* 130:131 */       for (ItemStack tDrop : aDrops)
/* 131:    */       {
/* 132:132 */         tRecipe = GT_Recipe.GT_Recipe_Map.sHammerRecipes.findRecipe(null, true, 2147483647L, null, new ItemStack[] { GT_Utility.copyAmount(1L, new Object[] { tDrop }) });
/* 133:133 */         if (tRecipe != null)
/* 134:    */         {
/* 135:134 */           ItemStack tHammeringOutput = tRecipe.getOutput(0);
/* 136:135 */           if (tHammeringOutput != null)
/* 137:    */           {
/* 138:136 */             rConversions += tDrop.stackSize;
/* 139:137 */             tDrop.stackSize *= tHammeringOutput.stackSize;
/* 140:138 */             tHammeringOutput.stackSize = tDrop.stackSize;
/* 141:139 */             GT_Utility.setStack(tDrop, tHammeringOutput);
/* 142:    */           }
/* 143:    */         }
/* 144:    */       }
/* 145:    */     }
/* 146:    */     else
/* 147:    */     {
/* 148:144 */       aDrops.clear();
/* 149:145 */       aDrops.add(tRecipe.getOutput(0));
/* 150:146 */       rConversions++;
/* 151:    */     }
/* 152:148 */     return rConversions;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public ItemStack getBrokenItem(ItemStack aStack)
/* 156:    */   {
/* 157:153 */     return null;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack)
/* 161:    */   {
/* 162:158 */     return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mIconSet.mTextures[gregtech.api.enums.OrePrefixes.toolHeadHammer.mTextureIndex] : GT_MetaGenerated_Tool.getSecondaryMaterial(aStack).mIconSet.mTextures[gregtech.api.enums.OrePrefixes.stick.mTextureIndex];
/* 163:    */   }
/* 164:    */   
/* 165:    */   public short[] getRGBa(boolean aIsToolHead, ItemStack aStack)
/* 166:    */   {
/* 167:163 */     return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mRGBa : GT_MetaGenerated_Tool.getSecondaryMaterial(aStack).mRGBa;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void onStatsAddedToTool(GT_MetaGenerated_Tool aItem, int aID)
/* 171:    */   {
/* 172:168 */     aItem.addItemBehavior(aID, new Behaviour_Prospecting(1, 1000));
/* 173:    */   }
/* 174:    */   
/* 175:    */   public IChatComponent getDeathMessage(EntityLivingBase aPlayer, EntityLivingBase aEntity)
/* 176:    */   {
/* 177:173 */     return new ChatComponentText(EnumChatFormatting.RED + aEntity.getCommandSenderName() + EnumChatFormatting.WHITE + " was squashed by " + EnumChatFormatting.GREEN + aPlayer.getCommandSenderName() + EnumChatFormatting.WHITE);
/* 178:    */   }

				public void onToolCrafted(ItemStack aStack, EntityPlayer aPlayer)
/* 117:    */   {
/* 118:117 */     super.onToolCrafted(aStack, aPlayer);
				  GT_Mod.achievements.issueAchievement(aPlayer, "tools");
/* 121:    */   }
/* 179:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tools.GT_Tool_HardHammer
 * JD-Core Version:    0.7.0.1
 */