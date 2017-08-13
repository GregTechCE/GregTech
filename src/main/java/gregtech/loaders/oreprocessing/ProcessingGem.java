package gregtech.loaders.oreprocessing;

import gregtech.api.ConfigCategories;
import gregtech.api.GT_Values;
import gregtech.api.GregTech_API;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import gregtech.common.GT_Proxy;
import net.minecraft.item.ItemStack;

public class ProcessingGem implements IOreRegistrationHandler {
    public ProcessingGem() {
        OrePrefix.gem.add(this);
        OrePrefix.gemChipped.add(this);
        OrePrefix.gemExquisite.add(this);
        OrePrefix.gemFlawed.add(this);
        OrePrefix.gemFlawless.add(this);
    }

    public void registerOre(OrePrefix aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        long aMaterialMass = aMaterial.getMass();
        boolean aNoSmashing = aMaterial.contains(SubTag.NO_SMASHING);
        boolean aNoWorking = aMaterial.contains(SubTag.NO_WORKING);
        boolean aNoSmelting = aMaterial.contains(SubTag.NO_SMELTING);
        boolean aSpecialRecipeReq = (aMaterial.contains(SubTag.MORTAR_GRINDABLE)) && (GregTech_API.sRecipeFile.get(ConfigCategories.Tools.mortar, aMaterial.mName, true));
        boolean aFuelPower = aMaterial.mFuelPower > 0;

        switch (aPrefix) {
            case gem:
                if (aFuelPower) {
                    GT_Values.RA.addFuel(GT_Utility.copyAmount(1L, aStack), null, aMaterial.mFuelPower * 2, aMaterial.mFuelType);
                }
                if (!OrePrefix.block.isIgnored(aMaterial)) {
                    GT_ModHandler.addCompressionRecipe(GT_Utility.copyAmount(9L, aStack), OreDictionaryUnifier.get(OrePrefix.block, aMaterial, 1L));
                }
                if (!aNoSmelting) {
                    GT_ModHandler.addSmeltingRecipe(GT_Utility.copyAmount(1L, aStack), OreDictionaryUnifier.get(OrePrefix.ingot, aMaterial.mSmeltInto, 1L));
                }
                if (aNoSmashing) {
                    GT_Values.RA.addForgeHammerRecipe(aStack, OreDictionaryUnifier.get(OrePrefix.gemFlawed, aMaterial, 2L), 64, 16);
                } else {
                    GT_Values.RA.addForgeHammerRecipe(GT_Utility.copyAmount(1L, aStack), OreDictionaryUnifier.get(OrePrefix.plate, aMaterial, 1L), (int) Math.max(aMaterialMass, 1L), 16);
                    GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(1L, aStack), OreDictionaryUnifier.get(OrePrefix.plate, aMaterial, 1L), (int) Math.max(aMaterialMass * 2L, 1L), 24);
                    GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(2L, aStack), OreDictionaryUnifier.get(OrePrefix.plateDouble, aMaterial, 1L), (int) Math.max(aMaterialMass * 2L, 1L), 96);
                    GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(3L, aStack), OreDictionaryUnifier.get(OrePrefix.plateTriple, aMaterial, 1L), (int) Math.max(aMaterialMass * 3L, 1L), 96);
                    GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(4L, aStack), OreDictionaryUnifier.get(OrePrefix.plateQuadruple, aMaterial, 1L), (int) Math.max(aMaterialMass * 4L, 1L), 96);
                    GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(5L, aStack), OreDictionaryUnifier.get(OrePrefix.plateQuintuple, aMaterial, 1L), (int) Math.max(aMaterialMass * 5L, 1L), 96);
                    GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(9L, aStack), OreDictionaryUnifier.get(OrePrefix.plateDense, aMaterial, 1L), (int) Math.max(aMaterialMass * 9L, 1L), 96);
                }

                if (aNoWorking) {
                    GT_Values.RA.addLatheRecipe(GT_Utility.copyAmount(1L, aStack), OreDictionaryUnifier.get(OrePrefix.stick, aMaterial, 1L), OreDictionaryUnifier.get(OrePrefix.dustSmall, aMaterial, 2L), (int) Math.max(aMaterialMass, 1L), 16);
                } else {
                    if (aMaterial.mUnificatable && (aMaterial.mMaterialInto == aMaterial)) {
                        GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.gem, aMaterial, 2L), GT_Proxy.tBits, new Object[]{"h", "X", Character.valueOf('X'), OrePrefix.gemFlawless.get(aMaterial)});
                        if (aMaterial.contains(SubTag.SMELTING_TO_GEM)) GT_ModHandler.addCraftingRecipe(GT_Utility.copyAmount(1L, aStack), GT_Proxy.tBits, new Object[]{"XXX", "XXX", "XXX", Character.valueOf('X'), OrePrefix.nugget.get(aMaterial)});
                        if (aSpecialRecipeReq) GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, aMaterial, 1L), GT_Proxy.tBits, new Object[]{"X", "m", Character.valueOf('X'), OrePrefix.gem.get(aMaterial)});
                    }
                }
                gregtech.api.util.GT_RecipeRegistrator.registerUsagesForMaterials(GT_Utility.copyAmount(1L, aStack), OrePrefix.plate.get(aMaterial).toString(), !aNoSmashing);

                switch (aMaterial.mName) {
                    case "NULL":
                        break;
                    case "Coal":
                    case "Charcoal":
                        if (gregtech.api.GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "torchesFromCoal", false)) {
                            GT_ModHandler.removeRecipe(new ItemStack[]{GT_Utility.copyAmount(1L, aStack), null, null, new ItemStack(net.minecraft.init.Items.STICK, 1, 0)});}
                        break;
                    case "CertusQuartz":
                        GT_Values.RA.addElectrolyzerRecipe(aStack, 0, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 1), null, null, null, null, null, 2000, 30);
                }

                break;
            case gemChipped:
                if (aFuelPower)
                    GT_Values.RA.addFuel(GT_Utility.copyAmount(1L, aStack), null, aMaterial.mFuelPower / 2, aMaterial.mFuelType);
                if (!aNoWorking) {
                    GT_Values.RA.addLatheRecipe(GT_Utility.copyAmount(1L, aStack), OreDictionaryUnifier.get(OrePrefix.bolt, aMaterial, 1L), OreDictionaryUnifier.get(OrePrefix.dustTiny, aMaterial, 1L), (int) Math.max(aMaterialMass, 1L), 8);
                    if (aMaterial.mUnificatable && (aMaterial.mMaterialInto == aMaterial)) {
                        GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.gemChipped, aMaterial, 2L), GT_Proxy.tBits, new Object[]{"h", "X", Character.valueOf('X'), OrePrefix.gemFlawed.get(aMaterial)});
                        if (aSpecialRecipeReq)
                            GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dustSmall, aMaterial, 1L), GT_Proxy.tBits, new Object[]{"X", "m", Character.valueOf('X'), OrePrefix.gemChipped.get(aMaterial)});
                    }
                }
                break;
            case gemExquisite:
                if (aFuelPower)
                    GT_Values.RA.addFuel(GT_Utility.copyAmount(1L, aStack), null, aMaterial.mFuelPower * 8, aMaterial.mFuelType);
                if (!aNoWorking) {
                    GT_Values.RA.addLatheRecipe(GT_Utility.copyAmount(1L, aStack), OreDictionaryUnifier.get(OrePrefix.stickLong, aMaterial, 3L), OreDictionaryUnifier.getDust(aMaterial, aPrefix.mMaterialAmount - OrePrefix.stickLong.mMaterialAmount * 3L), (int) Math.max(aMaterialMass * 10L, 1L), 16);
                    if (aMaterial.mUnificatable && (aMaterial.mMaterialInto == aMaterial))
                        if (aSpecialRecipeReq) GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, aMaterial, 4L), GT_Proxy.tBits, new Object[]{"X", "m", Character.valueOf('X'), OrePrefix.gemExquisite.get(aMaterial)});
                }
                GT_Values.RA.addForgeHammerRecipe(aStack, OreDictionaryUnifier.get(OrePrefix.gemFlawless, aMaterial, 2L), 64, 16);
                break;
            case gemFlawed:
                if (aFuelPower)
                    GT_Values.RA.addFuel(GT_Utility.copyAmount(1L, aStack), null, aMaterial.mFuelPower, aMaterial.mFuelType);
                if (!aNoWorking) {
                    GT_Values.RA.addLatheRecipe(GT_Utility.copyAmount(1L, aStack), OreDictionaryUnifier.get(OrePrefix.bolt, aMaterial, 2L), OreDictionaryUnifier.get(OrePrefix.dustSmall, aMaterial, 1L), (int) Math.max(aMaterialMass, 1L), 12);
                    if (aMaterial.mUnificatable && (aMaterial.mMaterialInto == aMaterial)) {
                        GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.gemFlawed, aMaterial, 2L), GT_Proxy.tBits, new Object[]{"h", "X", Character.valueOf('X'), OrePrefix.gem.get(aMaterial)});
                        if (aSpecialRecipeReq)
                            GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dustSmall, aMaterial, 2L), GT_Proxy.tBits, new Object[]{"X", "m", Character.valueOf('X'), OrePrefix.gemFlawed.get(aMaterial)});
                    }
                }
                GT_Values.RA.addForgeHammerRecipe(aStack, OreDictionaryUnifier.get(OrePrefix.gemChipped, aMaterial, 2L), 64, 16);
                break;
            case gemFlawless:
                if (aFuelPower)
                    GT_Values.RA.addFuel(GT_Utility.copyAmount(1L, aStack), null, aMaterial.mFuelPower * 4, aMaterial.mFuelType);
                if (!aNoWorking) {
                    GT_Values.RA.addLatheRecipe(GT_Utility.copyAmount(1L, aStack), OreDictionaryUnifier.get(OrePrefix.stickLong, aMaterial, 1L), OreDictionaryUnifier.getDust(aMaterial, aPrefix.mMaterialAmount - OrePrefix.stickLong.mMaterialAmount), (int) Math.max(aMaterialMass * 5L, 1L), 16);
                    if (aMaterial.mUnificatable && (aMaterial.mMaterialInto == aMaterial)) {
                        GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.gemFlawless, aMaterial, 2L), GT_Proxy.tBits, new Object[]{"h", "X", Character.valueOf('X'), OrePrefix.gemExquisite.get(aMaterial)});
                        if (aSpecialRecipeReq)
                            GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, aMaterial, 2L), GT_Proxy.tBits, new Object[]{"X", "m", Character.valueOf('X'), OrePrefix.gemFlawless.get(aMaterial)});
                    }
                }
                GT_Values.RA.addForgeHammerRecipe(aStack, OreDictionaryUnifier.get(OrePrefix.gem, aMaterial, 2L), 64, 16);
                break;
        }
    }
}
