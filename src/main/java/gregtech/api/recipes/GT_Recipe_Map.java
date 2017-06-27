package gregtech.api.recipes;

import gregtech.api.GregTech_API;
import gregtech.api.enums.Dyes;
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
import static gregtech.api.enums.GT_Values.RES_PATH_GUI;
import static gregtech.api.enums.GT_Values.W;

/**
 * @author exidex.
 * @since 11.06.2017.
 */
public class GT_Recipe_Map {
	/**
	 * Contains all Recipe Maps
	 */
	public static final Collection<GT_Recipe_Map> sMappings = new ArrayList<GT_Recipe_Map>();

	public static final GT_Recipe_Map sOreWasherRecipes = new GT_Recipe_Map_OreWasher(new HashSet<GT_Recipe>(0), "ic.recipe.orewasher", "Ore Washer", "ic2.blockOreWashingPlant", RES_PATH_GUI + "basicmachines/OreWasher", 1, 3, 1, 1, 1, E, 1, E, true, false);
	public static final GT_Recipe_Map sThermalCentrifugeRecipes = new GT_Recipe_Map_ThermalCentrifuge(new HashSet<GT_Recipe>(0), "ic.recipe.thermalcentrifuge", "Thermal Centrifuge", "ic2.blockCentrifuge", RES_PATH_GUI + "basicmachines/ThermalCentrifuge", 1, 3, 1, 0, 2, E, 1, E, true, false);
	public static final GT_Recipe_Map sCompressorRecipes = new GT_Recipe_Map_Compressor(new HashSet<GT_Recipe>(0), "ic.recipe.compressor", "Compressor", "ic2.compressor", RES_PATH_GUI + "basicmachines/Compressor", 1, 1, 1, 0, 1, E, 1, E, true, false);
	public static final GT_Recipe_Map sExtractorRecipes = new GT_Recipe_Map_Extractor(new HashSet<GT_Recipe>(0), "ic.recipe.extractor", "Extractor", "ic2.extractor", RES_PATH_GUI + "basicmachines/Extractor", 1, 1, 1, 0, 1, E, 1, E, true, false);
	public static final GT_Recipe_Map sRecyclerRecipes = new GT_Recipe_Map_Recycler(new HashSet<GT_Recipe>(0), "ic.recipe.recycler", "Recycler", "ic2.recycler", RES_PATH_GUI + "basicmachines/Recycler", 1, 1, 1, 0, 1, E, 1, E, true, false);
	public static final GT_Recipe_Map sFurnaceRecipes = new GT_Recipe_Map_Furnace(new HashSet<GT_Recipe>(0), "mc.recipe.furnace", "Furnace", "smelting", RES_PATH_GUI + "basicmachines/E_Furnace", 1, 1, 1, 0, 1, E, 1, E, true, false);
	public static final GT_Recipe_Map sMicrowaveRecipes = new GT_Recipe_Map_Microwave(new HashSet<GT_Recipe>(0), "gt.recipe.microwave", "Microwave", "smelting", RES_PATH_GUI + "basicmachines/E_Furnace", 1, 1, 1, 0, 1, E, 1, E, true, false);

	public static final GT_Recipe_Map sScannerFakeRecipes = new GT_Recipe_Map(new HashSet<GT_Recipe>(300), "gt.recipe.scanner", "Scanner", null, RES_PATH_GUI + "basicmachines/Scanner", 1, 1, 1, 0, 1, E, 1, E, true, true);
	public static final GT_Recipe_Map sRockBreakerFakeRecipes = new GT_Recipe_Map(new HashSet<GT_Recipe>(3), "gt.recipe.rockbreaker", "Rock Breaker", null, RES_PATH_GUI + "basicmachines/RockBreaker", 1, 1, 0, 0, 1, E, 1, E, true, true);
	public static final GT_Recipe_Map sByProductList = new GT_Recipe_Map(new HashSet<GT_Recipe>(1000), "gt.recipe.byproductlist", "Ore Byproduct List", null, RES_PATH_GUI + "basicmachines/Default", 1, 6, 1, 0, 1, E, 1, E, true, true);
	public static final GT_Recipe_Map sRepicatorFakeRecipes = new GT_Recipe_Map(new HashSet<GT_Recipe>(100), "gt.recipe.replicator", "Replicator", null, RES_PATH_GUI + "basicmachines/Replicator", 0, 1, 0, 1, 1, E, 1, E, true, true);
	public static final GT_Recipe_Map sAssemblylineFakeRecipes = new GT_Recipe_Map(new HashSet<GT_Recipe>(30), "gt.recipe.assemblyline", "Assembly Line", null, RES_PATH_GUI + "basicmachines/Default", 1, 1, 1, 0, 1, E, 1, E, true, true);

