package gregtech.loaders.recipe;

import com.google.common.base.CaseFormat;
import gregtech.api.recipes.CountableIngredient;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.builders.CokeOvenRecipeBuilder;
import gregtech.api.recipes.builders.PBFRecipeBuilder;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.MarkerMaterials.Tier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.GemMaterial;
import gregtech.api.unification.material.type.IngotMaterial;
import gregtech.api.unification.material.type.Material.MatFlags;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.ItemMaterialInfo;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTLog;
import gregtech.api.util.GTUtility;
import gregtech.common.ConfigHolder;
import gregtech.common.blocks.BlockConcrete.ConcreteVariant;
import gregtech.common.blocks.BlockGranite.GraniteVariant;
import gregtech.common.blocks.BlockMachineCasing.MachineCasingType;
import gregtech.common.blocks.BlockMetalCasing.MetalCasingType;
import gregtech.common.blocks.BlockMineral.MineralVariant;
import gregtech.common.blocks.BlockMultiblockCasing.MultiblockCasingType;
import gregtech.common.blocks.BlockTurbineCasing.TurbineCasingType;
import gregtech.common.blocks.BlockWireCoil.CoilType;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.blocks.StoneBlock;
import gregtech.common.blocks.StoneBlock.ChiselingVariant;
import gregtech.common.blocks.wood.BlockGregLog.LogVariant;
import gregtech.common.items.MetaItems;
import gregtech.common.metatileentities.MetaTileEntities;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static gregtech.api.GTValues.L;
import static gregtech.api.GTValues.M;
import static gregtech.common.items.MetaItems.*;

public class MachineRecipeLoader {

    private static final MaterialStack[][] alloySmelterList = {
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
        initializeWoodRecipes();
        initializeArcRecyclingRecipes();

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
            if (stack[0].material instanceof IngotMaterial) {
                RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder()
                    .duration((int) stack[2].amount * 50).EUt(16)
                    .input(OrePrefix.ingot, stack[0].material, (int) stack[0].amount)
                    .input(OrePrefix.dust, stack[1].material, (int) stack[1].amount)
                    .outputs(OreDictUnifier.get(OrePrefix.ingot, stack[2].material, (int) stack[2].amount))
                    .buildAndRegister();
            }
            if (stack[1].material instanceof IngotMaterial) {
                RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder()
                    .duration((int) stack[2].amount * 50).EUt(16)
                    .input(OrePrefix.dust, stack[0].material, (int) stack[0].amount)
                    .input(OrePrefix.ingot, stack[1].material, (int) stack[1].amount)
                    .outputs(OreDictUnifier.get(OrePrefix.ingot, stack[2].material, (int) stack[2].amount))
                    .buildAndRegister();
            }
            if (stack[0].material instanceof IngotMaterial && stack[1].material instanceof IngotMaterial) {
                RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder()
                    .duration((int) stack[2].amount * 50).EUt(16)
                    .input(OrePrefix.ingot, stack[0].material, (int) stack[0].amount)
                    .input(OrePrefix.ingot, stack[1].material, (int) stack[1].amount)
                    .outputs(OreDictUnifier.get(OrePrefix.ingot, stack[2].material, (int) stack[2].amount))
                    .buildAndRegister();
            }
            RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder()
                .duration((int) stack[2].amount * 50).EUt(16)
                .input(OrePrefix.dust, stack[0].material, (int) stack[0].amount)
                .input(OrePrefix.dust, stack[1].material, (int) stack[1].amount)
                .outputs(OreDictUnifier.get(OrePrefix.ingot, stack[2].material, (int) stack[2].amount))
                .buildAndRegister();
        }

        for (MaterialStack stack : solderingList) {
            IngotMaterial material = (IngotMaterial) stack.material;
            int multiplier = (int) stack.amount;
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(16).EUt(8).input(OrePrefix.plate, Materials.Steel, 1).input(OrePrefix.wireGtSingle, Materials.RedAlloy, 2).fluidInputs(material.getFluid(144 * multiplier / 8)).outputs(MetaItems.CIRCUIT_PRIMITIVE.getStackForm(2)).buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(16).EUt(8).input(OrePrefix.plate, Materials.Plastic, 1).input(OrePrefix.wireGtSingle, Materials.RedAlloy, 1).fluidInputs(material.getFluid(144 * multiplier / 8)).outputs(MetaItems.CIRCUIT_PRIMITIVE.getStackForm(2)).buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(32).EUt(16).inputs(MetaItems.CIRCUIT_BOARD_BASIC.getStackForm(), MetaItems.CIRCUIT_PRIMITIVE.getStackForm(2)).fluidInputs(material.getFluid(144 * multiplier / 4)).outputs(MetaItems.CIRCUIT_BASIC.getStackForm()).buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(32).EUt(16).inputs(MetaItems.CIRCUIT_BASIC.getStackForm(), MetaItems.CIRCUIT_PRIMITIVE.getStackForm(2)).fluidInputs(material.getFluid(144 * multiplier / 4)).outputs(MetaItems.CIRCUIT_GOOD.getStackForm()).buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(32).EUt(64).inputs(MetaItems.CIRCUIT_BOARD_ADVANCED.getStackForm(), MetaItems.CIRCUIT_PARTS_ADVANCED.getStackForm(2)).fluidInputs(material.getFluid(144 * multiplier / 2)).outputs(MetaItems.CIRCUIT_ADVANCED.getStackForm()).buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(32).EUt(64).inputs(MetaItems.CIRCUIT_BOARD_ADVANCED.getStackForm(), MetaItems.CIRCUIT_PARTS_CRYSTAL_CHIP_ELITE.getStackForm()).fluidInputs(material.getFluid(144 * multiplier / 2)).outputs(MetaItems.CIRCUIT_DATA.getStackForm()).buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(32).EUt(256).inputs(MetaItems.CIRCUIT_BOARD_ELITE.getStackForm(), MetaItems.CIRCUIT_DATA.getStackForm(3)).fluidInputs(material.getFluid(144 * multiplier)).outputs(MetaItems.CIRCUIT_ELITE.getStackForm()).buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(32).EUt(256).inputs(MetaItems.CIRCUIT_BOARD_ELITE.getStackForm(), MetaItems.CIRCUIT_PARTS_CRYSTAL_CHIP_MASTER.getStackForm(3)).fluidInputs(material.getFluid(144 * multiplier)).outputs(MetaItems.CIRCUIT_MASTER.getStackForm()).buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(128).EUt(64).inputs(MetaItems.CIRCUIT_DATA.getStackForm()).input(OrePrefix.plate, Materials.Plastic, 2).fluidInputs(material.getFluid(144 * multiplier / 2)).outputs(MetaItems.TOOL_DATA_STICK.getStackForm()).buildAndRegister();

            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(800).EUt(16).inputs(new ItemStack(Blocks.LEVER, 1, OreDictionary.WILDCARD_VALUE)).input(OrePrefix.plate, material, 1).fluidInputs(material.getFluid(144 * multiplier / 2)).outputs(MetaItems.COVER_CONTROLLER.getStackForm()).buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(800).EUt(16).inputs(new ItemStack(Blocks.REDSTONE_TORCH, 1, OreDictionary.WILDCARD_VALUE)).input(OrePrefix.plate, material, 1).fluidInputs(material.getFluid(144 * multiplier / 2)).outputs(MetaItems.COVER_ACTIVITY_DETECTOR.getStackForm()).buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(800).EUt(16).inputs(new ItemStack(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, 1, OreDictionary.WILDCARD_VALUE)).input(OrePrefix.plate, material, 1).fluidInputs(material.getFluid(144 * multiplier / 2)).outputs(MetaItems.COVER_FLUID_DETECTOR.getStackForm()).buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(800).EUt(16).inputs(new ItemStack(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE, 1, OreDictionary.WILDCARD_VALUE)).input(OrePrefix.plate, material, 1).fluidInputs(material.getFluid(144 * multiplier / 2)).outputs(MetaItems.COVER_ITEM_DETECTOR.getStackForm()).buildAndRegister();
        }

        RecipeMaps.COMPRESSOR_RECIPES.recipeBuilder()
            .input(OrePrefix.dust, Materials.Fireclay)
            .outputs(MetaItems.COMPRESSED_FIRECLAY.getStackForm())
            .duration(100).EUt(2)
            .buildAndRegister();

        RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder().input(OrePrefix.foil, Materials.Copper).notConsumable(OrePrefix.craftingLens, MarkerMaterials.Color.Red)
            .outputs(MetaItems.CIRCUIT_PARTS_WIRING_BASIC.getStackForm())
            .duration(64).EUt(30)
            .buildAndRegister();

        RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder().input(OrePrefix.foil, Materials.AnnealedCopper).notConsumable(OrePrefix.craftingLens, MarkerMaterials.Color.Red)
            .outputs(MetaItems.CIRCUIT_PARTS_WIRING_BASIC.getStackForm())
            .duration(64).EUt(30)
            .buildAndRegister();

        RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder().input(OrePrefix.foil, Materials.Gold).notConsumable(OrePrefix.craftingLens, MarkerMaterials.Color.Red)
            .outputs(MetaItems.CIRCUIT_PARTS_WIRING_ADVANCED.getStackForm())
            .duration(64).EUt(120)
            .buildAndRegister();

        RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder().input(OrePrefix.foil, Materials.Electrum).notConsumable(OrePrefix.craftingLens, MarkerMaterials.Color.Red)
            .outputs(MetaItems.CIRCUIT_PARTS_WIRING_ADVANCED.getStackForm())
            .duration(64).EUt(120)
            .buildAndRegister();

        RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder().input(OrePrefix.foil, Materials.Platinum).notConsumable(OrePrefix.craftingLens, MarkerMaterials.Color.Red)
            .outputs(MetaItems.CIRCUIT_PARTS_WIRING_ELITE.getStackForm())
            .duration(64).EUt(480)
            .buildAndRegister();

        RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder().input(OrePrefix.plate, Materials.Olivine).notConsumable(OrePrefix.craftingLens, MarkerMaterials.Color.Lime)
            .outputs(MetaItems.CIRCUIT_PARTS_CRYSTAL_CHIP_ELITE.getStackForm())
            .duration(256).EUt(480)
            .buildAndRegister();

        RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder().input(OrePrefix.plate, Materials.Emerald).notConsumable(OrePrefix.craftingLens, MarkerMaterials.Color.Lime)
            .outputs(MetaItems.CIRCUIT_PARTS_CRYSTAL_CHIP_ELITE.getStackForm())
            .duration(256).EUt(480)
            .buildAndRegister();

        RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder().inputs(MetaItems.LAPOTRON_CRYSTAL.getStackForm()).notConsumable(OrePrefix.craftingLens, MarkerMaterials.Color.Blue)
            .outputs(MetaItems.CIRCUIT_PARTS_CRYSTAL_CHIP_MASTER.getStackForm())
            .duration(256).EUt(480)
            .buildAndRegister();

        RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder().inputs(new ItemStack(Blocks.SANDSTONE, 1, 2)).notConsumable(OrePrefix.craftingLens, MarkerMaterials.Color.White)
            .outputs(new ItemStack(Blocks.SANDSTONE, 1, 1))
            .duration(20).EUt(16)
            .buildAndRegister();

        RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder().inputs(CountableIngredient.from("stone")).notConsumable(OrePrefix.craftingLens, MarkerMaterials.Color.White)
            .outputs(new ItemStack(Blocks.STONEBRICK, 1, 3))
            .duration(50).EUt(16)
            .buildAndRegister();

        RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder().inputs(new ItemStack(Blocks.QUARTZ_BLOCK)).notConsumable(OrePrefix.craftingLens, MarkerMaterials.Color.White)
            .outputs(new ItemStack(Blocks.QUARTZ_BLOCK, 1, 1))
            .duration(50).EUt(16)
            .buildAndRegister();

        PBFRecipeBuilder.start().input(OrePrefix.ingot, Materials.Iron).output(OreDictUnifier.get(OrePrefix.ingot, Materials.Steel)).duration(1500).fuelAmount(2).buildAndRegister();
        PBFRecipeBuilder.start().input(OrePrefix.block, Materials.Iron).output(OreDictUnifier.get(OrePrefix.block, Materials.Steel)).duration(13500).fuelAmount(18).buildAndRegister();
        PBFRecipeBuilder.start().input(OrePrefix.ingot, Materials.WroughtIron).output(OreDictUnifier.get(OrePrefix.ingot, Materials.Steel)).duration(600).fuelAmount(2).buildAndRegister();
        PBFRecipeBuilder.start().input(OrePrefix.block, Materials.WroughtIron).output(OreDictUnifier.get(OrePrefix.block, Materials.Steel)).duration(5600).fuelAmount(18).buildAndRegister();

        CokeOvenRecipeBuilder.start().input(OrePrefix.log, Materials.Wood).output(OreDictUnifier.get(OrePrefix.gem, Materials.Charcoal)).fluidOutput(Materials.Creosote.getFluid(250)).duration(900).buildAndRegister();
        CokeOvenRecipeBuilder.start().input(OrePrefix.gem, Materials.Coal).output(OreDictUnifier.get(OrePrefix.gem, Materials.Coke)).fluidOutput(Materials.Creosote.getFluid(500)).duration(900).buildAndRegister();
        CokeOvenRecipeBuilder.start().input(OrePrefix.block, Materials.Coal).output(OreDictUnifier.get(OrePrefix.block, Materials.Coke)).fluidOutput(Materials.Creosote.getFluid(4500)).duration(8100).buildAndRegister();

        //register seed oil recipes for all seed entries
        List<Tuple<ItemStack, Integer>> seedEntries = GTUtility.getGrassSeedEntries();
        for(Tuple<ItemStack, Integer> seedEntry : seedEntries) {
            RecipeMaps.FLUID_EXTRACTION_RECIPES.recipeBuilder()
                .duration(32).EUt(2)
                .inputs(seedEntry.getFirst())
                .fluidOutputs(Materials.SeedOil.getFluid(5))
                .buildAndRegister();
        }

        RecipeMaps.FLUID_EXTRACTION_RECIPES.recipeBuilder().duration(32).EUt(2)
            .inputs(new ItemStack(Items.MELON_SEEDS, 1, OreDictionary.WILDCARD_VALUE))
            .fluidOutputs(Materials.SeedOil.getFluid(3)).buildAndRegister();

        RecipeMaps.FLUID_EXTRACTION_RECIPES.recipeBuilder().duration(32).EUt(2)
            .inputs(new ItemStack(Items.PUMPKIN_SEEDS, 1, OreDictionary.WILDCARD_VALUE))
            .fluidOutputs(Materials.SeedOil.getFluid(6)).buildAndRegister();

        RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder().duration(100).EUt(16)
            .input(OrePrefix.ingot, Materials.Rubber, 2)
            .input(OrePrefix.wireGtSingle, Materials.Copper, 1)
            .outputs(OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.Copper,1))
            .buildAndRegister();

        for(IngotMaterial cableMaterial : new IngotMaterial[] {Materials.YttriumBariumCuprate, Materials.NiobiumTitanium, Materials.VanadiumGallium}) {
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.wireGtSingle, cableMaterial, 3)
                .input(OrePrefix.plate, Materials.TungstenSteel, 3)
                .inputs(MetaItems.ELECTRIC_PUMP_LV.getStackForm())
                .fluidInputs(Materials.Nitrogen.getFluid(2000))
                .outputs(OreDictUnifier.get(OrePrefix.wireGtSingle, Tier.Superconductor, 3))
                .duration(20).EUt(512)
                .buildAndRegister();
        }

        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(100).EUt(8).inputs(new ItemStack(Blocks.BROWN_MUSHROOM), new ItemStack(Items.SPIDER_EYE)).input(OrePrefix.dust, Materials.Sugar, 1).outputs(new ItemStack(Items.FERMENTED_SPIDER_EYE)).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(16).EUt(16).fluidInputs(Materials.LightFuel.getFluid(5000), Materials.HeavyFuel.getFluid(1000)).fluidOutputs(Materials.Fuel.getFluid(6000)).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(64).EUt(16).input(OrePrefix.dust, Materials.Stone, 1).fluidInputs(Materials.Lubricant.getFluid(20), ModHandler.getWater(1000)).fluidOutputs(Materials.DrillingFluid.getFluid(5000)).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(20).EUt(16).input(OrePrefix.dust, Materials.Clay, 1).input(OrePrefix.dust, Materials.Stone, 3).fluidInputs(Materials.Water.getFluid(500)).fluidOutputs(Materials.Concrete.getFluid(576)).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(12).EUt(4).inputs(MetaBlocks.CONCRETE.getItemVariant(ConcreteVariant.LIGHT_CONCRETE, ChiselingVariant.NORMAL)).fluidInputs(Materials.Water.getFluid(144)).outputs(MetaBlocks.CONCRETE.getItemVariant(ConcreteVariant.DARK_CONCRETE, ChiselingVariant.NORMAL)).buildAndRegister();


        RecipeMaps.MIXER_RECIPES.recipeBuilder()
            .duration(64).EUt(16)
            .fluidInputs(Materials.Water.getFluid(1000))
            .input("sand", 2)
            .input(OrePrefix.dust, Materials.Stone, 6)
            .input(OrePrefix.dust, Materials.Flint)
            .fluidOutputs(Materials.ConstructionFoam.getFluid(1000))
            .buildAndRegister();

        RecipeMaps.MIXER_RECIPES.recipeBuilder()
            .duration(1200).EUt(16)
            .input(OrePrefix.dust, Materials.Ruby, 9)
            .input(OrePrefix.dust, Materials.Redstone, 9)
            .outputs(MetaItems.ENERGIUM_DUST.getStackForm(9))
            .buildAndRegister();

        RecipeMaps.AUTOCLAVE_RECIPES.recipeBuilder()
            .inputs(MetaItems.ENERGIUM_DUST.getStackForm(9))
            .fluidInputs(Materials.Water.getFluid(1800))
            .outputs(MetaItems.ENERGY_CRYSTAL.getStackForm())
            .duration(2000).EUt(120)
            .buildAndRegister();

        RecipeMaps.AUTOCLAVE_RECIPES.recipeBuilder()
            .inputs(MetaItems.ENERGIUM_DUST.getStackForm(9))
            .fluidInputs(ModHandler.getDistilledWater(1800))
            .outputs(MetaItems.ENERGY_CRYSTAL.getStackForm())
            .duration(1500).EUt(120)
            .buildAndRegister();

        //decorative blocks: normal variant -> brick variant
        registerBrickRecipe(MetaBlocks.CONCRETE, ConcreteVariant.LIGHT_CONCRETE, ConcreteVariant.LIGHT_BRICKS);
        registerBrickRecipe(MetaBlocks.CONCRETE, ConcreteVariant.DARK_CONCRETE, ConcreteVariant.DARK_CONCRETE);
        registerBrickRecipe(MetaBlocks.GRANITE, GraniteVariant.BLACK_GRANITE, GraniteVariant.BLACK_GRANITE_BRICKS);
        registerBrickRecipe(MetaBlocks.GRANITE, GraniteVariant.RED_GRANITE, GraniteVariant.RED_GRANITE_BRICKS);
        registerBrickRecipe(MetaBlocks.MINERAL, MineralVariant.MARBLE, MineralVariant.MARBLE_BRICKS);
        registerBrickRecipe(MetaBlocks.MINERAL, MineralVariant.BASALT, MineralVariant.BASALT_BRICKS);

        //decorative blocks: normal chiseling -> different chiseling
        registerChiselingRecipes(MetaBlocks.CONCRETE);
        registerChiselingRecipes(MetaBlocks.GRANITE);
        registerChiselingRecipes(MetaBlocks.MINERAL);
       
        RecipeMaps.FLUID_CANNER_RECIPES.recipeBuilder().duration(100).EUt(30).inputs(MetaItems.BATTERY_HULL_LV.getStackForm()).fluidInputs(Materials.Mercury.getFluid(1000)).outputs(MetaItems.BATTERY_SU_LV_MERCURY.getChargedStack(Long.MAX_VALUE)).buildAndRegister();
        RecipeMaps.FLUID_CANNER_RECIPES.recipeBuilder().duration(200).EUt(30).inputs(MetaItems.BATTERY_HULL_MV.getStackForm()).fluidInputs(Materials.Mercury.getFluid(4000)).outputs(MetaItems.BATTERY_SU_MV_MERCURY.getChargedStack(Long.MAX_VALUE)).buildAndRegister();
        RecipeMaps.FLUID_CANNER_RECIPES.recipeBuilder().duration(400).EUt(30).inputs(MetaItems.BATTERY_HULL_HV.getStackForm()).fluidInputs(Materials.Mercury.getFluid(16000)).outputs(MetaItems.BATTERY_SU_HV_MERCURY.getChargedStack(Long.MAX_VALUE)).buildAndRegister();
        RecipeMaps.FLUID_CANNER_RECIPES.recipeBuilder().duration(100).EUt(30).inputs(MetaItems.BATTERY_HULL_LV.getStackForm()).fluidInputs(Materials.SulfuricAcid.getFluid(1000)).outputs(MetaItems.BATTERY_SU_LV_SULFURIC_ACID.getChargedStack(Long.MAX_VALUE)).buildAndRegister();
        RecipeMaps.FLUID_CANNER_RECIPES.recipeBuilder().duration(200).EUt(30).inputs(MetaItems.BATTERY_HULL_MV.getStackForm()).fluidInputs(Materials.SulfuricAcid.getFluid(4000)).outputs(MetaItems.BATTERY_SU_MV_SULFURIC_ACID.getChargedStack(Long.MAX_VALUE)).buildAndRegister();
        RecipeMaps.FLUID_CANNER_RECIPES.recipeBuilder().duration(400).EUt(30).inputs(MetaItems.BATTERY_HULL_HV.getStackForm()).fluidInputs(Materials.SulfuricAcid.getFluid(16000)).outputs(MetaItems.BATTERY_SU_HV_SULFURIC_ACID.getChargedStack(Long.MAX_VALUE)).buildAndRegister();

        RecipeMaps.FLUID_SOLIDFICATION_RECIPES.recipeBuilder().duration(128).EUt(4).notConsumable(MetaItems.SHAPE_MOLD_BALL.getStackForm()).fluidInputs(Materials.Water.getFluid(250)).outputs(new ItemStack(Items.SNOWBALL)).buildAndRegister();
        RecipeMaps.FLUID_SOLIDFICATION_RECIPES.recipeBuilder().duration(128).EUt(4).notConsumable(MetaItems.SHAPE_MOLD_BALL.getStackForm()).fluidInputs(ModHandler.getDistilledWater(250)).outputs(new ItemStack(Items.SNOWBALL)).buildAndRegister();
        RecipeMaps.FLUID_SOLIDFICATION_RECIPES.recipeBuilder().duration(512).EUt(4).notConsumable(MetaItems.SHAPE_MOLD_BLOCK.getStackForm()).fluidInputs(Materials.Water.getFluid(1000)).outputs(new ItemStack(Blocks.SNOW)).buildAndRegister();
        RecipeMaps.FLUID_SOLIDFICATION_RECIPES.recipeBuilder().duration(512).EUt(4).notConsumable(MetaItems.SHAPE_MOLD_BLOCK.getStackForm()).fluidInputs(ModHandler.getDistilledWater(1000)).outputs(new ItemStack(Blocks.SNOW)).buildAndRegister();
        RecipeMaps.FLUID_SOLIDFICATION_RECIPES.recipeBuilder().duration(1024).EUt(16).notConsumable(MetaItems.SHAPE_MOLD_BLOCK.getStackForm()).fluidInputs(Materials.Lava.getFluid(1000)).outputs(new ItemStack(Blocks.OBSIDIAN)).buildAndRegister();

        RecipeMaps.FLUID_SOLIDFICATION_RECIPES.recipeBuilder().duration(12).EUt(4).notConsumable(MetaItems.SHAPE_MOLD_BLOCK.getStackForm()).fluidInputs(Materials.Glowstone.getFluid(576)).outputs(new ItemStack(Blocks.GLOWSTONE)).buildAndRegister();
        RecipeMaps.FLUID_SOLIDFICATION_RECIPES.recipeBuilder().duration(12).EUt(4).notConsumable(MetaItems.SHAPE_MOLD_BLOCK.getStackForm()).fluidInputs(Materials.Glass.getFluid(144)).outputs(new ItemStack(Blocks.GLASS)).buildAndRegister();
        RecipeMaps.FLUID_SOLIDFICATION_RECIPES.recipeBuilder().duration(12).EUt(4).notConsumable(MetaItems.SHAPE_MOLD_PLATE.getStackForm()).fluidInputs(Materials.Glass.getFluid(144)).outputs(OreDictUnifier.get(OrePrefix.plate,Materials.Glass,1)).buildAndRegister();
        RecipeMaps.FLUID_SOLIDFICATION_RECIPES.recipeBuilder().duration(128).EUt(16).notConsumable(MetaItems.SHAPE_MOLD_ANVIL.getStackForm()).fluidInputs(Materials.Iron.getFluid(31 * L)).outputs(new ItemStack(Blocks.ANVIL)).buildAndRegister();
        RecipeMaps.FLUID_SOLIDFICATION_RECIPES.recipeBuilder().duration(128).EUt(16).notConsumable(MetaItems.SHAPE_MOLD_ANVIL.getStackForm()).fluidInputs(Materials.WroughtIron.getFluid(31 * L)).outputs(new ItemStack(Blocks.ANVIL)).buildAndRegister();

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
        RecipeMaps.FLUID_EXTRACTION_RECIPES.recipeBuilder().duration(64).EUt(64).input(OrePrefix.dust, Materials.Monazite, 1).fluidOutputs(Materials.Helium.getFluid(200)).buildAndRegister();
        RecipeMaps.FLUID_EXTRACTION_RECIPES.recipeBuilder().duration(32).EUt(4).inputs(new ItemStack(Items.SNOWBALL)).fluidOutputs(Materials.Water.getFluid(250)).buildAndRegister();
        RecipeMaps.FLUID_EXTRACTION_RECIPES.recipeBuilder().duration(128).EUt(4).inputs(new ItemStack(Blocks.SNOW)).fluidOutputs(Materials.Water.getFluid(1000)).buildAndRegister();
        RecipeMaps.AUTOCLAVE_RECIPES.recipeBuilder().duration(72000).EUt(480).input(OrePrefix.dust, Materials.NetherStar, 1).fluidInputs(Materials.UUMatter.getFluid(576)).chancedOutput(OreDictUnifier.get(OrePrefix.gem,Materials.NetherStar,1), 3333).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(1800).EUt(30).input(OrePrefix.dust, Materials.EnderPearl, 1).input(OrePrefix.circuit, MarkerMaterials.Tier.Basic, 4).fluidInputs(Materials.Osmium.getFluid(L * 2)).outputs(MetaItems.FIELD_GENERATOR_LV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(1800).EUt(120).input(OrePrefix.dust, Materials.EnderEye, 1).input(OrePrefix.circuit, MarkerMaterials.Tier.Good, 4).fluidInputs(Materials.Osmium.getFluid(576)).outputs(MetaItems.FIELD_GENERATOR_MV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(1800).EUt(480).inputs(MetaItems.QUANTUM_EYE.getStackForm()).input(OrePrefix.circuit, MarkerMaterials.Tier.Advanced, 4).fluidInputs(Materials.Osmium.getFluid(1152)).outputs(MetaItems.FIELD_GENERATOR_HV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(1800).EUt(1920).input(OrePrefix.dust, Materials.NetherStar, 1).input(OrePrefix.circuit, MarkerMaterials.Tier.Elite, 4).fluidInputs(Materials.Osmium.getFluid(2304)).outputs(MetaItems.FIELD_GENERATOR_EV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(1800).EUt(7680).inputs(MetaItems.QUANTUM_STAR.getStackForm()).input(OrePrefix.circuit, MarkerMaterials.Tier.Master, 4).fluidInputs(Materials.Osmium.getFluid(4608)).outputs(MetaItems.FIELD_GENERATOR_IV.getStackForm()).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(480).EUt(240).input(OrePrefix.dust, Materials.Graphite, 8).input(OrePrefix.foil, Materials.Silicon, 1).fluidInputs(Materials.Glue.getFluid(250)).outputs(OreDictUnifier.get(OrePrefix.dustSmall,Materials.Graphene,1)).buildAndRegister();

        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(1600).EUt(8).fluidInputs(Materials.Air.getFluid(10000)).fluidOutputs(Materials.Nitrogen.getFluid(3900), Materials.Oxygen.getFluid(1000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(950).EUt(30).fluidInputs(Materials.Water.getFluid(2000), Materials.NitrogenDioxide.getFluid(4000), Materials.Oxygen.getFluid(1000)).fluidOutputs(Materials.NitricAcid.getFluid(4000)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(OrePrefix.plate, Materials.Silicon, 1).input(OrePrefix.plate, Materials.Plastic, 1).outputs(MetaItems.EMPTY_BOARD_BASIC.getStackForm()).duration(32).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(256).input(OrePrefix.plate, Materials.Silicon, 2).input(OrePrefix.plate, Materials.Polytetrafluoroethylene, 1).outputs(MetaItems.EMPTY_BOARD_ELITE.getStackForm()).duration(32).buildAndRegister();

        RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder().duration(32).EUt(16).inputs(MetaItems.EMPTY_BOARD_BASIC.getStackForm(), MetaItems.CIRCUIT_PARTS_WIRING_BASIC.getStackForm(4)).outputs(MetaItems.CIRCUIT_BOARD_BASIC.getStackForm()).buildAndRegister();
        RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder().duration(32).EUt(64).inputs(MetaItems.EMPTY_BOARD_BASIC.getStackForm(), MetaItems.CIRCUIT_PARTS_WIRING_ADVANCED.getStackForm(4)).outputs(MetaItems.CIRCUIT_BOARD_ADVANCED.getStackForm()).buildAndRegister();
        RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder().duration(32).EUt(256).inputs(MetaItems.EMPTY_BOARD_ELITE.getStackForm(), MetaItems.CIRCUIT_PARTS_WIRING_ELITE.getStackForm(4)).outputs(MetaItems.CIRCUIT_BOARD_ELITE.getStackForm()).buildAndRegister();
        RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder().duration(32).EUt(64).input(OrePrefix.plate, Materials.Lapis, 1).input(OrePrefix.dust, Materials.Glowstone, 1).outputs(MetaItems.CIRCUIT_PARTS_ADVANCED.getStackForm(2)).buildAndRegister();
        RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder().duration(32).EUt(64).input(OrePrefix.plate, Materials.Lazurite, 1).input(OrePrefix.dust, Materials.Glowstone, 1).outputs(MetaItems.CIRCUIT_PARTS_ADVANCED.getStackForm(2)).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(800).EUt(1).inputs(new ItemStack(Blocks.REDSTONE_TORCH, 2, OreDictionary.WILDCARD_VALUE)).input(OrePrefix.dust, Materials.Redstone, 1).fluidInputs(Materials.Concrete.getFluid(144)).outputs(new ItemStack(Items.REPEATER)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(100).EUt(8).inputs(new ItemStack(Items.LEATHER, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Items.LEAD, 1, OreDictionary.WILDCARD_VALUE)).fluidInputs(Materials.Glue.getFluid(50)).outputs(new ItemStack(Items.NAME_TAG)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(100).EUt(8).inputs(new ItemStack(Items.COMPASS, 1, OreDictionary.WILDCARD_VALUE)).input(OrePrefix.plate, Materials.Paper, 8).outputs(new ItemStack(Items.MAP)).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(100).EUt(4).input(OrePrefix.dust, Materials.Tantalum, 1).input(OrePrefix.plate, Materials.Manganese, 1).fluidInputs(Materials.Plastic.getFluid(144)).outputs(MetaItems.BATTERY_RE_ULV_TANTALUM.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(512).EUt(256).inputs(MetaItems.CIRCUIT_ELITE.getStackForm(2), MetaItems.CIRCUIT_PARTS_CRYSTAL_CHIP_ELITE.getStackForm(18)).outputs(MetaItems.TOOL_DATA_ORB.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(512).EUt(1024).inputs(MetaItems.CIRCUIT_MASTER.getStackForm(2), MetaItems.CIRCUIT_PARTS_CRYSTAL_CHIP_MASTER.getStackForm(18)).outputs(MetaItems.ENERGY_LAPOTRONIC_ORB.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(2048).EUt(4096).inputs(MetaItems.ENERGY_LAPOTRONIC_ORB.getStackForm(8)).input(OrePrefix.plate, Materials.Europium, 4).outputs(MetaItems.ENERGY_LAPOTRONIC_ORB2.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(32768).EUt(4096).inputs(MetaItems.ENERGY_LAPOTRONIC_ORB2.getStackForm(8)).input(OrePrefix.plate, Materials.Darmstadtium, 16).outputs(MetaItems.ZPM2.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(400).EUt(1).inputs(new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE)).input(OrePrefix.stick, Materials.Wood, 1).fluidInputs(Materials.Creosote.getFluid(1000)).outputs(new ItemStack(Blocks.TORCH, 6)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(100).EUt(4).inputs(new ItemStack(Blocks.PISTON, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Items.SLIME_BALL, 1, OreDictionary.WILDCARD_VALUE)).outputs(new ItemStack(Blocks.STICKY_PISTON)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(100).EUt(4).inputs(new ItemStack(Blocks.PISTON, 1, OreDictionary.WILDCARD_VALUE)).fluidInputs(Materials.Glue.getFluid(100)).circuitMeta(1).outputs(new ItemStack(Blocks.STICKY_PISTON)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(32).EUt(8).inputs(new ItemStack(Items.LEATHER, 1, OreDictionary.WILDCARD_VALUE)).input(OrePrefix.plate, Materials.Paper, 3).fluidInputs(Materials.Glue.getFluid(20)).outputs(new ItemStack(Items.BOOK)).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .input(OrePrefix.plate, Materials.Rubber, 2)
            .input(OrePrefix.plate, Materials.Aluminium, 2)
            .outputs(MetaItems.COVER_SHUTTER.getStackForm(4))
            .EUt(16).duration(800)
            .buildAndRegister();

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
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(640).fluidInputs(Materials.Naphtha.getFluid(L * 2), Materials.Air.getFluid(2000)).fluidOutputs(Materials.Plastic.getFluid(144)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(640).input(OrePrefix.dustTiny, Materials.Titanium, 1).fluidInputs(Materials.Naphtha.getFluid(1296), Materials.Oxygen.getFluid(16000)).fluidOutputs(Materials.Plastic.getFluid(1296)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(640).input(OrePrefix.dust, Materials.Saltpeter, 1).fluidInputs(Materials.Naphtha.getFluid(576)).outputs(OreDictUnifier.get(OrePrefix.dustTiny,Materials.Potassium,1)).fluidOutputs(Materials.Polycaprolactam.getFluid(1296)).buildAndRegister();
        RecipeMaps.WIREMILL_RECIPES.recipeBuilder().duration(80).EUt(48).input(OrePrefix.ingot, Materials.Polycaprolactam, 1).outputs(new ItemStack(Items.STRING, 32)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(480).EUt(30).input(OrePrefix.dust, Materials.Carbon, 1).fluidInputs(Materials.LPG.getFluid(432), Materials.Chlorine.getFluid(1000)).fluidOutputs(Materials.Epichlorhydrin.getFluid(432)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(240).EUt(256).fluidInputs(Materials.Epichlorhydrin.getFluid(432), Materials.Naphtha.getFluid(3000), Materials.Fluorine.getFluid(1000)).fluidOutputs(Materials.Polytetrafluoroethylene.getFluid(432)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(240).EUt(96).input(OrePrefix.dust, Materials.Silicon, 1).fluidInputs(Materials.Epichlorhydrin.getFluid(144)).fluidOutputs(Materials.Silicone.getFluid(144)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(240).EUt(30).fluidInputs(Materials.Epichlorhydrin.getFluid(144), Materials.Naphtha.getFluid(3000), Materials.NitrogenDioxide.getFluid(1000)).fluidOutputs(Materials.Epoxid.getFluid(L * 2)).buildAndRegister();

        RecipeMaps.CRACKING_RECIPES.recipeBuilder().duration(16).EUt(320).fluidInputs(Materials.LightFuel.getFluid(128)).fluidOutputs(Materials.CrackedLightFuel.getFluid(192)).buildAndRegister();
        RecipeMaps.CRACKING_RECIPES.recipeBuilder().duration(16).EUt(320).fluidInputs(Materials.HeavyFuel.getFluid(128)).fluidOutputs(Materials.CrackedHeavyFuel.getFluid(192)).buildAndRegister();

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

        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration((int) Math.max(Materials.TungstenSteel.getAverageMass() / 80,1) * Materials.TungstenSteel.blastFurnaceTemperature).EUt(480).input(OrePrefix.ingot, Materials.Tungsten, 1).input(OrePrefix.ingot, Materials.Steel, 1).outputs(OreDictUnifier.get(OrePrefix.ingotHot, Materials.TungstenSteel,2), OreDictUnifier.get(OrePrefix.dustSmall,Materials.DarkAsh,1)).blastFurnaceTemp(Materials.TungstenSteel.blastFurnaceTemperature).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration((int) Math.max(Materials.TungstenCarbide.getAverageMass() / 40,1) * Materials.TungstenCarbide.blastFurnaceTemperature).EUt(480).input(OrePrefix.ingot, Materials.Tungsten, 1).input(OrePrefix.dust, Materials.Carbon, 1).outputs(OreDictUnifier.get(OrePrefix.ingotHot, Materials.TungstenCarbide,1), OreDictUnifier.get(OrePrefix.dustSmall,Materials.Ash,2)).blastFurnaceTemp(Materials.TungstenCarbide.blastFurnaceTemperature).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration((int) Math.max(Materials.VanadiumGallium.getAverageMass() / 40,1) * Materials.VanadiumGallium.blastFurnaceTemperature).EUt(480).input(OrePrefix.ingot, Materials.Vanadium, 3).input(OrePrefix.ingot, Materials.Gallium, 1).outputs(OreDictUnifier.get(OrePrefix.ingotHot, Materials.VanadiumGallium,4), OreDictUnifier.get(OrePrefix.dustSmall,Materials.DarkAsh,2)).blastFurnaceTemp(Materials.VanadiumGallium.blastFurnaceTemperature).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration((int) Math.max(Materials.NiobiumTitanium.getAverageMass() / 80,1) * Materials.NiobiumTitanium.blastFurnaceTemperature).EUt(480).input(OrePrefix.ingot, Materials.Niobium, 1).input(OrePrefix.ingot, Materials.Titanium, 1).outputs(OreDictUnifier.get(OrePrefix.ingotHot, Materials.NiobiumTitanium,2), OreDictUnifier.get(OrePrefix.dustSmall,Materials.DarkAsh,1)).blastFurnaceTemp(Materials.NiobiumTitanium.blastFurnaceTemperature).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration((int) Math.max(Materials.Nichrome.getAverageMass() / 32,1) * Materials.Nichrome.blastFurnaceTemperature).EUt(480).input(OrePrefix.ingot, Materials.Nickel, 4).input(OrePrefix.ingot, Materials.Chrome, 1).outputs(OreDictUnifier.get(OrePrefix.ingotHot, Materials.Nichrome,5), OreDictUnifier.get(OrePrefix.dustSmall,Materials.DarkAsh,2)).blastFurnaceTemp(Materials.Nichrome.blastFurnaceTemperature).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(400).EUt(100).input(OrePrefix.dust, Materials.Ruby, 1).outputs(OreDictUnifier.get(OrePrefix.nugget,Materials.Aluminium,3), OreDictUnifier.get(OrePrefix.dustTiny,Materials.DarkAsh,1)).blastFurnaceTemp(1200).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(320).EUt(100).input(OrePrefix.gem, Materials.Ruby, 1).outputs(OreDictUnifier.get(OrePrefix.nugget,Materials.Aluminium,3), OreDictUnifier.get(OrePrefix.dustTiny,Materials.DarkAsh,1)).blastFurnaceTemp(1200).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(400).EUt(100).input(OrePrefix.dust, Materials.GreenSapphire, 1).outputs(OreDictUnifier.get(OrePrefix.nugget,Materials.Aluminium,3), OreDictUnifier.get(OrePrefix.dustTiny,Materials.DarkAsh,1)).blastFurnaceTemp(1200).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(320).EUt(100).input(OrePrefix.gem, Materials.GreenSapphire, 1).outputs(OreDictUnifier.get(OrePrefix.nugget,Materials.Aluminium,3), OreDictUnifier.get(OrePrefix.dustTiny,Materials.DarkAsh,1)).blastFurnaceTemp(1200).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(400).EUt(100).input(OrePrefix.dust, Materials.Sapphire, 1).outputs(OreDictUnifier.get(OrePrefix.nugget,Materials.Aluminium,3)).blastFurnaceTemp(1200).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(320).EUt(100).input(OrePrefix.gem, Materials.Sapphire, 1).outputs(OreDictUnifier.get(OrePrefix.nugget,Materials.Aluminium,3)).blastFurnaceTemp(1200).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(800).EUt(500).input(OrePrefix.dust, Materials.Ilmenite, 1).input(OrePrefix.dust, Materials.Carbon, 1).outputs(OreDictUnifier.get(OrePrefix.nugget,Materials.WroughtIron,4), OreDictUnifier.get(OrePrefix.dustTiny,Materials.Rutile,4)).blastFurnaceTemp(1700).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(800).EUt(480).input(OrePrefix.dust, Materials.Magnesium, 2).fluidInputs(Materials.TitaniumTetrachloride.getFluid(1000)).outputs(OreDictUnifier.get(OrePrefix.ingotHot,Materials.Titanium,1), OreDictUnifier.get(OrePrefix.dust,Materials.MagnesiumChloride,2)).blastFurnaceTemp(Materials.Titanium.blastFurnaceTemperature + 200).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(400).EUt(500).input(OrePrefix.dust, Materials.Galena, 1).fluidInputs(Materials.Oxygen.getFluid(2000)).outputs(OreDictUnifier.get(OrePrefix.nugget,Materials.Silver,4), OreDictUnifier.get(OrePrefix.nugget,Materials.Lead,4)).blastFurnaceTemp(1500).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(400).EUt(500).input(OrePrefix.dust, Materials.Magnetite, 1).fluidInputs(Materials.Oxygen.getFluid(2000)).outputs(OreDictUnifier.get(OrePrefix.nugget,Materials.WroughtIron,4), OreDictUnifier.get(OrePrefix.dustSmall,Materials.DarkAsh,1)).blastFurnaceTemp(1000).buildAndRegister();
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
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(16).EUt(64).fluidInputs(Materials.Water.getFluid(L * 2)).fluidOutputs(ModHandler.getDistilledWater(260)).buildAndRegister();
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(32).EUt(64).fluidInputs(Materials.OilLight.getFluid(25)).fluidOutputs(Materials.SulfuricGas.getFluid(80), Materials.SulfuricNaphtha.getFluid(15), Materials.SulfuricLightFuel.getFluid(20), Materials.SulfuricHeavyFuel.getFluid(10)).buildAndRegister();
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(32).EUt(64).fluidInputs(Materials.Oil.getFluid(25)).fluidOutputs(Materials.SulfuricGas.getFluid(30), Materials.SulfuricNaphtha.getFluid(20), Materials.SulfuricLightFuel.getFluid(50), Materials.SulfuricHeavyFuel.getFluid(15)).buildAndRegister();
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(32).EUt(192).fluidInputs(Materials.OilHeavy.getFluid(25)).fluidOutputs(Materials.SulfuricGas.getFluid(15),Materials.SulfuricNaphtha.getFluid(15), Materials.SulfuricLightFuel.getFluid(45), Materials.SulfuricHeavyFuel.getFluid(250)).buildAndRegister();
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(32).EUt(64).fluidInputs(Materials.OilMedium.getFluid(25)).fluidOutputs(Materials.SulfuricGas.getFluid(15), Materials.SulfuricNaphtha.getFluid(20), Materials.SulfuricLightFuel.getFluid(50), Materials.SulfuricHeavyFuel.getFluid(15)).buildAndRegister();
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(16).EUt(64).fluidInputs(Materials.CrackedLightFuel.getFluid(25)).fluidOutputs(Materials.Gas.getFluid(120), Materials.Naphtha.getFluid(15), Materials.HeavyFuel.getFluid(10), Materials.Toluene.getFluid(10)).buildAndRegister();
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(16).EUt(64).fluidInputs(Materials.CrackedHeavyFuel.getFluid(25)).outputs(OreDictUnifier.get(OrePrefix.dustTiny,Materials.HydratedCoal, 1)).fluidOutputs(Materials.Gas.getFluid(40), Materials.Naphtha.getFluid(5), Materials.LightFuel.getFluid(40), Materials.Toluene.getFluid(30), Materials.Lubricant.getFluid(5)).buildAndRegister();


        RecipeMaps.ELECTROLYZER_RECIPES.recipeBuilder().duration(1500).EUt(30).fluidInputs(Materials.Water.getFluid(3000)).fluidOutputs(Materials.Hydrogen.getFluid(2000), Materials.Oxygen.getFluid(1000)).buildAndRegister();
        RecipeMaps.ELECTROLYZER_RECIPES.recipeBuilder().duration(1500).EUt(30).fluidInputs(ModHandler.getDistilledWater(3000)).fluidOutputs(Materials.Hydrogen.getFluid(2000), Materials.Oxygen.getFluid(1000)).buildAndRegister();
        RecipeMaps.ELECTROLYZER_RECIPES.recipeBuilder().inputs(new ItemStack(Items.DYE, 3)).outputs(OreDictUnifier.get(OrePrefix.dust,Materials.Calcium,1)).duration(96).EUt(26).buildAndRegister();
        RecipeMaps.ELECTROLYZER_RECIPES.recipeBuilder().inputs(new ItemStack(Blocks.SAND, 8)).outputs(OreDictUnifier.get(OrePrefix.dust,Materials.SiliconDioxide,1)).duration(500).EUt(25).buildAndRegister();
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

        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(500).EUt(480).input(OrePrefix.dust, Materials.Rutile, 1).input(OrePrefix.dust, Materials.Carbon, 3).fluidInputs(Materials.Chlorine.getFluid(2000)).outputs(OreDictUnifier.get(OrePrefix.dust,Materials.Ash,1)).fluidOutputs(Materials.TitaniumTetrachloride.getFluid(1000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(300).EUt(240).input(OrePrefix.dust, Materials.Sodium, 1).input(OrePrefix.dust, Materials.MagnesiumChloride, 2).outputs(OreDictUnifier.get(OrePrefix.dustSmall,Materials.Magnesium,6)).fluidOutputs(Materials.Chlorine.getFluid(1500)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(600).EUt(16).input(OrePrefix.dust, Materials.RawRubber, 9).input(OrePrefix.dust, Materials.Sulfur, 1).fluidOutputs(Materials.Rubber.getFluid(1296)).buildAndRegister();

        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(50).inputs(new ItemStack(Items.MELON, 1, OreDictionary.WILDCARD_VALUE)).input(OrePrefix.nugget, Materials.Gold, 8).outputs(new ItemStack(Items.SPECKLED_MELON)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(50).inputs(new ItemStack(Items.CARROT, 1, OreDictionary.WILDCARD_VALUE)).input(OrePrefix.nugget, Materials.Gold, 8).outputs(new ItemStack(Items.GOLDEN_CARROT)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(50).inputs(new ItemStack(Items.APPLE, 1, OreDictionary.WILDCARD_VALUE)).input(OrePrefix.ingot, Materials.Gold, 8).outputs(new ItemStack(Items.GOLDEN_APPLE)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(50).inputs(new ItemStack(Items.APPLE, 1, OreDictionary.WILDCARD_VALUE)).input(OrePrefix.block, Materials.Gold, 8).outputs(new ItemStack(Items.GOLDEN_APPLE, 1, 1)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(50).input(OrePrefix.dust, Materials.Blaze, 1).input(OrePrefix.gem, Materials.EnderPearl, 1).outputs(OreDictUnifier.get(OrePrefix.gem,Materials.EnderEye,1)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(50).inputs(new ItemStack(Items.SLIME_BALL, 1, OreDictionary.WILDCARD_VALUE)).input(OrePrefix.dust, Materials.Blaze, 1).outputs(new ItemStack(Items.MAGMA_CREAM)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(12000).EUt(8).input(OrePrefix.ingot, Materials.Plutonium, 6).outputs(OreDictUnifier.get(OrePrefix.dust,Materials.Plutonium,6)).fluidOutputs(Materials.Radon.getFluid(100)).buildAndRegister();

        RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(480).EUt(384).input(OrePrefix.gem, Materials.EnderEye, 1).fluidInputs(Materials.Radon.getFluid(250)).outputs(MetaItems.QUANTUM_EYE.getStackForm()).buildAndRegister();
        RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(1920).EUt(384).input(OrePrefix.gem, Materials.NetherStar, 1).fluidInputs(Materials.Radon.getFluid(1250)).outputs(MetaItems.QUANTUM_STAR.getStackForm()).buildAndRegister();

        RecipeMaps.AUTOCLAVE_RECIPES.recipeBuilder().duration(480).EUt(7680).input(OrePrefix.gem, Materials.NetherStar, 1).fluidInputs(Materials.Darmstadtium.getFluid(L * 2)).outputs(MetaItems.GRAVI_STAR.getStackForm()).buildAndRegister();

        RecipeMaps.BENDER_RECIPES.recipeBuilder().duration(800).EUt(4).input(OrePrefix.plate, Materials.Iron, 12).circuitMeta(1).outputs(new ItemStack(Items.BUCKET, 4)).buildAndRegister();
        RecipeMaps.BENDER_RECIPES.recipeBuilder().duration(800).EUt(4).input(OrePrefix.plate, Materials.WroughtIron, 12).circuitMeta(1).outputs(new ItemStack(Items.BUCKET, 4)).buildAndRegister();

        RecipeMaps.VACUUM_RECIPES.recipeBuilder().duration(50).fluidInputs(Materials.Water.getFluid(1000)).fluidOutputs(Materials.Ice.getFluid(1000)).buildAndRegister();
        RecipeMaps.VACUUM_RECIPES.recipeBuilder().duration(400).fluidInputs(Materials.Air.getFluid(4000)).fluidOutputs(Materials.LiquidAir.getFluid(4000)).buildAndRegister();

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

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(1).inputs(new ItemStack(Items.COAL, 1, OreDictionary.WILDCARD_VALUE)).input(OrePrefix.stick, Materials.Wood, 1).outputs(new ItemStack(Blocks.TORCH,4)).duration(400).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(4).input(OrePrefix.plate, Materials.Gold, 2).outputs(new ItemStack(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE,1)).circuitMeta(2).duration(200).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(4).input(OrePrefix.plate, Materials.Iron, 2).outputs(new ItemStack(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE,1)).circuitMeta(2).duration(200).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(4).input(OrePrefix.plate, Materials.Iron, 6).outputs(new ItemStack(Items.IRON_DOOR,3)).circuitMeta(6).duration(600).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(4).input(OrePrefix.plate, Materials.Iron, 7).outputs(new ItemStack(Items.CAULDRON,1)).circuitMeta(7).duration(700).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(4).input(OrePrefix.stick, Materials.Iron, 3).outputs(new ItemStack(Blocks.IRON_BARS,4)).circuitMeta(3).duration(300).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(4).input(OrePrefix.plate, Materials.WroughtIron, 2).outputs(new ItemStack(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE,1)).circuitMeta(2).duration(200).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(4).input(OrePrefix.plate, Materials.WroughtIron, 6).outputs(new ItemStack(Items.IRON_DOOR,3)).circuitMeta(6).duration(600).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(4).input(OrePrefix.plate, Materials.WroughtIron, 7).outputs(new ItemStack(Items.CAULDRON,1)).circuitMeta(7).duration(700).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(4).input(OrePrefix.stick, Materials.WroughtIron, 3).outputs(new ItemStack(Blocks.IRON_BARS,4)).circuitMeta(3).duration(300).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(4).input(OrePrefix.stick, Materials.Wood, 3).outputs(new ItemStack(Blocks.OAK_FENCE,1)).circuitMeta(3).duration(300).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(4).input(OrePrefix.stick, Materials.Wood, 2).input(OrePrefix.ring, Materials.Iron, 2).outputs(new ItemStack(Blocks.TRIPWIRE_HOOK,1)).duration(400).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(4).input(OrePrefix.stick, Materials.Wood, 2).input(OrePrefix.ring, Materials.WroughtIron, 2).outputs(new ItemStack(Blocks.TRIPWIRE_HOOK,1)).duration(400).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(4).inputs(new ItemStack(Items.STRING, 3, OreDictionary.WILDCARD_VALUE)).input(OrePrefix.stick, Materials.Wood, 3).outputs(new ItemStack(Items.BOW,1)).duration(400).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(4).inputs(new ItemStack(Blocks.STONE)).outputs(new ItemStack(Blocks.STONEBRICK,1,0)).circuitMeta(4).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(4).inputs(new ItemStack(Blocks.SANDSTONE)).outputs(new ItemStack(Blocks.SANDSTONE,1,2)).circuitMeta(1).duration(50).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(4).inputs(new ItemStack(Blocks.SANDSTONE, 1, 1)).outputs(new ItemStack(Blocks.SANDSTONE,1,0)).circuitMeta(1).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(4).inputs(new ItemStack(Blocks.SANDSTONE, 1, 2)).outputs(new ItemStack(Blocks.SANDSTONE,1,0)).circuitMeta(1).duration(50).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(OrePrefix.plate, Materials.WroughtIron, 8).outputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.ULV)).circuitMeta(8).duration(25).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(OrePrefix.plate, Materials.Steel, 8).outputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.LV)).circuitMeta(8).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(OrePrefix.plate, Materials.Aluminium, 8).outputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.MV)).circuitMeta(8).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(OrePrefix.plate, Materials.StainlessSteel, 8).outputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.HV)).circuitMeta(8).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(OrePrefix.plate, Materials.Titanium, 8).outputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.EV)).circuitMeta(8).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(OrePrefix.plate, Materials.TungstenSteel, 8).outputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.IV)).circuitMeta(8).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(OrePrefix.plate, Materials.Chrome, 8).outputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.LuV)).circuitMeta(8).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(OrePrefix.plate, Materials.Iridium, 8).outputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.ZPM)).circuitMeta(8).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(OrePrefix.plate, Materials.Osmium, 8).outputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.UV)).circuitMeta(8).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(OrePrefix.plate, Materials.Darmstadtium, 8).outputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.MAX)).circuitMeta(8).duration(50).buildAndRegister();

        for(CoilType coilType : CoilType.values()) {
            if(coilType.getMaterial() == null) continue;
            ItemStack outputStack = MetaBlocks.WIRE_COIL.getItemVariant(coilType);
            UnificationEntry input = new UnificationEntry(OrePrefix.wireGtDouble, coilType.getMaterial());
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(input.orePrefix, input.material, 8).outputs(outputStack).circuitMeta(8).duration(50).buildAndRegister();
            ModHandler.addShapedRecipe(String.format("heating_coil_%s", coilType.getName()), outputStack, "XXX", "XwX", "XXX", 'X', input);
            OreDictUnifier.registerOre(outputStack, new ItemMaterialInfo(new MaterialStack(coilType.getMaterial(), OrePrefix.wireGtDouble.materialAmount * 8)));
        }
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(OrePrefix.wireGtDouble, MarkerMaterials.Tier.Superconductor, 8).outputs(MetaBlocks.WIRE_COIL.getItemVariant(CoilType.SUPERCONDUCTOR)).circuitMeta(8).duration(50).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(OrePrefix.plate, Materials.Invar, 6).input(OrePrefix.frameGt, Materials.Invar, 1).outputs(MetaBlocks.METAL_CASING.getItemVariant(MetalCasingType.INVAR_HEATPROOF, 3)).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(OrePrefix.plate, Materials.Steel, 6).input(OrePrefix.frameGt, Materials.Steel, 1).outputs(MetaBlocks.METAL_CASING.getItemVariant(MetalCasingType.STEEL_SOLID, 3)).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(OrePrefix.plate, Materials.Aluminium, 6).input(OrePrefix.frameGt, Materials.Aluminium, 1).outputs(MetaBlocks.METAL_CASING.getItemVariant(MetalCasingType.ALUMINIUM_FROSTPROOF, 3)).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(OrePrefix.plate, Materials.TungstenSteel, 6).input(OrePrefix.frameGt, Materials.TungstenSteel, 1).outputs(MetaBlocks.METAL_CASING.getItemVariant(MetalCasingType.TUNGSTENSTEEL_ROBUST, 3)).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(OrePrefix.plate, Materials.StainlessSteel, 6).input(OrePrefix.frameGt, Materials.StainlessSteel, 1).outputs(MetaBlocks.METAL_CASING.getItemVariant(MetalCasingType.STAINLESS_CLEAN, 3)).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(OrePrefix.plate, Materials.Titanium, 6).input(OrePrefix.frameGt, Materials.Titanium, 1).outputs(MetaBlocks.METAL_CASING.getItemVariant(MetalCasingType.TITANIUM_STABLE, 3)).duration(50).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(16).inputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.LuV)).input(OrePrefix.plate, Materials.TungstenSteel, 6).outputs(MetaBlocks.MUTLIBLOCK_CASING.getItemVariant(MultiblockCasingType.FUSION_CASING)).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(OrePrefix.plate, Materials.Magnalium, 6).input(OrePrefix.frameGt, Materials.BlueSteel, 1).outputs(MetaBlocks.TURBINE_CASING.getItemVariant(TurbineCasingType.STEEL_TURBINE_CASING, 3)).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(16).inputs(MetaBlocks.TURBINE_CASING.getItemVariant(TurbineCasingType.STEEL_TURBINE_CASING)).input(OrePrefix.plate, Materials.StainlessSteel, 6).outputs(MetaBlocks.TURBINE_CASING.getItemVariant(TurbineCasingType.STAINLESS_TURBINE_CASING, 3)).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(16).inputs(MetaBlocks.TURBINE_CASING.getItemVariant(TurbineCasingType.STEEL_TURBINE_CASING)).input(OrePrefix.plate, Materials.Titanium, 6).outputs(MetaBlocks.TURBINE_CASING.getItemVariant(TurbineCasingType.TITANIUM_TURBINE_CASING, 3)).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(16).inputs(MetaBlocks.TURBINE_CASING.getItemVariant(TurbineCasingType.STEEL_TURBINE_CASING)).input(OrePrefix.plate, Materials.TungstenSteel, 6).outputs(MetaBlocks.TURBINE_CASING.getItemVariant(TurbineCasingType.TUNGSTENSTEEL_TURBINE_CASING, 3)).duration(50).buildAndRegister();

        if(ConfigHolder.harderMachineHulls) {
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(25).EUt(16).inputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.ULV)).input(OrePrefix.cableGtSingle, Materials.Lead, 2).fluidInputs(Materials.Plastic.getFluid(L * 2)).outputs(MetaTileEntities.HULL[0].getStackForm()).buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(16).inputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.LV)).input(OrePrefix.cableGtSingle, Materials.Tin, 2).fluidInputs(Materials.Plastic.getFluid(L * 2)).outputs(MetaTileEntities.HULL[1].getStackForm()).buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(16).inputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.MV)).input(OrePrefix.cableGtSingle, Materials.Copper, 2).fluidInputs(Materials.Plastic.getFluid(L * 2)).outputs(MetaTileEntities.HULL[2].getStackForm()).buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(16).inputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.MV)).input(OrePrefix.cableGtSingle, Materials.AnnealedCopper, 2).fluidInputs(Materials.Plastic.getFluid(L * 2)).outputs(MetaTileEntities.HULL[2].getStackForm()).buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(16).inputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.HV)).input(OrePrefix.cableGtSingle, Materials.Gold, 2).fluidInputs(Materials.Plastic.getFluid(L * 2)).outputs(MetaTileEntities.HULL[3].getStackForm()).buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(16).inputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.EV)).input(OrePrefix.cableGtSingle, Materials.Aluminium, 2).fluidInputs(Materials.Plastic.getFluid(L * 2)).outputs(MetaTileEntities.HULL[4].getStackForm()).buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(16).inputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.IV)).input(OrePrefix.cableGtSingle, Materials.Tungsten, 2).fluidInputs(Materials.Plastic.getFluid(L * 2)).outputs(MetaTileEntities.HULL[5].getStackForm()).buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(16).inputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.LuV)).input(OrePrefix.cableGtSingle, Materials.VanadiumGallium, 2).fluidInputs(Materials.Plastic.getFluid(L * 2)).outputs(MetaTileEntities.HULL[6].getStackForm()).buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(16).inputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.ZPM)).input(OrePrefix.cableGtSingle, Materials.Naquadah, 2).fluidInputs(Materials.Polytetrafluoroethylene.getFluid(L * 2)).outputs(MetaTileEntities.HULL[7].getStackForm()).buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(16).inputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.UV)).input(OrePrefix.wireGtQuadruple, Materials.NaquadahAlloy, 2).fluidInputs(Materials.Polytetrafluoroethylene.getFluid(L * 2)).outputs(MetaTileEntities.HULL[8].getStackForm()).buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(16).inputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.MAX)).input(OrePrefix.wireGtSingle, MarkerMaterials.Tier.Superconductor, 2).fluidInputs(Materials.Polytetrafluoroethylene.getFluid(L * 2)).outputs(MetaTileEntities.HULL[9].getStackForm()).buildAndRegister();
        } else {
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(25).EUt(16).inputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.ULV)).input(OrePrefix.cableGtSingle, Materials.Lead, 2).outputs(MetaTileEntities.HULL[0].getStackForm()).buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(16).inputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.LV)).input(OrePrefix.cableGtSingle, Materials.Tin, 2).outputs(MetaTileEntities.HULL[1].getStackForm()).buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(16).inputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.MV)).input(OrePrefix.cableGtSingle, Materials.Copper, 2).outputs(MetaTileEntities.HULL[2].getStackForm()).buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(16).inputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.MV)).input(OrePrefix.cableGtSingle, Materials.AnnealedCopper, 2).outputs(MetaTileEntities.HULL[2].getStackForm()).buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(16).inputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.HV)).input(OrePrefix.cableGtSingle, Materials.Gold, 2).outputs(MetaTileEntities.HULL[3].getStackForm()).buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(16).inputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.EV)).input(OrePrefix.cableGtSingle, Materials.Aluminium, 2).outputs(MetaTileEntities.HULL[4].getStackForm()).buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(16).inputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.IV)).input(OrePrefix.cableGtSingle, Materials.Tungsten, 2).outputs(MetaTileEntities.HULL[5].getStackForm()).buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(16).inputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.LuV)).input(OrePrefix.cableGtSingle, Materials.VanadiumGallium, 2).outputs(MetaTileEntities.HULL[6].getStackForm()).buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(16).inputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.ZPM)).input(OrePrefix.cableGtSingle, Materials.Naquadah, 2).outputs(MetaTileEntities.HULL[7].getStackForm()).buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(16).inputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.UV)).input(OrePrefix.wireGtQuadruple, Materials.NaquadahAlloy, 2).outputs(MetaTileEntities.HULL[8].getStackForm()).buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(16).inputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.MAX)).input(OrePrefix.wireGtSingle, MarkerMaterials.Tier.Superconductor, 2).outputs(MetaTileEntities.HULL[9].getStackForm()).buildAndRegister();
        }
        
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(800).EUt(1).input(OrePrefix.cableGtSingle, Materials.Tin, 1).input(OrePrefix.plate, Materials.BatteryAlloy, 1).fluidInputs(Materials.Plastic.getFluid(144)).outputs(MetaItems.BATTERY_HULL_LV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(1600).EUt(2).input(OrePrefix.cableGtSingle, Materials.Copper, 2).input(OrePrefix.plate, Materials.BatteryAlloy, 3).fluidInputs(Materials.Plastic.getFluid(432)).outputs(MetaItems.BATTERY_HULL_MV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(1600).EUt(2).input(OrePrefix.cableGtSingle, Materials.AnnealedCopper, 2).input(OrePrefix.plate, Materials.BatteryAlloy, 3).fluidInputs(Materials.Plastic.getFluid(432)).outputs(MetaItems.BATTERY_HULL_MV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(3200).EUt(4).input(OrePrefix.cableGtSingle, Materials.Gold, 4).input(OrePrefix.plate, Materials.BatteryAlloy, 9).fluidInputs(Materials.Plastic.getFluid(1296)).outputs(MetaItems.BATTERY_HULL_HV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(2).inputs(new ItemStack(Items.STRING, 4, OreDictionary.WILDCARD_VALUE), new ItemStack(Items.SLIME_BALL, 1, OreDictionary.WILDCARD_VALUE)).outputs(new ItemStack(Items.LEAD,2)).duration(200).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(2).inputs(new ItemStack(Blocks.CHEST, 1, OreDictionary.WILDCARD_VALUE)).input(OrePrefix.plate, Materials.Iron, 5).outputs(new ItemStack(Blocks.HOPPER)).duration(800).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(2).inputs(new ItemStack(Blocks.TRAPPED_CHEST, 1, OreDictionary.WILDCARD_VALUE)).input(OrePrefix.plate, Materials.Iron, 5).outputs(new ItemStack(Blocks.HOPPER)).duration(800).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(2).inputs(new ItemStack(Blocks.CHEST, 1, OreDictionary.WILDCARD_VALUE)).input(OrePrefix.plate, Materials.WroughtIron, 5).outputs(new ItemStack(Blocks.HOPPER)).duration(800).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(2).inputs(new ItemStack(Blocks.TRAPPED_CHEST, 1, OreDictionary.WILDCARD_VALUE)).input(OrePrefix.plate, Materials.WroughtIron, 5).outputs(new ItemStack(Blocks.HOPPER)).duration(800).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(2).inputs(new ItemStack(Items.BLAZE_POWDER)).input(OrePrefix.gem, Materials.EnderPearl, 1).outputs(new ItemStack(Items.ENDER_EYE,1,0)).duration(400).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(2).inputs(new ItemStack(Items.BLAZE_ROD)).input(OrePrefix.gem, Materials.EnderPearl, 6).outputs(new ItemStack(Items.ENDER_EYE,6,0)).duration(2500).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(2).input(OrePrefix.gear, Materials.CobaltBrass, 1).input(OrePrefix.dust, Materials.Diamond, 1).outputs(MetaItems.COMPONENT_SAW_BLADE_DIAMOND.getStackForm(1)).duration(1600).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(1).input(OrePrefix.dust, Materials.Redstone, 4).input(OrePrefix.dust, Materials.Glowstone, 4).outputs(new ItemStack(Blocks.REDSTONE_LAMP,1)).duration(400).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(1).input(OrePrefix.dust, Materials.Redstone, 1).input(OrePrefix.stick, Materials.Wood, 1).outputs(new ItemStack(Blocks.REDSTONE_TORCH,1)).duration(400).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(4).input(OrePrefix.dust, Materials.Redstone, 1).input(OrePrefix.plate, Materials.Iron, 4).outputs(new ItemStack(Items.COMPASS,1)).duration(400).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(4).input(OrePrefix.dust, Materials.Redstone, 1).input(OrePrefix.plate, Materials.WroughtIron, 4).outputs(new ItemStack(Items.COMPASS,1)).duration(400).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(4).input(OrePrefix.dust, Materials.Redstone, 1).input(OrePrefix.plate, Materials.Gold, 4).outputs(new ItemStack(Items.CLOCK,1)).duration(400).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(1).input(OrePrefix.stick, Materials.Wood, 1).input(OrePrefix.dust, Materials.Sulfur, 1).outputs(new ItemStack(Blocks.TORCH,2)).duration(400).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(1).input(OrePrefix.stick, Materials.Wood, 1).input(OrePrefix.dust, Materials.Phosphorus, 1).outputs(new ItemStack(Blocks.TORCH,6)).duration(400).buildAndRegister();

        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(300).EUt(5).inputs(MetaItems.RUBBER_DROP.getStackForm()).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.RawRubber, 3)).chancedOutput(MetaItems.PLANT_BALL.getStackForm(), 1000).fluidOutputs(Materials.Glue.getFluid(100)).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(200).EUt(20).inputs(MetaBlocks.LOG.getItem(LogVariant.RUBBER_WOOD)).chancedOutput(MetaItems.RUBBER_DROP.getStackForm(), 5000).chancedOutput(MetaItems.PLANT_BALL.getStackForm(), 3750).chancedOutput(OreDictUnifier.get(OrePrefix.dust, Materials.Carbon), 2500).chancedOutput(OreDictUnifier.get(OrePrefix.dust, Materials.Wood), 2500).fluidOutputs(Materials.Methane.getFluid(60)).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(50).EUt(30).inputs(new ItemStack(Blocks.SAND, 1, 1)).chancedOutput(OreDictUnifier.get(OrePrefix.dust,Materials.Iron,1), 500).chancedOutput(OreDictUnifier.get(OrePrefix.dustTiny,Materials.Diamond,1), 100).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(250).EUt(30).inputs(new ItemStack(Blocks.DIRT, 1, OreDictionary.WILDCARD_VALUE)).chancedOutput(MetaItems.PLANT_BALL.getStackForm(), 1250).chancedOutput(new ItemStack(Blocks.SAND), 5000).chancedOutput(OreDictUnifier.get(OrePrefix.dustTiny,Materials.Clay,1), 5000).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(250).EUt(30).inputs(new ItemStack(Blocks.GRASS, 1, OreDictionary.WILDCARD_VALUE)).chancedOutput(MetaItems.PLANT_BALL.getStackForm(), 2500).chancedOutput(new ItemStack(Blocks.SAND), 5000).chancedOutput(OreDictUnifier.get(OrePrefix.dustTiny,Materials.Clay,1), 5000).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(650).EUt(30).inputs(new ItemStack(Blocks.MYCELIUM, 1, OreDictionary.WILDCARD_VALUE)).chancedOutput(new ItemStack(Blocks.RED_MUSHROOM), 2500).chancedOutput(new ItemStack(Blocks.SAND), 5000).chancedOutput(new ItemStack(Blocks.BROWN_MUSHROOM), 2500).chancedOutput(OreDictUnifier.get(OrePrefix.dustTiny,Materials.Clay,1), 5000).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(50).EUt(30).input(OrePrefix.dust, Materials.DarkAsh, 2).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Ash,1), OreDictUnifier.get(OrePrefix.dust, Materials.Carbon,1)).buildAndRegister();

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

        for(EnumDyeColor dyeColor : EnumDyeColor.values()) {
            String colorName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, dyeColor.getName());
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(MetaItems.SPRAY_EMPTY.getStackForm())
                .input("dye" + colorName, 1)
                .outputs(MetaItems.SPRAY_CAN_DYES[dyeColor.getMetadata()].getStackForm())
                .EUt(8).duration(200)
                .buildAndRegister();
        }

        RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
            .input(OrePrefix.plate, Materials.Iron, 2)
            .notConsumable(MetaItems.SHAPE_EXTRUDER_CELL)
            .outputs(MetaItems.FLUID_CELL.getStackForm())
            .duration(200)
            .EUt(30)
            .buildAndRegister();

        RecipeMaps.BENDER_RECIPES.recipeBuilder()
            .input(OrePrefix.plate, Materials.Iron, 2)
            .circuitMeta(12)
            .outputs(MetaItems.FLUID_CELL.getStackForm())
            .duration(200)
            .EUt(30)
            .buildAndRegister();

        RecipeMaps.MACERATOR_RECIPES.recipeBuilder()
            .input(OrePrefix.stone, Materials.Endstone)
            .outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Endstone))
            .chancedOutput(OreDictUnifier.get(OrePrefix.dustTiny, Materials.Tungstate), 1200)
            .buildAndRegister();

        RecipeMaps.MACERATOR_RECIPES.recipeBuilder()
            .input(OrePrefix.stone, Materials.Netherrack)
            .outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Netherrack, 1))
            .chancedOutput(OreDictUnifier.get(OrePrefix.nugget, Materials.Gold, 1), 500)
            .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .input(OrePrefix.gem, Materials.NetherStar).input(OrePrefix.stone, Materials.Obsidian, 3)
            .fluidInputs(Materials.Glass.getFluid(720)).outputs(new ItemStack(Blocks.BEACON))
            .duration(32)
            .EUt(16)
            .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .input(OrePrefix.stone, Materials.Obsidian, 8)
            .input(OrePrefix.gem, Materials.EnderEye)
            .outputs(new ItemStack(Blocks.ENDER_CHEST, 1))
            .duration(400)
            .EUt(4)
            .buildAndRegister();

        RecipeMaps.MACERATOR_RECIPES.recipeBuilder()
            .input(OrePrefix.stone, Materials.Soapstone)
            .outputs(OreDictUnifier.get(OrePrefix.dustImpure, Materials.Talc, 1))
            .chancedOutput(OreDictUnifier.get(OrePrefix.dustTiny, Materials.Chromite, 1), 1000)
            .buildAndRegister();

        RecipeMaps.MACERATOR_RECIPES.recipeBuilder()
            .input(OrePrefix.stone, Materials.Redrock)
            .outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Redrock))
            .chancedOutput(OreDictUnifier.get(OrePrefix.dust, Materials.Redrock), 1000)
            .buildAndRegister();

        RecipeMaps.MACERATOR_RECIPES.recipeBuilder()
            .input(OrePrefix.stone, Materials.Marble)
            .outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Marble))
            .chancedOutput(OreDictUnifier.get(OrePrefix.dust, Materials.Marble), 1000)
            .buildAndRegister();

        RecipeMaps.MACERATOR_RECIPES.recipeBuilder()
            .input(OrePrefix.stone, Materials.Basalt)
            .outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Basalt, 1))
            .chancedOutput(OreDictUnifier.get(OrePrefix.dust, Materials.Basalt, 1), 1000)
            .buildAndRegister();

        RecipeMaps.MACERATOR_RECIPES.recipeBuilder()
            .input(OrePrefix.stone, Materials.Quartzite)
            .outputs(OreDictUnifier.get(OrePrefix.dustImpure, Materials.Quartzite, 1))
            .chancedOutput(OreDictUnifier.get(OrePrefix.dust, Materials.Quartzite, 1), 1000)
            .buildAndRegister();

        RecipeMaps.MACERATOR_RECIPES.recipeBuilder()
            .input(OrePrefix.stone, Materials.Flint)
            .outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Flint, 2))
            .chancedOutput(new ItemStack(Items.FLINT, 1), 5000)
            .buildAndRegister();

        RecipeMaps.MACERATOR_RECIPES.recipeBuilder()
            .input(OrePrefix.stone, Materials.GraniteBlack)
            .outputs(OreDictUnifier.get(OrePrefix.dust, Materials.GraniteBlack, 1))
            .chancedOutput(OreDictUnifier.get(OrePrefix.dust, Materials.Thorium, 1), 100)
            .buildAndRegister();

        RecipeMaps.MACERATOR_RECIPES.recipeBuilder()
            .input(OrePrefix.stone, Materials.GraniteRed)
            .outputs(OreDictUnifier.get(OrePrefix.dust, Materials.GraniteRed, 1))
            .chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Uranium, 1), 100)
            .buildAndRegister();

        RecipeMaps.MACERATOR_RECIPES.recipeBuilder()
            .input(OrePrefix.stone, Materials.Andesite)
            .outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Andesite, 1))
            .chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Stone, 1), 100)
            .buildAndRegister();

        RecipeMaps.MACERATOR_RECIPES.recipeBuilder()
            .input(OrePrefix.stone, Materials.Diorite)
            .outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Diorite, 1))
            .chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Stone, 1), 100)
            .buildAndRegister();

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
    }

    public static void initializeArcRecyclingRecipes() {
        for(Entry<ItemStack, ItemMaterialInfo> entry : OreDictUnifier.getAllItemInfos()) {
            ItemStack itemStack = entry.getKey();
            ItemMaterialInfo materialInfo = entry.getValue();
            ArrayList<MaterialStack> materialStacks = new ArrayList<>();
            materialStacks.add(materialInfo.material);
            materialStacks.addAll(materialInfo.additionalComponents);
            registerArcRecyclingRecipe(b -> b.inputs(itemStack), materialStacks, false);
        }
    }

    public static void registerArcRecyclingRecipe(Consumer<RecipeBuilder<?>> inputSupplier, List<MaterialStack> components, boolean ignoreArcSmelting) {
        List<MaterialStack> dustMaterials = components.stream()
            .filter(stack -> stack.material instanceof DustMaterial)
            .filter(stack -> stack.amount >= M / 9) //do only materials which have at least one nugget
            .collect(Collectors.toList());
        if(dustMaterials.isEmpty()) return;
        MaterialStack firstStack = dustMaterials.get(0);
        DustMaterial dustMaterial = (DustMaterial) firstStack.material;
        int voltageMultiplier = 1;
        if(dustMaterial instanceof IngotMaterial) {
            int blastFurnaceTemperature = ((IngotMaterial) dustMaterial).blastFurnaceTemperature;
            voltageMultiplier = blastFurnaceTemperature == 0 ? 1 : blastFurnaceTemperature > 2000 ? 16 : 4;
        } else {
            //do not apply arc smelting for gems, solid materials and dust materials
            //only generate recipes for ingot materials
            ignoreArcSmelting = true;
        }

        RecipeBuilder<?> maceratorRecipeBuilder = RecipeMaps.MACERATOR_RECIPES.recipeBuilder()
            .outputs(dustMaterials.stream().map(OreDictUnifier::getDust).collect(Collectors.toList()))
            .duration((int) Math.max(1L, firstStack.amount * 30 / M))
            .EUt(8 * voltageMultiplier);
        inputSupplier.accept(maceratorRecipeBuilder);
        maceratorRecipeBuilder.buildAndRegister();

        if(dustMaterial.shouldGenerateFluid()) {
            RecipeBuilder<?> fluidExtractorRecipeBuilder = RecipeMaps.FLUID_EXTRACTION_RECIPES.recipeBuilder()
                .fluidOutputs(dustMaterial.getFluid((int) (firstStack.amount * L / M)))
                .duration((int) Math.max(1L, firstStack.amount * 80 / M))
                .EUt(32 * voltageMultiplier);
            inputSupplier.accept(fluidExtractorRecipeBuilder);
            fluidExtractorRecipeBuilder.buildAndRegister();
        }

        if(!ignoreArcSmelting) {
            List<ItemStack> resultList = dustMaterials.stream().map(MachineRecipeLoader::getArcSmeltingResult).collect(Collectors.toList());
            resultList.removeIf(ItemStack::isEmpty);
            if(resultList.isEmpty()) return;
            RecipeBuilder<?> arcFurnaceRecipeBuilder = RecipeMaps.ARC_FURNACE_RECIPES.recipeBuilder()
                .outputs(resultList)
                .duration((int) Math.max(1L, firstStack.amount * 60 / M))
                .EUt(30 * voltageMultiplier);
            inputSupplier.accept(arcFurnaceRecipeBuilder);
            arcFurnaceRecipeBuilder.buildAndRegister();
        }
    }

    private static ItemStack getArcSmeltingResult(MaterialStack materialStack) {
        DustMaterial material = (DustMaterial) materialStack.material;
        long materialAmount = materialStack.amount;
        if(material.hasFlag(MatFlags.FLAMMABLE)) {
            return OreDictUnifier.getDust(Materials.Ash, materialAmount);
        } else if(material instanceof GemMaterial) {
            if(materialStack.material.materialComponents.stream()
                .anyMatch(stack -> stack.material == Materials.Oxygen)) {
                return OreDictUnifier.getDust(Materials.Ash, materialAmount);
            }
            if(materialStack.material.materialComponents.stream()
                .anyMatch(stack -> stack.material == Materials.Carbon)) {
                return OreDictUnifier.getDust(Materials.Carbon, materialAmount);
            }
            return OreDictUnifier.getDust(Materials.DarkAsh, materialAmount);
        } else if(material instanceof IngotMaterial) {
            IngotMaterial ingotMaterial = (IngotMaterial) material;
            if(ingotMaterial.arcSmeltInto != null)
                ingotMaterial = ingotMaterial.arcSmeltInto;
            return OreDictUnifier.getIngot(ingotMaterial, materialAmount);
        } else {
            return OreDictUnifier.getDust(material, materialAmount);
        }
    }

    public static void initializeWoodRecipes() {
        RecipeMaps.MACERATOR_RECIPES.recipeBuilder()
            .input(OrePrefix.log, Materials.Wood)
            .outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Wood, 6))
            .chancedOutput(OreDictUnifier.get(OrePrefix.dust, Materials.Wood), 8000)
            .buildAndRegister();

        RecipeMaps.LATHE_RECIPES.recipeBuilder()
            .input(OrePrefix.log, Materials.Wood)
            .outputs(OreDictUnifier.get(OrePrefix.stickLong, Materials.Wood, 4),
                OreDictUnifier.get(OrePrefix.dust, Materials.Wood, 2))
            .duration(160).EUt(8)
            .buildAndRegister();

        List<ItemStack> allWoodLogs =  OreDictionary.getOres("logWood").stream()
            .flatMap(stack -> ModHandler.getAllSubItems(stack).stream())
            .collect(Collectors.toList());

        //Wood processing
        for (ItemStack stack : allWoodLogs) {
            ItemStack smeltingOutput = ModHandler.getSmeltingOutput(stack);
            if (!smeltingOutput.isEmpty() && smeltingOutput.getItem() == Items.COAL && smeltingOutput.getMetadata() == 1) {
                int coalAmount = smeltingOutput.getCount();
                RecipeMaps.PYROLYSE_RECIPES.recipeBuilder()
                    .inputs(GTUtility.copyAmount(16, stack))
                    .circuitMeta(0)
                    .outputs(new ItemStack(Items.COAL, 24 * coalAmount, 1))
                    .fluidOutputs(Materials.Creosote.getFluid(5000 * coalAmount))
                    .duration(440).EUt(64)
                    .buildAndRegister();

                RecipeMaps.PYROLYSE_RECIPES.recipeBuilder()
                    .inputs(GTUtility.copyAmount(16, stack))
                    .circuitMeta(1)
                    .fluidInputs(Materials.Nitrogen.getFluid(400))
                    .outputs(new ItemStack(Items.COAL, 24, 1))
                    .fluidOutputs(Materials.Creosote.getFluid(4000))
                    .duration(200).EUt(96)
                    .buildAndRegister();

                RecipeMaps.PYROLYSE_RECIPES.recipeBuilder()
                    .inputs(GTUtility.copyAmount(16, stack))
                    .circuitMeta(2)
                    .outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Ash, 5))
                    .fluidOutputs(Materials.OilHeavy.getFluid(500 * coalAmount))
                    .duration(280).EUt(192)
                    .buildAndRegister();
            }

            Pair<IRecipe, ItemStack> outputPair = ModHandler.getRecipeOutput(null, stack);
            ItemStack output = outputPair.getValue();
            int originalOutput = output.getCount();
            if (!output.isEmpty()) {
                IRecipe outputRecipe = outputPair.getKey();
                if (ConfigHolder.vanillaRecipes.nerfWoodCrafting) {
                    GTLog.logger.info("Nerfing planks crafting recipe {} -> {}", stack, output);
                    //noinspection ConstantConditions
                    if(originalOutput / 2 > 0) {
                        ModHandler.addShapelessRecipe(outputRecipe.getRegistryName().toString(),
                            GTUtility.copyAmount(originalOutput / 2, output), stack);
                    } else {
                        //if not enough planks are on output, just remove recipe
                        ModHandler.removeRecipeByName(outputRecipe.getRegistryName());
                    }
                }

                RecipeMaps.CUTTER_RECIPES.recipeBuilder()
                    .inputs(stack)
                    .fluidInputs(Materials.Lubricant.getFluid(1))
                    .outputs(GTUtility.copyAmount((int) (originalOutput * 1.5), output),
                        OreDictUnifier.get(OrePrefix.dust, Materials.Wood, 2))
                    .duration(200).EUt(8)
                    .buildAndRegister();

                ModHandler.addShapedRecipe(outputRecipe.getRegistryName().getResourcePath() + "_saw",
                    GTUtility.copyAmount(originalOutput, output), "s", "L", 'L', stack);
            }
        }

        RecipeMaps.PYROLYSE_RECIPES.recipeBuilder()
            .input(OrePrefix.gem, Materials.Coal, 16)
            .circuitMeta(0)
            .outputs(OreDictUnifier.get(OrePrefix.gem, Materials.Coke, 20))
            .fluidOutputs(Materials.Creosote.getFluid(10000))
            .duration(440).EUt(96)
            .buildAndRegister();

        RecipeMaps.LATHE_RECIPES.recipeBuilder()
            .input(OrePrefix.plank, Materials.Wood)
            .outputs(OreDictUnifier.get(OrePrefix.stick, Materials.Wood, 2))
            .duration(10)
            .EUt(8)
            .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .input(OrePrefix.plank, Materials.Wood, 8)
            .input(OrePrefix.dust, Materials.Redstone)
            .outputs(new ItemStack(Blocks.NOTEBLOCK, 1))
            .duration(200)
            .EUt(4)
            .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .input(OrePrefix.plank, Materials.Wood, 8)
            .input(OrePrefix.gem, Materials.Diamond)
            .outputs(new ItemStack(Blocks.JUKEBOX, 1))
            .duration(400)
            .EUt(4)
            .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .input(OrePrefix.plank, Materials.Wood, 6)
            .inputs(new ItemStack(Items.BOOK, 3))
            .outputs(new ItemStack(Blocks.BOOKSHELF, 1))
            .duration(400)
            .EUt(4)
            .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .input(OrePrefix.plank, Materials.Wood)
            .circuitMeta(1)
            .outputs(new ItemStack(Blocks.WOODEN_BUTTON, 1))
            .duration(100)
            .EUt(4)
            .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .input(OrePrefix.plank, Materials.Wood, 2)
            .circuitMeta(2)
            .outputs(new ItemStack(Blocks.WOODEN_PRESSURE_PLATE))
            .duration(200)
            .EUt(4)
            .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .input(OrePrefix.plank, Materials.Wood, 3)
            .circuitMeta(3)
            .outputs(new ItemStack(Blocks.TRAPDOOR, 2))
            .duration(300)
            .EUt(4)
            .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .input(OrePrefix.plank, Materials.Wood, 4)
            .circuitMeta(4)
            .outputs(new ItemStack(Blocks.CRAFTING_TABLE))
            .duration(400).EUt(4)
            .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .input(OrePrefix.plank, Materials.Wood, 8)
            .outputs(new ItemStack(Blocks.CHEST, 1))
            .duration(800).EUt(4).circuitMeta(8)
            .buildAndRegister();

        List<ItemStack> allPlanksStacks = OreDictionary.getOres("plankWood").stream()
            .flatMap(stack -> ModHandler.getAllSubItems(stack).stream())
            .collect(Collectors.toList());
        HashSet<String> alreadyProcessedPlanks = new HashSet<>();
        for (ItemStack stack : allPlanksStacks) {
            ItemStack output = ModHandler.getRecipeOutput(null, stack, stack, stack).getValue();
            if (!output.isEmpty() && output.getCount() >= 3) {
                String recipeName = String.format("slab_%s_%s", stack.getUnlocalizedName(), stack.getMetadata());
                if(alreadyProcessedPlanks.contains(recipeName)) {
                    //any reason why this would ever happen trough? probably some mods just fuck up ore dictionary registrations
                    //completely and just fucking register same item in registry twice
                    continue;
                }
                alreadyProcessedPlanks.add(recipeName);

                RecipeMaps.CUTTER_RECIPES.recipeBuilder()
                    .inputs(stack)
                    .outputs(GTUtility.copyAmount(output.getCount() / 3, output))
                    .duration(25).EUt(4)
                    .buildAndRegister();
                ModHandler.addShapedRecipe(recipeName,
                    GTUtility.copyAmount(output.getCount() / 3, output),
                    "sP", 'P', stack);
            }
        }

        registerPlanksCuttingRecipes();
    }

    private static <T extends Enum<T> & IStringSerializable> void registerBrickRecipe(StoneBlock<T> stoneBlock, T normalVariant, T brickVariant) {
        ModHandler.addShapedRecipe(stoneBlock.getRegistryName().getResourceDomain() + "_" + normalVariant + "_bricks",
            stoneBlock.getItemVariant(brickVariant, ChiselingVariant.NORMAL, 4),
            "XX", "XX", 'X',
            stoneBlock.getItemVariant(normalVariant, ChiselingVariant.NORMAL));
    }
    
    private static <T extends Enum<T> & IStringSerializable> void registerChiselingRecipes(StoneBlock<T> stoneBlock) {
        for(T variant : stoneBlock.getVariantValues()) {
            boolean isBricksVariant = variant.getName().endsWith("_bricks");
            if(!isBricksVariant) {
                ModHandler.addSmeltingRecipe(stoneBlock.getItemVariant(variant, ChiselingVariant.CRACKED), stoneBlock.getItemVariant(variant, ChiselingVariant.NORMAL));
                RecipeMaps.FORGE_HAMMER_RECIPES.recipeBuilder().duration(12).EUt(4)
                    .inputs(stoneBlock.getItemVariant(variant, ChiselingVariant.NORMAL))
                    .outputs(stoneBlock.getItemVariant(variant, ChiselingVariant.CRACKED))
                    .buildAndRegister();
            } else {
                ModHandler.addSmeltingRecipe(stoneBlock.getItemVariant(variant, ChiselingVariant.NORMAL), stoneBlock.getItemVariant(variant, ChiselingVariant.CRACKED));
            }
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(12).EUt(4)
                .inputs(stoneBlock.getItemVariant(variant, !isBricksVariant ? ChiselingVariant.CRACKED : ChiselingVariant.NORMAL))
                .fluidInputs(Materials.Water.getFluid(144))
                .outputs(stoneBlock.getItemVariant(variant, ChiselingVariant.MOSSY))
                .buildAndRegister();
            ModHandler.addShapelessRecipe(stoneBlock.getRegistryName().getResourcePath() + "_chiseling_" + variant,
                stoneBlock.getItemVariant(variant, ChiselingVariant.CHISELED),
                stoneBlock.getItemVariant(variant, ChiselingVariant.NORMAL));
        }
    }

    //TODO move more stuff here and do it automatically
    private static void registerPlanksCuttingRecipes() {
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Blocks.PLANKS, 6, EnumType.OAK.getMetadata()))
            .outputs(new ItemStack(Items.OAK_DOOR, 3))
            .duration(600).EUt(4).circuitMeta(6)
            .buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Blocks.PLANKS, 6, EnumType.SPRUCE.getMetadata()))
            .outputs(new ItemStack(Items.SPRUCE_DOOR, 3))
            .duration(600).EUt(4).circuitMeta(6)
            .buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Blocks.PLANKS, 6, EnumType.BIRCH.getMetadata()))
            .outputs(new ItemStack(Items.BIRCH_DOOR, 3))
            .duration(600).EUt(4).circuitMeta(6)
            .buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Blocks.PLANKS, 6, EnumType.JUNGLE.getMetadata()))
            .outputs(new ItemStack(Items.JUNGLE_DOOR, 3))
            .duration(600).EUt(4).circuitMeta(6)
            .buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Blocks.PLANKS, 6, EnumType.ACACIA.getMetadata()))
            .outputs(new ItemStack(Items.ACACIA_DOOR, 3))
            .duration(600).EUt(4).circuitMeta(6)
            .buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Blocks.PLANKS, 6, EnumType.DARK_OAK.getMetadata()))
            .outputs(new ItemStack(Items.DARK_OAK_DOOR, 3))
            .duration(600).EUt(4).circuitMeta(6)
            .buildAndRegister();
    }

}
