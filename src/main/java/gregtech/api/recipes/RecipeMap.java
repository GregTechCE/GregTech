package gregtech.api.recipes;

import gregtech.api.GregTech_API;
import gregtech.api.enums.Dyes;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static gregtech.api.enums.GT_Values.D1;
import static gregtech.api.enums.GT_Values.L;
import static gregtech.api.enums.GT_Values.W;

public class RecipeMap {

	private static String E = "";

	/**
	 * Contains all Recipe Maps
	 */
	public static final Collection<RecipeMap> RECIPE_MAPS = new ArrayList<>();

	public static final RecipeMap ORE_WASHER_RECIPES = new GT_Recipe_Map_OreWasher(new HashSet<>(0), "ic.recipe.orewasher", "Ore Washer", "ic2.blockOreWashingPlant", "basicmachines/OreWasher", 1, 3, 1, 1, 1, E, 1, E, true, false);
	public static final RecipeMap THERMAL_CENTRIFUGE_RECIPES = new GT_Recipe_Map_ThermalCentrifuge(new HashSet<>(0), "ic.recipe.thermalcentrifuge", "Thermal Centrifuge", "ic2.blockCentrifuge", "basicmachines/ThermalCentrifuge", 1, 3, 1, 0, 2, E, 1, E, true, false);
	public static final RecipeMap COMPRESSOR_RECIPES = new GT_Recipe_Map_Compressor(new HashSet<>(0), "ic.recipe.compressor", "Compressor", "ic2.compressor", "basicmachines/Compressor", 1, 1, 1, 0, 1, E, 1, E, true, false);
	public static final RecipeMap EXTRACTOR_RECIPES = new GT_Recipe_Map_Extractor(new HashSet<>(0), "ic.recipe.extractor", "Extractor", "ic2.extractor", "basicmachines/Extractor", 1, 1, 1, 0, 1, E, 1, E, true, false);
	public static final RecipeMap RECYCLER_RECIPES = new GT_Recipe_Map_Recycler(new HashSet<>(0), "ic.recipe.recycler", "Recycler", "ic2.recycler", "basicmachines/Recycler", 1, 1, 1, 0, 1, E, 1, E, true, false);
	public static final RecipeMap FURNACE_RECIPES = new GT_Recipe_Map_Furnace(new HashSet<>(0), "mc.recipe.furnace", "Furnace", "smelting", "basicmachines/E_Furnace", 1, 1, 1, 0, 1, E, 1, E, true, false);
	public static final RecipeMap MICROWAVE_RECIPES = new GT_Recipe_Map_Microwave(new HashSet<>(0), "gt.recipe.microwave", "Microwave", "smelting", "basicmachines/E_Furnace", 1, 1, 1, 0, 1, E, 1, E, true, false);

	public static final RecipeMap SCANNER_FAKE_RECIPES = new RecipeMap(new HashSet<>(300), "gt.recipe.scanner", "Scanner", null, "basicmachines/Scanner", 1, 1, 1, 0, 1, E, 1, E, true, true);
	public static final RecipeMap ROCK_BREAKER_FAKE_RECIPES = new RecipeMap(new HashSet<>(3), "gt.recipe.rockbreaker", "Rock Breaker", null, "basicmachines/RockBreaker", 1, 1, 0, 0, 1, E, 1, E, true, true);
	public static final RecipeMap BY_PRODUCT_LIST = new RecipeMap(new HashSet<>(1000), "gt.recipe.byproductlist", "Ore Byproduct List", null, "basicmachines/Default", 1, 6, 1, 0, 1, E, 1, E, true, true);
	public static final RecipeMap REPICATOR_FAKE_RECIPES = new RecipeMap(new HashSet<>(100), "gt.recipe.replicator", "Replicator", null, "basicmachines/Replicator", 0, 1, 0, 1, 1, E, 1, E, true, true);
	public static final RecipeMap ASSEMBLYLINE_FAKE_RECIPES = new RecipeMap(new HashSet<>(30), "gt.recipe.assemblyline", "Assembly Line", null, "basicmachines/Default", 1, 1, 1, 0, 1, E, 1, E, true, true);

	public static final RecipeMap ASSEMBLER_RECIPES = new GT_Recipe_Map_Assembler(new HashSet<>(300), "gt.recipe.assembler", "Assembler", null, "basicmachines/Assembler", 2, 1, 1, 0, 1, E, 1, E, true, true);
	public static final RecipeMap PRINTER_RECIPES = new GT_Recipe_Map_Printer(new HashSet<>(100), "gt.recipe.printer", "Printer", null, "basicmachines/Printer", 1, 1, 1, 1, 1, E, 1, E, true, true);
	public static final RecipeMap PRESS_RECIPES = new GT_Recipe_Map_FormingPress(new HashSet<>(100), "gt.recipe.press", "Forming Press", null, "basicmachines/Press", 2, 1, 2, 0, 1, E, 1, E, true, true);
	public static final RecipeMap MACERATOR_RECIPES = new GT_Recipe_Map_Macerator(new HashSet<>(10000), "gt.recipe.macerator", "Pulverization", null, "basicmachines/Macerator4", 1, 4, 1, 0, 1, E, 1, E, true, true);
	public static final RecipeMap UNBOXINATOR_RECIPES = new GT_Recipe_Map_Unboxinator(new HashSet<>(2500), "gt.recipe.unpackager", "Unpackager", null, "basicmachines/Unpackager", 1, 2, 1, 0, 1, E, 1, E, true, true);
	public static final RecipeMap FLUID_CANNER_RECIPES = new GT_Recipe_Map_FluidCanner(new HashSet<>(100), "gt.recipe.fluidcanner", "Fluid Canning Machine", null, "basicmachines/FluidCannerJEI", 1, 1, 1, 0, 1, E, 1, E, true, true);

