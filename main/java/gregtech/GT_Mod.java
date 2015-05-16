/*   1:    */ package gregtech;
/*   3:    */ import cpw.mods.fml.common.FMLLog;
/*   4:    */ import cpw.mods.fml.common.LoadController;
/*   5:    */ import cpw.mods.fml.common.Loader;
/*   6:    */ import cpw.mods.fml.common.Mod;
/*   7:    */ import cpw.mods.fml.common.Mod.EventHandler;
/*   8:    */ import cpw.mods.fml.common.Mod.Instance;
/*   9:    */ import cpw.mods.fml.common.ModContainer;
/*  10:    */ import cpw.mods.fml.common.SidedProxy;
/*  11:    */ import cpw.mods.fml.common.event.FMLInitializationEvent;
/*  12:    */ import cpw.mods.fml.common.event.FMLModIdMappingEvent;
/*  13:    */ import cpw.mods.fml.common.event.FMLPostInitializationEvent;
/*  14:    */ import cpw.mods.fml.common.event.FMLPreInitializationEvent;
/*  15:    */ import cpw.mods.fml.common.event.FMLServerStartedEvent;
/*  16:    */ import cpw.mods.fml.common.event.FMLServerStartingEvent;
/*  17:    */ import cpw.mods.fml.common.event.FMLServerStoppingEvent;
/*  18:    */ import cpw.mods.fml.common.registry.EntityRegistry;
import forestry.factory.gadgets.MachineCentrifuge;
/*  19:    */ import forestry.factory.gadgets.MachineCentrifuge.Recipe;
/*  20:    */ import forestry.factory.gadgets.MachineCentrifuge.RecipeManager;
import forestry.factory.gadgets.MachineSqueezer;
/*  23:    */ import gregtech.api.GregTech_API;
/*  24:    */ import gregtech.api.enchants.Enchantment_EnderDamage;
/*  25:    */ import gregtech.api.enchants.Enchantment_Radioactivity;
/*  26:    */ import gregtech.api.enums.ConfigCategories.Recipes;
import gregtech.api.enums.*;
/*  33:    */ import gregtech.api.enums.Textures.BlockIcons;
/*  34:    */ import gregtech.api.enums.Textures.ItemIcons;
/*  35:    */ import gregtech.api.interfaces.internal.IGT_Mod;
/*  36:    */ import gregtech.api.objects.GT_ItemStack;
/*  37:    */ import gregtech.api.objects.ItemData;
/*  38:    */ import gregtech.api.objects.MaterialStack;
/*  39:    */ import gregtech.api.util.GT_Config;
/*  40:    */ import gregtech.api.util.GT_ItsNotMyFaultException;
/*  41:    */ import gregtech.api.util.GT_LanguageManager;
/*  42:    */ import gregtech.api.util.GT_Log;
/*  43:    */ import gregtech.api.util.GT_Log.LogBuffer;
/*  44:    */ import gregtech.api.util.GT_ModHandler;
/*  45:    */ import gregtech.api.util.GT_ModHandler.RecipeBits;
/*  46:    */ import gregtech.api.util.GT_OreDictUnificator;
/*  47:    */ import gregtech.api.util.GT_Recipe;
/*  48:    */ import gregtech.api.util.GT_Recipe.GT_Recipe_Map;
/*  49:    */ import gregtech.api.util.GT_RecipeRegistrator;
/*  50:    */ import gregtech.api.util.GT_Utility;
/*  51:    */ import gregtech.common.GT_DummyWorld;
/*  52:    */ import gregtech.common.GT_Network;
/*  53:    */ import gregtech.common.GT_Proxy;
/*  54:    */ import gregtech.common.GT_RecipeAdder;
/*  55:    */ import gregtech.common.entities.GT_Entity_Arrow;
/*  56:    */ import gregtech.common.entities.GT_Entity_Arrow_Potion;
/*  57:    */ import gregtech.common.items.behaviors.Behaviour_DataOrb;
/*  58:    */ import gregtech.loaders.load.GT_CoverBehaviorLoader;
/*  59:    */ import gregtech.loaders.load.GT_FuelLoader;
/*  60:    */ import gregtech.loaders.load.GT_ItemIterator;
/*  61:    */ import gregtech.loaders.load.GT_SonictronLoader;
import gregtech.loaders.misc.GT_Achievements;
/*  62:    */ import gregtech.loaders.misc.GT_CoverLoader;
/*  63:    */ import gregtech.loaders.postload.GT_BlockResistanceLoader;
/*  64:    */ import gregtech.loaders.postload.GT_BookAndLootLoader;
/*  65:    */ import gregtech.loaders.postload.GT_CraftingRecipeLoader;
/*  66:    */ import gregtech.loaders.postload.GT_CropLoader;
/*  67:    */ import gregtech.loaders.postload.GT_ItemMaxStacksizeLoader;
/*  68:    */ import gregtech.loaders.postload.GT_MachineRecipeLoader;
/*  69:    */ import gregtech.loaders.postload.GT_MinableRegistrator;
/*  70:    */ import gregtech.loaders.postload.GT_RecyclerBlacklistLoader;
/*  71:    */ import gregtech.loaders.postload.GT_ScrapboxDropLoader;
/*  72:    */ import gregtech.loaders.postload.GT_Worldgenloader;
/*  73:    */ import gregtech.loaders.preload.GT_Loader_CircuitBehaviors;
/*  74:    */ import gregtech.loaders.preload.GT_Loader_ItemData;
/*  75:    */ import gregtech.loaders.preload.GT_Loader_Item_Block_And_Fluid;
/*  76:    */ import gregtech.loaders.preload.GT_Loader_MetaTileEntities;
/*  77:    */ import gregtech.loaders.preload.GT_Loader_OreDictionary;
/*  78:    */ import gregtech.loaders.preload.GT_Loader_OreProcessing;
/*  79:    */ import ic2.api.recipe.ICannerBottleRecipeManager;
/*  80:    */ import ic2.api.recipe.IMachineRecipeManager;
/*  81:    */ import ic2.api.recipe.IRecipeInput;
/*  82:    */ import ic2.api.recipe.RecipeOutput;

/*  84:    */ import java.io.File;
/*  85:    */ import java.io.FileNotFoundException;
/*  86:    */ import java.io.PrintStream;
/*  87:    */ import java.lang.reflect.Field;
/*  88:    */ import java.util.ArrayList;
/*  89:    */ import java.util.Arrays;
/*  90:    */ import java.util.Calendar;
/*  91:    */ import java.util.Collection;
/*  92:    */ import java.util.HashMap;
/*  93:    */ import java.util.HashSet;
/*  94:    */ import java.util.Iterator;
/*  95:    */ import java.util.List;
/*  96:    */ import java.util.Map;
/*  97:    */ import java.util.Map.Entry;
/*  98:    */ import java.util.Random;
/*  99:    */ import java.util.Set;

