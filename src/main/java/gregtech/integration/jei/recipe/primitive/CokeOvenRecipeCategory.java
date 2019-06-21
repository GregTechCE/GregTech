package gregtech.integration.jei.recipe.primitive;

import java.util.List;

import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.gui.BlankUIHolder;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.SlotWidget;
import gregtech.api.gui.widgets.TankWidget;
import gregtech.api.gui.widgets.ProgressWidget.MoveType;
import gregtech.api.recipes.recipes.CokeOvenRecipe;
import gregtech.api.recipes.recipes.PrimitiveBlastFurnaceRecipe;
import gregtech.common.metatileentities.MetaTileEntities;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

public class CokeOvenRecipeCategory extends PrimitiveRecipeCategory<CokeOvenRecipe, CokeOvenRecipeWrapper> {

	protected final IDrawable fluidTank;
	protected final IDrawable fluidTankOverlay;
	
	public CokeOvenRecipeCategory(IGuiHelper guiHelper) {
		super("coke_oven", "gregtech.machine.coke_oven.name", guiHelper.createBlankDrawable(176, 166),
				CokeOvenRecipe.class, guiHelper);
		fluidTank = guiHelper.createDrawable(GuiTextures.FLUID_TANK_BACKGROUND.imageLocation, 0, 0, 20, 58, 20, 58);
		fluidTankOverlay = guiHelper.createDrawable(GuiTextures.FLUID_TANK_OVERLAY.imageLocation, 0, 0, 20, 58, 20, 58);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, CokeOvenRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup itemStackGroup = recipeLayout.getItemStacks();
		IGuiFluidStackGroup fluidStackGroup = recipeLayout.getFluidStacks();
		itemStackGroup.init(0, true, 32, 19);
		itemStackGroup.init(1, false, 84, 19);
		itemStackGroup.set(ingredients);
		fluidStackGroup.init(0, false, 133, 3, 20, 58, 32000, true, this.fluidTankOverlay);
		fluidStackGroup.set(ingredients);
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(CokeOvenRecipe recipe) {
		return new CokeOvenRecipeWrapper(recipe);
	}

	@Override
	public void drawExtras(Minecraft minecraft) {
		this.slot.draw(minecraft, 32, 19);
		this.slot.draw(minecraft, 84, 19);
		this.progressBar.draw(minecraft, 57, 20);
		this.fluidTank.draw(minecraft, 133, 3);
	}
}
