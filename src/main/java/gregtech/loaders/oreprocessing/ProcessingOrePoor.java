package gregtech.loaders.oreprocessing;

import gregtech.api.GT_Values;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingOrePoor implements IOreRegistrationHandler {
    public ProcessingOrePoor() {
        OrePrefix.orePoor.add(this);
        OrePrefix.oreSmall.add(this);
        OrePrefix.oreNormal.add(this);
        OrePrefix.oreRich.add(this);
    }

    public void registerOre(OrePrefix aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
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
            GT_Values.RA.addForgeHammerRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), OreDictionaryUnifier.get(OrePrefix.dustTiny, aMaterial, aMultiplier), 16, 10);
            GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), OreDictionaryUnifier.get(OrePrefix.dustTiny, aMaterial, 2 * aMultiplier), OreDictionaryUnifier.get(OrePrefix.dustTiny, GT_Utility.selectItemInList(0, aMaterial, aMaterial.mOreByProducts), 1L), 5 * aMultiplier, OreDictionaryUnifier.getDust(aPrefix.mSecondaryMaterial), 100, true);
            if (aMaterial.contains(gregtech.api.enums.SubTag.NO_SMELTING))
                GT_ModHandler.addSmeltingRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), OreDictionaryUnifier.get(OrePrefix.nugget, aMaterial.mDirectSmelting, aMultiplier));
        }
    }
}