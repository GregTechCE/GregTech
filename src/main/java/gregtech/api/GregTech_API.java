package gregtech.api;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.IDamagableItem;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.internal.IGT_RecipeAdder;
import gregtech.api.interfaces.internal.IThaumcraftCompat;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.metatileentity.BaseMetaTileEntity;
import gregtech.api.objects.GT_Cover_Default;
import gregtech.api.objects.GT_Cover_None;
import gregtech.api.objects.GT_HashSet;
import gregtech.api.objects.GT_ItemStack;
import gregtech.api.threads.GT_Runnable_MachineBlockUpdate;
import gregtech.api.util.*;
import gregtech.api.world.GT_Worldgen;
import gregtech.common.blocks.GT_Block_Machines;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.*;

import static gregtech.api.enums.GT_Values.*;

/**
 * Please do not include this File in your Mod-download as it ruins compatiblity, like with the IC2-API
 * You may just copy those Functions into your Code, or better call them via reflection.
 * <p/>
 * The whole API is the basic construct of my Mod. Everything is dependent on it.
 * I change things quite often so please don't include any File inside your Mod, even if it is an Interface.
 * Since some Authors were stupid enough to break this simple Rule, I added Version checks to enforce it.
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
    @Deprecated
    public static final long MATERIAL_UNIT = M, FLUID_MATERIAL_UNIT = L;
    /**
     * Fixes the HashMap Mappings for ItemStacks once the Server started
     */
    public static final Collection<Map<GT_ItemStack, ?>> sItemStackMappings = new ArrayList<Map<GT_ItemStack, ?>>();
    public static final Collection<Map<Fluid, ?>> sFluidMappings = new ArrayList<Map<Fluid, ?>>();
    /**
     * The MetaTileEntity-ID-List-Length
     */
    public static final short MAXIMUM_METATILE_IDS = Short.MAX_VALUE - 1;
    /**
     * My Creative Tab
     */
    public static final CreativeTabs TAB_GREGTECH = new GT_CreativeTab("Main", "Main"), TAB_GREGTECH_MATERIALS = new GT_CreativeTab("Materials", "Materials"), TAB_GREGTECH_ORES = new GT_CreativeTab("Ores", "Ores");
    /**
     * A List of all registered MetaTileEntities
     * <p/>
     * 0 -  1199 are used by GregTech.
     * 1200 -  2047 are used for GregTech Cables.
     * 2048 -  2559 are reserved for OvermindDL.
     * 2560 -  3071 are reserved for Immibis.
     * 3072 -  3583 are reserved for LinusPhoenix.
     * 3584 -  4095 are reserved for BloodyAsp.
     * 4096 -  5095 are used for GregTech Frames.
     * 5096 -  6099 are used for GregTech Pipes.
     * 6100 -  8191 are used for GregTech Decoration Blocks.
     * 8192 -  8703 are reserved for ZL123.
     * 8704 -  9215 are reserved for Mr10Movie.
     * 9216 -  9727 are used for GregTech Automation Machines.
     * 9728 - 10239 are reserved for 28Smiles.
     * 10240 - 10751 are reserved for VirMan.
     * 10752 - 11263 are reserved for Briareos81.
     * 11264 - 12000 are reserved for Quantum64.
     * 12001 - 12200 are reserved for the next one who asks me.
     * 12001 - 32766 are currently free.
     * <p/>
     * Contact me if you need a free ID-Range, which doesn't conflict with other Addons.
     * You could make an ID-Config, but we all know, what "stupid" customers think about conflicting ID's
     */
    public static final IMetaTileEntity[] METATILEENTITIES = new IMetaTileEntity[MAXIMUM_METATILE_IDS];
    /**
     * The Icon List for Covers
     */
    public static final Map<Integer, ITexture> sCovers = new HashMap<>();
    /**
     * The List of Cover Behaviors for the Covers
     */
    public static final BiMap<Integer, GT_CoverBehavior> sCoverBehaviors = HashBiMap.create();

    public static final BiMap<GT_ItemStack, Integer> sCoverItems = HashBiMap.create();
    /**
     * The List of Circuit Behaviors for the Redstone Circuit Block
     */
    public static final Map<Integer, GT_CircuitryBehavior> sCircuitryBehaviors = new HashMap<Integer, GT_CircuitryBehavior>();
    /**
     * The List of Blocks, which can conduct Machine Block Updates
     */
    public static final Map<Block, Integer> sMachineIDs = new HashMap<Block, Integer>();
    /**
     * The Redstone Frequencies
     */
    public static final Map<Integer, Byte> sWirelessRedstone = new HashMap<Integer, Byte>();
    /**
     * The IDSU Frequencies
     */
    public static final Map<Integer, Integer> sIDSUList = new HashMap<Integer, Integer>();
    /**
     * A List of all Books, which were created using @GT_Utility.getWrittenBook the original Title is the Key Value
     */
    public static final Map<String, ItemStack> sBookList = new HashMap<String, ItemStack>();
    /**
     * The List of all Sounds used in GT, indices are in the static Block at the bottom
     */
    public static final Map<Integer, String> sSoundList = new HashMap<>();
    /**
     * The List of Tools, which can be used. Accepts regular damageable Items and Electric Items
     */
    public static final GT_HashSet<GT_ItemStack> sToolList = new GT_HashSet<GT_ItemStack>(), sCrowbarList = new GT_HashSet<GT_ItemStack>(), sScrewdriverList = new GT_HashSet<GT_ItemStack>(), sWrenchList = new GT_HashSet<GT_ItemStack>(), sSoftHammerList = new GT_HashSet<GT_ItemStack>(), sHardHammerList = new GT_HashSet<GT_ItemStack>(), sSolderingToolList = new GT_HashSet<GT_ItemStack>(), sSolderingMetalList = new GT_HashSet<GT_ItemStack>();
    /**
     * The List of Hazmat Armors
     */
    public static final GT_HashSet<GT_ItemStack> sGasHazmatList = new GT_HashSet<GT_ItemStack>(), sBioHazmatList = new GT_HashSet<GT_ItemStack>(), sFrostHazmatList = new GT_HashSet<GT_ItemStack>(), sHeatHazmatList = new GT_HashSet<GT_ItemStack>(), sRadioHazmatList = new GT_HashSet<GT_ItemStack>(), sElectroHazmatList = new GT_HashSet<GT_ItemStack>();
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
     * A List containing all the Materials, which are somehow in use by GT and therefor receive a specific Set of Items.
     */
    public static final Materials[] sGeneratedMaterials = new Materials[1000];
    /**
     * This is the generic Cover behavior. Used for the default Covers, which have no Behavior.
     */
    public static final GT_CoverBehavior sDefaultBehavior = new GT_Cover_Default(), sNoBehavior = new GT_Cover_None();
    /**
     * For the API Version check
     */
    public static volatile int VERSION = 509;
    @Deprecated
    public static IGT_RecipeAdder sRecipeAdder;
    /**
     * Used to register Aspects to ThaumCraft, this Object might be null if ThaumCraft isn't installed
     */
    public static IThaumcraftCompat sThaumcraftCompat;
    /**
     * These Lists are getting executed at their respective timings. Useful if you have to do things right before/after I do them, without having to control the load order. Add your "Commands" in the Constructor or in a static Code Block of your Mods Main Class. These are not Threaded, I just use a native Java Interface for their execution. Implement just the Method run() and everything should work
     */
    public static List<Runnable> sBeforeGTPreload = new ArrayList<Runnable>(), sAfterGTPreload = new ArrayList<Runnable>(), sBeforeGTLoad = new ArrayList<Runnable>(), sAfterGTLoad = new ArrayList<Runnable>(), sBeforeGTPostload = new ArrayList<Runnable>(), sAfterGTPostload = new ArrayList<Runnable>(), sBeforeGTServerstart = new ArrayList<Runnable>(), sAfterGTServerstart = new ArrayList<Runnable>(), sBeforeGTServerstop = new ArrayList<Runnable>(), sAfterGTServerstop = new ArrayList<Runnable>(), sGTBlockIconload = new ArrayList<Runnable>(), sGTItemIconload = new ArrayList<Runnable>();
    /**
     * The Icon Registers from Blocks and Items. They will get set right before the corresponding Icon Load Phase as executed in the Runnable List above.
     */
    @SideOnly(Side.CLIENT)
    public static TextureMap sBlockIcons;
    /**
     * The Configuration Objects
     */
    public static GT_Config sRecipeFile = null, sMachineFile = null, sWorldgenFile = null, sModularArmor = null, sMaterialProperties = null, sMaterialComponents = null, sUnification = null, sSpecialFile = null, sClientDataFile, sOPStuff = null;
    public static int TICKS_FOR_LAG_AVERAGING = 25, MILLISECOND_THRESHOLD_UNTIL_LAG_WARNING = 100;
    /**
     * Initialized by the Block creation.
     */
    public static GT_Block_Machines sBlockMachines;

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
    public static Block sBlockGranites, sBlockConcretes, sBlockStones;
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
    public static boolean mIC2Classic = false;
    public static boolean mMagneticraft = false;
    public static boolean mImmersiveEngineering = false;
    public static boolean mGTPlusPlus = false;
    private static final String aTextIC2Lower = MOD_ID_IC2.toLowerCase(Locale.ENGLISH);
    /**
     * Getting assigned by the Mod loading
     */
    public static boolean sUnificationEntriesRegistered = false, sPreloadStarted = false, sPreloadFinished = false, sLoadStarted = false, sLoadFinished = false, sPostloadStarted = false, sPostloadFinished = false;
    private static Class sBaseMetaTileEntityClass = null;

    /**
     * Adds Biomes to the Biome Lists for World Generation
     */

    private static int size = 0; /* Used to assign Minecraft IDs to our SoundEvents. We don't use them. */
    static {
        sItemStackMappings.add(sCoverItems);
        size = SoundEvent.REGISTRY.getKeys().size();

        sDimensionalList.add(-1);
        sDimensionalList.add(0);
        sDimensionalList.add(1);

        sSoundList.put(0, "entity.arrow.shoot"); //SoundEvents.ENTITY_ARROW_SHOOT.getSoundName().toString());
        sSoundList.put(1, "block.anvil.use");//SoundEvents.BLOCK_ANVIL_USE.getSoundName().toString());
        sSoundList.put(2, "block.anvil.break");//SoundEvents.BLOCK_ANVIL_BREAK.getSoundName().toString());
        sSoundList.put(3, "block.stone_button.click_on");//SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON.getSoundName().toString());
        sSoundList.put(4, "block.fire.extinguish");//SoundEvents.ENTITY_ITEM_BREAK.getSoundName().toString());
        sSoundList.put(5, "entity.generic.explode");//SoundEvents.ENTITY_GENERIC_EXPLODE.getSoundName().toString());
        sSoundList.put(6, "item.flintandsteel.use");//SoundEvents.ITEM_FIRECHARGE_USE.getSoundName().toString());

        registerSound(100, aTextIC2Lower + ":" + "tools.Wrench");
        registerSound(101, aTextIC2Lower + ":" + "tools.RubberTrampoline");
        registerSound(102, aTextIC2Lower + ":" + "tools.Painter");
        registerSound(103, aTextIC2Lower + ":" + "tools.BatteryUse");
        registerSound(104, aTextIC2Lower + ":" + "tools.chainsaw.ChainsawUseOne");
        registerSound(105, aTextIC2Lower + ":" + "tools.chainsaw.ChainsawUseTwo");
        registerSound(106, aTextIC2Lower + ":" + "tools.drill.DrillSoft");
        registerSound(107, aTextIC2Lower + ":" + "tools.drill.DrillHard");
        registerSound(108, aTextIC2Lower + ":" + "tools.ODScanner");

        registerSound(200, aTextIC2Lower + ":" + "machines.ExtractorOp");
        registerSound(201, aTextIC2Lower + ":" + "machines.MaceratorOp");
        registerSound(202, aTextIC2Lower + ":" + "machines.InductionLoop");
        registerSound(203, aTextIC2Lower + ":" + "machines.CompressorOp");
        registerSound(204, aTextIC2Lower + ":" + "machines.RecyclerOp");
        registerSound(205, aTextIC2Lower + ":" + "machines.MinerOp");
        registerSound(206, aTextIC2Lower + ":" + "machines.PumpOp");
        registerSound(207, aTextIC2Lower + ":" + "machines.ElectroFurnaceLoop");
        registerSound(208, aTextIC2Lower + ":" + "machines.InductionLoop");
        registerSound(209, aTextIC2Lower + ":" + "machines.MachineOverload");
        registerSound(210, aTextIC2Lower + ":" + "machines.InterruptOne");
        registerSound(211, aTextIC2Lower + ":" + "machines.KaChing");
        registerSound(212, aTextIC2Lower + ":" + "machines.MagnetizerLoop");
    }

    public static void registerSound(int id, String name) {
        ResourceLocation loc = new ResourceLocation(name);
        SoundEvent e = new SoundEvent(loc);
        if(!SoundEvent.REGISTRY.containsKey(loc)) {
            SoundEvent.REGISTRY.register(size++, loc, e);
        }
        sSoundList.put(id, name);
    }

    /**
     * You want OreDict-Unification for YOUR Mod/Addon, when GregTech is installed? This Function is especially for YOU.
     * Call this Function after the load-Phase, as I register the the most of the Unification at that Phase (Redpowers Storageblocks are registered at postload).
     * A recommended use of this Function is inside your Recipe-System itself (if you have one), as the unification then makes 100% sure, that every added non-unificated Output gets automatically unificated.
     * <p/>
     * I will personally make sure, that only common prefixes of Ores get registered at the Unificator, as of now there are:
     * pulp, dust, dustSmall, ingot, nugget, gem, ore and block
     * If another Mod-Author messes these up, then it's not my fault and it's especially not your fault. As these are commonly used prefixes.
     * <p/>
     * This Unificator-API-Function uses the same Functions I use, for unificating Items. So if there is something messed up (very unlikely), then everything is messed up.
     * <p/>
     * You shouldn't use this to unificate the Inputs of your Recipes, this is only meant for the Outputs.
     *
     * @param aOreStack the Stack you want to get unificated. It is stackSize Sensitive.
     * @return Either an unificated Stack or the stack you toss in, but it should never be null, unless you throw a Nullpointer into it.
     */
    public static ItemStack getUnificatedOreDictStack(ItemStack aOreStack) {
        if (!GregTech_API.sPreloadFinished)
            GT_Log.err.println("GregTech_API ERROR: " + aOreStack.getItem() + "." + aOreStack.getItemDamage() + " - OreDict Unification Entries are not registered now, please call it in the postload phase.");
        return GT_OreDictUnificator.get(true, aOreStack);
    }

    /**
     * Causes a Machineblock Update
     * This update will cause surrounding MultiBlock Machines to update their Configuration.
     * You should call this Function in @Block.breakBlock and in @Block.onBlockAdded of your Machine.
     *
     * @param aWorld is being the World
     * @param aX     is the X-Coord of the update causing Block
     * @param aY     is the Y-Coord of the update causing Block
     * @param aZ     is the Z-Coord of the update causing Block
     */
    public static boolean causeMachineUpdate(World aWorld, int aX, int aY, int aZ) {
        if (!aWorld.isRemote)
            new Thread(new GT_Runnable_MachineBlockUpdate(aWorld, aX, aY, aZ), "Machine Block Updating").start();
        return true;
    }

    /**
     * Adds a Multi-Machine Block, like my Machine Casings for example.
     * You should call @causeMachineUpdate in @Block.breakBlock and in @Block.onBlockAdded of your registered Block.
     * You don't need to register TileEntities which implement @IMachineBlockUpdateable
     *
     * @param aBlock   the block
     * @param aMeta the Metadata of the Blocks as Bitmask! -1 or ~0 for all Metavalues
     */
    public static boolean registerMachineBlock(Block aBlock, int aMeta) {
        if (GT_Utility.isBlockInvalid(aBlock)) return false;
        if (GregTech_API.sThaumcraftCompat != null)
            GregTech_API.sThaumcraftCompat.registerPortholeBlacklistedBlock(aBlock);
        sMachineIDs.put(aBlock, aMeta);
        return true;
    }

    /**
     * Like above but with boolean Parameters instead of a BitMask
     */
    public static boolean registerMachineBlock(Block aBlock, boolean... aMeta) {
        if (GT_Utility.isBlockInvalid(aBlock) || aMeta == null || aMeta.length == 0) return false;
        if (GregTech_API.sThaumcraftCompat != null)
            GregTech_API.sThaumcraftCompat.registerPortholeBlacklistedBlock(aBlock);
        int rMeta = 0;
        for (byte i = 0; i < 16 && i < aMeta.length; i++) if (aMeta[i]) rMeta |= B[i];
        sMachineIDs.put(aBlock, rMeta);
        return true;
    }

    /**
     * if this Block is a Machine Update Conducting Block
     */
    public static boolean isMachineBlock(Block aBlock, int aMeta) {
        if (GT_Utility.isBlockInvalid(aBlock)) return false;
        return (sMachineIDs.containsKey(aBlock) && (sMachineIDs.get(aBlock) & B[aMeta]) != 0);
    }

    /**
     * Creates a new Coolant Cell Item for your Nuclear Reactor
     */
    public static Item constructCoolantCellItem(String aUnlocalized, String aEnglish, int aMaxStore) {
        try {
            return (Item) Class.forName("gregtech.api.items.GT_CoolantCellIC_Item").getConstructors()[0]
                    .newInstance(aUnlocalized, aEnglish, aMaxStore);
        } catch (Throwable e) {/*Do nothing*/}
        try {
            return (Item) Class.forName("gregtech.api.items.GT_CoolantCell_Item").getConstructors()[0]
                    .newInstance(aUnlocalized, aEnglish, aMaxStore);
        } catch (Throwable e) {/*Do nothing*/}
        return new gregtech.api.items.GT_Generic_Item(aUnlocalized, aEnglish, "Doesn't work as intended, this is a Bug", false);
    }


    /**
     * This gives you a new BaseMetaTileEntity. As some Interfaces are not always loaded (Buildcraft, Univeral Electricity) I have to use Invocation at the Constructor of the BaseMetaTileEntity
     */
    public static BaseMetaTileEntity constructBaseMetaTileEntity() {
        if (sBaseMetaTileEntityClass == null) {
            try {
                return (BaseMetaTileEntity) (sBaseMetaTileEntityClass = BaseMetaTileEntity.class).newInstance();
            } catch (Throwable e) {/*Do nothing*/}
        }

        try {
            return (BaseMetaTileEntity) (sBaseMetaTileEntityClass.newInstance());
        } catch (Throwable e) {
            GT_Log.err.println("GT_Mod: Fatal Error ocurred while initializing TileEntities, crashing Minecraft.");
            e.printStackTrace(GT_Log.err);
            throw new RuntimeException(e);
        }
    }

    public static void registerCover(ItemStack aStack, ITexture aCover, GT_CoverBehavior aBehavior) {
        GT_ItemStack stack = new GT_ItemStack(aStack);
        int coverId = stack.hashCode();
        sCoverItems.put(stack, coverId);
        sCovers.put(coverId, aCover == null || !aCover.isValidTexture() ? Textures.BlockIcons.ERROR_RENDERING[0] : aCover);
        if (aBehavior != null) sCoverBehaviors.put(coverId, aBehavior);
    }


    /**
     * Registers multiple Cover Items. I use that for the OreDict Functionality.
     *
     * @param aBehavior can be null
     */
    public static void registerCover(Collection<ItemStack> aStackList, ITexture aCover, GT_CoverBehavior aBehavior) {
        if (aCover.isValidTexture()) for (ItemStack tStack : aStackList) registerCover(tStack, aCover, aBehavior);
    }

    /**
     * returns a Cover behavior, guaranteed to not return null after preload
     */
    public static GT_CoverBehavior getCoverBehavior(ItemStack aStack) {
        if (aStack == null || aStack.getItem() == null) return sNoBehavior;
        GT_CoverBehavior rCover = sCoverBehaviors.get(sCoverItems.get(new GT_ItemStack(aStack)));
        if (rCover == null) return sDefaultBehavior;
        return rCover;
    }

    public static GT_CoverBehavior getCoverBehavior(GT_ItemStack aStack) {
        GT_CoverBehavior rCover = sCoverBehaviors.get(sCoverItems.get(aStack));
        if (rCover == null) return sDefaultBehavior;
        return rCover;
    }

    public static GT_ItemStack getCoverItem(int coverId) {
        GT_ItemStack stack = sCoverItems.inverse().get(coverId);
        if(stack == null) return null;
        return stack;
    }

    public static int getCoverId(GT_CoverBehavior behavior) {
        return sCoverBehaviors.inverse().get(behavior);
    }

    public static int getCoverId(GT_ItemStack stack) {
        return sCoverItems.get(stack);
    }

    /**
     * returns a Cover behavior, guaranteed to not return null
     */
    public static GT_CoverBehavior getCoverBehavior(int aStack) {
        if (aStack == 0) return sNoBehavior;
        GT_CoverBehavior rCover = sCoverBehaviors.get(aStack);
        if (rCover == null) return sDefaultBehavior;
        return rCover;
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
    public static boolean registerTool(ItemStack aTool, Collection<GT_ItemStack> aToolList) {
        if (aTool == null || GT_Utility.isStackInList(aTool, sToolList) || (!aTool.getItem().isDamageable() && !GT_ModHandler.isElectricItem(aTool) && !(aTool.getItem() instanceof IDamagableItem)))
            return false;
        aToolList.add(new GT_ItemStack(GT_Utility.copyAmount(1, aTool)));
        sToolList.add(new GT_ItemStack(GT_Utility.copyAmount(1, aTool)));
        return true;
    }
}