	public static final GT_Recipe_Map sPlasmaArcFurnaceRecipes = new GT_Recipe_Map(new HashSet<GT_Recipe>(10000), "gt.recipe.plasmaarcfurnace", "Plasma Arc Furnace", null, RES_PATH_GUI + "basicmachines/PlasmaArcFurnace", 1, 4, 1, 1, 1, E, 1, E, true, true);
	public static final GT_Recipe_Map sArcFurnaceRecipes = new GT_Recipe_Map(new HashSet<GT_Recipe>(10000), "gt.recipe.arcfurnace", "Arc Furnace", null, RES_PATH_GUI + "basicmachines/ArcFurnace", 1, 4, 1, 1, 3, E, 1, E, true, true);
	public static final GT_Recipe_Map sPrinterRecipes = new GT_Recipe_Map_Printer(new HashSet<GT_Recipe>(100), "gt.recipe.printer", "Printer", null, RES_PATH_GUI + "basicmachines/Printer", 1, 1, 1, 1, 1, E, 1, E, true, true);
	public static final GT_Recipe_Map sSifterRecipes = new GT_Recipe_Map(new HashSet<GT_Recipe>(100), "gt.recipe.sifter", "Sifter", null, RES_PATH_GUI + "basicmachines/Sifter", 1, 9, 1, 0, 1, E, 1, E, true, true);
	public static final GT_Recipe_Map sPressRecipes = new GT_Recipe_Map_FormingPress(new HashSet<GT_Recipe>(100), "gt.recipe.press", "Forming Press", null, RES_PATH_GUI + "basicmachines/Press", 2, 1, 2, 0, 1, E, 1, E, true, true);
	public static final GT_Recipe_Map sLaserEngraverRecipes = new GT_Recipe_Map(new HashSet<GT_Recipe>(100), "gt.recipe.laserengraver", "Precision Laser Engraver", null, RES_PATH_GUI + "basicmachines/LaserEngraver", 2, 1, 2, 0, 1, E, 1, E, true, true);
	public static final GT_Recipe_Map sMixerRecipes = new GT_Recipe_Map(new HashSet<GT_Recipe>(100), "gt.recipe.mixer", "Mixer", null, RES_PATH_GUI + "basicmachines/Mixer", 4, 1, 1, 0, 1, E, 1, E, true, true);
	public static final GT_Recipe_Map sAutoclaveRecipes = new GT_Recipe_Map(new HashSet<GT_Recipe>(200), "gt.recipe.autoclave", "Autoclave", null, RES_PATH_GUI + "basicmachines/Autoclave", 1, 1, 1, 1, 1, E, 1, E, true, true);
	public static final GT_Recipe_Map sElectroMagneticSeparatorRecipes = new GT_Recipe_Map(new HashSet<GT_Recipe>(50), "gt.recipe.electromagneticseparator", "Electromagnetic Separator", null, RES_PATH_GUI + "basicmachines/ElectromagneticSeparator", 1, 3, 1, 0, 1, E, 1, E, true, true);
	public static final GT_Recipe_Map sPolarizerRecipes = new GT_Recipe_Map(new HashSet<GT_Recipe>(100), "gt.recipe.polarizer", "Electromagnetic Polarizer", null, RES_PATH_GUI + "basicmachines/Polarizer", 1, 1, 1, 0, 1, E, 1, E, true, true);
	public static final GT_Recipe_Map sMaceratorRecipes = new GT_Recipe_Map_Macerator(new HashSet<GT_Recipe>(10000), "gt.recipe.macerator", "Pulverization", null, RES_PATH_GUI + "basicmachines/Macerator4", 1, 4, 1, 0, 1, E, 1, E, true, true);
	public static final GT_Recipe_Map sChemicalBathRecipes = new GT_Recipe_Map(new HashSet<GT_Recipe>(200), "gt.recipe.chemicalbath", "Chemical Bath", null, RES_PATH_GUI + "basicmachines/ChemicalBath", 1, 3, 1, 1, 1, E, 1, E, true, true);
	public static final GT_Recipe_Map sFluidCannerRecipes = new GT_Recipe_Map_FluidCanner(new HashSet<GT_Recipe>(100), "gt.recipe.fluidcanner", "Fluid Canning Machine", null, RES_PATH_GUI + "basicmachines/FluidCannerNEI", 1, 1, 1, 0, 1, E, 1, E, true, true);
	public static final GT_Recipe_Map sBrewingRecipes = new GT_Recipe_Map(new HashSet<GT_Recipe>(100), "gt.recipe.brewer", "Brewing Machine", null, RES_PATH_GUI + "basicmachines/PotionBrewer", 1, 0, 1, 1, 1, E, 1, E, true, true);
	public static final GT_Recipe_Map sFluidHeaterRecipes = new GT_Recipe_Map(new HashSet<GT_Recipe>(100), "gt.recipe.fluidheater", "Fluid Heater", null, RES_PATH_GUI + "basicmachines/FluidHeater", 1, 0, 1, 1, 1, E, 1, E, true, true);
	public static final GT_Recipe_Map sDistilleryRecipes = new GT_Recipe_Map(new HashSet<GT_Recipe>(100), "gt.recipe.distillery", "Distillery", null, RES_PATH_GUI + "basicmachines/Distillery", 1, 0, 1, 1, 1, E, 1, E, true, true);
	public static final GT_Recipe_Map sFermentingRecipes = new GT_Recipe_Map(new HashSet<GT_Recipe>(100), "gt.recipe.fermenter", "Fermenter", null, RES_PATH_GUI + "basicmachines/Fermenter", 0, 0, 0, 1, 1, E, 1, E, true, true);
	public static final GT_Recipe_Map sFluidSolidficationRecipes = new GT_Recipe_Map(new HashSet<GT_Recipe>(100), "gt.recipe.fluidsolidifier", "Fluid Solidifier", null, RES_PATH_GUI + "basicmachines/FluidSolidifier", 1, 1, 1, 1, 1, E, 1, E, true, true);
	public static final GT_Recipe_Map sFluidExtractionRecipes = new GT_Recipe_Map(new HashSet<GT_Recipe>(100), "gt.recipe.fluidextractor", "Fluid Extractor", null, RES_PATH_GUI + "basicmachines/FluidExtractor", 1, 1, 1, 0, 1, E, 1, E, true, true);
	public static final GT_Recipe_Map sBoxinatorRecipes = new GT_Recipe_Map(new HashSet<GT_Recipe>(2500), "gt.recipe.packager", "Packager", null, RES_PATH_GUI + "basicmachines/Packager", 2, 1, 2, 0, 1, E, 1, E, true, true);
	public static final GT_Recipe_Map sUnboxinatorRecipes = new GT_Recipe_Map_Unboxinator(new HashSet<GT_Recipe>(2500), "gt.recipe.unpackager", "Unpackager", null, RES_PATH_GUI + "basicmachines/Unpackager", 1, 2, 1, 0, 1, E, 1, E, true, true);
	public static final GT_Recipe_Map sFusionRecipes = new GT_Recipe_Map(new HashSet<GT_Recipe>(50), "gt.recipe.fusionreactor", "Fusion Reactor", null, RES_PATH_GUI + "basicmachines/Default", 0, 0, 0, 2, 1, "Start: ", 1, " EU", true, true);
	public static final GT_Recipe_Map sCentrifugeRecipes = new GT_Recipe_Map(new HashSet<GT_Recipe>(1000), "gt.recipe.centrifuge", "Centrifuge", null, RES_PATH_GUI + "basicmachines/Centrifuge", 2, 6, 0, 0, 1, E, 1, E, true, true);
	public static final GT_Recipe_Map sElectrolyzerRecipes = new GT_Recipe_Map(new HashSet<GT_Recipe>(200), "gt.recipe.electrolyzer", "Electrolyzer", null, RES_PATH_GUI + "basicmachines/Electrolyzer", 2, 6, 0, 0, 1, E, 1, E, true, true);
	public static final GT_Recipe_Map sBlastRecipes = new GT_Recipe_Map(new HashSet<GT_Recipe>(500), "gt.recipe.blastfurnace", "Blast Furnace", null, RES_PATH_GUI + "basicmachines/Default", 2, 2, 1, 0, 1, "Heat Capacity: ", 1, " K", false, true);
	public static final GT_Recipe_Map sImplosionRecipes = new GT_Recipe_Map(new HashSet<GT_Recipe>(50), "gt.recipe.implosioncompressor", "Implosion Compressor", null, RES_PATH_GUI + "basicmachines/Default", 2, 2, 2, 0, 1, E, 1, E, true, true);
	public static final GT_Recipe_Map sVacuumRecipes = new GT_Recipe_Map(new HashSet<GT_Recipe>(100), "gt.recipe.vacuumfreezer", "Vacuum Freezer", null, RES_PATH_GUI + "basicmachines/Default", 1, 1, 1, 0, 1, E, 1, E, true, true);
	public static final GT_Recipe_Map sChemicalRecipes = new GT_Recipe_Map(new HashSet<GT_Recipe>(100), "gt.recipe.chemicalreactor", "Chemical Reactor", null, RES_PATH_GUI + "basicmachines/ChemicalReactor", 2, 1, 1, 0, 1, E, 1, E, true, true);
	public static final GT_Recipe_Map sDistillationRecipes = new GT_Recipe_Map(new HashSet<GT_Recipe>(50), "gt.recipe.distillationtower", "Distillation Tower", null, RES_PATH_GUI + "basicmachines/Default", 2, 4, 0, 0, 1, E, 1, E, true, true);
	public static final GT_Recipe_Map sCrakingRecipes = new GT_Recipe_Map(new HashSet<GT_Recipe>(50), "gt.recipe.craker", "Oil Cracker", null, RES_PATH_GUI + "basicmachines/Default", 1, 1, 0, 1, 1, E, 1, E, true, true);
	public static final GT_Recipe_Map sPyrolyseRecipes = new GT_Recipe_Map(new HashSet<GT_Recipe>(50), "gt.recipe.pyro", "Pyrolyse Oven", null, RES_PATH_GUI + "basicmachines/Default", 2, 1, 1, 0, 1, E, 1, E, true, true);
	public static final GT_Recipe_Map sWiremillRecipes = new GT_Recipe_Map(new HashSet<>(50), "gt.recipe.wiremill", "Wiremill", null, RES_PATH_GUI + "basicmachines/Wiremill", 1, 1, 1, 0, 1, E, 1, E, true, true);
	public static final GT_Recipe_Map sBenderRecipes = new GT_Recipe_Map(new HashSet<>(400), "gt.recipe.metalbender", "Metal Bender", null, RES_PATH_GUI + "basicmachines/Bender", 2, 1, 2, 0, 1, E, 1, E, true, true);
	public static final GT_Recipe_Map sAlloySmelterRecipes = new GT_Recipe_Map(new HashSet<GT_Recipe>(3000), "gt.recipe.alloysmelter", "Alloy Smelter", null, RES_PATH_GUI + "basicmachines/AlloySmelter", 2, 1, 2, 0, 1, E, 1, E, true, true);
	public static final GT_Recipe_Map sAssemblerRecipes = new GT_Recipe_Map_Assembler(new HashSet<GT_Recipe>(300), "gt.recipe.assembler", "Assembler", null, RES_PATH_GUI + "basicmachines/Assembler", 2, 1, 1, 0, 1, E, 1, E, true, true);
	public static final GT_Recipe_Map sCannerRecipes = new GT_Recipe_Map(new HashSet<GT_Recipe>(300), "gt.recipe.canner", "Canning Machine", null, RES_PATH_GUI + "basicmachines/Canner", 2, 2, 1, 0, 1, E, 1, E, true, true);
	public static final GT_Recipe_Map sCNCRecipes = new GT_Recipe_Map(new HashSet<GT_Recipe>(100), "gt.recipe.cncmachine", "CNC Machine", null, RES_PATH_GUI + "basicmachines/Default", 2, 1, 2, 1, 1, E, 1, E, true, true);
	public static final GT_Recipe_Map sLatheRecipes = new GT_Recipe_Map(new HashSet<GT_Recipe>(400), "gt.recipe.lathe", "Lathe", null, RES_PATH_GUI + "basicmachines/Lathe", 1, 2, 1, 0, 1, E, 1, E, true, true);
	public static final GT_Recipe_Map sCutterRecipes = new GT_Recipe_Map(new HashSet<GT_Recipe>(200), "gt.recipe.cuttingsaw", "Cutting Saw", null, RES_PATH_GUI + "basicmachines/Cutter", 1, 2, 1, 1, 1, E, 1, E, true, true);
	public static final GT_Recipe_Map sSlicerRecipes = new GT_Recipe_Map(new HashSet<GT_Recipe>(200), "gt.recipe.slicer", "Slicer", null, RES_PATH_GUI + "basicmachines/Slicer", 2, 1, 2, 0, 1, E, 1, E, true, true);
	public static final GT_Recipe_Map sExtruderRecipes = new GT_Recipe_Map(new HashSet<GT_Recipe>(1000), "gt.recipe.extruder", "Extruder", null, RES_PATH_GUI + "basicmachines/Extruder", 2, 1, 2, 0, 1, E, 1, E, true, true);
	public static final GT_Recipe_Map sHammerRecipes = new GT_Recipe_Map(new HashSet<GT_Recipe>(200), "gt.recipe.hammer", "Hammer", null, RES_PATH_GUI + "basicmachines/Hammer", 1, 1, 1, 0, 1, E, 1, E, true, true);
	public static final GT_Recipe_Map sAmplifiers = new GT_Recipe_Map(new HashSet<GT_Recipe>(10), "gt.recipe.uuamplifier", "UU Amplifier", null, RES_PATH_GUI + "basicmachines/Amplifabricator", 1, 0, 1, 0, 1, E, 1, E, true, true);
	public static final GT_Recipe_Map_Fuel sDieselFuels = new GT_Recipe_Map_Fuel(new HashSet<GT_Recipe>(10), "gt.recipe.dieselgeneratorfuel", "Diesel Generator Fuel", null, RES_PATH_GUI + "basicmachines/Default", 1, 1, 0, 0, 1, "Fuel Value: ", 1000, " EU", true, true);
	public static final GT_Recipe_Map_Fuel sTurbineFuels = new GT_Recipe_Map_Fuel(new HashSet<GT_Recipe>(10), "gt.recipe.gasturbinefuel", "Gas Turbine Fuel", null, RES_PATH_GUI + "basicmachines/Default", 1, 1, 0, 0, 1, "Fuel Value: ", 1000, " EU", true, true);
	public static final GT_Recipe_Map_Fuel sHotFuels = new GT_Recipe_Map_Fuel(new HashSet<GT_Recipe>(10), "gt.recipe.thermalgeneratorfuel", "Thermal Generator Fuel", null, RES_PATH_GUI + "basicmachines/Default", 1, 1, 0, 0, 1, "Fuel Value: ", 1000, " EU", true, false);
	public static final GT_Recipe_Map_Fuel sDenseLiquidFuels = new GT_Recipe_Map_Fuel(new HashSet<GT_Recipe>(10), "gt.recipe.semifluidboilerfuels", "Semifluid Boiler Fuels", null, RES_PATH_GUI + "basicmachines/Default", 1, 1, 0, 0, 1, "Fuel Value: ", 1000, " EU", true, true);
	public static final GT_Recipe_Map_Fuel sPlasmaFuels = new GT_Recipe_Map_Fuel(new HashSet<GT_Recipe>(10), "gt.recipe.plasmageneratorfuels", "Plasma generator Fuels", null, RES_PATH_GUI + "basicmachines/Default", 1, 1, 0, 0, 1, "Fuel Value: ", 1000, " EU", true, true);
	public static final GT_Recipe_Map_Fuel sMagicFuels = new GT_Recipe_Map_Fuel(new HashSet<GT_Recipe>(10), "gt.recipe.magicfuels", "Magic Fuels", null, RES_PATH_GUI + "basicmachines/Default", 1, 1, 0, 0, 1, "Fuel Value: ", 1000, " EU", true, true);
	public static final GT_Recipe_Map_Fuel sSmallNaquadahReactorFuels = new GT_Recipe_Map_Fuel(new HashSet<GT_Recipe>(10), "gt.recipe.smallnaquadahreactor", "Small Naquadah Reactor", null, RES_PATH_GUI + "basicmachines/Default", 1, 1, 0, 0, 1, "Fuel Value: ", 1000, " EU", true, true);
	public static final GT_Recipe_Map_Fuel sLargeNaquadahReactorFuels = new GT_Recipe_Map_Fuel(new HashSet<GT_Recipe>(10), "gt.recipe.largenaquadahreactor", "Large Naquadah Reactor", null, RES_PATH_GUI + "basicmachines/Default", 1, 1, 0, 0, 1, "Fuel Value: ", 1000, " EU", true, true);
	public static final GT_Recipe_Map_Fuel sFluidNaquadahReactorFuels = new GT_Recipe_Map_Fuel(new HashSet<GT_Recipe>(10), "gt.recipe.fluidnaquadahreactor", "Fluid Naquadah Reactor", null, RES_PATH_GUI + "basicmachines/Default", 1, 1, 0, 0, 1, "Fuel Value: ", 1000, " EU", true, true);

