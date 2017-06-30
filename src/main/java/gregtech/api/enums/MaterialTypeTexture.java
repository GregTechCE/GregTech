package gregtech.api.enums;

import net.minecraft.util.ResourceLocation;

public enum MaterialTypeTexture {

    //ITEM TEXTURES
    dustTiny,
    dustSmall,
    dust,
    dustImpure,
    dustPure,
    crushed,
    crushedPurified,
    crushedCentrifuged,
    gem,
    nugget,
    ingot,
    ingotHot,
    ingotDouble,
    ingotTriple,
    ingotQuadruple,
    ingotQuintuple,
    plate,
    plateDouble,
    plateTriple,
    plateQuadruple,
    plateQuintuple,
    plateDense,
    stick,
    lens,
    round,
    bolt,
    screw,
    ring,
    cell,
    cellPlasma,
    toolHeadSword,
    toolHeadPickaxe,
    toolHeadShovel,
    toolHeadAxe,
    toolHeadHoe,
    toolHeadHammer,
    toolHeadFile,
    toolHeadSaw,
    toolHeadDrill,
    toolHeadChainsaw,
    toolHeadSense,
    toolHeadPlow,
    toolHeadArrow,
    toolHeadScrewdriver,
    toolHeadBuzSaw,
    toolHeadSoldering,
    wireFine,
    gearGtSmall,
    rotor,
    stickLong,
    springSmall,
    spring,
    arrow,
    gemChipped,
    gemFlawed,
    gemFlawless,
    gemExquisite,
    gearGt,
    foil,
    crateGtDust,
    crateGtIngot,
    crateGtGem,
    crateGtPlate,
    turbineBlade,
    handleMallet,
    toolHeadMallet,

    //BLOCK TEXTURES
    block(true),
    foilBlock(true),
    wire(true),
    ore(true),
    oreSmall(true),
    frameGt(true),
    pipeSide(true),
    pipeSmall(true),
    pipeMedium(true),
    pipeLarge(true),
    pipeHuge(true);

    private boolean isBlock;

    MaterialTypeTexture() {
        this.isBlock = false;
    }

    MaterialTypeTexture(boolean isBlock) {
        this.isBlock = isBlock;
    }

    public ResourceLocation getTexturePath(MaterialType materialType) {
        return new ResourceLocation(GT_Values.MODID, String.format("%s/%s/%s", isBlock ? "blocks" : "items", materialType.name(), name()));
    }

}
