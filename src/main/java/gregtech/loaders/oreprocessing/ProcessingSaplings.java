package gregtech.loaders.oreprocessing;

import gregtech.api.GT_Values;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingSaplings implements IOreRegistrationHandler {
    public ProcessingSaplings() {
        OrePrefix.treeSapling.add(this);
    }

    public void registerOre(OrePrefix aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), OreDictionaryUnifier.get(OrePrefix.dustSmall, Materials.Wood, 2L), null, 0, false);
        GT_Values.RA.addLatheRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), OreDictionaryUnifier.get(OrePrefix.stick, Materials.Wood, 1L), OreDictionaryUnifier.get(OrePrefix.dustTiny, Materials.Wood, 1L), 16, 8);
    }
}
