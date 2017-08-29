package gregtech.loaders.oreprocessing;

import gregtech.api.GTValues;
import gregtech.api.items.ItemList;
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

public class ProcessingWire implements IOreRegistrationHandler {
    public ProcessingWire() {
        OrePrefix.wireGt01.addProcessingHandler(this);
        OrePrefix.wireGt02.addProcessingHandler(this);
        OrePrefix.wireGt04.addProcessingHandler(this);
        OrePrefix.wireGt08.addProcessingHandler(this);
        OrePrefix.wireGt12.addProcessingHandler(this);
        OrePrefix.wireGt16.addProcessingHandler(this);
    }
    
    public void registerOre(UnificationEntry uEntry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();
        switch (uEntry.orePrefix) {
            case wireGt01:
                switch (uEntry.material.toString()){
                    case "Cobalt": case "Lead": case "Tin": case "Zinc":case "SolderingAlloy":
                        ModHandler.addShapelessCraftingRecipe(OreDictUnifier.get(OrePrefix.cableGt01, uEntry.material), aOreDictName, new ItemStack(Blocks.CARPET, 1, 15), new ItemStack(Items.STRING, 1));
                        RecipeMap.BOXINATOR_RECIPES.recipeBuilder()
                                .inputs(GTUtility.copyAmount(1, stack), new ItemStack(Blocks.CARPET, 1, 15))
                                .outputs(OreDictUnifier.get(OrePrefix.cableGt01, uEntry.material))
                                .duration(100)
                                .EUt(8)
                                .buildAndRegister();
                        RecipeMap.UNBOXINATOR_RECIPES.recipeBuilder()
                                .inputs(OreDictUnifier.get(OrePrefix.cableGt01, uEntry.material))
                                .outputs(GTUtility.copyAmount(1, stack), new ItemStack(Blocks.CARPET, 1, 15))
                                .duration(100)
                                .EUt(8)
                                .buildAndRegister();
                        break;
                    case "RedAlloy":
                        ModHandler.addShapelessCraftingRecipe(OreDictUnifier.get(OrePrefix.cableGt01, uEntry.material), aOreDictName, OreDictUnifier.get(OrePrefix.plate, Materials.Paper));
                        RecipeMap.BOXINATOR_RECIPES.recipeBuilder()
                                .inputs(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.plate, Materials.Paper))
                                .outputs(OreDictUnifier.get(OrePrefix.cableGt01, uEntry.material))
                                .duration(100)
                                .EUt(8)
                                .buildAndRegister();
                        RecipeMap.UNBOXINATOR_RECIPES.recipeBuilder()
                                .inputs(OreDictUnifier.get(OrePrefix.cableGt01, uEntry.material))
                                .outputs(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.plate, Materials.Paper))
                                .duration(100)
                                .EUt(8)
                                .buildAndRegister();
                        break;
                    default:
                        GTValues.RA.addAssemblerRecipe(stack, ItemList.Circuit_Integrated.getWithDamage(0L, 24L, new Object[0]), Materials.Rubber.getMolten(144L), OreDictUnifier.get(OrePrefix.cableGt01, uEntry.material, 1L), 100, 8);
                        GTValues.RA.addUnboxingRecipe(OreDictUnifier.get(OrePrefix.cableGt01, uEntry.material, 1L), GTUtility.copyAmount(1L, new Object[]{stack}), OreDictUnifier.get(OrePrefix.plate, Materials.Rubber, 1L), 100, 8);
                }
                if (!uEntry.material.hasFlag(DustMaterial.MatFlags.NO_SMASHING)) {
                    GTValues.RA.addBenderRecipe(GTUtility.copyAmount(1L, new Object[]{stack}), OreDictUnifier.get(OrePrefix.springSmall, uEntry.material, 2L), 100, 8);
                    GTValues.RA.addWiremillRecipe(GTUtility.copyAmount(1L, new Object[]{stack}), OreDictUnifier.get(OrePrefix.wireFine, uEntry.material, 4L), 200, 8);
                    GTValues.RA.addWiremillRecipe(OreDictUnifier.get(OrePrefix.ingot, uEntry.material, 1L), GTUtility.copy(new Object[]{GTUtility.copyAmount(2L, stack), OreDictUnifier.get(OrePrefix.wireFine, uEntry.material, 8L)}), 100, 4);
                    GTValues.RA.addWiremillRecipe(OreDictUnifier.get(OrePrefix.stick, uEntry.material, 1L), GTUtility.copy(new Object[]{stack, OreDictUnifier.get(OrePrefix.wireFine, uEntry.material, 4L)}), 50, 4);
                }
                if (uEntry.material.mUnificatable && (uEntry.material.mMaterialInto == uEntry.material) && !uEntry.material.hasFlag(DustMaterial.MatFlags.NO_WORKING)) {
                    ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.wireGt01, uEntry.material), CommonProxy.tBits, "Xx", Character.valueOf('X'), OreDictUnifier.get(OrePrefix.plate, uEntry.material));
                }
                RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(2, stack), ItemList.Circuit_Integrated.getWithDamage(0, 2))
                        .outputs(OreDictUnifier.get(OrePrefix.wireGt02, uEntry.material))
                        .duration(150)
                        .EUt(8)
                        .buildAndRegister();
                RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(4, stack), ItemList.Circuit_Integrated.getWithDamage(0, 4))
                        .outputs(OreDictUnifier.get(OrePrefix.wireGt04, uEntry.material))
                        .duration(200)
                        .EUt(8).buildAndRegister();
                RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(8, stack), ItemList.Circuit_Integrated.getWithDamage(0, 8))
                        .outputs(OreDictUnifier.get(OrePrefix.wireGt08, uEntry.material))
                        .duration(300)
                        .EUt(8)
                        .buildAndRegister();
                RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(12, stack), ItemList.Circuit_Integrated.getWithDamage(0, 12))
                        .outputs(OreDictUnifier.get(OrePrefix.wireGt12, uEntry.material))
                        .duration(400)
                        .EUt(8)
                        .buildAndRegister();
                RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(16, stack), ItemList.Circuit_Integrated.getWithDamage(0, 16))
                        .outputs(OreDictUnifier.get(OrePrefix.wireGt16, uEntry.material))
                        .duration(500)
                        .EUt(8)
                        .buildAndRegister();

