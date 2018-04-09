package gregtech.common.worldgen;

import gregtech.loaders.postload.WorldgenLoader;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class GTWorldGenerator implements IWorldGenerator {
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        ChunkPos chunkPos = new ChunkPos(chunkX, chunkZ);
        Random worldRandom = new Random(world.getSeed());
        for (WorldgenLoader.DepositConfig deposit : WorldgenLoader.deposits) {
            Random depositRandom = new Random(worldRandom.nextInt());
            deposit.generate(depositRandom, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
        }
    }
}