	/**
	 * HashMap of Recipes based on their Items
	 */
	public final Map<GT_ItemStack, Collection<GT_Recipe>> mRecipeItemMap = new HashMap<GT_ItemStack, Collection<GT_Recipe>>();
	/**
	 * HashMap of Recipes based on their Fluids
	 */
	public final Map<Fluid, Collection<GT_Recipe>> mRecipeFluidMap = new HashMap<Fluid, Collection<GT_Recipe>>();
	/**
	 * The List of all Recipes
	 */
	public final Collection<GT_Recipe> mRecipeList;
	/**
	 * String used as an unlocalised Name.
	 */
	public final String mUnlocalizedName;
	/**
	 * String used in NEI for the Recipe Lists. If null it will use the unlocalised Name instead
	 */
	public final String mNEIName;
	/**
	 * GUI used for NEI Display. Usually the GUI of the Machine itself
	 */
	public final String mNEIGUIPath;
	public final String mNEISpecialValuePre, mNEISpecialValuePost;
	public final int mUsualInputCount, mUsualOutputCount, mNEISpecialValueMultiplier, mMinimalInputItems, mMinimalInputFluids, mAmperage;
	public final boolean mNEIAllowed, mShowVoltageAmperageInNEI;

	/**
	 * Initialises a new type of Recipe Handler.
	 *
	 * @param aRecipeList                a List you specify as Recipe List. Usually just an ArrayList with a pre-initialised Size.
	 * @param aUnlocalizedName           the unlocalised Name of this Recipe Handler, used mainly for NEI.
	 * @param aLocalName                 the displayed Name inside the NEI Recipe GUI.
	 * @param aNEIGUIPath                the displayed GUI Texture, usually just a Machine GUI. Auto-Attaches ".png" if forgotten.
	 * @param aUsualInputCount           the usual amount of Input Slots this Recipe Class has.
	 * @param aUsualOutputCount          the usual amount of Output Slots this Recipe Class has.
	 * @param aNEISpecialValuePre        the String in front of the Special Value in NEI.
	 * @param aNEISpecialValueMultiplier the Value the Special Value is getting Multiplied with before displaying
	 * @param aNEISpecialValuePost       the String after the Special Value. Usually for a Unit or something.
	 * @param aNEIAllowed                if NEI is allowed to display this Recipe Handler in general.
	 */
	public GT_Recipe_Map(Collection<GT_Recipe> aRecipeList, String aUnlocalizedName, String aLocalName, String aNEIName, String aNEIGUIPath, int aUsualInputCount, int aUsualOutputCount, int aMinimalInputItems, int aMinimalInputFluids, int aAmperage, String aNEISpecialValuePre, int aNEISpecialValueMultiplier, String aNEISpecialValuePost, boolean aShowVoltageAmperageInNEI, boolean aNEIAllowed) {
		sMappings.add(this);
		mNEIAllowed = aNEIAllowed;
		mShowVoltageAmperageInNEI = aShowVoltageAmperageInNEI;
		mRecipeList = aRecipeList;
		mNEIName = aNEIName == null ? aUnlocalizedName : aNEIName;
		mNEIGUIPath = aNEIGUIPath.endsWith(".png") ? aNEIGUIPath : aNEIGUIPath + ".png";
		mNEISpecialValuePre = aNEISpecialValuePre;
		mNEISpecialValueMultiplier = aNEISpecialValueMultiplier;
		mNEISpecialValuePost = aNEISpecialValuePost;
		mAmperage = aAmperage;
		mUsualInputCount = aUsualInputCount;
		mUsualOutputCount = aUsualOutputCount;
		mMinimalInputItems = aMinimalInputItems;
		mMinimalInputFluids = aMinimalInputFluids;
		GregTech_API.sFluidMappings.add(mRecipeFluidMap);
		GregTech_API.sItemStackMappings.add(mRecipeItemMap);
		GT_LanguageManager.addStringLocalization(mUnlocalizedName = aUnlocalizedName, aLocalName);
	}

	public GT_Recipe addRecipe(boolean aOptimize, ItemStack[] aInputs, ItemStack[] aOutputs, Object aSpecial, int[] aOutputChances, FluidStack[] aFluidInputs, FluidStack[] aFluidOutputs, int aDuration, int aEUt, int aSpecialValue) {
		return addRecipe(new GT_Recipe(aOptimize, aInputs, aOutputs, aSpecial, aOutputChances, aFluidInputs, aFluidOutputs, aDuration, aEUt, aSpecialValue));
	}

	public GT_Recipe addRecipe(boolean aOptimize, ItemStack[] aInputs, ItemStack[] aOutputs, Object aSpecial, FluidStack[] aFluidInputs, FluidStack[] aFluidOutputs, int aDuration, int aEUt, int aSpecialValue) {
		return addRecipe(new GT_Recipe(aOptimize, aInputs, aOutputs, aSpecial, null, aFluidInputs, aFluidOutputs, aDuration, aEUt, aSpecialValue));
	}

	public GT_Recipe addRecipe(GT_Recipe aRecipe) {
		return addRecipe(aRecipe, true, false, false);
	}

	protected GT_Recipe addRecipe(GT_Recipe aRecipe, boolean aCheckForCollisions, boolean aFakeRecipe, boolean aHidden) {
		aRecipe.mHidden = aHidden;
		aRecipe.mFakeRecipe = aFakeRecipe;
		if (aRecipe.mFluidInputs.length < mMinimalInputFluids && aRecipe.mInputs.length < mMinimalInputItems)
			return null;
		if (aCheckForCollisions && findRecipe(null, false, Long.MAX_VALUE, aRecipe.mFluidInputs, aRecipe.mInputs) != null)
			return null;
		return add(aRecipe);
	}

