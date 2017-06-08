package gregtech.api.interfaces;

import gregtech.api.enums.SubTag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IProjectileItem {

    /**
     * @return if this Item has an Arrow Entity
     */
    boolean hasProjectile(SubTag projectileType, ItemStack itemStack);

    /**
     * @return an Arrow Entity to be spawned. If null then this is not an Arrow. Note: Other Projectiles still extend EntityArrow
     */
    EntityArrow getProjectile(SubTag projectileType, ItemStack itemStack, World world, double x, double y, double z);

    /**
     * @return an Arrow Entity to be spawned. If null then this is not an Arrow. Note: Other Projectiles still extend EntityArrow
     */
    EntityArrow getProjectile(SubTag projectileType, ItemStack itemStack, World world, EntityLivingBase entity, float speed);

}