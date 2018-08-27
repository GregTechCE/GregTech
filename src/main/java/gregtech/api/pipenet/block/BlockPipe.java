package gregtech.api.pipenet.block;

import codechicken.lib.raytracer.IndexedCuboid6;
import codechicken.lib.raytracer.RayTracer;
import codechicken.lib.vec.Cuboid6;
import codechicken.multipart.TileMultipart;
import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.block.BuiltInRenderBlock;
import gregtech.api.pipenet.PipeNet;
import gregtech.api.pipenet.WorldPipeNet;
import gregtech.api.pipenet.tile.IPipeTile;
import gregtech.api.pipenet.tile.TileEntityPipeBase;
import gregtech.api.unification.material.type.Material;
import gregtech.common.pipelike.cable.tile.TileEntityCable;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
public abstract class BlockPipe<PipeType extends Enum<PipeType> & IPipeType<NodeDataType>, NodeDataType, WorldPipeNetType extends WorldPipeNet<NodeDataType, ? extends PipeNet<NodeDataType>>> extends BuiltInRenderBlock implements ITileEntityProvider {

    public PropertyEnum<PipeType> pipeVariantProperty;

    public final NodeDataType baseProperties;
    public final Material material;
    private final List<NodeDataType> typePropertiesCache = new ArrayList<>();

    public BlockPipe(Material material, NodeDataType baseProperties) {
        super(net.minecraft.block.material.Material.IRON);
        this.material = material;
        this.baseProperties = baseProperties;
        setUnlocalizedName("pipe");
        setCreativeTab(GregTechAPI.TAB_GREGTECH);
        setSoundType(SoundType.METAL);
        setHardness(2.0f);
        setResistance(3.0f);
        setLightOpacity(1);
    }

    public abstract Class<PipeType> getPipeTypeClass();

    public abstract WorldPipeNetType getWorldPipeNet(World world);

    public abstract int getActiveNodeConnections(IBlockAccess world, BlockPos nodePos);

    @Override
    public abstract TileEntity createNewTileEntity(World worldIn, int meta);

    public NodeDataType getProperties(PipeType pipeType) {
        if(typePropertiesCache.isEmpty()) {
            for (PipeType pipeType1 : getPipeTypeClass().getEnumConstants()) {
                typePropertiesCache.add(pipeType1.modifyProperties(baseProperties));
            }
        }
        return typePropertiesCache.get(pipeType.ordinal());
    }

    public final ItemStack getItem(PipeType pipeType) {
        return new ItemStack(this, 1, pipeType.ordinal());
    }

    public final PipeType getPipeType(ItemStack itemStack) {
        return getPipeTypeClass().getEnumConstants()[itemStack.getMetadata()];
    }

    @Override
    protected BlockStateContainer createBlockState() {
        this.pipeVariantProperty = PropertyEnum.create("pipe_type", getPipeTypeClass());
        return new BlockStateContainer(this, pipeVariantProperty);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(pipeVariantProperty, getPipeTypeClass().getEnumConstants()[meta]);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(pipeVariantProperty).ordinal();
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        super.breakBlock(worldIn, pos, state);
        getWorldPipeNet(worldIn).removeNode(pos);
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        PipeType pipeType = state.getValue(pipeVariantProperty);
        boolean isActiveNode = getActiveNodeConnections(worldIn, pos) > 0;
        getWorldPipeNet(worldIn).addNode(pos, getProperties(pipeType), 0, 0, isActiveNode);
        onActiveModeChange(worldIn, pos, isActiveNode, true);
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

    /**
     * Can be used to update tile entity to tickable when node becomes active
     * usable for fluid pipes, as example
     */
    protected void onActiveModeChange(World world, BlockPos pos, boolean isActiveNow, boolean isInitialChange) {
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return getItem(state.getValue(pipeVariantProperty));
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return getDefaultState().withProperty(pipeVariantProperty, getPipeTypeClass().getEnumConstants()[meta]);
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        for(PipeType pipeType : getPipeTypeClass().getEnumConstants()) {
            items.add(getItem(pipeType));
        }
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        drops.add(getItem(state.getValue(pipeVariantProperty)));
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
        TileEntityCable tileEntityCable = (TileEntityCable) world.getTileEntity(pos);
        if(tileEntityCable != null && world.getBlockState(pos).getValue(pipeVariantProperty).isPaintable() &&
            tileEntityCable.getInsulationColor() != color.colorValue) {
            tileEntityCable.setInsulationColor(color.colorValue);
            return true;
        }
        return false;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote) {
            PipeNet<NodeDataType> pipeNet = getWorldPipeNet(worldIn).getNetFromPos(pos);
            playerIn.sendMessage(new TextComponentString("Pipe net " + pipeNet));
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
        float thickness = state.getValue(pipeVariantProperty).getThickness();
        ArrayList<IndexedCuboid6> result = new ArrayList<>();
        result.add(new IndexedCuboid6(0, getSideBox(null, thickness)));
        for(EnumFacing side : EnumFacing.VALUES) {
            if((actualConnections & 1 << side.getIndex()) > 0) {
                result.add(new IndexedCuboid6(0, getSideBox(side, thickness)));
            }
        }
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
