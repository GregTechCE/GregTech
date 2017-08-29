package gregtech.loaders.oreprocessing;

import gregtech.api.items.ItemList;
import gregtech.api.items.ToolDictNames;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.SolidMaterial;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.CommonProxy;
import gregtech.common.items.MetaTool;

public class ProcessingToolHead implements IOreRegistrationHandler {
    public ProcessingToolHead() {
        OrePrefix.toolHeadAxe.addProcessingHandler(this);
        OrePrefix.toolHeadBuzzSaw.addProcessingHandler(this);
        OrePrefix.toolHeadChainsaw.addProcessingHandler(this);
        OrePrefix.toolHeadDrill.addProcessingHandler(this);
        OrePrefix.toolHeadFile.addProcessingHandler(this);
        OrePrefix.toolHeadHoe.addProcessingHandler(this);
        OrePrefix.toolHeadPickaxe.addProcessingHandler(this);
        OrePrefix.toolHeadPlow.addProcessingHandler(this);
        OrePrefix.toolHeadSaw.addProcessingHandler(this);
        OrePrefix.toolHeadSense.addProcessingHandler(this);
        OrePrefix.toolHeadShovel.addProcessingHandler(this);
        OrePrefix.toolHeadSword.addProcessingHandler(this);
        OrePrefix.toolHeadUniversalSpade.addProcessingHandler(this);
        OrePrefix.toolHeadWrench.addProcessingHandler(this);
        OrePrefix.toolHeadHammer.addProcessingHandler(this);
        OrePrefix.turbineBlade.addProcessingHandler(this);
    }

