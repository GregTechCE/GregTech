package gregtech.loaders.oreprocessing;

import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.enums.SubTag;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import gregtech.common.GT_Proxy;
import net.minecraft.item.ItemStack;

public class ProcessingBolt implements IOreRegistrationHandler {
    public ProcessingBolt() {
        OrePrefix.bolt.add(this);
    }

    public void registerOre(OrePrefix aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        if ((aMaterial.mUnificatable) && (aMaterial.mMaterialInto == aMaterial) && !aMaterial.contains(SubTag.NO_WORKING)) {
            GT_ModHandler.addCraftingRecipe(GT_Utility.copyAmount(2L, new Object[]{aStack}), GT_Proxy.tBits, new Object[]{"s ", " X", Character.valueOf('X'), OrePrefix.stick.get(aMaterial)});
        }
    }
}
