package gregtech.loaders.oreprocessing;

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
import ic2.core.ref.ItemName;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import static gregtech.api.GTValues.L;

public class ProcessingStone implements IOreRegistrationHandler {

    private ProcessingStone() { }

    private static void register() {
        ProcessingStone processing = new ProcessingStone();
        OrePrefix.stone.addProcessingHandler(processing);
        OrePrefix.stoneCobble.addProcessingHandler(processing);
        OrePrefix.stoneSmooth.addProcessingHandler(processing);
    }

    public void registerOre(UnificationEntry entry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();

        switch (entry.orePrefix) {
            case stoneCobble:
                RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.stick, Materials.Wood))
                        .outputs(new ItemStack(Blocks.LEVER, 1))
                        .duration(400)
                        .EUt(1)
                        .buildAndRegister();
                RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(8, stack))
                        .circuitMeta(8)
                        .outputs(new ItemStack(Blocks.FURNACE, 1))
                        .duration(400)
                        .EUt(4)
                        .buildAndRegister();
                RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(7, stack), OreDictUnifier.get(OrePrefix.dust, Materials.Redstone))
                        .outputs(new ItemStack(Blocks.DROPPER, 1))
                        .duration(400)
                        .EUt(4)
                        .buildAndRegister();
                RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(7, stack), new ItemStack(Items.BOW, 1, 0)).fluidInputs(Materials.Redstone.getFluid(144))
                        .outputs(new ItemStack(Blocks.DISPENSER, 1))
                        .duration(400)
                        .EUt(4)
                        .buildAndRegister();
                break;
            case stoneSmooth:
                RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack))
                        .circuitMeta(1)
                        .outputs(new ItemStack(Blocks.STONE_BUTTON, 1))
                        .duration(100)
                        .EUt(4)
                        .buildAndRegister();
                RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(2, stack))
                        .circuitMeta(2)
                        .outputs(new ItemStack(Blocks.STONE_PRESSURE_PLATE, 1))
                        .duration(200)
                        .EUt(4)
                        .buildAndRegister();
                break;
            case stone:

                if (entry.material == null) {
                    RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(3, stack), new ItemStack(Blocks.REDSTONE_TORCH, 2))
                            .fluidInputs(Materials.Redstone.getFluid(144))
                            .outputs(new ItemStack(Items.REPEATER, 1))
                            .duration(100)
                            .EUt(4)
                            .buildAndRegister();
                }  else if (entry.material == Materials.Endstone) {
                    RecipeMap.MACERATOR_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(1, stack))
                            .outputs(OreDictUnifier.get(OrePrefix.dustImpure, Materials.Endstone, 1))
                            .chancedOutput(OreDictUnifier.get(OrePrefix.dustTiny, Materials.Tungsten, 1), 500)
                            .buildAndRegister();
                } else if (entry.material == Materials.Netherrack) {
                    RecipeMap.MACERATOR_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(1, stack))
                            .outputs(OreDictUnifier.get(OrePrefix.dustImpure, Materials.Netherrack, 1))
                            .chancedOutput(OreDictUnifier.get(OrePrefix.nugget, Materials.Gold, 1), 500)
                            .buildAndRegister();
                } else if (entry.material == Materials.Obsidian) {
                    RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                            .inputs(OreDictUnifier.get(OrePrefix.dust, Materials.Redstone), GTUtility.copyAmount(5, stack))
                            .fluidInputs(Materials.Glass.getFluid(L / 2))
                            .outputs(ModHandler.getModItem("Forestry", "thermionicTubes", 4, 6))
                            .duration(64)
                            .EUt(32)
                            .buildAndRegister();

                    RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                            .inputs(OreDictUnifier.get(OrePrefix.gem, Materials.NetherStar), GTUtility.copyAmount(3, stack))
                            .fluidInputs(Materials.Glass.getFluid(720)).outputs(new ItemStack(Blocks.BEACON, 1, 0))
                            .duration(32)
                            .EUt(16)
                            .buildAndRegister();

                    RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(1, stack), ModHandler.IC2.getIC2Item(ItemName.crafting, CraftingItemType.coal_ball, 8))
                            .outputs(ModHandler.IC2.getIC2Item(ItemName.crafting, CraftingItemType.coal_chunk, 1))
                            .duration(400)
                            .EUt(4)
                            .buildAndRegister();

                    RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(8, stack), OreDictUnifier.get(OrePrefix.gem, Materials.EnderEye))
                            .outputs(new ItemStack(Blocks.ENDER_CHEST, 1))
                            .duration(400)
                            .EUt(4)
                            .buildAndRegister();

                    RecipeMap.CUTTER_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(1, stack))
                            .outputs(OreDictUnifier.get(OrePrefix.plate, entry.material, 1))
                            .duration(200)
                            .EUt(32)
                            .buildAndRegister();

                } else if (entry.material == Materials.Concrete) {
                    RecipeMap.MACERATOR_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(1, stack))
                            .outputs(OreDictUnifier.get(OrePrefix.dust, entry.material))
                            .buildAndRegister();
                    RecipeMap.CUTTER_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(1, stack))
                            .outputs(OreDictUnifier.get(OrePrefix.plate, entry.material, 1))
                            .duration(200)
                            .EUt(32)
                            .buildAndRegister();
                } else if (entry.material == Materials.Soapstone) {
                    RecipeMap.MACERATOR_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(1, stack))
                            .outputs(OreDictUnifier.get(OrePrefix.dustImpure, Materials.Talc, 1))
                            .chancedOutput(OreDictUnifier.get(OrePrefix.dustTiny, Materials.Chromite, 1), 1000)
                            .buildAndRegister();
                } else if (entry.material == Materials.Redrock
                        || entry.material == Materials.Marble
                        || entry.material == Materials.Basalt
                        || entry.material == Materials.Quartzite) {
                    RecipeMap.MACERATOR_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(1, stack))
                            .outputs(OreDictUnifier.get(OrePrefix.dustImpure, entry.material, 1))
                            .chancedOutput(OreDictUnifier.get(OrePrefix.dust, entry.material, 1), 1000)
                            .buildAndRegister();
                } else if (entry.material == Materials.Flint) {
                    RecipeMap.MACERATOR_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(1, stack))
                            .outputs(OreDictUnifier.get(OrePrefix.dustImpure, entry.material, 2))
                            .chancedOutput(new ItemStack(Items.FLINT, 1), 5000)
                            .buildAndRegister();
                } else if (entry.material == Materials.GraniteBlack) {
                    RecipeMap.CUTTER_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(1, stack))
                            .outputs(OreDictUnifier.get(OrePrefix.plate, entry.material, 1))
                            .duration(200)
                            .EUt(32)
                            .buildAndRegister();
                    RecipeMap.MACERATOR_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(1, stack))
                            .outputs(OreDictUnifier.get(OrePrefix.dustImpure, entry.material, 1))
                            .chancedOutput(OreDictUnifier.get(OrePrefix.dust, Materials.Thorium, 1), 100)
                            .buildAndRegister();
                } else if (entry.material == Materials.GraniteRed) {
                    RecipeMap.CUTTER_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(1, stack))
                            .outputs(OreDictUnifier.get(OrePrefix.plate, entry.material, 1))
                            .duration(200)
                            .EUt(32)
                            .buildAndRegister();
                    RecipeMap.MACERATOR_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(1, stack))
                            .outputs(OreDictUnifier.get(OrePrefix.dustImpure, entry.material, 1))
                            .chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Uranium, 1), 100)
                            .buildAndRegister();
                    RecipeMap.CUTTER_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(1, stack))
                            .outputs(OreDictUnifier.get(OrePrefix.plate, entry.material, 1))
                            .duration(200)
                            .EUt(32)
                            .buildAndRegister();
                    RecipeMap.MACERATOR_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(1, stack))
                            .outputs(OreDictUnifier.get(OrePrefix.dustImpure, entry.material, 1))
                            .chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Stone, 1), 100)
                            .buildAndRegister();
                } else if (entry.material == Materials.Andesite || entry.material == Materials.Diorite) {
                    RecipeMap.CUTTER_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(1, stack))
                            .outputs(OreDictUnifier.get(OrePrefix.plate, entry.material, 1))
                            .duration(200)
                            .EUt(32)
                            .buildAndRegister();
                    RecipeMap.MACERATOR_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(1, stack))
                            .outputs(OreDictUnifier.get(OrePrefix.dustImpure, entry.material, 1))
                            .chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Stone, 1), 100)
                            .buildAndRegister();
                }
                break;
        }
    }
}
