package gregtech.loaders.postload;

import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.ore.StoneTypes;
import gregtech.api.util.GTLog;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.worldgen.*;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.io.File;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static gregtech.loaders.postload.WorldgenLoader.DepositConfig.DEPOSITS_CATEGORY_PREFIX;

public class WorldgenLoader {
    public static final Map<ShapeType, Supplier<IShapeGenerator>> ShapeGenSuppliers = new HashMap<>();

    public static List<DepositConfig> deposits = new ArrayList<>();
    public static List<SmallOreConfig> smallOres = new ArrayList<>();

    static {
        ShapeGenSuppliers.put(ShapeType.ELLIPSOID, EllipsoidGenerator::new);
        //ShapeGenSuppliers.put("ellipsoid", EllipsoidGenerator::new);

        deposits.add(new DepositConfig("red_granite_small", 200, new EllipsoidGenerator(5, 10), true));
        deposits.add(new DepositConfig("red_granite_medium", 400, new EllipsoidGenerator(10, 20), true));
        deposits.add(new DepositConfig("red_granite_big", 800, new EllipsoidGenerator(20, 40), true));
    }

    public static void init() {
        GTLog.logger.info("Loading world generators");
        GameRegistry.registerWorldGenerator(new GTWorldGenerator(), 0x3fffffff);
        Configuration config = new Configuration(new File(Loader.instance().getConfigDir(), "GregTech/WorldGeneration.cfg"));
        loadConfig(config);
        config.save();
    }

    private static void loadConfig(Configuration config) {
        //default deposits
        for (DepositConfig deposit : deposits) {
            deposit.loadFromConfig(config);
        }
        Set<String> defaultCategories = deposits.stream().map(cd -> cd.name).collect(Collectors.toSet());
        //custom deposits
        for (ConfigCategory category : config.getCategory(DEPOSITS_CATEGORY_PREFIX).getChildren()) {
            if (defaultCategories.contains(category.getName())) continue;
            deposits.add(new DepositConfig(category.getName(), config));
        }

        deposits.sort(Comparator.comparing(o -> o.category));
        smallOres.sort(Comparator.comparing(o -> o.category));
    }

    public static class DepositConfig {
        public static final String DEPOSITS_CATEGORY_PREFIX = "deposits";

        public final String category;
        public final String name;
        public int distance;
        public boolean enabled;
        public IShapeGenerator generator;

        private DepositConfig(String name) {
            this.name = name;
            this.category = DEPOSITS_CATEGORY_PREFIX + "." + name;
        }

        public DepositConfig(String name, Configuration config) {
            this(name);
            loadFromConfig(config);
        }

        public DepositConfig(String name, int distance, IShapeGenerator generator, boolean enabled) {
            this(name);
            this.distance = distance;
            this.enabled = enabled;
            this.generator = generator;
        }

        public void loadFromConfig(Configuration config) {
            distance = config.get(category, "distance", distance).getInt();
            enabled = config.get(category, "enabled", enabled).getBoolean();
            String shapeCategory = category + ".shape";
            ShapeType shapeType = ShapeType.valueOf(config.get(shapeCategory, "type", generator.getShapeType().name()).getString());
            if (shapeType != generator.getShapeType())
                generator = ShapeGenSuppliers.get(shapeType).get();
            generator.loadFromConfig(config, shapeCategory);
        }

        public Vec2i getNearest(ChunkPos chunkPos, Random random) {
            final long shift = 0x80000000L;
            long x = chunkPos.getXStart() + shift;
            long z = chunkPos.getZStart() + shift;

            long gridX = getGridCoordinate(x);
            long gridZ = getGridCoordinate(z);

            if (gridX != getGridCoordinate(chunkPos.getXEnd()) || gridZ != getGridCoordinate(chunkPos.getZEnd())) return null;
            Random gridRandom = new Random(gridX * random.nextInt() + gridZ * random.nextInt() ^ random.nextLong());

            double ratio = 0.5;
            double offsetX = getRndCoordInCell(gridRandom, ratio);
            double offsetZ = getRndCoordInCell(gridRandom, ratio);

            int depositX = (int) (offsetX * distance + gridX - shift);
            int depositZ = (int) (offsetZ * distance + gridZ - shift);

            return new Vec2i(depositX, depositZ);
        }

