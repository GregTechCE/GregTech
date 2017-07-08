package gregtech.api.recipes;

import gregtech.api.GregTech_API;
import gregtech.api.enums.Dyes;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.SubTag;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.interfaces.tileentity.IHasWorldObjectAndCoords;
import gregtech.api.objects.GT_ItemStack;
import gregtech.api.objects.ItemData;
import gregtech.api.objects.MaterialStack;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_Log;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;
import org.apache.commons.lang3.Validate;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static gregtech.api.enums.GT_Values.D1;
import static gregtech.api.enums.GT_Values.L;
import static gregtech.api.enums.GT_Values.W;

public class RecipeMap<T extends Recipe, R extends Recipe.RecipeBuilder<T, R>> {

	/**
	 * Contains all Recipe Maps
	 */
	public static final Collection<RecipeMap<?, ?>> RECIPE_MAPS = new ArrayList<>();

	public static final RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> ORE_WASHER_RECIPES = new GT_Recipe_Map_OreWasher(new HashSet<>(0), "ic.recipe.orewasher", "Ore Washer", "ic2.blockOreWashingPlant", "basicmachines/OreWasher", 1, 1, 3, 3, 0, 1, 0, 0, 1, "", 1, "", true, false, new Recipe.DefaultRecipeBuilder());
	public static final RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> THERMAL_CENTRIFUGE_RECIPES = new GT_Recipe_Map_ThermalCentrifuge(new HashSet<>(0), "ic.recipe.thermalcentrifuge", "Thermal Centrifuge", "ic2.blockCentrifuge", "basicmachines/ThermalCentrifuge", 1, 1, 1, 3, 0, 0, 0, 0, 2, "", 1, "", true, false, new Recipe.DefaultRecipeBuilder());
	public static final RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> COMPRESSOR_RECIPES = new GT_Recipe_Map_Compressor(new HashSet<>(0), "ic.recipe.compressor", "Compressor", "ic2.compressor", "basicmachines/Compressor", 1, 1, 1, 1, 0, 0, 0, 0, 1, "", 1, "", true, false, new Recipe.DefaultRecipeBuilder());
	public static final RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> EXTRACTOR_RECIPES = new GT_Recipe_Map_Extractor(new HashSet<>(0), "ic.recipe.extractor", "Extractor", "ic2.extractor", "basicmachines/Extractor", 1, 1, 1, 1, 0, 0, 0, 0, 1, "", 1, "", true, false, new Recipe.DefaultRecipeBuilder());
	public static final RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> RECYCLER_RECIPES = new GT_Recipe_Map_Recycler(new HashSet<>(0), "ic.recipe.recycler", "Recycler", "ic2.recycler", "basicmachines/Recycler", 1, 0, 0, 0, 0, 0, 0, 0, 1, "", 1, "", true, false, new Recipe.DefaultRecipeBuilder()); //TODO min max amounts
	public static final RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> FURNACE_RECIPES = new GT_Recipe_Map_Furnace(new HashSet<>(0), "mc.recipe.furnace", "Furnace", "smelting", "basicmachines/E_Furnace", 1, 0, 0, 0, 0, 0, 0, 0, 1, "", 1, "", true, false, new Recipe.DefaultRecipeBuilder()); //TODO min max amounts
	public static final RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> MICROWAVE_RECIPES = new GT_Recipe_Map_Microwave(new HashSet<>(0), "gt.recipe.microwave", "Microwave", "smelting", "basicmachines/E_Furnace", 1, 0, 0, 0, 0, 0, 0, 0, 1, "", 1, "", true, false, new Recipe.DefaultRecipeBuilder()); //TODO min max amounts

	public static final RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> SCANNER_FAKE_RECIPES = new FakeRecipeMap<>(new HashSet<>(300), "gt.recipe.scanner", "Scanner", null, "basicmachines/Scanner", 1, 0, 0, 0, 0, 0, 0, 0, 1, "", 1, "", true, true, new Recipe.DefaultRecipeBuilder());//TODO min max amounts
	public static final RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> ROCK_BREAKER_FAKE_RECIPES = new FakeRecipeMap<>(new HashSet<>(3), "gt.recipe.rockbreaker", "Rock Breaker", null, "basicmachines/RockBreaker", 0, 0, 0, 0, 0, 0, 0, 0, 1, "", 1, "", true, true, new Recipe.DefaultRecipeBuilder());//TODO min max amounts
	public static final RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> BY_PRODUCT_LIST = new FakeRecipeMap<>(new HashSet<>(1000), "gt.recipe.byproductlist", "Ore Byproduct List", null, "basicmachines/Default", 1, 0, 0, 0, 0, 0, 0, 0, 1, "", 1, "", true, true, new Recipe.DefaultRecipeBuilder());//TODO min max amounts
	public static final RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> REPICATOR_FAKE_RECIPES = new FakeRecipeMap<>(new HashSet<>(100), "gt.recipe.replicator", "Replicator", null, "basicmachines/Replicator", 0, 1, 0, 0, 0, 0, 0, 0, 1, "", 1, "", true, true, new Recipe.DefaultRecipeBuilder());//TODO min max amounts
	public static final RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> ASSEMBLYLINE_FAKE_RECIPES = new FakeRecipeMap<>(new HashSet<>(30), "gt.recipe.assemblyline", "Assembly Line", null, "basicmachines/Default", 1, 0, 0, 0, 0, 0, 0, 0, 1, "", 1, "", true, true, new Recipe.DefaultRecipeBuilder());//TODO min max amounts

	public static final RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> ASSEMBLER_RECIPES = new GT_Recipe_Map_Assembler(new HashSet<>(300), "gt.recipe.assembler", "Assembler", null, "basicmachines/Assembler", 1, 2, 1, 1, 0, 1, 0, 0, 1, "", 1, "", true, true, new Recipe.DefaultRecipeBuilder());
	public static final RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> PRINTER_RECIPES = new GT_Recipe_Map_Printer(new HashSet<>(100), "gt.recipe.printer", "Printer", null, "basicmachines/Printer", 1, 1, 1, 1, 1, 1, 0, 0, 1, "", 1, "", true, true, new Recipe.DefaultRecipeBuilder());

	/**
	 * Examples:
	 * RecipeMap.PRESS_RECIPES.recipeBuilder().inputs(ItemList.Empty_Board_Basic.get(1), ItemList.Circuit_Parts_Wiring_Basic.get(4)).outputs(ItemList.Circuit_Board_Basic.get(1)).duration(32).EUt(16).buildAndRegister();
	 * RecipeMap.PRESS_RECIPES.recipeBuilder().inputs(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 1)).notConsumable(ItemList.Shape_Mold_Credit).outputs(ItemList.Credit_Iron.get(4)).duration(100).EUt(16).buildAndRegister();
	 */
	public static final RecipeMap<Recipe, Recipe.NotConsumableInputRecipeBuilder> PRESS_RECIPES = new GT_Recipe_Map_FormingPress(new HashSet<>(100), "gt.recipe.press", "Forming Press", null, "basicmachines/Press", 2, 2, 1, 1, 0, 0, 0, 0, 1, "", 1, "", true, true, new Recipe.NotConsumableInputRecipeBuilder());
	public static final RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> MACERATOR_RECIPES = new GT_Recipe_Map_Macerator(new HashSet<>(10000), "gt.recipe.macerator", "Pulverization", null, "basicmachines/Macerator4", 1, 1, 1, 4, 0, 0, 0, 0, 1, "", 1, "", true, true, new Recipe.DefaultRecipeBuilder());

	/**
	 * Input full box, output item and empty box
	 */
	public static final RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> UNBOXINATOR_RECIPES = new GT_Recipe_Map_Unboxinator(new HashSet<>(2500), "gt.recipe.unpackager", "Unpackager", null, "basicmachines/Unpackager", 1, 1, 1, 2, 0, 0, 0, 0, 1, "", 1, "", true, true, new Recipe.DefaultRecipeBuilder());

	/**
	 * Example:
	 * RecipeMap.FLUID_CANNER_RECIPES.recipeBuilder().inputs(ItemList.Battery_Hull_LV.get(1)).outputs(ItemList.IC2_ReBattery.get(1)).fluidInputs(Materials.Redstone.getMolten(288L)).buildAndRegister();
	 */
	public static final RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> FLUID_CANNER_RECIPES = new GT_Recipe_Map_FluidCanner(new HashSet<>(100), "gt.recipe.fluidcanner", "Fluid Canning Machine", null, "basicmachines/FluidCannerJEI", 1, 1, 1, 1, 0, 1, 0, 1, 1, "", 1, "", true, true, new Recipe.DefaultRecipeBuilder() {
		@Override
		protected void fill() {
			duration(fluidOutputs.isEmpty() ? fluidInputs.get(0).amount / 62 : fluidOutputs.get(0).amount / 62);
			super.fill();
		}
	}.EUt(1));

	public static final RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> PLASMA_ARC_FURNACE_RECIPES = new RecipeMap<>(new HashSet<>(10000), "gt.recipe.plasmaarcfurnace", "Plasma Arc Furnace", null, "basicmachines/PlasmaArcFurnace", 1, 0, 0, 0, 0, 0, 1, 0, 1, "", 1, "", true, true, new Recipe.DefaultRecipeBuilder()); //TODO min max amounts
	public static final RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> ARC_FURNACE_RECIPES = new RecipeMap<>(new HashSet<>(10000), "gt.recipe.arcfurnace", "Arc Furnace", null, "basicmachines/ArcFurnace", 1, 0, 0, 0, 0, 0, 1, 0, 3, "", 1, "", true, true, new Recipe.DefaultRecipeBuilder()); //TODO
	public static final RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> SIFTER_RECIPES = new RecipeMap<>(new HashSet<>(100), "gt.recipe.sifter", "Sifter", null, "basicmachines/Sifter", 1, 0, 0, 0, 0, 0, 0, 0, 1, "", 1, "", true, true, new Recipe.DefaultRecipeBuilder()); //TODO

