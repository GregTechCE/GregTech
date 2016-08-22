package gregtech.loaders.oreprocessing;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.interfaces.IOreRecipeRegistrator;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingTransforming
        implements IOreRecipeRegistrator {
    public ProcessingTransforming() {
        for (OrePrefixes tPrefix : OrePrefixes.values())
            if (((tPrefix.mMaterialAmount > 0L) && (!tPrefix.mIsContainer) && (!tPrefix.mIsEnchantable)) || (tPrefix == OrePrefixes.plank))
                tPrefix.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        if (aPrefix == OrePrefixes.plank) aPrefix = OrePrefixes.plate;
        switch (aMaterial.mName) {
            case "Wood":
                GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), Materials.SeedOil.getFluid(GT_Utility.translateMaterialToAmount(aPrefix.mMaterialAmount, 120L, true)), GT_OreDictUnificator.get(aPrefix, Materials.WoodSealed, 1L), GT_Values.NI, GT_Values.NI, null, 100, 8);
                GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), Materials.SeedOilLin.getFluid(GT_Utility.translateMaterialToAmount(aPrefix.mMaterialAmount, 80L, true)), GT_OreDictUnificator.get(aPrefix, Materials.WoodSealed, 1L), GT_Values.NI, GT_Values.NI, null, 100, 8);
                GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), Materials.SeedOilHemp.getFluid(GT_Utility.translateMaterialToAmount(aPrefix.mMaterialAmount, 80L, true)), GT_OreDictUnificator.get(aPrefix, Materials.WoodSealed, 1L), GT_Values.NI, GT_Values.NI, null, 100, 8);
                break;
            case "Iron":
                GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), Materials.FierySteel.getFluid(GT_Utility.translateMaterialToAmount(aPrefix.mMaterialAmount, 250L, true)), GT_OreDictUnificator.get(aPrefix, Materials.FierySteel, 1L), GT_Values.NI, GT_Values.NI, null, 100, 8);
                GT_Values.RA.addPolarizerRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(aPrefix, Materials.IronMagnetic, 1L), (int) Math.max(16L, aPrefix.mMaterialAmount * 128L / 3628800L), 16);
                break;
            case "WroughtIron":
                GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), Materials.FierySteel.getFluid(GT_Utility.translateMaterialToAmount(aPrefix.mMaterialAmount, 225L, true)), GT_OreDictUnificator.get(aPrefix, Materials.FierySteel, 1L), GT_Values.NI, GT_Values.NI, null, 100, 8);
                GT_Values.RA.addPolarizerRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(aPrefix, Materials.IronMagnetic, 1L), (int) Math.max(16L, aPrefix.mMaterialAmount * 128L / 3628800L), 16);
                break;
            case "Steel":
                GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), Materials.FierySteel.getFluid(GT_Utility.translateMaterialToAmount(aPrefix.mMaterialAmount, 200L, true)), GT_OreDictUnificator.get(aPrefix, Materials.FierySteel, 1L), GT_Values.NI, GT_Values.NI, null, 100, 8);
                GT_Values.RA.addPolarizerRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(aPrefix, Materials.SteelMagnetic, 1L), (int) Math.max(16L, aPrefix.mMaterialAmount * 128L / 3628800L), 16);
                break;
            case "Neodymium":
                GT_Values.RA.addPolarizerRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(aPrefix, Materials.NeodymiumMagnetic, 1L), (int) Math.max(16L, aPrefix.mMaterialAmount * 128L / 3628800L), 256);
        }
    }
}
