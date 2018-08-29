package gregtech.api.unification.material.type;

import com.google.common.collect.ImmutableList;
import gregtech.api.unification.material.MaterialIconSet;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.MaterialStack;

public class RoughSolidMaterial extends SolidMaterial {

    private final OrePrefix solidPrefix;

    public RoughSolidMaterial(int metaItemSubId, String name, int materialRGB, MaterialIconSet materialIconSet, int harvestLevel, ImmutableList<MaterialStack> materialComponents, long materialGenerationFlags, float toolSpeed, int toolDurability, OrePrefix solidPrefix) {
        super(metaItemSubId, name, materialRGB, materialIconSet, harvestLevel, materialComponents, materialGenerationFlags, null, toolSpeed, toolDurability);
        this.solidPrefix = solidPrefix;
    }

    public RoughSolidMaterial(int metaItemSubId, String name, int materialRGB, MaterialIconSet materialIconSet, int harvestLevel, ImmutableList<MaterialStack> materialComponents, long materialGenerationFlags, OrePrefix solidPrefix) {
        super(metaItemSubId, name, materialRGB, materialIconSet, harvestLevel, materialComponents, materialGenerationFlags, null, 0, 0);
        this.solidPrefix = solidPrefix;
    }

    public OrePrefix getSolidForm() {
        return solidPrefix;
    }
}
