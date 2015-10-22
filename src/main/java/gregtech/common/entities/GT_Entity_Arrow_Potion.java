package gregtech.common.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class GT_Entity_Arrow_Potion
        extends GT_Entity_Arrow {
    private int[] mPotions = new int[0];

    public GT_Entity_Arrow_Potion(World aWorld) {
        super(aWorld);
    }

    public GT_Entity_Arrow_Potion(World aWorld, double aX, double aY, double aZ) {
        super(aWorld, aX, aY, aZ);
    }

    public GT_Entity_Arrow_Potion(World aWorld, EntityLivingBase aEntity, float aSpeed) {
        super(aWorld, aEntity, aSpeed);
    }

    public void writeEntityToNBT(NBTTagCompound aNBT) {
        super.writeEntityToNBT(aNBT);
        aNBT.setIntArray("mPotions", this.mPotions);
    }

    public void readEntityFromNBT(NBTTagCompound aNBT) {
        super.readEntityFromNBT(aNBT);
        setPotions(aNBT.getIntArray("mPotions"));
    }

    public boolean breaksOnImpact() {
        return true;
    }

    public int[] getPotions() {
        return this.mPotions;
    }

    public void setPotions(int... aPotions) {
        if (aPotions != null) {
            this.mPotions = aPotions;
        }
    }

    public int[] onHitEntity(Entity aHitEntity, Entity aShootingEntity, ItemStack aArrow, int aRegularDamage, int aMagicDamage, int aKnockback, int aFireDamage, int aHitTimer) {
        if ((aHitEntity instanceof EntityLivingBase)) {
            for (int i = 3; i < this.mPotions.length; i += 4) {
                if (aHitEntity.worldObj.rand.nextInt(100) < this.mPotions[i]) {
                    ((EntityLivingBase) aHitEntity).addPotionEffect(new PotionEffect(this.mPotions[(i - 3)], this.mPotions[(i - 2)], this.mPotions[(i - 1)], false));
                }
            }
        }
        return super.onHitEntity(aHitEntity, aShootingEntity, aArrow, 1, aMagicDamage, aKnockback, aFireDamage, aHitTimer);
    }
}
