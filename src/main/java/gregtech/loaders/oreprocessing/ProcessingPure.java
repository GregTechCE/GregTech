package gregtech.loaders.oreprocessing;

import gregtech.api.GT_Values;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingPure implements IOreRegistrationHandler {
    public ProcessingPure() {
        OrePrefix.crushedPurified.add(this);
        OrePrefix.cleanGravel.add(this);
        OrePrefix.reduced.add(this);
    }

    public void registerOre(OrePrefix aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        GT_Values.RA.addForgeHammerRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), OreDictionaryUnifier.get(OrePrefix.dustPure, aMaterial.mMacerateInto, 1L), 10, 16);
        GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), OreDictionaryUnifier.get(OrePrefix.dustPure, aMaterial.mMacerateInto, OreDictionaryUnifier.get(OrePrefix.dust, aMaterial.mMacerateInto, 1L), 1L), OreDictionaryUnifier.get(OrePrefix.dust, GT_Utility.selectItemInList(1, aMaterial.mMacerateInto, aMaterial.mOreByProducts), 1L), 10, false);
    }
}
