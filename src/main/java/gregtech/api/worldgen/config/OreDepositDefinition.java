package gregtech.api.worldgen.config;

import com.google.gson.JsonObject;
import gregtech.api.worldgen.filler.IBlockFiller;
import gregtech.api.worldgen.shape.IShapeGenerator;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.Biome;

import java.util.function.Function;
import java.util.function.Predicate;

public class OreDepositDefinition {

    public static final Function<Biome, Integer> NO_BIOME_INFLUENCE = biome -> 0;
    public static final Predicate<WorldProvider> PREDICATE_SURFACE_WORLD = WorldProvider::isSurfaceWorld;
    public static final Predicate<IBlockState> PREDICATE_STONE_TYPE = state -> true;

    private final String depositName;

    private int priority = 0;
    private int weight;
    private float density;
    private int[] heightLimit = new int[] {Integer.MIN_VALUE, Integer.MAX_VALUE};

    private Function<Biome, Integer> biomeWeightModifier = NO_BIOME_INFLUENCE;
    private Predicate<WorldProvider> dimensionFilter = PREDICATE_SURFACE_WORLD;
    private Predicate<IBlockState> generationPredicate = PREDICATE_STONE_TYPE;

    private IBlockFiller blockFiller;
    private IShapeGenerator shapeGenerator;

    public OreDepositDefinition(String depositName) {
        this.depositName = depositName;
    }

    public OreDepositDefinition(String depositName, int priority, int weight, float density, int[] heightLimit, Predicate<IBlockState> generationPredicate, IBlockFiller blockFiller, IShapeGenerator shapeGenerator) {
        this.depositName = depositName;
        this.priority = priority;
        this.weight = weight;
        this.density = density;
        this.heightLimit = heightLimit;
        this.generationPredicate = generationPredicate;
        this.blockFiller = blockFiller;
        this.shapeGenerator = shapeGenerator;
    }

    public void initializeFromConfig(JsonObject configRoot) {
        if(configRoot.has("priority")) {
            this.priority = configRoot.get("priority").getAsInt();
        }
        this.weight = configRoot.get("weight").getAsInt();
        this.density = configRoot.get("density").getAsFloat();
        if(configRoot.has("min_height")) {
            this.heightLimit[0] = configRoot.get("min_height").getAsInt();
        }
        if(configRoot.has("max_height")) {
            this.heightLimit[1] = configRoot.get("max_height").getAsInt();
        }
        if(configRoot.has("biome_modifier")) {
            this.biomeWeightModifier = OreConfigUtils.createBiomeWeightModifier(configRoot.get("biome_modifier"));
        }
        if(configRoot.has("dimension_filter")) {
            this.dimensionFilter = OreConfigUtils.createWorldPredicate(configRoot.get("dimension_filter"));
        }
        this.blockFiller = WorldGenRegistry.INSTANCE.createBlockFiller(configRoot.get("filler").getAsJsonObject());
        this.shapeGenerator = WorldGenRegistry.INSTANCE.createShapeGenerator(configRoot.get("generator").getAsJsonObject());
    }

    public String getDepositName() {
        return depositName;
    }

    public int getPriority() {
        return priority;
    }

    public int getWeight() {
        return weight;
    }

    public float getDensity() {
        return density;
    }

    public boolean checkInHeightLimit(int yLevel) {
        return true;//return yLevel >= heightLimit[0] && yLevel <= heightLimit[1];
    }

    public int[] getHeightLimit() {
        return heightLimit;
    }

    public Function<Biome, Integer> getBiomeWeightModifier() {
        return biomeWeightModifier;
    }

    public Predicate<WorldProvider> getDimensionFilter() {
        return dimensionFilter;
    }

    public Predicate<IBlockState> getGenerationPredicate() {
        return generationPredicate;
    }

    public IBlockFiller getBlockFiller() {
        return blockFiller;
    }

    public IShapeGenerator getShapeGenerator() {
        return shapeGenerator;
    }
}
