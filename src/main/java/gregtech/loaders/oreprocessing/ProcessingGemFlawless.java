package gregtech.loaders.oreprocessing;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingGemFlawless implements gregtech.api.interfaces.IOreRecipeRegistrator {
    public ProcessingGemFlawless() {
        OrePrefixes.gemFlawless.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        if (aMaterial.mFuelPower > 0)
            GT_Values.RA.addFuel(GT_Utility.copyAmount(1L, new Object[]{aStack}), null, aMaterial.mFuelPower * 4, aMaterial.mFuelType);
        if (!aMaterial.contains(gregtech.api.enums.SubTag.NO_WORKING))
            GT_Values.RA.addLatheRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.stickLong, aMaterial, 1L), GT_OreDictUnificator.getDust(aMaterial, aPrefix.mMaterialAmount - OrePrefixes.stickLong.mMaterialAmount), (int) Math.max(aMaterial.getMass() * 5L, 1L), 16);
        GT_Values.RA.addForgeHammerRecipe(aStack, GT_OreDictUnificator.get(OrePrefixes.gem, aMaterial, 2L), 64, 16);
    }
}
