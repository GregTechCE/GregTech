package gregtech.common.cable;

import codechicken.lib.raytracer.IndexedCuboid6;
import codechicken.lib.raytracer.RayTracer;
import codechicken.lib.render.particle.CustomParticleHandler;
import codechicken.lib.vec.Cuboid6;
import codechicken.multipart.TileMultipart;
import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.unification.material.type.Material;
import gregtech.common.cable.net.EnergyNet;
import gregtech.common.cable.net.WorldENet;
import gregtech.common.cable.tile.TileEntityCable;
import gregtech.common.multipart.CableMultiPart;
import gregtech.common.render.CableRenderer;
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
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class BlockCable extends Block implements ITileEntityProvider {

    public static final PropertyEnum<Insulation> INSULATION = PropertyEnum.create("insulation", Insulation.class);

    public final WireProperties baseProps;
    public final Material material;
    private WireProperties[] insulatedPropsCache;

    public BlockCable(Material material, WireProperties cableProperties) {
        super(net.minecraft.block.material.Material.IRON);
        this.material = material;
        this.baseProps = cableProperties;
        setUnlocalizedName("cable");
        setCreativeTab(GregTechAPI.TAB_GREGTECH);
        setSoundType(SoundType.METAL);
        setHarvestLevel("cutter", 1);
        setHardness(2.0f);
        setResistance(3.0f);
        setLightOpacity(1);
    }

    ///////////////////////// COLLISION AND BOUNDING BOXES /////////////////////////////////

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

    private List<IndexedCuboid6> getCollisionBox(IBlockAccess world, BlockPos pos, IBlockState state) {
        TileEntityCable tileEntityCable = (TileEntityCable) getCableTileEntity(world, pos);
        int actualConnections = getActualConnections(tileEntityCable, world, pos);
        float thickness = state.getValue(INSULATION).thickness;
        ArrayList<IndexedCuboid6> result = new ArrayList<>();
        result.add(new IndexedCuboid6(0, getSideBox(null, thickness)));
        for(EnumFacing facing : EnumFacing.VALUES) {
            if((actualConnections & 1 << facing.getIndex()) > 0)
                result.add(new IndexedCuboid6(0, getSideBox(facing, thickness)));
        }
        return result;
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

    //////////////////////////////// E-NET STUFF ////////////////////////////////////////////

    /**
     * Just returns proper cable tile entity
     */
    public static ICableTile getCableTileEntity(IBlockAccess world, BlockPos selfPos) {
        TileEntity tileEntityAtPos = world.getTileEntity(selfPos);
        if(tileEntityAtPos instanceof TileEntityCable) {
            return ((TileEntityCable) tileEntityAtPos);
        } else if(Loader.isModLoaded(GTValues.MODID_FMP)) {
            return tryGetMultipartTile(tileEntityAtPos);
        }
        return null;
    }

    private static ICableTile tryGetMultipartTile(TileEntity tileEntityAtPos) {
        if(tileEntityAtPos instanceof TileMultipart) {
            TileMultipart tileMultipart = (TileMultipart) tileEntityAtPos;
            return (ICableTile) tileMultipart.jPartList().stream()
                .filter(part -> part instanceof CableMultiPart)
                .findFirst().orElse(null);
        }
        return null;
    }


    /**
     * Tests whatever cable at given position can connect to cable from fromFacing face with fromColor color
     * @return 0 - not a cable; 1 - cable but blocked; 2,3 - accessible
     */
    private static int isCableAccessibleAtSide(IBlockAccess world, BlockPos pos, EnumFacing fromFacing, int fromColor, float selfThickness) {
        ICableTile tileEntityCable = getCableTileEntity(world, pos);
        if(tileEntityCable == null)
            return 0;
        if((tileEntityCable.getBlockedConnections() & (1 << fromFacing.getIndex())) > 0)
            return 1;

        if(fromColor != TileEntityCable.DEFAULT_INSULATION_COLOR &&
            tileEntityCable.getInsulationColor() != TileEntityCable.DEFAULT_INSULATION_COLOR &&
            fromColor != tileEntityCable.getInsulationColor())
            return 1;
        float thickness = tileEntityCable.getInsulation().thickness;
        return selfThickness > thickness ? 3 : 2;
    }

    /**
     * Updates cable e-net by removing it from current one and re-calling
     * attachToNetwork(). This is useful when you change blocked connections,
     * wire color or cover placement, which causes network to recompute paths
     */
    public static void updateCableConnections(ICableTile cableTile, World world, BlockPos blockPos) {
        WorldENet worldENet = WorldENet.getWorldENet(world);
        EnergyNet energyNet = worldENet.getNetFromPos(blockPos);
        if(energyNet != null) {
            //if should update node, remove it from current network and attach again
            energyNet.removeNode(blockPos);
            attachNoNearbyNetwork(world, blockPos, cableTile);
        }
    }

    /**
     * Returns bit mask of actual cable connections, including cable-cable and cable-receiver
     * connections. but excluding unaccessible covers on blocked sides
     */
    public static int getActualConnections(ICableTile selfTile, IBlockAccess world, BlockPos blockPos) {
        int connectedSidesMask = 0;
        for(EnumFacing enumFacing : EnumFacing.VALUES) {
            if((selfTile.getBlockedConnections() & (1 << enumFacing.getIndex())) > 0)
                continue; //do not check blocked connection sides
            BlockPos offsetPos = blockPos.offset(enumFacing);
            IBlockState blockState = world.getBlockState(offsetPos);
            int cableState = isCableAccessibleAtSide(world, offsetPos, enumFacing.getOpposite(), selfTile.getInsulationColor(),
                selfTile.getInsulation().thickness);
            if(cableState >= 2) {
                connectedSidesMask |= 1 << enumFacing.getIndex();
                if(cableState >= 3) {
                    connectedSidesMask |= 1 << (6 + enumFacing.getIndex());
                }
            } else if(cableState == 0 && blockState.getBlock().hasTileEntity(blockState)) {
                TileEntity tileEntity = world.getTileEntity(offsetPos);
                if(tileEntity != null && tileEntity.hasCapability(IEnergyContainer.CAPABILITY_ENERGY_CONTAINER,
                    enumFacing.getOpposite()))
                    connectedSidesMask |= 1 << enumFacing.getIndex();
            }
        }
        return connectedSidesMask;
    }

    public static void attachNoNearbyNetwork(World worldIn, BlockPos pos, ICableTile cableTile) {
        boolean hasCapability = hasEnergyCapabilities(worldIn, pos);
        WorldENet worldENet = WorldENet.getWorldENet(worldIn);
        EnergyNet energyNet = null;

        for(EnumFacing facing : EnumFacing.VALUES) {
            if((cableTile.getBlockedConnections() & (1 << facing.getIndex())) > 0)
                continue; //do not search blocked sides
            BlockPos offsetPos = pos.offset(facing);
            int cableState = isCableAccessibleAtSide(worldIn, offsetPos, facing.getOpposite(),
                cableTile.getInsulationColor(), cableTile.getInsulation().thickness);
            if(cableState >= 2) {
                EnergyNet offsetEnergyNet = worldENet.getNetFromPos(offsetPos);
                if(offsetEnergyNet == null) {
                    continue;
                }
                if(energyNet == null) {
                    energyNet = offsetEnergyNet;
                    energyNet.addNode(pos, cableTile.getWireProperties(), cableTile.getBlockedConnections());
                } else if(energyNet != offsetEnergyNet) {
                    //if there is another e-net here, unite with it
                    energyNet.uniteNetworks(offsetEnergyNet);
                }
            }
        }

        if(energyNet == null) {
            energyNet = new EnergyNet(worldENet);
            energyNet.addNode(pos, cableTile.getWireProperties(), cableTile.getBlockedConnections());
            worldENet.addEnergyNet(energyNet);
            worldENet.markDirty();
        }

        if(hasCapability) {
            energyNet.markNodeAsActive(pos);
        }
    }


    public static void detachFromNetwork(World worldIn, BlockPos pos) {
        WorldENet worldENet = WorldENet.getWorldENet(worldIn);
        EnergyNet energyNet = worldENet.getNetFromPos(pos);
        if(energyNet != null) {
            energyNet.removeNode(pos);
        }
    }

    private static boolean hasEnergyCapabilities(World worldIn, BlockPos pos) {
        for(EnumFacing facing : EnumFacing.VALUES) {
            BlockPos offsetPos = pos.offset(facing);
            TileEntity tileEntity = worldIn.getTileEntity(offsetPos);
            //do not connect to null cables and ignore cables
            if(tileEntity == null || getCableTileEntity(worldIn, offsetPos) != null) continue;
            EnumFacing opposite = facing.getOpposite();
            IEnergyContainer energyContainer = tileEntity.getCapability(IEnergyContainer.CAPABILITY_ENERGY_CONTAINER, opposite);
            if(energyContainer != null)
                return true;
        }
        return false;
    }

    ////////////////////////////// BLOCK METHODS /////////////////////////////////////

    private void initPropsCache() {
        Insulation[] insulationArray = Insulation.values();
        this.insulatedPropsCache = new WireProperties[insulationArray.length];
        for(int i = 0; i < insulationArray.length; i++) {
            Insulation insulation = insulationArray[i];
            int totalAmperage = baseProps.amperage * insulation.amperage;
            int minInsulationLoss = insulation.insulationLevel == -1 ? 1 : 0;
            int totalLossPerBlock = Math.max(baseProps.lossPerBlock, minInsulationLoss) * insulation.lossMultiplier;
            this.insulatedPropsCache[i] = new WireProperties(baseProps.voltage, totalAmperage, totalLossPerBlock);
        }
    }

    public ItemStack getItem(Insulation insulation) {
        return new ItemStack(this, 1, insulation.ordinal());
    }

    public static Insulation getInsulation(ItemStack itemStack) {
        return Insulation.values()[itemStack.getMetadata()];
    }

    public WireProperties getProperties(Insulation insulation) {
        if(insulatedPropsCache == null) {
            initPropsCache();
        }
        return insulatedPropsCache[insulation.ordinal()];
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, INSULATION);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(INSULATION, Insulation.values()[meta]);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(INSULATION).ordinal();
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return getItem(state.getValue(INSULATION));
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return getDefaultState().withProperty(INSULATION, Insulation.values()[meta]);
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        for(Insulation insulation : Insulation.values()) {
            items.add(getItem(insulation));
        }
    }

    @Override
    public boolean recolorBlock(World world, BlockPos pos, EnumFacing side, EnumDyeColor color) {
        TileEntityCable tileEntityCable = (TileEntityCable) world.getTileEntity(pos);
        if(tileEntityCable != null && world.getBlockState(pos).getValue(INSULATION).insulationLevel != -1 &&
            tileEntityCable.getInsulationColor() != color.colorValue) {
            tileEntityCable.setInsulationColor(color.colorValue);
            return true;
        }
        return false;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        super.breakBlock(worldIn, pos, state);
        detachFromNetwork(worldIn, pos);
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        Insulation insulation = state.getValue(INSULATION);
        attachNoNearbyNetwork(worldIn, pos, new SimpleCableTile(insulation, getProperties(insulation)));
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        boolean hasCapability = hasEnergyCapabilities(worldIn, pos);
        EnergyNet energyNet = WorldENet.getWorldENet(worldIn).getNetFromPos(pos);
        if(energyNet != null) {
            if(hasCapability) {
                energyNet.markNodeAsActive(pos);
            } else {
                energyNet.markNodeAsInactive(pos);
            }
        }
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
        return CableRenderer.BLOCK_RENDER_TYPE;
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        drops.add(getItem(state.getValue(INSULATION)));
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityCable();
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
        return CustomParticleHandler.handleDestroyEffects(world, pos, manager);
    }

    @Override
    public boolean addRunningEffects(IBlockState state, World world, BlockPos pos, Entity entity) {
        return true;
    }

}
