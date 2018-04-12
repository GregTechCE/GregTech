package gregtech.api.unification.ore;

import java.util.EnumMap;
import java.util.function.Supplier;

import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.util.Condition;
import gregtech.api.util.GTControlledRegistry;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;

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
    public Condition<IBlockState> conditioin;

    public static final GTControlledRegistry<StoneType> STONE_TYPE_REGISTRY = new GTControlledRegistry<>(128);

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
     * @param special           Bitmask. 0x1 if the ore block is unbreakable;
     *                                   0x2 if the ore block can fall like a sand block.
     * @param particleTexture   Texture of the particle
     * @param stone             Basic block of this stone type
     * @param conditions        Whether the ores with this stone type can generate with such BlockState
     */
    @SafeVarargs
    public StoneType(int id, String name, OrePrefix processingPrefix, DustMaterial stoneMaterial, String harvestTool, int special, String particleTexture, Supplier<IBlockState> stone, Condition<IBlockState>... conditions) {
        this.name = name;
        this.processingPrefix = processingPrefix;
        this.stoneMaterial = stoneMaterial;
        this.harvestTool = harvestTool;
        this.unbreakable = (special & 0x1) != 0;
        this.falling = (special & 0x2) != 0;
        this.particleTexture = particleTexture;
        this.stone = stone;
        if (id > -1) {
            STONE_TYPE_REGISTRY.register(id, name, this);
            this.conditioin = new Condition.Or<>(conditions);
        } else {
            STONE_TYPE_REGISTRY.putObject(name, this);
            this.conditioin = state -> false;
        }
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

    @Override
    public int compareTo(StoneType stoneType) {
        return STONE_TYPE_REGISTRY.getIDForObject(this) - STONE_TYPE_REGISTRY.getIDForObject(stoneType);
    }

    public static void init() {
        StoneType test = StoneTypes._NULL;
        StoneType.STONE_TYPE_REGISTRY.freezeRegistry();
    }

    public static StoneType computeStoneType(IBlockState blockState) {
        for (StoneType stoneType : STONE_TYPE_REGISTRY.getObjectsWithIds()) {
            if (stoneType.conditioin.isTrue(blockState)) return stoneType;
        }
        return StoneTypes._NULL;
    }

}
