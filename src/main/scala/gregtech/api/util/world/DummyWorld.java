package gregtech.api.util.world;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.profiler.Profiler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;

import javax.annotation.Nullable;

public class DummyWorld extends World {

    public static final DummyWorld INSTANCE = new DummyWorld();

    private static WorldSettings DUMMY_SETTINGS = new WorldSettings(1L, GameType.SURVIVAL, true, false, WorldType.DEFAULT);
    private static WorldInfo DUMMY_INFO = new WorldInfo(DUMMY_SETTINGS, "DUMMY_DIMENSION");

    public DummyWorld() {
        super(new DummySaveHandler(), DUMMY_INFO, new WorldProviderSurface(), new Profiler(), false);
    }

    protected IChunkProvider createChunkProvider() {
        return new DummyChunkProvider(this);
    }

    @Override
    protected boolean isChunkLoaded(int x, int z, boolean allowEmpty) {
        return true;
    }

    @Override
    public Entity getEntityByID(int aEntityID) {
        return null;
    }

    @Override
    public boolean setBlockState(BlockPos pos, IBlockState newState, int flags) {
        return true;
    }

    @Override
    public float getSunBrightness(float par1) {
        return 1.0F;
    }

    @Override
    public Biome getBiomeForCoordsBody(BlockPos pos) {
        return getBiome(pos);
    }

    @Override
    public Biome getBiome(BlockPos pos) {
        return Biomes.PLAINS;
    }

    @Override
    public int getLight(BlockPos pos) {
        return 10;
    }

    @Override
    public int getCombinedLight(BlockPos pos, int lightValue) {
        return 10;
    }

    @Override
    public IBlockState getBlockState(BlockPos pos) {
        return Blocks.AIR.getDefaultState();
    }

    @Nullable
    @Override
    public TileEntity getTileEntity(BlockPos pos) {
        return null;
    }

    @Override
    public void setTileEntity(BlockPos pos, @Nullable TileEntity tileEntityIn) {}

    @Override
    public void removeTileEntity(BlockPos pos) {}

    @Override
    public boolean addTileEntity(TileEntity tile) {
        return true;
    }

    @Override
    public boolean canBlockSeeSky(BlockPos pos) {
        return true;
    }

}
