package gregtech.loaders.oreprocessing;

import gregtech.api.enums.*;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_RecipeRegistrator;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingIngot implements gregtech.api.interfaces.IOreRecipeRegistrator {
    public ProcessingIngot() {
        OrePrefixes.ingot.add(this);
        OrePrefixes.ingotDouble.add(this);
        OrePrefixes.ingotTriple.add(this);
        OrePrefixes.ingotQuadruple.add(this);
        OrePrefixes.ingotQuintuple.add(this);
        OrePrefixes.ingotHot.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        Object[] aIngotObj = new Object[]{aStack};
        boolean aNoSmashing = aMaterial.contains(SubTag.NO_SMASHING);
        boolean aNoSmelting = aMaterial.contains(SubTag.NO_SMELTING);
        long aMaterialMass = aMaterial.getMass();

        switch (aPrefix) {
            case ingot:
                if (aMaterial.mFuelPower > 0) {
                    GT_Values.RA.addFuel(GT_Utility.copyAmount(1L, aIngotObj), null, aMaterial.mFuelPower, aMaterial.mFuelType);
                }
                if (aMaterial.mStandardMoltenFluid != null) {
                    GT_Values.RA.addFluidSolidifierRecipe(ItemList.Shape_Mold_Ingot.get(0L, new Object[0]), aMaterial.getMolten(144L), GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial, 1L), 32, 8);
                }
                GT_RecipeRegistrator.registerReverseFluidSmelting(aStack, aMaterial, aPrefix.mMaterialAmount, null);
                GT_RecipeRegistrator.registerReverseMacerating(aStack, aMaterial, aPrefix.mMaterialAmount, null, null, null, false);
                if (aMaterial.mSmeltInto.mArcSmeltInto != aMaterial) {
                    GT_RecipeRegistrator.registerReverseArcSmelting(GT_Utility.copyAmount(1L, aIngotObj), aMaterial, aPrefix.mMaterialAmount, null, null, null);
                }
                if (!aNoSmashing) {
                    GT_Values.RA.addWiremillRecipe(GT_Utility.copyAmount(1L, aIngotObj), GT_Utility.copy(new Object[]{GT_OreDictUnificator.get(OrePrefixes.wireGt01, aMaterial, 2L), GT_OreDictUnificator.get(OrePrefixes.wireFine, aMaterial, 8L)}), 100, 4);
                    GT_Values.RA.addForgeHammerRecipe(GT_Utility.copyAmount(2L, aIngotObj), GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial, 1L), (int) Math.max(aMaterialMass, 1L), 16);
                    GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(1L, aIngotObj), GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial, 1L), (int) Math.max(aMaterialMass * 1L, 1L), 24);
                    GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(2L, aIngotObj), GT_OreDictUnificator.get(OrePrefixes.plateDouble, aMaterial, 1L), (int) Math.max(aMaterialMass * 2L, 1L), 96);
                    GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(3L, aIngotObj), GT_OreDictUnificator.get(OrePrefixes.plateTriple, aMaterial, 1L), (int) Math.max(aMaterialMass * 3L, 1L), 96);
                    GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(4L, aIngotObj), GT_OreDictUnificator.get(OrePrefixes.plateQuadruple, aMaterial, 1L), (int) Math.max(aMaterialMass * 4L, 1L), 96);
                    GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(5L, aIngotObj), GT_OreDictUnificator.get(OrePrefixes.plateQuintuple, aMaterial, 1L), (int) Math.max(aMaterialMass * 5L, 1L), 96);
                    GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(9L, aIngotObj), GT_OreDictUnificator.get(OrePrefixes.plateDense, aMaterial, 1L), (int) Math.max(aMaterialMass * 9L, 1L), 96);
                }
                ItemStack tStack;
                if ((null != (tStack = GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial.mMacerateInto, 1L))) && (
                        (aMaterial.mBlastFurnaceRequired) || aNoSmelting)) {
                    GT_ModHandler.removeFurnaceSmelting(tStack);
                }

                GT_RecipeRegistrator.registerUsagesForMaterials(GT_Utility.copyAmount(1L, aIngotObj), OrePrefixes.plate.get(aMaterial).toString(), !aNoSmashing);
                break;
            case ingotDouble:
                if (!aNoSmashing) {
                    GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(1L, aIngotObj), GT_OreDictUnificator.get(OrePrefixes.plateDouble, aMaterial, 1L), (int) Math.max(aMaterialMass, 1L), 96);
                    GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(2L, aIngotObj), GT_OreDictUnificator.get(OrePrefixes.plateQuadruple, aMaterial, 1L), (int) Math.max(aMaterialMass * 2L, 1L), 96);
                }
                break;
            case ingotTriple:
                if (!aNoSmashing) {
                    GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(1L, aIngotObj), GT_OreDictUnificator.get(OrePrefixes.plateTriple, aMaterial, 1L), (int) Math.max(aMaterialMass, 1L), 96);
                    GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(3L, aIngotObj), GT_OreDictUnificator.get(OrePrefixes.plateDense, aMaterial, 1L), (int) Math.max(aMaterialMass * 3L, 1L), 96);
                }
                break;
            case ingotQuadruple: case ingotQuintuple:
                if (!aNoSmashing) {
                    GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(1L, aIngotObj), GT_OreDictUnificator.get(OrePrefixes.plateQuadruple, aMaterial, 1L), (int) Math.max(aMaterialMass, 1L), 96);
                }
                break;
            case ingotHot:
                GT_Values.RA.addVacuumFreezerRecipe(GT_Utility.copyAmount(1L, aIngotObj), GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial, 1L), (int) Math.max(aMaterialMass * 3L, 1L));
                break;
        }
    }
}
