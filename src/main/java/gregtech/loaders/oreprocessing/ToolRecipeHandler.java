package gregtech.loaders.oreprocessing;

import gregtech.api.GTValues;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.items.metaitem.MetaItem.MetaValueItem;
import gregtech.api.items.toolitem.ToolMetaItem.MetaToolValueItem;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.GemMaterial;
import gregtech.api.unification.material.type.IngotMaterial;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.material.type.SolidMaterial;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.items.MetaItems;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;

import static gregtech.api.unification.material.type.DustMaterial.MatFlags.GENERATE_PLATE;
import static gregtech.api.unification.material.type.DustMaterial.MatFlags.NO_WORKING;
import static gregtech.api.unification.material.type.IngotMaterial.MatFlags.GENERATE_BOLT_SCREW;
import static gregtech.api.unification.material.type.SolidMaterial.MatFlags.GENERATE_ROD;

public class ToolRecipeHandler {

    public static void register() {
        OrePrefix.ingot.addProcessingHandler(IngotMaterial.class, ToolRecipeHandler::processToolHeadExtruding);
        OrePrefix.stick.addProcessingHandler(SolidMaterial.class, ToolRecipeHandler::processStick);
        OrePrefix.stickLong.addProcessingHandler(IngotMaterial.class, ToolRecipeHandler::processLongStick);

        OrePrefix.toolHeadShovel.addProcessingHandler(SolidMaterial.class, ToolRecipeHandler::processShovelHead);
        OrePrefix.toolHeadAxe.addProcessingHandler(SolidMaterial.class, ToolRecipeHandler::processAxeHead);
        OrePrefix.toolHeadPickaxe.addProcessingHandler(SolidMaterial.class, ToolRecipeHandler::processPickaxeHead);
        OrePrefix.toolHeadSword.addProcessingHandler(SolidMaterial.class, ToolRecipeHandler::processSwordHead);
        OrePrefix.toolHeadHoe.addProcessingHandler(SolidMaterial.class, ToolRecipeHandler::processHoeHead);
        OrePrefix.toolHeadSaw.addProcessingHandler(SolidMaterial.class, ToolRecipeHandler::processSawHead);
        OrePrefix.toolHeadChainsaw.addProcessingHandler(SolidMaterial.class, ToolRecipeHandler::processChainSawHead);
        OrePrefix.toolHeadDrill.addProcessingHandler(SolidMaterial.class, ToolRecipeHandler::processDrillHead);

        OrePrefix.toolHeadPlow.addProcessingHandler(SolidMaterial.class, ToolRecipeHandler::processPlowHead);
        OrePrefix.toolHeadSense.addProcessingHandler(SolidMaterial.class, ToolRecipeHandler::processSenseHead);
        OrePrefix.toolHeadWrench.addProcessingHandler(SolidMaterial.class, ToolRecipeHandler::processWrenchHead);
        OrePrefix.toolHeadBuzzSaw.addProcessingHandler(SolidMaterial.class, ToolRecipeHandler::processBuzzSawHead);
        OrePrefix.toolHeadFile.addProcessingHandler(SolidMaterial.class, ToolRecipeHandler::processFileHead);
        OrePrefix.toolHeadUniversalSpade.addProcessingHandler(SolidMaterial.class, ToolRecipeHandler::processSpadeHead);
        OrePrefix.toolHeadScrewdriver.addProcessingHandler(SolidMaterial.class, ToolRecipeHandler::processScrewdriverHead);
        OrePrefix.toolHeadHammer.addProcessingHandler(SolidMaterial.class, ToolRecipeHandler::processHammerHead);
    }

    public static MetaValueItem[] motorItems;
    public static SolidMaterial[] baseMaterials;
    public static MetaValueItem[][] batteryItems;

    public static void initializeMetaItems() {
        motorItems = new MetaValueItem[]{MetaItems.ELECTRIC_MOTOR_LV, MetaItems.ELECTRIC_MOTOR_MV, MetaItems.ELECTRIC_MOTOR_HV};
        baseMaterials = new SolidMaterial[]{Materials.StainlessSteel, Materials.Titanium, Materials.TungstenSteel};
        batteryItems = new MetaValueItem[][]{
            {MetaItems.BATTERY_RE_LV_LITHIUM, MetaItems.BATTERY_RE_LV_CADMIUM, MetaItems.BATTERY_RE_LV_SODIUM},
            {MetaItems.BATTERY_RE_MV_LITHIUM, MetaItems.BATTERY_RE_MV_CADMIUM, MetaItems.BATTERY_RE_MV_SODIUM},
            {MetaItems.BATTERY_RE_HV_LITHIUM, MetaItems.BATTERY_RE_HV_CADMIUM, MetaItems.BATTERY_RE_HV_SODIUM}};
    }

