package gregtech.api.worldgen.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class WorldGenRegistry {

    private static final JsonParser jsonParser = new JsonParser();
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

        for(Path worldgenDefinition : worldgenFiles) {
            String depositName = worldgenRootPath.relativize(worldgenDefinition).toString();
            try(InputStream fileStream = Files.newInputStream(worldgenDefinition)) {
                InputStreamReader streamReader = new InputStreamReader(fileStream);
                JsonObject element = jsonParser.parse(streamReader).getAsJsonObject();
                OreDepositDefinition deposit = new OreDepositDefinition(depositName);
                deposit.initializeFromConfig(element);
                registeredDefinitions.add(deposit);
            }
        }
        GTLog.logger.info("Loaded {} worldgen definitions", registeredDefinitions.size());
    }

    private static void extractJarVeinDefinitions(Path worldgenRootPath) throws IOException {
        FileSystem zipFileSystem = null;
        try {
            URI sampleUri = WorldGenRegistry.class.getResource("/assets/gregtech/.gtassetsroot").toURI();
            Path worldgenJarRootPath;
            if(sampleUri.getScheme().equals("jar") || sampleUri.getScheme().equals("zip")) {
                zipFileSystem = FileSystems.newFileSystem(sampleUri, Collections.emptyMap());
                worldgenJarRootPath = zipFileSystem.getPath("/assets/gregtech/worldgen");
            } else if(sampleUri.getScheme().equals("file")) {
                worldgenJarRootPath = Paths.get(WorldGenRegistry.class.getResource("/assets/gregtech/worldgen").toURI());
            } else {
                throw new IllegalStateException("Unable to locate absolute path to worldgen root directory: " + sampleUri);
            }
            GTLog.logger.info("Attempting extraction of standard worldgen definitions from {} to {}",
                worldgenJarRootPath, worldgenRootPath);
            List<Path> jarFiles = Files.walk(worldgenJarRootPath)
                .filter(jarFile -> Files.isRegularFile(jarFile))
                .collect(Collectors.toList());
            for(Path jarFile : jarFiles) {
                Path worldgenPath = worldgenRootPath.resolve(worldgenJarRootPath.relativize(jarFile));
                Files.createDirectories(worldgenPath.getParent());
                Files.copy(jarFile, worldgenPath, StandardCopyOption.REPLACE_EXISTING);
            }
            GTLog.logger.info("Extracted {} builtin worldgen definitions into worldgen folder", jarFiles.size());
        } catch (URISyntaxException impossible) {
            //this is impossible, since getResource always returns valid URI
            throw new RuntimeException(impossible);
        } finally {
            if(zipFileSystem != null) {
                //close zip file system to avoid issues
                IOUtils.closeQuietly(zipFileSystem);
            }
        }
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
