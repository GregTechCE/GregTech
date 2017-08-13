package gregtech.api;

import com.google.common.collect.BiMap;
import com.google.common.collect.EnumBiMap;
import com.google.common.collect.EnumHashBiMap;
import com.google.common.collect.HashBiMap;
import gregtech.api.items.IDamagableItem;
import gregtech.api.metatileentity.GT_CoverBehavior;
import gregtech.api.metatileentity.IMetaTileEntityFactory;
import gregtech.api.items.GenericItem;
import gregtech.api.objects.GT_Cover_Default;
import gregtech.api.objects.GT_Cover_None;
import gregtech.api.objects.HashSet;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.threads.GT_Runnable_MachineBlockUpdate;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.util.*;
import gregtech.api.world.GT_Worldgen;
import gregtech.common.blocks.BlockMachines;
import gregtech.common.blocks.BlockStonesAbstract;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

import java.util.*;

/**
 * Please do not include this File in your Mod-download as it ruins compatiblity, like with the IC2-API
 * You may just copy those Functions into your Code, or better call them via reflection.
 * <p/>
 * The whole API is the basic construct of my Mod. Everything is dependent on it.
 * I change things quite often so please don't include any File inside your Mod, even if it is an Interface.
 * <p/>
 * In these Folders are many useful Functions. You can use them via reflection if you want.
 * I know not everything is compilable due to API's of other Mods, but these are easy to fix in your Setup.
 * <p/>
 * You can use this to learn about Modding, but I would recommend simpler Mods.
 * You may even copypaste Code from these API-Files into your Mod, as I have nothing against that, but you should look exactly at what you are copying.
 *
 * @author Gregorius Techneticies
 */
public class GregTech_API {

    /**
     * My Creative Tab
     */
    public static final CreativeTabs TAB_GREGTECH = new GT_CreativeTab("Main", "Main"),
            TAB_GREGTECH_MATERIALS = new GT_CreativeTab("Materials", "Materials"),
            TAB_GREGTECH_ORES = new GT_CreativeTab("Ores", "Ores");
    /**
     * A List of all registered MetaTileEntities
     * <p/>
     * 0 -  1199 are used by GregTech.
     * 1200 -  2047 are used for GregTech Cables.
     * 4096 -  5095 are used for GregTech Frames.
     * 5096 -  6099 are used for GregTech Pipes.
     * 6100 -  8191 are used for GregTech Decoration Blocks.
     * 9216 -  10000 are used for GregTech Automation Machines.
     * 10000 - 32766 are currently free.
     * <p/>
     * Contact us if you need a free ID-Range, which doesn't conflict with other Addons.
     */
    public static final GTControlledRegistry<IMetaTileEntityFactory> METATILEENTITY_REGISTRY = new GTControlledRegistry<>(Short.MAX_VALUE);

    public static final EnumHashBiMap<EnumDyeColor, Fluid> LIQUIDDYE_MAP = EnumHashBiMap.create(EnumDyeColor.class);
    
    /**
     * A List of all Books, which were created using @GT_Utility.getWrittenBook the original Title is the Key Value
     */
    public static final Map<String, ItemStack> sBookList = new HashMap<String, ItemStack>();
    /**
     * The List of all Sounds used in GT, indices are in the static Block at the bottom
     */
    public static final Map<Integer, ResourceLocation> sSoundList = new HashMap<>();
    /**
     * The List of Tools, which can be used. Accepts regular damageable Items and Electric Items
     */
    public static final HashSet<SimpleItemStack> sToolList = new HashSet<>(),
            sCrowbarList = new HashSet<>(),
            sScrewdriverList = new HashSet<>(),
            sWrenchList = new HashSet<>(),
            sSoftHammerList = new HashSet<>(),
            sHardHammerList = new HashSet<>(),
            sSolderingToolList = new HashSet<>(),
            sSolderingMetalList = new HashSet<>();

