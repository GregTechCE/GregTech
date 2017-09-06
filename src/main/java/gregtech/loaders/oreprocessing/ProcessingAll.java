package gregtech.loaders.oreprocessing;

import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.ItemMaterialInfo;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import gregtech.common.items.MetaItems;
import net.minecraft.item.ItemStack;

import static gregtech.api.GTValues.M;

public class ProcessingAll implements IOreRegistrationHandler {

    public void register() {
        for (OrePrefix prefix : OrePrefix.values()) {
            prefix.addProcessingHandler(this);
        }
    }

    public void registerOre(UnificationEntry entry, String modName, SimpleItemStack simpleStack) {
		ItemStack stack = simpleStack.asItemStack();

		if (entry.orePrefix == OrePrefix.food && entry.material == Materials.Cheese) {
            RecipeMap.SLICER_RECIPES.recipeBuilder()
					.inputs(stack)
					.notConsumable(MetaItems.SHAPE_SLICER_FLAT)
					.outputs(MetaItems.FOOD_SLICED_CHEESE.getStackForm(4))
					.duration(64)
					.EUt(4)
					.buildAndRegister();
            OreDictUnifier.registerOre(stack, new ItemMaterialInfo(new MaterialStack(Materials.Cheese, M)));
        } else if (entry.orePrefix == OrePrefix.food && entry.material == Materials.Dough) {
			ModHandler.removeFurnaceSmelting(stack);

			RecipeMap.BENDER_RECIPES.recipeBuilder()
					.inputs(GTUtility.copyAmount(1, stack))
					.outputs(MetaItems.FOOD_FLAT_DOUGH.getStackForm())
					.duration(16)
					.EUt(4)
					.buildAndRegister();

			RecipeMap.MIXER_RECIPES.recipeBuilder()
					.inputs(stack, OreDictUnifier.get(OrePrefix.dust, Materials.Sugar))
					.outputs(MetaItems.FOOD_DOUGH_SUGAR.getStackForm(2))
					.duration(32)
					.EUt(8)
					.buildAndRegister();
			RecipeMap.MIXER_RECIPES.recipeBuilder()
					.inputs(stack, OreDictUnifier.get(OrePrefix.dust, Materials.Cocoa))
					.outputs(MetaItems.FOOD_DOUGH_CHOCOLATE.getStackForm(2))
					.duration(32)
					.EUt(8)
					.buildAndRegister();
			RecipeMap.MIXER_RECIPES.recipeBuilder()
					.inputs(stack, OreDictUnifier.get(OrePrefix.dust, Materials.Chocolate))
					.outputs(MetaItems.FOOD_DOUGH_CHOCOLATE.getStackForm(2))
					.duration(32)
					.EUt(8)
					.buildAndRegister();

			RecipeMap.PRESS_RECIPES.recipeBuilder()
					.inputs(GTUtility.copyAmount(1, stack))
					.notConsumable(MetaItems.SHAPE_MOLD_BUN)
					.outputs(MetaItems.FOOD_RAW_BUN.getStackForm())
					.duration(128)
					.EUt(4)
					.buildAndRegister();
			RecipeMap.PRESS_RECIPES.recipeBuilder()
					.inputs(GTUtility.copyAmount(2, stack))
					.notConsumable(MetaItems.SHAPE_MOLD_BREAD)
					.outputs(MetaItems.FOOD_RAW_BREAD.getStackForm())
					.duration(256)
					.EUt(4)
					.buildAndRegister();
			RecipeMap.PRESS_RECIPES.recipeBuilder()
					.inputs(GTUtility.copyAmount(3, stack))
					.notConsumable(MetaItems.SHAPE_MOLD_BAGUETTE)
					.outputs(MetaItems.FOOD_RAW_BAGUETTE.getStackForm())
					.duration(384)
					.EUt(4)
					.buildAndRegister();
		}
    }
}
