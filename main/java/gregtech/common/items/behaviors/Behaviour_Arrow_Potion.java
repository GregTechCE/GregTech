/*  1:   */ package gregtech.common.items.behaviors;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.SubTag;
/*  4:   */ import gregtech.api.items.GT_MetaBase_Item;
/*  5:   */ import gregtech.common.entities.GT_Entity_Arrow_Potion;
/*  6:   */ import java.util.Random;
/*  7:   */ import net.minecraft.enchantment.Enchantment;
/*  8:   */ import net.minecraft.entity.Entity;
/*  9:   */ import net.minecraft.entity.EntityLivingBase;
/* 10:   */ import net.minecraft.entity.player.EntityPlayer;
/* 11:   */ import net.minecraft.entity.projectile.EntityArrow;
/* 12:   */ import net.minecraft.item.ItemStack;
/* 13:   */ import net.minecraft.potion.PotionEffect;
/* 14:   */ import net.minecraft.world.World;
/* 15:   */ 
/* 16:   */ public class Behaviour_Arrow_Potion
/* 17:   */   extends Behaviour_Arrow
/* 18:   */ {
/* 19:   */   private final int[] mPotions;
/* 20:   */   
/* 21:   */   public Behaviour_Arrow_Potion(float aSpeed, float aPrecision, int... aPotions)
/* 22:   */   {
/* 23:19 */     super(GT_Entity_Arrow_Potion.class, aSpeed, aPrecision);
/* 24:20 */     this.mPotions = aPotions;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public Behaviour_Arrow_Potion(float aSpeed, float aPrecision, Enchantment aEnchantment, int aLevel, int... aPotions)
/* 28:   */   {
/* 29:24 */     super(GT_Entity_Arrow_Potion.class, aSpeed, aPrecision, aEnchantment, aLevel);
/* 30:25 */     this.mPotions = aPotions;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public boolean onLeftClickEntity(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, Entity aEntity)
/* 34:   */   {
/* 35:30 */     if ((aEntity instanceof EntityLivingBase)) {
/* 36:30 */       for (int i = 3; i < this.mPotions.length; i += 4) {
/* 37:30 */         if (aEntity.worldObj.rand.nextInt(100) < this.mPotions[i]) {
/* 38:30 */           ((EntityLivingBase)aEntity).addPotionEffect(new PotionEffect(this.mPotions[(i - 3)], this.mPotions[(i - 2)], this.mPotions[(i - 1)], false));
/* 39:   */         }
/* 40:   */       }
/* 41:   */     }
/* 42:31 */     return super.onLeftClickEntity(aItem, aStack, aPlayer, aEntity);
/* 43:   */   }
/* 44:   */   
/* 45:   */   public EntityArrow getProjectile(GT_MetaBase_Item aItem, SubTag aProjectileType, ItemStack aStack, World aWorld, double aX, double aY, double aZ)
/* 46:   */   {
/* 47:36 */     if (!hasProjectile(aItem, aProjectileType, aStack)) {
/* 48:36 */       return null;
/* 49:   */     }
/* 50:37 */     GT_Entity_Arrow_Potion rArrow = new GT_Entity_Arrow_Potion(aWorld, aX, aY, aZ);
/* 51:38 */     rArrow.setArrowItem(aStack);
/* 52:39 */     rArrow.setPotions(this.mPotions);
/* 53:40 */     return rArrow;
/* 54:   */   }
/* 55:   */   
/* 56:   */   public EntityArrow getProjectile(GT_MetaBase_Item aItem, SubTag aProjectileType, ItemStack aStack, World aWorld, EntityLivingBase aEntity, float aSpeed)
/* 57:   */   {
/* 58:45 */     if (!hasProjectile(aItem, aProjectileType, aStack)) {
/* 59:45 */       return null;
/* 60:   */     }
/* 61:46 */     GT_Entity_Arrow_Potion rArrow = new GT_Entity_Arrow_Potion(aWorld, aEntity, aSpeed);
/* 62:47 */     rArrow.setArrowItem(aStack);
/* 63:48 */     rArrow.setPotions(this.mPotions);
/* 64:49 */     return rArrow;
/* 65:   */   }
/* 66:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.items.behaviors.Behaviour_Arrow_Potion
 * JD-Core Version:    0.7.0.1
 */