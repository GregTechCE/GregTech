package gregtech.api.recipes;

import gregtech.api.recipes.RecipeBuilder.DefaultRecipeBuilder;
import gregtech.api.recipes.RecipeBuilder.IntCircuitRecipeBuilder;
import gregtech.api.recipes.RecipeBuilder.NotConsumableInputRecipeBuilder;
import gregtech.api.recipes.RecipeBuilder.UniversalDistillationRecipeBuilder;
import gregtech.api.recipes.builders.AssemblerRecipeBuilder;
import gregtech.api.recipes.machines.RecipeMapFluidCanner;
import gregtech.api.recipes.machines.RecipeMapFormingPress;
import gregtech.api.recipes.machines.RecipeMapFurnace;
import gregtech.api.recipes.machines.RecipeMapPrinter;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.EnumValidationResult;
import gregtech.api.util.GTLog;
import net.minecraftforge.fluids.FluidStack;

import java.util.HashSet;

public class RecipeMaps {

    public static final RecipeMap<DefaultRecipeBuilder> COMPRESSOR_RECIPES = new RecipeMap<>("compressor", 1, 1, 1, 1, 0, 0, 0, 0, 1, new DefaultRecipeBuilder().duration(400).EUt(2));
    public static final RecipeMap<DefaultRecipeBuilder> EXTRACTOR_RECIPES = new RecipeMap<>("extractor", 1, 1, 1, 1, 0, 0, 0, 0, 1, new DefaultRecipeBuilder().duration(400).EUt(2));
    public static final RecipeMap<DefaultRecipeBuilder> MACERATOR_RECIPES = new RecipeMap<>("macerator", 1, 1, 1, 4, 0, 0, 0, 0, 1, new DefaultRecipeBuilder().duration(400).EUt(2));

    public static final RecipeMap<DefaultRecipeBuilder> ORE_WASHER_RECIPES = new RecipeMap<>("orewasher", 1, 1, 3, 3, 0, 1, 0, 0, 1, new DefaultRecipeBuilder().duration(400).EUt(16));
    public static final RecipeMap<DefaultRecipeBuilder> THERMAL_CENTRIFUGE_RECIPES = new RecipeMap<>("thermalcentrifuge", 1, 1, 1, 3, 0, 0, 0, 0, 2, new DefaultRecipeBuilder().duration(400).EUt(48));

    public static final RecipeMap<DefaultRecipeBuilder> FURNACE_RECIPES = new RecipeMapFurnace("furnace", 1, 1, 1, 1, 0, 0, 0, 0, 1, new DefaultRecipeBuilder());
    public static final RecipeMap<DefaultRecipeBuilder> MICROWAVE_RECIPES = new RecipeMapFurnace("microwave", 1, 1, 1, 1, 0, 0, 0, 0, 1, new DefaultRecipeBuilder());

    public static final RecipeMap<IntCircuitRecipeBuilder> ASSEMBLER_RECIPES = new RecipeMap<>("assembler", 1, 2, 1, 1, 0, 1, 0, 0, 1, new AssemblerRecipeBuilder());

    public static final RecipeMap<DefaultRecipeBuilder> PRINTER_RECIPES = new RecipeMapPrinter("printer", 1, 2, 1, 1, 1, 1, 0, 0, 1, new DefaultRecipeBuilder());
    public static final RecipeMap<NotConsumableInputRecipeBuilder> PRESS_RECIPES = new RecipeMapFormingPress("press", 2, 2, 1, 1, 0, 0, 0, 0, 1, new NotConsumableInputRecipeBuilder());

    public static final RecipeMap<DefaultRecipeBuilder> FLUID_CANNER_RECIPES = new RecipeMapFluidCanner("fluidcanner", 1, 1, 0, 1, 0, 1, 0, 1, 1, new DefaultRecipeBuilder());


    public static final RecipeMap<DefaultRecipeBuilder> PLASMA_ARC_FURNACE_RECIPES = new RecipeMap<>(new HashSet<>(10000), "plasmaarcfurnace", "basicmachines/PlasmaArcFurnace", 1, 1, 1, 4, 1, 1, 0, 1, true, 1, 1, true, new DefaultRecipeBuilder());
    public static final RecipeMap<ArcFurnaceRecipeBuilder> ARC_FURNACE_RECIPES = new RecipeMap<>(new HashSet<>(10000), "arcfurnace", "basicmachines/ArcFurnace", 1, 1, 1, 4, 1, 1, 0, 0, true, 3, 1, true, new ArcFurnaceRecipeBuilder());

