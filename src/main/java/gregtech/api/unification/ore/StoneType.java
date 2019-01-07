package gregtech.api.unification.ore;

import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.util.Condition;
import gregtech.api.util.GTControlledRegistry;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;

import java.util.EnumMap;
import java.util.function.Supplier;

/**
 * For ore generation
 */
public class StoneType implements Comparable<StoneType> {

    public final String name, particleTexture, harvestTool;
    public final EnumMap<EnumFacing, String> baseTexture = new EnumMap<>(EnumFacing.class);
    public final OrePrefix processingPrefix;
    public final DustMaterial stoneMaterial;
    public final boolean unbreakable, falling;
    public final Supplier<IBlockState> stone;
    public Condition<IBlockState> condition;
    public SoundType soundType = SoundType.STONE;

    public static final GTControlledRegistry<String, StoneType> STONE_TYPE_REGISTRY = new GTControlledRegistry<>(128);

    /**
     * @param processingPrefix  Prefix of the ores with this stone type
     * @param stoneMaterial     Material of this type of stone
     * @param baseTexture       Base texture of all facing of the ore block. will also be used as the particle texture.
     * @param stone             Basic block of this stone type
     * @param conditions        Whether the ores with this stone type can generate with such BlockState
     */
    @SafeVarargs
    public StoneType(int id, String name, OrePrefix processingPrefix, DustMaterial stoneMaterial, String baseTexture, Supplier<IBlockState> stone, Condition<IBlockState>... conditions) {
        this(id, name, processingPrefix, stoneMaterial, "pickaxe", baseTexture, stone, conditions);
    }

    /**
     * @param processingPrefix  Prefix of the ores with this stone type
     * @param stoneMaterial     Material of this type of stone
     * @param baseTexture       Base texture of all facing of the ore block. will also be used as the particle texture. 
     * @param stone             Basic block of this stone type
     * @param conditions        Whether the ores with this stone type can generate with such BlockState
     */
    @SafeVarargs
    public StoneType(int id, String name, OrePrefix processingPrefix, DustMaterial stoneMaterial, String harvestTool, String baseTexture, Supplier<IBlockState> stone, Condition<IBlockState>... conditions) {
        this(id, name, processingPrefix, stoneMaterial, harvestTool, 0, baseTexture, stone, conditions);
        this.setTextureForAllFacing(baseTexture);
    }

    /**
     * @param processingPrefix  Prefix of the ores with this stone type
     * @param stoneMaterial     Material of this type of stone
     * @param special           Bitmask. 0x1 if the ore block is unbreakable;
     *                                   0x2 if the ore block can fall like a sand block.
     * @param particleTexture   Texture of the particle
     * @param stone             Basic block of this stone type
     * @param conditions        Whether the ores with this stone type can generate with such BlockState
     */
    @SafeVarargs
    public StoneType(int id, String name, OrePrefix processingPrefix, DustMaterial stoneMaterial, int special, String particleTexture, Supplier<IBlockState> stone, Condition<IBlockState>... conditions) {
        this(id, name, processingPrefix, stoneMaterial, "pickaxe", special, particleTexture, stone, conditions);
    }

    /**
     * @param processingPrefix  Prefix of the ores with this stone type
     * @param stoneMaterial     Material of this type of stone
     * @param flags           Bitmask. 0x1 if the ore block is unbreakable;
     *                                   0x2 if the ore block can fall like a sand block.
     * @param particleTexture   Texture of the particle
     * @param stone             Basic block of this stone type
     * @param conditions        Whether the ores with this stone type can generate with such BlockState
     */
    @SafeVarargs
    public StoneType(int id, String name, OrePrefix processingPrefix, DustMaterial stoneMaterial, String harvestTool, int flags, String particleTexture, Supplier<IBlockState> stone, Condition<IBlockState>... conditions) {
        this.name = name;
        this.processingPrefix = processingPrefix;
        this.stoneMaterial = stoneMaterial;
        this.harvestTool = harvestTool;
        this.unbreakable = (flags & 0x1) != 0;
        this.falling = (flags & 0x2) != 0;
        this.particleTexture = particleTexture;
        this.stone = stone;
        STONE_TYPE_REGISTRY.register(id, name, this);
        this.condition = new Condition.Or<>(conditions);
    }

    public StoneType setTextureForDownFacing(String baseTexture) {
        this.baseTexture.put(EnumFacing.DOWN, baseTexture);
        return this;
    }

    public StoneType setTextureForUpFacing(String baseTexture) {
        this.baseTexture.put(EnumFacing.UP, baseTexture);
        return this;
    }

    public StoneType setTextureForNorthFacing(String baseTexture) {
        this.baseTexture.put(EnumFacing.NORTH, baseTexture);
        return this;
    }

    public StoneType setTextureForSouthFacing(String baseTexture) {
        this.baseTexture.put(EnumFacing.SOUTH, baseTexture);
        return this;
    }

    public StoneType setTextureForWestFacing(String baseTexture) {
        this.baseTexture.put(EnumFacing.WEST, baseTexture);
        return this;
    }

    public StoneType setTextureForEastFacing(String baseTexture) {
        this.baseTexture.put(EnumFacing.EAST, baseTexture);
        return this;
    }

    public StoneType setTextureForAllSide(String baseTexture) {
        return this.setTextureForNorthFacing(baseTexture)
                .setTextureForSouthFacing(baseTexture)
                .setTextureForWestFacing(baseTexture)
                .setTextureForEastFacing(baseTexture);
    }

    public StoneType setTextureForAllFacing(String baseTexture) {
        return this.setTextureForDownFacing(baseTexture)
                .setTextureForUpFacing(baseTexture)
                .setTextureForAllSide(baseTexture);
    }

    public StoneType setSoundType(SoundType soundType) {
        this.soundType = soundType;
        return this;
    }


    @Override
    public int compareTo(StoneType stoneType) {
        return STONE_TYPE_REGISTRY.getIDForObject(this) - STONE_TYPE_REGISTRY.getIDForObject(stoneType);
    }

    public static void init() {
        //noinspection ResultOfMethodCallIgnored
        StoneTypes.STONE.toString();
        StoneType.STONE_TYPE_REGISTRY.freezeRegistry();
    }

    public static StoneType computeStoneType(IBlockState blockState) {
        for (StoneType stoneType : STONE_TYPE_REGISTRY) {
            if (stoneType.condition.isTrue(blockState)) return stoneType;
        }
        return null;
    }

}
