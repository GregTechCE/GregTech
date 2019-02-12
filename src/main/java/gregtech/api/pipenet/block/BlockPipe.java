package gregtech.api.pipenet.block;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.raytracer.IndexedCuboid6;
import codechicken.lib.raytracer.RayTracer;
import codechicken.lib.vec.Cuboid6;
import codechicken.multipart.TileMultipart;
import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.block.BuiltInRenderBlock;
import gregtech.api.cover.CoverBehavior;
import gregtech.api.cover.ICoverable;
import gregtech.api.pipenet.PipeNet;
import gregtech.api.pipenet.WorldPipeNet;
import gregtech.api.pipenet.tile.IPipeTile;
import gregtech.api.pipenet.tile.TileEntityPipeBase;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.util.GTUtility;
import gregtech.common.tools.DamageValues;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SuppressWarnings("deprecation")
public abstract class BlockPipe<PipeType extends Enum<PipeType> & IPipeType<NodeDataType>, NodeDataType, WorldPipeNetType extends WorldPipeNet<NodeDataType, ? extends PipeNet<NodeDataType>>> extends BuiltInRenderBlock implements ITileEntityProvider {

    public BlockPipe() {
        super(net.minecraft.block.material.Material.IRON);
        setUnlocalizedName("pipe");
        setCreativeTab(GregTechAPI.TAB_GREGTECH);
        setSoundType(SoundType.METAL);
        setHardness(2.0f);
        setResistance(3.0f);
        setLightOpacity(1);
        disableStats();
    }

    public abstract Class<PipeType> getPipeTypeClass();

    public abstract WorldPipeNetType getWorldPipeNet(World world);

    public abstract int getActiveNodeConnections(IBlockAccess world, BlockPos nodePos);

    public abstract TileEntityPipeBase<PipeType, NodeDataType> createNewTileEntity(boolean supportsTicking);

    public NodeDataType createProperties(IPipeTile<PipeType, NodeDataType> pipeTile) {
        return createProperties(pipeTile.getPipeType(), pipeTile.getPipeMaterial());
    }

    public NodeDataType createItemProperties(ItemStack itemStack) {
        return createProperties(getItemPipeType(itemStack), getItemMaterial(itemStack));
    }

    protected abstract NodeDataType createProperties(PipeType pipeType, Material material);

    public ItemStack getDropItem(IPipeTile<PipeType, NodeDataType> pipeTile) {
        return getItem(pipeTile.getPipeType(), pipeTile.getPipeMaterial());
    }

    public ItemStack getItem(PipeType pipeType, Material material) {
        int materialId = Material.MATERIAL_REGISTRY.getIDForObject(material);
        return new ItemStack(this, 1, pipeType.ordinal() * 1000 + materialId);
    }

    public PipeType getItemPipeType(ItemStack itemStack) {
        return getPipeTypeClass().getEnumConstants()[itemStack.getMetadata() / 1000];
    }

    public Material getItemMaterial(ItemStack itemStack) {
        return Material.MATERIAL_REGISTRY.getObjectById(itemStack.getMetadata() % 1000);
    }

    public void setTileEntityData(IPipeTile<PipeType, NodeDataType> pipeTile, ItemStack itemStack) {
        pipeTile.setPipeType(getItemPipeType(itemStack));
        pipeTile.setPipeMaterial(getItemMaterial(itemStack));
    }

