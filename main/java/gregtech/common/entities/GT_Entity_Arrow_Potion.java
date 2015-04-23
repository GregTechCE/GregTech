/*  1:   */ package gregtech.common.entities;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.entity.Entity;
/*  5:   */ import net.minecraft.entity.EntityLivingBase;
/*  6:   */ import net.minecraft.item.ItemStack;
/*  7:   */ import net.minecraft.nbt.NBTTagCompound;
/*  8:   */ import net.minecraft.potion.PotionEffect;
/*  9:   */ import net.minecraft.world.World;
/* 10:   */ 
/* 11:   */ public class GT_Entity_Arrow_Potion
/* 12:   */   extends GT_Entity_Arrow
/* 13:   */ {
/* 14:   */   public GT_Entity_Arrow_Potion(World aWorld)
/* 15:   */   {
/* 16:13 */     super(aWorld);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public GT_Entity_Arrow_Potion(World aWorld, double aX, double aY, double aZ)
/* 20:   */   {
/* 21:17 */     super(aWorld, aX, aY, aZ);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public GT_Entity_Arrow_Potion(World aWorld, EntityLivingBase aEntity, float aSpeed)
/* 25:   */   {
/* 26:21 */     super(aWorld, aEntity, aSpeed);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void writeEntityToNBT(NBTTagCompound aNBT)
/* 30:   */   {
/* 31:26 */     super.writeEntityToNBT(aNBT);
/* 32:27 */     aNBT.setIntArray("mPotions", this.mPotions);
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void readEntityFromNBT(NBTTagCompound aNBT)
/* 36:   */   {
/* 37:32 */     super.readEntityFromNBT(aNBT);
/* 38:33 */     setPotions(aNBT.getIntArray("mPotions"));
/* 39:   */   }
/* 40:   */   
/* 41:   */   public boolean breaksOnImpact()
/* 42:   */   {
/* 43:38 */     return true;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public void setPotions(int... aPotions)
/* 47:   */   {
/* 48:49 */     if (aPotions != null) {
/* 49:49 */       this.mPotions = aPotions;
/* 50:   */     }
/* 51:   */   }
/* 52:   */   
/* 53:   */   public int[] getPotions()
/* 54:   */   {
/* 55:53 */     return this.mPotions;
/* 56:   */   }
/* 57:   */   
/* 58:56 */   private int[] mPotions = new int[0];
/* 59:   */   
/* 60:   */   public int[] onHitEntity(Entity aHitEntity, Entity aShootingEntity, ItemStack aArrow, int aRegularDamage, int aMagicDamage, int aKnockback, int aFireDamage, int aHitTimer)
/* 61:   */   {
/* 62:60 */     if ((aHitEntity instanceof EntityLivingBase)) {
/* 63:60 */       for (int i = 3; i < this.mPotions.length; i += 4) {
/* 64:61 */         if (aHitEntity.worldObj.rand.nextInt(100) < this.mPotions[i]) {
/* 65:62 */           ((EntityLivingBase)aHitEntity).addPotionEffect(new PotionEffect(this.mPotions[(i - 3)], this.mPotions[(i - 2)], this.mPotions[(i - 1)], false));
/* 66:   */         }
/* 67:   */       }
/* 68:   */     }
/* 69:65 */     return super.onHitEntity(aHitEntity, aShootingEntity, aArrow, 1, aMagicDamage, aKnockback, aFireDamage, aHitTimer);
/* 70:   */   }
/* 71:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.entities.GT_Entity_Arrow_Potion
 * JD-Core Version:    0.7.0.1
 */