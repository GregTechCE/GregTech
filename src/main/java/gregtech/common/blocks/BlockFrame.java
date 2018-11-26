package gregtech.common.blocks;

import gregtech.api.GregTechAPI;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.GemMaterial;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.material.type.IngotMaterial;
import gregtech.api.unification.material.type.SolidMaterial;
import gregtech.common.blocks.properties.PropertyMaterial;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving.SpawnPlacementType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public final class BlockFrame extends DelayedStateBlock {

    private static final AxisAlignedBB COLLISION_BOX = new AxisAlignedBB(0.05, 0.0, 0.05, 0.95, 1.0, 0.95);
    public final PropertyMaterial variantProperty;

    public BlockFrame(Material[] materials) {
        super(net.minecraft.block.material.Material.IRON);
        setUnlocalizedName("frame");
        setHardness(2.0f);
        setResistance(10.0f);
        setCreativeTab(GregTechAPI.TAB_GREGTECH_MATERIALS);
        this.variantProperty = PropertyMaterial.create("variant", materials);
        initBlockState();
    }

    @Override
    public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, SpawnPlacementType type) {
        return false;
    }


    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stackInHand = playerIn.getHeldItem(hand);
        if(stackInHand.isEmpty() || !(stackInHand.getItem() instanceof FrameItemBlock))
            return false;
        IBlockState blockState = ((FrameItemBlock) stackInHand.getItem()).getBlockState(stackInHand);
        if(blockState != worldIn.getBlockState(pos))
            return false;
        MutableBlockPos blockPos = new MutableBlockPos(pos);
        for(int i = 0; i < 32; i++) {
            IBlockState stateHere = worldIn.getBlockState(blockPos);
            if(stateHere.getBlock().isAir(stateHere, worldIn, pos)) {
                worldIn.setBlockState(blockPos, blockState);
                if(!playerIn.capabilities.isCreativeMode)
                    stackInHand.shrink(1);
                return true;
            } else if(stateHere != state) {
                return false;
            }
            blockPos.move(EnumFacing.UP);
        }
        return false;
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        IBlockState downState = worldIn.getBlockState(pos.down());
        return super.canPlaceBlockAt(worldIn, pos) && (downState.isSideSolid(worldIn, pos, EnumFacing.UP) ||
            downState.getBlockFaceShape(worldIn, pos.down(), EnumFacing.UP) == BlockFaceShape.SOLID);
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        IBlockState downState = worldIn.getBlockState(pos.down());
        if (!downState.isSideSolid(worldIn, pos, EnumFacing.UP) &&
            downState.getBlockFaceShape(worldIn, pos.down(), EnumFacing.UP) != BlockFaceShape.SOLID) {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
        }
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        entityIn.motionX = MathHelper.clamp(entityIn.motionX, -0.15, 0.15);
        entityIn.motionZ = MathHelper.clamp(entityIn.motionZ, -0.15, 0.15);
        entityIn.fallDistance = 0.0F;
        if (entityIn.motionY < -0.15D) {
            entityIn.motionY = -0.15D;
        }
        if (entityIn.isSneaking() && entityIn.motionY < 0.0D) {
            entityIn.motionY = 0.0D;
        }
        if(entityIn.collidedHorizontally) {
            entityIn.motionY = 0.2;
        }
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return COLLISION_BOX;
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }

    @Override
    public String getHarvestTool(IBlockState state) {
        Material material = state.getValue(variantProperty);
        if(material instanceof SolidMaterial) {
            return material.toString().contains("wood") ? "axe" : "pickaxe";
        }
        return "pickaxe";
    }

    @Override
    public int getHarvestLevel(IBlockState state) {
        Material material = state.getValue(variantProperty);
        if(material instanceof SolidMaterial) {
            return ((SolidMaterial) material).harvestLevel;
        }
        return 0;
    }

    @Override
    @SuppressWarnings("deprecation")
    public IBlockState getStateFromMeta(int meta) {
        Material material = variantProperty.getAllowedValues().get(meta);
        return getDefaultState().withProperty(variantProperty, material);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        Material material = state.getValue(variantProperty);
        return variantProperty.getAllowedValues().indexOf(material);
    }

    @Override
    protected BlockStateContainer createStateContainer() {
        return new BlockStateContainer(this, variantProperty);
    }

    public ItemStack getItem(Material material) {
        return getItem(getDefaultState().withProperty(variantProperty, material));
    }

    public ItemStack getItem(IBlockState blockState) {
        return new ItemStack(this, 1, getMetaFromState(blockState));
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
    	blockState.getValidStates().stream()
    		.filter(blockState -> blockState.getValue(variantProperty) != Materials._NULL)
    		.forEach(blockState -> list.add(getItem(blockState)));
    }

    @Override
    @SuppressWarnings("deprecation")
    public net.minecraft.block.material.Material getMaterial(IBlockState state) {
        Material material = state.getValue(variantProperty);
        if(material instanceof GemMaterial) {
            return net.minecraft.block.material.Material.ROCK;
        } else if(material instanceof IngotMaterial) {
            return net.minecraft.block.material.Material.IRON;
        } else if(material.toString().contains("wood")) {
            return net.minecraft.block.material.Material.WOOD;
        }
        return net.minecraft.block.material.Material.ROCK;
    }

    @Override
    @SuppressWarnings("deprecation")
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return getMaterial(state).getMaterialMapColor();
    }

    @Override
    public SoundType getSoundType(IBlockState state, World world, BlockPos pos, @Nullable Entity entity) {
        Material material = state.getValue(variantProperty);
        if(material instanceof GemMaterial) {
            return SoundType.STONE;
        } else if(material instanceof IngotMaterial) {
            return SoundType.METAL;
        } else if(material.toString().contains("wood")) {
            return SoundType.WOOD;
        }
        return SoundType.STONE;
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    public boolean isTopSolid(IBlockState state) {
        return true;
    }
}
