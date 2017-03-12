package gregtech.common;

import gregtech.api.GregTech_API;
import gregtech.api.enums.*;
import gregtech.api.enums.TC_Aspects.TC_AspectStack;
import gregtech.api.interfaces.IProjectileItem;
import gregtech.api.interfaces.internal.IGT_Mod;
import gregtech.api.interfaces.internal.IThaumcraftCompat;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.items.GT_MetaGenerated_Item;
import gregtech.api.items.GT_MetaGenerated_Tool;
import gregtech.api.objects.GT_Fluid;
import gregtech.api.objects.ItemData;
import gregtech.api.objects.MaterialStack;
import gregtech.api.util.*;
import gregtech.common.entities.GT_Entity_Arrow;
import gregtech.common.items.GT_MetaGenerated_Tool_01;
import gregtech.common.items.armor.*;
import ic2.core.block.wiring.CableType;
import ic2.core.item.ItemIC2FluidContainer;
import ic2.core.item.type.*;
import ic2.core.ref.ItemName;
import ic2.core.util.Ic2Color;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.*;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.io.File;
import java.text.DateFormat;
import java.util.*;

public abstract class GT_Proxy implements IGT_Mod, IGuiHandler, IFuelHandler {
    private static final EnumSet<OreGenEvent.GenerateMinable.EventType> PREVENTED_ORES = EnumSet.of(OreGenEvent.GenerateMinable.EventType.COAL,
            OreGenEvent.GenerateMinable.EventType.IRON, OreGenEvent.GenerateMinable.EventType.GOLD,
            OreGenEvent.GenerateMinable.EventType.DIAMOND, OreGenEvent.GenerateMinable.EventType.REDSTONE, OreGenEvent.GenerateMinable.EventType.LAPIS,
            OreGenEvent.GenerateMinable.EventType.QUARTZ);
    public final HashSet<ItemStack> mRegisteredOres = new HashSet<ItemStack>(10000);
    public final ArrayList<String> mSoundNames = new ArrayList<String>();
    public final ArrayList<ItemStack> mSoundItems = new ArrayList<ItemStack>();
    public final ArrayList<Integer> mSoundCounts = new ArrayList<Integer>();
    private final Collection<OreDictEventContainer> mEvents = new HashSet<OreDictEventContainer>();
    private final Collection<String> mIgnoredItems = new HashSet<String>(Arrays.asList(new String[]{"itemGhastTear", "itemFlint", "itemClay", "itemBucketSaltWater",
            "itemBucketFreshWater", "itemBucketWater", "itemRock", "itemReed", "itemArrow", "itemSaw", "itemKnife", "itemHammer", "itemChisel", "itemRubber",
            "itemEssence", "itemIlluminatedPanel", "itemSkull", "itemRawRubber", "itemBacon", "itemJetpackAccelerator", "itemLazurite", "itemIridium",
            "itemTear", "itemClaw", "itemFertilizer", "itemTar", "itemSlimeball", "itemCoke", "itemBeeswax", "itemBeeQueen", "itemForcicium", "itemForcillium",
            "itemRoyalJelly", "itemHoneydew", "itemHoney", "itemPollen", "itemReedTypha", "itemSulfuricAcid", "itemPotash", "itemCompressedCarbon",
            "itemBitumen", "itemBioFuel", "itemCokeSugar", "itemCokeCactus", "itemCharcoalSugar", "itemCharcoalCactus", "itemSludge", "itemEnrichedAlloy",
            "itemQuicksilver", "itemMercury", "itemOsmium", "itemUltimateCircuit", "itemEnergizedStar", "itemAntimatterMolecule", "itemAntimatterGlob",
            "itemCoal", "itemBoat", "itemHerbalMedicineCake", "itemCakeSponge", "itemFishandPumpkinCakeSponge", "itemSoulCleaver", "itemInstantCake",
            "itemWhippingCream", "itemGlisteningWhippingCream", "itemCleaver", "itemHerbalMedicineWhippingCream", "itemStrangeWhippingCream",
            "itemBlazeCleaver", "itemBakedCakeSponge", "itemMagmaCake", "itemGlisteningCake", "itemOgreCleaver", "itemFishandPumpkinCake",
            "itemMagmaWhippingCream", "itemMultimeter", "itemSuperconductor"}));
    private final Collection<String> mIgnoredNames = new HashSet<String>(Arrays.asList(new String[]{"grubBee", "chainLink", "candyCane", "bRedString", "bVial",
            "bFlask", "anorthositeSmooth", "migmatiteSmooth", "slateSmooth", "travertineSmooth", "limestoneSmooth", "orthogneissSmooth", "marbleSmooth",
            "honeyDrop", "lumpClay", "honeyEqualssugar", "flourEqualswheat", "bluestoneInsulated", "blockWaterstone", "blockSand", "blockTorch",
            "blockPumpkin", "blockClothRock", "blockStainedHardenedClay", "blockQuartzPillar", "blockQuartzChiselled", "blockSpawner", "blockCloth", "mobHead",
            "mobEgg", "enderFlower", "enderChest", "clayHardened", "dayGemMaterial", "nightGemMaterial", "snowLayer", "bPlaceholder", "hardenedClay",
            "eternalLifeEssence", "sandstone", "wheatRice", "transdimBlock", "bambooBasket", "lexicaBotania", "livingwoodTwig", "redstoneCrystal",
            "pestleAndMortar", "glowstone", "whiteStone", "stoneSlab", "transdimBlock", "clayBowl", "clayPlate", "ceramicBowl", "ceramicPlate", "ovenRack",
            "clayCup", "ceramicCup", "batteryBox", "transmutationStone", "torchRedstoneActive", "coal", "charcoal", "cloth", "cobblestoneSlab",
            "stoneBrickSlab", "cobblestoneWall", "stoneBrickWall", "cobblestoneStair", "stoneBrickStair", "blockCloud", "blockDirt", "blockTyrian",
            "blockCarpet", "blockFft", "blockLavastone", "blockHolystone", "blockConcrete", "sunnariumPart", "brSmallMachineCyaniteProcessor", "meteoriteCoal",
            "blockCobble", "pressOreProcessor", "crusherOreProcessor", "grinderOreProcessor", "blockRubber", "blockHoney", "blockHoneydew", "blockPeat",
            "blockRadioactive", "blockSlime", "blockCocoa", "blockSugarCane", "blockLeather", "blockClayBrick", "solarPanelHV", "cableRedNet", "stoneBowl",
            "crafterWood", "taintedSoil", "brickXyEngineering", "breederUranium", "wireMill", "chunkLazurite", "aluminumNatural", "aluminiumNatural",
            "naturalAluminum", "naturalAluminium", "antimatterMilligram", "antimatterGram", "strangeMatter", "coalGenerator", "electricFurnace",
            "unfinishedTank", "valvePart", "aquaRegia", "leatherSeal", "leatherSlimeSeal", "hambone", "slimeball", "clay", "enrichedUranium", "camoPaste",
            "antiBlock", "burntQuartz", "salmonRaw", "blockHopper", "blockEnderObsidian", "blockIcestone", "blockMagicWood", "blockEnderCore", "blockHeeEndium",
            "oreHeeEndPowder", "oreHeeStardust", "oreHeeIgneousRock", "oreHeeInstabilityOrb", "crystalPureFluix", "shardNether", "gemFluorite",
            "stickObsidian", "caveCrystal", "shardCrystal", "DYECrystal","shardFire","shardWater","shardAir","shardEarth","ingotRefinedIron","blockMarble","ingotUnstable",
            "blockCactus", "blockPrismarineBrick", "blockPrismarineDark", "stoneGranitePolished", "stoneDioritePolished", "stoneAndesitePolished", "doorWood", "doorIron"}));
    private final Collection<String> mInvalidNames = new HashSet<String>(Arrays.asList(new String[]{"diamondShard", "redstoneRoot", "obsidianStick", "bloodstoneOre",
            "universalCable", "bronzeTube", "ironTube", "netherTube", "obbyTube", "infiniteBattery", "eliteBattery", "advancedBattery", "10kEUStore",
            "blueDye", "MonazitOre", "quartzCrystal", "whiteLuminiteCrystal", "darkStoneIngot", "invisiumIngot", "demoniteOrb", "enderGem", "starconiumGem",
            "osmoniumIngot", "tapaziteGem", "zectiumIngot", "foolsRubyGem", "rubyGem", "meteoriteGem", "adamiteShard", "sapphireGem", "copperIngot",
            "ironStick", "goldStick", "diamondStick", "reinforcedStick", "draconicStick", "emeraldStick", "copperStick", "tinStick", "silverStick",
            "bronzeStick", "steelStick", "leadStick", "manyullynStick", "arditeStick", "cobaltStick", "aluminiumStick", "alumiteStick", "oilsandsOre",
            "copperWire", "superconductorWire", "sulfuricAcid", "conveyorBelt", "ironWire", "aluminumWire", "aluminiumWire", "silverWire", "tinWire",
            "dustSiliconSmall", "AluminumOre", "plateHeavyT2", "blockWool", "alloyPlateEnergizedHardened", "gasWood", "alloyPlateEnergized", "SilverOre",
            "LeadOre", "TinOre", "CopperOre", "silverOre", "leadOre", "tinOre", "copperOre", "bauxiteOre", "HSLivingmetalIngot", "oilMoving", "oilStill",
            "oilBucket", "petroleumOre", "dieselFuel", "diamondNugget", "planks", "wood", "stick", "sticks", "naquadah", "obsidianRod", "stoneRod",
            "thaumiumRod", "steelRod", "netherrackRod", "woodRod", "ironRod", "cactusRod", "flintRod", "copperRod", "cobaltRod", "alumiteRod", "blueslimeRod",
            "arditeRod", "manyullynRod", "bronzeRod", "boneRod", "slimeRod", "redalloyBundled", "bluestoneBundled", "infusedteslatiteInsulated",
            "redalloyInsulated", "infusedteslatiteBundled"}));
    private final DateFormat mDateFormat = DateFormat.getInstance();
    public ArrayList<String> mBufferedPlayerActivity = new ArrayList();
    public boolean mHardcoreCables = false;
    public boolean mDisableVanillaOres = true;
    public boolean mDisableModdedOres = true;
    public boolean mNerfDustCrafting = true;
    public boolean mSortToTheEnd = true;
    public boolean mCraftingUnification = true;
    public boolean mInventoryUnification = true;
    public boolean mIncreaseDungeonLoot = true;
    public boolean mAxeWhenAdventure = true;
    public boolean mSurvivalIntoAdventure = false;
    public boolean mNerfedWoodPlank = true;
    public boolean mNerfedVanillaTools = true;
    public boolean mHardRock = false;
    public boolean mHungerEffect = true;
    public boolean mOnline = true;
    public boolean mIgnoreTcon = true;
    public boolean mDisableIC2Cables = false;
    public boolean mAchievements = true;
    public boolean mAE2Integration = true;
    public boolean mArcSmeltIntoAnnealed = true;
    public boolean mMagneticraftRecipes = true;
    public boolean mImmersiveEngineeringRecipes = true;
    private boolean isFirstServerWorldTick = true;
    private boolean mOreDictActivated = false;
    public boolean mChangeHarvestLevels=false;
    public boolean mNerfedCombs = true;
    public boolean mNerfedCrops = true;
    public boolean mGTBees = true;
    public boolean mHideUnusedOres = true;
    public boolean mHideRecyclingRecipes = true;
    public boolean mPollution = true;
    public boolean mExplosionItemDrop = false;
    public int mSkeletonsShootGTArrows = 16;
    public int mMaxEqualEntitiesAtOneSpot = 3;
    public int mFlintChance = 30;
    public int mItemDespawnTime = 6000;
    public int mUpgradeCount = 4;
    public int[] mHarvestLevel= new int[1000];
    public int mGraniteHavestLevel=3;
    public int mMaxHarvestLevel=7;
    public int mWireHeatingTicks = 4;
    public int mPollutionSmogLimit = 500000;
    public int mPollutionPoisonLimit = 750000;
    public int mPollutionVegetationLimit = 1000000;
    public int mPollutionSourRainLimit = 2000000;
    public int mTicksUntilNextCraftSound = 0;
    public double mMagneticraftBonusOutputPercent = 100.0d;
    private World mUniverse = null;
    private final String aTextThermalExpansion = "ThermalExpansion";
    private final String aTextRailcraft = "Railcraft";
    private final String aTextTwilightForest = "TwilightForest";
    private final String aTextForestry = "Forestry";
    private final String aTextArsmagica2 = "arsmagica2";
    public boolean mTEMachineRecipes = false;
    public boolean mEnableAllMaterials = false;
    public boolean mEnableAllComponents = false;

    //IT IS NOT INTENDED TO CALL ANYTHING FROM FORGE DURING PROXY CONSTRUCTION
    public GT_Proxy() {
    }

    private static void registerRecipes(OreDictEventContainer aOre) {
        if ((aOre.mEvent.getOre() == null) || (aOre.mEvent.getOre().getItem() == null)) {
            return;
        }
        if (aOre.mEvent.getOre().stackSize != 1) {
            aOre.mEvent.getOre().stackSize = 1;
        }
        if (aOre.mPrefix != null) {
            if (!aOre.mPrefix.isIgnored(aOre.mMaterial)) {
                aOre.mPrefix.processOre(aOre.mMaterial == null ? Materials._NULL : aOre.mMaterial,
                        aOre.mEvent.getName(), aOre.mModID,
                        GT_Utility.copyAmount(1, aOre.mEvent.getOre()));
            }
        } else {
            //System.out.println("Thingy Name: "+ aOre.mEvent.getName() + " !!!Unknown 'Thingy' detected!!! This Object seems to probably not follow a valid OreDictionary Convention, or I missed a Convention. Please report to GregTech Intergalactical for additional compatiblity. This is not an Error, an Issue nor a Lag Source, it is just an Information, which you should pass to me.");
        }
    }

    public void onPreLoad() {
        GT_Log.out.println("GT_Mod: Preload-Phase started!");
        GT_Log.ore.println("GT_Mod: Preload-Phase started!");

        GregTech_API.sPreloadStarted = true;

        GameRegistry.registerFuelHandler(this);
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.ORE_GEN_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);
        GregTech_API.sThaumcraftCompat = (IThaumcraftCompat) GT_Utility.callConstructor("gregtech.common.GT_ThaumcraftCompat", 0, null, GT_Values.D1);
        for (FluidContainerRegistry.FluidContainerData tData : FluidContainerRegistry.getRegisteredFluidContainerData()) {
            onFluidContainerRegistration(new FluidContainerRegistry.FluidContainerRegisterEvent(tData));
        }
        try {
            for (String tOreName : OreDictionary.getOreNames()) {
                ItemStack tOreStack;
                for (Iterator i$ = OreDictionary.getOres(tOreName).iterator(); i$.hasNext(); registerOre(new OreDictionary.OreRegisterEvent(tOreName, tOreStack))) {
                    tOreStack = (ItemStack) i$.next();
                }
            }
        } catch (Throwable e) {e.printStackTrace(GT_Log.err);}

        this.mIgnoreTcon = GregTech_API.sOPStuff.get(ConfigCategories.general, "ignoreTConstruct", true);
        this.mWireHeatingTicks = GregTech_API.sOPStuff.get(ConfigCategories.general, "WireHeatingTicks", 4);
        NetworkRegistry.INSTANCE.registerGuiHandler(GT_Values.GT, this);
        for (FluidContainerRegistry.FluidContainerData tData : FluidContainerRegistry.getRegisteredFluidContainerData()) {
            if ((tData.filledContainer.getItem() == Items.POTIONITEM) && (tData.filledContainer.getItemDamage() == 0)) {
                tData.fluid.amount = 0;
                break;
            }
        }
        GT_Log.out.println("GT_Mod: Getting required Items of other Mods.");
        ItemList.TE_Slag.set(GT_ModHandler.getModItem(aTextThermalExpansion, "slag", 1));
        ItemList.TE_Slag_Rich.set(GT_ModHandler.getModItem(aTextThermalExpansion, "slagRich", 1));
        ItemList.TE_Rockwool.set(GT_ModHandler.getModItem(aTextThermalExpansion, "rockwool", 1));
        ItemList.TE_Hardened_Glass.set(GT_ModHandler.getModItem(aTextThermalExpansion, "glassHardened", 1));