    public static void processSimpleElectricToolHead(OrePrefix toolPrefix, SolidMaterial solidMaterial, MetaToolValueItem[] toolItems) {
        for(int i = 0; i < toolItems.length; i++) {
            for(MetaValueItem battery : batteryItems[i]) {
                ItemStack batteryStack = battery.getStackForm();

                @SuppressWarnings("ConstantConditions")
                long maxCharge = batteryStack.getCapability(GregtechCapabilities.CAPABILITY_ELECTRIC_ITEM, null).getMaxCharge();
                ItemStack drillStack = toolItems[i].getMaxChargeOverrideStack(
                    solidMaterial, baseMaterials[i], maxCharge);
                String recipeName = String.format("%s_%s_%s",
                    toolItems[i].unlocalizedName, battery.unlocalizedName, solidMaterial);

                ModHandler.addShapedRecipe(recipeName, drillStack,
                    "SXd", "GMG", "PBP",
                    'X', new UnificationEntry(toolPrefix, solidMaterial),
                    'M', motorItems[i].getStackForm(),
                    'S', new UnificationEntry(OrePrefix.screw, baseMaterials[i]),
                    'P', new UnificationEntry(OrePrefix.plate, baseMaterials[i]),
                    'G', new UnificationEntry(OrePrefix.gearSmall, baseMaterials[i]),
                    'B', batteryStack);
            }
        }
    }

    public static void processSimpleToolHead(OrePrefix toolPrefix, SolidMaterial solidMaterial, MetaToolValueItem toolItem, Object... recipe) {
        Material handleMaterial = solidMaterial.handleMaterial == null ? Materials.Wood : solidMaterial.handleMaterial;

        ModHandler.addShapelessRecipe(String.format("%s_%s_%s", toolPrefix.name(), solidMaterial, handleMaterial),
            toolItem.getStackForm(solidMaterial, (SolidMaterial) handleMaterial),
            new UnificationEntry(toolPrefix, solidMaterial),
            new UnificationEntry(OrePrefix.stick, handleMaterial));

        if (solidMaterial instanceof IngotMaterial && solidMaterial.hasFlag(GENERATE_PLATE)) {
            addSimpleToolRecipe(toolPrefix, solidMaterial, toolItem,
                new UnificationEntry(OrePrefix.plate, solidMaterial),
                new UnificationEntry(OrePrefix.ingot, solidMaterial), recipe);
        }
        if (solidMaterial instanceof GemMaterial) {
            addSimpleToolRecipe(toolPrefix, solidMaterial, toolItem,
                new UnificationEntry(OrePrefix.gem, solidMaterial),
                new UnificationEntry(OrePrefix.gem, solidMaterial), recipe);
        }
    }

