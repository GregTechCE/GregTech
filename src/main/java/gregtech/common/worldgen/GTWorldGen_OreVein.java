package gregtech.common.worldgen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import gregtech.api.GTValues;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.util.GTWorldGen;
import gregtech.api.util.IWeighted;
import gregtech.api.util.WeightedList;
import gregtech.api.util.WeightedList.WeightedWrapperList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;

public class GTWorldGen_OreVein extends GTWorldGen implements IWeighted {

    public static final List<GTWorldGen_OreVein> OREVEINS = new ArrayList<>();
    public static final ConcurrentHashMap<World, List<GTWorldGen_OreVein>> dimWiseOreVeinList = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<World, Integer> dimWiseOreSizeList = new ConcurrentHashMap<>();

    private final List<String> asteroidWhiteList = new ArrayList<>();
    public final int weight, minY, maxY, size, thickness, density;
    public final WeightedWrapperList<DustMaterial> orePrimaries, oreSecondaries, oreBetweens, oreSporadics;

    public static Optional<GTWorldGen_OreVein> getRandomOreVein(Random random, World world, Biome biome) {
        WeightedList<GTWorldGen_OreVein> list = new WeightedList<>();
        getOreGenList(world, dimWiseOreVeinList, o -> o.isGenerationAllowed(world)).stream().filter(o -> o.isGenerationAllowed(biome)).forEach(list::add);
        return Optional.ofNullable(list.getRandomObject(random));
    }

    public static Optional<GTWorldGen_OreVein> getRandomOreVein(Random random, World world, GTWorldGen_Asteroid asteroid) {
        return Optional.ofNullable(new WeightedList<GTWorldGen_OreVein>(getOreGenList(world, asteroid.dimWiseOreVeinList, o -> o.asteroidWhiteList.contains(asteroid.name))).getRandomObject(random));
    }

    public static int getMaxOreVeinSize(World world) {
        return dimWiseOreSizeList.computeIfAbsent(world,
                s -> getOreGenList(world, dimWiseOreVeinList, o -> o.isGenerationAllowed(world)).stream().reduce((o1, o2) -> o1.size > o2.size ? o1 : o2).map(o -> o.size).orElse(0));
    }

    public static List<GTWorldGen_OreVein> getOreGenList(World world, Map<World, List<GTWorldGen_OreVein>> mapping, Predicate<GTWorldGen_OreVein> filter) {
        return mapping.computeIfAbsent(world,
                s -> OREVEINS.stream()
                .filter(filter::test)
                .collect(Collectors.toList()));
    }

