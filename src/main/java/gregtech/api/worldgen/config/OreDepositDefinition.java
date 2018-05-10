package gregtech.api.worldgen.config;

import com.google.gson.JsonObject;
import gregtech.api.unification.material.type.MetalMaterial;
import gregtech.api.unification.ore.StoneType;
import gregtech.api.unification.ore.StoneTypes;
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
    public static final Predicate<IBlockState> PREDICATE_STONE_TYPE = state -> StoneType.computeStoneType(state) != StoneTypes._NULL;

    private final String depositName;

    private int priority = 0;
    private int weight;
    private float density;
    private int[] heightLimit = new int[] {Integer.MIN_VALUE, Integer.MAX_VALUE};
    private MetalMaterial surfaceStoneMaterial;

    private Function<Biome, Integer> biomeWeightModifier = NO_BIOME_INFLUENCE;
    private Predicate<WorldProvider> dimensionFilter = PREDICATE_SURFACE_WORLD;
    private Predicate<IBlockState> generationPredicate = PREDICATE_STONE_TYPE;

    private IBlockFiller blockFiller;
    private IShapeGenerator shapeGenerator;

    public OreDepositDefinition(String depositName) {
        this.depositName = depositName;
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
        if(configRoot.has("generation_predicate")) {
            this.generationPredicate = OreConfigUtils.createBlockStatePredicate(configRoot.get("generation_predicate"));
        }
        if(configRoot.has("surface_stone_material")) {
            this.surfaceStoneMaterial = (MetalMaterial) OreConfigUtils.getMaterialByName(configRoot.get("surface_stone_material").getAsString());
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

    public MetalMaterial getSurfaceStoneMaterial() {
        return surfaceStoneMaterial;
    }

    public boolean checkInHeightLimit(int yLevel) {
        return yLevel >= heightLimit[0] && yLevel <= heightLimit[1];
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
