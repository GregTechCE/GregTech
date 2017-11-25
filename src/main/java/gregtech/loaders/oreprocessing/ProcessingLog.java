package gregtech.loaders.oreprocessing;

import gregtech.api.GTValues;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import ic2.core.item.type.CraftingItemType;
import ic2.core.item.type.MiscResourceType;
import ic2.core.ref.ItemName;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ProcessingLog implements IOreRegistrationHandler {

    private ProcessingLog() {}

    public static void register() {
        OrePrefix.log.addProcessingHandler(new ProcessingLog());
    }
    
    public void registerOre(UnificationEntry entry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();

        RecipeMap.MACERATOR_RECIPES.recipeBuilder()
                .inputs(stack)
                .outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Wood, 6))
                .chancedOutput(OreDictUnifier.get(OrePrefix.dust, Materials.Wood), 8000)
                .buildAndRegister();

        ModHandler.addShapedRecipe("stick_long_" + entry.material,
            OreDictUnifier.get(OrePrefix.stickLong, Materials.Wood, 2),
            "sLf",
            'L', entry);

        RecipeMap.LATHE_RECIPES.recipeBuilder()
                .inputs(stack)
                .outputs(OreDictUnifier.get(OrePrefix.stickLong, Materials.Wood, 4), OreDictUnifier.get(OrePrefix.dust, Materials.Wood, 2))
                .duration(160)
                .EUt(8)
                .buildAndRegister();

        ItemStack smeltingOutput = ModHandler.getSmeltingOutput(stack);
        if (!smeltingOutput.isEmpty() && smeltingOutput.getItem() == Items.COAL && smeltingOutput.getMetadata() == 1) {
            int coalAmount = smeltingOutput.getCount();

            RecipeMap.PYROLYSE_RECIPES.recipeBuilder()
                    .inputs(GTUtility.copyAmount(16, stack))
                    .circuitMeta(0)
                    .outputs(new ItemStack(Items.COAL, 20 * coalAmount, 1))
                    .fluidOutputs(Materials.Creosote.getFluid(5000 * coalAmount))
                    .duration(440)
                    .EUt(64)
                    .buildAndRegister();
            RecipeMap.PYROLYSE_RECIPES.recipeBuilder()
                    .inputs(GTUtility.copyAmount(16, stack))
                    .circuitMeta(1)
                    .fluidInputs(Materials.Nitrogen.getFluid(400))
                    .outputs(new ItemStack(Items.COAL, 20, 1))
                    .fluidInputs(Materials.Creosote.getFluid(4000))
                    .duration(200)
                    .EUt(96)
                    .buildAndRegister();
            RecipeMap.PYROLYSE_RECIPES.recipeBuilder()
                    .inputs(GTUtility.copyAmount(16, stack))
                    .circuitMeta(2)
                    .outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Ash, 5))
                    .fluidOutputs(Materials.OilHeavy.getFluid(300))
                    .duration(280)
                    .EUt(192)
                    .buildAndRegister();

        }

        ItemStack output = ModHandler.getRecipeOutput(GTValues.DW, stack);
        if (!output.isEmpty() && OreDictUnifier.getPrefix(output) == OrePrefix.plank) {

            RecipeMap.CUTTER_RECIPES.recipeBuilder()
                    .inputs(stack)
                    .fluidInputs(Materials.Lubricant.getFluid(1))
                    .outputs(GTUtility.copyAmount(output.getCount() * 2, stack),
                            OreDictUnifier.get(OrePrefix.dust, Materials.Wood))
                    .duration(200)
                    .EUt(8)
                    .buildAndRegister();

            RecipeMap.CUTTER_RECIPES.recipeBuilder()
                    .inputs(stack)
                    .outputs(output,
                            OreDictUnifier.get(OrePrefix.dust, Materials.Wood, 2))
                    .duration(200)
                    .EUt(8)
                    .buildAndRegister();

            ModHandler.removeRecipes(output);
            ModHandler.addShapedRecipe("log_t_wood_" + entry.material ,
                GTUtility.copyAmount(output.getCount(), output),
                "s##",
                "L##",
                'L', stack);
        }

    }

}