    public static void processStick(OrePrefix stickPrefix, SolidMaterial material) {
        if (material instanceof IngotMaterial) {
            RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                .input(OrePrefix.ingot, material)
                .notConsumable(MetaItems.SHAPE_EXTRUDER_ROD)
                .outputs(OreDictUnifier.get(OrePrefix.stick, material, 2))
                .duration((int) material.getMass() * 2)
                .EUt(6 * getVoltageMultiplier(material))
                .buildAndRegister();

            ModHandler.addShapedRecipe(String.format("plunger_%s", material),
                MetaItems.PLUNGER.getStackForm(material, null),
                "xRR", " SR", "S f",
                'S', new UnificationEntry(OrePrefix.stick, material),
                'R', new UnificationEntry(OrePrefix.plate, Materials.Rubber));
        }

        SolidMaterial handleMaterial = material.handleMaterial == null ? Materials.Wood : material.handleMaterial;
        if (material.hasFlag(GENERATE_ROD) && material.toolDurability > 0) {
            ModHandler.addShapedRecipe(String.format("screwdriver_%s_%s", material.toString(), handleMaterial.toString()),
                MetaItems.SCREWDRIVER.getStackForm(material, handleMaterial),
                " fS", " Sh", "W  ",
                'S', new UnificationEntry(OrePrefix.stick, material),
                'W', new UnificationEntry(OrePrefix.stick, handleMaterial));

            ModHandler.addShapedRecipe(String.format("crowbar_%s", material),
                MetaItems.CROWBAR.getStackForm(material, null),
                "hDS", "DSD", "SDf",
                'S', new UnificationEntry(OrePrefix.stick, material),
                'D', new UnificationEntry(OrePrefix.dye, MarkerMaterials.Color.COLORS.get(EnumDyeColor.BLUE)));

            ModHandler.addShapedRecipe(String.format("scoop_%s", material.toString()),
                MetaItems.SCOOP.getStackForm(material, null),
                "SWS", "SSS", "xSh",
                'S', new UnificationEntry(OrePrefix.stick, material),
                'W', new ItemStack(Blocks.WOOL, 1, GTValues.W));
        }

        if (material.hasFlag(GENERATE_PLATE) && material.toolDurability > 0) {
            ModHandler.addShapedRecipe(String.format("knife_%s", material.toString()),
                MetaItems.KNIFE.getStackForm(material, null),
                "fPh", " S ",
                'S', new UnificationEntry(stickPrefix, material),
                'P', new UnificationEntry(OrePrefix.plate, material));
        }

        if (material.hasFlag(GENERATE_PLATE | GENERATE_ROD) && material.toolDurability > 0) {
            ModHandler.addShapedRecipe(String.format("butchery_knife_%s", material.toString()),
                MetaItems.BUTCHERY_KNIFE.getStackForm(material, null),
                "PPf", "PP ", "Sh ",
                'S', new UnificationEntry(OrePrefix.stick, material),
                'P', new UnificationEntry(OrePrefix.plate, material));
        }

        if (material.hasFlag(GENERATE_PLATE | GENERATE_ROD | GENERATE_BOLT_SCREW) && material.toolDurability > 0) {
            for (MetaValueItem batteryItem : batteryItems[0]) {
                ItemStack batteryStack = batteryItem.getStackForm();
                long maxCharge = batteryStack.getCapability(GregtechCapabilities.CAPABILITY_ELECTRIC_ITEM, null).getMaxCharge();
                ModHandler.addShapedRecipe(String.format("soldering_iron_lv_%s_%s", material.toString(), batteryItem.unlocalizedName),
                    MetaItems.SOLDERING_IRON_LV.getMaxChargeOverrideStack(material, Materials.Rubber, maxCharge),
                    "LBf", "Sd ", "P  ",
                    'B', new UnificationEntry(OrePrefix.bolt, material),
                    'P', new UnificationEntry(OrePrefix.plate, material),
                    'S', new UnificationEntry(OrePrefix.stick, Materials.Iron),
                    'L', MetaItems.BATTERY_RE_LV_LITHIUM.getStackForm());
            }

            ModHandler.addShapedRecipe(String.format("wire_cutter_%s", material.toString()),
                MetaItems.WIRE_CUTTER.getStackForm(material, null),
                "PfP", "hPd", "STS",
                'S', new UnificationEntry(stickPrefix, material),
                'P', new UnificationEntry(OrePrefix.plate, material),
                'T', new UnificationEntry(OrePrefix.screw, material));

            ModHandler.addShapedRecipe(String.format("branch_cutter_%s", material.toString()),
                MetaItems.BRANCH_CUTTER.getStackForm(material, null),
                "PfP", "PdP", "STS",
                'S', new UnificationEntry(stickPrefix, material),
                'P', new UnificationEntry(OrePrefix.plate, material),
                'T', new UnificationEntry(OrePrefix.screw, material));
        }
    }

    public static void processLongStick(OrePrefix orePrefix, IngotMaterial material) {
        if (material.toolDurability <= 0) return;
        for(MetaValueItem batteryItem : batteryItems[2]) {
            ItemStack batteryStack = batteryItem.getStackForm();
            long maxCharge = batteryStack.getCapability(GregtechCapabilities.CAPABILITY_ELECTRIC_ITEM, null).getMaxCharge();
            ModHandler.addShapedRecipe(String.format("jack_hammer_%s_%s", batteryItem.unlocalizedName, material.toString()),
                MetaItems.JACKHAMMER.getMaxChargeOverrideStack(material, null, maxCharge),
                "SXd", "PRP", "MPB",
                'X', new UnificationEntry(OrePrefix.stickLong, material),
                'M', MetaItems.ELECTRIC_PISTON_HV.getStackForm(),
                'S', new UnificationEntry(OrePrefix.screw, Materials.Titanium),
                'P', new UnificationEntry(OrePrefix.plate, Materials.Titanium),
                'R', new UnificationEntry(OrePrefix.spring, Materials.Titanium),
                'B', batteryItem);
        }
    }