    @Override
    public void registerOre(UnificationEntry uEntry, String modName, SimpleItemStack simpleStack) {
        boolean aSpecialRecipeReq1 = uEntry.material.mUnificatable && (uEntry.material.mMaterialInto == uEntry.material) && !uEntry.material.hasFlag(DustMaterial.MatFlags.NO_SMASHING);
        boolean aSpecialRecipeReq2 = uEntry.material.mUnificatable && (uEntry.material.mMaterialInto == uEntry.material) && !uEntry.material.hasFlag(DustMaterial.MatFlags.NO_WORKING);
        boolean aNoWorking = uEntry.material.hasFlag(DustMaterial.MatFlags.NO_WORKING);

        if (uEntry.material instanceof SolidMaterial) {
            SolidMaterial solidMaterial = (SolidMaterial) uEntry.material;
            switch (uEntry.orePrefix) {
                case toolHeadAxe:
                    ModHandler.addShapelessCraftingRecipe(MetaTool.INSTANCE.getToolWithStats(MetaTool.AXE, 1, solidMaterial, solidMaterial.handleMaterial, null), new Object[]{aOreDictName, OreDictUnifier.get(OrePrefix.stick, solidMaterial.handleMaterial)});
                    if (aSpecialRecipeReq1)
                        ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.toolHeadAxe, solidMaterial, 1), CommonProxy.tBits, new Object[]{"PIh", "P  ", "f  ", 'P', OreDictUnifier.get(OrePrefix.plate, solidMaterial), 'I', OreDictUnifier.get(OrePrefix.ingot, solidMaterial)});
                    if (!aNoWorking)
                        ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.toolHeadAxe, solidMaterial, 1), CommonProxy.tBits, new Object[]{"GG ", "G  ", "f  ", 'G', OreDictUnifier.get(OrePrefix.gem, solidMaterial)});
                    break;
                case toolHeadBuzzSaw:
                    ModHandler.addCraftingRecipe(MetaTool.INSTANCE.getToolWithStats(MetaTool.BUZZSAW, 1, solidMaterial, Materials.StainlessSteel, new long[]{100000L, 32L, 1L, -1L}), ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | ModHandler.RecipeBits.BUFFERED, new Object[]{"PBM", "dXG", "SGP", 'X', aOreDictName, 'M', ItemList.Electric_Motor_LV.get(1), 'S', OreDictUnifier.get(OrePrefix.screw, Materials.StainlessSteel), 'P', OreDictUnifier.get(OrePrefix.plate, Materials.StainlessSteel), 'G', OreDictUnifier.get(OrePrefix.gearGtSmall, Materials.StainlessSteel), 'B', ItemList.Battery_RE_LV_Lithium.get(1)});
                    ModHandler.addCraftingRecipe(MetaTool.INSTANCE.getToolWithStats(MetaTool.BUZZSAW, 1, solidMaterial, Materials.StainlessSteel, new long[]{75000L, 32L, 1L, -1L}), ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | ModHandler.RecipeBits.BUFFERED, new Object[]{"PBM", "dXG", "SGP", 'X', aOreDictName, 'M', ItemList.Electric_Motor_LV.get(1), 'S', OreDictUnifier.get(OrePrefix.screw, Materials.StainlessSteel), 'P', OreDictUnifier.get(OrePrefix.plate, Materials.StainlessSteel), 'G', OreDictUnifier.get(OrePrefix.gearGtSmall, Materials.StainlessSteel), 'B', ItemList.Battery_RE_LV_Cadmium.get(1)});
                    ModHandler.addCraftingRecipe(MetaTool.INSTANCE.getToolWithStats(MetaTool.BUZZSAW, 1, solidMaterial, Materials.StainlessSteel, new long[]{50000L, 32L, 1L, -1L}), ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | ModHandler.RecipeBits.BUFFERED, new Object[]{"PBM", "dXG", "SGP", 'X', aOreDictName, 'M', ItemList.Electric_Motor_LV.get(1), 'S', OreDictUnifier.get(OrePrefix.screw, Materials.StainlessSteel), 'P', OreDictUnifier.get(OrePrefix.plate, Materials.StainlessSteel), 'G', OreDictUnifier.get(OrePrefix.gearGtSmall, Materials.StainlessSteel), 'B', ItemList.Battery_RE_LV_Sodium.get(1)});
                    if (aSpecialRecipeReq2)
                        ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.toolHeadBuzzSaw, solidMaterial, 1), CommonProxy.tBits, new Object[]{"wXh", "X X", "fXx", 'X', OreDictUnifier.get(OrePrefix.plate, solidMaterial)});
                    break;
                case toolHeadChainsaw:
                    ModHandler.addCraftingRecipe(MetaTool.INSTANCE.getToolWithStats(MetaTool.CHAINSAW_LV, 1, solidMaterial, Materials.StainlessSteel, new long[]{100000L, 32L, 1L, -1L}), ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | ModHandler.RecipeBits.BUFFERED, new Object[]{"SXd", "GMG", "PBP", 'X', aOreDictName, 'M', ItemList.Electric_Motor_LV.get(1), 'S', OreDictUnifier.get(OrePrefix.screw, Materials.StainlessSteel), 'P', OreDictUnifier.get(OrePrefix.plate, Materials.StainlessSteel), 'G', OreDictUnifier.get(OrePrefix.gearGtSmall, Materials.StainlessSteel), 'B', ItemList.Battery_RE_LV_Lithium.get(1)});
                    ModHandler.addCraftingRecipe(MetaTool.INSTANCE.getToolWithStats(MetaTool.CHAINSAW_MV, 1, solidMaterial, Materials.Titanium, new long[]{400000L, 128L, 2L, -1L}), ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | ModHandler.RecipeBits.BUFFERED, new Object[]{"SXd", "GMG", "PBP", 'X', aOreDictName, 'M', ItemList.Electric_Motor_MV.get(1), 'S', OreDictUnifier.get(OrePrefix.screw, Materials.Titanium), 'P', OreDictUnifier.get(OrePrefix.plate, Materials.Titanium), 'G', OreDictUnifier.get(OrePrefix.plate, Materials.Titanium), 'B', ItemList.Battery_RE_MV_Lithium.get(1)});
                    ModHandler.addCraftingRecipe(MetaTool.INSTANCE.getToolWithStats(MetaTool.CHAINSAW_HV, 1, solidMaterial, Materials.TungstenSteel, new long[]{1600000L, 512L, 3L, -1L}), ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | ModHandler.RecipeBits.BUFFERED, new Object[]{"SXd", "GMG", "PBP", 'X', aOreDictName, 'M', ItemList.Electric_Motor_HV.get(1), 'S', OreDictUnifier.get(OrePrefix.screw, Materials.TungstenSteel), 'P', OreDictUnifier.get(OrePrefix.plate, Materials.TungstenSteel), 'G', OreDictUnifier.get(OrePrefix.gearGtSmall, Materials.TungstenSteel), 'B', ItemList.Battery_RE_HV_Lithium.get(1)});
                    ModHandler.addCraftingRecipe(MetaTool.INSTANCE.getToolWithStats(MetaTool.CHAINSAW_LV, 1, solidMaterial, Materials.StainlessSteel, new long[]{75000L, 32L, 1L, -1L}), ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | ModHandler.RecipeBits.BUFFERED, new Object[]{"SXd", "GMG", "PBP", 'X', aOreDictName, 'M', ItemList.Electric_Motor_LV.get(1), 'S', OreDictUnifier.get(OrePrefix.screw, Materials.StainlessSteel), 'P', OreDictUnifier.get(OrePrefix.plate, Materials.StainlessSteel), 'G', OreDictUnifier.get(OrePrefix.gearGtSmall, Materials.StainlessSteel), 'B', ItemList.Battery_RE_LV_Cadmium.get(1)});
                    ModHandler.addCraftingRecipe(MetaTool.INSTANCE.getToolWithStats(MetaTool.CHAINSAW_MV, 1, solidMaterial, Materials.Titanium, new long[]{300000L, 128L, 2L, -1L}), ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | ModHandler.RecipeBits.BUFFERED, new Object[]{"SXd", "GMG", "PBP", 'X', aOreDictName, 'M', ItemList.Electric_Motor_MV.get(1), 'S', OreDictUnifier.get(OrePrefix.screw, Materials.Titanium), 'P', OreDictUnifier.get(OrePrefix.plate, Materials.Titanium), 'G', OreDictUnifier.get(OrePrefix.plate, Materials.Titanium), 'B', ItemList.Battery_RE_MV_Cadmium.get(1)});
                    ModHandler.addCraftingRecipe(MetaTool.INSTANCE.getToolWithStats(MetaTool.CHAINSAW_HV, 1, solidMaterial, Materials.TungstenSteel, new long[]{1200000L, 512L, 3L, -1L}), ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | ModHandler.RecipeBits.BUFFERED, new Object[]{"SXd", "GMG", "PBP", 'X', aOreDictName, 'M', ItemList.Electric_Motor_HV.get(1), 'S', OreDictUnifier.get(OrePrefix.screw, Materials.TungstenSteel), 'P', OreDictUnifier.get(OrePrefix.plate, Materials.TungstenSteel), 'G', OreDictUnifier.get(OrePrefix.gearGtSmall, Materials.TungstenSteel), 'B', ItemList.Battery_RE_HV_Cadmium.get(1)});
                    ModHandler.addCraftingRecipe(MetaTool.INSTANCE.getToolWithStats(MetaTool.CHAINSAW_LV, 1, solidMaterial, Materials.StainlessSteel, new long[]{50000L, 32L, 1L, -1L}), ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | ModHandler.RecipeBits.BUFFERED, new Object[]{"SXd", "GMG", "PBP", 'X', aOreDictName, 'M', ItemList.Electric_Motor_LV.get(1), 'S', OreDictUnifier.get(OrePrefix.screw, Materials.StainlessSteel), 'P', OreDictUnifier.get(OrePrefix.plate, Materials.StainlessSteel), 'G', OreDictUnifier.get(OrePrefix.gearGtSmall, Materials.StainlessSteel), 'B', ItemList.Battery_RE_LV_Sodium.get(1)});
                    ModHandler.addCraftingRecipe(MetaTool.INSTANCE.getToolWithStats(MetaTool.CHAINSAW_MV, 1, solidMaterial, Materials.Titanium, new long[]{200000L, 128L, 2L, -1L}), ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | ModHandler.RecipeBits.BUFFERED, new Object[]{"SXd", "GMG", "PBP", 'X', aOreDictName, 'M', ItemList.Electric_Motor_MV.get(1), 'S', OreDictUnifier.get(OrePrefix.screw, Materials.Titanium), 'P', OreDictUnifier.get(OrePrefix.plate, Materials.Titanium), 'G', OreDictUnifier.get(OrePrefix.plate, Materials.Titanium), 'B', ItemList.Battery_RE_MV_Sodium.get(1)});
                    ModHandler.addCraftingRecipe(MetaTool.INSTANCE.getToolWithStats(MetaTool.CHAINSAW_HV, 1, solidMaterial, Materials.TungstenSteel, new long[]{800000L, 512L, 3L, -1L}), ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | ModHandler.RecipeBits.BUFFERED, new Object[]{"SXd", "GMG", "PBP", 'X', aOreDictName, 'M', ItemList.Electric_Motor_HV.get(1), 'S', OreDictUnifier.get(OrePrefix.screw, Materials.TungstenSteel), 'P', OreDictUnifier.get(OrePrefix.plate, Materials.TungstenSteel), 'G', OreDictUnifier.get(OrePrefix.gearGtSmall, Materials.TungstenSteel), 'B', ItemList.Battery_RE_HV_Sodium.get(1)});
                    if (aSpecialRecipeReq2)
                        ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.toolHeadChainsaw, solidMaterial, 1), CommonProxy.tBits, new Object[]{"SRS", "XhX", "SRS", 'X', OreDictUnifier.get(OrePrefix.plate, solidMaterial), 'S', OreDictUnifier.get(OrePrefix.plate, Materials.Steel), 'R', OreDictUnifier.get(OrePrefix.ring, Materials.Steel)});
                    break;
                case toolHeadDrill:
                    ModHandler.addCraftingRecipe(MetaTool.INSTANCE.getToolWithStats(MetaTool.DRILL_LV, 1, solidMaterial, Materials.StainlessSteel, new long[]{100000L, 32L, 1L, -1L}), ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | ModHandler.RecipeBits.BUFFERED, new Object[]{"SXd", "GMG", "PBP", 'X', aOreDictName, 'M', ItemList.Electric_Motor_LV.get(1), 'S', OreDictUnifier.get(OrePrefix.screw, Materials.StainlessSteel), 'P', OreDictUnifier.get(OrePrefix.plate, Materials.StainlessSteel), 'G', OreDictUnifier.get(OrePrefix.gearGtSmall, Materials.StainlessSteel), 'B', ItemList.Battery_RE_LV_Lithium.get(1)});
                    ModHandler.addCraftingRecipe(MetaTool.INSTANCE.getToolWithStats(MetaTool.DRILL_LV, 1, solidMaterial, Materials.StainlessSteel, new long[]{75000L, 32L, 1L, -1L}), ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | ModHandler.RecipeBits.BUFFERED, new Object[]{"SXd", "GMG", "PBP", 'X', aOreDictName, 'M', ItemList.Electric_Motor_LV.get(1), 'S', OreDictUnifier.get(OrePrefix.screw, Materials.StainlessSteel), 'P', OreDictUnifier.get(OrePrefix.plate, Materials.StainlessSteel), 'G', OreDictUnifier.get(OrePrefix.gearGtSmall, Materials.StainlessSteel), 'B', ItemList.Battery_RE_LV_Cadmium.get(1)});
                    ModHandler.addCraftingRecipe(MetaTool.INSTANCE.getToolWithStats(MetaTool.DRILL_LV, 1, solidMaterial, Materials.StainlessSteel, new long[]{50000L, 32L, 1L, -1L}), ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | ModHandler.RecipeBits.BUFFERED, new Object[]{"SXd", "GMG", "PBP", 'X', aOreDictName, 'M', ItemList.Electric_Motor_LV.get(1), 'S', OreDictUnifier.get(OrePrefix.screw, Materials.StainlessSteel), 'P', OreDictUnifier.get(OrePrefix.plate, Materials.StainlessSteel), 'G', OreDictUnifier.get(OrePrefix.gearGtSmall, Materials.StainlessSteel), 'B', ItemList.Battery_RE_LV_Sodium.get(1)});
                    ModHandler.addCraftingRecipe(MetaTool.INSTANCE.getToolWithStats(MetaTool.DRILL_MV, 1, solidMaterial, Materials.Titanium, new long[]{400000L, 128L, 2L, -1L}), ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | ModHandler.RecipeBits.BUFFERED, new Object[]{"SXd", "GMG", "PBP", 'X', aOreDictName, 'M', ItemList.Electric_Motor_MV.get(1), 'S', OreDictUnifier.get(OrePrefix.screw, Materials.Titanium), 'P', OreDictUnifier.get(OrePrefix.plate, Materials.Titanium), 'G', OreDictUnifier.get(OrePrefix.plate, Materials.Titanium), 'B', ItemList.Battery_RE_MV_Lithium.get(1)});
                    ModHandler.addCraftingRecipe(MetaTool.INSTANCE.getToolWithStats(MetaTool.DRILL_MV, 1, solidMaterial, Materials.Titanium, new long[]{300000L, 128L, 2L, -1L}), ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | ModHandler.RecipeBits.BUFFERED, new Object[]{"SXd", "GMG", "PBP", 'X', aOreDictName, 'M', ItemList.Electric_Motor_MV.get(1), 'S', OreDictUnifier.get(OrePrefix.screw, Materials.Titanium), 'P', OreDictUnifier.get(OrePrefix.plate, Materials.Titanium), 'G', OreDictUnifier.get(OrePrefix.plate, Materials.Titanium), 'B', ItemList.Battery_RE_MV_Cadmium.get(1)});
                    ModHandler.addCraftingRecipe(MetaTool.INSTANCE.getToolWithStats(MetaTool.DRILL_MV, 1, solidMaterial, Materials.Titanium, new long[]{200000L, 128L, 2L, -1L}), ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | ModHandler.RecipeBits.BUFFERED, new Object[]{"SXd", "GMG", "PBP", 'X', aOreDictName, 'M', ItemList.Electric_Motor_MV.get(1), 'S', OreDictUnifier.get(OrePrefix.screw, Materials.Titanium), 'P', OreDictUnifier.get(OrePrefix.plate, Materials.Titanium), 'G', OreDictUnifier.get(OrePrefix.plate, Materials.Titanium), 'B', ItemList.Battery_RE_MV_Sodium.get(1)});
                    ModHandler.addCraftingRecipe(MetaTool.INSTANCE.getToolWithStats(MetaTool.DRILL_HV, 1, solidMaterial, Materials.TungstenSteel, new long[]{1600000L, 512L, 3L, -1L}), ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | ModHandler.RecipeBits.BUFFERED, new Object[]{"SXd", "GMG", "PBP", 'X', aOreDictName, 'M', ItemList.Electric_Motor_HV.get(1), 'S', OreDictUnifier.get(OrePrefix.screw, Materials.TungstenSteel), 'P', OreDictUnifier.get(OrePrefix.plate, Materials.TungstenSteel), 'G', OreDictUnifier.get(OrePrefix.gearGtSmall, Materials.TungstenSteel), 'B', ItemList.Battery_RE_HV_Lithium.get(1)});
                    ModHandler.addCraftingRecipe(MetaTool.INSTANCE.getToolWithStats(MetaTool.DRILL_HV, 1, solidMaterial, Materials.TungstenSteel, new long[]{1200000L, 512L, 3L, -1L}), ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | ModHandler.RecipeBits.BUFFERED, new Object[]{"SXd", "GMG", "PBP", 'X', aOreDictName, 'M', ItemList.Electric_Motor_HV.get(1), 'S', OreDictUnifier.get(OrePrefix.screw, Materials.TungstenSteel), 'P', OreDictUnifier.get(OrePrefix.plate, Materials.TungstenSteel), 'G', OreDictUnifier.get(OrePrefix.gearGtSmall, Materials.TungstenSteel), 'B', ItemList.Battery_RE_HV_Cadmium.get(1)});
                    ModHandler.addCraftingRecipe(MetaTool.INSTANCE.getToolWithStats(MetaTool.DRILL_HV, 1, solidMaterial, Materials.TungstenSteel, new long[]{800000L, 512L, 3L, -1L}), ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | ModHandler.RecipeBits.BUFFERED, new Object[]{"SXd", "GMG", "PBP", 'X', aOreDictName, 'M', ItemList.Electric_Motor_HV.get(1), 'S', OreDictUnifier.get(OrePrefix.screw, Materials.TungstenSteel), 'P', OreDictUnifier.get(OrePrefix.plate, Materials.TungstenSteel), 'G', OreDictUnifier.get(OrePrefix.gearGtSmall, Materials.TungstenSteel), 'B', ItemList.Battery_RE_HV_Sodium.get(1)});
                    ModHandler.addCraftingRecipe(MetaTool.INSTANCE.getToolWithStats(MetaTool.JACKHAMMER, 1, solidMaterial, Materials.Titanium, new long[]{1600000L, 512L, 3L, -1L}), ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | ModHandler.RecipeBits.BUFFERED, new Object[]{"SXd", "PRP", "MPB", 'X', OreDictUnifier.get(OrePrefix.stickLong, solidMaterial), 'M', ItemList.Electric_Piston_HV.get(1), 'S', OreDictUnifier.get(OrePrefix.screw, Materials.Titanium), 'P', OreDictUnifier.get(OrePrefix.plate, Materials.Titanium), 'R', OreDictUnifier.get(OrePrefix.spring, Materials.Titanium), 'B', ItemList.Battery_RE_HV_Lithium.get(1)});
                    ModHandler.addCraftingRecipe(MetaTool.INSTANCE.getToolWithStats(MetaTool.JACKHAMMER, 1, solidMaterial, Materials.Titanium, new long[]{1200000L, 512L, 3L, -1L}), ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | ModHandler.RecipeBits.BUFFERED, new Object[]{"SXd", "PRP", "MPB", 'X', OreDictUnifier.get(OrePrefix.stickLong, solidMaterial), 'M', ItemList.Electric_Piston_HV.get(1), 'S', OreDictUnifier.get(OrePrefix.screw, Materials.Titanium), 'P', OreDictUnifier.get(OrePrefix.plate, Materials.Titanium), 'R', OreDictUnifier.get(OrePrefix.spring, Materials.Titanium), 'B', ItemList.Battery_RE_HV_Cadmium.get(1)});
                    ModHandler.addCraftingRecipe(MetaTool.INSTANCE.getToolWithStats(MetaTool.JACKHAMMER, 1, solidMaterial, Materials.Titanium, new long[]{800000L, 512L, 3L, -1L}), ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | ModHandler.RecipeBits.BUFFERED, new Object[]{"SXd", "PRP", "MPB", 'X', OreDictUnifier.get(OrePrefix.stickLong, solidMaterial), 'M', ItemList.Electric_Piston_HV.get(1), 'S', OreDictUnifier.get(OrePrefix.screw, Materials.Titanium), 'P', OreDictUnifier.get(OrePrefix.plate, Materials.Titanium), 'R', OreDictUnifier.get(OrePrefix.spring, Materials.Titanium), 'B', ItemList.Battery_RE_HV_Sodium.get(1)});
                    if (aSpecialRecipeReq2)
                        ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.toolHeadDrill, solidMaterial, 1), CommonProxy.tBits, new Object[]{"XSX", "XSX", "ShS", 'X', OreDictUnifier.get(OrePrefix.plate, solidMaterial), 'S', OreDictUnifier.get(OrePrefix.plate, Materials.Steel)});
                    break;
                case toolHeadFile:
                    ModHandler.addShapelessCraftingRecipe(MetaTool.INSTANCE.getToolWithStats(MetaTool.FILE, 1, solidMaterial, solidMaterial.handleMaterial, null), new Object[]{aOreDictName, OreDictUnifier.get(OrePrefix.stick, solidMaterial.handleMaterial)});
                    if ((!solidMaterial.contains(SubTag.NO_SMASHING)) && (!solidMaterial.contains(SubTag.BOUNCY))) {
                        ModHandler.addCraftingRecipe(MetaTool.INSTANCE.getToolWithStats(MetaTool.FILE, 1, solidMaterial, solidMaterial.handleMaterial, null), ModHandler.RecipeBits.MIRRORED | ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | ModHandler.RecipeBits.BUFFERED, new Object[]{"P", "P", "S", 'P', OreDictUnifier.get(OrePrefix.plate, solidMaterial), 'S', OreDictUnifier.get(OrePrefix.stick, solidMaterial.handleMaterial)});
                    }
                    break;
                case toolHeadHoe:
                    ModHandler.addShapelessCraftingRecipe(MetaTool.INSTANCE.getToolWithStats(MetaTool.HOE, 1, solidMaterial, solidMaterial.handleMaterial, null), new Object[]{aOreDictName, OreDictUnifier.get(OrePrefix.stick, solidMaterial.handleMaterial)});
                    if (aSpecialRecipeReq1)
                        ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.toolHeadHoe, solidMaterial, 1), CommonProxy.tBits, new Object[]{"PIh", "f  ", 'P', OreDictUnifier.get(OrePrefix.plate, solidMaterial), 'I', OreDictUnifier.get(OrePrefix.ingot, solidMaterial)});
                    if (!aNoWorking)
                        ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.toolHeadHoe, solidMaterial, 1), CommonProxy.tBits, new Object[]{"GG ", "f  ", "   ", 'G', OreDictUnifier.get(OrePrefix.gem, solidMaterial)});
                    break;
                case toolHeadPickaxe:
                    ModHandler.addShapelessCraftingRecipe(MetaTool.INSTANCE.getToolWithStats(MetaTool.PICKAXE, 1, solidMaterial, solidMaterial.handleMaterial, null), new Object[]{aOreDictName, OreDictUnifier.get(OrePrefix.stick, solidMaterial.handleMaterial)});
                    if (aSpecialRecipeReq1)
                        ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.toolHeadPickaxe, solidMaterial, 1), CommonProxy.tBits, new Object[]{"PII", "f h", 'P', OreDictUnifier.get(OrePrefix.plate, solidMaterial), 'I', OreDictUnifier.get(OrePrefix.ingot, solidMaterial)});
                    if (!aNoWorking)
                        ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.toolHeadPickaxe, solidMaterial, 1), CommonProxy.tBits, new Object[]{"GGG", "f  ", 'G', OreDictUnifier.get(OrePrefix.gem, solidMaterial)});
                    break;
                case toolHeadPlow:
                    ModHandler.addShapelessCraftingRecipe(MetaTool.INSTANCE.getToolWithStats(MetaTool.PLOW, 1, solidMaterial, solidMaterial.handleMaterial, null), new Object[]{aOreDictName, OreDictUnifier.get(OrePrefix.stick, solidMaterial.handleMaterial)});
                    if (aSpecialRecipeReq1)
                        ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.toolHeadPlow, solidMaterial, 1), CommonProxy.tBits, new Object[]{"PP", "PP", "hf", 'P', OreDictUnifier.get(OrePrefix.plate, solidMaterial)});
                    if (!aNoWorking)
                        ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.toolHeadPlow, solidMaterial, 1), CommonProxy.tBits, new Object[]{"GG", "GG", " f", 'G', OreDictUnifier.get(OrePrefix.gem, solidMaterial)});
                    break;
                case toolHeadSaw:
                    ModHandler.addShapelessCraftingRecipe(MetaTool.INSTANCE.getToolWithStats(MetaTool.SAW, 1, solidMaterial, solidMaterial.handleMaterial, null), new Object[]{aOreDictName, OreDictUnifier.get(OrePrefix.stick, solidMaterial.handleMaterial)});
                    if (aSpecialRecipeReq1)
                        ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.toolHeadSaw, solidMaterial, 1), CommonProxy.tBits, new Object[]{"PP ", "fh ", 'P', OreDictUnifier.get(OrePrefix.plate, solidMaterial), 'I', OreDictUnifier.get(OrePrefix.ingot, solidMaterial)});
                    if (!aNoWorking)
                        ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.toolHeadSaw, solidMaterial, 1), CommonProxy.tBits, new Object[]{"GGf", 'G', OreDictUnifier.get(OrePrefix.gem, solidMaterial)});
                    break;
                case toolHeadSense:
                    ModHandler.addShapelessCraftingRecipe(MetaTool.INSTANCE.getToolWithStats(MetaTool.SENSE, 1, solidMaterial, solidMaterial.handleMaterial, null), new Object[]{aOreDictName, OreDictUnifier.get(OrePrefix.stick, solidMaterial.handleMaterial)});
                    if (aSpecialRecipeReq1)
                        ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.toolHeadSense, solidMaterial, 1), CommonProxy.tBits, new Object[]{"PPI", "hf ", 'P', OreDictUnifier.get(OrePrefix.plate, solidMaterial), 'I', OreDictUnifier.get(OrePrefix.ingot, solidMaterial)});
                    if (!aNoWorking)
                        ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.toolHeadSense, solidMaterial, 1), CommonProxy.tBits, new Object[]{"GGG", " f ", "   ", 'G', OreDictUnifier.get(OrePrefix.gem, solidMaterial)});
                    break;
                case toolHeadShovel:
                    ModHandler.addShapelessCraftingRecipe(MetaTool.INSTANCE.getToolWithStats(MetaTool.SHOVEL, 1, solidMaterial, solidMaterial.handleMaterial, null), new Object[]{aOreDictName, OreDictUnifier.get(OrePrefix.stick, solidMaterial.handleMaterial)});
                    if (aSpecialRecipeReq1)
                        ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.toolHeadShovel, solidMaterial, 1), CommonProxy.tBits, new Object[]{"fPh", 'P', OreDictUnifier.get(OrePrefix.plate, solidMaterial), 'I', OreDictUnifier.get(OrePrefix.ingot, solidMaterial)});
                    if (!aNoWorking)
                        ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.toolHeadShovel, solidMaterial, 1), CommonProxy.tBits, new Object[]{"fG", 'G', OreDictUnifier.get(OrePrefix.gem, solidMaterial)});
                    break;
                case toolHeadSword:
                    ModHandler.addShapelessCraftingRecipe(MetaTool.INSTANCE.getToolWithStats(MetaTool.SWORD, 1, solidMaterial, solidMaterial.handleMaterial, null), new Object[]{aOreDictName, OreDictUnifier.get(OrePrefix.stick, solidMaterial.handleMaterial)});
                    if (aSpecialRecipeReq1)
                        ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.toolHeadSword, solidMaterial, 1), CommonProxy.tBits, new Object[]{" P ", "fPh", 'P', OreDictUnifier.get(OrePrefix.plate, solidMaterial), 'I', OreDictUnifier.get(OrePrefix.ingot, solidMaterial)});
                    if (!aNoWorking)
                        ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.toolHeadSword, solidMaterial, 1), CommonProxy.tBits, new Object[]{" G", "fG", 'G', OreDictUnifier.get(OrePrefix.gem, solidMaterial)});
                    break;
                case toolHeadUniversalSpade:
                    ModHandler.addShapelessCraftingRecipe(MetaTool.INSTANCE.getToolWithStats(MetaTool.UNIVERSALSPADE, 1, solidMaterial, solidMaterial, null), new Object[]{aOreDictName, OreDictUnifier.get(OrePrefix.stick, solidMaterial), OrePrefix.screw.get(solidMaterial), ToolDictNames.craftingToolScrewdriver});
                    if (aSpecialRecipeReq2)
                        ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.toolHeadUniversalSpade, solidMaterial, 1), CommonProxy.tBits, new Object[]{"fX", 'X', OreDictUnifier.get(OrePrefix.toolHeadShovel, solidMaterial)});
                    break;
                case toolHeadWrench:
                    ModHandler.addCraftingRecipe(MetaTool.INSTANCE.getToolWithStats(MetaTool.WRENCH_LV, 1, solidMaterial, Materials.StainlessSteel, new long[]{100000L, 32L, 1L, -1L}), ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | ModHandler.RecipeBits.BUFFERED, new Object[]{"SXd", "GMG", "PBP", 'X', aOreDictName, 'M', ItemList.Electric_Motor_LV.get(1), 'S', OreDictUnifier.get(OrePrefix.screw, Materials.StainlessSteel), 'P', OreDictUnifier.get(OrePrefix.plate, Materials.StainlessSteel), 'G', OreDictUnifier.get(OrePrefix.gearGtSmall, Materials.StainlessSteel), 'B', ItemList.Battery_RE_LV_Lithium.get(1)});
                    ModHandler.addCraftingRecipe(MetaTool.INSTANCE.getToolWithStats(MetaTool.WRENCH_MV, 1, solidMaterial, Materials.Titanium, new long[]{400000L, 128L, 2L, -1L}), ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | ModHandler.RecipeBits.BUFFERED, new Object[]{"SXd", "GMG", "PBP", 'X', aOreDictName, 'M', ItemList.Electric_Motor_MV.get(1), 'S', OreDictUnifier.get(OrePrefix.screw, Materials.Titanium), 'P', OreDictUnifier.get(OrePrefix.plate, Materials.Titanium), 'G', OreDictUnifier.get(OrePrefix.gearGtSmall, Materials.Titanium), 'B', ItemList.Battery_RE_MV_Lithium.get(1)});
                    ModHandler.addCraftingRecipe(MetaTool.INSTANCE.getToolWithStats(MetaTool.WRENCH_HV, 1, solidMaterial, Materials.TungstenSteel, new long[]{1600000L, 512L, 3L, -1L}), ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | ModHandler.RecipeBits.BUFFERED, new Object[]{"SXd", "GMG", "PBP", 'X', aOreDictName, 'M', ItemList.Electric_Motor_HV.get(1), 'S', OreDictUnifier.get(OrePrefix.screw, Materials.TungstenSteel), 'P', OreDictUnifier.get(OrePrefix.plate, Materials.TungstenSteel), 'G', OreDictUnifier.get(OrePrefix.gearGtSmall, Materials.TungstenSteel), 'B', ItemList.Battery_RE_HV_Lithium.get(1)});
                    ModHandler.addCraftingRecipe(MetaTool.INSTANCE.getToolWithStats(MetaTool.WRENCH_LV, 1, solidMaterial, Materials.StainlessSteel, new long[]{75000L, 32L, 1L, -1L}), ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | ModHandler.RecipeBits.BUFFERED, new Object[]{"SXd", "GMG", "PBP", 'X', aOreDictName, 'M', ItemList.Electric_Motor_LV.get(1), 'S', OreDictUnifier.get(OrePrefix.screw, Materials.StainlessSteel), 'P', OreDictUnifier.get(OrePrefix.plate, Materials.StainlessSteel), 'G', OreDictUnifier.get(OrePrefix.gearGtSmall, Materials.StainlessSteel), 'B', ItemList.Battery_RE_LV_Cadmium.get(1)});
                    ModHandler.addCraftingRecipe(MetaTool.INSTANCE.getToolWithStats(MetaTool.WRENCH_MV, 1, solidMaterial, Materials.Titanium, new long[]{300000L, 128L, 2L, -1L}), ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | ModHandler.RecipeBits.BUFFERED, new Object[]{"SXd", "GMG", "PBP", 'X', aOreDictName, 'M', ItemList.Electric_Motor_MV.get(1), 'S', OreDictUnifier.get(OrePrefix.screw, Materials.Titanium), 'P', OreDictUnifier.get(OrePrefix.plate, Materials.Titanium), 'G', OreDictUnifier.get(OrePrefix.gearGtSmall, Materials.Titanium), 'B', ItemList.Battery_RE_MV_Cadmium.get(1)});
                    ModHandler.addCraftingRecipe(MetaTool.INSTANCE.getToolWithStats(MetaTool.WRENCH_HV, 1, solidMaterial, Materials.TungstenSteel, new long[]{1200000L, 512L, 3L, -1L}), ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | ModHandler.RecipeBits.BUFFERED, new Object[]{"SXd", "GMG", "PBP", 'X', aOreDictName, 'M', ItemList.Electric_Motor_HV.get(1), 'S', OreDictUnifier.get(OrePrefix.screw, Materials.TungstenSteel), 'P', OreDictUnifier.get(OrePrefix.plate, Materials.TungstenSteel), 'G', OreDictUnifier.get(OrePrefix.gearGtSmall, Materials.TungstenSteel), 'B', ItemList.Battery_RE_HV_Cadmium.get(1)});
                    ModHandler.addCraftingRecipe(MetaTool.INSTANCE.getToolWithStats(MetaTool.WRENCH_LV, 1, solidMaterial, Materials.StainlessSteel, new long[]{50000L, 32L, 1L, -1L}), ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | ModHandler.RecipeBits.BUFFERED, new Object[]{"SXd", "GMG", "PBP", 'X', aOreDictName, 'M', ItemList.Electric_Motor_LV.get(1), 'S', OreDictUnifier.get(OrePrefix.screw, Materials.StainlessSteel), 'P', OreDictUnifier.get(OrePrefix.plate, Materials.StainlessSteel), 'G', OreDictUnifier.get(OrePrefix.gearGtSmall, Materials.StainlessSteel), 'B', ItemList.Battery_RE_LV_Sodium.get(1)});
                    ModHandler.addCraftingRecipe(MetaTool.INSTANCE.getToolWithStats(MetaTool.WRENCH_MV, 1, solidMaterial, Materials.Titanium, new long[]{200000L, 128L, 2L, -1L}), ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | ModHandler.RecipeBits.BUFFERED, new Object[]{"SXd", "GMG", "PBP", 'X', aOreDictName, 'M', ItemList.Electric_Motor_MV.get(1), 'S', OreDictUnifier.get(OrePrefix.screw, Materials.Titanium), 'P', OreDictUnifier.get(OrePrefix.plate, Materials.Titanium), 'G', OreDictUnifier.get(OrePrefix.gearGtSmall, Materials.Titanium), 'B', ItemList.Battery_RE_MV_Sodium.get(1)});
                    ModHandler.addCraftingRecipe(MetaTool.INSTANCE.getToolWithStats(MetaTool.WRENCH_HV, 1, solidMaterial, Materials.TungstenSteel, new long[]{800000L, 512L, 3L, -1L}), ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | ModHandler.RecipeBits.BUFFERED, new Object[]{"SXd", "GMG", "PBP", 'X', aOreDictName, 'M', ItemList.Electric_Motor_HV.get(1), 'S', OreDictUnifier.get(OrePrefix.screw, Materials.TungstenSteel), 'P', OreDictUnifier.get(OrePrefix.plate, Materials.Titanium), 'G', OreDictUnifier.get(OrePrefix.gearGtSmall, Materials.TungstenSteel), 'B', ItemList.Battery_RE_HV_Sodium.get(1)});
                    ModHandler.addCraftingRecipe(MetaTool.INSTANCE.getToolWithStats(MetaTool.SCREWDRIVER_LV, 1, solidMaterial, Materials.StainlessSteel, new long[]{100000L, 32L, 1L, -1L}), ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | ModHandler.RecipeBits.BUFFERED, new Object[]{"PdX", "MGS", "GBP", 'X', OreDictUnifier.get(OrePrefix.stickLong, solidMaterial), 'M', ItemList.Electric_Motor_LV.get(1), 'S', OreDictUnifier.get(OrePrefix.screw, Materials.StainlessSteel), 'P', OreDictUnifier.get(OrePrefix.plate, Materials.StainlessSteel), 'G', OreDictUnifier.get(OrePrefix.gearGtSmall, Materials.StainlessSteel), 'B', ItemList.Battery_RE_LV_Lithium.get(1)});
                    ModHandler.addCraftingRecipe(MetaTool.INSTANCE.getToolWithStats(MetaTool.SCREWDRIVER_LV, 1, solidMaterial, Materials.StainlessSteel, new long[]{75000L, 32L, 1L, -1L}), ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | ModHandler.RecipeBits.BUFFERED, new Object[]{"PdX", "MGS", "GBP", 'X', OreDictUnifier.get(OrePrefix.stickLong, solidMaterial), 'M', ItemList.Electric_Motor_LV.get(1), 'S', OreDictUnifier.get(OrePrefix.screw, Materials.StainlessSteel), 'P', OreDictUnifier.get(OrePrefix.plate, Materials.StainlessSteel), 'G', OreDictUnifier.get(OrePrefix.gearGtSmall, Materials.StainlessSteel), 'B', ItemList.Battery_RE_LV_Cadmium.get(1)});
                    ModHandler.addCraftingRecipe(MetaTool.INSTANCE.getToolWithStats(MetaTool.SCREWDRIVER_LV, 1, solidMaterial, Materials.StainlessSteel, new long[]{50000L, 32L, 1L, -1L}), ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | ModHandler.RecipeBits.BUFFERED, new Object[]{"PdX", "MGS", "GBP", 'X', OreDictUnifier.get(OrePrefix.stickLong, solidMaterial), 'M', ItemList.Electric_Motor_LV.get(1), 'S', OreDictUnifier.get(OrePrefix.screw, Materials.StainlessSteel), 'P', OreDictUnifier.get(OrePrefix.plate, Materials.StainlessSteel), 'G', OreDictUnifier.get(OrePrefix.gearGtSmall, Materials.StainlessSteel), 'B', ItemList.Battery_RE_LV_Sodium.get(1)});
                    if (aSpecialRecipeReq2)
                        ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.toolHeadWrench, solidMaterial, 1), CommonProxy.tBits, new Object[]{"hXW", "XRX", "WXd", 'X', OreDictUnifier.get(OrePrefix.plate, solidMaterial), 'S', OreDictUnifier.get(OrePrefix.plate, Materials.Steel), 'R', OreDictUnifier.get(OrePrefix.ring, Materials.Steel), 'W', OreDictUnifier.get(OrePrefix.screw, Materials.Steel)});
                    break;
                case toolHeadHammer:
                    if ((solidMaterial != Materials.Stone) && (solidMaterial != Materials.Flint)) {
                        ModHandler.addShapelessCraftingRecipe(MetaTool.INSTANCE.getToolWithStats((solidMaterial.contains(SubTag.BOUNCY)) || (solidMaterial.contains(SubTag.WOOD)) ? MetaTool.SOFTHAMMER : MetaTool.HARDHAMMER, 1, solidMaterial, solidMaterial.handleMaterial, null), ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | ModHandler.RecipeBits.BUFFERED, new Object[]{aOreDictName, OreDictUnifier.get(OrePrefix.stick, solidMaterial.handleMaterial)});
                        ModHandler.addCraftingRecipe(MetaTool.INSTANCE.getToolWithStats((solidMaterial.contains(SubTag.BOUNCY)) || (solidMaterial.contains(SubTag.WOOD)) ? MetaTool.SOFTHAMMER : MetaTool.HARDHAMMER, 1, solidMaterial, solidMaterial.handleMaterial, null), ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | ModHandler.RecipeBits.BUFFERED, new Object[]{"XX ", "XXS", "XX ", 'X', solidMaterial == Materials.Wood ? OreDictUnifier.get(OrePrefix.plank, Materials.Wood) : OreDictUnifier.get(OrePrefix.ingot, solidMaterial), 'S', OreDictUnifier.get(OrePrefix.stick, solidMaterial.handleMaterial)});
                        ModHandler.addCraftingRecipe(MetaTool.INSTANCE.getToolWithStats((solidMaterial.contains(SubTag.BOUNCY)) || (solidMaterial.contains(SubTag.WOOD)) ? MetaTool.SOFTHAMMER : MetaTool.HARDHAMMER, 1, solidMaterial, solidMaterial.handleMaterial, null), ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | ModHandler.RecipeBits.BUFFERED, new Object[]{"XX ", "XXS", "XX ", 'X', solidMaterial == Materials.Wood ? OreDictUnifier.get(OrePrefix.plank, Materials.Wood) : OreDictUnifier.get(OrePrefix.gem, solidMaterial), 'S', OreDictUnifier.get(OrePrefix.stick, solidMaterial.handleMaterial)});
                    }
                    if (uEntry.orePrefix == OrePrefix.toolHeadHammer)
                        if (aSpecialRecipeReq1)
                            ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.toolHeadHammer, solidMaterial, 1), CommonProxy.tBits, new Object[]{"II ", "IIh", "II ", 'P', OreDictUnifier.get(OrePrefix.plate, solidMaterial), 'I', OreDictUnifier.get(OrePrefix.ingot, solidMaterial)});
                    break;
                case turbineBlade:
                    RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                            .inputs(OreDictUnifier.get(OrePrefix.turbineBlade, solidMaterial, 4), OreDictUnifier.get(OrePrefix.stickLong, Materials.Magnalium))
                            .outputs(MetaTool.INSTANCE.getToolWithStats(170, 1, solidMaterial, solidMaterial, null))
                            .duration(160)
                            .EUt(100)
                            .buildAndRegister();
                    RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                            .inputs(OreDictUnifier.get(OrePrefix.turbineBlade, solidMaterial, 8), OreDictUnifier.get(OrePrefix.stickLong, Materials.Titanium))
                            .outputs(MetaTool.INSTANCE.getToolWithStats(172, 1, solidMaterial, solidMaterial, null))
                            .duration(320)
                            .EUt(400)
                            .buildAndRegister();
                    RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                            .inputs(OreDictUnifier.get(OrePrefix.turbineBlade, solidMaterial, 12), OreDictUnifier.get(OrePrefix.stickLong, Materials.TungstenSteel))
                            .outputs(MetaTool.INSTANCE.getToolWithStats(174, 1, solidMaterial, solidMaterial, null))
                            .duration(640)
                            .EUt(1600)
                            .buildAndRegister();
                    RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                            .inputs(OreDictUnifier.get(OrePrefix.turbineBlade, solidMaterial, 16), OreDictUnifier.get(OrePrefix.stickLong, Materials.Americium))
                            .outputs(MetaTool.INSTANCE.getToolWithStats(176, 1, solidMaterial, solidMaterial, null))
                            .duration(1280)
                            .EUt(6400)
                            .buildAndRegister();
                    if (aSpecialRecipeReq2) {
                        ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.turbineBlade, solidMaterial, 1), CommonProxy.tBits, new Object[]{"fPd", "SPS", " P ", 'P', solidMaterial == Materials.Wood ? OreDictUnifier.get(OrePrefix.plank, solidMaterial) : OreDictUnifier.get(OrePrefix.ingotDouble, solidMaterial), 'R', OreDictUnifier.get(OrePrefix.ring, solidMaterial), 'S', OreDictUnifier.get(OrePrefix.screw, solidMaterial)});
                    }
                    break;
            }
        }
    }
}