	/**
	 * Example:
	 * RecipeMap.LASER_ENGRAVER_RECIPES.recipeBuilder().inputs(ItemList.IC2_LapotronCrystal.getWildcard(1)).notConsumable(Items.APPLE).outputs(ItemList.Circuit_Parts_Crystal_Chip_Master.get(3)).duration(256).EUt(480).buildAndRegister();
	 */
	public static final RecipeMap<Recipe, Recipe.NotConsumableInputRecipeBuilder> LASER_ENGRAVER_RECIPES = new RecipeMap<>(new HashSet<>(100), "gt.recipe.laserengraver", "Precision Laser Engraver", null, "basicmachines/LaserEngraver", 2, 2, 1, 1, 0, 0, 0, 0, 1, "", 1, "", true, true, new Recipe.NotConsumableInputRecipeBuilder());
	public static final RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> MIXER_RECIPES = new RecipeMap<>(new HashSet<>(100), "gt.recipe.mixer", "Mixer", null, "basicmachines/Mixer", 1, 4, 0, 0, 0, 0, 0, 0, 1, "", 1, "", true, true, new Recipe.DefaultRecipeBuilder()); //TODO
	public static final RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> AUTOCLAVE_RECIPES = new RecipeMap<>(new HashSet<>(200), "gt.recipe.autoclave", "Autoclave", null, "basicmachines/Autoclave", 1, 1, 1, 1, 1, 1, 0, 0, 1, "", 1, "", true, true, new Recipe.DefaultRecipeBuilder());
	public static final RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> ELECTROMAGNETIC_SEPARATOR_RECIPES = new RecipeMap<>(new HashSet<>(50), "gt.recipe.electromagneticseparator", "Electromagnetic Separator", null, "basicmachines/ElectromagneticSeparator", 1, 1, 1, 3, 0, 0, 0, 0, 1, "", 1, "", true, true, new Recipe.DefaultRecipeBuilder());
	public static final RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> POLARIZER_RECIPES = new RecipeMap<>(new HashSet<>(100), "gt.recipe.polarizer", "Electromagnetic Polarizer", null, "basicmachines/Polarizer", 1, 1, 1, 1, 0, 0, 0, 0, 1, "", 1, "", true, true, new Recipe.DefaultRecipeBuilder());
	public static final RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> CHEMICAL_BATH_RECIPES = new RecipeMap<>(new HashSet<>(200), "gt.recipe.chemicalbath", "Chemical Bath", null, "basicmachines/ChemicalBath", 1, 1, 1, 3, 1, 1, 0, 0, 1, "", 1, "", true, true, new Recipe.DefaultRecipeBuilder());
	public static final RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> BREWING_RECIPES = new RecipeMap<>(new HashSet<>(100), "gt.recipe.brewer", "Brewing Machine", null, "basicmachines/PotionBrewer", 1, 1, 0, 0, 1, 1, 1, 1, 1, "", 1, "", true, true, new Recipe.DefaultRecipeBuilder().duration(128).EUt(4));

	/**
	 * Example:
	 * RecipeMap.FLUID_HEATER_RECIPES.recipeBuilder().circuitMeta(1).fluidInputs(Materials.Water.getFluid(6L)).fluidOutputs(Materials.Water.getGas(960L)).duration(30).EUt(32).buildAndRegister();
	 */
	public static final RecipeMap<Recipe, Recipe.IntCircuitRecipeBuilder> FLUID_HEATER_RECIPES = new RecipeMap<>(new HashSet<>(100), "gt.recipe.fluidheater", "Fluid Heater", null, "basicmachines/FluidHeater", 1, 1, 0, 0, 1, 1, 1, 1, 1, "", 1, "", true, true, new Recipe.IntCircuitRecipeBuilder());
	public static final RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> DISTILLERY_RECIPES = new RecipeMap<>(new HashSet<>(100), "gt.recipe.distillery", "Distillery", null, "basicmachines/Distillery", 1, 1, 0, 0, 1, 1, 1, 1, 1, "", 1, "", true, true, new Recipe.DefaultRecipeBuilder());
	public static final RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> FERMENTING_RECIPES = new RecipeMap<>(new HashSet<>(100), "gt.recipe.fermenter", "Fermenter", null, "basicmachines/Fermenter", 0, 0, 0, 0, 1, 1, 1, 1, 1, "", 1, "", true, true, new Recipe.DefaultRecipeBuilder());
	public static final RecipeMap<Recipe, Recipe.NotConsumableInputRecipeBuilder> FLUID_SOLIDFICATION_RECIPES = new RecipeMap<>(new HashSet<>(100), "gt.recipe.fluidsolidifier", "Fluid Solidifier", null, "basicmachines/FluidSolidifier", 1, 1, 1, 1, 1, 1, 0, 0, 1, "", 1, "", true, true, new Recipe.NotConsumableInputRecipeBuilder());

	/**
	 * Examples:
	 * RecipeMap.FLUID_EXTRACTION_RECIPES.recipeBuilder().inputs(new ItemStack(Blocks.SNOW)).fluidOutputs(Materials.Water.getFluid(1000L)).duration(128).EUt(4).buildAndRegister();
	 * RecipeMap.FLUID_EXTRACTION_RECIPES.recipeBuilder().inputs(GT_ModHandler.getModItem("Forestry", "phosphor", 1L)).chancedOutput(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Phosphor, 1L), 1000).fluidOutputs(Materials.Lava.getFluid(800L)).duration(256).EUt(128).buildAndRegister();
	 */
	public static final RecipeMap<Recipe, Recipe.NotConsumableInputRecipeBuilder> FLUID_EXTRACTION_RECIPES = new RecipeMap<>(new HashSet<>(100), "gt.recipe.fluidextractor", "Fluid Extractor", null, "basicmachines/FluidExtractor", 1, 1, 0, 1, 0, 0, 1, 1, 1, "", 1, "", true, true, new Recipe.NotConsumableInputRecipeBuilder());

	/**
	 * Input item and empty box, output full box
	 */
	public static final RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> BOXINATOR_RECIPES = new RecipeMap<>(new HashSet<>(2500), "gt.recipe.packager", "Packager", null, "basicmachines/Packager", 2, 2, 1, 1, 0, 0, 0, 0, 1, "", 1, "", true, true, new Recipe.DefaultRecipeBuilder());
	public static final RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> FUSION_RECIPES = new RecipeMap<>(new HashSet<>(50), "gt.recipe.fusionreactor", "Fusion Reactor", null, "basicmachines/Default", 0, 0, 0, 0, 2, 2, 1, 1, 1, "Start: ", 1, " EU", true, true, new Recipe.DefaultRecipeBuilder());
	public static final RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> CENTRIFUGE_RECIPES = new RecipeMap<>(new HashSet<>(1000), "gt.recipe.centrifuge", "Centrifuge", null, "basicmachines/Centrifuge", 0, 2, 0, 6, 0, 1, 0, 1, 1, "", 1, "", true, true, new Recipe.DefaultRecipeBuilder());
	public static final RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> ELECTROLYZER_RECIPES = new RecipeMap<>(new HashSet<>(200), "gt.recipe.electrolyzer", "Electrolyzer", null, "basicmachines/Electrolyzer", 0, 2, 0, 6, 0, 1, 0, 1, 1, "", 1, "", true, true, new Recipe.DefaultRecipeBuilder());
	public static final RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> BLAST_RECIPES = new RecipeMap<>(new HashSet<>(500), "gt.recipe.blastfurnace", "Blast Furnace", null, "basicmachines/Default", 1, 2, 1, 2, 0, 1, 0, 1, 1, "Heat Capacity: ", 1, " K", false, true, new Recipe.DefaultRecipeBuilder());
	public static final RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> IMPLOSION_RECIPES = new RecipeMap<>(new HashSet<>(50), "gt.recipe.implosioncompressor", "Implosion Compressor", null, "basicmachines/Default", 2, 0, 0, 0, 0, 0, 0, 0, 1, "", 1, "", true, true, new Recipe.DefaultRecipeBuilder()); //TODO
	public static final RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> VACUUM_RECIPES = new RecipeMap<>(new HashSet<>(100), "gt.recipe.vacuumfreezer", "Vacuum Freezer", null, "basicmachines/Default", 1, 1, 1, 1, 0, 0, 0, 0, 1, "", 1, "", true, true, new Recipe.DefaultRecipeBuilder());
	public static final RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> CHEMICAL_RECIPES = new RecipeMap<>(new HashSet<>(100), "gt.recipe.chemicalreactor", "Chemical Reactor", null, "basicmachines/ChemicalReactor", 0, 2, 0, 1, 0, 1, 0, 1, 1, "", 1, "", true, true, new Recipe.DefaultRecipeBuilder() {
		@Override
		protected Recipe.DefaultRecipeBuilder validate() {
			Validate.isTrue((inputs.isEmpty() && fluidInputs.isEmpty()) || (outputs.isEmpty() && fluidOutputs.isEmpty()));
			return super.validate();
		}
	}.duration(30));
	public static final RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> DISTILLATION_RECIPES = new RecipeMap<>(new HashSet<>(50), "gt.recipe.distillationtower", "Distillation Tower", null, "basicmachines/Default", 0, 0, 0, 0, 0, 0, 0, 0, 1, "", 1, "", true, true, new Recipe.DefaultRecipeBuilder()); //TODO
	public static final RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> CRAKING_RECIPES = new RecipeMap<>(new HashSet<>(50), "gt.recipe.craker", "Oil Cracker", null, "basicmachines/Default", 0, 0, 0, 0, 0, 0, 1, 0, 1, "", 1, "", true, true, new Recipe.DefaultRecipeBuilder() {  //TODO
		@Override
		public void buildAndRegister() {
			super.buildAndRegister();
		}
	});
	public static final RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> PYROLYSE_RECIPES = new RecipeMap<>(new HashSet<>(50), "gt.recipe.pyro", "Pyrolyse Oven", null, "basicmachines/Default", 1, 0, 0, 0, 0, 0, 0, 0, 1, "", 1, "", true, true, new Recipe.DefaultRecipeBuilder()); //TODO
	public static final RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> WIREMILL_RECIPES = new RecipeMap<>(new HashSet<>(50), "gt.recipe.wiremill", "Wiremill", null, "basicmachines/Wiremill", 1, 1, 1, 1, 0, 0, 0, 0, 1, "", 1, "", true, true, new Recipe.DefaultRecipeBuilder());
	public static final RecipeMap<Recipe, Recipe.IntCircuitRecipeBuilder> BENDER_RECIPES = new RecipeMap<>(new HashSet<>(400), "gt.recipe.metalbender", "Metal Bender", null, "basicmachines/Bender", 2, 2, 1, 1, 0, 0, 0, 0, 1, "", 1, "", true, true, new Recipe.IntCircuitRecipeBuilder());
	public static final RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> ALLOY_SMELTER_RECIPES = new RecipeMap<>(new HashSet<>(3000), "gt.recipe.alloysmelter", "Alloy Smelter", null, "basicmachines/AlloySmelter", 1, 2, 1, 1, 0, 0, 0, 0, 1, "", 1, "", true, true, new Recipe.DefaultRecipeBuilder() {
		@Override
		protected Recipe.DefaultRecipeBuilder validate() {
			super.validate();
			ItemStack input = inputs.get(0);
			Validate.isTrue(Materials.Graphite.contains(input)); // Why?
			Validate.isTrue((inputs.size() == 1) && (OrePrefixes.ingot.contains(input) || OrePrefixes.dust.contains(input) || OrePrefixes.gem.contains(input))); //Also why?
			return getThis();
		}
	});
	public static final RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> CANNER_RECIPES = new RecipeMap<>(new HashSet<>(300), "gt.recipe.canner", "Canning Machine", null, "basicmachines/Canner", 1, 2, 1, 2, 0, 0, 0, 0, 1, "", 1, "", true, true, new Recipe.DefaultRecipeBuilder());
	//	public static final RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> CNC_RECIPES = new RecipeMap<>(new HashSet<>(100), "gt.recipe.cncmachine", "CNC Machine", null, "basicmachines/Default", 2, 0, 0, 0, 0, 0, 1, 0,	1, "", 1, "", true,	true, new Recipe.DefaultRecipeBuilder()); //TODO
	public static final RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> LATHE_RECIPES = new RecipeMap<>(new HashSet<>(400), "gt.recipe.lathe", "Lathe", null, "basicmachines/Lathe", 1, 1, 1, 2, 0, 0, 0, 0, 1, "", 1, "", true, true, new Recipe.DefaultRecipeBuilder());
	public static final RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> CUTTER_RECIPES = new RecipeMap<>(new HashSet<>(200), "gt.recipe.cuttingsaw", "Cutting Saw", null, "basicmachines/Cutter", 1, 0, 0, 0, 0, 0, 1, 0, 1, "", 1, "", true, true, new Recipe.DefaultRecipeBuilder() { //TODO
		@Override
		public void buildAndRegister() {
			if (fluidInputs.isEmpty()) {
				recipeMap.addRecipe(this.copy().fluidInputs(Materials.Water.getFluid(Math.max(4, Math.min(1000, duration * EUt / 320)))).duration(duration * 2).validate().build());
				recipeMap.addRecipe(this.copy().fluidInputs(GT_ModHandler.getDistilledWater(Math.max(3, Math.min(750, duration * EUt / 426)))).duration(duration * 2).validate().build());
				recipeMap.addRecipe(this.copy().fluidInputs(Materials.Lubricant.getFluid(Math.max(1, Math.min(250, duration * EUt / 1280)))).duration(duration * 2).validate().build());
			} else {
				recipeMap.addRecipe(validate().build());
			}
		}
	});
	public static final RecipeMap<Recipe, Recipe.NotConsumableInputRecipeBuilder> SLICER_RECIPES = new RecipeMap<>(new HashSet<>(200), "gt.recipe.slicer", "Slicer", null, "basicmachines/Slicer", 2, 2, 1, 1, 0, 0, 0, 0, 1, "", 1, "", true, true, new Recipe.NotConsumableInputRecipeBuilder());
	public static final RecipeMap<Recipe, Recipe.NotConsumableInputRecipeBuilder> EXTRUDER_RECIPES = new RecipeMap<>(new HashSet<>(1000), "gt.recipe.extruder", "Extruder", null, "basicmachines/Extruder", 2, 2, 1, 1, 0, 0, 0, 0, 1, "", 1, "", true, true, new Recipe.NotConsumableInputRecipeBuilder());
	public static final RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> HAMMER_RECIPES = new RecipeMap<>(new HashSet<>(200), "gt.recipe.hammer", "Hammer", null, "basicmachines/Hammer", 1, 1, 1, 1, 0, 0, 0, 0, 1, "", 1, "", true, true, new Recipe.DefaultRecipeBuilder());
	public static final RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> AMPLIFIERS = new RecipeMap<>(new HashSet<>(10), "gt.recipe.uuamplifier", "UU Amplifier", null, "basicmachines/Amplifabricator", 1, 0, 0, 0, 0, 0, 0, 0, 1, "", 1, "", true, true, new Recipe.DefaultRecipeBuilder()); //TODO

