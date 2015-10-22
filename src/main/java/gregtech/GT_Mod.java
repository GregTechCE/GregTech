package gregtech;

import cpw.mods.fml.common.*;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.registry.EntityRegistry;
import forestry.factory.recipes.ISqueezerRecipe;
import forestry.factory.tiles.TileCentrifuge;
import forestry.factory.tiles.TileSqueezer;
import gregtech.api.GregTech_API;
import gregtech.api.enchants.Enchantment_EnderDamage;
import gregtech.api.enchants.Enchantment_Radioactivity;
import gregtech.api.enums.*;
import gregtech.api.interfaces.internal.IGT_Mod;
import gregtech.api.objects.ItemData;
import gregtech.api.objects.MaterialStack;
import gregtech.api.util.*;
import gregtech.common.GT_DummyWorld;
import gregtech.common.GT_Network;
import gregtech.common.GT_Proxy;
import gregtech.common.GT_RecipeAdder;
import gregtech.common.entities.GT_Entity_Arrow;
import gregtech.common.entities.GT_Entity_Arrow_Potion;
import gregtech.common.items.behaviors.Behaviour_DataOrb;
import gregtech.loaders.load.GT_CoverBehaviorLoader;
import gregtech.loaders.load.GT_FuelLoader;
import gregtech.loaders.load.GT_ItemIterator;
import gregtech.loaders.load.GT_SonictronLoader;
import gregtech.loaders.misc.GT_Achievements;
import gregtech.loaders.misc.GT_CoverLoader;
import gregtech.loaders.postload.*;
import gregtech.loaders.preload.*;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeOutput;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;
import java.util.Map.Entry;

//import forestry.factory.gadgets.MachineCentrifuge;
//import forestry.factory.gadgets.MachineCentrifuge.RecipeManager;
//import forestry.factory.gadgets.MachineSqueezer;