    /**
     * Example:
     * <pre>
     *     RecipeMap.SIFTER_RECIPES.recipeBuilder()
     *     			.inputs(new ItemStack(Blocks.SAND))
     *     			.chancedOutput(OreDictUnifier.get(OrePrefix.gemExquisite, Materials.Ruby, 1L), 300)
     *     			.chancedOutput(OreDictUnifier.get(OrePrefix.gemFlawless, Materials.Ruby, 1L), 1200)
     *     			.chancedOutput(OreDictUnifier.get(OrePrefix.gemFlawed, Materials.Ruby, 1L), 4500)
     *     			.chancedOutput(OreDictUnifier.get(OrePrefix.gemChipped, Materials.Ruby, 1L), 1400)
     *     			.chancedOutput(OreDictUnifier.get(OrePrefix.dust, Materials.Ruby, 1L), 2800)
     *     			.duration(800)
     *     			.EUt(16)
     *     			.buildAndRegister();
     * </pre>
     */
    public static final RecipeMap<DefaultRecipeBuilder> SIFTER_RECIPES = new RecipeMap<>(new HashSet<>(100), "sifter", "basicmachines/Sifter", 1, 1, 0, 6, 0, 0, 0, 0, true, 1, 1, true, new DefaultRecipeBuilder());

    /**
     * Example:
     * <pre>
     * 		RecipeMap.LASER_ENGRAVER_RECIPES.recipeBuilder()
     * 				.inputs(ItemList.IC2_LapotronCrystal.getWildcard(1))
     * 				.notConsumable(Items.APPLE)
     * 				.outputs(ItemList.Circuit_Parts_Crystal_Chip_Master.get(3))
     * 				.duration(256)
     * 				.EUt(480)
     * 				.buildAndRegister();
     * </pre>
     */
    public static final RecipeMap<NotConsumableInputRecipeBuilder> LASER_ENGRAVER_RECIPES = new RecipeMap<>(new HashSet<>(100), "laserengraver", "basicmachines/LaserEngraver", 2, 2, 1, 1, 0, 0, 0, 0, true, 1, 1, true, new NotConsumableInputRecipeBuilder());

    /**
     * Example:
     * <pre>
     * 		RecipeMap.MIXER_RECIPES.recipeBuilder()
     * 				.inputs(new ItemStack(Blocks.SAND, 1, GTValues.W), new ItemStack(Blocks.DIRT, 1, GTValues.W))
     * 				.fluidInputs(Materials.Water.getFluid(250))
     * 				.outputs(GT_ModHandler.getModItem("Forestry", "soil", 2, 1))
     * 				.duration(16)
     * 				.EUt(16)
     * 				.buildAndRegister();
     * </pre>
     */
    public static final RecipeMap<DefaultRecipeBuilder> MIXER_RECIPES = new RecipeMap<>(new HashSet<>(100), "mixer", "basicmachines/Mixer", 1, 4, 0, 0, 0, 1, 0, 1, true, 1, 1, true, new DefaultRecipeBuilder() {
        @Override
        private EnumValidationResult validate() {
            if (!((inputs.isEmpty() && fluidInputs.isEmpty()) || (outputs.isEmpty() && fluidOutputs.isEmpty()))){
                GTLog.logger.error("Recipe should have at least one input and one output", new IllegalArgumentException());
                recipeStatus = EnumValidationResult.INVALID;
            }
            return super.validate();
        }
    });

    /**
     * Example:
     * <pre>
     *	 	RecipeMap.AUTOCLAVE_RECIPES.recipeBuilder()
     * 				.inputs(OreDictUnifier.get(OrePrefix.dust, Materials.Carbon, 16))
     * 				.fluidInputs(Materials.Lutetium.getFluid(4))
     * 				.chancedOutput(GT_ModHandler.getIC2Item("carbonFiber", 8L), 3333)
     * 				.duration(600)
     * 				.EUt(5)
     * 				.buildAndRegister();
     * </pre>
     */
    public static final RecipeMap<DefaultRecipeBuilder> AUTOCLAVE_RECIPES = new RecipeMap<>(new HashSet<>(200), "autoclave", "basicmachines/Autoclave", 1, 1, 1, 1, 1, 1, 0, 0, true, 1, 1, true, new DefaultRecipeBuilder());

    /**
     * Example:
     * <pre>
     * 	    RecipeMap.ELECTROMAGNETIC_SEPARATOR_RECIPES.recipeBuilder()
     * 				.inputs(OreDictUnifier.get(OrePrefix.dustPure, Materials.Iron, 1L))
     * 				.chancedOutput(OreDictUnifier.get(OrePrefix.dust, Materials.Iron, 1L), 10000)
     * 				.chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Iron, 1L), 4000)
     * 				.chancedOutput(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 1L), 2000)
     * 				.duration(400)
     * 				.EUt(24)
     * 				.buildAndRegister();
     * </pre>
     */
    public static final RecipeMap<DefaultRecipeBuilder> ELECTROMAGNETIC_SEPARATOR_RECIPES = new RecipeMap<>(new HashSet<>(50), "electromagneticseparator", "basicmachines/ElectromagneticSeparator", 1, 1, 1, 3, 0, 0, 0, 0, true, 1, 1, true, new DefaultRecipeBuilder());

