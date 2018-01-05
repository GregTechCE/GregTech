package gregtech.api.unification.material;

import com.google.common.base.CaseFormat;
import com.google.common.collect.ImmutableMap;
import gregtech.api.util.GTResourceLocation;
import net.minecraft.util.ResourceLocation;

public enum MaterialIconType {

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
    toolHeadBuzzSaw,
    toolHeadDrill,
    toolHeadChainsaw,
    toolHeadSense,
    toolHeadPlow,
    toolHeadArrow,
    toolHeadScrewdriver,
    toolHeadBuzSaw,
    toolHeadSoldering,
    toolHeadWrench,
    toolHeadUniversalSpade,
    wireFine,
    gearSmall,
    rotor,
    stickLong,
    springSmall,
    spring,
    arrow,
    gemChipped,
    gemFlawed,
    gemFlawless,
    gemExquisite,
    gear,
    foil,
    crateGtDust,
    crateGtIngot,
    crateGtGem,
    crateGtPlate,
    turbineBlade,
    handleMallet,
    toolHeadMallet,

    //BLOCK TEXTURES
    block(true, false),
    foilBlock(true, false),
    wire(true, true),
    ore(true, true),
    oreSmall(true, true),
    frameGt(true, true),
    pipeSide(true, true),
    pipeTiny(true, true),
    pipeSmall(true, true),
    pipeMedium(true, true),
    pipeLarge(true, true),
    pipeHuge(true, true);

    public final boolean isBlock;
    public final boolean ambientOcclusion;

    public static final ImmutableMap<String, MaterialIconType> values;

    static {
        ImmutableMap.Builder<String, MaterialIconType> builder = ImmutableMap.builder();
        for (MaterialIconType value : values()) {
            builder.put(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, value.name()), value);
        }
        values = builder.build();
    }

    MaterialIconType() {
        this.isBlock = false;
        this.ambientOcclusion = false;
    }

    MaterialIconType(boolean isBlock, boolean ambientOcclusion) {
        this.isBlock = isBlock;
        this.ambientOcclusion = ambientOcclusion;

    }

    public ResourceLocation getBlockPath(MaterialIconSet materialIconSet) {
        String iconSet = materialIconSet.name().toLowerCase();
        String iconType = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name());
        return new GTResourceLocation("blocks/material_sets/" + iconSet + "/" + iconType);
    }

    public ResourceLocation getItemModelPath(MaterialIconSet materialIconSet) {
        String iconSet = materialIconSet.name().toLowerCase();
        String iconType = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name());
        return new GTResourceLocation("material_sets/" + iconSet + "/" + iconType);
    }

    public ResourceLocation getItemOverlayPath(MaterialIconSet materialIconSet) {
        String iconSet = materialIconSet.name().toLowerCase();
        String iconType = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name());
        return new GTResourceLocation("material_sets/" + iconSet + "/" + iconType + "_overlay");
    }

}
