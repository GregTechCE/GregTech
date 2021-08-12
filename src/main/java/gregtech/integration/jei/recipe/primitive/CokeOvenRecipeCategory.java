package gregtech.integration.jei.recipe.primitive;

import gregtech.api.gui.GuiTextures;
import gregtech.api.recipes.recipes.CokeOvenRecipe;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;

import javax.annotation.Nonnull;

public class CokeOvenRecipeCategory extends PrimitiveRecipeCategory<CokeOvenRecipe, CokeOvenRecipeWrapper> {

    protected final IDrawable slot;
    protected final IDrawable progressBar;
    protected final IDrawable fluidTank;


    public CokeOvenRecipeCategory(IGuiHelper guiHelper) {
        super("coke_oven",
                "gregtech.machine.coke_oven.name",
                guiHelper.createBlankDrawable(176, 60), guiHelper);

        this.slot = guiHelper.drawableBuilder(GuiTextures.SLOT.imageLocation, 0, 0, 18, 18).setTextureSize(18, 18).build();
        this.progressBar = guiHelper.drawableBuilder(GuiTextures.PROGRESS_BAR_COKE_OVEN.imageLocation, 0, 0, 36, 18).setTextureSize(36, 18).build();
        this.fluidTank = guiHelper.drawableBuilder(GuiTextures.FLUID_SLOT.imageLocation, 0, 0, 18, 18).setTextureSize(18, 18).build();
//        this.fluidTankOverlay = guiHelper.drawableBuilder(GuiTextures.FLUID_TANK_OVERLAY.imageLocation, 0, 0, 20, 58).setTextureSize(20, 58).build();
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, @Nonnull CokeOvenRecipeWrapper recipeWrapper, @Nonnull IIngredients ingredients) {
        IGuiItemStackGroup itemStackGroup = recipeLayout.getItemStacks();
        IGuiFluidStackGroup fluidStackGroup = recipeLayout.getFluidStacks();
        itemStackGroup.init(0, true, 51, 18);
        itemStackGroup.init(1, false, 105, 9);
        itemStackGroup.set(ingredients);
        fluidStackGroup.init(0, false, 105, 27, 18, 18, 5000, false, null); //todo convert to modularUI, change capacity to 32B
        fluidStackGroup.set(ingredients);
    }

    @Nonnull
    @Override
    public IRecipeWrapper getRecipeWrapper(@Nonnull CokeOvenRecipe recipe) {
        return new CokeOvenRecipeWrapper(recipe);
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        this.slot.draw(minecraft, 51, 18);
        this.slot.draw(minecraft, 105, 9);
        this.progressBar.draw(minecraft, 69, 18);
        this.fluidTank.draw(minecraft, 105, 27);
    }
}
