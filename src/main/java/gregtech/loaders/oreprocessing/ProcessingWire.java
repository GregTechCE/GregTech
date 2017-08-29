package gregtech.loaders.oreprocessing;

import gregtech.api.GTValues;
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
import gregtech.common.CommonProxy;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import static gregtech.api.GTValues.L;

public class ProcessingWire implements IOreRegistrationHandler {

    public void register() {
        OrePrefix.wireGt01.addProcessingHandler(this);
        OrePrefix.wireGt02.addProcessingHandler(this);
        OrePrefix.wireGt04.addProcessingHandler(this);
        OrePrefix.wireGt08.addProcessingHandler(this);
        OrePrefix.wireGt12.addProcessingHandler(this);
        OrePrefix.wireGt16.addProcessingHandler(this);
    }
    
    public void registerOre(UnificationEntry entry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();
        switch (entry.orePrefix) {
            case wireGt01:
                switch (entry.material.toString()){
                    case "Cobalt": case "Lead": case "Tin": case "Zinc": case "SolderingAlloy":
                        ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.cableGt01, entry.material),
                                entry,
                                new ItemStack(Blocks.CARPET, 1, 15),
                                new ItemStack(Items.STRING, 1));

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
                        break;
                    case "RedAlloy":
                        ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.cableGt01, entry.material),
                                entry,
                                OreDictUnifier.get(OrePrefix.plate, Materials.Paper));

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
                        break;
                    default:
                        GTValues.RA.addAssemblerRecipe(stack, ItemList.Circuit_Integrated.getWithDamage(0L, 24),
                                Materials.Rubber.getFluid(L),
                                OreDictUnifier.get(OrePrefix.cableGt01, entry.material, 1),
                                100,
                                8);

                        GTValues.RA.addUnboxingRecipe(OreDictUnifier.get(OrePrefix.cableGt01, entry.material, 1),
                                GTUtility.copyAmount(1, stack),
                                OreDictUnifier.get(OrePrefix.plate, Materials.Rubber, 1),
                                100,
                                8);
                }
                if (!entry.material.hasFlag(DustMaterial.MatFlags.NO_SMASHING)) {
                    GTValues.RA.addBenderRecipe(GTUtility.copyAmount(1, stack),
                            OreDictUnifier.get(OrePrefix.springSmall, entry.material, 2),
                            100,
                            8);

                    GTValues.RA.addWiremillRecipe(GTUtility.copyAmount(1, stack),
                            OreDictUnifier.get(OrePrefix.wireFine, entry.material, 4),
                            200,
                            8);

                    GTValues.RA.addWiremillRecipe(OreDictUnifier.get(OrePrefix.ingot, entry.material, 1),
                            GTUtility.copy(GTUtility.copyAmount(2, stack), OreDictUnifier.get(OrePrefix.wireFine, entry.material, 8)),
                            100,
                            4);

                    GTValues.RA.addWiremillRecipe(OreDictUnifier.get(OrePrefix.stick, entry.material, 1),
                            GTUtility.copy(stack, OreDictUnifier.get(OrePrefix.wireFine, entry.material, 4)),
                            50,
                            4);

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
                switch (entry.material.toString()){
                    case "Cobalt": case "Lead": case "Tin": case "Zinc":case "SolderingAlloy":
                        ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.cableGt02, entry.material),
                                        entry,
                                        new ItemStack(Blocks.CARPET, 1, 15),
                                        new ItemStack(Items.STRING));
                        GTValues.RA.addBoxingRecipe(GTUtility.copyAmount(1, stack), new ItemStack(Blocks.CARPET, 1, 15), OreDictUnifier.get(OrePrefix.cableGt02, entry.material, 1L), 100, 8);
                        GTValues.RA.addUnboxingRecipe(OreDictUnifier.get(OrePrefix.cableGt02, entry.material), GTUtility.copyAmount(1L, new Object[]{stack}), new ItemStack(Blocks.CARPET, 1, 15), 100, 8);
                        break;
                    case "RedAlloy":
                        ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.cableGt02, entry.material), new Object[]{aOreDictName, OrePrefix.plate.get(Materials.Paper)});
                        GTValues.RA.addBoxingRecipe(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.plate, Materials.Paper, 1L), OreDictUnifier.get(OrePrefix.cableGt02, entry.material, 1L), 100, 8);
                        GTValues.RA.addUnboxingRecipe(OreDictUnifier.get(OrePrefix.cableGt02, entry.material), GTUtility.copyAmount(1L, new Object[]{stack}), OreDictUnifier.get(OrePrefix.plate, Materials.Paper, 1L), 100, 8);
                        break;
                    default:
                        GTValues.RA.addAssemblerRecipe(stack, ItemList.Circuit_Integrated.getWithDamage(0L, 24L, new Object[0]), Materials.Rubber.getMolten(144L), OreDictUnifier.get(OrePrefix.cableGt02, entry.material, 1L), 100, 8);
                        GTValues.RA.addUnboxingRecipe(OreDictUnifier.get(OrePrefix.cableGt02, entry.material), GTUtility.copyAmount(1L, new Object[]{stack}), OreDictUnifier.get(OrePrefix.plate, Materials.Rubber, 1L), 100, 8);
                }
                ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.wireGt01, entry.material, 2),
                        entry);
                ModHandler.addShapelessRecipe(GTUtility.copyAmount(1, stack),
                        new UnificationEntry(OrePrefix.wireGt01, entry.material),
                        new UnificationEntry(OrePrefix.wireGt01, entry.material));

                break;
            case wireGt04:
                switch (entry.material.toString()){
                    case "Cobalt": case "Lead": case "Tin": case "Zinc": case "SolderingAlloy":
                        ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.cableGt04, entry.material, 1),
                                entry,
                                new ItemStack(Blocks.CARPET, 1, 15),
                                new ItemStack(Blocks.CARPET, 1, 15),
                                new ItemStack(Items.STRING, 1));

                        GTValues.RA.addBoxingRecipe(GTUtility.copyAmount(1, stack), new ItemStack(Blocks.CARPET, 2, 15), OreDictUnifier.get(OrePrefix.cableGt04, entry.material, 1L), 100, 8);
                        GTValues.RA.addUnboxingRecipe(OreDictUnifier.get(OrePrefix.cableGt04, entry.material, 1), GTUtility.copyAmount(1L, new Object[]{stack}), new ItemStack(Blocks.CARPET, 2, 15), 100, 8);
                        break;
                    case "RedAlloy":
                        ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.cableGt04, entry.material, 1),
                                entry,
                                new UnificationEntry(OrePrefix.plate, Materials.Paper),
                                new UnificationEntry(OrePrefix.plate, Materials.Paper));

                        GTValues.RA.addBoxingRecipe(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.plate, Materials.Paper, 2L), OreDictUnifier.get(OrePrefix.cableGt04, entry.material, 1L), 100, 8);
                        GTValues.RA.addUnboxingRecipe(OreDictUnifier.get(OrePrefix.cableGt04, entry.material, 1), GTUtility.copyAmount(1L, new Object[]{stack}), OreDictUnifier.get(OrePrefix.plate, Materials.Paper, 2L), 100, 8);
                        break;
                    default:
                        GTValues.RA.addAssemblerRecipe(stack, ItemList.Circuit_Integrated.getWithDamage(0L, 24L, new Object[0]), Materials.Rubber.getMolten(288L), OreDictUnifier.get(OrePrefix.cableGt04, entry.material, 1L), 100, 8);
                        GTValues.RA.addUnboxingRecipe(OreDictUnifier.get(OrePrefix.cableGt04, entry.material, 1), GTUtility.copyAmount(1L, new Object[]{stack}), OreDictUnifier.get(OrePrefix.plate, Materials.Rubber, 2L), 100, 8);
                }
                ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.wireGt01, entry.material, 4),
                        entry);

                ModHandler.addShapelessRecipe(GTUtility.copyAmount(1, stack),
                        new UnificationEntry(OrePrefix.wireGt02, entry.material),
                        new UnificationEntry(OrePrefix.wireGt02, entry.material));

                break;
            case wireGt08:
                switch (entry.material.toString()){
                    case "Cobalt": case "Lead": case "Tin": case "Zinc": case "SolderingAlloy":
                        ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.cableGt08, entry.material),
                                entry,
                                new ItemStack(Blocks.CARPET, 1, 15),
                                new ItemStack(Blocks.CARPET, 1, 15),
                                new ItemStack(Blocks.CARPET, 1, 15),
                                new ItemStack(Items.STRING, 1));

                        GTValues.RA.addBoxingRecipe(GTUtility.copyAmount(1, stack), new ItemStack(Blocks.CARPET, 3, 15), OreDictUnifier.get(OrePrefix.cableGt08, entry.material, 1L), 100, 8);
                        GTValues.RA.addUnboxingRecipe(OreDictUnifier.get(OrePrefix.cableGt08, entry.material), GTUtility.copyAmount(1L, new Object[]{stack}), new ItemStack(Blocks.CARPET, 3, 15), 100, 8);
                        break;
                    case "RedAlloy":
                        ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.cableGt08, entry.material),
                                entry,
                                new UnificationEntry(OrePrefix.plate, Materials.Paper),
                                new UnificationEntry(OrePrefix.plate, Materials.Paper),
                                new UnificationEntry(OrePrefix.plate, Materials.Paper));

                        GTValues.RA.addBoxingRecipe(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.plate, Materials.Paper, ), OreDictUnifier.get(OrePrefix.cableGt08, entry.material, 1L), 100, 8);
                        GTValues.RA.addUnboxingRecipe(OreDictUnifier.get(OrePrefix.cableGt08, entry.material), GTUtility.copyAmount(1L, new Object[]{stack}), OreDictUnifier.get(OrePrefix.plate, Materials.Paper, 3L), 100, 8);
                        break;
                    default:
                        GTValues.RA.addAssemblerRecipe(stack, ItemList.Circuit_Integrated.getWithDamage(0L, 24L, new Object[0]), Materials.Rubber.getMolten(432L), OreDictUnifier.get(OrePrefix.cableGt08, entry.material, 1L), 100, 8);
                        GTValues.RA.addUnboxingRecipe(OreDictUnifier.get(OrePrefix.cableGt08, entry.material), GTUtility.copyAmount(1L, new Object[]{stack}), OreDictUnifier.get(OrePrefix.plate, Materials.Rubber, 3L), 100, 8);
                }
                ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.wireGt01, entry.material, 8),
                        entry);
                ModHandler.addShapelessRecipe(GTUtility.copyAmount(1, stack),
                        OreDictUnifier.get(OrePrefix.wireGt04, entry.material),
                        OreDictUnifier.get(OrePrefix.wireGt04, entry.material));

                break;
            case wireGt12:
                switch (entry.material.toString()){
                    case "Cobalt": case "Lead": case "Tin": case "Zinc":case "SolderingAlloy":
                        ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.cableGt12, entry.material),
                                entry,
                                new ItemStack(Blocks.CARPET, 1, 15),
                                new ItemStack(Blocks.CARPET, 1, 15),
                                new ItemStack(Blocks.CARPET, 1, 15),
                                new ItemStack(Blocks.CARPET, 1, 15),
                                new ItemStack(Items.STRING, 1));

                        GTValues.RA.addBoxingRecipe(GTUtility.copyAmount(1, stack), new ItemStack(Blocks.CARPET, 4, 15), OreDictUnifier.get(OrePrefix.cableGt12, entry.material, 1L), 100, 8);
                        GTValues.RA.addUnboxingRecipe(OreDictUnifier.get(OrePrefix.cableGt12, entry.material), GTUtility.copyAmount(1L, new Object[]{stack}), new ItemStack(Blocks.CARPET, 4, 15), 100, 8);
                        break;
                    case "RedAlloy":
                        ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.cableGt12, entry.material), new Object[]{aOreDictName, OrePrefix.plate.get(Materials.Paper), OrePrefix.plate.get(Materials.Paper), OrePrefix.plate.get(Materials.Paper), OrePrefix.plate.get(Materials.Paper)});
                        GTValues.RA.addBoxingRecipe(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.plate, Materials.Paper, 4), OreDictUnifier.get(OrePrefix.cableGt12, entry.material, 1L), 100, 8);
                        GTValues.RA.addUnboxingRecipe(OreDictUnifier.get(OrePrefix.cableGt12, entry.material), GTUtility.copyAmount(1L, new Object[]{stack}), OreDictUnifier.get(OrePrefix.plate, Materials.Paper, 4L), 100, 8);
                        break;
                    default:
                        GTValues.RA.addAssemblerRecipe(stack, ItemList.Circuit_Integrated.getWithDamage(0L, 24L, new Object[0]), Materials.Rubber.getMolten(576L), OreDictUnifier.get(OrePrefix.cableGt12, entry.material, 1L), 100, 8);
                        GTValues.RA.addUnboxingRecipe(OreDictUnifier.get(OrePrefix.cableGt12, entry.material), GTUtility.copyAmount(1L, new Object[]{stack}), OreDictUnifier.get(OrePrefix.plate, Materials.Rubber, 4L), 100, 8);
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