@Mod(modid = "gregtech", name = "GregTech", version = "MC1710", useMetadata = false, dependencies = "required-after:IC2; after:Forestry; after:PFAAGeologica; after:Thaumcraft; after:Railcraft; after:appliedenergistics2; after:ThermalExpansion; after:TwilightForest; after:harvestcraft; after:magicalcrops; after:BuildCraft|Transport; after:BuildCraft|Silicon; after:BuildCraft|Factory; after:BuildCraft|Energy; after:BuildCraft|Core; after:BuildCraft|Builders; after:GalacticraftCore; after:GalacticraftMars; after:GalacticraftPlanets; after:ThermalExpansion|Transport; after:ThermalExpansion|Energy; after:ThermalExpansion|Factory; after:RedPowerCore; after:RedPowerBase; after:RedPowerMachine; after:RedPowerCompat; after:RedPowerWiring; after:RedPowerLogic; after:RedPowerLighting; after:RedPowerWorld; after:RedPowerControl;")
public class GT_Mod
        implements IGT_Mod {
    public static final int VERSION = 508;
    public static final int REQUIRED_IC2 = 624;
    @Mod.Instance("gregtech")
    public static GT_Mod instance;
    @SidedProxy(modId = "gregtech", clientSide = "gregtech.common.GT_Client", serverSide = "gregtech.common.GT_Server")
    public static GT_Proxy gregtechproxy;
    public static int MAX_IC2 = 2147483647;
    public static GT_Achievements achievements;

    static {
        if ((508 != GregTech_API.VERSION) || (508 != GT_ModHandler.VERSION) || (508 != GT_OreDictUnificator.VERSION) || (508 != GT_Recipe.VERSION) || (508 != GT_Utility.VERSION) || (508 != GT_RecipeRegistrator.VERSION) || (508 != Element.VERSION) || (508 != Materials.VERSION) || (508 != OrePrefixes.VERSION)) {
            throw new GT_ItsNotMyFaultException("One of your Mods included GregTech-API Files inside it's download, mention this to the Mod Author, who does this bad thing, and tell him/her to use reflection. I have added a Version check, to prevent Authors from breaking my Mod that way.");
        }
    }

    public GT_Mod() {
        try {
            Class.forName("ic2.core.IC2").getField("enableOreDictCircuit").set(null, Boolean.valueOf(true));
        } catch (Throwable e) {
        }
        try {
            Class.forName("ic2.core.IC2").getField("enableCraftingBucket").set(null, Boolean.valueOf(false));
        } catch (Throwable e) {
        }
        try {
            Class.forName("ic2.core.IC2").getField("enableEnergyInStorageBlockItems").set(null, Boolean.valueOf(false));
        } catch (Throwable e) {
        }
        GT_Values.GT = this;
        GT_Values.DW = new GT_DummyWorld();
        GT_Values.NW = new GT_Network();
        GregTech_API.sRecipeAdder = GT_Values.RA = new GT_RecipeAdder();

        Textures.BlockIcons.VOID.name();
        Textures.ItemIcons.VOID.name();
    }

    @Mod.EventHandler
    public void onPreLoad(FMLPreInitializationEvent aEvent) {
        if (GregTech_API.sPreloadStarted) {
            return;
        }
        for (Runnable tRunnable : GregTech_API.sBeforeGTPreload) {
            try {
                tRunnable.run();
            } catch (Throwable e) {
                e.printStackTrace(GT_Log.err);
            }
        }
        File tFile = new File(new File(aEvent.getModConfigurationDirectory(), "GregTech"), "GregTech.cfg");
        Configuration tMainConfig = new Configuration(tFile);
        tMainConfig.load();
        tFile = new File(new File(aEvent.getModConfigurationDirectory(), "GregTech"), "IDs.cfg");
        GT_Config.sConfigFileIDs = new Configuration(tFile);
        GT_Config.sConfigFileIDs.load();
        GT_Config.sConfigFileIDs.save();
        GregTech_API.sRecipeFile = new GT_Config(new Configuration(new File(new File(aEvent.getModConfigurationDirectory(), "GregTech"), "Recipes.cfg")));
        GregTech_API.sMachineFile = new GT_Config(new Configuration(new File(new File(aEvent.getModConfigurationDirectory(), "GregTech"), "MachineStats.cfg")));
        GregTech_API.sWorldgenFile = new GT_Config(new Configuration(new File(new File(aEvent.getModConfigurationDirectory(), "GregTech"), "WorldGeneration.cfg")));
        GregTech_API.sMaterialProperties = new GT_Config(new Configuration(new File(new File(aEvent.getModConfigurationDirectory(), "GregTech"), "MaterialProperties.cfg")));
        GregTech_API.sUnification = new GT_Config(new Configuration(new File(new File(aEvent.getModConfigurationDirectory(), "GregTech"), "Unification.cfg")));
        GregTech_API.sSpecialFile = new GT_Config(new Configuration(new File(new File(aEvent.getModConfigurationDirectory(), "GregTech"), "Other.cfg")));
        GregTech_API.sOPStuff = new GT_Config(new Configuration(new File(new File(aEvent.getModConfigurationDirectory(), "GregTech"), "OverpoweredStuff.cfg")));

        GregTech_API.sClientDataFile = new GT_Config(new Configuration(new File(aEvent.getModConfigurationDirectory().getParentFile(), "GregTech.cfg")));

        GT_Log.mLogFile = new File(aEvent.getModConfigurationDirectory().getParentFile(), "logs/GregTech.log");
        if (!GT_Log.mLogFile.exists()) {
            try {
                GT_Log.mLogFile.createNewFile();
            } catch (Throwable e) {
            }
        }
        try {
            GT_Log.out = GT_Log.err = new PrintStream(GT_Log.mLogFile);
        } catch (FileNotFoundException e) {
        }
        GT_Log.mOreDictLogFile = new File(aEvent.getModConfigurationDirectory().getParentFile(), "logs/OreDict.log");
        if (!GT_Log.mOreDictLogFile.exists()) {
            try {
                GT_Log.mOreDictLogFile.createNewFile();
            } catch (Throwable e) {
            }
        }
        if (tMainConfig.get("general", "LoggingPlayerActivity", true).getBoolean(true)) {
            GT_Log.mPlayerActivityLogFile = new File(aEvent.getModConfigurationDirectory().getParentFile(), "logs/PlayerActivity.log");
            if (!GT_Log.mPlayerActivityLogFile.exists()) {
                try {
                    GT_Log.mPlayerActivityLogFile.createNewFile();
                } catch (Throwable e) {
                }
            }
            try {
                GT_Log.pal = new PrintStream(GT_Log.mPlayerActivityLogFile);
            } catch (Throwable e) {
            }
        }
        try {
            List<String> tList = ((GT_Log.LogBuffer) GT_Log.ore).mBufferedOreDictLog;
            GT_Log.ore.println("******************************************************************************");
            GT_Log.ore.println("* This is the complete log of the GT5-Unofficial OreDictionary Handler. It   *");
            GT_Log.ore.println("* processes all OreDictionary entries and can sometimes cause errors. All    *");
            GT_Log.ore.println("* entries and errors are being logged. If you see an error please raise an   *");
            GT_Log.ore.println("* issue at https://github.com/Blood-Asp/GT5-Unofficial.                      *");
            GT_Log.ore.println("******************************************************************************");
            String tString;
            for (Iterator i$ = tList.iterator(); i$.hasNext(); GT_Log.ore.println(tString)) {
                tString = (String) i$.next();
            }
        } catch (Throwable e) {
        }
        gregtechproxy.onPreLoad();

        GT_Log.out.println("GT_Mod: Setting Configs");
        GT_Values.D1 = tMainConfig.get("general", "Debug", false).getBoolean(false);
        GT_Values.D2 = tMainConfig.get("general", "Debug2", false).getBoolean(false);

        GregTech_API.TICKS_FOR_LAG_AVERAGING = tMainConfig.get("general", "TicksForLagAveragingWithScanner", 25).getInt(25);
        GregTech_API.MILLISECOND_THRESHOLD_UNTIL_LAG_WARNING = tMainConfig.get("general", "MillisecondsPassedInGTTileEntityUntilLagWarning", 100).getInt(100);
        if (tMainConfig.get("general", "disable_STDOUT", false).getBoolean(false)) {
            System.out.close();
        }
        if (tMainConfig.get("general", "disable_STDERR", false).getBoolean(false)) {
            System.err.close();
        }
        GregTech_API.sMachineExplosions = tMainConfig.get("machines", "machines_explosion_damage", true).getBoolean(false);
        GregTech_API.sMachineFlammable = tMainConfig.get("machines", "machines_flammable", true).getBoolean(false);
        GregTech_API.sMachineNonWrenchExplosions = tMainConfig.get("machines", "explosions_on_nonwrenching", true).getBoolean(false);
        GregTech_API.sMachineWireFire = tMainConfig.get("machines", "wirefire_on_explosion", true).getBoolean(false);
        GregTech_API.sMachineFireExplosions = tMainConfig.get("machines", "fire_causes_explosions", true).getBoolean(false);
        GregTech_API.sMachineRainExplosions = tMainConfig.get("machines", "rain_causes_explosions", true).getBoolean(false);
        GregTech_API.sMachineThunderExplosions = tMainConfig.get("machines", "lightning_causes_explosions", true).getBoolean(false);
        GregTech_API.sConstantEnergy = tMainConfig.get("machines", "constant_need_of_energy", true).getBoolean(false);
        GregTech_API.sColoredGUI = tMainConfig.get("machines", "colored_guis_when_painted", true).getBoolean(false);

        GregTech_API.sTimber = tMainConfig.get("general", "timber_axe", false).getBoolean(false);
        GregTech_API.sDrinksAlwaysDrinkable = tMainConfig.get("general", "drinks_always_drinkable", false).getBoolean(false);
        GregTech_API.sDoShowAllItemsInCreative = tMainConfig.get("general", "show_all_metaitems_in_creative_and_NEI", false).getBoolean(false);
        GregTech_API.sMultiThreadedSounds = tMainConfig.get("general", "sound_multi_threading", false).getBoolean(false);
        for (Dyes tDye : Dyes.values()) {
            if ((tDye != Dyes._NULL) && (tDye.mIndex < 0)) {
                tDye.mRGBa[0] = ((short) Math.min(255, Math.max(0, GregTech_API.sClientDataFile.get("ColorModulation." + tDye, "R", tDye.mRGBa[0]))));
                tDye.mRGBa[1] = ((short) Math.min(255, Math.max(0, GregTech_API.sClientDataFile.get("ColorModulation." + tDye, "G", tDye.mRGBa[1]))));
                tDye.mRGBa[2] = ((short) Math.min(255, Math.max(0, GregTech_API.sClientDataFile.get("ColorModulation." + tDye, "B", tDye.mRGBa[2]))));
            }
        }
        gregtechproxy.mMaxEqualEntitiesAtOneSpot = tMainConfig.get("general", "MaxEqualEntitiesAtOneSpot", 3).getInt(3);
        gregtechproxy.mSkeletonsShootGTArrows = tMainConfig.get("general", "SkeletonsShootGTArrows", 16).getInt(16);
        gregtechproxy.mFlintChance = tMainConfig.get("general", "FlintAndSteelChance", 30).getInt(30);
        gregtechproxy.mItemDespawnTime = tMainConfig.get("general", "ItemDespawnTime", 6000).getInt(6000);
        gregtechproxy.mDisableVanillaOres = tMainConfig.get("general", "DisableVanillaOres", true).getBoolean(true);
        gregtechproxy.mNerfDustCrafting = tMainConfig.get("general", "NerfDustCrafting", true).getBoolean(true);
        gregtechproxy.mIncreaseDungeonLoot = tMainConfig.get("general", "IncreaseDungeonLoot", true).getBoolean(true);
        gregtechproxy.mAxeWhenAdventure = tMainConfig.get("general", "AdventureModeStartingAxe", true).getBoolean(true);
        gregtechproxy.mHardcoreCables = tMainConfig.get("general", "HardCoreCableLoss", false).getBoolean(false);
        gregtechproxy.mSurvivalIntoAdventure = tMainConfig.get("general", "forceAdventureMode", false).getBoolean(false);
        gregtechproxy.mHungerEffect = tMainConfig.get("general", "AFK_Hunger", false).getBoolean(false);
        gregtechproxy.mHardRock = tMainConfig.get("general", "harderstone", false).getBoolean(false);
        gregtechproxy.mInventoryUnification = tMainConfig.get("general", "InventoryUnification", true).getBoolean(true);
        gregtechproxy.mCraftingUnification = tMainConfig.get("general", "CraftingUnification", true).getBoolean(true);
        gregtechproxy.mNerfedWoodPlank = tMainConfig.get("general", "WoodNeedsSawForCrafting", true).getBoolean(true);
        gregtechproxy.mNerfedVanillaTools = tMainConfig.get("general", "smallerVanillaToolDurability", true).getBoolean(true);
        gregtechproxy.mSortToTheEnd = tMainConfig.get("general", "EnsureToBeLoadedLast", true).getBoolean(true);
        gregtechproxy.mDisableIC2Cables = tMainConfig.get("general", "DisableIC2Cables", false).getBoolean(false);
        gregtechproxy.mAchievements = tMainConfig.get("general", "EnableAchievements", true).getBoolean(true);
        gregtechproxy.mAE2Integration = tMainConfig.get("general", "EnableAE2Integration", Loader.isModLoaded("appliedenergistics2")).getBoolean(Loader.isModLoaded("appliedenergistics2"));


        GregTech_API.mOutputRF = GregTech_API.sOPStuff.get(ConfigCategories.general, "OutputRF", false);
        GregTech_API.mInputRF = GregTech_API.sOPStuff.get(ConfigCategories.general, "InputRF", false);
        GregTech_API.mEUtoRF = GregTech_API.sOPStuff.get(ConfigCategories.general, "100EUtoRF", 360);
        GregTech_API.mRFtoEU = GregTech_API.sOPStuff.get(ConfigCategories.general, "100RFtoEU", 20);
        GregTech_API.mRFExplosions = GregTech_API.sOPStuff.get(ConfigCategories.general, "RFExplosions", true);
        GregTech_API.meIOLoaded = Loader.isModLoaded("EnderIO");

        if (tMainConfig.get("general", "hardermobspawners", true).getBoolean(true)) {
            Blocks.mob_spawner.setHardness(500.0F).setResistance(6000000.0F);
        }
        gregtechproxy.mOnline = tMainConfig.get("general", "online", true).getBoolean(false);

        gregtechproxy.mUpgradeCount = Math.min(64, Math.max(1, tMainConfig.get("features", "UpgradeStacksize", 4).getInt()));
        for (OrePrefixes tPrefix : OrePrefixes.values()) {
            if (tPrefix.mIsUsedForOreProcessing) {
                tPrefix.mDefaultStackSize = ((byte) Math.min(64, Math.max(1, tMainConfig.get("features", "MaxOreStackSize", 64).getInt())));
            } else if (tPrefix == OrePrefixes.plank) {
                tPrefix.mDefaultStackSize = ((byte) Math.min(64, Math.max(16, tMainConfig.get("features", "MaxPlankStackSize", 64).getInt())));
            } else if ((tPrefix == OrePrefixes.wood) || (tPrefix == OrePrefixes.treeLeaves) || (tPrefix == OrePrefixes.treeSapling) || (tPrefix == OrePrefixes.log)) {
                tPrefix.mDefaultStackSize = ((byte) Math.min(64, Math.max(16, tMainConfig.get("features", "MaxLogStackSize", 64).getInt())));
            } else if (tPrefix.mIsUsedForBlocks) {
                tPrefix.mDefaultStackSize = ((byte) Math.min(64, Math.max(16, tMainConfig.get("features", "MaxOtherBlockStackSize", 64).getInt())));
            }
        }
        //GT_Config.troll = (Calendar.getInstance().get(2) + 1 == 4) && (Calendar.getInstance().get(5) >= 1) && (Calendar.getInstance().get(5) <= 2);

        Materials.init(GregTech_API.sMaterialProperties);

        GT_Log.out.println("GT_Mod: Saving Main Config");
        tMainConfig.save();

        GT_Log.out.println("GT_Mod: Generating Lang-File");
        GT_LanguageManager.sEnglishFile = new Configuration(new File(aEvent.getModConfigurationDirectory().getParentFile(), "GregTech.lang"));
        GT_LanguageManager.sEnglishFile.load();

        GT_Log.out.println("GT_Mod: Removing all original Scrapbox Drops.");
        try {
            GT_Utility.getField("ic2.core.item.ItemScrapbox$Drop", "topChance", true, true).set(null, Integer.valueOf(0));
            ((List) GT_Utility.getFieldContent(GT_Utility.getFieldContent("ic2.api.recipe.Recipes", "scrapboxDrops", true, true), "drops", true, true)).clear();
        } catch (Throwable e) {
            if (GT_Values.D1) {
                e.printStackTrace(GT_Log.err);
            }
        }
        GT_Log.out.println("GT_Mod: Adding Scrap with a Weight of 200.0F to the Scrapbox Drops.");
        GT_ModHandler.addScrapboxDrop(200.0F, GT_ModHandler.getIC2Item("scrap", 1L));

        EntityRegistry.registerModEntity(GT_Entity_Arrow.class, "GT_Entity_Arrow", 1, GT_Values.GT, 160, 1, true);
        EntityRegistry.registerModEntity(GT_Entity_Arrow_Potion.class, "GT_Entity_Arrow_Potion", 2, GT_Values.GT, 160, 1, true);

        new Enchantment_EnderDamage();
        new Enchantment_Radioactivity();

        new GT_Loader_OreProcessing().run();
        new GT_Loader_OreDictionary().run();
        new GT_Loader_ItemData().run();
        new GT_Loader_Item_Block_And_Fluid().run();
        new GT_Loader_MetaTileEntities().run();

        new GT_Loader_CircuitBehaviors().run();
        new GT_CoverBehaviorLoader().run();
        new GT_SonictronLoader().run();
        new GT_SpawnEventHandler();
        if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.gregtechrecipes, "SolarPanel", true)) {
            GT_ModHandler.addCraftingRecipe(ItemList.Cover_SolarPanel.get(1L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"SGS", "CPC", Character.valueOf('C'), OrePrefixes.circuit.get(Materials.Basic), Character.valueOf('G'), new ItemStack(Blocks.glass_pane, 1), Character.valueOf('P'), OrePrefixes.plateAlloy.get(Materials.Carbon), Character.valueOf('S'), OrePrefixes.plate.get(Materials.Silicon)});
        }
        if (GregTech_API.sOPStuff.get(ConfigCategories.Recipes.gregtechrecipes, "SolarPanel8V", false)) {
            GT_ModHandler.addCraftingRecipe(ItemList.Cover_SolarPanel_8V.get(1L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"SSS", "STS", "SSS", Character.valueOf('S'), ItemList.Cover_SolarPanel, Character.valueOf('T'), OrePrefixes.circuit.get(Materials.Advanced)});
        }
        if (GregTech_API.sOPStuff.get(ConfigCategories.Recipes.gregtechrecipes, "SolarPanelLV", false)) {
            GT_ModHandler.addCraftingRecipe(ItemList.Cover_SolarPanel_LV.get(1L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{" S ", "STS", " S ", Character.valueOf('S'), ItemList.Cover_SolarPanel_8V, Character.valueOf('T'), ItemList.Transformer_LV_ULV});
        }
        if (GregTech_API.sOPStuff.get(ConfigCategories.Recipes.gregtechrecipes, "SolarPanelMV", false)) {
            GT_ModHandler.addCraftingRecipe(ItemList.Cover_SolarPanel_MV.get(1L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{" S ", "STS", " S ", Character.valueOf('S'), ItemList.Cover_SolarPanel_LV, Character.valueOf('T'), ItemList.Transformer_MV_LV});
        }
        if (GregTech_API.sOPStuff.get(ConfigCategories.Recipes.gregtechrecipes, "SolarPanelHV", false)) {
            GT_ModHandler.addCraftingRecipe(ItemList.Cover_SolarPanel_HV.get(1L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{" S ", "STS", " S ", Character.valueOf('S'), ItemList.Cover_SolarPanel_MV, Character.valueOf('T'), ItemList.Transformer_HV_MV});
        }
        if (GregTech_API.sOPStuff.get(ConfigCategories.Recipes.gregtechrecipes, "SolarPanelEV", false)) {
            GT_ModHandler.addCraftingRecipe(ItemList.Cover_SolarPanel_EV.get(1L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{" S ", "STS", " S ", Character.valueOf('S'), ItemList.Cover_SolarPanel_HV, Character.valueOf('T'), ItemList.Transformer_EV_HV});
        }
        if (GregTech_API.sOPStuff.get(ConfigCategories.Recipes.gregtechrecipes, "SolarPanelIV", false)) {
            GT_ModHandler.addCraftingRecipe(ItemList.Cover_SolarPanel_IV.get(1L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{" S ", "STS", " S ", Character.valueOf('S'), ItemList.Cover_SolarPanel_EV, Character.valueOf('T'), ItemList.Transformer_IV_EV});
        }
        if (GregTech_API.sOPStuff.get(ConfigCategories.Recipes.gregtechrecipes, "SolarPanelLuV", false)) {
            GT_ModHandler.addCraftingRecipe(ItemList.Cover_SolarPanel_LuV.get(1L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{" S ", "STS", " S ", Character.valueOf('S'), ItemList.Cover_SolarPanel_IV, Character.valueOf('T'), ItemList.Transformer_LuV_IV});
        }
        if (GregTech_API.sOPStuff.get(ConfigCategories.Recipes.gregtechrecipes, "SolarPanelZPM", false)) {
            GT_ModHandler.addCraftingRecipe(ItemList.Cover_SolarPanel_ZPM.get(1L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{" S ", "STS", " S ", Character.valueOf('S'), ItemList.Cover_SolarPanel_LuV, Character.valueOf('T'), ItemList.Transformer_ZPM_LuV});
        }
        if (GregTech_API.sOPStuff.get(ConfigCategories.Recipes.gregtechrecipes, "SolarPanelUV", false)) {
            GT_ModHandler.addCraftingRecipe(ItemList.Cover_SolarPanel_UV.get(1L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{" S ", "STS", " S ", Character.valueOf('S'), ItemList.Cover_SolarPanel_ZPM, Character.valueOf('T'), ItemList.Transformer_UV_ZPM});
        }
        if (gregtechproxy.mSortToTheEnd) {
            try {
                GT_Log.out.println("GT_Mod: Sorting GregTech to the end of the Mod List for further processing.");
                LoadController tLoadController = (LoadController) GT_Utility.getFieldContent(Loader.instance(), "modController", true, true);
                List<ModContainer> tModList = tLoadController.getActiveModList();
                List<ModContainer> tNewModsList = new ArrayList();
                ModContainer tGregTech = null;
                for (short i = 0; i < tModList.size(); i = (short) (i + 1)) {
                    ModContainer tMod = (ModContainer) tModList.get(i);
                    if (tMod.getModId().equalsIgnoreCase("gregtech")) {
                        tGregTech = tMod;
                    } else {
                        tNewModsList.add(tMod);
                    }
                }
                if (tGregTech != null) {
                    tNewModsList.add(tGregTech);
                }
                GT_Utility.getField(tLoadController, "activeModList", true, true).set(tLoadController, tNewModsList);
            } catch (Throwable e) {
                if (GT_Values.D1) {
                    e.printStackTrace(GT_Log.err);
                }
            }
        }
        GregTech_API.sPreloadFinished = true;
        GT_Log.out.println("GT_Mod: Preload-Phase finished!");
        GT_Log.ore.println("GT_Mod: Preload-Phase finished!");
        for (Runnable tRunnable : GregTech_API.sAfterGTPreload) {
            try {
                tRunnable.run();
            } catch (Throwable e) {
                e.printStackTrace(GT_Log.err);
            }
        }
    }

    @Mod.EventHandler
    public void onLoad(FMLInitializationEvent aEvent) {
        if (GregTech_API.sLoadStarted) {
            return;
        }
        for (Runnable tRunnable : GregTech_API.sBeforeGTLoad) {
            try {
                tRunnable.run();
            } catch (Throwable e) {
                e.printStackTrace(GT_Log.err);
            }
        }
        gregtechproxy.onLoad();
        if (gregtechproxy.mSortToTheEnd) {
            new GT_ItemIterator().run();
            gregtechproxy.registerUnificationEntries();
            new GT_FuelLoader().run();
        }
        GregTech_API.sLoadFinished = true;
        GT_Log.out.println("GT_Mod: Load-Phase finished!");
        GT_Log.ore.println("GT_Mod: Load-Phase finished!");
        for (Runnable tRunnable : GregTech_API.sAfterGTLoad) {
            try {
                tRunnable.run();
            } catch (Throwable e) {
                e.printStackTrace(GT_Log.err);
            }
        }
    }

    @Mod.EventHandler
    public void onPostLoad(FMLPostInitializationEvent aEvent) {
        if (GregTech_API.sPostloadStarted) {
            return;
        }
        for (Runnable tRunnable : GregTech_API.sBeforeGTPostload) {
            try {
                tRunnable.run();
            } catch (Throwable e) {
                e.printStackTrace(GT_Log.err);
            }
        }
        gregtechproxy.onPostLoad();
        if (gregtechproxy.mSortToTheEnd) {
            gregtechproxy.registerUnificationEntries();
        } else {
            new GT_ItemIterator().run();
            gregtechproxy.registerUnificationEntries();
            new GT_FuelLoader().run();
        }
        new GT_BookAndLootLoader().run();
        new GT_ItemMaxStacksizeLoader().run();
        new GT_BlockResistanceLoader().run();
        new GT_RecyclerBlacklistLoader().run();
        new GT_MinableRegistrator().run();
        new GT_MachineRecipeLoader().run();
        new GT_ScrapboxDropLoader().run();
        new GT_CropLoader().run();
        new GT_Worldgenloader().run();
        new GT_CoverLoader().run();

        GT_RecipeRegistrator.registerUsagesForMaterials(new ItemStack(Blocks.planks, 1), null, false);
        GT_RecipeRegistrator.registerUsagesForMaterials(new ItemStack(Blocks.cobblestone, 1), null, false);
        GT_RecipeRegistrator.registerUsagesForMaterials(new ItemStack(Blocks.stone, 1), null, false);
        GT_RecipeRegistrator.registerUsagesForMaterials(new ItemStack(Items.leather, 1), null, false);

        GT_OreDictUnificator.addItemData(GT_ModHandler.getRecipeOutput(new ItemStack[]{null, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Tin, 1L), null, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Tin, 1L), null, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Tin, 1L), null, null, null}), new ItemData(Materials.Tin, 10886400L, new MaterialStack[0]));
        if (!GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.storageblockcrafting, "tile.glowstone", false)) {
            GT_ModHandler.removeRecipe(new ItemStack[]{new ItemStack(Items.glowstone_dust, 1), new ItemStack(Items.glowstone_dust, 1), null, new ItemStack(Items.glowstone_dust, 1), new ItemStack(Items.glowstone_dust, 1)});
        }
        GT_ModHandler.removeRecipe(new ItemStack[]{new ItemStack(Blocks.wooden_slab, 1, 0), new ItemStack(Blocks.wooden_slab, 1, 1), new ItemStack(Blocks.wooden_slab, 1, 2)});
        GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.wooden_slab, 6, 0), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"WWW", Character.valueOf('W'), new ItemStack(Blocks.planks, 1, 0)});

        GT_Log.out.println("GT_Mod: Activating OreDictionary Handler, this can take some time, as it scans the whole OreDictionary");
        FMLLog.info("If your Log stops here, you were too impatient. Wait a bit more next time, before killing Minecraft with the Task Manager.", new Object[0]);
        gregtechproxy.activateOreDictHandler();
        FMLLog.info("Congratulations, you have been waiting long enough. Have a Cake.", new Object[0]);
        GT_Log.out.println("GT_Mod: " + GT_ModHandler.sSingleNonBlockDamagableRecipeList.size() + " Recipes were left unused.");
        if (GT_Values.D1) {
            IRecipe tRecipe;
            for (Iterator i$ = GT_ModHandler.sSingleNonBlockDamagableRecipeList.iterator(); i$.hasNext(); GT_Log.out.println("=> " + tRecipe.getRecipeOutput().getDisplayName())) {
                tRecipe = (IRecipe) i$.next();
            }
        }
        new GT_CraftingRecipeLoader().run();
        if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "ic2forgehammer", true)) {
            GT_ModHandler.removeRecipeByOutput(ItemList.IC2_ForgeHammer.getWildcard(1L, new Object[0]));
        }
        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("machine", 1L));
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("machine", 1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"RRR", "RwR", "RRR", Character.valueOf('R'), OrePrefixes.plate.get(Materials.Iron)});
        for (FluidContainerRegistry.FluidContainerData tData : FluidContainerRegistry.getRegisteredFluidContainerData()) {
            if ((tData.filledContainer.getItem() == Items.potionitem) && (tData.filledContainer.getItemDamage() == 0)) {
                GT_Recipe.GT_Recipe_Map.sFluidCannerRecipes.addRecipe(true, new ItemStack[]{ItemList.Bottle_Empty.get(1L, new Object[0])}, new ItemStack[]{new ItemStack(Items.potionitem, 1, 0)}, null, new FluidStack[]{Materials.Water.getFluid(250L)}, null, 4, 1, 0);
                GT_Recipe.GT_Recipe_Map.sFluidCannerRecipes.addRecipe(true, new ItemStack[]{new ItemStack(Items.potionitem, 1, 0)}, new ItemStack[]{ItemList.Bottle_Empty.get(1L, new Object[0])}, null, null, null, 4, 1, 0);
            } else {
                GT_Recipe.GT_Recipe_Map.sFluidCannerRecipes.addRecipe(true, new ItemStack[]{tData.emptyContainer}, new ItemStack[]{tData.filledContainer}, null, new FluidStack[]{tData.fluid}, null, tData.fluid.amount / 62, 1, 0);
                GT_Recipe.GT_Recipe_Map.sFluidCannerRecipes.addRecipe(true, new ItemStack[]{tData.filledContainer}, new ItemStack[]{GT_Utility.getContainerItem(tData.filledContainer, true)}, null, null, new FluidStack[]{tData.fluid}, tData.fluid.amount / 62, 1, 0);
            }
        }
        try {
            for (Object tRecipe : TileCentrifuge.RecipeManager.recipes) {
                Map<ItemStack, Float> outputs = ((TileCentrifuge.CentrifugeRecipe) tRecipe).getAllProducts();
                ItemStack[] tOutputs = new ItemStack[outputs.size()];
                int[] tChances = new int[outputs.size()];
                int i = 0;
                for (Map.Entry<ItemStack, Float> entry : outputs.entrySet()) {
                    tChances[i] = (int) (entry.getValue() * 10000);
                    tOutputs[i] = entry.getKey().copy();
                    i++;
                }
                GT_Recipe.GT_Recipe_Map.sCentrifugeRecipes.addRecipe(true, new ItemStack[]{((TileCentrifuge.CentrifugeRecipe) tRecipe).getInput()}, tOutputs, null, tChances, null, null, 128, 5, 0);
            }
        } catch (Throwable e) {
            if (GT_Values.D1) {
                e.printStackTrace(GT_Log.err);
            }
        }
        try {
            for (Object tRecipe : TileSqueezer.RecipeManager.recipes) {
                if ((((ISqueezerRecipe) tRecipe).getResources().length == 1) && (((ISqueezerRecipe) tRecipe).getFluidOutput() != null)) {
                    GT_Recipe.GT_Recipe_Map.sFluidExtractionRecipes.addRecipe(true, new ItemStack[]{((ISqueezerRecipe) tRecipe).getResources()[0]}, new ItemStack[]{((ISqueezerRecipe) tRecipe).getRemnants()}, null, new int[]{(int) (((ISqueezerRecipe) tRecipe).getRemnantsChance() * 10000)}, null, new FluidStack[]{((ISqueezerRecipe) tRecipe).getFluidOutput()}, 400, 2, 0);
                }
            }
        } catch (Throwable e) {
            if (GT_Values.D1) {
                e.printStackTrace(GT_Log.err);
            }
        }
        String tName = "";
        if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "ic2_" + (tName = "blastfurnace"), true)) {
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(tName, 1L));
        }
        if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "ic2_" + (tName = "blockcutter"), true)) {
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(tName, 1L));
        }
        if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "ic2_" + (tName = "inductionFurnace"), true)) {
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(tName, 1L));
        }
        if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "ic2_" + (tName = "generator"), false)) {
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(tName, 1L));
        }
        if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "ic2_" + (tName = "windMill"), true)) {
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(tName, 1L));
        }
        if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "ic2_" + (tName = "waterMill"), true)) {
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(tName, 1L));
        }
        if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "ic2_" + (tName = "solarPanel"), true)) {
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(tName, 1L));
        }
        if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "ic2_" + (tName = "centrifuge"), true)) {
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(tName, 1L));
        }
        if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "ic2_" + (tName = "electrolyzer"), false)) {
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(tName, 1L));
        }
        if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "ic2_" + (tName = "compressor"), true)) {
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(tName, 1L));
        }
        if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "ic2_" + (tName = "electroFurnace"), true)) {
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(tName, 1L));
        }
        if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "ic2_" + (tName = "extractor"), true)) {
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(tName, 1L));
        }
        if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "ic2_" + (tName = "macerator"), true)) {
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(tName, 1L));
        }
        if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "ic2_" + (tName = "recycler"), true)) {
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(tName, 1L));
        }
        if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "ic2_" + (tName = "metalformer"), true)) {
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(tName, 1L));
        }
        if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "ic2_" + (tName = "orewashingplant"), true)) {
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(tName, 1L));
        }
        if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "ic2_" + (tName = "massFabricator"), true)) {
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(tName, 1L));
        }
        if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "ic2_" + (tName = "replicator"), true)) {
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(tName, 1L));
        }
        if (gregtechproxy.mNerfedVanillaTools) {
            GT_Log.out.println("GT_Mod: Nerfing Vanilla Tool Durability");
            Items.wooden_sword.setMaxDamage(12);
            Items.wooden_pickaxe.setMaxDamage(12);
            Items.wooden_shovel.setMaxDamage(12);
            Items.wooden_axe.setMaxDamage(12);
            Items.wooden_hoe.setMaxDamage(12);

            Items.stone_sword.setMaxDamage(48);
            Items.stone_pickaxe.setMaxDamage(48);
            Items.stone_shovel.setMaxDamage(48);
            Items.stone_axe.setMaxDamage(48);
            Items.stone_hoe.setMaxDamage(48);

            Items.iron_sword.setMaxDamage(256);
            Items.iron_pickaxe.setMaxDamage(256);
            Items.iron_shovel.setMaxDamage(256);
            Items.iron_axe.setMaxDamage(256);
            Items.iron_hoe.setMaxDamage(256);

            Items.golden_sword.setMaxDamage(24);
            Items.golden_pickaxe.setMaxDamage(24);
            Items.golden_shovel.setMaxDamage(24);
            Items.golden_axe.setMaxDamage(24);
            Items.golden_hoe.setMaxDamage(24);

            Items.diamond_sword.setMaxDamage(768);
            Items.diamond_pickaxe.setMaxDamage(768);
            Items.diamond_shovel.setMaxDamage(768);
            Items.diamond_axe.setMaxDamage(768);
            Items.diamond_hoe.setMaxDamage(768);
        }
        GT_Log.out.println("GT_Mod: Adding buffered Recipes.");
        GT_ModHandler.stopBufferingCraftingRecipes();

        GT_Log.out.println("GT_Mod: Saving Lang File.");
        GT_LanguageManager.sEnglishFile.save();
        GregTech_API.sPostloadFinished = true;
        GT_Log.out.println("GT_Mod: PostLoad-Phase finished!");
        GT_Log.ore.println("GT_Mod: PostLoad-Phase finished!");
        for (Runnable tRunnable : GregTech_API.sAfterGTPostload) {
            try {
                tRunnable.run();
            } catch (Throwable e) {
                e.printStackTrace(GT_Log.err);
            }
        }
        GT_Log.out.println("GT_Mod: Adding Fake Recipes for NEI");
        if (ItemList.FR_Bee_Drone.get(1L, new Object[0]) != null) {
            GT_Recipe.GT_Recipe_Map.sScannerFakeRecipes.addFakeRecipe(false, new ItemStack[]{ItemList.FR_Bee_Drone.getWildcard(1L, new Object[0])}, new ItemStack[]{ItemList.FR_Bee_Drone.getWithName(1L, "Scanned Drone", new Object[0])}, null, new FluidStack[]{Materials.Honey.getFluid(50L)}, null, 500, 2, 0);
        }
        if (ItemList.FR_Bee_Princess.get(1L, new Object[0]) != null) {
            GT_Recipe.GT_Recipe_Map.sScannerFakeRecipes.addFakeRecipe(false, new ItemStack[]{ItemList.FR_Bee_Princess.getWildcard(1L, new Object[0])}, new ItemStack[]{ItemList.FR_Bee_Princess.getWithName(1L, "Scanned Princess", new Object[0])}, null, new FluidStack[]{Materials.Honey.getFluid(50L)}, null, 500, 2, 0);
        }
        if (ItemList.FR_Bee_Queen.get(1L, new Object[0]) != null) {
            GT_Recipe.GT_Recipe_Map.sScannerFakeRecipes.addFakeRecipe(false, new ItemStack[]{ItemList.FR_Bee_Queen.getWildcard(1L, new Object[0])}, new ItemStack[]{ItemList.FR_Bee_Queen.getWithName(1L, "Scanned Queen", new Object[0])}, null, new FluidStack[]{Materials.Honey.getFluid(50L)}, null, 500, 2, 0);
        }
        if (ItemList.FR_Tree_Sapling.get(1L, new Object[0]) != null) {
            GT_Recipe.GT_Recipe_Map.sScannerFakeRecipes.addFakeRecipe(false, new ItemStack[]{ItemList.FR_Tree_Sapling.getWildcard(1L, new Object[0])}, new ItemStack[]{ItemList.FR_Tree_Sapling.getWithName(1L, "Scanned Sapling", new Object[0])}, null, new FluidStack[]{Materials.Honey.getFluid(50L)}, null, 500, 2, 0);
        }
        if (ItemList.FR_Butterfly.get(1L, new Object[0]) != null) {
            GT_Recipe.GT_Recipe_Map.sScannerFakeRecipes.addFakeRecipe(false, new ItemStack[]{ItemList.FR_Butterfly.getWildcard(1L, new Object[0])}, new ItemStack[]{ItemList.FR_Butterfly.getWithName(1L, "Scanned Butterfly", new Object[0])}, null, new FluidStack[]{Materials.Honey.getFluid(50L)}, null, 500, 2, 0);
        }
        if (ItemList.FR_Larvae.get(1L, new Object[0]) != null) {
            GT_Recipe.GT_Recipe_Map.sScannerFakeRecipes.addFakeRecipe(false, new ItemStack[]{ItemList.FR_Larvae.getWildcard(1L, new Object[0])}, new ItemStack[]{ItemList.FR_Larvae.getWithName(1L, "Scanned Larvae", new Object[0])}, null, new FluidStack[]{Materials.Honey.getFluid(50L)}, null, 500, 2, 0);
        }
        if (ItemList.FR_Serum.get(1L, new Object[0]) != null) {
            GT_Recipe.GT_Recipe_Map.sScannerFakeRecipes.addFakeRecipe(false, new ItemStack[]{ItemList.FR_Serum.getWildcard(1L, new Object[0])}, new ItemStack[]{ItemList.FR_Serum.getWithName(1L, "Scanned Serum", new Object[0])}, null, new FluidStack[]{Materials.Honey.getFluid(50L)}, null, 500, 2, 0);
        }
        if (ItemList.FR_Caterpillar.get(1L, new Object[0]) != null) {
            GT_Recipe.GT_Recipe_Map.sScannerFakeRecipes.addFakeRecipe(false, new ItemStack[]{ItemList.FR_Caterpillar.getWildcard(1L, new Object[0])}, new ItemStack[]{ItemList.FR_Caterpillar.getWithName(1L, "Scanned Caterpillar", new Object[0])}, null, new FluidStack[]{Materials.Honey.getFluid(50L)}, null, 500, 2, 0);
        }
        if (ItemList.FR_PollenFertile.get(1L, new Object[0]) != null) {
            GT_Recipe.GT_Recipe_Map.sScannerFakeRecipes.addFakeRecipe(false, new ItemStack[]{ItemList.FR_PollenFertile.getWildcard(1L, new Object[0])}, new ItemStack[]{ItemList.FR_PollenFertile.getWithName(1L, "Scanned Pollen", new Object[0])}, null, new FluidStack[]{Materials.Honey.getFluid(50L)}, null, 500, 2, 0);
        }
        if (ItemList.IC2_Crop_Seeds.get(1L, new Object[0]) != null) {
            GT_Recipe.GT_Recipe_Map.sScannerFakeRecipes.addFakeRecipe(false, new ItemStack[]{ItemList.IC2_Crop_Seeds.getWildcard(1L, new Object[0])}, new ItemStack[]{ItemList.IC2_Crop_Seeds.getWithName(1L, "Scanned Seeds", new Object[0])}, null, null, null, 160, 8, 0);
        }
        GT_Recipe.GT_Recipe_Map.sScannerFakeRecipes.addFakeRecipe(false, new ItemStack[]{new ItemStack(Items.written_book, 1, 32767)}, new ItemStack[]{ItemList.Tool_DataStick.getWithName(1L, "Scanned Book Data", new Object[0])}, ItemList.Tool_DataStick.getWithName(1L, "Stick to save it to", new Object[0]), null, null, 128, 32, 0);
        GT_Recipe.GT_Recipe_Map.sScannerFakeRecipes.addFakeRecipe(false, new ItemStack[]{new ItemStack(Items.filled_map, 1, 32767)}, new ItemStack[]{ItemList.Tool_DataStick.getWithName(1L, "Scanned Map Data", new Object[0])}, ItemList.Tool_DataStick.getWithName(1L, "Stick to save it to", new Object[0]), null, null, 128, 32, 0);
        GT_Recipe.GT_Recipe_Map.sScannerFakeRecipes.addFakeRecipe(false, new ItemStack[]{ItemList.Tool_DataOrb.getWithName(1L, "Orb to overwrite", new Object[0])}, new ItemStack[]{ItemList.Tool_DataOrb.getWithName(1L, "Copy of the Orb", new Object[0])}, ItemList.Tool_DataOrb.getWithName(0L, "Orb to copy", new Object[0]), null, null, 512, 32, 0);
        GT_Recipe.GT_Recipe_Map.sScannerFakeRecipes.addFakeRecipe(false, new ItemStack[]{ItemList.Tool_DataStick.getWithName(1L, "Stick to overwrite", new Object[0])}, new ItemStack[]{ItemList.Tool_DataStick.getWithName(1L, "Copy of the Stick", new Object[0])}, ItemList.Tool_DataStick.getWithName(0L, "Stick to copy", new Object[0]), null, null, 128, 32, 0);
        for (Materials tMaterial : Materials.VALUES) {
            if ((tMaterial.mElement != null) && (!tMaterial.mElement.mIsIsotope) && (tMaterial != Materials.Magic) && (tMaterial.getMass() > 0L)) {
                ItemStack tOutput = ItemList.Tool_DataOrb.get(1L, new Object[0]);
                Behaviour_DataOrb.setDataTitle(tOutput, "Elemental-Scan");
                Behaviour_DataOrb.setDataName(tOutput, tMaterial.mElement.name());
                ItemStack tInput = GT_OreDictUnificator.get(OrePrefixes.dust, tMaterial, 1L);
                if (tInput != null) {
                    GT_Recipe.GT_Recipe_Map.sScannerFakeRecipes.addFakeRecipe(false, new ItemStack[]{tInput}, new ItemStack[]{tOutput}, ItemList.Tool_DataOrb.get(1L, new Object[0]), null, null, (int) (tMaterial.getMass() * 8192L), 32, 0);
                    GT_Recipe.GT_Recipe_Map.sRepicatorFakeRecipes.addFakeRecipe(false, null, new ItemStack[]{tInput}, new ItemStack[]{tOutput}, new FluidStack[]{Materials.UUMatter.getFluid(tMaterial.getMass())}, null, (int) (tMaterial.getMass() * 512L), 32, 0);
                }
                tInput = GT_OreDictUnificator.get(OrePrefixes.cell, tMaterial, 1L);
                if (tInput != null) {
                    GT_Recipe.GT_Recipe_Map.sScannerFakeRecipes.addFakeRecipe(false, new ItemStack[]{tInput}, new ItemStack[]{tOutput}, ItemList.Tool_DataOrb.get(1L, new Object[0]), null, null, (int) (tMaterial.getMass() * 8192L), 32, 0);
                    GT_Recipe.GT_Recipe_Map.sRepicatorFakeRecipes.addFakeRecipe(false, null, new ItemStack[]{tInput}, new ItemStack[]{tOutput}, new FluidStack[]{Materials.UUMatter.getFluid(tMaterial.getMass())}, null, (int) (tMaterial.getMass() * 512L), 32, 0);
                }
            }
        }
        GT_Recipe.GT_Recipe_Map.sRockBreakerFakeRecipes.addFakeRecipe(false, new ItemStack[]{ItemList.Display_ITS_FREE.getWithName(0L, "Place Lava on Side", new Object[0])}, new ItemStack[]{new ItemStack(Blocks.cobblestone, 1)}, null, null, null, 16, 32, 0);
        GT_Recipe.GT_Recipe_Map.sRockBreakerFakeRecipes.addFakeRecipe(false, new ItemStack[]{ItemList.Display_ITS_FREE.getWithName(0L, "Place Lava on Top", new Object[0])}, new ItemStack[]{new ItemStack(Blocks.stone, 1)}, null, null, null, 16, 32, 0);
        GT_Recipe.GT_Recipe_Map.sRockBreakerFakeRecipes.addFakeRecipe(false, new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Redstone, 1L)}, new ItemStack[]{new ItemStack(Blocks.obsidian, 1)}, null, null, null, 128, 32, 0);
        for (Iterator i$ = GT_ModHandler.getMaceratorRecipeList().entrySet().iterator(); i$.hasNext(); ) {
            Entry tRecipe = (Map.Entry) i$.next();
            if (((RecipeOutput) tRecipe.getValue()).items.size() > 0) {
                for (ItemStack tStack : ((IRecipeInput) tRecipe.getKey()).getInputs()) {
                    if (GT_Utility.isStackValid(tStack)) {
                        GT_Recipe.GT_Recipe_Map.sMaceratorRecipes.addFakeRecipe(true, new ItemStack[]{GT_Utility.copyAmount(((IRecipeInput) tRecipe.getKey()).getAmount(), new Object[]{tStack})}, new ItemStack[]{(ItemStack) ((RecipeOutput) tRecipe.getValue()).items.get(0)}, null, null, null, null, 400, 2, 0);
                    }
                }
            }
        }
        achievements = new GT_Achievements();
        Map.Entry<IRecipeInput, RecipeOutput> tRecipe;
        GT_Log.out.println("GT_Mod: Loading finished, deallocating temporary Init Variables.");
        GregTech_API.sBeforeGTPreload = null;
        GregTech_API.sAfterGTPreload = null;
        GregTech_API.sBeforeGTLoad = null;
        GregTech_API.sAfterGTLoad = null;
        GregTech_API.sBeforeGTPostload = null;
        GregTech_API.sAfterGTPostload = null;
    }

    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent aEvent) {
        for (Runnable tRunnable : GregTech_API.sBeforeGTServerstart) {
            try {
                tRunnable.run();
            } catch (Throwable e) {
                e.printStackTrace(GT_Log.err);
            }
        }
        gregtechproxy.onServerStarting();
        GT_Log.out.println("GT_Mod: Unificating outputs of all known Recipe Types.");
        ArrayList<ItemStack> tStacks = new ArrayList(10000);
        GT_Log.out.println("GT_Mod: IC2 Machines");
        for (RecipeOutput tRecipe : ic2.api.recipe.Recipes.cannerBottle.getRecipes().values()) {
            ItemStack tStack;
            for (Iterator i$ = tRecipe.items.iterator(); i$.hasNext(); tStacks.add(tStack)) {
                tStack = (ItemStack) i$.next();
            }
        }
        for (RecipeOutput tRecipe : ic2.api.recipe.Recipes.centrifuge.getRecipes().values()) {
            ItemStack tStack;
            for (Iterator i$ = tRecipe.items.iterator(); i$.hasNext(); tStacks.add(tStack)) {
                tStack = (ItemStack) i$.next();
            }
        }
        for (RecipeOutput tRecipe : ic2.api.recipe.Recipes.compressor.getRecipes().values()) {
            ItemStack tStack;
            for (Iterator i$ = tRecipe.items.iterator(); i$.hasNext(); tStacks.add(tStack)) {
                tStack = (ItemStack) i$.next();
            }
        }
        for (RecipeOutput tRecipe : ic2.api.recipe.Recipes.extractor.getRecipes().values()) {
            ItemStack tStack;
            for (Iterator i$ = tRecipe.items.iterator(); i$.hasNext(); tStacks.add(tStack)) {
                tStack = (ItemStack) i$.next();
            }
        }
        for (RecipeOutput tRecipe : ic2.api.recipe.Recipes.macerator.getRecipes().values()) {
            ItemStack tStack;
            for (Iterator i$ = tRecipe.items.iterator(); i$.hasNext(); tStacks.add(tStack)) {
                tStack = (ItemStack) i$.next();
            }
        }
        for (RecipeOutput tRecipe : ic2.api.recipe.Recipes.metalformerCutting.getRecipes().values()) {
            ItemStack tStack;
            for (Iterator i$ = tRecipe.items.iterator(); i$.hasNext(); tStacks.add(tStack)) {
                tStack = (ItemStack) i$.next();
            }
        }
        for (RecipeOutput tRecipe : ic2.api.recipe.Recipes.metalformerExtruding.getRecipes().values()) {
            ItemStack tStack;
            for (Iterator i$ = tRecipe.items.iterator(); i$.hasNext(); tStacks.add(tStack)) {
                tStack = (ItemStack) i$.next();
            }
        }
        for (RecipeOutput tRecipe : ic2.api.recipe.Recipes.metalformerRolling.getRecipes().values()) {
            ItemStack tStack;
            for (Iterator i$ = tRecipe.items.iterator(); i$.hasNext(); tStacks.add(tStack)) {
                tStack = (ItemStack) i$.next();
            }
        }
        for (RecipeOutput tRecipe : ic2.api.recipe.Recipes.matterAmplifier.getRecipes().values()) {
            ItemStack tStack;
            for (Iterator i$ = tRecipe.items.iterator(); i$.hasNext(); tStacks.add(tStack)) {
                tStack = (ItemStack) i$.next();
            }
        }
        for (RecipeOutput tRecipe : ic2.api.recipe.Recipes.oreWashing.getRecipes().values()) {
            ItemStack tStack;
            for (Iterator i$ = tRecipe.items.iterator(); i$.hasNext(); tStacks.add(tStack)) {
                tStack = (ItemStack) i$.next();
            }
        }
        GT_Log.out.println("GT_Mod: Dungeon Loot");
        for (WeightedRandomChestContent tContent : ChestGenHooks.getInfo("dungeonChest").getItems(new Random())) {
            tStacks.add(tContent.theItemId);
        }
        for (WeightedRandomChestContent tContent : ChestGenHooks.getInfo("bonusChest").getItems(new Random())) {
            tStacks.add(tContent.theItemId);
        }
        for (WeightedRandomChestContent tContent : ChestGenHooks.getInfo("villageBlacksmith").getItems(new Random())) {
            tStacks.add(tContent.theItemId);
        }
        for (WeightedRandomChestContent tContent : ChestGenHooks.getInfo("strongholdCrossing").getItems(new Random())) {
            tStacks.add(tContent.theItemId);
        }
        for (WeightedRandomChestContent tContent : ChestGenHooks.getInfo("strongholdLibrary").getItems(new Random())) {
            tStacks.add(tContent.theItemId);
        }
        for (WeightedRandomChestContent tContent : ChestGenHooks.getInfo("strongholdCorridor").getItems(new Random())) {
            tStacks.add(tContent.theItemId);
        }
        for (WeightedRandomChestContent tContent : ChestGenHooks.getInfo("pyramidJungleDispenser").getItems(new Random())) {
            tStacks.add(tContent.theItemId);
        }
        for (WeightedRandomChestContent tContent : ChestGenHooks.getInfo("pyramidJungleChest").getItems(new Random())) {
            tStacks.add(tContent.theItemId);
        }
        for (WeightedRandomChestContent tContent : ChestGenHooks.getInfo("pyramidDesertyChest").getItems(new Random())) {
            tStacks.add(tContent.theItemId);
        }
        for (WeightedRandomChestContent tContent : ChestGenHooks.getInfo("mineshaftCorridor").getItems(new Random())) {
            tStacks.add(tContent.theItemId);
        }
        GT_Log.out.println("GT_Mod: Smelting");
        Object tStack;
        for (Iterator i$ = FurnaceRecipes.smelting().getSmeltingList().values().iterator(); i$.hasNext(); tStacks.add((ItemStack) tStack)) {
            tStack = i$.next();
        }
        if (gregtechproxy.mCraftingUnification) {
            GT_Log.out.println("GT_Mod: Crafting Recipes");
            for (Object tRecipe : CraftingManager.getInstance().getRecipeList()) {
                if ((tRecipe instanceof IRecipe)) {
                    tStacks.add(((IRecipe) tRecipe).getRecipeOutput());
                }
            }
        }
        for (ItemStack tOutput : tStacks) {
            if (gregtechproxy.mRegisteredOres.contains(tOutput)) {
                FMLLog.severe("GT-ERR-01: @ " + tOutput.getUnlocalizedName() + "   " + tOutput.getDisplayName(), new Object[0]);
                FMLLog.severe("A Recipe used an OreDict Item as Output directly, without copying it before!!! This is a typical CallByReference/CallByValue Error", new Object[0]);
                FMLLog.severe("Said Item will be renamed to make the invalid Recipe visible, so that you can report it properly.", new Object[0]);
                FMLLog.severe("Please check all Recipes outputting this Item, and report the Recipes to their Owner.", new Object[0]);
                FMLLog.severe("The Owner of the ==>RECIPE<==, NOT the Owner of the Item, which has been mentioned above!!!", new Object[0]);
                FMLLog.severe("And ONLY Recipes which are ==>OUTPUTTING<== the Item, sorry but I don't want failed Bug Reports.", new Object[0]);
                FMLLog.severe("GregTech just reports this Error to you, so you can report it to the Mod causing the Problem.", new Object[0]);
                FMLLog.severe("Even though I make that Bug visible, I can not and will not fix that for you, that's for the causing Mod to fix.", new Object[0]);
                FMLLog.severe("And speaking of failed Reports:", new Object[0]);
                FMLLog.severe("Both IC2 and GregTech CANNOT be the CAUSE of this Problem, so don't report it to either of them.", new Object[0]);
                FMLLog.severe("I REPEAT, BOTH, IC2 and GregTech CANNOT be the source of THIS BUG. NO MATTER WHAT.", new Object[0]);
                FMLLog.severe("Asking in the IC2 Forums, which Mod is causing that, won't help anyone, since it is not possible to determine, which Mod it is.", new Object[0]);
                FMLLog.severe("If it would be possible, then I would have had added the Mod which is causing it to the Message already. But it is not possible.", new Object[0]);
                FMLLog.severe("Sorry, but this Error is serious enough to justify this Wall-O-Text and the partially allcapsed Language.", new Object[0]);
                FMLLog.severe("Also it is a Ban Reason on the IC2-Forums to post this seriously.", new Object[0]);
                tOutput.setStackDisplayName("ERROR! PLEASE CHECK YOUR LOG FOR 'GT-ERR-01'!");
            } else {
                GT_OreDictUnificator.setStack(tOutput);
            }
        }
        GT_Log.out.println("GT_Mod: ServerStarting-Phase finished!");
        GT_Log.ore.println("GT_Mod: ServerStarting-Phase finished!");
        for (Runnable tRunnable : GregTech_API.sAfterGTServerstart) {
            try {
                tRunnable.run();
            } catch (Throwable e) {
                e.printStackTrace(GT_Log.err);
            }
        }
    }

    @Mod.EventHandler
    public void onServerStarted(FMLServerStartedEvent aEvent) {
        gregtechproxy.onServerStarted();
    }

    @Mod.EventHandler
    public void onIDChangingEvent(FMLModIdMappingEvent aEvent) {
        GT_Utility.reInit();
        GT_Recipe.reInit();
        for (Iterator i$ = GregTech_API.sItemStackMappings.iterator(); i$.hasNext(); ) {
            Map tMap = (Map) i$.next();
            try {
                GT_Utility.reMap(tMap);
            } catch (Throwable e) {
                e.printStackTrace(GT_Log.err);
            }
        }

    }
