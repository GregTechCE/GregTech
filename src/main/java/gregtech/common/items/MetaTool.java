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

public class MetaTool extends ToolMetaItem<ToolMetaItem.MetaToolValueItem> {

    public static final short SWORD = 0;
    public static final short PICKAXE = 1;
    public static final short SHOVEL = 2;
    public static final short AXE = 3;
    public static final short HOE = 4;
    public static final short SAW = 5;
    public static final short HARDHAMMER = 6;
    public static final short SOFTHAMMER = 7;
    public static final short WRENCH = 8;
    public static final short FILE = 9;
    public static final short CROWBAR = 10;
    public static final short SCREWDRIVER = 11;
    public static final short MORTAR = 12;
    public static final short WIRECUTTER = 13;
    public static final short SCOOP = 14;
    public static final short BRANCHCUTTER = 15;
    public static final short UNIVERSALSPADE = 16;
    public static final short KNIFE = 17;
    public static final short BUTCHERYKNIFE = 18;
    public static final short SENSE = 19;
    public static final short PLOW = 20;
    public static final short PLUNGER = 21;
    public static final short ROLLING_PIN = 22;
    public static final short DRILL_LV = 23;
    public static final short DRILL_MV = 24;
    public static final short DRILL_HV = 25;
    public static final short CHAINSAW_LV = 26;
    public static final short CHAINSAW_MV = 27;
    public static final short CHAINSAW_HV = 28;
    public static final short WRENCH_LV = 29;
    public static final short WRENCH_MV = 30;
    public static final short WRENCH_HV = 31;
    public static final short JACKHAMMER = 32;
    public static final short BUZZSAW = 33;
    public static final short SCREWDRIVER_LV = 34;
    public static final short SOLDERING_IRON_LV = 35;
    public static final short TURBINE_SMALL = 36;
    public static final short TURBINE_NORMAL = 37;
    public static final short TURBINE_LARGE = 38;
    public static final short TURBINE_HUGE = 39;