//                TODO
//                if (GregTechMod.gregtechproxy.mAE2Integration) {
//                    Api.INSTANCE.registries().p2pTunnel().addNewAttunement(stack, TunnelType.IC2_POWER);
//                    Api.INSTANCE.registries().p2pTunnel().addNewAttunement(OreDictUnifier.get(OrePrefix.cableGt01, uEntry.material, 1L), TunnelType.IC2_POWER);
//                }
                break;
            case wireGt02:
                switch (uEntry.material.toString()){
                    case "Cobalt": case "Lead": case "Tin": case "Zinc":case "SolderingAlloy":
                        ModHandler.addShapelessCraftingRecipe(OreDictUnifier.get(OrePrefix.cableGt02, uEntry.material, 1L), new Object[]{aOreDictName, new ItemStack(Blocks.CARPET, 1, 15), new ItemStack(Items.STRING, 1)});
                        GTValues.RA.addBoxingRecipe(GTUtility.copyAmount(1L, new Object[]{stack}), new ItemStack(Blocks.CARPET, 1, 15), OreDictUnifier.get(OrePrefix.cableGt02, uEntry.material, 1L), 100, 8);
                        GTValues.RA.addUnboxingRecipe(OreDictUnifier.get(OrePrefix.cableGt02, uEntry.material, 1L), GTUtility.copyAmount(1L, new Object[]{stack}), new ItemStack(Blocks.CARPET, 1, 15), 100, 8);
                        break;
                    case "RedAlloy":
                        ModHandler.addShapelessCraftingRecipe(OreDictUnifier.get(OrePrefix.cableGt02, uEntry.material, 1L), new Object[]{aOreDictName, OrePrefix.plate.get(Materials.Paper)});
                        GTValues.RA.addBoxingRecipe(GTUtility.copyAmount(1L, new Object[]{stack}), OreDictUnifier.get(OrePrefix.plate, Materials.Paper, 1L), OreDictUnifier.get(OrePrefix.cableGt02, uEntry.material, 1L), 100, 8);
                        GTValues.RA.addUnboxingRecipe(OreDictUnifier.get(OrePrefix.cableGt02, uEntry.material, 1L), GTUtility.copyAmount(1L, new Object[]{stack}), OreDictUnifier.get(OrePrefix.plate, Materials.Paper, 1L), 100, 8);
                        break;
                    default:
                        GTValues.RA.addAssemblerRecipe(stack, ItemList.Circuit_Integrated.getWithDamage(0L, 24L, new Object[0]), Materials.Rubber.getMolten(144L), OreDictUnifier.get(OrePrefix.cableGt02, uEntry.material, 1L), 100, 8);
                        GTValues.RA.addUnboxingRecipe(OreDictUnifier.get(OrePrefix.cableGt02, uEntry.material, 1L), GTUtility.copyAmount(1L, new Object[]{stack}), OreDictUnifier.get(OrePrefix.plate, Materials.Rubber, 1L), 100, 8);
                }
                ModHandler.addShapelessCraftingRecipe(OreDictUnifier.get(OrePrefix.wireGt01, uEntry.material, 2), aOreDictName);
                ModHandler.addShapelessCraftingRecipe(GTUtility.copyAmount(1, stack), new Object[]{OrePrefix.wireGt01.get(uEntry.material), OrePrefix.wireGt01.get(uEntry.material)});
