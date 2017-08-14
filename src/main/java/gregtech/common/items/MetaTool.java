package gregtech.common.items;

import gregtech.api.ConfigCategories;
import gregtech.api.GregTech_API;
import gregtech.api.items.ToolDictNames;
import gregtech.api.items.toolitem.ToolMetaItem;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.tools.*;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class MetaTool extends ToolMetaItem {
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
    public static final short TURBINE_NORMAL = 172;
    public static final short TURBINE_LARGE = 174;
    public static final short TURBINE_HUGE = 176;
    public static MetaTool INSTANCE;

    public MetaTool() {
        super("metatool.01");
        INSTANCE = this;
        addTool(0, "Sword", "", new ToolSword(), new Object[]{ToolDictNames.craftingToolSword, ToolDictNames.craftingToolBlade);
        addTool(2, "Pickaxe", "", new ToolPickaxe(), new Object[]{ToolDictNames.craftingToolPickaxe, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.PERFODIO, 4L)});
        addTool(4, "Shovel", "", new ToolShovel(), new Object[]{ToolDictNames.craftingToolShovel, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.PERFODIO, 4L)});
        addTool(6, "Axe", "", new ToolAxe(), new Object[]{ToolDictNames.craftingToolAxe, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.METO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 2L)});
        addTool(8, "Hoe", "", new ToolHoe(), new Object[]{ToolDictNames.craftingToolHoe, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 4L)});
        addTool(10, "Saw", "Can also harvest Ice", new ToolSaw(), new Object[]{ToolDictNames.craftingToolSaw, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.METO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 2L)});
        GregTech_API.registerTool(addTool(12, "Hammer", "Crushes Ores instead of harvesting them", new ToolHardHammer(), new Object[]{ToolDictNames.craftingToolHardHammer, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.FABRICO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.ORDO, 2L)}), GregTech_API.sHardHammerList);
        GregTech_API.registerTool(addTool(14, "Soft Mallet", "", new ToolSoftHammer(), new Object[]{ToolDictNames.craftingToolSoftHammer, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.LIMUS, 4L)}), GregTech_API.sSoftHammerList);
        GregTech_API.registerTool(addTool(WRENCH, "Wrench", "Hold Leftclick to dismantle Machines", new ToolWrench(), new Object[]{ToolDictNames.craftingToolWrench, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.ORDO, 2L)}), GregTech_API.sWrenchList);
        addTool(18, "File", "", new ToolFile(), new Object[]{ToolDictNames.craftingToolFile, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.FABRICO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.ORDO, 2L)});
        GregTech_API.registerTool(addTool(20, "Crowbar", "Dismounts Covers and Rotates Rails", new ToolCrowbar(), new Object[]{ToolDictNames.craftingToolCrowbar, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.FABRICO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 2L)}), GregTech_API.sCrowbarList);
        GregTech_API.registerTool(addTool(22, "Screwdriver", "Adjusts Covers and Machines", new ToolScrewdriver(), new Object[]{ToolDictNames.craftingToolScrewdriver, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.FABRICO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.ORDO, 2L)}), GregTech_API.sScrewdriverList);
        addTool(24, "Mortar", "", new ToolMortar(), new Object[]{ToolDictNames.craftingToolMortar, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.FABRICO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.PERDITIO, 2L)});
        addTool(26, "Wire Cutter", "", new ToolWireCutter(), new Object[]{ToolDictNames.craftingToolWireCutter, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.FABRICO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.ORDO, 2L)});
        addTool(28, "Scoop", "", new ToolScoop(), new Object[]{ToolDictNames.craftingToolScoop, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.BESTIA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.PANNUS, 2L)});
        addTool(30, "Branch Cutter", "", new ToolBranchCutter(), new Object[]{ToolDictNames.craftingToolBranchCutter, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.METO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 2L)});
        GregTech_API.registerTool(addTool(32, "Universal Spade", "", new ToolUniversalSpade(), new Object[]{ToolDictNames.craftingToolBlade, ToolDictNames.craftingToolShovel, ToolDictNames.craftingToolCrowbar, ToolDictNames.craftingToolSaw, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.METO, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FABRICO, 1L)}), GregTech_API.sCrowbarList);
        addTool(34, "Knife", "", new ToolKnife(), new Object[]{ToolDictNames.craftingToolBlade, ToolDictNames.craftingToolKnife, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.CORPUS, 2L)});
        addTool(36, "Butchery Knife", "Has a slow Attack Rate", new ToolButcheryKnife(), new Object[]{ToolDictNames.craftingToolBlade, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.FABRICO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.CORPUS, 4L)});

        addTool(40, "Sense", "Because a Scythe doesn't make Sense", new ToolSense(), new Object[]{ToolDictNames.craftingToolBlade, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.METO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.MORTUUS, 2L)});
        addTool(42, "Plow", "Used to get rid of Snow", new ToolPlow(), new Object[]{ToolDictNames.craftingToolPlow, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.METO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.GELUM, 2L)});
        addTool(44, "Plunger", "", new ToolPlunger(), new Object[]{ToolDictNames.craftingToolPlunger, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.VACUOS, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.ITER, 2L)});
        addTool(46, "Rolling Pin", "", new ToolRollingPin(), new Object[]{ToolDictNames.craftingToolRollingPin, new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.LIMUS, 4L)});

        addTool(100, "Drill (LV)", "", new ToolDrillLV(), new Object[]{ToolDictNames.craftingToolMiningDrill, new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.PERFODIO, 4L)});
        addTool(102, "Drill (MV)", "", new ToolDrillMV(), new Object[]{ToolDictNames.craftingToolMiningDrill, new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.PERFODIO, 4L)});
        addTool(104, "Drill (HV)", "", new ToolDrillHV(), new Object[]{ToolDictNames.craftingToolMiningDrill, new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.PERFODIO, 4L)});
        addTool(110, "Chainsaw (LV)", "Can also harvest Ice", new ToolChainsawLV(), new Object[]{ToolDictNames.craftingToolSaw, new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.METO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 2L)});
        addTool(112, "Chainsaw (MV)", "Can also harvest Ice", new ToolChainsawMV(), new Object[]{ToolDictNames.craftingToolSaw, new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.METO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 2L)});
        addTool(114, "Chainsaw (HV)", "Can also harvest Ice", new ToolChainsawHV(), new Object[]{ToolDictNames.craftingToolSaw, new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.METO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 2L)});
        GregTech_API.registerTool(addTool(WRENCH_LV, "Wrench (LV)", "Hold Leftclick to dismantle Machines", new ToolWrenchLV(), new Object[]{ToolDictNames.craftingToolWrench, new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.ORDO, 2L)}), GregTech_API.sWrenchList);
        GregTech_API.registerTool(addTool(WRENCH_MV, "Wrench (MV)", "Hold Leftclick to dismantle Machines", new ToolWrenchMV(), new Object[]{ToolDictNames.craftingToolWrench, new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.ORDO, 2L)}), GregTech_API.sWrenchList);
        GregTech_API.registerTool(addTool(WRENCH_HV, "Wrench (HV)", "Hold Leftclick to dismantle Machines", new ToolWrenchHV(), new Object[]{ToolDictNames.craftingToolWrench, new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.ORDO, 2L)}), GregTech_API.sWrenchList);
        addTool(130, "JackHammer (HV)", "Breaks Rocks into pieces", new ToolJackHammer(), new Object[]{ToolDictNames.craftingToolJackHammer, new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.PERFODIO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.PERDITIO, 2L)});
        addTool(140, "Buzzsaw (LV)", "Not suitable for harvesting Blocks", new ToolBuzzSaw(), new Object[]{ToolDictNames.craftingToolSaw, new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.FABRICO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 2L)});
        GregTech_API.registerTool(addTool(150, "Screwdriver (LV)", "Adjusts Covers and Machines", new ToolScrewdriverLV(), new Object[]{ToolDictNames.craftingToolScrewdriver, new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.FABRICO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.ORDO, 2L)}), GregTech_API.sScrewdriverList);
        GregTech_API.registerTool(addTool(SOLDERING_IRON_LV, "Soldering Iron (LV)", "Fixes burned out Circuits. Needs soldering materials in inventory and 10kEU", new ToolSolderingIron(), new Object[]{ToolDictNames.craftingToolSolderingIron, new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.FABRICO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.ORDO, 2L)}), GregTech_API.sSolderingToolList);

        addTool(TURBINE_SMALL, "Small Turbine", "Turbine Rotors for your power station", new ToolTurbineSmall(), new Object[]{});
        addTool(TURBINE_NORMAL, "Turbine", "Turbine Rotors for your power station", new ToolTurbineNormal(), new Object[]{});
        addTool(TURBINE_LARGE, "Large Turbine", "Turbine Rotors for your power station", new ToolTurbineLarge(), new Object[]{});
        addTool(TURBINE_HUGE, "Huge Turbine", "Turbine Rotors for your power station", new ToolTurbineHuge(), new Object[]{});

        ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(24, 1, Materials.Flint, Materials.Stone, null), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" I ", "SIS", "SSS", 'I', new ItemStack(Items.FLINT, 1), 'S', OrePrefix.stone});
        ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(24, 1, Materials.Bronze, Materials.Stone, null), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" I ", "SIS", "SSS", 'I', OrePrefix.ingot.get(Materials.Bronze), 'S', OrePrefix.stone});
        ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(24, 1, Materials.Iron, Materials.Stone, null), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" I ", "SIS", "SSS", 'I', OrePrefix.ingot.get(Materials.Iron), 'S', OrePrefix.stone});
        ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(24, 1, Materials.Steel, Materials.Stone, null), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" I ", "SIS", "SSS", 'I', OrePrefix.ingot.get(Materials.Steel), 'S', OrePrefix.stone});
        ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(24, 1, Materials.WroughtIron, Materials.Stone, null), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" I ", "SIS", "SSS", 'I', OrePrefix.ingot.get(Materials.WroughtIron), 'S', OrePrefix.stone});
        ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(24, 1, Materials.RedSteel, Materials.Stone, null), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" I ", "SIS", "SSS", 'I', OrePrefix.ingot.get(Materials.RedSteel), 'S', OrePrefix.stone});
        ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(24, 1, Materials.BlueSteel, Materials.Stone, null), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" I ", "SIS", "SSS", 'I', OrePrefix.ingot.get(Materials.BlueSteel), 'S', OrePrefix.stone});
        ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(24, 1, Materials.BlackSteel, Materials.Stone, null), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" I ", "SIS", "SSS", 'I', OrePrefix.ingot.get(Materials.BlackSteel), 'S', OrePrefix.stone});
        ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(24, 1, Materials.DamascusSteel, Materials.Stone, null), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" I ", "SIS", "SSS", 'I', OrePrefix.ingot.get(Materials.DamascusSteel), 'S', OrePrefix.stone});
        ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(24, 1, Materials.Thaumium, Materials.Stone, null), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" I ", "SIS", "SSS", 'I', OrePrefix.ingot.get(Materials.Thaumium), 'S', OrePrefix.stone});

        ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(46, 1, Materials.Wood, Materials.Wood, null), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"  S", " I ", "S f", 'I', OrePrefix.plank.get(Materials.Wood), 'S', OrePrefix.stick.get(Materials.Wood)});
        ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(46, 1, Materials.Plastic, Materials.Plastic, null), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"  S", " I ", "S f", 'I', OrePrefix.ingot.get(Materials.Plastic), 'S', OrePrefix.stick.get(Materials.Plastic)});
        ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(46, 1, Materials.Aluminium, Materials.Aluminium, null), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"  S", " I ", "S f", 'I', OrePrefix.ingot.get(Materials.Aluminium), 'S', OrePrefix.stick.get(Materials.Aluminium)});
        ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(46, 1, Materials.StainlessSteel, Materials.StainlessSteel, null), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"  S", " I ", "S f", 'I', OrePrefix.ingot.get(Materials.StainlessSteel), 'S', OrePrefix.stick.get(Materials.StainlessSteel)});


        if (!GregTech_API.sSpecialFile.get(ConfigCategories.general, "DisableFlintTools", false)) {
            ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(0, 1, Materials.Flint, Materials.Wood, null), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"F", "F", "S", 'S', OrePrefix.stick.get(Materials.Wood), 'F', new ItemStack(Items.FLINT, 1)});
            ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(2, 1, Materials.Flint, Materials.Wood, null), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"FFF", " S ", " S ", 'S', OrePrefix.stick.get(Materials.Wood), 'F', new ItemStack(Items.FLINT, 1)});
            ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(4, 1, Materials.Flint, Materials.Wood, null), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"F", "S", "S", 'S', OrePrefix.stick.get(Materials.Wood), 'F', new ItemStack(Items.FLINT, 1)});
            ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(6, 1, Materials.Flint, Materials.Wood, null), ModHandler.RecipeBits.MIRRORED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"FF", "FS", " S", 'S', OrePrefix.stick.get(Materials.Wood), 'F', new ItemStack(Items.FLINT, 1)});
            ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(8, 1, Materials.Flint, Materials.Wood, null), ModHandler.RecipeBits.MIRRORED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"FF", " S", " S", 'S', OrePrefix.stick.get(Materials.Wood), 'F', new ItemStack(Items.FLINT, 1)});
            ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(34, 1, Materials.Flint, Materials.Wood, null), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"F", "S", 'S', OrePrefix.stick.get(Materials.Wood), 'F', new ItemStack(Items.FLINT, 1)});

            ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(MORTAR, 1, Materials.Flint, Materials.Stone, null), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" I ", "SIS", "SSS", Character.valueOf('I'), new ItemStack(Items.FLINT, 1), Character.valueOf('S'), OrePrefix.stone});
            ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(MORTAR, 1, Materials.Bronze, Materials.Stone, null), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" I ", "SIS", "SSS", Character.valueOf('I'), OrePrefix.ingot.get(Materials.Bronze), Character.valueOf('S'), OrePrefix.stone});
            ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(MORTAR, 1, Materials.Iron, Materials.Stone, null), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" I ", "SIS", "SSS", Character.valueOf('I'), OrePrefix.ingot.get(Materials.Iron), Character.valueOf('S'), OrePrefix.stone});
            ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(MORTAR, 1, Materials.Steel, Materials.Stone, null), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" I ", "SIS", "SSS", Character.valueOf('I'), OrePrefix.ingot.get(Materials.Steel), Character.valueOf('S'), OrePrefix.stone});
            ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(MORTAR, 1, Materials.WroughtIron, Materials.Stone, null), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" I ", "SIS", "SSS", Character.valueOf('I'), OrePrefix.ingot.get(Materials.WroughtIron), Character.valueOf('S'), OrePrefix.stone});
            ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(MORTAR, 1, Materials.RedSteel, Materials.Stone, null), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" I ", "SIS", "SSS", Character.valueOf('I'), OrePrefix.ingot.get(Materials.RedSteel), Character.valueOf('S'), OrePrefix.stone});
            ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(MORTAR, 1, Materials.BlueSteel, Materials.Stone, null), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" I ", "SIS", "SSS", Character.valueOf('I'), OrePrefix.ingot.get(Materials.BlueSteel), Character.valueOf('S'), OrePrefix.stone});
            ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(MORTAR, 1, Materials.BlackSteel, Materials.Stone, null), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" I ", "SIS", "SSS", Character.valueOf('I'), OrePrefix.ingot.get(Materials.BlackSteel), Character.valueOf('S'), OrePrefix.stone});
            ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(MORTAR, 1, Materials.DamascusSteel, Materials.Stone, null), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" I ", "SIS", "SSS", Character.valueOf('I'), OrePrefix.ingot.get(Materials.DamascusSteel), Character.valueOf('S'), OrePrefix.stone});
            ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(MORTAR, 1, Materials.Thaumium, Materials.Stone, null), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" I ", "SIS", "SSS", Character.valueOf('I'), OrePrefix.ingot.get(Materials.Thaumium), Character.valueOf('S'), OrePrefix.stone});

            ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(ROLLING_PIN, 1, Materials.Wood, Materials.Wood, null), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"  S", " I ", "S f", Character.valueOf('I'), OrePrefix.plank.get(Materials.Wood), Character.valueOf('S'), OrePrefix.stick.get(Materials.Wood)});
            ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(ROLLING_PIN, 1, Materials.Plastic, Materials.Plastic, null), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"  S", " I ", "S f", Character.valueOf('I'), OrePrefix.ingot.get(Materials.Plastic), Character.valueOf('S'), OrePrefix.stick.get(Materials.Plastic)});
            ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(ROLLING_PIN, 1, Materials.Aluminium, Materials.Aluminium, null), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"  S", " I ", "S f", Character.valueOf('I'), OrePrefix.ingot.get(Materials.Aluminium), Character.valueOf('S'), OrePrefix.stick.get(Materials.Aluminium)});
            ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(ROLLING_PIN, 1, Materials.StainlessSteel, Materials.StainlessSteel, null), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"  S", " I ", "S f", Character.valueOf('I'), OrePrefix.ingot.get(Materials.StainlessSteel), Character.valueOf('S'), OrePrefix.stick.get(Materials.StainlessSteel)});


            if (!GregTech_API.sSpecialFile.get(ConfigCategories.general, "DisableFlintTools", false)) {
                ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(SWORD, 1, Materials.Flint, Materials.Wood, null), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"F", "F", "S", Character.valueOf('S'), OrePrefix.stick.get(Materials.Wood), 'F', new ItemStack(Items.FLINT, 1)});
                ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(PICKAXE, 1, Materials.Flint, Materials.Wood, null), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"FFF", " S ", " S ", Character.valueOf('S'), OrePrefix.stick.get(Materials.Wood), 'F', new ItemStack(Items.FLINT, 1)});
                ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(SHOVEL, 1, Materials.Flint, Materials.Wood, null), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"F", "S", "S", Character.valueOf('S'), OrePrefix.stick.get(Materials.Wood), 'F', new ItemStack(Items.FLINT, 1)});
                ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(AXE, 1, Materials.Flint, Materials.Wood, null), ModHandler.RecipeBits.MIRRORED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"FF", "FS", " S", Character.valueOf('S'), OrePrefix.stick.get(Materials.Wood), 'F', new ItemStack(Items.FLINT, 1)});
                ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(HOE, 1, Materials.Flint, Materials.Wood, null), ModHandler.RecipeBits.MIRRORED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"FF", " S", " S", Character.valueOf('S'), OrePrefix.stick.get(Materials.Wood), 'F', new ItemStack(Items.FLINT, 1)});
                ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(KNIFE, 1, Materials.Flint, Materials.Wood, null), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"F", "S", Character.valueOf('S'), OrePrefix.stick.get(Materials.Wood), 'F', new ItemStack(Items.FLINT, 1)});

            }
            if (GregTech_API.sRecipeFile.get(ConfigCategories.Tools.mortar, "Coal", true)) {
                ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, Materials.Coal, 1), ModHandler.RecipeBits.NOT_REMOVABLE, ToolDictNames.craftingToolMortar, new ItemStack(Items.COAL, 1));
            }
            if (GregTech_API.sRecipeFile.get(ConfigCategories.Tools.mortar, "Clay", true)) {
                ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, Materials.Clay, 1), ModHandler.RecipeBits.NOT_REMOVABLE, ToolDictNames.craftingToolMortar, new ItemStack(Blocks.CLAY, 1));
            }
            if (GregTech_API.sRecipeFile.get(ConfigCategories.Tools.mortar, "Wheat", true)) {
                ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, Materials.Wheat, 1), ModHandler.RecipeBits.NOT_REMOVABLE, ToolDictNames.craftingToolMortar, new ItemStack(Items.WHEAT, 1));
            }
            if (GregTech_API.sRecipeFile.get(ConfigCategories.Tools.mortar, "Flint", true)) {
                ModHandler.addShapelessCraftingRecipe(new ItemStack(Items.FLINT, 1), ModHandler.RecipeBits.NOT_REMOVABLE, ToolDictNames.craftingToolMortar, new ItemStack(Blocks.GRAVEL, 1));
            }
            if (GregTech_API.sRecipeFile.get(ConfigCategories.Tools.mortar, "Blaze", true)) {
                ModHandler.addShapelessCraftingRecipe(new ItemStack(Items.BLAZE_POWDER, 2), ModHandler.RecipeBits.NOT_REMOVABLE, ToolDictNames.craftingToolMortar, new ItemStack(Items.BLAZE_ROD, 1));
            }
        }
    }
}