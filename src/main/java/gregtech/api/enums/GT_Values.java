package gregtech.api.enums;

import gregtech.api.interfaces.internal.IGT_Mod;
import gregtech.api.interfaces.internal.IGT_RecipeAdder;
import gregtech.api.net.IGT_NetworkHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Locale;

/**
 * Made for static imports, this Class is just a Helper.
 * <p/>
 * I am doing this to have a better Table alike view on my Code, so I can change things faster using the Block Selection Mode of eclipse.
 * <p/>
 * Go to "Window > Preferences > Java > Editor > Content Assist > Favorites" to set static importable Constant Classes such as this one as AutoCompleteable.
 */
public class GT_Values {
    // unused: A, C, D, G, H, I, J, K, N, O, Q, R, S, T

    // TODO: Rename Material Units to 'U'
    // TODO: Rename OrePrefixes Class to 'P'
    // TODO: Rename Materials Class to 'M'

    /**
     * Empty String for an easier Call Hierarchy
     */
    public static final String E = "";

    /**
     * The first 32 Bits
     */
    public static final int[] B = new int[]{1 << 0, 1 << 1, 1 << 2, 1 << 3, 1 << 4, 1 << 5, 1 << 6, 1 << 7, 1 << 8, 1 << 9, 1 << 10, 1 << 11, 1 << 12, 1 << 13, 1 << 14, 1 << 15, 1 << 16, 1 << 17, 1 << 18, 1 << 19, 1 << 20, 1 << 21, 1 << 22, 1 << 23, 1 << 24, 1 << 25, 1 << 26, 1 << 27, 1 << 28, 1 << 29, 1 << 30, 1 << 31};

    /**
     * Renamed from "MATERIAL_UNIT" to just "M"
     * <p/>
     * This is worth exactly one normal Item.
     * This Constant can be divided by many commonly used Numbers such as
     * 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 14, 15, 16, 18, 20, 21, 24, ... 64 or 81
     * without loosing precision and is for that reason used as Unit of Amount.
     * But it is also small enough to be multiplied with larger Numbers.
     * <p/>
     * This is used to determine the amount of Material contained inside a prefixed Ore.
     * For example Nugget = M / 9 as it contains out of 1/9 of an Ingot.
     */
    public static final long M = 3628800;

    /**
     * Renamed from "FLUID_MATERIAL_UNIT" to just "L"
     * <p/>
     * Fluid per Material Unit (Prime Factors: 3 * 3 * 2 * 2 * 2 * 2)
     */
    public static final long L = 144;

    /**
     * The Item WildCard Tag. Even shorter than the "-1" of the past
     */
    public static final short W = OreDictionary.WILDCARD_VALUE;

    /**
     * The Voltage Tiers. Use this Array instead of the old named Voltage Variables
     */
    public static final long[] V = new long[]{8, 32, 128, 512, 2048, 8192, 32768, 131072, 524288, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE};

    /**
     * The short Names for the Voltages
     */
    public static final String[] VN = new String[]{"ULV", "LV", "MV", "HV", "EV", "IV", "LuV", "ZPM", "UV", "MAX", "MAX", "MAX", "MAX", "MAX", "MAX", "MAX"};

    /**
     * The long Names for the Voltages
     */
    public static final String[] VOLTAGE_NAMES = new String[]{"Ultra Low Voltage", "Low Voltage", "Medium Voltage", "High Voltage", "Extreme Voltage", "Insane Voltage", "Ludicrous Voltage", "ZPM Voltage", "Ultimate Voltage", "Maximum Voltage", "Maximum Voltage", "Maximum Voltage", "Maximum Voltage", "Maximum Voltage", "Maximum Voltage", "Maximum Voltage"};
    /**
     * This way it is possible to have a Call Hierarchy of NullPointers in ItemStack based Functions, and also because most of the time I don't know what kind of Data Type the "null" stands for
     */
    public static final ItemStack NI = null;
    /**
     * This way it is possible to have a Call Hierarchy of NullPointers in FluidStack based Functions, and also because most of the time I don't know what kind of Data Type the "null" stands for
     */
    public static final FluidStack NF = null;
    /**
     * MOD ID Strings, since they are very common Parameters.
     */
    public static final String
            MOD_ID = "gregtech", MOD_ID_IC2 = "IC2", MOD_ID_NC = "IC2NuclearControl", MOD_ID_TC = "Thaumcraft", MOD_ID_TF = "TwilightForest", MOD_ID_RC = "Railcraft", MOD_ID_TE = "ThermalExpansion", MOD_ID_AE = "appliedenergistics2", MOD_ID_TFC = "terrafirmacraft", MOD_ID_PFAA = "PFAAGeologica", MOD_ID_FR = "Forestry", MOD_ID_HaC = "harvestcraft", MOD_ID_APC = "AppleCore", MOD_ID_MaCr = "magicalcrops", MOD_ID_GaEn = "ganysend", MOD_ID_GaSu = "ganyssurface", MOD_ID_GaNe = "ganysnether", MOD_ID_BC_SILICON = "BuildCraft|Silicon", MOD_ID_BC_TRANSPORT = "BuildCraft|Transport", MOD_ID_BC_FACTORY = "BuildCraft|Factory", MOD_ID_BC_ENERGY = "BuildCraft|Energy", MOD_ID_BC_BUILDERS = "BuildCraft|Builders", MOD_ID_BC_CORE = "BuildCraft|Core", MOD_ID_GC_CORE = "GalacticraftCore", MOD_ID_GC_MARS = "GalacticraftMars", MOD_ID_GC_PLANETS = "GalacticraftPlanets";
    /**
     * File Paths and Resource Paths
     */
    public static final String
            TEX_DIR = "textures/", TEX_DIR_GUI = TEX_DIR + "gui/", TEX_DIR_ITEM = TEX_DIR + "items/", TEX_DIR_BLOCK = TEX_DIR + "blocks/", TEX_DIR_ENTITY = TEX_DIR + "entity/", TEX_DIR_ASPECTS = TEX_DIR + "aspects/", RES_PATH = MOD_ID + ":" + TEX_DIR, RES_PATH_GUI = MOD_ID + ":" + TEX_DIR_GUI, RES_PATH_ITEM = MOD_ID + ":", RES_PATH_BLOCK = MOD_ID + ":", RES_PATH_ENTITY = MOD_ID + ":" + TEX_DIR_ENTITY, RES_PATH_ASPECTS = MOD_ID + ":" + TEX_DIR_ASPECTS, RES_PATH_IC2 = MOD_ID_IC2.toLowerCase(Locale.ENGLISH) + ":", RES_PATH_MODEL = MOD_ID + ":" + TEX_DIR + "models/";
    /**
     * The Mod Object itself. That is the GT_Mod-Object. It's needed to open GUI's and similar.
     */
    public static IGT_Mod GT;
    /**
     * Use this Object to add Recipes. (Recipe Adder)
     */
    public static IGT_RecipeAdder RA;
    /**
     * For Internal Usage (Network)
     */
    public static IGT_NetworkHandler NW;
    /**
     * Not really Constants, but they set using the Config and therefore should be constant (those are for the Debug Mode)
     */
    public static boolean D1 = false, D2 = false;
    /**
     * If you have to give something a World Parameter but there is no World... (Dummy World)
     */
    public static World DW;
}