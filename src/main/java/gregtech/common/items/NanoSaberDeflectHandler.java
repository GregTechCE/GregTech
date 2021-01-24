package gregtech.common.items;

import gregtech.api.GTValues;
import gregtech.common.items.behaviors.NanoSaberBehavior;
import gregtech.common.items.behaviors.ToggleEnergyConsumerBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(modid = GTValues.MODID)
public class NanoSaberDeflectHandler {

    @SubscribeEvent
    public static void onProjectileHit(ProjectileImpactEvent event) {
        if (event.getRayTraceResult().typeOfHit == Type.ENTITY && event.getRayTraceResult().entityHit instanceof EntityPlayer) {
            EntityPlayer hitPlayer = (EntityPlayer) event.getRayTraceResult().entityHit;
            Entity projectile = event.getEntity();
            ItemStack itemInHand = hitPlayer.getActiveItemStack();
            if (hitPlayer.isHandActive() && MetaItems.NANO_SABER.isItemEqual(itemInHand) && ToggleEnergyConsumerBehavior.isItemActive(itemInHand) && canDeflectProjectile(hitPlayer, projectile)) {
                Vec3d lookVec = hitPlayer.getLookVec();
                float power = MathHelper.sqrt(projectile.motionX * projectile.motionX + projectile.motionY * projectile.motionY + projectile.motionZ * projectile.motionZ);
                projectile.motionX = lookVec.x * power;
                projectile.motionY = lookVec.y * power;
                projectile.motionZ = lookVec.z * power;
                projectile.rotationYaw = hitPlayer.rotationYaw;
                projectile.rotationPitch = hitPlayer.rotationPitch;
                projectile.velocityChanged = true;
                NanoSaberBehavior.playBlockAnimation(hitPlayer, itemInHand);
                if (hitPlayer.world instanceof WorldServer) {
                    ((WorldServer) hitPlayer.world).spawnParticle(EnumParticleTypes.REDSTONE, projectile.posX, projectile.posY, projectile.posZ, 5, 0.0, 0.3, 0.0, 0.01);
                }
                event.setCanceled(true);
            }
        }
    }

    //player can only deflect projectiles in field of view
    private static boolean canDeflectProjectile(EntityPlayer entityPlayer, Entity projectile) {
        double dX = projectile.posX - entityPlayer.posX;
        double dZ = projectile.posZ - entityPlayer.posZ;
        float targetYaw = MathHelper.wrapDegrees((float) ((MathHelper.atan2(dZ, dX) * (180D / Math.PI)) - 90.0F));
        float currentYaw = MathHelper.wrapDegrees(entityPlayer.rotationYaw);
        return Math.abs((targetYaw + 360.0f) % 360 - (currentYaw + 360.0f) % 360) <= 90;
    }
}
