package gregtech.loaders.oreprocessing;

import gregtech.api.ConfigCategories;
import gregtech.api.GT_Values;
import gregtech.api.GregTech_API;
import gregtech.api.items.ItemList;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeRegistrator;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.FluidMaterial;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GT_Utility;
import gregtech.common.GT_Proxy;
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
                    GT_Values.RA.addFuel(GT_Utility.copyAmount(1, stack), null, uEntry.material.mFuelPower, uEntry.material.mFuelType);
                }
                if (uEntry.material instanceof FluidMaterial) {
                    RecipeMap.FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
                            .inputs(ItemList.Shape_Mold_Ingot.get(0))
                            .fluidInputs(((FluidMaterial) uEntry.material).getFluid(144))
                            .outputs(OreDictionaryUnifier.get(OrePrefix.ingot, uEntry.material))
                            .duration(32)
                            .EUt(8)
                            .buildAndRegister();
                }
                RecipeRegistrator.registerReverseFluidSmelting(stack, uEntry.material, uEntry.orePrefix.mMaterialAmount, null);
                RecipeRegistrator.registerReverseMacerating(stack, uEntry.material, uEntry.orePrefix.mMaterialAmount, null, null, null, false);
                if (uEntry.material.mSmeltInto.mArcSmeltInto != uEntry.material) {
                    RecipeRegistrator.registerReverseArcSmelting(GT_Utility.copyAmount(1, stack), uEntry.material, uEntry.orePrefix.mMaterialAmount, null, null, null);
                }
                ItemStack tempDustStack;
                if ((null != (tempDustStack = OreDictionaryUnifier.get(OrePrefix.dust, uEntry.material.mMacerateInto, 1L))) && ((uEntry.material.mBlastFurnaceRequired) || aNoSmelting)) {
                    ModHandler.removeFurnaceSmelting(tempDustStack);
                }

                if (uEntry.material.mUnificatable && (uEntry.material.mMaterialInto == uEntry.material) && !uEntry.material.hasFlag(DustMaterial.MatFlags.NO_WORKING)) {
                    if (!uEntry.material.contains(SubTag.SMELTING_TO_GEM))
                        ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.ingot, uEntry.material, 1), GT_Proxy.tBits, "XXX", "XXX", "XXX", Character.valueOf('X'), OreDictionaryUnifier.get(OrePrefix.nugget, uEntry.material));
                    if ((uEntry.material.contains(SubTag.MORTAR_GRINDABLE)) && (GregTech_API.sRecipeFile.get(ConfigCategories.Tools.mortar, uEntry.material.toString(), true)))
                        ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, uEntry.material, 1), GT_Proxy.tBits, "X", "m", Character.valueOf('X'), OreDictionaryUnifier.get(OrePrefix.ingot, uEntry.material));
                }

                if (!aNoSmashing) {
                    RecipeMap.HAMMER_RECIPES.recipeBuilder()
                            .inputs(GT_Utility.copyAmount(2, stack))
                            .outputs(OreDictionaryUnifier.get(OrePrefix.plate, uEntry.material))
                            .duration((int) Math.max(materialMass, 1L))
                            .EUt(16)
                            .buildAndRegister();
                    RecipeMap.BENDER_RECIPES.recipeBuilder()
                            .inputs(GT_Utility.copyAmount(1, stack))
                            .outputs(OreDictionaryUnifier.get(OrePrefix.plate, uEntry.material))
                            .duration((int) Math.max(materialMass, 1L))
                            .EUt(24)
                            .buildAndRegister();
                    RecipeMap.BENDER_RECIPES.recipeBuilder()
                            .inputs(GT_Utility.copyAmount(2, stack))
                            .outputs(OreDictionaryUnifier.get(OrePrefix.plateDouble, uEntry.material))
                            .duration((int) Math.max(materialMass * 2L, 1L))
                            .EUt(96)
                            .buildAndRegister();
                    RecipeMap.BENDER_RECIPES.recipeBuilder()
                            .inputs(GT_Utility.copyAmount(3, stack))
                            .outputs(OreDictionaryUnifier.get(OrePrefix.plateTriple, uEntry.material))
                            .duration((int) Math.max(materialMass * 3L, 1L))
                            .EUt(96)
                            .buildAndRegister();
                    RecipeMap.BENDER_RECIPES.recipeBuilder()
                            .inputs(GT_Utility.copyAmount(4, stack))
                            .outputs(OreDictionaryUnifier.get(OrePrefix.plateQuadruple, uEntry.material))
                            .duration((int) Math.max(materialMass * 4L, 1L))
                            .EUt(96)
                            .buildAndRegister();
                    RecipeMap.BENDER_RECIPES.recipeBuilder()
                            .inputs(GT_Utility.copyAmount(5, stack))
                            .outputs(OreDictionaryUnifier.get(OrePrefix.plateQuintuple, uEntry.material))
                            .duration((int) Math.max(materialMass * 5L, 1L))
                            .EUt(96)
                            .buildAndRegister();
                    RecipeMap.BENDER_RECIPES.recipeBuilder()
                            .inputs(GT_Utility.copyAmount(9, stack))
                            .outputs(OreDictionaryUnifier.get(OrePrefix.plateDense, uEntry.material))
                            .duration((int) Math.max(materialMass * 9L, 1L))
                            .EUt(96)
                            .buildAndRegister();
                }

                RecipeRegistrator.registerUsagesForMaterials(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.plate, uEntry.material).toString(), !aNoSmashing);
                break;
            case ingotDouble:
                if (!aNoSmashing) {
                    RecipeMap.BENDER_RECIPES.recipeBuilder()
                            .inputs(GT_Utility.copyAmount(1, stack))
                            .outputs(OreDictionaryUnifier.get(OrePrefix.plateDouble, uEntry.material))
                            .duration((int) Math.max(materialMass * 2L, 1L))
                            .EUt(96)
                            .buildAndRegister();
                    RecipeMap.BENDER_RECIPES.recipeBuilder()
                            .inputs(GT_Utility.copyAmount(2, stack))
                            .outputs(OreDictionaryUnifier.get(OrePrefix.plateQuadruple, uEntry.material))
                            .duration((int) Math.max(materialMass * 2L, 1L)).EUt(96)
                            .buildAndRegister();
                    if (aSpecialRecipeReq && GregTech_API.sRecipeFile.get(ConfigCategories.Tools.hammermultiingot, uEntry.material.toString(), true)) {
                        ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.ingotDouble, uEntry.material, 1), GT_Proxy.tBits, "I", "I", "h", Character.valueOf('I'), OreDictionaryUnifier.get(OrePrefix.plate, uEntry.material));
                    }
                }
                break;
            case ingotTriple:
                if (!aNoSmashing) {
                    RecipeMap.BENDER_RECIPES.recipeBuilder()
                            .inputs(GT_Utility.copyAmount(1, stack))
                            .outputs(OreDictionaryUnifier.get(OrePrefix.plateTriple, uEntry.material, 1))
                            .duration((int) Math.max(materialMass, 1L))
                            .EUt(96)
                            .buildAndRegister();
                    RecipeMap.BENDER_RECIPES.recipeBuilder()
                            .inputs(GT_Utility.copyAmount(3, stack))
                            .outputs(OreDictionaryUnifier.get(OrePrefix.plateDense, uEntry.material, 1))
                            .duration((int) Math.max(materialMass * 3L, 1L))
                            .EUt(96)
                            .buildAndRegister();
                    if (aSpecialRecipeReq && GregTech_API.sRecipeFile.get(ConfigCategories.Tools.hammermultiingot, uEntry.material.toString(), true)) {
                        ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.ingotTriple, uEntry.material, 1), GT_Proxy.tBits, "I", "B", "h", Character.valueOf('I'), OreDictionaryUnifier.get(OrePrefix.ingotDouble, uEntry.material), Character.valueOf('B'), OreDictionaryUnifier.get(OrePrefix.ingot, uEntry.material));
                    }
                }
                break;
            case ingotQuadruple:
                if (!aNoSmashing) {
                    RecipeMap.BENDER_RECIPES.recipeBuilder()
                            .inputs(GT_Utility.copyAmount(1, stack))
                            .outputs(OreDictionaryUnifier.get(OrePrefix.plateQuadruple, uEntry.material, 1))
                            .duration((int) Math.max(materialMass, 1L))
                            .EUt(96)
                            .buildAndRegister();
                    if (aSpecialRecipeReq && GregTech_API.sRecipeFile.get(ConfigCategories.Tools.hammermultiingot, uEntry.material.toString(), true)) {
                        ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.ingotQuadruple, uEntry.material, 1), GT_Proxy.tBits, "I", "B", "h", Character.valueOf('I'), OreDictionaryUnifier.get(OrePrefix.ingotTriple, uEntry.material), Character.valueOf('B'), OreDictionaryUnifier.get(OrePrefix.ingot, uEntry.material));
                    }
                }
                break;
            case ingotQuintuple:
                if (!aNoSmashing) {
                    RecipeMap.BENDER_RECIPES.recipeBuilder()
                            .inputs(GT_Utility.copyAmount(1, stack))
                            .outputs(OreDictionaryUnifier.get(OrePrefix.plateQuintuple, uEntry.material, 1))
                            .duration((int) Math.max(materialMass, 1L))
                            .EUt(96)
                            .buildAndRegister();
                    if (aSpecialRecipeReq && GregTech_API.sRecipeFile.get(ConfigCategories.Tools.hammermultiingot, uEntry.material.toString(), true)) {
                        ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.ingotQuintuple, uEntry.material, 1), GT_Proxy.tBits, "I", "B", "h", Character.valueOf('I'), OreDictionaryUnifier.get(OrePrefix.ingotQuadruple, uEntry.material), Character.valueOf('B'), OreDictionaryUnifier.get(OrePrefix.ingot, uEntry.material));
                    }
                }
                break;
            case ingotHot:
                RecipeMap.VACUUM_RECIPES.recipeBuilder()
                        .inputs(GT_Utility.copyAmount(1, stack))
                        .outputs(OreDictionaryUnifier.get(OrePrefix.ingot, uEntry.material, 1))
                        .duration((int) Math.max(materialMass * 3L, 1L))
                        .buildAndRegister();
                break;
        }
    }
}
