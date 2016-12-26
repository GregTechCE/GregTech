package gregtech.loaders.oreprocessing;

import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.SubTag;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import gregtech.common.GT_Proxy;
import net.minecraft.item.ItemStack;

public class ProcessingBolt implements gregtech.api.interfaces.IOreRecipeRegistrator {
    public ProcessingBolt() {
        OrePrefixes.bolt.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        if ((aMaterial.mUnificatable) && (aMaterial.mMaterialInto == aMaterial) && !aMaterial.contains(SubTag.NO_WORKING)) {
            GT_ModHandler.addCraftingRecipe(GT_Utility.copyAmount(2L, new Object[]{aStack}), GT_Proxy.tBits, new Object[]{"s ", " X", Character.valueOf('X'), OrePrefixes.stick.get(aMaterial)});
        }
    }
}
