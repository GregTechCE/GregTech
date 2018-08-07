package gregtech.api.pipelike;

import codechicken.lib.raytracer.IndexedCuboid6;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Rotation;
import codechicken.lib.vec.Vector3;
import codechicken.multipart.TMultiPart;
import codechicken.multipart.TileMultipart;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import gregtech.api.GTValues;
import gregtech.api.block.machines.BlockMachine;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.multipart.MultipartPipeLike;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.type.GemMaterial;
import gregtech.api.unification.material.type.IngotMaterial;
import gregtech.api.unification.material.type.Material;
import gregtech.api.worldentries.pipenet.PipeNet;
import gregtech.api.worldentries.pipenet.WorldPipeNet;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional.Method;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;


public abstract class PipeFactory<Q extends Enum<Q> & IBaseProperty & IStringSerializable, P extends IPipeLikeTileProperty, C> {

    ///////////////////////////////////// REGISTRIES ///////////////////////////////////////

    public static final Map<String, PipeFactory<?, ?, ?>> allFactories = Maps.newHashMap();
    private final Map<Material, BlockPipeLike<Q, P, C>> blockMap = Maps.newHashMap();

    private boolean freezePropertyRegistry = false;
    private final Map<Material, P> REGISTERED_PROPERTIES = Maps.newLinkedHashMap();

    @SuppressWarnings("unchecked")
    public static <Q extends Enum<Q> & IBaseProperty & IStringSerializable, P extends IPipeLikeTileProperty, C> PipeFactory<Q, P, C> getFactoryByName(String name) {
        return (PipeFactory<Q, P, C>) allFactories.get(name);
    }

    protected void registerPropertyForMaterial(Material material, P property) {
        if (freezePropertyRegistry) throw new IllegalStateException("Property registry of " + name +" is already freezed!");
        REGISTERED_PROPERTIES.put(material, property);
    }

    public void registerOreDict() {
        blockMap.values().forEach(block -> {
            for (Q baseProperty : baseProperties) {
                if (!baseProperty.getOrePrefix().isIgnored(block.material)) {
                    ItemStack itemStack = block.getItem(baseProperty);
                    OreDictUnifier.registerOre(itemStack, baseProperty.getOrePrefix(), block.material);
                }
            }
        });
    }

    ////////////////////////////// BASIC FIELDS AND INITS //////////////////////////////////

    public final Class<Q> classBaseProperty;
    public final Class<P> classTileProperty;

    public final String name;

    public final PropertyEnum<Q> baseProperty;
    protected final Q[] baseProperties;
    public final ResourceLocation multipartType;

    public final Capability<C> capability;

    protected PipeFactory(String name, Capability<C> capability, Class<Q> classBaseProperty, Class<P> classTileProperty) {
        if (allFactories.containsKey(name)) throw new IllegalArgumentException(String.format("GT pipe-like multipart \"%s\" already existed!", name));
        this.name = name;
        this.multipartType = new ResourceLocation(GTValues.MODID, name);
        this.capability = capability;
        this.classBaseProperty = classBaseProperty;
        this.classTileProperty = classTileProperty;

        this.baseProperties = classBaseProperty.getEnumConstants();
        this.baseProperty = PropertyEnum.create(name + "_property", classBaseProperty);
        allFactories.put(name, this);
    }

    ///////////////////////////////// BLOCKS AND TILES /////////////////////////////////////

    public boolean isRegistered(Material material) {
        return REGISTERED_PROPERTIES.containsKey(material);
    }

    @Nullable
    public P getRegisteredProperty(Material material) {
        return REGISTERED_PROPERTIES.get(material);
    }

    public Map<Material, BlockPipeLike<Q, P, C>> getBlockMap() {
        return blockMap;
    }

    protected net.minecraft.block.material.Material getBlockMaterial(Material material) {
        if (material instanceof GemMaterial) {
            return net.minecraft.block.material.Material.ROCK;
        } else if (material instanceof IngotMaterial) {
            return net.minecraft.block.material.Material.IRON;
        } else if (material.toString().contains("wood")) {
            return net.minecraft.block.material.Material.WOOD;
        }
        return net.minecraft.block.material.Material.ROCK;
    }

