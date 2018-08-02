package gregtech.api.pipelike;

import codechicken.lib.raytracer.RayTracer;
import codechicken.lib.render.particle.CustomParticleHandler;
import codechicken.lib.vec.Cuboid6;
import gregtech.api.render.PipeLikeRenderer;
import gregtech.api.unification.material.type.Material;
import gregtech.api.worldentries.pipenet.WorldPipeNet;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

@SuppressWarnings("deprecation")
public class BlockPipeLike<Q extends Enum<Q> & IBaseProperty & IStringSerializable, P extends IPipeLikeTileProperty, C> extends Block implements ITileEntityProvider {

    public final PipeFactory<Q, P, C> factory;
    public final Material material;
    private P[] actualProperties;

    protected BlockPipeLike(PipeFactory<Q, P, C> factory, net.minecraft.block.material.Material mcMaterial, Material material, P[] actualProperties) {
        super(mcMaterial);
        this.factory = factory;
        this.material = material;
        this.actualProperties = actualProperties;
        initBlockState();
    }

    public PropertyEnum<Q> getBaseProperty() {
        return factory.baseProperty;
    }

    @Override
    public Block setSoundType(SoundType sound) {
        return super.setSoundType(sound);
    }

    ///////////////////////// COLLISION AND BOUNDING BOXES /////////////////////////////////

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
        for(Cuboid6 axisAlignedBB : factory.getCollisionBox(worldIn, pos, state)) {
            AxisAlignedBB offsetBox = axisAlignedBB.aabb().offset(pos);
            if (offsetBox.intersects(entityBox)) collidingBoxes.add(offsetBox);
        }
    }

    @Nullable
    @Override
    public RayTraceResult collisionRayTrace(IBlockState blockState, World worldIn, BlockPos pos, Vec3d start, Vec3d end) {
        return RayTracer.rayTraceCuboidsClosest(start, end, pos, factory.getCollisionBox(worldIn, pos, blockState));
    }

    ////////////////////////////// BLOCK METHODS /////////////////////////////////////

    public ItemStack getItem(Q baseProperty) {
        return new ItemStack(this, 1, baseProperty.ordinal());
    }

    @Nullable
    public Q getBaseProperties(ItemStack stack) {
        int index = stack.getItemDamage();
        if (index >= 0 && index < factory.baseProperties.length) {
            return factory.baseProperties[index];
        }
        return null;
    }

    public P getActualProperty(Q baseProperty) {
        return actualProperties[baseProperty.ordinal()];
    }

    @Override
    protected final BlockStateContainer createBlockState() {
        return new BlockStateContainer(this);
    }

    private void initBlockState() {
        blockState = new BlockStateContainer(this, factory.baseProperty);
        setDefaultState(blockState.getBaseState());
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(factory.baseProperty, factory.baseProperties[meta]);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(factory.baseProperty).ordinal();
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return getItem(state.getValue(factory.baseProperty));
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return getDefaultState().withProperty(factory.baseProperty, factory.baseProperties[meta]);
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        for(Q baseProperty : factory.baseProperties) {
            if (!baseProperty.getOrePrefix().isIgnored(material)) {
                items.add(getItem(baseProperty));
            }
        }
    }

    @Override
    public boolean recolorBlock(World world, BlockPos pos, EnumFacing side, @Nullable EnumDyeColor color) {
        ITilePipeLike<Q, P> tile = factory.getTile(world, pos);
        if(tile != null && world.getBlockState(pos).getValue(factory.baseProperty).isColorable()) {
            if (color == null) {
                if (tile.getColor() != factory.getDefaultColor()) {
                    tile.setColor(factory.getDefaultColor());
                    return true;
                }
            } else if (tile.getColor() != color.colorValue) {
                tile.setColor(color.colorValue);
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        TileEntity tile = worldIn.getTileEntity(pos);
        if (tile instanceof TileEntityPipeLike && stack.hasTagCompound()) {
            ((TileEntityPipeLike) tile).initFromItemStackData(stack.getTagCompound());
        }
    }

    private ThreadLocal<TileEntity> bufferedTile = new ThreadLocal<>();

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        bufferedTile.set(te);
        super.harvestBlock(worldIn, player, pos, state, te, stack);
        bufferedTile.set(null);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        TileEntity tile = bufferedTile.get() == null ? world.getTileEntity(pos) : bufferedTile.get();
        ItemStack stack = getItem(state.getValue(factory.baseProperty));
        if (tile instanceof TileEntityPipeLike) {
            NBTTagCompound tagCompound = new NBTTagCompound();
            ((TileEntityPipeLike) tile).writeItemStackData(tagCompound);
            if (!tagCompound.hasNoTags()) {
                stack.setTagCompound(tagCompound);
            }
        }
        drops.add(stack);
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        factory.onBreakingTile(factory.getTile(worldIn, pos));
        super.breakBlock(worldIn, pos, state);
        factory.removeFromPipeNet(worldIn, pos);
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        super.onBlockAdded(worldIn, pos, state);
        if (!worldIn.isRemote) {
            WorldPipeNet.addScheduledCheck(factory, worldIn, pos);
        }
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        TileEntity tile = worldIn.getTileEntity(pos);
        if (tile instanceof TileEntityPipeLike) {
            ((TileEntityPipeLike) tile).updateRenderMask();
            ((TileEntityPipeLike) tile).updateNode();
        }
    }

    @Override
    public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileEntityPipeLike) {
            ((TileEntityPipeLike) tile).updateRenderMask();
            ((TileEntityPipeLike) tile).updateNode();
        }
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        ITilePipeLike<Q, P> tile = factory.getTile(worldIn, pos);
        if (tile != null) factory.onEntityCollidedWithBlock(entityIn, tile);
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return PipeLikeRenderer.getRenderer(factory).getRenderType();
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return factory.createNewTileEntity();
    }

    ///////////////////////////////////////// PARTICLE HANDLING ////////////////////////////////////////////////////

    @Override
    public boolean addLandingEffects(IBlockState state, WorldServer worldObj, BlockPos blockPosition, IBlockState iblockstate, EntityLivingBase entity, int numberOfParticles) {
        return CustomParticleHandler.handleLandingEffects(worldObj, blockPosition, entity, numberOfParticles);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean addHitEffects(IBlockState state, World worldObj, RayTraceResult target, ParticleManager manager) {
        return CustomParticleHandler.handleHitEffects(state, worldObj, target, manager);
    }

    @Override
    public boolean addDestroyEffects(World world, BlockPos pos, ParticleManager manager) {
        return PipeLikeRenderer.handleDestroyEffects(world, world.getBlockState(pos), pos, manager);
    }

    @Override
    public boolean addRunningEffects(IBlockState state, World world, BlockPos pos, Entity entity) {
        return true;
    }
}
