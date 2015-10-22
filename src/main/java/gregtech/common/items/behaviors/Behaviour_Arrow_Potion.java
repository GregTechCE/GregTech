package gregtech.common.items.behaviors;

import gregtech.api.enums.SubTag;
import gregtech.api.items.GT_MetaBase_Item;
import gregtech.common.entities.GT_Entity_Arrow_Potion;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class Behaviour_Arrow_Potion
        extends Behaviour_Arrow {
    private final int[] mPotions;

    public Behaviour_Arrow_Potion(float aSpeed, float aPrecision, int... aPotions) {
        super(GT_Entity_Arrow_Potion.class, aSpeed, aPrecision);
        this.mPotions = aPotions;
    }

    public Behaviour_Arrow_Potion(float aSpeed, float aPrecision, Enchantment aEnchantment, int aLevel, int... aPotions) {
        super(GT_Entity_Arrow_Potion.class, aSpeed, aPrecision, aEnchantment, aLevel);
        this.mPotions = aPotions;
    }

    public boolean onLeftClickEntity(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, Entity aEntity) {
        if ((aEntity instanceof EntityLivingBase)) {
            for (int i = 3; i < this.mPotions.length; i += 4) {
                if (aEntity.worldObj.rand.nextInt(100) < this.mPotions[i]) {
                    ((EntityLivingBase) aEntity).addPotionEffect(new PotionEffect(this.mPotions[(i - 3)], this.mPotions[(i - 2)], this.mPotions[(i - 1)], false));
                }
            }
        }
        return super.onLeftClickEntity(aItem, aStack, aPlayer, aEntity);
    }

    public EntityArrow getProjectile(GT_MetaBase_Item aItem, SubTag aProjectileType, ItemStack aStack, World aWorld, double aX, double aY, double aZ) {
        if (!hasProjectile(aItem, aProjectileType, aStack)) {
            return null;
        }
        GT_Entity_Arrow_Potion rArrow = new GT_Entity_Arrow_Potion(aWorld, aX, aY, aZ);
        rArrow.setArrowItem(aStack);
        rArrow.setPotions(this.mPotions);
        return rArrow;
    }

    public EntityArrow getProjectile(GT_MetaBase_Item aItem, SubTag aProjectileType, ItemStack aStack, World aWorld, EntityLivingBase aEntity, float aSpeed) {
        if (!hasProjectile(aItem, aProjectileType, aStack)) {
            return null;
        }
        GT_Entity_Arrow_Potion rArrow = new GT_Entity_Arrow_Potion(aWorld, aEntity, aSpeed);
        rArrow.setArrowItem(aStack);
        rArrow.setPotions(this.mPotions);
        return rArrow;
    }
}
