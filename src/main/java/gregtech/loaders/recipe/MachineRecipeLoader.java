package gregtech.loaders.recipe;

import gregtech.api.GTValues;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.recipes.CountableIngredient;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.builders.CokeOvenRecipeBuilder;
import gregtech.api.recipes.builders.PBFRecipeBuilder;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.MarkerMaterials.Color;
import gregtech.api.unification.material.MarkerMaterials.Tier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.IngotMaterial;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import gregtech.common.ConfigHolder;
import gregtech.common.blocks.BlockConcrete.ConcreteVariant;
import gregtech.common.blocks.BlockGranite.GraniteVariant;
import gregtech.common.blocks.BlockMachineCasing.MachineCasingType;
import gregtech.common.blocks.BlockMetalCasing.MetalCasingType;
import gregtech.common.blocks.BlockMineral.MineralVariant;
import gregtech.common.blocks.BlockMultiblockCasing.MultiblockCasingType;
import gregtech.common.blocks.BlockTransparentCasing;
import gregtech.common.blocks.BlockTurbineCasing.TurbineCasingType;
import gregtech.common.blocks.BlockWireCoil.CoilType;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.blocks.StoneBlock;
import gregtech.common.blocks.StoneBlock.ChiselingVariant;
import gregtech.common.blocks.wood.BlockGregLog.LogVariant;
import gregtech.common.items.MetaItems;
import gregtech.common.metatileentities.MetaTileEntities;
import gregtech.loaders.recipe.chemistry.AssemblerRecipeLoader;
import gregtech.loaders.recipe.chemistry.ChemistryRecipes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Tuple;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.Collection;
import java.util.List;

import static gregtech.api.GTValues.L;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.api.util.DyeUtil.getOrdictColorName;
import static gregtech.common.items.MetaItems.*;

public class MachineRecipeLoader {

    private MachineRecipeLoader() {
    }

    public static void init() {
        ChemistryRecipes.init();
        FuelRecipes.registerFuels();
        AssemblyLineLoader.init();
        AssemblerRecipeLoader.init();
        ComponentRecipes.register();
        MiscRecipeLoader.init();

        registerCircuitRecipes();
        registerCutterRecipes();
        registerDecompositionRecipes();
        registerBlastFurnaceRecipes();
        registerAssemblerRecipes();
        registerAlloyRecipes();
        registerBendingCompressingRecipes();
        registerCokeOvenRecipes();
        registerFluidRecipes();
        registerMixingCrystallizationRecipes();
        registerPrimitiveBlastFurnaceRecipes();
        registerRecyclingRecipes();
        registerStoneBricksRecipes();
        registerOrganicRecyclingRecipes();
    }

    private static void registerBendingCompressingRecipes() {
        COMPRESSOR_RECIPES.recipeBuilder().inputs(new ItemStack(Blocks.ICE, 2, GTValues.W)).outputs(new ItemStack(Blocks.PACKED_ICE)).buildAndRegister();
        COMPRESSOR_RECIPES.recipeBuilder().input(OrePrefix.dust, Materials.Ice, 1).outputs(new ItemStack(Blocks.ICE)).buildAndRegister();

        PACKER_RECIPES.recipeBuilder()
                .inputs(new ItemStack(Items.WHEAT, 9))
                .inputs(new CountableIngredient(new IntCircuitIngredient(9), 0))
                .outputs(new ItemStack(Blocks.HAY_BLOCK))
                .duration(200).EUt(2)
                .buildAndRegister();

        COMPRESSOR_RECIPES.recipeBuilder()
            .input(OrePrefix.dust, Materials.Fireclay)
            .outputs(MetaItems.COMPRESSED_FIRECLAY.getStackForm())
            .duration(100).EUt(2)
            .buildAndRegister();

        FORMING_PRESS_RECIPES.recipeBuilder()
            .duration(100).EUt(16)
            .notConsumable(MetaItems.SHAPE_MOLD_CREDIT.getStackForm())
            .input(OrePrefix.plate, Materials.Cupronickel, 1)
            .outputs(MetaItems.CREDIT_CUPRONICKEL.getStackForm(4))
            .buildAndRegister();

        FORMING_PRESS_RECIPES.recipeBuilder()
            .duration(100).EUt(16)
            .notConsumable(MetaItems.SHAPE_MOLD_CREDIT.getStackForm())
            .input(OrePrefix.plate, Materials.Brass, 1)
            .outputs(MetaItems.COIN_DOGE.getStackForm(4))
            .buildAndRegister();

        for (MetaItem<?>.MetaValueItem shapeMold : SHAPE_MOLDS) {
            FORMING_PRESS_RECIPES.recipeBuilder()
                .duration(120).EUt(22)
                .notConsumable(shapeMold.getStackForm())
                .inputs(MetaItems.SHAPE_EMPTY.getStackForm())
                .outputs(shapeMold.getStackForm())
                .buildAndRegister();
        }

        for (MetaItem<?>.MetaValueItem shapeExtruder : SHAPE_EXTRUDERS) {
            FORMING_PRESS_RECIPES.recipeBuilder()
                .duration(120).EUt(22)
                .notConsumable(shapeExtruder.getStackForm())
                .inputs(MetaItems.SHAPE_EMPTY.getStackForm())
                .outputs(shapeExtruder.getStackForm())
                .buildAndRegister();
        }

        BENDER_RECIPES.recipeBuilder()
            .circuitMeta(4)
            .input(OrePrefix.plate, Materials.Steel, 4)
            .outputs(MetaItems.SHAPE_EMPTY.getStackForm())
            .duration(180).EUt(12)
            .buildAndRegister();

        BENDER_RECIPES.recipeBuilder()
            .circuitMeta(1)
            .input(OrePrefix.plate, Materials.Iron, 12)
            .outputs(new ItemStack(Items.BUCKET, 4))
            .duration(800).EUt(4)
            .buildAndRegister();

        BENDER_RECIPES.recipeBuilder()
            .circuitMeta(1)
            .input(OrePrefix.plate, Materials.WroughtIron, 12)
            .outputs(new ItemStack(Items.BUCKET, 4))
            .duration(800).EUt(4)
            .buildAndRegister();

        BENDER_RECIPES.recipeBuilder()
            .circuitMeta(12)
            .input(OrePrefix.plate, Materials.Iron, 2)
            .outputs(MetaItems.FLUID_CELL.getStackForm())
            .duration(200).EUt(30)
            .buildAndRegister();

        EXTRUDER_RECIPES.recipeBuilder()
            .input(OrePrefix.plate, Materials.Iron, 2)
            .notConsumable(MetaItems.SHAPE_EXTRUDER_CELL)
            .outputs(MetaItems.FLUID_CELL.getStackForm())
            .duration(200).EUt(30)
            .buildAndRegister();

        COMPRESSOR_RECIPES.recipeBuilder()
            .input(OrePrefix.dust, Materials.NetherQuartz)
            .output(OrePrefix.plate, Materials.NetherQuartz)
            .duration(400).EUt(2).buildAndRegister();

        COMPRESSOR_RECIPES.recipeBuilder()
            .input(OrePrefix.dust, Materials.CertusQuartz)
            .output(OrePrefix.plate, Materials.CertusQuartz)
            .duration(400).EUt(2).buildAndRegister();
    }

    private static void registerPrimitiveBlastFurnaceRecipes() {
        PBFRecipeBuilder.start().input(ingot, Iron).output(ingot, Steel).duration(1500).fuelAmount(2).buildAndRegister();
        PBFRecipeBuilder.start().input(block, Iron).output(block, Steel).duration(13500).fuelAmount(18).buildAndRegister();
        PBFRecipeBuilder.start().input(ingot, WroughtIron).output(ingot, Steel).duration(600).fuelAmount(2).buildAndRegister();
        PBFRecipeBuilder.start().input(block, WroughtIron).output(block, Steel).duration(5600).fuelAmount(18).buildAndRegister();
    }

    private static void registerCokeOvenRecipes() {
        CokeOvenRecipeBuilder.start().input(OrePrefix.log, Materials.Wood).output(OreDictUnifier.get(OrePrefix.gem, Materials.Charcoal)).fluidOutput(Materials.Creosote.getFluid(250)).duration(900).buildAndRegister();
        CokeOvenRecipeBuilder.start().input(OrePrefix.gem, Materials.Coal).output(OreDictUnifier.get(OrePrefix.gem, Materials.Coke)).fluidOutput(Materials.Creosote.getFluid(500)).duration(900).buildAndRegister();
        CokeOvenRecipeBuilder.start().input(OrePrefix.block, Materials.Coal).output(OreDictUnifier.get(OrePrefix.block, Materials.Coke)).fluidOutput(Materials.Creosote.getFluid(4500)).duration(8100).buildAndRegister();
    }

    private static void registerStoneBricksRecipes() {
        //decorative blocks: normal variant -> brick variant
        registerBrickRecipe(MetaBlocks.CONCRETE, ConcreteVariant.LIGHT_CONCRETE, ConcreteVariant.LIGHT_BRICKS);
        registerBrickRecipe(MetaBlocks.CONCRETE, ConcreteVariant.DARK_CONCRETE, ConcreteVariant.DARK_BRICKS);
        registerBrickRecipe(MetaBlocks.GRANITE, GraniteVariant.BLACK_GRANITE, GraniteVariant.BLACK_GRANITE_BRICKS);
        registerBrickRecipe(MetaBlocks.GRANITE, GraniteVariant.RED_GRANITE, GraniteVariant.RED_GRANITE_BRICKS);
        registerBrickRecipe(MetaBlocks.MINERAL, MineralVariant.MARBLE, MineralVariant.MARBLE_BRICKS);
        registerBrickRecipe(MetaBlocks.MINERAL, MineralVariant.BASALT, MineralVariant.BASALT_BRICKS);

        //decorative blocks: normal chiseling -> different chiseling
        registerChiselingRecipes(MetaBlocks.CONCRETE);
        registerChiselingRecipes(MetaBlocks.GRANITE);
        registerChiselingRecipes(MetaBlocks.MINERAL);
    }

    private static final MaterialStack[] solderingList = {
        new MaterialStack(Tin, 2L),
        new MaterialStack(SolderingAlloy, 1L)
    };

