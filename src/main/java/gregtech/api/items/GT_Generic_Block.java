package gregtech.api.items;

import codechicken.lib.render.particle.CustomParticleHandler;
import codechicken.lib.vec.Cuboid6;
import gregtech.api.enums.Textures;
import gregtech.api.util.GT_LanguageManager;
import gregtech.common.render.RenderBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;

import static gregtech.api.enums.GT_Values.W;

public abstract class GT_Generic_Block extends Block {

    public static IProperty<Integer> METADATA = PropertyInteger.create("metadata", 0, 15);

    public float minX, minY, minZ, maxX, maxY, maxZ;
    public final String mUnlocalizedName;

    protected GT_Generic_Block(Class<? extends ItemBlock> aItemClass, String aName, Material aMaterial) {
        super(aMaterial);
        setUnlocalizedName(mUnlocalizedName = aName);
        GameRegistry.registerBlock(this, aItemClass, getUnlocalizedName());
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + W + ".name", "Unnamed");
        setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
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
    public TextureAtlasSprite getParticleSprite(IBlockAccess worldObj, BlockPos aPos, EnumFacing side) {
        return getWorldIcon(worldObj, aPos, worldObj.getBlockState(aPos), side);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(TextureMap textureMap) {}

    @SideOnly(Side.CLIENT)
    public int getColorMultiplier(IBlockAccess aWorld, BlockPos aPos, IBlockState aState) {
        return 0xFFFFFFFF;
    }

    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getWorldIcon(IBlockAccess aWorld, BlockPos aPos, IBlockState aState, EnumFacing aSide) {
        return getIcon(aSide, aState.getValue(METADATA));
    }

    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getItemIcon(ItemStack itemStack, EnumFacing aSide) {
        return getIcon(aSide, itemStack.getItemDamage());
    }

    public TextureAtlasSprite getIcon(EnumFacing aSide, int metadata) {
        return null;
    }

    public void setBlockBounds(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }

    public void setBlockBoundsForItemRender() {}
    public void setBlockBoundsBasedOnState(IBlockAccess world, BlockPos pos, IBlockState state) {}

    public Cuboid6 getBlockBounds(BlockPos pos) {
        return new Cuboid6(pos.getX() + minX, pos.getY() + minY, pos.getZ() + minZ, pos.getX() + maxX, pos.getY() + maxY, pos.getZ() + maxX);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, METADATA);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(METADATA, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(METADATA);
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(this, 1, state.getValue(METADATA));
    }

    @Override
    public String getUnlocalizedName() {
        return mUnlocalizedName;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return RenderBlocks.INSTANCE.renderType;
    }
}