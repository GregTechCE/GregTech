package gregtech.loaders.postload;

import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.builders.AssemblyLineRecipeBuilder;
import gregtech.api.recipes.builders.PBFRecipeBuilder;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.MetalMaterial;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import gregtech.common.blocks.BlockConcrete.ConcreteVariant;
import gregtech.common.blocks.BlockMachineCasing.MachineCasingType;
import gregtech.common.blocks.BlockMetalCasing.MetalCasingType;
import gregtech.common.blocks.BlockMultiblockCasing.MultiblockCasingType;
import gregtech.common.blocks.BlockTurbineCasing.TurbineCasingType;
import gregtech.common.blocks.BlockWireCoil.CoilType;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.blocks.StoneBlock.ChiselingVariant;
import gregtech.common.items.MetaItems;
import gregtech.common.metatileentities.MetaTileEntities;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Arrays;

import static gregtech.api.GTValues.L;
import static gregtech.api.GTValues.M;

public class MachineRecipeLoader {

    private static final MaterialStack[][] alloySmelterList = {
        {new MaterialStack(Materials.Tetrahedrite, 3L), new MaterialStack(Materials.Tin, 1), new MaterialStack(Materials.Bronze, 3L)},
        {new MaterialStack(Materials.Tetrahedrite, 3L), new MaterialStack(Materials.Zinc, 1), new MaterialStack(Materials.Brass, 3L)},
        {new MaterialStack(Materials.Copper, 3L), new MaterialStack(Materials.Tin, 1), new MaterialStack(Materials.Bronze, 4L)},
        {new MaterialStack(Materials.Copper, 3L), new MaterialStack(Materials.Zinc, 1), new MaterialStack(Materials.Brass, 4L)},
        {new MaterialStack(Materials.Copper, 1), new MaterialStack(Materials.Nickel, 1), new MaterialStack(Materials.Cupronickel, 2L)},
        {new MaterialStack(Materials.Copper, 1), new MaterialStack(Materials.Redstone, 4L), new MaterialStack(Materials.RedAlloy, 1)},
        {new MaterialStack(Materials.AnnealedCopper, 3L), new MaterialStack(Materials.Tin, 1), new MaterialStack(Materials.Bronze, 4L)},
        {new MaterialStack(Materials.AnnealedCopper, 3L), new MaterialStack(Materials.Zinc, 1), new MaterialStack(Materials.Brass, 4L)},
        {new MaterialStack(Materials.AnnealedCopper, 1), new MaterialStack(Materials.Nickel, 1), new MaterialStack(Materials.Cupronickel, 2L)},
        {new MaterialStack(Materials.AnnealedCopper, 1), new MaterialStack(Materials.Redstone, 4L), new MaterialStack(Materials.RedAlloy, 1)},
        {new MaterialStack(Materials.Iron, 1), new MaterialStack(Materials.Tin, 1), new MaterialStack(Materials.TinAlloy, 2L)},
        {new MaterialStack(Materials.WroughtIron, 1), new MaterialStack(Materials.Tin, 1), new MaterialStack(Materials.TinAlloy, 2L)},
        {new MaterialStack(Materials.Iron, 2L), new MaterialStack(Materials.Nickel, 1), new MaterialStack(Materials.Invar, 3L)},
        {new MaterialStack(Materials.WroughtIron, 2L), new MaterialStack(Materials.Nickel, 1), new MaterialStack(Materials.Invar, 3L)},
        {new MaterialStack(Materials.Tin, 9L), new MaterialStack(Materials.Antimony, 1), new MaterialStack(Materials.SolderingAlloy, 10L)},
        {new MaterialStack(Materials.Lead, 4L), new MaterialStack(Materials.Antimony, 1), new MaterialStack(Materials.BatteryAlloy, 5L)},
        {new MaterialStack(Materials.Gold, 1), new MaterialStack(Materials.Silver, 1), new MaterialStack(Materials.Electrum, 2L)},
        {new MaterialStack(Materials.Magnesium, 1), new MaterialStack(Materials.Aluminium, 2L), new MaterialStack(Materials.Magnalium, 3L)}};

    private static final MaterialStack[] solderingList = {
        new MaterialStack(Materials.Tin, 2L),
        new MaterialStack(Materials.SolderingAlloy, 1L),
        new MaterialStack(Materials.Lead, 4L)
    };