    public static void processToolHeadExtruding(OrePrefix ingotPrefix, IngotMaterial material) {
        if(material.toolDurability <= 0) return;
        int voltageMultiplier = getVoltageMultiplier(material);

        RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
            .input(ingotPrefix, material, 2)
            .notConsumable(MetaItems.SHAPE_EXTRUDER_SWORD)
            .outputs(OreDictUnifier.get(OrePrefix.toolHeadSword, material))
            .duration((int) material.getMass() * 2)
            .EUt(8 * voltageMultiplier)
            .buildAndRegister();
        RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
            .input(ingotPrefix, material, 3)
            .notConsumable(MetaItems.SHAPE_EXTRUDER_PICKAXE)
            .outputs(OreDictUnifier.get(OrePrefix.toolHeadPickaxe, material))
            .duration((int) material.getMass() * 3)
            .EUt(8 * voltageMultiplier)
            .buildAndRegister();

        RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
            .input(ingotPrefix, material)
            .notConsumable(MetaItems.SHAPE_EXTRUDER_SHOVEL)
            .outputs(OreDictUnifier.get(OrePrefix.toolHeadShovel, material))
            .duration((int) material.getMass())
            .EUt(8 * voltageMultiplier)
            .buildAndRegister();

        RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
            .input(ingotPrefix, material, 3)
            .notConsumable(MetaItems.SHAPE_EXTRUDER_AXE)
            .outputs(OreDictUnifier.get(OrePrefix.toolHeadAxe, material))
            .duration((int) material.getMass() * 3)
            .EUt(8 * voltageMultiplier)
            .buildAndRegister();

        RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
            .input(ingotPrefix, material, 2)
            .notConsumable(MetaItems.SHAPE_EXTRUDER_HOE)
            .outputs(OreDictUnifier.get(OrePrefix.toolHeadHoe, material))
            .duration((int) material.getMass() * 2)
            .EUt(8 * voltageMultiplier)
            .buildAndRegister();

        RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
            .input(ingotPrefix, material, 6)
            .notConsumable(MetaItems.SHAPE_EXTRUDER_HAMMER)
            .outputs(OreDictUnifier.get(OrePrefix.toolHeadHammer, material))
            .duration((int) material.getMass() * 6)
            .EUt(8 * voltageMultiplier)
            .buildAndRegister();

        RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
            .input(ingotPrefix, material, 2)
            .notConsumable(MetaItems.SHAPE_EXTRUDER_FILE)
            .outputs(OreDictUnifier.get(OrePrefix.toolHeadFile, material))
            .duration((int) material.getMass() * 2)
            .EUt(8 * voltageMultiplier)
            .buildAndRegister();

        RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
            .input(ingotPrefix, material, 2)
            .notConsumable(MetaItems.SHAPE_EXTRUDER_SAW)
            .outputs(OreDictUnifier.get(OrePrefix.toolHeadSaw, material))
            .duration((int) material.getMass() * 2)
            .EUt(8 * voltageMultiplier)
            .buildAndRegister();
    }

    public static void processDrillHead(OrePrefix drillHead, SolidMaterial solidMaterial) {
        processSimpleElectricToolHead(drillHead, solidMaterial, new MetaToolValueItem[] {MetaItems.DRILL_LV, MetaItems.DRILL_MV, MetaItems.DRILL_HV});
        ModHandler.addShapedRecipe(String.format("drill_head_%s", solidMaterial.toString()),
            OreDictUnifier.get(OrePrefix.toolHeadDrill, solidMaterial),
            "XSX", "XSX", "ShS",
            'X', new UnificationEntry(OrePrefix.plate, solidMaterial),
            'S', new UnificationEntry(OrePrefix.plate, Materials.Steel));
    }

