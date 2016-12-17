package gregtech.api.items;

import codechicken.lib.render.DigIconParticle;
import gregtech.api.util.GT_LanguageManager;
import gregtech.common.render.RenderBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.ParticleDigging;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

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
        addBlockHitEffects(worldObj, target.getBlockPos() != null ? target.getBlockPos() : BlockPos.ORIGIN,
                target.sideHit != null ? target.sideHit : EnumFacing.UP, worldObj.rand, manager);
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean addDestroyEffects(World world, BlockPos pos, ParticleManager manager) {
        int count = 14 + world.rand.nextInt(10);
        for(int i = 0; i < count; i++) {
            addBlockHitEffects(world, pos, EnumFacing.UP, world.rand, manager);
        }
        return true;
    }

    @SideOnly(Side.CLIENT)
    public void addBlockHitEffects(World worldObj, BlockPos pos, EnumFacing side, Random rand, ParticleManager manager)
    {
        IBlockState iblockstate = worldObj.getBlockState(pos);

        if (iblockstate.getRenderType() != EnumBlockRenderType.INVISIBLE) {
            int i = pos.getX();
            int j = pos.getY();
            int k = pos.getZ();
            float f = 0.1F;
            AxisAlignedBB axisalignedbb = iblockstate.getBoundingBox(worldObj, pos);
            double d0 = (double) i + rand.nextDouble() * (axisalignedbb.maxX - axisalignedbb.minX - 0.20000000298023224D) + 0.10000000149011612D + axisalignedbb.minX;
            double d1 = (double) j + rand.nextDouble() * (axisalignedbb.maxY - axisalignedbb.minY - 0.20000000298023224D) + 0.10000000149011612D + axisalignedbb.minY;
            double d2 = (double) k + rand.nextDouble() * (axisalignedbb.maxZ - axisalignedbb.minZ - 0.20000000298023224D) + 0.10000000149011612D + axisalignedbb.minZ;

            if (side == EnumFacing.DOWN) {
                d1 = (double) j + axisalignedbb.minY - 0.10000000149011612D;
            }

            if (side == EnumFacing.UP) {
                d1 = (double) j + axisalignedbb.maxY + 0.10000000149011612D;
            }

            if (side == EnumFacing.NORTH) {
                d2 = (double) k + axisalignedbb.minZ - 0.10000000149011612D;
            }

            if (side == EnumFacing.SOUTH) {
                d2 = (double) k + axisalignedbb.maxZ + 0.10000000149011612D;
            }

            if (side == EnumFacing.WEST) {
                d0 = (double) i + axisalignedbb.minX - 0.10000000149011612D;
            }

            if (side == EnumFacing.EAST) {
                d0 = (double) i + axisalignedbb.maxX + 0.10000000149011612D;
            }


            TextureAtlasSprite sprite = getParticleSprite(worldObj, pos, side);
            if (sprite != null) {
                manager.addEffect((new DigIconParticle(worldObj, d0, d1, d2, 0.0D, 0.0D, 0.0D, sprite)).multiplyVelocity(0.2F).multipleParticleScaleBy(0.6F));
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getParticleSprite(World worldObj, BlockPos aPos, EnumFacing side) {
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