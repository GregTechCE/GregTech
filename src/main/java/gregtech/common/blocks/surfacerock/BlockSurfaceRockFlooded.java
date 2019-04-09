package gregtech.common.blocks.surfacerock;

import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.type.IngotMaterial;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.blocks.properties.PropertyMaterial;
import gregtech.common.render.StoneRenderer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
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

import java.util.EnumSet;
import java.util.Random;
import java.util.Set;

public class BlockSurfaceRockFlooded extends BlockLiquid {

    public final PropertyMaterial materialProperty;

    public BlockSurfaceRockFlooded(Material[] allowedValues) {
        super(net.minecraft.block.material.Material.WATER);
        this.materialProperty = PropertyMaterial.create("material", allowedValues);
        setHardness(1.0f);
        setResistance(0.3f);
        setSoundType(SoundType.STONE);
        setUnlocalizedName("surface_rock");
        initBlockState();
    }

    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (!worldIn.isAreaLoaded(pos, 4))
            return; // Forge: avoid loading unloaded chunks
        Set<EnumFacing> flowDirections = this.getPossibleFlowDirections(worldIn, pos);
        for (EnumFacing direction : flowDirections) {
            tryFlowInto(worldIn, pos.offset(direction), worldIn.getBlockState(pos.offset(direction)), 8);
        }
    }

    private Set<EnumFacing> getPossibleFlowDirections(World worldIn, BlockPos pos) {
        int minSlopeDistance = 1000;
        Set<EnumFacing> resultSet = EnumSet.noneOf(EnumFacing.class);
        for (EnumFacing direction : EnumFacing.Plane.HORIZONTAL) {
            BlockPos offsetPos = pos.offset(direction);
            IBlockState blockState = worldIn.getBlockState(offsetPos);
            if (!isBlockBlockedForFlow(blockState) && (blockState.getMaterial() != blockMaterial || blockState.getValue(LEVEL) > 0)) {
                int slopeDistance;
                if (isBlockBlockedForFlow(worldIn.getBlockState(offsetPos.down()))) {
                    slopeDistance = getSlopeDistance(worldIn, offsetPos, 1, direction.getOpposite());
                } else {
                    slopeDistance = 0;
                }
                if (slopeDistance < minSlopeDistance) {
                    resultSet.clear();
                }
                if (slopeDistance <= minSlopeDistance) {
                    resultSet.add(direction);
                    minSlopeDistance = slopeDistance;
                }
            }
        }

        return resultSet;
    }

    private int getSlopeDistance(World worldIn, BlockPos pos, int distance, EnumFacing calculateFlowCost) {
        int slopeDistance = 1000;
        for (EnumFacing direction : EnumFacing.Plane.HORIZONTAL) {
            if (direction != calculateFlowCost) {
                BlockPos offsetPos = pos.offset(direction);
                IBlockState blockState = worldIn.getBlockState(offsetPos);
                if (!isBlockBlockedForFlow(blockState) &&
                    (blockState.getMaterial() != this.blockMaterial ||
                        blockState.getValue(LEVEL) > 0)) {
                    if (!isBlockBlockedForFlow(worldIn.getBlockState(offsetPos.down()))) {
                        return distance;
                    }
                    if (distance < 4) {
                        int thisSlopDistance = this.getSlopeDistance(worldIn, offsetPos, distance + 1, direction.getOpposite());
                        if (thisSlopDistance < slopeDistance) {
                            slopeDistance = thisSlopDistance;
                        }
                    }
                }
            }
        }
        return slopeDistance;
    }

    private void tryFlowInto(World worldIn, BlockPos pos, IBlockState state, int level) {
        if (this.canFlowInto(state)) {
            if (state.getMaterial() != net.minecraft.block.material.Material.AIR) {
                if (state.getBlock() != Blocks.SNOW_LAYER) {
                    state.getBlock().dropBlockAsItem(worldIn, pos, state, 0);
                }
            }
            worldIn.setBlockState(pos, Blocks.FLOWING_WATER.getDefaultState().withProperty(LEVEL, level), 3);
        }
    }

    private boolean canFlowInto(IBlockState state) {
        net.minecraft.block.material.Material material = state.getMaterial();
        return material != this.blockMaterial && material != net.minecraft.block.material.Material.LAVA && !isBlockBlockedForFlow(state);
    }

    private boolean isBlockBlockedForFlow(IBlockState state) {
        Block block = state.getBlock();
        net.minecraft.block.material.Material mat = state.getMaterial();
        if (!(block instanceof BlockDoor) && block != Blocks.STANDING_SIGN && block != Blocks.LADDER && block != Blocks.REEDS) {
            return mat == net.minecraft.block.material.Material.PORTAL ||
                mat == net.minecraft.block.material.Material.STRUCTURE_VOID || mat.blocksMovement();
        } else {
            return true;
        }
    }

    protected void initBlockState() {
        BlockStateContainer stateContainer = createBlockState();
        this.blockState = stateContainer;
        this.setDefaultState(stateContainer.getBaseState().withProperty(LEVEL, 0));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        if (materialProperty == null)
            return new BlockStateContainer(this, LEVEL);
        return new BlockStateContainer(this, materialProperty, LEVEL);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(materialProperty, materialProperty.getAllowedValues().get(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return materialProperty.getAllowedValues().indexOf(state.getValue(materialProperty));
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
        return BlockSurfaceRock.getShapeFromBlockPos(pos).aabb().offset(pos);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return BlockSurfaceRock.getShapeFromBlockPos(pos).aabb();
    }

    @Override
    public boolean canCollideCheck(IBlockState state, boolean hitIfLiquid) {
        return true;
    }

    private ItemStack getDropStack(IBlockState state, int amount) {
        Material material = state.getValue(materialProperty);
        if (material instanceof IngotMaterial && ((IngotMaterial) material).blastFurnaceTemperature == 0)
            return OreDictUnifier.get(OrePrefix.nugget, material, amount);
        return OreDictUnifier.get(OrePrefix.dustTiny, material, amount);
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return getDropStack(state, 1);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        Random rand = world instanceof World ? ((World) world).rand : RANDOM;
        int amount = 1 + rand.nextInt(fortune == 0 ? 1 : fortune);
        drops.add(getDropStack(state, amount));
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        super.onBlockAdded(worldIn, pos, state);
        worldIn.scheduleUpdate(pos, state.getBlock(), 5);
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
        if (fromPos.up().equals(pos)) {
            if (worldIn.getBlockState(fromPos).getBlockFaceShape(worldIn, fromPos, EnumFacing.UP) != BlockFaceShape.SOLID) {
                //remove current block and replace it with water block
                worldIn.destroyBlock(pos, true);
                worldIn.setBlockState(pos, Blocks.WATER.getDefaultState().withProperty(LEVEL, state.getValue(LEVEL)));
                return;
            }
        }
        worldIn.scheduleUpdate(pos, state.getBlock(), 5);
    }

    @Override
    public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
        return false;
    }

    @Override
    public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
        worldIn.setBlockState(pos, Blocks.WATER.getDefaultState().withProperty(LEVEL, state.getValue(LEVEL)));
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return StoneRenderer.BLOCK_RENDER_TYPE;
    }

    @Override
    public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
        return super.canRenderInLayer(state, layer) || layer == BlockRenderLayer.SOLID;
    }

}
