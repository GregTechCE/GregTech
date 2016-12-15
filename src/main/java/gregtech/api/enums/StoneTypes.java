package gregtech.api.enums;

import gregtech.api.interfaces.IIconContainer;
import gregtech.api.objects.RegIconContainer;

/**
 * For ore generation
 */
public enum StoneTypes {

    //vanilla ones
    STONE(new RegIconContainer("blocks/stone"), OrePrefixes.ore),
    NETHERRACK(new RegIconContainer("blocks/netherrack"), OrePrefixes.oreNetherrack),
    ENDSTONE(new RegIconContainer("blocks/end_stone"), OrePrefixes.oreEndstone),

    //gt ones
    BLACK_GRANITE(Textures.BlockIcons.GRANITE_BLACK_STONE, OrePrefixes.oreBlackgranite),
    RED_GRANITE(Textures.BlockIcons.GRANITE_RED_STONE, OrePrefixes.oreRedgranite),
    MARBLE(Textures.BlockIcons.MARBLE_STONE, OrePrefixes.oreMarble),
    BASALT(Textures.BlockIcons.BASALT_STONE, OrePrefixes.oreBasalt);


    public final int mId = ordinal();
    public final IIconContainer mIconContainer;
    public final OrePrefixes processingPrefix;

    public static StoneTypes[] mTypes = values();

    StoneTypes(IIconContainer mIconContainer, OrePrefixes processingPrefix) {
        this.mIconContainer = mIconContainer;
        this.processingPrefix= processingPrefix;
    }

}