	/**
	 * Only used for fake Recipe Handlers to show something in NEI, do not use this for adding actual Recipes! findRecipe wont find fake Recipes, containsInput WILL find fake Recipes
	 */
	public GT_Recipe addFakeRecipe(boolean aCheckForCollisions, ItemStack[] aInputs, ItemStack[] aOutputs, Object aSpecial, int[] aOutputChances, FluidStack[] aFluidInputs, FluidStack[] aFluidOutputs, int aDuration, int aEUt, int aSpecialValue) {
		return addFakeRecipe(aCheckForCollisions, new GT_Recipe(false, aInputs, aOutputs, aSpecial, aOutputChances, aFluidInputs, aFluidOutputs, aDuration, aEUt, aSpecialValue));
	}

	/**
	 * Only used for fake Recipe Handlers to show something in NEI, do not use this for adding actual Recipes! findRecipe wont find fake Recipes, containsInput WILL find fake Recipes
	 */
	public GT_Recipe addFakeRecipe(boolean aCheckForCollisions, ItemStack[] aInputs, ItemStack[] aOutputs, Object aSpecial, FluidStack[] aFluidInputs, FluidStack[] aFluidOutputs, int aDuration, int aEUt, int aSpecialValue) {
		return addFakeRecipe(aCheckForCollisions, new GT_Recipe(false, aInputs, aOutputs, aSpecial, null, aFluidInputs, aFluidOutputs, aDuration, aEUt, aSpecialValue));
	}

	/**
	 * Only used for fake Recipe Handlers to show something in NEI, do not use this for adding actual Recipes! findRecipe wont find fake Recipes, containsInput WILL find fake Recipes
	 */
	public GT_Recipe addFakeRecipe(boolean aCheckForCollisions, GT_Recipe aRecipe) {
		return addRecipe(aRecipe, aCheckForCollisions, true, false);
	}

	public GT_Recipe add(GT_Recipe aRecipe) {
		mRecipeList.add(aRecipe);
		for (FluidStack aFluid : aRecipe.mFluidInputs)
			if (aFluid != null) {
				Collection<GT_Recipe> tList = mRecipeFluidMap.get(aFluid.getFluid());
				if (tList == null) mRecipeFluidMap.put(aFluid.getFluid(), tList = new HashSet<GT_Recipe>(1));
				tList.add(aRecipe);
			}
		return addToItemMap(aRecipe);
	}

	public void reInit() {
		Map<GT_ItemStack, Collection<GT_Recipe>> tMap = mRecipeItemMap;
		if (tMap != null) tMap.clear();
		for (GT_Recipe tRecipe : mRecipeList) {
			GT_OreDictUnificator.setStackArray(true, tRecipe.mInputs);
			GT_OreDictUnificator.setStackArray(true, tRecipe.mOutputs);
			if (tMap != null) addToItemMap(tRecipe);
		}
	}

	/**
	 * @return if this Item is a valid Input for any for the Recipes
	 */
	public boolean containsInput(ItemStack aStack) {
		return aStack != null && (mRecipeItemMap.containsKey(new GT_ItemStack(aStack)) || mRecipeItemMap.containsKey(new GT_ItemStack(GT_Utility.copyMetaData(W, aStack))));
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
		return aFluid != null && mRecipeFluidMap.containsKey(aFluid);
	}

	public GT_Recipe findRecipe(IHasWorldObjectAndCoords aTileEntity, boolean aNotUnificated, long aVoltage, FluidStack[] aFluids, ItemStack... aInputs) {
		return findRecipe(aTileEntity, null, aNotUnificated, aVoltage, aFluids, null, aInputs);
	}

	public GT_Recipe findRecipe(IHasWorldObjectAndCoords aTileEntity, GT_Recipe aRecipe, boolean aNotUnificated, long aVoltage, FluidStack[] aFluids, ItemStack... aInputs) {
		return findRecipe(aTileEntity, aRecipe, aNotUnificated, aVoltage, aFluids, null, aInputs);
	}

	/**
	 * finds a Recipe matching the aFluid and ItemStack Inputs.
	 *
	 * @param aTileEntity    an Object representing the current coordinates of the executing Block/Entity/Whatever. This may be null, especially during Startup.
	 * @param aRecipe        in case this is != null it will try to use this Recipe first when looking things up.
	 * @param aNotUnificated if this is T the Recipe searcher will unificate the ItemStack Inputs
	 * @param aVoltage       Voltage of the Machine or Long.MAX_VALUE if it has no Voltage
	 * @param aFluids        the Fluid Inputs
	 * @param aSpecialSlot   the content of the Special Slot, the regular Manager doesn't do anything with this, but some custom ones do.
	 * @param aInputs        the Item Inputs
	 * @return the Recipe it has found or null for no matching Recipe
	 */
	public GT_Recipe findRecipe(IHasWorldObjectAndCoords aTileEntity, GT_Recipe aRecipe, boolean aNotUnificated, long aVoltage, FluidStack[] aFluids, ItemStack aSpecialSlot, ItemStack... aInputs) {
		// No Recipes? Well, nothing to be found then.
		if (mRecipeList.isEmpty()) return null;

		// Some Recipe Classes require a certain amount of Inputs of certain kinds. Like "at least 1 Fluid + 1 Stack" or "at least 2 Stacks" before they start searching for Recipes.
		// This improves Performance massively, especially if people leave things like Circuits, Molds or Shapes in their Machines to select Sub Recipes.
		if (GregTech_API.sPostloadFinished) {
			if (mMinimalInputFluids > 0) {
				if (aFluids == null) return null;
				int tAmount = 0;
				for (FluidStack aFluid : aFluids) if (aFluid != null) tAmount++;
				if (tAmount < mMinimalInputFluids) return null;
			}
			if (mMinimalInputItems > 0) {
				if (aInputs == null) return null;
				int tAmount = 0;
				for (ItemStack aInput : aInputs) if (aInput != null) tAmount++;
				if (tAmount < mMinimalInputItems) return null;
			}
		}

		// Unification happens here in case the Input isn't already unificated.
		if (aNotUnificated) aInputs = GT_OreDictUnificator.getStackArray(true, (Object[]) aInputs);

		// Check the Recipe which has been used last time in order to not have to search for it again, if possible.
		if (aRecipe != null)
			if (!aRecipe.mFakeRecipe && aRecipe.mCanBeBuffered && aRecipe.isRecipeInputEqual(false, true, aFluids, aInputs))
				return aRecipe.mEnabled && aVoltage * mAmperage >= aRecipe.mEUt ? aRecipe : null;

		// Now look for the Recipes inside the Item HashMaps, but only when the Recipes usually have Items.
		if (mUsualInputCount > 0 && aInputs != null) for (ItemStack tStack : aInputs)
			if (tStack != null) {
				Collection<GT_Recipe>
						tRecipes = mRecipeItemMap.get(new GT_ItemStack(tStack));
				if (tRecipes != null) for (GT_Recipe tRecipe : tRecipes)
					if (!tRecipe.mFakeRecipe && tRecipe.isRecipeInputEqual(false, true, aFluids, aInputs))
						return tRecipe.mEnabled && aVoltage * mAmperage >= tRecipe.mEUt ? tRecipe : null;
				tRecipes = mRecipeItemMap.get(new GT_ItemStack(GT_Utility.copyMetaData(W, tStack)));
				if (tRecipes != null) for (GT_Recipe tRecipe : tRecipes)
					if (!tRecipe.mFakeRecipe && tRecipe.isRecipeInputEqual(false, true, aFluids, aInputs))
						return tRecipe.mEnabled && aVoltage * mAmperage >= tRecipe.mEUt ? tRecipe : null;
			}

		// If the minimal Amount of Items for the Recipe is 0, then it could be a Fluid-Only Recipe, so check that Map too.
		if (mMinimalInputItems == 0 && aFluids != null) for (FluidStack aFluid : aFluids)
			if (aFluid != null) {
				Collection<GT_Recipe>
						tRecipes = mRecipeFluidMap.get(aFluid.getFluid());
				if (tRecipes != null) for (GT_Recipe tRecipe : tRecipes)
					if (!tRecipe.mFakeRecipe && tRecipe.isRecipeInputEqual(false, true, aFluids, aInputs))
						return tRecipe.mEnabled && aVoltage * mAmperage >= tRecipe.mEUt ? tRecipe : null;
			}

		// And nothing has been found.
		return null;
	}

	protected GT_Recipe addToItemMap(GT_Recipe aRecipe) {
		for (ItemStack aStack : aRecipe.mInputs)
			if (aStack != null) {
				GT_ItemStack tStack = new GT_ItemStack(aStack);
				Collection<GT_Recipe> tList = mRecipeItemMap.get(tStack);
				if (tList == null) mRecipeItemMap.put(tStack, tList = new HashSet<GT_Recipe>(1));
				tList.add(aRecipe);
			}
		return aRecipe;
	}