//  public void onIDChangingEvent(FMLModIdMappingEvent aEvent)
//  {
//    GT_Utility.reInit();
//    GT_Recipe.reInit();
//    Map<GT_ItemStack, ?> tMap;
//    for (Iterator i$ = GregTech_API.sItemStackMappings.iterator(); i$.hasNext(); ) {
//      tMap = (Map)i$.next();
//    }
//  }

    @Mod.EventHandler
    public void onServerStopping(FMLServerStoppingEvent aEvent) {
        for (Runnable tRunnable : GregTech_API.sBeforeGTServerstop) {
            try {
                tRunnable.run();
            } catch (Throwable e) {
                e.printStackTrace(GT_Log.err);
            }
        }
        gregtechproxy.onServerStopping();
        try {
            if ((GT_Values.D1) || (GT_Log.out != System.out)) {
                GT_Log.out.println("*");
                GT_Log.out.println("Printing List of all registered Objects inside the OreDictionary, now with free extra Sorting:");
                GT_Log.out.println("*");
                GT_Log.out.println("*");
                GT_Log.out.println("*");

                String[] tList = OreDictionary.getOreNames();
                Arrays.sort(tList);
                for (String tOreName : tList) {
                    int tAmount = OreDictionary.getOres(tOreName).size();
                    if (tAmount > 0) {
                        GT_Log.out.println((tAmount < 10 ? " " : "") + tAmount + "x " + tOreName);
                    }
                }
                GT_Log.out.println("*");
                GT_Log.out.println("Printing List of all registered Objects inside the Fluid Registry, now with free extra Sorting:");
                GT_Log.out.println("*");
                GT_Log.out.println("*");
                GT_Log.out.println("*");

                tList = (String[]) FluidRegistry.getRegisteredFluids().keySet().toArray(new String[FluidRegistry.getRegisteredFluids().keySet().size()]);
                Arrays.sort(tList);
                for (String tFluidName : tList) {
                    GT_Log.out.println(tFluidName);
                }
                GT_Log.out.println("*");
                GT_Log.out.println("*");
                GT_Log.out.println("*");
                GT_Log.out.println("Outputting all the Names inside the Biomeslist");
                GT_Log.out.println("*");
                GT_Log.out.println("*");
                GT_Log.out.println("*");
                for (int i = 0; i < BiomeGenBase.getBiomeGenArray().length; i++) {
                    if (BiomeGenBase.getBiomeGenArray()[i] != null) {
                        GT_Log.out.println(BiomeGenBase.getBiomeGenArray()[i].biomeID + " = " + BiomeGenBase.getBiomeGenArray()[i].biomeName);
                    }
                }
                GT_Log.out.println("*");
                GT_Log.out.println("*");
                GT_Log.out.println("*");
                GT_Log.out.println("Printing List of generatable Materials");
                GT_Log.out.println("*");
                GT_Log.out.println("*");
                GT_Log.out.println("*");
                for (int i = 0; i < GregTech_API.sGeneratedMaterials.length; i++) {
                    if (GregTech_API.sGeneratedMaterials[i] == null) {
                        GT_Log.out.println("Index " + i + ":" + null);
                    } else {
                        GT_Log.out.println("Index " + i + ":" + GregTech_API.sGeneratedMaterials[i]);
                    }
                }
                GT_Log.out.println("*");
                GT_Log.out.println("*");
                GT_Log.out.println("*");
                GT_Log.out.println("END GregTech-Debug");
                GT_Log.out.println("*");
                GT_Log.out.println("*");
                GT_Log.out.println("*");
            }
        } catch (Throwable e) {
            if (GT_Values.D1) {
                e.printStackTrace(GT_Log.err);
            }
        }
        for (Runnable tRunnable : GregTech_API.sAfterGTServerstop) {
            try {
                tRunnable.run();
            } catch (Throwable e) {
                e.printStackTrace(GT_Log.err);
            }
        }
    }

    public boolean isServerSide() {
        return gregtechproxy.isServerSide();
    }

    public boolean isClientSide() {
        return gregtechproxy.isClientSide();
    }

    public boolean isBukkitSide() {
        return gregtechproxy.isBukkitSide();
    }

    public EntityPlayer getThePlayer() {
        return gregtechproxy.getThePlayer();
    }

    public int addArmor(String aArmorPrefix) {
        return gregtechproxy.addArmor(aArmorPrefix);
    }

    public void doSonictronSound(ItemStack aStack, World aWorld, double aX, double aY, double aZ) {
        gregtechproxy.doSonictronSound(aStack, aWorld, aX, aY, aZ);
    }
}