        private long getGridCoordinate(long coordinate) {
            return coordinate - coordinate % distance;
        }

        //random number from [(1 - ratio) / 2 .. (1 + ratio) / 2]
        private double getRndCoordInCell(Random random, double ratio) {
            return ratio * (random.nextDouble() - 0.5) + 0.5;
        }

        public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        }
    }

    private static class SmallOreConfig {
        public final static String SMALL_ORE_CATEGORY_PREFIX = "small_ores";

        public final String category;
        public int minY;
        public int maxY;
        public DustMaterial ore;

        private SmallOreConfig(String category) {
            this.category = category;
        }

        public SmallOreConfig(String name, int minY, int maxY, DustMaterial ore) {
            this(SMALL_ORE_CATEGORY_PREFIX + "." + name);
            this.minY = minY;
            this.maxY = maxY;
            this.ore = ore;
        }

        public void loadFromConfig(Configuration config) {

        }
    }

    private static void loadConfigOld() {
        String[] overworld = {"overworld"};
        String[] allBiome = {"all"};
        String[] allDims = {"overworld", "the_nether", "the_end", "?moon", "?mars", "?asteroid"};
        String[] noAst = {"overworld", "the_nether", "the_end", "?moon", "?mars"};
        String[] noEndMoon = {"overworld", "the_nether", "?mars", "?asteroid"};
        String[] nether = {"the_nether"};
        String[] none = new String[0];

        new GTWorldGenStone("tiny.blackgranite",   true, 0, 120,  4,  8,  48, 1, MetaBlocks.BLACK_GRANITE, false, overworld, allBiome);
        new GTWorldGenStone("small.blackgranite",  true, 0, 120,  8, 16,  96, 1, MetaBlocks.BLACK_GRANITE, false, overworld, allBiome);
        new GTWorldGenStone("medium.blackgranite", true, 0, 120, 16, 24, 144, 1, MetaBlocks.BLACK_GRANITE, false, overworld, allBiome);
        new GTWorldGenStone("large.blackgranite",  true, 0, 120, 24, 32, 192, 1, MetaBlocks.BLACK_GRANITE, false, overworld, allBiome);
        new GTWorldGenStone("huge.blackgranite",   true, 0, 120, 32, 40, 240, 1, MetaBlocks.BLACK_GRANITE, false, overworld, allBiome);

        new GTWorldGenStone("tiny.redgranite",   true, 0, 120,  4,  8,  48, 1, MetaBlocks.RED_GRANITE, false, overworld, allBiome);
        new GTWorldGenStone("small.redgranite",  true, 0, 120,  8, 16,  96, 1, MetaBlocks.RED_GRANITE, false, overworld, allBiome);
        new GTWorldGenStone("medium.redgranite", true, 0, 120, 16, 24, 144, 1, MetaBlocks.RED_GRANITE, false, overworld, allBiome);
        new GTWorldGenStone("large.redgranite",  true, 0, 120, 24, 32, 192, 1, MetaBlocks.RED_GRANITE, false, overworld, allBiome);
        new GTWorldGenStone("huge.redgranite",   true, 0, 120, 32, 40, 240, 1, MetaBlocks.RED_GRANITE, false, overworld, allBiome);

        new GTWorldGenStone("tiny.basalt",   true, 0, 120,  4,  8,  48, 1, MetaBlocks.BASALT, false, overworld, allBiome);
        new GTWorldGenStone("small.basalt",  true, 0, 120,  8, 16,  96, 1, MetaBlocks.BASALT, false, overworld, allBiome);
        new GTWorldGenStone("medium.basalt", true, 0, 120, 16, 24, 144, 1, MetaBlocks.BASALT, false, overworld, allBiome);
        new GTWorldGenStone("large.basalt",  true, 0, 120, 24, 32, 192, 1, MetaBlocks.BASALT, false, overworld, allBiome);
        new GTWorldGenStone("huge.basalt",   true, 0, 120, 32, 40, 240, 1, MetaBlocks.BASALT, false, overworld, allBiome);

        new GTWorldGenStone("tiny.marble",   true, 0, 120,  4,  8,  48, 1, MetaBlocks.MARBLE, false, overworld, allBiome);
        new GTWorldGenStone("small.marble",  true, 0, 120,  8, 16,  96, 1, MetaBlocks.MARBLE, false, overworld, allBiome);
        new GTWorldGenStone("medium.marble", true, 0, 120, 16, 24, 144, 1, MetaBlocks.MARBLE, false, overworld, allBiome);
        new GTWorldGenStone("large.marble",  true, 0, 120, 24, 32, 192, 1, MetaBlocks.MARBLE, false, overworld, allBiome);
        new GTWorldGenStone("huge.marble",   true, 0, 120, 32, 40, 240, 1, MetaBlocks.MARBLE, false, overworld, allBiome);

        new GTWorldGenOreSmall("copper", true, 60, 120, 32, Materials.Copper, noAst, allBiome);
        new GTWorldGenOreSmall("tin", true, 60, 120, 32, Materials.Tin, allDims, allBiome);
        new GTWorldGenOreSmall("bismuth", true, 80, 120, 8, Materials.Bismuth, new String[]{"overworld", "the_nether", "?moon", "?mars"}, allBiome);
        new GTWorldGenOreSmall("coal", true, 60, 100, 24, Materials.Coal, overworld, allBiome);
        new GTWorldGenOreSmall("iron", true, 40, 80, 16, Materials.Iron, noAst, allBiome);
        new GTWorldGenOreSmall("lead", true, 40, 80, 16, Materials.Lead, allDims, allBiome);
        new GTWorldGenOreSmall("zinc", true, 30, 60, 12, Materials.Zinc, noAst, allBiome);
        new GTWorldGenOreSmall("gold", true, 20, 40, 8, Materials.Gold, allDims, allBiome);
        new GTWorldGenOreSmall("lapis", true, 20, 40, 4, Materials.Lapis, new String[]{"overworld", "?moon", "?asteroid"}, allBiome);
        new GTWorldGenOreSmall("diamond", true, 5, 10, 2, Materials.Diamond, new String[]{"overworld", "the_nether", "?moon", "?mars", "?asteroid"}, allBiome);
        new GTWorldGenOreSmall("emerald", true, 5, 250, 1, Materials.Emerald, noEndMoon, allBiome);
        new GTWorldGenOreSmall("ruby", true, 5, 250, 1, Materials.Ruby, noEndMoon, allBiome);
        new GTWorldGenOreSmall("sapphire", true, 5, 250, 1, Materials.Sapphire, noEndMoon, allBiome);
        new GTWorldGenOreSmall("greensapphire", true, 5, 250, 1, Materials.GreenSapphire, noEndMoon, allBiome);
        new GTWorldGenOreSmall("olivine", true, 5, 250, 1, Materials.Olivine, noEndMoon, allBiome);
        new GTWorldGenOreSmall("topaz", true, 5, 250, 1, Materials.Topaz, noEndMoon, allBiome);
        new GTWorldGenOreSmall("tanzanite", true, 5, 250, 1, Materials.Tanzanite, noEndMoon, allBiome);
        new GTWorldGenOreSmall("amethyst", true, 5, 250, 1, Materials.Amethyst, noEndMoon, allBiome);
        new GTWorldGenOreSmall("opal", true, 5, 250, 1, Materials.Opal, noEndMoon, allBiome);
        new GTWorldGenOreSmall("jasper", true, 5, 250, 1, Materials.Jasper, noEndMoon, allBiome);
        new GTWorldGenOreSmall("bluetopaz", true, 5, 250, 1, Materials.BlueTopaz, noEndMoon, allBiome);
        //new GTWorldGen_OreSmall("amber", true, 5, 250, 1, Materials.Amber, noEndMoon, allBiome); TODO Missing Amber
        //new GTWorldGen_OreSmall("foolsruby", true, 5, 250, 1, Materials.FoolsRuby, noEndMoon, allBiome); TODO Missing Fool's Ruby
        new GTWorldGenOreSmall("garnetred", true, 5, 250, 1, Materials.GarnetRed, noEndMoon, allBiome);
        new GTWorldGenOreSmall("garnetyellow", true, 5, 250, 1, Materials.GarnetYellow, noEndMoon, allBiome);
        new GTWorldGenOreSmall("redstone", true, 5, 20, 8, Materials.Redstone, new String[]{"overworld", "the_nether", "?moon", "?mars", "?asteroid"}, allBiome);
        new GTWorldGenOreSmall("platinum", true, 20, 40, 8, Materials.Platinum, new String[]{"the_end", "?mars", "?asteroid"}, allBiome);
        new GTWorldGenOreSmall("iridium", true, 20, 40, 8, Materials.Iridium, new String[]{"the_end", "?mars", "?asteroid"}, allBiome);
        new GTWorldGenOreSmall("netherquartz", true, 30, 120, 64, Materials.NetherQuartz, nether, allBiome);
        new GTWorldGenOreSmall("saltpeter", true, 10, 60, 8, Materials.Saltpeter, nether, allBiome);
        new GTWorldGenOreSmall("sulfur_n", true, 10, 60, 32, Materials.Sulfur, nether, allBiome);
        new GTWorldGenOreSmall("sulfur_o", true, 5, 15, 8, Materials.Sulfur, overworld, allBiome);

        new GTWorldGenAsteroid("endstone", true, 50, 200, 6, 30, 300, 1, StoneTypes.ENDSTONE, new String[]{"the_end"}, allBiome, new String[]{"naquadah", "cassiterite", "nickel", "platinum", "molybdenum", "tungstate", "manganese", "olivine", "lapis", "beryllium"});
        new GTWorldGenAsteroid("blackgranite", true, 50, 200, 12, 50, 150, 1, MetaBlocks.BLACK_GRANITE, new String[]{"?asteroid"}, allBiome, new String[]{"naquadah", "gold", "bauxite", "platinum", "pitchblende", "uranium", "monazite", "manganese", "quartz", "galena", "beryllium"});
        new GTWorldGenAsteroid("redgranite", true, 50, 200, 12, 50, 150, 1, MetaBlocks.RED_GRANITE, new String[]{"?asteroid"}, allBiome, new String[]{"cassiterite", "tetrahedrite", "nickel", "redstone", "monazite", "molybdenum", "tungstate", "sapphire", "diamond", "olivine", "lapis"});

        new GTWorldGenOreVein("naquadah", true, 10, 10, 60, 32, 7, 5, Materials.Naquadah, Materials.Naquadah, Materials.Naquadah, Materials.NaquadahEnriched, new String[]{"the_end", "?mars"}, allBiome);
        new GTWorldGenOreVein("lignite", true, 160, 50, 130, 32, 7, 8, Materials.Lignite, Materials.Lignite, Materials.Lignite, Materials.Coal, overworld, allBiome);
        new GTWorldGenOreVein("coal", true, 80, 50, 80, 32, 7, 6, Materials.Coal, Materials.Coal, Materials.Coal, Materials.Lignite, overworld, allBiome);
        new GTWorldGenOreVein("magnetite", true, 160, 50, 120, 32, 7, 3, Materials.Magnetite, Materials.Magnetite, Materials.Iron, Materials.VanadiumMagnetite, new String[]{"overworld", "the_end", "?moon", "?mars"}, allBiome);
        new GTWorldGenOreVein("gold", true, 160, 60, 80, 32, 7, 3, Materials.Magnetite, Materials.Magnetite, Materials.VanadiumMagnetite, Materials.Gold, new String[]{"overworld", "?moon", "?mars"}, allBiome);
        new GTWorldGenOreVein("iron", true, 120, 10, 40, 24, 7, 4, Materials.BrownLimonite, Materials.YellowLimonite, Materials.BandedIron, Materials.Malachite, new String[]{"overworld", "the_nether", "?moon", "?mars"}, allBiome);
        new GTWorldGenOreVein("cassiterite", true, 50, 40, 120, 24, 7, 5, Materials.Tin, Materials.Tin, Materials.Cassiterite, Materials.Tin, new String[]{"overworld", "the_end", "?moon", "?mars"}, allBiome);
        new GTWorldGenOreVein("tetrahedrite", true, 70, 80, 120, 24, 7, 4, Materials.Tetrahedrite, Materials.Tetrahedrite, Materials.Copper, Materials.Stibnite, new String[]{"overworld", "the_nether", "?moon", "?mars"}, allBiome);
        new GTWorldGenOreVein("netherquratz", true, 80, 40, 80, 24, 7, 5, Materials.NetherQuartz, Materials.NetherQuartz, Materials.NetherQuartz, Materials.NetherQuartz, nether, allBiome);
        new GTWorldGenOreVein("sulfur", true, 100, 5, 20, 24, 7, 5, Materials.Sulfur, Materials.Sulfur, Materials.Pyrite, Materials.Sphalerite, new String[]{"the_nether", "?mars"}, allBiome);
        new GTWorldGenOreVein("copper", true, 80, 10, 30, 24, 7, 4, Materials.Chalcopyrite, Materials.Iron, Materials.Pyrite, Materials.Copper, new String[]{"overworld", "the_nether", "?moon", "?mars"}, allBiome);
        new GTWorldGenOreVein("bauxite", true, 80, 50, 90, 24, 7, 4, Materials.Bauxite, Materials.Bauxite, Materials.Aluminium, Materials.Ilmenite, new String[]{"overworld", "?moon", "?mars"}, allBiome);
        new GTWorldGenOreVein("salts", true, 50, 50, 60, 24, 7, 3, Materials.RockSalt, Materials.Salt, Materials.Lepidolite, Materials.Spodumene, new String[]{"overworld", "?moon"}, allBiome);
        new GTWorldGenOreVein("redstone", true, 60, 10, 40, 24, 7, 3, Materials.Redstone, Materials.Redstone, Materials.Ruby, Materials.Cinnabar, new String[]{"overworld", "the_nether", "?moon", "?mars"}, allBiome);
        new GTWorldGenOreVein("soapstone", true, 40, 10, 40, 16, 7, 3, Materials.Soapstone, Materials.Talc, Materials.Glauconite, Materials.Pentlandite, new String[]{"overworld", "?moon", "?mars"}, allBiome);
        new GTWorldGenOreVein("nickel", true, 40, 10, 40, 16, 7, 3, Materials.Garnierite, Materials.Nickel, Materials.Cobaltite, Materials.Pentlandite, new String[]{"overworld", "the_nether", "the_end", "?moon", "?mars"}, allBiome);
        new GTWorldGenOreVein("platinum", true, 5, 40, 50, 16, 7, 3, Materials.Cooperite, Materials.Palladium, Materials.Platinum, Materials.Iridium, new String[]{"overworld", "the_end", "?mars"}, allBiome);
        new GTWorldGenOreVein("pitchblende", true, 40, 10, 40, 16, 7, 3, Materials.Pitchblende, Materials.Pitchblende, Materials.Uraninite, Materials.Uraninite, new String[]{"overworld", "?moon", "?mars"}, allBiome);
        new GTWorldGenOreVein("uranium", true, 20, 20, 30, 16, 7, 3, Materials.Uraninite, Materials.Uraninite, Materials.Uranium, Materials.Uranium,  new String[]{"overworld", "?moon", "?mars"}, allBiome);
        new GTWorldGenOreVein("monazite", true, 30, 20, 40, 16, 7, 3, Materials.Bastnasite, Materials.Bastnasite, Materials.Monazite, Materials.Neodymium, new String[]{"overworld", "?moon", "?mars"}, allBiome);
        new GTWorldGenOreVein("molybdenum", true, 5, 20, 50, 16, 7, 3, Materials.Wulfenite, Materials.Molybdenite, Materials.Molybdenum, Materials.Powellite, new String[]{"overworld", "the_end", "?moon", "?mars"}, allBiome);
        new GTWorldGenOreVein("tungstate", true, 10, 20, 50, 16, 7, 3, Materials.Scheelite, Materials.Scheelite, Materials.Tungstate, Materials.Lithium, new String[]{"overworld", "the_end", "?moon", "?mars"}, allBiome);
        new GTWorldGenOreVein("sapphire", true, 60, 10, 40, 16, 7, 3, Materials.Almandine, Materials.Pyrope, Materials.Sapphire, Materials.GreenSapphire, new String[]{"overworld", "?moon", "?mars"}, allBiome);
        new GTWorldGenOreVein("manganese", true, 20, 20, 30, 16, 7, 3, Materials.Grossular, Materials.Spessartine, Materials.Pyrolusite, Materials.Tantalite, new String[]{"overworld", "the_end", "?moon"}, allBiome);
        new GTWorldGenOreVein("quartz", true, 60, 40, 80, 16, 7, 3, Materials.Quartzite, Materials.Barite, Materials.CertusQuartz, Materials.CertusQuartz, new String[]{"overworld", "?moon", "?mars"}, allBiome);
        new GTWorldGenOreVein("diamond", true, 40, 5, 20, 16, 7, 2, Materials.Graphite, Materials.Graphite, Materials.Diamond, Materials.Coal, new String[]{"overworld", "?moon", "?mars"}, allBiome);
        new GTWorldGenOreVein("olivine", true, 60, 10, 40, 16, 7, 3, Materials.Bentonite, Materials.Magnesite, Materials.Olivine, Materials.Glauconite, new String[]{"overworld", "the_end", "?moon", "?mars"}, allBiome);
        new GTWorldGenOreVein("galena", true, 40, 30, 60, 16, 7, 5, Materials.Galena, Materials.Galena, Materials.Silver, Materials.Lead, new String[]{"overworld", "?moon", "?mars"}, allBiome);
        new GTWorldGenOreVein("lapis", true, 40, 20, 50, 16, 7, 5, Materials.Lazurite, Materials.Sodalite, Materials.Lapis, Materials.Calcite, new String[]{"overworld", "the_end", "?moon", "?mars"}, allBiome);
        new GTWorldGenOreVein("beryllium", true, 30, 5, 30, 16, 7, 3, Materials.Beryllium, Materials.Beryllium, Materials.Emerald, Materials.Thorium, new String[]{"overworld", "the_end", "?moon", "?mars"}, allBiome);
        new GTWorldGenOreVein("oilsand", true, 80, 50, 80, 32, 7, 6, Materials.Oilsands, Materials.Oilsands, Materials.Oilsands, Materials.Oilsands, overworld, allBiome);
        new GTWorldGenOreVein("apatite", true, 60, 40, 60, 16, 7, 3, Materials.Apatite, Materials.Apatite, Materials.Phosphor, Materials.Phosphate, overworld, allBiome);//TODO Missing Pyrochlore.
    }
}