        ItemList.RC_ShuntingWire.set(GT_ModHandler.getModItem(aTextRailcraft, "tile.railcraft.machine.delta", 1, 0));
        ItemList.RC_ShuntingWireFrame.set(GT_ModHandler.getModItem(aTextRailcraft, "tile.railcraft.frame", 1, 0));
        ItemList.RC_Rail_Standard.set(GT_ModHandler.getModItem(aTextRailcraft, "part.rail", 1, 0));
        ItemList.RC_Rail_Adv.set(GT_ModHandler.getModItem(aTextRailcraft, "part.rail", 1, 1));
        ItemList.RC_Rail_Wooden.set(GT_ModHandler.getModItem(aTextRailcraft, "part.rail", 1, 2));
        ItemList.RC_Rail_HS.set(GT_ModHandler.getModItem(aTextRailcraft, "part.rail", 1, 3));
        ItemList.RC_Rail_Reinforced.set(GT_ModHandler.getModItem(aTextRailcraft, "part.rail", 1, 4));
        ItemList.RC_Rail_Electric.set(GT_ModHandler.getModItem(aTextRailcraft, "part.rail", 1, 5));
        ItemList.RC_Tie_Wood.set(GT_ModHandler.getModItem(aTextRailcraft, "part.tie", 1, 0));
        ItemList.RC_Tie_Stone.set(GT_ModHandler.getModItem(aTextRailcraft, "part.tie", 1, 1));
        ItemList.RC_Bed_Wood.set(GT_ModHandler.getModItem(aTextRailcraft, "part.railbed", 1, 0));
        ItemList.RC_Bed_Stone.set(GT_ModHandler.getModItem(aTextRailcraft, "part.railbed", 1, 1));
        ItemList.RC_Rebar.set(GT_ModHandler.getModItem(aTextRailcraft, "part.rebar", 1));
        ItemList.Tool_Sword_Steel.set(GT_ModHandler.getModItem(aTextRailcraft, "tool.steel.sword", 1));
        ItemList.Tool_Pickaxe_Steel.set(GT_ModHandler.getModItem(aTextRailcraft, "tool.steel.pickaxe", 1));
        ItemList.Tool_Shovel_Steel.set(GT_ModHandler.getModItem(aTextRailcraft, "tool.steel.shovel", 1));
        ItemList.Tool_Axe_Steel.set(GT_ModHandler.getModItem(aTextRailcraft, "tool.steel.axe", 1));
        ItemList.Tool_Hoe_Steel.set(GT_ModHandler.getModItem(aTextRailcraft, "tool.steel.hoe", 1));

        ItemList.TF_LiveRoot.set(GT_ModHandler.getModItem(aTextTwilightForest , "item.liveRoot", 1, 0));
        ItemList.TF_Vial_FieryBlood.set(GT_ModHandler.getModItem(aTextTwilightForest , "item.fieryBlood", 1));
        ItemList.TF_Vial_FieryTears.set(GT_ModHandler.getModItem(aTextTwilightForest , "item.fieryTears", 1));

        ItemList.FR_Lemon.set(GT_ModHandler.getModItem(aTextForestry, "fruits", 1, 3));
        ItemList.FR_Mulch.set(GT_ModHandler.getModItem(aTextForestry, "mulch", 1));
        ItemList.FR_Fertilizer.set(GT_ModHandler.getModItem(aTextForestry, "fertilizerCompound", 1));
        ItemList.FR_Compost.set(GT_ModHandler.getModItem(aTextForestry, "fertilizerBio", 1));
        ItemList.FR_Silk.set(GT_ModHandler.getModItem(aTextForestry, "craftingMaterial", 1, 2));
        ItemList.FR_Wax.set(GT_ModHandler.getModItem(aTextForestry, "beeswax", 1));
        ItemList.FR_WaxCapsule.set(GT_ModHandler.getModItem(aTextForestry, "waxCapsule", 1));
        ItemList.FR_RefractoryWax.set(GT_ModHandler.getModItem(aTextForestry, "refractoryWax", 1));
        ItemList.FR_RefractoryCapsule.set(GT_ModHandler.getModItem(aTextForestry, "refractoryEmpty", 1));
        ItemList.FR_Bee_Drone.set(GT_ModHandler.getModItem(aTextForestry, "beeDroneGE", 1));
        ItemList.FR_Bee_Princess.set(GT_ModHandler.getModItem(aTextForestry, "beePrincessGE", 1));
        ItemList.FR_Bee_Queen.set(GT_ModHandler.getModItem(aTextForestry, "beeQueenGE", 1));
        ItemList.FR_Tree_Sapling.set(GT_ModHandler.getModItem(aTextForestry, "sapling", 1, GT_ModHandler.getModItem(aTextForestry, "saplingGE", 1)));
        ItemList.FR_Butterfly.set(GT_ModHandler.getModItem(aTextForestry, "butterflyGE", 1));
        ItemList.FR_Larvae.set(GT_ModHandler.getModItem(aTextForestry, "beeLarvaeGE", 1));
        ItemList.FR_Serum.set(GT_ModHandler.getModItem(aTextForestry, "serumGE", 1));
        ItemList.FR_Caterpillar.set(GT_ModHandler.getModItem(aTextForestry, "caterpillarGE", 1));
        ItemList.FR_PollenFertile.set(GT_ModHandler.getModItem(aTextForestry, "pollenFertile", 1));
        ItemList.FR_Stick.set(GT_ModHandler.getModItem(aTextForestry, "oakStick", 1));
        ItemList.FR_Casing_Impregnated.set(GT_ModHandler.getModItem(aTextForestry, "impregnatedCasing", 1));
        ItemList.FR_Casing_Sturdy.set(GT_ModHandler.getModItem(aTextForestry, "sturdyMachine", 1));
        ItemList.FR_Casing_Hardened.set(GT_ModHandler.getModItem(aTextForestry, "hardenedMachine", 1));

        ItemList.Bottle_Empty.set(new ItemStack(Items.GLASS_BOTTLE, 1));

        ItemList.Cell_Universal_Fluid.set(GT_ModHandler.getIC2Item(ItemName.fluid_cell, "", 1));