/* 100:    */ import net.minecraft.block.Block;
/* 101:    */ import net.minecraft.entity.player.EntityPlayer;
/* 102:    */ import net.minecraft.init.Blocks;
/* 103:    */ import net.minecraft.init.Items;
/* 104:    */ import net.minecraft.item.Item;
/* 105:    */ import net.minecraft.item.ItemStack;
/* 106:    */ import net.minecraft.item.crafting.CraftingManager;
/* 107:    */ import net.minecraft.item.crafting.FurnaceRecipes;
/* 108:    */ import net.minecraft.item.crafting.IRecipe;
import net.minecraft.stats.Achievement;
/* 109:    */ import net.minecraft.util.WeightedRandomChestContent;
/* 110:    */ import net.minecraft.world.World;
/* 111:    */ import net.minecraft.world.biome.BiomeGenBase;
/* 112:    */ import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.ForgeVersion;
/* 113:    */ import net.minecraftforge.common.config.Configuration;
/* 114:    */ import net.minecraftforge.common.config.Property;
/* 115:    */ import net.minecraftforge.fluids.FluidContainerRegistry;
/* 116:    */ import net.minecraftforge.fluids.FluidContainerRegistry.FluidContainerData;
/* 117:    */ import net.minecraftforge.fluids.FluidRegistry;
/* 118:    */ import net.minecraftforge.fluids.FluidStack;
/* 119:    */ import net.minecraftforge.oredict.OreDictionary;
/* 120:    */ 
/* 121:    */ @Mod(modid="gregtech", name="GregTech", version="MC1710", useMetadata=false, dependencies="required-after:IC2; after:Forestry; after:PFAAGeologica; after:Thaumcraft; after:Railcraft; after:appliedenergistics2; after:ThermalExpansion; after:TwilightForest; after:harvestcraft; after:magicalcrops; after:BuildCraft|Transport; after:BuildCraft|Silicon; after:BuildCraft|Factory; after:BuildCraft|Energy; after:BuildCraft|Core; after:BuildCraft|Builders; after:GalacticraftCore; after:GalacticraftMars; after:GalacticraftPlanets; after:ThermalExpansion|Transport; after:ThermalExpansion|Energy; after:ThermalExpansion|Factory; after:RedPowerCore; after:RedPowerBase; after:RedPowerMachine; after:RedPowerCompat; after:RedPowerWiring; after:RedPowerLogic; after:RedPowerLighting; after:RedPowerWorld; after:RedPowerControl;")
/* 122:    */ public class GT_Mod
/* 123:    */   implements IGT_Mod
/* 124:    */ {
/* 125:    */   @Mod.Instance("gregtech")
/* 126:    */   public static GT_Mod instance;
/* 127:    */   @SidedProxy(modId="gregtech", clientSide="gregtech.common.GT_Client", serverSide="gregtech.common.GT_Server")
/* 128:    */   public static GT_Proxy gregtechproxy;
/* 129:    */   public static final int VERSION = 508;
/* 130:    */   public static final int REQUIRED_IC2 = 624;
/* 131: 72 */   public static int MAX_IC2 = 2147483647;
				public static GT_Achievements achievements;
/* 132:    */   
/* 133:    */   static
/* 134:    */   {
/* 135: 75 */     if ((508 != GregTech_API.VERSION) || (508 != GT_ModHandler.VERSION) || (508 != GT_OreDictUnificator.VERSION) || (508 != GT_Recipe.VERSION) || (508 != GT_Utility.VERSION) || (508 != GT_RecipeRegistrator.VERSION) || (508 != Element.VERSION) || (508 != Materials.VERSION) || (508 != OrePrefixes.VERSION)) {
/* 136: 84 */       throw new GT_ItsNotMyFaultException("One of your Mods included GregTech-API Files inside it's download, mention this to the Mod Author, who does this bad thing, and tell him/her to use reflection. I have added a Version check, to prevent Authors from breaking my Mod that way.");
/* 137:    */     }
/* 138:    */   }
/* 139:    */   
/* 140:    */   public GT_Mod()
/* 141:    */   {
/* 142:    */     try
/* 143:    */     {
/* 144: 89 */       Class.forName("ic2.core.IC2").getField("enableOreDictCircuit").set(null, Boolean.valueOf(true));
/* 145:    */     }
/* 146:    */     catch (Throwable e) {}
/* 147:    */     try
/* 148:    */     {
/* 149: 90 */       Class.forName("ic2.core.IC2").getField("enableCraftingBucket").set(null, Boolean.valueOf(false));
/* 150:    */     }
/* 151:    */     catch (Throwable e) {}
/* 152:    */     try
/* 153:    */     {
/* 154: 91 */       Class.forName("ic2.core.IC2").getField("enableEnergyInStorageBlockItems").set(null, Boolean.valueOf(false));
/* 155:    */     }
/* 156:    */     catch (Throwable e) {}
/* 157: 92 */     GT_Values.GT = this;
/* 158: 93 */     GT_Values.DW = new GT_DummyWorld();
/* 159: 94 */     GT_Values.NW = new GT_Network();
/* 160: 95 */     GregTech_API.sRecipeAdder = GT_Values.RA = new GT_RecipeAdder();
/* 161:    */     
/* 162: 97 */     Textures.BlockIcons.VOID.name();
/* 163: 98 */     Textures.ItemIcons.VOID.name();
/* 164:    */   }
/* 165:    */   
/* 166:    */   @Mod.EventHandler
/* 167:    */   public void onPreLoad(FMLPreInitializationEvent aEvent)
/* 168:    */   {
/* 169:103 */     if (GregTech_API.sPreloadStarted) {
/* 170:103 */       return;
/* 171:    */     }
/* 172:105 */     for (Runnable tRunnable : GregTech_API.sBeforeGTPreload) {
/* 173:    */       try
/* 174:    */       {
/* 175:107 */         tRunnable.run();
/* 176:    */       }
/* 177:    */       catch (Throwable e)
/* 178:    */       {
/* 179:109 */         e.printStackTrace(GT_Log.err);
/* 180:    */       }
/* 181:    */     }
/* 182:113 */     File tFile = new File(new File(aEvent.getModConfigurationDirectory(), "GregTech"), "GregTech.cfg");
/* 183:114 */     Configuration tMainConfig = new Configuration(tFile);
/* 184:115 */     tMainConfig.load();
/* 185:116 */     tFile = new File(new File(aEvent.getModConfigurationDirectory(), "GregTech"), "IDs.cfg");
/* 186:117 */     GT_Config.sConfigFileIDs = new Configuration(tFile);
/* 187:118 */     GT_Config.sConfigFileIDs.load();
/* 188:119 */     GT_Config.sConfigFileIDs.save();
/* 189:120 */     GregTech_API.sRecipeFile = new GT_Config(new Configuration(new File(new File(aEvent.getModConfigurationDirectory(), "GregTech"), "Recipes.cfg")));
/* 190:121 */     GregTech_API.sMachineFile = new GT_Config(new Configuration(new File(new File(aEvent.getModConfigurationDirectory(), "GregTech"), "MachineStats.cfg")));
/* 191:122 */     GregTech_API.sWorldgenFile = new GT_Config(new Configuration(new File(new File(aEvent.getModConfigurationDirectory(), "GregTech"), "WorldGeneration.cfg")));
/* 192:123 */     GregTech_API.sMaterialProperties = new GT_Config(new Configuration(new File(new File(aEvent.getModConfigurationDirectory(), "GregTech"), "MaterialProperties.cfg")));
/* 193:124 */     GregTech_API.sUnification = new GT_Config(new Configuration(new File(new File(aEvent.getModConfigurationDirectory(), "GregTech"), "Unification.cfg")));
/* 194:125 */     GregTech_API.sSpecialFile = new GT_Config(new Configuration(new File(new File(aEvent.getModConfigurationDirectory(), "GregTech"), "Other.cfg")));
/* 195:126 */     GregTech_API.sOPStuff = new GT_Config(new Configuration(new File(new File(aEvent.getModConfigurationDirectory(), "GregTech"), "OverpoweredStuff.cfg")));
/* 196:    */     
/* 197:128 */     GregTech_API.sClientDataFile = new GT_Config(new Configuration(new File(aEvent.getModConfigurationDirectory().getParentFile(), "GregTech.cfg")));
/* 198:    */     
/* 199:130 */     GT_Log.mLogFile = new File(aEvent.getModConfigurationDirectory().getParentFile(), "logs/GregTech.log");
/* 200:131 */     if (!GT_Log.mLogFile.exists()) {
/* 201:    */       try
/* 202:    */       {
/* 203:131 */         GT_Log.mLogFile.createNewFile();
/* 204:    */       }
/* 205:    */       catch (Throwable e) {}
/* 206:    */     }
/* 207:    */     try
/* 208:    */     {
/* 209:132 */       GT_Log.out = GT_Log.err = new PrintStream(GT_Log.mLogFile);
/* 210:    */     }
/* 211:    */     catch (FileNotFoundException e) {}
/* 212:134 */     GT_Log.mOreDictLogFile = new File(aEvent.getModConfigurationDirectory().getParentFile(), "logs/OreDict.log");
/* 213:135 */     if (!GT_Log.mOreDictLogFile.exists()) {
/* 214:    */       try
/* 215:    */       {
/* 216:135 */         GT_Log.mOreDictLogFile.createNewFile();
/* 217:    */       }
/* 218:    */       catch (Throwable e) {}
/* 219:    */     }
/* 220:137 */     if (tMainConfig.get("general", "LoggingPlayerActivity", true).getBoolean(true))
/* 221:    */     {
/* 222:138 */       GT_Log.mPlayerActivityLogFile = new File(aEvent.getModConfigurationDirectory().getParentFile(), "logs/PlayerActivity.log");
/* 223:139 */       if (!GT_Log.mPlayerActivityLogFile.exists()) {
/* 224:    */         try
/* 225:    */         {
/* 226:139 */           GT_Log.mPlayerActivityLogFile.createNewFile();
/* 227:    */         }
/* 228:    */         catch (Throwable e) {}
/* 229:    */       }
/* 230:    */       try
/* 231:    */       {
/* 232:140 */         GT_Log.pal = new PrintStream(GT_Log.mPlayerActivityLogFile);
/* 233:    */       }
/* 234:    */       catch (Throwable e) {}
/* 235:    */     }
/* 236:    */     try
/* 237:    */     {
/* 238:144 */       List<String> tList = ((GT_Log.LogBuffer)GT_Log.ore).mBufferedOreDictLog;
/* 239:145 */       GT_Log.ore = new PrintStream(GT_Log.mOreDictLogFile);
/* 240:146 */       GT_Log.ore.println("**********************************************************************");
/* 241:147 */       GT_Log.ore.println("* This is the complete Log of the GregTech OreDictionary Handler     *");
/* 242:148 */       GT_Log.ore.println("* Everything in the OreDict goes through it sometimes causing Errors *");
/* 243:149 */       GT_Log.ore.println("* These Errors are getting logged aswell as properly registered Ores *");
/* 244:150 */       GT_Log.ore.println("* If you see something fishy going on in this Log, such as improper  *");
/* 245:151 */       GT_Log.ore.println("* Items being registered, then mention it to the corresponding Mod   *");
/* 246:152 */       GT_Log.ore.println("* In case it mentions GregTech itself improperly registering Stuff   *");
/* 247:153 */       GT_Log.ore.println("* then please contact me about that immediatly                       *");
/* 248:154 */       GT_Log.ore.println("*                                                                    *");
/* 249:155 */       GT_Log.ore.println("* In case of something being 'ignored properly', that one isnt a Bug *");
/* 250:156 */       GT_Log.ore.println("**********************************************************************");
/* 251:    */       String tString;
/* 252:157 */       for (Iterator i$ = tList.iterator(); i$.hasNext(); GT_Log.ore.println(tString)) {
/* 253:157 */         tString = (String)i$.next();
/* 254:    */       }
/* 255:    */     }
/* 256:    */     catch (Throwable e) {}
/* 257:160 */     gregtechproxy.onPreLoad();
/* 258:    */     
/* 259:162 */     GT_Log.out.println("GT_Mod: Setting Configs");
/* 260:163 */     GT_Values.D1 = tMainConfig.get("general", "Debug", false).getBoolean(false);
/* 261:164 */     GT_Values.D2 = tMainConfig.get("general", "Debug2", false).getBoolean(false);
/* 262:    */     
/* 263:166 */     GregTech_API.TICKS_FOR_LAG_AVERAGING = tMainConfig.get("general", "TicksForLagAveragingWithScanner", 25).getInt(25);
/* 264:167 */     GregTech_API.MILLISECOND_THRESHOLD_UNTIL_LAG_WARNING = tMainConfig.get("general", "MillisecondsPassedInGTTileEntityUntilLagWarning", 100).getInt(100);
/* 265:169 */     if (tMainConfig.get("general", "disable_STDOUT", false).getBoolean(false)) {
/* 266:169 */       System.out.close();
/* 267:    */     }
/* 268:170 */     if (tMainConfig.get("general", "disable_STDERR", false).getBoolean(false)) {
/* 269:170 */       System.err.close();
/* 270:    */     }
/* 271:172 */     GregTech_API.sMachineExplosions = tMainConfig.get("machines", "machines_explosion_damage", true).getBoolean(false);
/* 272:173 */     GregTech_API.sMachineFlammable = tMainConfig.get("machines", "machines_flammable", true).getBoolean(false);
/* 273:174 */     GregTech_API.sMachineNonWrenchExplosions = tMainConfig.get("machines", "explosions_on_nonwrenching", true).getBoolean(false);
/* 274:175 */     GregTech_API.sMachineWireFire = tMainConfig.get("machines", "wirefire_on_explosion", true).getBoolean(false);
/* 275:176 */     GregTech_API.sMachineFireExplosions = tMainConfig.get("machines", "fire_causes_explosions", true).getBoolean(false);
/* 276:177 */     GregTech_API.sMachineRainExplosions = tMainConfig.get("machines", "rain_causes_explosions", true).getBoolean(false);
/* 277:178 */     GregTech_API.sMachineThunderExplosions = tMainConfig.get("machines", "lightning_causes_explosions", true).getBoolean(false);
/* 278:179 */     GregTech_API.sConstantEnergy = tMainConfig.get("machines", "constant_need_of_energy", true).getBoolean(false);
/* 279:180 */     GregTech_API.sColoredGUI = tMainConfig.get("machines", "colored_guis_when_painted", true).getBoolean(false);
/* 280:    */     
/* 281:182 */     GregTech_API.sTimber = tMainConfig.get("general", "timber_axe", false).getBoolean(false);
/* 282:183 */     GregTech_API.sDrinksAlwaysDrinkable = tMainConfig.get("general", "drinks_always_drinkable", false).getBoolean(false);
/* 283:184 */     GregTech_API.sDoShowAllItemsInCreative = tMainConfig.get("general", "show_all_metaitems_in_creative_and_NEI", false).getBoolean(false);
/* 284:185 */     GregTech_API.sMultiThreadedSounds = tMainConfig.get("general", "sound_multi_threading", false).getBoolean(false);
/* 285:187 */     for (Dyes tDye : Dyes.values()) {
/* 286:187 */       if ((tDye != Dyes._NULL) && (tDye.mIndex < 0))
/* 287:    */       {
/* 288:188 */         tDye.mRGBa[0] = ((short)Math.min(255, Math.max(0, GregTech_API.sClientDataFile.get("ColorModulation." + tDye, "R", tDye.mRGBa[0]))));
/* 289:189 */         tDye.mRGBa[1] = ((short)Math.min(255, Math.max(0, GregTech_API.sClientDataFile.get("ColorModulation." + tDye, "G", tDye.mRGBa[1]))));
/* 290:190 */         tDye.mRGBa[2] = ((short)Math.min(255, Math.max(0, GregTech_API.sClientDataFile.get("ColorModulation." + tDye, "B", tDye.mRGBa[2]))));
/* 291:    */       }
/* 292:    */     }
/* 293:193 */     gregtechproxy.mMaxEqualEntitiesAtOneSpot = tMainConfig.get("general", "MaxEqualEntitiesAtOneSpot", 3).getInt(3);
/* 294:194 */     gregtechproxy.mSkeletonsShootGTArrows = tMainConfig.get("general", "SkeletonsShootGTArrows", 16).getInt(16);
/* 295:195 */     gregtechproxy.mFlintChance = tMainConfig.get("general", "FlintAndSteelChance", 30).getInt(30);
/* 296:196 */     gregtechproxy.mItemDespawnTime = tMainConfig.get("general", "ItemDespawnTime", 6000).getInt(6000);
/* 297:197 */     gregtechproxy.mDisableVanillaOres = tMainConfig.get("general", "DisableVanillaOres", true).getBoolean(true);
/* 298:198 */     gregtechproxy.mNerfDustCrafting = tMainConfig.get("general", "NerfDustCrafting", true).getBoolean(true);
/* 299:199 */     gregtechproxy.mIncreaseDungeonLoot = tMainConfig.get("general", "IncreaseDungeonLoot", true).getBoolean(true);
/* 300:200 */     gregtechproxy.mAxeWhenAdventure = tMainConfig.get("general", "AdventureModeStartingAxe", true).getBoolean(true);
/* 301:201 */     gregtechproxy.mHardcoreCables = tMainConfig.get("general", "HardCoreCableLoss", false).getBoolean(false);
/* 302:202 */     gregtechproxy.mSurvivalIntoAdventure = tMainConfig.get("general", "forceAdventureMode", false).getBoolean(false);
/* 303:203 */     gregtechproxy.mHungerEffect = tMainConfig.get("general", "AFK_Hunger", false).getBoolean(false);
/* 304:204 */     gregtechproxy.mHardRock = tMainConfig.get("general", "harderstone", false).getBoolean(false);
/* 305:205 */     gregtechproxy.mInventoryUnification = tMainConfig.get("general", "InventoryUnification", true).getBoolean(true);
/* 306:206 */     gregtechproxy.mCraftingUnification = tMainConfig.get("general", "CraftingUnification", true).getBoolean(true);
/* 307:207 */     gregtechproxy.mNerfedWoodPlank = tMainConfig.get("general", "WoodNeedsSawForCrafting", true).getBoolean(true);
/* 308:208 */     gregtechproxy.mNerfedVanillaTools = tMainConfig.get("general", "smallerVanillaToolDurability", true).getBoolean(true);
/* 309:209 */     gregtechproxy.mSortToTheEnd = tMainConfig.get("general", "EnsureToBeLoadedLast", true).getBoolean(true);
				  gregtechproxy.mDisableIC2Cables = tMainConfig.get("general", "DisableIC2Cables", false).getBoolean(false);
				  gregtechproxy.mAchievements = tMainConfig.get("general", "EnableAchievements", true).getBoolean(true);
				  
/* 310:211 */     if (tMainConfig.get("general", "hardermobspawners", true).getBoolean(true)) {
/* 311:211 */       Blocks.mob_spawner.setHardness(500.0F).setResistance(6000000.0F);
/* 312:    */     }
/* 313:213 */     gregtechproxy.mOnline = tMainConfig.get("general", "online", true).getBoolean(false);
/* 314:    */     
/* 315:215 */     gregtechproxy.mUpgradeCount = Math.min(64, Math.max(1, tMainConfig.get("features", "UpgradeStacksize", 4).getInt()));
/* 316:217 */     for (OrePrefixes tPrefix : OrePrefixes.values()) {
/* 317:218 */       if (tPrefix.mIsUsedForOreProcessing) {
/* 318:219 */         tPrefix.mDefaultStackSize = ((byte)Math.min(64, Math.max(1, tMainConfig.get("features", "MaxOreStackSize", 64).getInt())));
/* 319:220 */       } else if (tPrefix == OrePrefixes.plank) {
/* 320:221 */         tPrefix.mDefaultStackSize = ((byte)Math.min(64, Math.max(16, tMainConfig.get("features", "MaxPlankStackSize", 64).getInt())));
/* 321:222 */       } else if ((tPrefix == OrePrefixes.wood) || (tPrefix == OrePrefixes.treeLeaves) || (tPrefix == OrePrefixes.treeSapling) || (tPrefix == OrePrefixes.log)) {
/* 322:223 */         tPrefix.mDefaultStackSize = ((byte)Math.min(64, Math.max(16, tMainConfig.get("features", "MaxLogStackSize", 64).getInt())));
/* 323:224 */       } else if (tPrefix.mIsUsedForBlocks) {
/* 324:225 */         tPrefix.mDefaultStackSize = ((byte)Math.min(64, Math.max(16, tMainConfig.get("features", "MaxOtherBlockStackSize", 64).getInt())));
/* 325:    */       }
/* 326:    */     }
/* 327:229 */     //GT_Config.troll = (Calendar.getInstance().get(2) + 1 == 4) && (Calendar.getInstance().get(5) >= 1) && (Calendar.getInstance().get(5) <= 2);
/* 328:    */     
/* 329:231 */     Materials.init(GregTech_API.sMaterialProperties);
/* 330:    */     
/* 331:233 */     GT_Log.out.println("GT_Mod: Saving Main Config");
/* 332:234 */     tMainConfig.save();
/* 333:    */     
/* 334:236 */     GT_Log.out.println("GT_Mod: Generating Lang-File");
/* 335:237 */     GT_LanguageManager.sEnglishFile = new Configuration(new File(aEvent.getModConfigurationDirectory().getParentFile(), "GregTech.lang"));
/* 336:238 */     GT_LanguageManager.sEnglishFile.load();
/* 337:    */     
/* 338:240 */     GT_Log.out.println("GT_Mod: Removing all original Scrapbox Drops.");
/* 339:    */     try
/* 340:    */     {
/* 341:242 */       GT_Utility.getField("ic2.core.item.ItemScrapbox$Drop", "topChance", true, true).set(null, Integer.valueOf(0));
/* 342:243 */       ((List)GT_Utility.getFieldContent(GT_Utility.getFieldContent("ic2.api.recipe.Recipes", "scrapboxDrops", true, true), "drops", true, true)).clear();
/* 343:    */     }
/* 344:    */     catch (Throwable e)
/* 345:    */     {
/* 346:245 */       if (GT_Values.D1) {
/* 347:245 */         e.printStackTrace(GT_Log.err);
/* 348:    */       }
/* 349:    */     }
/* 350:248 */     GT_Log.out.println("GT_Mod: Adding Scrap with a Weight of 200.0F to the Scrapbox Drops.");
/* 351:249 */     GT_ModHandler.addScrapboxDrop(200.0F, GT_ModHandler.getIC2Item("scrap", 1L));
/* 352:    */     
/* 353:251 */     EntityRegistry.registerModEntity(GT_Entity_Arrow.class, "GT_Entity_Arrow", 1, GT_Values.GT, 160, 1, true);
/* 354:252 */     EntityRegistry.registerModEntity(GT_Entity_Arrow_Potion.class, "GT_Entity_Arrow_Potion", 2, GT_Values.GT, 160, 1, true);
/* 355:    */     
/* 356:254 */     new Enchantment_EnderDamage();
/* 357:255 */     new Enchantment_Radioactivity();
/* 358:    */     
/* 359:257 */     new GT_Loader_OreProcessing().run();
/* 360:258 */     new GT_Loader_OreDictionary().run();
/* 361:259 */     new GT_Loader_ItemData().run();
/* 362:260 */     new GT_Loader_Item_Block_And_Fluid().run();
/* 363:261 */     new GT_Loader_MetaTileEntities().run();
/* 364:    */     
/* 365:263 */     new GT_Loader_CircuitBehaviors().run();
/* 366:264 */     new GT_CoverBehaviorLoader().run();
/* 367:265 */     new GT_SonictronLoader().run();
/* 368:267 */     if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.gregtechrecipes, "SolarPanel", true)) {
/* 369:267 */       GT_ModHandler.addCraftingRecipe(ItemList.Cover_SolarPanel.get(1L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "SGS", "CPC", Character.valueOf('C'), OrePrefixes.circuit.get(Materials.Basic), Character.valueOf('G'), new ItemStack(Blocks.glass_pane, 1), Character.valueOf('P'), OrePrefixes.plateAlloy.get(Materials.Carbon), Character.valueOf('S'), OrePrefixes.plate.get(Materials.Silicon) });
/* 370:    */     }
/* 371:268 */     if (GregTech_API.sOPStuff.get(ConfigCategories.Recipes.gregtechrecipes, "SolarPanel8V", false)) {
/* 372:268 */       GT_ModHandler.addCraftingRecipe(ItemList.Cover_SolarPanel_8V.get(1L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "SSS", "STS", "SSS", Character.valueOf('S'), ItemList.Cover_SolarPanel, Character.valueOf('T'), OrePrefixes.circuit.get(Materials.Advanced) });
/* 373:    */     }
/* 374:269 */     if (GregTech_API.sOPStuff.get(ConfigCategories.Recipes.gregtechrecipes, "SolarPanelLV", false)) {
/* 375:269 */       GT_ModHandler.addCraftingRecipe(ItemList.Cover_SolarPanel_LV.get(1L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { " S ", "STS", " S ", Character.valueOf('S'), ItemList.Cover_SolarPanel_8V, Character.valueOf('T'), ItemList.Transformer_LV_ULV });
/* 376:    */     }
/* 377:270 */     if (GregTech_API.sOPStuff.get(ConfigCategories.Recipes.gregtechrecipes, "SolarPanelMV", false)) {
/* 378:270 */       GT_ModHandler.addCraftingRecipe(ItemList.Cover_SolarPanel_MV.get(1L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { " S ", "STS", " S ", Character.valueOf('S'), ItemList.Cover_SolarPanel_LV, Character.valueOf('T'), ItemList.Transformer_MV_LV });
/* 379:    */     }
/* 380:271 */     if (GregTech_API.sOPStuff.get(ConfigCategories.Recipes.gregtechrecipes, "SolarPanelHV", false)) {
/* 381:271 */       GT_ModHandler.addCraftingRecipe(ItemList.Cover_SolarPanel_HV.get(1L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { " S ", "STS", " S ", Character.valueOf('S'), ItemList.Cover_SolarPanel_MV, Character.valueOf('T'), ItemList.Transformer_HV_MV });
/* 382:    */     }
/* 383:272 */     if (GregTech_API.sOPStuff.get(ConfigCategories.Recipes.gregtechrecipes, "SolarPanelEV", false)) {
/* 384:272 */       GT_ModHandler.addCraftingRecipe(ItemList.Cover_SolarPanel_EV.get(1L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { " S ", "STS", " S ", Character.valueOf('S'), ItemList.Cover_SolarPanel_HV, Character.valueOf('T'), ItemList.Transformer_EV_HV });
/* 385:    */     }
/* 386:273 */     if (GregTech_API.sOPStuff.get(ConfigCategories.Recipes.gregtechrecipes, "SolarPanelIV", false)) {
/* 387:273 */       GT_ModHandler.addCraftingRecipe(ItemList.Cover_SolarPanel_IV.get(1L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { " S ", "STS", " S ", Character.valueOf('S'), ItemList.Cover_SolarPanel_EV, Character.valueOf('T'), ItemList.Transformer_IV_EV });
/* 388:    */     }
/* 389:274 */     if (GregTech_API.sOPStuff.get(ConfigCategories.Recipes.gregtechrecipes, "SolarPanelLuV", false)) {
/* 390:274 */       GT_ModHandler.addCraftingRecipe(ItemList.Cover_SolarPanel_LuV.get(1L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { " S ", "STS", " S ", Character.valueOf('S'), ItemList.Cover_SolarPanel_IV, Character.valueOf('T'), ItemList.Transformer_LuV_IV });
/* 391:    */     }
/* 392:275 */     if (GregTech_API.sOPStuff.get(ConfigCategories.Recipes.gregtechrecipes, "SolarPanelZPM", false)) {
/* 393:275 */       GT_ModHandler.addCraftingRecipe(ItemList.Cover_SolarPanel_ZPM.get(1L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { " S ", "STS", " S ", Character.valueOf('S'), ItemList.Cover_SolarPanel_LuV, Character.valueOf('T'), ItemList.Transformer_ZPM_LuV });
/* 394:    */     }
/* 395:276 */     if (GregTech_API.sOPStuff.get(ConfigCategories.Recipes.gregtechrecipes, "SolarPanelUV", false)) {
/* 396:276 */       GT_ModHandler.addCraftingRecipe(ItemList.Cover_SolarPanel_UV.get(1L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { " S ", "STS", " S ", Character.valueOf('S'), ItemList.Cover_SolarPanel_ZPM, Character.valueOf('T'), ItemList.Transformer_UV_ZPM });
/* 397:    */     }
/* 398:278 */     if (gregtechproxy.mSortToTheEnd) {
/* 399:    */       try
/* 400:    */       {
/* 401:280 */         GT_Log.out.println("GT_Mod: Sorting GregTech to the end of the Mod List for further processing.");
/* 402:281 */         LoadController tLoadController = (LoadController)GT_Utility.getFieldContent(Loader.instance(), "modController", true, true);
/* 403:282 */         List<ModContainer> tModList = tLoadController.getActiveModList();List<ModContainer> tNewModsList = new ArrayList();
/* 404:283 */         ModContainer tGregTech = null;
/* 405:284 */         for (short i = 0; i < tModList.size(); i = (short)(i + 1))
/* 406:    */         {
/* 407:285 */           ModContainer tMod = (ModContainer)tModList.get(i);
/* 408:286 */           if (tMod.getModId().equalsIgnoreCase("gregtech")) {
/* 409:287 */             tGregTech = tMod;
/* 410:    */           } else {
/* 411:290 */             tNewModsList.add(tMod);
/* 412:    */           }
/* 413:    */         }
/* 414:292 */         if (tGregTech != null) {
/* 415:292 */           tNewModsList.add(tGregTech);
/* 416:    */         }
/* 417:293 */         GT_Utility.getField(tLoadController, "activeModList", true, true).set(tLoadController, tNewModsList);
/* 418:    */       }
/* 419:    */       catch (Throwable e)
/* 420:    */       {
/* 421:295 */         if (GT_Values.D1) {
/* 422:295 */           e.printStackTrace(GT_Log.err);
/* 423:    */         }
/* 424:    */       }
/* 425:    */     }
/* 426:299 */     GregTech_API.sPreloadFinished = true;
/* 427:300 */     GT_Log.out.println("GT_Mod: Preload-Phase finished!");
/* 428:301 */     GT_Log.ore.println("GT_Mod: Preload-Phase finished!");
/* 429:302 */     for (Runnable tRunnable : GregTech_API.sAfterGTPreload) {
/* 430:    */       try
/* 431:    */       {
/* 432:304 */         tRunnable.run();
/* 433:    */       }
/* 434:    */       catch (Throwable e)
/* 435:    */       {
/* 436:306 */         e.printStackTrace(GT_Log.err);
/* 437:    */       }
/* 438:    */     }
/* 439:    */   }
/* 440:    */   
/* 441:    */   @Mod.EventHandler
/* 442:    */   public void onLoad(FMLInitializationEvent aEvent)
/* 443:    */   {
/* 444:313 */     if (GregTech_API.sLoadStarted) {
/* 445:313 */       return;
/* 446:    */     }
/* 447:315 */     for (Runnable tRunnable : GregTech_API.sBeforeGTLoad) {
/* 448:    */       try
/* 449:    */       {
/* 450:317 */         tRunnable.run();
/* 451:    */       }
/* 452:    */       catch (Throwable e)
/* 453:    */       {
/* 454:319 */         e.printStackTrace(GT_Log.err);
/* 455:    */       }
/* 456:    */     }
/* 457:323 */     gregtechproxy.onLoad();
/* 458:325 */     if (gregtechproxy.mSortToTheEnd)
/* 459:    */     {
/* 460:326 */       new GT_ItemIterator().run();
/* 461:327 */       gregtechproxy.registerUnificationEntries();
/* 462:328 */       new GT_FuelLoader().run();
/* 463:    */     }
/* 464:333 */     GregTech_API.sLoadFinished = true;
/* 465:334 */     GT_Log.out.println("GT_Mod: Load-Phase finished!");
/* 466:335 */     GT_Log.ore.println("GT_Mod: Load-Phase finished!");
/* 467:336 */     for (Runnable tRunnable : GregTech_API.sAfterGTLoad) {
/* 468:    */       try
/* 469:    */       {
/* 470:338 */         tRunnable.run();
/* 471:    */       }
/* 472:    */       catch (Throwable e)
/* 473:    */       {
/* 474:340 */         e.printStackTrace(GT_Log.err);
/* 475:    */       }
/* 476:    */     }
/* 477:    */   }
/* 478:    */   
/* 479:    */   @Mod.EventHandler
/* 480:    */   public void onPostLoad(FMLPostInitializationEvent aEvent)
/* 481:    */   {
/* 482:347 */     if (GregTech_API.sPostloadStarted) {
/* 483:347 */       return;
/* 484:    */     }
/* 485:349 */     for (Runnable tRunnable : GregTech_API.sBeforeGTPostload) {
/* 486:    */       try
/* 487:    */       {
/* 488:351 */         tRunnable.run();
/* 489:    */       }
/* 490:    */       catch (Throwable e)
/* 491:    */       {
/* 492:353 */         e.printStackTrace(GT_Log.err);
/* 493:    */       }
/* 494:    */     }
/* 495:357 */     gregtechproxy.onPostLoad();
/* 496:359 */     if (gregtechproxy.mSortToTheEnd)
/* 497:    */     {
/* 498:360 */       gregtechproxy.registerUnificationEntries();
/* 499:    */     }
/* 500:    */     else
/* 501:    */     {
/* 502:362 */       new GT_ItemIterator().run();
/* 503:363 */       gregtechproxy.registerUnificationEntries();
/* 504:364 */       new GT_FuelLoader().run();
/* 505:    */     }
/* 506:367 */     new GT_BookAndLootLoader().run();
/* 507:368 */     new GT_ItemMaxStacksizeLoader().run();
/* 508:369 */     new GT_BlockResistanceLoader().run();
/* 509:370 */     new GT_RecyclerBlacklistLoader().run();
/* 510:371 */     new GT_MinableRegistrator().run();
/* 511:372 */     new GT_MachineRecipeLoader().run();
/* 512:373 */     new GT_ScrapboxDropLoader().run();
/* 513:374 */     new GT_CropLoader().run();
/* 514:375 */     new GT_Worldgenloader().run();
/* 515:376 */     new GT_CoverLoader().run();
/* 516:    */     
/* 517:378 */     GT_RecipeRegistrator.registerUsagesForMaterials(new ItemStack(Blocks.planks, 1), null, false);
/* 518:379 */     GT_RecipeRegistrator.registerUsagesForMaterials(new ItemStack(Blocks.cobblestone, 1), null, false);
/* 519:380 */     GT_RecipeRegistrator.registerUsagesForMaterials(new ItemStack(Blocks.stone, 1), null, false);
/* 520:381 */     GT_RecipeRegistrator.registerUsagesForMaterials(new ItemStack(Items.leather, 1), null, false);
/* 521:    */     
/* 522:383 */     GT_OreDictUnificator.addItemData(GT_ModHandler.getRecipeOutput(new ItemStack[] { null, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Tin, 1L), null, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Tin, 1L), null, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Tin, 1L), null, null, null }), new ItemData(Materials.Tin, 10886400L, new MaterialStack[0]));
/* 523:385 */     if (!GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.storageblockcrafting, "tile.glowstone", false)) {
/* 524:385 */       GT_ModHandler.removeRecipe(new ItemStack[] { new ItemStack(Items.glowstone_dust, 1), new ItemStack(Items.glowstone_dust, 1), null, new ItemStack(Items.glowstone_dust, 1), new ItemStack(Items.glowstone_dust, 1) });
/* 525:    */     }
/* 526:386 */     GT_ModHandler.removeRecipe(new ItemStack[] { new ItemStack(Blocks.wooden_slab, 1, 0), new ItemStack(Blocks.wooden_slab, 1, 1), new ItemStack(Blocks.wooden_slab, 1, 2) });
/* 527:387 */     GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.wooden_slab, 6, 0), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { "WWW", Character.valueOf('W'), new ItemStack(Blocks.planks, 1, 0) });
/* 528:    */     
/* 529:389 */     GT_Log.out.println("GT_Mod: Activating OreDictionary Handler, this can take some time, as it scans the whole OreDictionary");
/* 530:390 */     FMLLog.info("If your Log stops here, you were too impatient. Wait a bit more next time, before killing Minecraft with the Task Manager.", new Object[0]);
/* 531:391 */     gregtechproxy.activateOreDictHandler();
/* 532:392 */     FMLLog.info("Congratulations, you have been waiting long enough. Have a Cake.", new Object[0]);
/* 533:393 */     GT_Log.out.println("GT_Mod: " + GT_ModHandler.sSingleNonBlockDamagableRecipeList.size() + " Recipes were left unused.");
/* 534:395 */     if (GT_Values.D1)
/* 535:    */     {
/* 536:    */       IRecipe tRecipe;
/* 537:395 */       for (Iterator i$ = GT_ModHandler.sSingleNonBlockDamagableRecipeList.iterator(); i$.hasNext(); GT_Log.out.println("=> " + tRecipe.getRecipeOutput().getDisplayName())) {
/* 538:395 */         tRecipe = (IRecipe)i$.next();
/* 539:    */       }
/* 540:    */     }
/* 541:397 */     new GT_CraftingRecipeLoader().run();
/* 542:399 */     if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "ic2forgehammer", true)) {
/* 543:399 */       GT_ModHandler.removeRecipeByOutput(ItemList.IC2_ForgeHammer.getWildcard(1L, new Object[0]));
/* 544:    */     }
/* 545:401 */     GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("machine", 1L));
/* 546:402 */     GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("machine", 1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "RRR", "RwR", "RRR", Character.valueOf('R'), OrePrefixes.plate.get(Materials.Iron) });
/* 547:404 */     for (FluidContainerRegistry.FluidContainerData tData : FluidContainerRegistry.getRegisteredFluidContainerData()) {
/* 548:405 */       if ((tData.filledContainer.getItem() == Items.potionitem) && (tData.filledContainer.getItemDamage() == 0))
/* 549:    */       {
/* 550:406 */         GT_Recipe.GT_Recipe_Map.sFluidCannerRecipes.addRecipe(true, new ItemStack[] { ItemList.Bottle_Empty.get(1L, new Object[0]) }, new ItemStack[] { new ItemStack(Items.potionitem, 1, 0) }, null, new FluidStack[] { Materials.Water.getFluid(250L) }, null, 4, 1, 0);
/* 551:407 */         GT_Recipe.GT_Recipe_Map.sFluidCannerRecipes.addRecipe(true, new ItemStack[] { new ItemStack(Items.potionitem, 1, 0) }, new ItemStack[] { ItemList.Bottle_Empty.get(1L, new Object[0]) }, null, null, null, 4, 1, 0);
/* 552:    */       }
/* 553:    */       else
/* 554:    */       {
/* 555:409 */         GT_Recipe.GT_Recipe_Map.sFluidCannerRecipes.addRecipe(true, new ItemStack[] { tData.emptyContainer }, new ItemStack[] { tData.filledContainer }, null, new FluidStack[] { tData.fluid }, null, tData.fluid.amount / 62, 1, 0);
/* 556:410 */         GT_Recipe.GT_Recipe_Map.sFluidCannerRecipes.addRecipe(true, new ItemStack[] { tData.filledContainer }, new ItemStack[] { GT_Utility.getContainerItem(tData.filledContainer, true) }, null, null, new FluidStack[] { tData.fluid }, tData.fluid.amount / 62, 1, 0);
/* 557:    */       }
/* 558:    */     }
/* 559:    */     try
/* 560:    */     {
/* 561:415 */       for (Object tRecipe : MachineCentrifuge.RecipeManager.recipes)
/* 562:    */       {
/* 563:416 */         Integer[] tOriginalChances = (Integer[])((MachineCentrifuge.Recipe)tRecipe).products.values().toArray(new Integer[((MachineCentrifuge.Recipe)tRecipe).products.size()]);
/* 564:417 */         ItemStack[] tOutputs = (ItemStack[])((MachineCentrifuge.Recipe)tRecipe).products.keySet().toArray(new ItemStack[((MachineCentrifuge.Recipe)tRecipe).products.size()]);
/* 565:418 */         int[] tChances = new int[tOriginalChances.length];
/* 566:419 */         for (int i = 0; i < tOriginalChances.length; i++) {
/* 567:419 */           tChances[i] = (tOriginalChances[i].intValue() * 100);
/* 568:    */         }
/* 569:420 */         GT_Recipe.GT_Recipe_Map.sCentrifugeRecipes.addRecipe(true, new ItemStack[] { ((MachineCentrifuge.Recipe)tRecipe).resource }, tOutputs, null, tChances, null, null, 128, 5, 0);
/* 570:    */       }
/* 571:    */     }
/* 572:    */     catch (Throwable e)
/* 573:    */     {
/* 574:423 */       if (GT_Values.D1) {
/* 575:423 */         e.printStackTrace(GT_Log.err);
/* 576:    */       }
/* 577:    */     }
/* 578:    */     try
/* 579:    */     {
/* 580:427 */       for (Object tRecipe : MachineSqueezer.RecipeManager.recipes) {
/* 581:428 */         if ((((MachineSqueezer.Recipe)tRecipe).resources.length == 1) && (GT_Utility.getFluidForFilledItem(((MachineSqueezer.Recipe)tRecipe).resources[0], true) == null)) {
/* 582:429 */           GT_Recipe.GT_Recipe_Map.sFluidExtractionRecipes.addRecipe(true, new ItemStack[] { ((MachineSqueezer.Recipe)tRecipe).resources[0] }, new ItemStack[] { ((MachineSqueezer.Recipe)tRecipe).remnants }, null, new int[] { ((MachineSqueezer.Recipe)tRecipe).chance * 100 }, null, new FluidStack[] { ((MachineSqueezer.Recipe)tRecipe).liquid }, 400, 2, 0);
/* 583:    */         }
/* 584:    */       }
/* 585:    */     }
/* 586:    */     catch (Throwable e)
/* 587:    */     {
/* 588:432 */       if (GT_Values.D1) {
/* 589:432 */         e.printStackTrace(GT_Log.err);
/* 590:    */       }
/* 591:    */     }
/* 592:435 */     String tName = "";
/* 593:437 */     if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "ic2_" + (tName = "blastfurnace"), true)) {
/* 594:437 */       GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(tName, 1L));
/* 595:    */     }
/* 596:438 */     if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "ic2_" + (tName = "blockcutter"), true)) {
/* 597:438 */       GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(tName, 1L));
/* 598:    */     }
/* 599:439 */     if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "ic2_" + (tName = "inductionFurnace"), true)) {
/* 600:439 */       GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(tName, 1L));
/* 601:    */     }
/* 602:440 */     if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "ic2_" + (tName = "generator"), false)) {
/* 603:440 */       GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(tName, 1L));
/* 604:    */     }
/* 605:441 */     if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "ic2_" + (tName = "windMill"), true)) {
/* 606:441 */       GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(tName, 1L));
/* 607:    */     }
/* 608:442 */     if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "ic2_" + (tName = "waterMill"), true)) {
/* 609:442 */       GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(tName, 1L));
/* 610:    */     }
/* 611:443 */     if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "ic2_" + (tName = "solarPanel"), true)) {
/* 612:443 */       GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(tName, 1L));
/* 613:    */     }
/* 614:444 */     if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "ic2_" + (tName = "centrifuge"), true)) {
/* 615:444 */       GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(tName, 1L));
/* 616:    */     }
/* 617:445 */     if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "ic2_" + (tName = "electrolyzer"), false)) {
/* 618:445 */       GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(tName, 1L));
/* 619:    */     }
/* 620:446 */     if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "ic2_" + (tName = "compressor"), true)) {
/* 621:446 */       GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(tName, 1L));
/* 622:    */     }
/* 623:447 */     if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "ic2_" + (tName = "electroFurnace"), true)) {
/* 624:447 */       GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(tName, 1L));
/* 625:    */     }
/* 626:448 */     if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "ic2_" + (tName = "extractor"), true)) {
/* 627:448 */       GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(tName, 1L));
/* 628:    */     }
/* 629:449 */     if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "ic2_" + (tName = "macerator"), true)) {
/* 630:449 */       GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(tName, 1L));
/* 631:    */     }
/* 632:450 */     if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "ic2_" + (tName = "recycler"), true)) {
/* 633:450 */       GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(tName, 1L));
/* 634:    */     }
/* 635:451 */     if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "ic2_" + (tName = "metalformer"), true)) {
/* 636:451 */       GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(tName, 1L));
/* 637:    */     }
/* 638:452 */     if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "ic2_" + (tName = "orewashingplant"), true)) {
/* 639:452 */       GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(tName, 1L));
/* 640:    */     }
/* 641:453 */     if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "ic2_" + (tName = "massFabricator"), true)) {
/* 642:453 */       GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(tName, 1L));
/* 643:    */     }
/* 644:455 */     if (gregtechproxy.mNerfedVanillaTools)
/* 645:    */     {
/* 646:456 */       GT_Log.out.println("GT_Mod: Nerfing Vanilla Tool Durability");
/* 647:457 */       Items.wooden_sword.setMaxDamage(12);
/* 648:458 */       Items.wooden_pickaxe.setMaxDamage(12);
/* 649:459 */       Items.wooden_shovel.setMaxDamage(12);
/* 650:460 */       Items.wooden_axe.setMaxDamage(12);
/* 651:461 */       Items.wooden_hoe.setMaxDamage(12);
/* 652:    */       
/* 653:463 */       Items.stone_sword.setMaxDamage(48);
/* 654:464 */       Items.stone_pickaxe.setMaxDamage(48);
/* 655:465 */       Items.stone_shovel.setMaxDamage(48);
/* 656:466 */       Items.stone_axe.setMaxDamage(48);
/* 657:467 */       Items.stone_hoe.setMaxDamage(48);
/* 658:    */       
/* 659:469 */       Items.iron_sword.setMaxDamage(256);
/* 660:470 */       Items.iron_pickaxe.setMaxDamage(256);
/* 661:471 */       Items.iron_shovel.setMaxDamage(256);
/* 662:472 */       Items.iron_axe.setMaxDamage(256);
/* 663:473 */       Items.iron_hoe.setMaxDamage(256);
/* 664:    */       
/* 665:475 */       Items.golden_sword.setMaxDamage(24);
/* 666:476 */       Items.golden_pickaxe.setMaxDamage(24);
/* 667:477 */       Items.golden_shovel.setMaxDamage(24);
/* 668:478 */       Items.golden_axe.setMaxDamage(24);
/* 669:479 */       Items.golden_hoe.setMaxDamage(24);
/* 670:    */       
/* 671:481 */       Items.diamond_sword.setMaxDamage(768);
/* 672:482 */       Items.diamond_pickaxe.setMaxDamage(768);
/* 673:483 */       Items.diamond_shovel.setMaxDamage(768);
/* 674:484 */       Items.diamond_axe.setMaxDamage(768);
/* 675:485 */       Items.diamond_hoe.setMaxDamage(768);
/* 676:    */     }
/* 677:488 */     GT_Log.out.println("GT_Mod: Adding buffered Recipes.");
/* 678:489 */     GT_ModHandler.stopBufferingCraftingRecipes();
/* 679:    */     
/* 680:491 */     GT_Log.out.println("GT_Mod: Saving Lang File.");
/* 681:492 */     GT_LanguageManager.sEnglishFile.save();
/* 682:493 */     GregTech_API.sPostloadFinished = true;
/* 683:494 */     GT_Log.out.println("GT_Mod: PostLoad-Phase finished!");
/* 684:495 */     GT_Log.ore.println("GT_Mod: PostLoad-Phase finished!");
/* 685:497 */     for (Runnable tRunnable : GregTech_API.sAfterGTPostload) {
/* 686:    */       try
/* 687:    */       {
/* 688:499 */         tRunnable.run();
/* 689:    */       }
/* 690:    */       catch (Throwable e)
/* 691:    */       {
/* 692:501 */         e.printStackTrace(GT_Log.err);
/* 693:    */       }
/* 694:    */     }
/* 695:505 */     GT_Log.out.println("GT_Mod: Adding Fake Recipes for NEI");
/* 696:507 */     if (ItemList.FR_Bee_Drone.get(1L, new Object[0]) != null) {
/* 697:507 */       GT_Recipe.GT_Recipe_Map.sScannerFakeRecipes.addFakeRecipe(false, new ItemStack[] { ItemList.FR_Bee_Drone.getWildcard(1L, new Object[0]) }, new ItemStack[] { ItemList.FR_Bee_Drone.getWithName(1L, "Scanned Drone", new Object[0]) }, null, new FluidStack[] { Materials.Honey.getFluid(50L) }, null, 500, 2, 0);
/* 698:    */     }
/* 699:508 */     if (ItemList.FR_Bee_Princess.get(1L, new Object[0]) != null) {
/* 700:508 */       GT_Recipe.GT_Recipe_Map.sScannerFakeRecipes.addFakeRecipe(false, new ItemStack[] { ItemList.FR_Bee_Princess.getWildcard(1L, new Object[0]) }, new ItemStack[] { ItemList.FR_Bee_Princess.getWithName(1L, "Scanned Princess", new Object[0]) }, null, new FluidStack[] { Materials.Honey.getFluid(50L) }, null, 500, 2, 0);
/* 701:    */     }
/* 702:509 */     if (ItemList.FR_Bee_Queen.get(1L, new Object[0]) != null) {
/* 703:509 */       GT_Recipe.GT_Recipe_Map.sScannerFakeRecipes.addFakeRecipe(false, new ItemStack[] { ItemList.FR_Bee_Queen.getWildcard(1L, new Object[0]) }, new ItemStack[] { ItemList.FR_Bee_Queen.getWithName(1L, "Scanned Queen", new Object[0]) }, null, new FluidStack[] { Materials.Honey.getFluid(50L) }, null, 500, 2, 0);
/* 704:    */     }
/* 705:510 */     if (ItemList.FR_Tree_Sapling.get(1L, new Object[0]) != null) {
/* 706:510 */       GT_Recipe.GT_Recipe_Map.sScannerFakeRecipes.addFakeRecipe(false, new ItemStack[] { ItemList.FR_Tree_Sapling.getWildcard(1L, new Object[0]) }, new ItemStack[] { ItemList.FR_Tree_Sapling.getWithName(1L, "Scanned Sapling", new Object[0]) }, null, new FluidStack[] { Materials.Honey.getFluid(50L) }, null, 500, 2, 0);
/* 707:    */     }
/* 708:511 */     if (ItemList.FR_Butterfly.get(1L, new Object[0]) != null) {
/* 709:511 */       GT_Recipe.GT_Recipe_Map.sScannerFakeRecipes.addFakeRecipe(false, new ItemStack[] { ItemList.FR_Butterfly.getWildcard(1L, new Object[0]) }, new ItemStack[] { ItemList.FR_Butterfly.getWithName(1L, "Scanned Butterfly", new Object[0]) }, null, new FluidStack[] { Materials.Honey.getFluid(50L) }, null, 500, 2, 0);
/* 710:    */     }
/* 711:512 */     if (ItemList.FR_Larvae.get(1L, new Object[0]) != null) {
/* 712:512 */       GT_Recipe.GT_Recipe_Map.sScannerFakeRecipes.addFakeRecipe(false, new ItemStack[] { ItemList.FR_Larvae.getWildcard(1L, new Object[0]) }, new ItemStack[] { ItemList.FR_Larvae.getWithName(1L, "Scanned Larvae", new Object[0]) }, null, new FluidStack[] { Materials.Honey.getFluid(50L) }, null, 500, 2, 0);
/* 713:    */     }
/* 714:513 */     if (ItemList.FR_Serum.get(1L, new Object[0]) != null) {
/* 715:513 */       GT_Recipe.GT_Recipe_Map.sScannerFakeRecipes.addFakeRecipe(false, new ItemStack[] { ItemList.FR_Serum.getWildcard(1L, new Object[0]) }, new ItemStack[] { ItemList.FR_Serum.getWithName(1L, "Scanned Serum", new Object[0]) }, null, new FluidStack[] { Materials.Honey.getFluid(50L) }, null, 500, 2, 0);
/* 716:    */     }
/* 717:514 */     if (ItemList.FR_Caterpillar.get(1L, new Object[0]) != null) {
/* 718:514 */       GT_Recipe.GT_Recipe_Map.sScannerFakeRecipes.addFakeRecipe(false, new ItemStack[] { ItemList.FR_Caterpillar.getWildcard(1L, new Object[0]) }, new ItemStack[] { ItemList.FR_Caterpillar.getWithName(1L, "Scanned Caterpillar", new Object[0]) }, null, new FluidStack[] { Materials.Honey.getFluid(50L) }, null, 500, 2, 0);
/* 719:    */     }
/* 720:515 */     if (ItemList.FR_PollenFertile.get(1L, new Object[0]) != null) {
/* 721:515 */       GT_Recipe.GT_Recipe_Map.sScannerFakeRecipes.addFakeRecipe(false, new ItemStack[] { ItemList.FR_PollenFertile.getWildcard(1L, new Object[0]) }, new ItemStack[] { ItemList.FR_PollenFertile.getWithName(1L, "Scanned Pollen", new Object[0]) }, null, new FluidStack[] { Materials.Honey.getFluid(50L) }, null, 500, 2, 0);
/* 722:    */     }
/* 723:516 */     if (ItemList.IC2_Crop_Seeds.get(1L, new Object[0]) != null) {
/* 724:516 */       GT_Recipe.GT_Recipe_Map.sScannerFakeRecipes.addFakeRecipe(false, new ItemStack[] { ItemList.IC2_Crop_Seeds.getWildcard(1L, new Object[0]) }, new ItemStack[] { ItemList.IC2_Crop_Seeds.getWithName(1L, "Scanned Seeds", new Object[0]) }, null, null, null, 160, 8, 0);
/* 725:    */     }
/* 726:517 */     GT_Recipe.GT_Recipe_Map.sScannerFakeRecipes.addFakeRecipe(false, new ItemStack[] { new ItemStack(Items.written_book, 1, 32767) }, new ItemStack[] { ItemList.Tool_DataStick.getWithName(1L, "Scanned Book Data", new Object[0]) }, ItemList.Tool_DataStick.getWithName(1L, "Stick to save it to", new Object[0]), null, null, 128, 32, 0);
/* 727:518 */     GT_Recipe.GT_Recipe_Map.sScannerFakeRecipes.addFakeRecipe(false, new ItemStack[] { new ItemStack(Items.filled_map, 1, 32767) }, new ItemStack[] { ItemList.Tool_DataStick.getWithName(1L, "Scanned Map Data", new Object[0]) }, ItemList.Tool_DataStick.getWithName(1L, "Stick to save it to", new Object[0]), null, null, 128, 32, 0);
/* 728:519 */     GT_Recipe.GT_Recipe_Map.sScannerFakeRecipes.addFakeRecipe(false, new ItemStack[] { ItemList.Tool_DataOrb.getWithName(1L, "Orb to overwrite", new Object[0]) }, new ItemStack[] { ItemList.Tool_DataOrb.getWithName(1L, "Copy of the Orb", new Object[0]) }, ItemList.Tool_DataOrb.getWithName(0L, "Orb to copy", new Object[0]), null, null, 512, 32, 0);
/* 729:520 */     GT_Recipe.GT_Recipe_Map.sScannerFakeRecipes.addFakeRecipe(false, new ItemStack[] { ItemList.Tool_DataStick.getWithName(1L, "Stick to overwrite", new Object[0]) }, new ItemStack[] { ItemList.Tool_DataStick.getWithName(1L, "Copy of the Stick", new Object[0]) }, ItemList.Tool_DataStick.getWithName(0L, "Stick to copy", new Object[0]), null, null, 128, 32, 0);
/* 730:522 */     for (Materials tMaterial : Materials.VALUES) {
/* 731:522 */       if ((tMaterial.mElement != null) && (!tMaterial.mElement.mIsIsotope) && (tMaterial != Materials.Magic) && (tMaterial.getMass() > 0L))
/* 732:    */       {
/* 733:523 */         ItemStack tOutput = ItemList.Tool_DataOrb.get(1L, new Object[0]);
/* 734:524 */         Behaviour_DataOrb.setDataTitle(tOutput, "Elemental-Scan");
/* 735:525 */         Behaviour_DataOrb.setDataName(tOutput, tMaterial.mElement.name());
/* 736:526 */         ItemStack tInput = GT_OreDictUnificator.get(OrePrefixes.dust, tMaterial, 1L);
/* 737:527 */         if (tInput != null) {
/* 738:527 */           GT_Recipe.GT_Recipe_Map.sScannerFakeRecipes.addFakeRecipe(false, new ItemStack[] { tInput }, new ItemStack[] { tOutput }, ItemList.Tool_DataOrb.get(1L, new Object[0]), null, null, (int)(tMaterial.getMass() * 8192L), 32, 0);
/* 739:    */         }
/* 740:528 */         tInput = GT_OreDictUnificator.get(OrePrefixes.cell, tMaterial, 1L);
/* 741:529 */         if (tInput != null) {
/* 742:529 */           GT_Recipe.GT_Recipe_Map.sScannerFakeRecipes.addFakeRecipe(false, new ItemStack[] { tInput }, new ItemStack[] { tOutput }, ItemList.Tool_DataOrb.get(1L, new Object[0]), null, null, (int)(tMaterial.getMass() * 8192L), 32, 0);
/* 743:    */         }
/* 744:    */       }
/* 745:    */     }
/* 746:532 */     GT_Recipe.GT_Recipe_Map.sRockBreakerFakeRecipes.addFakeRecipe(false, new ItemStack[] { ItemList.Display_ITS_FREE.getWithName(0L, "Place Lava on Side", new Object[0]) }, new ItemStack[] { new ItemStack(Blocks.cobblestone, 1) }, null, null, null, 16, 32, 0);
/* 747:533 */     GT_Recipe.GT_Recipe_Map.sRockBreakerFakeRecipes.addFakeRecipe(false, new ItemStack[] { ItemList.Display_ITS_FREE.getWithName(0L, "Place Lava on Top", new Object[0]) }, new ItemStack[] { new ItemStack(Blocks.stone, 1) }, null, null, null, 16, 32, 0);
/* 748:534 */     GT_Recipe.GT_Recipe_Map.sRockBreakerFakeRecipes.addFakeRecipe(false, new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Redstone, 1L) }, new ItemStack[] { new ItemStack(Blocks.obsidian, 1) }, null, null, null, 128, 32, 0);
/* 749:536 */     for (Iterator i$ = GT_ModHandler.getMaceratorRecipeList().entrySet().iterator(); i$.hasNext();)
/* 750:    */     {
/* 751:536 */       Entry tRecipe = (Map.Entry)i$.next();
/* 752:536 */       if (((RecipeOutput)tRecipe.getValue()).items.size() > 0) {
/* 753:536 */         for (ItemStack tStack : ((IRecipeInput)tRecipe.getKey()).getInputs()) {
/* 754:536 */           if (GT_Utility.isStackValid(tStack)) {
/* 755:537 */             GT_Recipe.GT_Recipe_Map.sMaceratorRecipes.addFakeRecipe(true, new ItemStack[] { GT_Utility.copyAmount(((IRecipeInput)tRecipe.getKey()).getAmount(), new Object[] { tStack }) }, new ItemStack[] { (ItemStack)((RecipeOutput)tRecipe.getValue()).items.get(0) }, null, null, null, null, 400, 2, 0);
/* 756:    */           }
/* 757:    */         }
/* 758:    */       }
/* 759:    */     }
				  if(gregtechproxy.mAchievements){
				  achievements = new GT_Achievements();}
