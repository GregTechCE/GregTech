package gregtech.api.worldgen.generator;

import gregtech.api.unification.material.type.MetalMaterial;
import gregtech.api.worldgen.config.OreDepositDefinition;
import gregtech.common.ConfigHolder;
import gregtech.common.blocks.BlockSurfaceRock;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.ArrayList;
import java.util.List;
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
        int randomChunkY = (int) ((Objects.hash(selfGridX, selfGridZ) ^ world.getSeed()) % 16);
        int selfGridY = Math.floorDiv(randomChunkY, GRID_SIZE_Y);
        List<OreDepositDefinition> generatedOres = generateInternal(world, selfGridX, selfGridY, selfGridZ, chunkX, randomChunkY, chunkZ, false);
        //if generated ores aren't empty, and surface rocks are enabled in config, attempt to generate them
        if(!generatedOres.isEmpty() && ConfigHolder.enableOreVeinSurfaceRocks) {
            for(OreDepositDefinition depositDefinition : generatedOres) {
                MetalMaterial material = depositDefinition.getSurfaceStoneMaterial();
                if(material == null) continue;
                int stonesCount = random.nextInt(2);
                for(int i = 0; i < stonesCount; i++) {
                    int randomX = chunkX * 16 + random.nextInt(16);
                    int randomZ = chunkZ * 16 + random.nextInt(16);
                    BlockPos topBlockPos = new BlockPos(randomX, 0, randomZ);
                    topBlockPos = world.getTopSolidOrLiquidBlock(topBlockPos).down();
                    IBlockState blockState = world.getBlockState(topBlockPos);
                    if(!blockState.isBlockNormalCube() || !blockState.isFullBlock())
                        continue;
                    BlockPos topBlock = topBlockPos.up();
                    IBlockState upperState = world.getBlockState(topBlockPos);
                    if(upperState.getBlock() instanceof BlockLiquid ||
                        upperState.getBlock() instanceof IFluidBlock)
                        continue; //do not try to generate inside fluid blocks
                    BlockSurfaceRock blockSurfaceRock = MetaBlocks.SURFACE_ROCKS.get(material);
                    IBlockState statePlace = blockSurfaceRock.getDefaultState().withProperty(blockSurfaceRock.materialProperty, material);
                    world.setBlockState(topBlock, statePlace, 16);
                }
            }
        }
    }

    private List<OreDepositDefinition> generateInternal(World world, int selfGridX, int selfGridY, int selfGridZ, int chunkX, int chunkY, int chunkZ, boolean respectYChunk) {
        GeneratorAccessImpl generatorAccess = new GeneratorAccessImpl(world, chunkX, chunkY, chunkZ, respectYChunk);
        ArrayList<OreDepositDefinition> allGeneratedOres = new ArrayList<>();
        int halfSizeX = (GRID_SIZE_X - 1) / 2;
        int halfSizeY = (GRID_SIZE_Y - 1) / 2;
        int halfSizeZ = (GRID_SIZE_Z - 1) / 2;
        for(int gridX = -halfSizeX; gridX <= halfSizeX; gridX++) {
            for(int gridY = -halfSizeY; gridY <= halfSizeY; gridY++) {
                for(int gridZ = -halfSizeZ; gridZ <= halfSizeZ; gridZ++) {
                    if(!respectYChunk && (selfGridY + gridY < 0 || selfGridY + gridY > 5))
                        continue; //if not respecting y chunks, then skip generation outside vanilla range
                    generatorAccess.setupGridEntry(selfGridX + gridX, selfGridY + gridY, selfGridZ + gridZ);
                    allGeneratedOres.addAll(generatorAccess.triggerVeinsGeneration());
                }
            }
        }
        return allGeneratedOres;
    }

}