    @Override
    public abstract void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items);

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        IPipeTile<PipeType, NodeDataType> pipeTile = getPipeTileEntity(worldIn, pos);
        if(pipeTile != null && !((TileEntityPipeBase) pipeTile).isBeingConverted()) {
            pipeTile.getCoverableImplementation().dropAllCovers();
        }
        super.breakBlock(worldIn, pos, state);
        getWorldPipeNet(worldIn).removeNode(pos);
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        worldIn.scheduleUpdate(pos, this, 1);
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        boolean isActiveNode = getActiveNodeConnections(worldIn, pos) > 0;
        IPipeTile<PipeType, NodeDataType> pipeTile = getPipeTileEntity(worldIn, pos);
        getWorldPipeNet(worldIn).addNode(pos, createProperties(pipeTile), 0, 0, isActiveNode);
        onActiveModeChange(worldIn, pos, isActiveNode, true);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        IPipeTile<PipeType, NodeDataType> pipeTile = getPipeTileEntity(worldIn, pos);
        if(pipeTile != null) {
            setTileEntityData(pipeTile, stack);
        }
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        boolean isActiveNodeNow = getActiveNodeConnections(worldIn, pos) > 0;
        PipeNet<NodeDataType> pipeNet = getWorldPipeNet(worldIn).getNetFromPos(pos);
        if(pipeNet != null) {
            boolean modeChanged = pipeNet.markNodeAsActive(pos, isActiveNodeNow);
            if(modeChanged) {
                onActiveModeChange(worldIn, pos, isActiveNodeNow, false);
            }
        }
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return createNewTileEntity(false);
    }

    /**
     * Can be used to update tile entity to tickable when node becomes active
     * usable for fluid pipes, as example
     */
    protected void onActiveModeChange(World world, BlockPos pos, boolean isActiveNow, boolean isInitialChange) {
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        if(target instanceof CuboidRayTraceResult) {
            CuboidRayTraceResult result = (CuboidRayTraceResult) target;
            IPipeTile<PipeType, NodeDataType> pipeTile = getPipeTileEntity(world, pos);
            if(result.cuboid6.data == null) {
                return getDropItem(pipeTile);
            } else if(result.cuboid6.data instanceof EnumFacing) {
                EnumFacing coverSide = (EnumFacing) result.cuboid6.data;
                CoverBehavior coverBehavior = pipeTile.getCoverableImplementation().getCoverAtSide(coverSide);
                return coverBehavior == null ? ItemStack.EMPTY : coverBehavior.getCoverDefinition().getDropItemStack();
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        IPipeTile<PipeType, NodeDataType> pipeTile = getPipeTileEntity(worldIn, pos);
        if (pipeTile == null) {
            return false;
        }
        CuboidRayTraceResult rayTraceResult = (CuboidRayTraceResult) RayTracer.retraceBlock(worldIn, playerIn, pos);
        EnumFacing coverSide = ICoverable.traceCoverSide(rayTraceResult);
        if(coverSide == null) {
            return false;
        }
        CoverBehavior coverBehavior = pipeTile.getCoverableImplementation().getCoverAtSide(coverSide);
        if (coverBehavior == null) {
            return false;
        }
        ItemStack itemInHand = playerIn.getHeldItem(hand);
        SimpleItemStack simpleItemStack = itemInHand.isEmpty() ? null : new SimpleItemStack(itemInHand);
        if (simpleItemStack != null && GregTechAPI.screwdriverList.contains(simpleItemStack)) {
            if (GTUtility.doDamageItem(itemInHand, DamageValues.DAMAGE_FOR_SCREWDRIVER, true) &&
                coverBehavior.onScrewdriverClick(playerIn, hand, rayTraceResult) == EnumActionResult.SUCCESS) {
                GTUtility.doDamageItem(itemInHand, DamageValues.DAMAGE_FOR_SCREWDRIVER, false);
                return true;
            } else {
                return false;
            }
        }
        return coverBehavior.onRightClick(playerIn, hand, rayTraceResult) == EnumActionResult.SUCCESS;
    }

    @Override
    public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
        IPipeTile<PipeType, NodeDataType> pipeTile = getPipeTileEntity(worldIn, pos);
        if (pipeTile == null) {
            return;
        }
        CuboidRayTraceResult rayTraceResult = (CuboidRayTraceResult) RayTracer.retraceBlock(worldIn, playerIn, pos);
        EnumFacing coverSide = rayTraceResult == null ? null : ICoverable.traceCoverSide(rayTraceResult);
        if(coverSide == null) {
            return;
        }
        CoverBehavior coverBehavior = pipeTile.getCoverableImplementation().getCoverAtSide(coverSide);
        if (coverBehavior == null) {
            return;
        }
        coverBehavior.onLeftClick(playerIn, rayTraceResult);
    }

    protected ThreadLocal<IPipeTile<PipeType, NodeDataType>> tileEntities = new ThreadLocal<>();

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        tileEntities.set(te == null ? null : (IPipeTile<PipeType, NodeDataType>) te);
        super.harvestBlock(worldIn, player, pos, state, te, stack);
        tileEntities.set(null);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        IPipeTile<PipeType, NodeDataType> pipeTile = tileEntities.get() == null ? getPipeTileEntity(world, pos) : tileEntities.get();
        if(pipeTile == null) return;
        drops.add(getDropItem(pipeTile));
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
        for(Cuboid6 axisAlignedBB : getCollisionBox(worldIn, pos, state)) {
            AxisAlignedBB offsetBox = axisAlignedBB.aabb().offset(pos);
            if (offsetBox.intersects(entityBox)) collidingBoxes.add(offsetBox);
        }
    }

    @Nullable
    @Override
    public RayTraceResult collisionRayTrace(IBlockState blockState, World worldIn, BlockPos pos, Vec3d start, Vec3d end) {
        return RayTracer.rayTraceCuboidsClosest(start, end, pos, getCollisionBox(worldIn, pos, blockState));
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public boolean recolorBlock(World world, BlockPos pos, EnumFacing side, EnumDyeColor color) {
        IPipeTile<PipeType, NodeDataType> tileEntityPipe = (IPipeTile<PipeType, NodeDataType>) world.getTileEntity(pos);
        if(tileEntityPipe != null && tileEntityPipe.getPipeType().isPaintable() &&
            tileEntityPipe.getInsulationColor() != color.colorValue) {
            tileEntityPipe.setInsulationColor(color.colorValue);
            return true;
        }
        return false;
    }

    private boolean isThisPipeBlock(Block block) {
        return block.getClass().isAssignableFrom(getClass());
    }

    /**
     * Just returns proper pipe tile entity
     */
    public IPipeTile<PipeType, NodeDataType> getPipeTileEntity(IBlockAccess world, BlockPos selfPos) {
        TileEntity tileEntityAtPos = world.getTileEntity(selfPos);
        return getPipeTileEntity(tileEntityAtPos);
    }

    public IPipeTile<PipeType, NodeDataType> getPipeTileEntity(TileEntity tileEntityAtPos) {
        if(tileEntityAtPos instanceof IPipeTile && isThisPipeBlock(((IPipeTile) tileEntityAtPos).getPipeBlock())) {
            //noinspection unchecked
            return (IPipeTile<PipeType, NodeDataType>) tileEntityAtPos;
        } else if(Loader.isModLoaded(GTValues.MODID_FMP)) {
            return tryGetMultipartTile(tileEntityAtPos);
        }
        return null;
    }

    private IPipeTile<PipeType, NodeDataType> tryGetMultipartTile(TileEntity tileEntityAtPos) {
        if(tileEntityAtPos instanceof TileMultipart) {
            TileMultipart tileMultipart = (TileMultipart) tileEntityAtPos;
            //noinspection unchecked
            return (IPipeTile<PipeType, NodeDataType>) tileMultipart.jPartList().stream()
                .filter(part -> part instanceof IPipeTile)
                .filter(part -> isThisPipeBlock(((IPipeTile) part).getPipeBlock()))
                .findFirst().orElse(null);
        }
        return null;
    }


    /**
     * Tests whatever pipe at given position can connect to a pipe from fromFacing face with fromColor color
     * Used purely for rendering purposes
     * @return 0 - not a pipe; 1 - pipe but blocked; 2,3 - accessible
     */
    protected final int isPipeAccessibleAtSide(IBlockAccess world, IPipeTile<PipeType, NodeDataType> selfTile, EnumFacing side) {
        IPipeTile<PipeType, NodeDataType> tileEntityPipe = getPipeTileEntity(world, selfTile.getPipePos().offset(side));
        if(tileEntityPipe == null) {
            return 0; //not a cable pipe entity
        }
        if((tileEntityPipe.getBlockedConnections() & 1 << side.getOpposite().getIndex()) > 0 ||
            (selfTile.getBlockedConnections() & 1 << side.getIndex()) > 0) {
            return 1; //connection is blocked on this facing
        }

        int insulationColor = selfTile.getInsulationColor();
        if(insulationColor != IPipeTile.DEFAULT_INSULATION_COLOR &&
            tileEntityPipe.getInsulationColor() != IPipeTile.DEFAULT_INSULATION_COLOR &&
            insulationColor != tileEntityPipe.getInsulationColor()) {
            return 1; //color doesn't match; unable to connect
        }

        if(!canPipesConnect(selfTile, side, tileEntityPipe)) {
            return 1; //custom connection predicate didn't match
        }

        float thickness = tileEntityPipe.getPipeType().getThickness();
        return selfTile.getPipeType().getThickness() > thickness ? 3 : 2;
    }

    protected boolean canPipesConnect(IPipeTile<PipeType, NodeDataType> selfTile, EnumFacing side, IPipeTile<PipeType, NodeDataType> sideTile) {
        return true;
    }

    /**
     * Returns bit mask of actual cable connections, including cable-cable and cable-receiver
     * connections. but excluding unaccessible covers on blocked sides
     */
    public int getActualConnections(IPipeTile<PipeType, NodeDataType> selfTile, IBlockAccess world) {
        int connectedSidesMask = 0;
        int activeNodeConnections = getActiveNodeConnections(world, selfTile.getPipePos());
        for(EnumFacing side : EnumFacing.VALUES) {
            if(selfTile.getCoverableImplementation().getCoverAtSide(side) != null) {
                connectedSidesMask |= 1 << side.getIndex();
            }
            if((selfTile.getBlockedConnections() & (1 << side.getIndex())) > 0)
                continue; //do not check blocked connection sides
            int cableState = isPipeAccessibleAtSide(world, selfTile, side);
            if(cableState >= 2) {
                connectedSidesMask |= 1 << side.getIndex();
                if(cableState >= 3) {
                    connectedSidesMask |= 1 << (6 + side.getIndex());
                }
            } else if(cableState == 0 && (activeNodeConnections & 1 << side.getIndex()) > 0) {
                connectedSidesMask |= 1 << side.getIndex();
            }
        }
        return connectedSidesMask;
    }

    private List<IndexedCuboid6> getCollisionBox(IBlockAccess world, BlockPos pos, IBlockState state) {
        IPipeTile<PipeType, NodeDataType> pipeTile = getPipeTileEntity(world, pos);
        int actualConnections = getActualConnections(pipeTile, world);
        float thickness = pipeTile.getPipeType().getThickness();
        ArrayList<IndexedCuboid6> result = new ArrayList<>();
        result.add(new IndexedCuboid6(null, getSideBox(null, thickness)));
        for(EnumFacing side : EnumFacing.VALUES) {
            if((actualConnections & 1 << side.getIndex()) > 0) {
                result.add(new IndexedCuboid6(null, getSideBox(side, thickness)));
            }
        }
        pipeTile.getCoverableImplementation().addCoverCollisionBoundingBox(result, false);
        return result;
    }

    public static Cuboid6 getSideBox(EnumFacing side, float thickness) {
        float min = (1.0f - thickness) / 2.0f;
        float max = min + thickness;
        if(side == null) {
            return new Cuboid6(min, min, min, max, max, max);
        } else if(side == EnumFacing.DOWN) {
            return new Cuboid6(min, 0.0f, min, max, min, max);
        } else if(side == EnumFacing.UP) {
            return new Cuboid6(min, max, min, max, 1.0f, max);
        } else if(side == EnumFacing.WEST) {
            return new Cuboid6(0.0f, min, min, min, max, max);
        } else if(side == EnumFacing.EAST) {
            return new Cuboid6(max, min, min, 1.0f, max, max);
        } else if(side == EnumFacing.NORTH) {
            return new Cuboid6(min, min, 0.0f, max, max, min);
        } else if(side == EnumFacing.SOUTH) {
            return new Cuboid6(min, min, max, max, max, 1.0f);
        } else throw new IllegalArgumentException(side.toString());
    }

}