    /**
     * Example:
     * <pre>
     * 		RecipeMap.POLARIZER_RECIPES.recipeBuilder()
     * 				.inputs(OreDictUnifier.get(OrePrefix.plate, Materials.Iron, 1L))
     * 				.outputs(OreDictUnifier.get(aPrefix, Materials.IronMagnetic, 1L))
     * 				.duration(100)
     * 				.EUt(16)
     * 				.buildAndRegister();
     * </pre>
     */
    public static final RecipeMap<DefaultRecipeBuilder> POLARIZER_RECIPES = new RecipeMap<>(new HashSet<>(100), "polarizer", "basicmachines/Polarizer", 1, 1, 1, 1, 0, 0, 0, 0, true, 1, 1, true, new DefaultRecipeBuilder());

    /**
     * Example:
     * <pre>
     * 		RecipeMap.CHEMICAL_BATH_RECIPES.recipeBuilder()
     * 				.inputs(new ItemStack(Items.reeds, 1, GTValues.W))
     * 				.fluidInputs(Materials.Water.getFluid(100))
     * 				.outputs(new ItemStack(Items.paper, 1, 0))
     * 				.duration(100)
     * 				.EUt(8)
     * 				.buildAndRegister();
     * </pre>
     */
    public static final RecipeMap<DefaultRecipeBuilder> CHEMICAL_BATH_RECIPES = new RecipeMap<>(new HashSet<>(200), "chemicalbath", "basicmachines/ChemicalBath", 1, 1, 1, 3, 1, 1, 0, 0, true, 1, 1, true, new DefaultRecipeBuilder());

    /**
     * Example:
     * <pre>
     *      RecipeMap.BREWING_RECIPES.recipeBuilder()
     *         		.inputs(ItemList.IC2_Hops.get(1))
     *         		.fluidInput(FluidRegistry.WATER)
     *         		.fluidOutput(FluidRegistry.getFluid("potion.hopsjuice"))
     *         		.buildAndRegister();
     * </pre>
     */
    public static final RecipeMap<BrewingRecipeBuilder> BREWING_RECIPES = new RecipeMap<>(new HashSet<>(100), "brewer", "basicmachines/PotionBrewer", 1, 1, 0, 0, 1, 1, 1, 1, true, 1, 1, true, new BrewingRecipeBuilder().notOptimized().duration(128).EUt(4));

    /**
     * Example:
     * <pre>
     * 		RecipeMap.FLUID_HEATER_RECIPES.recipeBuilder()
     * 				.circuitMeta(1)
     * 				.fluidInputs(Materials.Water.getFluid(6))
     * 				.fluidOutputs(Materials.Water.getGas(960))
     * 				.duration(30)
     * 				.EUt(32)
     * 				.buildAndRegister();
     * </pre>
     */
    public static final RecipeMap<IntCircuitRecipeBuilder> FLUID_HEATER_RECIPES = new RecipeMap<>(new HashSet<>(100), "fluidheater", "basicmachines/FluidHeater", 1, 1, 0, 0, 1, 1, 1, 1, true, 1, 1, true, new IntCircuitRecipeBuilder());

    /**
     * Example:
     * <pre>
     *	 	RecipeMap.DISTILLERY_RECIPES.recipeBuilder()
     *	 			.circuitMeta(4)
     *	 			.fluidInputs(Materials.Creosote.getFluid(3))
     *	 			.fluidOutputs(Materials.Lubricant.getFluid(1))
     *	 			.duration(16)
     *	 			.EUt(24)
     *	 			.buildAndRegister();
     * </pre>
     */
    public static final RecipeMap<IntCircuitRecipeBuilder> DISTILLERY_RECIPES = new RecipeMap<>(new HashSet<>(100), "distillery", "basicmachines/Distillery", 1, 1, 0, 0, 1, 1, 1, 1, true, 1, 1, true, new IntCircuitRecipeBuilder());

    /**
     * Example:
     * <pre>
     *      RecipeMap.FERMENTING_RECIPES.recipeBuilder()
     *				.fluidInputs(FluidRegistry.getFluidStack("potion.lemonjuice", 50))
     *				.fluidOutputs(FluidRegistry.getFluidStack("potion.limoncello", 25))
     *				.duration(1024)
     *				.buildAndRegister();
     * </pre>
     */
    public static final RecipeMap<DefaultRecipeBuilder> FERMENTING_RECIPES = new RecipeMap<>(new HashSet<>(100), "fermenter", "basicmachines/Fermenter", 0, 0, 0, 0, 1, 1, 1, 1, true, 1, 1, true, new DefaultRecipeBuilder().notOptimized().EUt(2));

    /**
     * Example:
     * <pre>
     *  	RecipeMap.FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
     *				.notConsumable(ItemList.Shape_Mold_Casing)
     *				.fluidInputs(Materials.Steel.getFluid(72))
     *				.outputs(ItemList.IC2_Item_Casing_Steel.get(1))
     *				.duration(16)
     *				.EUt(8)
     *				.buildAndRegister();
     * </pre>
     */
    public static final RecipeMap<NotConsumableInputRecipeBuilder> FLUID_SOLIDFICATION_RECIPES = new RecipeMap<>(new HashSet<>(100), "fluidsolidifier", "basicmachines/FluidSolidifier", 1, 1, 1, 1, 1, 1, 0, 0, true, 1, 1, true, new NotConsumableInputRecipeBuilder());

