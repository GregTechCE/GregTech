package gregtech.common.worldgen;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.ore.StoneTypes;
import gregtech.api.util.WeightedList.WeightedWrapperList;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;

public class GTWorldGen_Asteroid extends GTWorldGen_Stone {

    public final ConcurrentHashMap<World, List<GTWorldGen_OreVein>> dimWiseOreVeinList = new ConcurrentHashMap<>();

    public GTWorldGen_Asteroid(String name, boolean enabled, int minY, int maxY, int minSize, int maxSize, int probability, int amount, IBlockState stone, String[] dimWhiteList, String[] biomeWhiteList) {
        super(name, enabled, 2048, minY, maxY, minSize, maxSize, probability, amount, stone, true, dimWhiteList, biomeWhiteList);
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, Biome biome, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if (!this.isGenerationAllowed(world, biome) || this.probability <= 0)
            return;
        for (int i = 0; i < this.amount; i++) {
            if (random.nextInt(this.probability) == 0) {
                GTWorldGen_OreVein.getRandomOreVein(random, world, this)
                .ifPresent(oreVein -> {
                    WeightedWrapperList<WeightedWrapperList<Material>> oreLists = new WeightedWrapperList<>();
                    oreLists.add(oreVein.orePrimaries, 6);
                    oreLists.add(oreVein.oreSecondaries, 6);
                    oreLists.add(oreVein.oreBetweens, 4);
                    oreLists.add(oreVein.oreSporadics, 3);
                    WeightedWrapperList<Material> ores = WeightedWrapperList.mergeWeightedList(oreLists);
                    int density = oreVein.density;
                    boolean generateOre = this.stoneType != StoneTypes._NULL;
                    this.generate(random, chunkX, chunkZ, world, false,
                            pos -> world.isAirBlock(pos),
                            (pos, r, rnd) -> {
                        if (generateOre && rnd.nextInt(Math.max(2, (int) (80.0f * r / density))) == 0) {
                            getOreBlock(ores.getRandomObject(rnd), this.stoneType, false)
                            .ifPresent(ore -> world.setBlockState(pos, ore, 18));
                        } else {
                            world.setBlockState(pos, stone, 18);
                        }
                    });
                });
            }
        }
    }

    @Override
    public String toString() {
        return "asteroid." + this.name;
    }
}
