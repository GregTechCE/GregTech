/*  1:   */ package gregtech.common.items;
/*  2:   */ 
/*  3:   */ import gregtech.api.GregTech_API;
import gregtech.api.enums.ConfigCategories;
/*  4:   */ import gregtech.api.enums.ConfigCategories.Tools;
/*  5:   */ import gregtech.api.enums.Materials;
/*  6:   */ import gregtech.api.enums.OrePrefixes;
/*  7:   */ import gregtech.api.enums.TC_Aspects;
/*  8:   */ import gregtech.api.enums.TC_Aspects.TC_AspectStack;
/*  9:   */ import gregtech.api.enums.ToolDictNames;
/* 10:   */ import gregtech.api.items.GT_MetaGenerated_Tool;
/* 11:   */ import gregtech.api.util.GT_Config;
/* 12:   */ import gregtech.api.util.GT_ModHandler;
/* 13:   */ import gregtech.api.util.GT_ModHandler.RecipeBits;
/* 14:   */ import gregtech.api.util.GT_OreDictUnificator;
/* 15:   */ import gregtech.common.tools.GT_Tool_Axe;
/* 16:   */ import gregtech.common.tools.GT_Tool_BranchCutter;
/* 17:   */ import gregtech.common.tools.GT_Tool_ButcheryKnife;
/* 18:   */ import gregtech.common.tools.GT_Tool_BuzzSaw;
/* 19:   */ import gregtech.common.tools.GT_Tool_Chainsaw_HV;
/* 20:   */ import gregtech.common.tools.GT_Tool_Chainsaw_LV;
/* 21:   */ import gregtech.common.tools.GT_Tool_Chainsaw_MV;
/* 22:   */ import gregtech.common.tools.GT_Tool_Crowbar;
/* 23:   */ import gregtech.common.tools.GT_Tool_Drill_HV;
/* 24:   */ import gregtech.common.tools.GT_Tool_Drill_LV;
/* 25:   */ import gregtech.common.tools.GT_Tool_Drill_MV;
/* 26:   */ import gregtech.common.tools.GT_Tool_File;
/* 27:   */ import gregtech.common.tools.GT_Tool_HardHammer;
/* 28:   */ import gregtech.common.tools.GT_Tool_Hoe;
/* 29:   */ import gregtech.common.tools.GT_Tool_JackHammer;
/* 30:   */ import gregtech.common.tools.GT_Tool_Knife;
/* 31:   */ import gregtech.common.tools.GT_Tool_Mortar;
/* 32:   */ import gregtech.common.tools.GT_Tool_Pickaxe;
/* 33:   */ import gregtech.common.tools.GT_Tool_Plow;
/* 34:   */ import gregtech.common.tools.GT_Tool_Plunger;
/* 35:   */ import gregtech.common.tools.GT_Tool_RollingPin;
/* 36:   */ import gregtech.common.tools.GT_Tool_Saw;
/* 37:   */ import gregtech.common.tools.GT_Tool_Scoop;
/* 38:   */ import gregtech.common.tools.GT_Tool_Screwdriver;
/* 39:   */ import gregtech.common.tools.GT_Tool_Screwdriver_LV;
/* 40:   */ import gregtech.common.tools.GT_Tool_Sense;
/* 41:   */ import gregtech.common.tools.GT_Tool_Shovel;
/* 42:   */ import gregtech.common.tools.GT_Tool_SoftHammer;
import gregtech.common.tools.GT_Tool_Soldering_Iron;
/* 43:   */ import gregtech.common.tools.GT_Tool_Sword;
import gregtech.common.tools.GT_Tool_Turbine;
import gregtech.common.tools.GT_Tool_Turbine_Huge;
import gregtech.common.tools.GT_Tool_Turbine_Large;
import gregtech.common.tools.GT_Tool_Turbine_Normal;
import gregtech.common.tools.GT_Tool_Turbine_Small;
/* 44:   */ import gregtech.common.tools.GT_Tool_UniversalSpade;
/* 45:   */ import gregtech.common.tools.GT_Tool_WireCutter;
/* 46:   */ import gregtech.common.tools.GT_Tool_Wrench;
/* 47:   */ import gregtech.common.tools.GT_Tool_Wrench_HV;
/* 48:   */ import gregtech.common.tools.GT_Tool_Wrench_LV;
/* 49:   */ import gregtech.common.tools.GT_Tool_Wrench_MV;
/* 50:   */ import net.minecraft.init.Blocks;
/* 51:   */ import net.minecraft.init.Items;
/* 52:   */ import net.minecraft.item.ItemStack;
/* 53:   */ 
/* 54:   */ public class GT_MetaGenerated_Tool_01
/* 55:   */   extends GT_MetaGenerated_Tool
/* 56:   */ {
/* 57:   */   public static GT_MetaGenerated_Tool_01 INSTANCE;
/* 58:   */   public static final short SWORD = 0;
/* 59:   */   public static final short PICKAXE = 2;
/* 60:   */   public static final short SHOVEL = 4;
/* 61:   */   public static final short AXE = 6;
/* 62:   */   public static final short HOE = 8;
/* 63:   */   public static final short SAW = 10;
/* 64:   */   public static final short HARDHAMMER = 12;
/* 65:   */   public static final short SOFTHAMMER = 14;
/* 66:   */   public static final short WRENCH = 16;
/* 67:   */   public static final short FILE = 18;
/* 68:   */   public static final short CROWBAR = 20;
/* 69:   */   public static final short SCREWDRIVER = 22;
/* 70:   */   public static final short MORTAR = 24;
/* 71:   */   public static final short WIRECUTTER = 26;
/* 72:   */   public static final short SCOOP = 28;
/* 73:   */   public static final short BRANCHCUTTER = 30;
/* 74:   */   public static final short UNIVERSALSPADE = 32;
/* 75:   */   public static final short KNIFE = 34;
/* 76:   */   public static final short BUTCHERYKNIFE = 36;
/* 77:   */   public static final short SICKLE = 38;
/* 78:   */   public static final short SENSE = 40;
/* 79:   */   public static final short PLOW = 42;
/* 80:   */   public static final short PLUNGER = 44;
/* 81:   */   public static final short ROLLING_PIN = 46;
/* 82:   */   public static final short DRILL_LV = 100;
/* 83:   */   public static final short DRILL_MV = 102;
/* 84:   */   public static final short DRILL_HV = 104;
/* 85:   */   public static final short CHAINSAW_LV = 110;
/* 86:   */   public static final short CHAINSAW_MV = 112;
/* 87:   */   public static final short CHAINSAW_HV = 114;
/* 88:   */   public static final short WRENCH_LV = 120;
/* 89:   */   public static final short WRENCH_MV = 122;
/* 90:   */   public static final short WRENCH_HV = 124;
/* 91:   */   public static final short JACKHAMMER = 130;
/* 92:   */   public static final short BUZZSAW = 140;
/* 93:   */   public static final short SCREWDRIVER_LV = 150;
			  public static final short SOLDERING_IRON_LV = 160;
			  public static final short TURBINE_SMALL = 170;
			  public static final short TURBINE = 172;
			  public static final short TURBINE_LARGE = 174;
			  public static final short TURBINE_HUGE = 176;
			  public static final short TURBINE_BLADE = 178;
/* 94:   */   
/* 95:   */   public GT_MetaGenerated_Tool_01()
/* 96:   */   {
/* 97:20 */     super("metatool.01");
/* 98:21 */     INSTANCE = this;
/* 99:22 */     addTool(0, "Sword", "", new GT_Tool_Sword(), new Object[] { ToolDictNames.craftingToolSword, ToolDictNames.craftingToolBlade, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 4L) });
/* :0:23 */     addTool(2, "Pickaxe", "", new GT_Tool_Pickaxe(), new Object[] { ToolDictNames.craftingToolPickaxe, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.PERFODIO, 4L) });
/* :1:24 */     addTool(4, "Shovel", "", new GT_Tool_Shovel(), new Object[] { ToolDictNames.craftingToolShovel, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.PERFODIO, 4L) });
/* :2:25 */     addTool(6, "Axe", "", new GT_Tool_Axe(), new Object[] { ToolDictNames.craftingToolAxe, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.METO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 2L) });
/* :3:26 */     addTool(8, "Hoe", "", new GT_Tool_Hoe(), new Object[] { ToolDictNames.craftingToolHoe, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 4L) });
/* :4:27 */     addTool(10, "Saw", "Can also harvest Ice", new GT_Tool_Saw(), new Object[] { ToolDictNames.craftingToolSaw, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.METO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 2L) });
/* :5:28 */     GregTech_API.registerTool(addTool(12, "Hammer", "Crushes Ores instead of harvesting them", new GT_Tool_HardHammer(), new Object[] { ToolDictNames.craftingToolHardHammer, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.FABRICO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.ORDO, 2L) }), GregTech_API.sHardHammerList);
/* :6:29 */     GregTech_API.registerTool(addTool(14, "Soft Hammer", "", new GT_Tool_SoftHammer(), new Object[] { ToolDictNames.craftingToolSoftHammer, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.LIMUS, 4L) }), GregTech_API.sSoftHammerList);
/* :7:30 */     GregTech_API.registerTool(addTool(WRENCH, "Wrench", "Hold Leftclick to dismantle Machines", new GT_Tool_Wrench(), new Object[] { ToolDictNames.craftingToolWrench, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.ORDO, 2L) }), GregTech_API.sWrenchList);
/* :8:31 */     addTool(18, "File", "", new GT_Tool_File(), new Object[] { ToolDictNames.craftingToolFile, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.FABRICO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.ORDO, 2L) });
/* :9:32 */     GregTech_API.registerTool(addTool(20, "Crowbar", "Dismounts Covers and Rotates Rails", new GT_Tool_Crowbar(), new Object[] { ToolDictNames.craftingToolCrowbar, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.FABRICO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 2L) }), GregTech_API.sCrowbarList);
/* ;0:33 */     GregTech_API.registerTool(addTool(22, "Screwdriver", "Adjusts Covers and Machines", new GT_Tool_Screwdriver(), new Object[] { ToolDictNames.craftingToolScrewdriver, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.FABRICO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.ORDO, 2L) }), GregTech_API.sScrewdriverList);
/* ;1:34 */     addTool(24, "Mortar", "", new GT_Tool_Mortar(), new Object[] { ToolDictNames.craftingToolMortar, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.FABRICO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.PERDITIO, 2L) });
/* ;2:35 */     addTool(26, "Wire Cutter", "", new GT_Tool_WireCutter(), new Object[] { ToolDictNames.craftingToolWireCutter, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.FABRICO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.ORDO, 2L) });
/* ;3:36 */     addTool(28, "Scoop", "", new GT_Tool_Scoop(), new Object[] { ToolDictNames.craftingToolScoop, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.BESTIA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.PANNUS, 2L) });
/* ;4:37 */     addTool(30, "Branch Cutter", "", new GT_Tool_BranchCutter(), new Object[] { ToolDictNames.craftingToolBranchCutter, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.METO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 2L) });
/* ;5:38 */     GregTech_API.registerTool(addTool(32, "Universal Spade", "", new GT_Tool_UniversalSpade(), new Object[] { ToolDictNames.craftingToolBlade, ToolDictNames.craftingToolShovel, ToolDictNames.craftingToolCrowbar, ToolDictNames.craftingToolSaw, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.METO, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FABRICO, 1L) }), GregTech_API.sCrowbarList);
/* ;6:39 */     addTool(34, "Knife", "", new GT_Tool_Knife(), new Object[] { ToolDictNames.craftingToolBlade, ToolDictNames.craftingToolKnife, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.CORPUS, 2L) });
/* ;7:40 */     addTool(36, "Butchery Knife", "Has a slow Attack Rate", new GT_Tool_ButcheryKnife(), new Object[] { ToolDictNames.craftingToolBlade, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.FABRICO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.CORPUS, 4L) });
/* ;8:   */     
/* ;9:42 */     addTool(40, "Sense", "Because a Scythe doesn't make Sense", new GT_Tool_Sense(), new Object[] { ToolDictNames.craftingToolBlade, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.METO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.MORTUUS, 2L) });
/* <0:43 */     addTool(42, "Plow", "Used to get rid of Snow", new GT_Tool_Plow(), new Object[] { ToolDictNames.craftingToolPlow, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.METO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.GELUM, 2L) });
/* <1:44 */     addTool(44, "Plunger", "", new GT_Tool_Plunger(), new Object[] { ToolDictNames.craftingToolPlunger, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.VACUOS, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.ITER, 2L) });
/* <2:45 */     addTool(46, "Rolling Pin", "", new GT_Tool_RollingPin(), new Object[] { ToolDictNames.craftingToolRollingPin, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.LIMUS, 4L) });
/* <3:   */     
/* <4:47 */     addTool(100, "Drill (LV)", "", new GT_Tool_Drill_LV(), new Object[] { ToolDictNames.craftingToolMiningDrill, new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.PERFODIO, 4L) });
/* <5:48 */     addTool(102, "Drill (MV)", "", new GT_Tool_Drill_MV(), new Object[] { ToolDictNames.craftingToolMiningDrill, new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.PERFODIO, 4L) });
/* <6:49 */     addTool(104, "Drill (HV)", "", new GT_Tool_Drill_HV(), new Object[] { ToolDictNames.craftingToolMiningDrill, new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.PERFODIO, 4L) });
/* <7:50 */     addTool(110, "Chainsaw (LV)", "Can also harvest Ice", new GT_Tool_Chainsaw_LV(), new Object[] { ToolDictNames.craftingToolSaw, new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.METO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 2L) });
/* <8:51 */     addTool(112, "Chainsaw (MV)", "Can also harvest Ice", new GT_Tool_Chainsaw_MV(), new Object[] { ToolDictNames.craftingToolSaw, new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.METO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 2L) });
/* <9:52 */     addTool(114, "Chainsaw (HV)", "Can also harvest Ice", new GT_Tool_Chainsaw_HV(), new Object[] { ToolDictNames.craftingToolSaw, new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.METO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 2L) });
/* =0:53 */     GregTech_API.registerTool(addTool(WRENCH_LV, "Wrench (LV)", "Hold Leftclick to dismantle Machines", new GT_Tool_Wrench_LV(), new Object[] { ToolDictNames.craftingToolWrench, new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.ORDO, 2L) }), GregTech_API.sWrenchList);
/* =1:54 */     GregTech_API.registerTool(addTool(WRENCH_MV, "Wrench (MV)", "Hold Leftclick to dismantle Machines", new GT_Tool_Wrench_MV(), new Object[] { ToolDictNames.craftingToolWrench, new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.ORDO, 2L) }), GregTech_API.sWrenchList);
/* =2:55 */     GregTech_API.registerTool(addTool(WRENCH_HV, "Wrench (HV)", "Hold Leftclick to dismantle Machines", new GT_Tool_Wrench_HV(), new Object[] { ToolDictNames.craftingToolWrench, new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.ORDO, 2L) }), GregTech_API.sWrenchList);
/* =3:56 */     addTool(130, "JackHammer (HV)", "Breaks Rocks into pieces", new GT_Tool_JackHammer(), new Object[] { ToolDictNames.craftingToolJackHammer, new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.PERFODIO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.PERDITIO, 2L) });
/* =4:57 */     addTool(140, "Buzzsaw (LV)", "Not suitable for harvesting Blocks", new GT_Tool_BuzzSaw(), new Object[] { ToolDictNames.craftingToolSaw, new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.FABRICO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 2L) });
/* =5:58 */     GregTech_API.registerTool(addTool(150, "Screwdriver (LV)", "Adjusts Covers and Machines", new GT_Tool_Screwdriver_LV(), new Object[] { ToolDictNames.craftingToolScrewdriver, new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.FABRICO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.ORDO, 2L) }), GregTech_API.sScrewdriverList);
/* =6:   */     GregTech_API.registerTool(addTool(SOLDERING_IRON_LV, "Soldering Iron (LV)", "Fixes burned out Circuits. Needs soldering materials in inventory", new GT_Tool_Soldering_Iron(), new Object[] { ToolDictNames.craftingToolSolderingIron, new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.FABRICO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.ORDO, 2L) }),  GregTech_API.sSolderingToolList);
				
				addTool(TURBINE_SMALL, "Small Turbine", "Turbine Rotors for your power station", new GT_Tool_Turbine_Small(), new Object[] {});
				addTool(TURBINE,       "Turbine",       "Turbine Rotors for your power station", new GT_Tool_Turbine_Normal(), new Object[] {});
				addTool(TURBINE_LARGE, "Large Turbine", "Turbine Rotors for your power station", new GT_Tool_Turbine_Large(), new Object[] {});
				addTool(TURBINE_HUGE,  "Huge Turbine",  "Turbine Rotors for your power station", new GT_Tool_Turbine_Huge(), new Object[] {});

