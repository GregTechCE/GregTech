package gregtech.loaders.oreprocessing;

import gregtech.api.GT_Values;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingTransforming
        implements IOreRegistrationHandler {
    public ProcessingTransforming() {
        for (OrePrefix tPrefix : OrePrefix.values())
            if (((tPrefix.mMaterialAmount > 0L) && (!tPrefix.mIsContainer) && (!tPrefix.mIsEnchantable)) || (tPrefix == OrePrefix.plank))
                tPrefix.addProcessingHandler(this);
    }

    public void registerOre(UnificationEntry uEntry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();
        OrePrefix prefix = uEntry.orePrefix;
        if (prefix == OrePrefix.plank) prefix = OrePrefix.plate;
        switch (uEntry.material.defaultLocalName) {
            case "Wood":
                GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1, stack), Materials.SeedOil.getFluid(GT_Utility.translateMaterialToAmount(prefix.mMaterialAmount, 120L, true)), OreDictionaryUnifier.get(prefix, Materials.WoodSealed, 1), GT_Values.NI, GT_Values.NI, null, 100, 8);
                GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1, stack), Materials.SeedOilLin.getFluid(GT_Utility.translateMaterialToAmount(prefix.mMaterialAmount, 80L, true)), OreDictionaryUnifier.get(prefix, Materials.WoodSealed, 1), GT_Values.NI, GT_Values.NI, null, 100, 8);
                GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1, stack), Materials.SeedOilHemp.getFluid(GT_Utility.translateMaterialToAmount(prefix.mMaterialAmount, 80L, true)), OreDictionaryUnifier.get(prefix, Materials.WoodSealed, 1), GT_Values.NI, GT_Values.NI, null, 100, 8);
                break;
            case "Iron":
                GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1, stack), Materials.FierySteel.getFluid(GT_Utility.translateMaterialToAmount(prefix.mMaterialAmount, 250, true)), OreDictionaryUnifier.get(prefix, Materials.FierySteel, 1), GT_Values.NI, GT_Values.NI, null, 100, 8);
                GT_Values.RA.addPolarizerRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(prefix, Materials.IronMagnetic, 1), (int) Math.max(16, prefix.mMaterialAmount * 128L / 3628800L), 16);
                break;
            case "WroughtIron":
                GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1, stack), Materials.FierySteel.getFluid(GT_Utility.translateMaterialToAmount(prefix.mMaterialAmount, 225L, true)), OreDictionaryUnifier.get(prefix, Materials.FierySteel, 1), GT_Values.NI, GT_Values.NI, null, 100, 8);
                GT_Values.RA.addPolarizerRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(prefix, Materials.IronMagnetic, 1), (int) Math.max(16, prefix.mMaterialAmount * 128L / 3628800L), 16);
                break;
            case "Steel":
                GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1, stack), Materials.FierySteel.getFluid(GT_Utility.translateMaterialToAmount(prefix.mMaterialAmount, 200L, true)), OreDictionaryUnifier.get(prefix, Materials.FierySteel, 1), GT_Values.NI, GT_Values.NI, null, 100, 8);
                GT_Values.RA.addPolarizerRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(prefix, Materials.SteelMagnetic, 1), (int) Math.max(16, prefix.mMaterialAmount * 128L / 3628800L), 16);
                break;
            case "Neodymium":
                GT_Values.RA.addPolarizerRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(prefix, Materials.NeodymiumMagnetic, 1), (int) Math.max(16, prefix.mMaterialAmount * 128L / 3628800L), 256);
        }
    }
}
