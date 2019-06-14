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
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

public class CokeOvenRecipeCategory extends PrimitiveRecipeCategory<CokeOvenRecipe, CokeOvenRecipeWrapper>{
	
    private final ModularUI modularUI;
    private final FluidTankList exportFluids;
	
    public CokeOvenRecipeCategory(IGuiHelper guiHelper) {
	super("coke_oven", "gregtech.machine.coke_oven.name", guiHelper.createBlankDrawable(140, 60), 
			CokeOvenRecipe.class);
	IItemHandlerModifiable importItems = new ItemStackHandler(2);
	IItemHandlerModifiable exportItems = new ItemStackHandler(2);
	exportFluids = new FluidTankList(false, new FluidTank[] {new FluidTank(32000)});
	
    this.modularUI = ModularUI.builder(GuiTextures.BACKGROUND, 176, 166)
            .widget(new SlotWidget(importItems, 0, 33, 30, true, true)
                    .setBackgroundTexture(GuiTextures.SLOT, GuiTextures.FURNACE_OVERLAY))
                .progressBar(()->0.0, 58, 30, 20, 15, GuiTextures.BRONZE_BLAST_FURNACE_PROGRESS_BAR, MoveType.HORIZONTAL)
                .widget(new SlotWidget(exportItems, 0, 85, 30, true, false)
                    .setBackgroundTexture(GuiTextures.SLOT, GuiTextures.FURNACE_OVERLAY))
                .widget(new TankWidget(exportFluids.getTankAt(0), 133, 13, 20, 58)
                    .setBackgroundTexture(GuiTextures.FLUID_TANK_BACKGROUND)
                    .setOverlayTexture(GuiTextures.FLUID_TANK_OVERLAY))
                .build(new BlankUIHolder(), Minecraft.getMinecraft().player);
        this.modularUI.initWidgets();
        this.background = guiHelper.createBlankDrawable(modularUI.getWidth(), modularUI.getHeight() * 2 / 3);
}

@Override
public void setRecipe(IRecipeLayout recipeLayout, CokeOvenRecipeWrapper recipeWrapper, IIngredients ingredients) {
	IGuiItemStackGroup itemStackGroup =  recipeLayout.getItemStacks();
	IGuiFluidStackGroup fluidStackGroup = recipeLayout.getFluidStacks();
	itemStackGroup.init(0, true, 32, 29);
	itemStackGroup.init(1, false, 84, 29);
	itemStackGroup.set(ingredients);
	for (Widget uiWidget : modularUI.guiWidgets.values()) {
		if(uiWidget instanceof TankWidget) {
			TankWidget tankWidget = (TankWidget) uiWidget;
			int exportIndex = exportFluids.getFluidTanks().indexOf(tankWidget.fluidTank);
    		List<List<FluidStack>> inputsList = ingredients.getOutputs(FluidStack.class);
    		int fluidAmount = 0;
    		if (inputsList.size() > exportIndex && !inputsList.get(exportIndex).isEmpty())
    		fluidAmount = inputsList.get(exportIndex).get(0).amount;
    		
    		fluidStackGroup.init(exportIndex, false,
    		tankWidget.getXPosition() + tankWidget.fluidRenderOffset,
    		tankWidget.getYPosition() + tankWidget.fluidRenderOffset,
    		tankWidget.getWidth() - tankWidget.fluidRenderOffset,
    		tankWidget.getHeight() - tankWidget.fluidRenderOffset,
    		fluidAmount, false, null);			
		}
	}    
    fluidStackGroup.set(ingredients);	
}

@Override
public IRecipeWrapper getRecipeWrapper(CokeOvenRecipe recipe)
{
	return new CokeOvenRecipeWrapper(recipe);
}

@Override
public void drawExtras(Minecraft minecraft) {
    for (Widget widget : modularUI.guiWidgets.values()) {
        widget.drawInBackground(0, 0);
        widget.drawInForeground(0, 0);
    }
}
}
