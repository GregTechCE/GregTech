package gregtech.api.worldgen.generator;

import gregtech.api.unification.material.type.IngotMaterial;
import gregtech.api.worldgen.config.OreDepositDefinition;
import gregtech.common.ConfigHolder;
import gregtech.common.blocks.surfacerock.BlockSurfaceRock;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.blocks.surfacerock.BlockSurfaceRockFlooded;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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
            event.setResult(Result.DENY);
        }
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        int selfGridX = Math.floorDiv(chunkX, GRID_SIZE_X);
        int selfGridZ = Math.floorDiv(chunkZ, GRID_SIZE_Z);
        List<OreDepositDefinition> generatedOres = generateInternal(world, selfGridX, selfGridZ, chunkX, chunkZ);

        //if we didn't generate anything, or surface rocks disabled, or it is a flat world,
        //do not generate them at all
        if (generatedOres.isEmpty() ||
            !ConfigHolder.enableOreVeinSurfaceRocks ||
            world.getWorldType() == WorldType.FLAT)
            return;

        for (OreDepositDefinition depositDefinition : generatedOres) {
            IngotMaterial material = depositDefinition.getSurfaceStoneMaterial();
            if (material == null) continue;
            int stonesCount = random.nextInt(2);

            for (int i = 0; i < stonesCount; i++) {
                int randomX = chunkX * 16 + random.nextInt(16);
                int randomZ = chunkZ * 16 + random.nextInt(16);
                BlockPos topBlockPos = new BlockPos(randomX, 0, randomZ);
                topBlockPos = world.getTopSolidOrLiquidBlock(topBlockPos).down();
                IBlockState blockState = world.getBlockState(topBlockPos);
                if(blockState.getBlockFaceShape(world, topBlockPos, EnumFacing.UP) != BlockFaceShape.SOLID ||
                    !blockState.isOpaqueCube() || !blockState.isFullBlock())
                    continue;
                BlockPos surfaceRockPos = topBlockPos.up();
                IBlockState blockStateReplaced = world.getBlockState(surfaceRockPos);

                boolean isFloodedBlock = blockStateReplaced.getMaterial() == Material.WATER;
                IBlockState stoneBlockState;
                if(!isFloodedBlock) {
                    BlockSurfaceRock blockSurfaceRock = MetaBlocks.SURFACE_ROCKS.get(material);
                    stoneBlockState = blockSurfaceRock.getDefaultState().withProperty(blockSurfaceRock.materialProperty, material);
                } else {
                    BlockSurfaceRockFlooded blockSurfaceRock = MetaBlocks.FLOODED_SURFACE_ROCKS.get(material);
                    stoneBlockState = blockSurfaceRock.getDefaultState().withProperty(blockSurfaceRock.materialProperty, material);
                }
                world.setBlockState(surfaceRockPos, stoneBlockState, 16);
            }
        }
    }

    public static List<OreDepositDefinition> getGeneratedVeinsAt(World world, BlockPos blockPos) {
        int chunkX = blockPos.getX() >> 4;
        int chunkZ = blockPos.getZ() >> 4;
        int selfGridX = Math.floorDiv(chunkX, GRID_SIZE_X);
        int selfGridZ = Math.floorDiv(chunkZ, GRID_SIZE_Z);
        CachedGridEntry cachedGridEntry = CachedGridEntry.getOrCreateEntry(world, selfGridX, selfGridZ);
        return cachedGridEntry.getGeneratedVeins();
    }

    private List<OreDepositDefinition> generateInternal(World world, int selfGridX, int selfGridZ, int chunkX, int chunkZ) {
        List<OreDepositDefinition> allGeneratedOres = Collections.emptyList();
        int halfSizeX = (GRID_SIZE_X - 1) / 2;
        int halfSizeZ = (GRID_SIZE_Z - 1) / 2;
        for(int gridX = -halfSizeX; gridX <= halfSizeX; gridX++) {
                for(int gridZ = -halfSizeZ; gridZ <= halfSizeZ; gridZ++) {
                    CachedGridEntry cachedGridEntry = CachedGridEntry.getOrCreateEntry(world, selfGridX + gridX, selfGridZ + gridZ);
                    boolean generatedSomething = cachedGridEntry.populateChunk(world, chunkX, chunkZ);
                    if(gridX == 0 && gridZ == 0 && generatedSomething) {
                        //add generated definitions only in current grid entry
                        //and only if we really generated at least one block of ore in current chunk
                        allGeneratedOres = cachedGridEntry.getGeneratedVeins();
                    }
                }
        }
        return allGeneratedOres;
    }

}
