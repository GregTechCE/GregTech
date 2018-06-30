package gregtech.api.worldgen.generator;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import gnu.trove.list.TLongList;
import gnu.trove.list.array.TLongArrayList;
import gnu.trove.map.TLongObjectMap;
import gnu.trove.map.hash.TLongObjectHashMap;
import gregtech.api.util.GTUtility;
import gregtech.api.util.XSTR;
import gregtech.api.worldgen.config.OreDepositDefinition;
import gregtech.api.worldgen.config.WorldGenRegistry;
import gregtech.common.ConfigHolder;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

public class CachedGridEntry implements IBlockGeneratorAccess {

    private static final Map<World, LoadingCache<Long, CachedGridEntry>> gridEntryCache = new WeakHashMap<>();

    public static CachedGridEntry getOrCreateEntry(World world, int gridX, int gridZ) {
        LoadingCache<Long, CachedGridEntry> currentValue = gridEntryCache.get(world);
        if(currentValue == null) {
            currentValue = createGridCache(world);
            gridEntryCache.put(world, currentValue);
        }
        Long gridEntryKey = (long) gridX << 32 | gridZ & 0xFFFFFFFFL;
        return currentValue.getUnchecked(gridEntryKey);
    }

    private static LoadingCache<Long, CachedGridEntry> createGridCache(World world) {
        return CacheBuilder.newBuilder()
            .maximumSize(300)
            .expireAfterAccess(5L, TimeUnit.MINUTES)
            .build(new CacheLoader<Long, CachedGridEntry>() {
                @Override
                public CachedGridEntry load(Long key) {
                    long actualKey = key;
                    int gridX = (int) (actualKey >> 32);
                    int gridZ = (int) actualKey;
                    return new CachedGridEntry(world, gridX, gridZ);
                }
            });
    }

    private final TLongObjectMap<ChunkDataEntry> dataByChunkPos = new TLongObjectHashMap<>();

    private final XSTR gridRandom;
    private final int gridX;
    private final int gridZ;
    private List<Entry<Integer, OreDepositDefinition>> cachedDepositMap;
    private int maxHeight;
    private List<OreDepositDefinition> generatedVeins;

    private int veinCenterX, veinCenterY, veinCenterZ;
    private OreDepositDefinition currentOreVein;

    public CachedGridEntry(World world, int gridX, int gridZ) {
        this.gridX = gridX;
        this.gridZ = gridZ;
        long gridRandomSeed = Objects.hash(gridX, gridZ) ^ world.getSeed();
        this.gridRandom = new XSTR(gridRandomSeed);

        int gridSizeX = WorldGeneratorImpl.GRID_SIZE_X * 16;
        int gridSizeZ = WorldGeneratorImpl.GRID_SIZE_Z * 16;
        BlockPos blockPos = new BlockPos(gridX * gridSizeX + gridSizeX / 2, world.getActualHeight(), gridZ * gridSizeZ + gridSizeZ / 2);
        Biome currentBiome = world.getBiomeProvider().getBiome(blockPos);
        this.cachedDepositMap = new ArrayList<>(WorldGenRegistry.INSTANCE.getCachedBiomeVeins(world.provider, currentBiome));
        this.maxHeight = world.getActualHeight();
        this.generatedVeins = triggerVeinsGeneration();
    }

    public List<OreDepositDefinition> getGeneratedVeins() {
        return Collections.unmodifiableList(generatedVeins);
    }

    public void populateChunk(World world, int chunkX, int chunkZ) {
        long chunkId = (long) chunkX << 32 | chunkZ & 0xFFFFFFFFL;
        ChunkDataEntry chunkDataEntry = dataByChunkPos.get(chunkId);
        if(chunkDataEntry != null) {
            chunkDataEntry.populateChunk(world);
        }
    }

    public List<OreDepositDefinition> triggerVeinsGeneration() {
        if(cachedDepositMap.isEmpty())
            return Collections.emptyList(); //do not try to generate an empty vein list
        ArrayList<OreDepositDefinition> generatedDeposits = new ArrayList<>();
        int currentCycle = 0;
        int maxCycles = ConfigHolder.minVeinsInSection + (ConfigHolder.additionalVeinsInSection == 0 ? 0 :
            gridRandom.nextInt(ConfigHolder.additionalVeinsInSection + 1));
        while(currentCycle < cachedDepositMap.size() && currentCycle < maxCycles) {
            //instead of removing already generated veins, we swap last element with one we selected
            int randomEntryIndex = GTUtility.getRandomItem(gridRandom, cachedDepositMap, cachedDepositMap.size() - currentCycle);
            OreDepositDefinition randomEntry = cachedDepositMap.get(randomEntryIndex).getValue();
            Collections.swap(cachedDepositMap, randomEntryIndex, cachedDepositMap.size() - 1 - currentCycle);
            //need to put into list first to apply priority properly, so
            //red granite vein will be properly filled with ores from other veins
            generatedDeposits.add(randomEntry);
            currentCycle++;
        }
        //sort generated veins according to their priority, so they get mixed in properly
        generatedDeposits.sort(Collections.reverseOrder(Comparator.comparing(OreDepositDefinition::getPriority)));
        //and finally generate all of them
        for(OreDepositDefinition depositDefinition : generatedDeposits) {
            doGenerateVein(depositDefinition);
        }
        return generatedDeposits;
    }

