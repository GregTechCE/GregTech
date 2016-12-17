package gregtech.api.enums;

import gregtech.api.interfaces.IIconContainer;
import gregtech.api.objects.RegIconContainer;

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
        this.processingPrefix= processingPrefix;
        this.stoneMaterial = stoneMaterial;
    }

}
