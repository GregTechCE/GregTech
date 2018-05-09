package gregtech.api.worldgen.generator;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Objects;
import java.util.Random;

//TODO implement CC support here
public class WorldGeneratorImpl implements IWorldGenerator {

    public static final int GRID_SIZE_X = 3;
    public static final int GRID_SIZE_Y = 3;
    public static final int GRID_SIZE_Z = 3;

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        int selfGridX = Math.floorDiv(chunkX, GRID_SIZE_X);
        int selfGridZ = Math.floorDiv(chunkZ, GRID_SIZE_Z);
        //because some way to generate random Y is needed here
        int randomChunkY = (int) (Objects.hash(selfGridX, selfGridZ) ^ world.getSeed() & 16);
        int selfGridY = Math.floorDiv(randomChunkY, GRID_SIZE_Y);
        generateInternal(world, selfGridX, selfGridY, selfGridZ, chunkX, randomChunkY, chunkZ, false);
    }

    private void generateInternal(World world, int selfGridX, int selfGridY, int selfGridZ, int chunkX, int chunkY, int chunkZ, boolean respectYChunk) {
        GeneratorAccessImpl generatorAccess = new GeneratorAccessImpl(world, chunkX, chunkY, chunkZ, respectYChunk);
        int halfSizeX = (GRID_SIZE_X - 1) / 2;
        int halfSizeY = (GRID_SIZE_Y - 1) / 2;
        int halfSizeZ = (GRID_SIZE_Z - 1) / 2;
        for(int gridX = -halfSizeX; gridX <= halfSizeX; gridX++) {
            for(int gridY = -halfSizeY; gridY <= halfSizeY; gridY++) {
                for(int gridZ = -halfSizeZ; gridZ <= halfSizeZ; gridZ++) {
                    if(!respectYChunk && (selfGridY + gridY < 0 || selfGridY + gridY > 5))
                        continue; //if not respecting y chunks, then skip generation outside vanilla range
                    generatorAccess.setupGridEntry(selfGridX + gridX, selfGridY + gridY, selfGridZ + gridZ);
                    generatorAccess.triggerVeinsGeneration();
                }
            }
        }
    }

}