    /**
     * Examples:
     * <pre>
     * 		RecipeMap.FLUID_EXTRACTION_RECIPES.recipeBuilder()
     * 				.inputs(new ItemStack(Blocks.SNOW))
     * 				.fluidOutputs(Materials.Water.getFluid(1000))
     * 				.duration(128)
     * 				.EUt(4)
     * 				.buildAndRegister();
     *
     * 		RecipeMap.FLUID_EXTRACTION_RECIPES.recipeBuilder()
     * 				.inputs(GT_ModHandler.getModItem("Forestry", "phosphor", 1))
     * 				.chancedOutput(OreDictUnifier.get(OrePrefix.dust, Materials.Phosphorus, 1), 1000)
     * 				.fluidOutputs(Materials.Lava.getFluid(800))
     * 				.duration(256)
     * 				.EUt(128)
     * 				.buildAndRegister();
     * </pre>
     */
    public static final RecipeMap<NotConsumableInputRecipeBuilder> FLUID_EXTRACTION_RECIPES = new RecipeMap<>(new HashSet<>(100), "fluidextractor", "basicmachines/FluidExtractor", 1, 1, 0, 1, 0, 0, 1, 1, true, 1, 1, true, new NotConsumableInputRecipeBuilder());

    /**
     * Input item and empty box, output full box
     * Example:
     * <pre>
     *   	RecipeMap.BOXINATOR_RECIPES.recipeBuilder()
     *				.inputs(ItemList.Tool_Matches.get(16), OreDictUnifier.get(OrePrefix.plateDouble, Materials.Paper, 1L))
     *				.outputs(ItemList.Tool_MatchBox_Full.get(1))
     *				.duration(64)
     *				.EUt(16)
     *				.buildAndRegister();
     * </pre>
     */
    public static final RecipeMap<DefaultRecipeBuilder> BOXINATOR_RECIPES = new RecipeMap<>(new HashSet<>(2500), "packager", "basicmachines/Packager", 2, 2, 1, 1, 0, 0, 0, 0, true, 1, 1, true, new DefaultRecipeBuilder());

    /**
     * Example:
     * <pre>
     * 		RecipeMap.FUSION_RECIPES.recipeBuilder()
     *				.fluidInputs(Materials.Lithium.getFluid(16), Materials.Tungsten.getFluid(16))
     *				.fluidOutputs(Materials.Iridium.getFluid(16))
     *				.duration(32)
     *				.EUt(32768)
     *				.EUToStart(300000000)
     *				.buildAndRegister();
     * </pre>
     */
    public static final RecipeMap<FusionRecipeBuilder> FUSION_RECIPES = new RecipeMap<>(new HashSet<>(50), "fusionreactor", "basicmachines/Default", 0, 0, 0, 0, 2, 2, 1, 1, true, 1, 1, true, new FusionRecipeBuilder());

    /**
     * Examples:
     * <pre>
     *		RecipeMap.CENTRIFUGE_RECIPES.recipeBuilder()
     *				.inputs(new ItemStack(Blocks.SANDSTONE))
     *				.cellAmount(1)
     *				.outputs(OreDictUnifier.get(OrePrefix.cell, Materials.Oil, 1L), new ItemStack(Blocks.SAND, 1, 0))
     *				.duration(1000)
     *				.buildAndRegister();
     *
     *		RecipeMap.CENTRIFUGE_RECIPES.recipeBuilder()
     *				.inputs(new ItemStack(Blocks.PUMPKIN, 16))
     *				.fluidOutputs(Materials.Methane.getGas(1152))
     *				.duration(4608)
     *				.buildAndRegister();
     * </pre>
     */
    public static final RecipeMap<DefaultRecipeBuilder> CENTRIFUGE_RECIPES = new RecipeMap<>(new HashSet<>(1000), "centrifuge", "basicmachines/Centrifuge", 0, 2, 0, 6, 0, 1, 0, 1, true, 1, 1, true, new DefaultRecipeBuilder() {
        @Override
        private EnumValidationResult validate() {
            if (!((inputs.isEmpty() && fluidInputs.isEmpty()) || (outputs.isEmpty() && fluidOutputs.isEmpty()))){
                GTLog.logger.error("Recipe should have at least one input and one output", new IllegalArgumentException());
                recipeStatus = EnumValidationResult.INVALID;
            }
            return super.validate();
        }
    }.EUt(5));