    public static void processChainSawHead(OrePrefix toolPrefix, SolidMaterial solidMaterial) {
        processSimpleElectricToolHead(toolPrefix, solidMaterial, new MetaToolValueItem[] {MetaItems.CHAINSAW_LV, MetaItems.CHAINSAW_MV, MetaItems.CHAINSAW_HV});
        ModHandler.addShapedRecipe(String.format("chainsaw_head_%s", solidMaterial.toString()),
            OreDictUnifier.get(toolPrefix, solidMaterial),
            "SRS", "XhX", "SRS",
            'X', new UnificationEntry(OrePrefix.plate, solidMaterial),
            'S', new UnificationEntry(OrePrefix.plate, Materials.Steel),
            'R', new UnificationEntry(OrePrefix.ring, Materials.Steel));
    }

    public static void processWrenchHead(OrePrefix toolPrefix, SolidMaterial solidMaterial) {
        processSimpleElectricToolHead(toolPrefix, solidMaterial, new MetaToolValueItem[]{MetaItems.WRENCH_LV, MetaItems.WRENCH_MV, MetaItems.WRENCH_HV});
        ModHandler.addShapedRecipe(String.format("wrench_head_%s", solidMaterial.toString()),
            OreDictUnifier.get(OrePrefix.toolHeadWrench, solidMaterial),
            "hXW", "XRX", "WXd",
            'X', new UnificationEntry(OrePrefix.plate, solidMaterial),
            'R', new UnificationEntry(OrePrefix.ring, Materials.Steel),
            'W', new UnificationEntry(OrePrefix.screw, Materials.Steel));
    }

    public static void processBuzzSawHead(OrePrefix toolPrefix, SolidMaterial solidMaterial) {
        processSimpleElectricToolHead(toolPrefix, solidMaterial, new MetaToolValueItem[] {MetaItems.BUZZSAW});
        ModHandler.addShapedRecipe(String.format("buzzsaw_head_%s", solidMaterial.toString()),
            OreDictUnifier.get(OrePrefix.toolHeadBuzzSaw, solidMaterial),
            "wXh", "X X", "fXx",
            'X', new UnificationEntry(OrePrefix.plate, solidMaterial));
    }

    public static void processScrewdriverHead(OrePrefix toolPrefix, Material material) {
        if(!(material instanceof SolidMaterial)) return;
        SolidMaterial solidMaterial = (SolidMaterial) material;
        processSimpleElectricToolHead(toolPrefix, solidMaterial, new MetaToolValueItem[] {MetaItems.SCREWDRIVER_LV});
        ModHandler.addShapedRecipe(String.format("screwdriver_head_%s", solidMaterial.toString()),
            OreDictUnifier.get(OrePrefix.toolHeadScrewdriver, solidMaterial),
            "fX", "Xh",
            'X', new UnificationEntry(OrePrefix.stick, solidMaterial));
    }

    public static void addSimpleToolRecipe(OrePrefix toolPrefix, SolidMaterial solidMaterial, MetaToolValueItem toolItem, UnificationEntry plate, UnificationEntry ingot, Object[] recipe) {
        ArrayList<Character> usedChars = new ArrayList<>();
        for(Object object : recipe) {
            if(!(object instanceof String))
                continue;
            char[] chars = ((String) object).toCharArray();
            for(char character : chars)
                usedChars.add(character);
        }

        if(usedChars.contains('P')) {
            recipe = ArrayUtils.addAll(recipe, 'P', plate);
        }
        if(usedChars.contains('I')) {
            recipe = ArrayUtils.addAll(recipe, 'I', ingot);
        }

        ModHandler.addShapedRecipe(
            String.format("head_%s_%s", toolPrefix.name(), solidMaterial.toString()),
            OreDictUnifier.get(toolPrefix, solidMaterial), recipe);
    }

    public static void processAxeHead(OrePrefix toolPrefix, SolidMaterial solidMaterial) {
        processSimpleToolHead(toolPrefix, solidMaterial, MetaItems.AXE, "PIh", "P  ", "f  ");
    }

    public static void processHoeHead(OrePrefix toolPrefix, SolidMaterial solidMaterial) {
        processSimpleToolHead(toolPrefix, solidMaterial, MetaItems.HOE, "PIh", "f  ");
    }

    public static void processPickaxeHead(OrePrefix toolPrefix, SolidMaterial solidMaterial) {
        processSimpleToolHead(toolPrefix, solidMaterial, MetaItems.PICKAXE, "PII", "f h");
    }

    public static void processPlowHead(OrePrefix toolPrefix, SolidMaterial solidMaterial) {
        processSimpleToolHead(toolPrefix, solidMaterial, MetaItems.PLOW, "PP", "PP", "hf");
    }