	/// TODO Map<FluidStack, Int>
	public static final RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> DIESEL_FUELS = new RecipeMap<>(new HashSet<>(10), "gt.recipe.dieselgeneratorfuel", "Diesel Generator Fuel", null, "basicmachines/Default", 1, 1, 0, 4, 0, 0, 0, 0, 1, "Fuel Value: ", 1000, " EU", true, true, new Recipe.DefaultRecipeBuilder());
	public static final RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> TURBINE_FUELS = new RecipeMap<>(new HashSet<>(10), "gt.recipe.gasturbinefuel", "Gas Turbine Fuel", null, "basicmachines/Default", 0, 1, 0, 4, 0, 0, 0, 0, 1, "Fuel Value: ", 1000, " EU", true, true, new Recipe.DefaultRecipeBuilder());
	public static final RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> HOT_FUELS = new RecipeMap<>(new HashSet<>(10), "gt.recipe.thermalgeneratorfuel", "Thermal Generator Fuel", null, "basicmachines/Default", 0, 1, 0, 4, 0, 0, 0, 0, 1, "Fuel Value: ", 1000, " EU", true, false, new Recipe.DefaultRecipeBuilder());
	public static final RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> DENSE_LIQUID_FUELS = new RecipeMap<>(new HashSet<>(10), "gt.recipe.semifluidboilerfuels", "Semifluid Boiler Fuels", null, "basicmachines/Default", 0, 1, 0, 4, 0, 0, 0, 0, 1, "Fuel Value: ", 1000, " EU", true, true, new Recipe.DefaultRecipeBuilder());
	public static final RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> PLASMA_FUELS = new RecipeMap<>(new HashSet<>(10), "gt.recipe.plasmageneratorfuels", "Plasma generator Fuels", null, "basicmachines/Default", 0, 1, 0, 4, 0, 0, 0, 0, 1, "Fuel Value: ", 1000, " EU", true, true, new Recipe.DefaultRecipeBuilder());
	////

	/**
	 * Use EUt() to set EU/t produced
	 */
	public static final RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> MAGIC_FUELS = new RecipeMap<>(new HashSet<>(10), "gt.recipe.magicfuels", "Magic Fuels", null, "basicmachines/Default", 1, 1, 0, 1, 0, 0, 0, 0, 1, "Fuel Value: ", 1000, " EU", true, true, new Recipe.DefaultRecipeBuilder() {
		@Override
		protected void fill() {
			this.EUt = EUt > 0 ? -EUt : EUt;
			super.fill();
		}
	});

	public static final RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> SMALL_NAQUADAH_REACTOR_FUELS = new RecipeMap<>(new HashSet<>(10), "gt.recipe.smallnaquadahreactor", "Small Naquadah Reactor", null, "basicmachines/Default", 1, 1, 1, 1, 0, 0, 0, 0, 1, "Fuel Value: ", 1000, " EU", true, true, new Recipe.DefaultRecipeBuilder());
	public static final RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> LARGE_NAQUADAH_REACTOR_FUELS = new RecipeMap<>(new HashSet<>(10), "gt.recipe.largenaquadahreactor", "Large Naquadah Reactor", null, "basicmachines/Default", 1, 1, 1, 1, 0, 0, 0, 0, 1, "Fuel Value: ", 1000, " EU", true, true, new Recipe.DefaultRecipeBuilder());
	public static final RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> FLUID_NAQUADAH_REACTOR_FUELS = new RecipeMap<>(new HashSet<>(10), "gt.recipe.fluidnaquadahreactor", "Fluid Naquadah Reactor", null, "basicmachines/Default", 1, 1, 1, 1, 0, 0, 0, 0, 1, "Fuel Value: ", 1000, " EU", true, true, new Recipe.DefaultRecipeBuilder());

	/**
	 * HashMap of Recipes based on their Item inputs
	 */
	protected final Map<GT_ItemStack, Collection<Recipe>> recipeItemMap = new HashMap<>();
	/**
	 * HashMap of Recipes based on their Fluid inputs
	 */
	protected final Map<Fluid, Collection<Recipe>> recipeFluidMap = new HashMap<>();
	/**
	 * The List of all Recipes
	 */
	protected final Collection<Recipe> recipeList;
	/**
	 * String used as an unlocalised Name.
	 */
	protected final String unlocalizedName;

	protected final R defaultRecipe;

	// Stuff that is used to validate recipes
	protected final int minInputs, maxInputs;
	protected final int minOutputs, maxOutputs;
	protected final int minFluidInputs, maxFluidInputs;
	protected final int minFluidOutputs, maxFluidOutputs;

	protected final int amperage;

	/**
	 * String used in JEI for the Recipe Lists. If null it will use the unlocalised Name instead
	 */
	protected final String JEIName;
	/**
	 * GUI used for JEI Display. Usually the GUI of the Machine itself
	 */
	protected final String JEIGUIPath;

	protected final String JEISpecialValuePre, JEISpecialValuePost;

	protected final int JEISpecialValueMultiplier;

	protected final boolean JEIAllowed;
	protected final boolean showVoltageAmperageInJEI;

	/**
	 * Initialises a new type of Recipe Handler.
	 *
	 * @param recipeList                a List you specify as Recipe List. Usually just an ArrayList with a pre-initialised Size.
	 * @param unlocalizedName           the unlocalised Name of this Recipe Handler, used mainly for JEI.
	 * @param localName                 the displayed Name inside the JEI Recipe GUI.
	 * @param JEIGUIPath                the displayed GUI Texture, usually just a Machine GUI. Auto-Attaches ".png" if forgotten.
	 * @param JEISpecialValuePre        the String in front of the Special Value in JEI.
	 * @param JEISpecialValueMultiplier the Value the Special Value is getting Multiplied with before displaying
	 * @param JEISpecialValuePost       the String after the Special Value. Usually for a Unit or something.
	 * @param JEIAllowed                if JEI is allowed to display this Recipe Handler in general.
	 */
	public RecipeMap(Collection<Recipe> recipeList, String unlocalizedName, String localName, String JEIName, String JEIGUIPath,
					 int minInputs, int maxInputs, int minOutputs, int maxOutputs, int minFluidInputs, int maxFluidInputs, int minFluidOutputs, int maxFluidOutputs,
					 int amperage, String JEISpecialValuePre, int JEISpecialValueMultiplier, String JEISpecialValuePost,
					 boolean showVoltageAmperageInJEI, boolean JEIAllowed, R defaultRecipe) {
		RECIPE_MAPS.add(this);
		this.JEIAllowed = JEIAllowed;
		this.showVoltageAmperageInJEI = showVoltageAmperageInJEI;
		this.recipeList = recipeList;
		this.JEIName = JEIName == null ? unlocalizedName : JEIName;
		this.JEIGUIPath = GT_Values.MODID + ":textures/gui/" + (JEIGUIPath.endsWith(".png") ? JEIGUIPath : JEIGUIPath + ".png");
		this.JEISpecialValuePre = JEISpecialValuePre;
		this.JEISpecialValueMultiplier = JEISpecialValueMultiplier;
		this.JEISpecialValuePost = JEISpecialValuePost;
		this.amperage = amperage;

		defaultRecipe.setRecipeMap(this);
		this.defaultRecipe = defaultRecipe;

		this.minInputs = minInputs;
		this.minFluidInputs = minFluidInputs;
		this.minOutputs = minOutputs;
		this.minFluidOutputs = minFluidOutputs;

		this.maxInputs = maxInputs;
		this.maxFluidInputs = maxFluidInputs;
		this.maxOutputs = maxOutputs;
		this.maxFluidOutputs = maxFluidOutputs;

		GT_LanguageManager.addStringLocalization(this.unlocalizedName = unlocalizedName, localName);
	}

