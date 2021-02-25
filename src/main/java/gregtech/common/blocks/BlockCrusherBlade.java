package gregtech.common.blocks;

import codechicken.lib.vec.Cuboid6;
import gregtech.api.GregTechAPI;
import gregtech.api.damagesources.DamageSources;
import gregtech.common.blocks.tileentity.TileEntityCrusherBlade;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving.SpawnPlacementType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockCrusherBlade extends Block implements ITileEntityProvider {

    public static final Cuboid6[] basicModel = new Cuboid6[]{
        new Cuboid6(6 / 16.0, 0.0, 6 / 16.0, 10 / 16.0, 1.0, 10 / 16.0), //rod
        new Cuboid6(2.0 / 16.0, 6 / 16.0, 2.0 / 16.0, 14.0 / 16.0, 10 / 16.0, 14.0 / 16.0),
        new Cuboid6(2.0 / 16.0, 0 / 16.0, 2.0 / 16.0, 14.0 / 16.0, 2 / 16.0, 14.0 / 16.0),
        new Cuboid6(2.0 / 16.0, 14 / 16.0, 2.0 / 16.0, 14.0 / 16.0, 16 / 16.0, 14.0 / 16.0)
    };

    public static final PropertyEnum<Axis> AXIS = PropertyEnum.create("axis", Axis.class);
    public static final PropertyBool ACTIVE = PropertyBool.create("active");

    public BlockCrusherBlade() {
        super(Material.IRON);
        setTranslationKey("gt.crusher_blade");
        setCreativeTab(GregTechAPI.TAB_GREGTECH);
        setHarvestLevel("pickaxe", 2);
        setHardness(3.0f);
        setResistance(5.0f);
        setLightOpacity(0);
        setDefaultState(getDefaultState()
            .withProperty(AXIS, Axis.Y)
            .withProperty(ACTIVE, false));
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, SpawnPlacementType type) {
        return false;
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return getDefaultState().withProperty(AXIS, facing.getAxis());
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState()
            .withProperty(AXIS, Axis.values()[meta % 8 % 3])
            .withProperty(ACTIVE, meta / 8 > 0);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return blockState.getValue(ACTIVE) ? null : FULL_BLOCK_AABB;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return (state.getValue(ACTIVE) ? 8 : 0) + state.getValue(AXIS).ordinal();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, AXIS, ACTIVE);
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityCrusherBlade();
    }

    @Override
    public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        if (state.getValue(ACTIVE)) {
            entityIn.attackEntityFrom(DamageSources.getCrusherDamage(), 5.0f);
            entityIn.motionY *= 0.04;
        }
    }
}
