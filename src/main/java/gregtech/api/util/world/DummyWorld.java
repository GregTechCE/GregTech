package gregtech.api.util.world;

import net.minecraft.profiler.Profiler;
import net.minecraft.world.*;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldInfo;

public class DummyWorld extends World {

    private static WorldSettings DEFAULT_SETTINGS = new WorldSettings(
        1L, GameType.SURVIVAL, true, false, WorldType.DEFAULT);

    public static final DummyWorld INSTANCE = new DummyWorld();

    public DummyWorld() {
        super(new DummySaveHandler(), new WorldInfo(DEFAULT_SETTINGS, "DummyServer"), new WorldProviderSurface(), new Profiler(), false);
        // Guarantee the dimension ID was not reset by the provider
        int providerDim = this.provider.getDimension();
        this.provider.setWorld(this);
        this.provider.setDimension(providerDim);
        this.chunkProvider = this.createChunkProvider();
        //noinspection ConstantConditions
        this.perWorldStorage = new MapStorage(null);
        this.calculateInitialSkylight();
        this.calculateInitialWeather();
        this.getWorldBorder().setSize(30000000);
    }

    @Override
    protected IChunkProvider createChunkProvider() {
        return new DummyChunkProvider(this);
    }

    @Override
    protected boolean isChunkLoaded(int x, int z, boolean allowEmpty) {
        return chunkProvider.isChunkGeneratedAt(x, z);
    }
}
