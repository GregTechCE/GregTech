package gregtech.common;

import java.io.File;
import java.text.DateFormat;
import java.util.*;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
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
import gregtech.api.objects.GT_FluidStack;
import gregtech.api.objects.ItemData;
import gregtech.api.objects.MaterialStack;
import gregtech.api.util.*;
import gregtech.common.entities.GT_Entity_Arrow;
import gregtech.common.items.GT_MetaGenerated_Tool_01;
import gregtech.common.items.armor.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings.GameType;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeEventFactory;
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
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public abstract class GT_Proxy implements IGT_Mod, IGuiHandler, IFuelHandler {
    private static final EnumSet<OreGenEvent.GenerateMinable.EventType> PREVENTED_ORES = EnumSet.of(OreGenEvent.GenerateMinable.EventType.COAL,
            new OreGenEvent.GenerateMinable.EventType[]{OreGenEvent.GenerateMinable.EventType.IRON, OreGenEvent.GenerateMinable.EventType.GOLD,
                    OreGenEvent.GenerateMinable.EventType.DIAMOND, OreGenEvent.GenerateMinable.EventType.REDSTONE, OreGenEvent.GenerateMinable.EventType.LAPIS,
                    OreGenEvent.GenerateMinable.EventType.QUARTZ});
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
            "stickObsidian", "caveCrystal", "shardCrystal", "dyeCrystal","shardFire","shardWater","shardAir","shardEarth","ingotRefinedIron","blockMarble","ingotUnstable"}));
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
    public int mSkeletonsShootGTArrows = 16;
    public int mMaxEqualEntitiesAtOneSpot = 3;
    public int mFlintChance = 30;
    public int mItemDespawnTime = 6000;
    public int mUpgradeCount = 4;
    public boolean mGTBees = true;
    private World mUniverse = null;
    private boolean isFirstServerWorldTick = true;
    private boolean mOreDictActivated = false;
    public int[] mHarvestLevel= new int[1000];
    public int mGraniteHavestLevel=3;
    public int mMaxHarvestLevel=7;
    public boolean mChangeHarvestLevels=false;
    public boolean mNerfedCombs = true;
    public int mWireHeatingTicks = 4;
    public boolean mHideUnusedOres = true;
    public boolean mHideRecyclingRecipes = true;

    public GT_Proxy() {
        GameRegistry.registerFuelHandler(this);
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.ORE_GEN_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);
        GregTech_API.sThaumcraftCompat = (IThaumcraftCompat) GT_Utility.callConstructor("gregtech.common.GT_ThaumcraftCompat", 0, null, GT_Values.D1,
                new Object[0]);
        for (FluidContainerRegistry.FluidContainerData tData : FluidContainerRegistry.getRegisteredFluidContainerData()) {
            onFluidContainerRegistration(new FluidContainerRegistry.FluidContainerRegisterEvent(tData));
        }
        for (String tOreName : OreDictionary.getOreNames()) {
            ItemStack tOreStack;
            for (Iterator i$ = OreDictionary.getOres(tOreName).iterator(); i$.hasNext(); registerOre(new OreDictionary.OreRegisterEvent(tOreName, tOreStack))) {
                tOreStack = (ItemStack) i$.next();
            }
        }
    }

    private static final void registerRecipes(OreDictEventContainer aOre) {
        if ((aOre.mEvent.Ore == null) || (aOre.mEvent.Ore.getItem() == null)) {
            return;
        }
        if (aOre.mEvent.Ore.stackSize != 1) {
            aOre.mEvent.Ore.stackSize = 1;
        }
        if (aOre.mPrefix != null) {
            if (!aOre.mPrefix.isIgnored(aOre.mMaterial)) {
                aOre.mPrefix.processOre(aOre.mMaterial == null ? Materials._NULL : aOre.mMaterial, aOre.mEvent.Name, aOre.mModID,
                        GT_Utility.copyAmount(1L, new Object[]{aOre.mEvent.Ore}));
            }
        } else {
//			System.out.println("Thingy Name: "+ aOre.mEvent.Name+ " !!!Unknown 'Thingy' detected!!! This Object seems to probably not follow a valid OreDictionary Convention, or I missed a Convention. Please report to GregTech Intergalactical for additional compatiblity. This is not an Error, an Issue nor a Lag Source, it is just an Information, which you should pass to me.");
        }
    }

    public void onPreLoad() {
        GT_Log.out.println("GT_Mod: Preload-Phase started!");
        GT_Log.ore.println("GT_Mod: Preload-Phase started!");

        GregTech_API.sPreloadStarted = true;
        this.mIgnoreTcon = GregTech_API.sOPStuff.get(ConfigCategories.general, "ignoreTConstruct", true);
        this.mWireHeatingTicks = GregTech_API.sOPStuff.get(ConfigCategories.general, "WireHeatingTicks", 4);
        NetworkRegistry.INSTANCE.registerGuiHandler(GT_Values.GT, this);
        for (FluidContainerRegistry.FluidContainerData tData : FluidContainerRegistry.getRegisteredFluidContainerData()) {
            if ((tData.filledContainer.getItem() == Items.potionitem) && (tData.filledContainer.getItemDamage() == 0)) {
                tData.fluid.amount = 0;
                break;
            }
        }
        GT_Log.out.println("GT_Mod: Getting required Items of other Mods.");
        ItemList.TE_Slag.set(GT_ModHandler.getModItem("ThermalExpansion", "slag", 1L));
        ItemList.TE_Slag_Rich.set(GT_ModHandler.getModItem("ThermalExpansion", "slagRich", 1L));
        ItemList.TE_Rockwool.set(GT_ModHandler.getModItem("ThermalExpansion", "rockwool", 1L));
        ItemList.TE_Hardened_Glass.set(GT_ModHandler.getModItem("ThermalExpansion", "glassHardened", 1L));

        ItemList.RC_ShuntingWire.set(GT_ModHandler.getModItem("Railcraft", "tile.railcraft.machine.delta", 1L, 0));
        ItemList.RC_ShuntingWireFrame.set(GT_ModHandler.getModItem("Railcraft", "tile.railcraft.frame", 1L, 0));
        ItemList.RC_Rail_Standard.set(GT_ModHandler.getModItem("Railcraft", "part.rail", 1L, 0));
        ItemList.RC_Rail_Adv.set(GT_ModHandler.getModItem("Railcraft", "part.rail", 1L, 1));
        ItemList.RC_Rail_Wooden.set(GT_ModHandler.getModItem("Railcraft", "part.rail", 1L, 2));
        ItemList.RC_Rail_HS.set(GT_ModHandler.getModItem("Railcraft", "part.rail", 1L, 3));
        ItemList.RC_Rail_Reinforced.set(GT_ModHandler.getModItem("Railcraft", "part.rail", 1L, 4));
        ItemList.RC_Rail_Electric.set(GT_ModHandler.getModItem("Railcraft", "part.rail", 1L, 5));
        ItemList.RC_Tie_Wood.set(GT_ModHandler.getModItem("Railcraft", "part.tie", 1L, 0));
        ItemList.RC_Tie_Stone.set(GT_ModHandler.getModItem("Railcraft", "part.tie", 1L, 1));
        ItemList.RC_Bed_Wood.set(GT_ModHandler.getModItem("Railcraft", "part.railbed", 1L, 0));
        ItemList.RC_Bed_Stone.set(GT_ModHandler.getModItem("Railcraft", "part.railbed", 1L, 1));
        ItemList.RC_Rebar.set(GT_ModHandler.getModItem("Railcraft", "part.rebar", 1L));
        ItemList.Tool_Sword_Steel.set(GT_ModHandler.getModItem("Railcraft", "tool.steel.sword", 1L));
        ItemList.Tool_Pickaxe_Steel.set(GT_ModHandler.getModItem("Railcraft", "tool.steel.pickaxe", 1L));
        ItemList.Tool_Shovel_Steel.set(GT_ModHandler.getModItem("Railcraft", "tool.steel.shovel", 1L));
        ItemList.Tool_Axe_Steel.set(GT_ModHandler.getModItem("Railcraft", "tool.steel.axe", 1L));
        ItemList.Tool_Hoe_Steel.set(GT_ModHandler.getModItem("Railcraft", "tool.steel.hoe", 1L));

        ItemList.TF_LiveRoot.set(GT_ModHandler.getModItem("TwilightForest", "item.liveRoot", 1L, 0));
        ItemList.TF_Vial_FieryBlood.set(GT_ModHandler.getModItem("TwilightForest", "item.fieryBlood", 1L));
        ItemList.TF_Vial_FieryTears.set(GT_ModHandler.getModItem("TwilightForest", "item.fieryTears", 1L));

        ItemList.FR_Lemon.set(GT_ModHandler.getModItem("Forestry", "fruits", 1L, 3));
        ItemList.FR_Mulch.set(GT_ModHandler.getModItem("Forestry", "mulch", 1L));
        ItemList.FR_Fertilizer.set(GT_ModHandler.getModItem("Forestry", "fertilizerCompound", 1L));
        ItemList.FR_Compost.set(GT_ModHandler.getModItem("Forestry", "fertilizerBio", 1L));
        ItemList.FR_Silk.set(GT_ModHandler.getModItem("Forestry", "craftingMaterial", 1L, 2));
        ItemList.FR_Wax.set(GT_ModHandler.getModItem("Forestry", "beeswax", 1L));
        ItemList.FR_WaxCapsule.set(GT_ModHandler.getModItem("Forestry", "waxCapsule", 1L));
        ItemList.FR_RefractoryWax.set(GT_ModHandler.getModItem("Forestry", "refractoryWax", 1L));
        ItemList.FR_RefractoryCapsule.set(GT_ModHandler.getModItem("Forestry", "refractoryEmpty", 1L));
        ItemList.FR_Bee_Drone.set(GT_ModHandler.getModItem("Forestry", "beeDroneGE", 1L));
        ItemList.FR_Bee_Princess.set(GT_ModHandler.getModItem("Forestry", "beePrincessGE", 1L));
        ItemList.FR_Bee_Queen.set(GT_ModHandler.getModItem("Forestry", "beeQueenGE", 1L));
        ItemList.FR_Tree_Sapling.set(GT_ModHandler.getModItem("Forestry", "sapling", 1L, GT_ModHandler.getModItem("Forestry", "saplingGE", 1L)));
        ItemList.FR_Butterfly.set(GT_ModHandler.getModItem("Forestry", "butterflyGE", 1L));
        ItemList.FR_Larvae.set(GT_ModHandler.getModItem("Forestry", "beeLarvaeGE", 1L));
        ItemList.FR_Serum.set(GT_ModHandler.getModItem("Forestry", "serumGE", 1L));
        ItemList.FR_Caterpillar.set(GT_ModHandler.getModItem("Forestry", "caterpillarGE", 1L));
        ItemList.FR_PollenFertile.set(GT_ModHandler.getModItem("Forestry", "pollenFertile", 1L));
        ItemList.FR_Stick.set(GT_ModHandler.getModItem("Forestry", "oakStick", 1L));
        ItemList.FR_Casing_Impregnated.set(GT_ModHandler.getModItem("Forestry", "impregnatedCasing", 1L));
        ItemList.FR_Casing_Sturdy.set(GT_ModHandler.getModItem("Forestry", "sturdyMachine", 1L));
        ItemList.FR_Casing_Hardened.set(GT_ModHandler.getModItem("Forestry", "hardenedMachine", 1L));

        ItemList.Bottle_Empty.set(new ItemStack(Items.glass_bottle, 1));

        ItemList.Cell_Universal_Fluid.set(GT_ModHandler.getIC2Item("FluidCell", 1L));
        ItemList.Cell_Empty.set(GT_ModHandler.getIC2Item("cell", 1L, GT_ModHandler.getIC2Item("cellEmpty", 1L, GT_ModHandler.getIC2Item("emptyCell", 1L))));
        ItemList.Cell_Water.set(GT_ModHandler.getIC2Item("waterCell", 1L, GT_ModHandler.getIC2Item("cellWater", 1L)));
        ItemList.Cell_Lava.set(GT_ModHandler.getIC2Item("lavaCell", 1L, GT_ModHandler.getIC2Item("cellLava", 1L)));
        ItemList.Cell_Air.set(GT_ModHandler.getIC2Item("airCell", 1L, GT_ModHandler.getIC2Item("cellAir", 1L, GT_ModHandler.getIC2Item("cellOxygen", 1L))));

        ItemList.IC2_Item_Casing_Iron.set(GT_ModHandler.getIC2Item("casingiron", 1L));
        ItemList.IC2_Item_Casing_Gold.set(GT_ModHandler.getIC2Item("casinggold", 1L));
        ItemList.IC2_Item_Casing_Bronze.set(GT_ModHandler.getIC2Item("casingbronze", 1L));
        ItemList.IC2_Item_Casing_Copper.set(GT_ModHandler.getIC2Item("casingcopper", 1L));
        ItemList.IC2_Item_Casing_Tin.set(GT_ModHandler.getIC2Item("casingtin", 1L));
        ItemList.IC2_Item_Casing_Lead.set(GT_ModHandler.getIC2Item("casinglead", 1L));
        ItemList.IC2_Item_Casing_Steel.set(GT_ModHandler.getIC2Item("casingadviron", 1L));
        ItemList.IC2_Spray_WeedEx.set(GT_ModHandler.getIC2Item("weedEx", 1L));
        ItemList.IC2_Fuel_Can_Empty.set(GT_ModHandler.getIC2Item("fuelCan", 1L, GT_ModHandler.getIC2Item("fuelCanEmpty", 1L, GT_ModHandler.getIC2Item("emptyFuelCan", 1L))));
        ItemList.IC2_Fuel_Can_Filled.set(GT_ModHandler.getIC2Item("filledFuelCan", 1L));
        ItemList.IC2_Mixed_Metal_Ingot.set(GT_ModHandler.getIC2Item("mixedMetalIngot", 1L));
        ItemList.IC2_Fertilizer.set(GT_ModHandler.getIC2Item("fertilizer", 1L));
        ItemList.IC2_CoffeeBeans.set(GT_ModHandler.getIC2Item("coffeeBeans", 1L));
        ItemList.IC2_CoffeePowder.set(GT_ModHandler.getIC2Item("coffeePowder", 1L));
        ItemList.IC2_Hops.set(GT_ModHandler.getIC2Item("hops", 1L));
        ItemList.IC2_Resin.set(GT_ModHandler.getIC2Item("resin", 1L));
        ItemList.IC2_Plantball.set(GT_ModHandler.getIC2Item("plantBall", 1L));
        ItemList.IC2_PlantballCompressed.set(GT_ModHandler.getIC2Item("compressedPlantBall", 1L, ItemList.IC2_Plantball.get(1L, new Object[0])));
        ItemList.IC2_Crop_Seeds.set(GT_ModHandler.getIC2Item("cropSeed", 1L));
        ItemList.IC2_Grin_Powder.set(GT_ModHandler.getIC2Item("grinPowder", 1L));
        ItemList.IC2_Energium_Dust.set(GT_ModHandler.getIC2Item("energiumDust", 1L));
        ItemList.IC2_Scrap.set(GT_ModHandler.getIC2Item("scrap", 1L));
        ItemList.IC2_Scrapbox.set(GT_ModHandler.getIC2Item("scrapBox", 1L));
        ItemList.IC2_Fuel_Rod_Empty.set(GT_ModHandler.getIC2Item("fuelRod", 1L));
        ItemList.IC2_Food_Can_Empty.set(GT_ModHandler.getIC2Item("tinCan", 1L));
        ItemList.IC2_Food_Can_Filled.set(GT_ModHandler.getIC2Item("filledTinCan", 1L, 0));
        ItemList.IC2_Food_Can_Spoiled.set(GT_ModHandler.getIC2Item("filledTinCan", 1L, 1));
        ItemList.IC2_Industrial_Diamond.set(GT_ModHandler.getIC2Item("industrialDiamond", 1L, new ItemStack(Items.diamond, 1)));
        ItemList.IC2_Compressed_Coal_Ball.set(GT_ModHandler.getIC2Item("compressedCoalBall", 1L));
        ItemList.IC2_Compressed_Coal_Chunk.set(GT_ModHandler.getIC2Item("coalChunk", 1L));
        ItemList.IC2_ShaftIron.set(GT_ModHandler.getIC2Item("ironshaft", 1L));
        ItemList.IC2_ShaftSteel.set(GT_ModHandler.getIC2Item("steelshaft", 1L));

        ItemList.IC2_SuBattery.set(GT_ModHandler.getIC2Item("suBattery", 1L));
        ItemList.IC2_ReBattery.set(GT_ModHandler.getIC2Item("reBattery", 1L));
        ItemList.IC2_AdvBattery.set(GT_ModHandler.getIC2Item("advBattery", 1L));
        ItemList.IC2_EnergyCrystal.set(GT_ModHandler.getIC2Item("energyCrystal", 1L));
        ItemList.IC2_LapotronCrystal.set(GT_ModHandler.getIC2Item("lapotronCrystal", 1L));

        ItemList.IC2_LapotronCrystal.set(GT_ModHandler.getIC2Item("lapotronCrystal", 1L));
        ItemList.IC2_LapotronCrystal.set(GT_ModHandler.getIC2Item("lapotronCrystal", 1L));
        ItemList.IC2_LapotronCrystal.set(GT_ModHandler.getIC2Item("lapotronCrystal", 1L));

        ItemList.Tool_Sword_Bronze.set(GT_ModHandler.getIC2Item("bronzeSword", 1L));
        ItemList.Tool_Pickaxe_Bronze.set(GT_ModHandler.getIC2Item("bronzePickaxe", 1L));
        ItemList.Tool_Shovel_Bronze.set(GT_ModHandler.getIC2Item("bronzeShovel", 1L));
        ItemList.Tool_Axe_Bronze.set(GT_ModHandler.getIC2Item("bronzeAxe", 1L));
        ItemList.Tool_Hoe_Bronze.set(GT_ModHandler.getIC2Item("bronzeHoe", 1L));
        ItemList.IC2_ForgeHammer.set(GT_ModHandler.getIC2Item("ForgeHammer", 1L));
        ItemList.IC2_WireCutter.set(GT_ModHandler.getIC2Item("cutter", 1L));

        ItemList.Credit_Iron.set(GT_ModHandler.getIC2Item("coin", 1L));

        ItemList.Circuit_Basic.set(GT_ModHandler.getIC2Item("electronicCircuit", 1L));
        ItemList.Circuit_Advanced.set(GT_ModHandler.getIC2Item("advancedCircuit", 1L));

        ItemList.Upgrade_Overclocker.set(GT_ModHandler.getIC2Item("overclockerUpgrade", 1L));
        ItemList.Upgrade_Battery.set(GT_ModHandler.getIC2Item("energyStorageUpgrade", 1L));

        ItemList.Dye_Bonemeal.set(new ItemStack(Items.dye, 1, 15));
        ItemList.Dye_SquidInk.set(new ItemStack(Items.dye, 1, 0));
        ItemList.Dye_Cocoa.set(new ItemStack(Items.dye, 1, 3));

        ItemList.Book_Written_00.set(new ItemStack(Items.written_book, 1, 0));

        ItemList.Food_Baked_Bread.set(new ItemStack(Items.bread, 1, 0));
        ItemList.Food_Raw_Potato.set(new ItemStack(Items.potato, 1, 0));
        ItemList.Food_Baked_Potato.set(new ItemStack(Items.baked_potato, 1, 0));
        ItemList.Food_Poisonous_Potato.set(new ItemStack(Items.poisonous_potato, 1, 0));

        OrePrefixes.bottle.mContainerItem = ItemList.Bottle_Empty.get(1L, new Object[0]);
        OrePrefixes.bucket.mContainerItem = new ItemStack(Items.bucket, 1);
        OrePrefixes.cellPlasma.mContainerItem = ItemList.Cell_Empty.get(1L, new Object[0]);
        OrePrefixes.cell.mContainerItem = ItemList.Cell_Empty.get(1L, new Object[0]);

        GregTech_API.sFrostHazmatList.add(GT_ModHandler.getIC2Item("hazmatHelmet", 1L, 32767));
        GregTech_API.sFrostHazmatList.add(GT_ModHandler.getIC2Item("hazmatChestplate", 1L, 32767));
        GregTech_API.sFrostHazmatList.add(GT_ModHandler.getIC2Item("hazmatLeggings", 1L, 32767));
        GregTech_API.sFrostHazmatList.add(GT_ModHandler.getIC2Item("hazmatBoots", 1L, 32767));

        GregTech_API.sHeatHazmatList.add(GT_ModHandler.getIC2Item("hazmatHelmet", 1L, 32767));
        GregTech_API.sHeatHazmatList.add(GT_ModHandler.getIC2Item("hazmatChestplate", 1L, 32767));
        GregTech_API.sHeatHazmatList.add(GT_ModHandler.getIC2Item("hazmatLeggings", 1L, 32767));
        GregTech_API.sHeatHazmatList.add(GT_ModHandler.getIC2Item("hazmatBoots", 1L, 32767));

        GregTech_API.sBioHazmatList.add(GT_ModHandler.getIC2Item("hazmatHelmet", 1L, 32767));
        GregTech_API.sBioHazmatList.add(GT_ModHandler.getIC2Item("hazmatChestplate", 1L, 32767));
        GregTech_API.sBioHazmatList.add(GT_ModHandler.getIC2Item("hazmatLeggings", 1L, 32767));
        GregTech_API.sBioHazmatList.add(GT_ModHandler.getIC2Item("hazmatBoots", 1L, 32767));

        GregTech_API.sGasHazmatList.add(GT_ModHandler.getIC2Item("hazmatHelmet", 1L, 32767));
        GregTech_API.sGasHazmatList.add(GT_ModHandler.getIC2Item("hazmatChestplate", 1L, 32767));
        GregTech_API.sGasHazmatList.add(GT_ModHandler.getIC2Item("hazmatLeggings", 1L, 32767));
        GregTech_API.sGasHazmatList.add(GT_ModHandler.getIC2Item("hazmatBoots", 1L, 32767));

        GregTech_API.sRadioHazmatList.add(GT_ModHandler.getIC2Item("hazmatHelmet", 1L, 32767));
        GregTech_API.sRadioHazmatList.add(GT_ModHandler.getIC2Item("hazmatChestplate", 1L, 32767));
        GregTech_API.sRadioHazmatList.add(GT_ModHandler.getIC2Item("hazmatLeggings", 1L, 32767));
        GregTech_API.sRadioHazmatList.add(GT_ModHandler.getIC2Item("hazmatBoots", 1L, 32767));

        GregTech_API.sElectroHazmatList.add(GT_ModHandler.getIC2Item("hazmatHelmet", 1L, 32767));
        GregTech_API.sElectroHazmatList.add(GT_ModHandler.getIC2Item("hazmatChestplate", 1L, 32767));
        GregTech_API.sElectroHazmatList.add(GT_ModHandler.getIC2Item("hazmatLeggings", 1L, 32767));
        GregTech_API.sElectroHazmatList.add(GT_ModHandler.getIC2Item("hazmatBoots", 1L, 32767));
        GregTech_API.sElectroHazmatList.add(new ItemStack(Items.chainmail_helmet, 1, 32767));
        GregTech_API.sElectroHazmatList.add(new ItemStack(Items.chainmail_chestplate, 1, 32767));
        GregTech_API.sElectroHazmatList.add(new ItemStack(Items.chainmail_leggings, 1, 32767));
        GregTech_API.sElectroHazmatList.add(new ItemStack(Items.chainmail_boots, 1, 32767));

        GT_ModHandler.sNonReplaceableItems.add(new ItemStack(Items.bow, 1, 32767));
        GT_ModHandler.sNonReplaceableItems.add(new ItemStack(Items.fishing_rod, 1, 32767));
        GT_ModHandler.sNonReplaceableItems.add(ItemList.IC2_ForgeHammer.getWithDamage(1L, 32767L, new Object[0]));
        GT_ModHandler.sNonReplaceableItems.add(ItemList.IC2_WireCutter.getWithDamage(1L, 32767L, new Object[0]));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getIC2Item("painter", 1L, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getIC2Item("blackPainter", 1L, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getIC2Item("redPainter", 1L, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getIC2Item("greenPainter", 1L, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getIC2Item("brownPainter", 1L, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getIC2Item("bluePainter", 1L, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getIC2Item("purplePainter", 1L, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getIC2Item("cyanPainter", 1L, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getIC2Item("lightGreyPainter", 1L, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getIC2Item("darkGreyPainter", 1L, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getIC2Item("pinkPainter", 1L, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getIC2Item("limePainter", 1L, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getIC2Item("yellowPainter", 1L, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getIC2Item("cloudPainter", 1L, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getIC2Item("magentaPainter", 1L, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getIC2Item("orangePainter", 1L, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getIC2Item("whitePainter", 1L, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getIC2Item("cfPack", 1L, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getIC2Item("jetpack", 1L, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getIC2Item("treetap", 1L, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getIC2Item("weedEx", 1L, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getIC2Item("staticBoots", 1L, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getIC2Item("compositeArmor", 1L, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getIC2Item("hazmatHelmet", 1L, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getIC2Item("hazmatChestplate", 1L, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getIC2Item("hazmatLeggings", 1L, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getIC2Item("hazmatBoots", 1L, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getModItem("Railcraft", "part.turbine.disk", 1L, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getModItem("Railcraft", "part.turbine.blade", 1L, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getModItem("Railcraft", "part.turbine.rotor", 1L, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getModItem("Railcraft", "borehead.diamond", 1L, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getModItem("Railcraft", "borehead.steel", 1L, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getModItem("Railcraft", "borehead.iron", 1L, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getModItem("TwilightForest", "item.plateNaga", 1L, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getModItem("TwilightForest", "item.legsNaga", 1L, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getModItem("TwilightForest", "item.arcticHelm", 1L, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getModItem("TwilightForest", "item.arcticPlate", 1L, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getModItem("TwilightForest", "item.arcticLegs", 1L, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getModItem("TwilightForest", "item.arcticBoots", 1L, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getModItem("TwilightForest", "item.yetiHelm", 1L, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getModItem("TwilightForest", "item.yetiPlate", 1L, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getModItem("TwilightForest", "item.yetiLegs", 1L, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getModItem("TwilightForest", "item.yetiBoots", 1L, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getModItem("appliedenergistics2", "item.ToolCertusQuartzCuttingKnife", 1L, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getModItem("appliedenergistics2", "item.ToolNetherQuartzCuttingKnife", 1L, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getModItem("Forestry", "apiaristHelmet", 1L, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getModItem("Forestry", "apiaristChest", 1L, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getModItem("Forestry", "apiaristLegs", 1L, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getModItem("Forestry", "apiaristBoots", 1L, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getModItem("Forestry", "frameUntreated", 1L, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getModItem("Forestry", "frameImpregnated", 1L, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getModItem("Forestry", "frameProven", 1L, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getModItem("Forestry", "waxCast", 1L, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getModItem("GalacticraftCore", "item.sensorGlasses", 1L, 32767));
        GT_ModHandler.sNonReplaceableItems.add(GT_ModHandler.getModItem("IC2NuclearControl", "ItemToolThermometer", 1L, 32767));

        RecipeSorter.register("gregtech:shaped", GT_Shaped_Recipe.class, RecipeSorter.Category.SHAPED, "after:minecraft:shaped before:minecraft:shapeless");
        RecipeSorter.register("gregtech:shapeless", GT_Shapeless_Recipe.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
    }

    public void onLoad() {
        GT_Log.out.println("GT_Mod: Beginning Load-Phase.");
        GT_Log.ore.println("GT_Mod: Beginning Load-Phase.");
        GT_OreDictUnificator.registerOre("cropChilipepper", GT_ModHandler.getModItem("magicalcrops", "magicalcrops_CropProduce", 1L, 2));
        GT_OreDictUnificator.registerOre("cropTomato", GT_ModHandler.getModItem("magicalcrops", "magicalcrops_CropProduce", 1L, 8));
        GT_OreDictUnificator.registerOre("cropGrape", GT_ModHandler.getModItem("magicalcrops", "magicalcrops_CropProduce", 1L, 4));
        GT_OreDictUnificator.registerOre("cropTea", GT_ModHandler.getModItem("ganyssurface", "teaLeaves", 1L, 0));

        GregTech_API.sLoadStarted = true;
        for (FluidContainerRegistry.FluidContainerData tData : FluidContainerRegistry.getRegisteredFluidContainerData()) {
            if ((tData.filledContainer.getItem() == Items.potionitem) && (tData.filledContainer.getItemDamage() == 0)) {
                tData.fluid.amount = 0;
                break;
            }
        }
    }

    public void onPostLoad() {
        GT_Log.out.println("GT_Mod: Beginning PostLoad-Phase.");
        GT_Log.ore.println("GT_Mod: Beginning PostLoad-Phase.");
        if (GT_Log.pal != null) {
            new Thread(new GT_PlayerActivityLogger()).start();
        }
        GregTech_API.sPostloadStarted = true;
        for (FluidContainerRegistry.FluidContainerData tData : FluidContainerRegistry.getRegisteredFluidContainerData()) {
            if ((tData.filledContainer.getItem() == Items.potionitem) && (tData.filledContainer.getItemDamage() == 0)) {
                tData.fluid.amount = 0;
                break;
            }
        }
        GT_Log.out.println("GT_Mod: Adding Configs specific for MetaTileEntities");
        for (int i = 1; i < GregTech_API.METATILEENTITIES.length; i++) {
            try {
                for (; i < GregTech_API.METATILEENTITIES.length; i++) {
                    if (GregTech_API.METATILEENTITIES[i] != null) {
                        GregTech_API.METATILEENTITIES[i].onConfigLoad(GregTech_API.sMachineFile);
                    }
                }
            } catch (Throwable e) {
                e.printStackTrace(GT_Log.err);
            }
        }
        GT_Log.out.println("GT_Mod: Adding Tool Usage Crafting Recipes for OreDict Items.");
        long tBits = GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED
                | GT_ModHandler.RecipeBits.ONLY_ADD_IF_RESULT_IS_NOT_NULL | GT_ModHandler.RecipeBits.NOT_REMOVABLE;
        for (Materials aMaterial : Materials.VALUES) {
            if ((aMaterial.mUnificatable) && (aMaterial.mMaterialInto == aMaterial)) {
                if (!aMaterial.contains(SubTag.NO_SMASHING)) {
                    if (GregTech_API.sRecipeFile.get(ConfigCategories.Tools.hammerplating, aMaterial.toString(), true)) {
                        GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial, 1L), tBits, new Object[]{"h", "X", "X",
                                Character.valueOf('X'), OrePrefixes.ingot.get(aMaterial)});
                        GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial, 1L), tBits,
                                new Object[]{"h", "X", Character.valueOf('X'), OrePrefixes.gem.get(aMaterial)});
                        GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial, 1L), tBits,
                                new Object[]{"H", "X", Character.valueOf('H'), ToolDictNames.craftingToolForgeHammer, Character.valueOf('X'),
                                        OrePrefixes.ingot.get(aMaterial)});
                        GT_ModHandler.addCraftingRecipe(
                                GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial, 1L),
                                tBits,
                                new Object[]{"H", "X", Character.valueOf('H'), ToolDictNames.craftingToolForgeHammer, Character.valueOf('X'),
                                        OrePrefixes.gem.get(aMaterial)});
                        GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial, 1L), tBits,
                                new Object[]{"h", "X", Character.valueOf('X'), OrePrefixes.ingotDouble.get(aMaterial)});
                        GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial, 2L), tBits,
                                new Object[]{"H", "X", Character.valueOf('H'), ToolDictNames.craftingToolForgeHammer, Character.valueOf('X'),
                                        OrePrefixes.ingotDouble.get(aMaterial)});
                    }
                    if (GregTech_API.sRecipeFile.get(ConfigCategories.Tools.hammermultiingot, aMaterial.toString(), true)) {
                        GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotDouble, aMaterial, 1L), tBits, new Object[]{"I", "I", "h",
                                Character.valueOf('I'), OrePrefixes.ingot.get(aMaterial)});
                        GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotTriple, aMaterial, 1L), tBits, new Object[]{"I", "B", "h",
                                Character.valueOf('I'), OrePrefixes.ingotDouble.get(aMaterial), Character.valueOf('B'), OrePrefixes.ingot.get(aMaterial)});
                        GT_ModHandler
                                .addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotQuadruple, aMaterial, 1L), tBits,
                                        new Object[]{"I", "B", "h", Character.valueOf('I'), OrePrefixes.ingotTriple.get(aMaterial), Character.valueOf('B'),
                                                OrePrefixes.ingot.get(aMaterial)});
                        GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.ingotQuintuple, aMaterial, 1L), tBits,
                                new Object[]{"I", "B", "h", Character.valueOf('I'), OrePrefixes.ingotQuadruple.get(aMaterial), Character.valueOf('B'),
                                        OrePrefixes.ingot.get(aMaterial)});
                    }
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadAxe, aMaterial, 1L), tBits, new Object[]{"PIh", "P  ",
                            "f  ", Character.valueOf('P'), OrePrefixes.plate.get(aMaterial), Character.valueOf('I'), OrePrefixes.ingot.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadHammer, aMaterial, 1L), tBits, new Object[]{"II ", "IIh",
                            "II ", Character.valueOf('P'), OrePrefixes.plate.get(aMaterial), Character.valueOf('I'), OrePrefixes.ingot.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadHoe, aMaterial, 1L), tBits, new Object[]{"PIh", "f  ",
                            Character.valueOf('P'), OrePrefixes.plate.get(aMaterial), Character.valueOf('I'), OrePrefixes.ingot.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadPickaxe, aMaterial, 1L), tBits, new Object[]{"PII", "f h",
                            Character.valueOf('P'), OrePrefixes.plate.get(aMaterial), Character.valueOf('I'), OrePrefixes.ingot.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadPlow, aMaterial, 1L), tBits, new Object[]{"PP", "PP", "hf",
                            Character.valueOf('P'), OrePrefixes.plate.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadSaw, aMaterial, 1L), tBits, new Object[]{"PP ", "fh ",
                            Character.valueOf('P'), OrePrefixes.plate.get(aMaterial), Character.valueOf('I'), OrePrefixes.ingot.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadSense, aMaterial, 1L), tBits, new Object[]{"PPI", "hf ",
                            Character.valueOf('P'), OrePrefixes.plate.get(aMaterial), Character.valueOf('I'), OrePrefixes.ingot.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(
                            GT_OreDictUnificator.get(OrePrefixes.toolHeadShovel, aMaterial, 1L),
                            tBits,
                            new Object[]{"fPh", Character.valueOf('P'), OrePrefixes.plate.get(aMaterial), Character.valueOf('I'),
                                    OrePrefixes.ingot.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadSword, aMaterial, 1L), tBits, new Object[]{" P ", "fPh",
                            Character.valueOf('P'), OrePrefixes.plate.get(aMaterial), Character.valueOf('I'), OrePrefixes.ingot.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.ring, aMaterial, 1L), tBits,
                            new Object[]{"h ", " X", Character.valueOf('X'), OrePrefixes.stick.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.stickLong, aMaterial, 1L), tBits,
                            new Object[]{"ShS", Character.valueOf('S'), OrePrefixes.stick.get(aMaterial)});
                }
                if (!aMaterial.contains(SubTag.NO_WORKING)) {
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, aMaterial, 2L), tBits,
                            new Object[]{"s", "X", Character.valueOf('X'), OrePrefixes.stickLong.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, aMaterial, 1L), tBits,
                            new Object[]{"f ", " X", Character.valueOf('X'), OrePrefixes.ingot.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.bolt, aMaterial, 2L), tBits,
                            new Object[]{"s ", " X", Character.valueOf('X'), OrePrefixes.stick.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.screw, aMaterial, 1L), tBits,
                            new Object[]{"fX", "X ", Character.valueOf('X'), OrePrefixes.bolt.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.round, aMaterial, 1L), tBits,
                            new Object[]{"fX", "X ", Character.valueOf('X'), OrePrefixes.nugget.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.rotor, aMaterial, 1L), tBits, new Object[]{"PhP", "SRf", "PdP",
                            Character.valueOf('P'), aMaterial == Materials.Wood ? OrePrefixes.plank.get(aMaterial) : OrePrefixes.plate.get(aMaterial),
                            Character.valueOf('R'), OrePrefixes.ring.get(aMaterial), Character.valueOf('S'), OrePrefixes.screw.get(aMaterial)});
                    GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial, 4L), GT_OreDictUnificator.get(OrePrefixes.ring, aMaterial, 1L), Materials.Tin.getMolten(32), GT_OreDictUnificator.get(OrePrefixes.rotor, aMaterial, 1L), 240, 24);
                    GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial, 4L), GT_OreDictUnificator.get(OrePrefixes.ring, aMaterial, 1L), Materials.Lead.getMolten(48), GT_OreDictUnificator.get(OrePrefixes.rotor, aMaterial, 1L), 240, 24);
                    GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial, 4L), GT_OreDictUnificator.get(OrePrefixes.ring, aMaterial, 1L), Materials.SolderingAlloy.getMolten(16), GT_OreDictUnificator.get(OrePrefixes.rotor, aMaterial, 1L), 240, 24);
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.stickLong, aMaterial, 1L), tBits,
                            new Object[]{"sf", "G ", Character.valueOf('G'), OrePrefixes.gemFlawless.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.stickLong, aMaterial, 2L), tBits,
                            new Object[]{"sf", "G ", Character.valueOf('G'), OrePrefixes.gemExquisite.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.wireGt01, aMaterial, 1L), tBits,
                            new Object[]{"Xx", Character.valueOf('X'), OrePrefixes.plate.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.wireFine, aMaterial, 1L), tBits,
                            new Object[]{"Xx", Character.valueOf('X'), OrePrefixes.foil.get(aMaterial)});

                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.turbineBlade, aMaterial, 1L), tBits, new Object[]{"fPd", "SPS", " P ",
                            Character.valueOf('P'), aMaterial == Materials.Wood ? OrePrefixes.plank.get(aMaterial) : OrePrefixes.plateDouble.get(aMaterial),
                            Character.valueOf('R'), OrePrefixes.ring.get(aMaterial), Character.valueOf('S'), OrePrefixes.screw.get(aMaterial)});


                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.arrowGtWood, aMaterial, 1L), tBits, new Object[]{"  A", " S ",
                            "F  ", Character.valueOf('S'), OrePrefixes.stick.get(Materials.Wood), Character.valueOf('F'), OreDictNames.craftingFeather,
                            Character.valueOf('A'), OrePrefixes.toolHeadArrow.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.arrowGtPlastic, aMaterial, 1L), tBits, new Object[]{"  A", " S ",
                            "F  ", Character.valueOf('S'), OrePrefixes.stick.get(Materials.Plastic), Character.valueOf('F'), OreDictNames.craftingFeather,
                            Character.valueOf('A'), OrePrefixes.toolHeadArrow.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadArrow, aMaterial, 1L), tBits,
                            new Object[]{"Xf", Character.valueOf('X'), OrePrefixes.gemChipped.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadArrow, aMaterial, 3L), tBits,
                            new Object[]{(aMaterial.contains(SubTag.WOOD) ? 115 : 'x') + "Pf", Character.valueOf('P'), OrePrefixes.plate.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadAxe, aMaterial, 1L), tBits, new Object[]{"GG ", "G  ",
                            "f  ", Character.valueOf('G'), OrePrefixes.gem.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadHoe, aMaterial, 1L), tBits, new Object[]{"GG ", "f  ",
                            "   ", Character.valueOf('G'), OrePrefixes.gem.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadPickaxe, aMaterial, 1L), tBits, new Object[]{"GGG", "f  ",
                            Character.valueOf('G'), OrePrefixes.gem.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadPlow, aMaterial, 1L), tBits, new Object[]{"GG", "GG", " f",
                            Character.valueOf('G'), OrePrefixes.gem.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadSaw, aMaterial, 1L), tBits,
                            new Object[]{"GGf", Character.valueOf('G'), OrePrefixes.gem.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadSense, aMaterial, 1L), tBits, new Object[]{"GGG", " f ",
                            "   ", Character.valueOf('G'), OrePrefixes.gem.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadShovel, aMaterial, 1L), tBits,
                            new Object[]{"fG", Character.valueOf('G'), OrePrefixes.gem.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadSword, aMaterial, 1L), tBits, new Object[]{" G", "fG",
                            Character.valueOf('G'), OrePrefixes.gem.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadUniversalSpade, aMaterial, 1L), tBits, new Object[]{"fX",
                            Character.valueOf('X'), OrePrefixes.toolHeadShovel.get(aMaterial)});

                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadBuzzSaw, aMaterial, 1L), tBits, new Object[]{"wXh", "X X",
                            "fXx", Character.valueOf('X'), OrePrefixes.plate.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadWrench, aMaterial, 1L), tBits, new Object[]{"hXW", "XRX",
                            "WXd", Character.valueOf('X'), OrePrefixes.plate.get(aMaterial), Character.valueOf('S'), OrePrefixes.plate.get(Materials.Steel),
                            Character.valueOf('R'), OrePrefixes.ring.get(Materials.Steel), Character.valueOf('W'), OrePrefixes.screw.get(Materials.Steel)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadChainsaw, aMaterial, 1L), tBits, new Object[]{"SRS", "XhX",
                            "SRS", Character.valueOf('X'), OrePrefixes.plate.get(aMaterial), Character.valueOf('S'), OrePrefixes.plate.get(Materials.Steel),
                            Character.valueOf('R'), OrePrefixes.ring.get(Materials.Steel)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadDrill, aMaterial, 1L), tBits, new Object[]{"XSX", "XSX",
                            "ShS", Character.valueOf('X'), OrePrefixes.plate.get(aMaterial), Character.valueOf('S'), OrePrefixes.plate.get(Materials.Steel)});
                    switch (aMaterial) {
                        case Wood:
                            GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, aMaterial, 1L), tBits, new Object[]{"P ", " s",
                                    Character.valueOf('P'), OrePrefixes.plank.get(aMaterial)});
                            break;
                        case Stone:
                            GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, aMaterial, 1L), tBits, new Object[]{"P ", " f",
                                    Character.valueOf('P'), OrePrefixes.stoneSmooth});
                            break;
                        default:
                            GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, aMaterial, 1L), tBits,
                                    new Object[]{"P ", aMaterial.contains(SubTag.WOOD) ? " s" : " h", Character.valueOf('P'), OrePrefixes.plate.get(aMaterial)});
                    }
                    switch (aMaterial) {
                        case Wood:
                            GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.gearGt, aMaterial, 1L), tBits, new Object[]{"SPS", "PsP", "SPS",
                                    Character.valueOf('P'), OrePrefixes.plank.get(aMaterial), Character.valueOf('S'), OrePrefixes.stick.get(aMaterial)});
                            break;
                        case Stone:
                            GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.gearGt, aMaterial, 1L), tBits, new Object[]{"SPS", "PfP", "SPS",
                                    Character.valueOf('P'), OrePrefixes.stoneSmooth, Character.valueOf('S'), new ItemStack(Blocks.stone_button, 1, 32767)});
                            break;
                        default:
                            GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.gearGt, aMaterial, 1L), tBits, new Object[]{"SPS", "PwP", "SPS",
                                    Character.valueOf('P'), OrePrefixes.plate.get(aMaterial), Character.valueOf('S'), OrePrefixes.stick.get(aMaterial)});
                    }
                }
                if (aMaterial.contains(SubTag.SMELTING_TO_GEM)) {
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.gem, aMaterial, 1L), tBits, new Object[]{"XXX", "XXX", "XXX",
                            Character.valueOf('X'), OrePrefixes.nugget.get(aMaterial)});
                } else {
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial, 1L), tBits, new Object[]{"XXX", "XXX", "XXX",
                            Character.valueOf('X'), OrePrefixes.nugget.get(aMaterial)});
                }
                GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial.mMacerateInto, 1L), tBits, new Object[]{"h", "X",
                        Character.valueOf('X'), OrePrefixes.crushedCentrifuged.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial.mMacerateInto, 1L), tBits, new Object[]{"h", "X",
                        Character.valueOf('X'), OrePrefixes.crystalline.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial.mMacerateInto, 1L), tBits, new Object[]{"h", "X",
                        Character.valueOf('X'), OrePrefixes.crystal.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dustPure, aMaterial.mMacerateInto, 1L), tBits, new Object[]{"h", "X",
                        Character.valueOf('X'), OrePrefixes.crushedPurified.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dustPure, aMaterial.mMacerateInto, 1L), tBits, new Object[]{"h", "X",
                        Character.valueOf('X'), OrePrefixes.cleanGravel.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dustPure, aMaterial.mMacerateInto, 1L), tBits, new Object[]{"h", "X",
                        Character.valueOf('X'), OrePrefixes.reduced.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dustImpure, aMaterial.mMacerateInto, 1L), tBits, new Object[]{"h", "X",
                        Character.valueOf('X'), OrePrefixes.clump.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dustImpure, aMaterial.mMacerateInto, 1L), tBits, new Object[]{"h", "X",
                        Character.valueOf('X'), OrePrefixes.shard.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dustImpure, aMaterial.mMacerateInto, 1L), tBits, new Object[]{"h", "X",
                        Character.valueOf('X'), OrePrefixes.crushed.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dustImpure, aMaterial.mMacerateInto, 1L), tBits, new Object[]{"h", "X",
                        Character.valueOf('X'), OrePrefixes.dirtyGravel.get(aMaterial)});

                GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dustSmall, aMaterial, 4L), tBits,
                        new Object[]{" X", "  ", Character.valueOf('X'), OrePrefixes.dust.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dustTiny, aMaterial, 9L), tBits,
                        new Object[]{"X ", "  ", Character.valueOf('X'), OrePrefixes.dust.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, 1L), tBits,
                        new Object[]{"XX", "XX", Character.valueOf('X'), OrePrefixes.dustSmall.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, 1L), tBits,
                        new Object[]{"XXX", "XXX", "XXX", Character.valueOf('X'), OrePrefixes.dustTiny.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, 16L), tBits, new Object[]{"Xc", Character.valueOf('X'),
                        OrePrefixes.crateGtDust.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.gem, aMaterial, 16L), tBits, new Object[]{"Xc", Character.valueOf('X'),
                        OrePrefixes.crateGtGem.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial, 16L), tBits, new Object[]{"Xc",
                        Character.valueOf('X'), OrePrefixes.crateGtIngot.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial, 16L), tBits, new Object[]{"Xc",
                        Character.valueOf('X'), OrePrefixes.crateGtPlate.get(aMaterial)});

                GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.gemChipped, aMaterial, 2L), tBits,
                        new Object[]{"h", "X", Character.valueOf('X'), OrePrefixes.gemFlawed.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.gemFlawed, aMaterial, 2L), tBits,
                        new Object[]{"h", "X", Character.valueOf('X'), OrePrefixes.gem.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.gem, aMaterial, 2L), tBits,
                        new Object[]{"h", "X", Character.valueOf('X'), OrePrefixes.gemFlawless.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.gemFlawless, aMaterial, 2L), tBits,
                        new Object[]{"h", "X", Character.valueOf('X'), OrePrefixes.gemExquisite.get(aMaterial)});
                if ((aMaterial.contains(SubTag.MORTAR_GRINDABLE)) && (GregTech_API.sRecipeFile.get(ConfigCategories.Tools.mortar, aMaterial.name(), true))) {
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dustSmall, aMaterial, 1L), tBits,
                            new Object[]{"X", "m", Character.valueOf('X'), OrePrefixes.gemChipped.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dustSmall, aMaterial, 2L), tBits,
                            new Object[]{"X", "m", Character.valueOf('X'), OrePrefixes.gemFlawed.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, 1L), tBits,
                            new Object[]{"X", "m", Character.valueOf('X'), OrePrefixes.gem.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, 2L), tBits,
                            new Object[]{"X", "m", Character.valueOf('X'), OrePrefixes.gemFlawless.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, 4L), tBits,
                            new Object[]{"X", "m", Character.valueOf('X'), OrePrefixes.gemExquisite.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, 1L), tBits,
                            new Object[]{"X", "m", Character.valueOf('X'), OrePrefixes.ingot.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, 1L), tBits,
                            new Object[]{"X", "m", Character.valueOf('X'), OrePrefixes.plate.get(aMaterial)});
                }
            }
        }
    }

    public void onServerStarting() {
        GT_Log.out.println("GT_Mod: ServerStarting-Phase started!");
        GT_Log.ore.println("GT_Mod: ServerStarting-Phase started!");

        this.mUniverse = null;
        this.isFirstServerWorldTick = true;
        for (FluidContainerRegistry.FluidContainerData tData : FluidContainerRegistry.getRegisteredFluidContainerData()) {
            if ((tData.filledContainer.getItem() == Items.potionitem) && (tData.filledContainer.getItemDamage() == 0)) {
                tData.fluid.amount = 0;
                break;
            }
        }
        for (int i = 1; i < GregTech_API.METATILEENTITIES.length; i++) {
            try {
                for (; i < GregTech_API.METATILEENTITIES.length; i++) {
                    if (GregTech_API.METATILEENTITIES[i] != null) {
                        GregTech_API.METATILEENTITIES[i].onServerStart();
                    }
                }
            } catch (Throwable e) {
                e.printStackTrace(GT_Log.err);
            }
        }
    }

    public void onServerStarted() {
        GregTech_API.sWirelessRedstone.clear();
        GT_FluidStack.fixAllThoseFuckingFluidIDs();

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
            for (int i = 1; i < GregTech_API.METATILEENTITIES.length; i++) {
                try {
                    for (; i < GregTech_API.METATILEENTITIES.length; i++) {
                        if (GregTech_API.METATILEENTITIES[i] != null) {
                            GregTech_API.METATILEENTITIES[i].onWorldSave(tSaveDirectory);
                        }
                    }
                } catch (Throwable e) {
                    e.printStackTrace(GT_Log.err);
                }
            }
        }
        this.mUniverse = null;
    }

    @SubscribeEvent
    public void onClientConnectedToServerEvent(FMLNetworkEvent.ClientConnectedToServerEvent aEvent) {
    }

    @SubscribeEvent
    public void onArrowNockEvent(ArrowNockEvent aEvent) {
        if ((!aEvent.isCanceled()) && (GT_Utility.isStackValid(aEvent.result))
                && (GT_Utility.getProjectile(SubTag.PROJECTILE_ARROW, aEvent.entityPlayer.inventory) != null)) {
            aEvent.entityPlayer.setItemInUse(aEvent.result, aEvent.result.getItem().getMaxItemUseDuration(aEvent.result));
            aEvent.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onArrowLooseEvent(ArrowLooseEvent aEvent) {
        ItemStack aArrow = GT_Utility.getProjectile(SubTag.PROJECTILE_ARROW, aEvent.entityPlayer.inventory);
        if ((!aEvent.isCanceled()) && (GT_Utility.isStackValid(aEvent.bow)) && (aArrow != null) && ((aEvent.bow.getItem() instanceof ItemBow))) {
            float tSpeed = aEvent.charge / 20.0F;
            tSpeed = (tSpeed * tSpeed + tSpeed * 2.0F) / 3.0F;
            if (tSpeed < 0.1D) {
                return;
            }
            if (tSpeed > 1.0D) {
                tSpeed = 1.0F;
            }
            EntityArrow tArrowEntity = ((IProjectileItem) aArrow.getItem()).getProjectile(SubTag.PROJECTILE_ARROW, aArrow, aEvent.entityPlayer.worldObj,
                    aEvent.entityPlayer, tSpeed * 2.0F);
            if (tSpeed >= 1.0F) {
                tArrowEntity.setIsCritical(true);
            }
            int tLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, aEvent.bow);
            if (tLevel > 0) {
                tArrowEntity.setDamage(tArrowEntity.getDamage() + tLevel * 0.5D + 0.5D);
            }
            tLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, aEvent.bow);
            if (tLevel > 0) {
                tArrowEntity.setKnockbackStrength(tLevel);
            }
            tLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, aEvent.bow);
            if (tLevel > 0) {
                tArrowEntity.setFire(tLevel * 100);
            }
            aEvent.bow.damageItem(1, aEvent.entityPlayer);
            aEvent.bow.getItem();
            aEvent.entityPlayer.worldObj.playSoundAtEntity(aEvent.entityPlayer, "random.bow", 1.0F, 1.0F / (new Random().nextFloat() * 0.4F + 1.2F) + tSpeed
                    * 0.5F);

            tArrowEntity.canBePickedUp = 1;
            if (!aEvent.entityPlayer.capabilities.isCreativeMode) {
                aArrow.stackSize -= 1;
            }
            if (aArrow.stackSize == 0) {
                GT_Utility.removeNullStacksFromInventory(aEvent.entityPlayer.inventory);
            }
            if (!aEvent.entityPlayer.worldObj.isRemote) {
                aEvent.entityPlayer.worldObj.spawnEntityInWorld(tArrowEntity);
            }
            aEvent.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onEndermanTeleportEvent(EnderTeleportEvent aEvent) {
        if (((aEvent.entityLiving instanceof EntityEnderman)) && (aEvent.entityLiving.getActivePotionEffect(Potion.weakness) != null)) {
            aEvent.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onEntitySpawningEvent(EntityJoinWorldEvent aEvent) {
        if ((aEvent.entity != null) && (!aEvent.entity.worldObj.isRemote)) {
            if ((aEvent.entity instanceof EntityItem)) {
                ((EntityItem) aEvent.entity).setEntityItemStack(GT_OreDictUnificator.get(((EntityItem) aEvent.entity).getEntityItem()));
            }
            if ((this.mSkeletonsShootGTArrows > 0) && (aEvent.entity.getClass() == EntityArrow.class)
                    && (aEvent.entity.worldObj.rand.nextInt(this.mSkeletonsShootGTArrows) == 0)
                    && ((((EntityArrow) aEvent.entity).shootingEntity instanceof EntitySkeleton))) {
                aEvent.entity.worldObj.spawnEntityInWorld(new GT_Entity_Arrow((EntityArrow) aEvent.entity, (ItemStack) OrePrefixes.arrowGtWood.mPrefixedItems
                        .get(aEvent.entity.worldObj.rand.nextInt(OrePrefixes.arrowGtWood.mPrefixedItems.size()))));
                aEvent.entity.setDead();
            }
        }
    }

    @SubscribeEvent
    public void onOreGenEvent(OreGenEvent.GenerateMinable aGenerator) {
        if ((this.mDisableVanillaOres) && ((aGenerator.generator instanceof WorldGenMinable)) && (PREVENTED_ORES.contains(aGenerator.type))) {
            aGenerator.setResult(Result.DENY);
        }
    }

    private String getDataAndTime() {
        return this.mDateFormat.format(new Date());
    }

    @SubscribeEvent
    public void onPlayerInteraction(PlayerInteractEvent aEvent) {
        if ((aEvent.entityPlayer == null) || (aEvent.entityPlayer.worldObj == null) || (aEvent.action == null) || (aEvent.world.provider == null)) {
            return;
        }
        if ((!aEvent.entityPlayer.worldObj.isRemote) && (aEvent.action != null) && (aEvent.action != PlayerInteractEvent.Action.RIGHT_CLICK_AIR)
                && (GT_Log.pal != null)) {
            this.mBufferedPlayerActivity.add(getDataAndTime() + ";" + aEvent.action.name() + ";" + aEvent.entityPlayer.getDisplayName() + ";DIM:"
                    + aEvent.world.provider.dimensionId + ";" + aEvent.x + ";" + aEvent.y + ";" + aEvent.z + ";|;" + aEvent.x / 10 + ";" + aEvent.y / 10 + ";"
                    + aEvent.z / 10);
        }
        ItemStack aStack = aEvent.entityPlayer.getCurrentEquippedItem();
        if ((aStack != null) && (aEvent.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) && (aStack.getItem() == Items.flint_and_steel)) {
            if ((!aEvent.world.isRemote) && (!aEvent.entityPlayer.capabilities.isCreativeMode) && (aEvent.world.rand.nextInt(100) >= this.mFlintChance)) {
                aEvent.setCanceled(true);
                aStack.damageItem(1, aEvent.entityPlayer);
                if (aStack.getItemDamage() >= aStack.getMaxDamage()) {
                    aStack.stackSize -= 1;
                }
                if (aStack.stackSize <= 0) {
                    ForgeEventFactory.onPlayerDestroyItem(aEvent.entityPlayer, aStack);
                }
            }
            return;
        }
    }

    @SubscribeEvent
    public void onBlockHarvestingEvent(BlockEvent.HarvestDropsEvent aEvent) {
        if (aEvent.harvester != null) {
            if ((!aEvent.world.isRemote) && (GT_Log.pal != null)) {
                this.mBufferedPlayerActivity.add(getDataAndTime() + ";HARVEST_BLOCK;" + aEvent.harvester.getDisplayName() + ";DIM:"
                        + aEvent.world.provider.dimensionId + ";" + aEvent.x + ";" + aEvent.y + ";" + aEvent.z + ";|;" + aEvent.x / 10 + ";" + aEvent.y / 10
                        + ";" + aEvent.z / 10);
            }
            ItemStack aStack = aEvent.harvester.getCurrentEquippedItem();
            if (aStack != null) {
                if ((aStack.getItem() instanceof GT_MetaGenerated_Tool)) {
                    ((GT_MetaGenerated_Tool) aStack.getItem()).onHarvestBlockEvent(aEvent.drops, aStack, aEvent.harvester, aEvent.block, aEvent.x, aEvent.y,
                            aEvent.z, (byte) aEvent.blockMetadata, aEvent.fortuneLevel, aEvent.isSilkTouching, aEvent);
                }
                if (EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, aStack) > 2) {
                    for (ItemStack tDrop : aEvent.drops) {
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
        if ((aEvent == null) || (aEvent.Ore == null) || (aEvent.Ore.getItem() == null) || (aEvent.Name == null) || (aEvent.Name.isEmpty())
                || (aEvent.Name.replaceAll("_", "").length() - aEvent.Name.length() == 9)) {
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
            aEvent.Ore.stackSize = 1;
            if (this.mIgnoreTcon || aEvent.Ore.getUnlocalizedName().startsWith("item.oreberry")) {
                if ((aOriginalMod.toLowerCase().contains("xycraft")) || (aOriginalMod.toLowerCase().contains("tconstruct"))
                        || ((aOriginalMod.toLowerCase().contains("natura")) && (!aOriginalMod.toLowerCase().contains("natural")))) {
                    if (GT_Values.D1) {
                        GT_Log.ore.println(aMod + " -> " + aEvent.Name + " is getting ignored, because of racism. :P");
                    }
                    return;
                }
            }
            String tModToName = aMod + " -> " + aEvent.Name;
            if ((this.mOreDictActivated) || (GregTech_API.sPostloadStarted) || ((this.mSortToTheEnd) && (GregTech_API.sLoadFinished))) {
                tModToName = aOriginalMod + " --Late--> " + aEvent.Name;
            }
            if (((aEvent.Ore.getItem() instanceof ItemBlock)) || (GT_Utility.getBlockFromStack(aEvent.Ore) != Blocks.air)) {
                GT_OreDictUnificator.addToBlacklist(aEvent.Ore);
            }
            this.mRegisteredOres.add(aEvent.Ore);
            if ((aEvent.Name.startsWith("item")) && (this.mIgnoredItems.contains(aEvent.Name))) {
                GT_Log.ore.println(tModToName);
                if (aEvent.Name.equals("itemCopperWire")) {
                    GT_OreDictUnificator.registerOre(OreDictNames.craftingWireCopper, aEvent.Ore);
                }
                if (aEvent.Name.equals("itemRubber")) {
                    GT_OreDictUnificator.registerOre(OrePrefixes.ingot, Materials.Rubber, aEvent.Ore);
                }
                return;
            }
            if (this.mIgnoredNames.contains(aEvent.Name)) {
                GT_Log.ore.println(tModToName + " is getting ignored via hardcode.");
                return;
            }
            if (aEvent.Name.equals("stone")) {
                GT_OreDictUnificator.registerOre("stoneSmooth", aEvent.Ore);
                return;
            }
            if (aEvent.Name.equals("cobblestone")) {
                GT_OreDictUnificator.registerOre("stoneCobble", aEvent.Ore);
                return;
            }
            if ((aEvent.Name.contains("|")) || (aEvent.Name.contains("*")) || (aEvent.Name.contains(":")) || (aEvent.Name.contains("."))
                    || (aEvent.Name.contains("$"))) {
                GT_Log.ore.println(tModToName + " is using a private Prefix and is therefor getting ignored properly.");
                return;
            }
            if (aEvent.Name.equals("copperWire")) {
                GT_OreDictUnificator.registerOre(OreDictNames.craftingWireCopper, aEvent.Ore);
            }
            if (aEvent.Name.equals("oreHeeEndrium")) {
                GT_OreDictUnificator.registerOre(OrePrefixes.ore, Materials.Endium, aEvent.Ore);
            }
            if (aEvent.Name.equals("sheetPlastic")) {
                GT_OreDictUnificator.registerOre(OrePrefixes.plate, Materials.Plastic, aEvent.Ore);
            }
            if (aEvent.Name.equals("shardAir")) {
                GT_OreDictUnificator.registerOre(OrePrefixes.gem, Materials.InfusedAir, aEvent.Ore);
                return;
            }
            if (aEvent.Name.equals("shardWater")) {
                GT_OreDictUnificator.registerOre(OrePrefixes.gem, Materials.InfusedWater, aEvent.Ore);
                return;
            }
            if (aEvent.Name.equals("shardFire")) {
                GT_OreDictUnificator.registerOre(OrePrefixes.gem, Materials.InfusedFire, aEvent.Ore);
                return;
            }
            if (aEvent.Name.equals("shardEarth")) {
                GT_OreDictUnificator.registerOre(OrePrefixes.gem, Materials.InfusedEarth, aEvent.Ore);
                return;
            }
            if (aEvent.Name.equals("shardOrder")) {
                GT_OreDictUnificator.registerOre(OrePrefixes.gem, Materials.InfusedOrder, aEvent.Ore);
                return;
            }
            if (aEvent.Name.equals("shardEntropy")) {
                GT_OreDictUnificator.registerOre(OrePrefixes.gem, Materials.InfusedEntropy, aEvent.Ore);
                return;
            }
            if (aEvent.Name.equals("fieryIngot")) {
                GT_OreDictUnificator.registerOre(OrePrefixes.ingot, Materials.FierySteel, aEvent.Ore);
                return;
            }
            if (aEvent.Name.equals("ironwood")) {
                GT_OreDictUnificator.registerOre(OrePrefixes.ingot, Materials.IronWood, aEvent.Ore);
                return;
            }
            if (aEvent.Name.equals("steeleaf")) {
                GT_OreDictUnificator.registerOre(OrePrefixes.ingot, Materials.Steeleaf, aEvent.Ore);
                return;
            }
            if (aEvent.Name.equals("knightmetal")) {
                GT_OreDictUnificator.registerOre(OrePrefixes.ingot, Materials.Knightmetal, aEvent.Ore);
                return;
            }
            if (aEvent.Name.contains(" ")) {
                GT_Log.ore.println(tModToName + " is getting re-registered because the OreDict Name containing invalid spaces.");
                GT_OreDictUnificator.registerOre(aEvent.Name.replaceAll(" ", ""), GT_Utility.copyAmount(1L, new Object[]{aEvent.Ore}));
                aEvent.Ore.setStackDisplayName("Invalid OreDictionary Tag");
                return;
            }
            if (this.mInvalidNames.contains(aEvent.Name)) {
                GT_Log.ore.println(tModToName + " is wrongly registered and therefor getting ignored.");

                return;
            }
            OrePrefixes aPrefix = OrePrefixes.getOrePrefix(aEvent.Name);
            Materials aMaterial = Materials._NULL;
            if ((aPrefix == OrePrefixes.nugget) && (aMod.equals("Thaumcraft")) && (aEvent.Ore.getItem().getUnlocalizedName().contains("ItemResource"))) {
                return;
            }
            if (aPrefix == null) {
                if (aEvent.Name.toLowerCase().equals(aEvent.Name)) {
                    GT_Log.ore.println(tModToName + " is invalid due to being solely lowercased.");
                    return;
                }
                if (aEvent.Name.toUpperCase().equals(aEvent.Name)) {
                    GT_Log.ore.println(tModToName + " is invalid due to being solely uppercased.");
                    return;
                }
                if (Character.isUpperCase(aEvent.Name.charAt(0))) {
                    GT_Log.ore.println(tModToName + " is invalid due to the first character being uppercased.");
                }
            } else {
                if (aPrefix.mDontUnificateActively) {
                    GT_OreDictUnificator.addToBlacklist(aEvent.Ore);
                }
                if (aPrefix != aPrefix.mPrefixInto) {
                    String tNewName = aEvent.Name.replaceFirst(aPrefix.toString(), aPrefix.mPrefixInto.toString());
                    if (!GT_OreDictUnificator.isRegisteringOres()) {
                        GT_Log.ore.println(tModToName + " uses a depricated Prefix, and is getting re-registered as " + tNewName);
                    }
                    GT_OreDictUnificator.registerOre(tNewName, aEvent.Ore);
                    return;
                }
                String tName = aEvent.Name.replaceFirst(aPrefix.toString(), "");
                if (tName.length() > 0) {
                    char firstChar = tName.charAt(0);
                    if (Character.isUpperCase(firstChar) || Character.isLowerCase(firstChar) || firstChar == '_') {
                        if (aPrefix.mIsMaterialBased) {
                            aMaterial = Materials.get(tName);
                            if (aMaterial != aMaterial.mMaterialInto) {
                                GT_OreDictUnificator.registerOre(aPrefix, aMaterial.mMaterialInto, aEvent.Ore);
                                if (!GT_OreDictUnificator.isRegisteringOres()) {
                                    GT_Log.ore.println(tModToName + " uses a deprecated Material and is getting re-registered as "
                                            + aPrefix.get(aMaterial.mMaterialInto));
                                }
                                return;
                            }
                            if (!aPrefix.isIgnored(aMaterial)) {
                                aPrefix.add(GT_Utility.copyAmount(1L, new Object[]{aEvent.Ore}));
                            }
                            if (aMaterial != Materials._NULL) {
                                Materials tReRegisteredMaterial;
                                for (Iterator i$ = aMaterial.mOreReRegistrations.iterator(); i$.hasNext(); GT_OreDictUnificator.registerOre(aPrefix,
                                        tReRegisteredMaterial, aEvent.Ore)) {
                                    tReRegisteredMaterial = (Materials) i$.next();
                                }
                                aMaterial.add(GT_Utility.copyAmount(1L, new Object[]{aEvent.Ore}));

                    			if (GregTech_API.sThaumcraftCompat != null && aPrefix.doGenerateItem(aMaterial) && !aPrefix.isIgnored(aMaterial)) {
                    	    	    List<TC_AspectStack> tAspects = new ArrayList<TC_AspectStack>();
                    	    	    for (TC_AspectStack tAspect : aPrefix.mAspects) tAspect.addToAspectList(tAspects);
                    	    	    if (aPrefix.mMaterialAmount >= 3628800 || aPrefix.mMaterialAmount < 0) for (TC_AspectStack tAspect : aMaterial.mAspects) tAspect.addToAspectList(tAspects);
                    	    	    GregTech_API.sThaumcraftCompat.registerThaumcraftAspectsToItem(GT_Utility.copyAmount(1, aEvent.Ore), tAspects, aEvent.Name);
                        	    }

                                switch (aPrefix) {
                                    case crystal:
                                        if ((aMaterial == Materials.CertusQuartz) || (aMaterial == Materials.NetherQuartz) || (aMaterial == Materials.Fluix)) {
                                            GT_OreDictUnificator.registerOre(OrePrefixes.gem, aMaterial, aEvent.Ore);
                                        }
                                        break;
                                    case gem:
                                        switch (aMaterial) {
                                            case Lapis:
                                            case Sodalite:
                                                GT_OreDictUnificator.registerOre(Dyes.dyeBlue, aEvent.Ore);
                                                break;
                                            case Lazurite:
                                                GT_OreDictUnificator.registerOre(Dyes.dyeCyan, aEvent.Ore);
                                                break;
                                            case InfusedAir:
                                            case InfusedWater:
                                            case InfusedFire:
                                            case InfusedEarth:
                                            case InfusedOrder:
                                            case InfusedEntropy:
                                                GT_OreDictUnificator.registerOre(aMaterial.name().replaceFirst("Infused", "shard"), aEvent.Ore);
                                                break;
                                            case Chocolate:
                                                GT_OreDictUnificator.registerOre(Dyes.dyeBrown, aEvent.Ore);
                                                break;
                                            case CertusQuartz:
                                            case NetherQuartz:
                                                GT_OreDictUnificator.registerOre(OrePrefixes.item.get(aMaterial), aEvent.Ore);
                                            case Fluix:
                                            case Quartz:
                                            case Quartzite:
                                                GT_OreDictUnificator.registerOre(OrePrefixes.crystal, aMaterial, aEvent.Ore);
                                                GT_OreDictUnificator.registerOre(OreDictNames.craftingQuartz, aEvent.Ore);
                                            default:
                                                break;
                                        }
                                        break;
                                    case cableGt01:
                                        if (aMaterial == Materials.Tin) {
                                            GT_OreDictUnificator.registerOre(OreDictNames.craftingWireTin, aEvent.Ore);
                                        }
                                        if (aMaterial == Materials.AnyCopper) {
                                            GT_OreDictUnificator.registerOre(OreDictNames.craftingWireCopper, aEvent.Ore);
                                        }
                                        if (aMaterial == Materials.Gold) {
                                            GT_OreDictUnificator.registerOre(OreDictNames.craftingWireGold, aEvent.Ore);
                                        }
                                        if (aMaterial == Materials.AnyIron) {
                                            GT_OreDictUnificator.registerOre(OreDictNames.craftingWireIron, aEvent.Ore);
                                        }
                                        break;
                                    case lens:
                                        if ((aMaterial.contains(SubTag.TRANSPARENT)) && (aMaterial.mColor != Dyes._NULL)) {
                                            GT_OreDictUnificator.registerOre("craftingLens" + aMaterial.mColor.toString().replaceFirst("dye", ""), aEvent.Ore);
                                        }
                                        break;
                                    case plate:
                                        if ((aMaterial == Materials.Plastic) || (aMaterial == Materials.Rubber)) {
                                            GT_OreDictUnificator.registerOre(OrePrefixes.sheet, aMaterial, aEvent.Ore);
                                        }
                                        if (aMaterial == Materials.Silicon) {
                                            GT_OreDictUnificator.registerOre(OrePrefixes.item, aMaterial, aEvent.Ore);
                                        }
                                        if (aMaterial == Materials.Wood) {
                                            GT_OreDictUnificator.addToBlacklist(aEvent.Ore);
                                            GT_OreDictUnificator.registerOre(OrePrefixes.plank, aMaterial, aEvent.Ore);
                                        }
                                        break;
                                    case cell:
                                        if (aMaterial == Materials.Empty) {
                                            GT_OreDictUnificator.addToBlacklist(aEvent.Ore);
                                        }
                                        break;
                                    case gearGt:
                                        GT_OreDictUnificator.registerOre(OrePrefixes.gear, aMaterial, aEvent.Ore);
                                        break;
                                    case stick:
                                        if (!GT_RecipeRegistrator.sRodMaterialList.contains(aMaterial)) {
                                            GT_RecipeRegistrator.sRodMaterialList.add(aMaterial);
                                        }
                                        if (aMaterial == Materials.Wood) {
                                            GT_OreDictUnificator.addToBlacklist(aEvent.Ore);
                                        }
                                        if ((aMaterial == Materials.Tin) || (aMaterial == Materials.Lead) || (aMaterial == Materials.SolderingAlloy)) {
                                            GT_OreDictUnificator.registerOre(ToolDictNames.craftingToolSolderingMetal, aEvent.Ore);
                                        }
                                        break;
                                    case dust:
                                        if (aMaterial == Materials.Salt) {
                                            GT_OreDictUnificator.registerOre("itemSalt", aEvent.Ore);
                                        }
                                        if (aMaterial == Materials.Wood) {
                                            GT_OreDictUnificator.registerOre("pulpWood", aEvent.Ore);
                                        }
                                        if (aMaterial == Materials.Wheat) {
                                            GT_OreDictUnificator.registerOre("foodFlour", aEvent.Ore);
                                        }
                                        if (aMaterial == Materials.Lapis) {
                                            GT_OreDictUnificator.registerOre(Dyes.dyeBlue, aEvent.Ore);
                                        }
                                        if (aMaterial == Materials.Lazurite) {
                                            GT_OreDictUnificator.registerOre(Dyes.dyeCyan, aEvent.Ore);
                                        }
                                        if (aMaterial == Materials.Sodalite) {
                                            GT_OreDictUnificator.registerOre(Dyes.dyeBlue, aEvent.Ore);
                                        }
                                        if (aMaterial == Materials.Cocoa) {
                                            GT_OreDictUnificator.registerOre(Dyes.dyeBrown, aEvent.Ore);
                                            GT_OreDictUnificator.registerOre("foodCocoapowder", aEvent.Ore);
                                        }
                                        if (aMaterial == Materials.Coffee) {
                                            GT_OreDictUnificator.registerOre(Dyes.dyeBrown, aEvent.Ore);
                                        }
                                        if (aMaterial == Materials.BrownLimonite) {
                                            GT_OreDictUnificator.registerOre(Dyes.dyeBrown, aEvent.Ore);
                                        }
                                        if (aMaterial == Materials.YellowLimonite) {
                                            GT_OreDictUnificator.registerOre(Dyes.dyeYellow, aEvent.Ore);
                                        }
                                        break;
                                    case ingot:
                                        if (aMaterial == Materials.Rubber) {
                                            GT_OreDictUnificator.registerOre("itemRubber", aEvent.Ore);
                                        }
                                        if (aMaterial == Materials.FierySteel) {
                                            GT_OreDictUnificator.registerOre("fieryIngot", aEvent.Ore);
                                        }
                                        if (aMaterial == Materials.IronWood) {
                                            GT_OreDictUnificator.registerOre("ironwood", aEvent.Ore);
                                        }
                                        if (aMaterial == Materials.Steeleaf) {
                                            GT_OreDictUnificator.registerOre("steeleaf", aEvent.Ore);
                                        }
                                        if (aMaterial == Materials.Knightmetal) {
                                            GT_OreDictUnificator.registerOre("knightmetal", aEvent.Ore);
                                        }
                                        if ((aMaterial == Materials.Brass) && (aEvent.Ore.getItemDamage() == 2)
                                                && (aEvent.Ore.getUnlocalizedName().equals("item.ingotBrass"))
                                                && (new ItemStack(aEvent.Ore.getItem(), 1, 0).getUnlocalizedName().contains("red"))) {
                                            GT_OreDictUnificator.set(OrePrefixes.ingot, Materials.RedAlloy, new ItemStack(aEvent.Ore.getItem(), 1, 0));
                                            GT_OreDictUnificator.set(OrePrefixes.ingot, Materials.BlueAlloy, new ItemStack(aEvent.Ore.getItem(), 1, 1));
                                            GT_OreDictUnificator.set(OrePrefixes.ingot, Materials.Brass, new ItemStack(aEvent.Ore.getItem(), 1, 2));
                                            if (!mDisableIC2Cables) {
                                                GT_Values.RA.addWiremillRecipe(GT_ModHandler.getIC2Item("copperCableItem", 3L), new ItemStack(aEvent.Ore.getItem(), 1,
                                                        8), 400, 1);
                                                GT_Values.RA.addWiremillRecipe(GT_ModHandler.getIC2Item("ironCableItem", 6L),
                                                        new ItemStack(aEvent.Ore.getItem(), 1, 9), 400, 2);
                                            }
                                            GT_Values.RA.addCutterRecipe(new ItemStack(aEvent.Ore.getItem(), 1, 3), new ItemStack(aEvent.Ore.getItem(), 16, 4),
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
                                    if (aEvent.Name.endsWith(tDye.name().replaceFirst("dye", ""))) {
                                        GT_OreDictUnificator.addToBlacklist(aEvent.Ore);
                                        GT_Log.ore.println(tModToName + " Oh man, why the fuck would anyone need a OreDictified Color for this, that is even too much for GregTech... do not report this, this is just a random Comment about how ridiculous this is.");
                                        return;
                                    }
                                }
//								System.out.println("Material Name: "+aEvent.Name+ " !!!Unknown Material detected!!! Please report to GregTech Intergalactical for additional compatiblity. This is not an Error, an Issue nor a Lag Source, it is just an Information, which you should pass to me.");
//								GT_Log.ore.println(tModToName + " uses an unknown Material. Report this to GregTech.");
                                return;
                            }
                        } else {
                            aPrefix.add(GT_Utility.copyAmount(1L, new Object[]{aEvent.Ore}));
                        }
                    }
                } else if (aPrefix.mIsSelfReferencing) {
                    aPrefix.add(GT_Utility.copyAmount(1L, new Object[]{aEvent.Ore}));
                } else {
                    GT_Log.ore.println(tModToName + " uses a Prefix as full OreDict Name, and is therefor invalid.");
                    aEvent.Ore.setStackDisplayName("Invalid OreDictionary Tag");
                    return;
                }
                switch (aPrefix) {
                    case dye:
                        if (GT_Utility.isStringValid(tName)) {
                            GT_OreDictUnificator.registerOre(OrePrefixes.dye, aEvent.Ore);
                        }
                        break;
                    case stoneSmooth:
                        GT_OreDictUnificator.registerOre("stone", aEvent.Ore);
                        break;
                    case stoneCobble:
                        GT_OreDictUnificator.registerOre("cobblestone", aEvent.Ore);
                        break;
                    case plank:
                        if (tName.equals("Wood")) {
                            GT_OreDictUnificator.addItemData(aEvent.Ore, new ItemData(Materials.Wood, 3628800L, new MaterialStack[0]));
                        }
                        break;
                    case slab:
                        if (tName.equals("Wood")) {
                            GT_OreDictUnificator.addItemData(aEvent.Ore, new ItemData(Materials.Wood, 1814400L, new MaterialStack[0]));
                        }
                        break;
                    case sheet:
                        if (tName.equals("Plastic")) {
                            GT_OreDictUnificator.registerOre(OrePrefixes.plate, Materials.Plastic, aEvent.Ore);
                        }
                        if (tName.equals("Rubber")) {
                            GT_OreDictUnificator.registerOre(OrePrefixes.plate, Materials.Rubber, aEvent.Ore);
                        }
                        break;
                    case crafting:
                        if (tName.equals("ToolSolderingMetal")) {
                            GregTech_API.registerSolderingMetal(aEvent.Ore);
                        }
                        if (tName.equals("IndustrialDiamond")) {
                            GT_OreDictUnificator.addToBlacklist(aEvent.Ore);
                        }
                        if (tName.equals("WireCopper")) {
                            GT_OreDictUnificator.registerOre(OrePrefixes.wire, Materials.Copper, aEvent.Ore);
                        }
                        break;
                    case wood:
                        if (tName.equals("Rubber")) {
                            GT_OreDictUnificator.registerOre("logRubber", aEvent.Ore);
                        }
                        break;
                    case food:
                        if (tName.equals("Cocoapowder")) {
                            GT_OreDictUnificator.registerOre(OrePrefixes.dust, Materials.Cocoa, aEvent.Ore);
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
        if ((aFluidEvent.data.filledContainer.getItem() == Items.potionitem) && (aFluidEvent.data.filledContainer.getItemDamage() == 0)) {
            aFluidEvent.data.fluid.amount = 0;
        }
        GT_OreDictUnificator.addToBlacklist(aFluidEvent.data.emptyContainer);
        GT_OreDictUnificator.addToBlacklist(aFluidEvent.data.filledContainer);
        GT_Utility.addFluidContainerData(aFluidEvent.data);
    }

    @SubscribeEvent
    public void onServerTickEvent(TickEvent.ServerTickEvent aEvent) {
    }

    @SubscribeEvent
    public void onWorldTickEvent(TickEvent.WorldTickEvent aEvent) {
        if (aEvent.side.isServer()) {
            if (this.mUniverse == null) {
                this.mUniverse = aEvent.world;
            }
            if (this.isFirstServerWorldTick) {
                File tSaveDiretory = getSaveDirectory();
                if (tSaveDiretory != null) {
                    this.isFirstServerWorldTick = false;
                    for (IMetaTileEntity tMetaTileEntity : GregTech_API.METATILEENTITIES) {
                        try {
                            if (tMetaTileEntity != null) {
                                tMetaTileEntity.onWorldLoad(tSaveDiretory);
                            }
                        } catch (Throwable e) {
                            e.printStackTrace(GT_Log.err);
                        }
                    }
                }
            }
            if ((aEvent.world.getTotalWorldTime() % 100L == 0L) && ((this.mItemDespawnTime != 6000) || (this.mMaxEqualEntitiesAtOneSpot > 0))) {
                for (int i = 0; i < aEvent.world.loadedEntityList.size(); i++) {
                    if ((aEvent.world.loadedEntityList.get(i) instanceof Entity)) {
                        Entity tEntity = (Entity) aEvent.world.loadedEntityList.get(i);
                        if (((tEntity instanceof EntityItem)) && (this.mItemDespawnTime != 6000) && (((EntityItem) tEntity).lifespan == 6000)) {
                            ((EntityItem) tEntity).lifespan = this.mItemDespawnTime;
                        } else if (((tEntity instanceof EntityLivingBase)) && (this.mMaxEqualEntitiesAtOneSpot > 0) && (!(tEntity instanceof EntityPlayer))
                                && (((EntityLivingBase) tEntity).canBePushed()) && (((EntityLivingBase) tEntity).getHealth() > 0.0F)) {
                            List tList = tEntity.worldObj.getEntitiesWithinAABBExcludingEntity(tEntity,
                                    tEntity.boundingBox.expand(0.20000000298023224D, 0.0D, 0.20000000298023224D));
                            Class tClass = tEntity.getClass();
                            int tEntityCount = 1;
                            if (tList != null) {
                                for (int j = 0; j < tList.size(); j++) {
                                    if ((tList.get(j) != null) && (tList.get(j).getClass() == tClass)) {
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
    }

    @SubscribeEvent
    public void onPlayerTickEventServer(TickEvent.PlayerTickEvent aEvent) {
        if ((!aEvent.player.isDead) && (aEvent.phase == TickEvent.Phase.END) && (aEvent.side.isServer())) {
            if ((aEvent.player.ticksExisted % 200 == 0) && (aEvent.player.capabilities.allowEdit) && (!aEvent.player.capabilities.isCreativeMode)
                    && (this.mSurvivalIntoAdventure)) {
                aEvent.player.setGameType(GameType.ADVENTURE);
                aEvent.player.capabilities.allowEdit = false;
                if (this.mAxeWhenAdventure) {
                    GT_Utility.sendChatToPlayer(aEvent.player, "It's dangerous to go alone! Take this.");
                    aEvent.player.worldObj.spawnEntityInWorld(new EntityItem(aEvent.player.worldObj, aEvent.player.posX, aEvent.player.posY,
                            aEvent.player.posZ, GT_MetaGenerated_Tool_01.INSTANCE.getToolWithStats(6, 1, Materials.Flint, Materials.Wood, null)));
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
    		int ID = aID-1000;
    		switch(ID){
    		case 0:
    			return new ContainerBasicArmor(aPlayer, new InventoryArmor(ModularArmor_Item.class, aPlayer.getCurrentEquippedItem()));
    		case 1:
    			return new ContainerElectricArmor1(aPlayer, new InventoryArmor(ModularArmor_Item.class, aPlayer.getCurrentEquippedItem()));
    		case 2:
    			return new ContainerElectricArmor1(aPlayer, new InventoryArmor(ModularArmor_Item.class, aPlayer.getCurrentEquippedItem()));
    		default:
    			return getRightItem(aPlayer, ID);
    		}
    	}
        TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
        if ((tTileEntity instanceof IGregTechTileEntity)) {
            IMetaTileEntity tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity();
            if (tMetaTileEntity != null) {
                return tMetaTileEntity.getServerGUI(aID, aPlayer.inventory, (IGregTechTileEntity) tTileEntity);
            }
        }
        return null;
    }

	public Object getRightItem(EntityPlayer player, int ID){
		ItemStack mStack = player.getEquipmentInSlot(ID/100);
		if(mStack==null||!(mStack.getItem() instanceof ModularArmor_Item))return null;

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
    			return new GuiModularArmor(new ContainerBasicArmor(aPlayer, new InventoryArmor(ModularArmor_Item.class, aPlayer.getCurrentEquippedItem())), aPlayer);
    		case 1:
    			return new GuiElectricArmor1(new ContainerElectricArmor1(aPlayer, new InventoryArmor(ModularArmor_Item.class, aPlayer.getCurrentEquippedItem())), aPlayer);
    		case 2:
    			return new GuiElectricArmor1(new ContainerElectricArmor1(aPlayer, new InventoryArmor(ModularArmor_Item.class, aPlayer.getCurrentEquippedItem())), aPlayer);
    		default:
    			return getRightItemGui(aPlayer, ID);
    		}
    	}
        TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
        if ((tTileEntity instanceof IGregTechTileEntity)) {
            IMetaTileEntity tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity();
            if (tMetaTileEntity != null) {
                return tMetaTileEntity.getClientGUI(aID, aPlayer.inventory, (IGregTechTileEntity) tTileEntity);
            }
        }
        return null;
    }

	public Object getRightItemGui(EntityPlayer player, int ID){
		ItemStack mStack = player.getEquipmentInSlot(ID/100);
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
        short rFuelValue = 0;
        if ((aFuel.getItem() instanceof GT_MetaGenerated_Item)) {
            Short tFuelValue = (Short) ((GT_MetaGenerated_Item) aFuel.getItem()).mBurnValues.get(Short.valueOf((short) aFuel.getItemDamage()));
            if (tFuelValue != null) {
                rFuelValue = (short) Math.max(rFuelValue, tFuelValue.shortValue());
            }
        }
        NBTTagCompound tNBT = aFuel.getTagCompound();
        if (tNBT != null) {
            short tValue = tNBT.getShort("GT.ItemFuelValue");
            rFuelValue = (short) Math.max(rFuelValue, tValue);
        }
        if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "gemSodium")) {
            rFuelValue = (short) Math.max(rFuelValue, 4000);
        }
        if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "crushedSodium")) {
            rFuelValue = (short) Math.max(rFuelValue, 4000);
        }
        if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "dustImpureSodium")) {
            rFuelValue = (short) Math.max(rFuelValue, 4000);
        }
        if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "dustSodium")) {
            rFuelValue = (short) Math.max(rFuelValue, 4000);
        }
        if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "dustSmallSodium")) {
            rFuelValue = (short) Math.max(rFuelValue, 1000);
        }
        if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "dustTinySodium")) {
            rFuelValue = (short) Math.max(rFuelValue, 444);
        }
        if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "gemLithium")) {
            rFuelValue = (short) Math.max(rFuelValue, 6000);
        }
        if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "crushedLithium")) {
            rFuelValue = (short) Math.max(rFuelValue, 6000);
        }
        if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "dustImpureLithium")) {
            rFuelValue = (short) Math.max(rFuelValue, 6000);
        }
        if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "dustLithium")) {
            rFuelValue = (short) Math.max(rFuelValue, 6000);
        }
        if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "dustSmallLithium")) {
            rFuelValue = (short) Math.max(rFuelValue, 2000);
        }
        if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "dustTinyLithium")) {
            rFuelValue = (short) Math.max(rFuelValue, 888);
        }
        if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "gemCaesium")) {
            rFuelValue = (short) Math.max(rFuelValue, 6000);
        }
        if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "crushedCaesium")) {
            rFuelValue = (short) Math.max(rFuelValue, 6000);
        }
        if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "dustImpureCaesium")) {
            rFuelValue = (short) Math.max(rFuelValue, 6000);
        }
        if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "dustCaesium")) {
            rFuelValue = (short) Math.max(rFuelValue, 6000);
        }
        if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "dustSmallCaesium")) {
            rFuelValue = (short) Math.max(rFuelValue, 2000);
        }
        if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "dustTinyCaesium")) {
            rFuelValue = (short) Math.max(rFuelValue, 888);
        }
        if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "gemLignite")) {
            rFuelValue = (short) Math.max(rFuelValue, 1200);
        }
        if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "crushedLignite")) {
            rFuelValue = (short) Math.max(rFuelValue, 1200);
        }
        if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "dustImpureLignite")) {
            rFuelValue = (short) Math.max(rFuelValue, 1200);
        }
        if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "dustLignite")) {
            rFuelValue = (short) Math.max(rFuelValue, 1200);
        }
        if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "dustSmallLignite")) {
            rFuelValue = (short) Math.max(rFuelValue, 375);
        }
        if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "dustTinyLignite")) {
            rFuelValue = (short) Math.max(rFuelValue, 166);
        }
        if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "gemCoal")) {
            rFuelValue = (short) Math.max(rFuelValue, 1600);
        }
        if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "crushedCoal")) {
            rFuelValue = (short) Math.max(rFuelValue, 1600);
        }
        if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "dustImpureCoal")) {
            rFuelValue = (short) Math.max(rFuelValue, 1600);
        }
        if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "dustCoal")) {
            rFuelValue = (short) Math.max(rFuelValue, 1600);
        }
        if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "dustSmallCoal")) {
            rFuelValue = (short) Math.max(rFuelValue, 400);
        }
        if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "dustTinyCoal")) {
            rFuelValue = (short) Math.max(rFuelValue, 177);
        }
        if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "gemCharcoal")) {
            rFuelValue = (short) Math.max(rFuelValue, 1600);
        }
        if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "crushedCharcoal")) {
            rFuelValue = (short) Math.max(rFuelValue, 1600);
        }
        if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "dustImpureCharcoal")) {
            rFuelValue = (short) Math.max(rFuelValue, 1600);
        }
        if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "dustCharcoal")) {
            rFuelValue = (short) Math.max(rFuelValue, 1600);
        }
        if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "dustSmallCharcoal")) {
            rFuelValue = (short) Math.max(rFuelValue, 400);
        }
        if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "dustTinyCharcoal")) {
            rFuelValue = (short) Math.max(rFuelValue, 177);
        }
        if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "dustWood")) {
            rFuelValue = (short) Math.max(rFuelValue, 100);
        }
        if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "dustSmallWood")) {
            rFuelValue = (short) Math.max(rFuelValue, 25);
        }
        if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "dustTinyWood")) {
            rFuelValue = (short) Math.max(rFuelValue, 11);
        }
        if (GT_OreDictUnificator.isItemStackInstanceOf(aFuel, "plateWood")) {
            rFuelValue = (short) Math.min(rFuelValue, 300);
        }
        if (GT_Utility.areStacksEqual(aFuel, new ItemStack(Blocks.wooden_button, 1))) {
            rFuelValue = (short) Math.max(rFuelValue, 150);
        }
        if (GT_Utility.areStacksEqual(aFuel, new ItemStack(Blocks.ladder, 1))) {
            rFuelValue = (short) Math.max(rFuelValue, 100);
        }
        if (GT_Utility.areStacksEqual(aFuel, new ItemStack(Items.sign, 1))) {
            rFuelValue = (short) Math.max(rFuelValue, 600);
        }
        if (GT_Utility.areStacksEqual(aFuel, new ItemStack(Items.wooden_door, 1))) {
            rFuelValue = (short) Math.max(rFuelValue, 600);
        }
        return rFuelValue;
    }

    public Fluid addAutogeneratedMoltenFluid(Materials aMaterial) {
        return addFluid("molten." + aMaterial.name().toLowerCase(), "molten.autogenerated", "Molten " + aMaterial.mDefaultLocalName, aMaterial,
                aMaterial.mMoltenRGBa, 4, aMaterial.mMeltingPoint <= 0 ? 1000 : aMaterial.mMeltingPoint, null, null, 0);
    }

    public Fluid addAutogeneratedPlasmaFluid(Materials aMaterial) {
        return addFluid("plasma." + aMaterial.name().toLowerCase(), "plasma.autogenerated", aMaterial.mDefaultLocalName + " Plasma", aMaterial,
                aMaterial.mMoltenRGBa, 3, 10000, GT_OreDictUnificator.get(OrePrefixes.cellPlasma, aMaterial, 1L), ItemList.Cell_Empty.get(1L, new Object[0]),
                1000);
    }

    public Fluid addFluid(String aName, String aLocalized, Materials aMaterial, int aState, int aTemperatureK) {
        return addFluid(aName, aLocalized, aMaterial, aState, aTemperatureK, null, null, 0);
    }

    public Fluid addFluid(String aName, String aLocalized, Materials aMaterial, int aState, int aTemperatureK, ItemStack aFullContainer,
                          ItemStack aEmptyContainer, int aFluidAmount) {
        return addFluid(aName, aName.toLowerCase(), aLocalized, aMaterial, null, aState, aTemperatureK, aFullContainer, aEmptyContainer, aFluidAmount);
    }

    public Fluid addFluid(String aName, String aTexture, String aLocalized, Materials aMaterial, short[] aRGBa, int aState, int aTemperatureK,
                          ItemStack aFullContainer, ItemStack aEmptyContainer, int aFluidAmount) {
        aName = aName.toLowerCase();
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
        if (rFluid.getTemperature() == new Fluid("test").getTemperature()) {
            rFluid.setTemperature(aTemperatureK);
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
        if ((aFullContainer != null) && (aEmptyContainer != null)
                && (!FluidContainerRegistry.registerFluidContainer(new FluidStack(rFluid, aFluidAmount), aFullContainer, aEmptyContainer))) {
            GT_Values.RA.addFluidCannerRecipe(aFullContainer, GT_Utility.getContainerItem(aFullContainer, false), null, new FluidStack(rFluid, aFluidAmount));
        }
        return rFluid;
    }

    public File getSaveDirectory() {
        return this.mUniverse == null ? null : this.mUniverse.getSaveHandler().getWorldDirectory();
    }

    public void registerUnificationEntries() {
        GregTech_API.sUnification.mConfig.save();
        GregTech_API.sUnification.mConfig.load();
        GT_OreDictUnificator.resetUnificationEntries();
        for (OreDictEventContainer tOre : this.mEvents) {
            if ((!(tOre.mEvent.Ore.getItem() instanceof GT_MetaGenerated_Item)) && (tOre.mPrefix != null) && (tOre.mPrefix.mIsUnificatable)
                    && (tOre.mMaterial != null)) {
                if (tOre.mModID != null && tOre.mModID.toLowerCase().equals("enderio") && tOre.mPrefix == OrePrefixes.ingot && tOre.mMaterial == Materials.DarkSteel) {
                    GT_OreDictUnificator.addAssociation(tOre.mPrefix, tOre.mMaterial, tOre.mEvent.Ore, false);
                    GT_OreDictUnificator.set(tOre.mPrefix, tOre.mMaterial, tOre.mEvent.Ore, (tOre.mModID != null) && (GregTech_API.sUnification.get(ConfigCategories.specialunificationtargets + "." + tOre.mModID, tOre.mEvent.Name, true)), true);
                } else if (tOre.mModID != null && tOre.mModID.toLowerCase().equals("thermalfoundation") && tOre.mPrefix == OrePrefixes.dust && tOre.mMaterial == Materials.Blizz) {
                    GT_OreDictUnificator.addAssociation(tOre.mPrefix, tOre.mMaterial, tOre.mEvent.Ore, false);
                    GT_OreDictUnificator.set(tOre.mPrefix, tOre.mMaterial, tOre.mEvent.Ore, (tOre.mModID != null) && (GregTech_API.sUnification.get(ConfigCategories.specialunificationtargets + "." + tOre.mModID, tOre.mEvent.Name, true)), true);
                } else if (tOre.mModID != null && tOre.mModID.toLowerCase().equals("thermalfoundation") && tOre.mPrefix == OrePrefixes.dust && tOre.mMaterial == Materials.Pyrotheum) {
                    GT_OreDictUnificator.addAssociation(tOre.mPrefix, tOre.mMaterial, tOre.mEvent.Ore, false);
                    GT_OreDictUnificator.set(tOre.mPrefix, tOre.mMaterial, tOre.mEvent.Ore, (tOre.mModID != null) && (GregTech_API.sUnification.get(ConfigCategories.specialunificationtargets + "." + tOre.mModID, tOre.mEvent.Name, true)), true);
                } else if (tOre.mModID != null && tOre.mModID.toLowerCase().equals("arsmagica2") && tOre.mPrefix == OrePrefixes.dust && tOre.mMaterial == Materials.Vinteum) {
                    GT_OreDictUnificator.addAssociation(tOre.mPrefix, tOre.mMaterial, tOre.mEvent.Ore, false);
                    GT_OreDictUnificator.set(tOre.mPrefix, tOre.mMaterial, tOre.mEvent.Ore, (tOre.mModID != null) && (GregTech_API.sUnification.get(ConfigCategories.specialunificationtargets + "." + tOre.mModID, tOre.mEvent.Name, true)), true);
                } else if (tOre.mModID != null && tOre.mModID.toLowerCase().equals("arsmagica2") && tOre.mPrefix == OrePrefixes.gem && tOre.mMaterial == Materials.BlueTopaz) {
                    GT_OreDictUnificator.addAssociation(tOre.mPrefix, tOre.mMaterial, tOre.mEvent.Ore, false);
                    GT_OreDictUnificator.set(tOre.mPrefix, tOre.mMaterial, tOre.mEvent.Ore, (tOre.mModID != null) && (GregTech_API.sUnification.get(ConfigCategories.specialunificationtargets + "." + tOre.mModID, tOre.mEvent.Name, true)), true);
                } else if (tOre.mModID != null && tOre.mModID.toLowerCase().equals("arsmagica2") && tOre.mPrefix == OrePrefixes.gem && tOre.mMaterial == Materials.Chimerite) {
                    GT_OreDictUnificator.addAssociation(tOre.mPrefix, tOre.mMaterial, tOre.mEvent.Ore, false);
                    GT_OreDictUnificator.set(tOre.mPrefix, tOre.mMaterial, tOre.mEvent.Ore, (tOre.mModID != null) && (GregTech_API.sUnification.get(ConfigCategories.specialunificationtargets + "." + tOre.mModID, tOre.mEvent.Name, true)), true);
                } else if (tOre.mModID != null && tOre.mModID.toLowerCase().equals("arsmagica2") && tOre.mPrefix == OrePrefixes.gem && tOre.mMaterial == Materials.Moonstone) {
                    GT_OreDictUnificator.addAssociation(tOre.mPrefix, tOre.mMaterial, tOre.mEvent.Ore, false);
                    GT_OreDictUnificator.set(tOre.mPrefix, tOre.mMaterial, tOre.mEvent.Ore, (tOre.mModID != null) && (GregTech_API.sUnification.get(ConfigCategories.specialunificationtargets + "." + tOre.mModID, tOre.mEvent.Name, true)), true);
                } else if (tOre.mModID != null && tOre.mModID.toLowerCase().equals("arsmagica2") && tOre.mPrefix == OrePrefixes.gem && tOre.mMaterial == Materials.Sunstone) {
                    GT_OreDictUnificator.addAssociation(tOre.mPrefix, tOre.mMaterial, tOre.mEvent.Ore, false);
                    GT_OreDictUnificator.set(tOre.mPrefix, tOre.mMaterial, tOre.mEvent.Ore, (tOre.mModID != null) && (GregTech_API.sUnification.get(ConfigCategories.specialunificationtargets + "." + tOre.mModID, tOre.mEvent.Name, true)), true);
                } else if (tOre.mModID != null && tOre.mModID.toLowerCase().equals("rotarycraft") && tOre.mPrefix == OrePrefixes.ingot && tOre.mMaterial == Materials.HSLA) {
                    GT_OreDictUnificator.addAssociation(tOre.mPrefix, tOre.mMaterial, tOre.mEvent.Ore, false);
                    GT_OreDictUnificator.set(tOre.mPrefix, tOre.mMaterial, tOre.mEvent.Ore, (tOre.mModID != null) && (GregTech_API.sUnification.get(ConfigCategories.specialunificationtargets + "." + tOre.mModID, tOre.mEvent.Name, true)), true);
                } else if (tOre.mModID != null && tOre.mModID.toLowerCase().equals("appliedenergistics2") && tOre.mPrefix == OrePrefixes.gem && tOre.mMaterial == Materials.CertusQuartz) {
                    GT_OreDictUnificator.addAssociation(tOre.mPrefix, tOre.mMaterial, tOre.mEvent.Ore, false);
                    GT_OreDictUnificator.set(tOre.mPrefix, tOre.mMaterial, tOre.mEvent.Ore, (tOre.mModID != null) && (GregTech_API.sUnification.get(ConfigCategories.specialunificationtargets + "." + tOre.mModID, tOre.mEvent.Name, true)), true);
                } else if (tOre.mModID != null && tOre.mModID.toLowerCase().equals("appliedenergistics2") && tOre.mPrefix == OrePrefixes.dust && tOre.mMaterial == Materials.CertusQuartz) {
                    GT_OreDictUnificator.addAssociation(tOre.mPrefix, tOre.mMaterial, tOre.mEvent.Ore, false);
                    GT_OreDictUnificator.set(tOre.mPrefix, tOre.mMaterial, tOre.mEvent.Ore, (tOre.mModID != null) && (GregTech_API.sUnification.get(ConfigCategories.specialunificationtargets + "." + tOre.mModID, tOre.mEvent.Name, true)), true);
                } else if (tOre.mModID != null && tOre.mModID.toLowerCase().equals(GT_Values.MOD_ID_TC) && tOre.mPrefix == OrePrefixes.block && tOre.mMaterial == Materials.Thaumium) {
                    GT_OreDictUnificator.addAssociation(tOre.mPrefix, tOre.mMaterial, tOre.mEvent.Ore, false);
                    GT_OreDictUnificator.set(tOre.mPrefix, tOre.mMaterial, tOre.mEvent.Ore, (tOre.mModID != null) && (GregTech_API.sUnification.get(ConfigCategories.specialunificationtargets + "." + tOre.mModID, tOre.mEvent.Name, true)), true);
                } else if (GT_OreDictUnificator.isBlacklisted(tOre.mEvent.Ore)) {
                    GT_OreDictUnificator.addAssociation(tOre.mPrefix, tOre.mMaterial, tOre.mEvent.Ore, true);
                } else {
                    GT_OreDictUnificator.addAssociation(tOre.mPrefix, tOre.mMaterial, tOre.mEvent.Ore, false);
                    GT_OreDictUnificator.set(tOre.mPrefix, tOre.mMaterial, tOre.mEvent.Ore, (tOre.mModID != null) && (GregTech_API.sUnification.get(ConfigCategories.specialunificationtargets + "." + tOre.mModID, tOre.mEvent.Name, false)), true);
                }
            }
        }
        for (OreDictEventContainer tOre : this.mEvents) {
            if (((tOre.mEvent.Ore.getItem() instanceof GT_MetaGenerated_Item)) && (tOre.mPrefix != null) && (tOre.mPrefix.mIsUnificatable)
                    && (tOre.mMaterial != null)) {
                if (GT_OreDictUnificator.isBlacklisted(tOre.mEvent.Ore)) {
                    GT_OreDictUnificator.addAssociation(tOre.mPrefix, tOre.mMaterial, tOre.mEvent.Ore, true);
                } else {
                    GT_OreDictUnificator.addAssociation(tOre.mPrefix, tOre.mMaterial, tOre.mEvent.Ore, false);
                    GT_OreDictUnificator.set(tOre.mPrefix, tOre.mMaterial, tOre.mEvent.Ore, (tOre.mModID != null) &&
                            (GregTech_API.sUnification.get(ConfigCategories.specialunificationtargets + "." + tOre.mModID, tOre.mEvent.Name, false)), true);
                }
            }
        }
        GregTech_API.sUnificationEntriesRegistered = true;
        GregTech_API.sUnification.mConfig.save();
        GT_Recipe.reInit();
    }

    public void activateOreDictHandler() {
        this.mOreDictActivated = true;
        OreDictEventContainer tEvent;
        for (Iterator i$ = this.mEvents.iterator(); i$.hasNext(); registerRecipes(tEvent)) {
            tEvent = (OreDictEventContainer) i$.next();
        }
    }

    public static final HashMap<ChunkPosition, int[]>  chunkData = new HashMap<ChunkPosition, int[]>(5000);

    @SubscribeEvent
    public void handleChunkSaveEvent(ChunkDataEvent.Save event)
    {
    	ChunkPosition tPos = new ChunkPosition(event.getChunk().xPosition,1,event.getChunk().zPosition);
    	if(chunkData.containsKey(tPos)){
    		int[] tInts = chunkData.get(tPos);
    		if(tInts.length>0){event.getData().setInteger("GTOIL", tInts[0]);}}
    }

    @SubscribeEvent
    public void handleChunkLoadEvent(ChunkDataEvent.Load event)
    {
    	int tOil = -1;
    	if(event.getData().hasKey("GTOIL")){
    	tOil = event.getData().getInteger("GTOIL");}
    	ChunkPosition tPos = new ChunkPosition(event.getChunk().xPosition,1,event.getChunk().zPosition);
    	if(chunkData.containsKey(tPos)){
    		chunkData.remove(tPos);}
    	chunkData.put(tPos, new int[]{ tOil});
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