    public static void init() {
        shapingRecipes();
        for (OrePrefix prefix : Arrays.asList(OrePrefix.dust, OrePrefix.dustSmall, OrePrefix.dustTiny)) {
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration((int) (100 * prefix.materialAmount / M)).EUt(8).input(prefix, Materials.EnderPearl, 1).input(prefix, Materials.Blaze, 1).outputs(OreDictUnifier.getDust(Materials.EnderEye, 1 * prefix.materialAmount)).buildAndRegister();
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration((int) (200 * prefix.materialAmount / M)).EUt(8).input(prefix, Materials.Gold, 1).input(prefix, Materials.Silver, 1).outputs(OreDictUnifier.getDust(Materials.Electrum, 2 * prefix.materialAmount)).buildAndRegister();
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration((int) (300 * prefix.materialAmount / M)).EUt(8).input(prefix, Materials.Iron, 2).input(prefix, Materials.Nickel, 1).outputs(OreDictUnifier.getDust(Materials.Invar, 3 * prefix.materialAmount)).buildAndRegister();
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration((int) (900 * prefix.materialAmount / M)).EUt(8).input(prefix, Materials.Iron, 4).input(prefix, Materials.Invar, 3).input(prefix, Materials.Manganese, 1).input(prefix, Materials.Chrome, 1).outputs(OreDictUnifier.getDust(Materials.StainlessSteel, 9 * prefix.materialAmount)).buildAndRegister();
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration((int) (300 * prefix.materialAmount / M)).EUt(8).input(prefix, Materials.Iron, 1).input(prefix, Materials.Aluminium, 1).input(prefix, Materials.Chrome, 1).outputs(OreDictUnifier.getDust(Materials.Kanthal, 3 * prefix.materialAmount)).buildAndRegister();
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration((int) (600 * prefix.materialAmount / M)).EUt(8).input(prefix, Materials.Copper, 3).input(prefix, Materials.Barium, 2).input(prefix, Materials.Yttrium, 1).outputs(OreDictUnifier.getDust(Materials.YttriumBariumCuprate, 6 * prefix.materialAmount)).buildAndRegister();
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration((int) (400 * prefix.materialAmount / M)).EUt(8).input(prefix, Materials.Copper, 3).input(prefix, Materials.Zinc, 1).outputs(OreDictUnifier.getDust(Materials.Brass, 4 * prefix.materialAmount)).buildAndRegister();
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration((int) (400 * prefix.materialAmount / M)).EUt(8).input(prefix, Materials.Copper, 3).input(prefix, Materials.Tin, 1).outputs(OreDictUnifier.getDust(Materials.Bronze, 4 * prefix.materialAmount)).buildAndRegister();
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration((int) (200 * prefix.materialAmount / M)).EUt(8).input(prefix, Materials.Copper, 1).input(prefix, Materials.Nickel, 1).outputs(OreDictUnifier.getDust(Materials.Cupronickel, 2 * prefix.materialAmount)).buildAndRegister();
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration((int) (500 * prefix.materialAmount / M)).EUt(8).input(prefix, Materials.Copper, 1).input(prefix, Materials.Gold, 4).outputs(OreDictUnifier.getDust(Materials.RoseGold, 5 * prefix.materialAmount)).buildAndRegister();
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration((int) (500 * prefix.materialAmount / M)).EUt(8).input(prefix, Materials.Copper, 1).input(prefix, Materials.Silver, 4).outputs(OreDictUnifier.getDust(Materials.SterlingSilver, 5 * prefix.materialAmount)).buildAndRegister();
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration((int) (500 * prefix.materialAmount / M)).EUt(8).input(prefix, Materials.Copper, 3).input(prefix, Materials.Electrum, 2).outputs(OreDictUnifier.getDust(Materials.BlackBronze, 5 * prefix.materialAmount)).buildAndRegister();
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration((int) (500 * prefix.materialAmount / M)).EUt(8).input(prefix, Materials.Bismuth, 1).input(prefix, Materials.Brass, 4).outputs(OreDictUnifier.getDust(Materials.BismuthBronze, 5 * prefix.materialAmount)).buildAndRegister();
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration((int) (500 * prefix.materialAmount / M)).EUt(8).input(prefix, Materials.BlackBronze, 1).input(prefix, Materials.Nickel, 1).input(prefix, Materials.Steel, 3).outputs(OreDictUnifier.getDust(Materials.BlackSteel, 5 * prefix.materialAmount)).buildAndRegister();
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration((int) (800 * prefix.materialAmount / M)).EUt(8).input(prefix, Materials.SterlingSilver, 1).input(prefix, Materials.BismuthBronze, 1).input(prefix, Materials.BlackSteel, 4).input(prefix, Materials.Steel, 2).outputs(OreDictUnifier.getDust(Materials.RedSteel, 8 * prefix.materialAmount)).buildAndRegister();
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration((int) (800 * prefix.materialAmount / M)).EUt(8).input(prefix, Materials.RoseGold, 1).input(prefix, Materials.Brass, 1).input(prefix, Materials.BlackSteel, 4).input(prefix, Materials.Steel, 2).outputs(OreDictUnifier.getDust(Materials.BlueSteel, 8 * prefix.materialAmount)).buildAndRegister();
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration((int) (900 * prefix.materialAmount / M)).EUt(8).input(prefix, Materials.Cobalt, 5).input(prefix, Materials.Chrome, 2).input(prefix, Materials.Nickel, 1).input(prefix, Materials.Molybdenum, 1).outputs(OreDictUnifier.getDust(Materials.Ultimet, 9 * prefix.materialAmount)).buildAndRegister();
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration((int) (900 * prefix.materialAmount / M)).EUt(8).input(prefix, Materials.Brass, 7).input(prefix, Materials.Aluminium, 1).input(prefix, Materials.Cobalt, 1).outputs(OreDictUnifier.getDust(Materials.CobaltBrass, 9 * prefix.materialAmount)).buildAndRegister();
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration((int) (400 * prefix.materialAmount / M)).EUt(8).input(prefix, Materials.Saltpeter, 2).input(prefix, Materials.Sulfur, 1).input(prefix, Materials.Coal, 1).outputs(OreDictUnifier.getDust(Materials.Gunpowder, 4 * prefix.materialAmount)).buildAndRegister();
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration((int) (300 * prefix.materialAmount / M)).EUt(8).input(prefix, Materials.Saltpeter, 2).input(prefix, Materials.Sulfur, 1).input(prefix, Materials.Charcoal, 1).outputs(OreDictUnifier.getDust(Materials.Gunpowder, 3 * prefix.materialAmount)).buildAndRegister();
        }

        for (MaterialStack[] stack : alloySmelterList) {
            ItemStack firstDust = OreDictUnifier.get(OrePrefix.dust, stack[0].material, (int) stack[0].amount);
            ItemStack secondDust = OreDictUnifier.get(OrePrefix.dust, stack[1].material, (int) stack[1].amount);
            ItemStack firstIngot = OreDictUnifier.get(OrePrefix.ingot, stack[0].material, (int) stack[0].amount);
            ItemStack secondIngot = OreDictUnifier.get(OrePrefix.ingot, stack[1].material, (int) stack[1].amount);
            ItemStack outputIngot = OreDictUnifier.get(OrePrefix.ingot, stack[2].material, (int) stack[2].amount);
            if (!outputIngot.isEmpty()) {
                if(!firstIngot.isEmpty() && !secondIngot.isEmpty()) {
                    RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder().duration((int) stack[2].amount * 50).EUt(16).inputs(firstIngot, secondIngot).outputs(outputIngot).buildAndRegister();
                    RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder().duration((int) stack[2].amount * 50).EUt(16).inputs(firstDust, secondIngot).outputs(outputIngot).buildAndRegister();
                    RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder().duration((int) stack[2].amount * 50).EUt(16).inputs(firstIngot, secondDust).outputs(outputIngot).buildAndRegister();
                }
                RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder().duration((int) stack[2].amount * 50).EUt(16).inputs(firstDust, secondDust).outputs(outputIngot).buildAndRegister();
            }
        }

        for (MaterialStack stack : solderingList) {
            MetalMaterial material = (MetalMaterial) stack.material;
            int multiplier = (int) stack.amount;
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(16).EUt(8).input(OrePrefix.plate, Materials.Steel, 1).input(OrePrefix.wireGtSingle, Materials.RedAlloy, 2).fluidInputs(material.getFluid(144 * multiplier / 8)).outputs(MetaItems.CIRCUIT_PRIMITIVE.getStackForm()).buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(16).EUt(8).input(OrePrefix.plate, Materials.Plastic, 1).input(OrePrefix.wireGtSingle, Materials.RedAlloy, 1).fluidInputs(material.getFluid(144 * multiplier / 8)).outputs(MetaItems.CIRCUIT_PRIMITIVE.getStackForm()).buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(32).EUt(16).inputs(MetaItems.CIRCUIT_BOARD_BASIC.getStackForm(), MetaItems.CIRCUIT_PRIMITIVE.getStackForm(2)).fluidInputs(material.getFluid(144 * multiplier / 4)).outputs(MetaItems.CIRCUIT_BASIC.getStackForm()).buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(32).EUt(16).inputs(MetaItems.CIRCUIT_BASIC.getStackForm(), MetaItems.CIRCUIT_PRIMITIVE.getStackForm(2)).fluidInputs(material.getFluid(144 * multiplier / 4)).outputs(MetaItems.CIRCUIT_GOOD.getStackForm()).buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(32).EUt(64).inputs(MetaItems.CIRCUIT_BOARD_ADVANCED.getStackForm(), MetaItems.CIRCUIT_PARTS_ADVANCED.getStackForm(2)).fluidInputs(material.getFluid(144 * multiplier / 2)).outputs(MetaItems.CIRCUIT_ADVANCED.getStackForm()).buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(32).EUt(64).inputs(MetaItems.CIRCUIT_BOARD_ADVANCED.getStackForm(), MetaItems.CIRCUIT_PARTS_CRYSTAL_CHIP_ELITE.getStackForm()).fluidInputs(material.getFluid(144 * multiplier / 2)).outputs(MetaItems.CIRCUIT_DATA.getStackForm()).buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(32).EUt(256).inputs(MetaItems.CIRCUIT_BOARD_ELITE.getStackForm(), MetaItems.CIRCUIT_DATA.getStackForm(3)).fluidInputs(material.getFluid(144 * multiplier / 1)).outputs(MetaItems.CIRCUIT_ELITE.getStackForm()).buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(32).EUt(256).inputs(MetaItems.CIRCUIT_BOARD_ELITE.getStackForm(), MetaItems.CIRCUIT_PARTS_CRYSTAL_CHIP_MASTER.getStackForm(3)).fluidInputs(material.getFluid(144 * multiplier / 1)).outputs(MetaItems.CIRCUIT_MASTER.getStackForm()).buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(128).EUt(64).inputs(MetaItems.CIRCUIT_DATA.getStackForm()).input(OrePrefix.plate, Materials.Plastic, 2).fluidInputs(material.getFluid(144 * multiplier / 2)).outputs(MetaItems.TOOL_DATASTICK.getStackForm()).buildAndRegister();

            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(800).EUt(16).inputs(new ItemStack(Blocks.LEVER, 1, OreDictionary.WILDCARD_VALUE)).input(OrePrefix.plate, material, 1).fluidInputs(material.getFluid(144 * multiplier / 2)).outputs(MetaItems.COVER_CONTROLLER.getStackForm()).buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(800).EUt(16).inputs(new ItemStack(Blocks.REDSTONE_TORCH, 1, OreDictionary.WILDCARD_VALUE)).input(OrePrefix.plate, material, 1).fluidInputs(material.getFluid(144 * multiplier / 2)).outputs(MetaItems.COVER_ACTIVITY_DETECTOR.getStackForm()).buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(800).EUt(16).inputs(new ItemStack(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, 1, OreDictionary.WILDCARD_VALUE)).input(OrePrefix.plate, material, 1).fluidInputs(material.getFluid(144 * multiplier / 2)).outputs(MetaItems.COVER_FLUID_DETECTOR.getStackForm()).buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(800).EUt(16).inputs(new ItemStack(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE, 1, OreDictionary.WILDCARD_VALUE)).input(OrePrefix.plate, material, 1).fluidInputs(material.getFluid(144 * multiplier / 2)).outputs(MetaItems.COVER_ITEM_DETECTOR.getStackForm()).buildAndRegister();
        }

        PBFRecipeBuilder.start().input(OrePrefix.ingot, Materials.Iron).output(OreDictUnifier.get(OrePrefix.ingot, Materials.Steel)).duration(1500).fuelAmount(2).buildAndRegister();
        PBFRecipeBuilder.start().input(OrePrefix.block, Materials.Iron).output(OreDictUnifier.get(OrePrefix.block, Materials.Steel)).duration(13500).fuelAmount(18).buildAndRegister();
        PBFRecipeBuilder.start().input(OrePrefix.ingot, Materials.WroughtIron).output(OreDictUnifier.get(OrePrefix.ingot, Materials.Steel)).duration(600).fuelAmount(2).buildAndRegister();
        PBFRecipeBuilder.start().input(OrePrefix.block, Materials.WroughtIron).output(OreDictUnifier.get(OrePrefix.block, Materials.Steel)).duration(5600).fuelAmount(18).buildAndRegister();

        RecipeMaps.FLUID_EXTRACTION_RECIPES.recipeBuilder().duration(32).EUt(2).inputs(new ItemStack(Items.WHEAT_SEEDS, 1, OreDictionary.WILDCARD_VALUE)).fluidOutputs(Materials.SeedOil.getFluid(5)).buildAndRegister();
        RecipeMaps.FLUID_EXTRACTION_RECIPES.recipeBuilder().duration(32).EUt(2).inputs(new ItemStack(Items.MELON_SEEDS, 1, OreDictionary.WILDCARD_VALUE)).fluidOutputs(Materials.SeedOil.getFluid(3)).buildAndRegister();
        RecipeMaps.FLUID_EXTRACTION_RECIPES.recipeBuilder().duration(32).EUt(2).inputs(new ItemStack(Items.PUMPKIN_SEEDS, 1, OreDictionary.WILDCARD_VALUE)).fluidOutputs(Materials.SeedOil.getFluid(6)).buildAndRegister();
        RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder().duration(100).EUt(16).input(OrePrefix.ingot, Materials.Rubber, 2).input(OrePrefix.wireGtSingle, Materials.Copper, 1).outputs(OreDictUnifier.get(OrePrefix.cableGtSingle,Materials.Copper,1)).buildAndRegister();

        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(20).EUt(16).input(OrePrefix.dust, Materials.Clay, 1).input(OrePrefix.dust, Materials.Stone, 3).fluidInputs(Materials.Water.getFluid(500)).fluidOutputs(Materials.Concrete.getFluid(576)).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(100).EUt(8).inputs(new ItemStack(Blocks.BROWN_MUSHROOM), new ItemStack(Items.SPIDER_EYE)).input(OrePrefix.dust, Materials.Sugar, 1).outputs(new ItemStack(Items.FERMENTED_SPIDER_EYE)).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(16).EUt(16).fluidInputs(Materials.LightFuel.getFluid(5000), Materials.HeavyFuel.getFluid(1000)).fluidOutputs(Materials.Fuel.getFluid(6000)).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(64).EUt(16).input(OrePrefix.dust, Materials.Stone, 1).fluidInputs(Materials.Lubricant.getFluid(20), ModHandler.getWater(1000)).fluidOutputs(Materials.DrillingFluid.getFluid(5000)).buildAndRegister();

        RecipeMaps.FLUID_CANNER_RECIPES.recipeBuilder().duration(100).EUt(30).inputs(MetaItems.BATTERY_HULL_LV.getStackForm()).fluidInputs(Materials.Mercury.getFluid(1000)).outputs(MetaItems.BATTERY_SU_LV_MERCURY.getFullyCharged(1)).buildAndRegister();
        RecipeMaps.FLUID_CANNER_RECIPES.recipeBuilder().duration(200).EUt(30).inputs(MetaItems.BATTERY_HULL_MV.getStackForm()).fluidInputs(Materials.Mercury.getFluid(4000)).outputs(MetaItems.BATTERY_SU_MV_MERCURY.getFullyCharged(1)).buildAndRegister();
        RecipeMaps.FLUID_CANNER_RECIPES.recipeBuilder().duration(400).EUt(30).inputs(MetaItems.BATTERY_HULL_HV.getStackForm()).fluidInputs(Materials.Mercury.getFluid(16000)).outputs(MetaItems.BATTERY_SU_HV_MERCURY.getFullyCharged(1)).buildAndRegister();
        RecipeMaps.FLUID_CANNER_RECIPES.recipeBuilder().duration(100).EUt(30).inputs(MetaItems.BATTERY_HULL_LV.getStackForm()).fluidInputs(Materials.SulfuricAcid.getFluid(1000)).outputs(MetaItems.BATTERY_SU_LV_SULFURICACID.getFullyCharged(1)).buildAndRegister();
        RecipeMaps.FLUID_CANNER_RECIPES.recipeBuilder().duration(200).EUt(30).inputs(MetaItems.BATTERY_HULL_MV.getStackForm()).fluidInputs(Materials.SulfuricAcid.getFluid(4000)).outputs(MetaItems.BATTERY_SU_MV_SULFURICACID.getFullyCharged(1)).buildAndRegister();
        RecipeMaps.FLUID_CANNER_RECIPES.recipeBuilder().duration(400).EUt(30).inputs(MetaItems.BATTERY_HULL_HV.getStackForm()).fluidInputs(Materials.SulfuricAcid.getFluid(16000)).outputs(MetaItems.BATTERY_SU_HV_SULFURICACID.getFullyCharged(1)).buildAndRegister();

        RecipeMaps.FLUID_SOLIDFICATION_RECIPES.recipeBuilder().duration(128).EUt(4).notConsumable(MetaItems.SHAPE_MOLD_BALL.getStackForm()).fluidInputs(Materials.Water.getFluid(250)).outputs(new ItemStack(Items.SNOWBALL)).buildAndRegister();
        RecipeMaps.FLUID_SOLIDFICATION_RECIPES.recipeBuilder().duration(128).EUt(4).notConsumable(MetaItems.SHAPE_MOLD_BALL.getStackForm()).fluidInputs(ModHandler.getDistilledWater(250)).outputs(new ItemStack(Items.SNOWBALL)).buildAndRegister();
        RecipeMaps.FLUID_SOLIDFICATION_RECIPES.recipeBuilder().duration(512).EUt(4).notConsumable(MetaItems.SHAPE_MOLD_BLOCK.getStackForm()).fluidInputs(Materials.Water.getFluid(1000)).outputs(new ItemStack(Blocks.SNOW)).buildAndRegister();
        RecipeMaps.FLUID_SOLIDFICATION_RECIPES.recipeBuilder().duration(512).EUt(4).notConsumable(MetaItems.SHAPE_MOLD_BLOCK.getStackForm()).fluidInputs(ModHandler.getDistilledWater(1000)).outputs(new ItemStack(Blocks.SNOW)).buildAndRegister();
        RecipeMaps.FLUID_SOLIDFICATION_RECIPES.recipeBuilder().duration(1024).EUt(16).notConsumable(MetaItems.SHAPE_MOLD_BLOCK.getStackForm()).fluidInputs(Materials.Lava.getFluid(1000)).outputs(new ItemStack(Blocks.OBSIDIAN)).buildAndRegister();
        RecipeMaps.FLUID_SOLIDFICATION_RECIPES.recipeBuilder().duration(12).EUt(4).notConsumable(MetaItems.SHAPE_MOLD_BLOCK.getStackForm()).fluidInputs(Materials.Concrete.getFluid(144)).outputs(MetaBlocks.CONCRETE.getItemVariant(ConcreteVariant.LIGHT_CONCRETE, ChiselingVariant.NORMAL)).buildAndRegister();
        RecipeMaps.FLUID_SOLIDFICATION_RECIPES.recipeBuilder().duration(12).EUt(4).notConsumable(MetaItems.SHAPE_MOLD_BLOCK.getStackForm()).fluidInputs(Materials.Glowstone.getFluid(576)).outputs(new ItemStack(Blocks.GLOWSTONE)).buildAndRegister();
        RecipeMaps.FLUID_SOLIDFICATION_RECIPES.recipeBuilder().duration(12).EUt(4).notConsumable(MetaItems.SHAPE_MOLD_BLOCK.getStackForm()).fluidInputs(Materials.Glass.getFluid(144)).outputs(new ItemStack(Blocks.GLASS)).buildAndRegister();
        RecipeMaps.FLUID_SOLIDFICATION_RECIPES.recipeBuilder().duration(12).EUt(4).notConsumable(MetaItems.SHAPE_MOLD_PLATE.getStackForm()).fluidInputs(Materials.Glass.getFluid(144)).outputs(OreDictUnifier.get(OrePrefix.plate,Materials.Glass,1)).buildAndRegister();
        RecipeMaps.FLUID_SOLIDFICATION_RECIPES.recipeBuilder().duration(128).EUt(16).notConsumable(MetaItems.SHAPE_MOLD_ANVIL.getStackForm()).fluidInputs(Materials.Iron.getFluid(4464)).outputs(new ItemStack(Blocks.ANVIL)).buildAndRegister();
        RecipeMaps.FLUID_SOLIDFICATION_RECIPES.recipeBuilder().duration(128).EUt(16).notConsumable(MetaItems.SHAPE_MOLD_ANVIL.getStackForm()).fluidInputs(Materials.WroughtIron.getFluid(4464)).outputs(new ItemStack(Blocks.ANVIL)).buildAndRegister();

        RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(12).EUt(4).input(OrePrefix.dust, Materials.Coal, 1).fluidInputs(Materials.Water.getFluid(125)).outputs(OreDictUnifier.get(OrePrefix.dust,Materials.HydratedCoal,1)).buildAndRegister();
        RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(200).EUt(4).input(OrePrefix.dust, Materials.Wood, 1).fluidInputs(Materials.Water.getFluid(100)).outputs(new ItemStack(Items.PAPER)).buildAndRegister();
        RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(100).EUt(4).input(OrePrefix.dust, Materials.Paper, 1).fluidInputs(Materials.Water.getFluid(100)).outputs(new ItemStack(Items.PAPER)).buildAndRegister();
        RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(100).EUt(8).inputs(new ItemStack(Items.REEDS, 1, OreDictionary.WILDCARD_VALUE)).fluidInputs(Materials.Water.getFluid(100)).outputs(new ItemStack(Items.PAPER)).buildAndRegister();
        RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(12).EUt(4).input(OrePrefix.dust, Materials.Coal, 1).fluidInputs(ModHandler.getDistilledWater(125)).outputs(OreDictUnifier.get(OrePrefix.dust,Materials.HydratedCoal,1)).buildAndRegister();
        RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(200).EUt(4).input(OrePrefix.dust, Materials.Wood, 1).fluidInputs(ModHandler.getDistilledWater(100)).outputs(new ItemStack(Items.PAPER)).buildAndRegister();
        RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(100).EUt(4).input(OrePrefix.dust, Materials.Paper, 1).fluidInputs(ModHandler.getDistilledWater(100)).outputs(new ItemStack(Items.PAPER)).buildAndRegister();

        RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(100).EUt(8).inputs(new ItemStack(Items.REEDS, 1, OreDictionary.WILDCARD_VALUE)).fluidInputs(ModHandler.getDistilledWater(100)).outputs(new ItemStack(Items.PAPER)).buildAndRegister();
        RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(400).EUt(2).inputs(new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE)).fluidInputs(Materials.Chlorine.getFluid(50)).outputs(new ItemStack(Blocks.WOOL)).buildAndRegister();
        RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(400).EUt(2).inputs(new ItemStack(Blocks.CARPET, 1, OreDictionary.WILDCARD_VALUE)).fluidInputs(Materials.Chlorine.getFluid(25)).outputs(new ItemStack(Blocks.CARPET)).buildAndRegister();
        RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(400).EUt(2).inputs(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, OreDictionary.WILDCARD_VALUE)).fluidInputs(Materials.Chlorine.getFluid(50)).outputs(new ItemStack(Blocks.HARDENED_CLAY)).buildAndRegister();
        RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(400).EUt(2).inputs(new ItemStack(Blocks.STAINED_GLASS, 1, OreDictionary.WILDCARD_VALUE)).fluidInputs(Materials.Chlorine.getFluid(50)).outputs(new ItemStack(Blocks.GLASS)).buildAndRegister();
        RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(400).EUt(2).inputs(new ItemStack(Blocks.STAINED_GLASS_PANE, 1, OreDictionary.WILDCARD_VALUE)).fluidInputs(Materials.Chlorine.getFluid(20)).outputs(new ItemStack(Blocks.GLASS_PANE)).buildAndRegister();

        RecipeMaps.FLUID_EXTRACTION_RECIPES.recipeBuilder().duration(128).EUt(4).inputs(new ItemStack(Items.COAL, 1, 1)).chancedOutput(OreDictUnifier.get(OrePrefix.dust,Materials.Ash,1), 1000).fluidOutputs(Materials.Creosote.getFluid(100)).buildAndRegister();
        RecipeMaps.FLUID_EXTRACTION_RECIPES.recipeBuilder().duration(16).EUt(4).input(OrePrefix.dust, Materials.Wood, 1).chancedOutput(MetaItems.PLANT_BALL.getStackForm(), 100).fluidOutputs(Materials.Creosote.getFluid(5)).buildAndRegister();
        RecipeMaps.FLUID_EXTRACTION_RECIPES.recipeBuilder().duration(32).EUt(4).input(OrePrefix.dust, Materials.HydratedCoal, 1).outputs(OreDictUnifier.get(OrePrefix.dust,Materials.Coal,1)).fluidOutputs(Materials.Water.getFluid(100)).buildAndRegister();
        RecipeMaps.FLUID_EXTRACTION_RECIPES.recipeBuilder().duration(128).EUt(4).input(OrePrefix.gem, Materials.Mercury, 1).fluidOutputs(Materials.Mercury.getFluid(1000)).buildAndRegister();
        RecipeMaps.FLUID_EXTRACTION_RECIPES.recipeBuilder().duration(64).EUt(64).input(OrePrefix.dust, Materials.Monazite, 1).fluidOutputs(Materials.Helium.getFluid(200)).buildAndRegister();
        RecipeMaps.FLUID_EXTRACTION_RECIPES.recipeBuilder().duration(32).EUt(4).inputs(new ItemStack(Items.SNOWBALL)).fluidOutputs(Materials.Water.getFluid(250)).buildAndRegister();
        RecipeMaps.FLUID_EXTRACTION_RECIPES.recipeBuilder().duration(128).EUt(4).inputs(new ItemStack(Blocks.SNOW)).fluidOutputs(Materials.Water.getFluid(1000)).buildAndRegister();
        RecipeMaps.AUTOCLAVE_RECIPES.recipeBuilder().duration(72000).EUt(480).input(OrePrefix.dust, Materials.NetherStar, 1).fluidInputs(Materials.UUMatter.getFluid(576)).chancedOutput(OreDictUnifier.get(OrePrefix.gem,Materials.NetherStar,1), 3333).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(1800).EUt(30).input(OrePrefix.dust, Materials.EnderPearl, 1).input(OrePrefix.circuit, MarkerMaterials.Tier.Basic, 4).fluidInputs(Materials.Osmium.getFluid(288)).outputs(MetaItems.FIELD_GENERATOR_LV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(1800).EUt(120).input(OrePrefix.dust, Materials.EnderEye, 1).input(OrePrefix.circuit, MarkerMaterials.Tier.Good, 4).fluidInputs(Materials.Osmium.getFluid(576)).outputs(MetaItems.FIELD_GENERATOR_MV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(1800).EUt(480).inputs(MetaItems.QUANTUMEYE.getStackForm()).input(OrePrefix.circuit, MarkerMaterials.Tier.Advanced, 4).fluidInputs(Materials.Osmium.getFluid(1152)).outputs(MetaItems.FIELD_GENERATOR_HV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(1800).EUt(1920).input(OrePrefix.dust, Materials.NetherStar, 1).input(OrePrefix.circuit, MarkerMaterials.Tier.Elite, 4).fluidInputs(Materials.Osmium.getFluid(2304)).outputs(MetaItems.FIELD_GENERATOR_EV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(1800).EUt(7680).inputs(MetaItems.QUANTUMSTAR.getStackForm()).input(OrePrefix.circuit, MarkerMaterials.Tier.Master, 4).fluidInputs(Materials.Osmium.getFluid(4608)).outputs(MetaItems.FIELD_GENERATOR_IV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(1600).EUt(16).input(OrePrefix.wireFine, Materials.Steel, 64).input(OrePrefix.foil, Materials.Zinc, 16).outputs(MetaItems.COMPONENT_FILTER.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(480).EUt(240).input(OrePrefix.dust, Materials.Graphite, 8).input(OrePrefix.foil, Materials.Silicon, 1).fluidInputs(Materials.Glue.getFluid(250)).outputs(OreDictUnifier.get(OrePrefix.dustSmall,Materials.Graphene,1)).buildAndRegister();

        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(1600).EUt(8).fluidInputs(Materials.Air.getFluid(10000)).fluidOutputs(Materials.Nitrogen.getFluid(3900), Materials.Oxygen.getFluid(1000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(950).EUt(30).fluidInputs(Materials.Water.getFluid(2000), Materials.NitrogenDioxide.getFluid(4000), Materials.Oxygen.getFluid(1000)).fluidOutputs(Materials.NitricAcid.getFluid(4000)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(16).input(OrePrefix.plate, Materials.Silicon, 1).input(OrePrefix.plate, Materials.Plastic, 1).outputs(MetaItems.EMPTY_BOARD_BASIC.getStackForm()).duration(32).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(256).input(OrePrefix.plate, Materials.Silicon, 2).input(OrePrefix.plate, Materials.Polytetrafluoroethylene, 1).outputs(MetaItems.EMPTY_BOARD_ELITE.getStackForm()).duration(32).buildAndRegister();

        RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder().duration(32).EUt(16).inputs(MetaItems.EMPTY_BOARD_BASIC.getStackForm(), MetaItems.CIRCUIT_PARTS_WIRING_BASIC.getStackForm(4)).outputs(MetaItems.CIRCUIT_BOARD_BASIC.getStackForm()).buildAndRegister();
        RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder().duration(32).EUt(64).inputs(MetaItems.EMPTY_BOARD_BASIC.getStackForm(), MetaItems.CIRCUIT_PARTS_WIRING_ADVANCED.getStackForm(4)).outputs(MetaItems.CIRCUIT_BOARD_ADVANCED.getStackForm()).buildAndRegister();
        RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder().duration(32).EUt(256).inputs(MetaItems.EMPTY_BOARD_ELITE.getStackForm(), MetaItems.CIRCUIT_PARTS_WIRING_ELITE.getStackForm(4)).outputs(MetaItems.CIRCUIT_BOARD_ELITE.getStackForm()).buildAndRegister();
        RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder().duration(32).EUt(64).input(OrePrefix.plate, Materials.Lapis, 1).input(OrePrefix.dust, Materials.Glowstone, 1).outputs(MetaItems.CIRCUIT_PARTS_ADVANCED.getStackForm(2)).buildAndRegister();
        RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder().duration(32).EUt(64).input(OrePrefix.plate, Materials.Lazurite, 1).input(OrePrefix.dust, Materials.Glowstone, 1).outputs(MetaItems.CIRCUIT_PARTS_ADVANCED.getStackForm(2)).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(800).EUt(1).inputs(new ItemStack(Blocks.REDSTONE_TORCH, 2, OreDictionary.WILDCARD_VALUE)).input(OrePrefix.dust, Materials.Redstone, 1).fluidInputs(Materials.Concrete.getFluid(144)).outputs(new ItemStack(Items.REPEATER)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(100).EUt(8).inputs(new ItemStack(Items.LEATHER, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Items.LEAD, 1, OreDictionary.WILDCARD_VALUE)).fluidInputs(Materials.Glue.getFluid(50)).outputs(new ItemStack(Items.NAME_TAG)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(100).EUt(8).inputs(new ItemStack(Items.COMPASS, 1, OreDictionary.WILDCARD_VALUE)).input(OrePrefix.plate, Materials.Paper, 8).outputs(new ItemStack(Items.MAP)).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(100).EUt(4).input(OrePrefix.dust, Materials.Tantalum, 1).input(OrePrefix.plate, Materials.Manganese, 1).fluidInputs(Materials.Plastic.getFluid(144)).outputs(MetaItems.BATTERY_RE_ULV_TANTALUM.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(512).EUt(256).inputs(MetaItems.CIRCUIT_ELITE.getStackForm(2), MetaItems.CIRCUIT_PARTS_CRYSTAL_CHIP_ELITE.getStackForm(18)).outputs(MetaItems.TOOL_DATAORB.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(512).EUt(1024).inputs(MetaItems.CIRCUIT_MASTER.getStackForm(2), MetaItems.CIRCUIT_PARTS_CRYSTAL_CHIP_MASTER.getStackForm(18)).outputs(MetaItems.ENERGY_LAPOTRONICORB.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(2048).EUt(4096).inputs(MetaItems.ENERGY_LAPOTRONICORB.getStackForm(8)).input(OrePrefix.plate, Materials.Europium, 4).outputs(MetaItems.ENERGY_LAPOTRONICORB2.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(32768).EUt(4096).inputs(MetaItems.ENERGY_LAPOTRONICORB2.getStackForm(8)).input(OrePrefix.plate, Materials.Darmstadtium, 16).outputs(MetaItems.ZPM2.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(400).EUt(1).inputs(new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE)).input(OrePrefix.stick, Materials.Wood, 1).fluidInputs(Materials.Creosote.getFluid(1000)).outputs(new ItemStack(Blocks.TORCH, 6)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(100).EUt(4).inputs(new ItemStack(Blocks.PISTON, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Items.SLIME_BALL, 1, OreDictionary.WILDCARD_VALUE)).outputs(new ItemStack(Blocks.STICKY_PISTON)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(100).EUt(4).inputs(new ItemStack(Blocks.PISTON, 1, OreDictionary.WILDCARD_VALUE)).fluidInputs(Materials.Glue.getFluid(100)).circuitMeta(1).outputs(new ItemStack(Blocks.STICKY_PISTON)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(32).EUt(8).inputs(new ItemStack(Items.LEATHER, 1, OreDictionary.WILDCARD_VALUE)).input(OrePrefix.plate, Materials.Paper, 3).fluidInputs(Materials.Glue.getFluid(20)).outputs(new ItemStack(Items.BOOK)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(16).inputs(new ItemStack(Items.IRON_DOOR)).input(OrePrefix.plate, Materials.Aluminium, 2).outputs(MetaItems.COVER_SHUTTER.getStackForm(4)).duration(800).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(16).inputs(new ItemStack(Items.IRON_DOOR)).input(OrePrefix.plate, Materials.Iron, 2).outputs(MetaItems.COVER_SHUTTER.getStackForm(2)).duration(800).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(16).inputs(new ItemStack(Items.IRON_DOOR)).input(OrePrefix.plate, Materials.WroughtIron, 2).outputs(MetaItems.COVER_SHUTTER.getStackForm(3)).duration(800).buildAndRegister();

        RecipeMaps.DISTILLERY_RECIPES.recipeBuilder().duration(16).EUt(24).fluidInputs(Materials.HeavyFuel.getFluid(10)).circuitMeta(1).fluidOutputs(Materials.Toluene.getFluid(4)).buildAndRegister();
        RecipeMaps.DISTILLERY_RECIPES.recipeBuilder().duration(16).EUt(24).fluidInputs(Materials.Toluene.getFluid(30)).circuitMeta(1).fluidOutputs(Materials.LightFuel.getFluid(30)).buildAndRegister();

        RecipeMaps.FLUID_SOLIDFICATION_RECIPES.recipeBuilder().duration(100).EUt(16).notConsumable(MetaItems.SHAPE_MOLD_BALL.getStackForm()).fluidInputs(Materials.Toluene.getFluid(100)).outputs(MetaItems.GELLED_TOLUENE.getStackForm()).buildAndRegister();

        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(200).EUt(24).inputs(MetaItems.GELLED_TOLUENE.getStackForm(4)).fluidInputs(Materials.SulfuricAcid.getFluid(250)).outputs(new ItemStack(Blocks.TNT)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(140).EUt(192).inputs(new ItemStack(Items.SUGAR)).input(OrePrefix.dustTiny, Materials.Plastic, 1).fluidInputs(Materials.Toluene.getFluid(133)).outputs(MetaItems.GELLED_TOLUENE.getStackForm(2)).buildAndRegister();

        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(500).EUt(2).fluidInputs(Materials.NitricAcid.getFluid(1000), Materials.SulfuricAcid.getFluid(1000)).fluidOutputs(Materials.NitrationMixture.getFluid(2000)).buildAndRegister();

        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(160).fluidInputs(Materials.NaturalGas.getFluid(16000), Materials.Hydrogen.getFluid(1000)).fluidOutputs(Materials.Gas.getFluid(16000), Materials.HydrogenSulfide.getFluid(2000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(160).fluidInputs(Materials.SulfuricGas.getFluid(16000), Materials.Hydrogen.getFluid(1000)).fluidOutputs(Materials.Gas.getFluid(16000), Materials.HydrogenSulfide.getFluid(2000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(160).fluidInputs(Materials.SulfuricNaphtha.getFluid(7000), Materials.Hydrogen.getFluid(1000)).fluidOutputs(Materials.Naphtha.getFluid(7000), Materials.HydrogenSulfide.getFluid(2000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(160).fluidInputs(Materials.SulfuricLightFuel.getFluid(6000), Materials.Hydrogen.getFluid(1000)).fluidOutputs(Materials.LightFuel.getFluid(6000), Materials.HydrogenSulfide.getFluid(2000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(160).fluidInputs(Materials.SulfuricHeavyFuel.getFluid(4000), Materials.Hydrogen.getFluid(1000)).fluidOutputs(Materials.HeavyFuel.getFluid(4000), Materials.HydrogenSulfide.getFluid(2000)).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(200).EUt(5).fluidInputs(Materials.Gas.getFluid(8000)).fluidOutputs(Materials.Methane.getFluid(4000), Materials.LPG.getFluid(4000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(320).fluidInputs(Materials.HydrogenSulfide.getFluid(2000), ModHandler.getWater(2000)).fluidOutputs(Materials.SulfuricAcid.getFluid(3000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(320).fluidInputs(Materials.Water.getFluid(2000), Materials.HydrogenSulfide.getFluid(2000), ModHandler.getWater(2000)).fluidOutputs(Materials.SulfuricAcid.getFluid(3000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(640).fluidInputs(Materials.Naphtha.getFluid(288), Materials.Air.getFluid(2000)).fluidOutputs(Materials.Plastic.getFluid(144)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(640).input(OrePrefix.dustTiny, Materials.Titanium, 1).fluidInputs(Materials.Naphtha.getFluid(1296), Materials.Oxygen.getFluid(16000)).fluidOutputs(Materials.Plastic.getFluid(1296)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(640).input(OrePrefix.dust, Materials.Saltpeter, 1).fluidInputs(Materials.Naphtha.getFluid(576)).outputs(OreDictUnifier.get(OrePrefix.dustTiny,Materials.Potassium,1)).fluidOutputs(Materials.Polycaprolactam.getFluid(1296)).buildAndRegister();
        RecipeMaps.WIREMILL_RECIPES.recipeBuilder().duration(80).EUt(48).input(OrePrefix.ingot, Materials.Polycaprolactam, 1).outputs(new ItemStack(Items.STRING, 32)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(480).EUt(30).input(OrePrefix.dust, Materials.Carbon, 1).fluidInputs(Materials.LPG.getFluid(432), Materials.Chlorine.getFluid(1000)).fluidOutputs(Materials.Epichlorhydrin.getFluid(432)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(240).EUt(256).fluidInputs(Materials.Epichlorhydrin.getFluid(432), Materials.Naphtha.getFluid(3000), Materials.Fluorine.getFluid(1000)).fluidOutputs(Materials.Polytetrafluoroethylene.getFluid(432)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(240).EUt(96).input(OrePrefix.dust, Materials.Silicon, 1).fluidInputs(Materials.Epichlorhydrin.getFluid(144)).fluidOutputs(Materials.Silicone.getFluid(144)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(240).EUt(30).fluidInputs(Materials.Epichlorhydrin.getFluid(144), Materials.Naphtha.getFluid(3000), Materials.NitrogenDioxide.getFluid(1000)).fluidOutputs(Materials.Epoxid.getFluid(288)).buildAndRegister();

        //FIX @Exidex
        //RecipeMaps.CRACKING_RECIPES.recipeBuilder().duration(16).EUt(320).fluidInputs(Materials.LightFuel.getFluid(128)).fluidOutputs(Materials.CrackedLightFuel.getFluid(192)).buildAndRegister();
        //RecipeMaps.CRACKING_RECIPES.recipeBuilder().duration(16).EUt(320).fluidInputs(Materials.HeavyFuel.getFluid(128)).fluidOutputs(Materials.CrackedHeavyFuel.getFluid(192)).buildAndRegister();

        RecipeMaps.DISTILLERY_RECIPES.recipeBuilder().duration(16).EUt(24).fluidInputs(Materials.Creosote.getFluid(3)).circuitMeta(4).fluidOutputs(Materials.Lubricant.getFluid(1)).buildAndRegister();
        RecipeMaps.DISTILLERY_RECIPES.recipeBuilder().duration(16).EUt(24).fluidInputs(Materials.SeedOil.getFluid(4)).circuitMeta(4).fluidOutputs(Materials.Lubricant.getFluid(1)).buildAndRegister();
        RecipeMaps.DISTILLERY_RECIPES.recipeBuilder().duration(16).EUt(24).fluidInputs(Materials.Biomass.getFluid(40)).circuitMeta(1).fluidOutputs(Materials.Ethanol.getFluid(12)).buildAndRegister();
        RecipeMaps.DISTILLERY_RECIPES.recipeBuilder().duration(16).EUt(24).fluidInputs(Materials.Biomass.getFluid(40)).circuitMeta(5).fluidOutputs(Materials.Water.getFluid(12)).buildAndRegister();
        RecipeMaps.DISTILLERY_RECIPES.recipeBuilder().duration(16).EUt(8).fluidInputs(Materials.Water.getFluid(5)).circuitMeta(5).fluidOutputs(ModHandler.getDistilledWater(4)).buildAndRegister();
        RecipeMaps.DISTILLERY_RECIPES.recipeBuilder().duration(16).EUt(24).fluidInputs(Materials.OilLight.getFluid(300)).circuitMeta(4).fluidOutputs(Materials.Oil.getFluid(100)).buildAndRegister();
        RecipeMaps.DISTILLERY_RECIPES.recipeBuilder().duration(16).EUt(24).fluidInputs(Materials.OilMedium.getFluid(200)).circuitMeta(4).fluidOutputs(Materials.Oil.getFluid(100)).buildAndRegister();
        RecipeMaps.DISTILLERY_RECIPES.recipeBuilder().duration(16).EUt(24).fluidInputs(Materials.OilHeavy.getFluid(100)).circuitMeta(4).fluidOutputs(Materials.Oil.getFluid(100)).buildAndRegister();

        RecipeMaps.FLUID_HEATER_RECIPES.recipeBuilder().duration(30).EUt(32).fluidInputs(Materials.Water.getFluid(6)).circuitMeta(1).fluidOutputs(Materials.Steam.getFluid(960)).buildAndRegister();
        RecipeMaps.FLUID_HEATER_RECIPES.recipeBuilder().duration(30).EUt(32).fluidInputs(ModHandler.getDistilledWater(6)).circuitMeta(1).fluidOutputs(Materials.Steam.getFluid(960)).buildAndRegister();

        RecipeMaps.EXTRACTOR_RECIPES.recipeBuilder().inputs(new ItemStack(Blocks.BOOKSHELF, 1, OreDictionary.WILDCARD_VALUE)).outputs(new ItemStack(Items.BOOK, 3)).buildAndRegister();
        RecipeMaps.EXTRACTOR_RECIPES.recipeBuilder().inputs(new ItemStack(Items.SLIME_BALL)).outputs(OreDictUnifier.get(OrePrefix.dust,Materials.RawRubber,2)).buildAndRegister();

        RecipeMaps.COMPRESSOR_RECIPES.recipeBuilder().inputs(new ItemStack(Blocks.ICE, 2, OreDictionary.WILDCARD_VALUE)).outputs(new ItemStack(Blocks.PACKED_ICE)).buildAndRegister();
        RecipeMaps.COMPRESSOR_RECIPES.recipeBuilder().input(OrePrefix.dust, Materials.Ice, 1).outputs(new ItemStack(Blocks.ICE)).buildAndRegister();
        RecipeMaps.COMPRESSOR_RECIPES.recipeBuilder().inputs(new ItemStack(Items.QUARTZ, 4)).outputs(new ItemStack(Blocks.QUARTZ_BLOCK)).buildAndRegister();
        RecipeMaps.COMPRESSOR_RECIPES.recipeBuilder().inputs(new ItemStack(Items.WHEAT, 9)).outputs(new ItemStack(Blocks.HAY_BLOCK)).buildAndRegister();
        RecipeMaps.COMPRESSOR_RECIPES.recipeBuilder().input(OrePrefix.dust, Materials.Glowstone, 4).outputs(new ItemStack(Blocks.GLOWSTONE)).buildAndRegister();

        RecipeMaps.CUTTER_RECIPES.recipeBuilder().duration(500).EUt(48).input(OrePrefix.block, Materials.Graphite, 1).outputs(OreDictUnifier.get(OrePrefix.ingot,Materials.Graphite,9)).buildAndRegister();
        RecipeMaps.MACERATOR_RECIPES.recipeBuilder().inputs(new ItemStack(Items.BLAZE_ROD)).outputs(new ItemStack(Items.BLAZE_POWDER, 3)).buildAndRegister();
        RecipeMaps.MACERATOR_RECIPES.recipeBuilder().inputs(new ItemStack(Items.FLINT, 1, OreDictionary.WILDCARD_VALUE)).outputs(OreDictUnifier.get(OrePrefix.dustTiny,Materials.Flint,4)).buildAndRegister();
        RecipeMaps.MACERATOR_RECIPES.recipeBuilder().inputs(new ItemStack(Items.ITEM_FRAME, 1, OreDictionary.WILDCARD_VALUE)).outputs(new ItemStack(Items.LEATHER)).buildAndRegister();
        RecipeMaps.MACERATOR_RECIPES.recipeBuilder().inputs(new ItemStack(Items.BOW)).outputs(new ItemStack(Items.STRING, 3)).buildAndRegister();

        RecipeMaps.FORGE_HAMMER_RECIPES.recipeBuilder().duration(16).EUt(10).inputs(new ItemStack(Blocks.STONEBRICK)).outputs(new ItemStack(Blocks.STONEBRICK, 1, 2)).buildAndRegister();
        RecipeMaps.FORGE_HAMMER_RECIPES.recipeBuilder().duration(16).EUt(10).inputs(new ItemStack(Blocks.STONE)).outputs(new ItemStack(Blocks.COBBLESTONE)).buildAndRegister();
        RecipeMaps.FORGE_HAMMER_RECIPES.recipeBuilder().duration(16).EUt(10).inputs(new ItemStack(Blocks.COBBLESTONE)).outputs(new ItemStack(Blocks.GRAVEL)).buildAndRegister();
        RecipeMaps.FORGE_HAMMER_RECIPES.recipeBuilder().duration(16).EUt(10).inputs(new ItemStack(Blocks.SANDSTONE, 1, OreDictionary.WILDCARD_VALUE)).outputs(new ItemStack(Blocks.SAND)).buildAndRegister();
        RecipeMaps.FORGE_HAMMER_RECIPES.recipeBuilder().duration(16).EUt(10).inputs(new ItemStack(Blocks.ICE)).outputs(OreDictUnifier.get(OrePrefix.dust,Materials.Ice,1)).buildAndRegister();
        RecipeMaps.FORGE_HAMMER_RECIPES.recipeBuilder().duration(16).EUt(10).inputs(new ItemStack(Blocks.PACKED_ICE)).outputs(OreDictUnifier.get(OrePrefix.dust,Materials.Ice,2)).buildAndRegister();
        RecipeMaps.FORGE_HAMMER_RECIPES.recipeBuilder().duration(16).EUt(10).inputs(new ItemStack(Blocks.HARDENED_CLAY)).outputs(OreDictUnifier.get(OrePrefix.dust,Materials.Clay,1)).buildAndRegister();
        RecipeMaps.FORGE_HAMMER_RECIPES.recipeBuilder().duration(16).EUt(10).inputs(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, OreDictionary.WILDCARD_VALUE)).outputs(OreDictUnifier.get(OrePrefix.dust,Materials.Clay,1)).buildAndRegister();
        RecipeMaps.FORGE_HAMMER_RECIPES.recipeBuilder().duration(16).EUt(10).inputs(new ItemStack(Blocks.BRICK_BLOCK)).outputs(new ItemStack(Items.BRICK, 3)).buildAndRegister();
        RecipeMaps.FORGE_HAMMER_RECIPES.recipeBuilder().duration(16).EUt(10).inputs(new ItemStack(Blocks.NETHER_BRICK)).outputs(new ItemStack(Items.NETHERBRICK, 3)).buildAndRegister();
        RecipeMaps.FORGE_HAMMER_RECIPES.recipeBuilder().duration(16).EUt(10).inputs(new ItemStack(Blocks.STAINED_GLASS, 1, OreDictionary.WILDCARD_VALUE)).outputs(OreDictUnifier.get(OrePrefix.dust,Materials.Glass,1)).buildAndRegister();
        RecipeMaps.FORGE_HAMMER_RECIPES.recipeBuilder().duration(16).EUt(10).inputs(new ItemStack(Blocks.GLASS, 1, OreDictionary.WILDCARD_VALUE)).outputs(OreDictUnifier.get(OrePrefix.dust,Materials.Glass,1)).buildAndRegister();
        RecipeMaps.FORGE_HAMMER_RECIPES.recipeBuilder().duration(16).EUt(10).inputs(new ItemStack(Blocks.STAINED_GLASS_PANE, 1, OreDictionary.WILDCARD_VALUE)).outputs(OreDictUnifier.get(OrePrefix.dustTiny,Materials.Glass,3)).buildAndRegister();
        RecipeMaps.FORGE_HAMMER_RECIPES.recipeBuilder().duration(16).EUt(10).inputs(new ItemStack(Blocks.GLASS_PANE, 1, OreDictionary.WILDCARD_VALUE)).outputs(OreDictUnifier.get(OrePrefix.dustTiny,Materials.Glass,3)).buildAndRegister();

        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration((int) Math.max(Materials.TungstenSteel.getMass() / 80,1) * Materials.TungstenSteel.blastFurnaceTemperature).EUt(480).input(OrePrefix.ingot, Materials.Tungsten, 1).input(OrePrefix.ingot, Materials.Steel, 1).outputs(OreDictUnifier.get(OrePrefix.ingotHot,Materials.TungstenSteel,2), OreDictUnifier.get(OrePrefix.dustSmall,Materials.DarkAsh,1)).blastFurnaceTemp(Materials.TungstenSteel.blastFurnaceTemperature).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration((int) Math.max(Materials.TungstenCarbide.getMass() / 40,1) * Materials.TungstenCarbide.blastFurnaceTemperature).EUt(480).input(OrePrefix.ingot, Materials.Tungsten, 1).input(OrePrefix.dust, Materials.Carbon, 1).outputs(OreDictUnifier.get(OrePrefix.ingotHot,Materials.TungstenCarbide,1), OreDictUnifier.get(OrePrefix.dustSmall,Materials.Ash,2)).blastFurnaceTemp(Materials.TungstenCarbide.blastFurnaceTemperature).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration((int) Math.max(Materials.VanadiumGallium.getMass() / 40,1) * Materials.VanadiumGallium.blastFurnaceTemperature).EUt(480).input(OrePrefix.ingot, Materials.Vanadium, 3).input(OrePrefix.ingot, Materials.Gallium, 1).outputs(OreDictUnifier.get(OrePrefix.ingotHot,Materials.VanadiumGallium,4), OreDictUnifier.get(OrePrefix.dustSmall,Materials.DarkAsh,2)).blastFurnaceTemp(Materials.VanadiumGallium.blastFurnaceTemperature).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration((int) Math.max(Materials.NiobiumTitanium.getMass() / 80,1) * Materials.NiobiumTitanium.blastFurnaceTemperature).EUt(480).input(OrePrefix.ingot, Materials.Niobium, 1).input(OrePrefix.ingot, Materials.Titanium, 1).outputs(OreDictUnifier.get(OrePrefix.ingotHot,Materials.NiobiumTitanium,2), OreDictUnifier.get(OrePrefix.dustSmall,Materials.DarkAsh,1)).blastFurnaceTemp(Materials.NiobiumTitanium.blastFurnaceTemperature).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration((int) Math.max(Materials.Nichrome.getMass() / 32,1) * Materials.Nichrome.blastFurnaceTemperature).EUt(480).input(OrePrefix.ingot, Materials.Nickel, 4).input(OrePrefix.ingot, Materials.Chrome, 1).outputs(OreDictUnifier.get(OrePrefix.ingotHot,Materials.Nichrome,5), OreDictUnifier.get(OrePrefix.dustSmall,Materials.DarkAsh,2)).blastFurnaceTemp(Materials.Nichrome.blastFurnaceTemperature).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(400).EUt(100).input(OrePrefix.dust, Materials.Ruby, 1).outputs(OreDictUnifier.get(OrePrefix.nugget,Materials.Aluminium,3), OreDictUnifier.get(OrePrefix.dustTiny,Materials.DarkAsh,1)).blastFurnaceTemp(1200).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(320).EUt(100).input(OrePrefix.gem, Materials.Ruby, 1).outputs(OreDictUnifier.get(OrePrefix.nugget,Materials.Aluminium,3), OreDictUnifier.get(OrePrefix.dustTiny,Materials.DarkAsh,1)).blastFurnaceTemp(1200).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(400).EUt(100).input(OrePrefix.dust, Materials.GreenSapphire, 1).outputs(OreDictUnifier.get(OrePrefix.nugget,Materials.Aluminium,3), OreDictUnifier.get(OrePrefix.dustTiny,Materials.DarkAsh,1)).blastFurnaceTemp(1200).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(320).EUt(100).input(OrePrefix.gem, Materials.GreenSapphire, 1).outputs(OreDictUnifier.get(OrePrefix.nugget,Materials.Aluminium,3), OreDictUnifier.get(OrePrefix.dustTiny,Materials.DarkAsh,1)).blastFurnaceTemp(1200).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(400).EUt(100).input(OrePrefix.dust, Materials.Sapphire, 1).outputs(OreDictUnifier.get(OrePrefix.nugget,Materials.Aluminium,3)).blastFurnaceTemp(1200).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(320).EUt(100).input(OrePrefix.gem, Materials.Sapphire, 1).outputs(OreDictUnifier.get(OrePrefix.nugget,Materials.Aluminium,3)).blastFurnaceTemp(1200).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(800).EUt(500).input(OrePrefix.dust, Materials.Ilmenite, 1).input(OrePrefix.dust, Materials.Carbon, 1).outputs(OreDictUnifier.get(OrePrefix.nugget,Materials.WroughtIron,4), OreDictUnifier.get(OrePrefix.dustTiny,Materials.Rutile,4)).blastFurnaceTemp(1700).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(800).EUt(500).input(OrePrefix.gem, Materials.Ilmenite, 1).input(OrePrefix.dust, Materials.Carbon, 1).outputs(OreDictUnifier.get(OrePrefix.nugget,Materials.WroughtIron,4), OreDictUnifier.get(OrePrefix.dustTiny,Materials.Rutile,4)).blastFurnaceTemp(1700).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(800).EUt(480).input(OrePrefix.dust, Materials.Magnesium, 2).fluidInputs(Materials.Titaniumtetrachloride.getFluid(1000)).outputs(OreDictUnifier.get(OrePrefix.ingotHot,Materials.Titanium,1), OreDictUnifier.get(OrePrefix.dust,Materials.Magnesiumchloride,2)).blastFurnaceTemp(Materials.Titanium.blastFurnaceTemperature + 200).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(400).EUt(500).input(OrePrefix.dust, Materials.Galena, 1).fluidInputs(Materials.Oxygen.getFluid(2000)).outputs(OreDictUnifier.get(OrePrefix.nugget,Materials.Silver,4), OreDictUnifier.get(OrePrefix.nugget,Materials.Lead,4)).blastFurnaceTemp(1500).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(320).EUt(500).input(OrePrefix.gem, Materials.Galena, 1).fluidInputs(Materials.Oxygen.getFluid(2000)).outputs(OreDictUnifier.get(OrePrefix.nugget,Materials.Silver,5), OreDictUnifier.get(OrePrefix.nugget,Materials.Lead,5)).blastFurnaceTemp(1500).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(400).EUt(500).input(OrePrefix.dust, Materials.Magnetite, 1).fluidInputs(Materials.Oxygen.getFluid(2000)).outputs(OreDictUnifier.get(OrePrefix.nugget,Materials.WroughtIron,4), OreDictUnifier.get(OrePrefix.dustSmall,Materials.DarkAsh,1)).blastFurnaceTemp(1000).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(320).EUt(500).input(OrePrefix.gem, Materials.Magnetite, 1).fluidInputs(Materials.Oxygen.getFluid(2000)).outputs(OreDictUnifier.get(OrePrefix.nugget,Materials.WroughtIron,5), OreDictUnifier.get(OrePrefix.dustSmall,Materials.Ash,1)).blastFurnaceTemp(1000).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(120).input(OrePrefix.ingot, Materials.Iron, 1).fluidInputs(Materials.Oxygen.getFluid(1000)).outputs(OreDictUnifier.get(OrePrefix.ingot,Materials.Steel,1), OreDictUnifier.get(OrePrefix.dustSmall,Materials.DarkAsh,1)).blastFurnaceTemp(1000).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(100).EUt(120).input(OrePrefix.ingot, Materials.PigIron, 1).fluidInputs(Materials.Oxygen.getFluid(1000)).outputs(OreDictUnifier.get(OrePrefix.ingot,Materials.Steel,1), OreDictUnifier.get(OrePrefix.dustSmall,Materials.DarkAsh,1)).blastFurnaceTemp(1000).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(100).EUt(120).input(OrePrefix.ingot, Materials.WroughtIron, 1).fluidInputs(Materials.Oxygen.getFluid(1000)).outputs(OreDictUnifier.get(OrePrefix.ingot,Materials.Steel,1), OreDictUnifier.get(OrePrefix.dustSmall,Materials.DarkAsh,1)).blastFurnaceTemp(1000).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(120).input(OrePrefix.dust, Materials.Copper, 1).fluidInputs(Materials.Oxygen.getFluid(1000)).outputs(OreDictUnifier.get(OrePrefix.ingot,Materials.AnnealedCopper,1)).blastFurnaceTemp(1200).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(120).input(OrePrefix.ingot, Materials.Copper, 1).fluidInputs(Materials.Oxygen.getFluid(1000)).outputs(OreDictUnifier.get(OrePrefix.ingot,Materials.AnnealedCopper,1)).blastFurnaceTemp(1200).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(1920).input(OrePrefix.ingot, Materials.Iridium, 3).input(OrePrefix.ingot, Materials.Osmium, 1).fluidInputs(Materials.Helium.getFluid(1000)).outputs(OreDictUnifier.get(OrePrefix.ingotHot,Materials.Osmiridium,4)).blastFurnaceTemp(2900).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(30720).input(OrePrefix.ingot, Materials.Naquadah, 1).input(OrePrefix.ingot, Materials.Osmiridium, 1).fluidInputs(Materials.Argon.getFluid(1000)).outputs(OreDictUnifier.get(OrePrefix.ingotHot,Materials.NaquadahAlloy,2)).blastFurnaceTemp(Materials.NaquadahAlloy.blastFurnaceTemperature).buildAndRegister();

        RecipeMaps.FUSION_RECIPES.recipeBuilder().duration(32).EUt(32768).fluidInputs(Materials.Lithium.getFluid(16), Materials.Tungsten.getFluid(16)).fluidOutputs(Materials.Iridium.getFluid(16)).EUToStart(300000000).buildAndRegister();
        RecipeMaps.FLUID_EXTRACTION_RECIPES.recipeBuilder().duration(600).EUt(28).input(OrePrefix.dust, Materials.Quartzite, 1).fluidOutputs(Materials.Glass.getFluid(72)).buildAndRegister();

        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(16).EUt(96).fluidInputs(Materials.Creosote.getFluid(24)).fluidOutputs(Materials.Lubricant.getFluid(12)).buildAndRegister();
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(16).EUt(96).fluidInputs(Materials.SeedOil.getFluid(32)).fluidOutputs(Materials.Lubricant.getFluid(12)).buildAndRegister();
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(25).EUt(64).fluidInputs(Materials.Biomass.getFluid(150)).outputs(OreDictUnifier.get(OrePrefix.dustSmall,Materials.Wood,1)).fluidOutputs(Materials.Ethanol.getFluid(60), Materials.Water.getFluid(60)).buildAndRegister();
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(16).EUt(64).fluidInputs(Materials.Water.getFluid(288)).fluidOutputs(ModHandler.getDistilledWater(260)).buildAndRegister();

        RecipeMaps.ELECTROLYZER_RECIPES.recipeBuilder().duration(1500).EUt(30).fluidInputs(Materials.Water.getFluid(3000)).fluidOutputs(Materials.Hydrogen.getFluid(2000), Materials.Oxygen.getFluid(1000)).buildAndRegister();
        RecipeMaps.ELECTROLYZER_RECIPES.recipeBuilder().duration(1500).EUt(30).fluidInputs(ModHandler.getDistilledWater(3000)).fluidOutputs(Materials.Hydrogen.getFluid(2000), Materials.Oxygen.getFluid(1000)).buildAndRegister();
        RecipeMaps.ELECTROLYZER_RECIPES.recipeBuilder().inputs(new ItemStack(Items.DYE, 3)).outputs(OreDictUnifier.get(OrePrefix.dust,Materials.Calcium,1)).duration(96).EUt(26).buildAndRegister();
        RecipeMaps.ELECTROLYZER_RECIPES.recipeBuilder().inputs(new ItemStack(Blocks.SAND, 8)).outputs(OreDictUnifier.get(OrePrefix.dust,Materials.SiliconDioxide,1)).duration(500).EUt(25).buildAndRegister();
        RecipeMaps.ELECTROLYZER_RECIPES.recipeBuilder().duration(120).EUt(1920).input(OrePrefix.dust, Materials.Tungstate, 7).fluidInputs(Materials.Hydrogen.getFluid(7000)).outputs(OreDictUnifier.get(OrePrefix.dust,Materials.Tungsten,1), OreDictUnifier.get(OrePrefix.dust,Materials.Lithium,2)).fluidOutputs(Materials.Oxygen.getFluid(4000)).buildAndRegister();
        RecipeMaps.ELECTROLYZER_RECIPES.recipeBuilder().duration(120).EUt(1920).input(OrePrefix.dust, Materials.Scheelite, 7).fluidInputs(Materials.Hydrogen.getFluid(7000)).outputs(OreDictUnifier.get(OrePrefix.dust,Materials.Tungsten,1), OreDictUnifier.get(OrePrefix.dust,Materials.Calcium,2)).fluidOutputs(Materials.Oxygen.getFluid(4000)).buildAndRegister();
        RecipeMaps.ELECTROLYZER_RECIPES.recipeBuilder().input(OrePrefix.dust, Materials.Graphite, 1).outputs(OreDictUnifier.get(OrePrefix.dust,Materials.Carbon,4)).duration(100).EUt(26).buildAndRegister();

        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(500).input(OrePrefix.dust, Materials.NetherQuartz, 3).input(OrePrefix.dust, Materials.Sodium, 1).fluidInputs(Materials.Water.getFluid(1000)).outputs(OreDictUnifier.get(OrePrefix.gem,Materials.NetherQuartz,3)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(500).input(OrePrefix.dust, Materials.CertusQuartz, 3).input(OrePrefix.dust, Materials.Sodium, 1).fluidInputs(Materials.Water.getFluid(1000)).outputs(OreDictUnifier.get(OrePrefix.gem,Materials.CertusQuartz,3)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(500).input(OrePrefix.dust, Materials.Quartzite, 3).input(OrePrefix.dust, Materials.Sodium, 1).fluidInputs(Materials.Water.getFluid(1000)).outputs(OreDictUnifier.get(OrePrefix.gem,Materials.Quartzite,3)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(500).input(OrePrefix.dust, Materials.NetherQuartz, 3).input(OrePrefix.dust, Materials.Sodium, 1).fluidInputs(ModHandler.getDistilledWater(1000)).outputs(OreDictUnifier.get(OrePrefix.gem,Materials.NetherQuartz,3)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(500).input(OrePrefix.dust, Materials.CertusQuartz, 3).input(OrePrefix.dust, Materials.Sodium, 1).fluidInputs(ModHandler.getDistilledWater(1000)).outputs(OreDictUnifier.get(OrePrefix.gem,Materials.CertusQuartz,3)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(500).input(OrePrefix.dust, Materials.Quartzite, 3).input(OrePrefix.dust, Materials.Sodium, 1).fluidInputs(ModHandler.getDistilledWater(1000)).outputs(OreDictUnifier.get(OrePrefix.gem,Materials.Quartzite,3)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(1000).input(OrePrefix.dust, Materials.Uraninite, 1).input(OrePrefix.dust, Materials.Aluminium, 1).outputs(OreDictUnifier.get(OrePrefix.dust,Materials.Uranium,1)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(1000).input(OrePrefix.dust, Materials.Uraninite, 1).input(OrePrefix.dust, Materials.Magnesium, 1).outputs(OreDictUnifier.get(OrePrefix.dust,Materials.Uranium,1)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(500).input(OrePrefix.dust, Materials.Calcium, 1).input(OrePrefix.dust, Materials.Carbon, 1).fluidInputs(Materials.Oxygen.getFluid(3000)).outputs(OreDictUnifier.get(OrePrefix.dust,Materials.Calcite,5)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(3500).input(OrePrefix.dust, Materials.Carbon, 1).fluidInputs(Materials.Hydrogen.getFluid(4000)).fluidOutputs(Materials.Methane.getFluid(5000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(1150).input(OrePrefix.dust, Materials.Sulfur, 1).fluidInputs(Materials.Water.getFluid(2000)).fluidOutputs(Materials.SulfuricAcid.getFluid(3000)).buildAndRegister();

        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(8000).input(OrePrefix.dust, Materials.Sulfur, 1).input(OrePrefix.dust, Materials.Sodium, 1).fluidInputs(Materials.Oxygen.getFluid(4000)).fluidOutputs(Materials.SodiumPersulfate.getFluid(6000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(2700).input(OrePrefix.dust, Materials.Carbon, 1).fluidInputs(Materials.Water.getFluid(2000), Materials.Nitrogen.getFluid(1000)).fluidOutputs(Materials.Glyceryl.getFluid(4000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(250).fluidInputs(Materials.Glyceryl.getFluid(250), Materials.Fuel.getFluid(1000)).fluidOutputs(Materials.NitroFuel.getFluid(1000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(1000).fluidInputs(Materials.Fuel.getFluid(4000), Materials.Glyceryl.getFluid(1000)).fluidOutputs(Materials.NitroFuel.getFluid(4000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(250).fluidInputs(Materials.Glyceryl.getFluid(250), Materials.LightFuel.getFluid(1000)).fluidOutputs(Materials.NitroFuel.getFluid(1250)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(1000).fluidInputs(Materials.LightFuel.getFluid(4000), Materials.Glyceryl.getFluid(1000)).fluidOutputs(Materials.NitroFuel.getFluid(5000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(1250).fluidInputs(Materials.Oxygen.getFluid(2000), Materials.Nitrogen.getFluid(1000)).fluidOutputs(Materials.NitrogenDioxide.getFluid(3000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(1250).fluidInputs(Materials.Nitrogen.getFluid(1000), Materials.Oxygen.getFluid(2000)).fluidOutputs(Materials.NitrogenDioxide.getFluid(3000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(10).fluidInputs(Materials.Hydrogen.getFluid(2000), Materials.Oxygen.getFluid(1000)).fluidOutputs(ModHandler.getDistilledWater(3000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(5).fluidInputs(Materials.Oxygen.getFluid(500), Materials.Hydrogen.getFluid(1000)).fluidOutputs(ModHandler.getDistilledWater(1500)).buildAndRegister();

        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(500).EUt(480).input(OrePrefix.dust, Materials.Rutile, 1).input(OrePrefix.dust, Materials.Carbon, 3).fluidInputs(Materials.Chlorine.getFluid(2000)).outputs(OreDictUnifier.get(OrePrefix.dust,Materials.Ash,1)).fluidOutputs(Materials.Titaniumtetrachloride.getFluid(1000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(300).EUt(240).input(OrePrefix.dust, Materials.Sodium, 1).input(OrePrefix.dust, Materials.Magnesiumchloride, 2).outputs(OreDictUnifier.get(OrePrefix.dustSmall,Materials.Magnesium,6)).fluidOutputs(Materials.Chlorine.getFluid(1500)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(600).EUt(16).input(OrePrefix.dust, Materials.RawRubber, 9).input(OrePrefix.dust, Materials.Sulfur, 1).fluidOutputs(Materials.Rubber.getFluid(1296)).buildAndRegister();

        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(50).inputs(new ItemStack(Items.MELON, 1, OreDictionary.WILDCARD_VALUE)).input(OrePrefix.nugget, Materials.Gold, 8).outputs(new ItemStack(Items.SPECKLED_MELON)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(50).inputs(new ItemStack(Items.CARROT, 1, OreDictionary.WILDCARD_VALUE)).input(OrePrefix.nugget, Materials.Gold, 8).outputs(new ItemStack(Items.GOLDEN_CARROT)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(50).inputs(new ItemStack(Items.APPLE, 1, OreDictionary.WILDCARD_VALUE)).input(OrePrefix.ingot, Materials.Gold, 8).outputs(new ItemStack(Items.GOLDEN_APPLE)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(50).inputs(new ItemStack(Items.APPLE, 1, OreDictionary.WILDCARD_VALUE)).input(OrePrefix.block, Materials.Gold, 8).outputs(new ItemStack(Items.GOLDEN_APPLE, 1, 1)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(50).input(OrePrefix.dust, Materials.Blaze, 1).input(OrePrefix.gem, Materials.EnderPearl, 1).outputs(OreDictUnifier.get(OrePrefix.gem,Materials.EnderEye,1)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(50).inputs(new ItemStack(Items.SLIME_BALL, 1, OreDictionary.WILDCARD_VALUE)).input(OrePrefix.dust, Materials.Blaze, 1).outputs(new ItemStack(Items.MAGMA_CREAM)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(12000).EUt(8).input(OrePrefix.ingot, Materials.Plutonium, 6).outputs(OreDictUnifier.get(OrePrefix.dust,Materials.Plutonium,6)).fluidOutputs(Materials.Radon.getFluid(100)).buildAndRegister();

        RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(480).EUt(384).input(OrePrefix.gem, Materials.EnderEye, 1).fluidInputs(Materials.Radon.getFluid(250)).outputs(MetaItems.QUANTUMEYE.getStackForm()).buildAndRegister();
        RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(1920).EUt(384).input(OrePrefix.gem, Materials.NetherStar, 1).fluidInputs(Materials.Radon.getFluid(1250)).outputs(MetaItems.QUANTUMSTAR.getStackForm()).buildAndRegister();

        RecipeMaps.AUTOCLAVE_RECIPES.recipeBuilder().duration(480).EUt(7680).input(OrePrefix.gem, Materials.NetherStar, 1).fluidInputs(Materials.Darmstadtium.getFluid(288)).outputs(MetaItems.GRAVISTAR.getStackForm()).buildAndRegister();

        RecipeMaps.BENDER_RECIPES.recipeBuilder().duration(800).EUt(4).input(OrePrefix.plate, Materials.Iron, 12).circuitMeta(1).outputs(new ItemStack(Items.BUCKET, 4)).buildAndRegister();
        RecipeMaps.BENDER_RECIPES.recipeBuilder().duration(800).EUt(4).input(OrePrefix.plate, Materials.WroughtIron, 12).circuitMeta(1).outputs(new ItemStack(Items.BUCKET, 4)).buildAndRegister();

        RecipeMaps.VACUUM_RECIPES.recipeBuilder().duration(50).fluidInputs(Materials.Water.getFluid(1000)).fluidOutputs(Materials.Ice.getFluid(1000)).buildAndRegister();
        RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder().duration(200).EUt(8).input(OrePrefix.dust, Materials.RawRubber, 3).input(OrePrefix.dust, Materials.Sulfur, 1).outputs(OreDictUnifier.get(OrePrefix.ingot, Materials.Rubber,1)).buildAndRegister();

        for(int i = 0; i < 16; i++) {
            RecipeMaps.CUTTER_RECIPES.recipeBuilder().duration(50).EUt(8).inputs(new ItemStack(Blocks.STAINED_GLASS, 3, i)).outputs(new ItemStack(Blocks.STAINED_GLASS_PANE, 8, i)).buildAndRegister();
        }
        RecipeMaps.CUTTER_RECIPES.recipeBuilder().duration(50).EUt(8).inputs(new ItemStack(Blocks.GLASS, 3)).outputs(new ItemStack(Blocks.GLASS_PANE, 8)).buildAndRegister();
        RecipeMaps.CUTTER_RECIPES.recipeBuilder().duration(25).EUt(8).inputs(new ItemStack(Blocks.STONE)).outputs(new ItemStack(Blocks.STONE_SLAB, 2)).buildAndRegister();
        RecipeMaps.CUTTER_RECIPES.recipeBuilder().duration(25).EUt(8).inputs(new ItemStack(Blocks.SANDSTONE)).outputs(new ItemStack(Blocks.STONE_SLAB, 2, 1)).buildAndRegister();
        RecipeMaps.CUTTER_RECIPES.recipeBuilder().duration(25).EUt(8).inputs(new ItemStack(Blocks.COBBLESTONE)).outputs(new ItemStack(Blocks.STONE_SLAB, 2, 3)).buildAndRegister();
        RecipeMaps.CUTTER_RECIPES.recipeBuilder().duration(25).EUt(8).inputs(new ItemStack(Blocks.BRICK_BLOCK)).outputs(new ItemStack(Blocks.STONE_SLAB, 2, 4)).buildAndRegister();
        RecipeMaps.CUTTER_RECIPES.recipeBuilder().duration(25).EUt(8).inputs(new ItemStack(Blocks.STONEBRICK)).outputs(new ItemStack(Blocks.STONE_SLAB, 2, 5)).buildAndRegister();
        RecipeMaps.CUTTER_RECIPES.recipeBuilder().duration(25).EUt(8).inputs(new ItemStack(Blocks.NETHER_BRICK)).outputs(new ItemStack(Blocks.STONE_SLAB, 2, 6)).buildAndRegister();
        RecipeMaps.CUTTER_RECIPES.recipeBuilder().duration(25).EUt(8).inputs(new ItemStack(Blocks.QUARTZ_BLOCK, 1, OreDictionary.WILDCARD_VALUE)).outputs(new ItemStack(Blocks.STONE_SLAB, 2, 7)).buildAndRegister();
        RecipeMaps.CUTTER_RECIPES.recipeBuilder().duration(100).EUt(16).inputs(new ItemStack(Blocks.GLOWSTONE)).outputs(OreDictUnifier.get(OrePrefix.plate,Materials.Glowstone,4)).buildAndRegister();

        RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder().duration(100).EUt(16).notConsumable(MetaItems.SHAPE_MOLD_CREDIT.getStackForm()).input(OrePrefix.plate, Materials.Cupronickel, 1).outputs(MetaItems.CREDIT_CUPRONICKEL.getStackForm(4)).buildAndRegister();
        RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder().duration(100).EUt(16).notConsumable(MetaItems.SHAPE_MOLD_CREDIT.getStackForm()).input(OrePrefix.plate, Materials.Brass, 1).outputs(MetaItems.COIN_DOGE.getStackForm(4)).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(1).inputs(new ItemStack(Items.COAL, 1, OreDictionary.WILDCARD_VALUE)).input(OrePrefix.stick, Materials.Wood, 1).outputs(new ItemStack(Blocks.TORCH,4)).duration(400).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(4).input(OrePrefix.plate, Materials.Gold, 2).outputs(new ItemStack(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE,1)).circuitMeta(2).duration(200).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(4).input(OrePrefix.plate, Materials.Iron, 2).outputs(new ItemStack(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE,1)).circuitMeta(2).duration(200).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(4).input(OrePrefix.plate, Materials.Iron, 6).outputs(new ItemStack(Items.IRON_DOOR,1)).circuitMeta(6).duration(600).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(4).input(OrePrefix.plate, Materials.Iron, 7).outputs(new ItemStack(Items.CAULDRON,1)).circuitMeta(7).duration(700).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(4).input(OrePrefix.stick, Materials.Iron, 3).outputs(new ItemStack(Blocks.IRON_BARS,4)).circuitMeta(3).duration(300).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(4).input(OrePrefix.plate, Materials.WroughtIron, 2).outputs(new ItemStack(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE,1)).circuitMeta(2).duration(200).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(4).input(OrePrefix.plate, Materials.WroughtIron, 6).outputs(new ItemStack(Items.IRON_DOOR,1)).circuitMeta(6).duration(600).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(4).input(OrePrefix.plate, Materials.WroughtIron, 7).outputs(new ItemStack(Items.CAULDRON,1)).circuitMeta(7).duration(700).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(4).input(OrePrefix.stick, Materials.WroughtIron, 3).outputs(new ItemStack(Blocks.IRON_BARS,4)).circuitMeta(3).duration(300).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(4).input(OrePrefix.stick, Materials.Wood, 3).outputs(new ItemStack(Blocks.OAK_FENCE,1)).circuitMeta(3).duration(300).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(4).input(OrePrefix.stick, Materials.Wood, 2).input(OrePrefix.ring, Materials.Iron, 2).outputs(new ItemStack(Blocks.TRIPWIRE_HOOK,1)).duration(400).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(4).input(OrePrefix.stick, Materials.Wood, 2).input(OrePrefix.ring, Materials.WroughtIron, 2).outputs(new ItemStack(Blocks.TRIPWIRE_HOOK,1)).duration(400).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(4).inputs(new ItemStack(Items.STRING, 3, OreDictionary.WILDCARD_VALUE)).input(OrePrefix.stick, Materials.Wood, 3).outputs(new ItemStack(Items.BOW,1)).duration(400).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(2).inputs(MetaItems.MINECART_WHEELS_IRON.getStackForm(2)).input(OrePrefix.plate, Materials.Iron, 3).outputs(new ItemStack(Items.MINECART,1)).duration(500).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(2).inputs(MetaItems.MINECART_WHEELS_IRON.getStackForm(2)).input(OrePrefix.plate, Materials.WroughtIron, 3).outputs(new ItemStack(Items.MINECART,1)).duration(400).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(2).inputs(MetaItems.MINECART_WHEELS_STEEL.getStackForm(2)).input(OrePrefix.plate, Materials.Steel, 3).outputs(new ItemStack(Items.MINECART,1)).duration(300).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(2).input(OrePrefix.stick, Materials.Iron, 1).input(OrePrefix.ring, Materials.Iron, 2).outputs(MetaItems.MINECART_WHEELS_IRON.getStackForm()).duration(500).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(2).input(OrePrefix.stick, Materials.WroughtIron, 1).input(OrePrefix.ring, Materials.WroughtIron, 2).outputs(MetaItems.MINECART_WHEELS_IRON.getStackForm()).duration(400).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(2).input(OrePrefix.stick, Materials.Steel, 1).input(OrePrefix.ring, Materials.Steel, 2).outputs(MetaItems.MINECART_WHEELS_STEEL.getStackForm()).duration(300).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(4).inputs(new ItemStack(Items.MINECART), new ItemStack(Blocks.HOPPER, 1, OreDictionary.WILDCARD_VALUE)).outputs(new ItemStack(Items.HOPPER_MINECART,1)).duration(400).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(4).inputs(new ItemStack(Items.MINECART), new ItemStack(Blocks.TNT, 1, OreDictionary.WILDCARD_VALUE)).outputs(new ItemStack(Items.TNT_MINECART,1)).duration(400).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(4).inputs(new ItemStack(Items.MINECART), new ItemStack(Blocks.CHEST, 1, OreDictionary.WILDCARD_VALUE)).outputs(new ItemStack(Items.CHEST_MINECART,1)).duration(400).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(4).inputs(new ItemStack(Items.MINECART), new ItemStack(Blocks.TRAPPED_CHEST, 1, OreDictionary.WILDCARD_VALUE)).outputs(new ItemStack(Items.CHEST_MINECART,1)).duration(400).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(4).inputs(new ItemStack(Items.MINECART), new ItemStack(Blocks.FURNACE, 1, OreDictionary.WILDCARD_VALUE)).outputs(new ItemStack(Items.FURNACE_MINECART,1)).duration(400).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(4).inputs(new ItemStack(Blocks.TRIPWIRE_HOOK), new ItemStack(Blocks.CHEST, 1, OreDictionary.WILDCARD_VALUE)).outputs(new ItemStack(Blocks.TRAPPED_CHEST,1)).duration(200).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(4).inputs(new ItemStack(Blocks.STONE)).outputs(new ItemStack(Blocks.STONEBRICK,1,0)).circuitMeta(4).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(4).inputs(new ItemStack(Blocks.SANDSTONE)).outputs(new ItemStack(Blocks.SANDSTONE,1,2)).circuitMeta(1).duration(50).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(4).inputs(new ItemStack(Blocks.SANDSTONE, 1, 1)).outputs(new ItemStack(Blocks.SANDSTONE,1,0)).circuitMeta(1).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(4).inputs(new ItemStack(Blocks.SANDSTONE, 1, 2)).outputs(new ItemStack(Blocks.SANDSTONE,1,0)).circuitMeta(1).duration(50).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(16).input(OrePrefix.plate, Materials.WroughtIron, 8).outputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.ULV)).circuitMeta(8).duration(25).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(16).input(OrePrefix.plate, Materials.Steel, 8).outputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.LV)).circuitMeta(8).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(16).input(OrePrefix.plate, Materials.Aluminium, 8).outputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.MV)).circuitMeta(8).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(16).input(OrePrefix.plate, Materials.StainlessSteel, 8).outputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.HV)).circuitMeta(8).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(16).input(OrePrefix.plate, Materials.Titanium, 8).outputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.EV)).circuitMeta(8).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(16).input(OrePrefix.plate, Materials.TungstenSteel, 8).outputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.IV)).circuitMeta(8).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(16).input(OrePrefix.plate, Materials.Chrome, 8).outputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.LuV)).circuitMeta(8).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(16).input(OrePrefix.plate, Materials.Iridium, 8).outputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.ZPM)).circuitMeta(8).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(16).input(OrePrefix.plate, Materials.Osmium, 8).outputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.UV)).circuitMeta(8).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(16).input(OrePrefix.plate, Materials.Darmstadtium, 8).outputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.MAX)).circuitMeta(8).duration(50).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(16).input(OrePrefix.wireGtDouble, Materials.Cupronickel, 8).outputs(MetaBlocks.WIRE_COIL.getItemVariant(CoilType.CUPRONICKEL)).circuitMeta(8).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(16).input(OrePrefix.wireGtDouble, Materials.Kanthal, 8).outputs(MetaBlocks.WIRE_COIL.getItemVariant(CoilType.KANTHAL)).circuitMeta(8).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(16).input(OrePrefix.wireGtDouble, Materials.Nichrome, 8).outputs(MetaBlocks.WIRE_COIL.getItemVariant(CoilType.NICHROME)).circuitMeta(8).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(16).input(OrePrefix.wireGtDouble, Materials.TungstenSteel, 8).outputs(MetaBlocks.WIRE_COIL.getItemVariant(CoilType.TUNGSTENSTEEL)).circuitMeta(8).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(16).input(OrePrefix.wireGtDouble, Materials.HSSG, 8).outputs(MetaBlocks.WIRE_COIL.getItemVariant(CoilType.HSS_G)).circuitMeta(8).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(16).input(OrePrefix.wireGtDouble, Materials.Naquadah, 8).outputs(MetaBlocks.WIRE_COIL.getItemVariant(CoilType.NAQUADAH)).circuitMeta(8).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(16).input(OrePrefix.wireGtDouble, Materials.NaquadahAlloy, 8).outputs(MetaBlocks.WIRE_COIL.getItemVariant(CoilType.NAQUADAH_ALLOY)).circuitMeta(8).duration(50).buildAndRegister();
        //RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(16).input(OrePrefix.wireGtDouble, Materials.Superconductor, 8).outputs(MetaBlocks.WIRE_COIL.getItemVariant(CoilType.SUPERCONDUCTOR)).circuitMeta(8).duration(50).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(16).input(OrePrefix.plate, Materials.Invar, 6).input(OrePrefix.frameGt, Materials.Invar, 1).outputs(MetaBlocks.METAL_CASING.getItemVariant(MetalCasingType.INVAR_HEATPROOF)).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(16).input(OrePrefix.plate, Materials.Steel, 6).input(OrePrefix.frameGt, Materials.Steel, 1).outputs(MetaBlocks.METAL_CASING.getItemVariant(MetalCasingType.STEEL_SOLID)).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(16).input(OrePrefix.plate, Materials.Aluminium, 6).input(OrePrefix.frameGt, Materials.Aluminium, 1).outputs(MetaBlocks.METAL_CASING.getItemVariant(MetalCasingType.ALUMINIUM_FROSTPROOF)).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(16).input(OrePrefix.plate, Materials.TungstenSteel, 6).input(OrePrefix.frameGt, Materials.TungstenSteel, 1).outputs(MetaBlocks.METAL_CASING.getItemVariant(MetalCasingType.TUNGSTENSTEEL_ROBUST)).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(16).input(OrePrefix.plate, Materials.StainlessSteel, 6).input(OrePrefix.frameGt, Materials.StainlessSteel, 1).outputs(MetaBlocks.METAL_CASING.getItemVariant(MetalCasingType.STAINLESS_CLEAN)).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(16).input(OrePrefix.plate, Materials.Titanium, 6).input(OrePrefix.frameGt, Materials.Titanium, 1).outputs(MetaBlocks.METAL_CASING.getItemVariant(MetalCasingType.TITANIUM_STABLE)).duration(50).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(16).inputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.LuV)).input(OrePrefix.plate, Materials.TungstenSteel, 6).outputs(MetaBlocks.MUTLIBLOCK_CASING.getItemVariant(MultiblockCasingType.FUSION_CASING)).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(16).input(OrePrefix.plate, Materials.Magnalium, 6).input(OrePrefix.frameGt, Materials.BlueSteel, 1).outputs(MetaBlocks.TURBINE_CASING.getItemVariant(TurbineCasingType.STEEL_TURBINE_CASING)).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(16).inputs(MetaBlocks.TURBINE_CASING.getItemVariant(TurbineCasingType.STEEL_TURBINE_CASING)).input(OrePrefix.plate, Materials.StainlessSteel, 6).outputs(MetaBlocks.TURBINE_CASING.getItemVariant(TurbineCasingType.STAINLESS_TURBINE_CASING)).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(16).inputs(MetaBlocks.TURBINE_CASING.getItemVariant(TurbineCasingType.STEEL_TURBINE_CASING)).input(OrePrefix.plate, Materials.Titanium, 6).outputs(MetaBlocks.TURBINE_CASING.getItemVariant(TurbineCasingType.TITANIUM_TURBINE_CASING)).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(16).inputs(MetaBlocks.TURBINE_CASING.getItemVariant(TurbineCasingType.STEEL_TURBINE_CASING)).input(OrePrefix.plate, Materials.TungstenSteel, 6).outputs(MetaBlocks.TURBINE_CASING.getItemVariant(TurbineCasingType.TUNGSTENSTEEL_TURBINE_CASING)).duration(50).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(25).EUt(16).inputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.ULV)).input(OrePrefix.cableGtSingle, Materials.Lead, 2).fluidInputs(Materials.Plastic.getFluid(288)).outputs(MetaTileEntities.HULL[0].getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(16).inputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.LV)).input(OrePrefix.cableGtSingle, Materials.Tin, 2).fluidInputs(Materials.Plastic.getFluid(288)).outputs(MetaTileEntities.HULL[1].getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(16).inputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.MV)).input(OrePrefix.cableGtSingle, Materials.Copper, 2).fluidInputs(Materials.Plastic.getFluid(288)).outputs(MetaTileEntities.HULL[2].getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(16).inputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.MV)).input(OrePrefix.cableGtSingle, Materials.AnnealedCopper, 2).fluidInputs(Materials.Plastic.getFluid(288)).outputs(MetaTileEntities.HULL[2].getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(16).inputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.HV)).input(OrePrefix.cableGtSingle, Materials.Gold, 2).fluidInputs(Materials.Plastic.getFluid(288)).outputs(MetaTileEntities.HULL[3].getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(16).inputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.EV)).input(OrePrefix.cableGtSingle, Materials.Aluminium, 2).fluidInputs(Materials.Plastic.getFluid(288)).outputs(MetaTileEntities.HULL[4].getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(16).inputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.IV)).input(OrePrefix.cableGtSingle, Materials.Tungsten, 2).fluidInputs(Materials.Plastic.getFluid(288)).outputs(MetaTileEntities.HULL[5].getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(16).inputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.LuV)).input(OrePrefix.cableGtSingle, Materials.VanadiumGallium, 2).fluidInputs(Materials.Plastic.getFluid(288)).outputs(MetaTileEntities.HULL[6].getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(16).inputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.ZPM)).input(OrePrefix.cableGtSingle, Materials.Naquadah, 2).fluidInputs(Materials.Polytetrafluoroethylene.getFluid(288)).outputs(MetaTileEntities.HULL[7].getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(16).inputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.UV)).input(OrePrefix.wireGtQuadruple, Materials.NaquadahAlloy, 2).fluidInputs(Materials.Polytetrafluoroethylene.getFluid(288)).outputs(MetaTileEntities.HULL[8].getStackForm()).buildAndRegister();
        //RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(16).inputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.MAX)).input(OrePrefix.wireGtSingle, Materials.Superconductor, 2).fluidInputs(Materials.Polytetrafluoroethylene.getFluid(288)).outputs(MetaTileEntities.HULL[9].getStackForm()).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(800).EUt(1).input(OrePrefix.cableGtSingle, Materials.Tin, 1).input(OrePrefix.plate, Materials.BatteryAlloy, 1).fluidInputs(Materials.Plastic.getFluid(144)).outputs(MetaItems.BATTERY_HULL_LV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(1600).EUt(2).input(OrePrefix.cableGtSingle, Materials.Copper, 2).input(OrePrefix.plate, Materials.BatteryAlloy, 3).fluidInputs(Materials.Plastic.getFluid(432)).outputs(MetaItems.BATTERY_HULL_MV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(1600).EUt(2).input(OrePrefix.cableGtSingle, Materials.AnnealedCopper, 2).input(OrePrefix.plate, Materials.BatteryAlloy, 3).fluidInputs(Materials.Plastic.getFluid(432)).outputs(MetaItems.BATTERY_HULL_MV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(3200).EUt(4).input(OrePrefix.cableGtSingle, Materials.Gold, 4).input(OrePrefix.plate, Materials.BatteryAlloy, 9).fluidInputs(Materials.Plastic.getFluid(1296)).outputs(MetaItems.BATTERY_HULL_HV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(2).inputs(new ItemStack(Items.STRING, 4, OreDictionary.WILDCARD_VALUE), new ItemStack(Items.SLIME_BALL, 1, OreDictionary.WILDCARD_VALUE)).outputs(new ItemStack(Items.LEAD,2)).duration(200).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(2).inputs(new ItemStack(Blocks.CHEST, 1, OreDictionary.WILDCARD_VALUE)).input(OrePrefix.plate, Materials.Iron, 5).outputs(new ItemStack(Blocks.HOPPER)).duration(800).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(2).inputs(new ItemStack(Blocks.TRAPPED_CHEST, 1, OreDictionary.WILDCARD_VALUE)).input(OrePrefix.plate, Materials.Iron, 5).outputs(new ItemStack(Blocks.HOPPER)).duration(800).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(2).inputs(new ItemStack(Blocks.CHEST, 1, OreDictionary.WILDCARD_VALUE)).input(OrePrefix.plate, Materials.WroughtIron, 5).outputs(new ItemStack(Blocks.HOPPER)).duration(800).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(2).inputs(new ItemStack(Blocks.TRAPPED_CHEST, 1, OreDictionary.WILDCARD_VALUE)).input(OrePrefix.plate, Materials.WroughtIron, 5).outputs(new ItemStack(Blocks.HOPPER)).duration(800).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(2).inputs(new ItemStack(Items.BLAZE_POWDER)).input(OrePrefix.gem, Materials.EnderPearl, 1).outputs(new ItemStack(Items.ENDER_EYE,1,0)).duration(400).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(2).inputs(new ItemStack(Items.BLAZE_ROD)).input(OrePrefix.gem, Materials.EnderPearl, 6).outputs(new ItemStack(Items.ENDER_EYE,6,0)).duration(2500).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(2).input(OrePrefix.gear, Materials.CobaltBrass, 1).input(OrePrefix.dust, Materials.Diamond, 1).outputs(MetaItems.COMPONENT_SAWBLADE_DIAMOND.getStackForm(1)).duration(1600).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(1).input(OrePrefix.dust, Materials.Redstone, 4).input(OrePrefix.dust, Materials.Glowstone, 4).outputs(new ItemStack(Blocks.REDSTONE_LAMP,1)).duration(400).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(1).input(OrePrefix.dust, Materials.Redstone, 1).input(OrePrefix.stick, Materials.Wood, 1).outputs(new ItemStack(Blocks.REDSTONE_TORCH,1)).duration(400).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(4).input(OrePrefix.dust, Materials.Redstone, 1).input(OrePrefix.plate, Materials.Iron, 4).outputs(new ItemStack(Items.COMPASS,1)).duration(400).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(4).input(OrePrefix.dust, Materials.Redstone, 1).input(OrePrefix.plate, Materials.WroughtIron, 4).outputs(new ItemStack(Items.COMPASS,1)).duration(400).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(4).input(OrePrefix.dust, Materials.Redstone, 1).input(OrePrefix.plate, Materials.Gold, 4).outputs(new ItemStack(Items.CLOCK,1)).duration(400).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(1).input(OrePrefix.stick, Materials.Wood, 1).input(OrePrefix.dust, Materials.Sulfur, 1).outputs(new ItemStack(Blocks.TORCH,2)).duration(400).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(1).input(OrePrefix.stick, Materials.Wood, 1).input(OrePrefix.dust, Materials.Phosphorus, 1).outputs(new ItemStack(Blocks.TORCH,6)).duration(400).buildAndRegister();

        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(50).EUt(30).inputs(new ItemStack(Blocks.SAND, 1, 1)).chancedOutput(OreDictUnifier.get(OrePrefix.dust,Materials.Iron,1), 500).chancedOutput(OreDictUnifier.get(OrePrefix.dustTiny,Materials.Diamond,1), 100).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(250).EUt(30).inputs(new ItemStack(Blocks.DIRT, 1, OreDictionary.WILDCARD_VALUE)).chancedOutput(MetaItems.PLANT_BALL.getStackForm(), 1250).chancedOutput(new ItemStack(Blocks.SAND), 5000).chancedOutput(OreDictUnifier.get(OrePrefix.dustTiny,Materials.Clay,1), 5000).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(250).EUt(30).inputs(new ItemStack(Blocks.GRASS, 1, OreDictionary.WILDCARD_VALUE)).chancedOutput(MetaItems.PLANT_BALL.getStackForm(), 2500).chancedOutput(new ItemStack(Blocks.SAND), 5000).chancedOutput(OreDictUnifier.get(OrePrefix.dustTiny,Materials.Clay,1), 5000).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(650).EUt(30).inputs(new ItemStack(Blocks.MYCELIUM, 1, OreDictionary.WILDCARD_VALUE)).chancedOutput(new ItemStack(Blocks.RED_MUSHROOM), 2500).chancedOutput(new ItemStack(Blocks.SAND), 5000).chancedOutput(new ItemStack(Blocks.BROWN_MUSHROOM), 2500).chancedOutput(OreDictUnifier.get(OrePrefix.dustTiny,Materials.Clay,1), 5000).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(50).EUt(30).input(OrePrefix.dust, Materials.DarkAsh, 2).outputs(OreDictUnifier.get(OrePrefix.dust,Materials.Ash,1), OreDictUnifier.get(OrePrefix.dust,Materials.Ash,1)).buildAndRegister();

        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(800).EUt(320).input(OrePrefix.dust, Materials.Uranium, 1).chancedOutput(OreDictUnifier.get(OrePrefix.dustTiny,Materials.Plutonium,1), 200).chancedOutput(OreDictUnifier.get(OrePrefix.dustTiny,Materials.Uranium235,1), 2000).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(1600).EUt(320).input(OrePrefix.dust, Materials.Plutonium, 1).chancedOutput(OreDictUnifier.get(OrePrefix.dustTiny,Materials.Uranium,1), 3000).chancedOutput(OreDictUnifier.get(OrePrefix.dustTiny,Materials.Plutonium241,1), 2000).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(3200).EUt(320).input(OrePrefix.dust, Materials.Naquadah, 1).chancedOutput(OreDictUnifier.get(OrePrefix.dustTiny,Materials.Naquadria,1), 1000).chancedOutput(OreDictUnifier.get(OrePrefix.dustTiny,Materials.NaquadahEnriched,1), 5000).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(6400).EUt(640).input(OrePrefix.dust, Materials.NaquadahEnriched, 1).chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall,Materials.Naquadah,1), 3000).chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall,Materials.Naquadria,1), 2000).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(160).EUt(20).fluidInputs(Materials.Hydrogen.getFluid(160)).fluidOutputs(Materials.Deuterium.getFluid(40)).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(160).EUt(80).fluidInputs(Materials.Deuterium.getFluid(160)).fluidOutputs(Materials.Tritium.getFluid(40)).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(160).EUt(80).fluidInputs(Materials.Helium.getFluid(80)).fluidOutputs(Materials.Helium3.getFluid(5)).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(488).EUt(80).input(OrePrefix.dust, Materials.Glowstone, 1).outputs(OreDictUnifier.get(OrePrefix.dustSmall,Materials.Redstone,2), OreDictUnifier.get(OrePrefix.dustSmall,Materials.Gold,2)).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(320).EUt(20).input(OrePrefix.dust, Materials.Endstone, 1).chancedOutput(new ItemStack(Blocks.SAND), 9000).chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall,Materials.Tungstate,1), 1250).chancedOutput(OreDictUnifier.get(OrePrefix.dustTiny,Materials.Platinum,1), 625).fluidOutputs(Materials.Helium.getFluid(120)).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(160).EUt(20).input(OrePrefix.dust, Materials.Netherrack, 1).chancedOutput(OreDictUnifier.get(OrePrefix.dustTiny,Materials.Redstone,1), 5625).chancedOutput(OreDictUnifier.get(OrePrefix.dustTiny,Materials.Gold,1), 625).chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall,Materials.Sulfur,1), 9900).chancedOutput(OreDictUnifier.get(OrePrefix.dustTiny,Materials.Coal,1), 5625).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(200).EUt(80).inputs(new ItemStack(Blocks.SOUL_SAND)).chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall,Materials.Saltpeter,1), 8000).chancedOutput(new ItemStack(Blocks.SAND), 9000).chancedOutput(OreDictUnifier.get(OrePrefix.dustTiny,Materials.Coal,1), 2000).fluidOutputs(Materials.Oil.getFluid(80)).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(80).EUt(80).fluidInputs(Materials.Lava.getFluid(100)).chancedOutput(OreDictUnifier.get(OrePrefix.nugget,Materials.Tantalum,1), 250).chancedOutput(OreDictUnifier.get(OrePrefix.nugget,Materials.Gold,1), 250).chancedOutput(OreDictUnifier.get(OrePrefix.nugget,Materials.Tin,1), 1000).chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall,Materials.Tungstate,1), 250).chancedOutput(OreDictUnifier.get(OrePrefix.nugget,Materials.Copper,1), 2000).chancedOutput(OreDictUnifier.get(OrePrefix.nugget,Materials.Silver,1), 250).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(64).EUt(20).input(OrePrefix.dust, Materials.RareEarth, 1).chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall,Materials.Cadmium,1), 2500).chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall,Materials.Neodymium,1), 2500).chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall,Materials.Caesium,1), 2500).chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall,Materials.Cerium,1), 2500).chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall,Materials.Yttrium,1), 2500).chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall,Materials.Lanthanum,1), 2500).buildAndRegister();
    }
    public static void shapingRecipes() {
        RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder()
            .inputs(GTUtility.copyAmount(31, OreDictUnifier.get(OrePrefix.ingot, Materials.Iron)))
            .notConsumable(MetaItems.SHAPE_MOLD_ANVIL)
            .outputs(new ItemStack(Blocks.ANVIL, 1, 0))
            .duration(31 * 512)
            .EUt(4 * 16)
            .buildAndRegister();

        RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder()
            .inputs(GTUtility.copyAmount(31, OreDictUnifier.get(OrePrefix.ingot, Materials.WroughtIron)))
            .notConsumable(MetaItems.SHAPE_MOLD_ANVIL)
            .outputs(new ItemStack(Blocks.ANVIL, 1, 0))
            .duration(31 * 512)
            .EUt(4 * 16)
            .buildAndRegister();

        ModHandler.addShapelessRecipe(Materials.RedAlloy + "_t_cable01", OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.RedAlloy),
            new UnificationEntry(OrePrefix.wireGtSingle, Materials.RedAlloy),
            new UnificationEntry(OrePrefix.plate, Materials.Paper));

        RecipeMaps.PACKER_RECIPES.recipeBuilder()
            .inputs(GTUtility.copyAmount(1, OreDictUnifier.get(OrePrefix.wireGtSingle, Materials.RedAlloy)), OreDictUnifier.get(OrePrefix.plate, Materials.Paper))
            .outputs(OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.RedAlloy))
            .duration(100)
            .EUt(8)
            .buildAndRegister();

        RecipeMaps.UNPACKER_RECIPES.recipeBuilder()
            .inputs(OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.RedAlloy))
            .outputs(GTUtility.copyAmount(1, OreDictUnifier.get(OrePrefix.wireGtSingle, Materials.RedAlloy)), OreDictUnifier.get(OrePrefix.plate, Materials.Paper))
            .duration(100)
            .EUt(8)
            .buildAndRegister();

        ModHandler.addShapelessRecipe(Materials.RedAlloy + "_t_cable02", OreDictUnifier.get(OrePrefix.cableGtDouble, Materials.RedAlloy),
            new UnificationEntry(OrePrefix.wireGtDouble, Materials.RedAlloy),
            new UnificationEntry(OrePrefix.plate, Materials.Paper));

        RecipeMaps.PACKER_RECIPES.recipeBuilder()
            .inputs(GTUtility.copyAmount(1, OreDictUnifier.get(OrePrefix.wireGtDouble, Materials.RedAlloy)), OreDictUnifier.get(OrePrefix.plate, Materials.Paper))
            .outputs(OreDictUnifier.get(OrePrefix.cableGtDouble, Materials.RedAlloy))
            .duration(100)
            .EUt(8)
            .buildAndRegister();

        RecipeMaps.UNPACKER_RECIPES.recipeBuilder()
            .inputs(OreDictUnifier.get(OrePrefix.cableGtDouble, Materials.RedAlloy))
            .outputs(GTUtility.copyAmount(1, OreDictUnifier.get(OrePrefix.wireGtDouble, Materials.RedAlloy)), OreDictUnifier.get(OrePrefix.plate, Materials.Paper))
            .duration(100)
            .EUt(8)
            .buildAndRegister();

        ModHandler.addShapelessRecipe(Materials.RedAlloy + "_t_cable04", OreDictUnifier.get(OrePrefix.cableGtQuadruple, Materials.RedAlloy),
            new UnificationEntry(OrePrefix.wireGtQuadruple, Materials.RedAlloy),
            new UnificationEntry(OrePrefix.plate, Materials.Paper),
            new UnificationEntry(OrePrefix.plate, Materials.Paper));

        RecipeMaps.PACKER_RECIPES.recipeBuilder()
            .inputs(GTUtility.copyAmount(1, OreDictUnifier.get(OrePrefix.wireGtQuadruple, Materials.RedAlloy)), OreDictUnifier.get(OrePrefix.plate, Materials.Paper, 2))
            .outputs(OreDictUnifier.get(OrePrefix.cableGtQuadruple, Materials.RedAlloy))
            .duration(100)
            .EUt(8)
            .buildAndRegister();

        RecipeMaps.UNPACKER_RECIPES.recipeBuilder()
            .inputs(OreDictUnifier.get(OrePrefix.cableGtQuadruple, Materials.RedAlloy))
            .outputs(GTUtility.copyAmount(1, OreDictUnifier.get(OrePrefix.wireGtOctal, Materials.RedAlloy)), OreDictUnifier.get(OrePrefix.plate, Materials.Paper, 2))
            .duration(100)
            .EUt(8)
            .buildAndRegister();

        ModHandler.addShapelessRecipe(Materials.RedAlloy + "_t_cable08", OreDictUnifier.get(OrePrefix.cableGtOctal, Materials.RedAlloy),
            new UnificationEntry(OrePrefix.wireGtOctal, Materials.RedAlloy),
            new UnificationEntry(OrePrefix.plate, Materials.Paper),
            new UnificationEntry(OrePrefix.plate, Materials.Paper),
            new UnificationEntry(OrePrefix.plate, Materials.Paper));

        RecipeMaps.PACKER_RECIPES.recipeBuilder()
            .inputs(GTUtility.copyAmount(1, OreDictUnifier.get(OrePrefix.wireGtOctal, Materials.RedAlloy)), OreDictUnifier.get(OrePrefix.plate, Materials.Paper, 3))
            .outputs(OreDictUnifier.get(OrePrefix.cableGtOctal, Materials.RedAlloy))
            .duration(100)
            .EUt(8)
            .buildAndRegister();

        RecipeMaps.UNPACKER_RECIPES.recipeBuilder()
            .inputs(OreDictUnifier.get(OrePrefix.cableGtOctal, Materials.RedAlloy))
            .outputs(GTUtility.copyAmount(1, OreDictUnifier.get(OrePrefix.wireGtOctal, Materials.RedAlloy)), OreDictUnifier.get(OrePrefix.plate, Materials.Paper, 3))
            .duration(100)
            .EUt(8)
            .buildAndRegister();

        ModHandler.addShapelessRecipe(Materials.RedAlloy + "_t_cable12", OreDictUnifier.get(OrePrefix.cableGtTwelve, Materials.RedAlloy),
            new UnificationEntry(OrePrefix.wireGtTwelve, Materials.RedAlloy),
            new UnificationEntry(OrePrefix.plate, Materials.Paper),
            new UnificationEntry(OrePrefix.plate, Materials.Paper),
            new UnificationEntry(OrePrefix.plate, Materials.Paper),
            new UnificationEntry(OrePrefix.plate, Materials.Paper));

        RecipeMaps.PACKER_RECIPES.recipeBuilder()
            .inputs(GTUtility.copyAmount(1, OreDictUnifier.get(OrePrefix.wireGtTwelve, Materials.RedAlloy)), OreDictUnifier.get(OrePrefix.plate, Materials.Paper, 4))
            .outputs(OreDictUnifier.get(OrePrefix.cableGtTwelve, Materials.RedAlloy))
            .duration(100)
            .EUt(8)
            .buildAndRegister();

        RecipeMaps.UNPACKER_RECIPES.recipeBuilder()
            .inputs(OreDictUnifier.get(OrePrefix.cableGtTwelve, Materials.RedAlloy))
            .outputs(GTUtility.copyAmount(1, OreDictUnifier.get(OrePrefix.wireGtTwelve, Materials.RedAlloy)), OreDictUnifier.get(OrePrefix.plate, Materials.Paper, 4))
            .duration(100)
            .EUt(8)
            .buildAndRegister();
    }
    public static void registerAssemblyLineRecipes() {
    AssemblyLineRecipeBuilder.start()
        .researchItem(MetaItems.ELECTRIC_MOTOR_IV.getStackForm())
        .researchTime(144000)
            .inputs(OreDictUnifier.get(OrePrefix.stickLong, Materials.NeodymiumMagnetic, 1),
                OreDictUnifier.get(OrePrefix.stickLong, Materials.HSSG, 2),
        OreDictUnifier.get(OrePrefix.wireFine, Materials.AnnealedCopper, 64),
        OreDictUnifier.get(OrePrefix.wireFine, Materials.AnnealedCopper, 64),
        OreDictUnifier.get(OrePrefix.wireFine, Materials.AnnealedCopper, 64),
        OreDictUnifier.get(OrePrefix.wireFine, Materials.AnnealedCopper, 64),
        OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.YttriumBariumCuprate, 2))
        .fluidInputs(Materials.SolderingAlloy.getFluid(L), Materials.Lubricant.getFluid(250))
        .output(MetaItems.ELECTRIC_MOTOR_LUV.getStackForm())
        .duration(600)
            .EUt(6000)
            .buildAndRegister();

        AssemblyLineRecipeBuilder.start()
            .researchItem(MetaItems.ELECTRIC_MOTOR_LUV.getStackForm())
        .researchTime(144000)
            .inputs(OreDictUnifier.get(OrePrefix.stickLong, Materials.NeodymiumMagnetic, 2),
                OreDictUnifier.get(OrePrefix.stickLong, Materials.HSSE, 4),
        OreDictUnifier.get(OrePrefix.ring, Materials.HSSE, 4),
        OreDictUnifier.get(OrePrefix.screw, Materials.HSSE, 16),
        OreDictUnifier.get(OrePrefix.wireFine, Materials.Platinum, 64),
        OreDictUnifier.get(OrePrefix.wireFine, Materials.Platinum, 64),
        OreDictUnifier.get(OrePrefix.wireFine, Materials.Platinum, 64),
        OreDictUnifier.get(OrePrefix.wireFine, Materials.Platinum, 64),
        OreDictUnifier.get(OrePrefix.cableGtOctal, Materials.VanadiumGallium, 2))
        .fluidInputs(Materials.SolderingAlloy.getFluid(2 * L),
                Materials.Lubricant.getFluid(750))
                    .output(MetaItems.ELECTRIC_MOTOR_ZPM.getStackForm())
        .duration(600)
            .EUt(24000)
            .buildAndRegister();

        AssemblyLineRecipeBuilder.start()
            .researchItem(MetaItems.ELECTRIC_MOTOR_ZPM.getStackForm())
        .researchTime(288000)
            .inputs(OreDictUnifier.get(OrePrefix.block, Materials.NeodymiumMagnetic, 1),
                OreDictUnifier.get(OrePrefix.stickLong, Materials.Darmstadtium, 4),
        OreDictUnifier.get(OrePrefix.ring, Materials.Darmstadtium, 4),
        OreDictUnifier.get(OrePrefix.screw, Materials.Darmstadtium, 16),
        OreDictUnifier.get(OrePrefix.wireGtSingle, MarkerMaterials.Tier.Superconductor, 64),
        OreDictUnifier.get(OrePrefix.wireGtSingle, MarkerMaterials.Tier.Superconductor, 64),
        OreDictUnifier.get(OrePrefix.wireGtSingle, MarkerMaterials.Tier.Superconductor, 64),
        OreDictUnifier.get(OrePrefix.wireGtSingle, MarkerMaterials.Tier.Superconductor, 64),
        OreDictUnifier.get(OrePrefix.cableGtOctal, Materials.NiobiumTitanium, 2))
        .fluidInputs(Materials.SolderingAlloy.getFluid(9 * L),
                Materials.Lubricant.getFluid(2000))
                    .output(MetaItems.ELECTRIC_MOTOR_UV.getStackForm())
        .duration(600)
            .EUt(100000)
            .buildAndRegister();

        AssemblyLineRecipeBuilder.start()
            .researchItem(MetaItems.ELECTRIC_PUMP_IV.getStackForm())
        .researchTime(144000)
            .inputs(MetaItems.ELECTRIC_MOTOR_LUV.getStackForm(),
                OreDictUnifier.get(OrePrefix.pipeSmall, MarkerMaterials.Tier.Ultimate, 2),
        OreDictUnifier.get(OrePrefix.plate, Materials.HSSG, 2),
        OreDictUnifier.get(OrePrefix.screw, Materials.HSSG, 8),
        OreDictUnifier.get(OrePrefix.ring, Materials.Rubber, 4),
        OreDictUnifier.get(OrePrefix.rotor, Materials.HSSG, 2),
        OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.YttriumBariumCuprate, 2))
        .fluidInputs(Materials.SolderingAlloy.getFluid(L), Materials.Lubricant.getFluid(250))
        .output(MetaItems.ELECTRIC_PUMP_LUV.getStackForm())
        .duration(600)
            .EUt(6000)
            .buildAndRegister();

        AssemblyLineRecipeBuilder.start()
            .researchItem(MetaItems.ELECTRIC_PUMP_LUV.getStackForm())
        .researchTime(144000)
            .inputs(MetaItems.ELECTRIC_MOTOR_ZPM.getStackForm(),
                OreDictUnifier.get(OrePrefix.pipeMedium, MarkerMaterials.Tier.Ultimate, 2),
        OreDictUnifier.get(OrePrefix.plate, Materials.HSSE, 2),
        OreDictUnifier.get(OrePrefix.screw, Materials.HSSE, 8),
        OreDictUnifier.get(OrePrefix.ring, Materials.Rubber, 16),
        OreDictUnifier.get(OrePrefix.rotor, Materials.HSSE, 2),
        OreDictUnifier.get(OrePrefix.cableGtOctal, Materials.VanadiumGallium, 2))
        .fluidInputs(Materials.SolderingAlloy.getFluid(2 * L),
                Materials.Lubricant.getFluid(750))
                    .output(MetaItems.ELECTRIC_PUMP_ZPM.getStackForm())
        .duration(600)
            .EUt(24000)
            .buildAndRegister();

        AssemblyLineRecipeBuilder.start()
            .researchItem(MetaItems.ELECTRIC_PUMP_ZPM.getStackForm())
        .researchTime(288000)
            .inputs(MetaItems.ELECTRIC_MOTOR_UV.getStackForm(),
                OreDictUnifier.get(OrePrefix.pipeLarge, MarkerMaterials.Tier.Ultimate, 2),
        OreDictUnifier.get(OrePrefix.plate, Materials.Darmstadtium, 2),
        OreDictUnifier.get(OrePrefix.screw, Materials.Darmstadtium, 8),
        OreDictUnifier.get(OrePrefix.ring, Materials.Rubber, 16),
        OreDictUnifier.get(OrePrefix.rotor, Materials.Darmstadtium, 2),
        OreDictUnifier.get(OrePrefix.cableGtOctal, Materials.NiobiumTitanium, 2))
        .fluidInputs(Materials.SolderingAlloy.getFluid(1296),
                Materials.Lubricant.getFluid(2000))
                    .output(MetaItems.ELECTRIC_PUMP_UV.getStackForm())
        .duration(600)
            .EUt(100000)
            .buildAndRegister();

        AssemblyLineRecipeBuilder.start()
            .researchItem(MetaItems.CONVEYOR_MODULE_IV.getStackForm())
        .researchTime(144000)
            .inputs(MetaItems.ELECTRIC_MOTOR_LUV.getStackForm(2),
                OreDictUnifier.get(OrePrefix.plate, Materials.HSSG, 2),
        OreDictUnifier.get(OrePrefix.ring, Materials.HSSG, 4),
        OreDictUnifier.get(OrePrefix.screw, Materials.HSSG, 32),
        OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.YttriumBariumCuprate, 2))
        .fluidInputs(Materials.Rubber.getFluid(10 * L),
                Materials.Lubricant.getFluid(250))
                    .output(MetaItems.CONVEYOR_MODULE_LUV.getStackForm())
        .duration(600)
            .EUt(6000)
            .buildAndRegister();

        AssemblyLineRecipeBuilder.start()
            .researchItem(MetaItems.CONVEYOR_MODULE_LUV.getStackForm())
        .researchTime(144000)
            .inputs(MetaItems.ELECTRIC_MOTOR_ZPM.getStackForm(2),
                OreDictUnifier.get(OrePrefix.plate, Materials.HSSE, 2),
        OreDictUnifier.get(OrePrefix.ring, Materials.HSSE, 4),
        OreDictUnifier.get(OrePrefix.screw, Materials.HSSE, 32),
        OreDictUnifier.get(OrePrefix.cableGtOctal, Materials.VanadiumGallium, 2))
        .fluidInputs(Materials.Rubber.getFluid(2880),
                Materials.Lubricant.getFluid(750))
                    .output(MetaItems.CONVEYOR_MODULE_ZPM.getStackForm())
        .duration(600)
            .EUt(24000)
            .buildAndRegister();

        AssemblyLineRecipeBuilder.start()
            .researchItem(MetaItems.CONVEYOR_MODULE_ZPM.getStackForm())
        .researchTime(288000)
            .inputs(MetaItems.ELECTRIC_MOTOR_UV.getStackForm(2),
                OreDictUnifier.get(OrePrefix.plate, Materials.Darmstadtium, 2),
        OreDictUnifier.get(OrePrefix.ring, Materials.Darmstadtium, 4),
        OreDictUnifier.get(OrePrefix.screw, Materials.Darmstadtium, 32),
        OreDictUnifier.get(OrePrefix.cableGtOctal, Materials.NiobiumTitanium, 2))
        .fluidInputs(Materials.Rubber.getFluid(20 * L),
                Materials.Lubricant.getFluid(2000))
                    .output(MetaItems.CONVEYOR_MODULE_UV.getStackForm())
        .duration(600)
            .EUt(100000)
            .buildAndRegister();

        AssemblyLineRecipeBuilder.start()
            .researchItem(MetaItems.ELECTRIC_PISTON_IV.getStackForm())
        .researchTime(144000)
            .inputs(MetaItems.ELECTRIC_MOTOR_LUV.getStackForm(),
                OreDictUnifier.get(OrePrefix.plate, Materials.HSSG, 6),
        OreDictUnifier.get(OrePrefix.ring, Materials.HSSG, 4),
        OreDictUnifier.get(OrePrefix.screw, Materials.HSSG, 32),
        OreDictUnifier.get(OrePrefix.stick, Materials.HSSG, 4),
        OreDictUnifier.get(OrePrefix.gear, Materials.HSSG, 1),
        OreDictUnifier.get(OrePrefix.gearSmall, Materials.HSSG, 2),
        OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.YttriumBariumCuprate, 4))
        .fluidInputs(Materials.SolderingAlloy.getFluid(L),
                Materials.Lubricant.getFluid(250))
                    .output( MetaItems.ELECTRIC_PISTON_LUV.getStackForm())
        .duration(600)
            .EUt(6000)
            .buildAndRegister();

        AssemblyLineRecipeBuilder.start()
            .researchItem(MetaItems.ELECTRIC_PISTON_LUV.getStackForm())
        .researchTime(144000)
            .inputs(MetaItems.ELECTRIC_MOTOR_ZPM.getStackForm(),
                OreDictUnifier.get(OrePrefix.plate, Materials.HSSE, 6),
        OreDictUnifier.get(OrePrefix.ring, Materials.HSSE, 4),
        OreDictUnifier.get(OrePrefix.screw, Materials.HSSE, 32),
        OreDictUnifier.get(OrePrefix.stick, Materials.HSSE, 4),
        OreDictUnifier.get(OrePrefix.gear, Materials.HSSE, 1),
        OreDictUnifier.get(OrePrefix.gearSmall, Materials.HSSE, 2),
        OreDictUnifier.get(OrePrefix.cableGtOctal, Materials.VanadiumGallium, 4))
        .fluidInputs(Materials.SolderingAlloy.getFluid(2 * L),
                Materials.Lubricant.getFluid(750))
                    .output(MetaItems.ELECTRIC_PISTON_ZPM.getStackForm())
        .duration(600)
            .EUt(24000)
            .buildAndRegister();

        AssemblyLineRecipeBuilder.start()
            .researchItem(MetaItems.ELECTRIC_PISTON_ZPM.getStackForm())
        .researchTime(288000)
            .inputs(MetaItems.ELECTRIC_MOTOR_UV.getStackForm(),
                OreDictUnifier.get(OrePrefix.plate, Materials.Darmstadtium, 6),
        OreDictUnifier.get(OrePrefix.ring, Materials.Darmstadtium, 4),
        OreDictUnifier.get(OrePrefix.screw, Materials.Darmstadtium, 32),
        OreDictUnifier.get(OrePrefix.stick, Materials.Darmstadtium, 4),
        OreDictUnifier.get(OrePrefix.gear, Materials.Darmstadtium, 1),
        OreDictUnifier.get(OrePrefix.gearSmall, Materials.Darmstadtium, 2),
        OreDictUnifier.get(OrePrefix.cableGtOctal, Materials.NiobiumTitanium, 4))
        .fluidInputs(Materials.SolderingAlloy.getFluid(9 * L),
                Materials.Lubricant.getFluid(2000))
                    .output(MetaItems.ELECTRIC_PISTON_UV.getStackForm())
        .duration(600)
            .EUt(100000)
            .buildAndRegister();

        AssemblyLineRecipeBuilder.start()
            .researchItem(MetaItems.ROBOT_ARM_IV.getStackForm())
        .researchTime(144000)
            .inputs(OreDictUnifier.get(OrePrefix.stickLong, Materials.HSSG, 4),
                OreDictUnifier.get(OrePrefix.gear, Materials.HSSG, 1),
        OreDictUnifier.get(OrePrefix.gearSmall, Materials.HSSG, 3),
        MetaItems.ELECTRIC_MOTOR_LUV.getStackForm(2),
        MetaItems.ELECTRIC_PISTON_LUV.getStackForm(),
        OreDictUnifier.get(OrePrefix.circuit, MarkerMaterials.Tier.Master, 2),
        OreDictUnifier.get(OrePrefix.circuit, MarkerMaterials.Tier.Elite, 2),
        OreDictUnifier.get(OrePrefix.circuit, MarkerMaterials.Tier.Advanced, 6),
        OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.YttriumBariumCuprate, 6))
        .fluidInputs(Materials.SolderingAlloy.getFluid(4 * L),
                Materials.Lubricant.getFluid(250))
                    .output(MetaItems.ROBOT_ARM_LUV.getStackForm())
        .duration(600)
            .EUt(6000)
            .buildAndRegister();

        AssemblyLineRecipeBuilder.start()
            .researchItem(MetaItems.ROBOT_ARM_LUV.getStackForm())
        .researchTime(144000)
            .inputs(OreDictUnifier.get(OrePrefix.stickLong, Materials.HSSE, 4),
                OreDictUnifier.get(OrePrefix.gear, Materials.HSSE, 1),
        OreDictUnifier.get(OrePrefix.gearSmall, Materials.HSSE, 3),
        MetaItems.ELECTRIC_MOTOR_ZPM.getStackForm(2),
        MetaItems.ELECTRIC_PISTON_ZPM.getStackForm(),
        OreDictUnifier.get(OrePrefix.circuit, MarkerMaterials.Tier.Master, 4),
        OreDictUnifier.get(OrePrefix.circuit, MarkerMaterials.Tier.Elite, 4),
        OreDictUnifier.get(OrePrefix.circuit, MarkerMaterials.Tier.Advanced, 12),
        OreDictUnifier.get(OrePrefix.cableGtOctal, Materials.VanadiumGallium, 6))
        .fluidInputs(Materials.SolderingAlloy.getFluid(8 * L),
                Materials.Lubricant.getFluid(750))
                    .output(MetaItems.ROBOT_ARM_ZPM.getStackForm())
        .duration(600)
            .EUt(24000)
            .buildAndRegister();

        AssemblyLineRecipeBuilder.start()
            .researchItem(MetaItems.ROBOT_ARM_ZPM.getStackForm())
        .researchTime(288000)
            .inputs(OreDictUnifier.get(OrePrefix.stickLong, Materials.Darmstadtium, 4),
                OreDictUnifier.get(OrePrefix.gear, Materials.Darmstadtium, 1),
        OreDictUnifier.get(OrePrefix.gearSmall, Materials.Darmstadtium, 3),
        MetaItems.ELECTRIC_MOTOR_UV.getStackForm(2),
        MetaItems.ELECTRIC_PISTON_UV.getStackForm(),
        OreDictUnifier.get(OrePrefix.circuit, MarkerMaterials.Tier.Master, 8),
        OreDictUnifier.get(OrePrefix.circuit, MarkerMaterials.Tier.Elite, 8),
        OreDictUnifier.get(OrePrefix.circuit, MarkerMaterials.Tier.Advanced, 24),
        OreDictUnifier.get(OrePrefix.cableGtOctal, Materials.NiobiumTitanium, 6))
        .fluidInputs(Materials.SolderingAlloy.getFluid(16 * L),
                Materials.Lubricant.getFluid(2000))
                    .output(MetaItems.ROBOT_ARM_UV.getStackForm())
        .duration(600)
            .EUt(100000)
            .buildAndRegister();

        AssemblyLineRecipeBuilder.start()
            .researchItem(MetaItems.EMITTER_IV.getStackForm())
        .researchTime(144000)
            .inputs(OreDictUnifier.get(OrePrefix.frameGt, Materials.HSSG, 1),
                MetaItems.EMITTER_IV.getStackForm(),
                    MetaItems.EMITTER_EV.getStackForm(2),
                    MetaItems.EMITTER_HV.getStackForm(4),
                    OreDictUnifier.get(OrePrefix.circuit, MarkerMaterials.Tier.Advanced, 7),
        OreDictUnifier.get(OrePrefix.foil, Materials.Electrum, 64),
        OreDictUnifier.get(OrePrefix.foil, Materials.Electrum, 64),
        OreDictUnifier.get(OrePrefix.foil, Materials.Electrum, 64),
        OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.YttriumBariumCuprate, 7))
        .fluidInputs(Materials.SolderingAlloy.getFluid(576))
        .output(MetaItems.EMITTER_LUV.getStackForm())
        .duration(600)
            .EUt(6000)
            .buildAndRegister();

        AssemblyLineRecipeBuilder.start()
            .researchItem(MetaItems.EMITTER_LUV.getStackForm())
        .researchTime(144000)
            .inputs(OreDictUnifier.get(OrePrefix.frameGt, Materials.HSSE, 1),
                MetaItems.EMITTER_LUV.getStackForm(),
                    MetaItems.EMITTER_IV.getStackForm(2),
                    MetaItems.EMITTER_EV.getStackForm(4),
                    OreDictUnifier.get(OrePrefix.circuit, MarkerMaterials.Tier.Elite, 7),
        OreDictUnifier.get(OrePrefix.foil, Materials.Platinum, 64),
        OreDictUnifier.get(OrePrefix.foil, Materials.Platinum, 64),
        OreDictUnifier.get(OrePrefix.foil, Materials.Platinum, 64),
        OreDictUnifier.get(OrePrefix.cableGtOctal, Materials.VanadiumGallium, 7))
        .fluidInputs(Materials.SolderingAlloy.getFluid(4 * L))
        .output(MetaItems.EMITTER_ZPM.getStackForm())
        .duration(600)
            .EUt(24000)
            .buildAndRegister();

        AssemblyLineRecipeBuilder.start()
            .researchItem(MetaItems.EMITTER_ZPM.getStackForm())
        .researchTime(288000)
            .inputs(OreDictUnifier.get(OrePrefix.frameGt, Materials.Darmstadtium, 1),
                MetaItems.EMITTER_ZPM.getStackForm(),
                    MetaItems.EMITTER_LUV.getStackForm(2),
                    MetaItems.EMITTER_IV.getStackForm(4),
                    OreDictUnifier.get(OrePrefix.circuit, MarkerMaterials.Tier.Master, 7),
        OreDictUnifier.get(OrePrefix.foil, Materials.Osmiridium, 64),
        OreDictUnifier.get(OrePrefix.foil, Materials.Osmiridium, 64),
        OreDictUnifier.get(OrePrefix.foil, Materials.Osmiridium, 64),
        OreDictUnifier.get(OrePrefix.cableGtOctal, Materials.NiobiumTitanium, 7))
        .fluidInputs(Materials.SolderingAlloy.getFluid(4 * L))
        .output(MetaItems.EMITTER_UV.getStackForm())
        .duration(600)
            .EUt(100000)
            .buildAndRegister();

        AssemblyLineRecipeBuilder.start()
            .researchItem(MetaItems.SENSOR_IV.getStackForm())
        .researchTime(144000)
            .inputs(OreDictUnifier.get(OrePrefix.frameGt, Materials.HSSG, 1),
                MetaItems.SENSOR_IV.getStackForm(),
                    MetaItems.SENSOR_EV.getStackForm(2),
                    MetaItems.SENSOR_HV.getStackForm(4),
                    OreDictUnifier.get(OrePrefix.circuit, MarkerMaterials.Tier.Advanced, 7),
        OreDictUnifier.get(OrePrefix.foil, Materials.Electrum, 64),
        OreDictUnifier.get(OrePrefix.foil, Materials.Electrum, 64),
        OreDictUnifier.get(OrePrefix.foil, Materials.Electrum, 64),
        OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.YttriumBariumCuprate, 7))
        .fluidInputs(Materials.SolderingAlloy.getFluid(4 * L))
        .output(MetaItems.SENSOR_LUV.getStackForm())
        .duration(600)
            .EUt(6000)
            .buildAndRegister();

        AssemblyLineRecipeBuilder.start()
            .researchItem(MetaItems.SENSOR_LUV.getStackForm())
        .researchTime(144000)
            .inputs(OreDictUnifier.get(OrePrefix.frameGt, Materials.HSSE, 1),
                MetaItems.SENSOR_LUV.getStackForm(),
                    MetaItems.SENSOR_IV.getStackForm(2),
                    MetaItems.SENSOR_EV.getStackForm(4),
                    OreDictUnifier.get(OrePrefix.circuit, MarkerMaterials.Tier.Elite, 7),
        OreDictUnifier.get(OrePrefix.foil, Materials.Platinum, 64),
        OreDictUnifier.get(OrePrefix.foil, Materials.Platinum, 64),
        OreDictUnifier.get(OrePrefix.foil, Materials.Platinum, 64),
        OreDictUnifier.get(OrePrefix.cableGtOctal, Materials.VanadiumGallium, 7))
        .fluidInputs(Materials.SolderingAlloy.getFluid(4 * L))
        .output(MetaItems.SENSOR_ZPM.getStackForm())
        .duration(600)
            .EUt(24000)
            .buildAndRegister();

        AssemblyLineRecipeBuilder.start()
            .researchItem(MetaItems.SENSOR_ZPM.getStackForm())
        .researchTime(288000)
            .inputs(OreDictUnifier.get(OrePrefix.frameGt, Materials.Darmstadtium, 1),
                MetaItems.SENSOR_ZPM.getStackForm(),
                    MetaItems.SENSOR_LUV.getStackForm(2),
                    MetaItems.SENSOR_IV.getStackForm(4),
                    OreDictUnifier.get(OrePrefix.circuit, MarkerMaterials.Tier.Master, 7),
        OreDictUnifier.get(OrePrefix.foil, Materials.Osmiridium, 64),
        OreDictUnifier.get(OrePrefix.foil, Materials.Osmiridium, 64),
        OreDictUnifier.get(OrePrefix.foil, Materials.Osmiridium, 64),
        OreDictUnifier.get(OrePrefix.cableGtOctal, Materials.NiobiumTitanium, 7))
        .fluidInputs(Materials.SolderingAlloy.getFluid(4 * L))
        .output(MetaItems.SENSOR_UV.getStackForm())
        .duration(600)
            .EUt(100000)
            .buildAndRegister();

        AssemblyLineRecipeBuilder.start()
            .researchItem(MetaItems.FIELD_GENERATOR_IV.getStackForm())
        .researchTime(144000)
            .inputs(OreDictUnifier.get(OrePrefix.frameGt, Materials.HSSG, 1),
                OreDictUnifier.get(OrePrefix.plate, Materials.HSSG, 6),
        MetaItems.QUANTUMSTAR.getStackForm(),
        MetaItems.EMITTER_LUV.getStackForm(4),
        OreDictUnifier.get(OrePrefix.circuit, MarkerMaterials.Tier.Master, 8),
        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
        OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.YttriumBariumCuprate, 8))
        .fluidInputs(Materials.SolderingAlloy.getFluid(4 * L))
        .output(MetaItems.FIELD_GENERATOR_LUV.getStackForm())
        .duration(600)
            .EUt(6000)
            .buildAndRegister();

        AssemblyLineRecipeBuilder.start()
            .researchItem(MetaItems.FIELD_GENERATOR_LUV.getStackForm())
        .researchTime(144000)
            .inputs(OreDictUnifier.get(OrePrefix.frameGt, Materials.HSSE, 1),
                OreDictUnifier.get(OrePrefix.plate, Materials.HSSE, 6),
        MetaItems.QUANTUMSTAR.getStackForm(4),
        MetaItems.EMITTER_ZPM.getStackForm(4),
        OreDictUnifier.get(OrePrefix.circuit, MarkerMaterials.Tier.Master, 16),
        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
        OreDictUnifier.get(OrePrefix.cableGtOctal, Materials.VanadiumGallium, 8))
        .fluidInputs(Materials.SolderingAlloy.getFluid(8 * L))
        .output(MetaItems.FIELD_GENERATOR_ZPM.getStackForm())
        .duration(600)
            .EUt(24000)
            .buildAndRegister();

        AssemblyLineRecipeBuilder.start()
            .researchItem(MetaItems.FIELD_GENERATOR_ZPM.getStackForm())
        .researchTime(288000)
            .inputs(OreDictUnifier.get(OrePrefix.frameGt, Materials.Darmstadtium, 1),
                OreDictUnifier.get(OrePrefix.plate, Materials.Darmstadtium, 6),
        MetaItems.GRAVISTAR.getStackForm(),
        MetaItems.EMITTER_UV.getStackForm(4),
        OreDictUnifier.get(OrePrefix.circuit, MarkerMaterials.Tier.Master, 64),
        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
        OreDictUnifier.get(OrePrefix.cableGtOctal, Materials.NiobiumTitanium, 8))
        .fluidInputs(Materials.SolderingAlloy.getFluid(16 * L))
        .output(MetaItems.FIELD_GENERATOR_UV.getStackForm())
        .duration(600)
            .EUt(100000)
            .buildAndRegister();
}
}