        ItemList.IC2_Item_Casing_Iron.set(GT_ModHandler.getIC2Item(ItemName.casing, CasingResourceType.iron, 1));
        ItemList.IC2_Item_Casing_Gold.set(GT_ModHandler.getIC2Item(ItemName.casing, CasingResourceType.gold, 1));
        ItemList.IC2_Item_Casing_Bronze.set(GT_ModHandler.getIC2Item(ItemName.casing, CasingResourceType.bronze, 1));
        ItemList.IC2_Item_Casing_Copper.set(GT_ModHandler.getIC2Item(ItemName.casing, CasingResourceType.copper, 1));
        ItemList.IC2_Item_Casing_Tin.set(GT_ModHandler.getIC2Item(ItemName.casing, CasingResourceType.tin, 1));
        ItemList.IC2_Item_Casing_Lead.set(GT_ModHandler.getIC2Item(ItemName.casing, CasingResourceType.lead, 1));
        ItemList.IC2_Item_Casing_Steel.set(GT_ModHandler.getIC2Item(ItemName.casing, CasingResourceType.steel, 1));
        ItemList.IC2_Spray_WeedEx.set(GT_ModHandler.getIC2Item(ItemName.crop_res, CropResItemType.weed, 1));
        ItemList.IC2_Fuel_Can_Empty.set(GT_ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.fuel_rod, 1));
        ItemList.IC2_Fuel_Can_Filled.set(GT_ModHandler.getIC2Item(ItemName.uranium_fuel_rod, 1));
        ItemList.IC2_Mixed_Metal_Ingot.set(GT_ModHandler.getIC2Item(ItemName.ingot, IngotResourceType.alloy, 1));
        ItemList.IC2_Fertilizer.set(GT_ModHandler.getIC2Item(ItemName.crop_res, CropResItemType.fertilizer, 1));
        ItemList.IC2_CoffeeBeans.set(GT_ModHandler.getIC2Item(ItemName.crop_res, CropResItemType.coffee_beans, 1));
        ItemList.IC2_CoffeePowder.set(GT_ModHandler.getIC2Item(ItemName.crop_res, CropResItemType.coffee_powder, 1));
        ItemList.IC2_Hops.set(GT_ModHandler.getIC2Item(ItemName.crop_res, CropResItemType.hops, 1));
        ItemList.IC2_Resin.set(GT_ModHandler.getIC2Item(ItemName.misc_resource, MiscResourceType.resin, 1));
        ItemList.IC2_Plantball.set(GT_ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.plant_ball, 1));
        ItemList.IC2_PlantballCompressed.set(GT_ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.plant_ball, 1));
        ItemList.IC2_Crop_Seeds.set(GT_ModHandler.getIC2Item(ItemName.crop_seed_bag, 1));
        ItemList.IC2_Grin_Powder.set(GT_ModHandler.getIC2Item(ItemName.crop_res, CropResItemType.grin_powder, 1));
        ItemList.IC2_Energium_Dust.set(GT_ModHandler.getIC2Item(ItemName.dust, DustResourceType.energium, 1));
        ItemList.IC2_Scrap.set(GT_ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.scrap, 1));
        ItemList.IC2_Scrapbox.set(GT_ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.scrap_box, 1));
        ItemList.IC2_Fuel_Rod_Empty.set(GT_ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.fuel_rod, 1));
        ItemList.IC2_Food_Can_Empty.set(GT_ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.tin_can, 1));
        ItemList.IC2_Food_Can_Filled.set(GT_ModHandler.getIC2Item(ItemName.filled_tin_can, 1));
        ItemList.IC2_Food_Can_Spoiled.set(GT_ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.tin_can, 1));
        ItemList.IC2_Industrial_Diamond.set(GT_ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.industrial_diamond, 1));
        ItemList.IC2_Compressed_Coal_Ball.set(GT_ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.coal_ball, 1));
        ItemList.IC2_Compressed_Coal_Chunk.set(GT_ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.coal_chunk, 1));
        ItemList.IC2_ShaftIron.set(GT_ModHandler.getIC2Item(ItemName.rotor_iron, 1));
        ItemList.IC2_ShaftSteel.set(GT_ModHandler.getIC2Item(ItemName.rotor_steel, 1));

        ItemList.IC2_SuBattery.set(GT_ModHandler.getIC2Item(ItemName.single_use_battery, 1));
        ItemList.IC2_ReBattery.set(GT_ModHandler.getIC2Item(ItemName.re_battery, 1));
        ItemList.IC2_AdvBattery.set(GT_ModHandler.getIC2Item(ItemName.advanced_re_battery, 1));
        ItemList.IC2_EnergyCrystal.set(GT_ModHandler.getIC2Item(ItemName.energy_crystal, 1));
        ItemList.IC2_LapotronCrystal.set(GT_ModHandler.getIC2Item(ItemName.lapotron_crystal, 1));

        ItemList.IC2_LapotronCrystal.set(GT_ModHandler.getIC2Item(ItemName.lapotron_crystal, 1));
        ItemList.IC2_LapotronCrystal.set(GT_ModHandler.getIC2Item(ItemName.lapotron_crystal, 1));
        ItemList.IC2_LapotronCrystal.set(GT_ModHandler.getIC2Item(ItemName.lapotron_crystal, 1));

        ItemList.Tool_Sword_Bronze.set(GT_ModHandler.getIC2Item(ItemName.bronze_sword, 1));
        ItemList.Tool_Pickaxe_Bronze.set(GT_ModHandler.getIC2Item(ItemName.bronze_pickaxe, 1));
        ItemList.Tool_Shovel_Bronze.set(GT_ModHandler.getIC2Item(ItemName.bronze_shovel, 1));
        ItemList.Tool_Axe_Bronze.set(GT_ModHandler.getIC2Item(ItemName.bronze_axe, 1));
        ItemList.Tool_Hoe_Bronze.set(GT_ModHandler.getIC2Item(ItemName.bronze_hoe, 1));
        ItemList.IC2_ForgeHammer.set(GT_ModHandler.getIC2Item(ItemName.forge_hammer, 1));
        ItemList.IC2_WireCutter.set(GT_ModHandler.getIC2Item(ItemName.cutter, 1));

        ItemList.Credit_Iron.set(GT_ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.coin, 1));

        ItemList.Circuit_Basic.set(GT_ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.circuit, 1));
        ItemList.Circuit_Advanced.set(GT_ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.advanced_circuit, 1));

        //ItemList.Upgrade_Overclocker.set(GT_ModHandler.getIC2Item(ItemName.upgrade, ItemUpgradeModule.UpgradeType.overclocker, 1));
        //ItemList.Upgrade_Battery.set(GT_ModHandler.getIC2Item(ItemName.upgrade, ItemUpgradeModule.UpgradeType.energy_storage, 1));

        ItemList.Dye_Bonemeal.set(new ItemStack(Items.DYE, 1, 15));
        ItemList.Dye_SquidInk.set(new ItemStack(Items.DYE, 1, 0));
        ItemList.Dye_Cocoa.set(new ItemStack(Items.DYE, 1, 3));

        ItemList.Book_Written_00.set(new ItemStack(Items.WRITTEN_BOOK, 1, 0));

        ItemList.Food_Baked_Bread.set(new ItemStack(Items.BREAD, 1, 0));
        ItemList.Food_Raw_Potato.set(new ItemStack(Items.POTATO, 1, 0));
        ItemList.Food_Baked_Potato.set(new ItemStack(Items.BAKED_POTATO, 1, 0));
        ItemList.Food_Poisonous_Potato.set(new ItemStack(Items.POISONOUS_POTATO, 1, 0));


        OrePrefixes.bottle.mContainerItem = ItemList.Bottle_Empty.get(1);
        OrePrefixes.bucket.mContainerItem = new ItemStack(Items.BUCKET, 1);

        GregTech_API.sFrostHazmatList.add(ItemName.hazmat_helmet.getItemStack());
        GregTech_API.sFrostHazmatList.add(ItemName.hazmat_chestplate.getItemStack());
        GregTech_API.sFrostHazmatList.add(ItemName.hazmat_leggings.getItemStack());
        GregTech_API.sFrostHazmatList.add(ItemName.rubber_boots.getItemStack());

        GregTech_API.sHeatHazmatList.add(ItemName.hazmat_helmet.getItemStack());
        GregTech_API.sHeatHazmatList.add(ItemName.hazmat_chestplate.getItemStack());
        GregTech_API.sHeatHazmatList.add(ItemName.hazmat_leggings.getItemStack());
        GregTech_API.sHeatHazmatList.add(ItemName.rubber_boots.getItemStack());

        GregTech_API.sBioHazmatList.add(ItemName.hazmat_helmet.getItemStack());
        GregTech_API.sBioHazmatList.add(ItemName.hazmat_chestplate.getItemStack());
        GregTech_API.sBioHazmatList.add(ItemName.hazmat_leggings.getItemStack());
        GregTech_API.sBioHazmatList.add(ItemName.rubber_boots.getItemStack());

        GregTech_API.sGasHazmatList.add(ItemName.hazmat_helmet.getItemStack());
        GregTech_API.sGasHazmatList.add(ItemName.hazmat_chestplate.getItemStack());
        GregTech_API.sGasHazmatList.add(ItemName.hazmat_leggings.getItemStack());
        GregTech_API.sGasHazmatList.add(ItemName.rubber_boots.getItemStack());

        GregTech_API.sRadioHazmatList.add(ItemName.hazmat_helmet.getItemStack());
        GregTech_API.sRadioHazmatList.add(ItemName.hazmat_chestplate.getItemStack());
        GregTech_API.sRadioHazmatList.add(ItemName.hazmat_leggings.getItemStack());
        GregTech_API.sRadioHazmatList.add(ItemName.rubber_boots.getItemStack());

        GregTech_API.sElectroHazmatList.add(ItemName.hazmat_helmet.getItemStack());
        GregTech_API.sElectroHazmatList.add(ItemName.hazmat_chestplate.getItemStack());
        GregTech_API.sElectroHazmatList.add(ItemName.hazmat_leggings.getItemStack());
        GregTech_API.sElectroHazmatList.add(ItemName.rubber_boots.getItemStack());
        GregTech_API.sElectroHazmatList.add(new ItemStack(Items.CHAINMAIL_HELMET, 1, 32767));
        GregTech_API.sElectroHazmatList.add(new ItemStack(Items.CHAINMAIL_CHESTPLATE, 1, 32767));
        GregTech_API.sElectroHazmatList.add(new ItemStack(Items.CHAINMAIL_LEGGINGS, 1, 32767));
        GregTech_API.sElectroHazmatList.add(new ItemStack(Items.CHAINMAIL_BOOTS, 1, 32767));

        GT_ModHandler.sNonReplaceableItems.add(new ItemStack(Items.BOW, 1, 32767));
        GT_ModHandler.sNonReplaceableItems.add(new ItemStack(Items.FISHING_ROD, 1, 32767));
        GT_ModHandler.sNonReplaceableItems.add(ItemList.IC2_ForgeHammer.getWithDamage(1, 32767L, new Object[0]));
        GT_ModHandler.sNonReplaceableItems.add(ItemList.IC2_WireCutter.getWithDamage(1, 32767L, new Object[0]));
        GT_ModHandler.sNonReplaceableItems.add(ItemName.painter.getItemStack(Ic2Color.white));
        GT_ModHandler.sNonReplaceableItems.add(ItemName.cf_pack.getItemStack());
        GT_ModHandler.sNonReplaceableItems.add(ItemName.jetpack.getItemStack());
        GT_ModHandler.sNonReplaceableItems.add(ItemName.treetap.getItemStack());
        GT_ModHandler.sNonReplaceableItems.add(ItemName.weeding_trowel.getItemStack());
        GT_ModHandler.sNonReplaceableItems.add(ItemName.static_boots.getItemStack());
        GT_ModHandler.sNonReplaceableItems.add(ItemName.alloy_chestplate.getItemStack());
        GT_ModHandler.sNonReplaceableItems.add(ItemName.hazmat_helmet.getItemStack());
        GT_ModHandler.sNonReplaceableItems.add(ItemName.hazmat_chestplate.getItemStack());
        GT_ModHandler.sNonReplaceableItems.add(ItemName.hazmat_leggings.getItemStack());
        GT_ModHandler.sNonReplaceableItems.add(ItemName.rubber_boots.getItemStack());
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getModItem(aTextRailcraft, "part.turbine.disk", 1, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getModItem(aTextRailcraft, "part.turbine.blade", 1, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getModItem(aTextRailcraft, "part.turbine.rotor", 1, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getModItem(aTextRailcraft, "borehead.diamond", 1, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getModItem(aTextRailcraft, "borehead.steel", 1, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getModItem(aTextRailcraft, "borehead.iron", 1, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getModItem(aTextTwilightForest , "item.plateNaga", 1, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getModItem(aTextTwilightForest , "item.legsNaga", 1, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getModItem(aTextTwilightForest , "item.arcticHelm", 1, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getModItem(aTextTwilightForest , "item.arcticPlate", 1, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getModItem(aTextTwilightForest , "item.arcticLegs", 1, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getModItem(aTextTwilightForest , "item.arcticBoots", 1, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getModItem(aTextTwilightForest , "item.yetiHelm", 1, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getModItem(aTextTwilightForest , "item.yetiPlate", 1, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getModItem(aTextTwilightForest , "item.yetiLegs", 1, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getModItem(aTextTwilightForest , "item.yetiBoots", 1, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getModItem("appliedenergistics2", "item.ToolCertusQuartzCuttingKnife", 1, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getModItem("appliedenergistics2", "item.ToolNetherQuartzCuttingKnife", 1, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getModItem(aTextForestry, "apiaristHelmet", 1, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getModItem(aTextForestry, "apiaristChest", 1, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getModItem(aTextForestry, "apiaristLegs", 1, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getModItem(aTextForestry, "apiaristBoots", 1, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getModItem(aTextForestry, "frameUntreated", 1, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getModItem(aTextForestry, "frameImpregnated", 1, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getModItem(aTextForestry, "frameProven", 1, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getModItem(aTextForestry, "waxCast", 1, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getModItem("GalacticraftCore", "item.sensorGlasses", 1, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getModItem("IC2NuclearControl", "ItemToolThermometer", 1, 32767));

        RecipeSorter.register("gregtech:shaped", GT_Shaped_Recipe.class, RecipeSorter.Category.SHAPED, "after:minecraft:shaped before:minecraft:shapeless");
        RecipeSorter.register("gregtech:shapeless", GT_Shapeless_Recipe.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
    }

    public void onLoad() {
        GT_Log.out.println("GT_Mod: Beginning Load-Phase.");
        GT_Log.ore.println("GT_Mod: Beginning Load-Phase.");
        GT_OreDictUnificator.registerOre("cropChilipepper", GT_ModHandler.getModItem("magicalcrops", "magicalcrops_CropProduce", 1, 2));
        GT_OreDictUnificator.registerOre("cropTomato", GT_ModHandler.getModItem("magicalcrops", "magicalcrops_CropProduce", 1, 8));
        GT_OreDictUnificator.registerOre("cropGrape", GT_ModHandler.getModItem("magicalcrops", "magicalcrops_CropProduce", 1, 4));
        GT_OreDictUnificator.registerOre("cropTea", GT_ModHandler.getModItem("ganyssurface", "teaLeaves", 1, 0));

        GregTech_API.sLoadStarted = true;
        for (FluidContainerRegistry.FluidContainerData tData : FluidContainerRegistry.getRegisteredFluidContainerData()) {
            if ((tData.filledContainer.getItem() == Items.POTIONITEM) && (tData.filledContainer.getItemDamage() == 0)) {
                tData.fluid.amount = 0;
                break;
            }
        }
    }

    public static long tBits = GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.ONLY_ADD_IF_RESULT_IS_NOT_NULL | GT_ModHandler.RecipeBits.NOT_REMOVABLE;
    public void onPostLoad() {
        GT_Log.out.println("GT_Mod: Beginning PostLoad-Phase.");
        GT_Log.ore.println("GT_Mod: Beginning PostLoad-Phase.");
        if (GT_Log.pal != null) {
            new Thread(new GT_PlayerActivityLogger()).start();
        }
        GregTech_API.sPostloadStarted = true;
        GT_OreDictUnificator.addItemData(new ItemStack(Items.IRON_DOOR, 1), new ItemData(Materials.Iron, 21772800L, new MaterialStack[0]));
        GT_OreDictUnificator.addItemData(new ItemStack(Items.ACACIA_DOOR, 1, 32767), new ItemData(Materials.Wood, 21772800L, new MaterialStack[0]));
        GT_OreDictUnificator.addItemData(new ItemStack(Items.BIRCH_DOOR, 1, 32767), new ItemData(Materials.Wood, 21772800L, new MaterialStack[0]));
        GT_OreDictUnificator.addItemData(new ItemStack(Items.JUNGLE_DOOR, 1, 32767), new ItemData(Materials.Wood, 21772800L, new MaterialStack[0]));
        GT_OreDictUnificator.addItemData(new ItemStack(Items.OAK_DOOR, 1, 32767), new ItemData(Materials.Wood, 21772800L, new MaterialStack[0]));
        GT_OreDictUnificator.addItemData(new ItemStack(Items.SPRUCE_DOOR, 1, 32767), new ItemData(Materials.Wood, 21772800L, new MaterialStack[0]));
        GT_OreDictUnificator.addItemData(new ItemStack(Items.DARK_OAK_DOOR, 1, 32767), new ItemData(Materials.Wood, 21772800L, new MaterialStack[0]));
        for (FluidContainerRegistry.FluidContainerData tData : FluidContainerRegistry.getRegisteredFluidContainerData()) {
            if ((tData.filledContainer.getItem() == Items.POTIONITEM) && (tData.filledContainer.getItemDamage() == 0)) {
                tData.fluid.amount = 0;
                break;
            }
        }
        GT_Log.out.println("GT_Mod: Adding Configs specific for MetaTileEntities");
        try {
            for (int i = 1; i < GregTech_API.METATILEENTITIES.length; i++) {
                for (; i < GregTech_API.METATILEENTITIES.length; i++) {
                    if (GregTech_API.METATILEENTITIES[i] != null) {
                        GregTech_API.METATILEENTITIES[i].onConfigLoad(GregTech_API.sMachineFile);
                    }
                }
            }
        } catch (Throwable e) {
            e.printStackTrace(GT_Log.err);
        }
        GT_Log.out.println("GT_Mod: Adding Tool Usage Crafting Recipes for OreDict Items.");
        for (Materials aMaterial : Materials.values()) {
            if ((aMaterial.mUnificatable) && (aMaterial.mMaterialInto == aMaterial)) {
                /*if (!aMaterial.contains(SubTag.NO_SMASHING)) {
                    if (GregTech_API.sRecipeFile.get(ConfigCategories.Tools.hammerplating, aMaterial.toString(), true)) {
                        GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial, 1), tBits, new Object[]{"h", "X", "X",
                                Character.valueOf('X'), OrePrefixes.ingot.get(aMaterial)});
                        GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial, 1), tBits,
                                new Object[]{"h", "X", Character.valueOf('X'), OrePrefixes.gem.get(aMaterial)});
                        GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial, 1), tBits,
                                new Object[]{"H", "X", Character.valueOf('H'), ToolDictNames.craftingToolForgeHammer, Character.valueOf('X'),
                                        OrePrefixes.ingot.get(aMaterial)});
                        GT_ModHandler.addCraftingRecipe(
                                GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial, 1),
                                tBits,
                                new Object[]{"H", "X", Character.valueOf('H'), ToolDictNames.craftingToolForgeHammer, Character.valueOf('X'),
                                        OrePrefixes.gem.get(aMaterial)});
                        GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial, 1), tBits,
                                new Object[]{"h", "X", Character.valueOf('X'), OrePrefixes.ingotDouble.get(aMaterial)});
                        GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial, 2L), tBits,
                                new Object[]{"H", "X", Character.valueOf('H'), ToolDictNames.craftingToolForgeHammer, Character.valueOf('X'),
                                        OrePrefixes.ingotDouble.get(aMaterial)});
                    }
                    if (GregTech_API.sRecipeFile.get(ConfigCategories.Tools.hammermultiingot, aMaterial.toString(), true)) {
                        GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotDouble, aMaterial, 1), tBits, new Object[]{"I", "I", "h",
                                Character.valueOf('I'), OrePrefixes.ingot.get(aMaterial)});
                        GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotTriple, aMaterial, 1), tBits, new Object[]{"I", "B", "h",
                                Character.valueOf('I'), OrePrefixes.ingotDouble.get(aMaterial), Character.valueOf('B'), OrePrefixes.ingot.get(aMaterial)});
                        GT_ModHandler
                                .addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotQuadruple, aMaterial, 1), tBits,
                                        new Object[]{"I", "B", "h", Character.valueOf('I'), OrePrefixes.ingotTriple.get(aMaterial), Character.valueOf('B'),
                                                OrePrefixes.ingot.get(aMaterial)});
                        GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotQuintuple, aMaterial, 1), tBits,
                                new Object[]{"I", "B", "h", Character.valueOf('I'), OrePrefixes.ingotQuadruple.get(aMaterial), Character.valueOf('B'),
                                        OrePrefixes.ingot.get(aMaterial)});
                    }
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadAxe, aMaterial, 1), tBits, new Object[]{"PIh", "P  ",
                            "f  ", Character.valueOf('P'), OrePrefixes.plate.get(aMaterial), Character.valueOf('I'), OrePrefixes.ingot.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadHammer, aMaterial, 1), tBits, new Object[]{"II ", "IIh",
                            "II ", Character.valueOf('P'), OrePrefixes.plate.get(aMaterial), Character.valueOf('I'), OrePrefixes.ingot.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadHoe, aMaterial, 1), tBits, new Object[]{"PIh", "f  ",
                            Character.valueOf('P'), OrePrefixes.plate.get(aMaterial), Character.valueOf('I'), OrePrefixes.ingot.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadPickaxe, aMaterial, 1), tBits, new Object[]{"PII", "f h",
                            Character.valueOf('P'), OrePrefixes.plate.get(aMaterial), Character.valueOf('I'), OrePrefixes.ingot.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadPlow, aMaterial, 1), tBits, new Object[]{"PP", "PP", "hf",
                            Character.valueOf('P'), OrePrefixes.plate.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadSaw, aMaterial, 1), tBits, new Object[]{"PP ", "fh ",
                            Character.valueOf('P'), OrePrefixes.plate.get(aMaterial), Character.valueOf('I'), OrePrefixes.ingot.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadSense, aMaterial, 1), tBits, new Object[]{"PPI", "hf ",
                            Character.valueOf('P'), OrePrefixes.plate.get(aMaterial), Character.valueOf('I'), OrePrefixes.ingot.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(
                            GT_OreDictUnificator.get(OrePrefixes.toolHeadShovel, aMaterial, 1),
                            tBits,
                            new Object[]{"fPh", 'P', OrePrefixes.plate.get(aMaterial), 'I',
                                    OrePrefixes.ingot.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadSword, aMaterial, 1), tBits, new Object[]{" P ", "fPh",
                            'P', OrePrefixes.plate.get(aMaterial), 'I', OrePrefixes.ingot.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.ring, aMaterial, 1), tBits,
                            new Object[]{"h ", " X", 'X', OrePrefixes.stick.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.stickLong, aMaterial, 1), tBits,
                            new Object[]{"ShS", 'S', OrePrefixes.stick.get(aMaterial)});
                }*/
                /*if (!aMaterial.contains(SubTag.NO_WORKING)) {
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, aMaterial, 2L), tBits,
                            new Object[]{"s", "X", Character.valueOf('X'), OrePrefixes.stickLong.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, aMaterial, 1), tBits,
                            new Object[]{"f ", " X", Character.valueOf('X'), OrePrefixes.ingot.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.bolt, aMaterial, 2L), tBits,
                            new Object[]{"s ", " X", Character.valueOf('X'), OrePrefixes.stick.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.screw, aMaterial, 1), tBits,
                            new Object[]{"fX", "X ", Character.valueOf('X'), OrePrefixes.bolt.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.round, aMaterial, 1), tBits,
                            new Object[]{"fX", "X ", Character.valueOf('X'), OrePrefixes.nugget.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.rotor, aMaterial, 1), tBits, new Object[]{"PhP", "SRf", "PdP",
                            Character.valueOf('P'), aMaterial == Materials.Wood ? OrePrefixes.plank.get(aMaterial) : OrePrefixes.plate.get(aMaterial),
                            Character.valueOf('R'), OrePrefixes.ring.get(aMaterial), Character.valueOf('S'), OrePrefixes.screw.get(aMaterial)});
                    GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial, 4L), GT_OreDictUnificator.get(OrePrefixes.ring, aMaterial, 1), Materials.Tin.getMolten(32), GT_OreDictUnificator.get(OrePrefixes.rotor, aMaterial, 1), 240, 24);
                    GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial, 4L), GT_OreDictUnificator.get(OrePrefixes.ring, aMaterial, 1), Materials.Lead.getMolten(48), GT_OreDictUnificator.get(OrePrefixes.rotor, aMaterial, 1), 240, 24);
                    GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial, 4L), GT_OreDictUnificator.get(OrePrefixes.ring, aMaterial, 1), Materials.SolderingAlloy.getMolten(16), GT_OreDictUnificator.get(OrePrefixes.rotor, aMaterial, 1), 240, 24);
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.stickLong, aMaterial, 1), tBits,
                            new Object[]{"sf", "G ", Character.valueOf('G'), OrePrefixes.gemFlawless.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.stickLong, aMaterial, 2L), tBits,
                            new Object[]{"sf", "G ", Character.valueOf('G'), OrePrefixes.gemExquisite.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.wireGt01, aMaterial, 1), tBits,
                            new Object[]{"Xx", Character.valueOf('X'), OrePrefixes.plate.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.wireFine, aMaterial, 1), tBits,
                            new Object[]{"Xx", Character.valueOf('X'), OrePrefixes.foil.get(aMaterial)});

                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.turbineBlade, aMaterial, 1), tBits, new Object[]{"fPd", "SPS", " P ",
                            Character.valueOf('P'), aMaterial == Materials.Wood ? OrePrefixes.plank.get(aMaterial) : OrePrefixes.plateDouble.get(aMaterial),
                            Character.valueOf('R'), OrePrefixes.ring.get(aMaterial), Character.valueOf('S'), OrePrefixes.screw.get(aMaterial)});


                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.arrowGtWood, aMaterial, 1), tBits, new Object[]{"  A", " S ",
                            "F  ", Character.valueOf('S'), OrePrefixes.stick.get(Materials.Wood), Character.valueOf('F'), OreDictNames.craftingFeather,
                            Character.valueOf('A'), OrePrefixes.toolHeadArrow.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.arrowGtPlastic, aMaterial, 1), tBits, new Object[]{"  A", " S ",
                            "F  ", Character.valueOf('S'), OrePrefixes.stick.get(Materials.Plastic), Character.valueOf('F'), OreDictNames.craftingFeather,
                            Character.valueOf('A'), OrePrefixes.toolHeadArrow.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadArrow, aMaterial, 1), tBits,
                            new Object[]{"Xf", Character.valueOf('X'), OrePrefixes.gemChipped.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadArrow, aMaterial, 3L), tBits,
                            new Object[]{(aMaterial.contains(SubTag.WOOD) ? 115 : 'x') + "Pf", Character.valueOf('P'), OrePrefixes.plate.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadAxe, aMaterial, 1), tBits, new Object[]{"GG ", "G  ",
                            "f  ", Character.valueOf('G'), OrePrefixes.gem.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadHoe, aMaterial, 1), tBits, new Object[]{"GG ", "f  ",
                            "   ", Character.valueOf('G'), OrePrefixes.gem.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadPickaxe, aMaterial, 1), tBits, new Object[]{"GGG", "f  ",
                            Character.valueOf('G'), OrePrefixes.gem.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadPlow, aMaterial, 1), tBits, new Object[]{"GG", "GG", " f",
                            Character.valueOf('G'), OrePrefixes.gem.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadSaw, aMaterial, 1), tBits,
                            new Object[]{"GGf", Character.valueOf('G'), OrePrefixes.gem.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadSense, aMaterial, 1), tBits, new Object[]{"GGG", " f ",
                            "   ", Character.valueOf('G'), OrePrefixes.gem.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadShovel, aMaterial, 1), tBits,
                            new Object[]{"fG", Character.valueOf('G'), OrePrefixes.gem.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadSword, aMaterial, 1), tBits, new Object[]{" G", "fG",
                            Character.valueOf('G'), OrePrefixes.gem.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadUniversalSpade, aMaterial, 1), tBits, new Object[]{"fX",
                            Character.valueOf('X'), OrePrefixes.toolHeadShovel.get(aMaterial)});

                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadBuzzSaw, aMaterial, 1), tBits, new Object[]{"wXh", "X X",
                            "fXx", Character.valueOf('X'), OrePrefixes.plate.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadWrench, aMaterial, 1), tBits, new Object[]{"hXW", "XRX",
                            "WXd", Character.valueOf('X'), OrePrefixes.plate.get(aMaterial), Character.valueOf('S'), OrePrefixes.plate.get(Materials.Steel),
                            Character.valueOf('R'), OrePrefixes.ring.get(Materials.Steel), Character.valueOf('W'), OrePrefixes.screw.get(Materials.Steel)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadChainsaw, aMaterial, 1), tBits, new Object[]{"SRS", "XhX",
                            "SRS", Character.valueOf('X'), OrePrefixes.plate.get(aMaterial), Character.valueOf('S'), OrePrefixes.plate.get(Materials.Steel),
                            Character.valueOf('R'), OrePrefixes.ring.get(Materials.Steel)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadDrill, aMaterial, 1), tBits, new Object[]{"XSX", "XSX",
                            "ShS", Character.valueOf('X'), OrePrefixes.plate.get(aMaterial), Character.valueOf('S'), OrePrefixes.plate.get(Materials.Steel)});
                    switch (aMaterial.mName) {
                        case "Wood":
                            GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, aMaterial, 1), tBits, new Object[]{"P ", " s",
                                    Character.valueOf('P'), OrePrefixes.plank.get(aMaterial)});
                            break;
                        case "Stone":
                            GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, aMaterial, 1), tBits, new Object[]{"P ", " f",
                                    Character.valueOf('P'), OrePrefixes.stoneSmooth});
                            break;
                        default:
                            GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, aMaterial, 1), tBits,
                                    new Object[]{"P ", aMaterial.contains(SubTag.WOOD) ? " s" : " h", Character.valueOf('P'), OrePrefixes.plate.get(aMaterial)});
                    }
                    switch (aMaterial.mName) {
                        case "Wood":
                            GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.gearGt, aMaterial, 1), tBits, new Object[]{"SPS", "PsP", "SPS",
                                    Character.valueOf('P'), OrePrefixes.plank.get(aMaterial), Character.valueOf('S'), OrePrefixes.stick.get(aMaterial)});
                            break;
                        case "Stone":
                            GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.gearGt, aMaterial, 1), tBits, new Object[]{"SPS", "PfP", "SPS",
                                    Character.valueOf('P'), OrePrefixes.stoneSmooth, Character.valueOf('S'), new ItemStack(Blocks.STONE_BUTTON, 1, 32767)});
                            break;
                        default:
                            GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.gearGt, aMaterial, 1), tBits, new Object[]{"SPS", "PwP", "SPS",
                                    Character.valueOf('P'), OrePrefixes.plate.get(aMaterial), Character.valueOf('S'), OrePrefixes.stick.get(aMaterial)});
                    }
                }*/
                /*if (aMaterial.contains(SubTag.SMELTING_TO_GEM)) {
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.gem, aMaterial, 1), tBits, new Object[]{"XXX", "XXX", "XXX",
                            Character.valueOf('X'), OrePrefixes.nugget.get(aMaterial)});
                } else {
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial, 1), tBits, new Object[]{"XXX", "XXX", "XXX",
                            Character.valueOf('X'), OrePrefixes.nugget.get(aMaterial)});
                }*/
                GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial.mMacerateInto, 1), tBits, new Object[]{"h", "X",
                        'X', OrePrefixes.crushedCentrifuged.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial.mMacerateInto, 1), tBits, new Object[]{"h", "X",
                        'X', OrePrefixes.crystalline.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial.mMacerateInto, 1), tBits, new Object[]{"h", "X",
                        'X', OrePrefixes.crystal.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dustPure, aMaterial.mMacerateInto, 1), tBits, new Object[]{"h", "X",
                        'X', OrePrefixes.crushedPurified.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dustPure, aMaterial.mMacerateInto, 1), tBits, new Object[]{"h", "X",
                        'X', OrePrefixes.cleanGravel.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dustPure, aMaterial.mMacerateInto, 1), tBits, new Object[]{"h", "X",
                        'X', OrePrefixes.reduced.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dustImpure, aMaterial.mMacerateInto, 1), tBits, new Object[]{"h", "X",
                        'X', OrePrefixes.clump.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dustImpure, aMaterial.mMacerateInto, 1), tBits, new Object[]{"h", "X",
                        'X', OrePrefixes.shard.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dustImpure, aMaterial.mMacerateInto, 1), tBits, new Object[]{"h", "X",
                        'X', OrePrefixes.crushed.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dustImpure, aMaterial.mMacerateInto, 1), tBits, new Object[]{"h", "X",
                        'X', OrePrefixes.dirtyGravel.get(aMaterial)});

                GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dustSmall, aMaterial, 4L), tBits,
                        new Object[]{" X", "  ", 'X', OrePrefixes.dust.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dustTiny, aMaterial, 9L), tBits,
                        new Object[]{"X ", "  ", 'X', OrePrefixes.dust.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, 1), tBits,
                        new Object[]{"XX", "XX", 'X', OrePrefixes.dustSmall.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, 1), tBits,
                        new Object[]{"XXX", "XXX", "XXX", 'X', OrePrefixes.dustTiny.get(aMaterial)});
//                GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, 16L), tBits, new Object[]{"Xc", Character.valueOf('X'),
//                        OrePrefixes.crateGtDust.get(aMaterial)});
//                GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.gem, aMaterial, 16L), tBits, new Object[]{"Xc", Character.valueOf('X'),
//                        OrePrefixes.crateGtGem.get(aMaterial)});
//                GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial, 16L), tBits, new Object[]{"Xc",
//                        Character.valueOf('X'), OrePrefixes.crateGtIngot.get(aMaterial)});
//                GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial, 16L), tBits, new Object[]{"Xc",
//                        Character.valueOf('X'), OrePrefixes.crateGtPlate.get(aMaterial)});
//
//                GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.gemChipped, aMaterial, 2L), tBits,
//                        new Object[]{"h", "X", Character.valueOf('X'), OrePrefixes.gemFlawed.get(aMaterial)});
//                GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.gemFlawed, aMaterial, 2L), tBits,
//                        new Object[]{"h", "X", Character.valueOf('X'), OrePrefixes.gem.get(aMaterial)});
//                GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.gem, aMaterial, 2L), tBits,
//                        new Object[]{"h", "X", Character.valueOf('X'), OrePrefixes.gemFlawless.get(aMaterial)});
//                GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.gemFlawless, aMaterial, 2L), tBits,
//                        new Object[]{"h", "X", Character.valueOf('X'), OrePrefixes.gemExquisite.get(aMaterial)});
//                if ((aMaterial.contains(SubTag.MORTAR_GRINDABLE)) && (GregTech_API.sRecipeFile.get(ConfigCategories.Tools.mortar, aMaterial.mName, true))) {
//                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dustSmall, aMaterial, 1), tBits,
//                            new Object[]{"X", "m", Character.valueOf('X'), OrePrefixes.gemChipped.get(aMaterial)});
//                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dustSmall, aMaterial, 2L), tBits,
//                            new Object[]{"X", "m", Character.valueOf('X'), OrePrefixes.gemFlawed.get(aMaterial)});
//                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, 1), tBits,
//                            new Object[]{"X", "m", Character.valueOf('X'), OrePrefixes.gem.get(aMaterial)});
//                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, 2L), tBits,
//                            new Object[]{"X", "m", Character.valueOf('X'), OrePrefixes.gemFlawless.get(aMaterial)});
//                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, 4L), tBits,
//                            new Object[]{"X", "m", Character.valueOf('X'), OrePrefixes.gemExquisite.get(aMaterial)});
//                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, 1), tBits,
//                            new Object[]{"X", "m", Character.valueOf('X'), OrePrefixes.ingot.get(aMaterial)});
//                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, 1), tBits,
//                            new Object[]{"X", "m", Character.valueOf('X'), OrePrefixes.plate.get(aMaterial)});
//                }
            }
        }
    }

    public void onServerStarting() {
        GT_Log.out.println("GT_Mod: ServerStarting-Phase started!");
        GT_Log.ore.println("GT_Mod: ServerStarting-Phase started!");

        this.mUniverse = null;
        this.isFirstServerWorldTick = true;
        for (FluidContainerRegistry.FluidContainerData tData : FluidContainerRegistry.getRegisteredFluidContainerData()) {
            if ((tData.filledContainer.getItem() == Items.POTIONITEM) && (tData.filledContainer.getItemDamage() == 0)) {
                tData.fluid.amount = 0;
                break;
            }
        }
        try {
            for (int i = 1; i < GregTech_API.METATILEENTITIES.length; i++) {
                for (; i < GregTech_API.METATILEENTITIES.length; i++) {
                    if (GregTech_API.METATILEENTITIES[i] != null) {
                        GregTech_API.METATILEENTITIES[i].onServerStart();
                    }
                }
            }
        } catch (Throwable e) {
            e.printStackTrace(GT_Log.err);
        }
    }

    public void onServerStarted() {
        GregTech_API.sWirelessRedstone.clear();

        GT_Log.out.println("GT_Mod: Cleaning up all OreDict Crafting Recipes, which have an empty List in them, since they are never meeting any Condition.");
        List tList = CraftingManager.getInstance().getRecipeList();
        for (int i = 0; i < tList.size(); i++) {

            if ((tList.get(i) instanceof ShapedOreRecipe)) {
                for (Object tObject : ((ShapedOreRecipe) tList.get(i)).getInput()) {
                    if (((tObject instanceof List)) && (((List) tObject).isEmpty())) {
                        tList.remove(i--);
                        break;
                    }
                }
            } else if ((tList.get(i) instanceof ShapelessOreRecipe)) {
                for (Object tObject : ((ShapelessOreRecipe) tList.get(i)).getInput()) {
                    if (((tObject instanceof List)) && (((List) tObject).isEmpty())) {
                        tList.remove(i--);
                        break;
                    }
                }
            }

        }

    }

    public void onServerStopping() {
        File tSaveDirectory = getSaveDirectory();
        GregTech_API.sWirelessRedstone.clear();
        if (tSaveDirectory != null) {
            try {
                for (int i = 1; i < GregTech_API.METATILEENTITIES.length; i++) {
                    for (; i < GregTech_API.METATILEENTITIES.length; i++) {
                        if (GregTech_API.METATILEENTITIES[i] != null) {
                            GregTech_API.METATILEENTITIES[i].onWorldSave(tSaveDirectory);
                        }
                    }
                }
            } catch (Throwable e) {
                e.printStackTrace(GT_Log.err);
            }
        }
        this.mUniverse = null;
    }

    @SubscribeEvent
    public void onClientConnectedToServerEvent(FMLNetworkEvent.ClientConnectedToServerEvent aEvent) {
    }

    @SubscribeEvent
    public void onArrowNockEvent(ArrowNockEvent aEvent) {
        if ((!aEvent.isCanceled()) && (GT_Utility.isStackValid(aEvent.getBow()))
                && (GT_Utility.getProjectile(SubTag.PROJECTILE_ARROW, aEvent.getEntityPlayer().inventory) != null)) {
            //aEvent.getEntityPlayer().setItemInUse(aEvent.result, aEvent.getR.getItem().getMaxItemUseDuration(aEvent.result));
            aEvent.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onArrowLooseEvent(ArrowLooseEvent aEvent) {
        ItemStack aArrow = GT_Utility.getProjectile(SubTag.PROJECTILE_ARROW, aEvent.getEntityPlayer().inventory);
        if ((!aEvent.isCanceled()) && (GT_Utility.isStackValid(aEvent.getBow())) && (aArrow != null) && ((aEvent.getBow().getItem() instanceof ItemBow))) {
            float tSpeed = aEvent.getCharge() / 20.0F;
            tSpeed = (tSpeed * tSpeed + tSpeed * 2.0F) / 3.0F;
            if (tSpeed < 0.1D) {
                return;
            }
            if (tSpeed > 1.0D) {
                tSpeed = 1.0F;
            }
            EntityArrow tArrowEntity = ((IProjectileItem) aArrow.getItem()).getProjectile(SubTag.PROJECTILE_ARROW, aArrow, aEvent.getEntityPlayer().worldObj,
                    aEvent.getEntityPlayer(), tSpeed * 2.0F);
            if (tSpeed >= 1.0F) {
                tArrowEntity.setIsCritical(true);
            }
            int tLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, aEvent.getBow());
            if (tLevel > 0) {
                tArrowEntity.setDamage(tArrowEntity.getDamage() + tLevel * 0.5D + 0.5D);
            }
            tLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, aEvent.getBow());
            if (tLevel > 0) {
                tArrowEntity.setKnockbackStrength(tLevel);
            }
            tLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, aEvent.getBow());
            if (tLevel > 0) {
                tArrowEntity.setFire(tLevel * 100);
            }
            aEvent.getBow().damageItem(1, aEvent.getEntityPlayer());
            aEvent.getEntityPlayer().worldObj.playSound(null, new BlockPos(aEvent.getEntity()),
                    SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS,
                    1.0F, 0.64893958288F + tSpeed * 0.5F);

            if (!aEvent.getEntityPlayer().capabilities.isCreativeMode) {
                aArrow.stackSize -= 1;
            }

            if (aArrow.stackSize == 0) {
                GT_Utility.removeNullStacksFromInventory(aEvent.getEntityPlayer().inventory);
            }

            if (!aEvent.getEntityPlayer().worldObj.isRemote) {
                aEvent.getEntityPlayer().worldObj.spawnEntityInWorld(tArrowEntity);
            }

            aEvent.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onEndermanTeleportEvent(EnderTeleportEvent aEvent) {
        if (aEvent.getEntity() instanceof EntityEnderman && aEvent.getEntityLiving()
                .getActivePotionEffect(MobEffects.WEAKNESS) != null) {
            aEvent.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onEntitySpawningEvent(EntityJoinWorldEvent aEvent) {
        if (aEvent.getEntity() != null && !aEvent.getEntity().worldObj.isRemote) {
            if (aEvent.getEntity() instanceof EntityItem) {
                ((EntityItem) aEvent.getEntity()).setEntityItemStack(GT_OreDictUnificator.get(((EntityItem) aEvent.getEntity()).getEntityItem()));
            }
            if ((this.mSkeletonsShootGTArrows > 0) && (aEvent.getEntity().getClass() == EntityArrow.class)
                    && (aEvent.getEntity().worldObj.rand.nextInt(this.mSkeletonsShootGTArrows) == 0)
                    && ((((EntityArrow) aEvent.getEntity()).shootingEntity instanceof EntitySkeleton))) {
                aEvent.getWorld().spawnEntityInWorld(new GT_Entity_Arrow(aEvent.getWorld(),
                        (EntityLivingBase) ((EntityArrow) aEvent.getEntity()).shootingEntity,
                        OrePrefixes.arrowGtWood.mPrefixedItems
                                .get(aEvent.getWorld().rand.nextInt(OrePrefixes.arrowGtWood.mPrefixedItems.size()))));
                aEvent.getEntity().setDead();
            }
        }
    }

    @SubscribeEvent
    public void onOreGenEvent(OreGenEvent.GenerateMinable aGenerator) {
        if (aGenerator.getGenerator() instanceof WorldGenMinable) {
            if (PREVENTED_ORES.contains(aGenerator.getType())) {
                if (mDisableVanillaOres) {
                    aGenerator.setResult(Result.DENY);
                }
                return;
            }
            if (mDisableModdedOres) {
                WorldGenMinable worldGenMinable = (WorldGenMinable) aGenerator.getGenerator();
                IBlockState oreBlock = ObfuscationReflectionHelper.getPrivateValue(WorldGenMinable.class, worldGenMinable, 0);
                ItemData itemData = GT_OreDictUnificator.getAssociation(oreBlock);
                if(itemData != null && itemData.mPrefix.toString().startsWith("ore") && (itemData.mMaterial.mMaterial.mTypes & 0x08) != 0) {
                    aGenerator.setResult(Result.DENY);
                }
            }
        }
    }

    private String getDataAndTime() {
        return this.mDateFormat.format(new Date());
    }

    public void onPlayerInteraction(PlayerInteractEvent.RightClickBlock aEvent) {
        ItemStack aStack = aEvent.getItemStack();
        if (aStack != null && aStack.getItem() == Items.FLINT_AND_STEEL) {
            if (!aEvent.getWorld().isRemote &&
                    !aEvent.getEntityPlayer().capabilities.isCreativeMode &&
                    aEvent.getWorld().rand.nextInt(100) >= this.mFlintChance) {
                aStack.damageItem(1, aEvent.getEntityPlayer());
                if (aStack.getItemDamage() >= aStack.getMaxDamage())
                    aStack.stackSize -= 1;
                aEvent.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onBlockHarvestingEvent(BlockEvent.HarvestDropsEvent aEvent) {
        if (aEvent.getHarvester() != null) {
            ItemStack aStack = aEvent.getHarvester().getHeldItemMainhand();
            if (aStack != null) {
                if ((aStack.getItem() instanceof GT_MetaGenerated_Tool)) {
                    ((GT_MetaGenerated_Tool) aStack.getItem())
                            .onHarvestBlockEvent(new ArrayList<>(aEvent.getDrops()), aStack, aEvent.getHarvester(),
                                    aEvent.getState(), aEvent.getPos(),
                                    aEvent.getFortuneLevel(), aEvent.isSilkTouching(), aEvent);
                }
                if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FIRE_ASPECT, aStack) > 2) {
                    for (ItemStack tDrop : aEvent.getDrops()) {
                        ItemStack tSmeltingOutput = GT_ModHandler.getSmeltingOutput(tDrop, false, null);
                        if (tSmeltingOutput != null) {
                            tDrop.stackSize *= tSmeltingOutput.stackSize;
                            tSmeltingOutput.stackSize = tDrop.stackSize;
                            GT_Utility.setStack(tDrop, tSmeltingOutput);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void registerOre(OreDictionary.OreRegisterEvent aEvent) {
        ModContainer tContainer = Loader.instance().activeModContainer();
        String aMod = tContainer == null ? "UNKNOWN" : tContainer.getModId();
        String aOriginalMod = aMod;
        if (GT_OreDictUnificator.isRegisteringOres()) {
            aMod = "gregtech";
        } else if (aMod.equals("gregtech")) {
            aMod = "UNKNOWN";
        }
        if ((aEvent == null) || (aEvent.getOre() == null) || (aEvent.getOre().getItem() == null) || (aEvent.getName() == null) || (aEvent.getName().isEmpty())
                || (aEvent.getName().replaceAll("_", "").length() - aEvent.getName().length() == 9)) {
            if (aOriginalMod.equals("gregtech")) {
                aOriginalMod = "UNKNOWN";
            }
            GT_Log.ore
                    .println(aOriginalMod
                            + " did something very bad! The registration is too invalid to even be shown properly. This happens only if you register null, invalid Items, empty Strings or even nonexisting Events to the OreDict.");
            throw new IllegalArgumentException(
                    aOriginalMod
                            + " did something very bad! The registration is too invalid to even be shown properly. This happens only if you register null, invalid Items, empty Strings or even nonexisting Events to the OreDict.");
        }
        try {
            aEvent.getOre().stackSize = 1;
            if (this.mIgnoreTcon || aEvent.getOre().getUnlocalizedName().startsWith("item.oreberry")) {
                if ((aOriginalMod.toLowerCase(Locale.ENGLISH).contains("xycraft")) || (aOriginalMod.toLowerCase(Locale.ENGLISH).contains("tconstruct"))
                        || ((aOriginalMod.toLowerCase(Locale.ENGLISH).contains("natura")) && (!aOriginalMod.toLowerCase(Locale.ENGLISH).contains("natural")))) {
                    if (GT_Values.D1) {
                        GT_Log.ore.println(aMod + " -> " + aEvent.getName() + " is getting ignored, because of racism. :P");
                    }
                    return;
                }
            }
            if(aEvent.getOre().getItem() instanceof ItemIC2FluidContainer) {
                System.out.println("Ignoring IC2 fluid container " + aEvent.getOre() + " registration as " + aEvent.getName() + " because ic2 fluid containers are mad.");
                GT_OreDictUnificator.addToBlacklist(aEvent.getOre());
                return;
            }
            String tModToName = aMod + " -> " + aEvent.getName();
            if ((this.mOreDictActivated) || (GregTech_API.sPostloadStarted) || ((this.mSortToTheEnd) && (GregTech_API.sLoadFinished))) {
                tModToName = aOriginalMod + " --Late--> " + aEvent.getName();
            }
            if (((aEvent.getOre().getItem() instanceof ItemBlock)) || (GT_Utility.getBlockFromStack(aEvent.getOre()) != null)) {
                GT_OreDictUnificator.addToBlacklist(aEvent.getOre());
            }
            this.mRegisteredOres.add(aEvent.getOre());

            if (this.mIgnoredItems.contains(aEvent.getName())) {
                if ((aEvent.getName().startsWith("item"))) {
                    GT_Log.ore.println(tModToName);
                    if (aEvent.getName().equals("itemCopperWire")) {
                        GT_OreDictUnificator.registerOre(OreDictNames.craftingWireCopper, aEvent.getOre());
                    }
                    if (aEvent.getName().equals("itemRubber")) {
                        GT_OreDictUnificator.registerOre(OrePrefixes.ingot, Materials.Rubber, aEvent.getOre());
                    }
                    return;
                }
            } else if (this.mIgnoredNames.contains(aEvent.getName())){
                GT_Log.ore.println(tModToName + " is getting ignored via hardcode.");
                return;
            } else if (aEvent.getName().equals("stone")) {
                GT_OreDictUnificator.registerOre("stoneSmooth", aEvent.getOre());
                return;
            } else if (aEvent.getName().equals("cobblestone")) {
                GT_OreDictUnificator.registerOre("stoneCobble", aEvent.getOre());
                return;
            } else if ((aEvent.getName().contains("|")) || (aEvent.getName().contains("*")) || (aEvent.getName().contains(":")) || (aEvent.getName().contains("."))
                    || (aEvent.getName().contains("$"))) {
                GT_Log.ore.println(tModToName + " is using a private Prefix and is therefor getting ignored properly.");
                return;
            } else if (aEvent.getName().equals("copperWire")) {
                GT_OreDictUnificator.registerOre(OreDictNames.craftingWireCopper, aEvent.getOre());
            } else if (aEvent.getName().equals("oreHeeEndrium")) {
                GT_OreDictUnificator.registerOre(OrePrefixes.ore, Materials.Endium, aEvent.getOre());
            } else if (aEvent.getName().equals("sheetPlastic")) {
                GT_OreDictUnificator.registerOre(OrePrefixes.plate, Materials.Plastic, aEvent.getOre());
            } else if (aEvent.getName().equals("shard")) {
                if (aEvent.getName().equals("shardAir")) {
                    GT_OreDictUnificator.registerOre(OrePrefixes.gem, Materials.InfusedAir, aEvent.getOre());
                    return;
                } else if (aEvent.getName().equals("shardWater")) {
                    GT_OreDictUnificator.registerOre(OrePrefixes.gem, Materials.InfusedWater, aEvent.getOre());
                    return;
                } else if (aEvent.getName().equals("shardFire")) {
                    GT_OreDictUnificator.registerOre(OrePrefixes.gem, Materials.InfusedFire, aEvent.getOre());
                    return;
                } else if (aEvent.getName().equals("shardEarth")) {
                    GT_OreDictUnificator.registerOre(OrePrefixes.gem, Materials.InfusedEarth, aEvent.getOre());
                    return;
                } else if (aEvent.getName().equals("shardOrder")) {
                    GT_OreDictUnificator.registerOre(OrePrefixes.gem, Materials.InfusedOrder, aEvent.getOre());
                    return;
                } else if (aEvent.getName().equals("shardEntropy")) {
                    GT_OreDictUnificator.registerOre(OrePrefixes.gem, Materials.InfusedEntropy, aEvent.getOre());
                    return;
                }
            } else if (aEvent.getName().equals("fieryIngot")) {
                GT_OreDictUnificator.registerOre(OrePrefixes.ingot, Materials.FierySteel, aEvent.getOre());
                return;
            } else if (aEvent.getName().equals("ironwood")) {
                GT_OreDictUnificator.registerOre(OrePrefixes.ingot, Materials.IronWood, aEvent.getOre());
                return;
            } else if (aEvent.getName().equals("steeleaf")) {
                GT_OreDictUnificator.registerOre(OrePrefixes.ingot, Materials.Steeleaf, aEvent.getOre());
                return;
            } else if (aEvent.getName().equals("knightmetal")) {
                GT_OreDictUnificator.registerOre(OrePrefixes.ingot, Materials.Knightmetal, aEvent.getOre());
                return;
            } else if (aEvent.getName().equals("compressedAluminum")) {
                GT_OreDictUnificator.registerOre(OrePrefixes.compressed, Materials.Aluminium, aEvent.getOre());
                return;
            } else if (aEvent.getName().contains(" ")) {
                GT_Log.ore.println(tModToName + " is getting re-registered because the OreDict Name containing invalid spaces.");
                GT_OreDictUnificator.registerOre(aEvent.getName().replaceAll(" ", ""), GT_Utility.copyAmount(1, aEvent.getOre()));
                aEvent.getOre().setStackDisplayName("Invalid OreDictionary Tag");
                return;
            } else if (this.mInvalidNames.contains(aEvent.getName())) {
                GT_Log.ore.println(tModToName + " is wrongly registered and therefor getting ignored.");

                return;
            }
            OrePrefixes aPrefix = OrePrefixes.getOrePrefix(aEvent.getName());
            Materials aMaterial = Materials._NULL;
            if ((aPrefix == OrePrefixes.nugget) && (aMod.equals("Thaumcraft")) && (aEvent.getOre().getItem().getUnlocalizedName().contains("ItemResource"))) {
                return;
            }
            if (aPrefix == null) {
                if (aEvent.getName().toLowerCase().equals(aEvent.getName())) {
                    GT_Log.ore.println(tModToName + " is invalid due to being solely lowercased.");
                    return;
                } else if (aEvent.getName().toUpperCase().equals(aEvent.getName())) {
                    GT_Log.ore.println(tModToName + " is invalid due to being solely uppercased.");
                    return;
                } else if (Character.isUpperCase(aEvent.getName().charAt(0))) {
                    GT_Log.ore.println(tModToName + " is invalid due to the first character being uppercased.");
                }
            } else {
                if (aPrefix.mDontUnificateActively) {
                    GT_OreDictUnificator.addToBlacklist(aEvent.getOre());
                }
                if (aPrefix != aPrefix.mPrefixInto) {
                    String tNewName = aEvent.getName().replaceFirst(aPrefix.toString(), aPrefix.mPrefixInto.toString());
                    if (!GT_OreDictUnificator.isRegisteringOres()) {
                        GT_Log.ore.println(tModToName + " uses a depricated Prefix, and is getting re-registered as " + tNewName);
                    }
                    GT_OreDictUnificator.registerOre(tNewName, aEvent.getOre());
                    return;
                }
                String tName = aEvent.getName().replaceFirst(aPrefix.toString(), "");
                if (tName.length() > 0) {
                    char firstChar = tName.charAt(0);
                    if (Character.isUpperCase(firstChar) || Character.isLowerCase(firstChar) || firstChar == '_') {
                        if (aPrefix.mIsMaterialBased) {
                            aMaterial = Materials.get(tName);
                            if (aMaterial != aMaterial.mMaterialInto) {
                                GT_OreDictUnificator.registerOre(aPrefix, aMaterial.mMaterialInto, aEvent.getOre());
                                if (!GT_OreDictUnificator.isRegisteringOres()) {
                                    GT_Log.ore.println(tModToName + " uses a deprecated Material and is getting re-registered as "
                                            + aPrefix.get(aMaterial.mMaterialInto));
                                }
                                return;
                            }
                            if (!aPrefix.isIgnored(aMaterial)) {
                                aPrefix.add(GT_Utility.copyAmount(1, new Object[]{aEvent.getOre()}));
                            }
                            if (aMaterial != Materials._NULL) {
                                Materials tReRegisteredMaterial;
                                for (Iterator i$ = aMaterial.mOreReRegistrations.iterator(); i$.hasNext(); GT_OreDictUnificator.registerOre(aPrefix,
                                        tReRegisteredMaterial, aEvent.getOre())) {
                                    tReRegisteredMaterial = (Materials) i$.next();
                                }
                                aMaterial.add(GT_Utility.copyAmount(1, new Object[]{aEvent.getOre()}));

                                if (GregTech_API.sThaumcraftCompat != null && aPrefix.doGenerateItem(aMaterial) && !aPrefix.isIgnored(aMaterial)) {
                                    List<TC_AspectStack> tAspects = new ArrayList<TC_AspectStack>();
                                    for (TC_AspectStack tAspect : aPrefix.mAspects) tAspect.addToAspectList(tAspects);
                                    if (aPrefix.mMaterialAmount >= 3628800 || aPrefix.mMaterialAmount < 0) for (TC_AspectStack tAspect : aMaterial.mAspects) tAspect.addToAspectList(tAspects);
                                    GregTech_API.sThaumcraftCompat.registerThaumcraftAspectsToItem(GT_Utility.copyAmount(1, aEvent.getOre()), tAspects, aEvent.getName());
                                }

                                switch (aPrefix) {
                                    case crystal:
                                        if ((aMaterial == Materials.CertusQuartz) || (aMaterial == Materials.NetherQuartz) || (aMaterial == Materials.Fluix)) {
                                            GT_OreDictUnificator.registerOre(OrePrefixes.gem, aMaterial, aEvent.getOre());
                                        }
                                        break;
                                    case gem:
                                        if (aMaterial == Materials.Lapis || aMaterial == Materials.Sodalite) {
                                            GT_OreDictUnificator.registerOre(Dyes.dyeBlue, aEvent.getOre());
                                        } else if (aMaterial == Materials.Lazurite) {
                                            GT_OreDictUnificator.registerOre(Dyes.dyeCyan, aEvent.getOre());
                                        } else if (aMaterial == Materials.InfusedAir || aMaterial == Materials.InfusedWater || aMaterial == Materials.InfusedFire || aMaterial == Materials.InfusedEarth || aMaterial == Materials.InfusedOrder || aMaterial == Materials.InfusedEntropy) {
                                            GT_OreDictUnificator.registerOre(aMaterial.mName.replaceFirst("Infused", "shard"), aEvent.getOre());
                                        } else if (aMaterial == Materials.Chocolate) {
                                            GT_OreDictUnificator.registerOre(Dyes.dyeBrown, aEvent.getOre());
                                        } else if (aMaterial == Materials.CertusQuartz || aMaterial == Materials.NetherQuartz) {
                                            GT_OreDictUnificator.registerOre(OrePrefixes.item.get(aMaterial), aEvent.getOre());
                                            GT_OreDictUnificator.registerOre(OrePrefixes.crystal, aMaterial, aEvent.getOre());
                                            GT_OreDictUnificator.registerOre(OreDictNames.craftingQuartz, aEvent.getOre());
                                        } else if (aMaterial == Materials.Fluix || aMaterial == Materials.Quartz || aMaterial == Materials.Quartzite) {
                                            GT_OreDictUnificator.registerOre(OrePrefixes.crystal, aMaterial, aEvent.getOre());
                                            GT_OreDictUnificator.registerOre(OreDictNames.craftingQuartz, aEvent.getOre());
                                        }
                                        break;
                                    case cableGt01:
                                        if (aMaterial == Materials.Tin) {
                                            GT_OreDictUnificator.registerOre(OreDictNames.craftingWireTin, aEvent.getOre());
                                        } else if (aMaterial == Materials.AnyCopper) {
                                            GT_OreDictUnificator.registerOre(OreDictNames.craftingWireCopper, aEvent.getOre());
                                        } else if (aMaterial == Materials.Gold) {
                                            GT_OreDictUnificator.registerOre(OreDictNames.craftingWireGold, aEvent.getOre());
                                        } else if (aMaterial == Materials.AnyIron) {
                                            GT_OreDictUnificator.registerOre(OreDictNames.craftingWireIron, aEvent.getOre());
                                        }
                                        break;
                                    case lens:
                                        if ((aMaterial.contains(SubTag.TRANSPARENT)) && (aMaterial.mColor != Dyes._NULL)) {
                                            GT_OreDictUnificator.registerOre("craftingLens" + aMaterial.mColor.toString().replaceFirst("DYE", ""), aEvent.getOre());
                                        }
                                        break;
                                    case plate:
                                        if ((aMaterial == Materials.Plastic) || (aMaterial == Materials.Rubber)) {
                                            GT_OreDictUnificator.registerOre(OrePrefixes.sheet, aMaterial, aEvent.getOre());
                                        } else if (aMaterial == Materials.Silicon) {
                                            GT_OreDictUnificator.registerOre(OrePrefixes.item, aMaterial, aEvent.getOre());
                                        } else if (aMaterial == Materials.Wood) {
                                            GT_OreDictUnificator.addToBlacklist(aEvent.getOre());
                                            GT_OreDictUnificator.registerOre(OrePrefixes.plank, aMaterial, aEvent.getOre());
                                        }
                                        break;
                                    case cell:
                                        if (aMaterial == Materials.Empty) {
                                            GT_OreDictUnificator.addToBlacklist(aEvent.getOre());
                                        }
                                        break;
                                    case gearGt:
                                        GT_OreDictUnificator.registerOre(OrePrefixes.gear, aMaterial, aEvent.getOre());
                                        break;
                                    case stick:
                                        if (!GT_RecipeRegistrator.sRodMaterialList.contains(aMaterial)) {
                                            GT_RecipeRegistrator.sRodMaterialList.add(aMaterial);
                                        } else if (aMaterial == Materials.Wood) {
                                            GT_OreDictUnificator.addToBlacklist(aEvent.getOre());
                                        } else if ((aMaterial == Materials.Tin) || (aMaterial == Materials.Lead) || (aMaterial == Materials.SolderingAlloy)) {
                                            GT_OreDictUnificator.registerOre(ToolDictNames.craftingToolSolderingMetal, aEvent.getOre());
                                        }
                                        break;
                                    case dust:
                                        if (aMaterial == Materials.Salt) {
                                            GT_OreDictUnificator.registerOre("itemSalt", aEvent.getOre());
                                        } else if (aMaterial == Materials.Wood) {
                                            GT_OreDictUnificator.registerOre("pulpWood", aEvent.getOre());
                                        } else if (aMaterial == Materials.Wheat) {
                                            GT_OreDictUnificator.registerOre("foodFlour", aEvent.getOre());
                                        } else if (aMaterial == Materials.Lapis) {
                                            GT_OreDictUnificator.registerOre(Dyes.dyeBlue, aEvent.getOre());
                                        } else if (aMaterial == Materials.Lazurite) {
                                            GT_OreDictUnificator.registerOre(Dyes.dyeCyan, aEvent.getOre());
                                        } else if (aMaterial == Materials.Sodalite) {
                                            GT_OreDictUnificator.registerOre(Dyes.dyeBlue, aEvent.getOre());
                                        } else if (aMaterial == Materials.Cocoa) {
                                            GT_OreDictUnificator.registerOre(Dyes.dyeBrown, aEvent.getOre());
                                            GT_OreDictUnificator.registerOre("foodCocoapowder", aEvent.getOre());
                                        } else if (aMaterial == Materials.Coffee) {
                                            GT_OreDictUnificator.registerOre(Dyes.dyeBrown, aEvent.getOre());
                                        } else if (aMaterial == Materials.BrownLimonite) {
                                            GT_OreDictUnificator.registerOre(Dyes.dyeBrown, aEvent.getOre());
                                        } else if (aMaterial == Materials.YellowLimonite) {
                                            GT_OreDictUnificator.registerOre(Dyes.dyeYellow, aEvent.getOre());
                                        }
                                        break;
                                    case ingot:
                                        if (aMaterial == Materials.Rubber) {
                                            GT_OreDictUnificator.registerOre("itemRubber", aEvent.getOre());
                                        } else if (aMaterial == Materials.FierySteel) {
                                            GT_OreDictUnificator.registerOre("fieryIngot", aEvent.getOre());
                                        } else if (aMaterial == Materials.IronWood) {
                                            GT_OreDictUnificator.registerOre("ironwood", aEvent.getOre());
                                        } else if (aMaterial == Materials.Steeleaf) {
                                            GT_OreDictUnificator.registerOre("steeleaf", aEvent.getOre());
                                        } else if (aMaterial == Materials.Knightmetal) {
                                            GT_OreDictUnificator.registerOre("knightmetal", aEvent.getOre());
                                        } else if ((aMaterial == Materials.Brass) && (aEvent.getOre().getItemDamage() == 2)
                                                && (aEvent.getOre().getUnlocalizedName().equals("item.ingotBrass"))
                                                && (new ItemStack(aEvent.getOre().getItem(), 1, 0).getUnlocalizedName().contains("red"))) {
                                            GT_OreDictUnificator.set(OrePrefixes.ingot, Materials.RedAlloy, new ItemStack(aEvent.getOre().getItem(), 1, 0));
                                            GT_OreDictUnificator.set(OrePrefixes.ingot, Materials.BlueAlloy, new ItemStack(aEvent.getOre().getItem(), 1, 1));
                                            GT_OreDictUnificator.set(OrePrefixes.ingot, Materials.Brass, new ItemStack(aEvent.getOre().getItem(), 1, 2));
                                            if (!mDisableIC2Cables) {
                                                GT_Values.RA.addWiremillRecipe(
                                                        GT_ModHandler.getIC2Item(ItemName.cable, CableType.copper, 3),
                                                        new ItemStack(aEvent.getOre().getItem(), 1,
                                                                8), 400, 1);
                                                GT_Values.RA.addWiremillRecipe(
                                                        GT_ModHandler.getIC2Item(ItemName.cable, CableType.iron, 6),
                                                        new ItemStack(aEvent.getOre().getItem(), 1, 9), 400, 2);
                                            }
                                            GT_Values.RA.addCutterRecipe(new ItemStack(aEvent.getOre().getItem(), 1, 3), new ItemStack(aEvent.getOre().getItem(), 16, 4),
                                                    null, 400, 8);
                                        }
                                        break;
                                    default:
                                        break;
                                }
                                if (aPrefix.mIsUnificatable && !aMaterial.mUnificatable) {
                                    return;
                                }
                            } else {
                                for (Dyes tDye : Dyes.VALUES) {
                                    if (aEvent.getName().endsWith(tDye.name().replaceFirst("dye", ""))) {
                                        GT_OreDictUnificator.addToBlacklist(aEvent.getOre());
                                        GT_Log.ore.println(tModToName + " Oh man, why the fuck would anyone need a OreDictified Color for this, that is even too much for GregTech... do not report this, this is just a random Comment about how ridiculous this is.");
                                        return;
                                    }
                                }
								//System.out.println("Material Name: "+aEvent.getName()+ " !!!Unknown Material detected!!! Please report to GregTech Intergalactical for additional compatiblity. This is not an Error, an Issue nor a Lag Source, it is just an Information, which you should pass to me.");
								//GT_Log.ore.println(tModToName + " uses an unknown Material. Report this to GregTech.");
                                return;
                            }
                        } else {
                            aPrefix.add(GT_Utility.copyAmount(1, new Object[]{aEvent.getOre()}));
                        }
                    }
                } else if (aPrefix.mIsSelfReferencing) {
                    aPrefix.add(GT_Utility.copyAmount(1, new Object[]{aEvent.getOre()}));
                } else {
                    GT_Log.ore.println(tModToName + " uses a Prefix as full OreDict Name, and is therefor invalid.");
                    aEvent.getOre().setStackDisplayName("Invalid OreDictionary Tag");
                    return;
                }
                switch (aPrefix) {
                    case dye:
                        if (GT_Utility.isStringValid(tName)) {
                            GT_OreDictUnificator.registerOre(OrePrefixes.dye, aEvent.getOre());
                        }
                        break;
                    case stoneSmooth:
                        GT_OreDictUnificator.registerOre("stone", aEvent.getOre());
                        break;
                    case stoneCobble:
                        GT_OreDictUnificator.registerOre("cobblestone", aEvent.getOre());
                        break;
                    case plank:
                        if (tName.equals("Wood")) {
                            GT_OreDictUnificator.addItemData(aEvent.getOre(), new ItemData(Materials.Wood, 3628800L));
                        }
                        break;
                    case slab:
                        if (tName.equals("Wood")) {
                            GT_OreDictUnificator.addItemData(aEvent.getOre(), new ItemData(Materials.Wood, 1814400L));
                        }
                        break;
                    case sheet:
                        if (tName.equals("Plastic")) {
                            GT_OreDictUnificator.registerOre(OrePrefixes.plate, Materials.Plastic, aEvent.getOre());
                        } else if (tName.equals("Rubber")) {
                            GT_OreDictUnificator.registerOre(OrePrefixes.plate, Materials.Rubber, aEvent.getOre());
                        }
                        break;
                    case crafting:
                        if (tName.equals("ToolSolderingMetal")) {
                            GregTech_API.registerSolderingMetal(aEvent.getOre());
                        } else if (tName.equals("IndustrialDiamond")) {
                            GT_OreDictUnificator.addToBlacklist(aEvent.getOre());
                        } else if (tName.equals("WireCopper")) {
                            GT_OreDictUnificator.registerOre(OrePrefixes.wire, Materials.Copper, aEvent.getOre());
                        }
                        break;
                    case wood:
                        if (tName.equals("Rubber")) {
                            GT_OreDictUnificator.registerOre("logRubber", aEvent.getOre());
                        }
                        break;
                    case food:
                        if (tName.equals("Cocoapowder")) {
                            GT_OreDictUnificator.registerOre(OrePrefixes.dust, Materials.Cocoa, aEvent.getOre());
                        }
                        break;
                    default:
                        break;
                }
            }
            GT_Log.ore.println(tModToName);

            OreDictEventContainer tOre = new OreDictEventContainer(aEvent, aPrefix, aMaterial, aMod);
            if ((!this.mOreDictActivated) || (!GregTech_API.sUnificationEntriesRegistered)) {
                this.mEvents.add(tOre);
            } else {
                this.mEvents.clear();
            }
            if (this.mOreDictActivated) {
                registerRecipes(tOre);
            }
        } catch (Throwable e) {
            e.printStackTrace(GT_Log.err);
        }
    }

    @SubscribeEvent
    public void onFluidContainerRegistration(FluidContainerRegistry.FluidContainerRegisterEvent aFluidEvent) {
        if ((aFluidEvent.getData().filledContainer.getItem() == Items.POTIONITEM) && (aFluidEvent.getData().filledContainer.getItemDamage() == 0)) {
            aFluidEvent.getData().fluid.amount = 0;
        }
        GT_OreDictUnificator.addToBlacklist(aFluidEvent.getData().emptyContainer);
        GT_OreDictUnificator.addToBlacklist(aFluidEvent.getData().filledContainer);
        GT_Utility.addFluidContainerData(aFluidEvent.getData());
    }

    @SubscribeEvent
    public void onServerTickEvent(TickEvent.ServerTickEvent aEvent) {
    }

    @SubscribeEvent
    public void onWorldTickEvent(TickEvent.WorldTickEvent aEvent) {
        if(aEvent.world.provider.getDimension() == 0)
            mTicksUntilNextCraftSound--;
        if (aEvent.side.isServer()) {
            if (this.mUniverse == null) {
                this.mUniverse = aEvent.world;
            }
            if (this.isFirstServerWorldTick) {
                File tSaveDiretory = getSaveDirectory();
                if (tSaveDiretory != null) {
                    this.isFirstServerWorldTick = false;
                    try {
                        for (IMetaTileEntity tMetaTileEntity : GregTech_API.METATILEENTITIES) {
                            if (tMetaTileEntity != null) {
                                tMetaTileEntity.onWorldLoad(tSaveDiretory);
                            }
                        }
                    } catch (Throwable e) {
                        e.printStackTrace(GT_Log.err);
                    }
                }
            }
            if ((aEvent.world.getTotalWorldTime() % 100L == 0L) && ((this.mItemDespawnTime != 6000) || (this.mMaxEqualEntitiesAtOneSpot > 0))) {
                for (int i = 0; i < aEvent.world.loadedEntityList.size(); i++) {
                    if ((aEvent.world.loadedEntityList.get(i) instanceof Entity)) {
                        Entity tEntity = aEvent.world.loadedEntityList.get(i);
                        if (((tEntity instanceof EntityItem)) && (this.mItemDespawnTime != 6000) && (((EntityItem) tEntity).lifespan == 6000)) {
                            ((EntityItem) tEntity).lifespan = this.mItemDespawnTime;
                        } else if (((tEntity instanceof EntityLivingBase)) && (this.mMaxEqualEntitiesAtOneSpot > 0) && (!(tEntity instanceof EntityPlayer))
                                && (tEntity.canBePushed()) && (((EntityLivingBase) tEntity).getHealth() > 0.0F)) {
                            AxisAlignedBB boundingBox = tEntity.getCollisionBoundingBox();
                            if (boundingBox != null) {
                                List tList = tEntity.worldObj.getEntitiesWithinAABBExcludingEntity(tEntity, boundingBox.expand(0.2D, 0.0D, 0.2D));
                                Class tClass = tEntity.getClass();
                                int tEntityCount = 1;
                                if (tList != null) {
                                    for (Object aTList : tList) {
                                        if ((aTList != null) && (aTList.getClass() == tClass)) {
                                            tEntityCount++;
                                        }
                                    }
                                }
                                if (tEntityCount > this.mMaxEqualEntitiesAtOneSpot) {
                                    tEntity.attackEntityFrom(DamageSource.inWall, tEntityCount - this.mMaxEqualEntitiesAtOneSpot);
                                }
                            }
                        }
                    }
                }
            }
            if (aEvent.world.provider.getDimension() == 0)
                GT_Pollution.onWorldTick(aEvent.world, (int) (aEvent.world.getTotalWorldTime() % 1200));
        }
    }

    @SubscribeEvent
    public void onPlayerTickEventServer(TickEvent.PlayerTickEvent aEvent) {
        if ((aEvent.side.isServer()) && (aEvent.phase == TickEvent.Phase.END) && (!aEvent.player.isDead)) {
            if ((aEvent.player.ticksExisted % 200 == 0) && (aEvent.player.capabilities.allowEdit) && (!aEvent.player.capabilities.isCreativeMode)
                    && (this.mSurvivalIntoAdventure)) {
                aEvent.player.setGameType(GameType.ADVENTURE);
                aEvent.player.capabilities.allowEdit = false;
                if (this.mAxeWhenAdventure) {
                    GT_Utility.sendChatToPlayer(aEvent.player, "It's dangerous to go alone! Take this.");
                    aEvent.player.worldObj.spawnEntityInWorld(new EntityItem(aEvent.player.worldObj, aEvent.player.posX, aEvent.player.posY,
                            aEvent.player.posZ, GT_MetaGenerated_Tool_01.INSTANCE.getToolWithStats(GT_MetaGenerated_Tool_01.AXE, 1, Materials.Flint, Materials.Wood, null)));
                }
            }
            boolean tHungerEffect = (this.mHungerEffect) && (aEvent.player.ticksExisted % 2400 == 1200);
            if (aEvent.player.ticksExisted % 120 == 0) {
                int tCount = 64;
                for (int i = 0; i < 36; i++) {
                    ItemStack tStack;
                    if ((tStack = aEvent.player.inventory.getStackInSlot(i)) != null) {
                        if (!aEvent.player.capabilities.isCreativeMode) {
                            GT_Utility.applyRadioactivity(aEvent.player, GT_Utility.getRadioactivityLevel(tStack), tStack.stackSize);
                            float tHeat = GT_Utility.getHeatDamageFromItem(tStack);
                            if (tHeat != 0.0F) {
                                if (tHeat > 0.0F) {
                                    GT_Utility.applyHeatDamage(aEvent.player, tHeat);
                                } else {
                                    GT_Utility.applyFrostDamage(aEvent.player, -tHeat);
                                }
                            }
                        }
                        if (tHungerEffect) {
                            tCount += tStack.stackSize * 64 / Math.max(1, tStack.getMaxStackSize());
                        }
                        if (this.mInventoryUnification) {
                            GT_OreDictUnificator.setStack(true, tStack);
                        }
                    }
                }
                for (int i = 0; i < 4; i++) {
                    ItemStack tStack;
                    if ((tStack = aEvent.player.inventory.armorInventory[i]) != null) {
                        if (!aEvent.player.capabilities.isCreativeMode) {
                            GT_Utility.applyRadioactivity(aEvent.player, GT_Utility.getRadioactivityLevel(tStack), tStack.stackSize);
                            float tHeat = GT_Utility.getHeatDamageFromItem(tStack);
                            if (tHeat != 0.0F) {
                                if (tHeat > 0.0F) {
                                    GT_Utility.applyHeatDamage(aEvent.player, tHeat);
                                } else {
                                    GT_Utility.applyFrostDamage(aEvent.player, -tHeat);
                                }
                            }
                        }
                        if (tHungerEffect) {
                            tCount += 256;
                        }
                    }
                }
                if (tHungerEffect) {
                    aEvent.player.addExhaustion(Math.max(1.0F, tCount / 666.6F));
                }
            }
        }
    }

    public Object getServerGuiElement(int aID, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ) {
        if(aID>=1000){
            int ID = aID - 1000;
            switch(ID){
                case 0:
                    return new ContainerBasicArmor(aPlayer, new InventoryArmor(ModularArmor_Item.class, aPlayer.getHeldItemMainhand()));
                case 1:
                    return new ContainerElectricArmor1(aPlayer, new InventoryArmor(ModularArmor_Item.class, aPlayer.getHeldItemMainhand()));
                case 2:
                    return new ContainerElectricArmor1(aPlayer, new InventoryArmor(ModularArmor_Item.class, aPlayer.getHeldItemMainhand()));
                default:
                    return getRightItem(aPlayer, ID);
            }
        }
        TileEntity tTileEntity = aWorld.getTileEntity(new BlockPos(aX, aY, aZ));
        if ((tTileEntity instanceof IGregTechTileEntity)) {
            IMetaTileEntity tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity();
            if (tMetaTileEntity != null) {
                return tMetaTileEntity.getServerGUI(aID, aPlayer.inventory, (IGregTechTileEntity) tTileEntity);
            }
        }
        return null;
    }

    public Object getRightItem(EntityPlayer player, int ID){
        ItemStack mStack = player.getItemStackFromSlot(EntityEquipmentSlot.values()[ID/100]);
        if(mStack==null||!(mStack.getItem() instanceof ModularArmor_Item))
            return null;

        switch(ID % 100){
            case 0:
                return new ContainerBasicArmor(player, new InventoryArmor(ModularArmor_Item.class, mStack));
            case 1:
                return new ContainerElectricArmor1(player, new InventoryArmor(ModularArmor_Item.class, mStack));
            case 2:
                return new ContainerElectricArmor1(player, new InventoryArmor(ModularArmor_Item.class, mStack));
        }
        return null;

    }

    public Object getClientGuiElement(int aID, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ) {
        if(aID>=1000){
            int ID = aID-1000;
            switch(ID){
                case 0:
                    return new GuiModularArmor(new ContainerBasicArmor(aPlayer, new InventoryArmor(ModularArmor_Item.class, aPlayer.getHeldItemMainhand())), aPlayer);
                case 1:
                    return new GuiElectricArmor1(new ContainerElectricArmor1(aPlayer, new InventoryArmor(ModularArmor_Item.class, aPlayer.getHeldItemMainhand())), aPlayer);
                case 2:
                    return new GuiElectricArmor1(new ContainerElectricArmor1(aPlayer, new InventoryArmor(ModularArmor_Item.class, aPlayer.getHeldItemMainhand())), aPlayer);
                default:
                    return getRightItemGui(aPlayer, ID);
            }
        }
        TileEntity tTileEntity = aWorld.getTileEntity(new BlockPos(aX, aY, aZ));
        if ((tTileEntity instanceof IGregTechTileEntity)) {
            IMetaTileEntity tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity();
            if (tMetaTileEntity != null) {
                return tMetaTileEntity.getClientGUI(aID, aPlayer.inventory, (IGregTechTileEntity) tTileEntity);
            }
        }
        return null;
    }

    public Object getRightItemGui(EntityPlayer player, int ID){
        ItemStack mStack = player.getItemStackFromSlot(EntityEquipmentSlot.values()[ID/100]);
        if(mStack==null||!(mStack.getItem() instanceof ModularArmor_Item))return null;

        switch(ID % 100){
            case 0:
                return new GuiModularArmor(new ContainerBasicArmor(player, new InventoryArmor(ModularArmor_Item.class, mStack)),player);
            case 1:
                return new GuiElectricArmor1(new ContainerElectricArmor1(player, new InventoryArmor(ModularArmor_Item.class, mStack)), player);
            case 2:
                return new GuiElectricArmor1(new ContainerElectricArmor1(player, new InventoryArmor(ModularArmor_Item.class, mStack)), player);
        }
        return null;

    }

    public int getBurnTime(ItemStack aFuel) {
        if ((aFuel == null) || (aFuel.getItem() == null)) {
            return 0;
        }
        int rFuelValue = 0;
        if ((aFuel.getItem() instanceof GT_MetaGenerated_Item)) {
            Short tFuelValue = ((GT_MetaGenerated_Item) aFuel.getItem()).mBurnValues.get((short) aFuel.getItemDamage());
            if (tFuelValue != null) {
                rFuelValue = Math.max(rFuelValue, tFuelValue);
            }
        }
        NBTTagCompound tNBT = aFuel.getTagCompound();
        if (tNBT != null) {
            short tValue = tNBT.getShort("GT.ItemFuelValue");
            rFuelValue = Math.max(rFuelValue, tValue);
        }
        if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "gemSodium")) {
            rFuelValue = Math.max(rFuelValue, 4000);
        } else if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "crushedSodium")) {
            rFuelValue = Math.max(rFuelValue, 4000);
        } else if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "dustImpureSodium")) {
            rFuelValue = Math.max(rFuelValue, 4000);
        } else if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "dustSodium")) {
            rFuelValue = Math.max(rFuelValue, 4000);
        } else if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "dustSmallSodium")) {
            rFuelValue = Math.max(rFuelValue, 1000);
        } else if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "dustTinySodium")) {
            rFuelValue = Math.max(rFuelValue, 444);
        } else if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "gemLithium")) {
            rFuelValue = Math.max(rFuelValue, 6000);
        } else if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "crushedLithium")) {
            rFuelValue = Math.max(rFuelValue, 6000);
        } else if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "dustImpureLithium")) {
            rFuelValue = Math.max(rFuelValue, 6000);
        } else if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "dustLithium")) {
            rFuelValue = Math.max(rFuelValue, 6000);
        } else if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "dustSmallLithium")) {
            rFuelValue = Math.max(rFuelValue, 2000);
        } else if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "dustTinyLithium")) {
            rFuelValue = Math.max(rFuelValue, 888);
        } else if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "gemCaesium")) {
            rFuelValue = Math.max(rFuelValue, 6000);
        } else if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "crushedCaesium")) {
            rFuelValue = Math.max(rFuelValue, 6000);
        } else if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "dustImpureCaesium")) {
            rFuelValue = Math.max(rFuelValue, 6000);
        } else if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "dustCaesium")) {
            rFuelValue = Math.max(rFuelValue, 6000);
        } else if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "dustSmallCaesium")) {
            rFuelValue = Math.max(rFuelValue, 2000);
        } else if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "dustTinyCaesium")) {
            rFuelValue = Math.max(rFuelValue, 888);
        } else if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "gemLignite")) {
            rFuelValue = Math.max(rFuelValue, 1200);
        } else if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "crushedLignite")) {
            rFuelValue = Math.max(rFuelValue, 1200);
        } else if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "dustImpureLignite")) {
            rFuelValue = Math.max(rFuelValue, 1200);
        } else if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "dustLignite")) {
            rFuelValue = Math.max(rFuelValue, 1200);
        } else if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "dustSmallLignite")) {
            rFuelValue = Math.max(rFuelValue, 375);
        } else if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "dustTinyLignite")) {
            rFuelValue = Math.max(rFuelValue, 166);
        } else if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "gemCoal")) {
            rFuelValue = Math.max(rFuelValue, 1600);
        } else if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "crushedCoal")) {
            rFuelValue = Math.max(rFuelValue, 1600);
        } else if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "dustImpureCoal")) {
            rFuelValue = Math.max(rFuelValue, 1600);
        } else if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "dustCoal")) {
            rFuelValue = Math.max(rFuelValue, 1600);
        } else if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "dustSmallCoal")) {
            rFuelValue = Math.max(rFuelValue, 400);
        } else if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "dustTinyCoal")) {
            rFuelValue = Math.max(rFuelValue, 177);
        } else if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "gemCharcoal")) {
            rFuelValue = Math.max(rFuelValue, 1600);
        } else if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "crushedCharcoal")) {
            rFuelValue = Math.max(rFuelValue, 1600);
        } else if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "dustImpureCharcoal")) {
            rFuelValue = Math.max(rFuelValue, 1600);
        } else if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "dustCharcoal")) {
            rFuelValue = Math.max(rFuelValue, 1600);
        } else if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "dustSmallCharcoal")) {
            rFuelValue = Math.max(rFuelValue, 400);
        } else if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "dustTinyCharcoal")) {
            rFuelValue = Math.max(rFuelValue, 177);
        } else if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "dustWood")) {
            rFuelValue = Math.max(rFuelValue, 100);
        } else if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "dustSmallWood")) {
            rFuelValue = Math.max(rFuelValue, 25);
        } else if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "dustTinyWood")) {
            rFuelValue = Math.max(rFuelValue, 11);
        } else if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "plateWood")) {
            rFuelValue = Math.min(rFuelValue, 300);
        } else if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "blockLignite")) {
            rFuelValue = Math.max(rFuelValue, 12000);
        } else if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "blockCharcoal")) {
            rFuelValue = Math.max(rFuelValue, 16000);
        } else if (GT_Utility.areStacksEqual(aFuel, new ItemStack(Blocks.WOODEN_BUTTON, 1))) {
            rFuelValue = Math.max(rFuelValue, 150);
        } else if (GT_Utility.areStacksEqual(aFuel, new ItemStack(Blocks.LADDER, 1))) {
            rFuelValue = Math.max(rFuelValue, 100);
        } else if (GT_Utility.areStacksEqual(aFuel, new ItemStack(Items.SIGN, 1))) {
            rFuelValue = Math.max(rFuelValue, 600);
        } else if (GT_Utility.areStacksEqual(aFuel, new ItemStack(Items.OAK_DOOR, 1))) {
            rFuelValue = Math.max(rFuelValue, 600);
        } else if (GT_Utility.areStacksEqual(aFuel, ItemList.Block_MSSFUEL.get(1))) {
            rFuelValue = Math.max(rFuelValue, 150000);
        } else if (GT_Utility.areStacksEqual(aFuel, ItemList.Block_SSFUEL.get(1))) {
            rFuelValue = Math.max(rFuelValue, 100000);
        }
        return rFuelValue;
    }

    public Fluid addAutogeneratedMoltenFluid(Materials aMaterial) {
        return addFluid("molten." + aMaterial.mName.toLowerCase(Locale.ENGLISH), "molten.autogenerated", "Molten " + aMaterial.mDefaultLocalName, aMaterial,
                aMaterial.mMoltenRGBa, 4, aMaterial.mMeltingPoint <= 0 ? 1000 : aMaterial.mMeltingPoint, null, null, 0);
    }

    public Fluid addAutogeneratedPlasmaFluid(Materials aMaterial) {
        return addFluid("plasma." + aMaterial.mName.toLowerCase(Locale.ENGLISH), "plasma.autogenerated", aMaterial.mDefaultLocalName + " Plasma", aMaterial,
                aMaterial.mMoltenRGBa, 3, 10000, GT_OreDictUnificator.get(OrePrefixes.cellPlasma, aMaterial, 1), ItemList.Cell_Empty.get(1),
                1000);
    }

    public Fluid addFluid(String aName, String aLocalized, Materials aMaterial, int aState, int aTemperatureK) {
        return addFluid(aName, aLocalized, aMaterial, aState, aTemperatureK, null, null, 0);
    }

    public Fluid addFluid(String aName, String aLocalized, Materials aMaterial, int aState, int aTemperatureK, ItemStack aFullContainer,
                          ItemStack aEmptyContainer, int aFluidAmount) {
        return addFluid(aName, aName.toLowerCase(Locale.ENGLISH), aLocalized, aMaterial, null, aState, aTemperatureK, aFullContainer, aEmptyContainer, aFluidAmount);
    }

    public Fluid addFluid(String aName, String aTexture, String aLocalized, Materials aMaterial, short[] aRGBa, int aState, int aTemperatureK,
                          ItemStack aFullContainer, ItemStack aEmptyContainer, int aFluidAmount) {
        aName = aName.toLowerCase(Locale.ENGLISH);
        Fluid rFluid = new GT_Fluid(aName, aTexture, aRGBa != null ? aRGBa : Dyes._NULL.getRGBA());
        GT_LanguageManager.addStringLocalization(rFluid.getUnlocalizedName(), aLocalized == null ? aName : aLocalized);
        if (FluidRegistry.registerFluid(rFluid)) {
            switch (aState) {
                case 0:
                    rFluid.setGaseous(false);
                    rFluid.setViscosity(10000);
                    break;
                case 1:
                case 4:
                    rFluid.setGaseous(false);
                    rFluid.setViscosity(1000);
                    break;
                case 2:
                    rFluid.setGaseous(true);
                    rFluid.setDensity(-100);
                    rFluid.setViscosity(200);
                    break;
                case 3:
                    rFluid.setGaseous(true);
                    rFluid.setDensity(55536);
                    rFluid.setViscosity(10);
                    rFluid.setLuminosity(15);
            }
        } else {
            rFluid = FluidRegistry.getFluid(aName);
        }
        if (aMaterial != null) {
            switch (aState) {
                case 0:
                    aMaterial.mSolid = rFluid;
                    break;
                case 1:
                    aMaterial.mFluid = rFluid;
                    break;
                case 2:
                    aMaterial.mGas = rFluid;
                    break;
                case 3:
                    aMaterial.mPlasma = rFluid;
                    break;
                case 4:
                    aMaterial.mStandardMoltenFluid = rFluid;
            }
        }
        registerFluidContainer(aFullContainer, aEmptyContainer, rFluid, aFluidAmount);
        return rFluid;
    }

    public void registerFluidContainer(ItemStack aFullContainer, ItemStack aEmptyContainer, Fluid rFluid, int aFluidAmount) {
        if ((aFullContainer != null) && (aEmptyContainer != null)
                && (!FluidContainerRegistry.registerFluidContainer(new FluidStack(rFluid, aFluidAmount), aFullContainer, aEmptyContainer))) {
            GT_Values.RA.addFluidCannerRecipe(aFullContainer, GT_Utility.getContainerItem(aFullContainer, false), null, new FluidStack(rFluid, aFluidAmount));
        }
    }

    public File getSaveDirectory() {
        return this.mUniverse == null ? null : this.mUniverse.getSaveHandler().getWorldDirectory();
    }

    public void registerUnificationEntries() {
        GregTech_API.sUnification.mConfig.save();
        GregTech_API.sUnification.mConfig.load();
        GT_OreDictUnificator.resetUnificationEntries();
        for (OreDictEventContainer tOre : this.mEvents) {
            if ((!(tOre.mEvent.getOre().getItem() instanceof GT_MetaGenerated_Item)) && (tOre.mPrefix != null) && (tOre.mPrefix.mIsUnificatable) && (tOre.mMaterial != null)) {
                boolean chkmi = tOre.mModID != null;
                if (chkmi) {
                    if (tOre.mModID.equalsIgnoreCase("enderio") && tOre.mPrefix == OrePrefixes.ingot && tOre.mMaterial == Materials.DarkSteel) {
                        GT_OreDictUnificator.addAssociation(tOre.mPrefix, tOre.mMaterial, tOre.mEvent.getOre(), false);
                        GT_OreDictUnificator.set(tOre.mPrefix, tOre.mMaterial, tOre.mEvent.getOre(), (GregTech_API.sUnification.get(new StringBuilder().append(ConfigCategories.specialunificationtargets).append(".").append(tOre.mModID).toString(), tOre.mEvent.getName(), true)), true);continue;
                    } else if (tOre.mModID.equalsIgnoreCase("thermalfoundation") && tOre.mPrefix == OrePrefixes.dust && tOre.mMaterial == Materials.Blizz) {
                        GT_OreDictUnificator.addAssociation(tOre.mPrefix, tOre.mMaterial, tOre.mEvent.getOre(), false);
                        GT_OreDictUnificator.set(tOre.mPrefix, tOre.mMaterial, tOre.mEvent.getOre(), (GregTech_API.sUnification.get(new StringBuilder().append(ConfigCategories.specialunificationtargets).append(".").append(tOre.mModID).toString(), tOre.mEvent.getName(), true)), true);continue;
                    } else if (tOre.mModID.equalsIgnoreCase("thermalfoundation") && tOre.mPrefix == OrePrefixes.dust && tOre.mMaterial == Materials.Pyrotheum) {
                        GT_OreDictUnificator.addAssociation(tOre.mPrefix, tOre.mMaterial, tOre.mEvent.getOre(), false);
                        GT_OreDictUnificator.set(tOre.mPrefix, tOre.mMaterial, tOre.mEvent.getOre(), (GregTech_API.sUnification.get(new StringBuilder().append(ConfigCategories.specialunificationtargets).append(".").append(tOre.mModID).toString(), tOre.mEvent.getName(), true)), true);continue;
                    } else if (tOre.mModID.equalsIgnoreCase(aTextArsmagica2) && tOre.mPrefix == OrePrefixes.dust && tOre.mMaterial == Materials.Vinteum) {
                        GT_OreDictUnificator.addAssociation(tOre.mPrefix, tOre.mMaterial, tOre.mEvent.getOre(), false);
                        GT_OreDictUnificator.set(tOre.mPrefix, tOre.mMaterial, tOre.mEvent.getOre(), (GregTech_API.sUnification.get(new StringBuilder().append(ConfigCategories.specialunificationtargets).append(".").append(tOre.mModID).toString(), tOre.mEvent.getName(), true)), true);continue;
                    } else if (tOre.mModID.equalsIgnoreCase(aTextArsmagica2) && tOre.mPrefix == OrePrefixes.gem && tOre.mMaterial == Materials.BlueTopaz) {
                        GT_OreDictUnificator.addAssociation(tOre.mPrefix, tOre.mMaterial, tOre.mEvent.getOre(), false);
                        GT_OreDictUnificator.set(tOre.mPrefix, tOre.mMaterial, tOre.mEvent.getOre(), (GregTech_API.sUnification.get(new StringBuilder().append(ConfigCategories.specialunificationtargets).append(".").append(tOre.mModID).toString(), tOre.mEvent.getName(), true)), true);continue;
                    } else if (tOre.mModID.equalsIgnoreCase(aTextArsmagica2) && tOre.mPrefix == OrePrefixes.gem && tOre.mMaterial == Materials.Chimerite) {
                        GT_OreDictUnificator.addAssociation(tOre.mPrefix, tOre.mMaterial, tOre.mEvent.getOre(), false);
                        GT_OreDictUnificator.set(tOre.mPrefix, tOre.mMaterial, tOre.mEvent.getOre(), (GregTech_API.sUnification.get(new StringBuilder().append(ConfigCategories.specialunificationtargets).append(".").append(tOre.mModID).toString(), tOre.mEvent.getName(), true)), true);continue;
                    } else if (tOre.mModID.equalsIgnoreCase(aTextArsmagica2) && tOre.mPrefix == OrePrefixes.gem && tOre.mMaterial == Materials.Moonstone) {
                        GT_OreDictUnificator.addAssociation(tOre.mPrefix, tOre.mMaterial, tOre.mEvent.getOre(), false);
                        GT_OreDictUnificator.set(tOre.mPrefix, tOre.mMaterial, tOre.mEvent.getOre(), (GregTech_API.sUnification.get(new StringBuilder().append(ConfigCategories.specialunificationtargets).append(".").append(tOre.mModID).toString(), tOre.mEvent.getName(), true)), true);continue;
                    } else if (tOre.mModID.equalsIgnoreCase(aTextArsmagica2) && tOre.mPrefix == OrePrefixes.gem && tOre.mMaterial == Materials.Sunstone) {
                        GT_OreDictUnificator.addAssociation(tOre.mPrefix, tOre.mMaterial, tOre.mEvent.getOre(), false);
                        GT_OreDictUnificator.set(tOre.mPrefix, tOre.mMaterial, tOre.mEvent.getOre(), (GregTech_API.sUnification.get(new StringBuilder().append(ConfigCategories.specialunificationtargets).append(".").append(tOre.mModID).toString(), tOre.mEvent.getName(), true)), true);continue;
                    } else if (tOre.mModID.equalsIgnoreCase("rotarycraft") && tOre.mPrefix == OrePrefixes.ingot && tOre.mMaterial == Materials.HSLA) {
                        GT_OreDictUnificator.addAssociation(tOre.mPrefix, tOre.mMaterial, tOre.mEvent.getOre(), false);
                        GT_OreDictUnificator.set(tOre.mPrefix, tOre.mMaterial, tOre.mEvent.getOre(), (GregTech_API.sUnification.get(new StringBuilder().append(ConfigCategories.specialunificationtargets).append(".").append(tOre.mModID).toString(), tOre.mEvent.getName(), true)), true);continue;
                    } else if (tOre.mModID.equalsIgnoreCase("appliedenergistics2") && tOre.mPrefix == OrePrefixes.gem && tOre.mMaterial == Materials.CertusQuartz) {
                        GT_OreDictUnificator.addAssociation(tOre.mPrefix, tOre.mMaterial, tOre.mEvent.getOre(), false);
                        GT_OreDictUnificator.set(tOre.mPrefix, tOre.mMaterial, tOre.mEvent.getOre(), (GregTech_API.sUnification.get(new StringBuilder().append(ConfigCategories.specialunificationtargets).append(".").append(tOre.mModID).toString(), tOre.mEvent.getName(), true)), true);continue;
                    } else if (tOre.mModID.equalsIgnoreCase("appliedenergistics2") && tOre.mPrefix == OrePrefixes.dust && tOre.mMaterial == Materials.CertusQuartz) {
                        GT_OreDictUnificator.addAssociation(tOre.mPrefix, tOre.mMaterial, tOre.mEvent.getOre(), false);
                        GT_OreDictUnificator.set(tOre.mPrefix, tOre.mMaterial, tOre.mEvent.getOre(), (GregTech_API.sUnification.get(new StringBuilder().append(ConfigCategories.specialunificationtargets).append(".").append(tOre.mModID).toString(), tOre.mEvent.getName(), true)), true);continue;
                    }
                }
                if (GT_OreDictUnificator.isBlacklisted(tOre.mEvent.getOre())) {
                    GT_OreDictUnificator.addAssociation(tOre.mPrefix, tOre.mMaterial, tOre.mEvent.getOre(), true);
                } else {
                    GT_OreDictUnificator.addAssociation(tOre.mPrefix, tOre.mMaterial, tOre.mEvent.getOre(), false);
                    GT_OreDictUnificator.set(tOre.mPrefix, tOre.mMaterial, tOre.mEvent.getOre(), (chkmi) && (GregTech_API.sUnification.get(new StringBuilder().append(ConfigCategories.specialunificationtargets).append(".").append(tOre.mModID).toString(), tOre.mEvent.getName(), false)), true);
                }
            }
        }
        for (OreDictEventContainer tOre : this.mEvents) {
            if (((tOre.mEvent.getOre().getItem() instanceof GT_MetaGenerated_Item)) && (tOre.mPrefix != null) && (tOre.mPrefix.mIsUnificatable)
                    && (tOre.mMaterial != null)) {
                if (GT_OreDictUnificator.isBlacklisted(tOre.mEvent.getOre())) {
                    GT_OreDictUnificator.addAssociation(tOre.mPrefix, tOre.mMaterial, tOre.mEvent.getOre(), true);
                } else {
                    GT_OreDictUnificator.addAssociation(tOre.mPrefix, tOre.mMaterial, tOre.mEvent.getOre(), false);
                    GT_OreDictUnificator.set(tOre.mPrefix, tOre.mMaterial, tOre.mEvent.getOre(), (tOre.mModID != null) &&
                            (GregTech_API.sUnification.get(new StringBuilder().append(ConfigCategories.specialunificationtargets).append(".").append(tOre.mModID), tOre.mEvent.getName(), false)), true);
                }
            }
        }
        GregTech_API.sUnificationEntriesRegistered = true;
        GregTech_API.sUnification.mConfig.save();
        GT_Recipe.reInit();
    }

    public void activateOreDictHandler() {
        this.mOreDictActivated = true;
        ProgressManager.ProgressBar progressBar = ProgressManager.push("Register materials", mEvents.size());
        OreDictEventContainer tEvent;
        for (Iterator i$ = this.mEvents.iterator(); i$.hasNext(); registerRecipes(tEvent)) {
            tEvent = (OreDictEventContainer) i$.next();
            progressBar.step(tEvent.mMaterial == null ? "" : tEvent.mMaterial.toString());
        }
        ProgressManager.pop(progressBar);
    }

    public static final HashMap<ChunkPos, int[]>  chunkData = new HashMap<>(5000);

    @SubscribeEvent
    public void handleChunkSaveEvent(ChunkDataEvent.Save event)
    {
        ChunkPos tPos = new ChunkPos(event.getChunk().xPosition, event.getChunk().zPosition);
        if(chunkData.containsKey(tPos)){
            int[] tInts = chunkData.get(tPos);
            if(tInts.length>0){event.getData().setInteger("GTOIL", tInts[0]);}
            if(tInts.length>1){event.getData().setInteger("GTPOLLUTION", tInts[1]);}}
    }

    @SubscribeEvent
    public void handleChunkLoadEvent(ChunkDataEvent.Load event)
    {
        int tOil = 0;
        int tPollution = 0;

        ChunkPos tPos = new ChunkPos(event.getChunk().xPosition, event.getChunk().zPosition);
        int[] tData = new int[2];
        if(chunkData.containsKey(tPos)){
            tData = chunkData.get(tPos);
            chunkData.remove(tPos);
        }

        if(event.getData().hasKey("GTOIL")){
            if(tData.length>2){
                tOil = tData[0];
            }else{
                tOil += event.getData().getInteger("GTOIL");
            }
        }else{
            if(tData[0]!=0){
                tOil = tData[0];
            }
        }

        if(event.getData().hasKey("GTPOLLUTION")){
            if(tData.length>2){
                tPollution = tData[1];
            }else{
                tPollution += event.getData().getInteger("GTPOLLUTION");
            }
        }else{
            if(tData[1]!=0){
                tPollution = tData[1];
            }
        }

        chunkData.put(tPos, new int[]{ tOil, tPollution,-1});
    }

    public static class OreDictEventContainer {
        public final OreDictionary.OreRegisterEvent mEvent;
        public final OrePrefixes mPrefix;
        public final Materials mMaterial;
        public final String mModID;

        public OreDictEventContainer(OreDictionary.OreRegisterEvent aEvent, OrePrefixes aPrefix, Materials aMaterial, String aModID) {
            this.mEvent = aEvent;
            this.mPrefix = aPrefix;
            this.mMaterial = aMaterial;
            this.mModID = ((aModID == null) || (aModID.equals("UNKNOWN")) ? null : aModID);
        }
    }
}