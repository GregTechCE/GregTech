package gregtech.common.items;

import gregtech.api.GregTechAPI;
import gregtech.api.items.ToolDictNames;
import gregtech.api.items.metaitem.ElectricStats;
import gregtech.api.items.toolitem.ToolMetaItem;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.IngotMaterial;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.ConfigHolder;
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
        HARD_HAMMER = addItem(6, "tool.hard_hammer").setToolStats(new ToolHardHammer()).addOreDict(ToolDictNames.craftingToolHardHammer).addToList(GregTechAPI.hardHammerList);
        SOFT_HAMMER = addItem(7, "tool.soft_hammer").setToolStats(new ToolSoftHammer()).addOreDict(ToolDictNames.craftingToolSoftHammer).addToList(GregTechAPI.softHammerList);
        WRENCH = addItem(8, "tool.wrench").setToolStats(new ToolWrench()).addOreDict(ToolDictNames.craftingToolWrench).addToList(GregTechAPI.wrenchList);
        FILE = addItem(9, "tool.file").setToolStats(new ToolFile()).addOreDict(ToolDictNames.craftingToolFile);
        CROWBAR = addItem(10, "tool.crowbar").setToolStats(new ToolCrowbar()).addOreDict(ToolDictNames.craftingToolCrowbar).addToList(GregTechAPI.crowbarList);
        SCREWDRIVER = addItem(11, "tool.screwdriver").setToolStats(new ToolScrewdriver()).addOreDict(ToolDictNames.craftingToolScrewdriver).addToList(GregTechAPI.screwdriverList);
        MORTAR = addItem(12, "tool.mortar").setToolStats(new ToolMortar()).addOreDict(ToolDictNames.craftingToolMortar);
        WIRE_CUTTER = addItem(13, "tool.wire_cutter").setToolStats(new ToolWireCutter()).addOreDict(ToolDictNames.craftingToolWireCutter);
        SCOOP = addItem(14, "tool.scoop").setToolStats(new ToolScoop()).addOreDict(ToolDictNames.craftingToolScoop);
        BRANCH_CUTTER = addItem(15, "tool.branch_cutter").setToolStats(new ToolBranchCutter()).addOreDict(ToolDictNames.craftingToolBranchCutter);
        UNIVERSAL_SPADE = addItem(16, "tool.universal_spade").setToolStats(new ToolUniversalSpade()).addOreDict(ToolDictNames.craftingToolBlade, ToolDictNames.craftingToolShovel, ToolDictNames.craftingToolCrowbar, ToolDictNames.craftingToolSaw).addToList(GregTechAPI.crowbarList);
        KNIFE = addItem(17, "tool.knife").setToolStats(new ToolKnife()).addOreDict(ToolDictNames.craftingToolBlade, ToolDictNames.craftingToolKnife);
        BUTCHERY_KNIFE = addItem(18, "tool.butchery_knife").setToolStats(new ToolButcheryKnife()).addOreDict(ToolDictNames.craftingToolBlade);

        SENSE = addItem(19, "tool.sense").setToolStats(new ToolSense()).addOreDict(ToolDictNames.craftingToolBlade);
        PLOW = addItem(20, "tool.plow").setToolStats(new ToolPlow()).addOreDict(ToolDictNames.craftingToolPlow);
        DRILL_LV = (MetaToolValueItem) addItem(23, "tool.drill.lv").setToolStats(new ToolDrillLV())
            .addOreDict(ToolDictNames.craftingToolMiningDrill)
            .addStats(ElectricStats.createElectricItem(100000L, 1L));
        DRILL_MV = (MetaToolValueItem) addItem(24, "tool.drill.mv").setToolStats(new ToolDrillMV())
            .addOreDict(ToolDictNames.craftingToolMiningDrill)
            .addStats(ElectricStats.createElectricItem(400000L, 2L));
        DRILL_HV = (MetaToolValueItem) addItem(25, "tool.drill.hv").setToolStats(new ToolDrillHV())
            .addOreDict(ToolDictNames.craftingToolMiningDrill)
            .addStats(ElectricStats.createElectricItem(1600000L, 3L));

        CHAINSAW_LV = (MetaToolValueItem) addItem(26, "tool.chainsaw.lv").setToolStats(new ToolChainsawLV())
            .addOreDict(ToolDictNames.craftingToolSaw)
            .addStats(ElectricStats.createElectricItem(100000L, 1L));
        CHAINSAW_MV = (MetaToolValueItem) addItem(27, "tool.chainsaw.mv").setToolStats(new ToolChainsawMV())
            .addOreDict(ToolDictNames.craftingToolSaw)
            .addStats(ElectricStats.createElectricItem(400000L, 2L));
        CHAINSAW_HV = (MetaToolValueItem) addItem(28, "tool.chainsaw.hv").setToolStats(new ToolChainsawHV())
            .addOreDict(ToolDictNames.craftingToolSaw)
            .addStats(ElectricStats.createElectricItem(1600000L, 3L));

        WRENCH_LV = (MetaToolValueItem) addItem(29, "tool.wrench.lv").setToolStats(new ToolWrenchLV())
            .addOreDict(ToolDictNames.craftingToolWrench).addToList(GregTechAPI.wrenchList)
            .addStats(ElectricStats.createElectricItem(100000L, 1L));
        WRENCH_MV = (MetaToolValueItem) addItem(30, "tool.wrench.mv").setToolStats(new ToolWrenchMV())
            .addOreDict(ToolDictNames.craftingToolWrench).addToList(GregTechAPI.wrenchList)
            .addStats(ElectricStats.createElectricItem(400000L, 2L));
        WRENCH_HV = (MetaToolValueItem) addItem(31, "tool.wrench.hv").setToolStats(new ToolWrenchHV())
            .addOreDict(ToolDictNames.craftingToolWrench).addToList(GregTechAPI.wrenchList)
            .addStats(ElectricStats.createElectricItem(1600000L, 3L));

        SCREWDRIVER_LV = (MetaToolValueItem) addItem(34, "tool.screwdriver.lv").setToolStats(new ToolScrewdriverLV())
            .addOreDict(ToolDictNames.craftingToolScrewdriver).addToList(GregTechAPI.screwdriverList)
            .addStats(ElectricStats.createElectricItem(100000L, 1L));
        SOLDERING_IRON_LV = (MetaToolValueItem) addItem(35, "tool.soldering_iron.lv").setToolStats(new ToolSolderingIron())
            .addOreDict(ToolDictNames.craftingToolSolderingIron)
            .addToList(GregTechAPI.solderingToolList)
            .addStats(ElectricStats.createElectricItem(100000L, 1L));

        JACKHAMMER = (MetaToolValueItem) addItem(32, "tool.jackhammer").setToolStats(new ToolJackHammer())
            .addOreDict(ToolDictNames.craftingToolJackHammer)
            .addStats(ElectricStats.createElectricItem(100000L, 1L));
        BUZZSAW = (MetaToolValueItem) addItem(33, "tool.buzzsaw").setToolStats(new ToolBuzzSaw())
            .addOreDict(ToolDictNames.craftingToolSaw)
            .addStats(ElectricStats.createElectricItem(100000L, 1L));

        MAGNIFYING_GLASS = addItem(36, "tool.magnifying_glass").setToolStats(new ToolMagnifyingGlass()).addOreDict(ToolDictNames.craftingToolMagnifyingGlass);
        PLUNGER = addItem(37, "tool.plunger").setToolStats(new ToolPlunger()).addOreDict(ToolDictNames.craftingToolPlunger);
        TURBINE = addItem(38, "tool.turbine").setToolStats(new ToolTurbineRotor());
    }

    public void registerRecipes() {
        ModHandler.addShapedRecipe("mortar_flint", MORTAR.getStackForm(Materials.Flint, null),
            " I ", "SIS", "SSS",
            'I', new ItemStack(Items.FLINT, 1),
            'S', OrePrefix.stone);

        IngotMaterial[] mortarMaterials = new IngotMaterial[] {Materials.Bronze, Materials.Iron,
            Materials.Steel, Materials.DamascusSteel, Materials.WroughtIron, Materials.RedSteel,
            Materials.BlackSteel, Materials.BlueSteel};
        IngotMaterial[] softHammerMaterials = new IngotMaterial[] {Materials.Rubber, Materials.Polytetrafluoroethylene};

        for (IngotMaterial material : mortarMaterials) {

            ModHandler.addShapedRecipe("mortar_" + material.toString(),
                MORTAR.getStackForm(material, null),
                " I ", "SIS", "SSS",
                'I', new UnificationEntry(OrePrefix.ingot, material),
                'S', OrePrefix.stone);
        }

        for (IngotMaterial material : softHammerMaterials) {
            ModHandler.addShapedRecipe("soft_hammer_" + material.toString(),
                SOFT_HAMMER.getStackForm(material, material),
                "RR ", "RRS", "RR ",
                'R', new UnificationEntry(OrePrefix.ingot, material),
                'S', new ItemStack(Items.STICK));
        }

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

        if(!ConfigHolder.disableFlintTools) {
            registerFlintToolRecipes();
        }
    }

    private void registerFlintToolRecipes() {
        ModHandler.addShapedRecipe("sword_flint", SWORD.getStackForm(Materials.Flint, Materials.Wood),
            "F", "F", "S",
            'S', new UnificationEntry(OrePrefix.stick, Materials.Wood),
            'F', new ItemStack(Items.FLINT, 1));

        ModHandler.addShapedRecipe("pickaxe_flint", PICKAXE.getStackForm(Materials.Flint, Materials.Wood),
            "FFF", " S ", " S ",
            'S', new UnificationEntry(OrePrefix.stick, Materials.Wood),
            'F', new ItemStack(Items.FLINT, 1));

        ModHandler.addShapedRecipe("shovel_flint", SHOVEL.getStackForm(Materials.Flint, Materials.Wood),
            "F", "S", "S",
            'S', new UnificationEntry(OrePrefix.stick, Materials.Wood),
            'F', new ItemStack(Items.FLINT, 1));

        ModHandler.addMirroredShapedRecipe("axe_flint", AXE.getStackForm(Materials.Flint, Materials.Wood),
            "FF", "FS", " S",
            'S', new UnificationEntry(OrePrefix.stick, Materials.Wood),
            'F', new ItemStack(Items.FLINT, 1));

        ModHandler.addMirroredShapedRecipe("hoe_flint", HOE.getStackForm(Materials.Flint, Materials.Wood),
            "FF", " S", " S",
            'S', new UnificationEntry(OrePrefix.stick, Materials.Wood),
            'F', new ItemStack(Items.FLINT, 1));

        ModHandler.addShapedRecipe("knife_flint", KNIFE.getStackForm(Materials.Flint, null),
            "F", "S",
            'S', new UnificationEntry(OrePrefix.stick, Materials.Wood),
            'F', new ItemStack(Items.FLINT, 1));
    }

}