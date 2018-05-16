package gregtech.common.items;

import gregtech.api.GregTechAPI;
import gregtech.api.items.ToolDictNames;
import gregtech.api.items.toolitem.ToolMetaItem;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.tools.*;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import static gregtech.common.items.MetaItems.*;

public class MetaTool extends ToolMetaItem<ToolMetaItem<?>.MetaToolValueItem> {

    public MetaTool() {
        super();
    }

    @Override
    public void registerSubItems() {

        SWORD = addItem(0, "tool.sword").setToolStats(new ToolSword()).addOreDict(ToolDictNames.craftingToolSword);
        PICKAXE = addItem(1, "tool.pickaxe").setToolStats(new ToolPickaxe()).addOreDict(ToolDictNames.craftingToolPickaxe);
        SHOVEL = addItem(2, "tool.shovel").setToolStats(new ToolShovel()).addOreDict(ToolDictNames.craftingToolShovel);
        AXE = addItem(3, "tool.axe").setToolStats(new ToolAxe()).addOreDict(ToolDictNames.craftingToolAxe);
        HOE = addItem(4, "tool.hoe").setToolStats(new ToolHoe()).addOreDict(ToolDictNames.craftingToolHoe);
        SAW = addItem(5, "tool.saw").setToolStats(new ToolSaw()).addOreDict(ToolDictNames.craftingToolSaw);
        HARDHAMMER = addItem(6, "tool.hard_hammer").setToolStats(new ToolHardHammer()).addOreDict(ToolDictNames.craftingToolHardHammer).addToList(GregTechAPI.hardHammerList);
        SOFTHAMMER = addItem(7, "tool.soft_hammer").setToolStats(new ToolSoftHammer()).addOreDict(ToolDictNames.craftingToolSoftHammer).addToList(GregTechAPI.softHammerList);
        WRENCH = addItem(8, "tool.wrench").setToolStats(new ToolWrench()).addOreDict(ToolDictNames.craftingToolWrench).addToList(GregTechAPI.wrenchList);
        FILE = addItem(9, "tool.file").setToolStats(new ToolFile()).addOreDict(ToolDictNames.craftingToolFile);
        CROWBAR = addItem(10, "tool.crowbar").setToolStats(new ToolCrowbar()).addOreDict(ToolDictNames.craftingToolCrowbar).addToList(GregTechAPI.crowbarList);
        SCREWDRIVER = addItem(11, "tool.screwdriver").setToolStats(new ToolScrewdriver()).addOreDict(ToolDictNames.craftingToolScrewdriver).addToList(GregTechAPI.screwdriverList);
        MORTAR = addItem(12, "tool.mortar").setToolStats(new ToolMortar()).addOreDict(ToolDictNames.craftingToolMortar);
        WIRECUTTER = addItem(13, "tool.wire_cutter").setToolStats(new ToolWireCutter()).addOreDict(ToolDictNames.craftingToolWireCutter);
        SCOOP = addItem(14, "tool.scoop").setToolStats(new ToolScoop()).addOreDict(ToolDictNames.craftingToolScoop);
        BRANCHCUTTER = addItem(15, "tool.branch_cutter").setToolStats(new ToolBranchCutter()).addOreDict(ToolDictNames.craftingToolBranchCutter);
        UNIVERSALSPADE = addItem(16, "tool.universal_spade").setToolStats(new ToolUniversalSpade()).addOreDict(ToolDictNames.craftingToolBlade, ToolDictNames.craftingToolShovel, ToolDictNames.craftingToolCrowbar, ToolDictNames.craftingToolSaw).addToList(GregTechAPI.crowbarList);
        KNIFE = addItem(17, "tool.knife").setToolStats(new ToolKnife()).addOreDict(ToolDictNames.craftingToolBlade, ToolDictNames.craftingToolKnife);
        BUTCHERYKNIFE = addItem(18, "tool.butchery_knife").setToolStats(new ToolButcheryKnife()).addOreDict(ToolDictNames.craftingToolBlade);

        SENSE = addItem(19, "tool.sense").setToolStats(new ToolSense()).addOreDict(ToolDictNames.craftingToolBlade);
        PLOW = addItem(20, "tool.plow").setToolStats(new ToolPlow()).addOreDict(ToolDictNames.craftingToolPlow);
        ROLLING_PIN = addItem(22, "tool.rolling_pin").setToolStats(new ToolRollingPin()).addOreDict(ToolDictNames.craftingToolRollingPin);

        DRILL_LV = addItem(23, "tool.drill.lv").setToolStats(new ToolDrillLV()).addOreDict(ToolDictNames.craftingToolMiningDrill);
        DRILL_MV = addItem(24, "tool.drill.mv").setToolStats(new ToolDrillMV()).addOreDict(ToolDictNames.craftingToolMiningDrill);
        DRILL_HV = addItem(25, "tool.drill.hv").setToolStats(new ToolDrillHV()).addOreDict(ToolDictNames.craftingToolMiningDrill);
        CHAINSAW_LV = addItem(26, "tool.chainsaw.lv").setToolStats(new ToolChainsawLV()).addOreDict(ToolDictNames.craftingToolSaw);
        CHAINSAW_MV = addItem(27, "tool.chainsaw.mv").setToolStats(new ToolChainsawMV()).addOreDict(ToolDictNames.craftingToolSaw);
        CHAINSAW_HV = addItem(28, "tool.chainsaw.hv").setToolStats(new ToolChainsawHV()).addOreDict(ToolDictNames.craftingToolSaw);
        WRENCH_LV = addItem(29, "tool.wrench.lv").setToolStats(new ToolWrenchLV()).addOreDict(ToolDictNames.craftingToolWrench).addToList(GregTechAPI.wrenchList);
        WRENCH_MV = addItem(30, "tool.wrench.mv").setToolStats(new ToolWrenchMV()).addOreDict(ToolDictNames.craftingToolWrench).addToList(GregTechAPI.wrenchList);
        WRENCH_HV = addItem(31, "tool.wrench.hv").setToolStats(new ToolWrenchHV()).addOreDict(ToolDictNames.craftingToolWrench).addToList(GregTechAPI.wrenchList);
        JACKHAMMER = addItem(32, "tool.jackhammer").setToolStats(new ToolJackHammer()).addOreDict(ToolDictNames.craftingToolJackHammer);
        BUZZSAW = addItem(33, "tool.buzzsaw").setToolStats(new ToolBuzzSaw()).addOreDict(ToolDictNames.craftingToolSaw);
        SCREWDRIVER_LV = addItem(34, "tool.screwdriver.lv").setToolStats(new ToolScrewdriverLV()).addOreDict(ToolDictNames.craftingToolScrewdriver).addToList(GregTechAPI.screwdriverList);
        SOLDERING_IRON_LV = addItem(35, "tool.soldering_iron.lv").setToolStats(new ToolSolderingIron()).addOreDict(ToolDictNames.craftingToolSolderingIron).addToList(GregTechAPI.solderingToolList);
        MAGNIFYING_GLASS = addItem(36, "tool.magnifying_glass").setToolStats(new ToolMagnifyingGlass()).addOreDict(ToolDictNames.craftingToolMagnifyingGlass);
        PLUNGER = addItem(37, "tool.plunger").setToolStats(new ToolPlunger()).addOreDict(ToolDictNames.craftingToolPlunger);
        TURBINE = addItem(38, "tool.turbine").setToolStats(new ToolTurbineRotor());
    }

