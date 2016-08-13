package gregtech.loaders.oreprocessing;

import gregtech.api.enums.*;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingGem implements gregtech.api.interfaces.IOreRecipeRegistrator {
    public ProcessingGem() {
        OrePrefixes.gem.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        Object[] Ogem0 = new Object[]{aStack};
        if (aMaterial.mFuelPower > 0) {
            GT_Values.RA.addFuel(GT_Utility.copyAmount(1L, Ogem0), null, aMaterial.mFuelPower * 2, aMaterial.mFuelType);
        }
        GT_Values.RA.addBoxingRecipe(GT_Utility.copyAmount(16L, Ogem0), ItemList.Crate_Empty.get(1L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.crateGtGem, aMaterial, 1L), 100, 8);
        GT_Values.RA.addUnboxingRecipe(GT_OreDictUnificator.get(OrePrefixes.crateGtGem, aMaterial, 1L), GT_OreDictUnificator.get(OrePrefixes.gem, aMaterial, 16L), ItemList.Crate_Empty.get(1L, new Object[0]), 800, 1);

        if (!OrePrefixes.block.isIgnored(aMaterial)) {
            GT_ModHandler.addCompressionRecipe(GT_Utility.copyAmount(9L, Ogem0), GT_OreDictUnificator.get(OrePrefixes.block, aMaterial, 1L));
        }
        if (!aMaterial.contains(SubTag.NO_SMELTING)) {
            GT_ModHandler.addSmeltingRecipe(GT_Utility.copyAmount(1L, Ogem0), GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial.mSmeltInto, 1L));
        }
        boolean BmatNSs0 = aMaterial.contains(SubTag.NO_SMASHING);
        if (BmatNSs0) {
            GT_Values.RA.addForgeHammerRecipe(aStack, GT_OreDictUnificator.get(OrePrefixes.gemFlawed, aMaterial, 2L), 64, 16);
        } else {
            long matgM0 = aMaterial.getMass();
            GT_Values.RA.addForgeHammerRecipe(GT_Utility.copyAmount(1L, Ogem0), GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial, 1L), (int) Math.max(matgM0, 1L), 16);
            GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(1L, Ogem0), GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial, 1L), (int) Math.max(matgM0 * 2L, 1L), 24);
            GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(2L, Ogem0), GT_OreDictUnificator.get(OrePrefixes.plateDouble, aMaterial, 1L), (int) Math.max(matgM0 * 2L, 1L), 96);
            GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(3L, Ogem0), GT_OreDictUnificator.get(OrePrefixes.plateTriple, aMaterial, 1L), (int) Math.max(matgM0 * 3L, 1L), 96);
            GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(4L, Ogem0), GT_OreDictUnificator.get(OrePrefixes.plateQuadruple, aMaterial, 1L), (int) Math.max(matgM0 * 4L, 1L), 96);
            GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(5L, Ogem0), GT_OreDictUnificator.get(OrePrefixes.plateQuintuple, aMaterial, 1L), (int) Math.max(matgM0 * 5L, 1L), 96);
            GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(9L, Ogem0), GT_OreDictUnificator.get(OrePrefixes.plateDense, aMaterial, 1L), (int) Math.max(matgM0 * 9L, 1L), 96);
        }

        if (!aMaterial.contains(SubTag.NO_WORKING)) {
            long matgM0 = aMaterial.getMass();
            GT_Values.RA.addLatheRecipe(GT_Utility.copyAmount(1L, Ogem0), GT_OreDictUnificator.get(OrePrefixes.stick, aMaterial, 1L), GT_OreDictUnificator.get(OrePrefixes.dustSmall, aMaterial, 2L), (int) Math.max(matgM0, 1L), 16);
        }
        gregtech.api.util.GT_RecipeRegistrator.registerUsagesForMaterials(GT_Utility.copyAmount(1L, Ogem0), OrePrefixes.plate.get(aMaterial).toString(), !BmatNSs0);

        switch (aMaterial) {
            case _NULL:
                break;
            case Coal:
            case Charcoal:
                if (gregtech.api.GregTech_API.sRecipeFile.get(gregtech.api.enums.ConfigCategories.Recipes.disabledrecipes, "torchesFromCoal", false)) {
                    GT_ModHandler.removeRecipe(new ItemStack[]{GT_Utility.copyAmount(1L, Ogem0), null, null, new ItemStack(net.minecraft.init.Items.stick, 1, 0)});}
                break;
            case CertusQuartz:
                GT_Values.RA.addElectrolyzerRecipe(aStack, 0, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 1), null, null, null, null, null, 2000, 30);
        }
    }
}
