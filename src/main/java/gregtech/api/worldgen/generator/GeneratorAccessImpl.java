package gregtech.api.worldgen.generator;

import gregtech.api.util.GTLog;
import gregtech.api.util.GTUtility;
import gregtech.api.util.XSTR;
import gregtech.api.worldgen.config.OreDepositDefinition;
import gregtech.api.worldgen.config.WorldGenRegistry;
import gregtech.common.ConfigHolder;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.*;
import java.util.Map.Entry;

public class GeneratorAccessImpl implements IBlockGeneratorAccess {

    protected final World world;
    protected final int chunkX, chunkY, chunkZ;
    protected boolean performYCheck;

    private Random gridRandom;
    private int gridX, gridY, gridZ;

    private int veinCenterX, veinCenterY, veinCenterZ;
    private OreDepositDefinition currentOreVein;
    private MutableBlockPos currentPos = new MutableBlockPos();

    public GeneratorAccessImpl(World world, int chunkX, int chunkY, int chunkZ, boolean performYCheck) {
        this.world = world;
        this.chunkX = chunkX;
        this.chunkY = chunkY;
        this.chunkZ = chunkZ;
        this.performYCheck = performYCheck;
    }

    public void setupGridEntry(int gridX, int gridY, int gridZ) {
        this.gridX = gridX;
        this.gridY = gridY;
        this.gridZ = gridZ;
        long gridRandomSeed = Objects.hash(gridX, gridY, gridZ) ^ world.getSeed();
        this.gridRandom = new XSTR(gridRandomSeed);
    }

    /**
     * Generates random veins in current grid section using current
     * grid random seed within this chunk
     * Biome lookup is applied to center chunk of grid section
     *
     * @return list of generated veins or empty if none generated
     */
    public List<OreDepositDefinition> triggerVeinsGeneration() {
        if(gridRandom.nextInt(ConfigHolder.chunkOreVeinGenerationProbability) != 0)
            return Collections.emptyList();
        int gridSizeX = WorldGeneratorImpl.GRID_SIZE_X * 16;
        int gridSizeY = WorldGeneratorImpl.GRID_SIZE_Y * 16;
        int gridSizeZ = WorldGeneratorImpl.GRID_SIZE_Z * 16;
        currentPos.setPos(
            gridX * gridSizeX + gridSizeX / 2,
            gridY * gridSizeY + gridSizeY / 2,
            gridZ * gridSizeZ + gridSizeZ / 2);
        Biome currentBiome = world.getBiomeProvider().getBiome(currentPos);
        List<Entry<Integer, OreDepositDefinition>> cachedDepositMap = new ArrayList<>(
            WorldGenRegistry.INSTANCE.getCachedBiomeVeins(world.provider, currentBiome));

        cachedDepositMap.removeIf(entry -> !entry.getValue().checkInHeightLimit(currentPos.getY()));
        if(cachedDepositMap.isEmpty())
            return Collections.emptyList(); //do not try to generate an empty vein list

        ArrayList<OreDepositDefinition> generatedDeposits = new ArrayList<>();
        int currentCycle = 0;
        while(currentCycle < cachedDepositMap.size()) {
            if(currentCycle != 0 && gridRandom.nextInt(ConfigHolder.chunkOreVeinSecondaryProbability * currentCycle) != 0)
                break; //give 100% generation in first cycle, then decrease it every time we generate secondary vein
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

        if(ConfigHolder.debug) {
            GTLog.logger.info("Generating ore vein {} at {}", generatedDeposits, currentPos);
        }
        for(OreDepositDefinition depositDefinition : generatedDeposits) {
            doGenerateVein(depositDefinition);
        }
        return generatedDeposits;
    }

    public void doGenerateVein(OreDepositDefinition oreVein) {
        this.currentOreVein = oreVein;
        int gridSizeX = WorldGeneratorImpl.GRID_SIZE_X * 16;
        int gridSizeY = WorldGeneratorImpl.GRID_SIZE_Y * 16;
        int gridSizeZ = WorldGeneratorImpl.GRID_SIZE_Z * 16;
        this.veinCenterX = gridX * gridSizeX + gridRandom.nextInt(gridSizeX);
        this.veinCenterY = gridY * gridSizeY + gridRandom.nextInt(gridSizeY);
        this.veinCenterZ = gridZ * gridSizeZ + gridRandom.nextInt(gridSizeZ);
        this.currentOreVein.getShapeGenerator().generate(gridRandom, this);
        this.currentOreVein = null;
    }

    //xyz are in global world space
    protected boolean checkChunkBounds(int x, int y, int z) {
        return x >= chunkX * 16 && chunkX * 16 + 16 > x &&
            (!performYCheck || y >= chunkY * 16 && chunkY * 16 + 16 > y) &&
            z >= chunkZ * 16 && chunkZ * 16 + 16 > z;
    }

    @Override
    //xyz are in local vein space relative to center at 0,0,0
    public boolean generateBlock(int x, int y, int z) {
        if(currentOreVein == null)
            throw new IllegalStateException("Attempted to call generateBlock without current ore vein!");
        int globalBlockX = veinCenterX + x;
        int globalBlockY = veinCenterY + y;
        int globalBlockZ = veinCenterZ + z;
        //we should do all random-related things here, otherwise it gets corrupted by current chunk information
        float randomDensityValue = gridRandom.nextFloat();

        if(!checkChunkBounds(globalBlockX, globalBlockY, globalBlockZ))
            return false; //do not place block outside current chunk boundaries

        if(currentOreVein.getDensity() < randomDensityValue)
            return false; //only place blocks in positions matching density

        currentPos.setPos(globalBlockX, globalBlockY, globalBlockZ);
        IBlockState blockStateHere = world.getBlockState(currentPos);

        if(!currentOreVein.getGenerationPredicate().test(blockStateHere))
            return false; //generate only on matching positions

        IBlockState placedState = currentOreVein.getBlockFiller().getStateForGeneration(blockStateHere, x, y, z);

        if(placedState == null || blockStateHere == placedState)
            return false; //do not place anything if state didn't change
        //finally place block in world without sending to client and
        //with 16 flag to prevent observers update from loading neighbour chunks
        return world.setBlockState(currentPos, placedState, 16);
    }

}
