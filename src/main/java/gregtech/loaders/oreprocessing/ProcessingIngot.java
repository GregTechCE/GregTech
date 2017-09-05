package gregtech.loaders.oreprocessing;

import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.FluidMaterial;
import gregtech.api.unification.material.type.MetalMaterial;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import gregtech.common.items.MetaItems;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.List;

import static gregtech.api.GTValues.L;
import static gregtech.api.unification.material.type.SolidMaterial.MatFlags.MORTAR_GRINDABLE;

public class ProcessingIngot implements IOreRegistrationHandler {

    public void register() {
        OrePrefix.ingot.addProcessingHandler(this);
    }

    public void registerOre(UnificationEntry entry, String modName, SimpleItemStack simpleStack) {
        if(entry.material instanceof MetalMaterial) {
            MetalMaterial material = (MetalMaterial) entry.material;
            ItemStack stack = simpleStack.asItemStack();

            OrePrefix prevPrefix = GTUtility.getItem(ORDER, ORDER.indexOf(entry.orePrefix) - 1, null);
            if(prevPrefix != null) {

            }

        }


        ItemStack stack = simpleStack.asItemStack();
        long materialMass = entry.material.getMass();
        boolean noSmashing = entry.material.hasFlag(DustMaterial.MatFlags.NO_SMASHING);
        boolean noSmelting = entry.material.hasFlag(DustMaterial.MatFlags.NO_SMELTING);

        switch (entry.orePrefix) {
            case ingot:
                if (entry.material instanceof FluidMaterial) {
                    RecipeMap.FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
                            .notConsumable(MetaItems.SHAPE_MOLD_INGOT)
                            .fluidInputs(((FluidMaterial) entry.material).getFluid(L))
                            .outputs(OreDictUnifier.get(OrePrefix.ingot, entry.material))
                            .duration(32)
                            .EUt(8)
                            .buildAndRegister();
                }

//                RecipeRegistrator.registerReverseFluidSmelting(stack, entry.material, entry.orePrefix.mMaterialAmount, null);
//                RecipeRegistrator.registerReverseMacerating(stack, entry.material, entry.orePrefix.mMaterialAmount, null, null, null, false);
//                if (entry.material.smeltInto.mArcSmeltInto != entry.material) {
//                    RecipeRegistrator.registerReverseArcSmelting(GTUtility.copyAmount(1, stack), entry.material, entry.orePrefix.mMaterialAmount, null, null, null);
//                }

                if (entry.material instanceof MetalMaterial) {
                    ItemStack tempDustStack = OreDictUnifier.getDust(((MetalMaterial) entry.material).macerateInto, 1);
                    if (tempDustStack != null && (((MetalMaterial) entry.material).blastFurnaceTemperature > 0 || noSmelting)) {
                        ModHandler.removeFurnaceSmelting(tempDustStack);
                    }
                }

                if (!entry.material.hasFlag(DustMaterial.MatFlags.NO_WORKING)) {

                    if (!entry.material.hasFlag(SMELTING_TO_GEM))
                        ModHandler.addShapedRecipe(OreDictUnifier.get(OrePrefix.ingot, entry.material, 1),
                                "XXX",
                                "XXX",
                                "XXX",
                                'X', OreDictUnifier.get(OrePrefix.nugget, entry.material));

                    if ((entry.material.hasFlag(MORTAR_GRINDABLE)))
                        ModHandler.addShapedRecipe(OreDictUnifier.get(OrePrefix.dust, entry.material),
                                "X",
                                "m",
                                'X', OreDictUnifier.get(OrePrefix.ingot, entry.material));
                }

                if (!noSmashing) {
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

//                RecipeRegistrator.registerUsagesForMaterials(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.plate, entry.material).toString(), !noSmashing);

                break;
            case ingotDouble:
                if (!noSmashing) {
                    RecipeMap.BENDER_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(1, stack))
                            .outputs(OreDictUnifier.get(OrePrefix.plateDouble, entry.material))
                            .duration((int) Math.max(materialMass * 2L, 1L))
                            .EUt(96)
                            .buildAndRegister();
                    RecipeMap.BENDER_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(2, stack))
                            .outputs(OreDictUnifier.get(OrePrefix.plateQuadruple, entry.material))
                            .duration((int) Math.max(materialMass * 2L, 1L)).EUt(96)
                            .buildAndRegister();

                    ModHandler.addShapedRecipe(OreDictUnifier.get(OrePrefix.ingotDouble, entry.material, 1),
                            "I",
                            "I",
                            "h",
                            'I', OreDictUnifier.get(OrePrefix.plate, entry.material));
                }
                break;
            case ingotTriple:
                if (!noSmashing) {
                    RecipeMap.BENDER_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(1, stack))
                            .outputs(OreDictUnifier.get(OrePrefix.plateTriple, entry.material, 1))
                            .duration((int) Math.max(materialMass, 1L))
                            .EUt(96)
                            .buildAndRegister();
                    RecipeMap.BENDER_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(3, stack))
                            .outputs(OreDictUnifier.get(OrePrefix.plateDense, entry.material, 1))
                            .duration((int) Math.max(materialMass * 3L, 1L))
                            .EUt(96)
                            .buildAndRegister();

                    ModHandler.addShapedRecipe(OreDictUnifier.get(OrePrefix.ingotTriple, entry.material),
                            "I",
                            "B",
                            "h",
                            'I', OreDictUnifier.get(OrePrefix.ingotDouble, entry.material),
                            'B', OreDictUnifier.get(OrePrefix.ingot, entry.material));
                }
                break;
            case ingotQuadruple:
                if (!noSmashing) {
                    RecipeMap.BENDER_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(1, stack))
                            .outputs(OreDictUnifier.get(OrePrefix.plateQuadruple, entry.material, 1))
                            .duration((int) Math.max(materialMass, 1L))
                            .EUt(96)
                            .buildAndRegister();

                    ModHandler.addShapedRecipe(OreDictUnifier.get(OrePrefix.ingotQuadruple, entry.material),
                            "I",
                            "B",
                            "h",
                            'I', OreDictUnifier.get(OrePrefix.ingotTriple, entry.material),
                            'B', OreDictUnifier.get(OrePrefix.ingot, entry.material));
                }
                break;
            case ingotQuintuple:
                if (!noSmashing) {
                    RecipeMap.BENDER_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(1, stack))
                            .outputs(OreDictUnifier.get(OrePrefix.plateQuintuple, entry.material, 1))
                            .duration((int) Math.max(materialMass, 1L))
                            .EUt(96)
                            .buildAndRegister();

                    ModHandler.addShapedRecipe(OreDictUnifier.get(OrePrefix.ingotQuintuple, entry.material, 1),
                            "I",
                            "B",
                            "h",
                            'I', OreDictUnifier.get(OrePrefix.ingotQuadruple, entry.material),
                            'B', OreDictUnifier.get(OrePrefix.ingot, entry.material));
                }
                break;
            case ingotHot:
                RecipeMap.VACUUM_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack))
                        .outputs(OreDictUnifier.get(OrePrefix.ingot, entry.material, 1))
                        .duration((int) Math.max(materialMass * 3L, 1L))
                        .buildAndRegister();
                break;
        }
    }
}
