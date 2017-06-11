package gregtech.api.items;

import codechicken.lib.render.particle.CustomParticleHandler;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_Log;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.LoaderException;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public abstract class GenericBlock extends Block {

    private String unlocalizedName;

    protected GenericBlock(String name, @Nullable Class<? extends ItemBlock> itemClass, Material material) {
        super(material);

        setUnlocalizedName("gt." + name);
        setRegistryName(name);
        GameRegistry.register(this);

        if (itemClass != null) {
            ItemBlock itemBlock = null;
            try {
                itemBlock = itemClass.getConstructor(Block.class).newInstance(this);
            } catch(ReflectiveOperationException e){
                e.printStackTrace(GT_Log.err);
                throw new LoaderException(e);
            }
            GameRegistry.register(itemBlock, getRegistryName());
        }
    }

    @Override
    public String getLocalizedName() {
        return GT_LanguageManager.getTranslation(this.getUnlocalizedName() + ".name");
    }

    @Override
    public final Block setUnlocalizedName(String unlocalizedName) {
        this.unlocalizedName = unlocalizedName;
        return this;
    }

    public final String getUnlocalizedNameWithoutPrefix() {
        return unlocalizedName;
    }

    @Override
    public final String getUnlocalizedName() {
        return "block." + unlocalizedName;
    }


    @Override
    public boolean addLandingEffects(IBlockState state, WorldServer worldObj, BlockPos blockPosition, IBlockState iblockstate, EntityLivingBase entity, int numberOfParticles) {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean addHitEffects(IBlockState state, World worldObj, RayTraceResult target, ParticleManager manager) {
        CustomParticleHandler.addBlockHitEffects(worldObj, getBlockBounds(target.getBlockPos()), target.sideHit, getParticleSprite(worldObj, target.getBlockPos(), target.sideHit), manager);
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean addDestroyEffects(World world, BlockPos pos, ParticleManager manager) {
        TextureAtlasSprite[] textures = new TextureAtlasSprite[6];
        for(EnumFacing facing : EnumFacing.VALUES) {
            textures[facing.getIndex()] = getParticleSprite(world, pos, facing);
        }
        CustomParticleHandler.addBlockDestroyEffects(world, getBlockBounds(pos), textures, manager);
        return true;
    }


    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getParticleSprite(IBlockAccess worldObj, BlockPos pos, EnumFacing side) {
        return getWorldIcon(worldObj, pos, worldObj.getBlockState(pos), side);
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(TextureMap textureMap) {}

    public int damageDropped(IBlockState state) {
        return createStackedBlock(state).getMetadata();
    }

    @SideOnly(Side.CLIENT)
    public int getColorMultiplier(IBlockAccess world, BlockPos pos, IBlockState state) {
        return 0xFFFFFFFF;
    }

   @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getWorldIcon(IBlockAccess world, BlockPos pos, IBlockState state, EnumFacing side) {
        return getIcon(side, state.getValue(METADATA));
    }

    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getItemIcon(ItemStack itemStack, EnumFacing side) {
        return getIcon(side, itemStack.getItemDamage());
    }

    public TextureAtlasSprite getIcon(EnumFacing side, int metadata) {
        return null;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return RenderBlocks.INSTANCE.renderType;
    }
}