package gregtech.api.recipes;

import gregtech.api.GregTech_API;
import gregtech.api.material.Dyes;
import gregtech.api.GT_Values;
import gregtech.api.items.ItemList;
import gregtech.api.material.type.Material;
import gregtech.api.material.Materials;
import gregtech.api.material.OrePrefixes;
import gregtech.api.material.type.MetalMaterial;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.interfaces.tileentity.IHasWorldObjectAndCoords;
import gregtech.api.objects.SimpleItemStack;
import gregtech.api.objects.ItemData;
import gregtech.api.objects.MaterialStack;
import gregtech.api.util.GT_LanguageManager;
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
import java.util.List;
import java.util.Map;

import static gregtech.api.GT_Values.L;
import static gregtech.api.GT_Values.W;

//todo update examples after materials/oredictunificator/itemlist update
public class RecipeMap<T extends Recipe, R extends RecipeBuilder<T, R>> {

	/**
	 * Contains all Recipe Maps
	 */
	public static final Collection<RecipeMap<?, ?>> RECIPE_MAPS = new ArrayList<>();

	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> ORE_WASHER_RECIPES = new RecipeMapOreWasher(new HashSet<>(0), "ic.recipe.orewasher", "Ore Washer", "ic2.blockOreWashingPlant", "basicmachines/OreWasher", 1, 1, 3, 3, 0, 1, 0, 0, 1, "", 1, "", true, false, new RecipeBuilder.DefaultRecipeBuilder());
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> THERMAL_CENTRIFUGE_RECIPES = new RecipeMapThermalCentrifuge(new HashSet<>(0), "ic.recipe.thermalcentrifuge", "Thermal Centrifuge", "ic2.blockCentrifuge", "basicmachines/ThermalCentrifuge", 1, 1, 1, 3, 0, 0, 0, 0, 2, "", 1, "", true, false, new RecipeBuilder.DefaultRecipeBuilder());
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> COMPRESSOR_RECIPES = new RecipeMapCompressor(new HashSet<>(0), "ic.recipe.compressor", "Compressor", "ic2.compressor", "basicmachines/Compressor", 1, 1, 1, 1, 0, 0, 0, 0, 1, "", 1, "", true, false, new RecipeBuilder.DefaultRecipeBuilder());
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> EXTRACTOR_RECIPES = new RecipeMapExtractor(new HashSet<>(0), "ic.recipe.extractor", "Extractor", "ic2.extractor", "basicmachines/Extractor", 1, 1, 1, 1, 0, 0, 0, 0, 1, "", 1, "", true, false, new RecipeBuilder.DefaultRecipeBuilder());
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> RECYCLER_RECIPES = new RecipeMapRecycler(new HashSet<>(0), "ic.recipe.recycler", "Recycler", "ic2.recycler", "basicmachines/Recycler", 1, 1, 1, 1, 0, 0, 0, 0, 1, "", 1, "", true, false, new RecipeBuilder.DefaultRecipeBuilder());
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> FURNACE_RECIPES = new RecipeMapFurnace(new HashSet<>(0), "mc.recipe.furnace", "Furnace", "smelting", "basicmachines/E_Furnace", 1, 1, 1, 1, 0, 0, 0, 0, 1, "", 1, "", true, false, new RecipeBuilder.DefaultRecipeBuilder());
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> MICROWAVE_RECIPES = new RecipeMapMicrowave(new HashSet<>(0), "gt.recipe.microwave", "Microwave", "smelting", "basicmachines/E_Furnace", 1, 1, 1, 1, 0, 0, 0, 0, 1, "", 1, "", true, false, new RecipeBuilder.DefaultRecipeBuilder());

	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> SCANNER_FAKE_RECIPES = new FakeRecipeMap<>(new HashSet<>(300), "gt.recipe.scanner", "Scanner", null, "basicmachines/Scanner", 1, 0, 0, 0, 0, 0, 0, 0, 1, "", 1, "", true, true, new RecipeBuilder.DefaultRecipeBuilder());//TODO min max amounts
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> ROCK_BREAKER_FAKE_RECIPES = new FakeRecipeMap<>(new HashSet<>(3), "gt.recipe.rockbreaker", "Rock Breaker", null, "basicmachines/RockBreaker", 0, 0, 0, 0, 0, 0, 0, 0, 1, "", 1, "", true, true, new RecipeBuilder.DefaultRecipeBuilder());//TODO min max amounts
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> BY_PRODUCT_LIST = new FakeRecipeMap<>(new HashSet<>(1000), "gt.recipe.byproductlist", "Ore Byproduct List", null, "basicmachines/Default", 1, 0, 0, 0, 0, 0, 0, 0, 1, "", 1, "", true, true, new RecipeBuilder.DefaultRecipeBuilder());//TODO min max amounts
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> REPICATOR_FAKE_RECIPES = new FakeRecipeMap<>(new HashSet<>(100), "gt.recipe.replicator", "Replicator", null, "basicmachines/Replicator", 0, 1, 0, 0, 0, 0, 0, 0, 1, "", 1, "", true, true, new RecipeBuilder.DefaultRecipeBuilder());//TODO min max amounts
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> ASSEMBLYLINE_FAKE_RECIPES = new FakeRecipeMap<>(new HashSet<>(30), "gt.recipe.assemblyline", "Assembly Line", null, "basicmachines/Default", 1, 0, 0, 0, 0, 0, 0, 0, 1, "", 1, "", true, true, new RecipeBuilder.DefaultRecipeBuilder());//TODO min max amounts

