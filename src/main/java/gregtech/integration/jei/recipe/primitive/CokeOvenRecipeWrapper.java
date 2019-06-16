package gregtech.integration.jei.recipe.primitive;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import codechicken.lib.util.ItemNBTUtils;
import gregtech.api.recipes.CountableIngredient;
import gregtech.api.recipes.recipes.CokeOvenRecipe;
import gregtech.api.recipes.recipes.PrimitiveBlastFurnaceRecipe;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class CokeOvenRecipeWrapper  implements IRecipeWrapper{
	
	private final CokeOvenRecipe recipe;
	private final List<List<ItemStack>> matchingInputs;
	private final List<ItemStack> outputs;
	private final List<FluidStack> fluidOutputs;
	
	public CokeOvenRecipeWrapper(CokeOvenRecipe recipe) {
		this.recipe = recipe;
		CountableIngredient ingredient = recipe.getInput();
		matchingInputs = new ArrayList<>();
		List<ItemStack> ingredientValues = Arrays.stream(ingredient.getIngredient().getMatchingStacks())
                .map(ItemStack::copy)
                .sorted(OreDictUnifier.getItemStackComparator())
                .collect(Collectors.toList());
            ingredientValues.forEach(stack -> {
                if (ingredient.getCount() == 0) {
                    ItemNBTUtils.setBoolean(stack, "not_consumed", true);
                    stack.setCount(1);
                } else stack.setCount(ingredient.getCount());
            });
            matchingInputs.add(ingredientValues);
            
		this.outputs = new ArrayList<ItemStack>();
		this.outputs.add(recipe.getOutput());
		this.fluidOutputs = new ArrayList<FluidStack>();
		this.fluidOutputs.add(recipe.getFluidOutput());
	}
	
	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(ItemStack.class, matchingInputs);		
		ingredients.setOutputs(ItemStack.class, outputs);	
		ingredients.setOutputs(FluidStack.class, fluidOutputs);
	}
	
	@Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        minecraft.fontRenderer.drawString(I18n.format("gregtech.recipe.duration", recipe.getDuration() / 20f), 0, 60, 0x111111);		
	}

}
