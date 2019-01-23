package gregtech.common.multipart;

import codechicken.lib.data.MCDataInput;
import codechicken.multipart.MultiPartRegistry;
import codechicken.multipart.TMultiPart;
import codechicken.multipart.api.IDynamicPartFactory;
import codechicken.multipart.api.IPartConverter;
import gregtech.api.GTValues;
import gregtech.api.pipenet.block.IPipeType;
import gregtech.api.pipenet.tile.IPipeTile;
import gregtech.api.pipenet.tile.TileEntityPipeBase;
import gregtech.common.pipelike.cable.BlockCable;
import gregtech.common.pipelike.cable.Insulation;
import gregtech.common.pipelike.cable.WireProperties;
import gregtech.common.pipelike.fluidpipe.BlockFluidPipe;
import gregtech.common.pipelike.fluidpipe.FluidPipeProperties;
import gregtech.common.pipelike.fluidpipe.FluidPipeType;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class GTMultipartFactory implements IDynamicPartFactory, IPartConverter {

    public static final ResourceLocation CABLE_PART_KEY = new ResourceLocation(GTValues.MODID, "cable");
    public static final ResourceLocation CABLE_PART_TICKABLE_KEY = new ResourceLocation(GTValues.MODID, "cable_tickable");
    public static final ResourceLocation FLUID_PIPE_PART_KEY = new ResourceLocation(GTValues.MODID, "fluid_pipe");
    public static final ResourceLocation FLUID_PIPE_ACTIVE_PART_KEY = new ResourceLocation(GTValues.MODID, "fluid_pipe_active");

    public static final GTMultipartFactory INSTANCE = new GTMultipartFactory();
    private final Map<ResourceLocation, Supplier<TMultiPart>> partRegistry = new HashMap<>();
    private final Map<Predicate<Block>, BiFunction<IBlockState, TileEntity, TMultiPart>> converterRegistry = new HashMap<>();

    public void registerFactory() {
        registerPart(CABLE_PART_KEY, CableMultiPart::new);
        registerPart(CABLE_PART_TICKABLE_KEY, CableMultiPartTickable::new);
        registerPart(FLUID_PIPE_PART_KEY, FluidPipeMultiPart::new);
        registerPart(FLUID_PIPE_ACTIVE_PART_KEY, FluidPipeActiveMultiPart::new);

        //because java is too dumb to infer types from arguments
        this.<Insulation, WireProperties>registerPipeConverter(block -> block instanceof BlockCable, CableMultiPart::new, CableMultiPartTickable::new);
        this.<FluidPipeType, FluidPipeProperties>registerPipeConverter(block -> block instanceof BlockFluidPipe, FluidPipeMultiPart::new, FluidPipeActiveMultiPart::new);

        MultiPartRegistry.registerParts(this, partRegistry.keySet());
        MultiPartRegistry.registerConverter(this);
    }

    private void registerPart(ResourceLocation identifier, Supplier<TMultiPart> supplier) {
        partRegistry.put(identifier, supplier);
    }

    private <T extends Enum<T> & IPipeType<E>, E> void registerPipeConverter(Predicate<Block> block, Function<IPipeTile<T, E>, PipeMultiPart<T, E>> normalConverter, Function<IPipeTile<T, E>, PipeMultiPart<T, E>> tickableConverter) {
        registerConverter(block, (blockState, tileEntity) -> {
            TileEntityPipeBase<T, E> pipeTileEntity = (TileEntityPipeBase<T, E>) tileEntity;
            pipeTileEntity.setBeingConverted(true);
            if(pipeTileEntity.supportsTicking()) {
                return tickableConverter.apply(pipeTileEntity);
            } else {
                return normalConverter.apply(pipeTileEntity);
            }
        });
    }

    private void registerConverter(Predicate<Block> block, BiFunction<IBlockState, TileEntity, TMultiPart> converter) {
        converterRegistry.put(block, converter);
    }

    @Override
    public TMultiPart createPartServer(ResourceLocation identifier, NBTTagCompound compound) {
        TMultiPart resultPart = createPart(identifier);
        //TODO remove in the net major update
        //prevents creation of invalid parts if material tag is missing
        if(resultPart instanceof PipeMultiPart) {
            if(!compound.hasKey("PipeMaterial", NBT.TAG_STRING)) {
                return null;
            }
        }
        return resultPart;
    }

    @Override
    public TMultiPart createPartClient(ResourceLocation identifier, MCDataInput packet) {
        return createPart(identifier);
    }

    public TMultiPart createPart(ResourceLocation identifier) {
        if(partRegistry.containsKey(identifier)) {
            Supplier<TMultiPart> supplier = partRegistry.get(identifier);
            return supplier.get();

        }
        return null;
    }

    private BiFunction<IBlockState, TileEntity, TMultiPart> findConverter(Block block) {
        return converterRegistry.entrySet().stream()
            .filter(entry -> entry.getKey().test(block))
            .map(Entry::getValue)
            .findFirst().orElse(null);
    }

    @Override
    public boolean canConvert(World world, BlockPos pos, IBlockState state) {
        return findConverter(state.getBlock()) != null;
    }

    public TMultiPart convert(World world, BlockPos pos, IBlockState state) {
        BiFunction<IBlockState, TileEntity, TMultiPart> converter = findConverter(state.getBlock());
        if(converter != null) {
            TileEntity tileEntity = world.getTileEntity(pos);
            return converter.apply(state, tileEntity);
        }
        return null;
    }


}