	public static final RecipeMap PLASMA_ARC_FURNACE_RECIPES = new RecipeMap(new HashSet<>(10000), "gt.recipe.plasmaarcfurnace", "Plasma Arc Furnace", null, "basicmachines/PlasmaArcFurnace", 1, 4, 1, 1, 1, E, 1, E, true, true);
	public static final RecipeMap ARC_FURNACE_RECIPES = new RecipeMap(new HashSet<>(10000), "gt.recipe.arcfurnace", "Arc Furnace", null, "basicmachines/ArcFurnace", 1, 4, 1, 1, 3, E, 1, E, true, true);
	public static final RecipeMap SIFTER_RECIPES = new RecipeMap(new HashSet<>(100), "gt.recipe.sifter", "Sifter", null, "basicmachines/Sifter", 1, 9, 1, 0, 1, E, 1, E, true, true);
	public static final RecipeMap LASER_ENGRAVER_RECIPES = new RecipeMap(new HashSet<>(100), "gt.recipe.laserengraver", "Precision Laser Engraver", null, "basicmachines/LaserEngraver", 2, 1, 2, 0, 1, E, 1, E, true, true);
	public static final RecipeMap MIXER_RECIPES = new RecipeMap(new HashSet<>(100), "gt.recipe.mixer", "Mixer", null, "basicmachines/Mixer", 4, 1, 1, 0, 1, E, 1, E, true, true);
	public static final RecipeMap AUTOCLAVE_RECIPES = new RecipeMap(new HashSet<>(200), "gt.recipe.autoclave", "Autoclave", null, "basicmachines/Autoclave", 1, 1, 1, 1, 1, E, 1, E, true, true);
	public static final RecipeMap ELECTROMAGNETIC_SEPARATOR_RECIPES = new RecipeMap(new HashSet<>(50), "gt.recipe.electromagneticseparator", "Electromagnetic Separator", null, "basicmachines/ElectromagneticSeparator", 1, 3, 1, 0, 1, E, 1, E, true, true);
	public static final RecipeMap POLARIZER_RECIPES = new RecipeMap(new HashSet<>(100), "gt.recipe.polarizer", "Electromagnetic Polarizer", null, "basicmachines/Polarizer", 1, 1, 1, 0, 1, E, 1, E, true, true);
	public static final RecipeMap CHEMICAL_BATH_RECIPES = new RecipeMap(new HashSet<>(200), "gt.recipe.chemicalbath", "Chemical Bath", null, "basicmachines/ChemicalBath", 1, 3, 1, 1, 1, E, 1, E, true, true);
	public static final RecipeMap BREWING_RECIPES = new RecipeMap(new HashSet<>(100), "gt.recipe.brewer", "Brewing Machine", null, "basicmachines/PotionBrewer", 1, 0, 1, 1, 1, E, 1, E, true, true);
	public static final RecipeMap FLUID_HEATER_RECIPES = new RecipeMap(new HashSet<>(100), "gt.recipe.fluidheater", "Fluid Heater", null, "basicmachines/FluidHeater", 1, 0, 1, 1, 1, E, 1, E, true, true);
	public static final RecipeMap DISTILLERY_RECIPES = new RecipeMap(new HashSet<>(100), "gt.recipe.distillery", "Distillery", null, "basicmachines/Distillery", 1, 0, 1, 1, 1, E, 1, E, true, true);
	public static final RecipeMap FERMENTING_RECIPES = new RecipeMap(new HashSet<>(100), "gt.recipe.fermenter", "Fermenter", null, "basicmachines/Fermenter", 0, 0, 0, 1, 1, E, 1, E, true, true);
	public static final RecipeMap FLUID_SOLIDFICATION_RECIPES = new RecipeMap(new HashSet<>(100), "gt.recipe.fluidsolidifier", "Fluid Solidifier", null, "basicmachines/FluidSolidifier", 1, 1, 1, 1, 1, E, 1, E, true, true);
	public static final RecipeMap FLUID_EXTRACTION_RECIPES = new RecipeMap(new HashSet<>(100), "gt.recipe.fluidextractor", "Fluid Extractor", null, "basicmachines/FluidExtractor", 1, 1, 1, 0, 1, E, 1, E, true, true);
	public static final RecipeMap BOXINATOR_RECIPES = new RecipeMap(new HashSet<>(2500), "gt.recipe.packager", "Packager", null, "basicmachines/Packager", 2, 1, 2, 0, 1, E, 1, E, true, true);
	public static final RecipeMap FUSION_RECIPES = new RecipeMap(new HashSet<>(50), "gt.recipe.fusionreactor", "Fusion Reactor", null, "basicmachines/Default", 0, 0, 0, 2, 1, "Start: ", 1, " EU", true, true);
	public static final RecipeMap CENTRIFUGE_RECIPES = new RecipeMap(new HashSet<>(1000), "gt.recipe.centrifuge", "Centrifuge", null, "basicmachines/Centrifuge", 2, 6, 0, 0, 1, E, 1, E, true, true);
	public static final RecipeMap ELECTROLYZER_RECIPES = new RecipeMap(new HashSet<>(200), "gt.recipe.electrolyzer", "Electrolyzer", null, "basicmachines/Electrolyzer", 2, 6, 0, 0, 1, E, 1, E, true, true);
	public static final RecipeMap BLAST_RECIPES = new RecipeMap(new HashSet<>(500), "gt.recipe.blastfurnace", "Blast Furnace", null, "basicmachines/Default", 2, 2, 1, 0, 1, "Heat Capacity: ", 1, " K", false, true);
	public static final RecipeMap IMPLOSION_RECIPES = new RecipeMap(new HashSet<>(50), "gt.recipe.implosioncompressor", "Implosion Compressor", null, "basicmachines/Default", 2, 2, 2, 0, 1, E, 1, E, true, true);
	public static final RecipeMap VACUUM_RECIPES = new RecipeMap(new HashSet<>(100), "gt.recipe.vacuumfreezer", "Vacuum Freezer", null, "basicmachines/Default", 1, 1, 1, 0, 1, E, 1, E, true, true);
	public static final RecipeMap CHEMICAL_RECIPES = new RecipeMap(new HashSet<>(100), "gt.recipe.chemicalreactor", "Chemical Reactor", null, "basicmachines/ChemicalReactor", 2, 1, 1, 0, 1, E, 1, E, true, true);
	public static final RecipeMap DISTILLATION_RECIPES = new RecipeMap(new HashSet<>(50), "gt.recipe.distillationtower", "Distillation Tower", null, "basicmachines/Default", 2, 4, 0, 0, 1, E, 1, E, true, true);
	public static final RecipeMap CRAKING_RECIPES = new RecipeMap(new HashSet<>(50), "gt.recipe.craker", "Oil Cracker", null, "basicmachines/Default", 1, 1, 0, 1, 1, E, 1, E, true, true);
	public static final RecipeMap PYROLYSE_RECIPES = new RecipeMap(new HashSet<>(50), "gt.recipe.pyro", "Pyrolyse Oven", null, "basicmachines/Default", 2, 1, 1, 0, 1, E, 1, E, true, true);
	public static final RecipeMap WIREMILL_RECIPES = new RecipeMap(new HashSet<>(50), "gt.recipe.wiremill", "Wiremill", null, "basicmachines/Wiremill", 1, 1, 1, 0, 1, E, 1, E, true, true);
	public static final RecipeMap BENDER_RECIPES = new RecipeMap(new HashSet<>(400), "gt.recipe.metalbender", "Metal Bender", null, "basicmachines/Bender", 2, 1, 2, 0, 1, E, 1, E, true, true);
	public static final RecipeMap ALLOY_SMELTER_RECIPES = new RecipeMap(new HashSet<>(3000), "gt.recipe.alloysmelter", "Alloy Smelter", null, "basicmachines/AlloySmelter", 2, 1, 2, 0, 1, E, 1, E, true, true);
	public static final RecipeMap CANNER_RECIPES = new RecipeMap(new HashSet<>(300), "gt.recipe.canner", "Canning Machine", null, "basicmachines/Canner", 2, 2, 1, 0, 1, E, 1, E, true, true);
	public static final RecipeMap CNC_RECIPES = new RecipeMap(new HashSet<>(100), "gt.recipe.cncmachine", "CNC Machine", null, "basicmachines/Default", 2, 1, 2, 1, 1, E, 1, E, true, true);
	public static final RecipeMap LATHE_RECIPES = new RecipeMap(new HashSet<>(400), "gt.recipe.lathe", "Lathe", null, "basicmachines/Lathe", 1, 2, 1, 0, 1, E, 1, E, true, true);
	public static final RecipeMap CUTTER_RECIPES = new RecipeMap(new HashSet<>(200), "gt.recipe.cuttingsaw", "Cutting Saw", null, "basicmachines/Cutter", 1, 2, 1, 1, 1, E, 1, E, true, true);
	public static final RecipeMap SLICER_RECIPES = new RecipeMap(new HashSet<>(200), "gt.recipe.slicer", "Slicer", null, "basicmachines/Slicer", 2, 1, 2, 0, 1, E, 1, E, true, true);
	public static final RecipeMap EXTRUDER_RECIPES = new RecipeMap(new HashSet<>(1000), "gt.recipe.extruder", "Extruder", null, "basicmachines/Extruder", 2, 1, 2, 0, 1, E, 1, E, true, true);
	public static final RecipeMap HAMMER_RECIPES = new RecipeMap(new HashSet<>(200), "gt.recipe.hammer", "Hammer", null, "basicmachines/Hammer", 1, 1, 1, 0, 1, E, 1, E, true, true);
	public static final RecipeMap AMPLIFIERS = new RecipeMap(new HashSet<>(10), "gt.recipe.uuamplifier", "UU Amplifier", null, "basicmachines/Amplifabricator", 1, 0, 1, 0, 1, E, 1, E, true, true);

	public static final RecipeMapFuel DIESEL_FUELS = new RecipeMapFuel(new HashSet<>(10), "gt.recipe.dieselgeneratorfuel", "Diesel Generator Fuel", null, "basicmachines/Default", 1, 1, 0, 0, 1, "Fuel Value: ", 1000, " EU", true, true);
	public static final RecipeMapFuel TURBINE_FUELS = new RecipeMapFuel(new HashSet<>(10), "gt.recipe.gasturbinefuel", "Gas Turbine Fuel", null, "basicmachines/Default", 1, 1, 0, 0, 1, "Fuel Value: ", 1000, " EU", true, true);
	public static final RecipeMapFuel HOT_FUELS = new RecipeMapFuel(new HashSet<>(10), "gt.recipe.thermalgeneratorfuel", "Thermal Generator Fuel", null, "basicmachines/Default", 1, 1, 0, 0, 1, "Fuel Value: ", 1000, " EU", true, false);
	public static final RecipeMapFuel DENSE_LIQUID_FUELS = new RecipeMapFuel(new HashSet<>(10), "gt.recipe.semifluidboilerfuels", "Semifluid Boiler Fuels", null, "basicmachines/Default", 1, 1, 0, 0, 1, "Fuel Value: ", 1000, " EU", true, true);
	public static final RecipeMapFuel PLASMA_FUELS = new RecipeMapFuel(new HashSet<>(10), "gt.recipe.plasmageneratorfuels", "Plasma generator Fuels", null, "basicmachines/Default", 1, 1, 0, 0, 1, "Fuel Value: ", 1000, " EU", true, true);
	public static final RecipeMapFuel MAGIC_FUELS = new RecipeMapFuel(new HashSet<>(10), "gt.recipe.magicfuels", "Magic Fuels", null, "basicmachines/Default", 1, 1, 0, 0, 1, "Fuel Value: ", 1000, " EU", true, true);
	public static final RecipeMapFuel SMALL_NAQUADAH_REACTOR_FUELS = new RecipeMapFuel(new HashSet<>(10), "gt.recipe.smallnaquadahreactor", "Small Naquadah Reactor", null, "basicmachines/Default", 1, 1, 0, 0, 1, "Fuel Value: ", 1000, " EU", true, true);
	public static final RecipeMapFuel LARGE_NAQUADAH_REACTOR_FUELS = new RecipeMapFuel(new HashSet<>(10), "gt.recipe.largenaquadahreactor", "Large Naquadah Reactor", null, "basicmachines/Default", 1, 1, 0, 0, 1, "Fuel Value: ", 1000, " EU", true, true);
	public static final RecipeMapFuel FLUID_NAQUADAH_REACTOR_FUELS = new RecipeMapFuel(new HashSet<>(10), "gt.recipe.fluidnaquadahreactor", "Fluid Naquadah Reactor", null, "basicmachines/Default", 1, 1, 0, 0, 1, "Fuel Value: ", 1000, " EU", true, true);

	/**
	 * HashMap of Recipes based on their Items
	 */
	public final Map<GT_ItemStack, Collection<GT_Recipe>> recipeItemMap = new HashMap<>();
	/**
	 * HashMap of Recipes based on their Fluids
	 */
	public final Map<Fluid, Collection<GT_Recipe>> recipeFluidMap = new HashMap<>();
	/**
	 * The List of all Recipes
	 */
	public final Collection<GT_Recipe> recipeList;
	/**
	 * String used as an unlocalised Name.
	 */
	public final String unlocalizedName;

	public final boolean holdsFakeRecipes = false; // TODO this or FakeRecipeMap


