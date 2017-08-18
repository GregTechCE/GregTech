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

        addItem(SWORD, "tool.sword").setToolStats(new ToolSword()).addOreDict(ToolDictNames.craftingToolSword);
        addItem(PICKAXE, "tool.pickaxe").setToolStats(new ToolPickaxe()).addOreDict(ToolDictNames.craftingToolPickaxe);
        addItem(SHOVEL, "tool.shovel").setToolStats(new ToolShovel()).addOreDict(ToolDictNames.craftingToolShovel);
        addItem(AXE, "tool.axe").setToolStats(new ToolAxe()).addOreDict(ToolDictNames.craftingToolAxe);
        addItem(HOE, "tool.hoe").setToolStats(new ToolHoe()).addOreDict(ToolDictNames.craftingToolHoe);
        addItem(SAW, "tool.saw").setToolStats(new ToolSaw()).addOreDict(ToolDictNames.craftingToolSaw);
        addItem(HARDHAMMER, "tool.hardhammer").setToolStats(new ToolHardHammer()).addOreDict(ToolDictNames.craftingToolHardHammer); // GregTech_API.sHardHammerList
        addItem(SOFTHAMMER, "tool.softhammer").setToolStats(new ToolSoftHammer()).addOreDict(ToolDictNames.craftingToolSoftHammer); // GregTech_API.sSoftHammerList
        addItem(WRENCH, "tool.wrench").setToolStats(new ToolWrench()).addOreDict(ToolDictNames.craftingToolWrench); // GregTech_API.sWrenchList
        addItem(FILE, "tool.file").setToolStats(new ToolFile()).addOreDict(ToolDictNames.craftingToolFile);
        addItem(CROWBAR, "tool.crowbar").setToolStats(new ToolCrowbar()).addOreDict(ToolDictNames.craftingToolCrowbar); // GregTech_API.sCrowbarList
        addItem(SCREWDRIVER, "tool.screwdriver").setToolStats(new ToolScrewdriver()).addOreDict(ToolDictNames.craftingToolScrewdriver); // GregTech_API.sScrewdriverList
        addItem(MORTAR, "tool.mortar").setToolStats(new ToolMortar()).addOreDict(ToolDictNames.craftingToolMortar);
        addItem(WIRECUTTER, "tool.wirecutter").setToolStats(new ToolWireCutter()).addOreDict(ToolDictNames.craftingToolWireCutter);
        addItem(SCOOP, "tool.scoop").setToolStats(new ToolScoop()).addOreDict(ToolDictNames.craftingToolScoop);
        addItem(BRANCHCUTTER, "tool.branchcutter").setToolStats(new ToolBranchCutter()).addOreDict(ToolDictNames.craftingToolBranchCutter);
        addItem(UNIVERSALSPADE, "tool.universalspade").setToolStats(new ToolUniversalSpade()).addOreDict(ToolDictNames.craftingToolBlade, ToolDictNames.craftingToolShovel, ToolDictNames.craftingToolCrowbar, ToolDictNames.craftingToolSaw); // GregTech_API.sCrowbarList
        addItem(KNIFE, "tool.knife").setToolStats(new ToolKnife()).addOreDict(ToolDictNames.craftingToolBlade, ToolDictNames.craftingToolKnife);
        addItem(BUTCHERYKNIFE, "tool.butcheryknife").setToolStats(new ToolButcheryKnife()).addOreDict(ToolDictNames.craftingToolBlade);

        addItem(SENSE, "tool.sense").setToolStats(new ToolSense()).addOreDict(ToolDictNames.craftingToolBlade);
        addItem(PLOW, "tool.plow").setToolStats(new ToolPlow()).addOreDict(ToolDictNames.craftingToolPlow);
        addItem(PLUNGER, "tool.plunger").setToolStats(new ToolPlunger()).addOreDict(ToolDictNames.craftingToolPlunger);
        addItem(ROLLING_PIN, "tool.rolling_pin").setToolStats(new ToolRollingPin()).addOreDict(ToolDictNames.craftingToolRollingPin);

        addItem(DRILL_LV, "tool.drill.lv").setToolStats(new ToolDrillLV()).addOreDict(ToolDictNames.craftingToolMiningDrill);
        addItem(DRILL_MV, "tool.drill.mv").setToolStats(new ToolDrillMV()).addOreDict(ToolDictNames.craftingToolMiningDrill);
        addItem(DRILL_HV, "tool.drill.hv").setToolStats(new ToolDrillHV()).addOreDict(ToolDictNames.craftingToolMiningDrill);
        addItem(CHAINSAW_LV, "tool.chainsaw.lv").setToolStats(new ToolChainsawLV()).addOreDict(ToolDictNames.craftingToolSaw);
        addItem(CHAINSAW_MV, "tool.chainsaw.mv").setToolStats(new ToolChainsawMV()).addOreDict(ToolDictNames.craftingToolSaw);
        addItem(CHAINSAW_HV, "tool.chainsaw.hv").setToolStats(new ToolChainsawHV()).addOreDict(ToolDictNames.craftingToolSaw);
        addItem(WRENCH_LV, "tool.wrench.lv").setToolStats(new ToolWrenchLV()).addOreDict(ToolDictNames.craftingToolWrench); // GregTech_API.sWrenchList
        addItem(WRENCH_MV, "tool.wrench.mv").setToolStats(new ToolWrenchMV()).addOreDict(ToolDictNames.craftingToolWrench); // GregTech_API.sWrenchList
        addItem(WRENCH_HV, "tool.wrench.hv").setToolStats(new ToolWrenchHV()).addOreDict(ToolDictNames.craftingToolWrench); // GregTech_API.sWrenchList
        addItem(JACKHAMMER, "tool.jackhammer").setToolStats(new ToolJackHammer()).addOreDict(ToolDictNames.craftingToolJackHammer);
        addItem(BUZZSAW, "tool.buzzsaw").setToolStats(new ToolBuzzSaw()).addOreDict(ToolDictNames.craftingToolSaw);
        addItem(SCREWDRIVER_LV, "tool.screwdriver.lv").setToolStats(new ToolScrewdriverLV()).addOreDict(ToolDictNames.craftingToolScrewdriver); // GregTech_API.sScrewdriverList
        addItem(SOLDERING_IRON_LV, "tool.soldering.iron.lv").setToolStats(new ToolSolderingIron()).addOreDict(ToolDictNames.craftingToolSolderingIron); // GregTech_API.sSolderingToolList

        addItem(TURBINE_SMALL, "tool.turbine.small").setToolStats(new ToolTurbineSmall());
        addItem(TURBINE_NORMAL, "tool.turbine.normal").setToolStats(new ToolTurbineNormal());
        addItem(TURBINE_LARGE, "tool.turbine.large").setToolStats(new ToolTurbineLarge());
        addItem(TURBINE_HUGE, "tool.turbine.huge").setToolStats(new ToolTurbineHuge());

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