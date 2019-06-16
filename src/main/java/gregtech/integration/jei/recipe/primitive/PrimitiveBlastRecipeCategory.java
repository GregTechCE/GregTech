package gregtech.integration.jei.recipe.primitive;

import gregtech.api.GTValues;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.gui.BlankUIHolder;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.gui.widgets.SlotWidget;
import gregtech.api.recipes.recipes.PrimitiveBlastFurnaceRecipe;
import gregtech.common.metatileentities.MetaTileEntities;
import gregtech.api.gui.widgets.ProgressWidget.MoveType;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

public class PrimitiveBlastRecipeCategory extends PrimitiveRecipeCategory<PrimitiveBlastFurnaceRecipe, PrimitiveBlastRecipeWrapper>{
	
    public PrimitiveBlastRecipeCategory(IGuiHelper guiHelper) {
    	super("primitive_blast_furnace", "gregtech.machine.primitive_blast_furnace.bronze.name", guiHelper.createBlankDrawable(140, 60), 
    			PrimitiveBlastFurnaceRecipe.class, guiHelper);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, PrimitiveBlastRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup itemStackGroup =  recipeLayout.getItemStacks();
		itemStackGroup.init(0, true, 32, 4);
		itemStackGroup.init(1, true, 32, 22);

		itemStackGroup.init(2, false, 84, 13);
		itemStackGroup.init(3, false, 102, 13);
		itemStackGroup.addTooltipCallback(recipeWrapper::addTooltip);
		itemStackGroup.set(ingredients);
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(PrimitiveBlastFurnaceRecipe recipe)
	{
		return new PrimitiveBlastRecipeWrapper(recipe);
	}

    @Override
    public void drawExtras(Minecraft minecraft) {
		slot.draw(minecraft, 32, 4);
		slot.draw(minecraft, 32, 22);
		slot.draw(minecraft, 84, 13);
		slot.draw(minecraft, 102, 13);
		progressBar.draw(minecraft, 57, 14);
    }
}
