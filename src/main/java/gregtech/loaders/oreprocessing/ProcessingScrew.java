package gregtech.loaders.oreprocessing;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.SubTag;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import gregtech.common.GT_Proxy;
import net.minecraft.item.ItemStack;

public class ProcessingScrew implements gregtech.api.interfaces.IOreRecipeRegistrator {
    public ProcessingScrew() {
        OrePrefixes.screw.add(this);
    }

    @Override
    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        if (!aMaterial.contains(SubTag.NO_WORKING)) {
            GT_Values.RA.addLatheRecipe(GT_OreDictUnificator.get(OrePrefixes.bolt, aMaterial, 1L), GT_Utility.copyAmount(1L, new Object[]{aStack}), null, (int) Math.max(aMaterial.getMass() / 8L, 1L), 4);
            if ((aMaterial.mUnificatable) && (aMaterial.mMaterialInto == aMaterial))
                GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.screw, aMaterial, 1L), GT_Proxy.tBits, new Object[]{"fX", "X ", Character.valueOf('X'), OrePrefixes.bolt.get(aMaterial)});
        }
    }
}
