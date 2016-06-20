package gregtech.common.items;

import gregtech.api.GregTech_API;
import gregtech.api.enums.*;
import gregtech.api.items.GT_MetaGenerated_Tool;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.common.tools.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class GT_MetaGenerated_Tool_01  extends GT_MetaGenerated_Tool {
    public static final short SWORD = 0;
    public static final short PICKAXE = 2;
    public static final short SHOVEL = 4;
    public static final short AXE = 6;
    public static final short HOE = 8;
    public static final short SAW = 10;
    public static final short HARDHAMMER = 12;
    public static final short SOFTHAMMER = 14;
    public static final short WRENCH = 16;
    public static final short FILE = 18;
    public static final short CROWBAR = 20;
    public static final short SCREWDRIVER = 22;
    public static final short MORTAR = 24;
    public static final short WIRECUTTER = 26;
    public static final short SCOOP = 28;
    public static final short BRANCHCUTTER = 30;
    public static final short UNIVERSALSPADE = 32;
    public static final short KNIFE = 34;
    public static final short BUTCHERYKNIFE = 36;
    public static final short SICKLE = 38;
    public static final short SENSE = 40;
    public static final short PLOW = 42;
    public static final short PLUNGER = 44;
    public static final short ROLLING_PIN = 46;
    public static final short DRILL_LV = 100;
    public static final short DRILL_MV = 102;
    public static final short DRILL_HV = 104;
    public static final short CHAINSAW_LV = 110;
    public static final short CHAINSAW_MV = 112;
    public static final short CHAINSAW_HV = 114;
    public static final short WRENCH_LV = 120;
    public static final short WRENCH_MV = 122;
    public static final short WRENCH_HV = 124;
    public static final short JACKHAMMER = 130;
    public static final short BUZZSAW = 140;
    public static final short SCREWDRIVER_LV = 150;
    public static final short SOLDERING_IRON_LV = 160;
    public static final short TURBINE_SMALL = 170;
    public static final short TURBINE = 172;
    public static final short TURBINE_LARGE = 174;
    public static final short TURBINE_HUGE = 176;
    public static final short TURBINE_BLADE = 178;
    public static GT_MetaGenerated_Tool_01 INSTANCE;

    public GT_MetaGenerated_Tool_01() {
        super("metatool.01");
        INSTANCE = this;
        addTool(0, "Sword", "", new GT_Tool_Sword(), new Object[]{ToolDictNames.craftingToolSword, ToolDictNames.craftingToolBlade, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 4L)});
        addTool(2, "Pickaxe", "", new GT_Tool_Pickaxe(), new Object[]{ToolDictNames.craftingToolPickaxe, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.PERFODIO, 4L)});
        addTool(4, "Shovel", "", new GT_Tool_Shovel(), new Object[]{ToolDictNames.craftingToolShovel, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.PERFODIO, 4L)});
        addTool(6, "Axe", "", new GT_Tool_Axe(), new Object[]{ToolDictNames.craftingToolAxe, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.METO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 2L)});
        addTool(8, "Hoe", "", new GT_Tool_Hoe(), new Object[]{ToolDictNames.craftingToolHoe, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 4L)});
        addTool(10, "Saw", "Can also harvest Ice", new GT_Tool_Saw(), new Object[]{ToolDictNames.craftingToolSaw, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.METO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 2L)});
        GregTech_API.registerTool(addTool(12, "Hammer", "Crushes Ores instead of harvesting them", new GT_Tool_HardHammer(), new Object[]{ToolDictNames.craftingToolHardHammer, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.FABRICO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.ORDO, 2L)}), GregTech_API.sHardHammerList);
        GregTech_API.registerTool(addTool(14, "Soft Mallet", "", new GT_Tool_SoftHammer(), new Object[]{ToolDictNames.craftingToolSoftHammer, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.LIMUS, 4L)}), GregTech_API.sSoftHammerList);
        GregTech_API.registerTool(addTool(WRENCH, "Wrench", "Hold Leftclick to dismantle Machines", new GT_Tool_Wrench(), new Object[]{ToolDictNames.craftingToolWrench, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.ORDO, 2L)}), GregTech_API.sWrenchList);
        addTool(18, "File", "", new GT_Tool_File(), new Object[]{ToolDictNames.craftingToolFile, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.FABRICO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.ORDO, 2L)});
        GregTech_API.registerTool(addTool(20, "Crowbar", "Dismounts Covers and Rotates Rails", new GT_Tool_Crowbar(), new Object[]{ToolDictNames.craftingToolCrowbar, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.FABRICO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 2L)}), GregTech_API.sCrowbarList);
        GregTech_API.registerTool(addTool(22, "Screwdriver", "Adjusts Covers and Machines", new GT_Tool_Screwdriver(), new Object[]{ToolDictNames.craftingToolScrewdriver, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.FABRICO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.ORDO, 2L)}), GregTech_API.sScrewdriverList);
        addTool(24, "Mortar", "", new GT_Tool_Mortar(), new Object[]{ToolDictNames.craftingToolMortar, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.FABRICO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.PERDITIO, 2L)});
        addTool(26, "Wire Cutter", "", new GT_Tool_WireCutter(), new Object[]{ToolDictNames.craftingToolWireCutter, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.FABRICO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.ORDO, 2L)});
        addTool(28, "Scoop", "", new GT_Tool_Scoop(), new Object[]{ToolDictNames.craftingToolScoop, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.BESTIA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.PANNUS, 2L)});
        addTool(30, "Branch Cutter", "", new GT_Tool_BranchCutter(), new Object[]{ToolDictNames.craftingToolBranchCutter, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.METO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 2L)});
        GregTech_API.registerTool(addTool(32, "Universal Spade", "", new GT_Tool_UniversalSpade(), new Object[]{ToolDictNames.craftingToolBlade, ToolDictNames.craftingToolShovel, ToolDictNames.craftingToolCrowbar, ToolDictNames.craftingToolSaw, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.METO, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FABRICO, 1L)}), GregTech_API.sCrowbarList);
        addTool(34, "Knife", "", new GT_Tool_Knife(), new Object[]{ToolDictNames.craftingToolBlade, ToolDictNames.craftingToolKnife, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.CORPUS, 2L)});
        addTool(36, "Butchery Knife", "Has a slow Attack Rate", new GT_Tool_ButcheryKnife(), new Object[]{ToolDictNames.craftingToolBlade, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.FABRICO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.CORPUS, 4L)});

        addTool(40, "Sense", "Because a Scythe doesn't make Sense", new GT_Tool_Sense(), new Object[]{ToolDictNames.craftingToolBlade, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.METO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.MORTUUS, 2L)});
        addTool(42, "Plow", "Used to get rid of Snow", new GT_Tool_Plow(), new Object[]{ToolDictNames.craftingToolPlow, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.METO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.GELUM, 2L)});
        addTool(44, "Plunger", "", new GT_Tool_Plunger(), new Object[]{ToolDictNames.craftingToolPlunger, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.VACUOS, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.ITER, 2L)});
        addTool(46, "Rolling Pin", "", new GT_Tool_RollingPin(), new Object[]{ToolDictNames.craftingToolRollingPin, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.LIMUS, 4L)});

        addTool(100, "Drill (LV)", "", new GT_Tool_Drill_LV(), new Object[]{ToolDictNames.craftingToolMiningDrill, new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.PERFODIO, 4L)});
        addTool(102, "Drill (MV)", "", new GT_Tool_Drill_MV(), new Object[]{ToolDictNames.craftingToolMiningDrill, new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.PERFODIO, 4L)});
        addTool(104, "Drill (HV)", "", new GT_Tool_Drill_HV(), new Object[]{ToolDictNames.craftingToolMiningDrill, new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.PERFODIO, 4L)});
        addTool(110, "Chainsaw (LV)", "Can also harvest Ice", new GT_Tool_Chainsaw_LV(), new Object[]{ToolDictNames.craftingToolSaw, new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.METO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 2L)});
        addTool(112, "Chainsaw (MV)", "Can also harvest Ice", new GT_Tool_Chainsaw_MV(), new Object[]{ToolDictNames.craftingToolSaw, new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.METO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 2L)});
        addTool(114, "Chainsaw (HV)", "Can also harvest Ice", new GT_Tool_Chainsaw_HV(), new Object[]{ToolDictNames.craftingToolSaw, new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.METO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 2L)});
        GregTech_API.registerTool(addTool(WRENCH_LV, "Wrench (LV)", "Hold Leftclick to dismantle Machines", new GT_Tool_Wrench_LV(), new Object[]{ToolDictNames.craftingToolWrench, new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.ORDO, 2L)}), GregTech_API.sWrenchList);
        GregTech_API.registerTool(addTool(WRENCH_MV, "Wrench (MV)", "Hold Leftclick to dismantle Machines", new GT_Tool_Wrench_MV(), new Object[]{ToolDictNames.craftingToolWrench, new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.ORDO, 2L)}), GregTech_API.sWrenchList);
        GregTech_API.registerTool(addTool(WRENCH_HV, "Wrench (HV)", "Hold Leftclick to dismantle Machines", new GT_Tool_Wrench_HV(), new Object[]{ToolDictNames.craftingToolWrench, new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.ORDO, 2L)}), GregTech_API.sWrenchList);
        addTool(130, "JackHammer (HV)", "Breaks Rocks into pieces", new GT_Tool_JackHammer(), new Object[]{ToolDictNames.craftingToolJackHammer, new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.PERFODIO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.PERDITIO, 2L)});
        addTool(140, "Buzzsaw (LV)", "Not suitable for harvesting Blocks", new GT_Tool_BuzzSaw(), new Object[]{ToolDictNames.craftingToolSaw, new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.FABRICO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 2L)});
        GregTech_API.registerTool(addTool(150, "Screwdriver (LV)", "Adjusts Covers and Machines", new GT_Tool_Screwdriver_LV(), new Object[]{ToolDictNames.craftingToolScrewdriver, new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.FABRICO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.ORDO, 2L)}), GregTech_API.sScrewdriverList);
        GregTech_API.registerTool(addTool(SOLDERING_IRON_LV, "Soldering Iron (LV)", "Fixes burned out Circuits. Needs soldering materials in inventory and 10kEU", new GT_Tool_Soldering_Iron(), new Object[]{ToolDictNames.craftingToolSolderingIron, new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.FABRICO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.ORDO, 2L)}), GregTech_API.sSolderingToolList);

        addTool(TURBINE_SMALL, "Small Turbine", "Turbine Rotors for your power station", new GT_Tool_Turbine_Small(), new Object[]{});
        addTool(TURBINE, "Turbine", "Turbine Rotors for your power station", new GT_Tool_Turbine_Normal(), new Object[]{});
        addTool(TURBINE_LARGE, "Large Turbine", "Turbine Rotors for your power station", new GT_Tool_Turbine_Large(), new Object[]{});
        addTool(TURBINE_HUGE, "Huge Turbine", "Turbine Rotors for your power station", new GT_Tool_Turbine_Huge(), new Object[]{});

        GT_ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(24, 1, Materials.Flint, Materials.Stone, null), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" I ", "SIS", "SSS", Character.valueOf('I'), new ItemStack(Items.flint, 1), Character.valueOf('S'), OrePrefixes.stone});
        GT_ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(24, 1, Materials.Bronze, Materials.Stone, null), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" I ", "SIS", "SSS", Character.valueOf('I'), OrePrefixes.ingot.get(Materials.Bronze), Character.valueOf('S'), OrePrefixes.stone});
        GT_ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(24, 1, Materials.Iron, Materials.Stone, null), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" I ", "SIS", "SSS", Character.valueOf('I'), OrePrefixes.ingot.get(Materials.Iron), Character.valueOf('S'), OrePrefixes.stone});
        GT_ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(24, 1, Materials.Steel, Materials.Stone, null), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" I ", "SIS", "SSS", Character.valueOf('I'), OrePrefixes.ingot.get(Materials.Steel), Character.valueOf('S'), OrePrefixes.stone});
        GT_ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(24, 1, Materials.WroughtIron, Materials.Stone, null), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" I ", "SIS", "SSS", Character.valueOf('I'), OrePrefixes.ingot.get(Materials.WroughtIron), Character.valueOf('S'), OrePrefixes.stone});
        GT_ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(24, 1, Materials.RedSteel, Materials.Stone, null), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" I ", "SIS", "SSS", Character.valueOf('I'), OrePrefixes.ingot.get(Materials.RedSteel), Character.valueOf('S'), OrePrefixes.stone});
        GT_ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(24, 1, Materials.BlueSteel, Materials.Stone, null), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" I ", "SIS", "SSS", Character.valueOf('I'), OrePrefixes.ingot.get(Materials.BlueSteel), Character.valueOf('S'), OrePrefixes.stone});
        GT_ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(24, 1, Materials.BlackSteel, Materials.Stone, null), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" I ", "SIS", "SSS", Character.valueOf('I'), OrePrefixes.ingot.get(Materials.BlackSteel), Character.valueOf('S'), OrePrefixes.stone});
        GT_ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(24, 1, Materials.DamascusSteel, Materials.Stone, null), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" I ", "SIS", "SSS", Character.valueOf('I'), OrePrefixes.ingot.get(Materials.DamascusSteel), Character.valueOf('S'), OrePrefixes.stone});
        GT_ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(24, 1, Materials.Thaumium, Materials.Stone, null), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" I ", "SIS", "SSS", Character.valueOf('I'), OrePrefixes.ingot.get(Materials.Thaumium), Character.valueOf('S'), OrePrefixes.stone});

        GT_ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(46, 1, Materials.Wood, Materials.Wood, null), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"  S", " I ", "S f", Character.valueOf('I'), OrePrefixes.plank.get(Materials.Wood), Character.valueOf('S'), OrePrefixes.stick.get(Materials.Wood)});
        GT_ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(46, 1, Materials.Plastic, Materials.Plastic, null), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"  S", " I ", "S f", Character.valueOf('I'), OrePrefixes.ingot.get(Materials.Plastic), Character.valueOf('S'), OrePrefixes.stick.get(Materials.Plastic)});
        GT_ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(46, 1, Materials.Aluminium, Materials.Aluminium, null), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"  S", " I ", "S f", Character.valueOf('I'), OrePrefixes.ingot.get(Materials.Aluminium), Character.valueOf('S'), OrePrefixes.stick.get(Materials.Aluminium)});
        GT_ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(46, 1, Materials.StainlessSteel, Materials.StainlessSteel, null), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"  S", " I ", "S f", Character.valueOf('I'), OrePrefixes.ingot.get(Materials.StainlessSteel), Character.valueOf('S'), OrePrefixes.stick.get(Materials.StainlessSteel)});


        if (!GregTech_API.sSpecialFile.get(ConfigCategories.general, "DisableFlintTools", false)) {
            GT_ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(0, 1, Materials.Flint, Materials.Wood, null), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"F", "F", "S", Character.valueOf('S'), OrePrefixes.stick.get(Materials.Wood), Character.valueOf('F'), new ItemStack(Items.flint, 1)});
            GT_ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(2, 1, Materials.Flint, Materials.Wood, null), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"FFF", " S ", " S ", Character.valueOf('S'), OrePrefixes.stick.get(Materials.Wood), Character.valueOf('F'), new ItemStack(Items.flint, 1)});
            GT_ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(4, 1, Materials.Flint, Materials.Wood, null), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"F", "S", "S", Character.valueOf('S'), OrePrefixes.stick.get(Materials.Wood), Character.valueOf('F'), new ItemStack(Items.flint, 1)});
            GT_ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(6, 1, Materials.Flint, Materials.Wood, null), GT_ModHandler.RecipeBits.MIRRORED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"FF", "FS", " S", Character.valueOf('S'), OrePrefixes.stick.get(Materials.Wood), Character.valueOf('F'), new ItemStack(Items.flint, 1)});
            GT_ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(8, 1, Materials.Flint, Materials.Wood, null), GT_ModHandler.RecipeBits.MIRRORED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"FF", " S", " S", Character.valueOf('S'), OrePrefixes.stick.get(Materials.Wood), Character.valueOf('F'), new ItemStack(Items.flint, 1)});
            GT_ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(34, 1, Materials.Flint, Materials.Wood, null), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"F", "S", Character.valueOf('S'), OrePrefixes.stick.get(Materials.Wood), Character.valueOf('F'), new ItemStack(Items.flint, 1)});
        }
        if (GregTech_API.sRecipeFile.get(ConfigCategories.Tools.mortar, "Coal", true)) {
            GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Coal, 1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ToolDictNames.craftingToolMortar, new ItemStack(Items.coal, 1)});
        }
        if (GregTech_API.sRecipeFile.get(ConfigCategories.Tools.mortar, "Clay", true)) {
            GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Clay, 1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ToolDictNames.craftingToolMortar, new ItemStack(Blocks.clay, 1)});
        }
        if (GregTech_API.sRecipeFile.get(ConfigCategories.Tools.mortar, "Wheat", true)) {
            GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Wheat, 1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ToolDictNames.craftingToolMortar, new ItemStack(Items.wheat, 1)});
        }
        if (GregTech_API.sRecipeFile.get(ConfigCategories.Tools.mortar, "Flint", true)) {
            GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Items.flint, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ToolDictNames.craftingToolMortar, new ItemStack(Blocks.gravel, 1)});
        }
        if (GregTech_API.sRecipeFile.get(ConfigCategories.Tools.mortar, "Blaze", true)) {
            GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Items.blaze_powder, 2), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ToolDictNames.craftingToolMortar, new ItemStack(Items.blaze_rod, 1)});
        }
    }
}
