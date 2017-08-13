package gregtech.loaders.oreprocessing;

import gregtech.api.GT_Values;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingTransforming
        implements IOreRegistrationHandler {
    public ProcessingTransforming() {
        for (OrePrefix tPrefix : OrePrefix.values())
            if (((tPrefix.mMaterialAmount > 0L) && (!tPrefix.mIsContainer) && (!tPrefix.mIsEnchantable)) || (tPrefix == OrePrefix.plank))
                tPrefix.add(this);
    }

    public void registerOre(OrePrefix aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        if (aPrefix == OrePrefix.plank) aPrefix = OrePrefix.plate;
        switch (aMaterial.mName) {
            case "Wood":
                GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), Materials.SeedOil.getFluid(GT_Utility.translateMaterialToAmount(aPrefix.mMaterialAmount, 120L, true)), OreDictionaryUnifier.get(aPrefix, Materials.WoodSealed, 1L), GT_Values.NI, GT_Values.NI, null, 100, 8);
                GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), Materials.SeedOilLin.getFluid(GT_Utility.translateMaterialToAmount(aPrefix.mMaterialAmount, 80L, true)), OreDictionaryUnifier.get(aPrefix, Materials.WoodSealed, 1L), GT_Values.NI, GT_Values.NI, null, 100, 8);
                GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), Materials.SeedOilHemp.getFluid(GT_Utility.translateMaterialToAmount(aPrefix.mMaterialAmount, 80L, true)), OreDictionaryUnifier.get(aPrefix, Materials.WoodSealed, 1L), GT_Values.NI, GT_Values.NI, null, 100, 8);
                break;
            case "Iron":
                GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), Materials.FierySteel.getFluid(GT_Utility.translateMaterialToAmount(aPrefix.mMaterialAmount, 250L, true)), OreDictionaryUnifier.get(aPrefix, Materials.FierySteel, 1L), GT_Values.NI, GT_Values.NI, null, 100, 8);
                GT_Values.RA.addPolarizerRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), OreDictionaryUnifier.get(aPrefix, Materials.IronMagnetic, 1L), (int) Math.max(16L, aPrefix.mMaterialAmount * 128L / 3628800L), 16);
                break;
            case "WroughtIron":
                GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), Materials.FierySteel.getFluid(GT_Utility.translateMaterialToAmount(aPrefix.mMaterialAmount, 225L, true)), OreDictionaryUnifier.get(aPrefix, Materials.FierySteel, 1L), GT_Values.NI, GT_Values.NI, null, 100, 8);
                GT_Values.RA.addPolarizerRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), OreDictionaryUnifier.get(aPrefix, Materials.IronMagnetic, 1L), (int) Math.max(16L, aPrefix.mMaterialAmount * 128L / 3628800L), 16);
                break;
            case "Steel":
                GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), Materials.FierySteel.getFluid(GT_Utility.translateMaterialToAmount(aPrefix.mMaterialAmount, 200L, true)), OreDictionaryUnifier.get(aPrefix, Materials.FierySteel, 1L), GT_Values.NI, GT_Values.NI, null, 100, 8);
                GT_Values.RA.addPolarizerRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), OreDictionaryUnifier.get(aPrefix, Materials.SteelMagnetic, 1L), (int) Math.max(16L, aPrefix.mMaterialAmount * 128L / 3628800L), 16);
                break;
            case "Neodymium":
                GT_Values.RA.addPolarizerRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), OreDictionaryUnifier.get(aPrefix, Materials.NeodymiumMagnetic, 1L), (int) Math.max(16L, aPrefix.mMaterialAmount * 128L / 3628800L), 256);
        }
    }
}
