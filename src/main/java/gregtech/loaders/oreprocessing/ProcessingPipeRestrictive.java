package gregtech.loaders.oreprocessing;

import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingPipeRestrictive implements gregtech.api.interfaces.IOreRecipeRegistrator {
    public ProcessingPipeRestrictive() {
        for (OrePrefixes tPrefix : OrePrefixes.values())
            if (tPrefix.name().startsWith("pipeRestrictive")) tPrefix.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        gregtech.api.enums.GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(aOreDictName.replaceFirst("Restrictive", ""), null, 1L, false, true), GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Steel, aPrefix.mSecondaryMaterial.mAmount / OrePrefixes.ring.mMaterialAmount), GT_Utility.copyAmount(1L, new Object[]{aStack}), (int) (aPrefix.mSecondaryMaterial.mAmount * 400L / OrePrefixes.ring.mMaterialAmount), 4);
    }
}
