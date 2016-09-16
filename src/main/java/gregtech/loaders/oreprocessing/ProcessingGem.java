package gregtech.loaders.oreprocessing;

import gregtech.api.enums.*;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingGem implements gregtech.api.interfaces.IOreRecipeRegistrator {
    public ProcessingGem() {
        OrePrefixes.gem.add(this);
        OrePrefixes.gemChipped.add(this);
        OrePrefixes.gemExquisite.add(this);
        OrePrefixes.gemFlawed.add(this);
        OrePrefixes.gemFlawless.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        Object[] aGemObj = new Object[]{aStack};
        long aMaterialMass = aMaterial.getMass();
        boolean aNoSmashing = aMaterial.contains(SubTag.NO_SMASHING);
        boolean aNoWorking = aMaterial.contains(SubTag.NO_WORKING);
        boolean aNoSmelting = aMaterial.contains(SubTag.NO_SMELTING);

        switch (aPrefix) {
            case gem:
                if (aMaterial.mFuelPower > 0) {
                    GT_Values.RA.addFuel(GT_Utility.copyAmount(1L, aGemObj), null, aMaterial.mFuelPower * 2, aMaterial.mFuelType);
                }
                if (!OrePrefixes.block.isIgnored(aMaterial)) {
                    GT_ModHandler.addCompressionRecipe(GT_Utility.copyAmount(9L, aGemObj), GT_OreDictUnificator.get(OrePrefixes.block, aMaterial, 1L));
                }
                if (!aMaterial.contains(SubTag.NO_SMELTING)) {
                    GT_ModHandler.addSmeltingRecipe(GT_Utility.copyAmount(1L, aGemObj), GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial.mSmeltInto, 1L));
                }
                if (aNoSmashing) {
                    GT_Values.RA.addForgeHammerRecipe(aStack, GT_OreDictUnificator.get(OrePrefixes.gemFlawed, aMaterial, 2L), 64, 16);
                } else {
                    GT_Values.RA.addForgeHammerRecipe(GT_Utility.copyAmount(1L, aGemObj), GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial, 1L), (int) Math.max(aMaterialMass, 1L), 16);
                    GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(1L, aGemObj), GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial, 1L), (int) Math.max(aMaterialMass * 2L, 1L), 24);
                    GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(2L, aGemObj), GT_OreDictUnificator.get(OrePrefixes.plateDouble, aMaterial, 1L), (int) Math.max(aMaterialMass * 2L, 1L), 96);
                    GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(3L, aGemObj), GT_OreDictUnificator.get(OrePrefixes.plateTriple, aMaterial, 1L), (int) Math.max(aMaterialMass * 3L, 1L), 96);
                    GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(4L, aGemObj), GT_OreDictUnificator.get(OrePrefixes.plateQuadruple, aMaterial, 1L), (int) Math.max(aMaterialMass * 4L, 1L), 96);
                    GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(5L, aGemObj), GT_OreDictUnificator.get(OrePrefixes.plateQuintuple, aMaterial, 1L), (int) Math.max(aMaterialMass * 5L, 1L), 96);
                    GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(9L, aGemObj), GT_OreDictUnificator.get(OrePrefixes.plateDense, aMaterial, 1L), (int) Math.max(aMaterialMass * 9L, 1L), 96);
                }

                if (aNoWorking) {
                    GT_Values.RA.addLatheRecipe(GT_Utility.copyAmount(1L, aGemObj), GT_OreDictUnificator.get(OrePrefixes.stick, aMaterial, 1L), GT_OreDictUnificator.get(OrePrefixes.dustSmall, aMaterial, 2L), (int) Math.max(aMaterialMass, 1L), 16);
                }
                gregtech.api.util.GT_RecipeRegistrator.registerUsagesForMaterials(GT_Utility.copyAmount(1L, aGemObj), OrePrefixes.plate.get(aMaterial).toString(), !aNoSmashing);

                switch (aMaterial.mName) {
                    case "NULL":
                        break;
                    case "Coal":
                    case "Charcoal":
                        if (gregtech.api.GregTech_API.sRecipeFile.get(gregtech.api.enums.ConfigCategories.Recipes.disabledrecipes, "torchesFromCoal", false)) {
                            GT_ModHandler.removeRecipe(new ItemStack[]{GT_Utility.copyAmount(1L, aGemObj), null, null, new ItemStack(net.minecraft.init.Items.stick, 1, 0)});}
                        break;
                    case "CertusQuartz":
                        GT_Values.RA.addElectrolyzerRecipe(aStack, 0, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 1), null, null, null, null, null, 2000, 30);
                }

                break;
            case gemChipped:
                if (aMaterial.mFuelPower > 0)
                    GT_Values.RA.addFuel(GT_Utility.copyAmount(1L, aGemObj), null, aMaterial.mFuelPower / 2, aMaterial.mFuelType);
                if (!aNoWorking)
                    GT_Values.RA.addLatheRecipe(GT_Utility.copyAmount(1L, aGemObj), GT_OreDictUnificator.get(OrePrefixes.bolt, aMaterial, 1L), GT_OreDictUnificator.get(OrePrefixes.dustTiny, aMaterial, 1L), (int) Math.max(aMaterialMass, 1L), 8);
                break;
            case gemExquisite:
                if (aMaterial.mFuelPower > 0)
                    GT_Values.RA.addFuel(GT_Utility.copyAmount(1L, aGemObj), null, aMaterial.mFuelPower * 8, aMaterial.mFuelType);
                if (!aNoWorking)
                    GT_Values.RA.addLatheRecipe(GT_Utility.copyAmount(1L, aGemObj), GT_OreDictUnificator.get(OrePrefixes.stickLong, aMaterial, 3L), GT_OreDictUnificator.getDust(aMaterial, aPrefix.mMaterialAmount - OrePrefixes.stickLong.mMaterialAmount * 3L), (int) Math.max(aMaterialMass * 10L, 1L), 16);
                GT_Values.RA.addForgeHammerRecipe(aStack, GT_OreDictUnificator.get(OrePrefixes.gemFlawless, aMaterial, 2L), 64, 16);
                break;
            case gemFlawed:
                if (aMaterial.mFuelPower > 0)
                    GT_Values.RA.addFuel(GT_Utility.copyAmount(1L, aGemObj), null, aMaterial.mFuelPower, aMaterial.mFuelType);
                if (!aNoWorking)
                    GT_Values.RA.addLatheRecipe(GT_Utility.copyAmount(1L, aGemObj), GT_OreDictUnificator.get(OrePrefixes.bolt, aMaterial, 2L), GT_OreDictUnificator.get(OrePrefixes.dustSmall, aMaterial, 1L), (int) Math.max(aMaterialMass, 1L), 12);
                GT_Values.RA.addForgeHammerRecipe(aStack, GT_OreDictUnificator.get(OrePrefixes.gemChipped, aMaterial, 2L), 64, 16);
                break;
            case gemFlawless:
                if (aMaterial.mFuelPower > 0)
                    GT_Values.RA.addFuel(GT_Utility.copyAmount(1L, aGemObj), null, aMaterial.mFuelPower * 4, aMaterial.mFuelType);
                if (!aNoWorking)
                    GT_Values.RA.addLatheRecipe(GT_Utility.copyAmount(1L, aGemObj), GT_OreDictUnificator.get(OrePrefixes.stickLong, aMaterial, 1L), GT_OreDictUnificator.getDust(aMaterial, aPrefix.mMaterialAmount - OrePrefixes.stickLong.mMaterialAmount), (int) Math.max(aMaterialMass * 5L, 1L), 16);
                GT_Values.RA.addForgeHammerRecipe(aStack, GT_OreDictUnificator.get(OrePrefixes.gem, aMaterial, 2L), 64, 16);
                break;
        }
    }
}
