package gregtech.api.multipart;

import codechicken.multipart.MultiPartRegistry;
import codechicken.multipart.TMultiPart;
import codechicken.multipart.api.IPartConverter;
import codechicken.multipart.api.IPartFactory;
import com.google.common.collect.Maps;
import gregtech.api.pipelike.BlockPipeLike;
import gregtech.api.pipelike.PipeFactory;
import gregtech.api.pipelike.TileEntityPipeLike;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Map;

@SuppressWarnings("unchecked")
public class PipeMultipartFactory implements IPartConverter, IPartFactory {

    private static final Map<ResourceLocation, PipeFactory> multipartFactories = Maps.newHashMap();

    public static final PipeMultipartFactory INSTANCE = new PipeMultipartFactory();

    public static void registerMultipartFactory() {
        PipeFactory.allFactories.values().forEach(factory -> multipartFactories.put(factory.getMultipartType(), factory));
        MultiPartRegistry.registerParts(INSTANCE, multipartFactories.keySet());
        MultiPartRegistry.registerConverter(INSTANCE);
    }

    @Override
    public TMultiPart createPart(ResourceLocation identifier, boolean client) {
        PipeFactory factory = multipartFactories.get(identifier);
        if (factory == null) return null;
        return (TMultiPart) factory.createMultipart();
    }

    @Override
    public boolean canConvert(World world, BlockPos pos, IBlockState state) {
        return state.getBlock() instanceof BlockPipeLike;
    }

    @Override
    public Iterable<TMultiPart> convertToParts(World world, BlockPos pos, IBlockState state) {
        return IPartConverter.super.convertToParts(world, pos, state);//TODO Covers
    }

    @Override
    public TMultiPart convert(World world, BlockPos pos, IBlockState state) {
        if (state.getBlock() instanceof BlockPipeLike) {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof TileEntityPipeLike) {
                return (TMultiPart) ((TileEntityPipeLike) tileEntity).getFactory().createMultipart((TileEntityPipeLike) tileEntity, state);
            }
        }
        return null;
    }
}