    protected BlockPipeLike<Q, P, C> createBlock(Material material, P[] materialProperties) {
        return new BlockPipeLike<>(this, getBlockMaterial(material), material, materialProperties);
    }

    protected abstract void initBlock(BlockPipeLike<Q, P, C> block, Material material, P materialProperty);
    protected abstract P createActualProperty(Q baseProperty, P materialProperty);

    public Map<Material, BlockPipeLike<Q, P, C>> createBlockWithRegisteredProperties() {
        freezePropertyRegistry = true;
        REGISTERED_PROPERTIES.forEach(this::createBlock);
        return blockMap;
    }

    @SuppressWarnings("unchecked")
    public BlockPipeLike<Q, P, C> createBlock(Material material, P materialProperty) {
        P[] materialProperties = (P[]) Array.newInstance(classTileProperty, baseProperties.length);
        for (int i = 0; i < baseProperties.length; i++) {
            materialProperties[i] = createActualProperty(baseProperties[i], materialProperty);
        }
        BlockPipeLike<Q, P, C> block = createBlock(material, materialProperties);
        block.setRegistryName(name + "_" + material.toString());
        block.setUnlocalizedName(name);
        initBlock(block, material, materialProperty);
        blockMap.put(material, block);
        return block;
    }

    public TileEntityPipeLike<Q, P, C> createNewTileEntity() {
        return new TileEntityPipeLike<>(this);
    }


