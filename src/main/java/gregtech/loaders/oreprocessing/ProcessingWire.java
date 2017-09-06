package gregtech.loaders.oreprocessing;

import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import static gregtech.api.GTValues.L;

public class ProcessingWire implements IOreRegistrationHandler {

    private ProcessingWire() {
    }

    public static void register() {
        ProcessingWire processing = new ProcessingWire();
        OrePrefix.wireGt01.addProcessingHandler(processing);
        OrePrefix.wireGt02.addProcessingHandler(processing);
        OrePrefix.wireGt04.addProcessingHandler(processing);
        OrePrefix.wireGt08.addProcessingHandler(processing);
        OrePrefix.wireGt12.addProcessingHandler(processing);
        OrePrefix.wireGt16.addProcessingHandler(processing);
    }

    public void registerOre(UnificationEntry entry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();
        switch (entry.orePrefix) {
            case wireGt01:
                if (entry.material == Materials.Cobalt
                    || entry.material == Materials.Lead
                    || entry.material == Materials.Tin
                    || entry.material == Materials.Zinc
                    || entry.material == Materials.SolderingAlloy) {

                    ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.cableGt01, entry.material),
                        entry,
                        new ItemStack(Blocks.CARPET, 1, 15),
                        new ItemStack(Items.STRING));

                    RecipeMap.BOXINATOR_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack), new ItemStack(Blocks.CARPET, 1, 15))
                        .outputs(OreDictUnifier.get(OrePrefix.cableGt01, entry.material))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();

                    RecipeMap.UNBOXINATOR_RECIPES.recipeBuilder()
                        .inputs(OreDictUnifier.get(OrePrefix.cableGt01, entry.material))
                        .outputs(GTUtility.copyAmount(1, stack), new ItemStack(Blocks.CARPET, 1, 15))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();
                } else if (entry.material == Materials.RedAlloy) {
                    ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.cableGt01, entry.material),
                        entry,
                        new UnificationEntry(OrePrefix.plate, Materials.Paper));

                    RecipeMap.BOXINATOR_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.plate, Materials.Paper))
                        .outputs(OreDictUnifier.get(OrePrefix.cableGt01, entry.material))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();

                    RecipeMap.UNBOXINATOR_RECIPES.recipeBuilder()
                        .inputs(OreDictUnifier.get(OrePrefix.cableGt01, entry.material))
                        .outputs(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.plate, Materials.Paper))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();
                } else {
                    RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                        .inputs(stack)
                        .circuitMeta(24)
                        .fluidInputs(Materials.Rubber.getFluid(L))
                        .outputs(OreDictUnifier.get(OrePrefix.cableGt01, entry.material))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();

                    RecipeMap.UNBOXINATOR_RECIPES.recipeBuilder()
                        .inputs(OreDictUnifier.get(OrePrefix.cableGt01, entry.material))
                        .outputs(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.plate, Materials.Rubber))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();
                }
                if (!entry.material.hasFlag(DustMaterial.MatFlags.NO_SMASHING)) {
                    RecipeMap.BENDER_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack))
                        .outputs(OreDictUnifier.get(OrePrefix.springSmall, entry.material, 2))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();
                    RecipeMap.WIREMILL_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack))
                        .outputs(OreDictUnifier.get(OrePrefix.wireFine, entry.material, 4))
                        .duration(200)
                        .EUt(8)
                        .buildAndRegister();
                    RecipeMap.WIREMILL_RECIPES.recipeBuilder()
                        .inputs(OreDictUnifier.get(OrePrefix.ingot, entry.material))
                        .outputs(GTUtility.copy(GTUtility.copyAmount(2, stack), OreDictUnifier.get(OrePrefix.wireFine, entry.material, 8)))
                        .duration(100)
                        .EUt(4)
                        .buildAndRegister();
                    RecipeMap.WIREMILL_RECIPES.recipeBuilder()
                        .inputs(OreDictUnifier.get(OrePrefix.stick, entry.material))
                        .outputs(GTUtility.copy(stack, OreDictUnifier.get(OrePrefix.wireFine, entry.material, 4)))
                        .duration(50)
                        .EUt(4)
                        .buildAndRegister();
                }
                if (!entry.material.hasFlag(DustMaterial.MatFlags.NO_WORKING)) {
                    ModHandler.addShapedRecipe(OreDictUnifier.get(OrePrefix.wireGt01, entry.material),
                        "Xx",
                        'X', OreDictUnifier.get(OrePrefix.plate, entry.material));
                }
                RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                    .inputs(GTUtility.copyAmount(2, stack))
                    .circuitMeta(2)
                    .outputs(OreDictUnifier.get(OrePrefix.wireGt02, entry.material))
                    .duration(150)
                    .EUt(8)
                    .buildAndRegister();
                RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                    .inputs(GTUtility.copyAmount(4, stack))
                    .circuitMeta(4)
                    .outputs(OreDictUnifier.get(OrePrefix.wireGt04, entry.material))
                    .duration(200)
                    .EUt(8).buildAndRegister();
                RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                    .inputs(GTUtility.copyAmount(8, stack))
                    .circuitMeta(8)
                    .outputs(OreDictUnifier.get(OrePrefix.wireGt08, entry.material))
                    .duration(300)
                    .EUt(8)
                    .buildAndRegister();
                RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                    .inputs(GTUtility.copyAmount(12, stack))
                    .circuitMeta(12)
                    .outputs(OreDictUnifier.get(OrePrefix.wireGt12, entry.material))
                    .duration(400)
                    .EUt(8)
                    .buildAndRegister();
                RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                    .inputs(GTUtility.copyAmount(16, stack))
                    .circuitMeta(16)
                    .outputs(OreDictUnifier.get(OrePrefix.wireGt16, entry.material))
                    .duration(500)
                    .EUt(8)
                    .buildAndRegister();
                break;
            case wireGt02:
                if (entry.material == Materials.Cobalt
                    || entry.material == Materials.Lead
                    || entry.material == Materials.Tin
                    || entry.material == Materials.Zinc
                    || entry.material == Materials.SolderingAlloy) {

                    ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.cableGt02, entry.material),
                        entry,
                        new ItemStack(Blocks.CARPET, 1, 15),
                        new ItemStack(Items.STRING));

                    RecipeMap.BOXINATOR_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack), new ItemStack(Blocks.CARPET, 1, 15))
                        .outputs(OreDictUnifier.get(OrePrefix.cableGt02, entry.material))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();

                    RecipeMap.UNBOXINATOR_RECIPES.recipeBuilder()
                        .inputs(OreDictUnifier.get(OrePrefix.cableGt02, entry.material))
                        .outputs(GTUtility.copyAmount(1, stack), new ItemStack(Blocks.CARPET, 1, 15))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();
                } else if (entry.material == Materials.RedAlloy) {
                    ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.cableGt02, entry.material),
                        entry,
                        new UnificationEntry(OrePrefix.plate, Materials.Paper));

                    RecipeMap.BOXINATOR_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.plate, Materials.Paper))
                        .outputs(OreDictUnifier.get(OrePrefix.cableGt02, entry.material))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();

                    RecipeMap.UNBOXINATOR_RECIPES.recipeBuilder()
                        .inputs(OreDictUnifier.get(OrePrefix.cableGt02, entry.material))
                        .outputs(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.plate, Materials.Paper))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();
                } else {
                    RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                        .inputs(stack)
                        .circuitMeta(24)
                        .fluidInputs(Materials.Rubber.getFluid(L))
                        .outputs(OreDictUnifier.get(OrePrefix.cableGt02, entry.material))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();

                    RecipeMap.UNBOXINATOR_RECIPES.recipeBuilder()
                        .inputs(OreDictUnifier.get(OrePrefix.cableGt02, entry.material))
                        .outputs(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.plate, Materials.Rubber))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();
                }
                ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.wireGt01, entry.material, 2),
                    entry);
                ModHandler.addShapelessRecipe(GTUtility.copyAmount(1, stack),
                    new UnificationEntry(OrePrefix.wireGt01, entry.material),
                    new UnificationEntry(OrePrefix.wireGt01, entry.material));

                break;
            case wireGt04:
                if (entry.material == Materials.Cobalt
                    || entry.material == Materials.Lead
                    || entry.material == Materials.Tin
                    || entry.material == Materials.Zinc
                    || entry.material == Materials.SolderingAlloy) {

                    ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.cableGt04, entry.material),
                        entry,
                        new ItemStack(Blocks.CARPET, 1, 15),
                        new ItemStack(Blocks.CARPET, 1, 15),
                        new ItemStack(Items.STRING));

                    RecipeMap.BOXINATOR_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack), new ItemStack(Blocks.CARPET, 2, 15))
                        .outputs(OreDictUnifier.get(OrePrefix.cableGt04, entry.material))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();

                    RecipeMap.UNBOXINATOR_RECIPES.recipeBuilder()
                        .inputs(OreDictUnifier.get(OrePrefix.cableGt04, entry.material))
                        .outputs(GTUtility.copyAmount(1, stack), new ItemStack(Blocks.CARPET, 2, 15))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();
                } else if (entry.material == Materials.RedAlloy) {
                    ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.cableGt04, entry.material),
                        entry,
                        new UnificationEntry(OrePrefix.plate, Materials.Paper),
                        new UnificationEntry(OrePrefix.plate, Materials.Paper));

                    RecipeMap.BOXINATOR_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.plate, Materials.Paper, 2))
                        .outputs(OreDictUnifier.get(OrePrefix.cableGt04, entry.material))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();
                    RecipeMap.UNBOXINATOR_RECIPES.recipeBuilder()
                        .inputs(OreDictUnifier.get(OrePrefix.cableGt04, entry.material))
                        .outputs(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.plate, Materials.Paper, 2))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();
                } else {
                    RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                        .inputs(stack)
                        .circuitMeta(24)
                        .fluidInputs(Materials.Rubber.getFluid(2 * L))
                        .outputs(OreDictUnifier.get(OrePrefix.cableGt04, entry.material))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();

                    RecipeMap.UNBOXINATOR_RECIPES.recipeBuilder()
                        .inputs(OreDictUnifier.get(OrePrefix.cableGt04, entry.material))
                        .outputs(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.plate, Materials.Rubber, 2))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();
                }
                ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.wireGt01, entry.material, 4),
                    entry);

                ModHandler.addShapelessRecipe(GTUtility.copyAmount(1, stack),
                    new UnificationEntry(OrePrefix.wireGt02, entry.material),
                    new UnificationEntry(OrePrefix.wireGt02, entry.material));

                break;
            case wireGt08:
                if (entry.material == Materials.Cobalt
                    || entry.material == Materials.Lead
                    || entry.material == Materials.Tin
                    || entry.material == Materials.Zinc
                    || entry.material == Materials.SolderingAlloy) {

                    ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.cableGt08, entry.material),
                        entry,
                        new ItemStack(Blocks.CARPET, 1, 15),
                        new ItemStack(Blocks.CARPET, 1, 15),
                        new ItemStack(Blocks.CARPET, 1, 15),
                        new ItemStack(Items.STRING));

                    RecipeMap.BOXINATOR_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack), new ItemStack(Blocks.CARPET, 3, 15))
                        .outputs(OreDictUnifier.get(OrePrefix.cableGt08, entry.material))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();
                    RecipeMap.UNBOXINATOR_RECIPES.recipeBuilder()
                        .inputs(OreDictUnifier.get(OrePrefix.cableGt08, entry.material))
                        .outputs(GTUtility.copyAmount(1, stack), new ItemStack(Blocks.CARPET, 3, 15))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();

                } else if (entry.material == Materials.RedAlloy) {

                    ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.cableGt08, entry.material),
                        entry,
                        new UnificationEntry(OrePrefix.plate, Materials.Paper),
                        new UnificationEntry(OrePrefix.plate, Materials.Paper),
                        new UnificationEntry(OrePrefix.plate, Materials.Paper));

                    RecipeMap.BOXINATOR_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.plate, Materials.Paper))
                        .outputs(OreDictUnifier.get(OrePrefix.cableGt08, entry.material))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();
                    RecipeMap.UNBOXINATOR_RECIPES.recipeBuilder()
                        .inputs(OreDictUnifier.get(OrePrefix.cableGt08, entry.material))
                        .outputs(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.plate, Materials.Paper, 3))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();
                } else {
                    RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                        .inputs(stack)
                        .circuitMeta(24)
                        .fluidInputs(Materials.Rubber.getFluid(3 * L))
                        .outputs(OreDictUnifier.get(OrePrefix.cableGt08, entry.material))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();

                    RecipeMap.UNBOXINATOR_RECIPES.recipeBuilder()
                        .inputs(OreDictUnifier.get(OrePrefix.cableGt08, entry.material))
                        .outputs(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.plate, Materials.Rubber, 3))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();
                }
                ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.wireGt01, entry.material, 8),
                    entry);
                ModHandler.addShapelessRecipe(GTUtility.copyAmount(1, stack),
                    OreDictUnifier.get(OrePrefix.wireGt04, entry.material),
                    OreDictUnifier.get(OrePrefix.wireGt04, entry.material));

                break;
            case wireGt12:
                if (entry.material == Materials.Cobalt
                    || entry.material == Materials.Lead
                    || entry.material == Materials.Tin
                    || entry.material == Materials.Zinc
                    || entry.material == Materials.SolderingAlloy) {

                    ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.cableGt12, entry.material),
                        entry,
                        new ItemStack(Blocks.CARPET, 1, 15),
                        new ItemStack(Blocks.CARPET, 1, 15),
                        new ItemStack(Blocks.CARPET, 1, 15),
                        new ItemStack(Blocks.CARPET, 1, 15),
                        new ItemStack(Items.STRING));

                    RecipeMap.BOXINATOR_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack), new ItemStack(Blocks.CARPET, 4, 15))
                        .outputs(OreDictUnifier.get(OrePrefix.cableGt12, entry.material))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();
                    RecipeMap.UNBOXINATOR_RECIPES.recipeBuilder()
                        .inputs(OreDictUnifier.get(OrePrefix.cableGt12, entry.material))
                        .outputs(GTUtility.copyAmount(1, stack), new ItemStack(Blocks.CARPET, 4, 15))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();

                } else if (entry.material == Materials.RedAlloy) {

                    ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.cableGt12, entry.material),
                        entry,
                        new UnificationEntry(OrePrefix.plate, Materials.Paper),
                        new UnificationEntry(OrePrefix.plate, Materials.Paper),
                        new UnificationEntry(OrePrefix.plate, Materials.Paper),
                        new UnificationEntry(OrePrefix.plate, Materials.Paper));

                    RecipeMap.BOXINATOR_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.plate, Materials.Paper, 4))
                        .outputs(OreDictUnifier.get(OrePrefix.cableGt12, entry.material))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();
                    RecipeMap.UNBOXINATOR_RECIPES.recipeBuilder()
                        .inputs(OreDictUnifier.get(OrePrefix.cableGt12, entry.material))
                        .outputs(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.plate, Materials.Paper, 4))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();

                } else {
                    RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                        .inputs(stack)
                        .circuitMeta(24)
                        .fluidInputs(Materials.Rubber.getFluid(4 * L))
                        .outputs(OreDictUnifier.get(OrePrefix.cableGt12, entry.material))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();

                    RecipeMap.UNBOXINATOR_RECIPES.recipeBuilder()
                        .inputs(OreDictUnifier.get(OrePrefix.cableGt12, entry.material))
                        .outputs(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.plate, Materials.Rubber, 4))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();
                }
                ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.wireGt01, entry.material, 12),
                    entry);

                ModHandler.addShapelessRecipe(GTUtility.copyAmount(1, stack),
                    OreDictUnifier.get(OrePrefix.wireGt08, entry.material),
                    OreDictUnifier.get(OrePrefix.wireGt04, entry.material));

                break;
            case wireGt16:
                ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.wireGt01, entry.material, 16),
                    entry);

                ModHandler.addShapelessRecipe(GTUtility.copyAmount(1, stack),
                    OreDictUnifier.get(OrePrefix.wireGt08, entry.material),
                    OreDictUnifier.get(OrePrefix.wireGt08, entry.material));

                ModHandler.addShapelessRecipe(GTUtility.copyAmount(1, stack),
                    OreDictUnifier.get(OrePrefix.wireGt12, entry.material),
                    OreDictUnifier.get(OrePrefix.wireGt12, entry.material));

                break;
        }
    }
}