	public final int usualInputCount, usualOutputCount;

	private final int minOutputs, maxOutputs;
	private final int minInputs, maxInputs;
	private final int minFluidInputs, maxFluidInputs;
	private final int minFluidOutputs, maxFluidOutputs;

	public final int amperage;

	/**
	 * String used in JEI for the Recipe Lists. If null it will use the unlocalised Name instead
	 */
	public final String JEIName;
	/**
	 * GUI used for JEI Display. Usually the GUI of the Machine itself
	 */
	public final String JEIGUIPath;

	public final String JEISpecialValuePre, JEISpecialValuePost;

	public final int JEISpecialValueMultiplier;

	public final boolean JEIAllowed;
	public final boolean showVoltageAmperageInJEI;


	/**
	 * Initialises a new type of Recipe Handler.
	 *
	 * @param recipeList                a List you specify as Recipe List. Usually just an ArrayList with a pre-initialised Size.
	 * @param unlocalizedName           the unlocalised Name of this Recipe Handler, used mainly for JEI.
	 * @param localName                 the displayed Name inside the JEI Recipe GUI.
	 * @param JEIGUIPath                the displayed GUI Texture, usually just a Machine GUI. Auto-Attaches ".png" if forgotten.
	 * @param usualInputCount           the usual amount of Input Slots this Recipe Class has.
	 * @param usualOutputCount          the usual amount of Output Slots this Recipe Class has.
	 * @param JEISpecialValuePre        the String in front of the Special Value in JEI.
	 * @param JEISpecialValueMultiplier the Value the Special Value is getting Multiplied with before displaying
	 * @param JEISpecialValuePost       the String after the Special Value. Usually for a Unit or something.
	 * @param JEIAllowed                if JEI is allowed to display this Recipe Handler in general.
	 */
	public RecipeMap(Collection<GT_Recipe> recipeList, String unlocalizedName, String localName, String JEIName, String JEIGUIPath, int usualInputCount, int usualOutputCount, int minInputs, int minFluidInputs, int amperage, String JEISpecialValuePre, int JEISpecialValueMultiplier, String JEISpecialValuePost, boolean showVoltageAmperageInJEI, boolean JEIAllowed) {
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
		this.usualInputCount = usualInputCount;
		this.usualOutputCount = usualOutputCount;

		this.minInputs = minInputs;
		this.minFluidInputs = minFluidInputs;
		this.minOutputs = 0; //TODO constructor args
		this.minFluidOutputs = 0;

		this.maxInputs = 0;
		this.maxFluidInputs = 0;
		this.maxOutputs = 0;
		this.maxFluidOutputs = 0;

		GregTech_API.sFluidMappings.add(recipeFluidMap);
		GregTech_API.sItemStackMappings.add(recipeItemMap);
		GT_LanguageManager.addStringLocalization(this.unlocalizedName = unlocalizedName, localName);
	}

	protected GT_Recipe addRecipe(GT_Recipe recipe, boolean checkForCollisions) {

		if (recipe.fluidInputs.length < minFluidInputs && recipe.inputs.length < minInputs) {
			GT_Log.logger.warn("Tried to register the recipe (inputs: {}, fluid inputs: {}, recipe map: {}) " +
					"with too little amount of inputs (items: {}, fluids: {}) should be min (items: {}, fluids: {}). Skipping!",
					recipe.inputs, recipe.fluidInputs, this.unlocalizedName, recipe.inputs.length, recipe.fluidInputs.length, this.minInputs, this.minFluidInputs);
			return null;
		}

		if (checkForCollisions && findRecipe(null, false, Long.MAX_VALUE, recipe.fluidInputs, recipe.inputs) != null) {
			GT_Log.logger.warn("Tried to register the recipe that has same input (inputs: {}, fluid inputs: {}, recipe map: {}) as another one. Skipping!",
					recipe.inputs, recipe.fluidInputs, this.unlocalizedName);
			return null;
		}

		recipeList.add(recipe);

		for (ItemStack stack : recipe.inputs) {
			recipeItemMap.computeIfAbsent(new GT_ItemStack(stack), k -> new HashSet<>(1)).add(recipe);
		}

		for (FluidStack fluid : recipe.fluidInputs) {
			recipeFluidMap.computeIfAbsent(fluid.getFluid(), k -> new HashSet<>(1)).add(recipe);
		}

		return recipe;
	}

//	public GT_Recipe addRecipe(boolean aOptimize, ItemStack[] aInputs, ItemStack[] aOutputs, Object aSpecial, int[] aOutputChances, FluidStack[] aFluidInputs, FluidStack[] aFluidOutputs, int aDuration, int aEUt, int aSpecialValue) {
//		return addRecipe(new GT_Recipe(aOptimize, aInputs, aOutputs, aSpecial, aOutputChances, aFluidInputs, aFluidOutputs, aDuration, aEUt, aSpecialValue));
//	}
//
//	public GT_Recipe addRecipe(boolean aOptimize, ItemStack[] aInputs, ItemStack[] aOutputs, Object aSpecial, FluidStack[] aFluidInputs, FluidStack[] aFluidOutputs, int aDuration, int aEUt, int aSpecialValue) {
//		return addRecipe(new GT_Recipe(aOptimize, aInputs, aOutputs, aSpecial, null, aFluidInputs, aFluidOutputs, aDuration, aEUt, aSpecialValue));
//	}
//
//	public GT_Recipe addRecipe(GT_Recipe aRecipe) {
//		return addRecipe(aRecipe, true, false, false);
//	}

//	/**
//	 * Only used for fake Recipe Handlers to show something in JEI, do not use this for adding actual Recipes! findRecipe wont find fake Recipes, containsInput WILL find fake Recipes
//	 */
//	public GT_Recipe addFakeRecipe(boolean aCheckForCollisions, ItemStack[] aInputs, ItemStack[] aOutputs, Object aSpecial, int[] aOutputChances, FluidStack[] aFluidInputs, FluidStack[] aFluidOutputs, int aDuration, int aEUt, int aSpecialValue) {
//		return addFakeRecipe(aCheckForCollisions, new GT_Recipe(false, aInputs, aOutputs, aSpecial, aOutputChances, aFluidInputs, aFluidOutputs, aDuration, aEUt, aSpecialValue));
//	}

//	/**
//	 * Only used for fake Recipe Handlers to show something in JEI, do not use this for adding actual Recipes! findRecipe wont find fake Recipes, containsInput WILL find fake Recipes
//	 */
//	public GT_Recipe addFakeRecipe(boolean aCheckForCollisions, ItemStack[] aInputs, ItemStack[] aOutputs, Object aSpecial, FluidStack[] aFluidInputs, FluidStack[] aFluidOutputs, int aDuration, int aEUt, int aSpecialValue) {
//		return addFakeRecipe(aCheckForCollisions, new GT_Recipe(false, aInputs, aOutputs, aSpecial, null, aFluidInputs, aFluidOutputs, aDuration, aEUt, aSpecialValue));
//	}

//	/**
//	 * Only used for fake Recipe Handlers to show something in JEI, do not use this for adding actual Recipes! findRecipe wont find fake Recipes, containsInput WILL find fake Recipes
//	 */
//	public GT_Recipe addFakeRecipe(boolean aCheckForCollisions, GT_Recipe aRecipe) {
//		return addRecipe(aRecipe, aCheckForCollisions, true, false);
//	}

	public void reInit() {
		Map<GT_ItemStack, Collection<GT_Recipe>> map = recipeItemMap;
		if (map != null) map.clear();
		for (GT_Recipe recipe : recipeList) {
			GT_OreDictUnificator.setStackArray(true, recipe.inputs);
			GT_OreDictUnificator.setStackArray(true, recipe.outputs);

			if (map != null) {
				for (ItemStack stack : recipe.inputs) {
					recipeItemMap.computeIfAbsent(new GT_ItemStack(stack), k -> new HashSet<>(1)).add(recipe);
				}
			}
		}
	}

	/**
	 * @return if this Item is a valid Input for any for the Recipes
	 */
	public boolean containsInput(ItemStack aStack) {
		return aStack != null && (recipeItemMap.containsKey(new GT_ItemStack(aStack)) || recipeItemMap.containsKey(new GT_ItemStack(GT_Utility.copyMetaData(W, aStack))));
	}

	/**
	 * @return if this Fluid is a valid Input for any for the Recipes
	 */
	public boolean containsInput(FluidStack aFluid) {
		return aFluid != null && containsInput(aFluid.getFluid());
	}

	/**
	 * @return if this Fluid is a valid Input for any for the Recipes
	 */
	public boolean containsInput(Fluid aFluid) {
		return aFluid != null && recipeFluidMap.containsKey(aFluid);
	}

	public GT_Recipe findRecipe(IHasWorldObjectAndCoords aTileEntity, boolean aNotUnificated, long aVoltage, FluidStack[] aFluids, ItemStack[] aInputs) {
		return findRecipe(aTileEntity, null, aNotUnificated, aVoltage, aFluids, null, aInputs);
	}

	public GT_Recipe findRecipe(IHasWorldObjectAndCoords aTileEntity, GT_Recipe aRecipe, boolean aNotUnificated, long aVoltage, FluidStack[] aFluids, ItemStack[] aInputs) {
		return findRecipe(aTileEntity, aRecipe, aNotUnificated, aVoltage, aFluids, null, aInputs);
	}

