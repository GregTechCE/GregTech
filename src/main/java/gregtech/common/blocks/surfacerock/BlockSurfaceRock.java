package gregtech.common.blocks.surfacerock;

import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.render.StoneRenderer;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public abstract class BlockSurfaceRock extends Block {

    private static final AxisAlignedBB STONE_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 8.0 / 16.0, 1.0);

    public BlockSurfaceRock() {
        super(net.minecraft.block.material.Material.ROCK);
        setHardness(1.5f);
        setSoundType(SoundType.STONE);
        setTranslationKey("surface_rock");
        setLightOpacity(1);
    }

    public abstract Material getStoneMaterial(IBlockAccess blockAccess, BlockPos pos, IBlockState blockState);

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return STONE_AABB;
    }

    private ItemStack getDropStack(IBlockAccess blockAccess, BlockPos pos, IBlockState blockState, int amount) {
        Material material = getStoneMaterial(blockAccess, pos, blockState);
        return OreDictUnifier.get(OrePrefix.dustTiny, material, amount);
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return getDropStack(world, pos, state, 1);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        Random rand = new Random();
        int amount = 3 + rand.nextInt((int) (2 + fortune * 1.5));
        drops.add(getDropStack(world, pos, state, amount));
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return StoneRenderer.BLOCK_RENDER_TYPE;
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (fromPos.up().equals(pos)) {
            if (worldIn.getBlockState(fromPos).getBlockFaceShape(worldIn, fromPos, EnumFacing.UP) != BlockFaceShape.SOLID) {
                worldIn.destroyBlock(pos, true);
            }
        }
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }
}
