package gregtech.loaders.oreprocessing;

import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.MetalMaterial;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import gregtech.common.items.MetaItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import static gregtech.api.GTValues.M;
import static gregtech.api.unification.material.type.DustMaterial.MatFlags.NO_SMASHING;
import static gregtech.api.unification.material.type.DustMaterial.MatFlags.NO_SMELTING;

public class ProcessingShaping  {

    private ProcessingShaping() { }

    public static void register() {
        ProcessingShaping processing = new ProcessingShaping();
        //OrePrefix.ingot.addProcessingHandler(processing);
        //OrePrefix.dust.addProcessingHandler(processing);
    }

    public void registerOre(UnificationEntry entry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();
        long materialMass = entry.material.getMass();
        int amount = (int) (entry.orePrefix.materialAmount / M);
        int voltageMultiplier;

        if ((entry.material instanceof MetalMaterial) && ((MetalMaterial) entry.material).blastFurnaceTemperature >= 2800) {
            voltageMultiplier = 64;
        } else {
            voltageMultiplier = 16;
        }

        if (!(amount > 0 && amount <= 64 && entry.orePrefix.materialAmount % M == 0L)) {
            return;
        }

        if (entry.material instanceof MetalMaterial && !entry.material.hasFlag(NO_SMELTING)) {

            if (entry.material.hasFlag(NO_SMASHING)) {
                voltageMultiplier /= 4;
            } else if (entry.orePrefix.name().startsWith(OrePrefix.dust.name())) {
                return;
            }

            MetalMaterial smeltInto = ((MetalMaterial) entry.material).smeltInto;
            if (!OrePrefix.block.isIgnored(smeltInto)) {
                RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
                    .inputs(GTUtility.copyAmount(9, stack))
                    .notConsumable(MetaItems.SHAPE_EXTRUDER_BLOCK)
                    .outputs(OreDictUnifier.get(OrePrefix.block, smeltInto, amount))
                    .duration(10 * amount)
                    .EUt(8 * voltageMultiplier)
                    .buildAndRegister();

                RecipeMap.ALLOY_SMELTER_RECIPES.recipeBuilder()
                    .inputs(GTUtility.copyAmount(9, stack))
                    .notConsumable(MetaItems.SHAPE_MOLD_BLOCK)
                    .outputs(OreDictUnifier.get(OrePrefix.block, smeltInto, amount))
                    .duration(5 * amount)
                    .EUt(4 * voltageMultiplier)
                    .buildAndRegister();
            }

            if (entry.material != smeltInto) {
                RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
                    .inputs(GTUtility.copyAmount(1, stack))
                    .notConsumable(MetaItems.SHAPE_EXTRUDER_INGOT)
                    .outputs(OreDictUnifier.get(OrePrefix.ingot, smeltInto, amount))
                    .duration(10)
                    .EUt(4 * voltageMultiplier)
                    .buildAndRegister();
            }

            RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copyAmount(1, stack))
                .notConsumable(MetaItems.SHAPE_EXTRUDER_PLATE)
                .outputs(OreDictUnifier.get(OrePrefix.plate, smeltInto, amount))
                .duration((int) Math.max(materialMass * amount, amount))
                .EUt(8 * voltageMultiplier)
                .buildAndRegister();

            if (amount * 2 <= 64) {
                RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
                    .inputs(GTUtility.copyAmount(1, stack))
                    .notConsumable(MetaItems.SHAPE_EXTRUDER_ROD)
                    .outputs(OreDictUnifier.get(OrePrefix.stick, smeltInto, amount * 2))
                    .duration((int) Math.max(materialMass * 2L * amount, amount))
                    .EUt(6 * voltageMultiplier)
                    .buildAndRegister();
            }
            if (amount * 2 <= 64) {
                RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
                    .inputs(GTUtility.copyAmount(1, stack))
                    .notConsumable(MetaItems.SHAPE_EXTRUDER_WIRE)
                    .outputs(OreDictUnifier.get(OrePrefix.wireGt01, smeltInto, amount * 2))
                    .duration((int) Math.max(materialMass * 2L * amount, amount))
                    .EUt(6 * voltageMultiplier)
                    .buildAndRegister();
            }
            if (amount * 8 <= 64) {
                RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
                    .inputs(GTUtility.copyAmount(1, stack))
                    .notConsumable(MetaItems.SHAPE_EXTRUDER_BOLT)
                    .outputs(OreDictUnifier.get(OrePrefix.bolt, smeltInto, amount * 8))
                    .duration((int) Math.max(materialMass * 2L * amount, amount))
                    .EUt(8 * voltageMultiplier)
                    .buildAndRegister();
            }
            if (amount * 4 <= 64) {
                RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
                    .inputs(GTUtility.copyAmount(1, stack))
                    .notConsumable(MetaItems.SHAPE_EXTRUDER_RING)
                    .outputs(OreDictUnifier.get(OrePrefix.ring, smeltInto, amount * 4))
                    .duration((int) Math.max(materialMass * 2L * amount, amount))
                    .EUt(6 * voltageMultiplier)
                    .buildAndRegister();

                if (!entry.material.hasFlag(NO_SMASHING)) {
                    ModHandler.addShapedRecipe("ring_" + entry.material,
                        OreDictUnifier.get(OrePrefix.ring, entry.material),
                        "h ",
                        " X",
                        'X', new UnificationEntry(OrePrefix.stick, entry.material));
                }
            }
            RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copyAmount(2, stack))
                .notConsumable(MetaItems.SHAPE_EXTRUDER_SWORD)
                .outputs(OreDictUnifier.get(OrePrefix.toolHeadSword, smeltInto, amount))
                .duration((int) Math.max(materialMass * 2L * amount, amount))
                .EUt(8 * voltageMultiplier)
                .buildAndRegister();

            RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copyAmount(3, stack))
                .notConsumable(MetaItems.SHAPE_EXTRUDER_PICKAXE)
                .outputs(OreDictUnifier.get(OrePrefix.toolHeadPickaxe, smeltInto, amount))
                .duration((int) Math.max(materialMass * 3L * amount, amount))
                .EUt(8 * voltageMultiplier)
                .buildAndRegister();

            RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copyAmount(1, stack))
                .notConsumable(MetaItems.SHAPE_EXTRUDER_SHOVEL)
                .outputs(OreDictUnifier.get(OrePrefix.toolHeadShovel, smeltInto, amount))
                .duration((int) Math.max(materialMass * amount, amount))
                .EUt(8 * voltageMultiplier)
                .buildAndRegister();

            RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copyAmount(3, stack))
                .notConsumable(MetaItems.SHAPE_EXTRUDER_AXE)
                .outputs(OreDictUnifier.get(OrePrefix.toolHeadAxe, smeltInto, amount))
                .duration((int) Math.max(materialMass * 3L * amount, amount))
                .EUt(8 * voltageMultiplier)
                .buildAndRegister();

            RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copyAmount(2, stack))
                .notConsumable(MetaItems.SHAPE_EXTRUDER_HOE)
                .outputs(OreDictUnifier.get(OrePrefix.toolHeadHoe, smeltInto, amount))
                .duration((int) Math.max(materialMass * 2L * amount, amount))
                .EUt(8 * voltageMultiplier)
                .buildAndRegister();

            RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copyAmount(6, stack))
                .notConsumable(MetaItems.SHAPE_EXTRUDER_HAMMER)
                .outputs(OreDictUnifier.get(OrePrefix.toolHeadHammer, smeltInto, amount))
                .duration((int) Math.max(materialMass * 6L * amount, amount))
                .EUt(8 * voltageMultiplier)
                .buildAndRegister();

            RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copyAmount(2, stack))
                .notConsumable(MetaItems.SHAPE_EXTRUDER_FILE)
                .outputs(OreDictUnifier.get(OrePrefix.toolHeadFile, smeltInto, amount))
                .duration((int) Math.max(materialMass * 2L * amount, amount))
                .EUt(8 * voltageMultiplier)
                .buildAndRegister();

            RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copyAmount(2, stack))
                .notConsumable(MetaItems.SHAPE_EXTRUDER_SAW)
                .outputs(OreDictUnifier.get(OrePrefix.toolHeadSaw, smeltInto, amount))
                .duration((int) Math.max(materialMass * 2L * amount, amount))
                .EUt(8 * voltageMultiplier)
                .buildAndRegister();

            RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copyAmount(4, stack))
                .notConsumable(MetaItems.SHAPE_EXTRUDER_GEAR)
                .outputs(OreDictUnifier.get(OrePrefix.gear, smeltInto, amount))
                .duration((int) Math.max(materialMass * 5L * amount, amount))
                .EUt(8 * voltageMultiplier)
                .buildAndRegister();

            RecipeMap.ALLOY_SMELTER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copyAmount(2, stack))
                .notConsumable(MetaItems.SHAPE_MOLD_PLATE)
                .outputs(OreDictUnifier.get(OrePrefix.plate, smeltInto, amount))
                .duration((int) Math.max(materialMass * 2L * amount, amount))
                .EUt(2 * voltageMultiplier)
                .buildAndRegister();

            RecipeMap.ALLOY_SMELTER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copyAmount(8, stack))
                .notConsumable(MetaItems.SHAPE_MOLD_GEAR)
                .outputs(OreDictUnifier.get(OrePrefix.gear, smeltInto, amount))
                .duration((int) Math.max(materialMass * 10L * amount, amount))
                .EUt(2 * voltageMultiplier)
                .buildAndRegister();

            if (entry.material == Materials.Steel) {
//                if (amount * 2 <= 64) {
//                    RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
//                        .inputs(GTUtility.copyAmount(1, stack))
//                        .notConsumable(MetaItems.SHAPE_EXTRUDER_CASING)
//                        .outputs(ModHandler.IC2.getIC2Item(ItemName.casing, CasingResourceType.steel, amount * 2))
//                        .duration(amount * 32)
//                        .EUt(3 * voltageMultiplier)
//                        .buildAndRegister();
//                }
//                if (amount * 2 <= 64) {
//                    RecipeMap.ALLOY_SMELTER_RECIPES.recipeBuilder()
//                        .inputs(GTUtility.copyAmount(2, stack))
//                        .notConsumable(MetaItems.SHAPE_MOLD_CASING)
//                        .outputs(ModHandler.IC2.getIC2Item(ItemName.casing, CasingResourceType.steel, amount * 3))
//                        .duration(amount * 128)
//                        .EUt(voltageMultiplier)
//                        .buildAndRegister();
//                }

            } else if (entry.material == Materials.Iron || entry.material == Materials.WroughtIron) {

//                RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
//                    .inputs(GTUtility.copyAmount(1, stack))
//                    .notConsumable(MetaItems.SHAPE_EXTRUDER_CELL)
//                    .outputs(ModHandler.IC2.getIC2Item(ItemName.crafting, CraftingItemType.fuel_rod, amount))
//                    .duration(amount * 128)
//                    .EUt(32)
//                    .buildAndRegister();
//
//                if (amount * 2 <= 64) {
//                    RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
//                        .inputs(GTUtility.copyAmount(1, stack))
//                        .notConsumable(MetaItems.SHAPE_EXTRUDER_CASING)
//                        .outputs(ModHandler.IC2.getIC2Item(ItemName.casing, CasingResourceType.iron, amount * 2))
//                        .duration(amount * 32)
//                        .EUt(3 * voltageMultiplier)
//                        .buildAndRegister();
//                }
//                if (amount * 2 <= 64) {
//                    RecipeMap.ALLOY_SMELTER_RECIPES.recipeBuilder()
//                        .inputs(GTUtility.copyAmount(2, stack))
//                        .notConsumable(MetaItems.SHAPE_MOLD_CASING)
//                        .outputs(ModHandler.IC2.getIC2Item(ItemName.casing, CasingResourceType.iron, amount * 3))
//                        .duration(amount * 128)
//                        .EUt(voltageMultiplier)
//                        .buildAndRegister();
//                }
                if (amount * 31 <= 64) {
                    RecipeMap.ALLOY_SMELTER_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(31, stack))
                        .notConsumable(MetaItems.SHAPE_MOLD_ANVIL)
                        .outputs(new ItemStack(Blocks.ANVIL, 1, 0))
                        .duration(amount * 512)
                        .EUt(4 * voltageMultiplier)
                        .buildAndRegister();
                }

            } else if (entry.material == Materials.Tin) {

//                RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
//                    .inputs(GTUtility.copyAmount(2, stack))
//                    .notConsumable(MetaItems.SHAPE_EXTRUDER_CELL)
//                    .outputs(ModHandler.IC2.getIC2Item(ItemName.fluid_cell, amount))
//                    .duration(amount * 128)
//                    .EUt(32)
//                    .buildAndRegister();
//
//                if (amount * 2 <= 64) {
//                    RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
//                        .inputs(GTUtility.copyAmount(1, stack))
//                        .notConsumable(MetaItems.SHAPE_EXTRUDER_CASING)
//                        .outputs(ModHandler.IC2.getIC2Item(ItemName.casing, CasingResourceType.tin, amount * 2))
//                        .duration(amount * 32)
//                        .EUt(3 * voltageMultiplier)
//                        .buildAndRegister();
//
//                }
//                if (amount * 2 <= 64) {
//                    RecipeMap.ALLOY_SMELTER_RECIPES.recipeBuilder()
//                        .inputs(GTUtility.copyAmount(2, stack))
//                        .notConsumable(MetaItems.SHAPE_MOLD_CASING)
//                        .outputs(ModHandler.IC2.getIC2Item(ItemName.casing, CasingResourceType.tin, amount * 3))
//                        .duration(amount * 128)
//                        .EUt(voltageMultiplier)
//                        .buildAndRegister();
//                }

            } else if (entry.material == Materials.Lead) {

//                if (amount * 2 <= 64) {
//                    RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
//                        .inputs(GTUtility.copyAmount(1, stack))
//                        .notConsumable(MetaItems.SHAPE_EXTRUDER_CASING)
//                        .outputs(ModHandler.IC2.getIC2Item(ItemName.casing, CasingResourceType.lead, amount * 2))
//                        .duration(amount * 32)
//                        .EUt(3 * voltageMultiplier)
//                        .buildAndRegister();
//                }
//                if (amount * 2 <= 64) {
//                    RecipeMap.ALLOY_SMELTER_RECIPES.recipeBuilder()
//                        .inputs(GTUtility.copyAmount(2, stack))
//                        .notConsumable(MetaItems.SHAPE_MOLD_CASING)
//                        .outputs(ModHandler.IC2.getIC2Item(ItemName.casing, CasingResourceType.lead, amount * 3))
//                        .duration(amount * 128)
//                        .EUt(voltageMultiplier)
//                        .buildAndRegister();
//                }

            } else if (entry.material == Materials.Copper || entry.material == Materials.AnnealedCopper) {

//                if (amount * 2 <= 64) {
//                    RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
//                        .inputs(GTUtility.copyAmount(1, stack))
//                        .notConsumable(MetaItems.SHAPE_EXTRUDER_CASING)
//                        .outputs(ModHandler.IC2.getIC2Item(ItemName.casing, CasingResourceType.copper, amount * 2))
//                        .duration(amount * 32)
//                        .EUt(3 * voltageMultiplier)
//                        .buildAndRegister();
//                }
//                if (amount * 2 <= 64) {
//                    RecipeMap.ALLOY_SMELTER_RECIPES.recipeBuilder()
//                        .inputs(GTUtility.copyAmount(2, stack))
//                        .notConsumable(MetaItems.SHAPE_MOLD_CASING)
//                        .outputs(ModHandler.IC2.getIC2Item(ItemName.casing, CasingResourceType.copper, amount * 3))
//                        .duration(amount * 128)
//                        .EUt(voltageMultiplier)
//                        .buildAndRegister();
//                }

            } else if (entry.material == Materials.Bronze) {

//                if (amount * 2 <= 64) {
//                    RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
//                        .inputs(GTUtility.copyAmount(1, stack))
//                        .notConsumable(MetaItems.SHAPE_EXTRUDER_CASING)
//                        .outputs(ModHandler.IC2.getIC2Item(ItemName.casing, CasingResourceType.bronze, amount * 2))
//                        .duration(amount * 32)
//                        .EUt(3 * voltageMultiplier)
//                        .buildAndRegister();
//                }
//                if (amount * 2 <= 64) {
//                    RecipeMap.ALLOY_SMELTER_RECIPES.recipeBuilder()
//                        .inputs(GTUtility.copyAmount(2, stack))
//                        .notConsumable(MetaItems.SHAPE_MOLD_CASING)
//                        .outputs(ModHandler.IC2.getIC2Item(ItemName.casing, CasingResourceType.bronze, amount * 3))
//                        .duration(amount * 128)
//                        .EUt(voltageMultiplier)
//                        .buildAndRegister();
//                }

            } else if (entry.material == Materials.Gold) {

//                if (amount * 2 <= 64) {
//                    RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
//                        .inputs(GTUtility.copyAmount(1, stack))
//                        .notConsumable(MetaItems.SHAPE_EXTRUDER_CASING)
//                        .outputs(ModHandler.IC2.getIC2Item(ItemName.casing, CasingResourceType.gold, amount * 2))
//                        .duration(amount * 32)
//                        .EUt(3 * voltageMultiplier)
//                        .buildAndRegister();
//                }
//                if (amount * 2 <= 64) {
//                    RecipeMap.ALLOY_SMELTER_RECIPES.recipeBuilder()
//                        .inputs(GTUtility.copyAmount(2, stack))
//                        .notConsumable(MetaItems.SHAPE_MOLD_CASING)
//                        .outputs(ModHandler.IC2.getIC2Item(ItemName.casing, CasingResourceType.gold, amount * 3))
//                        .duration(amount * 128)
//                        .EUt(voltageMultiplier)
//                        .buildAndRegister();
//                }
            }
        } else if (entry.material == Materials.Glass) {

            RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copyAmount(1, stack))
                .notConsumable(MetaItems.SHAPE_EXTRUDER_PLATE)
                .outputs(OreDictUnifier.get(OrePrefix.plate, entry.material, amount))
                .duration((int) Math.max(materialMass * amount, amount))
                .EUt(8 * voltageMultiplier)
                .buildAndRegister();

            RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copyAmount(1, stack))
                .notConsumable(MetaItems.SHAPE_EXTRUDER_BOTTLE)
                .outputs(new ItemStack(Items.GLASS_BOTTLE, 1))
                .duration(amount * 32)
                .EUt(16)
                .buildAndRegister();

            RecipeMap.ALLOY_SMELTER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copyAmount(1, stack))
                .notConsumable(MetaItems.SHAPE_MOLD_BOTTLE)
                .outputs(new ItemStack(Items.GLASS_BOTTLE, 1))
                .duration(amount * 64)
                .EUt(4)
                .buildAndRegister();
        }
    }

}
