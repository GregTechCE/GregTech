package gregtech.api.items.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import gregtech.api.items.metaitem.MetaOreDictItem;
import gregtech.api.unification.material.info.MaterialIconSet;
import gregtech.api.unification.ore.OrePrefix;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.gregtech.item.ItemRegistry")
@ZenRegister
public class CTItemUtils {

    @ZenMethod("registerItem")
    public static void registerItem(String name, short id, int rgb, String materialIconSet, String orePrefix) {
        new MetaOreDictItem.OreDictValueItem(
                id, name, rgb, MaterialIconSet.valueOf(materialIconSet), OrePrefix.getPrefix(orePrefix));
    }
}