	/**
	 * Abstract Class for general Recipe Handling of non GT Recipes
	 */
	public static abstract class GT_Recipe_Map_NonGTRecipes extends GT_Recipe_Map {
		public GT_Recipe_Map_NonGTRecipes(Collection<GT_Recipe> aRecipeList, String aUnlocalizedName, String aLocalName, String aNEIName, String aNEIGUIPath, int aUsualInputCount, int aUsualOutputCount, int aMinimalInputItems, int aMinimalInputFluids, int aAmperage, String aNEISpecialValuePre, int aNEISpecialValueMultiplier, String aNEISpecialValuePost, boolean aShowVoltageAmperageInNEI, boolean aNEIAllowed) {
			super(aRecipeList, aUnlocalizedName, aLocalName, aNEIName, aNEIGUIPath, aUsualInputCount, aUsualOutputCount, aMinimalInputItems, aMinimalInputFluids, aAmperage, aNEISpecialValuePre, aNEISpecialValueMultiplier, aNEISpecialValuePost, aShowVoltageAmperageInNEI, aNEIAllowed);
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
		public GT_Recipe add(GT_Recipe aRecipe) {
			return null;
		}

		@Override
		public void reInit() {/**/}

		@Override
		protected GT_Recipe addToItemMap(GT_Recipe aRecipe) {
			return null;
		}
	}