	/**
	 * finds a Recipe matching the aFluid and ItemStack Inputs.
	 *
	 * @param tileEntity    an Object representing the current coordinates of the executing Block/Entity/Whatever. This may be null, especially during Startup.
	 * @param recipe        in case this is != null it will try to use this Recipe first when looking things up.
	 * @param notUnificated if this is T the Recipe searcher will unificate the ItemStack Inputs
	 * @param voltage       Voltage of the Machine or Long.MAX_VALUE if it has no Voltage
	 * @param fluidInputs        the Fluid Inputs
	 * @param specialSlot   the content of the Special Slot, the regular Manager doesn't do anything with this, but some custom ones do.
	 * @param inputs        the Item Inputs
	 * @return the Recipe it has found or null for no matching Recipe
	 */
	public GT_Recipe findRecipe(IHasWorldObjectAndCoords tileEntity, GT_Recipe recipe, boolean notUnificated, long voltage, FluidStack[] fluidInputs, ItemStack specialSlot, ItemStack[] inputs) {
		// No Recipes? Well, nothing to be found then.
		if (recipeList.isEmpty()) return null;

		// Some Recipe Classes require a certain amount of Inputs of certain kinds. Like "at least 1 Fluid + 1 Stack" or "at least 2 Stacks" before they start searching for Recipes.
		// This improves Performance massively, especially if people leave things like Circuits, Molds or Shapes in their Machines to select Sub Recipes.
		if (GregTech_API.sPostloadFinished) {
			if (minFluidInputs > 0) {
				if (fluidInputs == null) {
					return null;
				}
				int tAmount = 0;
				for (FluidStack aFluid : fluidInputs) {
					if (aFluid != null) {
						tAmount++;
					}
				}
				if (tAmount < minFluidInputs) {
					return null;
				}
			}

			if (minInputs > 0) {
				if (inputs == null) {
					return null;
				}
				int tAmount = 0;
				for (ItemStack aInput : inputs) {
					if (aInput != null) {
						tAmount++;
					}
				}
				if (tAmount < minInputs) {
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
			if (!recipe.fakeRecipe && recipe.canBeBuffered && recipe.isRecipeInputEqual(false, true, fluidInputs, inputs)) {
				return recipe.enabled && voltage * amperage >= recipe.EUt ? recipe : null;
			}
		}

		// Now look for the Recipes inside the Item HashMaps, but only when the Recipes usually have Items.
		if (usualInputCount > 0 && inputs != null) {
			for (ItemStack tStack : inputs) {
				if (tStack != null) {
					Collection<GT_Recipe> tRecipes = recipeItemMap.get(new GT_ItemStack(tStack));
					if (tRecipes != null) {
						for (GT_Recipe tRecipe : tRecipes) {
							if (!tRecipe.fakeRecipe && tRecipe.isRecipeInputEqual(false, true, fluidInputs, inputs)) {
								return tRecipe.enabled && voltage * amperage >= tRecipe.EUt ? tRecipe : null;
							}
						}
					}
					tRecipes = recipeItemMap.get(new GT_ItemStack(GT_Utility.copyMetaData(W, tStack)));
					if (tRecipes != null) {
						for (GT_Recipe tRecipe : tRecipes) {
							if (!tRecipe.fakeRecipe && tRecipe.isRecipeInputEqual(false, true, fluidInputs, inputs)) {
								return tRecipe.enabled && voltage * amperage >= tRecipe.EUt ? tRecipe : null;
							}
						}
					}
				}
			}
		}

		// If the minimal Amount of Items for the Recipe is 0, then it could be a Fluid-Only Recipe, so check that Map too.
		if (minInputs == 0 && fluidInputs != null) {
			for (FluidStack aFluid : fluidInputs) {
				if (aFluid != null) {
					Collection<GT_Recipe> tRecipes = recipeFluidMap.get(aFluid.getFluid());
					if (tRecipes != null) {
						for (GT_Recipe tRecipe : tRecipes) {
							if (!tRecipe.fakeRecipe && tRecipe.isRecipeInputEqual(false, true, fluidInputs, inputs)) {
								return tRecipe.enabled && voltage * amperage >= tRecipe.EUt ? tRecipe : null;
							}
						}
					}
				}
			}
		}

		// And nothing has been found.
		return null;
	}

	/**
	 * Abstract Class for general Recipe Handling of non GT Recipes
	 */
	public static abstract class GT_Recipe_Map_NonGTRecipes extends RecipeMap {
		public GT_Recipe_Map_NonGTRecipes(Collection<GT_Recipe> aRecipeList, String aUnlocalizedName, String aLocalName, String aJEIName, String aJEIGUIPath, int aUsualInputCount, int aUsualOutputCount, int aMinimalInputItems, int aMinimalInputFluids, int aAmperage, String aJEISpecialValuePre, int aJEISpecialValueMultiplier, String aJEISpecialValuePost, boolean aShowVoltageAmperageInJEI, boolean aJEIAllowed) {
			super(aRecipeList, aUnlocalizedName, aLocalName, aJEIName, aJEIGUIPath, aUsualInputCount, aUsualOutputCount, aMinimalInputItems, aMinimalInputFluids, aAmperage, aJEISpecialValuePre, aJEISpecialValueMultiplier, aJEISpecialValuePost, aShowVoltageAmperageInJEI, aJEIAllowed);
		}

		@Override
		public boolean containsInput(ItemStack aStack) {
			return false;
		}

		@Override
		public boolean containsInput(FluidStack aFluid) {
			return false;
		}

		@Override
		public boolean containsInput(Fluid aFluid) {
			return false;
		}

		@Override
		public GT_Recipe addRecipe(boolean aOptimize, ItemStack[] aInputs, ItemStack[] aOutputs, Object aSpecial, int[] aOutputChances, FluidStack[] aFluidInputs, FluidStack[] aFluidOutputs, int aDuration, int aEUt, int aSpecialValue) {
			return null;
		}

		@Override
		public GT_Recipe addRecipe(boolean aOptimize, ItemStack[] aInputs, ItemStack[] aOutputs, Object aSpecial, FluidStack[] aFluidInputs, FluidStack[] aFluidOutputs, int aDuration, int aEUt, int aSpecialValue) {
			return null;
		}

		@Override
		public GT_Recipe addRecipe(GT_Recipe aRecipe) {
			return null;
		}

		@Override
		public GT_Recipe addFakeRecipe(boolean aCheckForCollisions, ItemStack[] aInputs, ItemStack[] aOutputs, Object aSpecial, int[] aOutputChances, FluidStack[] aFluidInputs, FluidStack[] aFluidOutputs, int aDuration, int aEUt, int aSpecialValue) {
			return null;
		}

		@Override
		public GT_Recipe addFakeRecipe(boolean aCheckForCollisions, ItemStack[] aInputs, ItemStack[] aOutputs, Object aSpecial, FluidStack[] aFluidInputs, FluidStack[] aFluidOutputs, int aDuration, int aEUt, int aSpecialValue) {
			return null;
		}

		@Override
		public GT_Recipe addFakeRecipe(boolean aCheckForCollisions, GT_Recipe aRecipe) {
			return null;
		}

		@Override
		public void reInit() {/**/}
	}

	/**
	 * @see gregtech.api.recipes.GT_Recipe.FuelRecipe TODO delete one of those
	 * Just a Recipe Map with Utility specifically for Fuels.
	 */
	public static class RecipeMapFuel extends RecipeMap {
		public RecipeMapFuel(Collection<GT_Recipe> aRecipeList, String aUnlocalizedName, String aLocalName, String aJEIName, String aJEIGUIPath, int aUsualInputCount, int aUsualOutputCount, int aMinimalInputItems, int aMinimalInputFluids, int aAmperage, String aJEISpecialValuePre, int aJEISpecialValueMultiplier, String aJEISpecialValuePost, boolean aShowVoltageAmperageInJEI, boolean aJEIAllowed) {
			super(aRecipeList, aUnlocalizedName, aLocalName, aJEIName, aJEIGUIPath, aUsualInputCount, aUsualOutputCount, aMinimalInputItems, aMinimalInputFluids, aAmperage, aJEISpecialValuePre, aJEISpecialValueMultiplier, aJEISpecialValuePost, aShowVoltageAmperageInJEI, aJEIAllowed);
		}

		public GT_Recipe addFuel(ItemStack aInput, ItemStack aOutput, int aFuelValueInEU) {
			return addFuel(aInput, aOutput, null, null, 10000, aFuelValueInEU);
		}

		public GT_Recipe addFuel(ItemStack aInput, ItemStack aOutput, int aChance, int aFuelValueInEU) {
			return addFuel(aInput, aOutput, null, null, aChance, aFuelValueInEU);
		}

		public GT_Recipe addFuel(FluidStack aFluidInput, FluidStack aFluidOutput, int aFuelValueInEU) {
			return addFuel(null, null, aFluidInput, aFluidOutput, 10000, aFuelValueInEU);
		}

		public GT_Recipe addFuel(ItemStack aInput, ItemStack aOutput, FluidStack aFluidInput, FluidStack aFluidOutput, int aFuelValueInEU) {
			return addFuel(aInput, aOutput, aFluidInput, aFluidOutput, 10000, aFuelValueInEU);
		}

		public GT_Recipe addFuel(ItemStack aInput, ItemStack aOutput, FluidStack aFluidInput, FluidStack aFluidOutput, int aChance, int aFuelValueInEU) {
			return addRecipe(true, new ItemStack[]{aInput}, new ItemStack[]{aOutput}, null, new int[]{aChance}, new FluidStack[]{aFluidInput}, new FluidStack[]{aFluidOutput}, 0, 0, aFuelValueInEU);
		}
	}

	/**
	 * Special Class for Furnace Recipe handling.
	 */
	public static class GT_Recipe_Map_Furnace extends GT_Recipe_Map_NonGTRecipes {
		public GT_Recipe_Map_Furnace(Collection<GT_Recipe> aRecipeList, String aUnlocalizedName, String aLocalName, String aJEIName, String aJEIGUIPath, int aUsualInputCount, int aUsualOutputCount, int aMinimalInputItems, int aMinimalInputFluids, int aAmperage, String aJEISpecialValuePre, int aJEISpecialValueMultiplier, String aJEISpecialValuePost, boolean aShowVoltageAmperageInJEI, boolean aJEIAllowed) {
			super(aRecipeList, aUnlocalizedName, aLocalName, aJEIName, aJEIGUIPath, aUsualInputCount, aUsualOutputCount, aMinimalInputItems, aMinimalInputFluids, aAmperage, aJEISpecialValuePre, aJEISpecialValueMultiplier, aJEISpecialValuePost, aShowVoltageAmperageInJEI, aJEIAllowed);
		}

		@Override
		public GT_Recipe findRecipe(IHasWorldObjectAndCoords tileEntity, GT_Recipe recipe, boolean notUnificated, long voltage, FluidStack[] fluidInputs, ItemStack specialSlot, ItemStack... inputs) {
			if (inputs == null || inputs.length <= 0 || inputs[0] == null) return null;
			if (recipe != null && recipe.isRecipeInputEqual(false, true, fluidInputs, inputs)) return recipe;
			ItemStack tOutput = GT_ModHandler.getSmeltingOutput(inputs[0], false, null);
			return tOutput == null ? null : new GT_Recipe(false, new ItemStack[]{GT_Utility.copyAmount(1, inputs[0])}, new ItemStack[]{tOutput}, null, null, null, null, 128, 4, 0);
		}

		@Override
		public boolean containsInput(ItemStack aStack) {
			return GT_ModHandler.getSmeltingOutput(aStack, false, null) != null;
		}
	}

	/**
	 * Special Class for Microwave Recipe handling.
	 */
	public static class GT_Recipe_Map_Microwave extends GT_Recipe_Map_NonGTRecipes {
		public GT_Recipe_Map_Microwave(Collection<GT_Recipe> aRecipeList, String aUnlocalizedName, String aLocalName, String aJEIName, String aJEIGUIPath, int aUsualInputCount, int aUsualOutputCount, int aMinimalInputItems, int aMinimalInputFluids, int aAmperage, String aJEISpecialValuePre, int aJEISpecialValueMultiplier, String aJEISpecialValuePost, boolean aShowVoltageAmperageInJEI, boolean aJEIAllowed) {
			super(aRecipeList, aUnlocalizedName, aLocalName, aJEIName, aJEIGUIPath, aUsualInputCount, aUsualOutputCount, aMinimalInputItems, aMinimalInputFluids, aAmperage, aJEISpecialValuePre, aJEISpecialValueMultiplier, aJEISpecialValuePost, aShowVoltageAmperageInJEI, aJEIAllowed);
		}

		@Override
		public GT_Recipe findRecipe(IHasWorldObjectAndCoords tileEntity, GT_Recipe recipe, boolean notUnificated, long voltage, FluidStack[] fluidInputs, ItemStack specialSlot, ItemStack... inputs) {
			if (inputs == null || inputs.length <= 0 || inputs[0] == null) return null;
			if (recipe != null && recipe.isRecipeInputEqual(false, true, fluidInputs, inputs)) return recipe;
			ItemStack tOutput = GT_ModHandler.getSmeltingOutput(inputs[0], false, null);

			if (GT_Utility.areStacksEqual(inputs[0], new ItemStack(Items.BOOK, 1, W))) {
				return new GT_Recipe(false, new ItemStack[]{GT_Utility.copyAmount(1, inputs[0])}, new ItemStack[]{GT_Utility.getWrittenBook("Manual_Microwave", ItemList.Book_Written_03.get(1))}, null, null, null, null, 32, 4, 0);
			}

			// Check Container Item of Input since it is around the Input, then the Input itself, then Container Item of Output and last check the Output itself
			for (ItemStack tStack : new ItemStack[]{GT_Utility.getContainerItem(inputs[0], true), inputs[0], GT_Utility.getContainerItem(tOutput, true), tOutput})
				if (tStack != null) {
					if (GT_Utility.areStacksEqual(tStack, new ItemStack(Blocks.NETHERRACK, 1, W), true)
							|| GT_Utility.areStacksEqual(tStack, new ItemStack(Blocks.TNT, 1, W), true)
							|| GT_Utility.areStacksEqual(tStack, new ItemStack(Items.EGG, 1, W), true)
							|| GT_Utility.areStacksEqual(tStack, new ItemStack(Items.FIREWORK_CHARGE, 1, W), true)
							|| GT_Utility.areStacksEqual(tStack, new ItemStack(Items.FIREWORKS, 1, W), true)
							|| GT_Utility.areStacksEqual(tStack, new ItemStack(Items.FIRE_CHARGE, 1, W), true)
							) {
						if (tileEntity instanceof IGregTechTileEntity)
							((IGregTechTileEntity) tileEntity).doExplosion(voltage * 4);
						return null;
					}
					ItemData tData = GT_OreDictUnificator.getItemData(tStack);


					if (tData != null) {
						if (tData.mMaterial != null && tData.mMaterial.mMaterial != null) {
							if (tData.mMaterial.mMaterial.contains(SubTag.METAL) || tData.mMaterial.mMaterial.contains(SubTag.EXPLOSIVE)) {
								if (tileEntity instanceof IGregTechTileEntity)
									((IGregTechTileEntity) tileEntity).doExplosion(voltage * 4);
								return null;
							}
							if (tData.mMaterial.mMaterial.contains(SubTag.FLAMMABLE)) {
								if (tileEntity instanceof IGregTechTileEntity)
									((IGregTechTileEntity) tileEntity).setOnFire();
								return null;
							}
						}
						for (MaterialStack tMaterial : tData.mByProducts)
							if (tMaterial != null) {
								if (tMaterial.mMaterial.contains(SubTag.METAL) || tMaterial.mMaterial.contains(SubTag.EXPLOSIVE)) {
									if (tileEntity instanceof IGregTechTileEntity)
										((IGregTechTileEntity) tileEntity).doExplosion(voltage * 4);
									return null;
								}
								if (tMaterial.mMaterial.contains(SubTag.FLAMMABLE)) {
									if (tileEntity instanceof IGregTechTileEntity)
										((IGregTechTileEntity) tileEntity).setOnFire();
									return null;
								}
							}
					}
					if (TileEntityFurnace.getItemBurnTime(tStack) > 0) {
						if (tileEntity instanceof IGregTechTileEntity) ((IGregTechTileEntity) tileEntity).setOnFire();
						return null;
					}

				}

			return tOutput == null ? null : new GT_Recipe(false, new ItemStack[]{GT_Utility.copyAmount(1, inputs[0])}, new ItemStack[]{tOutput}, null, null, null, null, 32, 4, 0);
		}

		@Override
		public boolean containsInput(ItemStack aStack) {
			return GT_ModHandler.getSmeltingOutput(aStack, false, null) != null;
		}
	}

	/**
	 * Special Class for Unboxinator handling.
	 */
	public static class GT_Recipe_Map_Unboxinator extends RecipeMap {
		public GT_Recipe_Map_Unboxinator(Collection<GT_Recipe> aRecipeList, String aUnlocalizedName, String aLocalName, String aJEIName, String aJEIGUIPath, int aUsualInputCount, int aUsualOutputCount, int aMinimalInputItems, int aMinimalInputFluids, int aAmperage, String aJEISpecialValuePre, int aJEISpecialValueMultiplier, String aJEISpecialValuePost, boolean aShowVoltageAmperageInJEI, boolean aJEIAllowed) {
			super(aRecipeList, aUnlocalizedName, aLocalName, aJEIName, aJEIGUIPath, aUsualInputCount, aUsualOutputCount, aMinimalInputItems, aMinimalInputFluids, aAmperage, aJEISpecialValuePre, aJEISpecialValueMultiplier, aJEISpecialValuePost, aShowVoltageAmperageInJEI, aJEIAllowed);
		}

		@Override
		public GT_Recipe findRecipe(IHasWorldObjectAndCoords tileEntity, GT_Recipe recipe, boolean notUnificated, long voltage, FluidStack[] fluidInputs, ItemStack specialSlot, ItemStack... inputs) {
			if (inputs == null || inputs.length <= 0 || !ItemList.IC2_Scrapbox.isStackEqual(inputs[0], false, true))
				return super.findRecipe(tileEntity, recipe, notUnificated, voltage, fluidInputs, specialSlot, inputs);
			ItemStack tOutput = GT_ModHandler.getRandomScrapboxDrop();
			if (tOutput == null)
				return super.findRecipe(tileEntity, recipe, notUnificated, voltage, fluidInputs, specialSlot, inputs);
			GT_Recipe rRecipe = new GT_Recipe(false, new ItemStack[]{ItemList.IC2_Scrapbox.get(1)}, new ItemStack[]{tOutput}, null, null, null, null, 16, 1, 0);
			// It is not allowed to be buffered due to the random Output
			rRecipe.canBeBuffered = false;
			// Due to its randomness it is not good if there are Items in the Output Slot, because those Items could manipulate the outcome.
			rRecipe.needsEmptyOutput = true;
			return rRecipe;
		}

		@Override
		public boolean containsInput(ItemStack aStack) {
			return ItemList.IC2_Scrapbox.isStackEqual(aStack, false, true) || super.containsInput(aStack);
		}
	}

	/**
	 * Special Class for Fluid Canner handling.
	 */
	public static class GT_Recipe_Map_FluidCanner extends RecipeMap {
		public GT_Recipe_Map_FluidCanner(Collection<GT_Recipe> aRecipeList, String aUnlocalizedName, String aLocalName, String aJEIName, String aJEIGUIPath, int aUsualInputCount, int aUsualOutputCount, int aMinimalInputItems, int aMinimalInputFluids, int aAmperage, String aJEISpecialValuePre, int aJEISpecialValueMultiplier, String aJEISpecialValuePost, boolean aShowVoltageAmperageInJEI, boolean aJEIAllowed) {
			super(aRecipeList, aUnlocalizedName, aLocalName, aJEIName, aJEIGUIPath, aUsualInputCount, aUsualOutputCount, aMinimalInputItems, aMinimalInputFluids, aAmperage, aJEISpecialValuePre, aJEISpecialValueMultiplier, aJEISpecialValuePost, aShowVoltageAmperageInJEI, aJEIAllowed);
		}

		@Override
		public GT_Recipe findRecipe(IHasWorldObjectAndCoords tileEntity, GT_Recipe recipe, boolean notUnificated, long voltage, FluidStack[] fluidInputs, ItemStack specialSlot, ItemStack... inputs) {
			GT_Recipe rRecipe = super.findRecipe(tileEntity, recipe, notUnificated, voltage, fluidInputs, specialSlot, inputs);
			if (inputs == null || inputs.length <= 0 || inputs[0] == null || rRecipe != null || !GregTech_API.sPostloadFinished)
				return rRecipe;
			if (fluidInputs != null && fluidInputs.length > 0 && fluidInputs[0] != null) {
				ItemStack tOutput = GT_Utility.fillFluidContainer(fluidInputs[0], inputs[0], false, true);
				FluidStack tFluid = GT_Utility.getFluidForFilledItem(tOutput, true);
				if (tFluid != null)
					rRecipe = new GT_Recipe(false, new ItemStack[]{GT_Utility.copyAmount(1, inputs[0])}, new ItemStack[]{tOutput}, null, null, new FluidStack[]{tFluid}, null, Math.max(tFluid.amount / 64, 16), 1, 0);
			}
			if (rRecipe == null) {
				FluidStack tFluid = GT_Utility.getFluidForFilledItem(inputs[0], true);
				if (tFluid != null)
					rRecipe = new GT_Recipe(false, new ItemStack[]{GT_Utility.copyAmount(1, inputs[0])}, new ItemStack[]{GT_Utility.getContainerItem(inputs[0], true)}, null, null, null, new FluidStack[]{tFluid}, Math.max(tFluid.amount / 64, 16), 1, 0);
			}
			if (rRecipe != null) rRecipe.canBeBuffered = false;
			return rRecipe;
		}

		@Override
		public boolean containsInput(ItemStack aStack) {
			return aStack != null && (super.containsInput(aStack) || (aStack.getItem() instanceof IFluidContainerItem && ((IFluidContainerItem) aStack.getItem()).getCapacity(aStack) > 0));
		}

		@Override
		public boolean containsInput(FluidStack aFluid) {
			return true;
		}

		@Override
		public boolean containsInput(Fluid aFluid) {
			return true;
		}
	}

	/**
	 * Special Class for Recycler Recipe handling.
	 */
	public static class GT_Recipe_Map_Recycler extends GT_Recipe_Map_NonGTRecipes {
		public GT_Recipe_Map_Recycler(Collection<GT_Recipe> aRecipeList, String aUnlocalizedName, String aLocalName, String aJEIName, String aJEIGUIPath, int aUsualInputCount, int aUsualOutputCount, int aMinimalInputItems, int aMinimalInputFluids, int aAmperage, String aJEISpecialValuePre, int aJEISpecialValueMultiplier, String aJEISpecialValuePost, boolean aShowVoltageAmperageInJEI, boolean aJEIAllowed) {
			super(aRecipeList, aUnlocalizedName, aLocalName, aJEIName, aJEIGUIPath, aUsualInputCount, aUsualOutputCount, aMinimalInputItems, aMinimalInputFluids, aAmperage, aJEISpecialValuePre, aJEISpecialValueMultiplier, aJEISpecialValuePost, aShowVoltageAmperageInJEI, aJEIAllowed);
		}

		@Override
		public GT_Recipe findRecipe(IHasWorldObjectAndCoords tileEntity, GT_Recipe recipe, boolean notUnificated, long voltage, FluidStack[] fluidInputs, ItemStack specialSlot, ItemStack... inputs) {
			if (inputs == null || inputs.length <= 0 || inputs[0] == null) return null;
			if (recipe != null && recipe.isRecipeInputEqual(false, true, fluidInputs, inputs)) return recipe;
			return new GT_Recipe(false, new ItemStack[]{GT_Utility.copyAmount(1, inputs[0])}, GT_ModHandler.getRecyclerOutput(GT_Utility.copyAmount(64, inputs[0]), 0) == null ? null : new ItemStack[]{ItemList.IC2_Scrap.get(1)}, null, new int[]{1250}, null, null, 45, 1, 0);
		}

		@Override
		public boolean containsInput(ItemStack aStack) {
			return GT_ModHandler.getRecyclerOutput(GT_Utility.copyAmount(64, aStack), 0) != null;
		}
	}

	/**
	 * Special Class for Compressor Recipe handling.
	 */
	public static class GT_Recipe_Map_Compressor extends GT_Recipe_Map_NonGTRecipes {
		public GT_Recipe_Map_Compressor(Collection<GT_Recipe> aRecipeList, String aUnlocalizedName, String aLocalName, String aJEIName, String aJEIGUIPath, int aUsualInputCount, int aUsualOutputCount, int aMinimalInputItems, int aMinimalInputFluids, int aAmperage, String aJEISpecialValuePre, int aJEISpecialValueMultiplier, String aJEISpecialValuePost, boolean aShowVoltageAmperageInJEI, boolean aJEIAllowed) {
			super(aRecipeList, aUnlocalizedName, aLocalName, aJEIName, aJEIGUIPath, aUsualInputCount, aUsualOutputCount, aMinimalInputItems, aMinimalInputFluids, aAmperage, aJEISpecialValuePre, aJEISpecialValueMultiplier, aJEISpecialValuePost, aShowVoltageAmperageInJEI, aJEIAllowed);
		}

		@Override
		public GT_Recipe findRecipe(IHasWorldObjectAndCoords tileEntity, GT_Recipe recipe, boolean notUnificated, long voltage, FluidStack[] fluidInputs, ItemStack specialSlot, ItemStack... inputs) {
			if (inputs == null || inputs.length <= 0 || inputs[0] == null) return null;
			if (recipe != null && recipe.isRecipeInputEqual(false, true, fluidInputs, inputs)) return recipe;
			ItemStack tComparedInput = GT_Utility.copy(inputs[0]);
			ItemStack[] tOutputItems = GT_ModHandler.getMachineOutput(tComparedInput, ic2.api.recipe.Recipes.compressor.getRecipes(), true, new NBTTagCompound(), null, null, null);
			return GT_Utility.arrayContainsNonNull(tOutputItems) ? new GT_Recipe(false, new ItemStack[]{GT_Utility.copyAmount(inputs[0].stackSize - tComparedInput.stackSize, inputs[0])}, tOutputItems, null, null, null, null, 400, 2, 0) : null;
		}

		@Override
		public boolean containsInput(ItemStack aStack) {
			return GT_Utility.arrayContainsNonNull(GT_ModHandler.getMachineOutput(GT_Utility.copyAmount(64, aStack), ic2.api.recipe.Recipes.compressor.getRecipes(), false, new NBTTagCompound(), null, null, null));
		}
	}

	/**
	 * Special Class for Extractor Recipe handling.
	 */
	public static class GT_Recipe_Map_Extractor extends GT_Recipe_Map_NonGTRecipes {
		public GT_Recipe_Map_Extractor(Collection<GT_Recipe> aRecipeList, String aUnlocalizedName, String aLocalName, String aJEIName, String aJEIGUIPath, int aUsualInputCount, int aUsualOutputCount, int aMinimalInputItems, int aMinimalInputFluids, int aAmperage, String aJEISpecialValuePre, int aJEISpecialValueMultiplier, String aJEISpecialValuePost, boolean aShowVoltageAmperageInJEI, boolean aJEIAllowed) {
			super(aRecipeList, aUnlocalizedName, aLocalName, aJEIName, aJEIGUIPath, aUsualInputCount, aUsualOutputCount, aMinimalInputItems, aMinimalInputFluids, aAmperage, aJEISpecialValuePre, aJEISpecialValueMultiplier, aJEISpecialValuePost, aShowVoltageAmperageInJEI, aJEIAllowed);
		}

		@Override
		public GT_Recipe findRecipe(IHasWorldObjectAndCoords tileEntity, GT_Recipe recipe, boolean notUnificated, long voltage, FluidStack[] fluidInputs, ItemStack specialSlot, ItemStack... inputs) {
			if (inputs == null || inputs.length <= 0 || inputs[0] == null) return null;
			if (recipe != null && recipe.isRecipeInputEqual(false, true, fluidInputs, inputs)) return recipe;
			ItemStack tComparedInput = GT_Utility.copy(inputs[0]);
			ItemStack[] tOutputItems = GT_ModHandler.getMachineOutput(tComparedInput, ic2.api.recipe.Recipes.extractor.getRecipes(), true, new NBTTagCompound(), null, null, null);
			return GT_Utility.arrayContainsNonNull(tOutputItems) ? new GT_Recipe(false, new ItemStack[]{GT_Utility.copyAmount(inputs[0].stackSize - tComparedInput.stackSize, inputs[0])}, tOutputItems, null, null, null, null, 400, 2, 0) : null;
		}

		@Override
		public boolean containsInput(ItemStack aStack) {
			return GT_Utility.arrayContainsNonNull(GT_ModHandler.getMachineOutput(GT_Utility.copyAmount(64, aStack), ic2.api.recipe.Recipes.extractor.getRecipes(), false, new NBTTagCompound(), null, null, null));
		}
	}

	/**
	 * Special Class for Thermal Centrifuge Recipe handling.
	 */
	public static class GT_Recipe_Map_ThermalCentrifuge extends GT_Recipe_Map_NonGTRecipes {
		public GT_Recipe_Map_ThermalCentrifuge(Collection<GT_Recipe> aRecipeList, String aUnlocalizedName, String aLocalName, String aJEIName, String aJEIGUIPath, int aUsualInputCount, int aUsualOutputCount, int aMinimalInputItems, int aMinimalInputFluids, int aAmperage, String aJEISpecialValuePre, int aJEISpecialValueMultiplier, String aJEISpecialValuePost, boolean aShowVoltageAmperageInJEI, boolean aJEIAllowed) {
			super(aRecipeList, aUnlocalizedName, aLocalName, aJEIName, aJEIGUIPath, aUsualInputCount, aUsualOutputCount, aMinimalInputItems, aMinimalInputFluids, aAmperage, aJEISpecialValuePre, aJEISpecialValueMultiplier, aJEISpecialValuePost, aShowVoltageAmperageInJEI, aJEIAllowed);
		}

		@Override
		public GT_Recipe findRecipe(IHasWorldObjectAndCoords tileEntity, GT_Recipe recipe, boolean notUnificated, long voltage, FluidStack[] fluidInputs, ItemStack specialSlot, ItemStack... inputs) {
			if (inputs == null || inputs.length <= 0 || inputs[0] == null) return null;
			if (recipe != null && recipe.isRecipeInputEqual(false, true, fluidInputs, inputs)) return recipe;
			ItemStack tComparedInput = GT_Utility.copy(inputs[0]);
			ItemStack[] tOutputItems = GT_ModHandler.getMachineOutput(tComparedInput, ic2.api.recipe.Recipes.centrifuge.getRecipes(), true, new NBTTagCompound(), null, null, null);
			return GT_Utility.arrayContainsNonNull(tOutputItems) ? new GT_Recipe(false, new ItemStack[]{GT_Utility.copyAmount(inputs[0].stackSize - tComparedInput.stackSize, inputs[0])}, tOutputItems, null, null, null, null, 400, 48, 0) : null;
		}

		@Override
		public boolean containsInput(ItemStack aStack) {
			return GT_Utility.arrayContainsNonNull(GT_ModHandler.getMachineOutput(GT_Utility.copyAmount(64, aStack), ic2.api.recipe.Recipes.centrifuge.getRecipes(), false, new NBTTagCompound(), null, null, null));
		}
	}

	/**
	 * Special Class for Ore Washer Recipe handling.
	 */
	public static class GT_Recipe_Map_OreWasher extends GT_Recipe_Map_NonGTRecipes {
		public GT_Recipe_Map_OreWasher(Collection<GT_Recipe> aRecipeList, String aUnlocalizedName, String aLocalName, String aJEIName, String aJEIGUIPath, int aUsualInputCount, int aUsualOutputCount, int aMinimalInputItems, int aMinimalInputFluids, int aAmperage, String aJEISpecialValuePre, int aJEISpecialValueMultiplier, String aJEISpecialValuePost, boolean aShowVoltageAmperageInJEI, boolean aJEIAllowed) {
			super(aRecipeList, aUnlocalizedName, aLocalName, aJEIName, aJEIGUIPath, aUsualInputCount, aUsualOutputCount, aMinimalInputItems, aMinimalInputFluids, aAmperage, aJEISpecialValuePre, aJEISpecialValueMultiplier, aJEISpecialValuePost, aShowVoltageAmperageInJEI, aJEIAllowed);
		}

		@Override
		public GT_Recipe findRecipe(IHasWorldObjectAndCoords tileEntity, GT_Recipe recipe, boolean notUnificated, long voltage, FluidStack[] fluidInputs, ItemStack specialSlot, ItemStack... inputs) {
			if (inputs == null || inputs.length <= 0 || inputs[0] == null || fluidInputs == null || fluidInputs.length < 1 || !GT_ModHandler.isWater(fluidInputs[0]))
				return null;
			if (recipe != null && recipe.isRecipeInputEqual(false, true, fluidInputs, inputs)) return recipe;
			ItemStack tComparedInput = GT_Utility.copy(inputs[0]);
			NBTTagCompound aRecipeMetaData = new NBTTagCompound();
			ItemStack[] tOutputItems = GT_ModHandler.getMachineOutput(tComparedInput, ic2.api.recipe.Recipes.oreWashing.getRecipes(), true, aRecipeMetaData, null, null, null);
			return GT_Utility.arrayContainsNonNull(tOutputItems) ? new GT_Recipe(false, new ItemStack[]{GT_Utility.copyAmount(inputs[0].stackSize - tComparedInput.stackSize, inputs[0])}, tOutputItems, null, null, new FluidStack[]{new FluidStack(fluidInputs[0].getFluid(), ((NBTTagCompound) aRecipeMetaData.getTag("return")).getInteger("amount"))}, null, 400, 16, 0) : null;
		}

		@Override
		public boolean containsInput(ItemStack aStack) {
			return GT_Utility.arrayContainsNonNull(GT_ModHandler.getMachineOutput(GT_Utility.copyAmount(64, aStack), ic2.api.recipe.Recipes.oreWashing.getRecipes(), false, new NBTTagCompound(), null, null, null));
		}

		@Override
		public boolean containsInput(FluidStack aFluid) {
			return GT_ModHandler.isWater(aFluid);
		}

		@Override
		public boolean containsInput(Fluid aFluid) {
			return GT_ModHandler.isWater(new FluidStack(aFluid, 0));
		}
	}

	/**
	 * Special Class for Macerator/RockCrusher Recipe handling.
	 */
	public static class GT_Recipe_Map_Macerator extends RecipeMap {
		public GT_Recipe_Map_Macerator(Collection<GT_Recipe> aRecipeList, String aUnlocalizedName, String aLocalName, String aJEIName, String aJEIGUIPath, int aUsualInputCount, int aUsualOutputCount, int aMinimalInputItems, int aMinimalInputFluids, int aAmperage, String aJEISpecialValuePre, int aJEISpecialValueMultiplier, String aJEISpecialValuePost, boolean aShowVoltageAmperageInJEI, boolean aJEIAllowed) {
			super(aRecipeList, aUnlocalizedName, aLocalName, aJEIName, aJEIGUIPath, aUsualInputCount, aUsualOutputCount, aMinimalInputItems, aMinimalInputFluids, aAmperage, aJEISpecialValuePre, aJEISpecialValueMultiplier, aJEISpecialValuePost, aShowVoltageAmperageInJEI, aJEIAllowed);
		}

		@Override
		public GT_Recipe findRecipe(IHasWorldObjectAndCoords tileEntity, GT_Recipe recipe, boolean notUnificated, long voltage, FluidStack[] fluidInputs, ItemStack specialSlot, ItemStack... inputs) {
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

			ItemStack tComparedInput = GT_Utility.copy(inputs[0]);
			ItemStack[] tOutputItems = GT_ModHandler.getMachineOutput(tComparedInput, ic2.api.recipe.Recipes.macerator.getRecipes(), true, new NBTTagCompound(), null, null, null);
			return GT_Utility.arrayContainsNonNull(tOutputItems) ? new GT_Recipe(false, new ItemStack[]{GT_Utility.copyAmount(inputs[0].stackSize - tComparedInput.stackSize, inputs[0])}, tOutputItems, null, null, null, null, 400, 2, 0) : null;
		}

		@Override
		public boolean containsInput(ItemStack aStack) {
			return super.containsInput(aStack) || GT_Utility.arrayContainsNonNull(GT_ModHandler.getMachineOutput(GT_Utility.copyAmount(64, aStack), ic2.api.recipe.Recipes.macerator.getRecipes(), false, new NBTTagCompound(), null, null, null));
		}
	}

	/**
	 * Special Class for Assembler handling.
	 */
	public static class GT_Recipe_Map_Assembler extends RecipeMap {
		public GT_Recipe_Map_Assembler(Collection<GT_Recipe> aRecipeList, String aUnlocalizedName, String aLocalName, String aJEIName, String aJEIGUIPath, int aUsualInputCount, int aUsualOutputCount, int aMinimalInputItems, int aMinimalInputFluids, int aAmperage, String aJEISpecialValuePre, int aJEISpecialValueMultiplier, String aJEISpecialValuePost, boolean aShowVoltageAmperageInJEI, boolean aJEIAllowed) {
			super(aRecipeList, aUnlocalizedName, aLocalName, aJEIName, aJEIGUIPath, aUsualInputCount, aUsualOutputCount, aMinimalInputItems, aMinimalInputFluids, aAmperage, aJEISpecialValuePre, aJEISpecialValueMultiplier, aJEISpecialValuePost, aShowVoltageAmperageInJEI, aJEIAllowed);
		}

		@Override
		public GT_Recipe findRecipe(IHasWorldObjectAndCoords tileEntity, GT_Recipe recipe, boolean notUnificated, long voltage, FluidStack[] fluidInputs, ItemStack specialSlot, ItemStack... inputs) {
			GT_Recipe rRecipe = super.findRecipe(tileEntity, recipe, notUnificated, voltage, fluidInputs, specialSlot, inputs);
			if (inputs == null || inputs.length <= 0 || inputs[0] == null || rRecipe == null || !GregTech_API.sPostloadFinished)
				return rRecipe;
			for (ItemStack aInput : inputs) {
				if (ItemList.Paper_Printed_Pages.isStackEqual(aInput, false, true)) {
					rRecipe = rRecipe.copy();
					rRecipe.canBeBuffered = false;
					rRecipe.outputs[0].setTagCompound(aInput.getTagCompound());
				}
			}
			return rRecipe;
		}
	}

	/**
	 * Special Class for Forming Press handling.
	 */
	public static class GT_Recipe_Map_FormingPress extends RecipeMap {
		public GT_Recipe_Map_FormingPress(Collection<GT_Recipe> aRecipeList, String aUnlocalizedName, String aLocalName, String aJEIName, String aJEIGUIPath, int aUsualInputCount, int aUsualOutputCount, int aMinimalInputItems, int aMinimalInputFluids, int aAmperage, String aJEISpecialValuePre, int aJEISpecialValueMultiplier, String aJEISpecialValuePost, boolean aShowVoltageAmperageInJEI, boolean aJEIAllowed) {
			super(aRecipeList, aUnlocalizedName, aLocalName, aJEIName, aJEIGUIPath, aUsualInputCount, aUsualOutputCount, aMinimalInputItems, aMinimalInputFluids, aAmperage, aJEISpecialValuePre, aJEISpecialValueMultiplier, aJEISpecialValuePost, aShowVoltageAmperageInJEI, aJEIAllowed);
		}

		@Override
		public GT_Recipe findRecipe(IHasWorldObjectAndCoords tileEntity, GT_Recipe recipe, boolean notUnificated, long voltage, FluidStack[] fluidInputs, ItemStack specialSlot, ItemStack... inputs) {
			GT_Recipe rRecipe = super.findRecipe(tileEntity, recipe, notUnificated, voltage, fluidInputs, specialSlot, inputs);
			if (inputs == null || inputs.length < 2 || inputs[0] == null || inputs[1] == null || !GregTech_API.sPostloadFinished)
				return rRecipe;
			if (rRecipe == null) {
				if (ItemList.Shape_Mold_Name.isStackEqual(inputs[0], false, true)) {
					ItemStack tOutput = GT_Utility.copyAmount(1, inputs[1]);
					tOutput.setStackDisplayName(inputs[0].getDisplayName());
					rRecipe = new GT_Recipe(false, new ItemStack[]{ItemList.Shape_Mold_Name.get(0), GT_Utility.copyAmount(1, inputs[1])}, new ItemStack[]{tOutput}, null, null, null, null, 128, 8, 0);
					rRecipe.canBeBuffered = false;
					return rRecipe;
				}
				if (ItemList.Shape_Mold_Name.isStackEqual(inputs[1], false, true)) {
					ItemStack tOutput = GT_Utility.copyAmount(1, inputs[0]);
					tOutput.setStackDisplayName(inputs[1].getDisplayName());
					rRecipe = new GT_Recipe(false, new ItemStack[]{ItemList.Shape_Mold_Name.get(0), GT_Utility.copyAmount(1, inputs[0])}, new ItemStack[]{tOutput}, null, null, null, null, 128, 8, 0);
					rRecipe.canBeBuffered = false;
					return rRecipe;
				}
				return null;
			}
			for (ItemStack aMold : inputs) {
				if (ItemList.Shape_Mold_Credit.isStackEqual(aMold, false, true)) {
					NBTTagCompound tNBT = aMold.getTagCompound();
					if (tNBT == null) tNBT = new NBTTagCompound();
					if (!tNBT.hasKey("credit_security_id")) tNBT.setLong("credit_security_id", System.nanoTime());
					aMold.setTagCompound(tNBT);

					rRecipe = rRecipe.copy();
					rRecipe.canBeBuffered = false;
					rRecipe.outputs[0].setTagCompound(tNBT);
					return rRecipe;
				}
			}
			return rRecipe;
		}
	}

	/**
	 * Special Class for Printer handling.
	 */
	public static class GT_Recipe_Map_Printer extends RecipeMap {
		public GT_Recipe_Map_Printer(Collection<GT_Recipe> aRecipeList, String aUnlocalizedName, String aLocalName, String aJEIName, String aJEIGUIPath, int aUsualInputCount, int aUsualOutputCount, int aMinimalInputItems, int aMinimalInputFluids, int aAmperage, String aJEISpecialValuePre, int aJEISpecialValueMultiplier, String aJEISpecialValuePost, boolean aShowVoltageAmperageInJEI, boolean aJEIAllowed) {
			super(aRecipeList, aUnlocalizedName, aLocalName, aJEIName, aJEIGUIPath, aUsualInputCount, aUsualOutputCount, aMinimalInputItems, aMinimalInputFluids, aAmperage, aJEISpecialValuePre, aJEISpecialValueMultiplier, aJEISpecialValuePost, aShowVoltageAmperageInJEI, aJEIAllowed);
		}

		@Override
		public GT_Recipe findRecipe(IHasWorldObjectAndCoords tileEntity, GT_Recipe recipe, boolean notUnificated, long voltage, FluidStack[] fluidInputs, ItemStack specialSlot, ItemStack... inputs) {
			GT_Recipe rRecipe = super.findRecipe(tileEntity, recipe, notUnificated, voltage, fluidInputs, specialSlot, inputs);
			if (inputs == null || inputs.length <= 0 || inputs[0] == null || fluidInputs == null || fluidInputs.length <= 0 || fluidInputs[0] == null || !GregTech_API.sPostloadFinished)
				return rRecipe;

			Dyes aDye = null;
			for (Dyes tDye : Dyes.VALUES)
				if (tDye.isFluidDye(fluidInputs[0])) {
					aDye = tDye;
					break;
				}

			if (aDye == null) return rRecipe;

			if (rRecipe == null) {
				ItemStack tOutput = GT_ModHandler.getAllRecipeOutput(tileEntity == null ? null : tileEntity.getWorldObj(), inputs[0], inputs[0], inputs[0], inputs[0], ItemList.DYE_ONLY_ITEMS[aDye.mIndex].get(1), inputs[0], inputs[0], inputs[0], inputs[0]);
				if (tOutput != null)
					return addRecipe(new GT_Recipe(true, new ItemStack[]{GT_Utility.copyAmount(8, inputs[0])}, new ItemStack[]{tOutput}, null, null, new FluidStack[]{new FluidStack(fluidInputs[0].getFluid(), (int) L)}, null, 256, 2, 0), false, false, true);

				tOutput = GT_ModHandler.getAllRecipeOutput(tileEntity == null ? null : tileEntity.getWorldObj(), inputs[0], ItemList.DYE_ONLY_ITEMS[aDye.mIndex].get(1));
				if (tOutput != null)
					return addRecipe(new GT_Recipe(true, new ItemStack[]{GT_Utility.copyAmount(1, inputs[0])}, new ItemStack[]{tOutput}, null, null, new FluidStack[]{new FluidStack(fluidInputs[0].getFluid(), (int) L)}, null, 32, 2, 0), false, false, true);
			} else {
				if (inputs[0].getItem() == Items.WRITTEN_BOOK) {
					if (!ItemList.Tool_DataStick.isStackEqual(specialSlot, false, true)) return null;
					NBTTagCompound tNBT = specialSlot.getTagCompound();
					if (tNBT == null || GT_Utility.isStringInvalid(tNBT.getString("title")) || GT_Utility.isStringInvalid(tNBT.getString("author")))
						return null;

					rRecipe = rRecipe.copy();
					rRecipe.canBeBuffered = false;
					rRecipe.outputs[0].setTagCompound(tNBT);
					return rRecipe;
				}
				if (inputs[0].getItem() == Items.FILLED_MAP) {
					if (!ItemList.Tool_DataStick.isStackEqual(specialSlot, false, true)) return null;
					NBTTagCompound tNBT = specialSlot.getTagCompound();
					if (tNBT == null || !tNBT.hasKey("map_id")) return null;

					rRecipe = rRecipe.copy();
					rRecipe.canBeBuffered = false;
					rRecipe.outputs[0].setItemDamage(tNBT.getShort("map_id"));
					return rRecipe;
				}
				if (ItemList.Paper_Punch_Card_Empty.isStackEqual(inputs[0], false, true)) {
					if (!ItemList.Tool_DataStick.isStackEqual(specialSlot, false, true)) return null;
					NBTTagCompound tNBT = specialSlot.getTagCompound();
					if (tNBT == null || !tNBT.hasKey("GT.PunchCardData")) return null;

					rRecipe = rRecipe.copy();
					rRecipe.canBeBuffered = false;
					rRecipe.outputs[0].setTagCompound(GT_Utility.getNBTContainingString(new NBTTagCompound(), "GT.PunchCardData", tNBT.getString("GT.PunchCardData")));
					return rRecipe;
				}
			}
			return rRecipe;
		}

		@Override
		public boolean containsInput(ItemStack aStack) {
			return true;
		}

		@Override
		public boolean containsInput(FluidStack aFluid) {
			return super.containsInput(aFluid) || Dyes.isAnyFluidDye(aFluid);
		}

		@Override
		public boolean containsInput(Fluid aFluid) {
			return super.containsInput(aFluid) || Dyes.isAnyFluidDye(aFluid);
		}
	}
}
