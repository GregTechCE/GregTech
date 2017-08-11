package gregtech.loaders.oreprocessing;

import gregtech.api.GT_Values;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefixes;
import gregtech.api.unification.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingRecycling implements IOreRegistrationHandler {
    public ProcessingRecycling() {
        for (OrePrefixes tPrefix : OrePrefixes.values())
            if ((tPrefix.mIsMaterialBased) && (tPrefix.mMaterialAmount > 0L) && (tPrefix.mIsContainer))
                tPrefix.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        if ((aMaterial != Materials.Empty) && (GT_Utility.getFluidForFilledItem(aStack, true) == null))
            GT_Values.RA.addCannerRecipe(aStack, null, GT_Utility.getContainerItem(aStack, true), GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, aPrefix.mMaterialAmount / 3628800L), (int) Math.max(aMaterial.getMass() / 2L, 1L), 2);
    }
}
