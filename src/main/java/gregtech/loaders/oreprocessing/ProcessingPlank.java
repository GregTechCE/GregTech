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
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import static gregtech.api.GTValues.W;

public class ProcessingPlank implements IOreRegistrationHandler {

    private ProcessingPlank() {}

    public static void register() {
        OrePrefix.plank.addProcessingHandler(new ProcessingPlank());
    }

    public void registerOre(UnificationEntry entry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();

        if (entry.material == Materials.Wood) {
			RecipeMap.LATHE_RECIPES.recipeBuilder()
					.inputs(GTUtility.copyAmount(1, stack))
					.outputs(OreDictUnifier.get(OrePrefix.stick, Materials.Wood, 2))
					.duration(10)
					.EUt(8)
					.buildAndRegister();

			RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
					.inputs(GTUtility.copyAmount(8, stack), OreDictUnifier.get(OrePrefix.dust, Materials.Redstone, 1))
					.outputs(new ItemStack(Blocks.NOTEBLOCK, 1))
					.duration(200)
					.EUt(4)
					.buildAndRegister();

			RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
					.inputs(GTUtility.copyAmount(8, stack), OreDictUnifier.get(OrePrefix.gem, Materials.Diamond, 1))
					.outputs(new ItemStack(Blocks.JUKEBOX, 1))
					.duration(400)
					.EUt(4)
					.buildAndRegister();

			RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
					.inputs(GTUtility.copyAmount(6, stack), new ItemStack(Items.BOOK, 3))
					.outputs(new ItemStack(Blocks.BOOKSHELF, 1))
					.duration(400)
					.EUt(4)
					.buildAndRegister();

			RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
					.inputs(GTUtility.copyAmount(1, stack))
					.circuitMeta(1)
					.outputs(new ItemStack(Blocks.WOODEN_BUTTON, 1))
					.duration(100)
					.EUt(4)
					.buildAndRegister();

			RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
					.inputs(GTUtility.copyAmount(2, stack))
					.circuitMeta(2)
					.outputs(new ItemStack(Blocks.WOODEN_PRESSURE_PLATE))
					.duration(200)
					.EUt(4)
					.buildAndRegister();

			RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
					.inputs(GTUtility.copyAmount(3, stack))
					.circuitMeta(3)
					.outputs(new ItemStack(Blocks.TRAPDOOR))
					.duration(300)
					.EUt(4)
					.buildAndRegister();

			RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
					.inputs(GTUtility.copyAmount(4, stack))
					.circuitMeta(4)
					.outputs(new ItemStack(Blocks.CRAFTING_TABLE))
					.duration(400)
					.EUt(4)
					.buildAndRegister();

			RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
					.inputs(GTUtility.copyAmount(6, stack))
					.circuitMeta(6)
					.outputs(new ItemStack(Items.OAK_DOOR))
					.duration(600)
					.EUt(4)
					.buildAndRegister();

			RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
					.inputs(GTUtility.copyAmount(8, stack))
					.circuitMeta(8)
					.outputs(new ItemStack(Blocks.CHEST, 1))
					.duration(800)
					.EUt(4)
					.buildAndRegister();

            if (stack.getItemDamage() == W) {
                for (byte i = 0; i < 64; i++) { // FIXME
                    ItemStack itemStack = stack.copy();
                    itemStack.setItemDamage(i);

                    ItemStack output = ModHandler.getRecipeOutput(null, itemStack, itemStack, itemStack);
                    if (!output.isEmpty() && output.getCount() >= 3) {

                        RecipeMap.CUTTER_RECIPES.recipeBuilder()
								.inputs(GTUtility.copyAmount(1, itemStack))
								.outputs(GTUtility.copyAmount(output.getCount() / 3, output))
								.duration(25)
								.EUt(4)
								.buildAndRegister();

//                        ModHandler.removeRecipe(itemStack, itemStack, itemStack);
                        ModHandler.addShapedRecipe("slab?_" + entry.material,
                                GTUtility.copyAmount(output.getCount() / 3, output),
								"sP",
								'P', itemStack);
                    }
                    if(itemStack.isEmpty() && i >= 16) break;
                }
            } else {
                ItemStack output = ModHandler.getRecipeOutput(null, stack, stack, stack);
                if (!output.isEmpty() && output.getCount() >= 3) {

					RecipeMap.CUTTER_RECIPES.recipeBuilder()
							.inputs(GTUtility.copyAmount(1, stack))
							.outputs(GTUtility.copyAmount(output.getCount() / 3, output))
							.duration(25)
							.EUt(4)
							.buildAndRegister();

//                    ModHandler.removeRecipe(stack, stack, stack);
                    ModHandler.addShapedRecipe("slab?_" + entry.material,
                        GTUtility.copyAmount(output.getCount() / 3, output),
							"sP",
							'P', stack);
                }
            }
        }
    }
}