//                TODO
//                if (GregTechMod.gregtechproxy.mAE2Integration) {
//                    Api.INSTANCE.registries().p2pTunnel().addNewAttunement(stack, TunnelType.IC2_POWER);
//                    Api.INSTANCE.registries().p2pTunnel().addNewAttunement(OreDictUnifier.get(OrePrefix.cableGt02, uEntry.material, 1L), TunnelType.IC2_POWER);
//                }
                break;
            case wireGt04:
                switch (uEntry.material.toString()){
                    case "Cobalt": case "Lead": case "Tin": case "Zinc":case "SolderingAlloy":
                        ModHandler.addShapelessCraftingRecipe(OreDictUnifier.get(OrePrefix.cableGt04, uEntry.material, 1L), new Object[]{aOreDictName, new ItemStack(Blocks.CARPET, 1, 15), new ItemStack(Blocks.CARPET, 1, 15), new ItemStack(Items.STRING, 1)});
                        GTValues.RA.addBoxingRecipe(GTUtility.copyAmount(1L, new Object[]{stack}), new ItemStack(Blocks.CARPET, 2, 15), OreDictUnifier.get(OrePrefix.cableGt04, uEntry.material, 1L), 100, 8);
                        GTValues.RA.addUnboxingRecipe(OreDictUnifier.get(OrePrefix.cableGt04, uEntry.material, 1L), GTUtility.copyAmount(1L, new Object[]{stack}), new ItemStack(Blocks.CARPET, 2, 15), 100, 8);
                        break;
                    case "RedAlloy":
                        ModHandler.addShapelessCraftingRecipe(OreDictUnifier.get(OrePrefix.cableGt04, uEntry.material, 1L), new Object[]{aOreDictName, OrePrefix.plate.get(Materials.Paper), OrePrefix.plate.get(Materials.Paper)});
                        GTValues.RA.addBoxingRecipe(GTUtility.copyAmount(1L, new Object[]{stack}), OreDictUnifier.get(OrePrefix.plate, Materials.Paper, 2L), OreDictUnifier.get(OrePrefix.cableGt04, uEntry.material, 1L), 100, 8);
                        GTValues.RA.addUnboxingRecipe(OreDictUnifier.get(OrePrefix.cableGt04, uEntry.material, 1L), GTUtility.copyAmount(1L, new Object[]{stack}), OreDictUnifier.get(OrePrefix.plate, Materials.Paper, 2L), 100, 8);
                        break;
                    default:
                        GTValues.RA.addAssemblerRecipe(stack, ItemList.Circuit_Integrated.getWithDamage(0L, 24L, new Object[0]), Materials.Rubber.getMolten(288L), OreDictUnifier.get(OrePrefix.cableGt04, uEntry.material, 1L), 100, 8);
                        GTValues.RA.addUnboxingRecipe(OreDictUnifier.get(OrePrefix.cableGt04, uEntry.material, 1L), GTUtility.copyAmount(1L, new Object[]{stack}), OreDictUnifier.get(OrePrefix.plate, Materials.Rubber, 2L), 100, 8);
                }
                ModHandler.addShapelessCraftingRecipe(OreDictUnifier.get(OrePrefix.wireGt01, uEntry.material, 4L), new Object[]{aOreDictName});
                ModHandler.addShapelessCraftingRecipe(GTUtility.copyAmount(1L, new Object[]{stack}), new Object[]{OrePrefix.wireGt02.get(uEntry.material), OrePrefix.wireGt02.get(uEntry.material)});
