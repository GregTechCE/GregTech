package gregtech.api.util.world;

import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;

import javax.annotation.Nullable;

public class DummyChunkProvider implements IChunkProvider {

    private final World world;

    public DummyChunkProvider(World world) {
        this.world = world;
    }

    @Nullable
    @Override
    public Chunk getLoadedChunk(int x, int z) {
        return provideChunk(x, z);
    }

    @Override
    public Chunk provideChunk(int x, int z) {
        return new Chunk(world, x, z);
    }

    @Override
    public boolean tick() {
        return false;
    }

    @Override
    public String makeString() {
        return "Dummy";
    }

    @Override
    public boolean isChunkGeneratedAt(int x, int z) {
        return false;
    }
}