/* 760:    */     Map.Entry<IRecipeInput, RecipeOutput> tRecipe;
/* 761:540 */     GT_Log.out.println("GT_Mod: Loading finished, deallocating temporary Init Variables.");
/* 762:541 */     GregTech_API.sBeforeGTPreload = null;
/* 763:542 */     GregTech_API.sAfterGTPreload = null;
/* 764:543 */     GregTech_API.sBeforeGTLoad = null;
/* 765:544 */     GregTech_API.sAfterGTLoad = null;
/* 766:545 */     GregTech_API.sBeforeGTPostload = null;
/* 767:546 */     GregTech_API.sAfterGTPostload = null;
/* 768:    */   }
/* 769:    */   
/* 770:    */   @Mod.EventHandler
/* 771:    */   public void onServerStarting(FMLServerStartingEvent aEvent)
/* 772:    */   {
/* 773:557 */     for (Runnable tRunnable : GregTech_API.sBeforeGTServerstart) {
/* 774:    */       try
/* 775:    */       {
/* 776:559 */         tRunnable.run();
/* 777:    */       }
/* 778:    */       catch (Throwable e)
/* 779:    */       {
/* 780:561 */         e.printStackTrace(GT_Log.err);
/* 781:    */       }
/* 782:    */     }
/* 783:565 */     gregtechproxy.onServerStarting();
/* 811:593 */     GT_Log.out.println("GT_Mod: Unificating outputs of all known Recipe Types.");
/* 812:594 */     ArrayList<ItemStack> tStacks = new ArrayList(10000);
/* 813:595 */     GT_Log.out.println("GT_Mod: IC2 Machines");
/* 814:596 */     for (RecipeOutput tRecipe : ic2.api.recipe.Recipes.cannerBottle.getRecipes().values())
/* 815:    */     {
/* 816:    */       ItemStack tStack;
/* 817:596 */       for (Iterator i$ = tRecipe.items.iterator(); i$.hasNext(); tStacks.add(tStack)) {
/* 818:596 */         tStack = (ItemStack)i$.next();
/* 819:    */       }
/* 820:    */     }
/* 821:597 */     for (RecipeOutput tRecipe : ic2.api.recipe.Recipes.centrifuge.getRecipes().values())
/* 822:    */     {
/* 823:    */       ItemStack tStack;
/* 824:597 */       for (Iterator i$ = tRecipe.items.iterator(); i$.hasNext(); tStacks.add(tStack)) {
/* 825:597 */         tStack = (ItemStack)i$.next();
/* 826:    */       }
/* 827:    */     }
/* 828:598 */     for (RecipeOutput tRecipe : ic2.api.recipe.Recipes.compressor.getRecipes().values())
/* 829:    */     {
/* 830:    */       ItemStack tStack;
/* 831:598 */       for (Iterator i$ = tRecipe.items.iterator(); i$.hasNext(); tStacks.add(tStack)) {
/* 832:598 */         tStack = (ItemStack)i$.next();
/* 833:    */       }
/* 834:    */     }
/* 835:599 */     for (RecipeOutput tRecipe : ic2.api.recipe.Recipes.extractor.getRecipes().values())
/* 836:    */     {
/* 837:    */       ItemStack tStack;
/* 838:599 */       for (Iterator i$ = tRecipe.items.iterator(); i$.hasNext(); tStacks.add(tStack)) {
/* 839:599 */         tStack = (ItemStack)i$.next();
/* 840:    */       }
/* 841:    */     }
/* 842:600 */     for (RecipeOutput tRecipe : ic2.api.recipe.Recipes.macerator.getRecipes().values())
/* 843:    */     {
/* 844:    */       ItemStack tStack;
/* 845:600 */       for (Iterator i$ = tRecipe.items.iterator(); i$.hasNext(); tStacks.add(tStack)) {
/* 846:600 */         tStack = (ItemStack)i$.next();
/* 847:    */       }
/* 848:    */     }
/* 849:601 */     for (RecipeOutput tRecipe : ic2.api.recipe.Recipes.metalformerCutting.getRecipes().values())
/* 850:    */     {
/* 851:    */       ItemStack tStack;
/* 852:601 */       for (Iterator i$ = tRecipe.items.iterator(); i$.hasNext(); tStacks.add(tStack)) {
/* 853:601 */         tStack = (ItemStack)i$.next();
/* 854:    */       }
/* 855:    */     }
/* 856:602 */     for (RecipeOutput tRecipe : ic2.api.recipe.Recipes.metalformerExtruding.getRecipes().values())
/* 857:    */     {
/* 858:    */       ItemStack tStack;
/* 859:602 */       for (Iterator i$ = tRecipe.items.iterator(); i$.hasNext(); tStacks.add(tStack)) {
/* 860:602 */         tStack = (ItemStack)i$.next();
/* 861:    */       }
/* 862:    */     }
/* 863:603 */     for (RecipeOutput tRecipe : ic2.api.recipe.Recipes.metalformerRolling.getRecipes().values())
/* 864:    */     {
/* 865:    */       ItemStack tStack;
/* 866:603 */       for (Iterator i$ = tRecipe.items.iterator(); i$.hasNext(); tStacks.add(tStack)) {
/* 867:603 */         tStack = (ItemStack)i$.next();
/* 868:    */       }
/* 869:    */     }
/* 870:604 */     for (RecipeOutput tRecipe : ic2.api.recipe.Recipes.matterAmplifier.getRecipes().values())
/* 871:    */     {
/* 872:    */       ItemStack tStack;
/* 873:604 */       for (Iterator i$ = tRecipe.items.iterator(); i$.hasNext(); tStacks.add(tStack)) {
/* 874:604 */         tStack = (ItemStack)i$.next();
/* 875:    */       }
/* 876:    */     }
/* 877:605 */     for (RecipeOutput tRecipe : ic2.api.recipe.Recipes.oreWashing.getRecipes().values())
/* 878:    */     {
/* 879:    */       ItemStack tStack;
/* 880:605 */       for (Iterator i$ = tRecipe.items.iterator(); i$.hasNext(); tStacks.add(tStack)) {
/* 881:605 */         tStack = (ItemStack)i$.next();
/* 882:    */       }
/* 883:    */     }
/* 884:606 */     GT_Log.out.println("GT_Mod: Dungeon Loot");
/* 885:607 */     for (WeightedRandomChestContent tContent : ChestGenHooks.getInfo("dungeonChest").getItems(new Random())) {
/* 886:607 */       tStacks.add(tContent.theItemId);
/* 887:    */     }
/* 888:608 */     for (WeightedRandomChestContent tContent : ChestGenHooks.getInfo("bonusChest").getItems(new Random())) {
/* 889:608 */       tStacks.add(tContent.theItemId);
/* 890:    */     }
/* 891:609 */     for (WeightedRandomChestContent tContent : ChestGenHooks.getInfo("villageBlacksmith").getItems(new Random())) {
/* 892:609 */       tStacks.add(tContent.theItemId);
/* 893:    */     }
/* 894:610 */     for (WeightedRandomChestContent tContent : ChestGenHooks.getInfo("strongholdCrossing").getItems(new Random())) {
/* 895:610 */       tStacks.add(tContent.theItemId);
/* 896:    */     }
/* 897:611 */     for (WeightedRandomChestContent tContent : ChestGenHooks.getInfo("strongholdLibrary").getItems(new Random())) {
/* 898:611 */       tStacks.add(tContent.theItemId);
/* 899:    */     }
/* 900:612 */     for (WeightedRandomChestContent tContent : ChestGenHooks.getInfo("strongholdCorridor").getItems(new Random())) {
/* 901:612 */       tStacks.add(tContent.theItemId);
/* 902:    */     }
/* 903:613 */     for (WeightedRandomChestContent tContent : ChestGenHooks.getInfo("pyramidJungleDispenser").getItems(new Random())) {
/* 904:613 */       tStacks.add(tContent.theItemId);
/* 905:    */     }
/* 906:614 */     for (WeightedRandomChestContent tContent : ChestGenHooks.getInfo("pyramidJungleChest").getItems(new Random())) {
/* 907:614 */       tStacks.add(tContent.theItemId);
/* 908:    */     }
/* 909:615 */     for (WeightedRandomChestContent tContent : ChestGenHooks.getInfo("pyramidDesertyChest").getItems(new Random())) {
/* 910:615 */       tStacks.add(tContent.theItemId);
/* 911:    */     }
/* 912:616 */     for (WeightedRandomChestContent tContent : ChestGenHooks.getInfo("mineshaftCorridor").getItems(new Random())) {
/* 913:616 */       tStacks.add(tContent.theItemId);
/* 914:    */     }
/* 915:617 */     GT_Log.out.println("GT_Mod: Smelting");
/* 916:    */     Object tStack;
/* 917:618 */     for (Iterator i$ = FurnaceRecipes.smelting().getSmeltingList().values().iterator(); i$.hasNext(); tStacks.add((ItemStack)tStack)) {
/* 918:618 */       tStack = i$.next();
/* 919:    */     }
/* 920:619 */     if (gregtechproxy.mCraftingUnification)
/* 921:    */     {
/* 922:620 */       GT_Log.out.println("GT_Mod: Crafting Recipes");
/* 923:621 */       for (Object tRecipe : CraftingManager.getInstance().getRecipeList()) {
/* 924:621 */         if ((tRecipe instanceof IRecipe)) {
/* 925:621 */           tStacks.add(((IRecipe)tRecipe).getRecipeOutput());
/* 926:    */         }
/* 927:    */       }
/* 928:    */     }
/* 929:624 */     for (ItemStack tOutput : tStacks) {
/* 930:625 */       if (gregtechproxy.mRegisteredOres.contains(tOutput))
/* 931:    */       {
/* 932:626 */         FMLLog.severe("GT-ERR-01: @ " + tOutput.getUnlocalizedName() + "   " + tOutput.getDisplayName(), new Object[0]);
/* 933:627 */         FMLLog.severe("A Recipe used an OreDict Item as Output directly, without copying it before!!! This is a typical CallByReference/CallByValue Error", new Object[0]);
/* 934:628 */         FMLLog.severe("Said Item will be renamed to make the invalid Recipe visible, so that you can report it properly.", new Object[0]);
/* 935:629 */         FMLLog.severe("Please check all Recipes outputting this Item, and report the Recipes to their Owner.", new Object[0]);
/* 936:630 */         FMLLog.severe("The Owner of the ==>RECIPE<==, NOT the Owner of the Item, which has been mentioned above!!!", new Object[0]);
/* 937:631 */         FMLLog.severe("And ONLY Recipes which are ==>OUTPUTTING<== the Item, sorry but I don't want failed Bug Reports.", new Object[0]);
/* 938:632 */         FMLLog.severe("GregTech just reports this Error to you, so you can report it to the Mod causing the Problem.", new Object[0]);
/* 939:633 */         FMLLog.severe("Even though I make that Bug visible, I can not and will not fix that for you, that's for the causing Mod to fix.", new Object[0]);
/* 940:634 */         FMLLog.severe("And speaking of failed Reports:", new Object[0]);
/* 941:635 */         FMLLog.severe("Both IC2 and GregTech CANNOT be the CAUSE of this Problem, so don't report it to either of them.", new Object[0]);
/* 942:636 */         FMLLog.severe("I REPEAT, BOTH, IC2 and GregTech CANNOT be the source of THIS BUG. NO MATTER WHAT.", new Object[0]);
/* 943:637 */         FMLLog.severe("Asking in the IC2 Forums, which Mod is causing that, won't help anyone, since it is not possible to determine, which Mod it is.", new Object[0]);
/* 944:638 */         FMLLog.severe("If it would be possible, then I would have had added the Mod which is causing it to the Message already. But it is not possible.", new Object[0]);
/* 945:639 */         FMLLog.severe("Sorry, but this Error is serious enough to justify this Wall-O-Text and the partially allcapsed Language.", new Object[0]);
/* 946:640 */         FMLLog.severe("Also it is a Ban Reason on the IC2-Forums to post this seriously.", new Object[0]);
/* 947:641 */         tOutput.setStackDisplayName("ERROR! PLEASE CHECK YOUR LOG FOR 'GT-ERR-01'!");
/* 948:    */       }
/* 949:    */       else
/* 950:    */       {
/* 951:643 */         GT_OreDictUnificator.setStack(tOutput);
/* 952:    */       }
/* 953:    */     }
/* 954:647 */     GT_Log.out.println("GT_Mod: ServerStarting-Phase finished!");
/* 955:648 */     GT_Log.ore.println("GT_Mod: ServerStarting-Phase finished!");
/* 956:650 */     for (Runnable tRunnable : GregTech_API.sAfterGTServerstart) {
/* 957:    */       try
/* 958:    */       {
/* 959:652 */         tRunnable.run();
/* 960:    */       }
/* 961:    */       catch (Throwable e)
/* 962:    */       {
/* 963:654 */         e.printStackTrace(GT_Log.err);
/* 964:    */       }
/* 965:    */     }
/* 966:    */   }
/* 967:    */   
/* 968:    */   @Mod.EventHandler
/* 969:    */   public void onServerStarted(FMLServerStartedEvent aEvent)
/* 970:    */   {
/* 971:661 */     gregtechproxy.onServerStarted();
/* 972:    */   }
/* 973:    */   
/* 974:    */   @Mod.EventHandler
public void onIDChangingEvent(FMLModIdMappingEvent aEvent)
{
    GT_Utility.reInit();
    GT_Recipe.reInit();
    for(Iterator i$ = GregTech_API.sItemStackMappings.iterator(); i$.hasNext();)
    {
        Map tMap = (Map)i$.next();
        try
        {
            GT_Utility.reMap(tMap);
        }
        catch(Throwable e)
        {
            e.printStackTrace(GT_Log.err);
        }
    }

}
///* 975:    */   public void onIDChangingEvent(FMLModIdMappingEvent aEvent)
///* 976:    */   {
///* 977:666 */     GT_Utility.reInit();
///* 978:667 */     GT_Recipe.reInit();
///* 979:    */     Map<GT_ItemStack, ?> tMap;
///* 980:668 */     for (Iterator i$ = GregTech_API.sItemStackMappings.iterator(); i$.hasNext(); ) {
///* 981:668 */       tMap = (Map)i$.next();
///* 982:    */     }
///* 983:    */   }
/* 984:    */   
/* 985:    */   @Mod.EventHandler
/* 986:    */   public void onServerStopping(FMLServerStoppingEvent aEvent)
/* 987:    */   {
/* 988:673 */     for (Runnable tRunnable : GregTech_API.sBeforeGTServerstop) {
/* 989:    */       try
/* 990:    */       {
/* 991:675 */         tRunnable.run();
/* 992:    */       }
/* 993:    */       catch (Throwable e)
/* 994:    */       {
/* 995:677 */         e.printStackTrace(GT_Log.err);
/* 996:    */       }
/* 997:    */     }
/* 998:681 */     gregtechproxy.onServerStopping();
/* 999:    */     try
/* :00:    */     {
/* :01:684 */       if ((GT_Values.D1) || (GT_Log.out != System.out))
/* :02:    */       {
/* :03:685 */         GT_Log.out.println("*");
/* :04:686 */         GT_Log.out.println("Printing List of all registered Objects inside the OreDictionary, now with free extra Sorting:");
/* :05:687 */         GT_Log.out.println("*");GT_Log.out.println("*");GT_Log.out.println("*");
/* :06:    */         
/* :07:689 */         String[] tList = OreDictionary.getOreNames();
/* :08:690 */         Arrays.sort(tList);
/* :09:691 */         for (String tOreName : tList)
/* :10:    */         {
/* :11:692 */           int tAmount = OreDictionary.getOres(tOreName).size();
/* :12:693 */           if (tAmount > 0) {
/* :13:693 */             GT_Log.out.println((tAmount < 10 ? " " : "") + tAmount + "x " + tOreName);
/* :14:    */           }
/* :15:    */         }
/* :16:696 */         GT_Log.out.println("*");
/* :17:697 */         GT_Log.out.println("Printing List of all registered Objects inside the Fluid Registry, now with free extra Sorting:");
/* :18:698 */         GT_Log.out.println("*");GT_Log.out.println("*");GT_Log.out.println("*");
/* :19:    */         
/* :20:700 */         tList = (String[])FluidRegistry.getRegisteredFluids().keySet().toArray(new String[FluidRegistry.getRegisteredFluids().keySet().size()]);
/* :21:701 */         Arrays.sort(tList);
/* :22:702 */         for (String tFluidName : tList) {
/* :23:702 */           GT_Log.out.println(tFluidName);
/* :24:    */         }
/* :25:704 */         GT_Log.out.println("*");GT_Log.out.println("*");GT_Log.out.println("*");
/* :26:705 */         GT_Log.out.println("Outputting all the Names inside the Biomeslist");
/* :27:706 */         GT_Log.out.println("*");GT_Log.out.println("*");GT_Log.out.println("*");
/* :28:708 */         for (int i = 0; i < BiomeGenBase.getBiomeGenArray().length; i++) {
/* :29:709 */           if (BiomeGenBase.getBiomeGenArray()[i] != null) {
/* :30:710 */             GT_Log.out.println(BiomeGenBase.getBiomeGenArray()[i].biomeID + " = " + BiomeGenBase.getBiomeGenArray()[i].biomeName);
/* :31:    */           }
/* :32:    */         }
/* :33:713 */         GT_Log.out.println("*");GT_Log.out.println("*");GT_Log.out.println("*");
/* :34:714 */         GT_Log.out.println("Printing List of generatable Materials");
/* :35:715 */         GT_Log.out.println("*");GT_Log.out.println("*");GT_Log.out.println("*");
/* :36:717 */         for (int i = 0; i < GregTech_API.sGeneratedMaterials.length; i++) {
/* :37:718 */           if (GregTech_API.sGeneratedMaterials[i] == null) {
/* :38:719 */             GT_Log.out.println("Index " + i + ":" + null);
/* :39:    */           } else {
/* :40:721 */             GT_Log.out.println("Index " + i + ":" + GregTech_API.sGeneratedMaterials[i]);
/* :41:    */           }
/* :42:    */         }
/* :43:725 */         GT_Log.out.println("*");GT_Log.out.println("*");GT_Log.out.println("*");
/* :44:726 */         GT_Log.out.println("END GregTech-Debug");
/* :45:727 */         GT_Log.out.println("*");GT_Log.out.println("*");GT_Log.out.println("*");
/* :46:    */       }
/* :47:    */     }
/* :48:    */     catch (Throwable e)
/* :49:    */     {
/* :50:729 */       if (GT_Values.D1) {
/* :51:729 */         e.printStackTrace(GT_Log.err);
/* :52:    */       }
/* :53:    */     }
/* :54:731 */     for (Runnable tRunnable : GregTech_API.sAfterGTServerstop) {
/* :55:    */       try
/* :56:    */       {
/* :57:733 */         tRunnable.run();
/* :58:    */       }
/* :59:    */       catch (Throwable e)
/* :60:    */       {
/* :61:735 */         e.printStackTrace(GT_Log.err);
/* :62:    */       }
/* :63:    */     }
/* :64:    */   }
/* :65:    */   
/* :66:    */   public boolean isServerSide()
/* :67:    */   {
/* :68:740 */     return gregtechproxy.isServerSide();
/* :69:    */   }
/* :70:    */   
/* :71:    */   public boolean isClientSide()
/* :72:    */   {
/* :73:741 */     return gregtechproxy.isClientSide();
/* :74:    */   }
/* :75:    */   
/* :76:    */   public boolean isBukkitSide()
/* :77:    */   {
/* :78:742 */     return gregtechproxy.isBukkitSide();
/* :79:    */   }
/* :80:    */   
/* :81:    */   public EntityPlayer getThePlayer()
/* :82:    */   {
/* :83:743 */     return gregtechproxy.getThePlayer();
/* :84:    */   }
/* :85:    */   
/* :86:    */   public int addArmor(String aArmorPrefix)
/* :87:    */   {
/* :88:744 */     return gregtechproxy.addArmor(aArmorPrefix);
/* :89:    */   }
/* :90:    */   
/* :91:    */   public void doSonictronSound(ItemStack aStack, World aWorld, double aX, double aY, double aZ)
/* :92:    */   {
/* :93:745 */     gregtechproxy.doSonictronSound(aStack, aWorld, aX, aY, aZ);
/* :94:    */   }
/* :95:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.GT_Mod
 * JD-Core Version:    0.7.0.1
 */