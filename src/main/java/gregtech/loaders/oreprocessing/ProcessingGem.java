package gregtech.loaders.oreprocessing;

import gregtech.api.ConfigCategories;
import gregtech.api.GT_Values;
import gregtech.api.GregTech_API;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
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

    public void registerOre(UnificationEntry uEntry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();
        long materialMass = uEntry.material.getMass();
        boolean aNoSmashing = uEntry.material.contains(SubTag.NO_SMASHING);
        boolean aNoWorking = uEntry.material.contains(SubTag.NO_WORKING);
        boolean aNoSmelting = uEntry.material.contains(SubTag.NO_SMELTING);
        boolean aSpecialRecipeReq = (uEntry.material.contains(SubTag.MORTAR_GRINDABLE)) && (GregTech_API.sRecipeFile.get(ConfigCategories.Tools.mortar, uEntry.material.defaultLocalName, true));
        boolean aFuelPower = uEntry.material.mFuelPower > 0;

        switch (uEntry.orePrefix) {
            case gem:
                if (aFuelPower) {
                    GT_Values.RA.addFuel(GT_Utility.copyAmount(1, stack), null, uEntry.material.mFuelPower * 2, uEntry.material.mFuelType);
                }
                if (!OrePrefix.block.isIgnored(uEntry.material)) {
                    ModHandler.addCompressionRecipe(GT_Utility.copyAmount(9, stack), OreDictionaryUnifier.get(OrePrefix.block, uEntry.material, 1L));
                }
                if (!aNoSmelting) {
                    ModHandler.addSmeltingRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.ingot, uEntry.material.mSmeltInto, 1L));
                }
                if (aNoSmashing) {
                    GT_Values.RA.addForgeHammerRecipe(stack, OreDictionaryUnifier.get(OrePrefix.gemFlawed, uEntry.material, 2), 64, 16);
                } else {
                    GT_Values.RA.addForgeHammerRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.plate, uEntry.material, 1), (int) Math.max(materialMass, 1L), 16);
                    GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.plate, uEntry.material, 1), (int) Math.max(materialMass * 2L, 1L), 24);
                    GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(2, stack), OreDictionaryUnifier.get(OrePrefix.plateDouble, uEntry.material, 1), (int) Math.max(materialMass * 2L, 1L), 96);
                    GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(3, stack), OreDictionaryUnifier.get(OrePrefix.plateTriple, uEntry.material, 1), (int) Math.max(materialMass * 3L, 1L), 96);
                    GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(4, stack), OreDictionaryUnifier.get(OrePrefix.plateQuadruple, uEntry.material, 1), (int) Math.max(materialMass * 4L, 1L), 96);
                    GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(5, stack), OreDictionaryUnifier.get(OrePrefix.plateQuintuple, uEntry.material, 1), (int) Math.max(materialMass * 5L, 1L), 96);
                    GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(9, stack), OreDictionaryUnifier.get(OrePrefix.plateDense, uEntry.material, 1), (int) Math.max(materialMass * 9L, 1L), 96);
                }

                if (aNoWorking) {
                    GT_Values.RA.addLatheRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.stick, uEntry.material, 1), OreDictionaryUnifier.get(OrePrefix.dustSmall, uEntry.material, 2), (int) Math.max(materialMass, 1L), 16);
                } else {
                    if (uEntry.material.mUnificatable && (uEntry.material.mMaterialInto == uEntry.material)) {
                        ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.gem, uEntry.material, 2), GT_Proxy.tBits, new Object[]{"h", "X", Character.valueOf('X'), OrePrefix.gemFlawless.get(uEntry.material)});
                        if (uEntry.material.contains(SubTag.SMELTING_TO_GEM)) ModHandler.addCraftingRecipe(GT_Utility.copyAmount(1, stack), GT_Proxy.tBits, new Object[]{"XXX", "XXX", "XXX", Character.valueOf('X'), OrePrefix.nugget.get(uEntry.material)});
                        if (aSpecialRecipeReq) ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, uEntry.material, 1), GT_Proxy.tBits, new Object[]{"X", "m", Character.valueOf('X'), OrePrefix.gem.get(uEntry.material)});
                    }
                }
                gregtech.api.util.GT_RecipeRegistrator.registerUsagesForMaterials(GT_Utility.copyAmount(1, stack), OrePrefix.plate.get(uEntry.material).toString(), !aNoSmashing);

                switch (uEntry.material.defaultLocalName) {
                    case "Coal":
                    case "Charcoal":
                        if (gregtech.api.GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "torchesFromCoal", false)) {
                            ModHandler.removeRecipe(GT_Utility.copyAmount(1, stack), null, null, new ItemStack(net.minecraft.init.Items.STICK, 1, 0));}
                        break;
                    case "Certus Quartz":
                        GT_Values.RA.addElectrolyzerRecipe(stack, 0, ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1, 1), null, null, null, null, null, 2000, 30);
                }

                break;
            case gemChipped:
                if (aFuelPower)
                    GT_Values.RA.addFuel(GT_Utility.copyAmount(1, stack), null, uEntry.material.mFuelPower / 2, uEntry.material.mFuelType);
                if (!aNoWorking) {
                    GT_Values.RA.addLatheRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.bolt, uEntry.material, 1), OreDictionaryUnifier.get(OrePrefix.dustTiny, uEntry.material, 1), (int) Math.max(materialMass, 1L), 8);
                    if (uEntry.material.mUnificatable && (uEntry.material.mMaterialInto == uEntry.material)) {
                        ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.gemChipped, uEntry.material, 2), GT_Proxy.tBits, new Object[]{"h", "X", Character.valueOf('X'), OrePrefix.gemFlawed.get(uEntry.material)});
                        if (aSpecialRecipeReq)
                            ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dustSmall, uEntry.material, 1), GT_Proxy.tBits, new Object[]{"X", "m", Character.valueOf('X'), OrePrefix.gemChipped.get(uEntry.material)});
                    }
                }
                break;
            case gemExquisite:
                if (aFuelPower)
                    GT_Values.RA.addFuel(GT_Utility.copyAmount(1, stack), null, uEntry.material.mFuelPower * 8, uEntry.material.mFuelType);
                if (!aNoWorking) {
                    GT_Values.RA.addLatheRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.stickLong, uEntry.material, 3), OreDictionaryUnifier.getDust(uEntry.material, uEntry.orePrefix.mMaterialAmount - OrePrefix.stickLong.mMaterialAmount * 3L), (int) Math.max(materialMass * 10L, 1L), 16);
                    if (uEntry.material.mUnificatable && (uEntry.material.mMaterialInto == uEntry.material))
                        if (aSpecialRecipeReq) ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, uEntry.material, 4), GT_Proxy.tBits, new Object[]{"X", "m", Character.valueOf('X'), OrePrefix.gemExquisite.get(uEntry.material)});
                }
                GT_Values.RA.addForgeHammerRecipe(stack, OreDictionaryUnifier.get(OrePrefix.gemFlawless, uEntry.material, 2), 64, 16);
                break;
            case gemFlawed:
                if (aFuelPower)
                    GT_Values.RA.addFuel(GT_Utility.copyAmount(1, stack), null, uEntry.material.mFuelPower, uEntry.material.mFuelType);
                if (!aNoWorking) {
                    GT_Values.RA.addLatheRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.bolt, uEntry.material, 2), OreDictionaryUnifier.get(OrePrefix.dustSmall, uEntry.material, 1), (int) Math.max(materialMass, 1L), 12);
                    if (uEntry.material.mUnificatable && (uEntry.material.mMaterialInto == uEntry.material)) {
                        ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.gemFlawed, uEntry.material, 2), GT_Proxy.tBits, new Object[]{"h", "X", Character.valueOf('X'), OrePrefix.gem.get(uEntry.material)});
                        if (aSpecialRecipeReq)
                            ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dustSmall, uEntry.material, 2), GT_Proxy.tBits, new Object[]{"X", "m", Character.valueOf('X'), OrePrefix.gemFlawed.get(uEntry.material)});
                    }
                }
                GT_Values.RA.addForgeHammerRecipe(stack, OreDictionaryUnifier.get(OrePrefix.gemChipped, uEntry.material, 2), 64, 16);
                break;
            case gemFlawless:
                if (aFuelPower)
                    GT_Values.RA.addFuel(GT_Utility.copyAmount(1, stack), null, uEntry.material.mFuelPower * 4, uEntry.material.mFuelType);
                if (!aNoWorking) {
                    GT_Values.RA.addLatheRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.stickLong, uEntry.material, 1), OreDictionaryUnifier.getDust(uEntry.material, uEntry.orePrefix.mMaterialAmount - OrePrefix.stickLong.mMaterialAmount), (int) Math.max(materialMass * 5L, 1L), 16);
                    if (uEntry.material.mUnificatable && (uEntry.material.mMaterialInto == uEntry.material)) {
                        ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.gemFlawless, uEntry.material, 2), GT_Proxy.tBits, new Object[]{"h", "X", Character.valueOf('X'), OrePrefix.gemExquisite.get(uEntry.material)});
                        if (aSpecialRecipeReq)
                            ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, uEntry.material, 2), GT_Proxy.tBits, new Object[]{"X", "m", Character.valueOf('X'), OrePrefix.gemFlawless.get(uEntry.material)});
                    }
                }
                GT_Values.RA.addForgeHammerRecipe(stack, OreDictionaryUnifier.get(OrePrefix.gem, uEntry.material, 2), 64, 16);
                break;
        }
    }
}