    @SuppressWarnings("unchecked")
    public ITilePipeLike<Q, P> getTile(IBlockAccess world, BlockPos pos) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile == null) return null;
        if (tile instanceof ITilePipeLike && ((ITilePipeLike) tile).getFactory() == this) {
            return (ITilePipeLike<Q, P>) tile;
        } else if (Loader.isModLoaded(GTValues.MODID_FMP)) {
            return getFromMultipart(tile);
        }
        return null;
    }

    public BlockPipeLike<Q, P, C> getBlock(Material material) {
        return blockMap.get(material);
    }

    public Q getBaseProperty(int index) {
        return index >= 0 && index < baseProperties.length ? baseProperties[index] : null;
    }

    public void onBreakingTile(ITilePipeLike<Q, P> tile) {}

    /////////////////////////////// MULTIPART METHODS //////////////////////////////////////

    public EnumActionResult tryPlaceMultipart(EntityPlayer player, World worldIn, BlockPos blockPos, ItemStack stack, EnumFacing facing, Vector3 hit, BlockPipeLike<Q, P, C> blockPipeLike) {
        return _tryPlaceMultipart(player, worldIn, blockPos, stack, facing, hit, blockPipeLike);
    }

    public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side, EntityPlayer player, ItemStack stack, BlockPipeLike blockPipeLike) {
        return _canPlaceBlockOnSide(worldIn, pos, side, player, stack, blockPipeLike);
    }

    /**
     * Resembling {@link codechicken.multipart.TItemMultiPart#onItemUse(EntityPlayer, World, BlockPos, EnumHand, EnumFacing, float, float, float)}
     */
    @Method(modid = GTValues.MODID_FMP)
    protected EnumActionResult _tryPlaceMultipart(EntityPlayer player, World worldIn, BlockPos blockPos, ItemStack stack, EnumFacing facing, Vector3 hit, BlockPipeLike<Q, P, C> blockPipeLike) {
        IBlockState state = blockPipeLike.getStateFromMeta(stack.getItemDamage());
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(blockPos);
        int side = facing.getIndex();
        double depth = hit.copy().scalarProject(Rotation.axes[side]) + (side % 2 ^ 1);

        Supplier<EnumActionResult> place = () -> {//TODO Covers
            TMultiPart part = (TMultiPart) createMultipart(null, state);
            if (!(worldIn.getTileEntity(pos) instanceof TileMultipart) || !TileMultipart.canPlacePart(worldIn, pos, part)) return EnumActionResult.FAIL;

            if (!worldIn.isRemote) {
                TileMultipart.addPart(worldIn, pos, part);
                SoundType sound = blockPipeLike.getSoundType(state, worldIn, pos, player);
                worldIn.playSound(null, blockPos, sound.getPlaceSound(), SoundCategory.BLOCKS, (sound.getVolume() + 1.0F) / 2.0F, sound.getPitch() * 0.8F);
                if (!player.capabilities.isCreativeMode) stack.shrink(1);
            }
            return EnumActionResult.SUCCESS;
        };

        if (depth < 1 && place.get() == EnumActionResult.SUCCESS) return EnumActionResult.SUCCESS;

        pos.move(facing);
        return place.get();
    }

    /**
     * Modified version of {@link net.minecraft.item.ItemBlock#canPlaceBlockOnSide(World, BlockPos, EnumFacing, EntityPlayer, ItemStack)}
     */
    @Method(modid = GTValues.MODID_FMP)
    protected boolean _canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side, EntityPlayer player, ItemStack stack, BlockPipeLike blockPipeLike) {
        Block block = worldIn.getBlockState(pos).getBlock();
        TMultiPart part = (TMultiPart) createMultipart(null, blockPipeLike.getStateFromMeta(stack.getItemDamage()));

        if (block == Blocks.SNOW_LAYER && block.isReplaceable(worldIn, pos)) {
            side = EnumFacing.UP;
        } else if (!block.isReplaceable(worldIn, pos) && !(worldIn.getTileEntity(pos) instanceof TileMultipart) || !TileMultipart.canPlacePart(worldIn, pos, part)) {
            pos = pos.offset(side);
        }

        IBlockState state = worldIn.getBlockState(pos);
        AxisAlignedBB axisalignedbb = blockPipeLike.getDefaultState().getCollisionBoundingBox(worldIn, pos);

        if (axisalignedbb != Block.NULL_AABB && !worldIn.checkNoEntityCollision(axisalignedbb.offset(pos), null)) {
            return false;
        } else {
            return (state.getBlock().isReplaceable(worldIn, pos) && blockPipeLike.canPlaceBlockOnSide(worldIn, pos, side)) || worldIn.getTileEntity(pos) instanceof TileMultipart;
        }
    }

    @SuppressWarnings("unchecked")
    private ITilePipeLike<Q, P> getFromMultipart(TileEntity tile) {
        if (tile instanceof TileMultipart) {
            return (ITilePipeLike<Q, P>) ((TileMultipart) tile).jPartList().stream()
                .filter(part -> part instanceof ITilePipeLike && ((ITilePipeLike) part).getFactory() == this)
                .findFirst().orElse(null);
        }
        return null;
    }

    public ResourceLocation getMultipartType() {
        return multipartType;
    }

    /**
     * @return Must extends {@link TMultiPart}
     */
    @Method(modid = GTValues.MODID_FMP)
    public ITilePipeLike<Q, P> createMultipart() {
        return new MultipartPipeLike<>(this);
    }

    /**
     * @return Must extends {@link TMultiPart}
     */
    @Method(modid = GTValues.MODID_FMP)
    public ITilePipeLike<Q, P> createMultipart(TileEntityPipeLike<Q, P, C> tileEntity, IBlockState state) {
        return new MultipartPipeLike<>(this, tileEntity, state);
    }

    ///////////////////////// COLLISION AND BOUNDING BOXES /////////////////////////////////

    public static int MASK_FORMAL_CONNECTION = 1;
    public static int MASK_RENDER_SIDE = 1 << 6;

    public static Cuboid6 getSideBox(EnumFacing side, float thickness) {//TODO Covers
        float min = (1.0f - thickness) / 2.0f;
        float max = min + thickness;
        if(side == null) {
            return new Cuboid6(min, min, min, max, max, max);
        }
        switch (side) {
            case DOWN:  return new Cuboid6(min, 0.0f, min, max, min, max);
            case UP:    return new Cuboid6(min, max, min, max, 1.0f, max);
            case WEST:  return new Cuboid6(0.0f, min, min, min, max, max);
            case EAST:  return new Cuboid6(max, min, min, 1.0f, max, max);
            case NORTH: return new Cuboid6(min, min, 0.0f, max, max, min);
            case SOUTH: return new Cuboid6(min, min, max, max, max, 1.0f);
            default:    throw new IllegalArgumentException(side.toString());
        }
    }

    public List<IndexedCuboid6> getCollisionBox(IBlockAccess world, BlockPos pos, IBlockState state) {//TODO Covers
        ITilePipeLike<Q, P> tile = getTile(world, pos);
        List<IndexedCuboid6> result = Lists.newArrayList();
        float thickness = state.getValue(baseProperty).getThickness();
        result.add(new IndexedCuboid6(0, getSideBox(null, thickness)));
        if (tile != null) {
            int formalConnections = tile.getRenderMask();
            for(EnumFacing facing : EnumFacing.VALUES) {
                if((formalConnections & MASK_FORMAL_CONNECTION << facing.getIndex()) != 0)
                    result.add(new IndexedCuboid6(0, getSideBox(facing, thickness)));
            }
        }
        return result;
    }

    public void onEntityCollidedWithBlock(Entity entity, ITilePipeLike<Q, P> tile) {
        float thickness = tile.getBaseProperty().getThickness();
        List<Cuboid6> boxes = Lists.newArrayList();
        boxes.add(getSideBox(null, thickness));
        for (EnumFacing facing : EnumFacing.VALUES) {
            boxes.add(getSideBox(facing, thickness));
        }
        if (boxes.stream()
            .map(cuboid6 -> cuboid6.aabb().offset(tile.getTilePos()))
            .anyMatch(entity.getEntityBoundingBox()::intersects)) {
            onEntityCollided(entity, tile);
        }
    }

    protected abstract void onEntityCollided(Entity entity, ITilePipeLike<Q, P> tile);

    ///////////////////////////////// CONNECTIONS //////////////////////////////////////////

    public abstract int getDefaultColor();

    /**
     * @return 0: not pipe
     *          1: find pipe but blocked
     *          2: accessible, self thickness <= tile thickness
     *          3: accessible, self thickness >  tile thickness
     */
    protected int isPipeAccessibleAtSide(IBlockAccess world, BlockPos pos, EnumFacing fromFacing, int fromColor, float selfThickness) {
        ITilePipeLike<Q, P> tile = getTile(world, pos);
        if (tile == null) return 0;
        if ((tile.getInternalConnections() & (ITilePipeLike.MASK_BLOCKED << fromFacing.getIndex())) != 0) return 1;
        if (fromColor != getDefaultColor() && tile.getColor() != getDefaultColor() && fromColor != tile.getColor()) return 1;
        return selfThickness <= tile.getBaseProperty().getThickness() ? 2 : 3;
    }

    /**
     * @return bitmask for render
     *          =  (render side, 6 bits)    << 6
     *           | (formal connection, 6 bits)
     */
    public int getRenderMask(ITilePipeLike<Q, ?> tile, IBlockAccess world, BlockPos pos) {
        int blockedConnection = tile.getInternalConnections();
        int connectedSideMask = blockedConnection >> 12 & 0b111111;
        BlockPos.MutableBlockPos sidePos = new BlockPos.MutableBlockPos(pos);
        for (EnumFacing facing : EnumFacing.VALUES) {
            if ((blockedConnection & ITilePipeLike.MASK_BLOCKED << facing.getIndex()) != 0)
                continue;
            sidePos.move(facing);
            switch (isPipeAccessibleAtSide(world, sidePos, facing.getOpposite(), tile.getColor(), tile.getBaseProperty().getThickness())) {
                case 0: if (!tile.hasCapabilityAtSide(capability, facing)) break;
                case 3: connectedSideMask |= MASK_RENDER_SIDE << facing.getIndex();
                case 2: connectedSideMask |= MASK_FORMAL_CONNECTION << facing.getIndex(); break;
            }
            sidePos.move(facing.getOpposite());
        }
        return connectedSideMask;
    }

    /**
     * @return bitmask for actual connections AMONG PIPE-LIKE TILES
     *          =  (input disabled, 6 bits)    << 6
     *           | (output disabled, 6 bits)
     */
    public int getConnectionMask(ITilePipeLike<Q, P> tile, World world, BlockPos pos) {
        int blockedConnection = tile.getInternalConnections();
        int connectedSideMask = blockedConnection & 0b111111_111111;
        BlockPos.MutableBlockPos sidePos = new BlockPos.MutableBlockPos(pos);
        for (EnumFacing facing : EnumFacing.VALUES) {
            sidePos.move(facing);
            if ((blockedConnection & ITilePipeLike.MASK_BLOCKED << facing.getIndex()) != 0
                || isPipeAccessibleAtSide(world, sidePos, facing.getOpposite(), tile.getColor(), tile.getBaseProperty().getThickness()) < 2) {
                connectedSideMask |= (ITilePipeLike.MASK_INPUT_DISABLED | ITilePipeLike.MASK_OUTPUT_DISABLED) << facing.getIndex();
            }
            sidePos.move(facing.getOpposite());
        }
        return connectedSideMask;
    }

    //////////////////////////////////// NETWORK ////////////////////////////////////////////

    public abstract C createCapability(ITilePipeLike<Q, P> tile);

    public abstract PipeNet<Q, P, C> createPipeNet(WorldPipeNet worldNet);

    public C onGettingNetworkCapability(C capability, EnumFacing facing) {
        return capability;
    }

    @Nullable
    public ICapabilityProvider getCapabilityProviderAtSide(@Nonnull EnumFacing facing, ITilePipeLike<?, ?> tile) {
        World world = tile.getTileWorld();
        int color = tile.getColor();
        BlockPos.PooledMutableBlockPos pos = BlockPos.PooledMutableBlockPos.retain().setPos(tile.getTilePos()).move(facing);
        ICapabilityProvider result = world == null ? null : world.getTileEntity(pos);
        if (result != null && color != getDefaultColor()) {
            MetaTileEntity mte = BlockMachine.getMetaTileEntity(world, pos);
            if (mte != null && mte.getPaintingColor() != MetaTileEntity.DEFAULT_PAINTING_COLOR
                && mte.getPaintingColor() != color) {
                result = null;
            }
        }
        pos.release();
        return result;
    }

    public PipeNet<Q, P, C> addToPipeNet(World world, BlockPos pos, ITilePipeLike<Q, P> tile) {
        return WorldPipeNet.getWorldPipeNet(world).addNodeToAdjacentOrNewNet(pos, tile, this);
    }

    public void removeFromPipeNet(World world, BlockPos pos) {
        WorldPipeNet.getWorldPipeNet(world).removeNodeFromNet(pos, this);
    }

    public void updateNode(World world, BlockPos pos, ITilePipeLike<Q, P> tile) {
        PipeNet<Q, P, C> net = WorldPipeNet.getWorldPipeNet(world).getPipeNetFromPos(pos, this);
        if (net != null) net.updateNode(pos, tile);
    }

    /**
     * Override this method for casting
     */
    public PipeNet<Q, P, C> getPipeNetAt(ITilePipeLike<Q, P> tile) {
        return WorldPipeNet.getWorldPipeNet(tile.getTileWorld()).getPipeNetFromPos(tile.getTilePos(), this);
    }

    public abstract P createEmptyProperty();

    public int getActiveSideMask(ITilePipeLike<Q, P> tile) {
        int result = 0;
        int connectionMask = tile.getInternalConnections();
        BlockPos.MutableBlockPos sidePos = new BlockPos.MutableBlockPos(tile.getTilePos());
        for (EnumFacing facing : EnumFacing.VALUES) if ((connectionMask & ITilePipeLike.MASK_BLOCKED << facing.getIndex()) == 0) {
            sidePos.move(facing);
            if (getTile(tile.getTileWorld(), sidePos) == null // ignore other pipes
                && tile.hasCapabilityAtSide(capability, facing)) {
                result |= 1 << facing.getIndex();
            }
            sidePos.move(facing.getOpposite());
        }
        return result;
    }
}