    /**
     * Examples:
     * <pre>
     *	   	RecipeMap.ELECTROLYZER_RECIPES.recipeBuilder()
     *	   			.cellAmount(1)
     *	   			.fluidInputs(new FluidStack(ItemList.sBlueVitriol,9000))
     *	   			.fluidOutputs(Materials.SulfuricAcid.getFluid(8000))
     *	   			.outputs(OreDictUnifier.get(OrePrefix.dust,Materials.Copper,1), OreDictUnifier.get(OrePrefix.cell,Materials.Oxygen,1))
     *	   			.duration(900)
     *	   			.EUt(30)
     *	   			.buildAndRegister();
     *
     *		RecipeMap.ELECTROLYZER_RECIPES.recipeBuilder()
     *				.inputs(OreDictUnifier.get(OrePrefix.dust, Materials.Tungstate, 7L))
     *				.fluidInputs(Materials.Hydrogen.getGas(7000))
     *				.fluidOutputs(Materials.Oxygen.getGas(4000))
     *				.outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Tungsten, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Lithium, 2L))
     *				.duration(120)
     *				.EUt(1920)
     *				.buildAndRegister();
     * </pre>
     */
    public static final RecipeMap<DefaultRecipeBuilder> ELECTROLYZER_RECIPES = new RecipeMap<>(new HashSet<>(200), "electrolyzer", "basicmachines/Electrolyzer", 0, 2, 0, 6, 0, 1, 0, 1, true, 1, 1, true, new DefaultRecipeBuilder() {
        @Override
        private EnumValidationResult validate() {
            if (!((inputs.isEmpty() && fluidInputs.isEmpty()) || (outputs.isEmpty() && fluidOutputs.isEmpty()))){
                GTLog.logger.error("Recipe should have at least one input and one output", new IllegalArgumentException());
                recipeStatus = EnumValidationResult.INVALID;
            }
            return super.validate();
        }
    });

    /**
     * Example:
     * <pre>
     *	    RecipeMap.BLAST_RECIPES.recipeBuilder()
     *				.inputs(OreDictUnifier.get(OrePrefix.dust, Materials.Glass, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Carbon, 1))
     *				.fluidInputs(Materials.Electrum.getFluid(16))
     *				.outputs(ItemList.Circuit_Board_Fiberglass.get(16))
     *				.duration(80)
     *				.EUt(480)
     *				.blastFurnaceTemp(2600)
     *				.buildAndRegister();
     * </pre>
     */
    public static final RecipeMap<BlastRecipeBuilder> BLAST_RECIPES = new RecipeMap<>(new HashSet<>(500), "blastfurnace", "basicmachines/Default", 1, 2, 1, 2, 0, 1, 0, 1, false, 1, 1, true, new BlastRecipeBuilder());

    /**
     * Example:
     * <pre>
     *      RecipeMap.IMPLOSION_RECIPES.recipeBuilder()
     *         		.inputs(ItemList.Ingot_IridiumAlloy.get(1))
     *         		.explosivesAmount(8)
     *         		.outputs(OreDictUnifier.get(OrePrefix.plateAlloy, Materials.Iridium, 1), OreDictUnifier.get(OrePrefix.dustTiny, Materials.DarkAsh, 4L))
     *         		.buildAndRegister();
     * </pre>
     */
    public static final RecipeMap<ImplosionRecipeBuilder> IMPLOSION_RECIPES = new RecipeMap<>(new HashSet<>(50), "implosioncompressor", "basicmachines/Default", 1, 2, 1, 2, 0, 0, 0, 0, true, 1, 1, true, new ImplosionRecipeBuilder().duration(20).EUt(30));

    /**
     * Example:
     * <pre>
     * 		RecipeMap.VACUUM_RECIPES.recipeBuilder()
     *				.inputs(OreDictUnifier.get(OrePrefix.cell, Materials.Water, 1L))
     *				.outputs(OreDictUnifier.get(OrePrefix.cell, Materials.Ice, 1L))
     *				.duration(50)
     *				.buildAndRegister();
     * </pre>
     */
    public static final RecipeMap<DefaultRecipeBuilder> VACUUM_RECIPES = new RecipeMap<>(new HashSet<>(100), "vacuumfreezer", "basicmachines/Default", 1, 1, 1, 1, 0, 0, 0, 0, true, 1, 1, true, new DefaultRecipeBuilder().EUt(120));

    /**
     * Example:
     * <pre>
     *      RecipeMap.CHEMICAL_RECIPES.recipeBuilder()
     *				.inputs(OreDictUnifier.get(OrePrefix.cell, Materials.NitrogenDioxide, 4), OreDictUnifier.get(OrePrefix.cell, Materials.Oxygen, 1))
     *				.fluidInputs(Materials.Water.getFluid(2000))
     *				.fluidOutputs( new FluidStack(ItemList.sNitricAcid,4000))
     *				.outputs(ItemList.Cell_Empty.get(5))
     *				.duration(950)
     *				.EUt(30)
     *				.buildAndRegister();
     * </pre>
     */
    public static final RecipeMap<DefaultRecipeBuilder> CHEMICAL_RECIPES = new RecipeMap<>(new HashSet<>(100), "chemicalreactor", "basicmachines/ChemicalReactor", 0, 2, 0, 1, 0, 1, 0, 1, true, 1, 1, true, new DefaultRecipeBuilder() {
        @Override
        private EnumValidationResult validate() {
            if (!((inputs.isEmpty() && fluidInputs.isEmpty()) || (outputs.isEmpty() && fluidOutputs.isEmpty()))){
                GTLog.logger.error("Recipe should have at least one input and one output", new IllegalArgumentException());
                recipeStatus = EnumValidationResult.INVALID;
            }
            return super.validate();
        }
    }.duration(30));