    /**
     * The List of Hazmat Armors
     */
    public static final HashSet<SimpleItemStack> sGasHazmatList = new HashSet<>(),
            sBioHazmatList = new HashSet<>(),
            sFrostHazmatList = new HashSet<>(),
            sHeatHazmatList = new HashSet<>(),
            sRadioHazmatList = new HashSet<>(),
            sElectroHazmatList = new HashSet<>();
    /**
     * The List of Dimensions, which are Whitelisted for the Teleporter. This list should not contain other Planets.
     * Mystcraft Dimensions and other Dimensional Things should be allowed.
     * Mystcraft and Twilight Forest are automatically considered a Dimension, without being in this List.
     */
    public static final Collection<Integer> sDimensionalList = new HashSet<Integer>();

    /**
     * Lists of all the active World generation Features, these are getting Initialized in Postload!
     */
    public static final List<GT_Worldgen> sWorldgenList = new ArrayList<GT_Worldgen>();
    /**
     * These Lists are getting executed at their respective timings. Useful if you have to do things right before/after I do them, without having to control the load order. Add your "Commands" in the Constructor or in a static Code Block of your Mods Main Class. These are not Threaded, I just use a native Java Interface for their execution. Implement just the Method run() and everything should work
     */
    public static List<Runnable> sBeforeGTPreload = new ArrayList<>(),
            sAfterGTPreload = new ArrayList<>(),
            sBeforeGTLoad = new ArrayList<>(),
            sAfterGTLoad = new ArrayList<>(),
            sBeforeGTPostload = new ArrayList<>(),
            sAfterGTPostload = new ArrayList<>(),
            sBeforeGTServerstart = new ArrayList<>(),
            sAfterGTServerstart = new ArrayList<>(),
            sBeforeGTServerstop = new ArrayList<>(),
            sAfterGTServerstop = new ArrayList<>();
    /**
     * The Configuration Objects
     */
    public static GT_Config sRecipeFile = null,
            sMachineFile = null,
            sWorldgenFile = null,
            sModularArmor = null,
            sMaterialProperties = null,
            sMaterialComponents = null,
            sUnification = null,
            sSpecialFile = null,
            sClientDataFile, sOPStuff = null;

    public static int TICKS_FOR_LAG_AVERAGING = 25, MILLISECOND_THRESHOLD_UNTIL_LAG_WARNING = 100;
    /**
     * Initialized by the Block creation.
     */
    public static BlockMachines sBlockMachines;

    public static Block sBlockOres1;
    public static Block sBlockMetal1;
    public static Block sBlockMetal2;
    public static Block sBlockMetal3;
    public static Block sBlockMetal4;
    public static Block sBlockMetal5;
    public static Block sBlockMetal6;
    public static Block sBlockMetal7;
    public static Block sBlockMetal8;
    public static Block sBlockGem1;
    public static Block sBlockGem2;
    public static Block sBlockGem3;
    public static Block sBlockReinforced;
    public static BlockStonesAbstract sBlockGranites, sBlockConcretes, sBlockStones;
    public static Block sBlockCasings1, sBlockCasings2, sBlockCasings3, sBlockCasings4, sBlockCasings5;
    /**
     * Getting assigned by the Config
     */
    public static boolean sTimber = false, sDrinksAlwaysDrinkable = false, sDoShowAllItemsInCreative = false, sColoredGUI = true, sConstantEnergy = true, sMachineExplosions = true, sMachineFlammable = true, sMachineNonWrenchExplosions = true, sMachineRainExplosions = true, sMachineThunderExplosions = true, sMachineFireExplosions = true, sMachineWireFire = true;
    public static boolean mOutputRF = false;
    public static boolean mInputRF = false;
    public static boolean meIOLoaded = false;
    public static int mEUtoRF = 360;
    public static int mRFtoEU = 20;
    public static boolean mRFExplosions = true;
    public static boolean mServerStarted = false;
    private static final String aTextIC2Lower = MOD_ID_IC2.toLowerCase(Locale.ENGLISH);
    /**
     * Getting assigned by the Mod loading
     */
    public static boolean sUnificationEntriesRegistered = false,
            sPreloadStarted = false,
            sPreloadFinished = false,
            sLoadStarted = false,
            sLoadFinished = false,
            sPostloadStarted = false,
            sPostloadFinished = false;

