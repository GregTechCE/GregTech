package gregtech.loaders.oreprocessing;

import gregtech.api.ConfigCategories;
import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.MetalMaterial;
import gregtech.api.unification.material.type.SolidMaterial;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import gregtech.common.CommonProxy;
import net.minecraft.item.ItemStack;

public class ProcessingGem implements IOreRegistrationHandler {
    public ProcessingGem() {
        OrePrefix.gem.addProcessingHandler(this);
        OrePrefix.gemChipped.addProcessingHandler(this);
        OrePrefix.gemExquisite.addProcessingHandler(this);
        OrePrefix.gemFlawed.addProcessingHandler(this);
        OrePrefix.gemFlawless.addProcessingHandler(this);
    }

    public void registerOre(UnificationEntry uEntry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();
        long materialMass = uEntry.material.getMass();
        boolean aNoSmashing = uEntry.material.hasFlag(DustMaterial.MatFlags.NO_SMASHING);
        boolean aNoWorking = uEntry.material.hasFlag(DustMaterial.MatFlags.NO_WORKING);
        boolean aNoSmelting = uEntry.material.hasFlag(DustMaterial.MatFlags.NO_SMELTING);
        boolean aSpecialRecipeReq = uEntry.material.hasFlag(SolidMaterial.MatFlags.MORTAR_GRINDABLE) && GregTechAPI.sRecipeFile.get(ConfigCategories.Tools.mortar, uEntry.material.defaultLocalName, true);
        boolean aFuelPower = uEntry.material.mFuelPower > 0;

        switch (uEntry.orePrefix) {
            case gem:
                if (aFuelPower) {
                    GTValues.RA.addFuel(GTUtility.copyAmount(1, stack), null, uEntry.material.mFuelPower * 2, uEntry.material.mFuelType);
                }
                if (!OrePrefix.block.isIgnored(uEntry.material)) {
                    ModHandler.addCompressionRecipe(GTUtility.copyAmount(9, stack), OreDictUnifier.get(OrePrefix.block, uEntry.material));
                }
                if (!aNoSmelting && uEntry.material instanceof MetalMaterial) {
                    ModHandler.addSmeltingRecipe(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.ingot, ((MetalMaterial)uEntry.material).smeltInto, 1));
                }
                if (aNoSmashing) {
                    RecipeMap.HAMMER_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(2, stack))
                            .outputs(OreDictUnifier.get(OrePrefix.gemFlawed, uEntry.material))
                            .duration(64)
                            .EUt(16)
                            .buildAndRegister();
                } else {
                    RecipeMap.HAMMER_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(2, stack))
                            .outputs(OreDictUnifier.get(OrePrefix.plate, uEntry.material))
                            .duration((int) Math.max(materialMass, 1L))
                            .EUt(16)
                            .buildAndRegister();
                    RecipeMap.BENDER_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(1, stack))
                            .outputs(OreDictUnifier.get(OrePrefix.plate, uEntry.material))
                            .duration((int) Math.max(materialMass, 1L))
                            .EUt(24)
                            .buildAndRegister();
                    RecipeMap.BENDER_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(2, stack))
                            .outputs(OreDictUnifier.get(OrePrefix.plateDouble, uEntry.material))
                            .duration((int) Math.max(materialMass * 2L, 1L))
                            .EUt(96)
                            .buildAndRegister();
                    RecipeMap.BENDER_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(3, stack))
                            .outputs(OreDictUnifier.get(OrePrefix.plateTriple, uEntry.material))
                            .duration((int) Math.max(materialMass * 3L, 1L))
                            .EUt(96)
                            .buildAndRegister();
                    RecipeMap.BENDER_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(4, stack))
                            .outputs(OreDictUnifier.get(OrePrefix.plateQuadruple, uEntry.material))
                            .duration((int) Math.max(materialMass * 4L, 1L))
                            .EUt(96)
                            .buildAndRegister();
                    RecipeMap.BENDER_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(5, stack))
                            .outputs(OreDictUnifier.get(OrePrefix.plateQuintuple, uEntry.material))
                            .duration((int) Math.max(materialMass * 5L, 1L))
                            .EUt(96)
                            .buildAndRegister();
                    RecipeMap.BENDER_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(9, stack))
                            .outputs(OreDictUnifier.get(OrePrefix.plateDense, uEntry.material))
                            .duration((int) Math.max(materialMass * 9L, 1L))
                            .EUt(96)
                            .buildAndRegister();
                }

                if (aNoWorking) {
                    GTValues.RA.addLatheRecipe(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.stick, uEntry.material, 1), OreDictUnifier.get(OrePrefix.dustSmall, uEntry.material, 2), (int) Math.max(materialMass, 1L), 16);
                } else {
                    if (uEntry.material.mUnificatable && (uEntry.material.mMaterialInto == uEntry.material)) {
                        ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.gem, uEntry.material, 2), CommonProxy.tBits, "h", "X", Character.valueOf('X'), OreDictUnifier.get(OrePrefix.gemFlawless, uEntry.material));
                        if (uEntry.material.contains(SubTag.SMELTING_TO_GEM)) ModHandler.addCraftingRecipe(GTUtility.copyAmount(1, stack), CommonProxy.tBits, "XXX", "XXX", "XXX", Character.valueOf('X'), OreDictUnifier.get(OrePrefix.nugget, uEntry.material));
                        if (aSpecialRecipeReq) ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.dust, uEntry.material, 1), CommonProxy.tBits, "X", "m", Character.valueOf('X'), OreDictUnifier.get(OrePrefix.gem, uEntry.material));
                    }
                }
                RecipeRegistrator.registerUsagesForMaterials(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.plate, uEntry.material).toString(), !aNoSmashing);

                switch (uEntry.material.defaultLocalName) {
                    case "Coal":
                    case "Charcoal":
                        if (GregTechAPI.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "torchesFromCoal", false)) {
                            ModHandler.removeRecipe(GTUtility.copyAmount(1, stack), null, null, new ItemStack(net.minecraft.init.Items.STICK, 1, 0));}
                        break;
                    case "Certus Quartz":
                        GTValues.RA.addElectrolyzerRecipe(stack, 0, ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1, 1), null, null, null, null, null, 2000, 30);
                }

                break;
            case gemChipped:
                if (aFuelPower)
                    GTValues.RA.addFuel(GTUtility.copyAmount(1, stack), null, uEntry.material.mFuelPower / 2, uEntry.material.mFuelType);
                if (!aNoWorking) {
                    GTValues.RA.addLatheRecipe(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.bolt, uEntry.material, 1), OreDictUnifier.get(OrePrefix.dustTiny, uEntry.material, 1), (int) Math.max(materialMass, 1L), 8);
                    if (uEntry.material.mUnificatable && (uEntry.material.mMaterialInto == uEntry.material)) {
                        ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.gemChipped, uEntry.material, 2), CommonProxy.tBits, "h", "X", Character.valueOf('X'), OreDictUnifier.get(OrePrefix.gemFlawed, uEntry.material));
                        if (aSpecialRecipeReq)
                            ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.dustSmall, uEntry.material, 1), CommonProxy.tBits, "X", "m", Character.valueOf('X'), OreDictUnifier.get(OrePrefix.gemChipped, uEntry.material));
                    }
                }
                break;
            case gemExquisite:
                if (aFuelPower)
                    GTValues.RA.addFuel(GTUtility.copyAmount(1, stack), null, uEntry.material.mFuelPower * 8, uEntry.material.mFuelType);
                if (!aNoWorking) {
                    GTValues.RA.addLatheRecipe(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.stickLong, uEntry.material, 3), OreDictUnifier.getDust(uEntry.material, uEntry.orePrefix.mMaterialAmount - OrePrefix.stickLong.mMaterialAmount * 3L), (int) Math.max(materialMass * 10L, 1L), 16);
                    if (uEntry.material.mUnificatable && (uEntry.material.mMaterialInto == uEntry.material))
                        if (aSpecialRecipeReq) ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.dust, uEntry.material, 4), CommonProxy.tBits, "X", "m", Character.valueOf('X'), OreDictUnifier.get(OrePrefix.gemExquisite, uEntry.material));
                }
                GTValues.RA.addForgeHammerRecipe(stack, OreDictUnifier.get(OrePrefix.gemFlawless, uEntry.material, 2), 64, 16);
                break;
            case gemFlawed:
                if (aFuelPower)
                    GTValues.RA.addFuel(GTUtility.copyAmount(1, stack), null, uEntry.material.mFuelPower, uEntry.material.mFuelType);
                if (!aNoWorking) {
                    GTValues.RA.addLatheRecipe(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.bolt, uEntry.material, 2), OreDictUnifier.get(OrePrefix.dustSmall, uEntry.material, 1), (int) Math.max(materialMass, 1L), 12);
                    if (uEntry.material.mUnificatable && (uEntry.material.mMaterialInto == uEntry.material)) {
                        ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.gemFlawed, uEntry.material, 2), CommonProxy.tBits, "h", "X", Character.valueOf('X'), OreDictUnifier.get(OrePrefix.gem, uEntry.material));
                        if (aSpecialRecipeReq)
                            ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.dustSmall, uEntry.material, 2), CommonProxy.tBits, "X", "m", Character.valueOf('X'), OreDictUnifier.get(OrePrefix.gemFlawed, uEntry.material));
                    }
                }
                GTValues.RA.addForgeHammerRecipe(stack, OreDictUnifier.get(OrePrefix.gemChipped, uEntry.material, 2), 64, 16);
                break;
            case gemFlawless:
                if (aFuelPower)
                    GTValues.RA.addFuel(GTUtility.copyAmount(1, stack), null, uEntry.material.mFuelPower * 4, uEntry.material.mFuelType);
                if (!aNoWorking) {
                    GTValues.RA.addLatheRecipe(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.stickLong, uEntry.material, 1), OreDictUnifier.getDust(uEntry.material, uEntry.orePrefix.mMaterialAmount - OrePrefix.stickLong.mMaterialAmount), (int) Math.max(materialMass * 5L, 1L), 16);
                    if (uEntry.material.mUnificatable && (uEntry.material.mMaterialInto == uEntry.material)) {
                        ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.gemFlawless, uEntry.material, 2), CommonProxy.tBits, "h", "X", Character.valueOf('X'), OreDictUnifier.get(OrePrefix.gemExquisite, uEntry.material));
                        if (aSpecialRecipeReq)
                            ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.dust, uEntry.material, 2), CommonProxy.tBits, "X", "m", Character.valueOf('X'), OreDictUnifier.get(OrePrefix.gemFlawless, uEntry.material));
                    }
                }
                RecipeMap.HAMMER_RECIPES.recipeBuilder()
                        .inputs(stack)
                        .outputs(OreDictUnifier.get(OrePrefix.gem, uEntry.material, 2))
                        .duration(64)
                        .EUt(16)
                        .buildAndRegister();
                break;
        }
    }
}
