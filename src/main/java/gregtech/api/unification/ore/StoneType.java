package gregtech.api.unification.ore;

import java.util.function.Supplier;

import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.util.Condition;
import gregtech.api.util.GTControlledRegistry;
import net.minecraft.block.state.IBlockState;

/**
 * For ore generation
 */
public class StoneType implements Comparable<StoneType> {

    public static enum ModelType {
        NORMAL, SIDED;
    }

    public final String name, particleTexture, harvestTool;
    public final String[] baseTexture;
    public final OrePrefix processingPrefix;
    public final DustMaterial stoneMaterial;
    public final ModelType modelType;
    public final boolean unbreakable, falling;
    public final Supplier<IBlockState> stone;
    public Condition<IBlockState> conditioin;

    public static final GTControlledRegistry<StoneType> STONE_TYPE_REGISTRY = new GTControlledRegistry<>(128);

    /**
     * @param processingPrefix  Prefix of the ores with this stone type
     * @param stoneMaterial     Material of this type of stone
     * @param baseTexture       Base texture of the ore block. Must have 6 entries with bitmask 0x1.
     * @param stone             Basic block of this stone type
     * @param conditions        Whether the ores with this stone type can generate with such BlockState
     */
    public StoneType(int id, String name, OrePrefix processingPrefix, DustMaterial stoneMaterial, String baseTexture, Supplier<IBlockState> stone, Condition<IBlockState>... conditions) {
        this(id, name, processingPrefix, stoneMaterial, "pickaxe", baseTexture, stone, conditions);
    }

    /**
     * @param processingPrefix  Prefix of the ores with this stone type
     * @param stoneMaterial     Material of this type of stone
     * @param baseTexture       Base texture of the ore block.
     * @param stone             Basic block of this stone type
     * @param conditions        Whether the ores with this stone type can generate with such BlockState
     */
    public StoneType(int id, String name, OrePrefix processingPrefix, DustMaterial stoneMaterial, String harvestTool, String baseTexture, Supplier<IBlockState> stone, Condition<IBlockState>... conditions) {
        this(id, name, processingPrefix, stoneMaterial, harvestTool, 0, baseTexture, new String[]{baseTexture}, stone, conditions);
    }

    /**
     * @param processingPrefix  Prefix of the ores with this stone type
     * @param stoneMaterial     Material of this type of stone
     * @param special           Bitmask. 0x1 if each side of the ore block has different base texture;
     *                                   0x2 if the ore block is unbreakable;
     *                                   0x4 if the ore block can fall like a sand block.
     * @param particleTexture   Texture of the particle
     * @param baseTextures      Base textures of the ore block. Must have 6 entries with bitmask 0x1.
     * @param stone             Basic block of this stone type
     * @param conditions        Whether the ores with this stone type can generate with such BlockState
     */
    public StoneType(int id, String name, OrePrefix processingPrefix, DustMaterial stoneMaterial, int special, String particleTexture, String[] baseTextures, Supplier<IBlockState> stone, Condition<IBlockState>... conditions) {
        this(id, name, processingPrefix, stoneMaterial, "pickaxe", special, particleTexture, baseTextures, stone, conditions);
    }

    /**
     * @param processingPrefix  Prefix of the ores with this stone type
     * @param stoneMaterial     Material of this type of stone
     * @param special           Bitmask. 0x1 if each side of the ore block has different base texture;
     *                                   0x2 if the ore block is unbreakable;
     *                                   0x4 if the ore block can fall like a sand block.
     * @param particleTexture   Texture of the particle
     * @param baseTextures      Base textures of the ore block. Must have 6 entries with bitmask 0x1.
     * @param stone             Basic block of this stone type
     * @param conditions        Whether the ores with this stone type can generate with such BlockState
     */
    public StoneType(int id, String name, OrePrefix processingPrefix, DustMaterial stoneMaterial, String harvestTool, int special, String particleTexture, String[] baseTextures, Supplier<IBlockState> stone, Condition<IBlockState>... conditions) {
        this.name = name;
        this.processingPrefix = processingPrefix;
        this.stoneMaterial = stoneMaterial;
        this.harvestTool = harvestTool;
        this.baseTexture = baseTextures;
        this.modelType = (special & 0x1) != 0 ? ModelType.SIDED : ModelType.NORMAL;
        this.unbreakable = (special & 0x2) != 0;
        this.falling = (special & 0x4) != 0;
        this.particleTexture = particleTexture;
        this.stone = stone;
        if (id > -1) {
            STONE_TYPE_REGISTRY.register(id, name, this);
            this.conditioin = new Condition.Or<IBlockState>(conditions);
        } else {
            STONE_TYPE_REGISTRY.putObject(name, this);
            this.conditioin = state -> false;
        }
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
