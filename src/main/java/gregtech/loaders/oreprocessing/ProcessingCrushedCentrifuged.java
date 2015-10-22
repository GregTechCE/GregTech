package gregtech.loaders.oreprocessing;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingCrushedCentrifuged implements gregtech.api.interfaces.IOreRecipeRegistrator {
    public ProcessingCrushedCentrifuged() {
        OrePrefixes.crushedCentrifuged.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        GT_Values.RA.addForgeHammerRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial.mMacerateInto, 1L), 10, 16);
        GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial.mMacerateInto, 1L), GT_OreDictUnificator.get(OrePrefixes.dust, GT_Utility.selectItemInList(2, aMaterial.mMacerateInto, aMaterial.mOreByProducts), 1L), 10, false);
    }
}
