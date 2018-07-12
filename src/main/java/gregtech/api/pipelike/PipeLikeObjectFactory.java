package gregtech.api.pipelike;

import codechicken.lib.raytracer.IndexedCuboid6;
import codechicken.lib.vec.Cuboid6;
import codechicken.multipart.MultiPartRegistry;
import codechicken.multipart.TMultiPart;
import codechicken.multipart.TileMultipart;
import codechicken.multipart.api.IPartConverter;
import codechicken.multipart.api.IPartFactory;
import gregtech.api.GTValues;
import gregtech.api.multipart.MultipartPipeLike;
import gregtech.api.render.PipeLikeRenderer;
import gregtech.api.unification.material.type.GemMaterial;
import gregtech.api.unification.material.type.IngotMaterial;
import gregtech.api.unification.material.type.Material;
import gregtech.api.util.GTLog;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class PipeLikeObjectFactory<Q extends Enum<Q> & IBaseProperty & IStringSerializable, P extends ITileProperty, T extends ITilePipeLike<Q, P>, C> {

    ///////////////////////////////////// REGISTRIES ///////////////////////////////////////

    public static final Map<String, PipeLikeObjectFactory> allFactories = new HashMap<>();
    private static final Map<ResourceLocation, PipeLikeObjectFactory> multipartFactories = new HashMap<>();
    private final Map<Material, BlockPipeLike<Q, P, T, C>> blocks;

    ////////////////////////////// BASIC FIELDS AND INITS //////////////////////////////////

    public final Class<Q> classBaseProperty;
    public final Class<P> classTileProperty;
    public final Class<T> classTileInterface;

    public final String name;

    protected final PropertyEnum<Q> baseProperty;
    protected final Q[] baseProperties;
    public final ResourceLocation multipartType;

    public final Capability<C> capability;

    protected PipeLikeObjectFactory(String name, ResourceLocation multipartType, Map<Material, BlockPipeLike<Q, P, T, C>> blockMap, Capability<C> capability, Class<Q> classBaseProperty, Class<P> classTileProperty, Class<T> classTileInterface) {
        if (allFactories.containsKey(name)) throw new IllegalArgumentException(String.format("GT pipe-like multipart \"%s\" already existed!", name));
        this.name = name;
        this.blocks = blockMap;
        this.multipartType = multipartType;
        this.capability = capability;
        this.classBaseProperty = classBaseProperty;
        this.classTileProperty = classTileProperty;
        this.classTileInterface = classTileInterface;

        this.baseProperties = classBaseProperty.getEnumConstants();
        this.baseProperty = PropertyEnum.create(name + "Property", classBaseProperty);
        allFactories.put(name, this);
        multipartFactories.put(multipartType, this);
    }

    ///////////////////////////////// BLOCKS AND TILES /////////////////////////////////////

    protected net.minecraft.block.material.Material getBlockMaterial(Material material) {
        if (material instanceof GemMaterial) {
            return net.minecraft.block.material.Material.ROCK;
        } else if (material instanceof IngotMaterial) {
            if (material.toString().contains("rubber") || material.toString().contains("poly") || material.toString().contains("plastic"))
                return net.minecraft.block.material.Material.SPONGE;
            return net.minecraft.block.material.Material.IRON;
        } else if (material.toString().contains("wood")) {
            return net.minecraft.block.material.Material.WOOD;
        }
        return net.minecraft.block.material.Material.ROCK;
    }

    protected BlockPipeLike<Q, P, T, C> createBlock(Material material, P[] materialProperties) {
        return new BlockPipeLike<>(this, getBlockMaterial(material), material, materialProperties);
    }

    protected abstract void initBlock(BlockPipeLike<Q, P, T, C> block);
    protected abstract P createActualProperty(Q baseProperty, P materialProperty);

    @SuppressWarnings("unchecked")
    public BlockPipeLike<Q, P, T, C> createBlock(Material material, P materialProperty) {
        P[] materialProperties = (P[]) Array.newInstance(classTileProperty, baseProperties.length);
        for (int i = 0; i < baseProperties.length; i++) {
            materialProperties[i] = createActualProperty(baseProperties[i], materialProperty);
        }
        BlockPipeLike<Q, P, T, C> block = createBlock(material, materialProperties);
        block.setUnlocalizedName(name);
        initBlock(block);
        blocks.put(material, block);
        return block;
    }

    public TileEntityPipeLike<Q, P, T, C> createNewTileEntity() {
        return new TileEntityPipeLike<>(this);
    }

    @SuppressWarnings("unchecked")
    public T getTile(IBlockAccess world, BlockPos pos) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile == null) return null;
        if (classTileInterface.isInstance(tile)) {
            return (T) tile;
        } else if (Loader.isModLoaded(GTValues.MODID_FMP)) {
            return getFromMultipart(tile);
        }
        return null;
    }

    public BlockPipeLike<Q, P, T, C> getBlock(Material material) {
        return blocks.get(material);
    }

    public Q getBaseProperty(int index) {
        return index >= 0 && index < baseProperties.length ? baseProperties[index] : null;
    }

    /////////////////////////////// MULTIPART METHODS //////////////////////////////////////

    @Optional.Method(modid = GTValues.MODID_FMP)
    private static void registerMultipartFactory() {
        MultiPartRegistry.registerParts((IPartFactory) (identifier, client) -> {
            PipeLikeObjectFactory factory = multipartFactories.get(identifier);
            if (factory == null) return null;
            return factory.createMultipart();
        }, multipartFactories.keySet());
        MultiPartRegistry.registerConverter(new IPartConverter() {
            @Override
            public boolean canConvert(World world, BlockPos pos, IBlockState state) {
                return state.getBlock() instanceof BlockPipeLike;
            }

            @Override
            public Iterable<TMultiPart> convertToParts(World world, BlockPos pos, IBlockState state) {
                return IPartConverter.super.convertToParts(world, pos, state);//TODO Covers
            }

            @SuppressWarnings("unchecked")
            @Override
            public TMultiPart convert(World world, BlockPos pos, IBlockState state) {
                if (state.getBlock() instanceof BlockPipeLike) {
                    TileEntity tileEntity = world.getTileEntity(pos);
                    if (tileEntity instanceof TileEntityPipeLike) {
                        return ((TileEntityPipeLike) tileEntity).factory.createMultipart((TileEntityPipeLike) tileEntity, state);
                    }
                }
                return null;
            }
        });
    }

    @SuppressWarnings("unchecked")
    @Optional.Method(modid = GTValues.MODID_FMP)
    private T getFromMultipart(TileEntity tile) {
        if (tile instanceof TileMultipart) {
            return (T) ((TileMultipart) tile).jPartList().stream()
                .filter(classTileInterface::isInstance)
                .findFirst().orElse(null);
        }
        return null;
    }

    @Optional.Method(modid = GTValues.MODID_FMP)
    protected MultipartPipeLike<Q, P, T, C> createMultipart() {
        return new MultipartPipeLike<>(this);
    }

    @Optional.Method(modid = GTValues.MODID_FMP)
    protected MultipartPipeLike<Q, P, T, C> createMultipart(TileEntityPipeLike<Q, P, T, C> tileEntity, IBlockState state) {
        return new MultipartPipeLike<>(this, tileEntity, state);
    }

    ///////////////////////// COLLISION AND BOUNDING BOXES /////////////////////////////////

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
        T tile = getTile(world, pos);
        List<IndexedCuboid6> result = new ArrayList<>();
        float thickness = state.getValue(baseProperty).getThickness();
        result.add(new IndexedCuboid6(0, getSideBox(null, thickness)));
        if (tile != null) {
            int formalConnections = getRenderMask(tile, world, pos);
            for(EnumFacing facing : EnumFacing.VALUES) {
                if((formalConnections & PipeLikeRenderer.MASK_FORMAL_CONNECTION << facing.getIndex()) != 0)
                    result.add(new IndexedCuboid6(0, getSideBox(facing, thickness)));
            }
        }
        return result;
    }

    ///////////////////////////////// CONNECTIONS //////////////////////////////////////////

    public abstract int getDefaultColor();

    /**
     * @return 0: not tile
     *          1: find tile but blocked
     *          2: accessible, self thickness <= tile thickness
     *          3: accessible, self thickness >  tile thickness
     */
    protected int isTileAccessibleAtSide(IBlockAccess world, BlockPos pos, EnumFacing fromFacing, int fromColor, float selfThickness) {
        T tile = getTile(world, pos);
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
            switch (isTileAccessibleAtSide(world, sidePos, facing.getOpposite(), tile.getColor(), tile.getBaseProperty().getThickness())) {
                case 3: connectedSideMask |= PipeLikeRenderer.MASK_RENDER_SIDE << facing.getIndex();
                case 2: connectedSideMask |= PipeLikeRenderer.MASK_FORMAL_CONNECTION << facing.getIndex(); break;
                case 0: {
                    if (tile.hasCapabilityAtSide(capability, facing)) {
                        connectedSideMask |= PipeLikeRenderer.MASK_FORMAL_CONNECTION << facing.getIndex();
                    }
                    break;
                }
            }
            sidePos.move(facing.getOpposite());
        }
        return connectedSideMask;
    }

    /**
     * @return bitmask for actual connections between pipe-like tiles
     *          =  (input allowed, 6 bits)    << 6
     *           | (output allowed, 6 bits)
     */
    public int getActualConnections(T tile, World world, BlockPos pos) {
        int blockedConnection = tile.getInternalConnections();
        int connectedSideMask = blockedConnection & 0b111111_111111;
        BlockPos.MutableBlockPos sidePos = new BlockPos.MutableBlockPos(pos);
        for (EnumFacing facing : EnumFacing.VALUES) {
            sidePos.move(facing);
            if ((blockedConnection & ITilePipeLike.MASK_BLOCKED << facing.getIndex()) != 0
                || isTileAccessibleAtSide(world, sidePos, facing.getOpposite(), tile.getColor(), tile.getBaseProperty().getThickness()) < 2) {
                connectedSideMask |= (ITilePipeLike.MASK_INPUT_DISABLED | ITilePipeLike.MASK_OUTPUT_DISABLED) << facing.getIndex();
            }
            sidePos.move(facing.getOpposite());
        }
        return connectedSideMask;
    }

    public abstract void onConnectionUpdate(ITilePipeLike tile, World world, BlockPos pos);

    //////////////////////////////////// NETWORK ////////////////////////////////////////////

    public abstract C createCapability(ITilePipeLike<Q, P> tile);

    //////////////////////////////////// RENDER ////////////////////////////////////////////

    public abstract PipeLikeRenderer<Q, T> getRenderer();
}