    /**
     * Use {@linkplain GTWorldGen_OreVein#generate(Random, int, int, int, int, World, Biome, IChunkGenerator, IChunkProvider) the optimized algorithm}
     * to avoid cascading worldgen
     */
    @Override
    @Deprecated
    public void generate(Random random, int chunkX, int chunkZ, World world, Biome biome, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {}



    /**
     * @param name              Name of the ore vein
     * @param enabled           Set true to enable this ore vein
     * @param weight            Random weight of the ore vein; Must >= 0
     * @param minY              Minimum height the ore vein will generate
     * @param maxY              Maximum height the ore vein will generate; Must > minY
     * @param size              Size of the ore vein; Must > -8
     * @param thickness         Thickness of the ore vein; Must >= 5
     * @param density           Density of the ores in the ore vein; Must > 0
     * @param orePrimary        Ores generated in the upper layer
     * @param oreSecondary      Ores generated in the lower layer
     * @param oreBetween        Ores generated between the upper and lower layer
     * @param oreSporadic       Ores generated sporadically
     * @param asteroidWhiteList Name of the asteroid generators
     */
    public GTWorldGen_OreVein(String name, boolean enabled, int weight, int minY, int maxY, int size, int thickness, int density,
            DustMaterial orePrimary, DustMaterial oreSecondary, DustMaterial oreBetween, DustMaterial oreSporadic,
            String[] dimWhiteList, String[] biomeWhiteList, String[] asteroidWhiteList) {
        this(name, enabled, weight, minY, maxY, size, thickness, density,
                new WeightedWrapperList<DustMaterial>().add(orePrimary, 100),
                new WeightedWrapperList<DustMaterial>().add(oreSecondary, 100),
                new WeightedWrapperList<DustMaterial>().add(oreBetween, 100),
                new WeightedWrapperList<DustMaterial>().add(oreSporadic, 100),
                dimWhiteList, biomeWhiteList, asteroidWhiteList);
    }

    /**
     * @param name              Name of the ore vein
     * @param enabled           Set true to enable this ore vein
     * @param weight            Random weight of the ore vein; Must >= 0
     * @param minY              Minimum height the ore vein will generate
     * @param maxY              Maximum height the ore vein will generate; Must > minY
     * @param size              Size of the ore vein; Must > -8
     * @param thickness         Thickness of the ore vein; Must >= 5
     * @param density           Density of the ores in the ore vein; Must > 0
     * @param orePrimaries      Ores generated in the upper layer
     * @param oreSecondaries    Ores generated in the lower layer
     * @param oreBetweens       Ores generated between the upper and lower layer
     * @param oreSporadics      Ores generated sporadically
     * @param asteroidWhiteList Name of the asteroid generators
     */
    public GTWorldGen_OreVein(String name, boolean enabled, int weight, int minY, int maxY, int size, int thickness, int density,
            WeightedWrapperList<DustMaterial> orePrimaries,
            WeightedWrapperList<DustMaterial> oreSecondaries,
            WeightedWrapperList<DustMaterial> oreBetweens,
            WeightedWrapperList<DustMaterial> oreSporadics,
            String[] dimWhiteList, String[] biomeWhiteList, String[] asteroidWhiteList) {
        super(name, enabled, 0, OREVEINS, dimWhiteList, biomeWhiteList);
        this.weight = weight;
        this.minY = minY;
        this.maxY = maxY;
        this.size = size;
        this.thickness = thickness;
        this.density = density;
        this.orePrimaries = orePrimaries;
        this.oreSecondaries = oreSecondaries;
        this.oreBetweens = oreBetweens;
        this.oreSporadics = oreSporadics;
        this.asteroidWhiteList.addAll(Arrays.asList(asteroidWhiteList));
    }

    private int getDensity(int a, int b, int x) {
        return Math.max(1, Math.max(Math.abs(a - x), Math.abs(b - x)) / this.density);
    }

    private int getRandomSize(Random random) {
        return Math.max(-8, (int) ((random.nextGaussian() / 10 + 0.5) * this.size));
    }

    private void generateOre(World world, BlockPos pos, int x0, int x1, int z0, int z1, WeightedWrapperList<DustMaterial> ores, Random random) {
        if (pos.getY() <= 0) return;
        if (random.nextInt(getDensity(x0, x1, pos.getX())) == 0 || random.nextInt(getDensity(z0, z1, pos.getZ())) == 0)
            generateOreBlock(world, pos, ores.fetchRandomObject(random), false, false);
    }

    public void generate(Random random, int chunkX, int chunkZ, int centerX, int centerZ, World world, Biome biome, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        int level = this.minY + random.nextInt(this.maxY - this.minY - 5);
        int minX = (centerX << 4) - getRandomSize(random);
        int maxX = (centerX << 4) + 16 + getRandomSize(random);
        int minZ = (centerZ << 4) - getRandomSize(random);
        int maxZ = (centerZ << 4) + 16 + getRandomSize(random);
        int x0 = Math.max((chunkX << 4), minX);
        int x1 = Math.min((chunkX << 4) + 16, maxX);
        int z0 = Math.max((chunkZ << 4), minZ);
        int z1 = Math.min((chunkZ << 4) + 16, maxZ);
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        if (x0 < x1 && z0 < z1) {
            random.setSeed(random.nextLong() ^ chunkX ^ chunkZ);
            int d1 = (this.thickness + 2) / 3;
            int d2 = (this.thickness - 2) / 3;
            int height = this.thickness - d2 - d2 + 2;
            int type = this.thickness % 3;
            int y0 = level - d2;
            int val14 = d2 & 1;
            int val15 = level + d1;
            int val16 = val15 - d2 - ((d2 + (type == 0 ? 0 : 1)) >> 1);
            int val17 = d2 + d2 + 2;
            int[] val18 = {0, 0}, val19 = {1, 0}, val20 = {0, 1};
            for (int x = x0; x < x1; x++) {
                for (int z = z0; z < z1; z++) {
                    if (type == 0) {
                        if (random.nextBoolean())
                            val18 = val19;
                        else
                            val18 = val20;
                    }
                    int[] val21 = {0, 1, 2, 3};
                    for (int i = 3; i >= 0; i--) {
                        int j = random.nextInt(i + 1);
                        switch (val21[j]) {
                        case 0:
                            if (!this.oreSecondaries.isEmpty())
                                for (int ya = 0, yb = y0; ya < d1 + val18[0]; ya++)
                                    generateOre(world, pos.setPos(x, yb + ya, z), minX, maxX, minZ, maxZ, this.oreSecondaries, random);
                            break;
                        case 1:
                            if (!this.orePrimaries.isEmpty())
                                for (int ya = 0, yb = val15 + val18[0]; ya < d1 + val18[1]; ya++)
                                    generateOre(world, pos.setPos(x, yb + ya, z), minX, maxX, minZ, maxZ, this.orePrimaries, random);
                            break;
                        case 2:
                            if (!this.oreBetweens.isEmpty())
                                for (int ya = 0, yb = val16 + ((val14 + random.nextInt(val17)) >> 1); ya < d2; ya ++)
                                    generateOre(world, pos.setPos(x, yb + ya, z), minX, maxX, minZ, maxZ, this.oreBetweens, random);
                            break;
                        case 3:
                            if (!this.oreSporadics.isEmpty())
                                for (int ya = 0, yb = y0 + random.nextInt(height); ya < d2; ya++)
                                    generateOre(world, pos.setPos(x, yb + ya + ya, z), minX, maxX, minZ, maxZ, this.oreSporadics, random);
                            break;
                        }
                        val21[j] = val21[i];
                    }
                }
            }
            if (GTValues.D1) System.out.println("Generated Orevein: " + this.name + " " + (chunkX << 4) + " " + (chunkZ << 4));
        }
    }

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "ore.mix." + this.name;
    }

}