    private void doGenerateVein(OreDepositDefinition definition) {
        this.currentOreVein = definition;
        int gridSizeX = WorldGeneratorImpl.GRID_SIZE_X * 16;
        int gridSizeZ = WorldGeneratorImpl.GRID_SIZE_Z * 16;
        this.veinCenterX = gridX * gridSizeX + gridRandom.nextInt(gridSizeX);
        int maximumHeight = Math.min(maxHeight, definition.getHeightLimit()[1]);
        int minimumHeight = Math.max(3, definition.getHeightLimit()[0]);
        this.veinCenterY = minimumHeight + gridRandom.nextInt(maximumHeight - minimumHeight);
        this.veinCenterZ = gridZ * gridSizeZ + gridRandom.nextInt(gridSizeZ);
        this.currentOreVein.getShapeGenerator().generate(new XSTR(gridRandom.getSeed()), this);
        this.currentOreVein = null;
    }

    @Override
    public boolean generateBlock(int x, int y, int z) {
        if(currentOreVein == null)
            throw new IllegalStateException("Attempted to call generateBlock without current ore vein!");
        int globalBlockX = veinCenterX + x;
        int globalBlockY = veinCenterY + y;
        int globalBlockZ = veinCenterZ + z;
        //we should do all random-related things here, otherwise it gets corrupted by current chunk information
        float randomDensityValue = gridRandom.nextFloat();

        if(currentOreVein.getDensity() < randomDensityValue)
            return false; //only place blocks in positions matching density

        setBlock(globalBlockX, globalBlockY, globalBlockZ, currentOreVein);
        return true;
    }

    private void setBlock(int worldX, int worldY, int worldZ, OreDepositDefinition definition) {
        int chunkX = worldX >> 4;
        int chunkZ = worldZ >> 4;
        int localX = worldX - chunkX * 16;
        int localZ = worldZ - chunkZ * 16;
        long chunkKey = (long) chunkX << 32 | chunkZ & 0xFFFFFFFFL;
        ChunkDataEntry dataEntry = dataByChunkPos.get(chunkKey);
        if(dataEntry == null) {
            dataEntry = new ChunkDataEntry(chunkX, chunkZ);
            dataByChunkPos.put(chunkKey, dataEntry);
        }
        dataEntry.setBlock(localX, worldY, localZ, definition);
    }


    public static class ChunkDataEntry {

        private final Map<OreDepositDefinition, TLongList> oreBlocks = new HashMap<>();
        private final int chunkX;
        private final int chunkZ;

        public ChunkDataEntry(int chunkX, int chunkZ) {
            this.chunkX = chunkX;
            this.chunkZ = chunkZ;
        }

        public void setBlock(int x, int y, int z, OreDepositDefinition definition) {
            int xzValue = ((x & 0xFF) << 8) | (z & 0xFF);
            long blockIndex = (long) xzValue << 32 | y & 0xFFFFFFFFL;
            TLongList longList = oreBlocks.get(definition);
            if(longList == null) {
                longList = new TLongArrayList();
                oreBlocks.put(definition, longList);
            }
            longList.add(blockIndex);
        }

        public void populateChunk(World world) {
            MutableBlockPos blockPos = new MutableBlockPos();
            for(OreDepositDefinition definition : oreBlocks.keySet()) {
                long[] blockIndexArray = oreBlocks.get(definition).toArray();
                for(long blockIndex : blockIndexArray) {
                    int xzValue = (int) (blockIndex >> 32);
                    int blockY = (int) blockIndex;
                    int blockX = (byte) (xzValue >> 8);
                    int blockZ = (byte) xzValue;
                    blockPos.setPos(chunkX * 16 + blockX, blockY, chunkZ * 16 + blockZ);
                    IBlockState currentState = world.getBlockState(blockPos);
                    if(!definition.getGenerationPredicate().test(currentState))
                        continue; //do not generate if predicate didn't match
                    IBlockState newState = definition.getBlockFiller().getStateForGeneration(currentState, blockX, blockY, blockZ);
                    //set flags as 16 to avoid observer updates loading neighbour chunks
                    world.setBlockState(blockPos, newState, 16);
                }
            }
        }

    }

}
