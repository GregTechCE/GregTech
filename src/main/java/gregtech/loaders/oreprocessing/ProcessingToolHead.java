package gregtech.loaders.oreprocessing;

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
import gregtech.common.items.MetaItems;

import static gregtech.api.unification.material.type.DustMaterial.MatFlags.NO_SMASHING;

public class ProcessingToolHead implements IOreRegistrationHandler {

    private ProcessingToolHead() {}

    public static void register() {
        ProcessingToolHead processing = new ProcessingToolHead();
        OrePrefix.toolHeadAxe.addProcessingHandler(processing);
        OrePrefix.toolHeadBuzzSaw.addProcessingHandler(processing);
        OrePrefix.toolHeadChainsaw.addProcessingHandler(processing);
        OrePrefix.toolHeadDrill.addProcessingHandler(processing);
        OrePrefix.toolHeadFile.addProcessingHandler(processing);
        OrePrefix.toolHeadHoe.addProcessingHandler(processing);
        OrePrefix.toolHeadPickaxe.addProcessingHandler(processing);
        OrePrefix.toolHeadPlow.addProcessingHandler(processing);
        OrePrefix.toolHeadSaw.addProcessingHandler(processing);
        OrePrefix.toolHeadSense.addProcessingHandler(processing);
        OrePrefix.toolHeadShovel.addProcessingHandler(processing);
        OrePrefix.toolHeadSword.addProcessingHandler(processing);
        OrePrefix.toolHeadUniversalSpade.addProcessingHandler(processing);
        OrePrefix.toolHeadWrench.addProcessingHandler(processing);
        OrePrefix.toolHeadHammer.addProcessingHandler(processing);
        OrePrefix.turbineBlade.addProcessingHandler(processing);
    }

