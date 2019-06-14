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
	
    private final ModularUI modularUI;
	
    public PrimitiveBlastRecipeCategory(IGuiHelper guiHelper) {
    	super("primitive_blast_furnace", "gregtech.machine.primitive_blast_furnace.bronze.name", guiHelper.createBlankDrawable(140, 60), 
    			PrimitiveBlastFurnaceRecipe.class);
    	IItemHandlerModifiable importItems = new ItemStackHandler(2);
    	IItemHandlerModifiable exportItems = new ItemStackHandler(2);
    	
        this.modularUI = ModularUI.builder(GuiTextures.BACKGROUND, 176, 166)
                .widget(new SlotWidget(importItems, 0, 33, 15, false, false)
                        .setBackgroundTexture(GuiTextures.SLOT, GuiTextures.INGOT_OVERLAY))
                    .widget(new SlotWidget(importItems, 1, 33, 33, false, false)
                        .setBackgroundTexture(GuiTextures.SLOT, GuiTextures.FURNACE_OVERLAY))
                    .progressBar(()->0.0, 58, 24, 20, 15, GuiTextures.BRONZE_BLAST_FURNACE_PROGRESS_BAR, MoveType.HORIZONTAL)
                    .widget(new SlotWidget(exportItems, 0, 85, 24, false, false)
                        .setBackgroundTexture(GuiTextures.SLOT, GuiTextures.INGOT_OVERLAY))
                    .widget(new SlotWidget(exportItems, 1, 103, 24, false, false)
                        .setBackgroundTexture(GuiTextures.SLOT, GuiTextures.DUST_OVERLAY))
                    .build(new BlankUIHolder(), Minecraft.getMinecraft().player);
            this.modularUI.initWidgets();
            this.background = guiHelper.createBlankDrawable(modularUI.getWidth(), modularUI.getHeight() * 2 / 3);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, PrimitiveBlastRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup itemStackGroup =  recipeLayout.getItemStacks();
		itemStackGroup.init(0, true, 32, 14);
		itemStackGroup.init(1, true, 32, 32);

		itemStackGroup.init(2, false, 84, 23);
		itemStackGroup.init(3, false, 102, 23);
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
        for (Widget widget : modularUI.guiWidgets.values()) {
            widget.drawInBackground(0, 0);
            widget.drawInForeground(0, 0);
        }
    }
}
