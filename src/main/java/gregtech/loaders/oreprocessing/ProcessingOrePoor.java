package gregtech.loaders.oreprocessing;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingOrePoor implements gregtech.api.interfaces.IOreRecipeRegistrator {
    public ProcessingOrePoor() {
        OrePrefixes.orePoor.add(this);
        OrePrefixes.oreSmall.add(this);
        OrePrefixes.oreNormal.add(this);
        OrePrefixes.oreRich.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        int aMultiplier = 1;
        switch (aPrefix) {
            case oreSmall:
                aMultiplier = 1;
                break;
            case orePoor:
                aMultiplier = 2;
                break;
            case oreNormal:
                aMultiplier = 3;
                break;
            case oreRich:
                aMultiplier = 4;
        }
        if (aMaterial != null) {
            GT_Values.RA.addForgeHammerRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.dustTiny, aMaterial, aMultiplier), 16, 10);
            GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.dustTiny, aMaterial, 2 * aMultiplier), GT_OreDictUnificator.get(OrePrefixes.dustTiny, GT_Utility.selectItemInList(0, aMaterial, aMaterial.mOreByProducts), 1L), 5 * aMultiplier, GT_OreDictUnificator.getDust(aPrefix.mSecondaryMaterial), 100, true);
            if (aMaterial.contains(gregtech.api.enums.SubTag.NO_SMELTING))
                GT_ModHandler.addSmeltingRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.nugget, aMaterial.mDirectSmelting, aMultiplier));
        }
    }
}