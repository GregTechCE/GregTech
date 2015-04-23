/*   1:    */ package gregtech.common.items.behaviors;
/*   2:    */ 
/*   3:    */ import codechicken.lib.math.MathHelper;
/*   4:    */ import gregtech.api.GregTech_API;
/*   5:    */ import gregtech.api.items.GT_MetaBase_Item;
/*   6:    */ import gregtech.api.util.GT_LanguageManager;
/*   7:    */ import gregtech.api.util.GT_Utility;
/*   8:    */ import gregtech.api.util.GT_Utility.ItemNBT;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import net.minecraft.entity.Entity;
/*  12:    */ import net.minecraft.entity.monster.EntityCreeper;
/*  13:    */ import net.minecraft.entity.player.EntityPlayer;
/*  14:    */ import net.minecraft.entity.player.PlayerCapabilities;
/*  15:    */ import net.minecraft.init.Blocks;
/*  16:    */ import net.minecraft.init.Items;
/*  17:    */ import net.minecraft.item.Item;
/*  18:    */ import net.minecraft.item.ItemStack;
/*  19:    */ import net.minecraft.nbt.NBTTagCompound;
/*  20:    */ import net.minecraft.world.World;
/*  21:    */ import net.minecraftforge.common.util.ForgeDirection;
/*  22:    */ 
/*  23:    */ public class Behaviour_Lighter
/*  24:    */   extends Behaviour_None
/*  25:    */ {
/*  26:    */   private final ItemStack mEmptyLighter;
/*  27:    */   private final ItemStack mUsedLighter;
/*  28:    */   private final ItemStack mFullLighter;
/*  29:    */   private final long mFuelAmount;
/*  30:    */   
/*  31:    */   public Behaviour_Lighter(ItemStack aEmptyLighter, ItemStack aUsedLighter, ItemStack aFullLighter, long aFuelAmount)
/*  32:    */   {
/*  33: 26 */     this.mFullLighter = aFullLighter;
/*  34: 27 */     this.mUsedLighter = aUsedLighter;
/*  35: 28 */     this.mEmptyLighter = aEmptyLighter;
/*  36: 29 */     this.mFuelAmount = aFuelAmount;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public boolean onLeftClickEntity(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, Entity aEntity)
/*  40:    */   {
/*  41: 34 */     if ((aPlayer.worldObj.isRemote) || (aStack.stackSize != 1)) {
/*  42: 34 */       return false;
/*  43:    */     }
/*  44: 36 */     boolean rOutput = false;
/*  45: 38 */     if ((aEntity instanceof EntityCreeper))
/*  46:    */     {
/*  47: 39 */       prepare(aStack);
/*  48: 40 */       long tFuelAmount = GT_Utility.ItemNBT.getLighterFuel(aStack);
/*  49: 41 */       if (GT_Utility.areStacksEqual(aStack, this.mUsedLighter, true))
/*  50:    */       {
/*  51: 42 */         GT_Utility.sendSoundToPlayers(aPlayer.worldObj, (String)GregTech_API.sSoundList.get(Integer.valueOf(6)), 1.0F, 1.0F, MathHelper.floor_double(aEntity.posX), MathHelper.floor_double(aEntity.posY), MathHelper.floor_double(aEntity.posZ));
/*  52: 43 */         ((EntityCreeper)aEntity).func_146079_cb();
/*  53: 44 */         if (!aPlayer.capabilities.isCreativeMode) {
/*  54: 44 */           tFuelAmount -= 1L;
/*  55:    */         }
/*  56: 45 */         rOutput = true;
/*  57:    */       }
/*  58: 47 */       GT_Utility.ItemNBT.setLighterFuel(aStack, tFuelAmount);
/*  59: 48 */       if (tFuelAmount <= 0L) {
/*  60: 48 */         useUp(aStack);
/*  61:    */       }
/*  62:    */     }
/*  63: 50 */     return rOutput;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public boolean onItemUse(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide, float hitX, float hitY, float hitZ)
/*  67:    */   {
/*  68: 55 */     return false;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public boolean onItemUseFirst(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide, float hitX, float hitY, float hitZ)
/*  72:    */   {
/*  73: 60 */     if ((aWorld.isRemote) || (aStack.stackSize != 1)) {
/*  74: 60 */       return false;
/*  75:    */     }
/*  76: 62 */     boolean rOutput = false;
/*  77:    */     
/*  78: 64 */     ForgeDirection tDirection = ForgeDirection.getOrientation(aSide);
/*  79: 65 */     aX += tDirection.offsetX;aY += tDirection.offsetY;aZ += tDirection.offsetZ;
/*  80: 67 */     if ((!GT_Utility.isAirBlock(aWorld, aX, aY, aZ)) || (!aPlayer.canPlayerEdit(aX, aY, aZ, aSide, aStack))) {
/*  81: 67 */       return false;
/*  82:    */     }
/*  83: 68 */     prepare(aStack);
/*  84: 69 */     long tFuelAmount = GT_Utility.ItemNBT.getLighterFuel(aStack);
/*  85: 70 */     if (GT_Utility.areStacksEqual(aStack, this.mUsedLighter, true))
/*  86:    */     {
/*  87: 71 */       GT_Utility.sendSoundToPlayers(aWorld, (String)GregTech_API.sSoundList.get(Integer.valueOf(6)), 1.0F, 1.0F, aX, aY, aZ);
/*  88: 72 */       aWorld.setBlock(aX, aY, aZ, Blocks.fire);
/*  89: 73 */       if (!aPlayer.capabilities.isCreativeMode) {
/*  90: 73 */         tFuelAmount -= 1L;
/*  91:    */       }
/*  92: 74 */       rOutput = true;
/*  93:    */     }
/*  94: 76 */     GT_Utility.ItemNBT.setLighterFuel(aStack, tFuelAmount);
/*  95: 77 */     if (tFuelAmount <= 0L) {
/*  96: 77 */       useUp(aStack);
/*  97:    */     }
/*  98: 78 */     return rOutput;
/*  99:    */   }
/* 100:    */   
/* 101:    */   private void prepare(ItemStack aStack)
/* 102:    */   {
/* 103: 82 */     if (GT_Utility.areStacksEqual(aStack, this.mFullLighter, true))
/* 104:    */     {
/* 105: 83 */       aStack.func_150996_a(this.mUsedLighter.getItem());
/* 106: 84 */       Items.feather.setDamage(aStack, Items.feather.getDamage(this.mUsedLighter));
/* 107: 85 */       GT_Utility.ItemNBT.setLighterFuel(aStack, this.mFuelAmount);
/* 108:    */     }
/* 109:    */   }
/* 110:    */   
/* 111:    */   private void useUp(ItemStack aStack)
/* 112:    */   {
/* 113: 90 */     if (this.mEmptyLighter == null)
/* 114:    */     {
/* 115: 91 */       aStack.stackSize -= 1;
/* 116:    */     }
/* 117:    */     else
/* 118:    */     {
/* 119: 93 */       aStack.func_150996_a(this.mEmptyLighter.getItem());
/* 120: 94 */       Items.feather.setDamage(aStack, Items.feather.getDamage(this.mEmptyLighter));
/* 121:    */     }
/* 122:    */   }
/* 123:    */   
/* 124: 98 */   private final String mTooltip = GT_LanguageManager.addStringLocalization("gt.behaviour.lighter.tooltip", "Can light things on Fire");
/* 125: 99 */   private final String mTooltipUses = GT_LanguageManager.addStringLocalization("gt.behaviour.lighter.uses", "Remaining Uses:");
/* 126:100 */   private final String mTooltipUnstackable = GT_LanguageManager.addStringLocalization("gt.behaviour.unstackable", "Not usable when stacked!");
/* 127:    */   
/* 128:    */   public List<String> getAdditionalToolTips(GT_MetaBase_Item aItem, List<String> aList, ItemStack aStack)
/* 129:    */   {
/* 130:104 */     aList.add(this.mTooltip);
/* 131:105 */     NBTTagCompound tNBT = aStack.getTagCompound();
/* 132:106 */     long tFuelAmount = tNBT == null ? 0L : GT_Utility.areStacksEqual(aStack, this.mFullLighter, true) ? this.mFuelAmount : tNBT.getLong("GT.LighterFuel");
/* 133:107 */     aList.add(this.mTooltipUses + " " + tFuelAmount);
/* 134:108 */     aList.add(this.mTooltipUnstackable);
/* 135:109 */     return aList;
/* 136:    */   }
/* 137:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.items.behaviors.Behaviour_Lighter
 * JD-Core Version:    0.7.0.1
 */