    /**
     * If universal every Fluid also gets separate distillation recipes
     * Examples:
     * <pre>
     *	    RecipeMap.DISTILLATION_RECIPES.recipeBuilder()
     *	        	.fluidInputs(new FluidStack(FluidName.biomass.getInstance(), 250))
     *	        	.fluidOutputs(new FluidStack(FluidRegistry.getFluid("ic2biogas"), 8000), Materials.Water.getFluid(125))
     *	        	.outputs(ItemList.IC2_Fertilizer.get(1))
     *	        	.duration(250)
     *	        	.EUt(480)
     *	        	.buildAndRegister();
     *
     *		RecipeMap.DISTILLATION_RECIPES.recipeBuilder()
     *				.universal()
     *				.fluidInputs(Materials.CrackedHeavyFuel.getFluid(100))
     *				.fluidOutputs(Materials.Gas.getGas(80), Materials.Naphtha.getFluid(10), Materials.LightFuel.getFluid(40), new FluidStack(ItemList.sToluene,30), Materials.Lubricant.getFluid(5))
     *				.outputs(OreDictUnifier.get(OrePrefix.dustTiny, Materials.HydratedCoal, 1))
     *				.duration(16)
     *				.EUt(64)
     *				.buildAndRegister();
     * </pre>
     */
    public static final RecipeMap<UniversalDistillationRecipeBuilder> DISTILLATION_RECIPES = new RecipeMap<>(new HashSet<>(50), "distillationtower", "basicmachines/Default", 0, 0, 0, 1, 1, 1, 1, 5, true, 1, 1, true, new UniversalDistillationRecipeBuilder().notOptimized());

    /**
     * Example:
     * <pre>
     *      RecipeMap.CRACKING_RECIPES.recipeBuilder()
     *         		.fluidInputs(Materials.HeavyFuel.getFluid(128))
     *         		.fluidOutputs(Materials.CrackedHeavyFuel.getFluid(192))
     *         		.duration(16)
     *         		.EUt(320)
     *         		.buildAndRegister();
     * </pre>
     */
    public static final RecipeMap<DefaultRecipeBuilder> CRACKING_RECIPES = new RecipeMap<>(new HashSet<>(50), "craker", "basicmachines/Default", 0, 0, 0, 0, 1, 2, 1, 2, true, 1, 1, true, new DefaultRecipeBuilder() {
        @Override
        public void buildAndRegister() {
            super.buildAndRegister();
            FluidStack fluidInput = fluidInputs.get(0);
            FluidStack fluidOutput = fluidInputs.get(0);
            recipeMap.addRecipe(this.copy().fluidInputs(fluidInput, ModHandler.getSteam(fluidInput.amount)).fluidOutputs(fluidOutput, Materials.Hydrogen.getFluid(fluidInput.amount)).build());
            recipeMap.addRecipe(this.copy().fluidInputs(fluidInput, Materials.Hydrogen.getFluid(fluidInput.amount)).fluidOutputs(new FluidStack(fluidOutput.getFluid(), (int) (fluidOutput.amount * 1.3))).build());
        }
    }.notOptimized());

    /**
     * Example:
     * <pre>
     *      RecipeMap.PYROLYSE_RECIPES.recipeBuilder()
     *     			.inputs(new ItemStack(Blocks.LOG, 16))
     *     			.circuitMeta(2)
     *     			.fluidInputs(Materials.Nitrogen.getGas(1000))
     *     			.outputs(new ItemStack(Items.COAL, 20, 1))
     *     			.fluidOutputs(Materials.Creosote.getFluid(4000))
     *     			.duration(320)
     *     			.EUt(96)
     *     			.buildAndRegister();
     * </pre>
     */
    public static final RecipeMap<IntCircuitRecipeBuilder> PYROLYSE_RECIPES = new RecipeMap<>(new HashSet<>(50), "pyro", 2, 2, 0, 1, 0, 1, 1, 1, true, 1, 1, true, new IntCircuitRecipeBuilder().notOptimized());

    /**
     * Example:
     * <pre>
     * 		RecipeMap.WIREMILL_RECIPES.recipeBuilder()
     *				.inputs(OreDictUnifier.get(OrePrefix.plate, Materials.Iron, 1L))
     *				.outputs(GT_ModHandler.getIC2Item("ironCableItem", 6L))
     *				.duration(200)
     *				.EUt(2)
     *				.buildAndRegister();
     * </pre>
     */
    public static final RecipeMap<DefaultRecipeBuilder> WIREMILL_RECIPES = new RecipeMap<>(new HashSet<>(50), "wiremill", 1, 1, 1, 1, 0, 0, 0, 0, true, 1, 1, true, new DefaultRecipeBuilder());