	/**
	 * Examples:
	 * <pre>
	 * 		RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
	 *			.inputs(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 1L), new ItemStack(Items.coal, 1, GT_Values.W))
	 *			.outputs(new ItemStack(Blocks.torch, 4))
	 *			.duration(400)
	 *			.EUt(1)
	 *			.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> ASSEMBLER_RECIPES = new RecipeMapAssembler(new HashSet<>(300), "gt.recipe.assembler", "Assembler", null, "basicmachines/Assembler", 1, 2, 1, 1, 0, 1, 0, 0, 1, "", 1, "", true, true, new RecipeBuilder.DefaultRecipeBuilder());

	/**
	 * Example:
	 * <pre>
	 * 	    RecipeMap.PRINTER_RECIPES.recipeBuilder()
	 *				.inputs(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Paper, 3L))
	 *				.fluidInputs(FluidRegistry.getFluidStack("squidink", 144))
	 *				.specialItem(ItemList.Tool_DataStick.getWithName(0, "With Scanned Book Data"))
	 *				.outputs(ItemList.Paper_Printed_Pages.get(1))
	 *				.duration(400)
	 *				.EUt(2)
	 *				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> PRINTER_RECIPES = new RecipeMapPrinter(new HashSet<>(100), "gt.recipe.printer", "Printer", null, "basicmachines/Printer", 1, 1, 1, 1, 1, 1, 0, 0, 1, "", 1, "", true, true, new RecipeBuilder.DefaultRecipeBuilder());

	/**
	 * Examples:
	 * <pre>
	 * 		RecipeMap.PRESS_RECIPES.recipeBuilder()
	 * 				.inputs(ItemList.Empty_Board_Basic.get(1), ItemList.Circuit_Parts_Wiring_Basic.get(4))
	 * 				.outputs(ItemList.Circuit_Board_Basic.get(1))
	 * 				.duration(32)
	 * 				.EUt(16)
	 * 				.buildAndRegister();
	 * 		RecipeMap.PRESS_RECIPES.recipeBuilder()
	 * 				.inputs(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 1))
	 * 				.notConsumable(ItemList.Shape_Mold_Credit)
	 * 				.outputs(ItemList.Credit_Iron.get(4))
	 * 				.duration(100)
	 * 				.EUt(16)
	 * 				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.NotConsumableInputRecipeBuilder> PRESS_RECIPES = new RecipeMapFormingPress(new HashSet<>(100), "gt.recipe.press", "Forming Press", null, "basicmachines/Press", 2, 2, 1, 1, 0, 0, 0, 0, 1, "", 1, "", true, true, new RecipeBuilder.NotConsumableInputRecipeBuilder());

	/**
	 * Example:
	 * <pre>
	 *	   RecipeMap.MACERATOR_RECIPES.recipeBuilder()
	 *	   			.inputs(GT_OreDictUnificator.get(OrePrefixes.block, Materials.Marble, 1))
	 *	   			.outputs(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Marble, 1))
	 *	   			.duration(160)
	 *	   			.EUt(4)
	 *	   			.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> MACERATOR_RECIPES = new RecipeMapMacerator(new HashSet<>(10000), "gt.recipe.macerator", "Pulverization", null, "basicmachines/Macerator4", 1, 1, 1, 4, 0, 0, 0, 0, 1, "", 1, "", true, true, new RecipeBuilder.DefaultRecipeBuilder());

	/**
	 * Input full box, output item and empty box.
	 * Example:
	 * <pre>
	 *		RecipeMap.UNBOXINATOR_RECIPES.recipeBuilder()
	 *				.inputs(ItemList.Tool_MatchBox_Full.get(1))
	 *				.outputs(ItemList.Tool_Matches.get(16))
	 *				.duration(32)
	 *				.EUt(16)
	 *				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> UNBOXINATOR_RECIPES = new RecipeMapUnboxinator(new HashSet<>(2500), "gt.recipe.unpackager", "Unpackager", null, "basicmachines/Unpackager", 1, 1, 1, 2, 0, 0, 0, 0, 1, "", 1, "", true, true, new RecipeBuilder.DefaultRecipeBuilder());

	/**
	 * Example:
	 * <pre>
	 * 		RecipeMap.FLUID_CANNER_RECIPES.recipeBuilder()
	 * 				.inputs(ItemList.Battery_Hull_LV.get(1))
	 * 				.outputs(ItemList.IC2_ReBattery.get(1))
	 * 				.fluidInputs(Materials.Redstone.getFluid(288L))
	 * 				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> FLUID_CANNER_RECIPES = new RecipeMapFluidCanner(new HashSet<>(100), "gt.recipe.fluidcanner", "Fluid Canning Machine", null, "basicmachines/FluidCannerJEI", 1, 1, 1, 1, 0, 1, 0, 1, 1, "", 1, "", true, true, new RecipeBuilder.DefaultRecipeBuilder() {
		@Override
		protected void finalizeAndValidate() {
			duration(fluidOutputs.isEmpty() ? fluidInputs.get(0).amount / 62 : fluidOutputs.get(0).amount / 62);
			super.finalizeAndValidate();
		}
	}.EUt(1));

	/**
	 * Example:
	 * <pre>
	 * 		RecipeMap.PLASMA_ARC_FURNACE_RECIPES.recipeBuilder()
	 * 				.inputs(ItemList.Block_TungstenSteelReinforced.get(1))
	 * 				.outputs(GT_OreDictUnificator.get(OrePrefixes.ingot,Materials.TungstenSteel,2), GT_OreDictUnificator.get(OrePrefixes.dust,Materials.Concrete,1))
	 * 				.fluidInputs(Materials.Argon.getPlasma(16))
	 * 				.fluidOutputs(Materials.Argon.getGas(16))
	 * 				.duration(160)
	 * 				.EUt(96)
	 * 				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> PLASMA_ARC_FURNACE_RECIPES = new RecipeMap<>(new HashSet<>(10000), "gt.recipe.plasmaarcfurnace", "Plasma Arc Furnace", null, "basicmachines/PlasmaArcFurnace", 1, 1, 1, 4, 1, 1, 0, 1, 1, "", 1, "", true, true, new RecipeBuilder.DefaultRecipeBuilder());

	/**
	 * <pre>
	 * Example:
	 * 		RecipeMap.ARC_FURNACE_RECIPES.recipeBuilder()
	 * 				.inputs(ItemList.Block_TungstenSteelReinforced.get(1))
	 * 				.outputs(GT_OreDictUnificator.get(OrePrefixes.ingot,Materials.TungstenSteel,2), GT_OreDictUnificator.get(OrePrefixes.dust,Materials.Concrete,1))
	 * 				.duration(160)
	 * 				.EUt(96)
	 * 				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.ArcFurnaceRecipeBuilder> ARC_FURNACE_RECIPES = new RecipeMap<>(new HashSet<>(10000), "gt.recipe.arcfurnace", "Arc Furnace", null, "basicmachines/ArcFurnace", 1, 1, 1, 4, 1, 1, 0, 0, 3, "", 1, "", true, true, new RecipeBuilder.ArcFurnaceRecipeBuilder());

	/**
	 * Example:
	 * <pre>
	 *     RecipeMap.SIFTER_RECIPES.recipeBuilder()
	 *     			.inputs(new ItemStack(Blocks.SAND))
	 *     			.chancedOutput(GT_OreDictUnificator.get(OrePrefixes.gemExquisite, Materials.Ruby, 1L), 300)
	 *     			.chancedOutput(GT_OreDictUnificator.get(OrePrefixes.gemFlawless, Materials.Ruby, 1L), 1200)
	 *     			.chancedOutput(GT_OreDictUnificator.get(OrePrefixes.gemFlawed, Materials.Ruby, 1L), 4500)
	 *     			.chancedOutput(GT_OreDictUnificator.get(OrePrefixes.gemChipped, Materials.Ruby, 1L), 1400)
	 *     			.chancedOutput(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Ruby, 1L), 2800)
	 *     			.duration(800)
	 *     			.EUt(16)
	 *     			.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> SIFTER_RECIPES = new RecipeMap<>(new HashSet<>(100), "gt.recipe.sifter", "Sifter", null, "basicmachines/Sifter", 1, 1, 0, 6, 0, 0, 0, 0, 1, "", 1, "", true, true, new RecipeBuilder.DefaultRecipeBuilder());

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
	public static final RecipeMap<Recipe, RecipeBuilder.NotConsumableInputRecipeBuilder> LASER_ENGRAVER_RECIPES = new RecipeMap<>(new HashSet<>(100), "gt.recipe.laserengraver", "Precision Laser Engraver", null, "basicmachines/LaserEngraver", 2, 2, 1, 1, 0, 0, 0, 0, 1, "", 1, "", true, true, new RecipeBuilder.NotConsumableInputRecipeBuilder());

	/**
	 * Example:
	 * <pre>
	 * 		RecipeMap.MIXER_RECIPES.recipeBuilder()
	 * 				.inputs(new ItemStack(Blocks.SAND, 1, GT_Values.W), new ItemStack(Blocks.DIRT, 1, GT_Values.W))
	 * 				.fluidInputs(Materials.Water.getFluid(250L))
	 * 				.outputs(GT_ModHandler.getModItem("Forestry", "soil", 2, 1))
	 * 				.duration(16)
	 * 				.EUt(16)
	 * 				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> MIXER_RECIPES = new RecipeMap<>(new HashSet<>(100), "gt.recipe.mixer", "Mixer", null, "basicmachines/Mixer", 1, 4, 0, 0, 0, 1, 0, 1, 1, "", 1, "", true, true, new RecipeBuilder.DefaultRecipeBuilder() {
		@Override
		protected RecipeBuilder.DefaultRecipeBuilder validate() {
			Validate.isTrue((inputs.isEmpty() && fluidInputs.isEmpty()) || (outputs.isEmpty() && fluidOutputs.isEmpty()));
			return super.validate();
		}
	});

	/**
	 * Example:
	 * <pre>
	 *	 	RecipeMap.AUTOCLAVE_RECIPES.recipeBuilder()
	 * 				.inputs(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Carbon, 16))
	 * 				.fluidInputs(Materials.Lutetium.getFluid(4))
	 * 				.chancedOutput(GT_ModHandler.getIC2Item("carbonFiber", 8L), 3333)
	 * 				.duration(600)
	 * 				.EUt(5)
	 * 				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> AUTOCLAVE_RECIPES = new RecipeMap<>(new HashSet<>(200), "gt.recipe.autoclave", "Autoclave", null, "basicmachines/Autoclave", 1, 1, 1, 1, 1, 1, 0, 0, 1, "", 1, "", true, true, new RecipeBuilder.DefaultRecipeBuilder());

	/**
	 * Example:
	 * <pre>
	 * 	    RecipeMap.ELECTROMAGNETIC_SEPARATOR_RECIPES.recipeBuilder()
	 * 				.inputs(GT_OreDictUnificator.get(OrePrefixes.dustPure, Materials.Iron, 1L))
	 * 				.chancedOutput(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Iron, 1L), 10000)
	 * 				.chancedOutput(GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Iron, 1L), 4000)
	 * 				.chancedOutput(GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.Iron, 1L), 2000)
	 * 				.duration(400)
	 * 				.EUt(24)
	 * 				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> ELECTROMAGNETIC_SEPARATOR_RECIPES = new RecipeMap<>(new HashSet<>(50), "gt.recipe.electromagneticseparator", "Electromagnetic Separator", null, "basicmachines/ElectromagneticSeparator", 1, 1, 1, 3, 0, 0, 0, 0, 1, "", 1, "", true, true, new RecipeBuilder.DefaultRecipeBuilder());

	/**
	 * Example:
	 * <pre>
	 * 		RecipeMap.POLARIZER_RECIPES.recipeBuilder()
	 * 				.inputs(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 1L))
	 * 				.outputs(GT_OreDictUnificator.get(aPrefix, Materials.IronMagnetic, 1L))
	 * 				.duration(100)
	 * 				.EUt(16)
	 * 				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> POLARIZER_RECIPES = new RecipeMap<>(new HashSet<>(100), "gt.recipe.polarizer", "Electromagnetic Polarizer", null, "basicmachines/Polarizer", 1, 1, 1, 1, 0, 0, 0, 0, 1, "", 1, "", true, true, new RecipeBuilder.DefaultRecipeBuilder());

	/**
	 * Example:
	 * <pre>
	 * 		RecipeMap.CHEMICAL_BATH_RECIPES.recipeBuilder()
	 * 				.inputs(new ItemStack(Items.reeds, 1, GT_Values.W))
	 * 				.fluidInputs(Materials.Water.getFluid(100))
	 * 				.outputs(new ItemStack(Items.paper, 1, 0))
	 * 				.duration(100)
	 * 				.EUt(8)
	 * 				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> CHEMICAL_BATH_RECIPES = new RecipeMap<>(new HashSet<>(200), "gt.recipe.chemicalbath", "Chemical Bath", null, "basicmachines/ChemicalBath", 1, 1, 1, 3, 1, 1, 0, 0, 1, "", 1, "", true, true, new RecipeBuilder.DefaultRecipeBuilder());

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
	public static final RecipeMap<Recipe, RecipeBuilder.BrewingRecipeBuilder> BREWING_RECIPES = new RecipeMap<>(new HashSet<>(100), "gt.recipe.brewer", "Brewing Machine", null, "basicmachines/PotionBrewer", 1, 1, 0, 0, 1, 1, 1, 1, 1, "", 1, "", true, true, new RecipeBuilder.BrewingRecipeBuilder().nonOptimized().duration(128).EUt(4));

	/**
	 * Example:
	 * <pre>
	 * 		RecipeMap.FLUID_HEATER_RECIPES.recipeBuilder()
	 * 				.circuitMeta(1)
	 * 				.fluidInputs(Materials.Water.getFluid(6L))
	 * 				.fluidOutputs(Materials.Water.getGas(960L))
	 * 				.duration(30)
	 * 				.EUt(32)
	 * 				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.IntCircuitRecipeBuilder> FLUID_HEATER_RECIPES = new RecipeMap<>(new HashSet<>(100), "gt.recipe.fluidheater", "Fluid Heater", null, "basicmachines/FluidHeater", 1, 1, 0, 0, 1, 1, 1, 1, 1, "", 1, "", true, true, new RecipeBuilder.IntCircuitRecipeBuilder());

	/**
	 * Example:
	 * <pre>
	 *	 	RecipeMap.DISTILLERY_RECIPES.recipeBuilder()
	 *	 			.circuitMeta(4)
	 *	 			.fluidInputs(Materials.Creosote.getFluid(3L))
	 *	 			.fluidOutputs(Materials.Lubricant.getFluid(1L))
	 *	 			.duration(16)
	 *	 			.EUt(24)
	 *	 			.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.IntCircuitRecipeBuilder> DISTILLERY_RECIPES = new RecipeMap<>(new HashSet<>(100), "gt.recipe.distillery", "Distillery", null, "basicmachines/Distillery", 1, 1, 0, 0, 1, 1, 1, 1, 1, "", 1, "", true, true, new RecipeBuilder.IntCircuitRecipeBuilder());

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
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> FERMENTING_RECIPES = new RecipeMap<>(new HashSet<>(100), "gt.recipe.fermenter", "Fermenter", null, "basicmachines/Fermenter", 0, 0, 0, 0, 1, 1, 1, 1, 1, "", 1, "", true, true, new RecipeBuilder.DefaultRecipeBuilder().nonOptimized().EUt(2));

	/**
	 * Example:
	 * <pre>
	 *  	RecipeMap.FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
	 *				.notConsumable(ItemList.Shape_Mold_Casing)
	 *				.fluidInputs(Materials.Steel.getFluid(72L))
	 *				.outputs(ItemList.IC2_Item_Casing_Steel.get(1))
	 *				.duration(16)
	 *				.EUt(8)
	 *				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.NotConsumableInputRecipeBuilder> FLUID_SOLIDFICATION_RECIPES = new RecipeMap<>(new HashSet<>(100), "gt.recipe.fluidsolidifier", "Fluid Solidifier", null, "basicmachines/FluidSolidifier", 1, 1, 1, 1, 1, 1, 0, 0, 1, "", 1, "", true, true, new RecipeBuilder.NotConsumableInputRecipeBuilder());

	/**
	 * Examples:
	 * <pre>
	 * 		RecipeMap.FLUID_EXTRACTION_RECIPES.recipeBuilder()
	 * 				.inputs(new ItemStack(Blocks.SNOW))
	 * 				.fluidOutputs(Materials.Water.getFluid(1000L))
	 * 				.duration(128)
	 * 				.EUt(4)
	 * 				.buildAndRegister();
	 *
	 * 		RecipeMap.FLUID_EXTRACTION_RECIPES.recipeBuilder()
	 * 				.inputs(GT_ModHandler.getModItem("Forestry", "phosphor", 1L))
	 * 				.chancedOutput(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Phosphor, 1L), 1000)
	 * 				.fluidOutputs(Materials.Lava.getFluid(800L))
	 * 				.duration(256)
	 * 				.EUt(128)
	 * 				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.NotConsumableInputRecipeBuilder> FLUID_EXTRACTION_RECIPES = new RecipeMap<>(new HashSet<>(100), "gt.recipe.fluidextractor", "Fluid Extractor", null, "basicmachines/FluidExtractor", 1, 1, 0, 1, 0, 0, 1, 1, 1, "", 1, "", true, true, new RecipeBuilder.NotConsumableInputRecipeBuilder());

	/**
	 * Input item and empty box, output full box
	 * Example:
	 * <pre>
	 *   	RecipeMap.BOXINATOR_RECIPES.recipeBuilder()
	 *				.inputs(ItemList.Tool_Matches.get(16), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Paper, 1L))
	 *				.outputs(ItemList.Tool_MatchBox_Full.get(1))
	 *				.duration(64)
	 *				.EUt(16)
	 *				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> BOXINATOR_RECIPES = new RecipeMap<>(new HashSet<>(2500), "gt.recipe.packager", "Packager", null, "basicmachines/Packager", 2, 2, 1, 1, 0, 0, 0, 0, 1, "", 1, "", true, true, new RecipeBuilder.DefaultRecipeBuilder());

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
	public static final RecipeMap<Recipe.FusionRecipe, RecipeBuilder.FusionRecipeBuilder> FUSION_RECIPES = new RecipeMap<>(new HashSet<>(50), "gt.recipe.fusionreactor", "Fusion Reactor", null, "basicmachines/Default", 0, 0, 0, 0, 2, 2, 1, 1, 1, "Start: ", 1, " EU", true, true, new RecipeBuilder.FusionRecipeBuilder());

	/**
	 * Examples:
	 * <pre>
	 *		RecipeMap.CENTRIFUGE_RECIPES.recipeBuilder()
	 *				.inputs(new ItemStack(Blocks.SANDSTONE))
	 *				.cellAmount(1)
	 *				.outputs(GT_OreDictUnificator.get(OrePrefixes.cell, Materials.Oil, 1L), new ItemStack(Blocks.SAND, 1, 0))
	 *				.duration(1000)
	 *				.buildAndRegister();
	 *
	 *		RecipeMap.CENTRIFUGE_RECIPES.recipeBuilder()
	 *				.inputs(new ItemStack(Blocks.PUMPKIN, 16))
	 *				.fluidOutputs(Materials.Methane.getGas(1152L))
	 *				.duration(4608)
	 *				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.CellInputRecipeBuilder> CENTRIFUGE_RECIPES = new RecipeMap<>(new HashSet<>(1000), "gt.recipe.centrifuge", "Centrifuge", null, "basicmachines/Centrifuge", 0, 2, 0, 6, 0, 1, 0, 1, 1, "", 1, "", true, true, new RecipeBuilder.CellInputRecipeBuilder() {
		@Override
		protected RecipeBuilder.CellInputRecipeBuilder validate() {
			Validate.isTrue((inputs.isEmpty() && fluidInputs.isEmpty()) || (outputs.isEmpty() && fluidOutputs.isEmpty()));
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
	 *	   			.outputs(GT_OreDictUnificator.get(OrePrefixes.dust,Materials.Copper,1), GT_OreDictUnificator.get(OrePrefixes.cell,Materials.Oxygen,1))
	 *	   			.duration(900)
	 *	   			.EUt(30)
	 *	   			.buildAndRegister();
	 *
	 *		RecipeMap.ELECTROLYZER_RECIPES.recipeBuilder()
	 *				.inputs(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Tungstate, 7L))
	 *				.fluidInputs(Materials.Hydrogen.getGas(7000L))
	 *				.fluidOutputs(Materials.Oxygen.getGas(4000L))
	 *				.outputs(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Tungsten, 1), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Lithium, 2L))
	 *				.duration(120)
	 *				.EUt(1920)
	 *				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.CellInputRecipeBuilder> ELECTROLYZER_RECIPES = new RecipeMap<>(new HashSet<>(200), "gt.recipe.electrolyzer", "Electrolyzer", null, "basicmachines/Electrolyzer", 0, 2, 0, 6, 0, 1, 0, 1, 1, "", 1, "", true, true, new RecipeBuilder.CellInputRecipeBuilder() {
		@Override
		protected RecipeBuilder.CellInputRecipeBuilder validate() {
			Validate.isTrue((inputs.isEmpty() && fluidInputs.isEmpty()) || (outputs.isEmpty() && fluidOutputs.isEmpty()));
			return super.validate();
		}
	});

	/**
	 * Example:
	 * <pre>
	 *	    RecipeMap.BLAST_RECIPES.recipeBuilder()
	 *				.inputs(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Glass, 1), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Carbon, 1))
	 *				.fluidInputs(Materials.Electrum.getFluid(16))
	 *				.outputs(ItemList.Circuit_Board_Fiberglass.get(16))
	 *				.duration(80)
	 *				.EUt(480)
	 *				.blastFurnaceTemp(2600)
	 *				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe.BlastRecipe, RecipeBuilder.BlastRecipeBuilder> BLAST_RECIPES = new RecipeMap<>(new HashSet<>(500), "gt.recipe.blastfurnace", "Blast Furnace", null, "basicmachines/Default", 1, 2, 1, 2, 0, 1, 0, 1, 1, "Heat Capacity: ", 1, " K", false, true, new RecipeBuilder.BlastRecipeBuilder());

	/**
	 * Example:
	 * <pre>
	 *      RecipeMap.IMPLOSION_RECIPES.recipeBuilder()
	 *         		.inputs(ItemList.Ingot_IridiumAlloy.get(1))
	 *         		.explosivesAmount(8)
	 *         		.outputs(GT_OreDictUnificator.get(OrePrefixes.plateAlloy, Materials.Iridium, 1), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.DarkAsh, 4L))
	 *         		.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.ImplosionRecipeBuilder> IMPLOSION_RECIPES = new RecipeMap<>(new HashSet<>(50), "gt.recipe.implosioncompressor", "Implosion Compressor", null, "basicmachines/Default", 2, 2, 2, 2, 0, 0, 0, 0, 1, "", 1, "", true, true, new RecipeBuilder.ImplosionRecipeBuilder().duration(20).EUt(30));

	/**
	 * Example:
	 * <pre>
	 * 		RecipeMap.VACUUM_RECIPES.recipeBuilder()
	 *				.inputs(GT_OreDictUnificator.get(OrePrefixes.cell, Materials.Water, 1L))
	 *				.outputs(GT_OreDictUnificator.get(OrePrefixes.cell, Materials.Ice, 1L))
	 *				.duration(50)
	 *				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> VACUUM_RECIPES = new RecipeMap<>(new HashSet<>(100), "gt.recipe.vacuumfreezer", "Vacuum Freezer", null, "basicmachines/Default", 1, 1, 1, 1, 0, 0, 0, 0, 1, "", 1, "", true, true, new RecipeBuilder.DefaultRecipeBuilder().EUt(120));

	/**
	 * Example:
	 * <pre>
	 *      RecipeMap.CHEMICAL_RECIPES.recipeBuilder()
	 *				.inputs(GT_OreDictUnificator.get(OrePrefixes.cell, Materials.NitrogenDioxide, 4), GT_OreDictUnificator.get(OrePrefixes.cell, Materials.Oxygen, 1))
	 *				.fluidInputs(Materials.Water.getFluid(2000))
	 *				.fluidOutputs( new FluidStack(ItemList.sNitricAcid,4000))
	 *				.outputs(ItemList.Cell_Empty.get(5))
	 *				.duration(950)
	 *				.EUt(30)
	 *				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> CHEMICAL_RECIPES = new RecipeMap<>(new HashSet<>(100), "gt.recipe.chemicalreactor", "Chemical Reactor", null, "basicmachines/ChemicalReactor", 0, 2, 0, 1, 0, 1, 0, 1, 1, "", 1, "", true, true, new RecipeBuilder.DefaultRecipeBuilder() {
		@Override
		protected RecipeBuilder.DefaultRecipeBuilder validate() {
			Validate.isTrue((inputs.isEmpty() && fluidInputs.isEmpty()) || (outputs.isEmpty() && fluidOutputs.isEmpty()));
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
	 *				.outputs(GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.HydratedCoal, 1))
	 *				.duration(16)
	 *				.EUt(64)
	 *				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.UniversalDistillationRecipeBuilder> DISTILLATION_RECIPES = new RecipeMap<>(new HashSet<>(50), "gt.recipe.distillationtower", "Distillation Tower", null, "basicmachines/Default", 0, 0, 0, 1, 1, 1, 1, 5, 1, "", 1, "", true, true, new RecipeBuilder.UniversalDistillationRecipeBuilder().nonOptimized());

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
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> CRACKING_RECIPES = new RecipeMap<>(new HashSet<>(50), "gt.recipe.craker", "Oil Cracker", null, "basicmachines/Default", 0, 0, 0, 0, 1, 2, 1, 2, 1, "", 1, "", true, true, new RecipeBuilder.DefaultRecipeBuilder() {
		@Override
		public void buildAndRegister() {
			super.buildAndRegister();
			FluidStack fluidInput = fluidInputs.get(0);
			FluidStack fluidOutput = fluidInputs.get(0);
			recipeMap.addRecipe(this.copy().fluidInputs(fluidInput, ModHandler.getSteam(fluidInput.amount)).fluidOutputs(fluidOutput, Materials.Hydrogen.getGas(fluidInput.amount)).build());
			recipeMap.addRecipe(this.copy().fluidInputs(fluidInput, Materials.Hydrogen.getGas(fluidInput.amount)).fluidOutputs(new FluidStack(fluidOutput.getFluid(), (int) (fluidOutput.amount * 1.3))).build());
		}
	}.nonOptimized());

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
	public static final RecipeMap<Recipe, RecipeBuilder.IntCircuitRecipeBuilder> PYROLYSE_RECIPES = new RecipeMap<>(new HashSet<>(50), "gt.recipe.pyro", "Pyrolyse Oven", null, "basicmachines/Default", 2, 2, 0, 1, 0, 1, 1, 1, 1, "", 1, "", true, true, new RecipeBuilder.IntCircuitRecipeBuilder().nonOptimized());

	/**
	 * Example:
	 * <pre>
	 * 		RecipeMap.WIREMILL_RECIPES.recipeBuilder()
	 *				.inputs(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 1L))
	 *				.outputs(GT_ModHandler.getIC2Item("ironCableItem", 6L))
	 *				.duration(200)
	 *				.EUt(2)
	 *				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> WIREMILL_RECIPES = new RecipeMap<>(new HashSet<>(50), "gt.recipe.wiremill", "Wiremill", null, "basicmachines/Wiremill", 1, 1, 1, 1, 0, 0, 0, 0, 1, "", 1, "", true, true, new RecipeBuilder.DefaultRecipeBuilder());

	/**
	 * Example:
	 * <pre>
	 *		RecipeMap.BENDER_RECIPES.recipeBuilder()
	 *				.inputs(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 12L))
	 *				.outputs(ItemList.Cell_Empty.get(6))
	 *				.duration(1200)
	 *				.EUt(8)
	 *				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.IntCircuitRecipeBuilder> BENDER_RECIPES = new RecipeMap<>(new HashSet<>(400), "gt.recipe.metalbender", "Metal Bender", null, "basicmachines/Bender", 2, 2, 1, 1, 0, 0, 0, 0, 1, "", 1, "", true, true, new RecipeBuilder.IntCircuitRecipeBuilder() {
		@Override
		protected void finalizeAndValidate() {
			this.circuitMeta(this.inputs.get(0).stackSize);
			super.finalizeAndValidate();
		}
	});

	/**
	 * Example:
	 * <pre>
	 *	 	RecipeMap.ALLOY_SMELTER_RECIPES.recipeBuilder()
	 *				.inputs(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Redstone, 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Iron, 1L))
	 *				.outputs(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.ConductiveIron, 1L))
	 *				.duration(400)
	 *				.EUt(24)
	 *				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.NotConsumableInputRecipeBuilder> ALLOY_SMELTER_RECIPES = new RecipeMap<>(new HashSet<>(3000), "gt.recipe.alloysmelter", "Alloy Smelter", null, "basicmachines/AlloySmelter", 1, 2, 1, 1, 0, 0, 0, 0, 1, "", 1, "", true, true, new RecipeBuilder.NotConsumableInputRecipeBuilder() {
		@Override
		protected RecipeBuilder.NotConsumableInputRecipeBuilder validate() {
			ItemStack input = inputs.get(0);
//			Validate.isTrue(Materials.Graphite.contains(input));
			Validate.isTrue((inputs.size() == 1) && (OrePrefixes.ingot.contains(input) || OrePrefixes.dust.contains(input) || OrePrefixes.gem.contains(input)));
			super.validate();
			return getThis();
		}
	});

	/**
	 * Example:
	 * <pre>
	 *       RecipeMap.CANNER_RECIPES.recipeBuilder()
	 * 				.inputs(new ItemStack(Items.cake, 1, GT_Values.W), ItemList.IC2_Food_Can_Empty.get(12))
	 * 				.outputs(ItemList.IC2_Food_Can_Filled.get(12))
	 * 				.duration(600)
	 * 				.EUt(1)
	 * 				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> CANNER_RECIPES = new RecipeMap<>(new HashSet<>(300), "gt.recipe.canner", "Canning Machine", null, "basicmachines/Canner", 1, 2, 1, 2, 0, 0, 0, 0, 1, "", 1, "", true, true, new RecipeBuilder.DefaultRecipeBuilder());

	/**
	 * Example:
	 * <pre>
	 *	     RecipeMap.LATHE_RECIPES.recipeBuilder()
	 * 				.inputs(GT_OreDictUnificator.get(OrePrefixes.gemExquisite, Materials.Ruby, 1L))
	 * 				.outputs(GT_OreDictUnificator.get(OrePrefixes.lens, Materials.Ruby, 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Ruby, 2L))
	 * 				.duration(Materials.Ruby.getMass())
	 * 				.EUt(24)
	 * 				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> LATHE_RECIPES = new RecipeMap<>(new HashSet<>(400), "gt.recipe.lathe", "Lathe", null, "basicmachines/Lathe", 1, 1, 1, 2, 0, 0, 0, 0, 1, "", 1, "", true, true, new RecipeBuilder.DefaultRecipeBuilder());

	/**
	 * Example:
	 * <pre>
	 *      RecipeMap.CUTTER_RECIPES.recipeBuilder()
	 *				.inputs(new ItemStack(Blocks.LOG))
	 *				.fluidInputs(Materials.Lubricant.getFluid(1))
	 *				.outputs(new ItemStack(Blocks.PLANKS), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Wood, 1L))
	 *				.duration(200)
	 *				.EUt(8)
	 *				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> CUTTER_RECIPES = new RecipeMap<>(new HashSet<>(200), "gt.recipe.cuttingsaw", "Cutting Saw", null, "basicmachines/Cutter", 1, 1, 0, 0, 1, 1, 1, 2, 1, "", 1, "", true, true, new RecipeBuilder.DefaultRecipeBuilder() {
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
	public static final RecipeMap<Recipe, RecipeBuilder.NotConsumableInputRecipeBuilder> SLICER_RECIPES = new RecipeMap<>(new HashSet<>(200), "gt.recipe.slicer", "Slicer", null, "basicmachines/Slicer", 2, 2, 1, 1, 0, 0, 0, 0, 1, "", 1, "", true, true, new RecipeBuilder.NotConsumableInputRecipeBuilder());

	/**
	 * Example:
	 * <pre>
	 *      RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
	 *				.inputs(new ItemStack(Items.IRON_INGOT))
	 *				.notConsumable(ItemList.Shape_Extruder_Rod)
	 *				.outputs(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Iron.smeltInto, 2))
	 *				.duration(64)
	 *				.EUt(8)
	 *				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.NotConsumableInputRecipeBuilder> EXTRUDER_RECIPES = new RecipeMap<>(new HashSet<>(1000), "gt.recipe.extruder", "Extruder", null, "basicmachines/Extruder", 2, 2, 1, 1, 0, 0, 0, 0, 1, "", 1, "", true, true, new RecipeBuilder.NotConsumableInputRecipeBuilder());

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
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> HAMMER_RECIPES = new RecipeMap<>(new HashSet<>(200), "gt.recipe.hammer", "Hammer", null, "basicmachines/Hammer", 1, 1, 1, 1, 0, 0, 0, 0, 1, "", 1, "", true, true, new RecipeBuilder.DefaultRecipeBuilder());

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
	public static final RecipeMap<Recipe.AmplifierRecipe, RecipeBuilder.AmplifierRecipeBuilder> AMPLIFIERS = new RecipeMap<>(new HashSet<>(10), "gt.recipe.uuamplifier", "UU Amplifier", null, "basicmachines/Amplifabricator", 1, 1, 0, 0, 0, 0, 1, 1, 1, "", 1, "", true, true, new RecipeBuilder.AmplifierRecipeBuilder().EUt(32));

	/// TODO Map<FluidStack, Int>
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> DIESEL_FUELS = new RecipeMap<>(new HashSet<>(10), "gt.recipe.dieselgeneratorfuel", "Diesel Generator Fuel", null, "basicmachines/Default", 1, 1, 0, 4, 0, 0, 0, 0, 1, "Fuel Value: ", 1000, " EU", true, true, new RecipeBuilder.DefaultRecipeBuilder());
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> TURBINE_FUELS = new RecipeMap<>(new HashSet<>(10), "gt.recipe.gasturbinefuel", "Gas Turbine Fuel", null, "basicmachines/Default", 0, 1, 0, 4, 0, 0, 0, 0, 1, "Fuel Value: ", 1000, " EU", true, true, new RecipeBuilder.DefaultRecipeBuilder());
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> HOT_FUELS = new RecipeMap<>(new HashSet<>(10), "gt.recipe.thermalgeneratorfuel", "Thermal Generator Fuel", null, "basicmachines/Default", 0, 1, 0, 4, 0, 0, 0, 0, 1, "Fuel Value: ", 1000, " EU", true, false, new RecipeBuilder.DefaultRecipeBuilder());
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> DENSE_LIQUID_FUELS = new RecipeMap<>(new HashSet<>(10), "gt.recipe.semifluidboilerfuels", "Semifluid Boiler Fuels", null, "basicmachines/Default", 0, 1, 0, 4, 0, 0, 0, 0, 1, "Fuel Value: ", 1000, " EU", true, true, new RecipeBuilder.DefaultRecipeBuilder());
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> PLASMA_FUELS = new RecipeMap<>(new HashSet<>(10), "gt.recipe.plasmageneratorfuels", "Plasma generator Fuels", null, "basicmachines/Default", 0, 1, 0, 4, 0, 0, 0, 0, 1, "Fuel Value: ", 1000, " EU", true, true, new RecipeBuilder.DefaultRecipeBuilder());
	////

	/**
	 * Use {@link RecipeBuilder#EUt(int)} to set EU/t produced
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> MAGIC_FUELS = new RecipeMap<>(new HashSet<>(10), "gt.recipe.magicfuels", "Magic Fuels", null, "basicmachines/Default", 1, 1, 0, 1, 0, 0, 0, 0, 1, "Fuel Value: ", 1000, " EU", true, true, new RecipeBuilder.DefaultRecipeBuilder() {
		@Override
		protected void finalizeAndValidate() {
			this.EUt = -EUt;
			super.finalizeAndValidate();
		}
	});

	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> SMALL_NAQUADAH_REACTOR_FUELS = new RecipeMap<>(new HashSet<>(10), "gt.recipe.smallnaquadahreactor", "Small Naquadah Reactor", null, "basicmachines/Default", 1, 1, 1, 1, 0, 0, 0, 0, 1, "Fuel Value: ", 1000, " EU", true, true, new RecipeBuilder.DefaultRecipeBuilder());
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> LARGE_NAQUADAH_REACTOR_FUELS = new RecipeMap<>(new HashSet<>(10), "gt.recipe.largenaquadahreactor", "Large Naquadah Reactor", null, "basicmachines/Default", 1, 1, 1, 1, 0, 0, 0, 0, 1, "Fuel Value: ", 1000, " EU", true, true, new RecipeBuilder.DefaultRecipeBuilder());
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> FLUID_NAQUADAH_REACTOR_FUELS = new RecipeMap<>(new HashSet<>(10), "gt.recipe.fluidnaquadahreactor", "Fluid Naquadah Reactor", null, "basicmachines/Default", 1, 1, 1, 1, 0, 0, 0, 0, 1, "Fuel Value: ", 1000, " EU", true, true, new RecipeBuilder.DefaultRecipeBuilder());

	/**
	 * Example:
	 * <pre>
	 * 			new RecipeBuilder.AssemblyLineRecipeBuilder()
	 *					.researchItem(ItemList.Sensor_ZPM.get(1))
	 *					.researchTime(288000)
	 *					.inputs(GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Neutronium, 1L),
	 *								ItemList.Sensor_ZPM.get(1),
	 *								ItemList.Sensor_LuV.get(2),
	 *								ItemList.Sensor_IV.get(4),
	 *								GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 7L),
	 *								GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Osmiridium, 64L),
	 *								GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Osmiridium, 64L),
	 *								GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Osmiridium, 64L),
	 *								GT_OreDictUnificator.get(OrePrefixes.cableGt04, Materials.NiobiumTitanium, 7L))
	 *					.fluidInputs(Materials.SolderingAlloy.getMolten(576))
	 *					.output(ItemList.Sensor_UV.get(1))
	 *					.duration(600)
	 *					.EUt(100000)
	 *					.buildAndRegister();
	 * </pre>
	 */
	public static final ArrayList<Recipe.AssemblyLineRecipe> ASSEMBLYLINE_RECIPES = new ArrayList<>();

