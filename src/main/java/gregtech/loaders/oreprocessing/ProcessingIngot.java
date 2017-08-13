package gregtech.loaders.oreprocessing;

import gregtech.api.ConfigCategories;
import gregtech.api.GT_Values;
import gregtech.api.GregTech_API;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.items.ItemList;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_RecipeRegistrator;
import gregtech.api.util.GT_Utility;
import gregtech.common.GT_Proxy;
import net.minecraft.item.ItemStack;

public class ProcessingIngot implements IOreRegistrationHandler {
    public ProcessingIngot() {
        OrePrefix.ingot.add(this);
        OrePrefix.ingotDouble.add(this);
        OrePrefix.ingotTriple.add(this);
        OrePrefix.ingotQuadruple.add(this);
        OrePrefix.ingotQuintuple.add(this);
        OrePrefix.ingotHot.add(this);
    }

    public void registerOre(OrePrefix aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        boolean aNoSmashing = aMaterial.contains(SubTag.NO_SMASHING);
        boolean aNoSmelting = aMaterial.contains(SubTag.NO_SMELTING);
        long aMaterialMass = aMaterial.getMass();
        boolean aSpecialRecipeReq = aMaterial.mUnificatable && (aMaterial.mMaterialInto == aMaterial) && !aMaterial.contains(SubTag.NO_SMASHING);

        switch (aPrefix) {
            case ingot:
                if (aMaterial.mFuelPower > 0) {
                    GT_Values.RA.addFuel(GT_Utility.copyAmount(1L, new Object[]{aStack}), null, aMaterial.mFuelPower, aMaterial.mFuelType);
                }
                if (aMaterial.mStandardMoltenFluid != null) {
                    GT_Values.RA.addFluidSolidifierRecipe(ItemList.Shape_Mold_Ingot.get(0L, new Object[0]), aMaterial.getMolten(144L), OreDictionaryUnifier.get(OrePrefix.ingot, aMaterial, 1L), 32, 8);
                }
                GT_RecipeRegistrator.registerReverseFluidSmelting(aStack, aMaterial, aPrefix.mMaterialAmount, null);
                GT_RecipeRegistrator.registerReverseMacerating(aStack, aMaterial, aPrefix.mMaterialAmount, null, null, null, false);
                if (aMaterial.mSmeltInto.mArcSmeltInto != aMaterial) {
                    GT_RecipeRegistrator.registerReverseArcSmelting(GT_Utility.copyAmount(1L, new Object[]{aStack}), aMaterial, aPrefix.mMaterialAmount, null, null, null);
                }
                ItemStack tStack;
                if ((null != (tStack = OreDictionaryUnifier.get(OrePrefix.dust, aMaterial.mMacerateInto, 1L))) && (
                        (aMaterial.mBlastFurnaceRequired) || aNoSmelting)) {
                    GT_ModHandler.removeFurnaceSmelting(tStack);
                }

                if (aMaterial.mUnificatable && (aMaterial.mMaterialInto == aMaterial) && !aMaterial.contains(SubTag.NO_WORKING)) {
                    if (!aMaterial.contains(SubTag.SMELTING_TO_GEM))
                        GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.ingot, aMaterial, 1L), GT_Proxy.tBits, new Object[]{"XXX", "XXX", "XXX", Character.valueOf('X'), OrePrefix.nugget.get(aMaterial)});
                    if ((aMaterial.contains(SubTag.MORTAR_GRINDABLE)) && (GregTech_API.sRecipeFile.get(ConfigCategories.Tools.mortar, aMaterial.mName, true)))
                        GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, aMaterial, 1L), GT_Proxy.tBits, new Object[]{"X", "m", Character.valueOf('X'), OrePrefix.ingot.get(aMaterial)});
                }

                if (!aNoSmashing) {
                    //GT_Values.RA.addWiremillRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_Utility.copy(new Object[]{OreDictionaryUnifier.get(OrePrefix.wireGt01, aMaterial, 2L), OreDictionaryUnifier.get(OrePrefix.wireFine, aMaterial, 8L)}), 100, 4);
                    //GT_Values.RA.addWiremillRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), OreDictionaryUnifier.get(OrePrefix.wireGt01, aMaterial, 2L), 100, 4);
                    GT_Values.RA.addForgeHammerRecipe(GT_Utility.copyAmount(2L, new Object[]{aStack}), OreDictionaryUnifier.get(OrePrefix.plate, aMaterial, 1L), (int) Math.max(aMaterialMass, 1L), 16);
                    GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), OreDictionaryUnifier.get(OrePrefix.plate, aMaterial, 1L), (int) Math.max(aMaterialMass * 1L, 1L), 24);
                    GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(2L, new Object[]{aStack}), OreDictionaryUnifier.get(OrePrefix.plateDouble, aMaterial, 1L), (int) Math.max(aMaterialMass * 2L, 1L), 96);
                    GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(3L, new Object[]{aStack}), OreDictionaryUnifier.get(OrePrefix.plateTriple, aMaterial, 1L), (int) Math.max(aMaterialMass * 3L, 1L), 96);
                    GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(4L, new Object[]{aStack}), OreDictionaryUnifier.get(OrePrefix.plateQuadruple, aMaterial, 1L), (int) Math.max(aMaterialMass * 4L, 1L), 96);
                    GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(5L, new Object[]{aStack}), OreDictionaryUnifier.get(OrePrefix.plateQuintuple, aMaterial, 1L), (int) Math.max(aMaterialMass * 5L, 1L), 96);
                    GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(9L, new Object[]{aStack}), OreDictionaryUnifier.get(OrePrefix.plateDense, aMaterial, 1L), (int) Math.max(aMaterialMass * 9L, 1L), 96);
                }

                GT_RecipeRegistrator.registerUsagesForMaterials(GT_Utility.copyAmount(1L, new Object[]{aStack}), OrePrefix.plate.get(aMaterial).toString(), !aNoSmashing);
                break;
            case ingotDouble:
                if (!aNoSmashing) {
                    GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), OreDictionaryUnifier.get(OrePrefix.plateDouble, aMaterial, 1L), (int) Math.max(aMaterialMass, 1L), 96);
                    GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(2L, new Object[]{aStack}), OreDictionaryUnifier.get(OrePrefix.plateQuadruple, aMaterial, 1L), (int) Math.max(aMaterialMass * 2L, 1L), 96);
                    if (aSpecialRecipeReq && GregTech_API.sRecipeFile.get(ConfigCategories.Tools.hammermultiingot, aMaterial.toString(), true)) {
                        GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.ingotDouble, aMaterial, 1L), GT_Proxy.tBits, new Object[]{"I", "I", "h", Character.valueOf('I'), OrePrefix.ingot.get(aMaterial)});
                    }
                }
                break;
            case ingotTriple:
                if (!aNoSmashing) {
                    GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), OreDictionaryUnifier.get(OrePrefix.plateTriple, aMaterial, 1L), (int) Math.max(aMaterialMass, 1L), 96);
                    GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(3L, new Object[]{aStack}), OreDictionaryUnifier.get(OrePrefix.plateDense, aMaterial, 1L), (int) Math.max(aMaterialMass * 3L, 1L), 96);
                    if (aSpecialRecipeReq && GregTech_API.sRecipeFile.get(ConfigCategories.Tools.hammermultiingot, aMaterial.toString(), true)) {
                        GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.ingotTriple, aMaterial, 1L), GT_Proxy.tBits, new Object[]{"I", "B", "h", Character.valueOf('I'), OrePrefix.ingotDouble.get(aMaterial), Character.valueOf('B'), OrePrefix.ingot.get(aMaterial)});
                    }
                }
                break;
            case ingotQuadruple:
                if (!aNoSmashing) {
                    GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), OreDictionaryUnifier.get(OrePrefix.plateQuadruple, aMaterial, 1L), (int) Math.max(aMaterialMass, 1L), 96);
                    if (aSpecialRecipeReq && GregTech_API.sRecipeFile.get(ConfigCategories.Tools.hammermultiingot, aMaterial.toString(), true)) {
                        GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.ingotQuadruple, aMaterial, 1L), GT_Proxy.tBits, new Object[]{"I", "B", "h", Character.valueOf('I'), OrePrefix.ingotTriple.get(aMaterial), Character.valueOf('B'), OrePrefix.ingot.get(aMaterial)});
                    }
                }
                break;
            case ingotQuintuple:
                if (!aNoSmashing) {
                    GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), OreDictionaryUnifier.get(OrePrefix.plateQuintuple, aMaterial, 1L), (int) Math.max(aMaterialMass, 1L), 96);
                    if (aSpecialRecipeReq && GregTech_API.sRecipeFile.get(ConfigCategories.Tools.hammermultiingot, aMaterial.toString(), true)) {
                        GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.ingotQuintuple, aMaterial, 1L), GT_Proxy.tBits, new Object[]{"I", "B", "h", Character.valueOf('I'), OrePrefix.ingotQuadruple.get(aMaterial), Character.valueOf('B'), OrePrefix.ingot.get(aMaterial)});
                    }
                }
                break;
            case ingotHot:
                GT_Values.RA.addVacuumFreezerRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), OreDictionaryUnifier.get(OrePrefix.ingot, aMaterial, 1L), (int) Math.max(aMaterialMass * 3L, 1L));
                break;
        }
    }
}
