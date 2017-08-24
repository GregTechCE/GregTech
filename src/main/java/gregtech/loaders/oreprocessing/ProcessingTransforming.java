package gregtech.loaders.oreprocessing;

import gregtech.api.GTValues;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
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
        switch (uEntry.material.toString()) {
            case "Wood":
                GTValues.RA.addChemicalBathRecipe(GTUtility.copyAmount(1, stack), Materials.SeedOil.getFluid(GTUtility.translateMaterialToAmount(prefix.mMaterialAmount, 120L, true)), OreDictionaryUnifier.get(prefix, Materials.WoodSealed, 1), GTValues.NI, GTValues.NI, null, 100, 8);
                GTValues.RA.addChemicalBathRecipe(GTUtility.copyAmount(1, stack), Materials.SeedOilLin.getFluid(GTUtility.translateMaterialToAmount(prefix.mMaterialAmount, 80L, true)), OreDictionaryUnifier.get(prefix, Materials.WoodSealed, 1), GTValues.NI, GTValues.NI, null, 100, 8);
                GTValues.RA.addChemicalBathRecipe(GTUtility.copyAmount(1, stack), Materials.SeedOilHemp.getFluid(GTUtility.translateMaterialToAmount(prefix.mMaterialAmount, 80L, true)), OreDictionaryUnifier.get(prefix, Materials.WoodSealed, 1), GTValues.NI, GTValues.NI, null, 100, 8);
                break;
            case "Iron":
                //TODO GTValues.RA.addChemicalBathRecipe(GTUtility.copyAmount(1, stack), Materials.FierySteel.getFluid(GTUtility.translateMaterialToAmount(prefix.mMaterialAmount, 250, true)), OreDictionaryUnifier.get(prefix, Materials.FierySteel, 1), GTValues.NI, GTValues.NI, null, 100, 8);
                GTValues.RA.addPolarizerRecipe(GTUtility.copyAmount(1, stack), OreDictionaryUnifier.get(prefix, Materials.IronMagnetic, 1), (int) Math.max(16, prefix.mMaterialAmount * 128L / 3628800L), 16);
                break;
            case "WroughtIron":
                //TODO GTValues.RA.addChemicalBathRecipe(GTUtility.copyAmount(1, stack), Materials.FierySteel.getFluid(GTUtility.translateMaterialToAmount(prefix.mMaterialAmount, 225L, true)), OreDictionaryUnifier.get(prefix, Materials.FierySteel, 1), GTValues.NI, GTValues.NI, null, 100, 8);
                GTValues.RA.addPolarizerRecipe(GTUtility.copyAmount(1, stack), OreDictionaryUnifier.get(prefix, Materials.IronMagnetic, 1), (int) Math.max(16, prefix.mMaterialAmount * 128L / 3628800L), 16);
                break;
            case "Steel":
                //TODO GTValues.RA.addChemicalBathRecipe(GTUtility.copyAmount(1, stack), Materials.FierySteel.getFluid(GTUtility.translateMaterialToAmount(prefix.mMaterialAmount, 200L, true)), OreDictionaryUnifier.get(prefix, Materials.FierySteel, 1), GTValues.NI, GTValues.NI, null, 100, 8);
                GTValues.RA.addPolarizerRecipe(GTUtility.copyAmount(1, stack), OreDictionaryUnifier.get(prefix, Materials.SteelMagnetic, 1), (int) Math.max(16, prefix.mMaterialAmount * 128L / 3628800L), 16);
                break;
            case "Neodymium":
                GTValues.RA.addPolarizerRecipe(GTUtility.copyAmount(1, stack), OreDictionaryUnifier.get(prefix, Materials.NeodymiumMagnetic, 1), (int) Math.max(16, prefix.mMaterialAmount * 128L / 3628800L), 256);
        }
    }
}