    /**
     * Adds Biomes to the Biome Lists for World Generation
     */

    private static int size = 0; /* Used to assign Minecraft IDs to our SoundEvents. We don't use them. */
    static {
        size = SoundEvent.REGISTRY.getKeys().size();

        sDimensionalList.add(-1);
        sDimensionalList.add(0);
        sDimensionalList.add(1);

        sSoundList.put(0, new ResourceLocation("entity.arrow.shoot"));
        sSoundList.put(1, new ResourceLocation("block.anvil.use"));
        sSoundList.put(2, new ResourceLocation("block.anvil.break"));
        sSoundList.put(3, new ResourceLocation("block.stone_button.click_on"));
        sSoundList.put(4, new ResourceLocation("block.fire.extinguish"));
        sSoundList.put(5, new ResourceLocation("entity.generic.explode"));
        sSoundList.put(6, new ResourceLocation("item.flintandsteel.use"));

        registerSound(100, new ResourceLocation(aTextIC2Lower, "tools.Wrench"));
        registerSound(101, new ResourceLocation(aTextIC2Lower, "tools.RubberTrampoline"));
        registerSound(102, new ResourceLocation(aTextIC2Lower, "tools.Painter"));
        registerSound(103, new ResourceLocation(aTextIC2Lower, "tools.BatteryUse"));
        registerSound(104, new ResourceLocation(aTextIC2Lower, "tools.chainsaw.ChainsawUseOne"));
        registerSound(105, new ResourceLocation(aTextIC2Lower, "tools.chainsaw.ChainsawUseTwo"));
        registerSound(106, new ResourceLocation(aTextIC2Lower, "tools.drill.DrillSoft"));
        registerSound(107, new ResourceLocation(aTextIC2Lower, "tools.drill.DrillHard"));
        registerSound(108, new ResourceLocation(aTextIC2Lower, "tools.ODScanner"));

        registerSound(200, new ResourceLocation(aTextIC2Lower, "machines.ExtractorOp"));
        registerSound(201, new ResourceLocation(aTextIC2Lower, "machines.MaceratorOp"));
        registerSound(202, new ResourceLocation(aTextIC2Lower, "machines.InductionLoop"));
        registerSound(203, new ResourceLocation(aTextIC2Lower, "machines.CompressorOp"));
        registerSound(204, new ResourceLocation(aTextIC2Lower, "machines.RecyclerOp"));
        registerSound(205, new ResourceLocation(aTextIC2Lower, "machines.MinerOp"));
        registerSound(206, new ResourceLocation(aTextIC2Lower, "machines.PumpOp"));
        registerSound(207, new ResourceLocation(aTextIC2Lower, "machines.ElectroFurnaceLoop"));
        registerSound(208, new ResourceLocation(aTextIC2Lower, "machines.InductionLoop"));
        registerSound(209, new ResourceLocation(aTextIC2Lower, "machines.MachineOverload"));
        registerSound(210, new ResourceLocation(aTextIC2Lower, "machines.InterruptOne"));
        registerSound(211, new ResourceLocation(aTextIC2Lower, "machines.KaChing"));
        registerSound(212, new ResourceLocation(aTextIC2Lower, "machines.MagnetizerLoop"));
    }