	protected Recipe addRecipe(Recipe recipe) {
		recipeList.add(recipe);

		for (ItemStack stack : recipe.getInputs()) {
			recipeItemMap.computeIfAbsent(new GT_ItemStack(stack), k -> new HashSet<>(1)).add(recipe);
		}

		for (FluidStack fluid : recipe.getFluidInputs()) {
			recipeFluidMap.computeIfAbsent(fluid.getFluid(), k -> new HashSet<>(1)).add(recipe);
		}

		return recipe;
	}

	/**
	 * @return if this Item is a valid Input for any for the Recipes
	 */
	public boolean containsInput(ItemStack stack) {
		return stack != null && (recipeItemMap.containsKey(new GT_ItemStack(stack)) || recipeItemMap.containsKey(new GT_ItemStack(GT_Utility.copyMetaData(W, stack))));
	}

	/**
	 * @return if this Fluid is a valid Input for any for the Recipes
	 */
	public boolean containsInput(FluidStack fluid) {
		return fluid != null && containsInput(fluid.getFluid());
	}

	/**
	 * @return if this Fluid is a valid Input for any for the Recipes
	 */
	public boolean containsInput(Fluid fluid) {
		return fluid != null && recipeFluidMap.containsKey(fluid);
	}

	public T findRecipe(IHasWorldObjectAndCoords tileEntity, boolean notUnificated, long voltage, FluidStack[] fluids, ItemStack[] inputs) {
		return findRecipe(tileEntity, null, notUnificated, voltage, fluids, null, inputs);
	}

	public T findRecipe(IHasWorldObjectAndCoords tileEntity, Recipe recipe, boolean notUnificated, long voltage, FluidStack[] fluids, ItemStack[] inputs) {
		return findRecipe(tileEntity, recipe, notUnificated, voltage, fluids, null, inputs);
	}

	/**
	 * Finds a Recipe matching the Fluid and ItemStack Inputs.
	 *
	 * @param tileEntity    an Object representing the current coordinates of the executing Block/Entity/Whatever. This may be null, especially during Startup.
	 * @param recipe        in case this is != null it will try to use this Recipe first when looking things up.
	 * @param notUnificated if this is true the Recipe searcher will unificate the ItemStack Inputs
	 * @param voltage       Voltage of the Machine or Long.MAX_VALUE if it has no Voltage
	 * @param fluidInputs   the Fluid Inputs
	 * @param specialSlot   the content of the Special Slot, the regular Manager doesn't do anything with this, but some custom ones do.
	 * @param inputs        the Item Inputs
	 * @return the Recipe it has found or null for no matching Recipe
	 */
	public T findRecipe(@Nullable IHasWorldObjectAndCoords tileEntity, @Nullable Recipe recipe, boolean notUnificated, long voltage, FluidStack[] fluidInputs, ItemStack specialSlot, ItemStack[] inputs) {
		// No Recipes? Well, nothing to be found then.
		if (recipeList.isEmpty()) return null;

		// Some Recipe Classes require a certain amount of Inputs of certain kinds. Like "at least 1 Fluid + 1 Stack" or "at least 2 Stacks" before they start searching for Recipes.
		// This improves Performance massively, especially if people leave things like Circuits, Molds or Shapes in their Machines to select Sub Recipes.
		if (GregTech_API.sPostloadFinished) {
			if (minFluidInputs > 0) {
				if (fluidInputs == null) {
					return null;
				}
				int amount = 0;
				for (FluidStack fluid : fluidInputs) {
					if (fluid != null) {
						amount++;
					}
				}
				if (amount < minFluidInputs) {
					return null;
				}
			}

			if (minInputs > 0) {
				if (inputs == null) {
					return null;
				}
				int amount = 0;
				for (ItemStack stack : inputs) {
					if (stack != null) {
						amount++;
					}
				}
				if (amount < minInputs) {
					return null;
				}
			}
		}

		// Unification happens here in case the Input isn't already unificated.
		if (notUnificated) {
			inputs = GT_OreDictUnificator.getStackArray(true, (Object[]) inputs);
		}

		// Check the Recipe which has been used last time in order to not have to search for it again, if possible.
		if (recipe != null) {
			if (recipe.canBeBuffered() && recipe.isRecipeInputEqual(false, true, fluidInputs, inputs)) {
				return voltage * amperage >= recipe.getEUt() ? recipe : null;
			}
		}

		// Now look for the Recipes inside the Item HashMaps, but only when the Recipes usually have Items.
		if (maxInputs > 0 && inputs != null) {
			for (ItemStack stack : inputs) {
				if (stack != null) {
					Collection<Recipe> recipes = recipeItemMap.get(new GT_ItemStack(stack));
					if (recipes != null) {
						for (Recipe tmpRecipe : recipes) {
							if (tmpRecipe.isRecipeInputEqual(false, true, fluidInputs, inputs)) {
								return voltage * amperage >= tmpRecipe.getEUt() ? tmpRecipe : null;
							}
						}
					}
					recipes = recipeItemMap.get(new GT_ItemStack(GT_Utility.copyMetaData(W, stack)));
					if (recipes != null) {
						for (Recipe tmpRecipe : recipes) {
							if (tmpRecipe.isRecipeInputEqual(false, true, fluidInputs, inputs)) {
								return voltage * amperage >= tmpRecipe.getEUt() ? tmpRecipe : null;
							}
						}
					}
				}
			}
		}

		// If the minimal Amount of Items for the Recipe is 0, then it could be a Fluid-Only Recipe, so check that Map too.
		if (maxInputs == 0 && fluidInputs != null) {
			for (FluidStack fluid : fluidInputs) {
				if (fluid != null) {
					Collection<Recipe> recipes = recipeFluidMap.get(fluid.getFluid());
					if (recipes != null) {
						for (Recipe tmpRecipe : recipes) {
							if (tmpRecipe.isRecipeInputEqual(false, true, fluidInputs, inputs)) {
								return voltage * amperage >= tmpRecipe.getEUt() ? tmpRecipe : null;
							}
						}
					}
				}
			}
		}

		// And nothing has been found.
		return null;
	}

	public R recipeBuilder() {
		return defaultRecipe.copy();
	}

	///////////////////
	//    Getters    //
	///////////////////

	public String getUnlocalizedName() {
		return unlocalizedName;
	}

	public int getMinInputs() {
		return minInputs;
	}

	public int getMaxInputs() {
		return maxInputs;
	}

	public int getMinOutputs() {
		return minOutputs;
	}

	public int getMaxOutputs() {
		return maxOutputs;
	}

	public int getMinFluidInputs() {
		return minFluidInputs;
	}

	public int getMaxFluidInputs() {
		return maxFluidInputs;
	}

	public int getMinFluidOutputs() {
		return minFluidOutputs;
	}

	public int getMaxFluidOutputs() {
		return maxFluidOutputs;
	}

	public int getAmperage() {
		return amperage;
	}

	public String getJEIName() {
		return JEIName;
	}

	public String getJEIGUIPath() {
		return JEIGUIPath;
	}

	public String getJEISpecialValuePre() {
		return JEISpecialValuePre;
	}

	public String getJEISpecialValuePost() {
		return JEISpecialValuePost;
	}

	public int getJEISpecialValueMultiplier() {
		return JEISpecialValueMultiplier;
	}

	public boolean isJEIAllowed() {
		return JEIAllowed;
	}

	public boolean doShowVoltageAmperageInJEI() {
		return showVoltageAmperageInJEI;
	}

	public static class FakeRecipeMap<T extends Recipe, R extends Recipe.RecipeBuilder<T, R>> extends RecipeMap<T, R> {

		public FakeRecipeMap(Collection<Recipe> recipeList, String unlocalizedName, String localName, String JEIName, String JEIGUIPath, int minInputs, int maxInputs, int minOutputs, int maxOutputs, int minFluidInputs, int maxFluidInputs, int minFluidOutputs, int maxFluidOutputs, int amperage, String JEISpecialValuePre, int JEISpecialValueMultiplier, String JEISpecialValuePost, boolean showVoltageAmperageInJEI, boolean JEIAllowed, Recipe.RecipeBuilder defaultRecipe) {
			super(recipeList, unlocalizedName, localName, JEIName, JEIGUIPath, minInputs, maxInputs, minOutputs, maxOutputs, minFluidInputs, maxFluidInputs, minFluidOutputs, maxFluidOutputs, amperage, JEISpecialValuePre, JEISpecialValueMultiplier, JEISpecialValuePost, showVoltageAmperageInJEI, JEIAllowed, defaultRecipe);
		}

		@Override
		public boolean containsInput(ItemStack stack) {
			throw new UnsupportedOperationException("This should not get called on fake recipe map");
		}

		@Override
		public boolean containsInput(FluidStack fluid) {
			throw new UnsupportedOperationException("This should not get called on fake recipe map");
		}

		@Override
		public boolean containsInput(Fluid fluid) {
			throw new UnsupportedOperationException("This should not get called on fake recipe map");
		}

		@Override
		public T findRecipe(IHasWorldObjectAndCoords tileEntity, Recipe recipe, boolean notUnificated, long voltage, FluidStack[] fluidInputs, ItemStack specialSlot, ItemStack[] inputs) {
			throw new UnsupportedOperationException("This should not get called on fake recipe map");
		}
	}