    /**
     * Example:
     * <pre>
     *		RecipeMap.BENDER_RECIPES.recipeBuilder()
     *				.inputs(OreDictUnifier.get(OrePrefix.plate, Materials.Tin, 12L))
     *				.outputs(ItemList.Cell_Empty.get(6))
     *				.duration(1200)
     *				.EUt(8)
     *				.buildAndRegister();
     * </pre>
     */
    public static final RecipeMap<IntCircuitRecipeBuilder> BENDER_RECIPES = new RecipeMap<>(new HashSet<>(400), "metalbender", 2, 2, 1, 1, 0, 0, 0, 0, true, 1, 1, true, new IntCircuitRecipeBuilder() {
        @Override
        private EnumValidationResult finalizeAndValidate() {
            this.circuitMeta(this.inputs.get(0).getCount());
            return super.finalizeAndValidate();
        }
    });


    public static final RecipeMap<NotConsumableInputRecipeBuilder> ALLOY_SMELTER_RECIPES = new RecipeMap<>("alloysmelter", 1, 2, 1, 1, 0, 0, 0, 1, new NotConsumableInputRecipeBuilder());

    /**
     * Example:
     * <pre>
     *       RecipeMap.CANNER_RECIPES.recipeBuilder()
     * 				.inputs(new ItemStack(Items.cake, 1, GTValues.W), ItemList.IC2_Food_Can_Empty.get(12))
     * 				.outputs(ItemList.IC2_Food_Can_Filled.get(12))
     * 				.duration(600)
     * 				.EUt(1)
     * 				.buildAndRegister();
     * </pre>
     */
    public static final RecipeMap<DefaultRecipeBuilder> CANNER_RECIPES = new RecipeMap<>(new HashSet<>(300), "canner", "basicmachines/Canner", 1, 2, 1, 2, 0, 0, 0, 0, true, 1, 1, true, new DefaultRecipeBuilder());

    /**
     * Example:
     * <pre>
     *	     RecipeMap.LATHE_RECIPES.recipeBuilder()
     * 				.inputs(OreDictUnifier.get(OrePrefix.gemExquisite, Materials.Ruby, 1L))
     * 				.outputs(OreDictUnifier.get(OrePrefix.lens, Materials.Ruby, 1L), OreDictUnifier.get(OrePrefix.dust, Materials.Ruby, 2L))
     * 				.duration(Materials.Ruby.getMass())
     * 				.EUt(24)
     * 				.buildAndRegister();
     * </pre>
     */
    public static final RecipeMap<DefaultRecipeBuilder> LATHE_RECIPES = new RecipeMap<>(new HashSet<>(400), "lathe", "basicmachines/Lathe", 1, 1, 1, 2, 0, 0, 0, 0, true, 1, 1, true, new DefaultRecipeBuilder());

    /**
     * Example:
     * <pre>
     *      RecipeMap.CUTTER_RECIPES.recipeBuilder()
     *				.inputs(new ItemStack(Blocks.LOG))
     *				.fluidInputs(Materials.Lubricant.getFluid(1))
     *				.outputs(new ItemStack(Blocks.PLANKS), OreDictUnifier.get(OrePrefix.dust, Materials.Wood, 1L))
     *				.duration(200)
     *				.EUt(8)
     *				.buildAndRegister();
     * </pre>
     */
    public static final RecipeMap<DefaultRecipeBuilder> CUTTER_RECIPES = new RecipeMap<>(new HashSet<>(200), "cuttingsaw", "basicmachines/Cutter", 1, 1, 1, 1, 0, 1, 0, 0, true, 1, 1, true, new DefaultRecipeBuilder() {
        @Override
        public void buildAndRegister() {
            if (fluidInputs.isEmpty()) {
                recipeMap.addRecipe(this.copy().fluidInputs(Materials.Water.getFluid(Math.max(4, Math.min(1000, duration * EUt / 320)))).duration(duration * 2).build());
                recipeMap.addRecipe(this.copy().fluidInputs(ModHandler.getDistilledWater(Math.max(3, Math.min(750, duration * EUt / 426)))).duration(duration * 2).build());
                recipeMap.addRecipe(this.copy().fluidInputs(Materials.Lubricant.getFluid(Math.max(1, Math.min(250, duration * EUt / 1280)))).duration(duration * 2).build());
            } else {
                recipeMap.addRecipe(build());
            }
        }
    });

    /**
     * <pre>
     * Example:
     *	    RecipeMap.SLICER_RECIPES.recipeBuilder()
     *				.inputs(ItemList.Food_Baked_Bread.get(1))
     *				.notConsumable(ItemList.Shape_Slicer_Flat)
     *				.outputs(ItemList.Food_Sliced_Bread.get(2))
     *				.duration(128)
     *				.EUt(4)
     *				.buildAndRegister();
     * </pre>
     */
    public static final RecipeMap<NotConsumableInputRecipeBuilder> SLICER_RECIPES = new RecipeMap<>(new HashSet<>(200), "slicer", "basicmachines/Slicer", 2, 2, 1, 1, 0, 0, 0, 0, true, 1, 1, true, new NotConsumableInputRecipeBuilder());

