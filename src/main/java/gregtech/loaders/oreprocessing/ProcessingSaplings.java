package gregtech.loaders.oreprocessing;

import gregtech.api.GT_Values;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.unification.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingSaplings implements IOreRegistrationHandler {
    public ProcessingSaplings() {
        OrePrefixes.treeSapling.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Wood, 2L), null, 0, false);
        GT_Values.RA.addLatheRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 1L), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Wood, 1L), 16, 8);
    }
}