	/**
	 * Abstract Class for general Recipe Handling of non GT Recipes
	 */
	public static abstract class GT_Recipe_Map_NonGTRecipes<T extends Recipe, R extends Recipe.RecipeBuilder<T, R>> extends RecipeMap<T, R> {

		public GT_Recipe_Map_NonGTRecipes(Collection<Recipe> recipeList, String unlocalizedName, String localName, String JEIName, String JEIGUIPath, int minInputs, int maxInputs, int minOutputs, int maxOutputs, int minFluidInputs, int maxFluidInputs, int minFluidOutputs, int maxFluidOutputs, int amperage, String JEISpecialValuePre, int JEISpecialValueMultiplier, String JEISpecialValuePost, boolean showVoltageAmperageInJEI, boolean JEIAllowed, Recipe.RecipeBuilder defaultRecipe) {
			super(recipeList, unlocalizedName, localName, JEIName, JEIGUIPath, minInputs, maxInputs, minOutputs, maxOutputs, minFluidInputs, maxFluidInputs, minFluidOutputs, maxFluidOutputs, amperage, JEISpecialValuePre, JEISpecialValueMultiplier, JEISpecialValuePost, showVoltageAmperageInJEI, JEIAllowed, defaultRecipe);
		}

		@Override
		public boolean containsInput(ItemStack stack) {
			return false;
		}

		@Override
		public boolean containsInput(FluidStack fluid) {
			return false;
		}

		@Override
		public boolean containsInput(Fluid fluid) {
			return false;
		}

		@Override
		protected Recipe addRecipe(Recipe recipe) {
			return null;
		}
	}

	/**
	 * Special Class for Furnace Recipe handling.
	 */
	public static class GT_Recipe_Map_Furnace extends GT_Recipe_Map_NonGTRecipes<Recipe, Recipe.DefaultRecipeBuilder> {

		public GT_Recipe_Map_Furnace(Collection<Recipe> recipeList, String unlocalizedName, String localName, String JEIName, String JEIGUIPath, int minInputs, int maxInputs, int minOutputs, int maxOutputs, int minFluidInputs, int maxFluidInputs, int minFluidOutputs, int maxFluidOutputs, int amperage, String JEISpecialValuePre, int JEISpecialValueMultiplier, String JEISpecialValuePost, boolean showVoltageAmperageInJEI, boolean JEIAllowed, Recipe.RecipeBuilder defaultRecipe) {
			super(recipeList, unlocalizedName, localName, JEIName, JEIGUIPath, minInputs, maxInputs, minOutputs, maxOutputs, minFluidInputs, maxFluidInputs, minFluidOutputs, maxFluidOutputs, amperage, JEISpecialValuePre, JEISpecialValueMultiplier, JEISpecialValuePost, showVoltageAmperageInJEI, JEIAllowed, defaultRecipe);
		}

		@Override
		public Recipe findRecipe(IHasWorldObjectAndCoords tileEntity, Recipe recipe, boolean notUnificated, long voltage, FluidStack[] fluidInputs, ItemStack specialSlot, ItemStack[] inputs) {
			if (inputs == null || inputs.length <= 0 || inputs[0] == null) return null;
			if (recipe != null && recipe.isRecipeInputEqual(false, true, fluidInputs, inputs)) return recipe;
			ItemStack output = GT_ModHandler.getSmeltingOutput(inputs[0], false, null);
			return output == null ? null : new Recipe(false, new ItemStack[]{GT_Utility.copyAmount(1, inputs[0])}, new ItemStack[]{output}, null, null, null, null, 128, 4, 0);
		}

		@Override
		public boolean containsInput(ItemStack stack) {
			return GT_ModHandler.getSmeltingOutput(stack, false, null) != null;
		}
	}

	/**
	 * Special Class for Microwave Recipe handling.
	 */
	public static class GT_Recipe_Map_Microwave extends GT_Recipe_Map_NonGTRecipes<Recipe, Recipe.DefaultRecipeBuilder> {

		public GT_Recipe_Map_Microwave(Collection<Recipe> recipeList, String unlocalizedName, String localName, String JEIName, String JEIGUIPath, int minInputs, int maxInputs, int minOutputs, int maxOutputs, int minFluidInputs, int maxFluidInputs, int minFluidOutputs, int maxFluidOutputs, int amperage, String JEISpecialValuePre, int JEISpecialValueMultiplier, String JEISpecialValuePost, boolean showVoltageAmperageInJEI, boolean JEIAllowed, Recipe.RecipeBuilder defaultRecipe) {
			super(recipeList, unlocalizedName, localName, JEIName, JEIGUIPath, minInputs, maxInputs, minOutputs, maxOutputs, minFluidInputs, maxFluidInputs, minFluidOutputs, maxFluidOutputs, amperage, JEISpecialValuePre, JEISpecialValueMultiplier, JEISpecialValuePost, showVoltageAmperageInJEI, JEIAllowed, defaultRecipe);
		}

		@Override
		public Recipe findRecipe(IHasWorldObjectAndCoords tileEntity, Recipe recipe, boolean notUnificated, long voltage, FluidStack[] fluidInputs, ItemStack specialSlot, ItemStack[] inputs) {
			if (inputs == null || inputs.length <= 0 || inputs[0] == null) return null;
			if (recipe != null && recipe.isRecipeInputEqual(false, true, fluidInputs, inputs)) return recipe;
			ItemStack output = GT_ModHandler.getSmeltingOutput(inputs[0], false, null);

			if (GT_Utility.areStacksEqual(inputs[0], new ItemStack(Items.BOOK, 1, W))) {
				return new Recipe(false, new ItemStack[]{GT_Utility.copyAmount(1, inputs[0])}, new ItemStack[]{GT_Utility.getWrittenBook("Manual_Microwave", ItemList.Book_Written_03.get(1))}, null, null, null, null, 32, 4, 0);
			}

			// Check Container Item of Input since it is around the Input, then the Input itself, then Container Item of Output and last check the Output itself
			for (ItemStack stack : new ItemStack[]{GT_Utility.getContainerItem(inputs[0], true), inputs[0], GT_Utility.getContainerItem(output, true), output})
				if (stack != null) {
					if (GT_Utility.areStacksEqual(stack, new ItemStack(Blocks.NETHERRACK, 1, W), true)
							|| GT_Utility.areStacksEqual(stack, new ItemStack(Blocks.TNT, 1, W), true)
							|| GT_Utility.areStacksEqual(stack, new ItemStack(Items.EGG, 1, W), true)
							|| GT_Utility.areStacksEqual(stack, new ItemStack(Items.FIREWORK_CHARGE, 1, W), true)
							|| GT_Utility.areStacksEqual(stack, new ItemStack(Items.FIREWORKS, 1, W), true)
							|| GT_Utility.areStacksEqual(stack, new ItemStack(Items.FIRE_CHARGE, 1, W), true)
							) {
						if (tileEntity instanceof IGregTechTileEntity)
							((IGregTechTileEntity) tileEntity).doExplosion(voltage * 4);
						return null;
					}
					ItemData data = GT_OreDictUnificator.getItemData(stack);


					if (data != null) {
						if (data.mMaterial != null && data.mMaterial.mMaterial != null) {
							if (data.mMaterial.mMaterial.contains(SubTag.METAL) || data.mMaterial.mMaterial.contains(SubTag.EXPLOSIVE)) {
								if (tileEntity instanceof IGregTechTileEntity)
									((IGregTechTileEntity) tileEntity).doExplosion(voltage * 4);
								return null;
							}
							if (data.mMaterial.mMaterial.contains(SubTag.FLAMMABLE)) {
								if (tileEntity instanceof IGregTechTileEntity)
									((IGregTechTileEntity) tileEntity).setOnFire();
								return null;
							}
						}
						for (MaterialStack material : data.mByProducts)
							if (material != null) {
								if (material.mMaterial.contains(SubTag.METAL) || material.mMaterial.contains(SubTag.EXPLOSIVE)) {
									if (tileEntity instanceof IGregTechTileEntity)
										((IGregTechTileEntity) tileEntity).doExplosion(voltage * 4);
									return null;
								}
								if (material.mMaterial.contains(SubTag.FLAMMABLE)) {
									if (tileEntity instanceof IGregTechTileEntity)
										((IGregTechTileEntity) tileEntity).setOnFire();
									return null;
								}
							}
					}
					if (TileEntityFurnace.getItemBurnTime(stack) > 0) {
						if (tileEntity instanceof IGregTechTileEntity) ((IGregTechTileEntity) tileEntity).setOnFire();
						return null;
					}

				}

			return output == null ? null : new Recipe(false, new ItemStack[]{GT_Utility.copyAmount(1, inputs[0])}, new ItemStack[]{output}, null, null, null, null, 32, 4, 0);
		}

		@Override
		public boolean containsInput(ItemStack stack) {
			return GT_ModHandler.getSmeltingOutput(stack, false, null) != null;
		}
	}

	/**
	 * Special Class for Unboxinator handling.
	 */
	public static class GT_Recipe_Map_Unboxinator extends RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> {

		public GT_Recipe_Map_Unboxinator(Collection<Recipe> recipeList, String unlocalizedName, String localName, String JEIName, String JEIGUIPath, int minInputs, int maxInputs, int minOutputs, int maxOutputs, int minFluidInputs, int maxFluidInputs, int minFluidOutputs, int maxFluidOutputs, int amperage, String JEISpecialValuePre, int JEISpecialValueMultiplier, String JEISpecialValuePost, boolean showVoltageAmperageInJEI, boolean JEIAllowed, Recipe.RecipeBuilder defaultRecipe) {
			super(recipeList, unlocalizedName, localName, JEIName, JEIGUIPath, minInputs, maxInputs, minOutputs, maxOutputs, minFluidInputs, maxFluidInputs, minFluidOutputs, maxFluidOutputs, amperage, JEISpecialValuePre, JEISpecialValueMultiplier, JEISpecialValuePost, showVoltageAmperageInJEI, JEIAllowed, defaultRecipe);
		}

		@Override
		public Recipe findRecipe(IHasWorldObjectAndCoords tileEntity, Recipe recipe, boolean notUnificated, long voltage, FluidStack[] fluidInputs, ItemStack specialSlot, ItemStack[] inputs) {
			if (inputs == null || inputs.length <= 0 || !ItemList.IC2_Scrapbox.isStackEqual(inputs[0], false, true))
				return super.findRecipe(tileEntity, recipe, notUnificated, voltage, fluidInputs, specialSlot, inputs);
			ItemStack tOutput = GT_ModHandler.getRandomScrapboxDrop();
			if (tOutput == null)
				return super.findRecipe(tileEntity, recipe, notUnificated, voltage, fluidInputs, specialSlot, inputs);
			Recipe rRecipe = new Recipe(false, new ItemStack[]{ItemList.IC2_Scrapbox.get(1)}, new ItemStack[]{tOutput}, null, null, null, null, 16, 1, 0);
			// It is not allowed to be buffered due to the random Output
			rRecipe.canBeBuffered = false;
			// Due to its randomness it is not good if there are Items in the Output Slot, because those Items could manipulate the outcome.
			rRecipe.needsEmptyOutput = true;
			return rRecipe;
		}