    /**
     * Example:
     * <pre>
     *      RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
     *				.inputs(new ItemStack(Items.IRON_INGOT))
     *				.notConsumable(ItemList.Shape_Extruder_Rod)
     *				.outputs(OreDictUnifier.get(OrePrefix.stick, Materials.Iron.smeltInto, 2))
     *				.duration(64)
     *				.EUt(8)
     *				.buildAndRegister();
     * </pre>
     */
    public static final RecipeMap<NotConsumableInputRecipeBuilder> EXTRUDER_RECIPES = new RecipeMap<>(new HashSet<>(1000), "extruder", "basicmachines/Extruder", 2, 2, 1, 1, 0, 0, 0, 0, true, 1, 1, true, new NotConsumableInputRecipeBuilder());

    /**
     * Example:
     * <pre>
     *      RecipeMap.HAMMER_RECIPES.recipeBuilder()
     *				.inputs(new ItemStack(Blocks.STONE, 1, 0))
     *				.outputs(new ItemStack(Blocks.COBBLESTONE, 1, 0))
     *				.duration(16)
     *				.EUt(10)
     *				.buildAndRegister();
     * </pre>
     */
    public static final RecipeMap<DefaultRecipeBuilder> HAMMER_RECIPES = new RecipeMap<>(new HashSet<>(200), "hammer", "basicmachines/Hammer", 1, 1, 1, 1, 0, 0, 0, 0, true, 1, 1, true, new DefaultRecipeBuilder());

    /**
     * Example:
     * <pre>
     *      RecipeMap.AMPLIFIERS.recipeBuilder()
     *      		.inputs(ItemList.IC2_Scrap.get(9))
     *      		.duration(180)
     *      		.amplifierAmountOutputted(1)
     *      		.buildAndRegister();
     * </pre>
     */
    public static final RecipeMap<AmplifierRecipeBuilder> AMPLIFIERS = new RecipeMap<>("uuamplifier", "basicmachines/Amplifabricator", 1, 1, 0, 0, 0, 0, 1, 1, true, 1, 1, true, new AmplifierRecipeBuilder().EUt(32));

    public static final RecipeMap<DefaultRecipeBuilder> DIESEL_FUELS = new RecipeMap<>("dieselgeneratorfuel", "basicmachines/Default", 1, 1, 0, 4, 0, 0, 0, 0, true, 1, 1000, true, new DefaultRecipeBuilder());
    public static final RecipeMap<DefaultRecipeBuilder> TURBINE_FUELS = new RecipeMap<>("gasturbinefuel", "basicmachines/Default", 0, 1, 0, 4, 0, 0, 0, 0, true, 1, 1000, true, new DefaultRecipeBuilder());
    public static final RecipeMap<DefaultRecipeBuilder> HOT_FUELS = new RecipeMap<>("thermalgeneratorfuel", "basicmachines/Default", 0, 1, 0, 4, 0, 0, 0, 0, true, 1, 1000, false, new DefaultRecipeBuilder());
    public static final RecipeMap<DefaultRecipeBuilder> DENSE_LIQUID_FUELS = new RecipeMap<>("semifluidboilerfuels", "basicmachines/Default", 0, 1, 0, 4, 0, 0, 0, 0, true, 1, 1000, true, new DefaultRecipeBuilder());
    public static final RecipeMap<DefaultRecipeBuilder> PLASMA_FUELS = new RecipeMap<>("plasmageneratorfuels", "basicmachines/Default", 0, 1, 0, 4, 0, 0, 0, 0, true, 1, 1000, true, new DefaultRecipeBuilder());

    /**
     * Use {@link RecipeBuilder#EUt(int)} to set EU/t produced
     */
    public static final RecipeMap<DefaultRecipeBuilder> MAGIC_FUELS = new RecipeMap<>("magicfuels", "basicmachines/Default", 1, 1, 0, 1, 0, 0, 0, 0, true, 1, 1000, true, new DefaultRecipeBuilder());

    public static final RecipeMap<DefaultRecipeBuilder> SMALL_NAQUADAH_REACTOR_FUELS = new RecipeMap<>("smallnaquadahreactor", "basicmachines/Default", 1, 1, 0, 0, 0, 1, 0, 0, true, 1, 1000, true, new DefaultRecipeBuilder());
    public static final RecipeMap<DefaultRecipeBuilder> LARGE_NAQUADAH_REACTOR_FUELS = new RecipeMap<>("largenaquadahreactor", "basicmachines/Default", 1, 1, 0, 0, 0, 1, 0, 0, true, 1, 1000, true, new DefaultRecipeBuilder());
    public static final RecipeMap<DefaultRecipeBuilder> FLUID_NAQUADAH_REACTOR_FUELS = new RecipeMap<>("fluidnaquadahreactor", "basicmachines/Default", 0, 0, 0, 0, 1, 1, 0, 0, true, 1, 1000, true, new DefaultRecipeBuilder());


}
