package gregtech.common.worldgen;

import java.util.HashSet;
import java.util.Random;

import gregtech.api.GregTechAPI;
import gregtech.api.util.GTLog;
import gregtech.api.util.GTWorldGen;
import gregtech.api.util.XSTR;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class WorldGenerator implements IWorldGenerator {

    public static int getVeinCenterCoordinate(int c) {
        c += c < 0 ? 1 : 3;
        return c - c % 3 - 2;
    }

    public static long getRandomSeed(World world, int xChunk, int zChunk) {
        long worldSeed = world.getSeed();
        Random fmlRandom = new Random(worldSeed);
        long xSeed = fmlRandom.nextLong() >> 2 + 1L;
        long zSeed = fmlRandom.nextLong() >> 2 + 1L;
        long chunkSeed = xSeed * xChunk + zSeed * zChunk ^ worldSeed;
        fmlRandom.setSeed(chunkSeed);
        return fmlRandom.nextInt();
    }

    public static void generateOreLayerAt(int chunkX, int chunkZ, int centerX, int centerZ, World world, Biome biome, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        Random rnd = new XSTR(getRandomSeed(world, centerX, centerZ));
        try {
            GTWorldGen_OreVein.getRandomOreVein(rnd, world, biome).ifPresent(oregen -> oregen.generate(rnd, chunkX, chunkZ, centerX, centerZ, world, biome, chunkGenerator, chunkProvider));
        } catch (Exception e) {
            GTLog.logger.catching(e);
        }
    }

    private Boolean sorted = false;

    public WorldGenerator() {
        GameRegistry.registerWorldGenerator(this, 1073741823);
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        synchronized (sorted) {
            if (!sorted) {
                GregTechAPI.worldgenList.sort(GTWorldGen::compareTo);
                sorted = true;
            }
        }
        Random rnd = new XSTR();
        Biome biome = world.getBiome(new BlockPos(chunkX << 4, 64, chunkZ << 4));
        HashSet<ChunkPos> centers = new HashSet<>();
        int maxRange = (GTWorldGen_OreVein.getMaxOreVeinSize(world) + 15) >> 4;
        if (maxRange > 0) {
            for (int i = -maxRange; i <= maxRange; i++) {
                for (int j = -maxRange; j <= maxRange; j++) {
                    centers.add(new ChunkPos(getVeinCenterCoordinate(chunkX + i), getVeinCenterCoordinate(chunkZ + j)));
                }
            }
            centers.forEach(pos -> generateOreLayerAt(chunkX, chunkZ, pos.x, pos.z, world, biome, chunkGenerator, chunkProvider));
        }
        for (GTWorldGen worldgen : GregTechAPI.worldgenList) {
            try {
                worldgen.generate(rnd, chunkX, chunkZ, world, biome, chunkGenerator, chunkProvider);
            } catch (Exception e) {
                GTLog.logger.catching(e);
            }
        }
    }

}
