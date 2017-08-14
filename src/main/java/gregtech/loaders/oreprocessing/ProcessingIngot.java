package gregtech.loaders.oreprocessing;

import gregtech.api.ConfigCategories;
import gregtech.api.GT_Values;
import gregtech.api.GregTech_API;
import gregtech.api.items.ItemList;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
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

    public void registerOre(UnificationEntry uEntry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();
        boolean aNoSmashing = uEntry.material.contains(SubTag.NO_SMASHING);
        boolean aNoSmelting = uEntry.material.contains(SubTag.NO_SMELTING);
        long materialMass = uEntry.material.getMass();
        boolean aSpecialRecipeReq = uEntry.material.mUnificatable && (uEntry.material.mMaterialInto == uEntry.material) && !uEntry.material.contains(SubTag.NO_SMASHING);

        switch (uEntry.orePrefix) {
            case ingot:
                if (uEntry.material.mFuelPower > 0) {
                    GT_Values.RA.addFuel(GT_Utility.copyAmount(1, stack), null, uEntry.material.mFuelPower, uEntry.material.mFuelType);
                }
                if (uEntry.material.mStandardMoltenFluid != null) {
                    GT_Values.RA.addFluidSolidifierRecipe(ItemList.Shape_Mold_Ingot.get(0), uEntry.material.getMolten(144L), OreDictionaryUnifier.get(OrePrefix.ingot, uEntry.material, 1L), 32, 8);
                }
                GT_RecipeRegistrator.registerReverseFluidSmelting(stack, uEntry.material, uEntry.orePrefix.mMaterialAmount, null);
                GT_RecipeRegistrator.registerReverseMacerating(stack, uEntry.material, uEntry.orePrefix.mMaterialAmount, null, null, null, false);
                if (uEntry.material.mSmeltInto.mArcSmeltInto != uEntry.material) {
                    GT_RecipeRegistrator.registerReverseArcSmelting(GT_Utility.copyAmount(1, stack), uEntry.material, uEntry.orePrefix.mMaterialAmount, null, null, null);
                }
                ItemStack tStack;
                if ((null != (tStack = OreDictionaryUnifier.get(OrePrefix.dust, uEntry.material.mMacerateInto, 1L))) && (
                        (uEntry.material.mBlastFurnaceRequired) || aNoSmelting)) {
                    ModHandler.removeFurnaceSmelting(tStack);
                }

                if (uEntry.material.mUnificatable && (uEntry.material.mMaterialInto == uEntry.material) && !uEntry.material.contains(SubTag.NO_WORKING)) {
                    if (!uEntry.material.contains(SubTag.SMELTING_TO_GEM))
                        ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.ingot, uEntry.material, 1L), GT_Proxy.tBits, new Object[]{"XXX", "XXX", "XXX", Character.valueOf('X'), OrePrefix.nugget.get(uEntry.material)});
                    if ((uEntry.material.contains(SubTag.MORTAR_GRINDABLE)) && (GregTech_API.sRecipeFile.get(ConfigCategories.Tools.mortar, uEntry.material.mName, true)))
                        ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, uEntry.material, 1L), GT_Proxy.tBits, new Object[]{"X", "m", Character.valueOf('X'), OrePrefix.ingot.get(uEntry.material)});
                }

                if (!aNoSmashing) {
                    //GT_Values.RA.addWiremillRecipe(GT_Utility.copyAmount(1L, new Object[]{stack}), GT_Utility.copy(new Object[]{OreDictionaryUnifier.get(OrePrefixes.wireGt01, uEntry.material, 2L), OreDictionaryUnifier.get(OrePrefixes.wireFine, uEntry.material, 8L)}), 100, 4);
                    //GT_Values.RA.addWiremillRecipe(GT_Utility.copyAmount(1L, new Object[]{stack}), OreDictionaryUnifier.get(OrePrefixes.wireGt01, uEntry.material, 2L), 100, 4);
                    GT_Values.RA.addForgeHammerRecipe(GT_Utility.copyAmount(2, stack), OreDictionaryUnifier.get(OrePrefix.plate, uEntry.material, 1L), (int) Math.max(uEntry.materialMass, 1L), 16);
                    GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.plate, uEntry.material, 1L), (int) Math.max(uEntry.materialMass * 1L, 1L), 24);
                    GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(2, stack), OreDictionaryUnifier.get(OrePrefix.plateDouble, uEntry.material, 1L), (int) Math.max(uEntry.materialMass * 2L, 1L), 96);
                    GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(3, stack), OreDictionaryUnifier.get(OrePrefix.plateTriple, uEntry.material, 1L), (int) Math.max(uEntry.materialMass * 3L, 1L), 96);
                    GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(4, stack), OreDictionaryUnifier.get(OrePrefix.plateQuadruple, uEntry.material, 1L), (int) Math.max(uEntry.materialMass * 4L, 1L), 96);
                    GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(5, stack), OreDictionaryUnifier.get(OrePrefix.plateQuintuple, uEntry.material, 1L), (int) Math.max(uEntry.materialMass * 5L, 1L), 96);
                    GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(9, stack), OreDictionaryUnifier.get(OrePrefix.plateDense, uEntry.material, 1L), (int) Math.max(uEntry.materialMass * 9L, 1L), 96);
                }

                GT_RecipeRegistrator.registerUsagesForMaterials(GT_Utility.copyAmount(1, stack), OrePrefix.plate.get(uEntry.material).toString(), !aNoSmashing);
                break;
            case ingotDouble:
                if (!aNoSmashing) {
                    GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.plateDouble, uEntry.material, 1), (int) Math.max(uEntry.materialMass, 1L), 96);
                    GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(2, stack), OreDictionaryUnifier.get(OrePrefix.plateQuadruple, uEntry.material, 1), (int) Math.max(uEntry.materialMass * 2L, 1L), 96);
                    if (aSpecialRecipeReq && GregTech_API.sRecipeFile.get(ConfigCategories.Tools.hammermultiingot, uEntry.material.toString(), true)) {
                        ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.ingotDouble, uEntry.material, 1), GT_Proxy.tBits, new Object[]{"I", "I", "h", Character.valueOf('I'), OrePrefix.ingot.get(uEntry.material)});
                    }
                }
                break;
            case ingotTriple:
                if (!aNoSmashing) {
                    GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.plateTriple, uEntry.material, 1), (int) Math.max(uEntry.materialMass, 1L), 96);
                    GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(3, stack), OreDictionaryUnifier.get(OrePrefix.plateDense, uEntry.material, 1), (int) Math.max(uEntry.materialMass * 3L, 1L), 96);
                    if (aSpecialRecipeReq && GregTech_API.sRecipeFile.get(ConfigCategories.Tools.hammermultiingot, uEntry.material.toString(), true)) {
                        ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.ingotTriple, uEntry.material, 1), GT_Proxy.tBits, new Object[]{"I", "B", "h", Character.valueOf('I'), OrePrefix.ingotDouble.get(uEntry.material), Character.valueOf('B'), OrePrefix.ingot.get(uEntry.material)});
                    }
                }
                break;
            case ingotQuadruple:
                if (!aNoSmashing) {
                    GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.plateQuadruple, uEntry.material, 1), (int) Math.max(uEntry.materialMass, 1L), 96);
                    if (aSpecialRecipeReq && GregTech_API.sRecipeFile.get(ConfigCategories.Tools.hammermultiingot, uEntry.material.toString(), true)) {
                        ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.ingotQuadruple, uEntry.material, 1), GT_Proxy.tBits, new Object[]{"I", "B", "h", Character.valueOf('I'), OrePrefix.ingotTriple.get(uEntry.material), Character.valueOf('B'), OrePrefix.ingot.get(uEntry.material)});
                    }
                }
                break;
            case ingotQuintuple:
                if (!aNoSmashing) {
                    GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.plateQuintuple, uEntry.material, 1), (int) Math.max(uEntry.materialMass, 1L), 96);
                    if (aSpecialRecipeReq && GregTech_API.sRecipeFile.get(ConfigCategories.Tools.hammermultiingot, uEntry.material.toString(), true)) {
                        ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.ingotQuintuple, uEntry.material, 1), GT_Proxy.tBits, new Object[]{"I", "B", "h", Character.valueOf('I'), OrePrefix.ingotQuadruple.get(uEntry.material), Character.valueOf('B'), OrePrefix.ingot.get(uEntry.material)});
                    }
                }
                break;
            case ingotHot:
                GT_Values.RA.addVacuumFreezerRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.ingot, uEntry.material, 1), (int) Math.max(uEntry.materialMass * 3L, 1L));
                break;
        }
    }
}