		@Override
		public boolean containsInput(ItemStack stack) {
			return ItemList.IC2_Scrapbox.isStackEqual(stack, false, true) || super.containsInput(stack);
		}
	}

	/**
	 * Special Class for Fluid Canner handling.
	 */
	public static class GT_Recipe_Map_FluidCanner extends RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> {

		public GT_Recipe_Map_FluidCanner(Collection<Recipe> recipeList, String unlocalizedName, String localName, String JEIName, String JEIGUIPath, int minInputs, int maxInputs, int minOutputs, int maxOutputs, int minFluidInputs, int maxFluidInputs, int minFluidOutputs, int maxFluidOutputs, int amperage, String JEISpecialValuePre, int JEISpecialValueMultiplier, String JEISpecialValuePost, boolean showVoltageAmperageInJEI, boolean JEIAllowed, Recipe.RecipeBuilder defaultRecipe) {
			super(recipeList, unlocalizedName, localName, JEIName, JEIGUIPath, minInputs, maxInputs, minOutputs, maxOutputs, minFluidInputs, maxFluidInputs, minFluidOutputs, maxFluidOutputs, amperage, JEISpecialValuePre, JEISpecialValueMultiplier, JEISpecialValuePost, showVoltageAmperageInJEI, JEIAllowed, defaultRecipe);
		}

		@Override
		public Recipe findRecipe(IHasWorldObjectAndCoords tileEntity, Recipe recipe, boolean notUnificated, long voltage, FluidStack[] fluidInputs, ItemStack specialSlot, ItemStack[] inputs) {
			Recipe tmpRecipe = super.findRecipe(tileEntity, recipe, notUnificated, voltage, fluidInputs, specialSlot, inputs);
			if (inputs == null || inputs.length <= 0 || inputs[0] == null || tmpRecipe != null || !GregTech_API.sPostloadFinished)
				return tmpRecipe;
			if (fluidInputs != null && fluidInputs.length > 0 && fluidInputs[0] != null) {
				ItemStack output = GT_Utility.fillFluidContainer(fluidInputs[0], inputs[0], false, true);
				FluidStack fluid = GT_Utility.getFluidForFilledItem(output, true);
				if (fluid != null)
					tmpRecipe = new Recipe(false, new ItemStack[]{GT_Utility.copyAmount(1, inputs[0])}, new ItemStack[]{output}, null, null, new FluidStack[]{fluid}, null, Math.max(fluid.amount / 64, 16), 1, 0);
			}
			if (tmpRecipe == null) {
				FluidStack fluid = GT_Utility.getFluidForFilledItem(inputs[0], true);
				if (fluid != null)
					tmpRecipe = new Recipe(false, new ItemStack[]{GT_Utility.copyAmount(1, inputs[0])}, new ItemStack[]{GT_Utility.getContainerItem(inputs[0], true)}, null, null, null, new FluidStack[]{fluid}, Math.max(fluid.amount / 64, 16), 1, 0);
			}
			if (tmpRecipe != null) tmpRecipe.canBeBuffered = false;
			return tmpRecipe;
		}

		@Override
		public boolean containsInput(ItemStack stack) {
			return stack != null && (super.containsInput(stack) || (stack.getItem() instanceof IFluidContainerItem && ((IFluidContainerItem) stack.getItem()).getCapacity(stack) > 0));
		}

		@Override
		public boolean containsInput(FluidStack fluid) {
			return true;
		}

		@Override
		public boolean containsInput(Fluid fluid) {
			return true;
		}
	}

	/**
	 * Special Class for Recycler Recipe handling.
	 */
	public static class GT_Recipe_Map_Recycler extends GT_Recipe_Map_NonGTRecipes<Recipe, Recipe.DefaultRecipeBuilder> {

		public GT_Recipe_Map_Recycler(Collection<Recipe> recipeList, String unlocalizedName, String localName, String JEIName, String JEIGUIPath, int minInputs, int maxInputs, int minOutputs, int maxOutputs, int minFluidInputs, int maxFluidInputs, int minFluidOutputs, int maxFluidOutputs, int amperage, String JEISpecialValuePre, int JEISpecialValueMultiplier, String JEISpecialValuePost, boolean showVoltageAmperageInJEI, boolean JEIAllowed, Recipe.RecipeBuilder defaultRecipe) {
			super(recipeList, unlocalizedName, localName, JEIName, JEIGUIPath, minInputs, maxInputs, minOutputs, maxOutputs, minFluidInputs, maxFluidInputs, minFluidOutputs, maxFluidOutputs, amperage, JEISpecialValuePre, JEISpecialValueMultiplier, JEISpecialValuePost, showVoltageAmperageInJEI, JEIAllowed, defaultRecipe);
		}

		@Override
		public Recipe findRecipe(IHasWorldObjectAndCoords tileEntity, Recipe recipe, boolean notUnificated, long voltage, FluidStack[] fluidInputs, ItemStack specialSlot, ItemStack[] inputs) {
			if (inputs == null || inputs.length <= 0 || inputs[0] == null) return null;
			if (recipe != null && recipe.isRecipeInputEqual(false, true, fluidInputs, inputs)) return recipe;
			return new Recipe(false, new ItemStack[]{GT_Utility.copyAmount(1, inputs[0])}, GT_ModHandler.getRecyclerOutput(GT_Utility.copyAmount(64, inputs[0]), 0) == null ? null : new ItemStack[]{ItemList.IC2_Scrap.get(1)}, null, new int[]{1250}, null, null, 45, 1, 0);
		}

		@Override
		public boolean containsInput(ItemStack stack) {
			return GT_ModHandler.getRecyclerOutput(GT_Utility.copyAmount(64, stack), 0) != null;
		}
	}

	/**
	 * Special Class for Compressor Recipe handling.
	 */
	public static class GT_Recipe_Map_Compressor extends GT_Recipe_Map_NonGTRecipes<Recipe, Recipe.DefaultRecipeBuilder> {

		public GT_Recipe_Map_Compressor(Collection<Recipe> recipeList, String unlocalizedName, String localName, String JEIName, String JEIGUIPath, int minInputs, int maxInputs, int minOutputs, int maxOutputs, int minFluidInputs, int maxFluidInputs, int minFluidOutputs, int maxFluidOutputs, int amperage, String JEISpecialValuePre, int JEISpecialValueMultiplier, String JEISpecialValuePost, boolean showVoltageAmperageInJEI, boolean JEIAllowed, Recipe.RecipeBuilder defaultRecipe) {
			super(recipeList, unlocalizedName, localName, JEIName, JEIGUIPath, minInputs, maxInputs, minOutputs, maxOutputs, minFluidInputs, maxFluidInputs, minFluidOutputs, maxFluidOutputs, amperage, JEISpecialValuePre, JEISpecialValueMultiplier, JEISpecialValuePost, showVoltageAmperageInJEI, JEIAllowed, defaultRecipe);
		}

		@Override
		public Recipe findRecipe(IHasWorldObjectAndCoords tileEntity, Recipe recipe, boolean notUnificated, long voltage, FluidStack[] fluidInputs, ItemStack specialSlot, ItemStack[] inputs) {
			if (inputs == null || inputs.length <= 0 || inputs[0] == null) return null;
			if (recipe != null && recipe.isRecipeInputEqual(false, true, fluidInputs, inputs)) return recipe;
			ItemStack comparedInput = GT_Utility.copy(inputs[0]);
			ItemStack[] outputItems = GT_ModHandler.getMachineOutput(comparedInput, ic2.api.recipe.Recipes.compressor.getRecipes(), true, new NBTTagCompound(), null, null, null);
			return GT_Utility.arrayContainsNonNull(outputItems) ? new Recipe(false, new ItemStack[]{GT_Utility.copyAmount(inputs[0].stackSize - comparedInput.stackSize, inputs[0])}, outputItems, null, null, null, null, 400, 2, 0) : null;
		}

		@Override
		public boolean containsInput(ItemStack stack) {
			return GT_Utility.arrayContainsNonNull(GT_ModHandler.getMachineOutput(GT_Utility.copyAmount(64, stack), ic2.api.recipe.Recipes.compressor.getRecipes(), false, new NBTTagCompound(), null, null, null));
		}
	}

	/**
	 * Special Class for Extractor Recipe handling.
	 */
	public static class GT_Recipe_Map_Extractor extends GT_Recipe_Map_NonGTRecipes<Recipe, Recipe.DefaultRecipeBuilder> {

		public GT_Recipe_Map_Extractor(Collection<Recipe> recipeList, String unlocalizedName, String localName, String JEIName, String JEIGUIPath, int minInputs, int maxInputs, int minOutputs, int maxOutputs, int minFluidInputs, int maxFluidInputs, int minFluidOutputs, int maxFluidOutputs, int amperage, String JEISpecialValuePre, int JEISpecialValueMultiplier, String JEISpecialValuePost, boolean showVoltageAmperageInJEI, boolean JEIAllowed, Recipe.RecipeBuilder defaultRecipe) {
			super(recipeList, unlocalizedName, localName, JEIName, JEIGUIPath, minInputs, maxInputs, minOutputs, maxOutputs, minFluidInputs, maxFluidInputs, minFluidOutputs, maxFluidOutputs, amperage, JEISpecialValuePre, JEISpecialValueMultiplier, JEISpecialValuePost, showVoltageAmperageInJEI, JEIAllowed, defaultRecipe);
		}

		@Override
		public Recipe findRecipe(IHasWorldObjectAndCoords tileEntity, Recipe recipe, boolean notUnificated, long voltage, FluidStack[] fluidInputs, ItemStack specialSlot, ItemStack[] inputs) {
			if (inputs == null || inputs.length <= 0 || inputs[0] == null) return null;
			if (recipe != null && recipe.isRecipeInputEqual(false, true, fluidInputs, inputs)) return recipe;
			ItemStack tComparedInput = GT_Utility.copy(inputs[0]);
			ItemStack[] tOutputItems = GT_ModHandler.getMachineOutput(tComparedInput, ic2.api.recipe.Recipes.extractor.getRecipes(), true, new NBTTagCompound(), null, null, null);
			return GT_Utility.arrayContainsNonNull(tOutputItems) ? new Recipe(false, new ItemStack[]{GT_Utility.copyAmount(inputs[0].stackSize - tComparedInput.stackSize, inputs[0])}, tOutputItems, null, null, null, null, 400, 2, 0) : null;
		}

