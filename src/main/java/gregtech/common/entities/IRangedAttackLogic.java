package gregtech.common.entities;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;

public interface IRangedAttackLogic {

    void attackWithRangedAttack(EntityLiving attacker, EntityLivingBase target, float distanceFactor);

}