	/**
	 * HashMap of Recipes based on their Item inputs
	 */
	protected final Map<SimpleItemStack, Collection<T>> recipeItemMap = new HashMap<>();
	/**
	 * HashMap of Recipes based on their Fluid inputs
	 */
	protected final Map<Fluid, Collection<T>> recipeFluidMap = new HashMap<>();
	/**
	 * The List of all Recipes
	 */
	protected final Collection<T> recipeList;
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
	public RecipeMap(Collection<T> recipeList, String unlocalizedName, String localName, String JEIName, String JEIGUIPath,
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

	protected T addRecipe(T recipe) {
		recipeList.add(recipe);

		for (ItemStack stack : recipe.getInputs()) {
			recipeItemMap.computeIfAbsent(new SimpleItemStack(stack), k -> new HashSet<>(1)).add(recipe);
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
		return stack != null && (recipeItemMap.containsKey(new SimpleItemStack(stack)) || recipeItemMap.containsKey(new SimpleItemStack(GT_Utility.copyMetaData(W, stack))));
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
		return findRecipe(tileEntity, null, notUnificated, voltage, fluids, inputs);
	}

	/**
	 * Finds a Recipe matching the Fluid and ItemStack Inputs.
	 *
	 * @param tileEntity    an Object representing the current coordinates of the executing Block/Entity/Whatever. This may be null, especially during Startup.
	 * @param inputRecipe        in case this is != null it will try to use this Recipe first when looking things up.
	 * @param notUnificated if this is true the Recipe searcher will unificate the ItemStack Inputs
	 * @param voltage       Voltage of the Machine or Long.MAX_VALUE if it has no Voltage
	 * @param fluidInputs   the Fluid Inputs
	 * @param inputs        the Item Inputs
	 * @return the Recipe it has found or null for no matching Recipe
	 */
	public T findRecipe(@Nullable IHasWorldObjectAndCoords tileEntity, @Nullable T inputRecipe, boolean notUnificated, long voltage, FluidStack[] fluidInputs, ItemStack[] inputs) {
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
		if (inputRecipe != null) {
			if (inputRecipe.canBeBuffered() && inputRecipe.isRecipeInputEqual(false, true, fluidInputs, inputs)) {
				return voltage * amperage >= inputRecipe.getEUt() ? inputRecipe : null;
			}
		}

		// Now look for the Recipes inside the Item HashMaps, but only when the Recipes usually have Items.
		if (maxInputs > 0 && inputs != null) {
			for (ItemStack stack : inputs) {
				if (stack != null) {
					Collection<T> recipes = recipeItemMap.get(new SimpleItemStack(stack));
					if (recipes != null) {
						for (T tmpRecipe : recipes) {
							if (tmpRecipe.isRecipeInputEqual(false, true, fluidInputs, inputs)) {
								return voltage * amperage >= tmpRecipe.getEUt() ? tmpRecipe : null;
							}
						}
					}
					recipes = recipeItemMap.get(new SimpleItemStack(GT_Utility.copyMetaData(W, stack)));
					if (recipes != null) {
						for (T tmpRecipe : recipes) {
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
					Collection<T> recipes = recipeFluidMap.get(fluid.getFluid());
					if (recipes != null) {
						for (T tmpRecipe : recipes) {
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

	public static class FakeRecipeMap<T extends Recipe, R extends RecipeBuilder<T, R>> extends RecipeMap<T, R> {

		public FakeRecipeMap(Collection<T> recipeList, String unlocalizedName, String localName, String JEIName, String JEIGUIPath, int minInputs, int maxInputs, int minOutputs, int maxOutputs, int minFluidInputs, int maxFluidInputs, int minFluidOutputs, int maxFluidOutputs, int amperage, String JEISpecialValuePre, int JEISpecialValueMultiplier, String JEISpecialValuePost, boolean showVoltageAmperageInJEI, boolean JEIAllowed, R defaultRecipe) {
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
		public T findRecipe(IHasWorldObjectAndCoords tileEntity, Recipe inputRecipe, boolean notUnificated, long voltage, FluidStack[] fluidInputs, ItemStack[] inputs) {
			throw new UnsupportedOperationException("This should not get called on fake recipe map");
		}
	}

	/**
	 * Abstract Class for general Recipe Handling of non GT Recipes
	 */
	public static abstract class RecipeMapNonGTRecipes<T extends Recipe, R extends RecipeBuilder<T, R>> extends RecipeMap<T, R> {

		public RecipeMapNonGTRecipes(Collection<T> recipeList, String unlocalizedName, String localName, String JEIName, String JEIGUIPath, int minInputs, int maxInputs, int minOutputs, int maxOutputs, int minFluidInputs, int maxFluidInputs, int minFluidOutputs, int maxFluidOutputs, int amperage, String JEISpecialValuePre, int JEISpecialValueMultiplier, String JEISpecialValuePost, boolean showVoltageAmperageInJEI, boolean JEIAllowed, R defaultRecipe) {
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
		protected T addRecipe(T recipe) {
			return null;
		}
	}

	/**
	 * Special Class for Furnace Recipe handling.
	 */
	public static class RecipeMapFurnace extends RecipeMapNonGTRecipes<Recipe, RecipeBuilder.DefaultRecipeBuilder> {

		public RecipeMapFurnace(Collection<Recipe> recipeList, String unlocalizedName, String localName, String JEIName, String JEIGUIPath, int minInputs, int maxInputs, int minOutputs, int maxOutputs, int minFluidInputs, int maxFluidInputs, int minFluidOutputs, int maxFluidOutputs, int amperage, String JEISpecialValuePre, int JEISpecialValueMultiplier, String JEISpecialValuePost, boolean showVoltageAmperageInJEI, boolean JEIAllowed, RecipeBuilder.DefaultRecipeBuilder defaultRecipe) {
			super(recipeList, unlocalizedName, localName, JEIName, JEIGUIPath, minInputs, maxInputs, minOutputs, maxOutputs, minFluidInputs, maxFluidInputs, minFluidOutputs, maxFluidOutputs, amperage, JEISpecialValuePre, JEISpecialValueMultiplier, JEISpecialValuePost, showVoltageAmperageInJEI, JEIAllowed, defaultRecipe);
		}

		@Override
		public Recipe findRecipe(IHasWorldObjectAndCoords tileEntity, Recipe inputRecipe, boolean notUnificated, long voltage, FluidStack[] fluidInputs, ItemStack[] inputs) {
			if (inputs == null || inputs.length <= 0 || inputs[0] == null) return null;
			if (inputRecipe != null && inputRecipe.isRecipeInputEqual(false, true, fluidInputs, inputs)) return inputRecipe;
			ItemStack output = ModHandler.getSmeltingOutput(inputs[0], false, null);
			return output == null ? null : this.recipeBuilder()
					.nonOptimized()
					.inputs(GT_Utility.copyAmount(1, inputs[0]))
					.outputs(output)
					.duration(128)
					.EUt(4)
					.build();
		}

		@Override
		public boolean containsInput(ItemStack stack) {
			return ModHandler.getSmeltingOutput(stack, false, null) != null;
		}
	}

	/**
	 * Special Class for Microwave Recipe handling.
	 */
	public static class RecipeMapMicrowave extends RecipeMapNonGTRecipes<Recipe, RecipeBuilder.DefaultRecipeBuilder> {

		public RecipeMapMicrowave(Collection<Recipe> recipeList, String unlocalizedName, String localName, String JEIName, String JEIGUIPath, int minInputs, int maxInputs, int minOutputs, int maxOutputs, int minFluidInputs, int maxFluidInputs, int minFluidOutputs, int maxFluidOutputs, int amperage, String JEISpecialValuePre, int JEISpecialValueMultiplier, String JEISpecialValuePost, boolean showVoltageAmperageInJEI, boolean JEIAllowed, RecipeBuilder.DefaultRecipeBuilder defaultRecipe) {
			super(recipeList, unlocalizedName, localName, JEIName, JEIGUIPath, minInputs, maxInputs, minOutputs, maxOutputs, minFluidInputs, maxFluidInputs, minFluidOutputs, maxFluidOutputs, amperage, JEISpecialValuePre, JEISpecialValueMultiplier, JEISpecialValuePost, showVoltageAmperageInJEI, JEIAllowed, defaultRecipe);
		}

		@Override
		public Recipe findRecipe(IHasWorldObjectAndCoords tileEntity, Recipe inputRecipe, boolean notUnificated, long voltage, FluidStack[] fluidInputs, ItemStack[] inputs) {
			if (inputs == null || inputs.length <= 0 || inputs[0] == null) return null;
			if (inputRecipe != null && inputRecipe.isRecipeInputEqual(false, true, fluidInputs, inputs)) return inputRecipe;
			ItemStack output = ModHandler.getSmeltingOutput(inputs[0], false, null);

			if (GT_Utility.areStacksEqual(inputs[0], new ItemStack(Items.BOOK, 1, W))) {
				return this.recipeBuilder()
						.nonOptimized()
						.inputs(GT_Utility.copyAmount(1, inputs[0]))
						.outputs(GT_Utility.getWrittenBook("Manual_Microwave", ItemList.Book_Written_03.get(1)))
						.duration(32)
						.EUt(4)
						.build();
			}

			// Check Container Item of Input since it is around the Input, then the Input itself, then Container Item of Output and last check the Output itself
			for (ItemStack stack : new ItemStack[]{GT_Utility.getContainerItem(inputs[0], true), inputs[0], GT_Utility.getContainerItem(output, true), output}) {
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
							if (data.mMaterial.mMaterial instanceof MetalMaterial || data.mMaterial.mMaterial.contains(Material.MatFlags.EXPLOSIVE)) {
								if (tileEntity instanceof IGregTechTileEntity)
									((IGregTechTileEntity) tileEntity).doExplosion(voltage * 4);
								return null;
							}
							if (data.mMaterial.mMaterial.contains(Material.MatFlags.FLAMMABLE)) {
								if (tileEntity instanceof IGregTechTileEntity)
									((IGregTechTileEntity) tileEntity).setOnFire();
								return null;
							}
						}
						for (MaterialStack material : data.mByProducts)
							if (material != null) {
								if (material.mMaterial instanceof MetalMaterial || material.mMaterial.contains(Material.MatFlags.EXPLOSIVE)) {
									if (tileEntity instanceof IGregTechTileEntity)
										((IGregTechTileEntity) tileEntity).doExplosion(voltage * 4);
									return null;
								}
								if (material.mMaterial.contains(Material.MatFlags.FLAMMABLE)) {
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
			}

			return output == null ? null : new RecipeBuilder.DefaultRecipeBuilder(this.defaultRecipe)
					.nonOptimized()
					.inputs(GT_Utility.copyAmount(1, inputs[0]))
					.outputs(output)
					.duration(32)
					.EUt(4)
					.build();
		}

		@Override
		public boolean containsInput(ItemStack stack) {
			return ModHandler.getSmeltingOutput(stack, false, null) != null;
		}
	}

	/**
	 * Special Class for Unboxinator handling.
	 */
	public static class RecipeMapUnboxinator extends RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> {

		public RecipeMapUnboxinator(Collection<Recipe> recipeList, String unlocalizedName, String localName, String JEIName, String JEIGUIPath, int minInputs, int maxInputs, int minOutputs, int maxOutputs, int minFluidInputs, int maxFluidInputs, int minFluidOutputs, int maxFluidOutputs, int amperage, String JEISpecialValuePre, int JEISpecialValueMultiplier, String JEISpecialValuePost, boolean showVoltageAmperageInJEI, boolean JEIAllowed, RecipeBuilder.DefaultRecipeBuilder defaultRecipe) {
			super(recipeList, unlocalizedName, localName, JEIName, JEIGUIPath, minInputs, maxInputs, minOutputs, maxOutputs, minFluidInputs, maxFluidInputs, minFluidOutputs, maxFluidOutputs, amperage, JEISpecialValuePre, JEISpecialValueMultiplier, JEISpecialValuePost, showVoltageAmperageInJEI, JEIAllowed, defaultRecipe);
		}

		@Override
		public Recipe findRecipe(IHasWorldObjectAndCoords tileEntity, Recipe inputRecipe, boolean notUnificated, long voltage, FluidStack[] fluidInputs, ItemStack[] inputs) {
			if (inputs == null || inputs.length <= 0 || !ItemList.IC2_Scrapbox.isStackEqual(inputs[0], false, true))
				return super.findRecipe(tileEntity, inputRecipe, notUnificated, voltage, fluidInputs, inputs);
			ItemStack output = ModHandler.getRandomScrapboxDrop();
			if (output == null) {
				return super.findRecipe(tileEntity, inputRecipe, notUnificated, voltage, fluidInputs, inputs);
			}
			return this.recipeBuilder()
					.nonOptimized()
					.cannotBeBuffered() // It is not allowed to be buffered due to the random Output
					.needsEmptyOutput() // Due to its randomness it is not good if there are Items in the Output Slot, because those Items could manipulate the outcome.
					.inputs(ItemList.IC2_Scrapbox.get(1))
					.outputs(output)
					.duration(16)
					.EUt(1)
					.build();
		}

		@Override
		public boolean containsInput(ItemStack stack) {
			return ItemList.IC2_Scrapbox.isStackEqual(stack, false, true) || super.containsInput(stack);
		}
	}

	/**
	 * Special Class for Fluid Canner handling.
	 */
	public static class RecipeMapFluidCanner extends RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> {

		public RecipeMapFluidCanner(Collection<Recipe> recipeList, String unlocalizedName, String localName, String JEIName, String JEIGUIPath, int minInputs, int maxInputs, int minOutputs, int maxOutputs, int minFluidInputs, int maxFluidInputs, int minFluidOutputs, int maxFluidOutputs, int amperage, String JEISpecialValuePre, int JEISpecialValueMultiplier, String JEISpecialValuePost, boolean showVoltageAmperageInJEI, boolean JEIAllowed, RecipeBuilder.DefaultRecipeBuilder defaultRecipe) {
			super(recipeList, unlocalizedName, localName, JEIName, JEIGUIPath, minInputs, maxInputs, minOutputs, maxOutputs, minFluidInputs, maxFluidInputs, minFluidOutputs, maxFluidOutputs, amperage, JEISpecialValuePre, JEISpecialValueMultiplier, JEISpecialValuePost, showVoltageAmperageInJEI, JEIAllowed, defaultRecipe);
		}

		@Override
		public Recipe findRecipe(IHasWorldObjectAndCoords tileEntity, Recipe inputRecipe, boolean notUnificated, long voltage, FluidStack[] fluidInputs, ItemStack[] inputs) {
			Recipe recipe = super.findRecipe(tileEntity, inputRecipe, notUnificated, voltage, fluidInputs, inputs);
			if (inputs == null || inputs.length <= 0 || inputs[0] == null || recipe != null || !GregTech_API.sPostloadFinished)
				return recipe;
			if (fluidInputs != null && fluidInputs.length > 0 && fluidInputs[0] != null) {
				ItemStack output = GT_Utility.fillFluidContainer(fluidInputs[0], inputs[0], false, true);
				FluidStack fluid = GT_Utility.getFluidForFilledItem(output, true);
				if (fluid != null) {
					recipe = this.recipeBuilder()
							.cannotBeBuffered()
							.nonOptimized()
							.inputs(GT_Utility.copyAmount(1, inputs[0]))
							.outputs(output)
							.fluidInputs(fluidInputs)
							.duration(Math.max(fluid.amount / 64, 16))
							.EUt(1)
							.build();
				}
			}
			if (recipe == null) {
				FluidStack fluid = GT_Utility.getFluidForFilledItem(inputs[0], true);
				if (fluid != null) {
					recipe = this.recipeBuilder()
							.cannotBeBuffered()
							.nonOptimized()
							.inputs(GT_Utility.copyAmount(1, inputs[0]))
							.outputs(GT_Utility.getContainerItem(inputs[0], true))
							.fluidOutputs(fluidInputs)
							.duration(Math.max(fluid.amount / 64, 16))
							.EUt(1)
							.build();
				}
			}
			return recipe;
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
	public static class RecipeMapRecycler extends RecipeMapNonGTRecipes<Recipe, RecipeBuilder.DefaultRecipeBuilder> {

		public RecipeMapRecycler(Collection<Recipe> recipeList, String unlocalizedName, String localName, String JEIName, String JEIGUIPath, int minInputs, int maxInputs, int minOutputs, int maxOutputs, int minFluidInputs, int maxFluidInputs, int minFluidOutputs, int maxFluidOutputs, int amperage, String JEISpecialValuePre, int JEISpecialValueMultiplier, String JEISpecialValuePost, boolean showVoltageAmperageInJEI, boolean JEIAllowed, RecipeBuilder.DefaultRecipeBuilder defaultRecipe) {
			super(recipeList, unlocalizedName, localName, JEIName, JEIGUIPath, minInputs, maxInputs, minOutputs, maxOutputs, minFluidInputs, maxFluidInputs, minFluidOutputs, maxFluidOutputs, amperage, JEISpecialValuePre, JEISpecialValueMultiplier, JEISpecialValuePost, showVoltageAmperageInJEI, JEIAllowed, defaultRecipe);
		}

		@Override
		public Recipe findRecipe(IHasWorldObjectAndCoords tileEntity, Recipe inputRecipe, boolean notUnificated, long voltage, FluidStack[] fluidInputs, ItemStack[] inputs) {
			if (inputs == null || inputs.length <= 0 || inputs[0] == null) return null;
			if (inputRecipe != null && inputRecipe.isRecipeInputEqual(false, true, fluidInputs, inputs)) return inputRecipe;

			RecipeBuilder builder = this.recipeBuilder()
					.nonOptimized()
					.inputs(GT_Utility.copyAmount(1, inputs[0]))
					.duration(45)
					.EUt(1);

			if (ModHandler.getRecyclerOutput(GT_Utility.copyAmount(64, inputs[0]), 0) != null) {
				builder.chancedOutput(ItemList.IC2_Scrap.get(1), 1250);
			}

			return builder.build();
		}

		@Override
		public boolean containsInput(ItemStack stack) {
			return ModHandler.getRecyclerOutput(GT_Utility.copyAmount(64, stack), 0) != null;
		}
	}

	/**
	 * Special Class for Compressor Recipe handling.
	 */
	public static class RecipeMapCompressor extends RecipeMapNonGTRecipes<Recipe, RecipeBuilder.DefaultRecipeBuilder> {

		public RecipeMapCompressor(Collection<Recipe> recipeList, String unlocalizedName, String localName, String JEIName, String JEIGUIPath, int minInputs, int maxInputs, int minOutputs, int maxOutputs, int minFluidInputs, int maxFluidInputs, int minFluidOutputs, int maxFluidOutputs, int amperage, String JEISpecialValuePre, int JEISpecialValueMultiplier, String JEISpecialValuePost, boolean showVoltageAmperageInJEI, boolean JEIAllowed, RecipeBuilder.DefaultRecipeBuilder defaultRecipe) {
			super(recipeList, unlocalizedName, localName, JEIName, JEIGUIPath, minInputs, maxInputs, minOutputs, maxOutputs, minFluidInputs, maxFluidInputs, minFluidOutputs, maxFluidOutputs, amperage, JEISpecialValuePre, JEISpecialValueMultiplier, JEISpecialValuePost, showVoltageAmperageInJEI, JEIAllowed, defaultRecipe);
		}

		@Override
		public Recipe findRecipe(IHasWorldObjectAndCoords tileEntity, Recipe inputRecipe, boolean notUnificated, long voltage, FluidStack[] fluidInputs, ItemStack[] inputs) {
			if (inputs == null || inputs.length <= 0 || inputs[0] == null) return null;
			if (inputRecipe != null && inputRecipe.isRecipeInputEqual(false, true, fluidInputs, inputs)) return inputRecipe;
			ItemStack comparedInput = GT_Utility.copy(inputs[0]);
			ItemStack[] outputItems = ModHandler.getMachineOutput(comparedInput, ic2.api.recipe.Recipes.compressor.getRecipes(), true, new NBTTagCompound(), null, null, null);
			if (GT_Utility.arrayContainsNonNull(outputItems)) {
				return this.recipeBuilder()
						.nonOptimized()
						.inputs(GT_Utility.copyAmount(inputs[0].stackSize - comparedInput.stackSize, inputs[0]))
						.outputs(outputItems)
						.duration(400)
						.EUt(2)
						.build();
			}
			return null;
		}

		@Override
		public boolean containsInput(ItemStack stack) {
			return GT_Utility.arrayContainsNonNull(ModHandler.getMachineOutput(GT_Utility.copyAmount(64, stack), ic2.api.recipe.Recipes.compressor.getRecipes(), false, new NBTTagCompound(), null, null, null));
		}
	}

	/**
	 * Special Class for Extractor Recipe handling.
	 */
	public static class RecipeMapExtractor extends RecipeMapNonGTRecipes<Recipe, RecipeBuilder.DefaultRecipeBuilder> {

		public RecipeMapExtractor(Collection<Recipe> recipeList, String unlocalizedName, String localName, String JEIName, String JEIGUIPath, int minInputs, int maxInputs, int minOutputs, int maxOutputs, int minFluidInputs, int maxFluidInputs, int minFluidOutputs, int maxFluidOutputs, int amperage, String JEISpecialValuePre, int JEISpecialValueMultiplier, String JEISpecialValuePost, boolean showVoltageAmperageInJEI, boolean JEIAllowed, RecipeBuilder.DefaultRecipeBuilder defaultRecipe) {
			super(recipeList, unlocalizedName, localName, JEIName, JEIGUIPath, minInputs, maxInputs, minOutputs, maxOutputs, minFluidInputs, maxFluidInputs, minFluidOutputs, maxFluidOutputs, amperage, JEISpecialValuePre, JEISpecialValueMultiplier, JEISpecialValuePost, showVoltageAmperageInJEI, JEIAllowed, defaultRecipe);
		}

		@Override
		public Recipe findRecipe(IHasWorldObjectAndCoords tileEntity, Recipe inputRecipe, boolean notUnificated, long voltage, FluidStack[] fluidInputs, ItemStack[] inputs) {
			if (inputs == null || inputs.length <= 0 || inputs[0] == null) return null;
			if (inputRecipe != null && inputRecipe.isRecipeInputEqual(false, true, fluidInputs, inputs)) return inputRecipe;
			ItemStack comparedInput = GT_Utility.copy(inputs[0]);
			ItemStack[] outputItems = ModHandler.getMachineOutput(comparedInput, ic2.api.recipe.Recipes.extractor.getRecipes(), true, new NBTTagCompound(), null, null, null);

			if (GT_Utility.arrayContainsNonNull(outputItems)) {
				return this.recipeBuilder()
						.nonOptimized()
						.inputs(GT_Utility.copyAmount(inputs[0].stackSize - comparedInput.stackSize, inputs[0]))
						.outputs(outputItems)
						.duration(400)
						.EUt(2)
						.build();
			}
			return null;
		}

		@Override
		public boolean containsInput(ItemStack stack) {
			return GT_Utility.arrayContainsNonNull(ModHandler.getMachineOutput(GT_Utility.copyAmount(64, stack), ic2.api.recipe.Recipes.extractor.getRecipes(), false, new NBTTagCompound(), null, null, null));
		}
	}

	/**
	 * Special Class for Thermal Centrifuge Recipe handling.
	 */
	public static class RecipeMapThermalCentrifuge extends RecipeMapNonGTRecipes<Recipe, RecipeBuilder.DefaultRecipeBuilder> {

		public RecipeMapThermalCentrifuge(Collection<Recipe> recipeList, String unlocalizedName, String localName, String JEIName, String JEIGUIPath, int minInputs, int maxInputs, int minOutputs, int maxOutputs, int minFluidInputs, int maxFluidInputs, int minFluidOutputs, int maxFluidOutputs, int amperage, String JEISpecialValuePre, int JEISpecialValueMultiplier, String JEISpecialValuePost, boolean showVoltageAmperageInJEI, boolean JEIAllowed, RecipeBuilder.DefaultRecipeBuilder defaultRecipe) {
			super(recipeList, unlocalizedName, localName, JEIName, JEIGUIPath, minInputs, maxInputs, minOutputs, maxOutputs, minFluidInputs, maxFluidInputs, minFluidOutputs, maxFluidOutputs, amperage, JEISpecialValuePre, JEISpecialValueMultiplier, JEISpecialValuePost, showVoltageAmperageInJEI, JEIAllowed, defaultRecipe);
		}

		@Override
		public Recipe findRecipe(IHasWorldObjectAndCoords tileEntity, Recipe inputRecipe, boolean notUnificated, long voltage, FluidStack[] fluidInputs, ItemStack[] inputs) {
			if (inputs == null || inputs.length <= 0 || inputs[0] == null) return null;
			if (inputRecipe != null && inputRecipe.isRecipeInputEqual(false, true, fluidInputs, inputs)) return inputRecipe;
			ItemStack comparedInput = GT_Utility.copy(inputs[0]);
			ItemStack[] outputItems = ModHandler.getMachineOutput(comparedInput, ic2.api.recipe.Recipes.centrifuge.getRecipes(), true, new NBTTagCompound(), null, null, null);

			if (GT_Utility.arrayContainsNonNull(outputItems)) {
				return this.recipeBuilder()
						.nonOptimized()
						.inputs(GT_Utility.copyAmount(inputs[0].stackSize - comparedInput.stackSize, inputs[0]))
						.outputs(outputItems)
						.duration(400)
						.EUt(48)
						.build();
			}
			return null;
		}

		@Override
		public boolean containsInput(ItemStack stack) {
			return GT_Utility.arrayContainsNonNull(ModHandler.getMachineOutput(GT_Utility.copyAmount(64, stack), ic2.api.recipe.Recipes.centrifuge.getRecipes(), false, new NBTTagCompound(), null, null, null));
		}
	}

	/**
	 * Special Class for Ore Washer Recipe handling.
	 */
	public static class RecipeMapOreWasher extends RecipeMapNonGTRecipes<Recipe, RecipeBuilder.DefaultRecipeBuilder> {

		public RecipeMapOreWasher(Collection<Recipe> recipeList, String unlocalizedName, String localName, String JEIName, String JEIGUIPath, int minInputs, int maxInputs, int minOutputs, int maxOutputs, int minFluidInputs, int maxFluidInputs, int minFluidOutputs, int maxFluidOutputs, int amperage, String JEISpecialValuePre, int JEISpecialValueMultiplier, String JEISpecialValuePost, boolean showVoltageAmperageInJEI, boolean JEIAllowed, RecipeBuilder.DefaultRecipeBuilder defaultRecipe) {
			super(recipeList, unlocalizedName, localName, JEIName, JEIGUIPath, minInputs, maxInputs, minOutputs, maxOutputs, minFluidInputs, maxFluidInputs, minFluidOutputs, maxFluidOutputs, amperage, JEISpecialValuePre, JEISpecialValueMultiplier, JEISpecialValuePost, showVoltageAmperageInJEI, JEIAllowed, defaultRecipe);
		}

		@Override
		public Recipe findRecipe(IHasWorldObjectAndCoords tileEntity, Recipe inputRecipe, boolean notUnificated, long voltage, FluidStack[] fluidInputs, ItemStack[] inputs) {
			if (inputs == null || inputs.length <= 0 || inputs[0] == null || fluidInputs == null || fluidInputs.length < 1 || !ModHandler.isWater(fluidInputs[0]))
				return null;
			if (inputRecipe != null && inputRecipe.isRecipeInputEqual(false, true, fluidInputs, inputs)) return inputRecipe;
			ItemStack comparedInput = GT_Utility.copy(inputs[0]);
			NBTTagCompound recipeMetaData = new NBTTagCompound();
			ItemStack[] outputItems = ModHandler.getMachineOutput(comparedInput, ic2.api.recipe.Recipes.oreWashing.getRecipes(), true, recipeMetaData, null, null, null);

			if (GT_Utility.arrayContainsNonNull(outputItems)) {
				return this.recipeBuilder()
						.nonOptimized()
						.inputs(GT_Utility.copyAmount(inputs[0].stackSize - comparedInput.stackSize, inputs[0]))
						.outputs(outputItems)
						.fluidInputs(new FluidStack(fluidInputs[0].getFluid(), ((NBTTagCompound) recipeMetaData.getTag("return")).getInteger("amount")))
						.duration(400)
						.EUt(16)
						.build();
			}
			return null;
		}

		@Override
		public boolean containsInput(ItemStack stack) {
			return GT_Utility.arrayContainsNonNull(ModHandler.getMachineOutput(GT_Utility.copyAmount(64, stack), ic2.api.recipe.Recipes.oreWashing.getRecipes(), false, new NBTTagCompound(), null, null, null));
		}

		@Override
		public boolean containsInput(FluidStack fluid) {
			return ModHandler.isWater(fluid);
		}

		@Override
		public boolean containsInput(Fluid fluid) {
			return ModHandler.isWater(new FluidStack(fluid, 0));
		}
	}

	/**
	 * Special Class for Macerator/RockCrusher Recipe handling.
	 */
	public static class RecipeMapMacerator extends RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> {

		public RecipeMapMacerator(Collection<Recipe> recipeList, String unlocalizedName, String localName, String JEIName, String JEIGUIPath, int minInputs, int maxInputs, int minOutputs, int maxOutputs, int minFluidInputs, int maxFluidInputs, int minFluidOutputs, int maxFluidOutputs, int amperage, String JEISpecialValuePre, int JEISpecialValueMultiplier, String JEISpecialValuePost, boolean showVoltageAmperageInJEI, boolean JEIAllowed, RecipeBuilder.DefaultRecipeBuilder defaultRecipe) {
			super(recipeList, unlocalizedName, localName, JEIName, JEIGUIPath, minInputs, maxInputs, minOutputs, maxOutputs, minFluidInputs, maxFluidInputs, minFluidOutputs, maxFluidOutputs, amperage, JEISpecialValuePre, JEISpecialValueMultiplier, JEISpecialValuePost, showVoltageAmperageInJEI, JEIAllowed, defaultRecipe);
		}

		@Override
		public Recipe findRecipe(IHasWorldObjectAndCoords tileEntity, Recipe inputRecipe, boolean notUnificated, long voltage, FluidStack[] fluidInputs, ItemStack[] inputs) {
			if (inputs == null || inputs.length <= 0 || inputs[0] == null || !GregTech_API.sPostloadFinished)
				return super.findRecipe(tileEntity, inputRecipe, notUnificated, voltage, fluidInputs, inputs);
			inputRecipe = super.findRecipe(tileEntity, inputRecipe, notUnificated, voltage, fluidInputs, inputs);
			if (inputRecipe != null) return inputRecipe;

			ItemStack comparedInput = GT_Utility.copy(inputs[0]);
			ItemStack[] outputItems = ModHandler.getMachineOutput(comparedInput, ic2.api.recipe.Recipes.macerator.getRecipes(), true, new NBTTagCompound(), null, null, null);

			if (GT_Utility.arrayContainsNonNull(outputItems)) {
				return this.recipeBuilder()
						.nonOptimized()
						.inputs(GT_Utility.copyAmount(inputs[0].stackSize - comparedInput.stackSize, inputs[0]))
						.outputs(outputItems)
						.duration(400)
						.EUt(2)
						.build();
			}
			return null;
		}

		@Override
		public boolean containsInput(ItemStack stack) {
			return super.containsInput(stack) || GT_Utility.arrayContainsNonNull(ModHandler.getMachineOutput(GT_Utility.copyAmount(64, stack), ic2.api.recipe.Recipes.macerator.getRecipes(), false, new NBTTagCompound(), null, null, null));
		}
	}

	/**
	 * Special Class for Assembler handling.
	 */
	public static class RecipeMapAssembler extends RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> {

		public RecipeMapAssembler(Collection<Recipe> recipeList, String unlocalizedName, String localName, String JEIName, String JEIGUIPath, int minInputs, int maxInputs, int minOutputs, int maxOutputs, int minFluidInputs, int maxFluidInputs, int minFluidOutputs, int maxFluidOutputs, int amperage, String JEISpecialValuePre, int JEISpecialValueMultiplier, String JEISpecialValuePost, boolean showVoltageAmperageInJEI, boolean JEIAllowed, RecipeBuilder.DefaultRecipeBuilder defaultRecipe) {
			super(recipeList, unlocalizedName, localName, JEIName, JEIGUIPath, minInputs, maxInputs, minOutputs, maxOutputs, minFluidInputs, maxFluidInputs, minFluidOutputs, maxFluidOutputs, amperage, JEISpecialValuePre, JEISpecialValueMultiplier, JEISpecialValuePost, showVoltageAmperageInJEI, JEIAllowed, defaultRecipe);
		}

		@Override
		public Recipe findRecipe(IHasWorldObjectAndCoords tileEntity, Recipe inputRecipe, boolean notUnificated, long voltage, FluidStack[] fluidInputs, ItemStack[] inputs) {
			Recipe recipe = super.findRecipe(tileEntity, inputRecipe, notUnificated, voltage, fluidInputs, inputs);
			if (inputs == null || inputs.length <= 0 || inputs[0] == null || recipe == null || !GregTech_API.sPostloadFinished)
				return recipe;
			for (ItemStack stack : inputs) {
				if (ItemList.Paper_Printed_Pages.isStackEqual(stack, false, true)) {
					RecipeBuilder builder = this.recipeBuilder()
							.fromRecipe(recipe)
							.cannotBeBuffered();

					List<ItemStack> outputs = builder.getOutputs();
					ItemStack itemStack = outputs.get(0);
					itemStack.setTagCompound(itemStack.getTagCompound());
					outputs.set(0, itemStack);

					recipe = builder.build();
				}
			}
			return recipe;
		}
	}

	/**
	 * Special Class for Forming Press handling.
	 */
	public static class RecipeMapFormingPress extends RecipeMap<Recipe, RecipeBuilder.NotConsumableInputRecipeBuilder> {

		public RecipeMapFormingPress(Collection<Recipe> recipeList, String unlocalizedName, String localName, String JEIName, String JEIGUIPath, int minInputs, int maxInputs, int minOutputs, int maxOutputs, int minFluidInputs, int maxFluidInputs, int minFluidOutputs, int maxFluidOutputs, int amperage, String JEISpecialValuePre, int JEISpecialValueMultiplier, String JEISpecialValuePost, boolean showVoltageAmperageInJEI, boolean JEIAllowed, RecipeBuilder.NotConsumableInputRecipeBuilder defaultRecipe) {
			super(recipeList, unlocalizedName, localName, JEIName, JEIGUIPath, minInputs, maxInputs, minOutputs, maxOutputs, minFluidInputs, maxFluidInputs, minFluidOutputs, maxFluidOutputs, amperage, JEISpecialValuePre, JEISpecialValueMultiplier, JEISpecialValuePost, showVoltageAmperageInJEI, JEIAllowed, defaultRecipe);
		}

		@Override
		public Recipe findRecipe(IHasWorldObjectAndCoords tileEntity, Recipe inputRecipe, boolean notUnificated, long voltage, FluidStack[] fluidInputs, ItemStack[] inputs) {
			Recipe recipe = super.findRecipe(tileEntity, inputRecipe, notUnificated, voltage, fluidInputs, inputs);
			if (inputs == null || inputs.length < 2 || inputs[0] == null || inputs[1] == null || !GregTech_API.sPostloadFinished)
				return recipe;
			if (recipe == null) {
				if (ItemList.Shape_Mold_Name.isStackEqual(inputs[0], false, true)) {
					ItemStack output = GT_Utility.copyAmount(1, inputs[1]);
					output.setStackDisplayName(inputs[0].getDisplayName());

					return this.recipeBuilder()
							.cannotBeBuffered()
							.nonOptimized()
							.notConsumable(ItemList.Shape_Mold_Name)
							.inputs(GT_Utility.copyAmount(1, inputs[1]))
							.outputs(output)
							.duration(128)
							.EUt(8)
							.build();
				}
				if (ItemList.Shape_Mold_Name.isStackEqual(inputs[1], false, true)) {
					ItemStack output = GT_Utility.copyAmount(1, inputs[0]);
					output.setStackDisplayName(inputs[1].getDisplayName());

					return this.recipeBuilder()
							.cannotBeBuffered()
							.nonOptimized()
							.notConsumable(ItemList.Shape_Mold_Name)
							.inputs(GT_Utility.copyAmount(1, inputs[0]))
							.outputs(output)
							.duration(128)
							.EUt(8)
							.build();
				}
				return null;
			}
			for (ItemStack mold : inputs) {
				if (ItemList.Shape_Mold_Credit.isStackEqual(mold, false, true)) {
					NBTTagCompound tag = mold.getTagCompound();
					if (tag == null) tag = new NBTTagCompound();
					if (!tag.hasKey("credit_security_id")) tag.setLong("credit_security_id", System.nanoTime());
					mold.setTagCompound(tag);

					RecipeBuilder builder = this.recipeBuilder()
							.fromRecipe(recipe)
							.cannotBeBuffered();

					List<ItemStack> outputs = builder.getOutputs();
					ItemStack stack = outputs.get(0);
					stack.setTagCompound(tag);
					outputs.set(0, stack);

					return builder.build();
				}
			}
			return recipe;
		}
	}

	/**
	 * Special Class for Printer handling.
	 */
	public static class RecipeMapPrinter extends RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> {

		public RecipeMapPrinter(Collection<Recipe> recipeList, String unlocalizedName, String localName, String JEIName, String JEIGUIPath, int minInputs, int maxInputs, int minOutputs, int maxOutputs, int minFluidInputs, int maxFluidInputs, int minFluidOutputs, int maxFluidOutputs, int amperage, String JEISpecialValuePre, int JEISpecialValueMultiplier, String JEISpecialValuePost, boolean showVoltageAmperageInJEI, boolean JEIAllowed, RecipeBuilder.DefaultRecipeBuilder defaultRecipe) {
			super(recipeList, unlocalizedName, localName, JEIName, JEIGUIPath, minInputs, maxInputs, minOutputs, maxOutputs, minFluidInputs, maxFluidInputs, minFluidOutputs, maxFluidOutputs, amperage, JEISpecialValuePre, JEISpecialValueMultiplier, JEISpecialValuePost, showVoltageAmperageInJEI, JEIAllowed, defaultRecipe);
		}

		@Override
		public Recipe findRecipe(IHasWorldObjectAndCoords tileEntity, Recipe inputRecipe, boolean notUnificated, long voltage, FluidStack[] fluidInputs, ItemStack[] inputs) {
			Recipe recipe = super.findRecipe(tileEntity, inputRecipe, notUnificated, voltage, fluidInputs, inputs);
			if (inputs == null || inputs.length <= 0 || inputs[0] == null || fluidInputs == null || fluidInputs.length <= 0 || fluidInputs[0] == null || !GregTech_API.sPostloadFinished)
				return recipe;

			Dyes dye = null;
			for (Dyes tmpDye : Dyes.VALUES)
				if (tmpDye.isFluidDye(fluidInputs[0])) {
					dye = tmpDye;
					break;
				}

			if (dye == null) return recipe;

			if (recipe == null) {
				ItemStack output = ModHandler.getAllRecipeOutput(tileEntity == null ? null : tileEntity.getWorldObj(), inputs[0], inputs[0], inputs[0], inputs[0], ItemList.DYE_ONLY_ITEMS[dye.mIndex].get(1), inputs[0], inputs[0], inputs[0], inputs[0]);
				if (output != null) {
					Recipe outputRecipe = this.recipeBuilder()
							.inputs(GT_Utility.copyAmount(8, inputs[0]))
							.outputs(output)
							.fluidInputs(new FluidStack(fluidInputs[0].getFluid(), (int) L))
							.duration(256)
							.EUt(2)
							.build();

					return this.addRecipe(outputRecipe);
				}

				output = ModHandler.getAllRecipeOutput(tileEntity == null ? null : tileEntity.getWorldObj(), inputs[0], ItemList.DYE_ONLY_ITEMS[dye.mIndex].get(1));
				if (output != null) {
					Recipe outputRecipe = this.recipeBuilder()
							.inputs(GT_Utility.copyAmount(1, inputs[0]))
							.outputs(output)
							.fluidInputs(new FluidStack(fluidInputs[0].getFluid(), (int) L))
							.duration(32)
							.EUt(2)
							.build();
					return this.addRecipe(recipe);

				}
			} else {
				if (inputs[0].getItem() == Items.WRITTEN_BOOK) {
					if (!ItemList.Tool_DataStick.isStackEqual(specialSlot, false, true)) return null;
					NBTTagCompound tag = specialSlot.getTagCompound();
					if (tag == null || !GT_Utility.isStringValid(tag.getString("title")) || !GT_Utility.isStringValid(tag.getString("author")))
						return null;

					RecipeBuilder builder = this.recipeBuilder()
							.fromRecipe(recipe)
							.cannotBeBuffered();

					List<ItemStack> outputs = builder.getOutputs();
					ItemStack stack = outputs.get(0);
					stack.setTagCompound(tag);
					outputs.set(0, stack);

					return builder.build();

				}
				if (inputs[0].getItem() == Items.FILLED_MAP) {
					if (!ItemList.Tool_DataStick.isStackEqual(specialSlot, false, true)) return null;
					NBTTagCompound tag = specialSlot.getTagCompound();
					if (tag == null || !tag.hasKey("map_id")) return null;

					RecipeBuilder builder = this.recipeBuilder()
							.fromRecipe(recipe)
							.cannotBeBuffered();

					List<ItemStack> outputs = builder.getOutputs();
					ItemStack stack = outputs.get(0);
					stack.setItemDamage(tag.getShort("map_id"));
					outputs.set(0, stack);

					return builder.build();
				}
				if (ItemList.Paper_Punch_Card_Empty.isStackEqual(inputs[0], false, true)) {
					if (!ItemList.Tool_DataStick.isStackEqual(specialSlot, false, true)) return null;
					NBTTagCompound tag = specialSlot.getTagCompound();
					if (tag == null || !tag.hasKey("GT.PunchCardData")) return null;

					RecipeBuilder builder = this.recipeBuilder()
							.fromRecipe(recipe)
							.cannotBeBuffered();

					List<ItemStack> outputs = builder.getOutputs();
					ItemStack stack = outputs.get(0);

					NBTTagCompound tagCompound = new NBTTagCompound();
					tagCompound.setString("GT.PunchCardData", tagCompound.getString("GT.PunchCardData"));
					stack.setTagCompound(tagCompound);

					outputs.set(0, stack);
					return builder.build();
				}
			}
			return recipe;
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