    private static void registerCircuitRecipes() {
        //Wafer blast furnace recipes
        BLAST_RECIPES.recipeBuilder().duration(9000).EUt(120).input(dust, Silicon, 32).input(dustSmall, GalliumArsenide).notConsumable(new IntCircuitIngredient(1)).output(SILICON_BOULE).blastFurnaceTemp(1784).buildAndRegister();
        BLAST_RECIPES.recipeBuilder().duration(12000).EUt(480).input(dust, Silicon, 64).input(dust, Glowstone, 8).notConsumable(new IntCircuitIngredient(1)).fluidInputs(Nitrogen.getFluid(8000)).output(GLOWSTONE_BOULE).blastFurnaceTemp(2484).buildAndRegister();
        BLAST_RECIPES.recipeBuilder().duration(1500).EUt(1920).input(block, Silicon, 9).input(ingot, Naquadah).notConsumable(new IntCircuitIngredient(1)).fluidInputs(Argon.getFluid(8000)).output(NAQUADAH_BOULE).blastFurnaceTemp(5400).buildAndRegister();

        //Wafer engraving recipes
        LASER_ENGRAVER_RECIPES.recipeBuilder().duration(900).EUt(120).input(SILICON_WAFER).notConsumable(craftingLens, Color.Red).output(INTEGRATED_LOGIC_CIRCUIT_WAFER).buildAndRegister();
        LASER_ENGRAVER_RECIPES.recipeBuilder().duration(500).EUt(480).input(GLOWSTONE_WAFER).notConsumable(craftingLens, Color.Red).output(INTEGRATED_LOGIC_CIRCUIT_WAFER, 4).buildAndRegister();
        LASER_ENGRAVER_RECIPES.recipeBuilder().duration(200).EUt(1920).input(NAQUADAH_WAFER).notConsumable(craftingLens, Color.Red).output(INTEGRATED_LOGIC_CIRCUIT_WAFER, 8).buildAndRegister();

        LASER_ENGRAVER_RECIPES.recipeBuilder().duration(900).EUt(120).input(SILICON_WAFER).notConsumable(craftingLens, Color.Silver).output(RANDOM_ACCESS_MEMORY_WAFER).buildAndRegister();
        LASER_ENGRAVER_RECIPES.recipeBuilder().duration(500).EUt(480).input(GLOWSTONE_WAFER).notConsumable(craftingLens, Color.Silver).output(RANDOM_ACCESS_MEMORY_WAFER, 4).buildAndRegister();
        LASER_ENGRAVER_RECIPES.recipeBuilder().duration(200).EUt(1920).input(NAQUADAH_WAFER).notConsumable(craftingLens, Color.Silver).output(RANDOM_ACCESS_MEMORY_WAFER, 8).buildAndRegister();

        LASER_ENGRAVER_RECIPES.recipeBuilder().duration(500).EUt(480).input(GLOWSTONE_WAFER).notConsumable(craftingLens, Color.LightBlue).output(NAND_MEMORY_CHIP_WAFER).buildAndRegister();
        LASER_ENGRAVER_RECIPES.recipeBuilder().duration(200).EUt(1920).input(NAQUADAH_WAFER).notConsumable(craftingLens, Color.LightBlue).output(NAND_MEMORY_CHIP_WAFER, 4).buildAndRegister();

        LASER_ENGRAVER_RECIPES.recipeBuilder().duration(500).EUt(480).input(GLOWSTONE_WAFER).notConsumable(craftingLens, Color.Lime).output(NOR_MEMORY_CHIP_WAFER).buildAndRegister();
        LASER_ENGRAVER_RECIPES.recipeBuilder().duration(200).EUt(1920).input(NAQUADAH_WAFER).notConsumable(craftingLens, Color.Lime).output(NOR_MEMORY_CHIP_WAFER, 4).buildAndRegister();

        LASER_ENGRAVER_RECIPES.recipeBuilder().duration(900).EUt(120).input(SILICON_WAFER).notConsumable(craftingLens, Color.White).output(CENTRAL_PROCESSING_UNIT_WAFER).buildAndRegister();
        LASER_ENGRAVER_RECIPES.recipeBuilder().duration(500).EUt(480).input(GLOWSTONE_WAFER).notConsumable(craftingLens, Color.White).output(CENTRAL_PROCESSING_UNIT_WAFER, 4).buildAndRegister();
        LASER_ENGRAVER_RECIPES.recipeBuilder().duration(200).EUt(1920).input(NAQUADAH_WAFER).notConsumable(craftingLens, Color.White).output(CENTRAL_PROCESSING_UNIT_WAFER, 8).buildAndRegister();

        LASER_ENGRAVER_RECIPES.recipeBuilder().duration(200).EUt(1920).input(NAQUADAH_WAFER).notConsumable(craftingLens, Color.Yellow).output(SYSTEM_ON_CHIP_WAFER, 2).buildAndRegister();
        LASER_ENGRAVER_RECIPES.recipeBuilder().duration(200).EUt(1920).input(NAQUADAH_WAFER).notConsumable(craftingLens, Color.Orange).output(ADVANCED_SYSTEM_ON_CHIP_WAFER).buildAndRegister();

        LASER_ENGRAVER_RECIPES.recipeBuilder().duration(500).EUt(480).input(GLOWSTONE_WAFER).notConsumable(craftingLens, Color.Blue).output(POWER_INTEGRATED_CIRCUIT_WAFER).buildAndRegister();
        LASER_ENGRAVER_RECIPES.recipeBuilder().duration(200).EUt(1920).input(NAQUADAH_WAFER).notConsumable(craftingLens, Color.Blue).output(POWER_INTEGRATED_CIRCUIT_WAFER, 4).buildAndRegister();

        //Wafer chemical refining recipes
        CHEMICAL_RECIPES.recipeBuilder().duration(1200).EUt(1920).input(POWER_INTEGRATED_CIRCUIT_WAFER).input(dust, IndiumGalliumPhosphide, 2).fluidInputs(RedAlloy.getFluid(288)).output(HIGH_POWER_INTEGRATED_CIRCUIT_WAFER).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(400).EUt(480).input(CENTRAL_PROCESSING_UNIT_WAFER).input(CARBON_FIBERS, 16).fluidInputs(Glowstone.getFluid(576)).output(NANO_CENTRAL_PROCESSING_UNIT_WAFER).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(400).EUt(1920).input(NANO_CENTRAL_PROCESSING_UNIT_WAFER).input(QUANTUM_EYE, 2).fluidInputs(GalliumArsenide.getFluid(288)).output(QBIT_CENTRAL_PROCESSING_UNIT_WAFER).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(600).EUt(1920).input(NANO_CENTRAL_PROCESSING_UNIT_WAFER).input(dust, IndiumGalliumPhosphide).fluidInputs(Radon.getFluid(50)).output(QBIT_CENTRAL_PROCESSING_UNIT_WAFER).buildAndRegister();

        //Wafer cutting recipes
        CUTTER_RECIPES.recipeBuilder().duration(200).EUt(8).input(SILICON_BOULE).output(SILICON_WAFER, 16).buildAndRegister();
        CUTTER_RECIPES.recipeBuilder().duration(400).EUt(64).input(GLOWSTONE_BOULE).output(GLOWSTONE_WAFER, 32).buildAndRegister();
        CUTTER_RECIPES.recipeBuilder().duration(800).EUt(384).input(NAQUADAH_BOULE).output(NAQUADAH_WAFER, 64).buildAndRegister();

        CUTTER_RECIPES.recipeBuilder().duration(600).EUt(48).input(ADVANCED_SYSTEM_ON_CHIP_WAFER).output(ADVANCED_SYSTEM_ON_CHIP, 6).buildAndRegister();
        CUTTER_RECIPES.recipeBuilder().duration(600).EUt(48).input(SYSTEM_ON_CHIP_WAFER).output(SYSTEM_ON_CHIP, 6).buildAndRegister();
        CUTTER_RECIPES.recipeBuilder().duration(600).EUt(48).input(RANDOM_ACCESS_MEMORY_WAFER).output(RANDOM_ACCESS_MEMORY, 32).buildAndRegister();
        CUTTER_RECIPES.recipeBuilder().duration(600).EUt(48).input(QBIT_CENTRAL_PROCESSING_UNIT_WAFER).output(QBIT_CENTRAL_PROCESSING_UNIT, 5).buildAndRegister();
        CUTTER_RECIPES.recipeBuilder().duration(600).EUt(48).input(POWER_INTEGRATED_CIRCUIT_WAFER).output(POWER_INTEGRATED_CIRCUIT, 4).buildAndRegister();
        CUTTER_RECIPES.recipeBuilder().duration(600).EUt(48).input(HIGH_POWER_INTEGRATED_CIRCUIT_WAFER).output(HIGH_POWER_INTEGRATED_CIRCUIT, 2).buildAndRegister();
        CUTTER_RECIPES.recipeBuilder().duration(600).EUt(48).input(NOR_MEMORY_CHIP_WAFER).output(NOR_MEMORY_CHIP, 16).buildAndRegister();
        CUTTER_RECIPES.recipeBuilder().duration(600).EUt(48).input(NAND_MEMORY_CHIP_WAFER).output(NAND_MEMORY_CHIP, 32).buildAndRegister();
        CUTTER_RECIPES.recipeBuilder().duration(600).EUt(48).input(CENTRAL_PROCESSING_UNIT_WAFER).output(CENTRAL_PROCESSING_UNIT, 8).buildAndRegister();
        CUTTER_RECIPES.recipeBuilder().duration(600).EUt(48).input(INTEGRATED_LOGIC_CIRCUIT_WAFER).output(INTEGRATED_LOGIC_CIRCUIT, 8).buildAndRegister();
        CUTTER_RECIPES.recipeBuilder().duration(600).EUt(48).input(NANO_CENTRAL_PROCESSING_UNIT_WAFER).output(NANO_CENTRAL_PROCESSING_UNIT, 7).buildAndRegister();

        //circuit assembling recipes
        for (MaterialStack stack : solderingList) {
            IngotMaterial material = (IngotMaterial) stack.material;
            int multiplier = (int) stack.amount;
            ASSEMBLER_RECIPES.recipeBuilder().duration(200).EUt(8).inputs(MetaItems.PHENOLIC_BOARD.getStackForm(), MetaItems.INTEGRATED_LOGIC_CIRCUIT.getStackForm(), MetaItems.RESISTOR.getStackForm(2)).input(OrePrefix.wireFine, Materials.Copper).fluidInputs(material.getFluid(72 * multiplier)).outputs(MetaItems.BASIC_ELECTRONIC_CIRCUIT_LV.getStackForm()).buildAndRegister();
            ASSEMBLER_RECIPES.recipeBuilder().duration(200).EUt(8).inputs(MetaItems.PHENOLIC_BOARD.getStackForm(), MetaItems.INTEGRATED_LOGIC_CIRCUIT.getStackForm(), MetaItems.SMD_RESISTOR.getStackForm(2)).input(OrePrefix.wireFine, Materials.Copper).fluidInputs(material.getFluid(72 * multiplier)).outputs(MetaItems.BASIC_ELECTRONIC_CIRCUIT_LV.getStackForm()).buildAndRegister();

            ASSEMBLER_RECIPES.recipeBuilder().duration(200).EUt(60).inputs(MetaItems.PLASTIC_BOARD.getStackForm(), MetaItems.CENTRAL_PROCESSING_UNIT.getStackForm(4), MetaItems.RESISTOR.getStackForm(4), MetaItems.CAPACITOR.getStackForm(4), MetaItems.TRANSISTOR.getStackForm(4)).input(OrePrefix.wireFine, Materials.Copper, 2).fluidInputs(material.getFluid(L / 2 * multiplier)).outputs(ADVANCED_CIRCUIT_PARTS_LV.getStackForm(4)).buildAndRegister();
            ASSEMBLER_RECIPES.recipeBuilder().duration(200).EUt(60).inputs(MetaItems.PLASTIC_BOARD.getStackForm(), MetaItems.CENTRAL_PROCESSING_UNIT.getStackForm(4), MetaItems.SMD_RESISTOR.getStackForm(4), MetaItems.SMD_CAPACITOR.getStackForm(4), MetaItems.SMD_TRANSISTOR.getStackForm(4)).input(OrePrefix.wireFine, Materials.Copper, 2).fluidInputs(material.getFluid(L / 2 * multiplier)).outputs(ADVANCED_CIRCUIT_PARTS_LV.getStackForm(4)).buildAndRegister();
            ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(600).inputs(MetaItems.PLASTIC_BOARD.getStackForm(), MetaItems.SYSTEM_ON_CHIP.getStackForm(4)).input(OrePrefix.wireFine, Materials.Copper, 2).fluidInputs(material.getFluid(L / 2 * multiplier)).outputs(ADVANCED_CIRCUIT_PARTS_LV.getStackForm(4)).buildAndRegister();

            ASSEMBLER_RECIPES.recipeBuilder().duration(400).EUt(16).inputs(MetaItems.PHENOLIC_BOARD.getStackForm(), MetaItems.BASIC_ELECTRONIC_CIRCUIT_LV.getStackForm(3), MetaItems.RESISTOR.getStackForm(4)).input(OrePrefix.wireFine, Materials.Gold, 8).fluidInputs(material.getFluid(L / 2 * multiplier)).outputs(MetaItems.GOOD_INTEGRATED_CIRCUIT_MV.getStackForm()).buildAndRegister();
            ASSEMBLER_RECIPES.recipeBuilder().duration(400).EUt(16).inputs(MetaItems.PHENOLIC_BOARD.getStackForm(), MetaItems.BASIC_ELECTRONIC_CIRCUIT_LV.getStackForm(3), MetaItems.SMD_RESISTOR.getStackForm(4)).input(OrePrefix.wireFine, Materials.Gold, 8).fluidInputs(material.getFluid(L / 2 * multiplier)).outputs(MetaItems.GOOD_INTEGRATED_CIRCUIT_MV.getStackForm()).buildAndRegister();

            ASSEMBLER_RECIPES.recipeBuilder().duration(200).EUt(60).inputs(MetaItems.PLASTIC_BOARD.getStackForm(), MetaItems.CENTRAL_PROCESSING_UNIT.getStackForm(), MetaItems.RESISTOR.getStackForm(2), MetaItems.CAPACITOR.getStackForm(2), MetaItems.TRANSISTOR.getStackForm(2)).input(OrePrefix.wireFine, Materials.RedAlloy, 2).fluidInputs(material.getFluid(72 * multiplier)).outputs(MetaItems.ADVANCED_CIRCUIT_MV.getStackForm()).buildAndRegister();
            ASSEMBLER_RECIPES.recipeBuilder().duration(200).EUt(60).inputs(MetaItems.PLASTIC_BOARD.getStackForm(), MetaItems.CENTRAL_PROCESSING_UNIT.getStackForm(), MetaItems.SMD_RESISTOR.getStackForm(2), MetaItems.SMD_CAPACITOR.getStackForm(2), MetaItems.SMD_TRANSISTOR.getStackForm(2)).input(OrePrefix.wireFine, Materials.RedAlloy, 2).fluidInputs(material.getFluid(72 * multiplier)).outputs(MetaItems.ADVANCED_CIRCUIT_MV.getStackForm()).buildAndRegister();
            ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(2400).inputs(MetaItems.PLASTIC_BOARD.getStackForm(), MetaItems.SYSTEM_ON_CHIP.getStackForm()).input(OrePrefix.wireFine, Materials.RedAlloy, 2).fluidInputs(material.getFluid(72 * multiplier)).outputs(MetaItems.ADVANCED_CIRCUIT_MV.getStackForm()).buildAndRegister();

            ASSEMBLER_RECIPES.recipeBuilder().duration(400).EUt(90).inputs(MetaItems.PLASTIC_BOARD.getStackForm(), MetaItems.ADVANCED_CIRCUIT_MV.getStackForm(2), MetaItems.SMALL_COIL.getStackForm(4), MetaItems.CAPACITOR.getStackForm(4), MetaItems.RANDOM_ACCESS_MEMORY.getStackForm(4)).input(OrePrefix.wireFine, Materials.RedAlloy, 12).fluidInputs(material.getFluid(144 * multiplier)).outputs(MetaItems.PROCESSOR_ASSEMBLY_HV.getStackForm()).buildAndRegister();
            ASSEMBLER_RECIPES.recipeBuilder().duration(400).EUt(90).inputs(MetaItems.PLASTIC_BOARD.getStackForm(), MetaItems.ADVANCED_CIRCUIT_MV.getStackForm(2), MetaItems.SMALL_COIL.getStackForm(4), MetaItems.SMD_CAPACITOR.getStackForm(4), MetaItems.RANDOM_ACCESS_MEMORY.getStackForm(4)).input(OrePrefix.wireFine, Materials.RedAlloy, 12).fluidInputs(material.getFluid(144 * multiplier)).outputs(MetaItems.PROCESSOR_ASSEMBLY_HV.getStackForm()).buildAndRegister();

            ASSEMBLER_RECIPES.recipeBuilder().duration(200).EUt(480).inputs(MetaItems.EPOXY_BOARD.getStackForm(), MetaItems.NANO_CENTRAL_PROCESSING_UNIT.getStackForm(), MetaItems.SMD_RESISTOR.getStackForm(2), MetaItems.SMD_CAPACITOR.getStackForm(2), MetaItems.SMD_TRANSISTOR.getStackForm(2)).input(OrePrefix.wireFine, Materials.Electrum, 2).fluidInputs(material.getFluid(72 * multiplier)).outputs(MetaItems.NANO_PROCESSOR_HV.getStackForm()).buildAndRegister();
            ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(9600).inputs(MetaItems.EPOXY_BOARD.getStackForm(), MetaItems.SYSTEM_ON_CHIP.getStackForm()).input(OrePrefix.wireFine, Materials.Electrum, 2).fluidInputs(material.getFluid(72 * multiplier)).outputs(MetaItems.NANO_PROCESSOR_HV.getStackForm()).buildAndRegister();

            ASSEMBLER_RECIPES.recipeBuilder().duration(400).EUt(480).inputs(MetaItems.EPOXY_BOARD.getStackForm(), MetaItems.NANO_PROCESSOR_HV.getStackForm(2), MetaItems.SMALL_COIL.getStackForm(4), MetaItems.SMD_CAPACITOR.getStackForm(4), MetaItems.RANDOM_ACCESS_MEMORY.getStackForm(4)).input(OrePrefix.wireFine, Materials.Electrum, 6).fluidInputs(material.getFluid(144 * multiplier)).outputs(MetaItems.NANO_PROCESSOR_ASSEMBLY_EV.getStackForm()).buildAndRegister();

            ASSEMBLER_RECIPES.recipeBuilder().duration(200).EUt(1960).inputs(MetaItems.FIBER_BOARD.getStackForm(), MetaItems.QBIT_CENTRAL_PROCESSING_UNIT.getStackForm(), MetaItems.NANO_CENTRAL_PROCESSING_UNIT.getStackForm(), MetaItems.SMD_CAPACITOR.getStackForm(2), MetaItems.SMD_TRANSISTOR.getStackForm(2)).input(OrePrefix.wireFine, Materials.Platinum, 2).fluidInputs(material.getFluid(72 * multiplier)).outputs(MetaItems.QUANTUM_PROCESSOR_EV.getStackForm()).buildAndRegister();
            ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(32000).inputs(MetaItems.FIBER_BOARD.getStackForm(), MetaItems.ADVANCED_SYSTEM_ON_CHIP.getStackForm()).input(OrePrefix.wireFine, Materials.Platinum, 2).fluidInputs(material.getFluid(72 * multiplier)).outputs(MetaItems.QUANTUM_PROCESSOR_EV.getStackForm()).buildAndRegister();

            ASSEMBLER_RECIPES.recipeBuilder().duration(400).EUt(1960).inputs(MetaItems.FIBER_BOARD.getStackForm(), MetaItems.QUANTUM_PROCESSOR_EV.getStackForm(2), MetaItems.SMALL_COIL.getStackForm(4), MetaItems.SMD_CAPACITOR.getStackForm(4), MetaItems.RANDOM_ACCESS_MEMORY.getStackForm(4)).input(OrePrefix.wireFine, Materials.Platinum, 6).fluidInputs(material.getFluid(144 * multiplier)).outputs(MetaItems.DATA_CONTROL_CIRCUIT_IV.getStackForm()).buildAndRegister();
            ASSEMBLER_RECIPES.recipeBuilder().duration(200).EUt(7600).inputs(MetaItems.MULTILAYER_FIBER_BOARD.getStackForm(), MetaItems.CRYSTAL_CENTRAL_PROCESSING_UNIT.getStackForm(), MetaItems.NANO_CENTRAL_PROCESSING_UNIT.getStackForm(), MetaItems.SMD_CAPACITOR.getStackForm(2), MetaItems.SMD_TRANSISTOR.getStackForm(2)).input(OrePrefix.wireFine, Materials.NiobiumTitanium, 2).fluidInputs(material.getFluid(72 * multiplier)).outputs(MetaItems.CRYSTAL_PROCESSOR_IV.getStackForm()).buildAndRegister();
            ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(31900).inputs(MetaItems.MULTILAYER_FIBER_BOARD.getStackForm(), MetaItems.CRYSTAL_SYSTEM_ON_CHIP.getStackForm()).input(OrePrefix.wireFine, Materials.NiobiumTitanium, 2).fluidInputs(material.getFluid(72 * multiplier)).outputs(MetaItems.CRYSTAL_PROCESSOR_IV.getStackForm()).buildAndRegister();

            ASSEMBLER_RECIPES.recipeBuilder().duration(400).EUt(7600).inputs(MetaItems.MULTILAYER_FIBER_BOARD.getStackForm(), MetaItems.CRYSTAL_PROCESSOR_IV.getStackForm(2), MetaItems.SMALL_COIL.getStackForm(4), MetaItems.SMD_CAPACITOR.getStackForm(4), MetaItems.RANDOM_ACCESS_MEMORY.getStackForm(4)).input(OrePrefix.wireFine, Materials.NiobiumTitanium, 6).fluidInputs(material.getFluid(144 * multiplier)).outputs(MetaItems.ENERGY_FLOW_CIRCUIT_LUV.getStackForm()).buildAndRegister();
            ASSEMBLER_RECIPES.recipeBuilder().duration(200).EUt(32800).inputs(MetaItems.WETWARE_BOARD.getStackForm(), MetaItems.CRYSTAL_CENTRAL_PROCESSING_UNIT.getStackForm(), MetaItems.NANO_CENTRAL_PROCESSING_UNIT.getStackForm(), MetaItems.SMD_CAPACITOR.getStackForm(2), MetaItems.SMD_TRANSISTOR.getStackForm(2)).input(OrePrefix.wireFine, Materials.YttriumBariumCuprate, 2).fluidInputs(material.getFluid(72 * multiplier)).outputs(MetaItems.WETWARE_PROCESSOR_LUV.getStackForm()).buildAndRegister();

            ASSEMBLER_RECIPES.recipeBuilder().duration(400).EUt(34400).inputs(MetaItems.WETWARE_BOARD.getStackForm(), MetaItems.WETWARE_PROCESSOR_LUV.getStackForm(2), MetaItems.SMALL_COIL.getStackForm(4), MetaItems.SMD_CAPACITOR.getStackForm(4), MetaItems.RANDOM_ACCESS_MEMORY.getStackForm(4)).input(OrePrefix.wireFine, Materials.YttriumBariumCuprate, 6).fluidInputs(material.getFluid(144 * multiplier)).outputs(MetaItems.WETWARE_PROCESSOR_ASSEMBLY_ZPM.getStackForm()).buildAndRegister();
            ASSEMBLER_RECIPES.recipeBuilder().duration(400).EUt(34400).inputs(MetaItems.WETWARE_BOARD.getStackForm(2), MetaItems.WETWARE_PROCESSOR_ASSEMBLY_ZPM.getStackForm(3), MetaItems.SMD_DIODE.getStackForm(4), MetaItems.NOR_MEMORY_CHIP.getStackForm(4), MetaItems.RANDOM_ACCESS_MEMORY.getStackForm(4)).input(OrePrefix.wireFine, Materials.YttriumBariumCuprate, 6).fluidInputs(material.getFluid(144 * multiplier)).outputs(MetaItems.WETWARE_SUPER_COMPUTER_UV.getStackForm()).buildAndRegister();

            ASSEMBLER_RECIPES.recipeBuilder().duration(512).EUt(1024).inputs(MetaItems.FIBER_BOARD.getStackForm(), MetaItems.POWER_INTEGRATED_CIRCUIT.getStackForm(4), MetaItems.ENGRAVED_LAPOTRON_CHIP.getStackForm(18), MetaItems.NANO_CENTRAL_PROCESSING_UNIT.getStackForm()).input(OrePrefix.wireFine, Materials.Platinum, 16).fluidInputs(material.getFluid(144 * multiplier)).outputs(MetaItems.ENERGY_LAPOTRONIC_ORB.getStackForm()).buildAndRegister();
            ASSEMBLER_RECIPES.recipeBuilder().duration(1024).EUt(4096).inputs(MetaItems.FIBER_BOARD.getStackForm(), MetaItems.HIGH_POWER_INTEGRATED_CIRCUIT.getStackForm(4), MetaItems.ENERGY_LAPOTRONIC_ORB.getStackForm(8), MetaItems.QBIT_CENTRAL_PROCESSING_UNIT.getStackForm()).input(OrePrefix.wireFine, Materials.Platinum, 16).input(OrePrefix.plate, Materials.Europium, 4).fluidInputs(material.getFluid(144 * multiplier)).outputs(MetaItems.ENERGY_LAPOTRONIC_ORB2.getStackForm()).buildAndRegister();
            ASSEMBLER_RECIPES.recipeBuilder().duration(400).EUt(90).inputs(MetaItems.PLASTIC_BOARD.getStackForm(), MetaItems.ADVANCED_CIRCUIT_MV.getStackForm(), MetaItems.NAND_MEMORY_CHIP.getStackForm(32), MetaItems.RANDOM_ACCESS_MEMORY.getStackForm(4)).input(OrePrefix.wireFine, Materials.RedAlloy, 8).input(OrePrefix.plate, Materials.Polyethylene, 4).fluidInputs(material.getFluid(144 * multiplier)).outputs(MetaItems.TOOL_DATA_STICK.getStackForm()).buildAndRegister();
            ASSEMBLER_RECIPES.recipeBuilder().duration(400).EUt(1200).inputs(MetaItems.EPOXY_BOARD.getStackForm(), MetaItems.NANO_PROCESSOR_HV.getStackForm(), MetaItems.RANDOM_ACCESS_MEMORY.getStackForm(4), MetaItems.NOR_MEMORY_CHIP.getStackForm(32), MetaItems.NAND_MEMORY_CHIP.getStackForm(64)).input(OrePrefix.wireFine, Materials.Platinum, 32).fluidInputs(material.getFluid(144 * multiplier)).outputs(MetaItems.TOOL_DATA_ORB.getStackForm()).buildAndRegister();
        }

        //circuit components recipes
        ModHandler.addShapedRecipe("vacuum_tube_wire", MetaItems.VACUUM_TUBE.getStackForm(), "PTP", "WWW", 'P', new ItemStack(Items.PAPER), 'T', MetaItems.GLASS_TUBE.getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtSingle, Materials.Copper));
        ModHandler.addShapedRecipe("vacuum_tube_wire_fine", MetaItems.VACUUM_TUBE.getStackForm(), "PTP", "WWW", 'P', new ItemStack(Items.PAPER), 'T', MetaItems.GLASS_TUBE.getStackForm(), 'W', new UnificationEntry(OrePrefix.wireFine, Materials.Copper));
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(120).EUt(8).inputs(MetaItems.GLASS_TUBE.getStackForm(), new ItemStack(Items.PAPER, 2)).input(OrePrefix.wireGtSingle, Materials.Copper, 2).outputs(MetaItems.VACUUM_TUBE.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(120).EUt(8).inputs(MetaItems.GLASS_TUBE.getStackForm(), new ItemStack(Items.PAPER, 2)).input(OrePrefix.wireFine, Materials.Copper, 2).outputs(MetaItems.VACUUM_TUBE.getStackForm()).buildAndRegister();
        ModHandler.addShapedRecipe("basic_circuit", MetaItems.BASIC_CIRCUIT_LV.getStackForm(), "RPR", "VBV", "CCC", 'R', MetaItems.RESISTOR.getStackForm(), 'P', new UnificationEntry(OrePrefix.plate, Materials.Steel), 'V', MetaItems.VACUUM_TUBE.getStackForm(), 'B', MetaItems.COATED_BOARD.getStackForm(), 'C', new UnificationEntry(OrePrefix.cableGtSingle, Materials.RedAlloy));
        ModHandler.addShapedRecipe("good_circuit", MetaItems.GOOD_INTEGRATED_CIRCUIT_MV.getStackForm(), "RCP", "CDC", "PCR", 'R', new UnificationEntry(OrePrefix.cableGtSingle, Materials.RedAlloy), 'P', new UnificationEntry(OrePrefix.plate, Materials.Steel), 'C', MetaItems.BASIC_CIRCUIT_LV.getStackForm(), 'D', MetaItems.DIODE.getStackForm());

        RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder().duration(160).EUt(8).input(OrePrefix.dust, Materials.Glass).notConsumable(MetaItems.SHAPE_MOLD_BALL).outputs(MetaItems.GLASS_TUBE.getStackForm()).buildAndRegister();
        ModHandler.addShapedRecipe("resistor_wire", MetaItems.RESISTOR.getStackForm(3), " P ", "WCW", " P ", 'P', new ItemStack(Items.PAPER), 'W', new UnificationEntry(OrePrefix.wireGtSingle, Materials.Copper), 'C', new UnificationEntry(OrePrefix.dust, Materials.Coal));
        ModHandler.addShapedRecipe("resistor_wire_fine", MetaItems.RESISTOR.getStackForm(3), " P ", "WCW", " P ", 'P', new ItemStack(Items.PAPER), 'W', new UnificationEntry(OrePrefix.wireFine, Materials.Copper), 'C', new UnificationEntry(OrePrefix.dust, Materials.Coal));
        ModHandler.addShapedRecipe("resistor_wire_charcoal", MetaItems.RESISTOR.getStackForm(3), " P ", "WCW", " P ", 'P', new ItemStack(Items.PAPER), 'W', new UnificationEntry(OrePrefix.wireGtSingle, Materials.Copper), 'C', new UnificationEntry(OrePrefix.dust, Materials.Charcoal));
        ModHandler.addShapedRecipe("resistor_wire_fine_charcoal", MetaItems.RESISTOR.getStackForm(3), " P ", "WCW", " P ", 'P', new ItemStack(Items.PAPER), 'W', new UnificationEntry(OrePrefix.wireFine, Materials.Copper), 'C', new UnificationEntry(OrePrefix.dust, Materials.Charcoal));
        ModHandler.addShapedRecipe("resistor_wire_carbon", MetaItems.RESISTOR.getStackForm(3), " P ", "WCW", " P ", 'P', new ItemStack(Items.PAPER), 'W', new UnificationEntry(OrePrefix.wireGtSingle, Materials.Copper), 'C', new UnificationEntry(OrePrefix.dust, Materials.Carbon));
        ModHandler.addShapedRecipe("resistor_wire_fine_carbon", MetaItems.RESISTOR.getStackForm(3), " P ", "WCW", " P ", 'P', new ItemStack(Items.PAPER), 'W', new UnificationEntry(OrePrefix.wireFine, Materials.Copper), 'C', new UnificationEntry(OrePrefix.dust, Materials.Carbon));
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(160).EUt(6).input(OrePrefix.dust, Materials.Coal).input(OrePrefix.wireFine, Materials.Copper, 4).outputs(MetaItems.RESISTOR.getStackForm(12)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(160).EUt(6).input(OrePrefix.dust, Materials.Charcoal).input(OrePrefix.wireFine, Materials.Copper, 4).outputs(MetaItems.RESISTOR.getStackForm(12)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(160).EUt(6).input(OrePrefix.dust, Materials.Carbon).input(OrePrefix.wireFine, Materials.Copper, 4).outputs(MetaItems.RESISTOR.getStackForm(12)).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(80).EUt(96).input(OrePrefix.plate, Materials.Polyethylene).input(OrePrefix.foil, Materials.Aluminium, 2).outputs(MetaItems.CAPACITOR.getStackForm(2)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(80).EUt(24).input(OrePrefix.plate, Materials.Silicon).input(OrePrefix.wireFine, Materials.Tin, 6).fluidInputs(Materials.Polyethylene.getFluid(144)).outputs(MetaItems.TRANSISTOR.getStackForm(8)).buildAndRegister();

        ModHandler.addShapedRecipe("diode_wafer", MetaItems.DIODE.getStackForm(1), "DG ", "TWT", "DG ", 'D', "dyeBlack", 'G', "paneGlass", 'T', new UnificationEntry(OrePrefix.wireGtSingle, Materials.Tin), 'W', MetaItems.SILICON_WAFER.getStackForm(1));
        ModHandler.addShapedRecipe("diode_wafer_fine", MetaItems.DIODE.getStackForm(1), "DG ", "TWT", "DG ", 'D', "dyeBlack", 'G', "paneGlass", 'T', new UnificationEntry(OrePrefix.wireFine, Materials.Tin), 'W', MetaItems.SILICON_WAFER.getStackForm(1));
        ModHandler.addShapedRecipe("diode", MetaItems.DIODE.getStackForm(4), "DG ", "TWT", "DG ", 'D', "dyeBlack", 'G', "paneGlass", 'T', new UnificationEntry(OrePrefix.wireGtSingle, Materials.Tin), 'W', new UnificationEntry(OrePrefix.dustTiny, Materials.Gallium));
        ModHandler.addShapedRecipe("diode_wire", MetaItems.DIODE.getStackForm(4), "DG ", "TWT", "DG ", 'D', "dyeBlack", 'G', "paneGlass", 'T', new UnificationEntry(OrePrefix.wireFine, Materials.Tin), 'W', new UnificationEntry(OrePrefix.dustTiny, Materials.Gallium));
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(400).EUt(48).input(OrePrefix.wireFine, Materials.AnnealedCopper, 4).input(OrePrefix.dustSmall, Materials.Gallium).fluidInputs(Materials.Polyethylene.getFluid(288)).outputs(MetaItems.DIODE.getStackForm(16)).buildAndRegister();

        ModHandler.addShapedRecipe("small_coil_copper_steel", MetaItems.SMALL_COIL.getStackForm(2), "WWW", "WBW", "WWW", 'W', new UnificationEntry(OrePrefix.wireFine, Materials.Copper), 'B', new UnificationEntry(OrePrefix.bolt, Materials.Steel));
        ModHandler.addShapedRecipe("small_coil_annealed_copper_steel", MetaItems.SMALL_COIL.getStackForm(2), "WWW", "WBW", "WWW", 'W', new UnificationEntry(OrePrefix.wireFine, Materials.AnnealedCopper), 'B', new UnificationEntry(OrePrefix.bolt, Materials.Steel));
        ModHandler.addShapedRecipe("small_coil_copper_ferrite", MetaItems.SMALL_COIL.getStackForm(4), "WWW", "WBW", "WWW", 'W', new UnificationEntry(OrePrefix.wireFine, Materials.Copper), 'B', new UnificationEntry(OrePrefix.bolt, Materials.NickelZincFerrite));
        ModHandler.addShapedRecipe("small_coil_annealed_copper_ferrite", MetaItems.SMALL_COIL.getStackForm(4), "WWW", "WBW", "WWW", 'W', new UnificationEntry(OrePrefix.wireFine, Materials.AnnealedCopper), 'B', new UnificationEntry(OrePrefix.bolt, Materials.NickelZincFerrite));

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(80).EUt(8).input(OrePrefix.wireFine, Materials.Copper).input(OrePrefix.bolt, Materials.Steel).outputs(MetaItems.SMALL_COIL.getStackForm(2)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(80).EUt(8).input(OrePrefix.wireFine, Materials.AnnealedCopper).input(OrePrefix.bolt, Materials.Steel).outputs(MetaItems.SMALL_COIL.getStackForm(2)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(80).EUt(8).input(OrePrefix.wireFine, Materials.Copper).input(OrePrefix.bolt, Materials.NickelZincFerrite).outputs(MetaItems.SMALL_COIL.getStackForm(4)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(80).EUt(8).input(OrePrefix.wireFine, Materials.AnnealedCopper).input(OrePrefix.bolt, Materials.NickelZincFerrite).outputs(MetaItems.SMALL_COIL.getStackForm(4)).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(80).EUt(96).input(OrePrefix.dust, Materials.Carbon).input(OrePrefix.wireFine, Materials.Electrum, 4).fluidInputs(Materials.Polyethylene.getFluid(144)).outputs(MetaItems.SMD_RESISTOR.getStackForm(24)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(400).EUt(120).input(OrePrefix.dustSmall, Materials.Gallium).input(OrePrefix.wireFine, Materials.Platinum, 4).fluidInputs(Materials.Polyethylene.getFluid(288)).outputs(MetaItems.SMD_DIODE.getStackForm(32)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(80).EUt(96).input(OrePrefix.plate, Materials.Gallium).input(OrePrefix.wireFine, Materials.AnnealedCopper, 6).fluidInputs(Materials.Polyethylene.getFluid(288)).outputs(MetaItems.SMD_TRANSISTOR.getStackForm(32)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(120).input(OrePrefix.foil, Materials.PolyvinylChloride, 4).input(OrePrefix.foil, Materials.Aluminium).fluidInputs(Materials.Polyethylene.getFluid(36)).outputs(MetaItems.SMD_CAPACITOR.getStackForm(16)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(60).EUt(120).input(OrePrefix.foil, Materials.SiliconeRubber, 4).input(OrePrefix.foil, Materials.Aluminium).fluidInputs(Materials.Polyethylene.getFluid(36)).outputs(MetaItems.SMD_CAPACITOR.getStackForm(16)).buildAndRegister();

        RecipeMaps.AUTOCLAVE_RECIPES.recipeBuilder().duration(150).EUt(5).input(OrePrefix.dust, Materials.Carbon, 4).fluidInputs(Materials.Palladium.getFluid(1)).chancedOutput(MetaItems.CARBON_FIBERS.getStackForm(2), 9000, 1000).buildAndRegister();
        RecipeMaps.AUTOCLAVE_RECIPES.recipeBuilder().duration(150).EUt(5).input(OrePrefix.dust, Materials.Carbon, 4).fluidInputs(Materials.Platinum.getFluid(1)).chancedOutput(MetaItems.CARBON_FIBERS.getStackForm(2), 5000, 5000).buildAndRegister();
        RecipeMaps.AUTOCLAVE_RECIPES.recipeBuilder().duration(150).EUt(5).input(OrePrefix.dust, Materials.Carbon, 4).fluidInputs(Materials.Lutetium.getFluid(1)).chancedOutput(MetaItems.CARBON_FIBERS.getStackForm(2), 3333, 3334).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(4000).EUt(120).inputs(MetaItems.PLATE_IRIDIUM_ALLOY.getStackForm()).input(OrePrefix.plate, Materials.Beryllium, 32).input(OrePrefix.plate, Materials.TungstenCarbide, 4).fluidInputs(Materials.TinAlloy.getFluid(L * 32)).outputs(MetaItems.NEUTRON_REFLECTOR.getStackForm()).buildAndRegister();

        //circuit board recipes
        ModHandler.addShapedRecipe("coated_board", MetaItems.COATED_BOARD.getStackForm(3), " R ", "PPP", " R ", 'R', MetaItems.RUBBER_DROP.getStackForm(), 'P', new UnificationEntry(OrePrefix.plate, Materials.Wood));
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(160).EUt(8).input(OrePrefix.plate, Materials.Wood, 8).inputs(MetaItems.RUBBER_DROP.getStackForm()).fluidInputs(Materials.Glue.getFluid(100)).outputs(MetaItems.COATED_BOARD.getStackForm(8)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(30).EUt(8).input(OrePrefix.dust, Materials.Wood).notConsumable(MetaItems.SHAPE_MOLD_PLATE.getStackForm()).fluidInputs(Materials.Glue.getFluid(100)).outputs(MetaItems.PHENOLIC_BOARD.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(30).EUt(8).input(OrePrefix.dust, Materials.Wood).notConsumable(MetaItems.SHAPE_MOLD_PLATE.getStackForm()).fluidInputs(Materials.BisphenolA.getFluid(100)).outputs(MetaItems.PHENOLIC_BOARD.getStackForm(4)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(600).EUt(10).input(OrePrefix.plate, Materials.Polyethylene).input(OrePrefix.foil, Materials.Copper).fluidInputs(Materials.SulfuricAcid.getFluid(125)).outputs(MetaItems.PLASTIC_BOARD.getStackForm()).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(600).EUt(10).input(OrePrefix.plate, Materials.PolyvinylChloride).input(OrePrefix.foil, Materials.Copper).fluidInputs(Materials.SulfuricAcid.getFluid(125)).outputs(MetaItems.PLASTIC_BOARD.getStackForm(2)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(600).EUt(10).input(OrePrefix.plate, Materials.Polytetrafluoroethylene).input(OrePrefix.foil, Materials.Copper).fluidInputs(Materials.SulfuricAcid.getFluid(125)).outputs(MetaItems.PLASTIC_BOARD.getStackForm(4)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(500).EUt(10).input(OrePrefix.plate, Materials.Epoxy).input(OrePrefix.foil, Materials.Copper).fluidInputs(Materials.SulfuricAcid.getFluid(125)).outputs(MetaItems.EPOXY_BOARD.getStackForm()).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(500).EUt(10).input(OrePrefix.plate, Materials.ReinforcedEpoxyResin).input(OrePrefix.foil, Materials.Copper).fluidInputs(Materials.SulfuricAcid.getFluid(125)).outputs(MetaItems.FIBER_BOARD.getStackForm()).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(100).EUt(480).inputs(MetaItems.FIBER_BOARD.getStackForm()).input(OrePrefix.foil, Materials.Electrum, 16).fluidInputs(Materials.SulfuricAcid.getFluid(250)).outputs(MetaItems.MULTILAYER_FIBER_BOARD.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(400).EUt(480).inputs(MetaItems.MULTILAYER_FIBER_BOARD.getStackForm()).input(OrePrefix.circuit, Tier.Good).inputs(MetaItems.ELECTRIC_PUMP_LV.getStackForm(), MetaItems.SENSOR_LV.getStackForm(), MetaItems.PETRI_DISH.getStackForm()).fluidInputs(Materials.SterileGrowthMedium.getFluid(250)).outputs(MetaItems.WETWARE_BOARD.getStackForm()).buildAndRegister();

        RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder().duration(100).EUt(32000).inputs(MetaItems.CRYSTAL_CENTRAL_PROCESSING_UNIT.getStackForm()).notConsumable(OrePrefix.craftingLens, Color.Blue).outputs(MetaItems.CRYSTAL_SYSTEM_ON_CHIP.getStackForm()).buildAndRegister();
        RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder().duration(100).EUt(7600).inputs(MetaItems.ENGRAVED_CRYSTAL_CHIP.getStackForm()).notConsumable(OrePrefix.craftingLens, Color.Lime).outputs(MetaItems.CRYSTAL_CENTRAL_PROCESSING_UNIT.getStackForm()).buildAndRegister();
        RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder().duration(256).EUt(480).inputs(MetaItems.LAPOTRON_CRYSTAL.getStackForm()).notConsumable(OrePrefix.craftingLens, Color.Blue).outputs(MetaItems.ENGRAVED_LAPOTRON_CHIP.getStackForm(3)).buildAndRegister();

        RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(240).EUt(16).inputs(MetaItems.GLASS_FIBER.getStackForm()).fluidInputs(Materials.Epoxy.getFluid(144)).outputs(OreDictUnifier.get(OrePrefix.plate, Materials.ReinforcedEpoxyResin)).buildAndRegister();
        RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(240).EUt(16).inputs(MetaItems.CARBON_FIBERS.getStackForm()).fluidInputs(Materials.Epoxy.getFluid(144)).outputs(OreDictUnifier.get(OrePrefix.plate, Materials.ReinforcedEpoxyResin)).buildAndRegister();
        RecipeMaps.EXTRUDER_RECIPES.recipeBuilder().duration(160).EUt(96).input(OrePrefix.ingot, Materials.BorosilicateGlass).notConsumable(MetaItems.SHAPE_EXTRUDER_WIRE.getStackForm()).outputs(MetaItems.GLASS_FIBER.getStackForm(8)).buildAndRegister();

        RecipeMaps.AUTOCLAVE_RECIPES.recipeBuilder().duration(12000).EUt(320).input(OrePrefix.gemExquisite, Materials.Emerald).fluidInputs(Materials.Europium.getFluid(L / 9)).chancedOutput(MetaItems.RAW_CRYSTAL_CHIP.getStackForm(), 1000, 2000).buildAndRegister();
        RecipeMaps.AUTOCLAVE_RECIPES.recipeBuilder().duration(12000).EUt(320).input(OrePrefix.gemExquisite, Materials.Olivine).fluidInputs(Materials.Europium.getFluid(L / 9)).chancedOutput(MetaItems.RAW_CRYSTAL_CHIP.getStackForm(), 1000, 2000).buildAndRegister();

        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(900).EUt(480).blastFurnaceTemp(5000).input(OrePrefix.plate, Materials.Emerald).inputs(MetaItems.RAW_CRYSTAL_CHIP.getStackForm(1)).fluidInputs(Materials.Helium.getFluid(1000)).outputs(MetaItems.ENGRAVED_CRYSTAL_CHIP.getStackForm(1)).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(900).EUt(480).blastFurnaceTemp(5000).input(OrePrefix.plate, Materials.Olivine).inputs(MetaItems.RAW_CRYSTAL_CHIP.getStackForm(1)).fluidInputs(Materials.Helium.getFluid(1000)).outputs(MetaItems.ENGRAVED_CRYSTAL_CHIP.getStackForm(1)).buildAndRegister();

        RecipeMaps.CUTTER_RECIPES.recipeBuilder().EUt(60).duration(960).inputs(MetaItems.CRYSTAL_CENTRAL_PROCESSING_UNIT.getStackForm(1)).outputs(MetaItems.RAW_CRYSTAL_CHIP.getStackForm(2)).buildAndRegister();

        RecipeMaps.FLUID_SOLIDFICATION_RECIPES.recipeBuilder().duration(160).EUt(16).notConsumable(MetaItems.SHAPE_MOLD_CYLINDER).fluidInputs(Materials.Polystyrene.getFluid(L / 4)).outputs(MetaItems.PETRI_DISH.getStackForm()).buildAndRegister();
        RecipeMaps.FLUID_SOLIDFICATION_RECIPES.recipeBuilder().duration(160).EUt(16).notConsumable(MetaItems.SHAPE_MOLD_CYLINDER).fluidInputs(Materials.Polytetrafluoroethylene.getFluid(L / 4)).outputs(MetaItems.PETRI_DISH.getStackForm()).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(30).EUt(4).input(OrePrefix.dust, Materials.Tantalum).input(OrePrefix.foil, Materials.Manganese).fluidInputs(Materials.Polyethylene.getFluid(144)).outputs(MetaItems.BATTERY_RE_ULV_TANTALUM.getStackForm(8)).buildAndRegister();
        RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(480).EUt(384).input(OrePrefix.gem, Materials.EnderEye, 1).fluidInputs(Materials.Radon.getFluid(250)).outputs(MetaItems.QUANTUM_EYE.getStackForm()).buildAndRegister();
        RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(1920).EUt(384).input(OrePrefix.gem, Materials.NetherStar, 1).fluidInputs(Materials.Radon.getFluid(1250)).outputs(MetaItems.QUANTUM_STAR.getStackForm()).buildAndRegister();
        RecipeMaps.AUTOCLAVE_RECIPES.recipeBuilder().duration(480).EUt(7680).input(OrePrefix.gem, Materials.NetherStar, 1).fluidInputs(Materials.Neutronium.getFluid(L * 2)).outputs(MetaItems.GRAVI_STAR.getStackForm()).buildAndRegister();
    }

    private static void registerMixingCrystallizationRecipes() {
        RecipeMaps.MIXER_RECIPES.recipeBuilder()
            .input(OrePrefix.dust, Materials.Stone, 1)
            .fluidInputs(Materials.Lubricant.getFluid(20), ModHandler.getWater(4980))
            .fluidOutputs(Materials.DrillingFluid.getFluid(5000))
            .duration(64).EUt(16)
            .buildAndRegister();

        RecipeMaps.MIXER_RECIPES.recipeBuilder()
            .input(OrePrefix.dust, Materials.Clay, 1)
            .input(OrePrefix.dust, Materials.Stone, 3)
            .fluidInputs(Materials.Water.getFluid(500))
            .fluidOutputs(Materials.Concrete.getFluid(576))
            .duration(20).EUt(16)
            .buildAndRegister();

        RecipeMaps.MIXER_RECIPES.recipeBuilder()
            .inputs(MetaBlocks.CONCRETE.getItemVariant(ConcreteVariant.LIGHT_CONCRETE, ChiselingVariant.NORMAL))
            .fluidInputs(Materials.Water.getFluid(144))
            .outputs(MetaBlocks.CONCRETE.getItemVariant(ConcreteVariant.DARK_CONCRETE, ChiselingVariant.NORMAL))
            .duration(12).EUt(4)
            .buildAndRegister();

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

        RecipeMaps.AUTOCLAVE_RECIPES.recipeBuilder().inputs(MetaItems.ENERGIUM_DUST.getStackForm(9))
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

        RecipeMaps.AUTOCLAVE_RECIPES.recipeBuilder()
            .input(OrePrefix.dust, Materials.SiliconDioxide)
            .fluidInputs(ModHandler.getDistilledWater(200))
            .chancedOutput(OreDictUnifier.get(OrePrefix.gem, Materials.Quartzite), 1000, 1000)
            .duration(1500).EUt(24).buildAndRegister();

        RecipeMaps.AUTOCLAVE_RECIPES.recipeBuilder()
            .input(OrePrefix.dust, Materials.NetherStar)
            .fluidInputs(Materials.UUMatter.getFluid(576))
            .chancedOutput(new ItemStack(Items.NETHER_STAR), 3333, 3333)
            .duration(72000).EUt(480).buildAndRegister();

        RecipeMaps.MIXER_RECIPES.recipeBuilder()
            .input(OrePrefix.crushedPurified, Materials.Sphalerite)
            .input(OrePrefix.crushedPurified, Materials.Galena)
            .fluidInputs(Materials.SulfuricAcid.getFluid(4000))
            .fluidOutputs(Materials.IndiumConcentrate.getFluid(1000))
            .duration(60).EUt(150).buildAndRegister();

    }

    private static void registerOrganicRecyclingRecipes() {


    }

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

    private static void registerAlloyRecipes() {
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

        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(800).EUt(8).input(OrePrefix.dust, Materials.Boron, 1).input(OrePrefix.dust, Materials.Glass, 7).output(OrePrefix.dust, Materials.BorosilicateGlass, 8).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(100).EUt(8).input(OrePrefix.dust, Materials.Indium, 1).input(OrePrefix.dust, Materials.Gallium).input(OrePrefix.dust, Materials.Phosphorus, 1).output(OrePrefix.dust, Materials.IndiumGalliumPhosphide, 3).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(600).EUt(8).input(OrePrefix.dust, Materials.Nickel, 1).input(OrePrefix.dust, Materials.Zinc).input(OrePrefix.dust, Materials.Iron, 4).notConsumable(new IntCircuitIngredient(2)).output(OrePrefix.dust, Materials.FerriteMixture, 6).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(100).EUt(8).input(OrePrefix.dust, Materials.EnderPearl, 1).input(OrePrefix.dust, Materials.Blaze).output(OrePrefix.dust, Materials.EnderEye).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(200).EUt(8).input(OrePrefix.dust, Materials.Gold, 1).input(OrePrefix.dust, Materials.Silver).output(OrePrefix.dust, Materials.Electrum, 2).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(300).EUt(8).input(OrePrefix.dust, Materials.Iron, 2).input(OrePrefix.dust, Materials.Nickel).notConsumable(new IntCircuitIngredient(1)).output(OrePrefix.dust, Materials.Invar, 3).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(900).EUt(8).input(OrePrefix.dust, Materials.Iron, 4).input(OrePrefix.dust, Materials.Invar, 3).input(OrePrefix.dust, Materials.Manganese).input(OrePrefix.dust, Materials.Chrome, 1).output(OrePrefix.dust, Materials.StainlessSteel, 9).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(300).EUt(8).input(OrePrefix.dust, Materials.Iron, 1).input(OrePrefix.dust, Materials.Aluminium).input(OrePrefix.dust, Materials.Chrome).output(OrePrefix.dust, Materials.Kanthal, 3).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(400).EUt(8).input(OrePrefix.dust, Materials.Copper, 3).input(OrePrefix.dust, Materials.Zinc).output(OrePrefix.dust, Materials.Brass, 4).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(400).EUt(8).input(OrePrefix.dust, Materials.Copper, 3).input(OrePrefix.dust, Materials.Tin).output(OrePrefix.dust, Materials.Bronze, 4).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(200).EUt(8).input(OrePrefix.dust, Materials.Copper, 1).input(OrePrefix.dust, Materials.Nickel).output(OrePrefix.dust, Materials.Cupronickel, 2).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(500).EUt(8).input(OrePrefix.dust, Materials.Copper, 1).input(OrePrefix.dust, Materials.Gold, 4).output(OrePrefix.dust, Materials.RoseGold, 5).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(500).EUt(8).input(OrePrefix.dust, Materials.Copper, 1).input(OrePrefix.dust, Materials.Silver, 4).output(OrePrefix.dust, Materials.SterlingSilver, 5).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(500).EUt(8).input(OrePrefix.dust, Materials.Copper, 3).input(OrePrefix.dust, Materials.Electrum, 2).output(OrePrefix.dust, Materials.BlackBronze, 5).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(500).EUt(8).input(OrePrefix.dust, Materials.Bismuth, 1).input(OrePrefix.dust, Materials.Brass, 4).output(OrePrefix.dust, Materials.BismuthBronze, 5).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(500).EUt(8).input(OrePrefix.dust, Materials.BlackBronze, 1).input(OrePrefix.dust, Materials.Nickel).input(OrePrefix.dust, Materials.Steel, 3).output(OrePrefix.dust, Materials.BlackSteel, 5).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(800).EUt(8).input(OrePrefix.dust, Materials.SterlingSilver, 1).input(OrePrefix.dust, Materials.BismuthBronze).input(OrePrefix.dust, Materials.BlackSteel, 4).input(OrePrefix.dust, Materials.Steel, 2).output(OrePrefix.dust, Materials.RedSteel, 8).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(800).EUt(8).input(OrePrefix.dust, Materials.RoseGold, 1).input(OrePrefix.dust, Materials.Brass).input(OrePrefix.dust, Materials.BlackSteel, 4).input(OrePrefix.dust, Materials.Steel, 2).output(OrePrefix.dust, Materials.BlueSteel, 8).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(900).EUt(8).input(OrePrefix.dust, Materials.Cobalt, 5).input(OrePrefix.dust, Materials.Chrome, 2).input(OrePrefix.dust, Materials.Nickel).input(OrePrefix.dust, Materials.Molybdenum).output(OrePrefix.dust, Materials.Ultimet, 9).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(900).EUt(8).input(OrePrefix.dust, Materials.Brass, 7).input(OrePrefix.dust, Materials.Aluminium).input(OrePrefix.dust, Materials.Cobalt).output(OrePrefix.dust, Materials.CobaltBrass, 9).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(400).EUt(8).input(OrePrefix.dust, Materials.Saltpeter, 2).input(OrePrefix.dust, Materials.Sulfur).input(OrePrefix.dust, Materials.Coal).output(OrePrefix.dust, Materials.Gunpowder, 4).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(300).EUt(8).input(OrePrefix.dust, Materials.Saltpeter, 2).input(OrePrefix.dust, Materials.Sulfur).input(OrePrefix.dust, Materials.Charcoal).output(OrePrefix.dust, Materials.Gunpowder, 3).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(400).EUt(8).input(OrePrefix.dust, Materials.Electrum, 1).input(OrePrefix.dust, Materials.NaquadahAlloy).input(OrePrefix.dust, Materials.BlueSteel).input(OrePrefix.dust, Materials.RedSteel).output(OrePrefix.dust, Materials.FluxedElectrum, 4).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(300).EUt(8).input(OrePrefix.dust, Materials.Americium, 2).input(OrePrefix.dust, Materials.Titanium).output(OrePrefix.dust, Materials.DiamericiumTitanium, 3).buildAndRegister();

        COMPRESSOR_RECIPES.recipeBuilder().inputs(MetaItems.INGOT_MIXED_METAL.getStackForm()).outputs(MetaItems.ADVANCED_ALLOY_PLATE.getStackForm()).duration(300).EUt(2).buildAndRegister();
        BENDER_RECIPES.recipeBuilder().inputs(MetaItems.INGOT_MIXED_METAL.getStackForm()).circuitMeta(1).outputs(MetaItems.ADVANCED_ALLOY_PLATE.getStackForm()).duration(100).EUt(8).buildAndRegister();
        IMPLOSION_RECIPES.recipeBuilder().inputs(MetaItems.INGOT_IRIDIUM_ALLOY.getStackForm()).outputs(MetaItems.PLATE_IRIDIUM_ALLOY.getStackForm()).output(OrePrefix.dustTiny, Materials.DarkAsh, 4).explosivesAmount(8).buildAndRegister();
        COMPRESSOR_RECIPES.recipeBuilder().inputs(MetaItems.CARBON_FIBERS.getStackForm(2)).outputs(MetaItems.CARBON_MESH.getStackForm()).buildAndRegister();
        COMPRESSOR_RECIPES.recipeBuilder().inputs(MetaItems.CARBON_MESH.getStackForm()).outputs(MetaItems.CARBON_PLATE.getStackForm()).buildAndRegister();

        ALLOY_SMELTER_RECIPES.recipeBuilder().duration(400).EUt(4).input(OrePrefix.dust, Materials.Glass, 3).inputs(MetaItems.ADVANCED_ALLOY_PLATE.getStackForm()).outputs(MetaBlocks.TRANSPARENT_CASING.getItemVariant(BlockTransparentCasing.CasingType.REINFORCED_GLASS, 4)).buildAndRegister();
        ALLOY_SMELTER_RECIPES.recipeBuilder().duration(400).EUt(4).inputs(new ItemStack(Blocks.GLASS)).inputs(MetaItems.ADVANCED_ALLOY_PLATE.getStackForm()).outputs(MetaBlocks.TRANSPARENT_CASING.getItemVariant(BlockTransparentCasing.CasingType.REINFORCED_GLASS, 4)).buildAndRegister();

        ALLOY_SMELTER_RECIPES.recipeBuilder().duration(10).EUt(8).input(OrePrefix.ingot, Materials.Rubber, 2).notConsumable(MetaItems.SHAPE_MOLD_PLATE).output(OrePrefix.plate, Materials.Rubber).buildAndRegister();
        ALLOY_SMELTER_RECIPES.recipeBuilder().duration(100).EUt(1).input(OrePrefix.dust, Materials.Sulfur).input(OrePrefix.dust, Materials.RawRubber, 3).output(OrePrefix.ingot, Materials.Rubber).buildAndRegister();
    }

    private static void registerAssemblerRecipes() {
        for (EnumDyeColor dyeColor : EnumDyeColor.values()) {
            CANNER_RECIPES.recipeBuilder()
                .inputs(MetaItems.SPRAY_EMPTY.getStackForm())
                .input(getOrdictColorName(dyeColor), 1)
                .outputs(MetaItems.SPRAY_CAN_DYES[dyeColor.getMetadata()].getStackForm())
                .EUt(8).duration(200)
                .buildAndRegister();
        }

        for (IngotMaterial cableMaterial : new IngotMaterial[]{Materials.YttriumBariumCuprate, Materials.NiobiumTitanium, Materials.VanadiumGallium, Materials.Naquadah}) {
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.wireGtSingle, cableMaterial, 3)
                .input(OrePrefix.pipeTiny, Materials.TungstenSteel, 2)
                .inputs(MetaItems.ELECTRIC_PUMP_LV.getStackForm(2))
                .fluidInputs(Materials.Nitrogen.getFluid(2000))
                .outputs(OreDictUnifier.get(OrePrefix.wireGtSingle, Tier.Superconductor, 3))
                .duration(20).EUt(512)
                .buildAndRegister();
        }

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .duration(4096).EUt(4096)
            .inputs(MetaItems.ENERGY_LAPOTRONIC_ORB2.getStackForm(8))
            .input(OrePrefix.plate, Materials.Darmstadtium, 16)
            .outputs(MetaItems.ZPM2.getStackForm())
            .buildAndRegister();

        Material[] coverMaterials = new Material[]{Materials.Iron, Materials.WroughtIron, Materials.Aluminium};

        for (Material material : coverMaterials) {
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                    .inputs(new ItemStack(Items.IRON_DOOR))
                    .input(OrePrefix.plate, material, 2)
                    .outputs(MetaItems.COVER_SHUTTER.getStackForm(2))
                    .EUt(16).duration(800)
                    .buildAndRegister();

            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                    .inputs(new ItemStack(Blocks.LEVER))
                    .input(OrePrefix.plate, material)
                    .fluidInputs(Materials.Tin.getFluid(L))
                    .outputs(MetaItems.COVER_MACHINE_CONTROLLER.getStackForm(1))
                    .EUt(16).duration(200)
                    .buildAndRegister();

            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                    .inputs(new ItemStack(Blocks.LEVER))
                    .input(OrePrefix.plate, material)
                    .fluidInputs(Materials.SolderingAlloy.getFluid(L / 2))
                    .outputs(MetaItems.COVER_MACHINE_CONTROLLER.getStackForm(1))
                    .EUt(16).duration(200)
                    .buildAndRegister();
        }

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(480).EUt(240).input(OrePrefix.dust, Materials.Graphite, 8).input(OrePrefix.foil, Materials.Silicon, 1).fluidInputs(Materials.Glue.getFluid(250)).outputs(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Graphene, 1)).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(1).inputs(new ItemStack(Items.COAL, 1, GTValues.W)).input(OrePrefix.stick, Materials.Wood, 1).outputs(new ItemStack(Blocks.TORCH, 4)).duration(400).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(4).input(OrePrefix.plate, Materials.Gold, 2).outputs(new ItemStack(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE, 1)).circuitMeta(2).duration(200).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(4).input(OrePrefix.plate, Materials.Iron, 2).outputs(new ItemStack(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, 1)).circuitMeta(2).duration(200).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(4).input(OrePrefix.plate, Materials.Iron, 6).outputs(new ItemStack(Items.IRON_DOOR, 3)).circuitMeta(6).duration(600).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(4).input(OrePrefix.plate, Materials.Iron, 7).outputs(new ItemStack(Items.CAULDRON, 1)).circuitMeta(7).duration(700).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(4).input(OrePrefix.stick, Materials.Iron, 3).outputs(new ItemStack(Blocks.IRON_BARS, 4)).circuitMeta(3).duration(300).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(4).input(OrePrefix.plate, Materials.WroughtIron, 2).outputs(new ItemStack(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, 1)).circuitMeta(2).duration(200).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(4).input(OrePrefix.plate, Materials.WroughtIron, 6).outputs(new ItemStack(Items.IRON_DOOR, 3)).circuitMeta(6).duration(600).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(4).input(OrePrefix.plate, Materials.WroughtIron, 7).outputs(new ItemStack(Items.CAULDRON, 1)).circuitMeta(7).duration(700).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(4).input(OrePrefix.stick, Materials.WroughtIron, 3).outputs(new ItemStack(Blocks.IRON_BARS, 4)).circuitMeta(3).duration(300).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(4).input(OrePrefix.stick, Materials.Wood, 3).outputs(new ItemStack(Blocks.OAK_FENCE, 1)).circuitMeta(3).duration(300).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(4).input(OrePrefix.stick, Materials.Wood, 2).input(OrePrefix.ring, Materials.Iron, 2).outputs(new ItemStack(Blocks.TRIPWIRE_HOOK, 1)).duration(400).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(4).input(OrePrefix.stick, Materials.Wood, 2).input(OrePrefix.ring, Materials.WroughtIron, 2).outputs(new ItemStack(Blocks.TRIPWIRE_HOOK, 1)).duration(400).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(4).inputs(new ItemStack(Items.STRING, 3, GTValues.W)).input(OrePrefix.stick, Materials.Wood, 3).outputs(new ItemStack(Items.BOW, 1)).duration(400).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(4).inputs(new ItemStack(Blocks.STONE)).outputs(new ItemStack(Blocks.STONEBRICK, 1, 0)).circuitMeta(4).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(4).inputs(new ItemStack(Blocks.SANDSTONE)).outputs(new ItemStack(Blocks.SANDSTONE, 1, 2)).circuitMeta(1).duration(50).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(4).inputs(new ItemStack(Blocks.SANDSTONE, 1, 1)).outputs(new ItemStack(Blocks.SANDSTONE, 1, 0)).circuitMeta(1).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(4).inputs(new ItemStack(Blocks.SANDSTONE, 1, 2)).outputs(new ItemStack(Blocks.SANDSTONE, 1, 0)).circuitMeta(1).duration(50).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(OrePrefix.plate, Materials.WroughtIron, 8).outputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.ULV)).circuitMeta(8).duration(25).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(OrePrefix.plate, Materials.Steel, 8).outputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.LV)).circuitMeta(8).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(OrePrefix.plate, Materials.Aluminium, 8).outputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.MV)).circuitMeta(8).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(OrePrefix.plate, Materials.StainlessSteel, 8).outputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.HV)).circuitMeta(8).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(OrePrefix.plate, Materials.Titanium, 8).outputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.EV)).circuitMeta(8).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(OrePrefix.plate, Materials.TungstenSteel, 8).outputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.IV)).circuitMeta(8).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(OrePrefix.plate, Materials.Chrome, 8).outputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.LuV)).circuitMeta(8).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(OrePrefix.plate, Materials.Iridium, 8).outputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.ZPM)).circuitMeta(8).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(OrePrefix.plate, Materials.Osmium, 8).outputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.UV)).circuitMeta(8).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(OrePrefix.plate, Materials.Darmstadtium, 8).outputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.UHV)).circuitMeta(8).duration(50).buildAndRegister();

        for (CoilType coilType : CoilType.values()) {
            if (coilType.getMaterial() != null) {
                ItemStack outputStack = MetaBlocks.WIRE_COIL.getItemVariant(coilType);
                ASSEMBLER_RECIPES.recipeBuilder()
                    .circuitMeta(8)
                    .input(wireGtDouble, coilType.getMaterial(), 8)
                    .outputs(outputStack)
                    .duration(50).EUt(16).buildAndRegister();
            }
        }

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(OrePrefix.plate, Materials.Invar, 6).input(OrePrefix.frameGt, Materials.Invar, 1).outputs(MetaBlocks.METAL_CASING.getItemVariant(MetalCasingType.INVAR_HEATPROOF, 2)).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(OrePrefix.plate, Materials.Steel, 6).input(OrePrefix.frameGt, Materials.Steel, 1).outputs(MetaBlocks.METAL_CASING.getItemVariant(MetalCasingType.STEEL_SOLID, 2)).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(OrePrefix.plate, Materials.Aluminium, 6).input(OrePrefix.frameGt, Materials.Aluminium, 1).outputs(MetaBlocks.METAL_CASING.getItemVariant(MetalCasingType.ALUMINIUM_FROSTPROOF, 2)).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(OrePrefix.plate, Materials.TungstenSteel, 6).input(OrePrefix.frameGt, Materials.TungstenSteel, 1).outputs(MetaBlocks.METAL_CASING.getItemVariant(MetalCasingType.TUNGSTENSTEEL_ROBUST, 2)).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(OrePrefix.plate, Materials.StainlessSteel, 6).input(OrePrefix.frameGt, Materials.StainlessSteel, 1).outputs(MetaBlocks.METAL_CASING.getItemVariant(MetalCasingType.STAINLESS_CLEAN, 2)).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(OrePrefix.plate, Materials.Titanium, 6).input(OrePrefix.frameGt, Materials.Titanium, 1).outputs(MetaBlocks.METAL_CASING.getItemVariant(MetalCasingType.TITANIUM_STABLE, 2)).duration(50).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(16).inputs(MetaBlocks.METAL_CASING.getItemVariant(MetalCasingType.STEEL_SOLID)).fluidInputs(Materials.Polytetrafluoroethylene.getFluid(216)).notConsumable(new IntCircuitIngredient(6)).outputs(MetaBlocks.METAL_CASING.getItemVariant(MetalCasingType.PTFE_INERT_CASING)).duration(50).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(16).inputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.LuV)).input(OrePrefix.plate, Materials.TungstenSteel, 6).outputs(MetaBlocks.MULTIBLOCK_CASING.getItemVariant(MultiblockCasingType.FUSION_CASING)).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(16).inputs(MetaBlocks.MULTIBLOCK_CASING.getItemVariant(MultiblockCasingType.FUSION_CASING)).input(OrePrefix.plate, Materials.Americium, 6).outputs(MetaBlocks.MULTIBLOCK_CASING.getItemVariant(MultiblockCasingType.FUSION_CASING_MK2)).duration(50).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(OrePrefix.plate, Materials.Magnalium, 6).input(OrePrefix.frameGt, Materials.BlueSteel, 1).outputs(MetaBlocks.TURBINE_CASING.getItemVariant(TurbineCasingType.STEEL_TURBINE_CASING, 2)).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(16).inputs(MetaBlocks.TURBINE_CASING.getItemVariant(TurbineCasingType.STEEL_TURBINE_CASING)).input(OrePrefix.plate, Materials.StainlessSteel, 6).outputs(MetaBlocks.TURBINE_CASING.getItemVariant(TurbineCasingType.STAINLESS_TURBINE_CASING, 2)).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(16).inputs(MetaBlocks.TURBINE_CASING.getItemVariant(TurbineCasingType.STEEL_TURBINE_CASING)).input(OrePrefix.plate, Materials.Titanium, 6).outputs(MetaBlocks.TURBINE_CASING.getItemVariant(TurbineCasingType.TITANIUM_TURBINE_CASING, 2)).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(16).inputs(MetaBlocks.TURBINE_CASING.getItemVariant(TurbineCasingType.STEEL_TURBINE_CASING)).input(OrePrefix.plate, Materials.TungstenSteel, 6).outputs(MetaBlocks.TURBINE_CASING.getItemVariant(TurbineCasingType.TUNGSTENSTEEL_TURBINE_CASING, 2)).duration(50).buildAndRegister();

        if (ConfigHolder.harderMachineHulls) {
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(25).EUt(16).inputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.ULV)).input(OrePrefix.cableGtSingle, Materials.Lead, 2).fluidInputs(Materials.Polyethylene.getFluid(L * 2)).outputs(MetaTileEntities.HULL[0].getStackForm()).buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(16).inputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.LV)).input(OrePrefix.cableGtSingle, Materials.Tin, 2).fluidInputs(Materials.Polyethylene.getFluid(L * 2)).outputs(MetaTileEntities.HULL[1].getStackForm()).buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(16).inputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.MV)).input(OrePrefix.cableGtSingle, Materials.Copper, 2).fluidInputs(Materials.Polyethylene.getFluid(L * 2)).outputs(MetaTileEntities.HULL[2].getStackForm()).buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(16).inputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.MV)).input(OrePrefix.cableGtSingle, Materials.AnnealedCopper, 2).fluidInputs(Materials.Polyethylene.getFluid(L * 2)).outputs(MetaTileEntities.HULL[2].getStackForm()).buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(16).inputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.HV)).input(OrePrefix.cableGtSingle, Materials.Gold, 2).fluidInputs(Materials.Polyethylene.getFluid(L * 2)).outputs(MetaTileEntities.HULL[3].getStackForm()).buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(16).inputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.EV)).input(OrePrefix.cableGtSingle, Materials.Aluminium, 2).fluidInputs(Materials.Polyethylene.getFluid(L * 2)).outputs(MetaTileEntities.HULL[4].getStackForm()).buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(16).inputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.IV)).input(OrePrefix.cableGtSingle, Materials.Tungsten, 2).fluidInputs(Materials.Polyethylene.getFluid(L * 2)).outputs(MetaTileEntities.HULL[5].getStackForm()).buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(16).inputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.LuV)).input(OrePrefix.cableGtSingle, Materials.VanadiumGallium, 2).fluidInputs(Materials.Polyethylene.getFluid(L * 2)).outputs(MetaTileEntities.HULL[6].getStackForm()).buildAndRegister();
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
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(16).inputs(MetaBlocks.MACHINE_CASING.getItemVariant(MachineCasingType.MAX)).input(OrePrefix.wireGtSingle, MarkerMaterials.Tier.Superconductor, 2).outputs(MetaTileEntities.HULL[14].getStackForm()).buildAndRegister();
        }

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(800).EUt(1).input(OrePrefix.cableGtSingle, Materials.Tin, 1).input(OrePrefix.plate, Materials.BatteryAlloy, 1).fluidInputs(Materials.Polyethylene.getFluid(144)).outputs(MetaItems.BATTERY_HULL_LV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(1600).EUt(2).input(OrePrefix.cableGtSingle, Materials.Copper, 2).input(OrePrefix.plate, Materials.BatteryAlloy, 3).fluidInputs(Materials.Polyethylene.getFluid(432)).outputs(MetaItems.BATTERY_HULL_MV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(1600).EUt(2).input(OrePrefix.cableGtSingle, Materials.AnnealedCopper, 2).input(OrePrefix.plate, Materials.BatteryAlloy, 3).fluidInputs(Materials.Polyethylene.getFluid(432)).outputs(MetaItems.BATTERY_HULL_MV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(3200).EUt(4).input(OrePrefix.cableGtSingle, Materials.Gold, 4).input(OrePrefix.plate, Materials.BatteryAlloy, 9).fluidInputs(Materials.Polyethylene.getFluid(1296)).outputs(MetaItems.BATTERY_HULL_HV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(2).inputs(new ItemStack(Items.STRING, 4, GTValues.W), new ItemStack(Items.SLIME_BALL, 1, GTValues.W)).outputs(new ItemStack(Items.LEAD, 2)).duration(200).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(2).inputs(new ItemStack(Blocks.CHEST, 1, GTValues.W)).input(OrePrefix.plate, Materials.Iron, 5).outputs(new ItemStack(Blocks.HOPPER)).duration(800).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(2).inputs(new ItemStack(Blocks.TRAPPED_CHEST, 1, GTValues.W)).input(OrePrefix.plate, Materials.Iron, 5).outputs(new ItemStack(Blocks.HOPPER)).duration(800).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(2).inputs(new ItemStack(Blocks.CHEST, 1, GTValues.W)).input(OrePrefix.plate, Materials.WroughtIron, 5).outputs(new ItemStack(Blocks.HOPPER)).duration(800).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(2).inputs(new ItemStack(Blocks.TRAPPED_CHEST, 1, GTValues.W)).input(OrePrefix.plate, Materials.WroughtIron, 5).outputs(new ItemStack(Blocks.HOPPER)).duration(800).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(2).inputs(new ItemStack(Items.BLAZE_ROD)).input(OrePrefix.gem, Materials.EnderPearl, 6).outputs(new ItemStack(Items.ENDER_EYE, 6, 0)).duration(2500).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(2).input(OrePrefix.gear, Materials.CobaltBrass, 1).input(OrePrefix.dust, Materials.Diamond, 1).outputs(MetaItems.COMPONENT_SAW_BLADE_DIAMOND.getStackForm(1)).duration(1600).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(1).input(OrePrefix.dust, Materials.Redstone, 4).input(OrePrefix.dust, Materials.Glowstone, 4).outputs(new ItemStack(Blocks.REDSTONE_LAMP, 1)).duration(400).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(1).input(OrePrefix.dust, Materials.Redstone, 1).input(OrePrefix.stick, Materials.Wood, 1).outputs(new ItemStack(Blocks.REDSTONE_TORCH, 1)).duration(400).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(4).input(OrePrefix.dust, Materials.Redstone, 1).input(OrePrefix.plate, Materials.Iron, 4).outputs(new ItemStack(Items.COMPASS, 1)).duration(400).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(4).input(OrePrefix.dust, Materials.Redstone, 1).input(OrePrefix.plate, Materials.WroughtIron, 4).outputs(new ItemStack(Items.COMPASS, 1)).duration(400).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(4).input(OrePrefix.dust, Materials.Redstone, 1).input(OrePrefix.plate, Materials.Gold, 4).outputs(new ItemStack(Items.CLOCK, 1)).duration(400).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(1).input(OrePrefix.stick, Materials.Wood, 1).input(OrePrefix.dust, Materials.Sulfur, 1).outputs(new ItemStack(Blocks.TORCH, 2)).duration(400).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(1).input(OrePrefix.stick, Materials.Wood, 1).input(OrePrefix.dust, Materials.Phosphorus, 1).outputs(new ItemStack(Blocks.TORCH, 6)).duration(400).buildAndRegister();
    }

    private static void registerBlastFurnaceRecipes() {
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration((int) Math.max(Materials.TungstenSteel.getAverageMass() / 80, 1) * Materials.TungstenSteel.blastFurnaceTemperature).EUt(480).input(OrePrefix.ingot, Materials.Tungsten, 1).input(OrePrefix.ingot, Materials.Steel, 1).outputs(OreDictUnifier.get(OrePrefix.ingotHot, Materials.TungstenSteel, 2), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh, 1)).blastFurnaceTemp(Materials.TungstenSteel.blastFurnaceTemperature).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration((int) Math.max(Materials.TungstenCarbide.getAverageMass() / 40, 1) * Materials.TungstenCarbide.blastFurnaceTemperature).EUt(480).input(OrePrefix.ingot, Materials.Tungsten, 1).input(OrePrefix.dust, Materials.Carbon, 1).outputs(OreDictUnifier.get(OrePrefix.ingotHot, Materials.TungstenCarbide, 2), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh, 1)).blastFurnaceTemp(Materials.TungstenCarbide.blastFurnaceTemperature).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration((int) Math.max(Materials.VanadiumGallium.getAverageMass() / 40, 1) * Materials.VanadiumGallium.blastFurnaceTemperature).EUt(480).input(OrePrefix.ingot, Materials.Vanadium, 3).input(OrePrefix.ingot, Materials.Gallium, 1).outputs(OreDictUnifier.get(OrePrefix.ingotHot, Materials.VanadiumGallium, 4), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh, 2)).blastFurnaceTemp(Materials.VanadiumGallium.blastFurnaceTemperature).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration((int) Math.max(Materials.NiobiumTitanium.getAverageMass() / 80, 1) * Materials.NiobiumTitanium.blastFurnaceTemperature).EUt(480).input(OrePrefix.ingot, Materials.Niobium, 1).input(OrePrefix.ingot, Materials.Titanium, 1).outputs(OreDictUnifier.get(OrePrefix.ingotHot, Materials.NiobiumTitanium, 2), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh, 1)).blastFurnaceTemp(Materials.NiobiumTitanium.blastFurnaceTemperature).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration((int) Math.max(Materials.Nichrome.getAverageMass() / 32, 1) * Materials.Nichrome.blastFurnaceTemperature).EUt(480).input(OrePrefix.ingot, Materials.Nickel, 4).input(OrePrefix.ingot, Materials.Chrome, 1).outputs(OreDictUnifier.get(OrePrefix.ingotHot, Materials.Nichrome, 5), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh, 2)).blastFurnaceTemp(Materials.Nichrome.blastFurnaceTemperature).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(400).EUt(100).input(OrePrefix.dust, Materials.Ruby, 1).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Aluminium, 5), OreDictUnifier.get(OrePrefix.dustTiny, Materials.DarkAsh, 4)).blastFurnaceTemp(1200).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(320).EUt(100).input(OrePrefix.gem, Materials.Ruby, 1).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Aluminium, 5), OreDictUnifier.get(OrePrefix.dustTiny, Materials.DarkAsh, 4)).blastFurnaceTemp(1200).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(400).EUt(100).input(OrePrefix.dust, Materials.GreenSapphire, 1).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Aluminium, 5), OreDictUnifier.get(OrePrefix.dustTiny, Materials.DarkAsh, 4)).blastFurnaceTemp(1200).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(320).EUt(100).input(OrePrefix.gem, Materials.GreenSapphire, 1).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Aluminium, 5), OreDictUnifier.get(OrePrefix.dustTiny, Materials.DarkAsh, 4)).blastFurnaceTemp(1200).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(400).EUt(100).input(OrePrefix.dust, Materials.Sapphire, 1).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Aluminium, 5), OreDictUnifier.get(OrePrefix.dustTiny, Materials.DarkAsh, 4)).blastFurnaceTemp(1200).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(320).EUt(100).input(OrePrefix.gem, Materials.Sapphire, 1).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Aluminium, 5), OreDictUnifier.get(OrePrefix.dustTiny, Materials.DarkAsh, 4)).blastFurnaceTemp(1200).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(800).EUt(500).input(OrePrefix.dust, Materials.Ilmenite, 5).outputs(OreDictUnifier.get(OrePrefix.ingot, Materials.WroughtIron), OreDictUnifier.get(OrePrefix.dust, Materials.Rutile, 3)).blastFurnaceTemp(1700).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(800).EUt(480).input(OrePrefix.dust, Materials.Magnesium, 2).fluidInputs(Materials.TitaniumTetrachloride.getFluid(1000)).outputs(OreDictUnifier.get(OrePrefix.ingotHot, Materials.Titanium, 1), OreDictUnifier.get(OrePrefix.dust, Materials.MagnesiumChloride, 6)).blastFurnaceTemp(Materials.Titanium.blastFurnaceTemperature + 200).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(400).EUt(500).input(OrePrefix.dust, Materials.Galena, 1).fluidInputs(Materials.Oxygen.getFluid(2000)).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Silver, 4), OreDictUnifier.get(OrePrefix.nugget, Materials.Lead, 4)).blastFurnaceTemp(1500).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(400).EUt(500).input(OrePrefix.dust, Materials.Magnetite, 1).fluidInputs(Materials.Oxygen.getFluid(2000)).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.WroughtIron, 4), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh, 1)).blastFurnaceTemp(1000).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(120).input(OrePrefix.ingot, Materials.Iron, 1).fluidInputs(Materials.Oxygen.getFluid(1000)).outputs(OreDictUnifier.get(OrePrefix.ingot, Materials.Steel, 1), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh, 1)).blastFurnaceTemp(1000).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(100).EUt(120).input(OrePrefix.ingot, Materials.PigIron, 1).fluidInputs(Materials.Oxygen.getFluid(1000)).outputs(OreDictUnifier.get(OrePrefix.ingot, Materials.Steel, 1), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh, 1)).blastFurnaceTemp(1000).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(100).EUt(120).input(OrePrefix.ingot, Materials.WroughtIron, 1).fluidInputs(Materials.Oxygen.getFluid(1000)).outputs(OreDictUnifier.get(OrePrefix.ingot, Materials.Steel, 1), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh, 1)).blastFurnaceTemp(1000).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(120).input(OrePrefix.dust, Materials.Copper, 1).fluidInputs(Materials.Oxygen.getFluid(1000)).outputs(OreDictUnifier.get(OrePrefix.ingot, Materials.AnnealedCopper, 1)).blastFurnaceTemp(1200).notConsumable(new IntCircuitIngredient(1)).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(120).input(OrePrefix.ingot, Materials.Copper, 1).fluidInputs(Materials.Oxygen.getFluid(1000)).outputs(OreDictUnifier.get(OrePrefix.ingot, Materials.AnnealedCopper, 1)).blastFurnaceTemp(1200).notConsumable(new IntCircuitIngredient(1)).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(1920).input(OrePrefix.ingot, Materials.Iridium, 3).input(OrePrefix.ingot, Materials.Osmium, 1).fluidInputs(Materials.Helium.getFluid(1000)).outputs(OreDictUnifier.get(OrePrefix.ingotHot, Materials.Osmiridium, 4)).blastFurnaceTemp(2900).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(30720).input(OrePrefix.ingot, Materials.Naquadah, 1).input(OrePrefix.ingot, Materials.Osmiridium, 1).fluidInputs(Materials.Argon.getFluid(1000)).outputs(OreDictUnifier.get(OrePrefix.ingotHot, Materials.NaquadahAlloy, 2)).blastFurnaceTemp(Materials.NaquadahAlloy.blastFurnaceTemperature).buildAndRegister();
    }


    private static void registerDecompositionRecipes() {


        EXTRACTOR_RECIPES.recipeBuilder()
                .inputs(RUBBER_DROP.getStackForm())
                .output(dust, RawRubber, 4)
                .duration(300).EUt(2)
                .buildAndRegister();

        EXTRACTOR_RECIPES.recipeBuilder().duration(300).EUt(2)
                .inputs(MetaBlocks.LEAVES.getItem(LogVariant.RUBBER_WOOD, 16))
                .output(dust, RawRubber)
                .buildAndRegister();

        EXTRACTOR_RECIPES.recipeBuilder().duration(300).EUt(2)
                .inputs(MetaBlocks.LOG.getItem(LogVariant.RUBBER_WOOD))
                .output(dust, RawRubber)
                .buildAndRegister();

        EXTRACTOR_RECIPES.recipeBuilder().duration(300).EUt(2)
                .inputs(MetaBlocks.SAPLING.getItem(LogVariant.RUBBER_WOOD))
                .output(dust, RawRubber)
                .buildAndRegister();

        EXTRACTOR_RECIPES.recipeBuilder().duration(150).EUt(2)
                .inputs(new ItemStack(Items.SLIME_BALL))
                .output(dust, RawRubber, 2)
                .buildAndRegister();



        //electromagnetic separation recipes
        //todo add to ore byproducts page and cleanup
        ELECTROMAGNETIC_SEPARATOR_RECIPES.recipeBuilder().duration(400).EUt(24).input(dustPure, BrownLimonite).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.BrownLimonite)).chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Iron), 4000, 900).chancedOutput(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron), 2000, 600).buildAndRegister();
        ELECTROMAGNETIC_SEPARATOR_RECIPES.recipeBuilder().duration(400).EUt(24).input(dustPure, YellowLimonite).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.YellowLimonite)).chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Iron), 4000, 900).chancedOutput(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron), 2000, 600).buildAndRegister();
        ELECTROMAGNETIC_SEPARATOR_RECIPES.recipeBuilder().duration(400).EUt(24).input(dustPure, Nickel).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Nickel)).chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Iron), 4000, 900).chancedOutput(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron), 2000, 600).buildAndRegister();
        ELECTROMAGNETIC_SEPARATOR_RECIPES.recipeBuilder().duration(400).EUt(24).input(dustPure, Pentlandite).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Pentlandite)).chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Iron), 4000, 900).chancedOutput(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron), 2000, 600).buildAndRegister();
        ELECTROMAGNETIC_SEPARATOR_RECIPES.recipeBuilder().duration(400).EUt(24).input(dustPure, BandedIron).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.BandedIron)).chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Iron), 4000, 900).chancedOutput(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron), 2000, 600).buildAndRegister();
        ELECTROMAGNETIC_SEPARATOR_RECIPES.recipeBuilder().duration(400).EUt(24).input(dustPure, Ilmenite).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Ilmenite)).chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Iron), 4000, 900).chancedOutput(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron), 2000, 600).buildAndRegister();
        ELECTROMAGNETIC_SEPARATOR_RECIPES.recipeBuilder().duration(400).EUt(24).input(dustPure, Pyrite).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Pyrite)).chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Iron), 4000, 900).chancedOutput(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron), 2000, 600).buildAndRegister();
        ELECTROMAGNETIC_SEPARATOR_RECIPES.recipeBuilder().duration(400).EUt(24).input(dustPure, Tin).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Tin)).chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Iron), 4000, 900).chancedOutput(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron), 2000, 600).buildAndRegister();
        ELECTROMAGNETIC_SEPARATOR_RECIPES.recipeBuilder().duration(400).EUt(24).input(dustPure, Chromite).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Chromite)).chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Iron), 4000, 900).chancedOutput(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron), 2000, 600).buildAndRegister();
        ELECTROMAGNETIC_SEPARATOR_RECIPES.recipeBuilder().duration(400).EUt(24).input(dustPure, Monazite).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Monazite)).chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Neodymium), 4000, 900).chancedOutput(OreDictUnifier.get(OrePrefix.nugget, Materials.Neodymium), 2000, 600).buildAndRegister();
        ELECTROMAGNETIC_SEPARATOR_RECIPES.recipeBuilder().duration(400).EUt(24).input(dustPure, Bastnasite).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Bastnasite)).chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Neodymium), 4000, 900).chancedOutput(OreDictUnifier.get(OrePrefix.nugget, Materials.Neodymium), 2000, 600).buildAndRegister();
        ELECTROMAGNETIC_SEPARATOR_RECIPES.recipeBuilder().duration(400).EUt(24).input(dustPure, VanadiumMagnetite).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.VanadiumMagnetite)).chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Gold), 4000, 900).chancedOutput(OreDictUnifier.get(OrePrefix.nugget, Materials.Gold), 2000, 600).buildAndRegister();
        ELECTROMAGNETIC_SEPARATOR_RECIPES.recipeBuilder().duration(400).EUt(24).input(dustPure, Magnetite).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Magnetite)).chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Gold), 4000, 900).chancedOutput(OreDictUnifier.get(OrePrefix.nugget, Materials.Gold), 2000, 600).buildAndRegister();
        ELECTROMAGNETIC_SEPARATOR_RECIPES.recipeBuilder().duration(400).EUt(24).input(dustPure, Naquadah).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Naquadah)).chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.NaquadahEnriched), 4000, 900).chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Trinium), 2000, 600).buildAndRegister();
        ELECTROMAGNETIC_SEPARATOR_RECIPES.recipeBuilder().duration(400).EUt(24).input(dustPure, Iridium).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Iridium)).chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Osmium), 4000, 900).chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Trinium), 2000, 600).buildAndRegister();
        ELECTROMAGNETIC_SEPARATOR_RECIPES.recipeBuilder().duration(400).EUt(24).input(dustPure, Wulfenite).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Wulfenite)).chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Trinium), 4000, 900).chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Trinium), 2000, 600).buildAndRegister();





        COMPRESSOR_RECIPES.recipeBuilder().duration(300).EUt(2).input("treeSapling", 8).output(PLANT_BALL).buildAndRegister();
        COMPRESSOR_RECIPES.recipeBuilder().duration(300).EUt(2).inputs(new ItemStack(Items.WHEAT, 8)).output(PLANT_BALL).buildAndRegister();
        COMPRESSOR_RECIPES.recipeBuilder().duration(300).EUt(2).inputs(new ItemStack(Items.POTATO, 8)).output(PLANT_BALL).buildAndRegister();
        COMPRESSOR_RECIPES.recipeBuilder().duration(300).EUt(2).inputs(new ItemStack(Items.CARROT, 8)).output(PLANT_BALL).buildAndRegister();
        COMPRESSOR_RECIPES.recipeBuilder().duration(300).EUt(2).inputs(new ItemStack(Blocks.CACTUS, 8)).output(PLANT_BALL).buildAndRegister();
        COMPRESSOR_RECIPES.recipeBuilder().duration(300).EUt(2).inputs(new ItemStack(Items.REEDS, 8)).output(PLANT_BALL).buildAndRegister();
        COMPRESSOR_RECIPES.recipeBuilder().duration(300).EUt(2).inputs(new ItemStack(Blocks.BROWN_MUSHROOM, 8)).output(PLANT_BALL).buildAndRegister();
        COMPRESSOR_RECIPES.recipeBuilder().duration(300).EUt(2).inputs(new ItemStack(Blocks.RED_MUSHROOM, 8)).output(PLANT_BALL).buildAndRegister();
        COMPRESSOR_RECIPES.recipeBuilder().duration(300).EUt(2).inputs(new ItemStack(Items.BEETROOT, 8)).output(PLANT_BALL).buildAndRegister();

    }

    private static void registerCutterRecipes() {
        for (int i = 0; i < 16; i++) {
            CUTTER_RECIPES.recipeBuilder().duration(50).EUt(8).inputs(new ItemStack(Blocks.STAINED_GLASS, 3, i)).outputs(new ItemStack(Blocks.STAINED_GLASS_PANE, 8, i)).buildAndRegister();
        }
        CUTTER_RECIPES.recipeBuilder().duration(50).EUt(8).inputs(new ItemStack(Blocks.GLASS, 3)).outputs(new ItemStack(Blocks.GLASS_PANE, 8)).buildAndRegister();
        CUTTER_RECIPES.recipeBuilder().duration(25).EUt(8).inputs(new ItemStack(Blocks.STONE)).outputs(new ItemStack(Blocks.STONE_SLAB, 2)).buildAndRegister();
        CUTTER_RECIPES.recipeBuilder().duration(25).EUt(8).inputs(new ItemStack(Blocks.SANDSTONE)).outputs(new ItemStack(Blocks.STONE_SLAB, 2, 1)).buildAndRegister();
        CUTTER_RECIPES.recipeBuilder().duration(25).EUt(8).inputs(new ItemStack(Blocks.COBBLESTONE)).outputs(new ItemStack(Blocks.STONE_SLAB, 2, 3)).buildAndRegister();
        CUTTER_RECIPES.recipeBuilder().duration(25).EUt(8).inputs(new ItemStack(Blocks.BRICK_BLOCK)).outputs(new ItemStack(Blocks.STONE_SLAB, 2, 4)).buildAndRegister();
        CUTTER_RECIPES.recipeBuilder().duration(25).EUt(8).inputs(new ItemStack(Blocks.STONEBRICK)).outputs(new ItemStack(Blocks.STONE_SLAB, 2, 5)).buildAndRegister();
        CUTTER_RECIPES.recipeBuilder().duration(25).EUt(8).inputs(new ItemStack(Blocks.NETHER_BRICK)).outputs(new ItemStack(Blocks.STONE_SLAB, 2, 6)).buildAndRegister();
        CUTTER_RECIPES.recipeBuilder().duration(100).EUt(16).inputs(new ItemStack(Blocks.GLOWSTONE)).output(plate, Glowstone, 4).buildAndRegister();
    }

    private static void registerRecyclingRecipes() {
        FORGE_HAMMER_RECIPES.recipeBuilder()
            .input(stone.name(), 1)
            .outputs(new ItemStack(Blocks.COBBLESTONE, 1))
            .EUt(8).duration(200)
            .buildAndRegister();

        FORGE_HAMMER_RECIPES.recipeBuilder()
            .input(cobblestone.name(), 1)
            .outputs(new ItemStack(Blocks.GRAVEL, 1))
            .EUt(8).duration(200)
            .buildAndRegister();

        MACERATOR_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Blocks.GRAVEL, 1))
            .outputs(new ItemStack(Blocks.SAND))
            .EUt(8).duration(200)
            .buildAndRegister();

        MACERATOR_RECIPES.recipeBuilder()
            .input(stone, Endstone)
            .output(dust, Endstone)
            .chancedOutput(dustTiny, Tungstate, 1200, 280)
            .buildAndRegister();

        MACERATOR_RECIPES.recipeBuilder()
            .input(stone, Netherrack)
            .output(dust, Netherrack)
            .chancedOutput(nugget, Gold, 500, 120)
            .buildAndRegister();

        MACERATOR_RECIPES.recipeBuilder()
            .input(stone, Soapstone)
            .output(dustImpure, Talc)
            .chancedOutput(dustTiny, Chromite, 1000, 280)
            .buildAndRegister();

        MACERATOR_RECIPES.recipeBuilder()
            .input(stone, Redrock)
            .output(dust, Redrock)
            .chancedOutput(dust, Redrock, 1000, 380)
            .buildAndRegister();

        MACERATOR_RECIPES.recipeBuilder()
            .input(stone, Marble)
            .output(dust, Marble)
            .chancedOutput(dust, Marble, 1000, 380)
            .buildAndRegister();

        MACERATOR_RECIPES.recipeBuilder()
            .input(stone, Basalt)
            .output(dust, Basalt)
            .chancedOutput(dust, Basalt, 1000, 380)
            .buildAndRegister();

        MACERATOR_RECIPES.recipeBuilder()
            .input(stone, GraniteBlack)
            .output(dust, GraniteBlack)
            .chancedOutput(dust, Thorium, 100, 40)
            .buildAndRegister();

        MACERATOR_RECIPES.recipeBuilder()
            .input(stone, GraniteRed)
            .output(dust, GraniteRed)
            .chancedOutput(dustSmall, Uranium238, 100, 40)
            .buildAndRegister();

        MACERATOR_RECIPES.recipeBuilder()
            .input(stone, Andesite)
            .output(dust, Andesite)
            .chancedOutput(dustSmall, Stone, 100, 40)
            .buildAndRegister();

        MACERATOR_RECIPES.recipeBuilder()
            .input(stone, Diorite)
            .output(dust, Diorite)
            .chancedOutput(dustSmall, Stone, 100, 40)
            .buildAndRegister();

        MACERATOR_RECIPES.recipeBuilder()
            .input(stone, Granite)
            .output(dust, Granite)
            .chancedOutput(dustSmall, Stone, 100, 40)
            .buildAndRegister();

        MACERATOR_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Items.PORKCHOP))
            .output(dustSmall, Meat, 6)
            .output(dustTiny, Bone)
            .duration(102).EUt(4).buildAndRegister();

        MACERATOR_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Items.FISH, 1, GTValues.W))
            .output(dustSmall, Meat, 6)
            .output(dustTiny, Bone)
            .duration(102).EUt(4).buildAndRegister();

        MACERATOR_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Items.CHICKEN))
            .output(dust, Meat)
            .output(dustTiny, Bone)
            .duration(102).EUt(4).buildAndRegister();

        MACERATOR_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Items.BEEF))
            .output(dustSmall, Meat, 6)
            .output(dustTiny, Bone)
            .duration(102).EUt(4).buildAndRegister();

        MACERATOR_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Items.RABBIT))
            .output(dustSmall, Meat, 6)
            .output(dustTiny, Bone)
            .duration(102).EUt(4).buildAndRegister();

        MACERATOR_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Items.MUTTON))
            .output(dust, Meat)
            .output(dustTiny, Bone)
            .duration(102).EUt(4).buildAndRegister();


    }

    private static void registerFluidRecipes() {


        FLUID_HEATER_RECIPES.recipeBuilder().duration(32).EUt(4)
                .fluidInputs(Ice.getFluid(L))
                .circuitMeta(1)
                .fluidOutputs(Water.getFluid(L)).buildAndRegister();



        FLUID_CANNER_RECIPES.recipeBuilder().duration(100).EUt(30).input(BATTERY_HULL_LV).fluidInputs(Mercury.getFluid(1000)).outputs(BATTERY_SU_LV_MERCURY.getChargedStack(Long.MAX_VALUE)).buildAndRegister();
        FLUID_CANNER_RECIPES.recipeBuilder().duration(200).EUt(30).input(BATTERY_HULL_MV).fluidInputs(Mercury.getFluid(4000)).outputs(BATTERY_SU_MV_MERCURY.getChargedStack(Long.MAX_VALUE)).buildAndRegister();
        FLUID_CANNER_RECIPES.recipeBuilder().duration(400).EUt(30).input(BATTERY_HULL_HV).fluidInputs(Mercury.getFluid(16000)).outputs(BATTERY_SU_HV_MERCURY.getChargedStack(Long.MAX_VALUE)).buildAndRegister();
        FLUID_CANNER_RECIPES.recipeBuilder().duration(100).EUt(30).input(BATTERY_HULL_LV).fluidInputs(SulfuricAcid.getFluid(1000)).outputs(BATTERY_SU_LV_SULFURIC_ACID.getChargedStack(Long.MAX_VALUE)).buildAndRegister();
        FLUID_CANNER_RECIPES.recipeBuilder().duration(200).EUt(30).input(BATTERY_HULL_MV).fluidInputs(SulfuricAcid.getFluid(4000)).outputs(BATTERY_SU_MV_SULFURIC_ACID.getChargedStack(Long.MAX_VALUE)).buildAndRegister();
        FLUID_CANNER_RECIPES.recipeBuilder().duration(400).EUt(30).input(BATTERY_HULL_HV).fluidInputs(SulfuricAcid.getFluid(16000)).outputs(BATTERY_SU_HV_SULFURIC_ACID.getChargedStack(Long.MAX_VALUE)).buildAndRegister();



        FLUID_SOLIDFICATION_RECIPES.recipeBuilder().duration(128).EUt(4).notConsumable(SHAPE_MOLD_BALL).fluidInputs(Water.getFluid(250)).outputs(new ItemStack(Items.SNOWBALL)).buildAndRegister();
        FLUID_SOLIDFICATION_RECIPES.recipeBuilder().duration(128).EUt(4).notConsumable(SHAPE_MOLD_BALL).fluidInputs(DistilledWater.getFluid(250)).outputs(new ItemStack(Items.SNOWBALL)).buildAndRegister();
        FLUID_SOLIDFICATION_RECIPES.recipeBuilder().duration(512).EUt(4).notConsumable(SHAPE_MOLD_BLOCK).fluidInputs(Water.getFluid(1000)).outputs(new ItemStack(Blocks.SNOW)).buildAndRegister();
        FLUID_SOLIDFICATION_RECIPES.recipeBuilder().duration(512).EUt(4).notConsumable(SHAPE_MOLD_BLOCK).fluidInputs(DistilledWater.getFluid(1000)).outputs(new ItemStack(Blocks.SNOW)).buildAndRegister();
        FLUID_SOLIDFICATION_RECIPES.recipeBuilder().duration(1024).EUt(16).notConsumable(SHAPE_MOLD_BLOCK).fluidInputs(Lava.getFluid(1000)).outputs(new ItemStack(Blocks.OBSIDIAN)).buildAndRegister();
        FLUID_SOLIDFICATION_RECIPES.recipeBuilder().duration(12).EUt(4).notConsumable(SHAPE_MOLD_BLOCK).fluidInputs(Glowstone.getFluid(L * 4)).outputs(new ItemStack(Blocks.GLOWSTONE)).buildAndRegister();
        FLUID_SOLIDFICATION_RECIPES.recipeBuilder().duration(12).EUt(4).notConsumable(SHAPE_MOLD_BLOCK).fluidInputs(Glass.getFluid(L)).outputs(new ItemStack(Blocks.GLASS)).buildAndRegister();

        FLUID_SOLIDFICATION_RECIPES.recipeBuilder().duration(12).EUt(4).notConsumable(SHAPE_MOLD_PLATE).fluidInputs(Glass.getFluid(L)).output(plate, Glass).buildAndRegister();
        FLUID_SOLIDFICATION_RECIPES.recipeBuilder().duration(128).EUt(16).notConsumable(SHAPE_MOLD_ANVIL).fluidInputs(Iron.getFluid(L * 31)).outputs(new ItemStack(Blocks.ANVIL)).buildAndRegister();
        FLUID_SOLIDFICATION_RECIPES.recipeBuilder().duration(128).EUt(16).notConsumable(SHAPE_MOLD_ANVIL).fluidInputs(WroughtIron.getFluid(L * 31)).outputs(new ItemStack(Blocks.ANVIL)).buildAndRegister();
        FLUID_SOLIDFICATION_RECIPES.recipeBuilder().duration(100).EUt(16).notConsumable(SHAPE_MOLD_BALL).fluidInputs(Toluene.getFluid(100)).output(GELLED_TOLUENE).buildAndRegister();

        FLUID_HEATER_RECIPES.recipeBuilder().duration(30).EUt(32).fluidInputs(Water.getFluid(6)).circuitMeta(1).fluidOutputs(Steam.getFluid(960)).buildAndRegister();
        FLUID_HEATER_RECIPES.recipeBuilder().duration(30).EUt(32).fluidInputs(DistilledWater.getFluid(6)).circuitMeta(1).fluidOutputs(Steam.getFluid(960)).buildAndRegister();

        ALLOY_SMELTER_RECIPES.recipeBuilder().input(ingot, Iron, 31).notConsumable(SHAPE_MOLD_ANVIL).outputs(new ItemStack(Blocks.ANVIL)).duration(512).EUt(60).buildAndRegister();
        ALLOY_SMELTER_RECIPES.recipeBuilder().input(ingot, WroughtIron, 31).notConsumable(SHAPE_MOLD_ANVIL).outputs(new ItemStack(Blocks.ANVIL)).duration(512).EUt(60).buildAndRegister();
    }

    private static <T extends Enum<T> & IStringSerializable> void registerBrickRecipe(StoneBlock<T> stoneBlock, T normalVariant, T brickVariant) {
        ModHandler.addShapedRecipe(stoneBlock.getRegistryName().getNamespace() + "_" + normalVariant + "_bricks",
            stoneBlock.getItemVariant(brickVariant, ChiselingVariant.NORMAL, 4),
            "XX", "XX", 'X',
            stoneBlock.getItemVariant(normalVariant, ChiselingVariant.NORMAL));
    }

    private static <T extends Enum<T> & IStringSerializable> void registerChiselingRecipes(StoneBlock<T> stoneBlock) {
        for (T variant : stoneBlock.getVariantValues()) {
            boolean isBricksVariant = variant.getName().endsWith("_bricks");
            if (!isBricksVariant) {
                ModHandler.addSmeltingRecipe(stoneBlock.getItemVariant(variant, ChiselingVariant.CRACKED), stoneBlock.getItemVariant(variant, ChiselingVariant.NORMAL));
                FORGE_HAMMER_RECIPES.recipeBuilder().duration(12).EUt(4)
                    .inputs(stoneBlock.getItemVariant(variant, ChiselingVariant.NORMAL))
                    .outputs(stoneBlock.getItemVariant(variant, ChiselingVariant.CRACKED))
                    .buildAndRegister();
            } else {
                ModHandler.addSmeltingRecipe(stoneBlock.getItemVariant(variant, ChiselingVariant.NORMAL), stoneBlock.getItemVariant(variant, ChiselingVariant.CRACKED));
            }
            CHEMICAL_BATH_RECIPES.recipeBuilder().duration(12).EUt(4)
                .inputs(stoneBlock.getItemVariant(variant, !isBricksVariant ? ChiselingVariant.CRACKED : ChiselingVariant.NORMAL))
                .fluidInputs(Materials.Water.getFluid(144))
                .outputs(stoneBlock.getItemVariant(variant, ChiselingVariant.MOSSY))
                .buildAndRegister();
            ModHandler.addShapelessRecipe(stoneBlock.getRegistryName().getPath() + "_chiseling_" + variant,
                stoneBlock.getItemVariant(variant, ChiselingVariant.CHISELED),
                stoneBlock.getItemVariant(variant, ChiselingVariant.NORMAL));
        }
    }
}
