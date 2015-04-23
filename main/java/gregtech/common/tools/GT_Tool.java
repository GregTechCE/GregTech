/*   1:    */ package gregtech.common.tools;
/*   2:    */ 
/*   3:    */ import gregtech.api.GregTech_API;
/*   4:    */ import gregtech.api.damagesources.GT_DamageSources;
/*   5:    */ import gregtech.api.interfaces.IToolStats;
/*   6:    */ import gregtech.api.items.GT_MetaGenerated_Tool;

/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;

/*   9:    */ import net.minecraft.block.Block;
/*  10:    */ import net.minecraft.enchantment.Enchantment;
/*  11:    */ import net.minecraft.entity.Entity;
/*  12:    */ import net.minecraft.entity.EntityLivingBase;
/*  13:    */ import net.minecraft.entity.player.EntityPlayer;
/*  14:    */ import net.minecraft.item.ItemStack;
/*  15:    */ import net.minecraft.stats.AchievementList;
/*  16:    */ import net.minecraft.util.DamageSource;
/*  17:    */ import net.minecraft.util.EntityDamageSource;
/*  18:    */ import net.minecraft.util.IChatComponent;
import net.minecraftforge.event.world.BlockEvent;
/*  19:    */ import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
/*  20:    */ 
/*  21:    */ public abstract class GT_Tool
/*  22:    */   implements IToolStats
/*  23:    */ {
/*  24: 23 */   public static final Enchantment[] FORTUNE_ENCHANTMENT = { Enchantment.fortune };
/*  25: 24 */   public static final Enchantment[] LOOTING_ENCHANTMENT = { Enchantment.looting };
/*  26: 25 */   public static final Enchantment[] ZERO_ENCHANTMENTS = new Enchantment[0];
/*  27: 26 */   public static final int[] ZERO_ENCHANTMENT_LEVELS = new int[0];
/*  28:    */   
/*  29:    */   public int getToolDamagePerBlockBreak()
/*  30:    */   {
/*  31: 30 */     return 100;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public int getToolDamagePerDropConversion()
/*  35:    */   {
/*  36: 35 */     return 100;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public int getToolDamagePerContainerCraft()
/*  40:    */   {
/*  41: 40 */     return 800;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public int getToolDamagePerEntityAttack()
/*  45:    */   {
/*  46: 45 */     return 200;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public float getSpeedMultiplier()
/*  50:    */   {
/*  51: 50 */     return 1.0F;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public float getMaxDurabilityMultiplier()
/*  55:    */   {
/*  56: 55 */     return 1.0F;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public int getHurtResistanceTime(int aOriginalHurtResistance, Entity aEntity)
/*  60:    */   {
/*  61: 60 */     return aOriginalHurtResistance;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public String getMiningSound()
/*  65:    */   {
/*  66: 65 */     return null;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public String getCraftingSound()
/*  70:    */   {
/*  71: 70 */     return null;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public String getEntityHitSound()
/*  75:    */   {
/*  76: 75 */     return null;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public String getBreakingSound()
/*  80:    */   {
/*  81: 80 */     return (String)GregTech_API.sSoundList.get(Integer.valueOf(0));
/*  82:    */   }
/*  83:    */   
/*  84:    */   public int getBaseQuality()
/*  85:    */   {
/*  86: 85 */     return 0;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public boolean canBlock()
/*  90:    */   {
/*  91: 90 */     return false;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public boolean isCrowbar()
/*  95:    */   {
/*  96: 95 */     return false;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public boolean isGrafter()
/* 100:    */   {
/* 101:100 */     return false;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public boolean isWeapon()
/* 105:    */   {
/* 106:105 */     return false;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public boolean isRangedWeapon()
/* 110:    */   {
/* 111:110 */     return false;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public boolean isMiningTool()
/* 115:    */   {
/* 116:115 */     return true;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public DamageSource getDamageSource(EntityLivingBase aPlayer, Entity aEntity)
/* 120:    */   {
/* 121:120 */     return GT_DamageSources.getCombatDamage((aPlayer instanceof EntityPlayer) ? "player" : "mob", aPlayer, (aEntity instanceof EntityLivingBase) ? getDeathMessage(aPlayer, (EntityLivingBase)aEntity) : null);
/* 122:    */   }
/* 123:    */   
/* 124:    */   public IChatComponent getDeathMessage(EntityLivingBase aPlayer, EntityLivingBase aEntity)
/* 125:    */   {
/* 126:124 */     return new EntityDamageSource((aPlayer instanceof EntityPlayer) ? "player" : "mob", aPlayer).func_151519_b(aEntity);
/* 127:    */   }
/* 128:    */   
/* 129:    */   public int convertBlockDrops(List<ItemStack> aDrops, ItemStack aStack, EntityPlayer aPlayer, Block aBlock, int aX, int aY, int aZ, byte aMetaData, int aFortune, boolean aSilkTouch, BlockEvent.HarvestDropsEvent aEvent)
/* 130:    */   {
/* 131:129 */     return 0;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public ItemStack getBrokenItem(ItemStack aStack)
/* 135:    */   {
/* 136:134 */     return null;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public Enchantment[] getEnchantments(ItemStack aStack)
/* 140:    */   {
/* 141:139 */     return ZERO_ENCHANTMENTS;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public int[] getEnchantmentLevels(ItemStack aStack)
/* 145:    */   {
/* 146:144 */     return ZERO_ENCHANTMENT_LEVELS;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void onToolCrafted(ItemStack aStack, EntityPlayer aPlayer)
/* 150:    */   {
/* 151:149 */     aPlayer.triggerAchievement(AchievementList.openInventory);
/* 152:150 */     aPlayer.triggerAchievement(AchievementList.mineWood);
/* 153:151 */     aPlayer.triggerAchievement(AchievementList.buildWorkBench);
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void onStatsAddedToTool(GT_MetaGenerated_Tool aItem, int aID) {}
/* 157:    */   
/* 158:    */   public float getNormalDamageAgainstEntity(float aOriginalDamage, Entity aEntity, ItemStack aStack, EntityPlayer aPlayer)
/* 159:    */   {
/* 160:161 */     return aOriginalDamage;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public float getMagicDamageAgainstEntity(float aOriginalDamage, Entity aEntity, ItemStack aStack, EntityPlayer aPlayer)
/* 164:    */   {
/* 165:166 */     return aOriginalDamage;
/* 166:    */   }
/* 167:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tools.GT_Tool
 * JD-Core Version:    0.7.0.1
 */