//                TODO
//                if (GregTechMod.gregtechproxy.mAE2Integration) {
//                    Api.INSTANCE.registries().p2pTunnel().addNewAttunement(stack, TunnelType.IC2_POWER);
//                    Api.INSTANCE.registries().p2pTunnel().addNewAttunement(OreDictUnifier.get(OrePrefix.cableGt04, uEntry.material, 1L), TunnelType.IC2_POWER);
//                }
                break;
            case wireGt08:
                switch (uEntry.material.toString()){
                    case "Cobalt": case "Lead": case "Tin": case "Zinc":case "SolderingAlloy":
                        ModHandler.addShapelessCraftingRecipe(OreDictUnifier.get(OrePrefix.cableGt08, uEntry.material, 1L), new Object[]{aOreDictName, new ItemStack(Blocks.CARPET, 1, 15), new ItemStack(Blocks.CARPET, 1, 15), new ItemStack(Blocks.CARPET, 1, 15), new ItemStack(Items.STRING, 1)});
                        GTValues.RA.addBoxingRecipe(GTUtility.copyAmount(1L, new Object[]{stack}), new ItemStack(Blocks.CARPET, 3, 15), OreDictUnifier.get(OrePrefix.cableGt08, uEntry.material, 1L), 100, 8);
                        GTValues.RA.addUnboxingRecipe(OreDictUnifier.get(OrePrefix.cableGt08, uEntry.material, 1L), GTUtility.copyAmount(1L, new Object[]{stack}), new ItemStack(Blocks.CARPET, 3, 15), 100, 8);
                        break;
                    case "RedAlloy":
                        ModHandler.addShapelessCraftingRecipe(OreDictUnifier.get(OrePrefix.cableGt08, uEntry.material, 1L), new Object[]{aOreDictName, OrePrefix.plate.get(Materials.Paper), OrePrefix.plate.get(Materials.Paper), OrePrefix.plate.get(Materials.Paper)});
                        GTValues.RA.addBoxingRecipe(GTUtility.copyAmount(1L, new Object[]{stack}), OreDictUnifier.get(OrePrefix.plate, Materials.Paper, 3L), OreDictUnifier.get(OrePrefix.cableGt08, uEntry.material, 1L), 100, 8);
                        GTValues.RA.addUnboxingRecipe(OreDictUnifier.get(OrePrefix.cableGt08, uEntry.material, 1L), GTUtility.copyAmount(1L, new Object[]{stack}), OreDictUnifier.get(OrePrefix.plate, Materials.Paper, 3L), 100, 8);
                        break;
                    default:
                        GTValues.RA.addAssemblerRecipe(stack, ItemList.Circuit_Integrated.getWithDamage(0L, 24L, new Object[0]), Materials.Rubber.getMolten(432L), OreDictUnifier.get(OrePrefix.cableGt08, uEntry.material, 1L), 100, 8);
                        GTValues.RA.addUnboxingRecipe(OreDictUnifier.get(OrePrefix.cableGt08, uEntry.material, 1L), GTUtility.copyAmount(1L, new Object[]{stack}), OreDictUnifier.get(OrePrefix.plate, Materials.Rubber, 3L), 100, 8);
                }
                ModHandler.addShapelessCraftingRecipe(OreDictUnifier.get(OrePrefix.wireGt01, uEntry.material, 8), aOreDictName);
                ModHandler.addShapelessCraftingRecipe(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.wireGt04, uEntry.material), OreDictUnifier.get(OrePrefix.wireGt04, uEntry.material));