    public void registerRecipes() {
        ModHandler.addShapedRecipe("mortar_flint", MORTAR.getStackForm(Materials.Flint, null),
            " I ",
            "SIS",
            "SSS",
            'I', new ItemStack(Items.FLINT, 1),
            'S', OrePrefix.stone);

        ModHandler.addShapedRecipe("mortar_bronze", MORTAR.getStackForm(Materials.Bronze, null),
            " I ",
            "SIS",
            "SSS",
            'I', new UnificationEntry(OrePrefix.ingot, Materials.Bronze),
            'S', OrePrefix.stone);

        ModHandler.addShapedRecipe("mortar_iron", MORTAR.getStackForm(Materials.Iron, null),
            " I ",
            "SIS",
            "SSS",
            'I', new UnificationEntry(OrePrefix.ingot, Materials.Iron),
            'S', OrePrefix.stone);

        ModHandler.addShapedRecipe("mortar_steel", MORTAR.getStackForm(Materials.Steel, null),

            " I ",
            "SIS",
            "SSS",
            'I', new UnificationEntry(OrePrefix.ingot, Materials.Steel),
            'S', OrePrefix.stone);

        ModHandler.addShapedRecipe("mortar_wrought_iron", MORTAR.getStackForm(Materials.WroughtIron, null),
            " I ",
            "SIS",
            "SSS",
            'I', new UnificationEntry(OrePrefix.ingot, Materials.WroughtIron),
            'S', OrePrefix.stone);

        ModHandler.addShapedRecipe("mortar_red_steel", MORTAR.getStackForm(Materials.RedSteel, null),
            " I ",
            "SIS",
            "SSS",
            'I', new UnificationEntry(OrePrefix.ingot, Materials.RedSteel),
            'S', OrePrefix.stone);

        ModHandler.addShapedRecipe("mortar_blue_steel", MORTAR.getStackForm(Materials.BlueSteel, null),
            " I ",
            "SIS",
            "SSS",
            'I', new UnificationEntry(OrePrefix.ingot, Materials.BlueSteel),
            'S', OrePrefix.stone);

        ModHandler.addShapedRecipe("mortar_black_steel", MORTAR.getStackForm(Materials.BlackSteel, null),
            " I ",
            "SIS",
            "SSS",
            'I', new UnificationEntry(OrePrefix.ingot, Materials.BlackSteel),
            'S', OrePrefix.stone);

        ModHandler.addShapedRecipe("mortar_damascus_steel", MORTAR.getStackForm(Materials.DamascusSteel, null),
            " I ",
            "SIS",
            "SSS",
            'I', new UnificationEntry(OrePrefix.ingot, Materials.DamascusSteel),
            'S', OrePrefix.stone);

        ModHandler.addShapedRecipe("rolling_pin_wood", ROLLING_PIN.getStackForm(Materials.Wood, null),
            "  S",
            " I ",
            "S f",
            'I', new UnificationEntry(OrePrefix.ingot, Materials.Wood),
            'S', new UnificationEntry(OrePrefix.stick, Materials.Wood));

        ModHandler.addShapedRecipe("rolling_pin_aluminium", ROLLING_PIN.getStackForm(Materials.Aluminium, null),
            "  S",
            " I ",
            "S f",
            'I', new UnificationEntry(OrePrefix.ingot, Materials.Aluminium),
            'S', new UnificationEntry(OrePrefix.stick, Materials.Aluminium));

        ModHandler.addShapedRecipe("rolling_pin_stainless_steel", ROLLING_PIN.getStackForm(Materials.StainlessSteel, null),
            "  S",
            " I ",
            "S f",
            'I', new UnificationEntry(OrePrefix.ingot, Materials.StainlessSteel),
            'S', new UnificationEntry(OrePrefix.stick, Materials.StainlessSteel));


        ModHandler.addShapedRecipe("sword_flint", SWORD.getStackForm(Materials.Flint, Materials.Wood),
            "F",
            "F",
            "S",
            'S', new UnificationEntry(OrePrefix.stick, Materials.Wood),
            'F', new ItemStack(Items.FLINT, 1));

        ModHandler.addShapedRecipe("pickaxe_flint", PICKAXE.getStackForm(Materials.Flint, Materials.Wood),
            "FFF",
            " S ",
            " S ",
            'S', new UnificationEntry(OrePrefix.stick, Materials.Wood),
            'F', new ItemStack(Items.FLINT, 1));

        ModHandler.addShapedRecipe("shovel_flint", SHOVEL.getStackForm(Materials.Flint, Materials.Wood),
            "F",
            "S",
            "S",
            'S', new UnificationEntry(OrePrefix.stick, Materials.Wood),
            'F', new ItemStack(Items.FLINT, 1));

        ModHandler.addMirroredShapedRecipe("axe_flint", AXE.getStackForm(Materials.Flint, Materials.Wood),
            "FF",
            "FS",
            " S",
            'S', new UnificationEntry(OrePrefix.stick, Materials.Wood),
            'F', new ItemStack(Items.FLINT, 1));

        ModHandler.addMirroredShapedRecipe("hoe_flint", HOE.getStackForm(Materials.Flint, Materials.Wood),
            "FF",
            " S",
            " S",
            'S', new UnificationEntry(OrePrefix.stick, Materials.Wood),
            'F', new ItemStack(Items.FLINT, 1));

        ModHandler.addShapedRecipe("knife_flint", KNIFE.getStackForm(Materials.Flint, null),
            "F",
            "S",
            'S', new UnificationEntry(OrePrefix.stick, Materials.Wood),
            'F', new ItemStack(Items.FLINT, 1));

        ModHandler.addShapedRecipe("mortar_flint", MORTAR.getStackForm(Materials.Flint, null),
            " I ",
            "SIS",
            "SSS",
            'I', new ItemStack(Items.FLINT, 1),
            'S', OrePrefix.stone);

        ModHandler.addShapedRecipe("mortar_bronze", MORTAR.getStackForm(Materials.Bronze, null),
            " I ",
            "SIS",
            "SSS",
            'I', new UnificationEntry(OrePrefix.ingot, Materials.Bronze),
            'S', OrePrefix.stone);

        ModHandler.addShapedRecipe("mortar_iron", MORTAR.getStackForm(Materials.Iron, null),
            " I ",
            "SIS",
            "SSS",
            'I', new UnificationEntry(OrePrefix.ingot, Materials.Iron),
            'S', OrePrefix.stone);

        ModHandler.addShapedRecipe("mortar_steel", MORTAR.getStackForm(Materials.Steel, null),
            " I ",
            "SIS",
            "SSS",
            'I', new UnificationEntry(OrePrefix.ingot, Materials.Steel),
            'S', OrePrefix.stone);

        ModHandler.addShapedRecipe("mortar_wrought_iron", MORTAR.getStackForm(Materials.WroughtIron, null),
            " I ",
            "SIS",
            "SSS",
            'I', new UnificationEntry(OrePrefix.ingot, Materials.WroughtIron),
            'S', OrePrefix.stone);

        ModHandler.addShapedRecipe("mortar_red_steel", MORTAR.getStackForm(Materials.RedSteel, null),
            " I ",
            "SIS",
            "SSS",
            'I', new UnificationEntry(OrePrefix.ingot, Materials.RedSteel),
            'S', OrePrefix.stone);

        ModHandler.addShapedRecipe("mortar_blue_steel", MORTAR.getStackForm(Materials.BlueSteel, null),
            " I ",
            "SIS",
            "SSS",
            'I', new UnificationEntry(OrePrefix.ingot, Materials.BlueSteel),
            'S', OrePrefix.stone);

        ModHandler.addShapedRecipe("mortar_black_steel", MORTAR.getStackForm(Materials.BlackSteel, null),
            " I ",
            "SIS",
            "SSS",
            'I', new UnificationEntry(OrePrefix.ingot, Materials.BlackSteel),
            'S', OrePrefix.stone);

        ModHandler.addShapedRecipe("mortar_damascus_steel", MORTAR.getStackForm(Materials.DamascusSteel, null),
            " I ",
            "SIS",
            "SSS",
            'I', new UnificationEntry(OrePrefix.ingot, Materials.DamascusSteel),
            'S', OrePrefix.stone);

        ModHandler.addShapelessRecipe("coal_to_dust", OreDictUnifier.get(OrePrefix.dust, Materials.Coal, 1),
            ToolDictNames.craftingToolMortar,
            new ItemStack(Items.COAL, 1));


        ModHandler.addShapelessRecipe("clay_to_dust", OreDictUnifier.get(OrePrefix.dust, Materials.Clay, 1),
            ToolDictNames.craftingToolMortar,
            new ItemStack(Blocks.CLAY, 1));


        ModHandler.addShapelessRecipe("wheat_to_dust", OreDictUnifier.get(OrePrefix.dust, Materials.Wheat, 1),
            ToolDictNames.craftingToolMortar,
            new ItemStack(Items.WHEAT, 1));


        ModHandler.addShapelessRecipe("gravel_to_flint", new ItemStack(Items.FLINT, 1),
            ToolDictNames.craftingToolMortar,
            new ItemStack(Blocks.GRAVEL, 1));


        ModHandler.addShapelessRecipe("blaze_rod_to_powder", new ItemStack(Items.BLAZE_POWDER, 2),
            ToolDictNames.craftingToolMortar,
            new ItemStack(Items.BLAZE_ROD, 1));
    }

}