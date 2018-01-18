package gregtech.api.unification.ore;

import java.util.ArrayList;

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

    public final String name, particalTexture, harvestTool;
    public final String[] baseTexture;
    public final OrePrefix processingPrefix;
    public final DustMaterial stoneMaterial;
    public final ModelType modelType;
    public final boolean unbreakable, falling;
    public Condition<IBlockState> conditioin;

    public static final GTControlledRegistry<StoneType> STONE_TYPE_REGISTRY = new GTControlledRegistry<>(128);

    StoneType(int id, String name, OrePrefix processingPrefix, DustMaterial stoneMaterial, String baseTexture, Condition<IBlockState>... conditions) {
        this(id, name, processingPrefix, stoneMaterial, "pickaxe", baseTexture, conditions);
    }

    StoneType(int id, String name, OrePrefix processingPrefix, DustMaterial stoneMaterial, String harvestTool, String baseTexture, Condition<IBlockState>... conditions) {
        this(id, name, processingPrefix, stoneMaterial, harvestTool, 0, baseTexture, new String[]{baseTexture}, conditions);
    }

    StoneType(int id, String name, OrePrefix processingPrefix, DustMaterial stoneMaterial, int special, String particalTexture, String[] baseTextures, Condition<IBlockState>... conditions) {
        this(id, name, processingPrefix, stoneMaterial, "pickaxe", special, particalTexture, baseTextures, conditions);
    }

    StoneType(int id, String name, OrePrefix processingPrefix, DustMaterial stoneMaterial, String harvestTool, int special, String particalTexture, String[] baseTextures, Condition<IBlockState>... conditions) {
        this.name = name;
        this.processingPrefix = processingPrefix;
        this.stoneMaterial = stoneMaterial;
        this.harvestTool = harvestTool;
        this.baseTexture = baseTextures;
        this.modelType = (special & 0x1) != 0 ? ModelType.SIDED : ModelType.NORMAL;
        this.unbreakable = (special & 0x2) != 0;
        this.falling = (special & 0x4) != 0;
        this.particalTexture = particalTexture;
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
