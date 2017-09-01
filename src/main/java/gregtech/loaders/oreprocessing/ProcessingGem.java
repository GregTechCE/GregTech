package gregtech.loaders.oreprocessing;

import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.Recipe;
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
import net.minecraft.item.ItemStack;

public class ProcessingGem implements IOreRegistrationHandler {

    public void register() {
        OrePrefix.gem.addProcessingHandler(this);
        OrePrefix.gemChipped.addProcessingHandler(this);
        OrePrefix.gemExquisite.addProcessingHandler(this);
        OrePrefix.gemFlawed.addProcessingHandler(this);
        OrePrefix.gemFlawless.addProcessingHandler(this);
    }

    public void registerOre(UnificationEntry entry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();
        long materialMass = entry.material.getMass();
        boolean noSmashing = entry.material.hasFlag(DustMaterial.MatFlags.NO_SMASHING);
        boolean noWorking = entry.material.hasFlag(DustMaterial.MatFlags.NO_WORKING);
        boolean noSmelting = entry.material.hasFlag(DustMaterial.MatFlags.NO_SMELTING);
        boolean mortarGrindable = entry.material.hasFlag(SolidMaterial.MatFlags.MORTAR_GRINDABLE);

        switch (entry.orePrefix) {
            case gem:
                if (!OrePrefix.block.isIgnored(entry.material)) {
                    RecipeMap.COMPRESSOR_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(9, stack))
                            .outputs(OreDictUnifier.get(OrePrefix.block, entry.material))
                            .buildAndRegister();
                }
                if (!noSmelting && entry.material instanceof MetalMaterial) {
                    ModHandler.addSmeltingRecipe(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.ingot, ((MetalMaterial) entry.material).smeltInto, 1));
                }
                if (noSmashing) {
                    RecipeMap.HAMMER_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(2, stack))
                            .outputs(OreDictUnifier.get(OrePrefix.gemFlawed, entry.material))
                            .duration(64)
                            .EUt(16)
                            .buildAndRegister();
                } else {
                    RecipeMap.HAMMER_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(2, stack))
                            .outputs(OreDictUnifier.get(OrePrefix.plate, entry.material))
                            .duration((int) Math.max(materialMass, 1L))
                            .EUt(16)
                            .buildAndRegister();
                    RecipeMap.BENDER_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(1, stack))
                            .outputs(OreDictUnifier.get(OrePrefix.plate, entry.material))
                            .duration((int) Math.max(materialMass, 1L))
                            .EUt(24)
                            .buildAndRegister();
                    RecipeMap.BENDER_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(2, stack))
                            .outputs(OreDictUnifier.get(OrePrefix.plateDouble, entry.material))
                            .duration((int) Math.max(materialMass * 2L, 1L))
                            .EUt(96)
                            .buildAndRegister();
                    RecipeMap.BENDER_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(3, stack))
                            .outputs(OreDictUnifier.get(OrePrefix.plateTriple, entry.material))
                            .duration((int) Math.max(materialMass * 3L, 1L))
                            .EUt(96)
                            .buildAndRegister();
                    RecipeMap.BENDER_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(4, stack))
                            .outputs(OreDictUnifier.get(OrePrefix.plateQuadruple, entry.material))
                            .duration((int) Math.max(materialMass * 4L, 1L))
                            .EUt(96)
                            .buildAndRegister();
                    RecipeMap.BENDER_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(5, stack))
                            .outputs(OreDictUnifier.get(OrePrefix.plateQuintuple, entry.material))
                            .duration((int) Math.max(materialMass * 5L, 1L))
                            .EUt(96)
                            .buildAndRegister();
                    RecipeMap.BENDER_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(9, stack))
                            .outputs(OreDictUnifier.get(OrePrefix.plateDense, entry.material))
                            .duration((int) Math.max(materialMass * 9L, 1L))
                            .EUt(96)
                            .buildAndRegister();
                }

                if (noWorking) {
                    RecipeMap.LATHE_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(1, stack))
                            .outputs(OreDictUnifier.get(OrePrefix.stick, entry.material), OreDictUnifier.get(OrePrefix.dustSmall, entry.material, 2))
                            .duration((int) Math.max(materialMass, 1L))
                            .EUt(16)
                            .buildAndRegister();
                } else {
                    ModHandler.addShapedRecipe(OreDictUnifier.get(OrePrefix.gem, entry.material, 2),
                            "h",
                            "X",
                            'X', OreDictUnifier.get(OrePrefix.gemFlawless, entry.material));

                    if (entry.material.hasFlag(SMELTING_TO_GEM))
                        ModHandler.addShapedRecipe(GTUtility.copyAmount(1, stack),
                                "XXX",
                                "XXX",
                                "XXX",
                                'X', OreDictUnifier.get(OrePrefix.nugget, entry.material));

                if (mortarGrindable)
                        ModHandler.addShapedRecipe(OreDictUnifier.get(OrePrefix.dust, entry.material, 1),
                                "X",
                                "m",
                                'X', OreDictUnifier.get(OrePrefix.gem, entry.material));
                }

