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
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.*;

import static net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.*;

//TODO implement CC support here
public class WorldGeneratorImpl implements IWorldGenerator {

    private static final List<EventType> ORE_EVENT_TYPES = Arrays.asList(
        COAL, DIAMOND, GOLD, IRON, LAPIS, REDSTONE, QUARTZ, DIORITE, GRANITE, ANDESITE, EMERALD);
    public static final int GRID_SIZE_X = 3;
    public static final int GRID_SIZE_Z = 3;

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onOreGenerate(OreGenEvent.GenerateMinable event) {
        EventType eventType = event.getType();
        if(ConfigHolder.disableVanillaOres &&
            ORE_EVENT_TYPES.contains(eventType)) {
            event.setResult(Result.ALLOW);
        }
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        int selfGridX = Math.floorDiv(chunkX, GRID_SIZE_X);
        int selfGridZ = Math.floorDiv(chunkZ, GRID_SIZE_Z);
        List<OreDepositDefinition> generatedOres = generateInternal(world, selfGridX, selfGridZ, chunkX, chunkZ);

        //if generated ores aren't empty, and surface rocks are enabled in config, attempt to generate them
        if (generatedOres.isEmpty() || !ConfigHolder.enableOreVeinSurfaceRocks)
            return;

        for (OreDepositDefinition depositDefinition : generatedOres) {
            MetalMaterial material = depositDefinition.getSurfaceStoneMaterial();
            if (material == null) continue;
            int stonesCount = random.nextInt(2);

            for (int i = 0; i < stonesCount; i++) {
                int randomX = chunkX * 16 + random.nextInt(16);
                int randomZ = chunkZ * 16 + random.nextInt(16);
                BlockPos topBlockPos = new BlockPos(randomX, 0, randomZ);
                topBlockPos = world.getTopSolidOrLiquidBlock(topBlockPos).down();
                IBlockState blockState = world.getBlockState(topBlockPos);
                if (!blockState.isBlockNormalCube() || !blockState.isFullBlock())
                    continue; //do not generate on non-solid blocks

                BlockPos topBlock = topBlockPos.up();
                IBlockState upperState = world.getBlockState(topBlockPos);
                if (upperState.getBlock() instanceof BlockLiquid ||
                    upperState.getBlock() instanceof IFluidBlock)
                    continue; //do not try to generate inside fluid blocks

                BlockSurfaceRock blockSurfaceRock = MetaBlocks.SURFACE_ROCKS.get(material);
                IBlockState statePlace = blockSurfaceRock.getDefaultState().withProperty(blockSurfaceRock.materialProperty, material);
                world.setBlockState(topBlock, statePlace, 16);
            }
        }
    }

    private List<OreDepositDefinition> generateInternal(World world, int selfGridX, int selfGridZ, int chunkX, int chunkZ) {
        List<OreDepositDefinition> allGeneratedOres = Collections.emptyList();
        int halfSizeX = (GRID_SIZE_X - 1) / 2;
        int halfSizeZ = (GRID_SIZE_Z - 1) / 2;
        for(int gridX = -halfSizeX; gridX <= halfSizeX; gridX++) {
                for(int gridZ = -halfSizeZ; gridZ <= halfSizeZ; gridZ++) {
                    CachedGridEntry cachedGridEntry = CachedGridEntry.getOrCreateEntry(world, selfGridX + gridX, selfGridZ + gridZ);
                    cachedGridEntry.populateChunk(world, chunkX, chunkZ);
                    if(gridX == 0 && gridZ == 0) {
                        //add generated definitions only in current grid entry
                        allGeneratedOres = cachedGridEntry.getGeneratedVeins();
                    }
                }
        }
        return allGeneratedOres;
    }

}