		@Override
		public boolean containsInput(ItemStack stack) {
			return GT_Utility.arrayContainsNonNull(GT_ModHandler.getMachineOutput(GT_Utility.copyAmount(64, stack), ic2.api.recipe.Recipes.extractor.getRecipes(), false, new NBTTagCompound(), null, null, null));
		}
	}

	/**
	 * Special Class for Thermal Centrifuge Recipe handling.
	 */
	public static class GT_Recipe_Map_ThermalCentrifuge extends GT_Recipe_Map_NonGTRecipes<Recipe, Recipe.DefaultRecipeBuilder> {

		public GT_Recipe_Map_ThermalCentrifuge(Collection<Recipe> recipeList, String unlocalizedName, String localName, String JEIName, String JEIGUIPath, int minInputs, int maxInputs, int minOutputs, int maxOutputs, int minFluidInputs, int maxFluidInputs, int minFluidOutputs, int maxFluidOutputs, int amperage, String JEISpecialValuePre, int JEISpecialValueMultiplier, String JEISpecialValuePost, boolean showVoltageAmperageInJEI, boolean JEIAllowed, Recipe.RecipeBuilder defaultRecipe) {
			super(recipeList, unlocalizedName, localName, JEIName, JEIGUIPath, minInputs, maxInputs, minOutputs, maxOutputs, minFluidInputs, maxFluidInputs, minFluidOutputs, maxFluidOutputs, amperage, JEISpecialValuePre, JEISpecialValueMultiplier, JEISpecialValuePost, showVoltageAmperageInJEI, JEIAllowed, defaultRecipe);
		}

		@Override
		public Recipe findRecipe(IHasWorldObjectAndCoords tileEntity, Recipe recipe, boolean notUnificated, long voltage, FluidStack[] fluidInputs, ItemStack specialSlot, ItemStack[] inputs) {
			if (inputs == null || inputs.length <= 0 || inputs[0] == null) return null;
			if (recipe != null && recipe.isRecipeInputEqual(false, true, fluidInputs, inputs)) return recipe;
			ItemStack tComparedInput = GT_Utility.copy(inputs[0]);
			ItemStack[] tOutputItems = GT_ModHandler.getMachineOutput(tComparedInput, ic2.api.recipe.Recipes.centrifuge.getRecipes(), true, new NBTTagCompound(), null, null, null);
			return GT_Utility.arrayContainsNonNull(tOutputItems) ? new Recipe(false, new ItemStack[]{GT_Utility.copyAmount(inputs[0].stackSize - tComparedInput.stackSize, inputs[0])}, tOutputItems, null, null, null, null, 400, 48, 0) : null;
		}

		@Override
		public boolean containsInput(ItemStack stack) {
			return GT_Utility.arrayContainsNonNull(GT_ModHandler.getMachineOutput(GT_Utility.copyAmount(64, stack), ic2.api.recipe.Recipes.centrifuge.getRecipes(), false, new NBTTagCompound(), null, null, null));
		}
	}

	/**
	 * Special Class for Ore Washer Recipe handling.
	 */
	public static class GT_Recipe_Map_OreWasher extends GT_Recipe_Map_NonGTRecipes<Recipe, Recipe.DefaultRecipeBuilder> {

		public GT_Recipe_Map_OreWasher(Collection<Recipe> recipeList, String unlocalizedName, String localName, String JEIName, String JEIGUIPath, int minInputs, int maxInputs, int minOutputs, int maxOutputs, int minFluidInputs, int maxFluidInputs, int minFluidOutputs, int maxFluidOutputs, int amperage, String JEISpecialValuePre, int JEISpecialValueMultiplier, String JEISpecialValuePost, boolean showVoltageAmperageInJEI, boolean JEIAllowed, Recipe.RecipeBuilder defaultRecipe) {
			super(recipeList, unlocalizedName, localName, JEIName, JEIGUIPath, minInputs, maxInputs, minOutputs, maxOutputs, minFluidInputs, maxFluidInputs, minFluidOutputs, maxFluidOutputs, amperage, JEISpecialValuePre, JEISpecialValueMultiplier, JEISpecialValuePost, showVoltageAmperageInJEI, JEIAllowed, defaultRecipe);
		}

		@Override
		public Recipe findRecipe(IHasWorldObjectAndCoords tileEntity, Recipe recipe, boolean notUnificated, long voltage, FluidStack[] fluidInputs, ItemStack specialSlot, ItemStack[] inputs) {
			if (inputs == null || inputs.length <= 0 || inputs[0] == null || fluidInputs == null || fluidInputs.length < 1 || !GT_ModHandler.isWater(fluidInputs[0]))
				return null;
			if (recipe != null && recipe.isRecipeInputEqual(false, true, fluidInputs, inputs)) return recipe;
			ItemStack tComparedInput = GT_Utility.copy(inputs[0]);
			NBTTagCompound aRecipeMetaData = new NBTTagCompound();
			ItemStack[] tOutputItems = GT_ModHandler.getMachineOutput(tComparedInput, ic2.api.recipe.Recipes.oreWashing.getRecipes(), true, aRecipeMetaData, null, null, null);
			return GT_Utility.arrayContainsNonNull(tOutputItems) ? new Recipe(false, new ItemStack[]{GT_Utility.copyAmount(inputs[0].stackSize - tComparedInput.stackSize, inputs[0])}, tOutputItems, null, null, new FluidStack[]{new FluidStack(fluidInputs[0].getFluid(), ((NBTTagCompound) aRecipeMetaData.getTag("return")).getInteger("amount"))}, null, 400, 16, 0) : null;
		}

		@Override
		public boolean containsInput(ItemStack stack) {
			return GT_Utility.arrayContainsNonNull(GT_ModHandler.getMachineOutput(GT_Utility.copyAmount(64, stack), ic2.api.recipe.Recipes.oreWashing.getRecipes(), false, new NBTTagCompound(), null, null, null));
		}

		@Override
		public boolean containsInput(FluidStack fluid) {
			return GT_ModHandler.isWater(fluid);
		}

		@Override
		public boolean containsInput(Fluid fluid) {
			return GT_ModHandler.isWater(new FluidStack(fluid, 0));
		}
	}

	/**
	 * Special Class for Macerator/RockCrusher Recipe handling.
	 */
	public static class GT_Recipe_Map_Macerator extends RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> {

		public GT_Recipe_Map_Macerator(Collection<Recipe> recipeList, String unlocalizedName, String localName, String JEIName, String JEIGUIPath, int minInputs, int maxInputs, int minOutputs, int maxOutputs, int minFluidInputs, int maxFluidInputs, int minFluidOutputs, int maxFluidOutputs, int amperage, String JEISpecialValuePre, int JEISpecialValueMultiplier, String JEISpecialValuePost, boolean showVoltageAmperageInJEI, boolean JEIAllowed, Recipe.RecipeBuilder defaultRecipe) {
			super(recipeList, unlocalizedName, localName, JEIName, JEIGUIPath, minInputs, maxInputs, minOutputs, maxOutputs, minFluidInputs, maxFluidInputs, minFluidOutputs, maxFluidOutputs, amperage, JEISpecialValuePre, JEISpecialValueMultiplier, JEISpecialValuePost, showVoltageAmperageInJEI, JEIAllowed, defaultRecipe);
		}

		@Override
		public Recipe findRecipe(IHasWorldObjectAndCoords tileEntity, Recipe recipe, boolean notUnificated, long voltage, FluidStack[] fluidInputs, ItemStack specialSlot, ItemStack[] inputs) {
			if (inputs == null || inputs.length <= 0 || inputs[0] == null || !GregTech_API.sPostloadFinished)
				return super.findRecipe(tileEntity, recipe, notUnificated, voltage, fluidInputs, specialSlot, inputs);
			recipe = super.findRecipe(tileEntity, recipe, notUnificated, voltage, fluidInputs, specialSlot, inputs);
			if (recipe != null) return recipe;

			try {
				//TODO: railcraft on 1.9
				//List<ItemStack> tRecipeOutputs = mods.railcraft.api.crafting.RailcraftCraftingManager.rockCrusher.getRecipe(GT_Utility.copyAmount(1, aInputs[0])).getRandomizedOuputs();
				//if (tRecipeOutputs != null) {
				//    aRecipe = new GT_Recipe(false, new ItemStack[]{GT_Utility.copyAmount(1, aInputs[0])}, tRecipeOutputs.toArray(new ItemStack[tRecipeOutputs.size()]), null, null, null, null, 800, 2, 0);
				//    aRecipe.mCanBeBuffered = false;
				//    aRecipe.mNeedsEmptyOutput = true;
				//    return aRecipe;
				//}
			} catch (NoClassDefFoundError e) {
				if (D1) GT_Log.err.println("Railcraft Not loaded");
			} catch (NullPointerException e) {/**/}

			ItemStack comparedInput = GT_Utility.copy(inputs[0]);
			ItemStack[] outputItems = GT_ModHandler.getMachineOutput(comparedInput, ic2.api.recipe.Recipes.macerator.getRecipes(), true, new NBTTagCompound(), null, null, null);
			return GT_Utility.arrayContainsNonNull(outputItems) ? new Recipe(false, new ItemStack[]{GT_Utility.copyAmount(inputs[0].stackSize - comparedInput.stackSize, inputs[0])}, outputItems, null, null, null, null, 400, 2, 0) : null;
		}

		@Override
		public boolean containsInput(ItemStack stack) {
			return super.containsInput(stack) || GT_Utility.arrayContainsNonNull(GT_ModHandler.getMachineOutput(GT_Utility.copyAmount(64, stack), ic2.api.recipe.Recipes.macerator.getRecipes(), false, new NBTTagCompound(), null, null, null));
		}
	}

	/**
	 * Special Class for Assembler handling.
	 */
	public static class GT_Recipe_Map_Assembler extends RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> {

