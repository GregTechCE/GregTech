package gregtech;

import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.common.blocks.BlockOre;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.*;

public class GTWorldGenerator implements IWorldGenerator {
    private final static Map<Integer, List<OreVeinConfig>> configs = new HashMap<>();

    static {
        List<OreVeinConfig> overworld = new ArrayList<>();
        List<OreVeinConfig> nether = new ArrayList<>();
        List<OreVeinConfig> end = new ArrayList<>();

        overworld.add(new OreVeinConfig(1500, 40, 50, 130, 8, Materials.Lignite, Materials.Lignite, Materials.Lignite, Materials.Coal));
        overworld.add(new OreVeinConfig(1500, 40, 50, 80, 6, Materials.Coal, Materials.Coal, Materials.Coal, Materials.Lignite));
        overworld.add(new OreVeinConfig(1000, 40, 50, 120, 3, Materials.Magnetite, Materials.Magnetite, Materials.Iron, Materials.VanadiumMagnetite));
        overworld.add(new OreVeinConfig(1000, 40, 60, 80, 3, Materials.Magnetite, Materials.Magnetite, Materials.VanadiumMagnetite, Materials.Gold));
        overworld.add(new OreVeinConfig(1000, 32, 10, 40, 4, Materials.BrownLimonite, Materials.YellowLimonite, Materials.BandedIron, Materials.Malachite));
        overworld.add(new OreVeinConfig(1500, 32, 40, 120, 5, Materials.Tin, Materials.Tin, Materials.Cassiterite, Materials.Tin));
        overworld.add(new OreVeinConfig(1500, 32, 80, 120, 4, Materials.Tetrahedrite, Materials.Tetrahedrite, Materials.Copper, Materials.Stibnite));
        overworld.add(new OreVeinConfig(1000, 32, 10, 30, 4, Materials.Chalcopyrite, Materials.Iron, Materials.Pyrite, Materials.Copper));
        overworld.add(new OreVeinConfig(1000, 32, 50, 90, 4, Materials.Bauxite, Materials.Bauxite, Materials.Aluminium, Materials.Ilmenite));
        overworld.add(new OreVeinConfig(1500, 32, 50, 60, 3, Materials.RockSalt, Materials.Salt, Materials.Lepidolite, Materials.Spodumene));
        overworld.add(new OreVeinConfig(1500, 32, 10, 40, 3, Materials.Redstone, Materials.Redstone, Materials.Ruby, Materials.Cinnabar));
        overworld.add(new OreVeinConfig(2000, 24, 10, 40, 3, Materials.Soapstone, Materials.Talc, Materials.Glauconite, Materials.Pentlandite));
        overworld.add(new OreVeinConfig(2000, 24, 10, 40, 3, Materials.Garnierite, Materials.Nickel, Materials.Cobaltite, Materials.Pentlandite));
        overworld.add(new OreVeinConfig(10000, 24, 40, 50, 3, Materials.Cooperite, Materials.Palladium, Materials.Platinum, Materials.Iridium));
        overworld.add(new OreVeinConfig(2000, 24, 10, 40, 3, Materials.Pitchblende, Materials.Pitchblende, Materials.Uraninite, Materials.Uraninite));
        overworld.add(new OreVeinConfig(3000, 24, 20, 30, 3, Materials.Uraninite, Materials.Uraninite, Materials.Uranium, Materials.Uranium));
        overworld.add(new OreVeinConfig(2500, 24, 20, 40, 3, Materials.Bastnasite, Materials.Bastnasite, Materials.Monazite, Materials.Neodymium));
        overworld.add(new OreVeinConfig(10000, 24, 20, 50, 3, Materials.Wulfenite, Materials.Molybdenite, Materials.Molybdenum, Materials.Powellite));
        overworld.add(new OreVeinConfig(5000, 24, 20, 50, 3, Materials.Scheelite, Materials.Scheelite, Materials.Tungstate, Materials.Lithium));
        overworld.add(new OreVeinConfig(1500, 24, 10, 40, 3, Materials.Almandine, Materials.Pyrope, Materials.Sapphire, Materials.CertusQuartz));
        overworld.add(new OreVeinConfig(3000, 24, 20, 30, 3, Materials.Grossular, Materials.Spessartine, Materials.Pyrolusite, Materials.Tantalite));
        overworld.add(new OreVeinConfig(1500, 24, 40, 80, 3, Materials.Quartzite, Materials.Barite, Materials.CertusQuartz, Materials.CertusQuartz));
        overworld.add(new OreVeinConfig(2000, 24, 5, 20, 2, Materials.Graphite, Materials.Graphite, Materials.Diamond, Materials.Coal));
        overworld.add(new OreVeinConfig(1500, 24, 10, 40, 3, Materials.Bentonite, Materials.Magnesite, Materials.Olivine, Materials.Glauconite));
        overworld.add(new OreVeinConfig(1500, 24, 40, 60, 3, Materials.Apatite, Materials.Apatite, Materials.Phosphorus, Materials.Phosphate));
        overworld.add(new OreVeinConfig(2000, 24, 30, 60, 5, Materials.Galena, Materials.Galena, Materials.Silver, Materials.Lead));
        overworld.add(new OreVeinConfig(2000, 24, 20, 50, 5, Materials.Lazurite, Materials.Sodalite, Materials.Lapis, Materials.Calcite));
        overworld.add(new OreVeinConfig(2500, 24, 5, 30, 3, Materials.Beryllium, Materials.Beryllium, Materials.Emerald, Materials.Thorium));
        overworld.add(new OreVeinConfig(2000, 40, 50, 80, 6, Materials.Oilsands, Materials.Oilsands, Materials.Oilsands, Materials.Oilsands));

        nether.add(new OreVeinConfig(213, 40, 50, 120, 3, Materials.Magnetite, Materials.Magnetite, Materials.Iron, Materials.VanadiumMagnetite));
        nether.add(new OreVeinConfig(284, 32, 10, 40, 4, Materials.BrownLimonite, Materials.YellowLimonite, Materials.BandedIron, Materials.Malachite));
        nether.add(new OreVeinConfig(568, 32, 10, 40, 3, Materials.Redstone, Materials.Redstone, Materials.Ruby, Materials.Cinnabar));
        nether.add(new OreVeinConfig(852, 24, 10, 40, 3, Materials.Garnierite, Materials.Nickel, Materials.Cobaltite, Materials.Pentlandite));
        nether.add(new OreVeinConfig(486, 32, 80, 120, 4, Materials.Tetrahedrite, Materials.Tetrahedrite, Materials.Copper, Materials.Stibnite));
        nether.add(new OreVeinConfig(426, 32, 40, 80, 5, Materials.NetherQuartz, Materials.NetherQuartz, Materials.NetherQuartz, Materials.NetherQuartz));
        nether.add(new OreVeinConfig(340, 32, 5, 20, 5, Materials.Sulfur, Materials.Sulfur, Materials.Pyrite, Materials.Sphalerite));
        nether.add(new OreVeinConfig(426, 32, 10, 30, 4, Materials.Chalcopyrite, Materials.Iron, Materials.Pyrite, Materials.Copper));

        end.add(new OreVeinConfig(1296, 40, 10, 60, 5, Materials.Naquadah, Materials.Naquadah, Materials.Naquadah, Materials.NaquadahEnriched));
        end.add(new OreVeinConfig(259, 32, 40, 120, 5, Materials.Tin, Materials.Tin, Materials.Cassiterite, Materials.Tin));
        end.add(new OreVeinConfig(324, 24, 10, 40, 3, Materials.Garnierite, Materials.Nickel, Materials.Cobaltite, Materials.Pentlandite));
        end.add(new OreVeinConfig(2592, 24, 40, 50, 3, Materials.Cooperite, Materials.Palladium, Materials.Platinum, Materials.Iridium));
        end.add(new OreVeinConfig(2592, 24, 20, 50, 3, Materials.Wulfenite, Materials.Molybdenite, Materials.Molybdenum, Materials.Powellite));
        end.add(new OreVeinConfig(1296, 24, 20, 50, 3, Materials.Scheelite, Materials.Scheelite, Materials.Tungstate, Materials.Lithium));
        end.add(new OreVeinConfig(648, 24, 20, 30, 3, Materials.Grossular, Materials.Spessartine, Materials.Pyrolusite, Materials.Tantalite));
        end.add(new OreVeinConfig(216, 24, 10, 40, 3, Materials.Bentonite, Materials.Magnesite, Materials.Olivine, Materials.Glauconite));
        end.add(new OreVeinConfig(324, 24, 20, 50, 5, Materials.Lazurite, Materials.Sodalite, Materials.Lapis, Materials.Calcite));
        end.add(new OreVeinConfig(432, 24, 5, 30, 3, Materials.Beryllium, Materials.Beryllium, Materials.Emerald, Materials.Thorium));

        configs.put(0, overworld);
        configs.put(-1, nether);
        configs.put(1, end);
        /*
        try {
            Field field = FMLLog.class.getField("log");
            field.setAccessible(true);

            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

            field.set(null, new MyLogger((ExtendedLogger) FMLLog.log));
        } catch (Exception ignored) {
        }
        */
    }
    /*
    public static class MyLogger extends ExtendedLoggerWrapper {
        MyLogger(ExtendedLogger logger) {
            super(logger, "", null);
        }

        @Override
        public void warn(String message, Object p0, Object p1, Object p2, Object p3) {
            super.warn(message, p0, p1, p2, p3);
            if (message.contains("causing cascading worldgen lag")) {
                System.out.println();
            }
        }
    }
    */
    public GTWorldGenerator() {
        GameRegistry.registerWorldGenerator(this, 0x3fffffff);
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        generateOreVeins(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
        generateSmallOres(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
    }

    private static void generateOreVeins(Random chunkRandom, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        List<OreVeinConfig> oreVeinConfigs = configs.get(world.provider.getDimensionType().getId());
        if (oreVeinConfigs == null) return;

        //convert to positive coordinates
        final long shift = 0x80000000L;
        long xPositive = (chunkX << 4) + shift;
        long zPositive = (chunkZ << 4) + shift;

        Random worldRandom = new Random(world.getSeed());

        WeightedMaterials wm = new WeightedMaterials(
            new WeightedMaterial(Materials.Diamond, 1),
            new WeightedMaterial(Materials.Graphite, 3),
            new WeightedMaterial(Materials.Coal, 6)
        );


        for (OreVeinConfig config : oreVeinConfigs) {
            Random veinRandom = new Random(worldRandom.nextLong());

            //int xOffset = veinRandom.nextInt(config.distance);
            //int zOffset = veinRandom.nextInt(config.distance);
            int xOffset = 0;
            int zOffset = 0;
            long xGrid = getGridCoordinate(xPositive, xOffset, config.distance);
            long zGrid = getGridCoordinate(zPositive, zOffset, config.distance);

            //ignore chunks on the grid
            if (xGrid != getGridCoordinate(xPositive + 15, xOffset, config.distance)
                || zGrid != getGridCoordinate(zPositive + 15, zOffset, config.distance)) continue;

            Random gridRandom = new Random(xGrid * veinRandom.nextInt() + zGrid * veinRandom.nextInt() ^ veinRandom.nextLong());
            double spawnCoef = 0.9;

            int xVein = (int) ((spawnCoef * (gridRandom.nextDouble() - 0.5) + 0.5) * config.distance + xGrid - shift);
            int zVein = (int) ((spawnCoef * (gridRandom.nextDouble() - 0.5) + 0.5) * config.distance + zGrid - shift);

            //Random random = nearestCenter.getRandom(veinConfigSeed); //todo random size
            int xMax = xVein + config.radius, xMin = xVein - config.radius, zMax = zVein + config.radius, zMin = zVein - config.radius;

            for (int x = xMin; x <= xMax; x++) {
                if (!inChunk(x, chunkX)) continue;
                for (int z = zMin; z <= zMax; z++) {
                    if (!inChunk(z, chunkZ)) continue;
                    for (int y = 30; y < 35; y++) {
                        if (chunkRandom.nextInt(10) > config.d) continue;
                        int i = chunkRandom.nextInt(config.materials.length);
                        IBlockState blockState = getNormalOreBlockStateFor(config.materials[i]);
                        world.setBlockState(new BlockPos(x, y, z), blockState, 18);
                    }
                }
            }
        }
    }

    private static boolean inChunk(int coordinate, int chunkCoordinate) {
        return coordinate >> 4 == chunkCoordinate;
    }

    private static IBlockState getNormalOreBlockStateFor(DustMaterial material) {
        return MetaBlocks.ORES.get(material).getDefaultState().withProperty(BlockOre.SMALL, false);
    }

    private static long getGridCoordinate(long coordinate, int offset, int distance) {
        return (coordinate - offset) / distance * distance + offset;
    }

    private static void generateSmallOres(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        //todo
    }

    private static class WeightedMaterial extends WeightedRandom.Item {
        private final DustMaterial material;

        public WeightedMaterial(DustMaterial material, int weight) {
            super(weight);
            this.material = material;
        }

        public DustMaterial getMaterial() {
            return material;
        }
    }

    private static class WeightedMaterials {
        private List<WeightedMaterial> data;
        private int totalWeight;

        private WeightedMaterials(WeightedMaterial... weightedMaterials) {
            data = Arrays.asList(weightedMaterials);
            totalWeight = data.stream().mapToInt(wm -> wm.itemWeight).sum();
        }

        public WeightedMaterial getRandomMaterial(Random random) {
            return WeightedRandom.getRandomItem(random, data, totalWeight);
        }
    }

    public interface OreBody {
        void generate(World world, Random gridRandom, OreVeinConfig config, int xChunk, int zChunk);
    }

    public static class ClusterOreBody implements OreBody {
        private final int minCount;
        private final int maxCount;
        private final int minSize;
        private final int maxSize;

        public ClusterOreBody(int minCount, int maxCount, int minSize, int maxSize) {
            this.minCount = minCount;
            this.maxCount = maxCount;
            this.minSize = minSize;
            this.maxSize = maxSize;
        }

        @Override
        public void generate(World world, Random gridRandom, OreVeinConfig config, int xChunk, int zChunk) {
            int count = gridRandom.nextInt(maxCount - minCount + 1) + minCount;
            double spawnCoef = 0.9;

//            int xVein = (int) ((spawnCoef * (gridRandom.nextDouble() - 0.5) + 0.5) * config.distance + xGrid - shift);
//            int zVein = (int) ((spawnCoef * (gridRandom.nextDouble() - 0.5) + 0.5) * config.distance + zGrid - shift);
            for (int i = 0; i < count; i++) {

            }
        }
    }

    public static class LayerOreBody implements OreBody {
        private final int minCount;
        private final int maxCount;
        private final int minSize;
        private final int maxSize;

        public LayerOreBody(int cubeSize, int minCount, int maxCount, int minSize, int maxSize) {
            this.minCount = minCount;
            this.maxCount = maxCount;
            this.minSize = minSize;
            this.maxSize = maxSize;
        }

        @Override
        public void generate(World world, Random gridRandom, OreVeinConfig config, int xChunk, int zChunk) {
            int count = gridRandom.nextInt(maxCount - minCount + 1) + minCount;
            for (int i = 0; i < count; i++) {

            }
        }
    }

    private static class OreVeinConfig {
        public int d;
        public int distance;
        public int radius;
        public int minY;
        public int maxY;
        public DustMaterial[] materials;

        public OreVeinConfig(int distance, int radius, int minY, int maxY, int d, DustMaterial... materials) {
            this(distance, radius, materials);
            this.minY = minY;
            this.maxY = maxY;
            this.d = d;
        }

        public OreVeinConfig(int distance, int radius, DustMaterial... materials) {
            //this.distance = distance;
            this.distance = 500;
            this.radius = radius;
            this.materials = materials;
            for (DustMaterial material : materials) {
                getNormalOreBlockStateFor(material);
            }
        }
    }
}