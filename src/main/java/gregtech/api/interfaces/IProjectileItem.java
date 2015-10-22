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
    public boolean hasProjectile(SubTag aProjectileType, ItemStack aStack);

    /**
     * @return an Arrow Entity to be spawned. If null then this is not an Arrow. Note: Other Projectiles still extend EntityArrow
     */
    public EntityArrow getProjectile(SubTag aProjectileType, ItemStack aStack, World aWorld, double aX, double aY, double aZ);

    /**
     * @return an Arrow Entity to be spawned. If null then this is not an Arrow. Note: Other Projectiles still extend EntityArrow
     */
    public EntityArrow getProjectile(SubTag aProjectileType, ItemStack aStack, World aWorld, EntityLivingBase aEntity, float aSpeed);
}