		public GT_Recipe_Map_Assembler(Collection<Recipe> recipeList, String unlocalizedName, String localName, String JEIName, String JEIGUIPath, int minInputs, int maxInputs, int minOutputs, int maxOutputs, int minFluidInputs, int maxFluidInputs, int minFluidOutputs, int maxFluidOutputs, int amperage, String JEISpecialValuePre, int JEISpecialValueMultiplier, String JEISpecialValuePost, boolean showVoltageAmperageInJEI, boolean JEIAllowed, Recipe.RecipeBuilder defaultRecipe) {
			super(recipeList, unlocalizedName, localName, JEIName, JEIGUIPath, minInputs, maxInputs, minOutputs, maxOutputs, minFluidInputs, maxFluidInputs, minFluidOutputs, maxFluidOutputs, amperage, JEISpecialValuePre, JEISpecialValueMultiplier, JEISpecialValuePost, showVoltageAmperageInJEI, JEIAllowed, defaultRecipe);
		}

		@Override
		public Recipe findRecipe(IHasWorldObjectAndCoords tileEntity, Recipe recipe, boolean notUnificated, long voltage, FluidStack[] fluidInputs, ItemStack specialSlot, ItemStack[] inputs) {
			Recipe tmpRecipe = super.findRecipe(tileEntity, recipe, notUnificated, voltage, fluidInputs, specialSlot, inputs);
			if (inputs == null || inputs.length <= 0 || inputs[0] == null || tmpRecipe == null || !GregTech_API.sPostloadFinished)
				return tmpRecipe;
			for (ItemStack stack : inputs) {
				if (ItemList.Paper_Printed_Pages.isStackEqual(stack, false, true)) {
					tmpRecipe = tmpRecipe.copy();
					tmpRecipe.canBeBuffered = false;
					tmpRecipe.outputs[0].setTagCompound(stack.getTagCompound());
				}
			}
			return tmpRecipe;
		}
	}

	/**
	 * Special Class for Forming Press handling.
	 */
	public static class GT_Recipe_Map_FormingPress extends RecipeMap<Recipe, Recipe.NotConsumableInputRecipeBuilder> {

		public GT_Recipe_Map_FormingPress(Collection<Recipe> recipeList, String unlocalizedName, String localName, String JEIName, String JEIGUIPath, int minInputs, int maxInputs, int minOutputs, int maxOutputs, int minFluidInputs, int maxFluidInputs, int minFluidOutputs, int maxFluidOutputs, int amperage, String JEISpecialValuePre, int JEISpecialValueMultiplier, String JEISpecialValuePost, boolean showVoltageAmperageInJEI, boolean JEIAllowed, Recipe.RecipeBuilder defaultRecipe) {
			super(recipeList, unlocalizedName, localName, JEIName, JEIGUIPath, minInputs, maxInputs, minOutputs, maxOutputs, minFluidInputs, maxFluidInputs, minFluidOutputs, maxFluidOutputs, amperage, JEISpecialValuePre, JEISpecialValueMultiplier, JEISpecialValuePost, showVoltageAmperageInJEI, JEIAllowed, defaultRecipe);
		}

		@Override
		public Recipe findRecipe(IHasWorldObjectAndCoords tileEntity, Recipe recipe, boolean notUnificated, long voltage, FluidStack[] fluidInputs, ItemStack specialSlot, ItemStack[] inputs) {
			Recipe tmpRecipe = super.findRecipe(tileEntity, recipe, notUnificated, voltage, fluidInputs, specialSlot, inputs);
			if (inputs == null || inputs.length < 2 || inputs[0] == null || inputs[1] == null || !GregTech_API.sPostloadFinished)
				return tmpRecipe;
			if (tmpRecipe == null) {
				if (ItemList.Shape_Mold_Name.isStackEqual(inputs[0], false, true)) {
					ItemStack output = GT_Utility.copyAmount(1, inputs[1]);
					output.setStackDisplayName(inputs[0].getDisplayName());
					tmpRecipe = new Recipe(false, new ItemStack[]{ItemList.Shape_Mold_Name.get(0), GT_Utility.copyAmount(1, inputs[1])}, new ItemStack[]{output}, null, null, null, null, 128, 8, 0);
					tmpRecipe.canBeBuffered = false;
					return tmpRecipe;
				}
				if (ItemList.Shape_Mold_Name.isStackEqual(inputs[1], false, true)) {
					ItemStack output = GT_Utility.copyAmount(1, inputs[0]);
					output.setStackDisplayName(inputs[1].getDisplayName());
					tmpRecipe = new Recipe(false, new ItemStack[]{ItemList.Shape_Mold_Name.get(0), GT_Utility.copyAmount(1, inputs[0])}, new ItemStack[]{output}, null, null, null, null, 128, 8, 0);
					tmpRecipe.canBeBuffered = false;
					return tmpRecipe;
				}
				return null;
			}
			for (ItemStack mold : inputs) {
				if (ItemList.Shape_Mold_Credit.isStackEqual(mold, false, true)) {
					NBTTagCompound tag = mold.getTagCompound();
					if (tag == null) tag = new NBTTagCompound();
					if (!tag.hasKey("credit_security_id")) tag.setLong("credit_security_id", System.nanoTime());
					mold.setTagCompound(tag);

					tmpRecipe = tmpRecipe.copy();
					tmpRecipe.canBeBuffered = false;
					tmpRecipe.outputs[0].setTagCompound(tag);
					return tmpRecipe;
				}
			}
			return tmpRecipe;
		}
	}

	/**
	 * Special Class for Printer handling.
	 */
	public static class GT_Recipe_Map_Printer extends RecipeMap<Recipe, Recipe.DefaultRecipeBuilder> {

		public GT_Recipe_Map_Printer(Collection<Recipe> recipeList, String unlocalizedName, String localName, String JEIName, String JEIGUIPath, int minInputs, int maxInputs, int minOutputs, int maxOutputs, int minFluidInputs, int maxFluidInputs, int minFluidOutputs, int maxFluidOutputs, int amperage, String JEISpecialValuePre, int JEISpecialValueMultiplier, String JEISpecialValuePost, boolean showVoltageAmperageInJEI, boolean JEIAllowed, Recipe.RecipeBuilder defaultRecipe) {
			super(recipeList, unlocalizedName, localName, JEIName, JEIGUIPath, minInputs, maxInputs, minOutputs, maxOutputs, minFluidInputs, maxFluidInputs, minFluidOutputs, maxFluidOutputs, amperage, JEISpecialValuePre, JEISpecialValueMultiplier, JEISpecialValuePost, showVoltageAmperageInJEI, JEIAllowed, defaultRecipe);
		}

		@Override
		public Recipe findRecipe(IHasWorldObjectAndCoords tileEntity, Recipe recipe, boolean notUnificated, long voltage, FluidStack[] fluidInputs, ItemStack specialSlot, ItemStack[] inputs) {
			Recipe tmpRecipe = super.findRecipe(tileEntity, recipe, notUnificated, voltage, fluidInputs, specialSlot, inputs);
			if (inputs == null || inputs.length <= 0 || inputs[0] == null || fluidInputs == null || fluidInputs.length <= 0 || fluidInputs[0] == null || !GregTech_API.sPostloadFinished)
				return tmpRecipe;

			Dyes dye = null;
			for (Dyes tmpDye : Dyes.VALUES)
				if (tmpDye.isFluidDye(fluidInputs[0])) {
					dye = tmpDye;
					break;
				}

			if (dye == null) return tmpRecipe;

			if (tmpRecipe == null) {
				ItemStack output = GT_ModHandler.getAllRecipeOutput(tileEntity == null ? null : tileEntity.getWorldObj(), inputs[0], inputs[0], inputs[0], inputs[0], ItemList.DYE_ONLY_ITEMS[dye.mIndex].get(1), inputs[0], inputs[0], inputs[0], inputs[0]);
				if (output != null)
					return addRecipe(new Recipe(true, new ItemStack[]{GT_Utility.copyAmount(8, inputs[0])}, new ItemStack[]{output}, null, null, new FluidStack[]{new FluidStack(fluidInputs[0].getFluid(), (int) L)}, null, 256, 2, 0), false, false, true);

				output = GT_ModHandler.getAllRecipeOutput(tileEntity == null ? null : tileEntity.getWorldObj(), inputs[0], ItemList.DYE_ONLY_ITEMS[dye.mIndex].get(1));
				if (output != null)
					return addRecipe(new Recipe(true, new ItemStack[]{GT_Utility.copyAmount(1, inputs[0])}, new ItemStack[]{output}, null, null, new FluidStack[]{new FluidStack(fluidInputs[0].getFluid(), (int) L)}, null, 32, 2, 0), false, false, true);
			} else {
				if (inputs[0].getItem() == Items.WRITTEN_BOOK) {
					if (!ItemList.Tool_DataStick.isStackEqual(specialSlot, false, true)) return null;
					NBTTagCompound tag = specialSlot.getTagCompound();
					if (tag == null || !GT_Utility.isStringValid(tag.getString("title")) || !GT_Utility.isStringValid(tag.getString("author")))
						return null;

					tmpRecipe = tmpRecipe.copy();
					tmpRecipe.canBeBuffered = false;
					tmpRecipe.outputs[0].setTagCompound(tag);
					return tmpRecipe;
				}
				if (inputs[0].getItem() == Items.FILLED_MAP) {
					if (!ItemList.Tool_DataStick.isStackEqual(specialSlot, false, true)) return null;
					NBTTagCompound tag = specialSlot.getTagCompound();
					if (tag == null || !tag.hasKey("map_id")) return null;

					tmpRecipe = tmpRecipe.copy();
					tmpRecipe.canBeBuffered = false;
					tmpRecipe.outputs[0].setItemDamage(tag.getShort("map_id"));
					return tmpRecipe;
				}
				if (ItemList.Paper_Punch_Card_Empty.isStackEqual(inputs[0], false, true)) {
					if (!ItemList.Tool_DataStick.isStackEqual(specialSlot, false, true)) return null;
					NBTTagCompound tag = specialSlot.getTagCompound();
					if (tag == null || !tag.hasKey("GT.PunchCardData")) return null;

					tmpRecipe = tmpRecipe.copy();
					tmpRecipe.canBeBuffered = false;
					tmpRecipe.outputs[0].setTagCompound(GT_Utility.getNBTContainingString(new NBTTagCompound(), "GT.PunchCardData", tag.getString("GT.PunchCardData")));
					return tmpRecipe;
				}
			}
			return tmpRecipe;
		}

		@Override
		public boolean containsInput(ItemStack stack) {
			return true;
		}

		@Override
		public boolean containsInput(FluidStack fluid) {
			return super.containsInput(fluid) || Dyes.isAnyFluidDye(fluid);
		}

		@Override
		public boolean containsInput(Fluid fluid) {
			return super.containsInput(fluid) || Dyes.isAnyFluidDye(fluid);
		}
	}
}