//                RecipeRegistrator.registerUsagesForMaterials(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.plate, entry.material).toString(), !noSmashing);

                break;
            case gemChipped:
                if (!noWorking) {
                    RecipeMap.LATHE_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(1, stack))
                            .outputs(OreDictUnifier.get(OrePrefix.bolt, entry.material), OreDictUnifier.get(OrePrefix.dustTiny, entry.material))
                            .duration((int) Math.max(materialMass, 1L))
                            .EUt(8)
                            .buildAndRegister();

                    ModHandler.addShapedRecipe(OreDictUnifier.get(OrePrefix.gemChipped, entry.material, 2),
                            "h",
                            "X",
                            'X', OreDictUnifier.get(OrePrefix.gemFlawed, entry.material));

                    if (mortarGrindable)
                        ModHandler.addShapedRecipe(OreDictUnifier.get(OrePrefix.dustSmall, entry.material),
                                "X",
                                "m",
                                'X', OreDictUnifier.get(OrePrefix.gemChipped, entry.material));
                }
                break;
            case gemExquisite:
                if (!noWorking && entry.material instanceof DustMaterial) {
                    RecipeMap.LATHE_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(1, stack))
                            .outputs(OreDictUnifier.get(OrePrefix.stickLong, entry.material, 3), OreDictUnifier.getDust((DustMaterial) entry.material, entry.orePrefix.materialAmount - OrePrefix.stickLong.materialAmount * 3L))
                            .duration((int) Math.max(materialMass * 10L, 1L))
                            .EUt(16)
                            .buildAndRegister();

                    if (mortarGrindable)
                        ModHandler.addShapedRecipe(OreDictUnifier.getDust((DustMaterial) entry.material, 4),
                                "X",
                                "m",
                                'X', OreDictUnifier.get(OrePrefix.gemExquisite, entry.material));
                }
                RecipeMap.HAMMER_RECIPES.recipeBuilder()
                        .inputs(stack)
                        .outputs(OreDictUnifier.get(OrePrefix.gemFlawless, entry.material, 2))
                        .duration(64)
                        .EUt(16)
                        .buildAndRegister();
                break;
            case gemFlawed:
                if (!noWorking) {
                    RecipeMap.LATHE_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(1, stack))
                            .outputs(OreDictUnifier.get(OrePrefix.bolt, entry.material, 2), OreDictUnifier.get(OrePrefix.dustSmall, entry.material))
                            .duration((int) Math.max(materialMass, 1L))
                            .EUt(12)
                            .buildAndRegister();

                    ModHandler.addShapedRecipe(OreDictUnifier.get(OrePrefix.gemFlawed, entry.material, 2),
                            "h",
                            "X",
                            'X', OreDictUnifier.get(OrePrefix.gem, entry.material));

                    if (mortarGrindable)
                        ModHandler.addShapedRecipe(OreDictUnifier.get(OrePrefix.dustSmall, entry.material, 2),
                                "X",
                                "m",
                                'X', OreDictUnifier.get(OrePrefix.gemFlawed, entry.material));
                }
                RecipeMap.HAMMER_RECIPES.recipeBuilder()
                        .inputs(stack)
                        .outputs(OreDictUnifier.get(OrePrefix.gemChipped, entry.material, 2))
                        .duration(64)
                        .EUt(16)
                        .buildAndRegister();
                break;
            case gemFlawless:
                if (!noWorking && entry.material instanceof DustMaterial) {
                    RecipeMap.LATHE_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(1, stack))
                            .outputs(OreDictUnifier.get(OrePrefix.stickLong, entry.material), OreDictUnifier.getDust((DustMaterial) entry.material, entry.orePrefix.materialAmount - OrePrefix.stickLong.materialAmount))
                            .duration((int) Math.max(materialMass * 5L, 1L))
                            .EUt(16)
                            .buildAndRegister();
                    ModHandler.addShapedRecipe(OreDictUnifier.get(OrePrefix.gemFlawless, entry.material, 2), "h", "X", 'X', OreDictUnifier.get(OrePrefix.gemExquisite, entry.material));
                    if (mortarGrindable)
                        ModHandler.addShapedRecipe(OreDictUnifier.get(OrePrefix.dust, entry.material, 2), "X", "m", 'X', OreDictUnifier.get(OrePrefix.gemFlawless, entry.material));
                }
                RecipeMap.HAMMER_RECIPES.recipeBuilder()
                        .inputs(stack)
                        .outputs(OreDictUnifier.get(OrePrefix.gem, entry.material, 2))
                        .duration(64)
                        .EUt(16)
                        .buildAndRegister();
                break;
        }
    }
}
