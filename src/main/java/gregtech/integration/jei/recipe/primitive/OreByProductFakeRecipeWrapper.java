package gregtech.integration.jei.recipe.primitive;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import gregtech.api.recipes.CountableIngredient;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.recipes.OreByProductFakeRecipe;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.GTUtility;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

public class OreByProductFakeRecipeWrapper implements IRecipeWrapper {

	private final OreByProductFakeRecipe recipe;
	private final List<List<ItemStack>> matchingInputs = new ArrayList<>();
	private final List<ItemStack> oreProcessingSteps = new ArrayList<>();
	private final List<ItemStack> outputs = new ArrayList<>();
	
	public OreByProductFakeRecipeWrapper(OreByProductFakeRecipe recipe) {
		this.recipe = recipe;
		List<ItemStack> inputOres = new ArrayList<ItemStack>();
		inputOres.addAll(recipe.oreIngredients);
		matchingInputs.add(inputOres);
		oreProcessingSteps.addAll(recipe.oreProcessingSteps);
		for(ItemStack stack : oreProcessingSteps) {
			List<ItemStack> stepStack = new ArrayList<ItemStack>();
			stepStack.add(stack);
			matchingInputs.add(stepStack);
		}
		outputs.addAll(recipe.byProductIngredients);
	}
	
	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(ItemStack.class, this.matchingInputs);
		ingredients.setOutputs(ItemStack.class, this.outputs);		
	}
	
	public void addTooltip(int slotIndex, boolean input, Object ingredient, List<String> tooltip) {
		switch(slotIndex) {
		case 0: //Ore
			addOreTooltip(tooltip, 0, RecipeMaps.MACERATOR_RECIPES.getLocalizedName(), false);
			break;
		case 1: //Crushed
			addOreTooltip(tooltip, 0, RecipeMaps.ORE_WASHER_RECIPES.getLocalizedName(), false);
			addOreTooltip(tooltip, 0, RecipeMaps.MACERATOR_RECIPES.getLocalizedName(), false);
			if(recipe.material.washedIn != null) addOreTooltip(tooltip, 3, RecipeMaps.CHEMICAL_BATH_RECIPES.getLocalizedName(), false);
			addOreTooltip(tooltip, 0, RecipeMaps.THERMAL_CENTRIFUGE_RECIPES.getLocalizedName(), false);
			break;
		case 2: //Crushed Purified
			addOreTooltip(tooltip, 1, RecipeMaps.MACERATOR_RECIPES.getLocalizedName(), false);
			addOreTooltip(tooltip, 1, RecipeMaps.THERMAL_CENTRIFUGE_RECIPES.getLocalizedName(), false);
			break;
		case 3: //Crushed Centrifuged
			addOreTooltip(tooltip, 2, RecipeMaps.MACERATOR_RECIPES.getLocalizedName(), false);
			break;
		case 4: //Dust impure
			addOreTooltip(tooltip, 2, RecipeMaps.CENTRIFUGE_RECIPES.getLocalizedName(), false);
			break;
		case 5: //Dust Pure
			addOreTooltip(tooltip, 1, RecipeMaps.CENTRIFUGE_RECIPES.getLocalizedName(), false);
			break;
//		case 6: //Dust
//			break;
		case 7: //1st Byproduct
			addOreTooltip(tooltip, 0, RecipeMaps.MACERATOR_RECIPES.getLocalizedName(), true);
			addOreTooltip(tooltip, 1, RecipeMaps.ORE_WASHER_RECIPES.getLocalizedName(), true);
			addOreTooltip(tooltip, 1, RecipeMaps.THERMAL_CENTRIFUGE_RECIPES.getLocalizedName(), true);
			break;
		case 8: //2nd Byproduct
			addOreTooltip(tooltip, 2, RecipeMaps.MACERATOR_RECIPES.getLocalizedName(), true);
			addOreTooltip(tooltip, 2, RecipeMaps.THERMAL_CENTRIFUGE_RECIPES.getLocalizedName(), true);
			addOreTooltip(tooltip, 5, RecipeMaps.CENTRIFUGE_RECIPES.getLocalizedName(), true);
			break;
		case 9: //3rd Byproduct
			addOreTooltip(tooltip, 3, RecipeMaps.MACERATOR_RECIPES.getLocalizedName(), true);
			addOreTooltip(tooltip, 4, RecipeMaps.CENTRIFUGE_RECIPES.getLocalizedName(), true);
			break;
		case 10: //4th Byproduct
			if(recipe.material.washedIn != null)addOreTooltip(tooltip, 1, RecipeMaps.CHEMICAL_BATH_RECIPES.getLocalizedName(), true);
			break;
		default:
			break;
		}
	}
	
	public int getOutputCount() {
		return outputs.size();
	}
	
	public int getProcessingStepCount() {
		return oreProcessingSteps.size();
	}
	
	public void addOreTooltip(List<String> tooltip, int byproduct, String machine, boolean result) {
		Material byProductMaterial = GTUtility.selectItemInList(byproduct, recipe.material, recipe.material.oreByProducts, DustMaterial.class);
		if(!result)tooltip.add(I18n.format("gregtech.jei.ore_by_product_from_ore", machine, byProductMaterial.getLocalizedName()));
		else {
			String oreType = byproduct == 0 ? 
					recipe.oreIngredients.get(0).getDisplayName() : 
					recipe.oreProcessingSteps.get(byproduct - 1).getDisplayName();
			tooltip.add(I18n.format("gregtech.jei.ore_by_product_from_machine", oreType, machine));		
		}
	}

}
