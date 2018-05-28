package gregtech.common.blocks.wood;

import gregtech.api.GregTechAPI;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.blocks.wood.BlockLogGT.LogVariant;
import net.minecraft.block.*;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.*;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

import java.util.Random;

public class BlockSaplingGT extends BlockBush implements IGrowable, IPlantable {

    public static final PropertyEnum<LogVariant> VARIANT = PropertyEnum.create("variant", LogVariant.class);
    public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 1);
    protected static final AxisAlignedBB SAPLING_AABB = new AxisAlignedBB(0.1, 0.0D, 0.1, 0.9, 0.8, 0.9);

    public BlockSaplingGT() {
        this.setDefaultState(this.blockState.getBaseState()
            .withProperty(VARIANT, LogVariant.RUBBER_WOOD)
            .withProperty(STAGE, 0));
        setUnlocalizedName("gt.sapling");
        this.setCreativeTab(GregTechAPI.TAB_GREGTECH);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, VARIANT, STAGE);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState()
            .withProperty(VARIANT, LogVariant.values()[meta % 4])
            .withProperty(STAGE, meta / 4);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(STAGE) * 4 + state.getValue(VARIANT).ordinal();
    }

    @Override
    public int damageDropped(IBlockState state) {
        return state.getValue(VARIANT).ordinal();
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        for (LogVariant logVariant : LogVariant.values()) {
            items.add(new ItemStack(this, 1, logVariant.ordinal()));
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return SAPLING_AABB;
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (!worldIn.isRemote) {
            super.updateTick(worldIn, pos, state, rand);
            if (!worldIn.isAreaLoaded(pos, 1))
                return;
            if (worldIn.getLightFromNeighbors(pos.up()) >= 9 && rand.nextInt(7) == 0) {
                this.grow(worldIn, rand, pos, state);
            }
        }
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        return (double) worldIn.rand.nextFloat() < 0.45D;
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        return EnumPlantType.Plains;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        if (state.getValue(STAGE) == 0) {
            worldIn.setBlockState(pos, state.cycleProperty(STAGE), 4);
        } else {
            this.generateTree(worldIn, pos, state, rand);
        }
    }

    public void generateTree(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        WorldGenerator worldgenerator = new WorldGenTrees(true, 6,
            MetaBlocks.LOG.getDefaultState().withProperty(BlockLogGT.VARIANT, LogVariant.RUBBER_WOOD),
            MetaBlocks.LEAVES.getDefaultState().withProperty(BlockLogGT.VARIANT, LogVariant.RUBBER_WOOD),
            false);
        worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 4);
        if (!worldgenerator.generate(worldIn, rand, pos)) {
            worldIn.setBlockState(pos, state, 4);
        }
    }

}
