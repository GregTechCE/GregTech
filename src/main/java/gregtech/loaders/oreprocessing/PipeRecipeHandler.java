package gregtech.loaders.oreprocessing;

import com.google.common.base.CaseFormat;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.IngotMaterial;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.items.MetaItems;

import static gregtech.api.unification.material.type.DustMaterial.MatFlags.*;
import static gregtech.api.unification.ore.OrePrefix.*;

public class PipeRecipeHandler {

    public static void register() {
        pipeTiny.addProcessingHandler(DustMaterial.class, PipeRecipeHandler::processNormalPipe);
        pipeSmall.addProcessingHandler(DustMaterial.class, PipeRecipeHandler::processNormalPipe);
        pipeMedium.addProcessingHandler(DustMaterial.class, PipeRecipeHandler::processNormalPipe);
        pipeLarge.addProcessingHandler(DustMaterial.class, PipeRecipeHandler::processNormalPipe);
        pipeHuge.addProcessingHandler(DustMaterial.class, PipeRecipeHandler::processNormalPipe);
        pipeRestrictiveTiny.addProcessingHandler(DustMaterial.class, PipeRecipeHandler::processRestrictivePipe);
        pipeRestrictiveSmall.addProcessingHandler(DustMaterial.class, PipeRecipeHandler::processRestrictivePipe);
        pipeRestrictiveMedium.addProcessingHandler(DustMaterial.class, PipeRecipeHandler::processRestrictivePipe);
        pipeRestrictiveLarge.addProcessingHandler(DustMaterial.class, PipeRecipeHandler::processRestrictivePipe);
        pipeRestrictiveHuge.addProcessingHandler(DustMaterial.class, PipeRecipeHandler::processRestrictivePipe);
        //pipeQuadruple.addProcessingHandler(DustMaterial.class, PipeRecipeHandler::processMultiPipe);
        //pipeNonuple.addProcessingHandler(DustMaterial.class, PipeRecipeHandler::processMultiPipe);
        //pipeSexdecuple.addProcessingHandler(DustMaterial.class, PipeRecipeHandler::processMultiPipe);
    }

    public static void processNormalPipe(OrePrefix pipePrefix, DustMaterial material) {
        if (material.hasFlag(GENERATE_PLATE) && !material.hasFlag(NO_WORKING)) {
            char hammer = material instanceof IngotMaterial ? 'h' : 'r';
            char tool = material instanceof IngotMaterial ? 'w' : 's';
            int amount = 0;
            String[] recipe = new String[3];
            switch (pipePrefix) {
                case pipeTiny:
                    amount = 10;
                    recipe[0] = "XXX";
                    recipe[1] = tool+" "+hammer;
                    recipe[2] = "XXX";
                    break;
                case pipeSmall:
                    amount = 6;
                    recipe[0] = "X"+tool+"X";
                    recipe[1] = "X X";
                    recipe[2] = "X"+hammer+"X";
                    break;
                case pipeMedium:
                    amount = 2;
                    recipe[0] = "XXX";
                    recipe[1] = hammer+" "+tool;
                    recipe[2] = "XXX";
                    break;
                case pipeLarge:
                    amount = 1;
                    recipe[0] = "X"+hammer+"X";
                    recipe[1] = "X X";
                    recipe[2] = "X"+tool+"X";
                    break;
            }
            if (amount > 0) {
                ModHandler.addShapedRecipe(getRecipeName(pipePrefix, material),
                    OreDictUnifier.get(pipePrefix, material, amount),
                    recipe[0], recipe[1], recipe[2], 'X', new UnificationEntry(plate, material));
            }
        }

        if (material instanceof IngotMaterial) {
            int input = 0;
            int output = 1;
            MetaItem.MetaValueItem extruderShape = null;
            switch (pipePrefix) {
                case pipeTiny:
                    input = 1;
                    output = 2;
                    extruderShape = MetaItems.SHAPE_EXTRUDER_PIPE_TINY;
                    break;
                case pipeSmall:
                    input = 1;
                    extruderShape = MetaItems.SHAPE_EXTRUDER_PIPE_SMALL;
                    break;
                case pipeMedium:
                    input = 3;
                    extruderShape = MetaItems.SHAPE_EXTRUDER_PIPE_MEDIUM;
                    break;
                case pipeLarge:
                    input = 6;
                    extruderShape = MetaItems.SHAPE_EXTRUDER_PIPE_LARGE;
                    break;
                case pipeHuge:
                    input = 12;
                    extruderShape = MetaItems.SHAPE_EXTRUDER_PIPE_HUGE;
                    break;
            }
            if (input > 0 && extruderShape != null) {
                RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                    .input(ingot, material, input)
                    .notConsumable(extruderShape)
                    .outputs(OreDictUnifier.get(pipePrefix, material, output))
                    .duration((int) material.getMass())
                    .EUt(6 * getVoltageMultiplier((IngotMaterial) material))
                    .buildAndRegister();
            }
        }
    }

    public static void processRestrictivePipe(OrePrefix pipePrefix, Material material) {
        if (pipePrefix.secondaryMaterials.isEmpty()) return;
        MaterialStack materialStack = pipePrefix.secondaryMaterials.get(0);
        int amount = (int) (materialStack.amount / ring.materialAmount);
        OrePrefix prefix = OrePrefix.getPrefix(pipePrefix.name().replace("Restrictive", ""));
        if (prefix != null && amount > 0) {
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(prefix, material)
                .input(ring, materialStack.material, amount)
                .outputs(OreDictUnifier.get(pipePrefix, material))
                .duration(400 * amount)
                .EUt(4)
                .buildAndRegister();
        }
    }

    public static void processMultiPipe(OrePrefix pipePrefix, Material material) {
        int amount = 0;
        int duration = 0;
        OrePrefix prefix = null;
        switch (pipePrefix) {
            case pipeQuadruple:
                amount = 4;
                duration = 40;
                prefix = pipeMedium;
                if (OreDictUnifier.get(prefix, material).isEmpty()) return;
                ModHandler.addShapedRecipe(getRecipeName(pipePrefix, material),
                    OreDictUnifier.get(pipePrefix, material),
                    "XX", "XX", 'X', new UnificationEntry(prefix, material));
                break;
            case pipeNonuple:
                amount = 9;
                duration = 60;
                prefix = pipeSmall;
                if (OreDictUnifier.get(prefix, material).isEmpty()) return;
                ModHandler.addShapedRecipe(getRecipeName(pipePrefix, material),
                    OreDictUnifier.get(pipePrefix, material),
                    "XXX", "XXX", "XXX", 'X', new UnificationEntry(prefix, material));
                break;
            case pipeSexdecuple:
                amount = 16;
                duration = 80;
                prefix = pipeTiny;
                if (OreDictUnifier.get(prefix, material).isEmpty()) return;
                break;
        }
        if (prefix != null) {
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(prefix, material, amount)
                .circuitMeta(amount)
                .outputs(OreDictUnifier.get(pipePrefix, material))
                .duration(duration)
                .EUt(8)
                .buildAndRegister();
        }
    }

    private static int getVoltageMultiplier(IngotMaterial material) {
        return material.hasFlag(NO_SMASHING) ? 2 : material.blastFurnaceTemperature >= 2800 ? 32 : 8;
    }

    private static String getRecipeName(OrePrefix orePrefix, Material material) {
        return String.format("%s_" + CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, orePrefix.name()), material);
    }
}