    @Override
    public void registerOre(UnificationEntry entry, String modName, SimpleItemStack simpleStack) {
        boolean smashing = !entry.material.hasFlag(NO_SMASHING);
        boolean working = !entry.material.hasFlag(DustMaterial.MatFlags.NO_WORKING);

        if (entry.material instanceof SolidMaterial) {
            SolidMaterial solidMaterial = (SolidMaterial) entry.material;
            switch (entry.orePrefix) {
                case toolHeadAxe:
                    ModHandler.addShapelessRecipe(MetaItems.AXE.getStackForm(solidMaterial, solidMaterial.handleMaterial),
                        entry,
                        OreDictUnifier.get(OrePrefix.stick, solidMaterial.handleMaterial));

                    if (smashing)
                        ModHandler.addShapedRecipe(OreDictUnifier.get(OrePrefix.toolHeadAxe, solidMaterial),
                            "PIh",
                            "P  ",
                            "f  ",
                            'P', OreDictUnifier.get(OrePrefix.plate, solidMaterial),
                            'I', OreDictUnifier.get(OrePrefix.ingot, solidMaterial));
                    if (working)
                        ModHandler.addShapedRecipe(OreDictUnifier.get(OrePrefix.toolHeadAxe, solidMaterial),
                            "GG ",
                            "G  ",
                            "f  ",
                            'G', OreDictUnifier.get(OrePrefix.gem, solidMaterial));
                    break;
                case toolHeadBuzzSaw:
                    ModHandler.addShapedRecipe(MetaItems.BUZZSAW.getStackForm(solidMaterial, Materials.StainlessSteel), // new long[]{100000L, 32L, 1L, -1L}),
                        "PBM",
                        "dXG",
                        "SGP",
                        'X', entry,
                        'M', MetaItems.ELECTRIC_MOTOR_LV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.StainlessSteel),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.StainlessSteel),
                        'G', OreDictUnifier.get(OrePrefix.gearSmall, Materials.StainlessSteel),
                        'B', MetaItems.BATTERY_RE_LV_LITHIUM.getStackForm());

                    ModHandler.addShapedRecipe(MetaItems.BUZZSAW.getStackForm(solidMaterial, Materials.StainlessSteel), // new long[]{75000L, 32L, 1L, -1L}),
                        "PBM",
                        "dXG",
                        "SGP",
                        'X', entry,
                        'M', MetaItems.ELECTRIC_MOTOR_LV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.StainlessSteel),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.StainlessSteel),
                        'G', OreDictUnifier.get(OrePrefix.gearSmall, Materials.StainlessSteel),
                        'B', MetaItems.BATTERY_RE_LV_CADMIUM.getStackForm());

                    ModHandler.addShapedRecipe(MetaItems.BUZZSAW.getStackForm(solidMaterial, Materials.StainlessSteel), // new long[]{50000L, 32L, 1L, -1L}),
                        "PBM",
                        "dXG",
                        "SGP",
                        'X', entry,
                        'M', MetaItems.ELECTRIC_MOTOR_LV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.StainlessSteel),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.StainlessSteel),
                        'G', OreDictUnifier.get(OrePrefix.gearSmall, Materials.StainlessSteel),
                        'B', MetaItems.BATTERY_RE_LV_SODIUM.getStackForm());

                    if (working)
                        ModHandler.addShapedRecipe(OreDictUnifier.get(OrePrefix.toolHeadBuzzSaw, solidMaterial),
                            "wXh",
                            "X X",
                            "fXx",
                            'X', OreDictUnifier.get(OrePrefix.plate, solidMaterial));
                    break;
                case toolHeadChainsaw:
                    ModHandler.addShapedRecipe(MetaItems.CHAINSAW_LV.getStackForm(solidMaterial, Materials.StainlessSteel), // new long[]{100000L, 32L, 1L, -1L}),
                        "SXd",
                        "GMG",
                        "PBP",
                        'X', entry,
                        'M', MetaItems.ELECTRIC_MOTOR_LV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.StainlessSteel),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.StainlessSteel),
                        'G', OreDictUnifier.get(OrePrefix.gearSmall, Materials.StainlessSteel),
                        'B', MetaItems.BATTERY_RE_LV_LITHIUM.getStackForm());

                    ModHandler.addShapedRecipe(MetaItems.CHAINSAW_MV.getStackForm(solidMaterial, Materials.Titanium), // new long[]{400000L, 128L, 2L, -1L}),
                        "SXd",
                        "GMG",
                        "PBP",
                        'X', entry,
                        'M', MetaItems.ELECTRIC_MOTOR_MV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.Titanium),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.Titanium),
                        'G', OreDictUnifier.get(OrePrefix.plate, Materials.Titanium),
                        'B', MetaItems.BATTERY_RE_MV_LITHIUM.getStackForm());

                    ModHandler.addShapedRecipe(MetaItems.CHAINSAW_HV.getStackForm(solidMaterial, Materials.TungstenSteel), // new long[]{1600000L, 512L, 3L, -1L}),
                        "SXd",
                        "GMG",
                        "PBP",
                        'X', entry,
                        'M', MetaItems.ELECTRIC_MOTOR_HV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.TungstenSteel),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.TungstenSteel),
                        'G', OreDictUnifier.get(OrePrefix.gearSmall, Materials.TungstenSteel),
                        'B', MetaItems.BATTERY_RE_HV_LITHIUM.getStackForm());

                    ModHandler.addShapedRecipe(MetaItems.CHAINSAW_LV.getStackForm(solidMaterial, Materials.StainlessSteel), // new long[]{75000L, 32L, 1L, -1L}),
                        "SXd",
                        "GMG",
                        "PBP",
                        'X', entry,
                        'M', MetaItems.ELECTRIC_MOTOR_LV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.StainlessSteel),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.StainlessSteel),
                        'G', OreDictUnifier.get(OrePrefix.gearSmall, Materials.StainlessSteel),
                        'B', MetaItems.BATTERY_RE_LV_CADMIUM.getStackForm());

                    ModHandler.addShapedRecipe(MetaItems.CHAINSAW_MV.getStackForm(solidMaterial, Materials.Titanium), // new long[]{300000L, 128L, 2L, -1L}),
                        "SXd",
                        "GMG",
                        "PBP",
                        'X', entry,
                        'M', MetaItems.ELECTRIC_MOTOR_MV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.Titanium),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.Titanium),
                        'G', OreDictUnifier.get(OrePrefix.plate, Materials.Titanium),
                        'B', MetaItems.BATTERY_RE_MV_CADMIUM.getStackForm());

                    ModHandler.addShapedRecipe(MetaItems.CHAINSAW_HV.getStackForm(solidMaterial, Materials.TungstenSteel), // new long[]{1200000L, 512L, 3L, -1L}),
                        "SXd",
                        "GMG",
                        "PBP",
                        'X', entry,
                        'M', MetaItems.ELECTRIC_MOTOR_HV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.TungstenSteel),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.TungstenSteel),
                        'G', OreDictUnifier.get(OrePrefix.gearSmall, Materials.TungstenSteel),
                        'B', MetaItems.BATTERY_RE_HV_CADMIUM.getStackForm());

                    ModHandler.addShapedRecipe(MetaItems.CHAINSAW_LV.getStackForm(solidMaterial, Materials.StainlessSteel), // new long[]{50000L, 32L, 1L, -1L}),
                        "SXd",
                        "GMG",
                        "PBP",
                        'X', entry,
                        'M', MetaItems.ELECTRIC_MOTOR_LV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.StainlessSteel),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.StainlessSteel),
                        'G', OreDictUnifier.get(OrePrefix.gearSmall, Materials.StainlessSteel),
                        'B', MetaItems.BATTERY_RE_LV_SODIUM.getStackForm());

                    ModHandler.addShapedRecipe(MetaItems.CHAINSAW_MV.getStackForm(solidMaterial, Materials.Titanium), // new long[]{200000L, 128L, 2L, -1L}),
                        "SXd",
                        "GMG",
                        "PBP",
                        'X', entry,
                        'M', MetaItems.ELECTRIC_MOTOR_MV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.Titanium),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.Titanium),
                        'G', OreDictUnifier.get(OrePrefix.plate, Materials.Titanium),
                        'B', MetaItems.BATTERY_RE_MV_SODIUM.getStackForm());

                    ModHandler.addShapedRecipe(MetaItems.CHAINSAW_HV.getStackForm(solidMaterial, Materials.TungstenSteel), // new long[]{800000L, 512L, 3L, -1L}),
                        "SXd",
                        "GMG",
                        "PBP",
                        'X', entry,
                        'M', MetaItems.ELECTRIC_MOTOR_HV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.TungstenSteel),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.TungstenSteel),
                        'G', OreDictUnifier.get(OrePrefix.gearSmall, Materials.TungstenSteel),
                        'B', MetaItems.BATTERY_RE_HV_SODIUM.getStackForm());

                    if (working)
                        ModHandler.addShapedRecipe(OreDictUnifier.get(OrePrefix.toolHeadChainsaw, solidMaterial),
                            "SRS",
                            "XhX",
                            "SRS",
                            'X', OreDictUnifier.get(OrePrefix.plate, solidMaterial),
                            'S', OreDictUnifier.get(OrePrefix.plate, Materials.Steel),
                            'R', OreDictUnifier.get(OrePrefix.ring, Materials.Steel));
                    break;
                case toolHeadDrill:
                    ModHandler.addShapedRecipe(MetaItems.DRILL_LV.getStackForm(solidMaterial, Materials.StainlessSteel), // new long[]{100000L, 32L, 1L, -1L}),
                        "SXd",
                        "GMG",
                        "PBP",
                        'X', entry,
                        'M', MetaItems.ELECTRIC_MOTOR_LV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.StainlessSteel),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.StainlessSteel),
                        'G', OreDictUnifier.get(OrePrefix.gearSmall, Materials.StainlessSteel),
                        'B', MetaItems.BATTERY_RE_LV_LITHIUM.getStackForm());

                    ModHandler.addShapedRecipe(MetaItems.DRILL_LV.getStackForm(solidMaterial, Materials.StainlessSteel), // new long[]{75000L, 32L, 1L, -1L}),
                        "SXd",
                        "GMG",
                        "PBP",
                        'X', entry,
                        'M', MetaItems.ELECTRIC_MOTOR_LV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.StainlessSteel),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.StainlessSteel),
                        'G', OreDictUnifier.get(OrePrefix.gearSmall, Materials.StainlessSteel),
                        'B', MetaItems.BATTERY_RE_LV_CADMIUM.getStackForm());

                    ModHandler.addShapedRecipe(MetaItems.DRILL_LV.getStackForm(solidMaterial, Materials.StainlessSteel), // new long[]{50000L, 32L, 1L, -1L}),
                        "SXd",
                        "GMG",
                        "PBP",
                        'X', entry,
                        'M', MetaItems.ELECTRIC_MOTOR_LV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.StainlessSteel),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.StainlessSteel),
                        'G', OreDictUnifier.get(OrePrefix.gearSmall, Materials.StainlessSteel),
                        'B', MetaItems.BATTERY_RE_LV_SODIUM.getStackForm());

                    ModHandler.addShapedRecipe(MetaItems.DRILL_MV.getStackForm(solidMaterial, Materials.Titanium), // new long[]{400000L, 128L, 2L, -1L}),
                        "SXd",
                        "GMG",
                        "PBP",
                        'X', entry,
                        'M', MetaItems.ELECTRIC_MOTOR_MV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.Titanium),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.Titanium),
                        'G', OreDictUnifier.get(OrePrefix.plate, Materials.Titanium),
                        'B', MetaItems.BATTERY_RE_MV_LITHIUM.getStackForm());

                    ModHandler.addShapedRecipe(MetaItems.DRILL_MV.getStackForm(solidMaterial, Materials.Titanium), // new long[]{300000L, 128L, 2L, -1L}),
                        "SXd",
                        "GMG",
                        "PBP",
                        'X', entry,
                        'M', MetaItems.ELECTRIC_MOTOR_MV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.Titanium),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.Titanium),
                        'G', OreDictUnifier.get(OrePrefix.plate, Materials.Titanium),
                        'B', MetaItems.BATTERY_RE_MV_CADMIUM.getStackForm());

                    ModHandler.addShapedRecipe(MetaItems.DRILL_MV.getStackForm(solidMaterial, Materials.Titanium), // new long[]{200000L, 128L, 2L, -1L}),
                        "SXd",
                        "GMG",
                        "PBP",
                        'X', entry,
                        'M', MetaItems.ELECTRIC_MOTOR_MV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.Titanium),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.Titanium),
                        'G', OreDictUnifier.get(OrePrefix.plate, Materials.Titanium),
                        'B', MetaItems.BATTERY_RE_MV_SODIUM.getStackForm());

                    ModHandler.addShapedRecipe(MetaItems.DRILL_HV.getStackForm(solidMaterial, Materials.TungstenSteel), // new long[]{1600000L, 512L, 3L, -1L}),
                        "SXd",
                        "GMG",
                        "PBP",
                        'X', entry,
                        'M', MetaItems.ELECTRIC_MOTOR_HV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.TungstenSteel),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.TungstenSteel),
                        'G', OreDictUnifier.get(OrePrefix.gearSmall, Materials.TungstenSteel),
                        'B', MetaItems.BATTERY_RE_HV_LITHIUM.getStackForm());

                    ModHandler.addShapedRecipe(MetaItems.DRILL_HV.getStackForm(solidMaterial, Materials.TungstenSteel), // new long[]{1200000L, 512L, 3L, -1L}),
                        "SXd",
                        "GMG",
                        "PBP",
                        'X', entry,
                        'M', MetaItems.ELECTRIC_MOTOR_HV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.TungstenSteel),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.TungstenSteel),
                        'G', OreDictUnifier.get(OrePrefix.gearSmall, Materials.TungstenSteel),
                        'B', MetaItems.BATTERY_RE_HV_CADMIUM.getStackForm());
                    ModHandler.addShapedRecipe(MetaItems.DRILL_HV.getStackForm(solidMaterial, Materials.TungstenSteel), // new long[]{800000L, 512L, 3L, -1L}),
                        "SXd",
                        "GMG",
                        "PBP",
                        'X', entry,
                        'M', MetaItems.ELECTRIC_MOTOR_HV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.TungstenSteel),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.TungstenSteel),
                        'G', OreDictUnifier.get(OrePrefix.gearSmall, Materials.TungstenSteel),
                        'B', MetaItems.BATTERY_RE_HV_SODIUM.getStackForm());

                    ModHandler.addShapedRecipe(MetaItems.JACKHAMMER.getStackForm(solidMaterial, Materials.Titanium), // new long[]{1600000L, 512L, 3L, -1L}),
                        "SXd",
                        "PRP",
                        "MPB",
                        'X', OreDictUnifier.get(OrePrefix.stickLong, solidMaterial),
                        'M', MetaItems.ELECTRIC_PISTON_HV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.Titanium),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.Titanium),
                        'R', OreDictUnifier.get(OrePrefix.spring, Materials.Titanium),
                        'B', MetaItems.BATTERY_RE_HV_LITHIUM.getStackForm());

                    ModHandler.addShapedRecipe(MetaItems.JACKHAMMER.getStackForm(solidMaterial, Materials.Titanium), // new long[]{1200000L, 512L, 3L, -1L}),
                        "SXd",
                        "PRP",
                        "MPB",
                        'X', OreDictUnifier.get(OrePrefix.stickLong, solidMaterial),
                        'M', MetaItems.ELECTRIC_PISTON_HV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.Titanium),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.Titanium),
                        'R', OreDictUnifier.get(OrePrefix.spring, Materials.Titanium),
                        'B', MetaItems.BATTERY_RE_HV_CADMIUM.getStackForm());

                    ModHandler.addShapedRecipe(MetaItems.JACKHAMMER.getStackForm(solidMaterial, Materials.Titanium), // new long[]{800000L, 512L, 3L, -1L}),
                        "SXd",
                        "PRP",
                        "MPB",
                        'X', OreDictUnifier.get(OrePrefix.stickLong, solidMaterial),
                        'M', MetaItems.ELECTRIC_PISTON_HV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.Titanium),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.Titanium),
                        'R', OreDictUnifier.get(OrePrefix.spring, Materials.Titanium),
                        'B', MetaItems.BATTERY_RE_HV_SODIUM.getStackForm());

                    if (working)
                        ModHandler.addShapedRecipe(OreDictUnifier.get(OrePrefix.toolHeadDrill, solidMaterial),
                            "XSX",
                            "XSX",
                            "ShS",
                            'X', OreDictUnifier.get(OrePrefix.plate, solidMaterial),
                            'S', OreDictUnifier.get(OrePrefix.plate, Materials.Steel));
                    break;
                case toolHeadFile:
                    ModHandler.addShapelessRecipe(MetaItems.FILE.getStackForm(solidMaterial, solidMaterial.handleMaterial), entry, OreDictUnifier.get(OrePrefix.stick, solidMaterial.handleMaterial));
                    if (!smashing) {
                        ModHandler.addMirroredShapedRecipe(MetaItems.FILE.getStackForm(solidMaterial, solidMaterial.handleMaterial),
                            "P",
                            "P",
                            "S",
                            'P', OreDictUnifier.get(OrePrefix.plate, solidMaterial),
                            'S', OreDictUnifier.get(OrePrefix.stick, solidMaterial.handleMaterial));
                    }
                    break;
                case toolHeadHoe:
                    ModHandler.addShapelessRecipe(MetaItems.HOE.getStackForm(solidMaterial, solidMaterial.handleMaterial), entry, OreDictUnifier.get(OrePrefix.stick, solidMaterial.handleMaterial));
                    if (smashing)
                        ModHandler.addShapedRecipe(OreDictUnifier.get(OrePrefix.toolHeadHoe, solidMaterial),
                            "PIh",
                            "f  ",
                            'P', OreDictUnifier.get(OrePrefix.plate, solidMaterial),
                            'I', OreDictUnifier.get(OrePrefix.ingot, solidMaterial));
                    if (working)
                        ModHandler.addShapedRecipe(OreDictUnifier.get(OrePrefix.toolHeadHoe, solidMaterial),
                            "GG",
                            "f ",
                            'G', OreDictUnifier.get(OrePrefix.gem, solidMaterial));
                    break;
                case toolHeadPickaxe:
                    ModHandler.addShapelessRecipe(MetaItems.PICKAXE.getStackForm(solidMaterial, solidMaterial.handleMaterial),
                        entry,
                        OreDictUnifier.get(OrePrefix.stick, solidMaterial.handleMaterial));

                    if (smashing)
                        ModHandler.addShapedRecipe(OreDictUnifier.get(OrePrefix.toolHeadPickaxe, solidMaterial),
                            "PII",
                            "f h",
                            'P', OreDictUnifier.get(OrePrefix.plate, solidMaterial),
                            'I', OreDictUnifier.get(OrePrefix.ingot, solidMaterial));
                    if (working)
                        ModHandler.addShapedRecipe(OreDictUnifier.get(OrePrefix.toolHeadPickaxe, solidMaterial),
                            "GGG",
                            "f  ",
                            'G', OreDictUnifier.get(OrePrefix.gem, solidMaterial));
                    break;
                case toolHeadPlow:
                    ModHandler.addShapelessRecipe(MetaItems.PLOW.getStackForm(solidMaterial, solidMaterial.handleMaterial),
                        entry,
                        OreDictUnifier.get(OrePrefix.stick, solidMaterial.handleMaterial));

                    if (smashing)
                        ModHandler.addShapedRecipe(OreDictUnifier.get(OrePrefix.toolHeadPlow, solidMaterial),
                            "PP",
                            "PP",
                            "hf",
                            'P', OreDictUnifier.get(OrePrefix.plate, solidMaterial));
                    if (working)
                        ModHandler.addShapedRecipe(OreDictUnifier.get(OrePrefix.toolHeadPlow, solidMaterial),
                            "GG",
                            "GG",
                            " f",
                            'G', OreDictUnifier.get(OrePrefix.gem, solidMaterial));
                    break;
                case toolHeadSaw:
                    ModHandler.addShapelessRecipe(MetaItems.SAW.getStackForm(solidMaterial, solidMaterial.handleMaterial),
                        entry,
                        OreDictUnifier.get(OrePrefix.stick, solidMaterial.handleMaterial));

                    if (smashing)
                        ModHandler.addShapedRecipe(OreDictUnifier.get(OrePrefix.toolHeadSaw, solidMaterial),
                            "PP ",
                            "fh ",
                            'P', OreDictUnifier.get(OrePrefix.plate, solidMaterial),
                            'I', OreDictUnifier.get(OrePrefix.ingot, solidMaterial));
                    if (working)
                        ModHandler.addShapedRecipe(OreDictUnifier.get(OrePrefix.toolHeadSaw, solidMaterial),
                            "GGf",
                            'G', OreDictUnifier.get(OrePrefix.gem, solidMaterial));
                    break;
                case toolHeadSense:
                    ModHandler.addShapelessRecipe(MetaItems.SENSE.getStackForm(solidMaterial, solidMaterial.handleMaterial),
                        entry,
                        OreDictUnifier.get(OrePrefix.stick, solidMaterial.handleMaterial));

                    if (smashing)
                        ModHandler.addShapedRecipe(OreDictUnifier.get(OrePrefix.toolHeadSense, solidMaterial),
                            "PPI",
                            "hf ",
                            'P', OreDictUnifier.get(OrePrefix.plate, solidMaterial),
                            'I', OreDictUnifier.get(OrePrefix.ingot, solidMaterial));
                    if (working)
                        ModHandler.addShapedRecipe(OreDictUnifier.get(OrePrefix.toolHeadSense, solidMaterial),
                            "GGG",
                            " f ",
                            "   ",
                            'G', OreDictUnifier.get(OrePrefix.gem, solidMaterial));
                    break;
                case toolHeadShovel:
                    ModHandler.addShapelessRecipe(MetaItems.SHOVEL.getStackForm(solidMaterial, solidMaterial.handleMaterial),
                        entry,
                        OreDictUnifier.get(OrePrefix.stick, solidMaterial.handleMaterial));

                    if (smashing)
                        ModHandler.addShapedRecipe(OreDictUnifier.get(OrePrefix.toolHeadShovel, solidMaterial),
                            "fPh",
                            'P', OreDictUnifier.get(OrePrefix.plate, solidMaterial),
                            'I', OreDictUnifier.get(OrePrefix.ingot, solidMaterial));
                    if (working)
                        ModHandler.addShapedRecipe(OreDictUnifier.get(OrePrefix.toolHeadShovel, solidMaterial),
                            "fG",
                            'G', OreDictUnifier.get(OrePrefix.gem, solidMaterial));
                    break;
                case toolHeadSword:
                    ModHandler.addShapelessRecipe(MetaItems.SWORD.getStackForm(solidMaterial, solidMaterial.handleMaterial),
                        entry,
                        OreDictUnifier.get(OrePrefix.stick, solidMaterial.handleMaterial));

                    if (smashing)
                        ModHandler.addShapedRecipe(OreDictUnifier.get(OrePrefix.toolHeadSword, solidMaterial),
                            " P ",
                            "fPh",
                            'P', OreDictUnifier.get(OrePrefix.plate, solidMaterial),
                            'I', OreDictUnifier.get(OrePrefix.ingot, solidMaterial));
                    if (working)
                        ModHandler.addShapedRecipe(OreDictUnifier.get(OrePrefix.toolHeadSword, solidMaterial),
                            " G",
                            "fG",
                            'G', OreDictUnifier.get(OrePrefix.gem, solidMaterial));
                    break;
                case toolHeadUniversalSpade:
                    ModHandler.addShapelessRecipe(MetaItems.UNIVERSALSPADE.getStackForm(solidMaterial, solidMaterial),
                        entry,
                        OreDictUnifier.get(OrePrefix.stick, solidMaterial),
                        new UnificationEntry(OrePrefix.screw, solidMaterial), ToolDictNames.craftingToolScrewdriver);

                    if (working)
                        ModHandler.addShapedRecipe(OreDictUnifier.get(OrePrefix.toolHeadUniversalSpade, solidMaterial),
                            "fX",
                            'X', OreDictUnifier.get(OrePrefix.toolHeadShovel, solidMaterial));
                    break;
                case toolHeadWrench:
                    ModHandler.addShapedRecipe(MetaItems.WRENCH_LV.getStackForm(solidMaterial, Materials.StainlessSteel), // new long[]{100000L, 32L, 1L, -1L}),
                        "SXd",
                        "GMG",
                        "PBP",
                        'X', entry,
                        'M', MetaItems.ELECTRIC_MOTOR_LV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.StainlessSteel),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.StainlessSteel),
                        'G', OreDictUnifier.get(OrePrefix.gearSmall, Materials.StainlessSteel),
                        'B', MetaItems.BATTERY_RE_LV_LITHIUM.getStackForm());
                    ModHandler.addShapedRecipe(MetaItems.WRENCH_MV.getStackForm(solidMaterial, Materials.Titanium), // new long[]{400000L, 128L, 2L, -1L}),
                        "SXd",
                        "GMG",
                        "PBP",
                        'X', entry,
                        'M', MetaItems.ELECTRIC_MOTOR_MV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.Titanium),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.Titanium),
                        'G', OreDictUnifier.get(OrePrefix.gearSmall, Materials.Titanium),
                        'B', MetaItems.BATTERY_RE_MV_LITHIUM.getStackForm());
                    ModHandler.addShapedRecipe(MetaItems.WRENCH_HV.getStackForm(solidMaterial, Materials.TungstenSteel), // new long[]{1600000L, 512L, 3L, -1L}),
                        "SXd",
                        "GMG",
                        "PBP",
                        'X', entry,
                        'M', MetaItems.ELECTRIC_MOTOR_HV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.TungstenSteel),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.TungstenSteel),
                        'G', OreDictUnifier.get(OrePrefix.gearSmall, Materials.TungstenSteel),
                        'B', MetaItems.BATTERY_RE_HV_LITHIUM.getStackForm());
                    ModHandler.addShapedRecipe(MetaItems.WRENCH_LV.getStackForm(solidMaterial, Materials.StainlessSteel), // new long[]{75000L, 32L, 1L, -1L}),
                        "SXd",
                        "GMG",
                        "PBP",
                        'X', entry,
                        'M', MetaItems.ELECTRIC_MOTOR_LV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.StainlessSteel),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.StainlessSteel),
                        'G', OreDictUnifier.get(OrePrefix.gearSmall, Materials.StainlessSteel),
                        'B', MetaItems.BATTERY_RE_LV_CADMIUM.getStackForm());
                    ModHandler.addShapedRecipe(MetaItems.WRENCH_MV.getStackForm(solidMaterial, Materials.Titanium), // new long[]{300000L, 128L, 2L, -1L}),
                        "SXd",
                        "GMG",
                        "PBP",
                        'X', entry,
                        'M', MetaItems.ELECTRIC_MOTOR_MV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.Titanium),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.Titanium),
                        'G', OreDictUnifier.get(OrePrefix.gearSmall, Materials.Titanium),
                        'B', MetaItems.BATTERY_RE_MV_CADMIUM.getStackForm());
                    ModHandler.addShapedRecipe(MetaItems.WRENCH_HV.getStackForm(solidMaterial, Materials.TungstenSteel),  //new long[]{1200000L, 512L, 3L, -1L}),
                        "SXd",
                        "GMG",
                        "PBP",
                        'X', entry,
                        'M', MetaItems.ELECTRIC_MOTOR_HV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.TungstenSteel),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.TungstenSteel),
                        'G', OreDictUnifier.get(OrePrefix.gearSmall, Materials.TungstenSteel),
                        'B', MetaItems.BATTERY_RE_HV_CADMIUM.getStackForm());
                    ModHandler.addShapedRecipe(MetaItems.WRENCH_LV.getStackForm(solidMaterial, Materials.StainlessSteel),  //new long[]{50000L, 32L, 1L, -1L}),
                        "SXd",
                        "GMG",
                        "PBP",
                        'X', entry,
                        'M', MetaItems.ELECTRIC_MOTOR_LV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.StainlessSteel),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.StainlessSteel),
                        'G', OreDictUnifier.get(OrePrefix.gearSmall, Materials.StainlessSteel),
                        'B', MetaItems.BATTERY_RE_LV_SODIUM.getStackForm());
                    ModHandler.addShapedRecipe(MetaItems.WRENCH_MV.getStackForm(solidMaterial, Materials.Titanium),  //new long[]{200000L, 128L, 2L, -1L}),
                        "SXd",
                        "GMG",
                        "PBP",
                        'X', entry,
                        'M', MetaItems.ELECTRIC_MOTOR_MV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.Titanium),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.Titanium),
                        'G', OreDictUnifier.get(OrePrefix.gearSmall, Materials.Titanium),
                        'B', MetaItems.BATTERY_RE_MV_SODIUM.getStackForm());
                    ModHandler.addShapedRecipe(MetaItems.WRENCH_HV.getStackForm(solidMaterial, Materials.TungstenSteel),  //new long[]{800000L, 512L, 3L, -1L}),
                        "SXd",
                        "GMG",
                        "PBP",
                        'X', entry,
                        'M', MetaItems.ELECTRIC_MOTOR_HV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.TungstenSteel),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.Titanium),
                        'G', OreDictUnifier.get(OrePrefix.gearSmall, Materials.TungstenSteel),
                        'B', MetaItems.BATTERY_RE_HV_SODIUM.getStackForm());
                    ModHandler.addShapedRecipe(MetaItems.SCREWDRIVER_LV.getStackForm(solidMaterial, Materials.StainlessSteel),  //new long[]{100000L, 32L, 1L, -1L}),
                        "PdX",
                        "MGS",
                        "GBP",
                        'X', OreDictUnifier.get(OrePrefix.stickLong, solidMaterial),
                        'M', MetaItems.ELECTRIC_MOTOR_LV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.StainlessSteel),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.StainlessSteel),
                        'G', OreDictUnifier.get(OrePrefix.gearSmall, Materials.StainlessSteel),
                        'B', MetaItems.BATTERY_RE_LV_LITHIUM.getStackForm());
                    ModHandler.addShapedRecipe(MetaItems.SCREWDRIVER_LV.getStackForm(solidMaterial, Materials.StainlessSteel),  //new long[]{75000L, 32L, 1L, -1L}),
                        "PdX",
                        "MGS",
                        "GBP",
                        'X', OreDictUnifier.get(OrePrefix.stickLong, solidMaterial),
                        'M', MetaItems.ELECTRIC_MOTOR_LV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.StainlessSteel),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.StainlessSteel),
                        'G', OreDictUnifier.get(OrePrefix.gearSmall, Materials.StainlessSteel),
                        'B', MetaItems.BATTERY_RE_LV_CADMIUM.getStackForm());
                    ModHandler.addShapedRecipe(MetaItems.SCREWDRIVER_LV.getStackForm(solidMaterial, Materials.StainlessSteel),  //new long[]{50000L, 32L, 1L, -1L}),
                        "PdX",
                        "MGS",
                        "GBP",
                        'X', OreDictUnifier.get(OrePrefix.stickLong, solidMaterial),
                        'M', MetaItems.ELECTRIC_MOTOR_LV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.StainlessSteel),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.StainlessSteel),
                        'G', OreDictUnifier.get(OrePrefix.gearSmall, Materials.StainlessSteel),
                        'B', MetaItems.BATTERY_RE_LV_SODIUM.getStackForm());
                    if (working)
                        ModHandler.addShapedRecipe(OreDictUnifier.get(OrePrefix.toolHeadWrench, solidMaterial),
                            "hXW",
                            "XRX",
                            "WXd",
                            'X', OreDictUnifier.get(OrePrefix.plate, solidMaterial),
                            'S', OreDictUnifier.get(OrePrefix.plate, Materials.Steel),
                            'R', OreDictUnifier.get(OrePrefix.ring, Materials.Steel),
                            'W', OreDictUnifier.get(OrePrefix.screw, Materials.Steel));
                    break;
                case toolHeadHammer:
                    ModHandler.addShapelessRecipe(MetaItems.HARDHAMMER.getStackForm(solidMaterial, solidMaterial.handleMaterial),
                        entry,
                        OreDictUnifier.get(OrePrefix.stick, solidMaterial.handleMaterial));

                    ModHandler.addShapedRecipe(MetaItems.HARDHAMMER.getStackForm(solidMaterial, solidMaterial.handleMaterial),
                        "XX ",
                        "XXS",
                        "XX ",
                        'X', OreDictUnifier.get(OrePrefix.ingot, solidMaterial),
                        'S', OreDictUnifier.get(OrePrefix.stick, solidMaterial.handleMaterial));

                    ModHandler.addShapedRecipe(MetaItems.HARDHAMMER.getStackForm(solidMaterial, solidMaterial.handleMaterial),
                        "XX ",
                        "XXS",
                        "XX ",
                        'X',  OreDictUnifier.get(OrePrefix.gem, solidMaterial),
                        'S', OreDictUnifier.get(OrePrefix.stick, solidMaterial.handleMaterial));

                    if (smashing) {
                        ModHandler.addShapedRecipe(OreDictUnifier.get(OrePrefix.toolHeadHammer, solidMaterial),
                            "II ",
                            "IIh",
                            "II ",
                            'P', OreDictUnifier.get(OrePrefix.plate, solidMaterial),
                            'I', OreDictUnifier.get(OrePrefix.ingot, solidMaterial));

                    }
                    break;
                case turbineBlade:
                    RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                            .inputs(OreDictUnifier.get(OrePrefix.turbineBlade, solidMaterial, 4), OreDictUnifier.get(OrePrefix.stickLong, Materials.Magnalium))
                            .outputs(MetaItems.TURBINE_SMALL.getStackForm(solidMaterial, solidMaterial))
                            .duration(160)
                            .EUt(100)
                            .buildAndRegister();
                    RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                            .inputs(OreDictUnifier.get(OrePrefix.turbineBlade, solidMaterial, 8), OreDictUnifier.get(OrePrefix.stickLong, Materials.Titanium))
                            .outputs(MetaItems.TURBINE_NORMAL.getStackForm(solidMaterial, solidMaterial))
                            .duration(320)
                            .EUt(400)
                            .buildAndRegister();
                    RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                            .inputs(OreDictUnifier.get(OrePrefix.turbineBlade, solidMaterial, 12), OreDictUnifier.get(OrePrefix.stickLong, Materials.TungstenSteel))
                            .outputs(MetaItems.TURBINE_LARGE.getStackForm(solidMaterial, solidMaterial))
                            .duration(640)
                            .EUt(1600)
                            .buildAndRegister();
                    RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                            .inputs(OreDictUnifier.get(OrePrefix.turbineBlade, solidMaterial, 16), OreDictUnifier.get(OrePrefix.stickLong, Materials.Americium))
                            .outputs(MetaItems.TURBINE_HUGE.getStackForm(solidMaterial, solidMaterial))
                            .duration(1280)
                            .EUt(6400)
                            .buildAndRegister();
                    if (working) {
                        ModHandler.addShapedRecipe(OreDictUnifier.get(OrePrefix.turbineBlade, solidMaterial),
                            "fPd", 
                            "SPS", 
                            " P ", 
                            'P', OreDictUnifier.get(OrePrefix.ingot, solidMaterial),
                            'R', OreDictUnifier.get(OrePrefix.ring, solidMaterial), 
                            'S', OreDictUnifier.get(OrePrefix.screw, solidMaterial));
                    }
                    break;
            }
        }
    }
}
