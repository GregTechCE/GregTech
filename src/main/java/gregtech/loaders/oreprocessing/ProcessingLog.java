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

    public void register() {
        OrePrefix.log.addProcessingHandler(this);
    }
    
    public void registerOre(UnificationEntry entry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();

        if (entry.material == Materials.Rubber) {

			RecipeMap.CENTRIFUGE_RECIPES.recipeBuilder()
					.inputs(GTUtility.copyAmount(1, stack))
					.chancedOutput(ModHandler.IC2.getIC2Item(ItemName.misc_resource, MiscResourceType.resin, 1), 5000)
					.chancedOutput(ModHandler.IC2.getIC2Item(ItemName.crafting, CraftingItemType.plant_ball, 1), 3750)
					.chancedOutput(OreDictUnifier.get(OrePrefix.dust, Materials.Carbon), 2500)
					.chancedOutput(OreDictUnifier.get(OrePrefix.dust, Materials.Wood), 2500)
					.fluidOutputs(Materials.Methane.getFluid(60))
					.duration(200)
					.EUt(20)
					.buildAndRegister();

			RecipeMap.EXTRACTOR_RECIPES.recipeBuilder()
					.inputs(GTUtility.copyAmount(1, stack))
					.outputs(OreDictUnifier.get(OrePrefix.dust, Materials.RawRubber))
					.buildAndRegister();
			RecipeMap.MACERATOR_RECIPES.recipeBuilder()
					.inputs(GTUtility.copyAmount(1, stack))
					.outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Wood, 6))
					.chancedOutput(ModHandler.IC2.getIC2Item(ItemName.misc_resource, MiscResourceType.resin, 1), 3300)
					.buildAndRegister();
        } else {
			RecipeMap.MACERATOR_RECIPES.recipeBuilder()
					.inputs(GTUtility.copyAmount(1, stack))
					.outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Wood, 6))
					.chancedOutput(OreDictUnifier.get(OrePrefix.dust, Materials.Wood), 8000)
					.buildAndRegister();
        }

        ModHandler.addShapedRecipe(OreDictUnifier.get(OrePrefix.stickLong, Materials.Wood, 2),
				"sLf",
				'L', GTUtility.copyAmount(1, stack));

        RecipeMap.LATHE_RECIPES.recipeBuilder()
				.inputs(GTUtility.copyAmount(1, stack))
				.outputs(OreDictUnifier.get(OrePrefix.stickLong, Materials.Wood, 4), OreDictUnifier.get(OrePrefix.dust, Materials.Wood, 2))
				.duration(160)
				.EUt(8)
				.buildAndRegister();
        RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
				.inputs(GTUtility.copyAmount(1, stack))
				.circuitMeta(2)
				.fluidInputs(Materials.SeedOil.getFluid(50))
				.outputs(ModHandler.getModItem("forestry", "oakStick", 1))
				.buildAndRegister();

		RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
				.inputs(GTUtility.copyAmount(8, stack))
				.circuitMeta(8)
				.fluidInputs(Materials.SeedOil.getFluid(250))
				.outputs(ModHandler.getModItem("forestry", "impregnatedCasing", 1))
				.duration(64)
				.EUt(16)
				.buildAndRegister();

		RecipeMap.CHEMICAL_BATH_RECIPES.recipeBuilder()
				.inputs(GTUtility.copyAmount(1, stack))
				.fluidInputs(Materials.Creosote.getFluid(1000))
				.outputs(ModHandler.getModItem("Railcraft", "tile.railcraft.cube", 1, 8)) //TODO CHECK ITEM ID IN 1.10...
				.duration(16)
				.EUt(16)
				.buildAndRegister();

        short meta = (short) stack.getItemDamage();

        if (meta == Short.MAX_VALUE) {
            if (ItemStack.areItemStacksEqual(ModHandler.getSmeltingOutput(stack), new ItemStack(Items.COAL, 1, 1))) {
            	RecipeMap.PYROLYSE_RECIPES.recipeBuilder()
						.inputs(GTUtility.copyAmount(16, stack))
						.circuitMeta(1)
						.outputs(new ItemStack(Items.COAL, 20, 1))
						.fluidOutputs(Materials.Creosote.getFluid(4000))
						.duration(640)
						.EUt(64)
						.buildAndRegister();
				RecipeMap.PYROLYSE_RECIPES.recipeBuilder()
						.inputs(GTUtility.copyAmount(16, stack))
						.circuitMeta(2)
						.fluidInputs(Materials.Nitrogen.getFluid(1000))
						.outputs(new ItemStack(Items.COAL, 20, 1))
						.fluidInputs(Materials.Creosote.getFluid(4000))
						.duration(320)
						.EUt(96)
						.buildAndRegister();
				RecipeMap.PYROLYSE_RECIPES.recipeBuilder()
						.inputs(GTUtility.copyAmount(16, stack))
						.circuitMeta(3)
						.outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Ash, 4))
						.fluidOutputs(Materials.OilHeavy.getFluid(200))
						.duration(322)
						.EUt(192)
						.buildAndRegister();
				ModHandler.removeFurnaceSmelting(GTUtility.copyAmount(1, stack));
            }
            for (int i = 0; i < 32767; i++) { // FIXME
                if (ItemStack.areItemStacksEqual(ModHandler.getSmeltingOutput(new ItemStack(stack.getItem(), 1, i)), new ItemStack(Items.COAL, 1, 1))) {
					RecipeMap.PYROLYSE_RECIPES.recipeBuilder()
							.inputs(GTUtility.copyAmount(16, stack))
							.circuitMeta(1)
							.outputs(new ItemStack(Items.COAL, 20, 1))
							.fluidOutputs(Materials.Creosote.getFluid(4000))
							.duration(640)
							.EUt(64)
							.buildAndRegister();
					RecipeMap.PYROLYSE_RECIPES.recipeBuilder()
							.inputs(GTUtility.copyAmount(16, stack))
							.circuitMeta(2)
							.fluidInputs(Materials.Nitrogen.getFluid(1000))
							.outputs(new ItemStack(Items.COAL, 20, 1))
							.fluidInputs(Materials.Creosote.getFluid(4000))
							.duration(320)
							.EUt(96)
							.buildAndRegister();
					RecipeMap.PYROLYSE_RECIPES.recipeBuilder()
							.inputs(GTUtility.copyAmount(16, stack))
							.circuitMeta(3)
							.outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Ash, 4))
							.fluidOutputs(Materials.OilHeavy.getFluid(200))
							.duration(322)
							.EUt(192)
							.buildAndRegister();
					ModHandler.removeFurnaceSmelting(new ItemStack(stack.getItem(), 1, i));
                }
                ItemStack itemStack = ModHandler.getRecipeOutput(GTValues.DW, new ItemStack(stack.getItem(), 1, i));
                if (itemStack == null) {
					if (i >= 16) {
						break;
					}
				} else {
					ItemStack planks = GTUtility.copy(itemStack);
                    planks.stackSize = planks.stackSize * 3 / 2;
                    RecipeMap.CUTTER_RECIPES.recipeBuilder()
							.inputs(new ItemStack(stack.getItem(), 1, i))
							.fluidInputs(Materials.Lubricant.getFluid(1))
							.outputs(GTUtility.copy(planks), OreDictUnifier.get(OrePrefix.dust, Materials.Wood))
							.duration(200)
							.EUt(8)
							.buildAndRegister();
					RecipeMap.CUTTER_RECIPES.recipeBuilder()
							.inputs(new ItemStack(stack.getItem(), 1, i))
							.outputs(GTUtility.copyAmount(itemStack.stackSize * 5 / 4, itemStack), OreDictUnifier.get(OrePrefix.dust, Materials.Wood, 2))
							.duration(200)
							.EUt(8)
							.buildAndRegister();

                    ModHandler.removeRecipe(new ItemStack(stack.getItem(), 1, i));
                    ModHandler.addShapedRecipe(GTUtility.copyAmount(itemStack.stackSize * 5 / 4, itemStack),
							"s",
							"L",
							'L', new ItemStack(stack.getItem(), 1, i));
                    ModHandler.addShapelessRecipe(GTUtility.copyAmount(itemStack.stackSize),
							itemStack, new ItemStack(stack.getItem(), 1, i));
                }
            }
        } else {
			if (ItemStack.areItemStacksEqual(ModHandler.getSmeltingOutput(GTUtility.copyAmount(1, stack)), new ItemStack(Items.COAL, 1, 1))) {
				RecipeMap.PYROLYSE_RECIPES.recipeBuilder()
						.inputs(GTUtility.copyAmount(16, stack))
						.circuitMeta(1)
						.outputs(new ItemStack(Items.COAL, 20, 1))
						.fluidOutputs(Materials.Creosote.getFluid(4000))
						.duration(640)
						.EUt(64)
						.buildAndRegister();
				RecipeMap.PYROLYSE_RECIPES.recipeBuilder()
						.inputs(GTUtility.copyAmount(16, stack))
						.circuitMeta(2)
						.fluidInputs(Materials.Nitrogen.getFluid(1000))
						.outputs(new ItemStack(Items.COAL, 20, 1))
						.fluidInputs(Materials.Creosote.getFluid(4000))
						.duration(320)
						.EUt(96)
						.buildAndRegister();
				RecipeMap.PYROLYSE_RECIPES.recipeBuilder()
						.inputs(GTUtility.copyAmount(16, stack))
						.circuitMeta(3)
						.outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Ash, 4))
						.fluidOutputs(Materials.OilHeavy.getFluid(200))
						.duration(322)
						.EUt(192)
						.buildAndRegister();
				ModHandler.removeFurnaceSmelting(GTUtility.copyAmount(1, stack));
			}
			ItemStack itemStack = ModHandler.getRecipeOutput(GTValues.DW, GTUtility.copyAmount(1, stack));
			if (itemStack == null) {
				ItemStack planks = GTUtility.copy(itemStack);
				planks.stackSize = planks.stackSize * 3 / 2;
				RecipeMap.CUTTER_RECIPES.recipeBuilder()
						.inputs(GTUtility.copyAmount(1, stack))
						.fluidInputs(Materials.Lubricant.getFluid(1))
						.outputs(GTUtility.copy(planks), OreDictUnifier.get(OrePrefix.dust, Materials.Wood))
						.duration(200)
						.EUt(8)
						.buildAndRegister();
				RecipeMap.CUTTER_RECIPES.recipeBuilder()
						.inputs(GTUtility.copyAmount(1, stack))
						.outputs(GTUtility.copyAmount(itemStack.stackSize * 5 / 4, itemStack), OreDictUnifier.get(OrePrefix.dust, Materials.Wood, 2))
						.duration(200)
						.EUt(8)
						.buildAndRegister();

				ModHandler.removeRecipe(GTUtility.copyAmount(1, stack));
				ModHandler.addShapedRecipe(GTUtility.copyAmount(itemStack.stackSize * 5 / 4, itemStack),
						"s",
						"L",
						'L', GTUtility.copyAmount(1, stack));
				ModHandler.addShapelessRecipe(GTUtility.copyAmount(itemStack.stackSize),
						itemStack, GTUtility.copyAmount(1, stack));
			}
        }
    }
}