	/**
	 * Just a Recipe Map with Utility specifically for Fuels.
	 */
	public static class GT_Recipe_Map_Fuel extends GT_Recipe_Map {
		public GT_Recipe_Map_Fuel(Collection<GT_Recipe> aRecipeList, String aUnlocalizedName, String aLocalName, String aNEIName, String aNEIGUIPath, int aUsualInputCount, int aUsualOutputCount, int aMinimalInputItems, int aMinimalInputFluids, int aAmperage, String aNEISpecialValuePre, int aNEISpecialValueMultiplier, String aNEISpecialValuePost, boolean aShowVoltageAmperageInNEI, boolean aNEIAllowed) {
			super(aRecipeList, aUnlocalizedName, aLocalName, aNEIName, aNEIGUIPath, aUsualInputCount, aUsualOutputCount, aMinimalInputItems, aMinimalInputFluids, aAmperage, aNEISpecialValuePre, aNEISpecialValueMultiplier, aNEISpecialValuePost, aShowVoltageAmperageInNEI, aNEIAllowed);
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
		public GT_Recipe_Map_Furnace(Collection<GT_Recipe> aRecipeList, String aUnlocalizedName, String aLocalName, String aNEIName, String aNEIGUIPath, int aUsualInputCount, int aUsualOutputCount, int aMinimalInputItems, int aMinimalInputFluids, int aAmperage, String aNEISpecialValuePre, int aNEISpecialValueMultiplier, String aNEISpecialValuePost, boolean aShowVoltageAmperageInNEI, boolean aNEIAllowed) {
			super(aRecipeList, aUnlocalizedName, aLocalName, aNEIName, aNEIGUIPath, aUsualInputCount, aUsualOutputCount, aMinimalInputItems, aMinimalInputFluids, aAmperage, aNEISpecialValuePre, aNEISpecialValueMultiplier, aNEISpecialValuePost, aShowVoltageAmperageInNEI, aNEIAllowed);
		}

		@Override
		public GT_Recipe findRecipe(IHasWorldObjectAndCoords aTileEntity, GT_Recipe aRecipe, boolean aNotUnificated, long aVoltage, FluidStack[] aFluids, ItemStack aSpecialSlot, ItemStack... aInputs) {
			if (aInputs == null || aInputs.length <= 0 || aInputs[0] == null) return null;
			if (aRecipe != null && aRecipe.isRecipeInputEqual(false, true, aFluids, aInputs)) return aRecipe;
			ItemStack tOutput = GT_ModHandler.getSmeltingOutput(aInputs[0], false, null);
			return tOutput == null ? null : new GT_Recipe(false, new ItemStack[]{GT_Utility.copyAmount(1, aInputs[0])}, new ItemStack[]{tOutput}, null, null, null, null, 128, 4, 0);
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
		public GT_Recipe_Map_Microwave(Collection<GT_Recipe> aRecipeList, String aUnlocalizedName, String aLocalName, String aNEIName, String aNEIGUIPath, int aUsualInputCount, int aUsualOutputCount, int aMinimalInputItems, int aMinimalInputFluids, int aAmperage, String aNEISpecialValuePre, int aNEISpecialValueMultiplier, String aNEISpecialValuePost, boolean aShowVoltageAmperageInNEI, boolean aNEIAllowed) {
			super(aRecipeList, aUnlocalizedName, aLocalName, aNEIName, aNEIGUIPath, aUsualInputCount, aUsualOutputCount, aMinimalInputItems, aMinimalInputFluids, aAmperage, aNEISpecialValuePre, aNEISpecialValueMultiplier, aNEISpecialValuePost, aShowVoltageAmperageInNEI, aNEIAllowed);
		}

		@Override
		public GT_Recipe findRecipe(IHasWorldObjectAndCoords aTileEntity, GT_Recipe aRecipe, boolean aNotUnificated, long aVoltage, FluidStack[] aFluids, ItemStack aSpecialSlot, ItemStack... aInputs) {
			if (aInputs == null || aInputs.length <= 0 || aInputs[0] == null) return null;
			if (aRecipe != null && aRecipe.isRecipeInputEqual(false, true, aFluids, aInputs)) return aRecipe;
			ItemStack tOutput = GT_ModHandler.getSmeltingOutput(aInputs[0], false, null);

			if (GT_Utility.areStacksEqual(aInputs[0], new ItemStack(Items.BOOK, 1, W))) {
				return new GT_Recipe(false, new ItemStack[]{GT_Utility.copyAmount(1, aInputs[0])}, new ItemStack[]{GT_Utility.getWrittenBook("Manual_Microwave", ItemList.Book_Written_03.get(1))}, null, null, null, null, 32, 4, 0);
			}

			// Check Container Item of Input since it is around the Input, then the Input itself, then Container Item of Output and last check the Output itself
			for (ItemStack tStack : new ItemStack[]{GT_Utility.getContainerItem(aInputs[0], true), aInputs[0], GT_Utility.getContainerItem(tOutput, true), tOutput})
				if (tStack != null) {
					if (GT_Utility.areStacksEqual(tStack, new ItemStack(Blocks.NETHERRACK, 1, W), true)
							|| GT_Utility.areStacksEqual(tStack, new ItemStack(Blocks.TNT, 1, W), true)
							|| GT_Utility.areStacksEqual(tStack, new ItemStack(Items.EGG, 1, W), true)
							|| GT_Utility.areStacksEqual(tStack, new ItemStack(Items.FIREWORK_CHARGE, 1, W), true)
							|| GT_Utility.areStacksEqual(tStack, new ItemStack(Items.FIREWORKS, 1, W), true)
							|| GT_Utility.areStacksEqual(tStack, new ItemStack(Items.FIRE_CHARGE, 1, W), true)
							) {
						if (aTileEntity instanceof IGregTechTileEntity)
							((IGregTechTileEntity) aTileEntity).doExplosion(aVoltage * 4);
						return null;
					}
					ItemData tData = GT_OreDictUnificator.getItemData(tStack);


					if (tData != null) {
						if (tData.mMaterial != null && tData.mMaterial.mMaterial != null) {
							if (tData.mMaterial.mMaterial.contains(SubTag.METAL) || tData.mMaterial.mMaterial.contains(SubTag.EXPLOSIVE)) {
								if (aTileEntity instanceof IGregTechTileEntity)
									((IGregTechTileEntity) aTileEntity).doExplosion(aVoltage * 4);
								return null;
							}
							if (tData.mMaterial.mMaterial.contains(SubTag.FLAMMABLE)) {
								if (aTileEntity instanceof IGregTechTileEntity)
									((IGregTechTileEntity) aTileEntity).setOnFire();
								return null;
							}
						}
						for (MaterialStack tMaterial : tData.mByProducts)
							if (tMaterial != null) {
								if (tMaterial.mMaterial.contains(SubTag.METAL) || tMaterial.mMaterial.contains(SubTag.EXPLOSIVE)) {
									if (aTileEntity instanceof IGregTechTileEntity)
										((IGregTechTileEntity) aTileEntity).doExplosion(aVoltage * 4);
									return null;
								}
								if (tMaterial.mMaterial.contains(SubTag.FLAMMABLE)) {
									if (aTileEntity instanceof IGregTechTileEntity)
										((IGregTechTileEntity) aTileEntity).setOnFire();
									return null;
								}
							}
					}
					if (TileEntityFurnace.getItemBurnTime(tStack) > 0) {
						if (aTileEntity instanceof IGregTechTileEntity) ((IGregTechTileEntity) aTileEntity).setOnFire();
						return null;
					}

				}

			return tOutput == null ? null : new GT_Recipe(false, new ItemStack[]{GT_Utility.copyAmount(1, aInputs[0])}, new ItemStack[]{tOutput}, null, null, null, null, 32, 4, 0);
		}

		@Override
		public boolean containsInput(ItemStack aStack) {
			return GT_ModHandler.getSmeltingOutput(aStack, false, null) != null;
		}
	}

	/**
	 * Special Class for Unboxinator handling.
	 */
	public static class GT_Recipe_Map_Unboxinator extends GT_Recipe_Map {
		public GT_Recipe_Map_Unboxinator(Collection<GT_Recipe> aRecipeList, String aUnlocalizedName, String aLocalName, String aNEIName, String aNEIGUIPath, int aUsualInputCount, int aUsualOutputCount, int aMinimalInputItems, int aMinimalInputFluids, int aAmperage, String aNEISpecialValuePre, int aNEISpecialValueMultiplier, String aNEISpecialValuePost, boolean aShowVoltageAmperageInNEI, boolean aNEIAllowed) {
			super(aRecipeList, aUnlocalizedName, aLocalName, aNEIName, aNEIGUIPath, aUsualInputCount, aUsualOutputCount, aMinimalInputItems, aMinimalInputFluids, aAmperage, aNEISpecialValuePre, aNEISpecialValueMultiplier, aNEISpecialValuePost, aShowVoltageAmperageInNEI, aNEIAllowed);
		}

		@Override
		public GT_Recipe findRecipe(IHasWorldObjectAndCoords aTileEntity, GT_Recipe aRecipe, boolean aNotUnificated, long aVoltage, FluidStack[] aFluids, ItemStack aSpecialSlot, ItemStack... aInputs) {
			if (aInputs == null || aInputs.length <= 0 || !ItemList.IC2_Scrapbox.isStackEqual(aInputs[0], false, true))
				return super.findRecipe(aTileEntity, aRecipe, aNotUnificated, aVoltage, aFluids, aSpecialSlot, aInputs);
			ItemStack tOutput = GT_ModHandler.getRandomScrapboxDrop();
			if (tOutput == null)
				return super.findRecipe(aTileEntity, aRecipe, aNotUnificated, aVoltage, aFluids, aSpecialSlot, aInputs);
			GT_Recipe rRecipe = new GT_Recipe(false, new ItemStack[]{ItemList.IC2_Scrapbox.get(1)}, new ItemStack[]{tOutput}, null, null, null, null, 16, 1, 0);
			// It is not allowed to be buffered due to the random Output
			rRecipe.mCanBeBuffered = false;
			// Due to its randomness it is not good if there are Items in the Output Slot, because those Items could manipulate the outcome.
			rRecipe.mNeedsEmptyOutput = true;
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
	public static class GT_Recipe_Map_FluidCanner extends GT_Recipe_Map {
		public GT_Recipe_Map_FluidCanner(Collection<GT_Recipe> aRecipeList, String aUnlocalizedName, String aLocalName, String aNEIName, String aNEIGUIPath, int aUsualInputCount, int aUsualOutputCount, int aMinimalInputItems, int aMinimalInputFluids, int aAmperage, String aNEISpecialValuePre, int aNEISpecialValueMultiplier, String aNEISpecialValuePost, boolean aShowVoltageAmperageInNEI, boolean aNEIAllowed) {
			super(aRecipeList, aUnlocalizedName, aLocalName, aNEIName, aNEIGUIPath, aUsualInputCount, aUsualOutputCount, aMinimalInputItems, aMinimalInputFluids, aAmperage, aNEISpecialValuePre, aNEISpecialValueMultiplier, aNEISpecialValuePost, aShowVoltageAmperageInNEI, aNEIAllowed);
		}

		@Override
		public GT_Recipe findRecipe(IHasWorldObjectAndCoords aTileEntity, GT_Recipe aRecipe, boolean aNotUnificated, long aVoltage, FluidStack[] aFluids, ItemStack aSpecialSlot, ItemStack... aInputs) {
			GT_Recipe rRecipe = super.findRecipe(aTileEntity, aRecipe, aNotUnificated, aVoltage, aFluids, aSpecialSlot, aInputs);
			if (aInputs == null || aInputs.length <= 0 || aInputs[0] == null || rRecipe != null || !GregTech_API.sPostloadFinished)
				return rRecipe;
			if (aFluids != null && aFluids.length > 0 && aFluids[0] != null) {
				ItemStack tOutput = GT_Utility.fillFluidContainer(aFluids[0], aInputs[0], false, true);
				FluidStack tFluid = GT_Utility.getFluidForFilledItem(tOutput, true);
				if (tFluid != null)
					rRecipe = new GT_Recipe(false, new ItemStack[]{GT_Utility.copyAmount(1, aInputs[0])}, new ItemStack[]{tOutput}, null, null, new FluidStack[]{tFluid}, null, Math.max(tFluid.amount / 64, 16), 1, 0);
			}
			if (rRecipe == null) {
				FluidStack tFluid = GT_Utility.getFluidForFilledItem(aInputs[0], true);
				if (tFluid != null)
					rRecipe = new GT_Recipe(false, new ItemStack[]{GT_Utility.copyAmount(1, aInputs[0])}, new ItemStack[]{GT_Utility.getContainerItem(aInputs[0], true)}, null, null, null, new FluidStack[]{tFluid}, Math.max(tFluid.amount / 64, 16), 1, 0);
			}
			if (rRecipe != null) rRecipe.mCanBeBuffered = false;
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
		public GT_Recipe_Map_Recycler(Collection<GT_Recipe> aRecipeList, String aUnlocalizedName, String aLocalName, String aNEIName, String aNEIGUIPath, int aUsualInputCount, int aUsualOutputCount, int aMinimalInputItems, int aMinimalInputFluids, int aAmperage, String aNEISpecialValuePre, int aNEISpecialValueMultiplier, String aNEISpecialValuePost, boolean aShowVoltageAmperageInNEI, boolean aNEIAllowed) {
			super(aRecipeList, aUnlocalizedName, aLocalName, aNEIName, aNEIGUIPath, aUsualInputCount, aUsualOutputCount, aMinimalInputItems, aMinimalInputFluids, aAmperage, aNEISpecialValuePre, aNEISpecialValueMultiplier, aNEISpecialValuePost, aShowVoltageAmperageInNEI, aNEIAllowed);
		}

		@Override
		public GT_Recipe findRecipe(IHasWorldObjectAndCoords aTileEntity, GT_Recipe aRecipe, boolean aNotUnificated, long aVoltage, FluidStack[] aFluids, ItemStack aSpecialSlot, ItemStack... aInputs) {
			if (aInputs == null || aInputs.length <= 0 || aInputs[0] == null) return null;
			if (aRecipe != null && aRecipe.isRecipeInputEqual(false, true, aFluids, aInputs)) return aRecipe;
			return new GT_Recipe(false, new ItemStack[]{GT_Utility.copyAmount(1, aInputs[0])}, GT_ModHandler.getRecyclerOutput(GT_Utility.copyAmount(64, aInputs[0]), 0) == null ? null : new ItemStack[]{ItemList.IC2_Scrap.get(1)}, null, new int[]{1250}, null, null, 45, 1, 0);
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
		public GT_Recipe_Map_Compressor(Collection<GT_Recipe> aRecipeList, String aUnlocalizedName, String aLocalName, String aNEIName, String aNEIGUIPath, int aUsualInputCount, int aUsualOutputCount, int aMinimalInputItems, int aMinimalInputFluids, int aAmperage, String aNEISpecialValuePre, int aNEISpecialValueMultiplier, String aNEISpecialValuePost, boolean aShowVoltageAmperageInNEI, boolean aNEIAllowed) {
			super(aRecipeList, aUnlocalizedName, aLocalName, aNEIName, aNEIGUIPath, aUsualInputCount, aUsualOutputCount, aMinimalInputItems, aMinimalInputFluids, aAmperage, aNEISpecialValuePre, aNEISpecialValueMultiplier, aNEISpecialValuePost, aShowVoltageAmperageInNEI, aNEIAllowed);
		}

		@Override
		public GT_Recipe findRecipe(IHasWorldObjectAndCoords aTileEntity, GT_Recipe aRecipe, boolean aNotUnificated, long aVoltage, FluidStack[] aFluids, ItemStack aSpecialSlot, ItemStack... aInputs) {
			if (aInputs == null || aInputs.length <= 0 || aInputs[0] == null) return null;
			if (aRecipe != null && aRecipe.isRecipeInputEqual(false, true, aFluids, aInputs)) return aRecipe;
			ItemStack tComparedInput = GT_Utility.copy(aInputs[0]);
			ItemStack[] tOutputItems = GT_ModHandler.getMachineOutput(tComparedInput, ic2.api.recipe.Recipes.compressor.getRecipes(), true, new NBTTagCompound(), null, null, null);
			return GT_Utility.arrayContainsNonNull(tOutputItems) ? new GT_Recipe(false, new ItemStack[]{GT_Utility.copyAmount(aInputs[0].stackSize - tComparedInput.stackSize, aInputs[0])}, tOutputItems, null, null, null, null, 400, 2, 0) : null;
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
		public GT_Recipe_Map_Extractor(Collection<GT_Recipe> aRecipeList, String aUnlocalizedName, String aLocalName, String aNEIName, String aNEIGUIPath, int aUsualInputCount, int aUsualOutputCount, int aMinimalInputItems, int aMinimalInputFluids, int aAmperage, String aNEISpecialValuePre, int aNEISpecialValueMultiplier, String aNEISpecialValuePost, boolean aShowVoltageAmperageInNEI, boolean aNEIAllowed) {
			super(aRecipeList, aUnlocalizedName, aLocalName, aNEIName, aNEIGUIPath, aUsualInputCount, aUsualOutputCount, aMinimalInputItems, aMinimalInputFluids, aAmperage, aNEISpecialValuePre, aNEISpecialValueMultiplier, aNEISpecialValuePost, aShowVoltageAmperageInNEI, aNEIAllowed);
		}

		@Override
		public GT_Recipe findRecipe(IHasWorldObjectAndCoords aTileEntity, GT_Recipe aRecipe, boolean aNotUnificated, long aVoltage, FluidStack[] aFluids, ItemStack aSpecialSlot, ItemStack... aInputs) {
			if (aInputs == null || aInputs.length <= 0 || aInputs[0] == null) return null;
			if (aRecipe != null && aRecipe.isRecipeInputEqual(false, true, aFluids, aInputs)) return aRecipe;
			ItemStack tComparedInput = GT_Utility.copy(aInputs[0]);
			ItemStack[] tOutputItems = GT_ModHandler.getMachineOutput(tComparedInput, ic2.api.recipe.Recipes.extractor.getRecipes(), true, new NBTTagCompound(), null, null, null);
			return GT_Utility.arrayContainsNonNull(tOutputItems) ? new GT_Recipe(false, new ItemStack[]{GT_Utility.copyAmount(aInputs[0].stackSize - tComparedInput.stackSize, aInputs[0])}, tOutputItems, null, null, null, null, 400, 2, 0) : null;
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
		public GT_Recipe_Map_ThermalCentrifuge(Collection<GT_Recipe> aRecipeList, String aUnlocalizedName, String aLocalName, String aNEIName, String aNEIGUIPath, int aUsualInputCount, int aUsualOutputCount, int aMinimalInputItems, int aMinimalInputFluids, int aAmperage, String aNEISpecialValuePre, int aNEISpecialValueMultiplier, String aNEISpecialValuePost, boolean aShowVoltageAmperageInNEI, boolean aNEIAllowed) {
			super(aRecipeList, aUnlocalizedName, aLocalName, aNEIName, aNEIGUIPath, aUsualInputCount, aUsualOutputCount, aMinimalInputItems, aMinimalInputFluids, aAmperage, aNEISpecialValuePre, aNEISpecialValueMultiplier, aNEISpecialValuePost, aShowVoltageAmperageInNEI, aNEIAllowed);
		}

		@Override
		public GT_Recipe findRecipe(IHasWorldObjectAndCoords aTileEntity, GT_Recipe aRecipe, boolean aNotUnificated, long aVoltage, FluidStack[] aFluids, ItemStack aSpecialSlot, ItemStack... aInputs) {
			if (aInputs == null || aInputs.length <= 0 || aInputs[0] == null) return null;
			if (aRecipe != null && aRecipe.isRecipeInputEqual(false, true, aFluids, aInputs)) return aRecipe;
			ItemStack tComparedInput = GT_Utility.copy(aInputs[0]);
			ItemStack[] tOutputItems = GT_ModHandler.getMachineOutput(tComparedInput, ic2.api.recipe.Recipes.centrifuge.getRecipes(), true, new NBTTagCompound(), null, null, null);
			return GT_Utility.arrayContainsNonNull(tOutputItems) ? new GT_Recipe(false, new ItemStack[]{GT_Utility.copyAmount(aInputs[0].stackSize - tComparedInput.stackSize, aInputs[0])}, tOutputItems, null, null, null, null, 400, 48, 0) : null;
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
		public GT_Recipe_Map_OreWasher(Collection<GT_Recipe> aRecipeList, String aUnlocalizedName, String aLocalName, String aNEIName, String aNEIGUIPath, int aUsualInputCount, int aUsualOutputCount, int aMinimalInputItems, int aMinimalInputFluids, int aAmperage, String aNEISpecialValuePre, int aNEISpecialValueMultiplier, String aNEISpecialValuePost, boolean aShowVoltageAmperageInNEI, boolean aNEIAllowed) {
			super(aRecipeList, aUnlocalizedName, aLocalName, aNEIName, aNEIGUIPath, aUsualInputCount, aUsualOutputCount, aMinimalInputItems, aMinimalInputFluids, aAmperage, aNEISpecialValuePre, aNEISpecialValueMultiplier, aNEISpecialValuePost, aShowVoltageAmperageInNEI, aNEIAllowed);
		}

		@Override
		public GT_Recipe findRecipe(IHasWorldObjectAndCoords aTileEntity, GT_Recipe aRecipe, boolean aNotUnificated, long aVoltage, FluidStack[] aFluids, ItemStack aSpecialSlot, ItemStack... aInputs) {
			if (aInputs == null || aInputs.length <= 0 || aInputs[0] == null || aFluids == null || aFluids.length < 1 || !GT_ModHandler.isWater(aFluids[0]))
				return null;
			if (aRecipe != null && aRecipe.isRecipeInputEqual(false, true, aFluids, aInputs)) return aRecipe;
			ItemStack tComparedInput = GT_Utility.copy(aInputs[0]);
			NBTTagCompound aRecipeMetaData = new NBTTagCompound();
			ItemStack[] tOutputItems = GT_ModHandler.getMachineOutput(tComparedInput, ic2.api.recipe.Recipes.oreWashing.getRecipes(), true, aRecipeMetaData, null, null, null);
			return GT_Utility.arrayContainsNonNull(tOutputItems) ? new GT_Recipe(false, new ItemStack[]{GT_Utility.copyAmount(aInputs[0].stackSize - tComparedInput.stackSize, aInputs[0])}, tOutputItems, null, null, new FluidStack[]{new FluidStack(aFluids[0].getFluid(), ((NBTTagCompound) aRecipeMetaData.getTag("return")).getInteger("amount"))}, null, 400, 16, 0) : null;
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
	public static class GT_Recipe_Map_Macerator extends GT_Recipe_Map {
		public GT_Recipe_Map_Macerator(Collection<GT_Recipe> aRecipeList, String aUnlocalizedName, String aLocalName, String aNEIName, String aNEIGUIPath, int aUsualInputCount, int aUsualOutputCount, int aMinimalInputItems, int aMinimalInputFluids, int aAmperage, String aNEISpecialValuePre, int aNEISpecialValueMultiplier, String aNEISpecialValuePost, boolean aShowVoltageAmperageInNEI, boolean aNEIAllowed) {
			super(aRecipeList, aUnlocalizedName, aLocalName, aNEIName, aNEIGUIPath, aUsualInputCount, aUsualOutputCount, aMinimalInputItems, aMinimalInputFluids, aAmperage, aNEISpecialValuePre, aNEISpecialValueMultiplier, aNEISpecialValuePost, aShowVoltageAmperageInNEI, aNEIAllowed);
		}

		@Override
		public GT_Recipe findRecipe(IHasWorldObjectAndCoords aTileEntity, GT_Recipe aRecipe, boolean aNotUnificated, long aVoltage, FluidStack[] aFluids, ItemStack aSpecialSlot, ItemStack... aInputs) {
			if (aInputs == null || aInputs.length <= 0 || aInputs[0] == null || !GregTech_API.sPostloadFinished)
				return super.findRecipe(aTileEntity, aRecipe, aNotUnificated, aVoltage, aFluids, aSpecialSlot, aInputs);
			aRecipe = super.findRecipe(aTileEntity, aRecipe, aNotUnificated, aVoltage, aFluids, aSpecialSlot, aInputs);
			if (aRecipe != null) return aRecipe;

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

			ItemStack tComparedInput = GT_Utility.copy(aInputs[0]);
			ItemStack[] tOutputItems = GT_ModHandler.getMachineOutput(tComparedInput, ic2.api.recipe.Recipes.macerator.getRecipes(), true, new NBTTagCompound(), null, null, null);
			return GT_Utility.arrayContainsNonNull(tOutputItems) ? new GT_Recipe(false, new ItemStack[]{GT_Utility.copyAmount(aInputs[0].stackSize - tComparedInput.stackSize, aInputs[0])}, tOutputItems, null, null, null, null, 400, 2, 0) : null;
		}

		@Override
		public boolean containsInput(ItemStack aStack) {
			return super.containsInput(aStack) || GT_Utility.arrayContainsNonNull(GT_ModHandler.getMachineOutput(GT_Utility.copyAmount(64, aStack), ic2.api.recipe.Recipes.macerator.getRecipes(), false, new NBTTagCompound(), null, null, null));
		}
	}

	/**
	 * Special Class for Assembler handling.
	 */
	public static class GT_Recipe_Map_Assembler extends GT_Recipe_Map {
		public GT_Recipe_Map_Assembler(Collection<GT_Recipe> aRecipeList, String aUnlocalizedName, String aLocalName, String aNEIName, String aNEIGUIPath, int aUsualInputCount, int aUsualOutputCount, int aMinimalInputItems, int aMinimalInputFluids, int aAmperage, String aNEISpecialValuePre, int aNEISpecialValueMultiplier, String aNEISpecialValuePost, boolean aShowVoltageAmperageInNEI, boolean aNEIAllowed) {
			super(aRecipeList, aUnlocalizedName, aLocalName, aNEIName, aNEIGUIPath, aUsualInputCount, aUsualOutputCount, aMinimalInputItems, aMinimalInputFluids, aAmperage, aNEISpecialValuePre, aNEISpecialValueMultiplier, aNEISpecialValuePost, aShowVoltageAmperageInNEI, aNEIAllowed);
		}

		@Override
		public GT_Recipe findRecipe(IHasWorldObjectAndCoords aTileEntity, GT_Recipe aRecipe, boolean aNotUnificated, long aVoltage, FluidStack[] aFluids, ItemStack aSpecialSlot, ItemStack... aInputs) {
			GT_Recipe rRecipe = super.findRecipe(aTileEntity, aRecipe, aNotUnificated, aVoltage, aFluids, aSpecialSlot, aInputs);
			if (aInputs == null || aInputs.length <= 0 || aInputs[0] == null || rRecipe == null || !GregTech_API.sPostloadFinished)
				return rRecipe;
			for (ItemStack aInput : aInputs) {
				if (ItemList.Paper_Printed_Pages.isStackEqual(aInput, false, true)) {
					rRecipe = rRecipe.copy();
					rRecipe.mCanBeBuffered = false;
					rRecipe.mOutputs[0].setTagCompound(aInput.getTagCompound());
				}
			}
			return rRecipe;
		}
	}

	/**
	 * Special Class for Forming Press handling.
	 */
	public static class GT_Recipe_Map_FormingPress extends GT_Recipe_Map {
		public GT_Recipe_Map_FormingPress(Collection<GT_Recipe> aRecipeList, String aUnlocalizedName, String aLocalName, String aNEIName, String aNEIGUIPath, int aUsualInputCount, int aUsualOutputCount, int aMinimalInputItems, int aMinimalInputFluids, int aAmperage, String aNEISpecialValuePre, int aNEISpecialValueMultiplier, String aNEISpecialValuePost, boolean aShowVoltageAmperageInNEI, boolean aNEIAllowed) {
			super(aRecipeList, aUnlocalizedName, aLocalName, aNEIName, aNEIGUIPath, aUsualInputCount, aUsualOutputCount, aMinimalInputItems, aMinimalInputFluids, aAmperage, aNEISpecialValuePre, aNEISpecialValueMultiplier, aNEISpecialValuePost, aShowVoltageAmperageInNEI, aNEIAllowed);
		}

		@Override
		public GT_Recipe findRecipe(IHasWorldObjectAndCoords aTileEntity, GT_Recipe aRecipe, boolean aNotUnificated, long aVoltage, FluidStack[] aFluids, ItemStack aSpecialSlot, ItemStack... aInputs) {
			GT_Recipe rRecipe = super.findRecipe(aTileEntity, aRecipe, aNotUnificated, aVoltage, aFluids, aSpecialSlot, aInputs);
			if (aInputs == null || aInputs.length < 2 || aInputs[0] == null || aInputs[1] == null || !GregTech_API.sPostloadFinished)
				return rRecipe;
			if (rRecipe == null) {
				if (ItemList.Shape_Mold_Name.isStackEqual(aInputs[0], false, true)) {
					ItemStack tOutput = GT_Utility.copyAmount(1, aInputs[1]);
					tOutput.setStackDisplayName(aInputs[0].getDisplayName());
					rRecipe = new GT_Recipe(false, new ItemStack[]{ItemList.Shape_Mold_Name.get(0), GT_Utility.copyAmount(1, aInputs[1])}, new ItemStack[]{tOutput}, null, null, null, null, 128, 8, 0);
					rRecipe.mCanBeBuffered = false;
					return rRecipe;
				}
				if (ItemList.Shape_Mold_Name.isStackEqual(aInputs[1], false, true)) {
					ItemStack tOutput = GT_Utility.copyAmount(1, aInputs[0]);
					tOutput.setStackDisplayName(aInputs[1].getDisplayName());
					rRecipe = new GT_Recipe(false, new ItemStack[]{ItemList.Shape_Mold_Name.get(0), GT_Utility.copyAmount(1, aInputs[0])}, new ItemStack[]{tOutput}, null, null, null, null, 128, 8, 0);
					rRecipe.mCanBeBuffered = false;
					return rRecipe;
				}
				return null;
			}
			for (ItemStack aMold : aInputs) {
				if (ItemList.Shape_Mold_Credit.isStackEqual(aMold, false, true)) {
					NBTTagCompound tNBT = aMold.getTagCompound();
					if (tNBT == null) tNBT = new NBTTagCompound();
					if (!tNBT.hasKey("credit_security_id")) tNBT.setLong("credit_security_id", System.nanoTime());
					aMold.setTagCompound(tNBT);

					rRecipe = rRecipe.copy();
					rRecipe.mCanBeBuffered = false;
					rRecipe.mOutputs[0].setTagCompound(tNBT);
					return rRecipe;
				}
			}
			return rRecipe;
		}
	}

	/**
	 * Special Class for Printer handling.
	 */
	public static class GT_Recipe_Map_Printer extends GT_Recipe_Map {
		public GT_Recipe_Map_Printer(Collection<GT_Recipe> aRecipeList, String aUnlocalizedName, String aLocalName, String aNEIName, String aNEIGUIPath, int aUsualInputCount, int aUsualOutputCount, int aMinimalInputItems, int aMinimalInputFluids, int aAmperage, String aNEISpecialValuePre, int aNEISpecialValueMultiplier, String aNEISpecialValuePost, boolean aShowVoltageAmperageInNEI, boolean aNEIAllowed) {
			super(aRecipeList, aUnlocalizedName, aLocalName, aNEIName, aNEIGUIPath, aUsualInputCount, aUsualOutputCount, aMinimalInputItems, aMinimalInputFluids, aAmperage, aNEISpecialValuePre, aNEISpecialValueMultiplier, aNEISpecialValuePost, aShowVoltageAmperageInNEI, aNEIAllowed);
		}

		@Override
		public GT_Recipe findRecipe(IHasWorldObjectAndCoords aTileEntity, GT_Recipe aRecipe, boolean aNotUnificated, long aVoltage, FluidStack[] aFluids, ItemStack aSpecialSlot, ItemStack... aInputs) {
			GT_Recipe rRecipe = super.findRecipe(aTileEntity, aRecipe, aNotUnificated, aVoltage, aFluids, aSpecialSlot, aInputs);
			if (aInputs == null || aInputs.length <= 0 || aInputs[0] == null || aFluids == null || aFluids.length <= 0 || aFluids[0] == null || !GregTech_API.sPostloadFinished)
				return rRecipe;

			Dyes aDye = null;
			for (Dyes tDye : Dyes.VALUES)
				if (tDye.isFluidDye(aFluids[0])) {
					aDye = tDye;
					break;
				}

			if (aDye == null) return rRecipe;

			if (rRecipe == null) {
				ItemStack
						tOutput = GT_ModHandler.getAllRecipeOutput(aTileEntity == null ? null : aTileEntity.getWorldObj(), aInputs[0], aInputs[0], aInputs[0], aInputs[0], ItemList.DYE_ONLY_ITEMS[aDye.mIndex].get(1), aInputs[0], aInputs[0], aInputs[0], aInputs[0]);
				if (tOutput != null)
					return addRecipe(new GT_Recipe(true, new ItemStack[]{GT_Utility.copyAmount(8, aInputs[0])}, new ItemStack[]{tOutput}, null, null, new FluidStack[]{new FluidStack(aFluids[0].getFluid(), (int) L)}, null, 256, 2, 0), false, false, true);

				tOutput = GT_ModHandler.getAllRecipeOutput(aTileEntity == null ? null : aTileEntity.getWorldObj(), aInputs[0], ItemList.DYE_ONLY_ITEMS[aDye.mIndex].get(1));
				if (tOutput != null)
					return addRecipe(new GT_Recipe(true, new ItemStack[]{GT_Utility.copyAmount(1, aInputs[0])}, new ItemStack[]{tOutput}, null, null, new FluidStack[]{new FluidStack(aFluids[0].getFluid(), (int) L)}, null, 32, 2, 0), false, false, true);
			} else {
				if (aInputs[0].getItem() == Items.WRITTEN_BOOK) {
					if (!ItemList.Tool_DataStick.isStackEqual(aSpecialSlot, false, true)) return null;
					NBTTagCompound tNBT = aSpecialSlot.getTagCompound();
					if (tNBT == null || GT_Utility.isStringInvalid(tNBT.getString("title")) || GT_Utility.isStringInvalid(tNBT.getString("author")))
						return null;

					rRecipe = rRecipe.copy();
					rRecipe.mCanBeBuffered = false;
					rRecipe.mOutputs[0].setTagCompound(tNBT);
					return rRecipe;
				}
				if (aInputs[0].getItem() == Items.FILLED_MAP) {
					if (!ItemList.Tool_DataStick.isStackEqual(aSpecialSlot, false, true)) return null;
					NBTTagCompound tNBT = aSpecialSlot.getTagCompound();
					if (tNBT == null || !tNBT.hasKey("map_id")) return null;

					rRecipe = rRecipe.copy();
					rRecipe.mCanBeBuffered = false;
					rRecipe.mOutputs[0].setItemDamage(tNBT.getShort("map_id"));
					return rRecipe;
				}
				if (ItemList.Paper_Punch_Card_Empty.isStackEqual(aInputs[0], false, true)) {
					if (!ItemList.Tool_DataStick.isStackEqual(aSpecialSlot, false, true)) return null;
					NBTTagCompound tNBT = aSpecialSlot.getTagCompound();
					if (tNBT == null || !tNBT.hasKey("GT.PunchCardData")) return null;

					rRecipe = rRecipe.copy();
					rRecipe.mCanBeBuffered = false;
					rRecipe.mOutputs[0].setTagCompound(GT_Utility.getNBTContainingString(new NBTTagCompound(), "GT.PunchCardData", tNBT.getString("GT.PunchCardData")));
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