    public static void registerSound(int id, ResourceLocation loc) {
        SoundEvent e = new SoundEvent(loc);
        if(!SoundEvent.REGISTRY.containsKey(loc)) {
            SoundEvent.REGISTRY.register(size++, loc, e);
        }
        sSoundList.put(id, loc);
    }

    
    /**
     * Register a Wrench to be usable on GregTech Machines.
     * The Wrench MUST have some kind of Durability unlike certain Buildcraft Wrenches.
     * <p/>
     * You need to register Tools in the Load Phase, because otherwise the Autodetection will assign a Tool Type in certain Cases during postload (When IToolWrench or similar Interfaces are implemented).
     * <p/>
     * -----
     * <p/>
     * Returning true at isDamagable was a great Idea, KingLemming. Well played.
     * Since the OmniWrench is just a Single-Item-Mod, people can choose if they want your infinite durability or not. So that's not really a Problem.
     * I even have a new Config to autodisable most infinite BC Wrenches (but that one is turned off).
     * <p/>
     * One last Bug for you to fix:
     * My Autoregistration detects Railcrafts Crowbars, Buildcrafts Wrenches and alike, due to their Interfaces.
     * Guess what now became a Crowbar by accident. Try registering the Wrench at the load phase to prevent things like that from happening.
     * Yes, I know that "You need to register Tools in the Load Phase"-Part wasn't there before this. Sorry about that.
     */
    public static boolean registerWrench(ItemStack aTool) {
        return registerTool(aTool, sWrenchList);
    }

    /**
     * Register a Crowbar to extract Covers from Machines
     * Crowbars are NOT Wrenches btw.
     * <p/>
     * You need to register Tools in the Load Phase, because otherwise the Autodetection will assign a Tool Type in certain Cases during postload (When IToolWrench or similar Interfaces are implemented).
     */
    public static boolean registerCrowbar(ItemStack aTool) {
        return registerTool(aTool, sCrowbarList);
    }

    /**
     * Register a Screwdriver to interact directly with Machines and Covers
     * Did I mention, that it is intentionally not possible to make a Multitool, which doesn't switch ItemID (like a Mode) all the time?
     * <p/>
     * You need to register Tools in the Load Phase, because otherwise the Autodetection will assign a Tool Type in certain Cases during postload (When IToolWrench or similar Interfaces are implemented).
     */
    public static boolean registerScrewdriver(ItemStack aTool) {
        return registerTool(aTool, sScrewdriverList);
    }

    /**
     * Register a Soft Hammer to interact with Machines
     * <p/>
     * You need to register Tools in the Load Phase, because otherwise the Autodetection will assign a Tool Type in certain Cases during postload (When IToolWrench or similar Interfaces are implemented).
     */
    public static boolean registerSoftHammer(ItemStack aTool) {
        return registerTool(aTool, sSoftHammerList);
    }

    /**
     * Register a Hard Hammer to interact with Machines
     * <p/>
     * You need to register Tools in the Load Phase, because otherwise the Autodetection will assign a Tool Type in certain Cases during postload (When IToolWrench or similar Interfaces are implemented).
     */
    public static boolean registerHardHammer(ItemStack aTool) {
        return registerTool(aTool, sHardHammerList);
    }

    /**
     * Register a Soldering Tool to interact with Machines
     * <p/>
     * You need to register Tools in the Load Phase, because otherwise the Autodetection will assign a Tool Type in certain Cases during postload (When IToolWrench or similar Interfaces are implemented).
     */
    public static boolean registerSolderingTool(ItemStack aTool) {
        return registerTool(aTool, sSolderingToolList);
    }

    /**
     * Register a Soldering Tin to interact with Soldering Tools
     * <p/>
     * You need to register Tools in the Load Phase, because otherwise the Autodetection will assign a Tool Type in certain Cases during postload (When IToolWrench or similar Interfaces are implemented).
     */
    public static boolean registerSolderingMetal(ItemStack aTool) {
        return registerTool(aTool, sSolderingMetalList);
    }

    /**
     * Generic Function to add Tools to the Lists.
     * Contains all sanity Checks for Tools, like preventing one Tool from being registered for multiple purposes as controls would override each other.
     */
    public static boolean registerTool(ItemStack aTool, Collection<SimpleItemStack> aToolList) {
        if (aTool == null || GT_Utility.isStackInList(aTool, sToolList) || (!aTool.getItem().isDamageable() && !GT_ModHandler.isElectricItem(aTool) && !(aTool.getItem() instanceof IDamagableItem)))
            return false;
        aToolList.add(new SimpleItemStack(GT_Utility.copyAmount(1, aTool)));
        sToolList.add(new SimpleItemStack(GT_Utility.copyAmount(1, aTool)));
        return true;
    }
}