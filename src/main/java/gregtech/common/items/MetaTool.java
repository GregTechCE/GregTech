package gregtech.common.items;

import gregtech.api.items.ToolDictNames;
import gregtech.api.items.toolitem.ToolMetaItem;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import static gregtech.common.items.MetaItems.*;

public class MetaTool extends ToolMetaItem<ToolMetaItem<?>.MetaToolValueItem> {

    public MetaTool() {
        super();

        SWORD = addItem(0, "tool.sword")/*.setToolStats(new ToolSword())*/.addOreDict(ToolDictNames.craftingToolSword);
        PICKAXE = addItem(1, "tool.pickaxe")/*.setToolStats(new ToolPickaxe())*/.addOreDict(ToolDictNames.craftingToolPickaxe);
        SHOVEL = addItem(2, "tool.shovel")/*.setToolStats(new ToolShovel())*/.addOreDict(ToolDictNames.craftingToolShovel);
        AXE = addItem(3, "tool.axe")/*.setToolStats(new ToolAxe())*/.addOreDict(ToolDictNames.craftingToolAxe);
        HOE = addItem(4, "tool.hoe")/*.setToolStats(new ToolHoe())*/.addOreDict(ToolDictNames.craftingToolHoe);
        SAW = addItem(5, "tool.saw")/*.setToolStats(new ToolSaw())*/.addOreDict(ToolDictNames.craftingToolSaw);
        HARDHAMMER = addItem(6, "tool.hardhammer")/*.setToolStats(new ToolHardHammer())*/.addOreDict(ToolDictNames.craftingToolHardHammer); // GregTechAPI.sHardHammerList
        SOFTHAMMER = addItem(7, "tool.softhammer")/*.setToolStats(new ToolSoftHammer())*/.addOreDict(ToolDictNames.craftingToolSoftHammer); // GregTechAPI.sSoftHammerList
        WRENCH = addItem(8, "tool.wrench")/*.setToolStats(new ToolWrench())*/.addOreDict(ToolDictNames.craftingToolWrench); // GregTechAPI.sWrenchList
        FILE = addItem(9, "tool.file")/*.setToolStats(new ToolFile())*/.addOreDict(ToolDictNames.craftingToolFile);
        CROWBAR = addItem(10, "tool.crowbar")/*.setToolStats(new ToolCrowbar())*/.addOreDict(ToolDictNames.craftingToolCrowbar); // GregTechAPI.sCrowbarList
        SCREWDRIVER = addItem(11, "tool.screwdriver")/*.setToolStats(new ToolScrewdriver())*/.addOreDict(ToolDictNames.craftingToolScrewdriver); // GregTechAPI.sScrewdriverList
        MORTAR = addItem(12, "tool.mortar")/*.setToolStats(new ToolMortar())*/.addOreDict(ToolDictNames.craftingToolMortar);
        WIRECUTTER = addItem(13, "tool.wirecutter")/*.setToolStats(new ToolWireCutter())*/.addOreDict(ToolDictNames.craftingToolWireCutter);
        SCOOP = addItem(14, "tool.scoop")/*.setToolStats(new ToolScoop())*/.addOreDict(ToolDictNames.craftingToolScoop);
        BRANCHCUTTER = addItem(15, "tool.branchcutter")/*.setToolStats(new ToolBranchCutter())*/.addOreDict(ToolDictNames.craftingToolBranchCutter);
        UNIVERSALSPADE = addItem(16, "tool.universalspade")/*.setToolStats(new ToolUniversalSpade())*/.addOreDict(ToolDictNames.craftingToolBlade, ToolDictNames.craftingToolShovel, ToolDictNames.craftingToolCrowbar, ToolDictNames.craftingToolSaw); // GregTechAPI.sCrowbarList
        KNIFE = addItem(17, "tool.knife")/*.setToolStats(new ToolKnife())*/.addOreDict(ToolDictNames.craftingToolBlade, ToolDictNames.craftingToolKnife);
        BUTCHERYKNIFE = addItem(18, "tool.butcheryknife")/*.setToolStats(new ToolButcheryKnife())*/.addOreDict(ToolDictNames.craftingToolBlade);

        SENSE = addItem(19, "tool.sense")/*.setToolStats(new ToolSense())*/.addOreDict(ToolDictNames.craftingToolBlade);
        PLOW = addItem(20, "tool.plow")/*.setToolStats(new ToolPlow())*/.addOreDict(ToolDictNames.craftingToolPlow);
        PLUNGER = addItem(21, "tool.plunger")/*.setToolStats(new ToolPlunger())*/.addOreDict(ToolDictNames.craftingToolPlunger);
        ROLLING_PIN = addItem(22, "tool.rolling_pin")/*.setToolStats(new ToolRollingPin())*/.addOreDict(ToolDictNames.craftingToolRollingPin);

        DRILL_LV = addItem(23, "tool.drill.lv")/*.setToolStats(new ToolDrillLV())*/.addOreDict(ToolDictNames.craftingToolMiningDrill);
        DRILL_MV = addItem(24, "tool.drill.mv")/*.setToolStats(new ToolDrillMV())*/.addOreDict(ToolDictNames.craftingToolMiningDrill);
        DRILL_HV = addItem(25, "tool.drill.hv")/*.setToolStats(new ToolDrillHV())*/.addOreDict(ToolDictNames.craftingToolMiningDrill);
        CHAINSAW_LV = addItem(26, "tool.chainsaw.lv")/*.setToolStats(new ToolChainsawLV())*/.addOreDict(ToolDictNames.craftingToolSaw);
        CHAINSAW_MV = addItem(27, "tool.chainsaw.mv")/*.setToolStats(new ToolChainsawMV())*/.addOreDict(ToolDictNames.craftingToolSaw);
        CHAINSAW_HV = addItem(28, "tool.chainsaw.hv")/*.setToolStats(new ToolChainsawHV())*/.addOreDict(ToolDictNames.craftingToolSaw);
        WRENCH_LV = addItem(29, "tool.wrench.lv")/*.setToolStats(new ToolWrenchLV())*/.addOreDict(ToolDictNames.craftingToolWrench); // GregTechAPI.sWrenchList
        WRENCH_MV = addItem(30, "tool.wrench.mv")/*.setToolStats(new ToolWrenchMV())*/.addOreDict(ToolDictNames.craftingToolWrench); // GregTechAPI.sWrenchList
        WRENCH_HV = addItem(31, "tool.wrench.hv")/*.setToolStats(new ToolWrenchHV())*/.addOreDict(ToolDictNames.craftingToolWrench); // GregTechAPI.sWrenchList
        JACKHAMMER = addItem(32, "tool.jackhammer")/*.setToolStats(new ToolJackHammer())*/.addOreDict(ToolDictNames.craftingToolJackHammer);
        BUZZSAW = addItem(33, "tool.buzzsaw")/*.setToolStats(new ToolBuzzSaw())*/.addOreDict(ToolDictNames.craftingToolSaw);
        SCREWDRIVER_LV = addItem(34, "tool.screwdriver.lv")/*.setToolStats(new ToolScrewdriverLV())*/.addOreDict(ToolDictNames.craftingToolScrewdriver); // GregTechAPI.sScrewdriverList
        SOLDERING_IRON_LV = addItem(35, "tool.soldering.iron.lv")/*.setToolStats(new ToolSolderingIron())*/.addOreDict(ToolDictNames.craftingToolSolderingIron); // GregTechAPI.sSolderingToolList

        TURBINE_SMALL = addItem(36, "tool.turbine.small")/*.setToolStats(new ToolTurbineSmall())*/;
        TURBINE_NORMAL = addItem(37, "tool.turbine.normal")/*.setToolStats(new ToolTurbineNormal())*/;
        TURBINE_LARGE = addItem(38, "tool.turbine.large")/*.setToolStats(new ToolTurbineLarge())*/;
        TURBINE_HUGE = addItem(39, "tool.turbine.huge")/*.setToolStats(new ToolTurbineHuge())*/;

        ModHandler.addShapedRecipe(MORTAR.getStackForm(Materials.Flint, Materials.Stone),
                " I ",
                "SIS",
                "SSS",
                'I', new ItemStack(Items.FLINT, 1),
                'S', OrePrefix.stone);

        ModHandler.addShapedRecipe(MORTAR.getStackForm(Materials.Bronze, Materials.Stone),
                " I ",
                "SIS",
                "SSS",
                'I', OreDictionaryUnifier.get(OrePrefix.ingot, Materials.Bronze),
                'S', OrePrefix.stone);

        ModHandler.addShapedRecipe(MORTAR.getStackForm(Materials.Iron, Materials.Stone),
                " I ",
                "SIS",
                "SSS",
                'I', OreDictionaryUnifier.get(OrePrefix.ingot, Materials.Iron),
                'S', OrePrefix.stone);

        ModHandler.addShapedRecipe(MORTAR.getStackForm(Materials.Steel, Materials.Stone),

                " I ",
                "SIS",
                "SSS",
                'I', OreDictionaryUnifier.get(OrePrefix.ingot, Materials.Steel),
                'S', OrePrefix.stone);

        ModHandler.addShapedRecipe(MORTAR.getStackForm(Materials.WroughtIron, Materials.Stone),
                " I ",
                "SIS",
                "SSS",
                'I', OreDictionaryUnifier.get(OrePrefix.ingot, Materials.WroughtIron),
                'S', OrePrefix.stone);

        ModHandler.addShapedRecipe(MORTAR.getStackForm(Materials.RedSteel, Materials.Stone),
                " I ",
                "SIS",
                "SSS",
                'I', OreDictionaryUnifier.get(OrePrefix.ingot, Materials.RedSteel),
                'S', OrePrefix.stone);

        ModHandler.addShapedRecipe(MORTAR.getStackForm(Materials.BlueSteel, Materials.Stone),
                " I ",
                "SIS",
                "SSS",
                'I', OreDictionaryUnifier.get(OrePrefix.ingot, Materials.BlueSteel),
                'S', OrePrefix.stone);

        ModHandler.addShapedRecipe(MORTAR.getStackForm(Materials.BlackSteel, Materials.Stone),
                " I ",
                "SIS",
                "SSS",
                'I', OreDictionaryUnifier.get(OrePrefix.ingot, Materials.BlackSteel),
                'S', OrePrefix.stone);

        ModHandler.addShapedRecipe(MORTAR.getStackForm(Materials.DamascusSteel, Materials.Stone),
                " I ",
                "SIS",
                "SSS",
                'I', OreDictionaryUnifier.get(OrePrefix.ingot, Materials.DamascusSteel),
                'S', OrePrefix.stone);

        ModHandler.addShapedRecipe(ROLLING_PIN.getStackForm(Materials.Wood, Materials.Wood),
                "  S",
                " I ",
                "S f",
                'I', OreDictionaryUnifier.get(OrePrefix.ingot, Materials.Wood),
                'S', OreDictionaryUnifier.get(OrePrefix.stick, Materials.Wood));

        ModHandler.addShapedRecipe(ROLLING_PIN.getStackForm(Materials.Plastic, Materials.Plastic),
                "  S",
                " I ",
                "S f",
                'I', OreDictionaryUnifier.get(OrePrefix.ingot, Materials.Plastic),
                'S', OreDictionaryUnifier.get(OrePrefix.stick, Materials.Plastic));

        ModHandler.addShapedRecipe(ROLLING_PIN.getStackForm(Materials.Aluminium, Materials.Aluminium),
                "  S",
                " I ",
                "S f",
                'I', OreDictionaryUnifier.get(OrePrefix.ingot, Materials.Aluminium),
                'S', OreDictionaryUnifier.get(OrePrefix.stick, Materials.Aluminium));

        ModHandler.addShapedRecipe(ROLLING_PIN.getStackForm(Materials.StainlessSteel, Materials.StainlessSteel),
                "  S",
                " I ",
                "S f",
                'I', OreDictionaryUnifier.get(OrePrefix.ingot, Materials.StainlessSteel),
                'S', OreDictionaryUnifier.get(OrePrefix.stick, Materials.StainlessSteel));


        if (false) { //TODO CONFIG !GregTechAPI.sSpecialFile.get(ConfigCategories.general, "DisableFlintTools", false)
            ModHandler.addShapedRecipe(SWORD.getStackForm(Materials.Flint, Materials.Wood),
                    "F",
                    "F",
                    "S",
                    'S', OreDictionaryUnifier.get(OrePrefix.stick, Materials.Wood),
                    'F', new ItemStack(Items.FLINT, 1));

            ModHandler.addShapedRecipe(PICKAXE.getStackForm(Materials.Flint, Materials.Wood),
                    "FFF",
                    " S ",
                    " S ",
                    'S', OreDictionaryUnifier.get(OrePrefix.stick, Materials.Wood),
                    'F', new ItemStack(Items.FLINT, 1));

            ModHandler.addShapedRecipe(SHOVEL.getStackForm(Materials.Flint, Materials.Wood),
                    "F",
                    "S",
                    "S",
                    'S', OreDictionaryUnifier.get(OrePrefix.stick, Materials.Wood),
                    'F', new ItemStack(Items.FLINT, 1));

            ModHandler.addShapedRecipe(AXE.getStackForm(Materials.Flint, Materials.Wood),// true,
                    "FF",
                    "FS",
                    " S",
                    'S', OreDictionaryUnifier.get(OrePrefix.stick, Materials.Wood),
                    'F', new ItemStack(Items.FLINT, 1));

            ModHandler.addShapedRecipe(HOE.getStackForm(Materials.Flint, Materials.Wood),// true,
                    "FF",
                    " S",
                    " S",
                    'S', OreDictionaryUnifier.get(OrePrefix.stick, Materials.Wood),
                    'F', new ItemStack(Items.FLINT, 1));

            ModHandler.addShapedRecipe(KNIFE.getStackForm(Materials.Flint, Materials.Wood),
                    "F",
                    "S",
                    'S', OreDictionaryUnifier.get(OrePrefix.stick, Materials.Wood),
                    'F', new ItemStack(Items.FLINT, 1));

            ModHandler.addShapedRecipe(MORTAR.getStackForm(Materials.Flint, Materials.Stone),
                    " I ",
                    "SIS",
                    "SSS",
                    'I', new ItemStack(Items.FLINT, 1),
                    'S', OrePrefix.stone);

            ModHandler.addShapedRecipe(MORTAR.getStackForm(Materials.Bronze, Materials.Stone),
                    " I ",
                    "SIS",
                    "SSS",
                    'I', OreDictionaryUnifier.get(OrePrefix.ingot, Materials.Bronze),
                    'S', OrePrefix.stone);

            ModHandler.addShapedRecipe(MORTAR.getStackForm(Materials.Iron, Materials.Stone),
                    " I ",
                    "SIS",
                    "SSS",
                    'I', OreDictionaryUnifier.get(OrePrefix.ingot, Materials.Iron),
                    'S', OrePrefix.stone);

            ModHandler.addShapedRecipe(MORTAR.getStackForm(Materials.Steel, Materials.Stone),
                    " I ",
                    "SIS",
                    "SSS",
                    'I', OreDictionaryUnifier.get(OrePrefix.ingot, Materials.Steel),
                    'S', OrePrefix.stone);

            ModHandler.addShapedRecipe(MORTAR.getStackForm(Materials.WroughtIron, Materials.Stone),
                    " I ",
                    "SIS",
                    "SSS",
                    'I', OreDictionaryUnifier.get(OrePrefix.ingot, Materials.WroughtIron),
                    'S', OrePrefix.stone);

            ModHandler.addShapedRecipe(MORTAR.getStackForm(Materials.RedSteel, Materials.Stone),
                    " I ",
                    "SIS",
                    "SSS",
                    'I', OreDictionaryUnifier.get(OrePrefix.ingot, Materials.RedSteel),
                    'S', OrePrefix.stone);

            ModHandler.addShapedRecipe(MORTAR.getStackForm(Materials.BlueSteel, Materials.Stone),
                    " I ",
                    "SIS",
                    "SSS",
                    'I', OreDictionaryUnifier.get(OrePrefix.ingot, Materials.BlueSteel),
                    'S', OrePrefix.stone);

            ModHandler.addShapedRecipe(MORTAR.getStackForm(Materials.BlackSteel, Materials.Stone),
                    " I ",
                    "SIS",
                    "SSS",
                    'I', OreDictionaryUnifier.get(OrePrefix.ingot, Materials.BlackSteel),
                    'S', OrePrefix.stone);

            ModHandler.addShapedRecipe(MORTAR.getStackForm(Materials.DamascusSteel, Materials.Stone),
                    " I ",
                    "SIS",
                    "SSS",
                    'I', OreDictionaryUnifier.get(OrePrefix.ingot, Materials.DamascusSteel),
                    'S', OrePrefix.stone);

            ModHandler.addShapedRecipe(ROLLING_PIN.getStackForm(Materials.Wood, Materials.Wood),
                    "  S",
                    " I ",
                    "S f",
                    'I', OreDictionaryUnifier.get(OrePrefix.plank, Materials.Wood),
                    'S', OreDictionaryUnifier.get(OrePrefix.stick, Materials.Wood));

            ModHandler.addShapedRecipe(ROLLING_PIN.getStackForm(Materials.Plastic, Materials.Plastic),
                    "  S",
                    " I ",
                    "S f",
                    'I', OreDictionaryUnifier.get(OrePrefix.ingot, Materials.Plastic),
                    'S', OreDictionaryUnifier.get(OrePrefix.stick, Materials.Plastic));

            ModHandler.addShapedRecipe(ROLLING_PIN.getStackForm(Materials.Aluminium, Materials.Aluminium),
                    "  S",
                    " I ",
                    "S f",
                    'I', OreDictionaryUnifier.get(OrePrefix.ingot, Materials.Aluminium),
                    'S', OreDictionaryUnifier.get(OrePrefix.stick, Materials.Aluminium));

            ModHandler.addShapedRecipe(ROLLING_PIN.getStackForm(Materials.StainlessSteel, Materials.StainlessSteel),
                    "  S",
                    " I ",
                    "S f",
                    'I', OreDictionaryUnifier.get(OrePrefix.ingot, Materials.StainlessSteel),
                    'S', OreDictionaryUnifier.get(OrePrefix.stick, Materials.StainlessSteel));

            if (false) { // //TODO CONFIG !GregTechAPI.sSpecialFile.get(ConfigCategories.general, "DisableFlintTools", false)
                ModHandler.addShapedRecipe(SWORD.getStackForm(Materials.Flint, Materials.Wood),
                        "F",
                        "F",
                        "S",
                        'S', OreDictionaryUnifier.get(OrePrefix.stick, Materials.Wood),
                        'F', new ItemStack(Items.FLINT, 1));

                ModHandler.addShapedRecipe(PICKAXE.getStackForm(Materials.Flint, Materials.Wood),
                        "FFF",
                        " S ",
                        " S ",
                        'S', OreDictionaryUnifier.get(OrePrefix.stick, Materials.Wood),
                        'F', new ItemStack(Items.FLINT, 1));

                ModHandler.addShapedRecipe(SHOVEL.getStackForm(Materials.Flint, Materials.Wood),
                        "F",
                        "S",
                        "S",
                        'S', OreDictionaryUnifier.get(OrePrefix.stick, Materials.Wood),
                        'F', new ItemStack(Items.FLINT, 1));

                ModHandler.addShapedRecipe(AXE.getStackForm(Materials.Flint, Materials.Wood),// true,
                        "FF",
                        "FS",
                        " S",
                        'S', OreDictionaryUnifier.get(OrePrefix.stick, Materials.Wood),
                        'F', new ItemStack(Items.FLINT, 1));

                ModHandler.addShapedRecipe(HOE.getStackForm(Materials.Flint, Materials.Wood),// true,
                        "FF",
                        " S",
                        " S",
                        'S', OreDictionaryUnifier.get(OrePrefix.stick, Materials.Wood),
                        'F', new ItemStack(Items.FLINT, 1));

                ModHandler.addShapedRecipe(KNIFE.getStackForm(Materials.Flint, Materials.Wood),
                        "F",
                        "S",
                        'S', OreDictionaryUnifier.get(OrePrefix.stick, Materials.Wood),
                        'F', new ItemStack(Items.FLINT, 1));

            }
            if (true) {  //TODO CONFIG GregTechAPI.sRecipeFile.get(ConfigCategories.Tools.mortar, "Coal", true)
                ModHandler.addShapelessRecipe(OreDictionaryUnifier.get(OrePrefix.dust, Materials.Coal, 1),
                        ToolDictNames.craftingToolMortar,
                        new ItemStack(Items.COAL, 1));
            }
            if (true) {  //TODO CONFIG GregTechAPI.sRecipeFile.get(ConfigCategories.Tools.mortar, "Clay", true)
                ModHandler.addShapelessRecipe(OreDictionaryUnifier.get(OrePrefix.dust, Materials.Clay, 1),
                        ToolDictNames.craftingToolMortar,
                        new ItemStack(Blocks.CLAY, 1));
            }
            if (true) { // TODO CONFIG GregTechAPI.sRecipeFile.get(ConfigCategories.Tools.mortar, "Wheat", true)
                ModHandler.addShapelessRecipe(OreDictionaryUnifier.get(OrePrefix.dust, Materials.Wheat, 1),
                        ToolDictNames.craftingToolMortar,
                        new ItemStack(Items.WHEAT, 1));
            }
            if (true) {  //TODO CONFIG GregTechAPI.sRecipeFile.get(ConfigCategories.Tools.mortar, "Clay", true)
                ModHandler.addShapelessRecipe(new ItemStack(Items.FLINT, 1),
                        ToolDictNames.craftingToolMortar,
                        new ItemStack(Blocks.GRAVEL, 1));
            }
            if (true) {  //TODO CONFIG GregTechAPI.sRecipeFile.get(ConfigCategories.Tools.mortar, "Blaze", true)
                ModHandler.addShapelessRecipe(new ItemStack(Items.BLAZE_POWDER, 2),
                        ToolDictNames.craftingToolMortar,
                        new ItemStack(Items.BLAZE_ROD, 1));
            }
        }
    }
}