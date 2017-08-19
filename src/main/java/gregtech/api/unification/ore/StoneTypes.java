package gregtech.api.unification.ore;

import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.Material;
import gregtech.common.blocks.BlockGranite;
import gregtech.common.blocks.BlockMineral;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.IStringSerializable;

/**
 * For ore generation
 */
public enum StoneTypes implements IStringSerializable {

    //vanilla ones
    STONE("stone", OrePrefix.ore, Materials.Stone),
    NETHERRACK("netherrack", OrePrefix.oreNetherrack, Materials.Netherrack),
    ENDSTONE("endstone", OrePrefix.oreEndstone, Materials.Endstone),

    //gt ones
    BLACK_GRANITE("black_granite", OrePrefix.oreBlackgranite, Materials.GraniteBlack),
    RED_GRANITE("red_granite", OrePrefix.oreRedgranite, Materials.GraniteRed),
    MARBLE("marble", OrePrefix.oreMarble, Materials.Marble),
    BASALT("basalt", OrePrefix.oreBasalt, Materials.Basalt);


    public final int id = ordinal();
    public final String name;
    public final OrePrefix processingPrefix;
    public final Material stoneMaterial;

    public static final StoneTypes[] TYPES = values();

    StoneTypes(String name, OrePrefix processingPrefix, Material stoneMaterial) {
        this.name = name;
        this.processingPrefix = processingPrefix;
        this.stoneMaterial = stoneMaterial;
    }

    public static StoneTypes computeStoneType(IBlockState blockState) {
        StoneTypes variantId = StoneTypes.STONE;
        if (blockState.getBlock() == Blocks.STONE) {
            variantId = StoneTypes.STONE;
        } else if (blockState.getBlock() instanceof BlockGranite) {
            BlockGranite block = (BlockGranite) blockState.getBlock();
            BlockGranite.GraniteVariant variant = block.getVariant(blockState);
            if (variant == BlockGranite.GraniteVariant.BLACK_GRANITE)
                variantId = StoneTypes.BLACK_GRANITE;
            else if (variant == BlockGranite.GraniteVariant.RED_GRANITE)
                variantId = StoneTypes.RED_GRANITE;
        } else if (blockState.getBlock() instanceof BlockMineral) {
            BlockMineral block = (BlockMineral) blockState.getBlock();
            BlockMineral.MineralVariant variant = block.getVariant(blockState);
            if (variant == BlockMineral.MineralVariant.MARBLE)
                variantId = StoneTypes.MARBLE;
            else if (variant == BlockMineral.MineralVariant.BASALT)
                variantId = StoneTypes.BASALT;
        } else if (blockState.getBlock() == Blocks.NETHERRACK) {
            variantId = StoneTypes.NETHERRACK;
        } else if (blockState.getBlock() == Blocks.END_STONE) {
            variantId = StoneTypes.ENDSTONE;
        }
        return variantId;
    }

    @Override
    public String getName() {
        return name;
    }

}
