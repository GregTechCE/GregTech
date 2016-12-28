package gregtech.api.enums;

import gregtech.api.GregTech_API;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.objects.RegIconContainer;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

/**
 * For ore generation
 */
public enum StoneTypes {

    //vanilla ones
    STONE(new RegIconContainer("blocks/stone"), OrePrefixes.ore, Materials.Stone),
    NETHERRACK(new RegIconContainer("blocks/netherrack"), OrePrefixes.oreNetherrack, Materials.Netherrack),
    ENDSTONE(new RegIconContainer("blocks/end_stone"), OrePrefixes.oreEndstone, Materials.Endstone),

    //gt ones
    BLACK_GRANITE(Textures.BlockIcons.GRANITE_BLACK_STONE, OrePrefixes.oreBlackgranite, Materials.BlackGranite),
    RED_GRANITE(Textures.BlockIcons.GRANITE_RED_STONE, OrePrefixes.oreRedgranite, Materials.RedGranite),
    MARBLE(Textures.BlockIcons.MARBLE_STONE, OrePrefixes.oreMarble, Materials.Marble),
    BASALT(Textures.BlockIcons.BASALT_STONE, OrePrefixes.oreBasalt, Materials.Basalt);


    public final int mId = ordinal();
    public final IIconContainer mIconContainer;
    public final OrePrefixes processingPrefix;
    public final Materials stoneMaterial;

    public static StoneTypes[] mTypes = values();

    StoneTypes(IIconContainer mIconContainer, OrePrefixes processingPrefix, Materials stoneMaterial) {
        this.mIconContainer = mIconContainer;
        this.processingPrefix = processingPrefix;
        this.stoneMaterial = stoneMaterial;
    }

    public static StoneTypes computeStoneType(IBlockState blockState) {
        Block block = blockState.getBlock();
        int metadata = block.getMetaFromState(blockState);

        StoneTypes variantId = StoneTypes.STONE;

        if (block == Blocks.STONE) {
            variantId = StoneTypes.STONE;
        } else if (block == GregTech_API.sBlockGranites) {
            if (metadata == 0)
                variantId = StoneTypes.BLACK_GRANITE;
            else if (metadata == 8)
                variantId = StoneTypes.RED_GRANITE;
        } else if (block == GregTech_API.sBlockStones) {
            if (metadata == 0)
                variantId = StoneTypes.MARBLE;
            else if (metadata == 8)
                variantId = StoneTypes.BASALT;
        } else if (block == Blocks.NETHERRACK) {
            variantId = StoneTypes.NETHERRACK;
        } else if (block == Blocks.END_STONE) {
            variantId = StoneTypes.ENDSTONE;
        }
        return variantId;
    }
}
