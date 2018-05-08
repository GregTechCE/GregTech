package gregtech.api.worldgen.generator;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

//TODO implement CC support here
public class WorldGeneratorImpl implements IWorldGenerator {

    public static final int GRID_SIZE_X = 3;
    public static final int GRID_SIZE_Y = 3;
    public static final int GRID_SIZE_Z = 3;

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        for(int chunkY = 0; chunkY < world.provider.getActualHeight() / 16; chunkY++) {
            generateInternal(world, chunkX, chunkY, chunkZ);
        }
    }

    private void generateInternal(World world, int chunkX, int chunkY, int chunkZ) {
        GeneratorAccessImpl generatorAccess = new GeneratorAccessImpl(world, chunkX, chunkY, chunkZ);
        int selfGridX = chunkX / GRID_SIZE_X + chunkX >= 0 ? 0 : -1; //append -1 to negative chunks
        int selfGridY = chunkY / GRID_SIZE_Y + chunkY >= 0 ? 0 : -1; //append -1 to negative chunks
        int selfGridZ = chunkZ / GRID_SIZE_Z + chunkZ >= 0 ? 0 : -1; //append -1 to negative chunks
        int halfSizeX = (GRID_SIZE_X - 1) / 2;
        int halfSizeY = (GRID_SIZE_Y - 1) / 2;
        int halfSizeZ = (GRID_SIZE_Z - 1) / 2;
        for(int gridX = -halfSizeX; gridX <= halfSizeX; gridX++) {
            for(int gridY = -halfSizeY; gridY <= halfSizeY; gridY++) {
                for(int gridZ = -halfSizeZ; gridZ <= halfSizeZ; gridZ++) {
                    generatorAccess.setupGridEntry(selfGridX + gridX, selfGridY + gridY, selfGridZ + gridZ);
                    generatorAccess.triggerVeinsGeneration();
                }
            }
        }
    }

}
