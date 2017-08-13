package gregtech.common.items;

import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.items.GT_MetaGenerated_Item_X32;

public class GT_MetaGenerated_Item_03
        extends GT_MetaGenerated_Item_X32 {
    public static GT_MetaGenerated_Item_03 INSTANCE;

    public GT_MetaGenerated_Item_03() {
        super("metaitem.03", OrePrefix.crateGtDust, OrePrefix.crateGtIngot, OrePrefix.crateGtGem, OrePrefix.crateGtPlate);
        INSTANCE = this;
    }

    public boolean doesShowInCreative(OrePrefix aPrefix, Materials aMaterial, boolean aDoShowAllItems) {
        return aDoShowAllItems;
    }
}
