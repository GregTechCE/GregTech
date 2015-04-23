/*   1:    */ package gregtech.common.items.behaviors;
/*   2:    */ 
/*   3:    */ import gregtech.api.enums.SubTag;
/*   4:    */ import gregtech.api.items.GT_MetaBase_Item;
/*   5:    */ import gregtech.api.util.GT_Utility;
/*   6:    */ import gregtech.api.util.GT_Utility.GT_EnchantmentHelper;
/*   7:    */ import gregtech.api.util.GT_Utility.ItemNBT;
/*   8:    */ import gregtech.common.entities.GT_Entity_Arrow;
/*   9:    */ import net.minecraft.block.BlockDispenser;
/*  10:    */ import net.minecraft.dispenser.IBlockSource;
/*  11:    */ import net.minecraft.dispenser.IPosition;
/*  12:    */ import net.minecraft.enchantment.Enchantment;
/*  13:    */ import net.minecraft.entity.Entity;
/*  14:    */ import net.minecraft.entity.EntityLivingBase;
/*  15:    */ import net.minecraft.entity.player.EntityPlayer;
/*  16:    */ import net.minecraft.entity.player.PlayerCapabilities;
/*  17:    */ import net.minecraft.entity.projectile.EntityArrow;
/*  18:    */ import net.minecraft.item.ItemStack;
/*  19:    */ import net.minecraft.nbt.NBTTagCompound;
/*  20:    */ import net.minecraft.util.EnumFacing;
/*  21:    */ import net.minecraft.world.World;
/*  22:    */ 
/*  23:    */ public class Behaviour_Arrow
/*  24:    */   extends Behaviour_None
/*  25:    */ {
/*  26: 22 */   public static Behaviour_Arrow DEFAULT_WOODEN = new Behaviour_Arrow(GT_Entity_Arrow.class, 1.0F, 6.0F);
/*  27: 23 */   public static Behaviour_Arrow DEFAULT_PLASTIC = new Behaviour_Arrow(GT_Entity_Arrow.class, 1.5F, 6.0F);
/*  28:    */   private final int mLevel;
/*  29:    */   private final Enchantment mEnchantment;
/*  30:    */   private final float mSpeedMultiplier;
/*  31:    */   private final float mPrecision;
/*  32:    */   private final Class<? extends GT_Entity_Arrow> mArrow;
/*  33:    */   
/*  34:    */   public Behaviour_Arrow(Class<? extends GT_Entity_Arrow> aArrow, float aSpeed, float aPrecision)
/*  35:    */   {
/*  36: 31 */     this(aArrow, aSpeed, aPrecision, null, 0);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public Behaviour_Arrow(Class<? extends GT_Entity_Arrow> aArrow, float aSpeed, float aPrecision, Enchantment aEnchantment, int aLevel)
/*  40:    */   {
/*  41: 35 */     this.mArrow = aArrow;
/*  42: 36 */     this.mSpeedMultiplier = aSpeed;
/*  43: 37 */     this.mPrecision = aPrecision;
/*  44: 38 */     this.mEnchantment = aEnchantment;
/*  45: 39 */     this.mLevel = aLevel;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public boolean onLeftClickEntity(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, Entity aEntity)
/*  49:    */   {
/*  50: 44 */     if ((aEntity instanceof EntityLivingBase))
/*  51:    */     {
/*  52: 45 */       GT_Utility.GT_EnchantmentHelper.applyBullshitA((EntityLivingBase)aEntity, aPlayer, aStack);
/*  53: 46 */       GT_Utility.GT_EnchantmentHelper.applyBullshitB(aPlayer, aEntity, aStack);
/*  54: 47 */       if (!aPlayer.capabilities.isCreativeMode) {
/*  55: 47 */         aStack.stackSize -= 1;
/*  56:    */       }
/*  57: 48 */       if (aStack.stackSize <= 0) {
/*  58: 48 */         aPlayer.destroyCurrentEquippedItem();
/*  59:    */       }
/*  60: 49 */       return false;
/*  61:    */     }
/*  62: 51 */     return false;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public boolean isItemStackUsable(GT_MetaBase_Item aItem, ItemStack aStack)
/*  66:    */   {
/*  67: 56 */     if ((this.mEnchantment != null) && (this.mLevel > 0))
/*  68:    */     {
/*  69: 57 */       NBTTagCompound tNBT = GT_Utility.ItemNBT.getNBT(aStack);
/*  70: 58 */       if (!tNBT.getBoolean("GT.HasBeenUpdated"))
/*  71:    */       {
/*  72: 59 */         tNBT.setBoolean("GT.HasBeenUpdated", true);
/*  73: 60 */         GT_Utility.ItemNBT.setNBT(aStack, tNBT);
/*  74: 61 */         GT_Utility.ItemNBT.addEnchantment(aStack, this.mEnchantment, this.mLevel);
/*  75:    */       }
/*  76:    */     }
/*  77: 64 */     return true;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public boolean canDispense(GT_MetaBase_Item aItem, IBlockSource aSource, ItemStack aStack)
/*  81:    */   {
/*  82: 69 */     return true;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public ItemStack onDispense(GT_MetaBase_Item aItem, IBlockSource aSource, ItemStack aStack)
/*  86:    */   {
/*  87: 74 */     World aWorld = aSource.getWorld();
/*  88: 75 */     IPosition tPosition = BlockDispenser.func_149939_a(aSource);
/*  89: 76 */     EnumFacing tFacing = BlockDispenser.func_149937_b(aSource.getBlockMetadata());
/*  90: 77 */     GT_Entity_Arrow tEntityArrow = (GT_Entity_Arrow)getProjectile(aItem, SubTag.PROJECTILE_ARROW, aStack, aWorld, tPosition.getX(), tPosition.getY(), tPosition.getZ());
/*  91: 78 */     if (tEntityArrow != null)
/*  92:    */     {
/*  93: 79 */       tEntityArrow.setThrowableHeading(tFacing.getFrontOffsetX(), tFacing.getFrontOffsetY() + 0.1F, tFacing.getFrontOffsetZ(), this.mSpeedMultiplier * 1.1F, this.mPrecision);
/*  94: 80 */       tEntityArrow.setArrowItem(aStack);
/*  95: 81 */       tEntityArrow.canBePickedUp = 1;
/*  96: 82 */       aWorld.spawnEntityInWorld(tEntityArrow);
/*  97: 83 */       if (aStack.stackSize < 100) {
/*  98: 83 */         aStack.stackSize -= 1;
/*  99:    */       }
/* 100: 84 */       return aStack;
/* 101:    */     }
/* 102: 86 */     return super.onDispense(aItem, aSource, aStack);
/* 103:    */   }
/* 104:    */   
/* 105:    */   public boolean hasProjectile(GT_MetaBase_Item aItem, SubTag aProjectileType, ItemStack aStack)
/* 106:    */   {
/* 107: 91 */     return aProjectileType == SubTag.PROJECTILE_ARROW;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public EntityArrow getProjectile(GT_MetaBase_Item aItem, SubTag aProjectileType, ItemStack aStack, World aWorld, double aX, double aY, double aZ)
/* 111:    */   {
/* 112: 96 */     if (!hasProjectile(aItem, aProjectileType, aStack)) {
/* 113: 96 */       return null;
/* 114:    */     }
/* 115: 97 */     GT_Entity_Arrow rArrow = (GT_Entity_Arrow)GT_Utility.callConstructor(this.mArrow.getName(), -1, null, true, new Object[] { aWorld, Double.valueOf(aX), Double.valueOf(aY), Double.valueOf(aZ) });
/* 116: 98 */     rArrow.setArrowItem(aStack);
/* 117: 99 */     return rArrow;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public EntityArrow getProjectile(GT_MetaBase_Item aItem, SubTag aProjectileType, ItemStack aStack, World aWorld, EntityLivingBase aEntity, float aSpeed)
/* 121:    */   {
/* 122:104 */     if (!hasProjectile(aItem, aProjectileType, aStack)) {
/* 123:104 */       return null;
/* 124:    */     }
/* 125:105 */     GT_Entity_Arrow rArrow = (GT_Entity_Arrow)GT_Utility.callConstructor(this.mArrow.getName(), -1, null, true, new Object[] { aWorld, aEntity, Float.valueOf(this.mSpeedMultiplier * aSpeed) });
/* 126:106 */     rArrow.setArrowItem(aStack);
/* 127:107 */     return rArrow;
/* 128:    */   }
/* 129:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.items.behaviors.Behaviour_Arrow
 * JD-Core Version:    0.7.0.1
 */