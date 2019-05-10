package gregtech.api.util;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.particle.CustomParticleHandler;
import codechicken.lib.render.particle.DigIconParticle;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Vector3;
import com.google.common.collect.Lists;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;

import java.util.Random;

public class ParticleHandlerUtil {

    public static void addBlockRunningEffects(World worldObj, Entity entity, TextureAtlasSprite atlasSprite) {
        Random rand = new Random();
        double posX = entity.posX + (rand.nextFloat() - 0.5) * entity.width;
        double posY = entity.getEntityBoundingBox().minY + 0.1;
        double posZ = entity.posZ + (rand.nextFloat() - 0.5) * entity.width;
        ParticleManager manager = Minecraft.getMinecraft().effectRenderer;
        manager.addEffect(new DigIconParticle(worldObj, posX, posY, posZ, -entity.motionX * 4.0, 1.5, -entity.motionZ * 4.0, atlasSprite));
    }

    public static void addBlockLandingEffects(World worldObj, Vector3 entityPos, TextureAtlasSprite atlasSprite, ParticleManager manager, int numParticles) {
        Vector3 start = entityPos.copy();
        Vector3 end = start.copy().add(Vector3.down.copy().multiply(4));
        RayTraceResult traceResult = worldObj.rayTraceBlocks(start.vec3(), end.vec3(), true, false, true);
        double speed = 0.15;
        Random randy = new Random();
        if (traceResult != null && traceResult.typeOfHit == Type.BLOCK && numParticles != 0) {
            for (int i = 0; i < numParticles; i++) {
                double mX = randy.nextGaussian() * speed;
                double mY = randy.nextGaussian() * speed;
                double mZ = randy.nextGaussian() * speed;
                manager.addEffect(DigIconParticle.newLandingParticle(worldObj, entityPos.x, entityPos.y, entityPos.z, mX, mY, mZ, atlasSprite));
            }
        }
    }

    public static void addBlockDestroyEffects(World worldObj, CuboidRayTraceResult result, TextureAtlasSprite atlasSprite, ParticleManager manager) {
        CustomParticleHandler.addBlockDestroyEffects(worldObj, result.cuboid6, Lists.newArrayList(atlasSprite), manager);
    }

    public static void addBlockDestroyEffects(IBlockState blockState, World worldObj, BlockPos blockPos, TextureAtlasSprite atlasSprite, ParticleManager manager) {
        Cuboid6 cuboid6 = new Cuboid6(blockState.getBoundingBox(worldObj, blockPos)).add(blockPos);
        CustomParticleHandler.addBlockDestroyEffects(worldObj, cuboid6, Lists.newArrayList(atlasSprite), manager);
    }

    public static void addHitEffects(World worldObj, CuboidRayTraceResult result, TextureAtlasSprite atlasSprite, ParticleManager manager) {
        CustomParticleHandler.addBlockHitEffects(worldObj, result.cuboid6, result.sideHit, atlasSprite, manager);
    }

    public static void addHitEffects(IBlockState blockState, World worldObj, RayTraceResult target, TextureAtlasSprite atlasSprite, ParticleManager manager) {
        Cuboid6 cuboid6 = getBoundingBox(blockState, worldObj, target);
        CustomParticleHandler.addBlockHitEffects(worldObj, cuboid6, target.sideHit, atlasSprite, manager);
    }

    private static Cuboid6 getBoundingBox(IBlockState blockState, World world, RayTraceResult target) {
        BlockPos blockPos = target.getBlockPos();
        if (target instanceof CuboidRayTraceResult) {
            return ((CuboidRayTraceResult) target).cuboid6.copy().add(blockPos);
        }
        return new Cuboid6(blockState.getBoundingBox(world, blockPos)).add(blockPos);
    }
}