    public MetaTool() {
        super("metatool");

        addItem(SWORD).setToolStats(new ToolSword()).addOreDict(ToolDictNames.craftingToolSword); //"Sword", "",
        addItem(PICKAXE).setToolStats(new ToolPickaxe()).addOreDict(ToolDictNames.craftingToolPickaxe); //"Pickaxe", "",
        addItem(SHOVEL).setToolStats(new ToolShovel()).addOreDict(ToolDictNames.craftingToolShovel); // "Shovel", "",
        addTool(AXE, "Axe", "", new ToolAxe(), ToolDictNames.craftingToolAxe.name());
        addTool(HOE, "Hoe", "", new ToolHoe(), ToolDictNames.craftingToolHoe.name());
        addTool(SAW, "Saw", "Can also harvest Ice", new ToolSaw(), ToolDictNames.craftingToolSaw.name());
        GregTech_API.registerTool(addTool(HARDHAMMER, "Hammer", "Crushes Ores instead of harvesting them", new ToolHardHammer(), ToolDictNames.craftingToolHardHammer.name()), GregTech_API.sHardHammerList);
        GregTech_API.registerTool(addTool(SOFTHAMMER, "Soft Mallet", "", new ToolSoftHammer(), ToolDictNames.craftingToolSoftHammer.name()), GregTech_API.sSoftHammerList);
        GregTech_API.registerTool(addTool(WRENCH, "Wrench", "Hold Leftclick to dismantle Machines", new ToolWrench(), ToolDictNames.craftingToolWrench.name()), GregTech_API.sWrenchList);
        addTool(FILE, "File", "", new ToolFile(), ToolDictNames.craftingToolFile.name());
        GregTech_API.registerTool(addTool(CROWBAR, "Crowbar", "Dismounts Covers and Rotates Rails", new ToolCrowbar(), ToolDictNames.craftingToolCrowbar.name()), GregTech_API.sCrowbarList);
        GregTech_API.registerTool(addTool(SCREWDRIVER, "Screwdriver", "Adjusts Covers and Machines", new ToolScrewdriver(), ToolDictNames.craftingToolScrewdriver.name()), GregTech_API.sScrewdriverList);
        addTool(MORTAR, "Mortar", "", new ToolMortar(), ToolDictNames.craftingToolMortar.name());
        addTool(WIRECUTTER, "Wire Cutter", "", new ToolWireCutter(), ToolDictNames.craftingToolWireCutter.name());
        addTool(SCOOP, "Scoop", "", new ToolScoop(), ToolDictNames.craftingToolScoop.name());
        addTool(BRANCHCUTTER, "Branch Cutter", "", new ToolBranchCutter(), ToolDictNames.craftingToolBranchCutter.name());
        GregTech_API.registerTool(addTool(UNIVERSALSPADE, "Universal Spade", "", new ToolUniversalSpade(), ToolDictNames.craftingToolBlade.name(), ToolDictNames.craftingToolShovel.name(), ToolDictNames.craftingToolCrowbar.name(), ToolDictNames.craftingToolSaw.name()), GregTech_API.sCrowbarList);
        addTool(KNIFE, "Knife", "", new ToolKnife(), ToolDictNames.craftingToolBlade.name(), ToolDictNames.craftingToolKnife.name());
        addTool(BUTCHERYKNIFE, "Butchery Knife", "Has a slow Attack Rate", new ToolButcheryKnife(), ToolDictNames.craftingToolBlade.name());

        addTool(SENSE, "Sense", "Because a Scythe doesn't make Sense", new ToolSense(), ToolDictNames.craftingToolBlade.name());
        addTool(PLOW, "Plow", "Used to get rid of Snow", new ToolPlow(), ToolDictNames.craftingToolPlow.name());
        addTool(PLUNGER, "Plunger", "", new ToolPlunger(), ToolDictNames.craftingToolPlunger.name());
        addTool(ROLLING_PIN, "Rolling Pin", "", new ToolRollingPin(), ToolDictNames.craftingToolRollingPin.name());

        addTool(DRILL_LV, "Drill (LV)", "", new ToolDrillLV(), ToolDictNames.craftingToolMiningDrill.name());
        addTool(DRILL_MV, "Drill (MV)", "", new ToolDrillMV(), ToolDictNames.craftingToolMiningDrill.name());
        addTool(DRILL_HV, "Drill (HV)", "", new ToolDrillHV(), ToolDictNames.craftingToolMiningDrill.name());
        addTool(CHAINSAW_LV, "Chainsaw (LV)", "Can also harvest Ice", new ToolChainsawLV(), ToolDictNames.craftingToolSaw.name());
        addTool(CHAINSAW_MV, "Chainsaw (MV)", "Can also harvest Ice", new ToolChainsawMV(), ToolDictNames.craftingToolSaw.name());
        addTool(CHAINSAW_HV, "Chainsaw (HV)", "Can also harvest Ice", new ToolChainsawHV(), ToolDictNames.craftingToolSaw.name());
        GregTech_API.registerTool(addTool(WRENCH_LV, "Wrench (LV)", "Hold Leftclick to dismantle Machines", new ToolWrenchLV(), ToolDictNames.craftingToolWrench.name()), GregTech_API.sWrenchList);
        GregTech_API.registerTool(addTool(WRENCH_MV, "Wrench (MV)", "Hold Leftclick to dismantle Machines", new ToolWrenchMV(), ToolDictNames.craftingToolWrench.name()), GregTech_API.sWrenchList);
        GregTech_API.registerTool(addTool(WRENCH_HV, "Wrench (HV)", "Hold Leftclick to dismantle Machines", new ToolWrenchHV(), ToolDictNames.craftingToolWrench.name()), GregTech_API.sWrenchList);
        addTool(JACKHAMMER, "JackHammer (HV)", "Breaks Rocks into pieces", new ToolJackHammer(), ToolDictNames.craftingToolJackHammer.name());
        addTool(BUZZSAW, "Buzzsaw (LV)", "Not suitable for harvesting Blocks", new ToolBuzzSaw(), ToolDictNames.craftingToolSaw.name());
        GregTech_API.registerTool(addTool(SCREWDRIVER_LV, "Screwdriver (LV)", "Adjusts Covers and Machines", new ToolScrewdriverLV(), ToolDictNames.craftingToolScrewdriver.name()), GregTech_API.sScrewdriverList);
        GregTech_API.registerTool(addTool(SOLDERING_IRON_LV, "Soldering Iron (LV)", "Fixes burned out Circuits. Needs soldering materials in inventory and 10kEU", new ToolSolderingIron(), ToolDictNames.craftingToolSolderingIron.name()), GregTech_API.sSolderingToolList);

        addTool(TURBINE_SMALL, "Small Turbine Rotor", "Turbine Rotors for your power station", new ToolTurbineSmall());
        addTool(TURBINE_NORMAL, "Turbine Rotor", "Turbine Rotors for your power station", new ToolTurbineNormal());
        addTool(TURBINE_LARGE, "Large Turbine Rotor", "Turbine Rotors for your power station", new ToolTurbineLarge());
        addTool(TURBINE_HUGE, "Huge Turbine Rotor", "Turbine Rotors for your power station", new ToolTurbineHuge());

        ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(MORTAR, 1, Materials.Flint, Materials.Stone, null), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" I ", "SIS", "SSS", 'I', new ItemStack(Items.FLINT, 1), 'S', OrePrefix.stone});
        ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(MORTAR, 1, Materials.Bronze, Materials.Stone, null), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" I ", "SIS", "SSS", 'I', OreDictionaryUnifier.get(OrePrefix.ingot, Materials.Bronze), 'S', OrePrefix.stone});
        ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(MORTAR, 1, Materials.Iron, Materials.Stone, null), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" I ", "SIS", "SSS", 'I', OreDictionaryUnifier.get(OrePrefix.ingot, Materials.Iron), 'S', OrePrefix.stone});
        ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(MORTAR, 1, Materials.Steel, Materials.Stone, null), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" I ", "SIS", "SSS", 'I', OreDictionaryUnifier.get(OrePrefix.ingot, Materials.Steel), 'S', OrePrefix.stone});
        ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(MORTAR, 1, Materials.WroughtIron, Materials.Stone, null), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" I ", "SIS", "SSS", 'I', OreDictionaryUnifier.get(OrePrefix.ingot, Materials.WroughtIron), 'S', OrePrefix.stone});
        ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(MORTAR, 1, Materials.RedSteel, Materials.Stone, null), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" I ", "SIS", "SSS", 'I', OreDictionaryUnifier.get(OrePrefix.ingot, Materials.RedSteel), 'S', OrePrefix.stone});
        ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(MORTAR, 1, Materials.BlueSteel, Materials.Stone, null), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" I ", "SIS", "SSS", 'I', OreDictionaryUnifier.get(OrePrefix.ingot, Materials.BlueSteel), 'S', OrePrefix.stone});
        ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(MORTAR, 1, Materials.BlackSteel, Materials.Stone, null), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" I ", "SIS", "SSS", 'I', OreDictionaryUnifier.get(OrePrefix.ingot, Materials.BlackSteel), 'S', OrePrefix.stone});
        ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(MORTAR, 1, Materials.DamascusSteel, Materials.Stone, null), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" I ", "SIS", "SSS", 'I', OreDictionaryUnifier.get(OrePrefix.ingot, Materials.DamascusSteel), 'S', OrePrefix.stone});
        //TODO ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(24, 1, Materials.Thaumium, Materials.Stone, null), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" I ", "SIS", "SSS", 'I', OreDictionaryUnifier.get(OrePrefix.ingot, Materials.Thaumium), 'S', OrePrefix.stone});

        ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(ROLLING_PIN, 1, Materials.Wood, Materials.Wood, null), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"  S", " I ", "S f", 'I', OreDictionaryUnifier.get(OrePrefix.ingot, Materials.Wood), 'S', OreDictionaryUnifier.get(OrePrefix.stick, Materials.Wood)});
        ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(ROLLING_PIN, 1, Materials.Plastic, Materials.Plastic, null), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"  S", " I ", "S f", 'I', OreDictionaryUnifier.get(OrePrefix.ingot, Materials.Plastic), 'S', OreDictionaryUnifier.get(OrePrefix.stick, Materials.Plastic)});
        ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(ROLLING_PIN, 1, Materials.Aluminium, Materials.Aluminium, null), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"  S", " I ", "S f", 'I', OreDictionaryUnifier.get(OrePrefix.ingot, Materials.Aluminium), 'S', OreDictionaryUnifier.get(OrePrefix.stick, Materials.Aluminium)});
        ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(ROLLING_PIN, 1, Materials.StainlessSteel, Materials.StainlessSteel, null), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"  S", " I ", "S f", 'I', OreDictionaryUnifier.get(OrePrefix.ingot, Materials.StainlessSteel), 'S', OreDictionaryUnifier.get(OrePrefix.stick, Materials.StainlessSteel)});


        if (!GregTech_API.sSpecialFile.get(ConfigCategories.general, "DisableFlintTools", false)) {
            ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(SWORD, 1, Materials.Flint, Materials.Wood, null), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"F", "F", "S", 'S', OreDictionaryUnifier.get(OrePrefix.stick, Materials.Wood), 'F', new ItemStack(Items.FLINT, 1)});
            ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(PICKAXE, 1, Materials.Flint, Materials.Wood, null), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"FFF", " S ", " S ", 'S', OreDictionaryUnifier.get(OrePrefix.stick, Materials.Wood), 'F', new ItemStack(Items.FLINT, 1)});
            ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(SHOVEL, 1, Materials.Flint, Materials.Wood, null), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"F", "S", "S", 'S', OreDictionaryUnifier.get(OrePrefix.stick, Materials.Wood), 'F', new ItemStack(Items.FLINT, 1)});
            ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(AXE, 1, Materials.Flint, Materials.Wood, null), ModHandler.RecipeBits.MIRRORED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"FF", "FS", " S", 'S', OreDictionaryUnifier.get(OrePrefix.stick, Materials.Wood), 'F', new ItemStack(Items.FLINT, 1)});
            ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(HOE, 1, Materials.Flint, Materials.Wood, null), ModHandler.RecipeBits.MIRRORED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"FF", " S", " S", 'S', OreDictionaryUnifier.get(OrePrefix.stick, Materials.Wood), 'F', new ItemStack(Items.FLINT, 1)});
            ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(KNIFE, 1, Materials.Flint, Materials.Wood, null), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"F", "S", 'S', OreDictionaryUnifier.get(OrePrefix.stick, Materials.Wood), 'F', new ItemStack(Items.FLINT, 1)});

            ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(MORTAR, 1, Materials.Flint, Materials.Stone, null), ModHandler.RecipeBits.NOT_REMOVABLE, " I ", "SIS", "SSS", Character.valueOf('I'), new ItemStack(Items.FLINT, 1), Character.valueOf('S'), OrePrefix.stone);
            ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(MORTAR, 1, Materials.Bronze, Materials.Stone, null), ModHandler.RecipeBits.NOT_REMOVABLE, " I ", "SIS", "SSS", Character.valueOf('I'), OreDictionaryUnifier.get(OrePrefix.ingot, Materials.Bronze), Character.valueOf('S'), OrePrefix.stone);
            ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(MORTAR, 1, Materials.Iron, Materials.Stone, null), ModHandler.RecipeBits.NOT_REMOVABLE, " I ", "SIS", "SSS", Character.valueOf('I'), OreDictionaryUnifier.get(OrePrefix.ingot, Materials.Iron), Character.valueOf('S'), OrePrefix.stone);
            ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(MORTAR, 1, Materials.Steel, Materials.Stone, null), ModHandler.RecipeBits.NOT_REMOVABLE, " I ", "SIS", "SSS", Character.valueOf('I'), OreDictionaryUnifier.get(OrePrefix.ingot, Materials.Steel), Character.valueOf('S'), OrePrefix.stone);
            ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(MORTAR, 1, Materials.WroughtIron, Materials.Stone, null), ModHandler.RecipeBits.NOT_REMOVABLE, " I ", "SIS", "SSS", Character.valueOf('I'), OreDictionaryUnifier.get(OrePrefix.ingot, Materials.WroughtIron), Character.valueOf('S'), OrePrefix.stone);
            ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(MORTAR, 1, Materials.RedSteel, Materials.Stone, null), ModHandler.RecipeBits.NOT_REMOVABLE, " I ", "SIS", "SSS", Character.valueOf('I'), OreDictionaryUnifier.get(OrePrefix.ingot, Materials.RedSteel), Character.valueOf('S'), OrePrefix.stone);
            ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(MORTAR, 1, Materials.BlueSteel, Materials.Stone, null), ModHandler.RecipeBits.NOT_REMOVABLE, " I ", "SIS", "SSS", Character.valueOf('I'), OreDictionaryUnifier.get(OrePrefix.ingot, Materials.BlueSteel), Character.valueOf('S'), OrePrefix.stone);
            ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(MORTAR, 1, Materials.BlackSteel, Materials.Stone, null), ModHandler.RecipeBits.NOT_REMOVABLE, " I ", "SIS", "SSS", Character.valueOf('I'), OreDictionaryUnifier.get(OrePrefix.ingot, Materials.BlackSteel), Character.valueOf('S'), OrePrefix.stone);
            ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(MORTAR, 1, Materials.DamascusSteel, Materials.Stone, null), ModHandler.RecipeBits.NOT_REMOVABLE, " I ", "SIS", "SSS", Character.valueOf('I'), OreDictionaryUnifier.get(OrePrefix.ingot, Materials.DamascusSteel), Character.valueOf('S'), OrePrefix.stone);
            //TODO ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(MORTAR, 1, Materials.Thaumium, Materials.Stone, null), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" I ", "SIS", "SSS", Character.valueOf('I'), OreDictionaryUnifier.get(OrePrefix.ingot, Materials.Thaumium), Character.valueOf('S'), OrePrefix.stone});

            ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(ROLLING_PIN, 1, Materials.Wood, Materials.Wood, null), ModHandler.RecipeBits.NOT_REMOVABLE, "  S", " I ", "S f", Character.valueOf('I'), OreDictionaryUnifier.get(OrePrefix.plank, Materials.Wood), Character.valueOf('S'), OreDictionaryUnifier.get(OrePrefix.stick, Materials.Wood));
            ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(ROLLING_PIN, 1, Materials.Plastic, Materials.Plastic, null), ModHandler.RecipeBits.NOT_REMOVABLE, "  S", " I ", "S f", Character.valueOf('I'), OreDictionaryUnifier.get(OrePrefix.ingot, Materials.Plastic), Character.valueOf('S'), OreDictionaryUnifier.get(OrePrefix.stick, Materials.Plastic));
            ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(ROLLING_PIN, 1, Materials.Aluminium, Materials.Aluminium, null), ModHandler.RecipeBits.NOT_REMOVABLE, "  S", " I ", "S f", Character.valueOf('I'), OreDictionaryUnifier.get(OrePrefix.ingot, Materials.Aluminium), Character.valueOf('S'), OreDictionaryUnifier.get(OrePrefix.stick, Materials.Aluminium));
            ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(ROLLING_PIN, 1, Materials.StainlessSteel, Materials.StainlessSteel, null), ModHandler.RecipeBits.NOT_REMOVABLE, "  S", " I ", "S f", Character.valueOf('I'), OreDictionaryUnifier.get(OrePrefix.ingot, Materials.StainlessSteel), Character.valueOf('S'), OreDictionaryUnifier.get(OrePrefix.stick, Materials.StainlessSteel));


            if (!GregTech_API.sSpecialFile.get(ConfigCategories.general, "DisableFlintTools", false)) {
                ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(SWORD, 1, Materials.Flint, Materials.Wood, null), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"F", "F", "S", Character.valueOf('S'), OreDictionaryUnifier.get(OrePrefix.stick, Materials.Wood), 'F', new ItemStack(Items.FLINT, 1)});
                ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(PICKAXE, 1, Materials.Flint, Materials.Wood, null), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"FFF", " S ", " S ", Character.valueOf('S'), OreDictionaryUnifier.get(OrePrefix.stick, Materials.Wood), 'F', new ItemStack(Items.FLINT, 1)});
                ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(SHOVEL, 1, Materials.Flint, Materials.Wood, null), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"F", "S", "S", Character.valueOf('S'), OreDictionaryUnifier.get(OrePrefix.stick, Materials.Wood), 'F', new ItemStack(Items.FLINT, 1)});
                ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(AXE, 1, Materials.Flint, Materials.Wood, null), ModHandler.RecipeBits.MIRRORED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"FF", "FS", " S", Character.valueOf('S'), OreDictionaryUnifier.get(OrePrefix.stick, Materials.Wood), 'F', new ItemStack(Items.FLINT, 1)});
                ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(HOE, 1, Materials.Flint, Materials.Wood, null), ModHandler.RecipeBits.MIRRORED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"FF", " S", " S", Character.valueOf('S'), OreDictionaryUnifier.get(OrePrefix.stick, Materials.Wood), 'F', new ItemStack(Items.FLINT, 1)});
                ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(KNIFE, 1, Materials.Flint, Materials.Wood, null), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"F", "S", Character.valueOf('S'), OreDictionaryUnifier.get(OrePrefix.stick, Materials.Wood), 'F', new ItemStack(Items.FLINT, 1)});

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