//                TODO
//                if (GregTechMod.gregtechproxy.mAE2Integration) {
//                    Api.INSTANCE.registries().p2pTunnel().addNewAttunement(stack, TunnelType.IC2_POWER);
//                    Api.INSTANCE.registries().p2pTunnel().addNewAttunement(OreDictUnifier.get(OrePrefix.cableGt08, uEntry.material, 1L), TunnelType.IC2_POWER);
//                }
                break;
            case wireGt12:
                switch (uEntry.material.toString()){
                    case "Cobalt": case "Lead": case "Tin": case "Zinc":case "SolderingAlloy":
                        ModHandler.addShapelessCraftingRecipe(OreDictUnifier.get(OrePrefix.cableGt12, uEntry.material, 1L), new Object[]{aOreDictName, new ItemStack(Blocks.CARPET, 1, 15), new ItemStack(Blocks.CARPET, 1, 15), new ItemStack(Blocks.CARPET, 1, 15), new ItemStack(Blocks.CARPET, 1, 15), new ItemStack(Items.STRING, 1)});
                        GTValues.RA.addBoxingRecipe(GTUtility.copyAmount(1L, new Object[]{stack}), new ItemStack(Blocks.CARPET, 4, 15), OreDictUnifier.get(OrePrefix.cableGt12, uEntry.material, 1L), 100, 8);
                        GTValues.RA.addUnboxingRecipe(OreDictUnifier.get(OrePrefix.cableGt12, uEntry.material, 1L), GTUtility.copyAmount(1L, new Object[]{stack}), new ItemStack(Blocks.CARPET, 4, 15), 100, 8);
                        break;
                    case "RedAlloy":
                        ModHandler.addShapelessCraftingRecipe(OreDictUnifier.get(OrePrefix.cableGt12, uEntry.material, 1L), new Object[]{aOreDictName, OrePrefix.plate.get(Materials.Paper), OrePrefix.plate.get(Materials.Paper), OrePrefix.plate.get(Materials.Paper), OrePrefix.plate.get(Materials.Paper)});
                        GTValues.RA.addBoxingRecipe(GTUtility.copyAmount(1L, new Object[]{stack}), OreDictUnifier.get(OrePrefix.plate, Materials.Paper, 4L), OreDictUnifier.get(OrePrefix.cableGt12, uEntry.material, 1L), 100, 8);
                        GTValues.RA.addUnboxingRecipe(OreDictUnifier.get(OrePrefix.cableGt12, uEntry.material, 1L), GTUtility.copyAmount(1L, new Object[]{stack}), OreDictUnifier.get(OrePrefix.plate, Materials.Paper, 4L), 100, 8);
                        break;
                    default:
                        GTValues.RA.addAssemblerRecipe(stack, ItemList.Circuit_Integrated.getWithDamage(0L, 24L, new Object[0]), Materials.Rubber.getMolten(576L), OreDictUnifier.get(OrePrefix.cableGt12, uEntry.material, 1L), 100, 8);
                        GTValues.RA.addUnboxingRecipe(OreDictUnifier.get(OrePrefix.cableGt12, uEntry.material, 1L), GTUtility.copyAmount(1L, new Object[]{stack}), OreDictUnifier.get(OrePrefix.plate, Materials.Rubber, 4L), 100, 8);
                }
                ModHandler.addShapelessCraftingRecipe(OreDictUnifier.get(OrePrefix.wireGt01, uEntry.material, 12), aOreDictName);
                ModHandler.addShapelessCraftingRecipe(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.wireGt08, uEntry.material), OreDictUnifier.get(OrePrefix.wireGt04, uEntry.material));
//                TODO
//                if (GregTechMod.gregtechproxy.mAE2Integration) {
//                    Api.INSTANCE.registries().p2pTunnel().addNewAttunement(stack, TunnelType.IC2_POWER);
//                    Api.INSTANCE.registries().p2pTunnel().addNewAttunement(OreDictUnifier.get(OrePrefix.cableGt12, uEntry.material, 1L), TunnelType.IC2_POWER);
//                }
                break;
            case wireGt16:
                ModHandler.addShapelessCraftingRecipe(OreDictUnifier.get(OrePrefix.wireGt01, uEntry.material, 16), aOreDictName);
                ModHandler.addShapelessCraftingRecipe(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.wireGt08, uEntry.material), OreDictUnifier.get(OrePrefix.wireGt08, uEntry.material));
                ModHandler.addShapelessCraftingRecipe(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.wireGt12, uEntry.material), OreDictUnifier.get(OrePrefix.wireGt12, uEntry.material));
//                TODO
//                if (GregTechMod.gregtechproxy.mAE2Integration) {
//                    Api.INSTANCE.registries().p2pTunnel().addNewAttunement(stack, TunnelType.IC2_POWER);
//                }
                break;
        }
    }
}
