package gregtech.loaders.oreprocessing;

import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.type.IngotMaterial;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import gregtech.common.items.MetaItems;
import net.minecraft.item.ItemStack;

public class PipeRecipeHandler {

    public static void register() {
        OrePrefix.pipeTiny.addProcessingHandler(IngotMaterial.class, PipeRecipeHandler::processPipeTiny);
        OrePrefix.pipeSmall.addProcessingHandler(IngotMaterial.class, PipeRecipeHandler::processPipeSmall);
        OrePrefix.pipeMedium.addProcessingHandler(IngotMaterial.class, PipeRecipeHandler::processPipeNormal);
        OrePrefix.pipeLarge.addProcessingHandler(IngotMaterial.class, PipeRecipeHandler::processPipeLarge);
    }

    private static void processPipeTiny(OrePrefix pipePrefix, IngotMaterial material) {
        RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
            .input(OrePrefix.ingot, material, 3)
            .notConsumable(MetaItems.SHAPE_EXTRUDER_PIPE_TINY)
            .outputs(GTUtility.copyAmount(4, OreDictUnifier.get(pipePrefix, material)))
            .duration((int) (material.getAverageMass() * 4))
            .EUt(6 * getVoltageMultiplier(material))
            .buildAndRegister();
    }

    private static void processPipeSmall(OrePrefix pipePrefix, IngotMaterial material) {
        ItemStack pipeStack = OreDictUnifier.get(pipePrefix, material);
        RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
            .input(OrePrefix.ingot, material, 3)
            .notConsumable(MetaItems.SHAPE_EXTRUDER_PIPE_SMALL)
            .outputs(GTUtility.copyAmount(2, pipeStack))
            .duration((int) (material.getAverageMass() * 2))
            .EUt(6 * getVoltageMultiplier(material))
            .buildAndRegister();

        ModHandler.addShapedRecipe(String.format("small_%s_pipe", material.toString()),
            GTUtility.copyAmount(4, pipeStack), "XXX", "h f", "XXX",
            'X', new UnificationEntry(OrePrefix.plate, material));
    }

    private static void processPipeNormal(OrePrefix pipePrefix, IngotMaterial material) {
        ItemStack pipeStack = OreDictUnifier.get(pipePrefix, material);
        RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
            .input(OrePrefix.ingot, material, 3)
            .notConsumable(MetaItems.SHAPE_EXTRUDER_PIPE_MEDIUM)
            .outputs(pipeStack)
            .duration((int) material.getAverageMass())
            .EUt(6 * getVoltageMultiplier(material))
            .buildAndRegister();

        ModHandler.addShapedRecipe(String.format("medium_%s_pipe", material.toString()),
            GTUtility.copyAmount(2, pipeStack), "XXX", "f h", "XXX",
            'X', new UnificationEntry(OrePrefix.plate, material));
    }

    private static void processPipeLarge(OrePrefix pipePrefix, IngotMaterial material) {
        RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
            .input(OrePrefix.ingot, material, 6)
            .notConsumable(MetaItems.SHAPE_EXTRUDER_PIPE_LARGE)
            .outputs(OreDictUnifier.get(pipePrefix, material))
            .duration((int) material.getAverageMass())
            .EUt(6 * getVoltageMultiplier(material))
            .buildAndRegister();
    }



    private static int getVoltageMultiplier(Material material) {
        return material instanceof IngotMaterial && ((IngotMaterial) material)
            .blastFurnaceTemperature >= 2800 ? 32 : 8;
    }
}