    public static void processSawHead(OrePrefix toolPrefix, SolidMaterial solidMaterial) {
        processSimpleToolHead(toolPrefix, solidMaterial, MetaItems.SAW, "PP", "fh");
    }

    public static void processSenseHead(OrePrefix toolPrefix, SolidMaterial solidMaterial) {
        processSimpleToolHead(toolPrefix, solidMaterial, MetaItems.SENSE, "PPI", "hf ");
    }

    public static void processShovelHead(OrePrefix toolPrefix, SolidMaterial solidMaterial) {
        processSimpleToolHead(toolPrefix, solidMaterial, MetaItems.SHOVEL, "fPh");
    }

    public static void processSwordHead(OrePrefix toolPrefix, SolidMaterial solidMaterial) {
        processSimpleToolHead(toolPrefix, solidMaterial, MetaItems.SWORD, " P ", "fPh");
    }

    public static void processSpadeHead(OrePrefix toolPrefix, SolidMaterial solidMaterial) {
        processSimpleToolHead(toolPrefix, solidMaterial, MetaItems.UNIVERSAL_SPADE, "PPP", "IhI", " I ");
    }

    public static void processHammerHead(OrePrefix toolPrefix, SolidMaterial solidMaterial) {
        if(!solidMaterial.hasFlag(NO_WORKING)) {
            processSimpleToolHead(toolPrefix, solidMaterial, MetaItems.HARD_HAMMER, "II ", "IIh", "II ");
        }
        if(solidMaterial instanceof IngotMaterial) {
            SolidMaterial handleMaterial = solidMaterial.handleMaterial == null ? Materials.Wood : solidMaterial.handleMaterial;
            if(!solidMaterial.hasFlag(NO_WORKING)) {
                ModHandler.addShapedRecipe(String.format("hammer_%s", solidMaterial.toString()),
                    MetaItems.HARD_HAMMER.getStackForm(solidMaterial, handleMaterial),
                    "XX ", "XXS", "XX ",
                    'X', new UnificationEntry(OrePrefix.ingot, solidMaterial),
                    'S', new UnificationEntry(OrePrefix.stick, handleMaterial));
            } else {
                ModHandler.addShapedRecipe(String.format("soft_hammer_%s", solidMaterial.toString()),
                    MetaItems.SOFT_HAMMER.getStackForm(solidMaterial, handleMaterial),
                    "XX ", "XXS", "XX ",
                    'X', new UnificationEntry(OrePrefix.ingot, solidMaterial),
                    'S', new UnificationEntry(OrePrefix.stick, handleMaterial));
            }
        }
    }

    public static void processSoftHammerHead(OrePrefix toolPrefix, IngotMaterial metalMaterial) {
        processSimpleToolHead(toolPrefix, metalMaterial, MetaItems.SOFT_HAMMER, "II", "IIh", "II ");

        SolidMaterial handleMaterial = metalMaterial.handleMaterial == null ? Materials.Wood : metalMaterial.handleMaterial;
        ModHandler.addShapedRecipe(String.format("hammer_soft_%s", metalMaterial.toString()),
            MetaItems.HARD_HAMMER.getStackForm(metalMaterial, handleMaterial),
            "XX ", "XXS", "XX ",
            'X', new UnificationEntry(OrePrefix.ingot, metalMaterial),
            'S', new UnificationEntry(OrePrefix.stick, handleMaterial));
    }

    public static void processFileHead(OrePrefix toolPrefix, SolidMaterial solidMaterial) {
        processSimpleToolHead(toolPrefix, solidMaterial, MetaItems.FILE, " I ", " I ", "  h");
        if(solidMaterial instanceof IngotMaterial) {
            SolidMaterial handleMaterial = solidMaterial.handleMaterial == null ? Materials.Wood : solidMaterial.handleMaterial;
            ModHandler.addShapedRecipe(String.format("file_%s", solidMaterial),
                MetaItems.FILE.getStackForm(solidMaterial, handleMaterial),
                "P", "P", "S",
                'P', new UnificationEntry(OrePrefix.plate, solidMaterial),
                'S', new UnificationEntry(OrePrefix.stick, handleMaterial));
        }
    }

    private static int getVoltageMultiplier(Material material) {
        return material instanceof IngotMaterial && ((IngotMaterial) material)
            .blastFurnaceTemperature >= 2800 ? 32 : 8;
    }

}