/* =7:60 */     GT_ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(24, 1, Materials.Flint, Materials.Stone, null), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { " I ", "SIS", "SSS", Character.valueOf('I'), new ItemStack(Items.flint, 1), Character.valueOf('S'), OrePrefixes.stone });
/* =8:61 */     GT_ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(24, 1, Materials.Bronze, Materials.Stone, null), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { " I ", "SIS", "SSS", Character.valueOf('I'), OrePrefixes.ingot.get(Materials.Bronze), Character.valueOf('S'), OrePrefixes.stone });
/* =9:62 */     GT_ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(24, 1, Materials.Iron, Materials.Stone, null), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { " I ", "SIS", "SSS", Character.valueOf('I'), OrePrefixes.ingot.get(Materials.Iron), Character.valueOf('S'), OrePrefixes.stone });
/* >0:63 */     GT_ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(24, 1, Materials.Steel, Materials.Stone, null), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { " I ", "SIS", "SSS", Character.valueOf('I'), OrePrefixes.ingot.get(Materials.Steel), Character.valueOf('S'), OrePrefixes.stone });
/* >1:64 */     GT_ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(24, 1, Materials.WroughtIron, Materials.Stone, null), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { " I ", "SIS", "SSS", Character.valueOf('I'), OrePrefixes.ingot.get(Materials.WroughtIron), Character.valueOf('S'), OrePrefixes.stone });
/* >2:65 */     GT_ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(24, 1, Materials.RedSteel, Materials.Stone, null), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { " I ", "SIS", "SSS", Character.valueOf('I'), OrePrefixes.ingot.get(Materials.RedSteel), Character.valueOf('S'), OrePrefixes.stone });
/* >3:66 */     GT_ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(24, 1, Materials.BlueSteel, Materials.Stone, null), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { " I ", "SIS", "SSS", Character.valueOf('I'), OrePrefixes.ingot.get(Materials.BlueSteel), Character.valueOf('S'), OrePrefixes.stone });
/* >4:67 */     GT_ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(24, 1, Materials.BlackSteel, Materials.Stone, null), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { " I ", "SIS", "SSS", Character.valueOf('I'), OrePrefixes.ingot.get(Materials.BlackSteel), Character.valueOf('S'), OrePrefixes.stone });
/* >5:68 */     GT_ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(24, 1, Materials.DamascusSteel, Materials.Stone, null), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { " I ", "SIS", "SSS", Character.valueOf('I'), OrePrefixes.ingot.get(Materials.DamascusSteel), Character.valueOf('S'), OrePrefixes.stone });
/* >6:69 */     GT_ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(24, 1, Materials.Thaumium, Materials.Stone, null), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { " I ", "SIS", "SSS", Character.valueOf('I'), OrePrefixes.ingot.get(Materials.Thaumium), Character.valueOf('S'), OrePrefixes.stone });
/* >7:   */     
/* >8:71 */     GT_ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(46, 1, Materials.Wood, Materials.Wood, null), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { "  S", " I ", "S f", Character.valueOf('I'), OrePrefixes.plank.get(Materials.Wood), Character.valueOf('S'), OrePrefixes.stick.get(Materials.Wood) });
/* >9:72 */     GT_ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(46, 1, Materials.Plastic, Materials.Plastic, null), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { "  S", " I ", "S f", Character.valueOf('I'), OrePrefixes.ingot.get(Materials.Plastic), Character.valueOf('S'), OrePrefixes.stick.get(Materials.Plastic) });
/* ?0:73 */     GT_ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(46, 1, Materials.Aluminium, Materials.Aluminium, null), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { "  S", " I ", "S f", Character.valueOf('I'), OrePrefixes.ingot.get(Materials.Aluminium), Character.valueOf('S'), OrePrefixes.stick.get(Materials.Aluminium) });
/* ?1:74 */     GT_ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(46, 1, Materials.StainlessSteel, Materials.StainlessSteel, null), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { "  S", " I ", "S f", Character.valueOf('I'), OrePrefixes.ingot.get(Materials.StainlessSteel), Character.valueOf('S'), OrePrefixes.stick.get(Materials.StainlessSteel) });
/* ?2:   */     
/* ?3:76 */     GT_ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(0, 1, Materials.Flint, Materials.Wood, null), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { "F", "F", "S", Character.valueOf('S'), OrePrefixes.stick.get(Materials.Wood), Character.valueOf('F'), new ItemStack(Items.flint, 1) });
/* ?4:77 */     GT_ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(2, 1, Materials.Flint, Materials.Wood, null), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { "FFF", " S ", " S ", Character.valueOf('S'), OrePrefixes.stick.get(Materials.Wood), Character.valueOf('F'), new ItemStack(Items.flint, 1) });
/* ?5:78 */     GT_ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(4, 1, Materials.Flint, Materials.Wood, null), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { "F", "S", "S", Character.valueOf('S'), OrePrefixes.stick.get(Materials.Wood), Character.valueOf('F'), new ItemStack(Items.flint, 1) });
/* ?6:79 */     GT_ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(6, 1, Materials.Flint, Materials.Wood, null), GT_ModHandler.RecipeBits.MIRRORED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { "FF", "FS", " S", Character.valueOf('S'), OrePrefixes.stick.get(Materials.Wood), Character.valueOf('F'), new ItemStack(Items.flint, 1) });
/* ?7:80 */     GT_ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(8, 1, Materials.Flint, Materials.Wood, null), GT_ModHandler.RecipeBits.MIRRORED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { "FF", " S", " S", Character.valueOf('S'), OrePrefixes.stick.get(Materials.Wood), Character.valueOf('F'), new ItemStack(Items.flint, 1) });
/* ?8:81 */     GT_ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(34, 1, Materials.Flint, Materials.Wood, null), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { "F", "S", Character.valueOf('S'), OrePrefixes.stick.get(Materials.Wood), Character.valueOf('F'), new ItemStack(Items.flint, 1) });
/* ?9:83 */     if (GregTech_API.sRecipeFile.get(ConfigCategories.Tools.mortar, "Coal", true)) {
/* @0:83 */       GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Coal, 1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { ToolDictNames.craftingToolMortar, new ItemStack(Items.coal, 1) });
/* @1:   */     }
/* @2:84 */     if (GregTech_API.sRecipeFile.get(ConfigCategories.Tools.mortar, "Clay", true)) {
/* @3:84 */       GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Clay, 1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { ToolDictNames.craftingToolMortar, new ItemStack(Blocks.clay, 1) });
/* @4:   */     }
/* @5:85 */     if (GregTech_API.sRecipeFile.get(ConfigCategories.Tools.mortar, "Wheat", true)) {
/* @6:85 */       GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Wheat, 1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { ToolDictNames.craftingToolMortar, new ItemStack(Items.wheat, 1) });
/* @7:   */     }
/* @8:86 */     if (GregTech_API.sRecipeFile.get(ConfigCategories.Tools.mortar, "Flint", true)) {
/* @9:86 */       GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Items.flint, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { ToolDictNames.craftingToolMortar, new ItemStack(Blocks.gravel, 1) });
/* A0:   */     }
/* A1:87 */     if (GregTech_API.sRecipeFile.get(ConfigCategories.Tools.mortar, "Blaze", true)) {
/* A2:87 */       GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Items.blaze_powder, 2), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { ToolDictNames.craftingToolMortar, new ItemStack(Items.blaze_rod, 1) });
/* A3:   */     }
/* A4:   */   }
/* A5:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.items.GT_MetaGenerated_Tool_01
 * JD-Core Version:    0.7.0.1
 */