package gregtech.api.block;

import codechicken.lib.vec.Vector3;
import gregtech.api.net.NetworkHandler;
import gregtech.api.net.PacketBlockParticle;
import gregtech.api.util.ParticleHandlerUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class BlockCustomParticle extends Block implements ICustomParticleBlock {

    public BlockCustomParticle(Material blockMaterialIn, MapColor blockMapColorIn) {
        super(blockMaterialIn, blockMapColorIn);
    }

    public BlockCustomParticle(Material materialIn) {
        super(materialIn);
    }

    @SideOnly(Side.CLIENT)
    protected abstract TextureAtlasSprite getParticleTexture(World world, BlockPos blockPos);

    @Override
    @SideOnly(Side.CLIENT)
    public boolean addHitEffects(IBlockState state, World worldObj, RayTraceResult target, ParticleManager manager) {
        TextureAtlasSprite atlasSprite = getParticleTexture(worldObj, target.getBlockPos());
        ParticleHandlerUtil.addHitEffects(state, worldObj, target, atlasSprite, manager);
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean addDestroyEffects(World world, BlockPos pos, ParticleManager manager) {
        TextureAtlasSprite atlasSprite = getParticleTexture(world, pos);
        ParticleHandlerUtil.addBlockDestroyEffects(world.getBlockState(pos), world, pos, atlasSprite, manager);
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void handleCustomParticle(World worldObj, BlockPos blockPos, ParticleManager particleManager, Vector3 entityPos, int numberOfParticles) {
        TextureAtlasSprite atlasSprite = getParticleTexture(worldObj, blockPos);
        ParticleHandlerUtil.addBlockLandingEffects(worldObj, entityPos, atlasSprite, particleManager, numberOfParticles);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean addRunningEffects(IBlockState state, World world, BlockPos pos, Entity entity) {
        if(world.isRemote) {
            TextureAtlasSprite atlasSprite = getParticleTexture(world, pos);
            ParticleHandlerUtil.addBlockRunningEffects(world, entity, atlasSprite);
        }
        return true;
    }

    @Override
    public boolean addLandingEffects(IBlockState state, WorldServer worldObj, BlockPos blockPosition, IBlockState iblockstate, EntityLivingBase entity, int numberOfParticles) {
        NetworkHandler.channel.sendToAllTracking(
            new PacketBlockParticle(blockPosition, new Vector3(entity.posX, entity.posY, entity.posZ), numberOfParticles).toFMLPacket(),
            NetworkHandler.blockPoint(worldObj, blockPosition));
        return true;
    }
}
