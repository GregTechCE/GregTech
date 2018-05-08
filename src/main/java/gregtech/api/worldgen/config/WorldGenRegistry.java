package gregtech.api.worldgen.config;

import com.google.gson.JsonObject;
import gregtech.api.GTValues;
import gregtech.api.util.GTLog;
import gregtech.api.worldgen.filler.IBlockFiller;
import gregtech.api.worldgen.filler.SimpleBlockFiller;
import gregtech.api.worldgen.generator.WorldGeneratorImpl;
import gregtech.api.worldgen.shape.*;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class WorldGenRegistry {

    public static final WorldGenRegistry INSTANCE = new WorldGenRegistry();
    private WorldGenRegistry() {}

    private final Map<String, Supplier<IShapeGenerator>> shapeGeneratorRegistry = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    private final Map<String, Supplier<IBlockFiller>> blockFillerRegistry = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    private final List<OreDepositDefinition> registeredDefinitions = new ArrayList<>();
    private final Map<WorldProvider, WorldOreVeinCache> oreVeinCache = new WeakHashMap<>();

    private class WorldOreVeinCache {
        private final List<OreDepositDefinition> worldVeins;
        private final Map<Biome, List<Entry<OreDepositDefinition, Integer>>> biomeVeins = new HashMap<>();

        public WorldOreVeinCache(WorldProvider worldProvider) {
            this.worldVeins = registeredDefinitions.stream()
                .filter(definition -> definition.getDimensionFilter().test(worldProvider))
                .sorted(Collections.reverseOrder(Comparator.comparing(OreDepositDefinition::getPriority)))
                .collect(Collectors.toList());
        }

        private List<Entry<OreDepositDefinition, Integer>> getBiomeEntry(Biome biome) {
            if(biomeVeins.containsKey(biome))
                return biomeVeins.get(biome);
            List<Entry<OreDepositDefinition, Integer>> result = worldVeins.stream()
                .map(vein -> new SimpleEntry<>(vein, vein.getWeight() + vein.getBiomeWeightModifier().apply(biome)))
                .filter(entry -> entry.getValue() > 0)
                .collect(Collectors.toList());
            biomeVeins.put(biome, result);
            return result;
        }
    }

    public List<Entry<OreDepositDefinition, Integer>> getCachedBiomeVeins(WorldProvider provider, Biome biome) {
        if(oreVeinCache.containsKey(provider))
            return oreVeinCache.get(provider).getBiomeEntry(biome);
        WorldOreVeinCache worldOreVeinCache = new WorldOreVeinCache(provider);
        oreVeinCache.put(provider, worldOreVeinCache);
        return worldOreVeinCache.getBiomeEntry(biome);
    }

    public void initializeRegistry() {
        GTLog.logger.info("Initializing ore generation registry...");
        registerShapeGenerator("ellipsoid", EllipsoidGenerator::new);
        registerShapeGenerator("sphere", SphereGenerator::new);
        registerShapeGenerator("plate", PlateGenerator::new);
        registerShapeGenerator("single", SingleBlockGenerator::new);
        registerBlockFiller("simple", SimpleBlockFiller::new);
        GameRegistry.registerWorldGenerator(new WorldGeneratorImpl(), 1);
        try {
            reinitializeRegisteredVeins();
        } catch (IOException | RuntimeException exception) {
            GTLog.logger.fatal("Failed to initialize worldgen registry.", exception);
        }
    }

    public void reinitializeRegisteredVeins() throws IOException {
        GTLog.logger.info("Reloading ore generation files from config...");
        registeredDefinitions.clear();
        oreVeinCache.clear();
        Path configPath = Loader.instance().getConfigDir().toPath().resolve(GTValues.MODID);
        Path worldgenRootPath = configPath.resolve("worldgen");
        Path jarFileExtractLock = configPath.resolve(".worldgen_extracted");
        if(!Files.exists(worldgenRootPath)) {
            Files.createDirectories(worldgenRootPath);
        }
        if(!Files.exists(jarFileExtractLock)) {
            Files.createFile(jarFileExtractLock);
            extractJarVeinDefinitions(worldgenRootPath);
        }
        List<Path> worldgenFiles = Files.walk(worldgenRootPath)
            .filter(path -> path.toString().endsWith(".json"))
            .filter(path -> Files.isRegularFile(path))
            .collect(Collectors.toList());



    }

    private static void extractJarVeinDefinitions(Path worldgenRootPath) {

    }

    public void registerShapeGenerator(String identifier, Supplier<IShapeGenerator> shapeGeneratorSupplier) {
        if(shapeGeneratorRegistry.containsKey(identifier))
            throw new IllegalArgumentException("Identifier already occupied:" + identifier);
        shapeGeneratorRegistry.put(identifier, shapeGeneratorSupplier);
    }

    public void registerBlockFiller(String identifier, Supplier<IBlockFiller> blockFillerSupplier) {
        if(blockFillerRegistry.containsKey(identifier))
            throw new IllegalArgumentException("Identifier already occupied:" + identifier);
        blockFillerRegistry.put(identifier, blockFillerSupplier);
    }

    public IShapeGenerator createShapeGenerator(JsonObject object) {
        String identifier = object.get("type").getAsString();
        if(!shapeGeneratorRegistry.containsKey(identifier))
            throw new IllegalArgumentException("No shape generator found for type " + identifier);
        IShapeGenerator shapeGenerator = shapeGeneratorRegistry.get(identifier).get();
        shapeGenerator.loadFromConfig(object);
        return shapeGenerator;
    }

    public IBlockFiller createBlockFiller(JsonObject object) {
        String identifier = object.get("type").getAsString();
        if(!blockFillerRegistry.containsKey(identifier))
            throw new IllegalArgumentException("No block filler found for type " + identifier);
        IBlockFiller blockFiller = blockFillerRegistry.get(identifier).get();
        blockFiller.loadFromConfig(object);
        return blockFiller;
    }

}
