package gregtech.loaders.oreprocessing;

import gregtech.api.ConfigCategories;
import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.items.ItemList;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.FluidMaterial;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import gregtech.common.CommonProxy;
import net.minecraft.item.ItemStack;

public class ProcessingIngot implements IOreRegistrationHandler {
    public ProcessingIngot() {
        OrePrefix.ingot.addProcessingHandler(this);
        OrePrefix.ingotDouble.addProcessingHandler(this);
        OrePrefix.ingotTriple.addProcessingHandler(this);
        OrePrefix.ingotQuadruple.addProcessingHandler(this);
        OrePrefix.ingotQuintuple.addProcessingHandler(this);
        OrePrefix.ingotHot.addProcessingHandler(this);
    }

    public void registerOre(UnificationEntry uEntry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();
        boolean aNoSmashing = uEntry.material.hasFlag(DustMaterial.MatFlags.NO_SMASHING);
        boolean aNoSmelting = uEntry.material.hasFlag(DustMaterial.MatFlags.NO_SMELTING);
        long materialMass = uEntry.material.getMass();
        boolean aSpecialRecipeReq = uEntry.material.mUnificatable && (uEntry.material.mMaterialInto == uEntry.material) && !uEntry.material.hasFlag(DustMaterial.MatFlags.NO_SMASHING);

        switch (uEntry.orePrefix) {
            case ingot:
                if (uEntry.material.mFuelPower > 0) {
                    GTValues.RA.addFuel(GTUtility.copyAmount(1, stack), null, uEntry.material.mFuelPower, uEntry.material.mFuelType);
                }
                if (uEntry.material instanceof FluidMaterial) {
                    RecipeMap.FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
                            .inputs(ItemList.Shape_Mold_Ingot.get(0))
                            .fluidInputs(((FluidMaterial) uEntry.material).getFluid(144))
                            .outputs(OreDictUnifier.get(OrePrefix.ingot, uEntry.material))
                            .duration(32)
                            .EUt(8)
                            .buildAndRegister();
                }
                RecipeRegistrator.registerReverseFluidSmelting(stack, uEntry.material, uEntry.orePrefix.mMaterialAmount, null);
                RecipeRegistrator.registerReverseMacerating(stack, uEntry.material, uEntry.orePrefix.mMaterialAmount, null, null, null, false);
                if (uEntry.material.mSmeltInto.mArcSmeltInto != uEntry.material) {
                    RecipeRegistrator.registerReverseArcSmelting(GTUtility.copyAmount(1, stack), uEntry.material, uEntry.orePrefix.mMaterialAmount, null, null, null);
                }
                ItemStack tempDustStack;
                if ((null != (tempDustStack = OreDictUnifier.get(OrePrefix.dust, uEntry.material.mMacerateInto, 1L))) && ((uEntry.material.mBlastFurnaceRequired) || aNoSmelting)) {
                    ModHandler.removeFurnaceSmelting(tempDustStack);
                }

                if (uEntry.material.mUnificatable && (uEntry.material.mMaterialInto == uEntry.material) && !uEntry.material.hasFlag(DustMaterial.MatFlags.NO_WORKING)) {
                    if (!uEntry.material.contains(SubTag.SMELTING_TO_GEM))
                        ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.ingot, uEntry.material, 1), CommonProxy.tBits, "XXX", "XXX", "XXX", Character.valueOf('X'), OreDictUnifier.get(OrePrefix.nugget, uEntry.material));
                    if ((uEntry.material.contains(SubTag.MORTAR_GRINDABLE)) && (GregTechAPI.sRecipeFile.get(ConfigCategories.Tools.mortar, uEntry.material.toString(), true)))
                        ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.dust, uEntry.material, 1), CommonProxy.tBits, "X", "m", Character.valueOf('X'), OreDictUnifier.get(OrePrefix.ingot, uEntry.material));
                }

                if (!aNoSmashing) {
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

                RecipeRegistrator.registerUsagesForMaterials(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.plate, uEntry.material).toString(), !aNoSmashing);
                break;
            case ingotDouble:
                if (!aNoSmashing) {
                    RecipeMap.BENDER_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(1, stack))
                            .outputs(OreDictUnifier.get(OrePrefix.plateDouble, uEntry.material))
                            .duration((int) Math.max(materialMass * 2L, 1L))
                            .EUt(96)
                            .buildAndRegister();
                    RecipeMap.BENDER_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(2, stack))
                            .outputs(OreDictUnifier.get(OrePrefix.plateQuadruple, uEntry.material))
                            .duration((int) Math.max(materialMass * 2L, 1L)).EUt(96)
                            .buildAndRegister();
                    if (aSpecialRecipeReq && GregTechAPI.sRecipeFile.get(ConfigCategories.Tools.hammermultiingot, uEntry.material.toString(), true)) {
                        ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.ingotDouble, uEntry.material, 1), CommonProxy.tBits, "I", "I", "h", Character.valueOf('I'), OreDictUnifier.get(OrePrefix.plate, uEntry.material));
                    }
                }
                break;
            case ingotTriple:
                if (!aNoSmashing) {
                    RecipeMap.BENDER_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(1, stack))
                            .outputs(OreDictUnifier.get(OrePrefix.plateTriple, uEntry.material, 1))
                            .duration((int) Math.max(materialMass, 1L))
                            .EUt(96)
                            .buildAndRegister();
                    RecipeMap.BENDER_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(3, stack))
                            .outputs(OreDictUnifier.get(OrePrefix.plateDense, uEntry.material, 1))
                            .duration((int) Math.max(materialMass * 3L, 1L))
                            .EUt(96)
                            .buildAndRegister();
                    if (aSpecialRecipeReq && GregTechAPI.sRecipeFile.get(ConfigCategories.Tools.hammermultiingot, uEntry.material.toString(), true)) {
                        ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.ingotTriple, uEntry.material, 1), CommonProxy.tBits, "I", "B", "h", Character.valueOf('I'), OreDictUnifier.get(OrePrefix.ingotDouble, uEntry.material), Character.valueOf('B'), OreDictUnifier.get(OrePrefix.ingot, uEntry.material));
                    }
                }
                break;
            case ingotQuadruple:
                if (!aNoSmashing) {
                    RecipeMap.BENDER_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(1, stack))
                            .outputs(OreDictUnifier.get(OrePrefix.plateQuadruple, uEntry.material, 1))
                            .duration((int) Math.max(materialMass, 1L))
                            .EUt(96)
                            .buildAndRegister();
                    if (aSpecialRecipeReq && GregTechAPI.sRecipeFile.get(ConfigCategories.Tools.hammermultiingot, uEntry.material.toString(), true)) {
                        ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.ingotQuadruple, uEntry.material, 1), CommonProxy.tBits, "I", "B", "h", Character.valueOf('I'), OreDictUnifier.get(OrePrefix.ingotTriple, uEntry.material), Character.valueOf('B'), OreDictUnifier.get(OrePrefix.ingot, uEntry.material));
                    }
                }
                break;
            case ingotQuintuple:
                if (!aNoSmashing) {
                    RecipeMap.BENDER_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(1, stack))
                            .outputs(OreDictUnifier.get(OrePrefix.plateQuintuple, uEntry.material, 1))
                            .duration((int) Math.max(materialMass, 1L))
                            .EUt(96)
                            .buildAndRegister();
                    if (aSpecialRecipeReq && GregTechAPI.sRecipeFile.get(ConfigCategories.Tools.hammermultiingot, uEntry.material.toString(), true)) {
                        ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.ingotQuintuple, uEntry.material, 1), CommonProxy.tBits, "I", "B", "h", Character.valueOf('I'), OreDictUnifier.get(OrePrefix.ingotQuadruple, uEntry.material), Character.valueOf('B'), OreDictUnifier.get(OrePrefix.ingot, uEntry.material));
                    }
                }
                break;
            case ingotHot:
                RecipeMap.VACUUM_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack))
                        .outputs(OreDictUnifier.get(OrePrefix.ingot, uEntry.material, 1))
                        .duration((int) Math.max(materialMass * 3L, 1L))
                        .buildAndRegister();
                break;
        }
    }
}
