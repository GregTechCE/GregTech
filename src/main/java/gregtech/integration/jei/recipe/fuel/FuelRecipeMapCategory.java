package gregtech.integration.jei.recipe.fuel;

import gregtech.api.GTValues;
import gregtech.api.gui.GuiTextures;
import gregtech.api.recipes.machines.FuelRecipeMap;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;

public class FuelRecipeMapCategory implements IRecipeCategory<GTFuelRecipeWrapper> {

    private final FuelRecipeMap recipeMap;
    private final IDrawable background;

    public FuelRecipeMapCategory(FuelRecipeMap recipeMap, IGuiHelper helper) {
        this.recipeMap = recipeMap;
        this.background = helper.createBlankDrawable(176, 110);
    }

    @Override
    public String getUid() {
        return GTValues.MODID + ":" + recipeMap.getUnlocalizedName();
    }

    @Override
    public String getTitle() {
        return recipeMap.getLocalizedName();
    }

    @Override
    public String getModName() {
        return GTValues.MODID;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, GTFuelRecipeWrapper recipeWrapper, IIngredients ingredients) {
        recipeLayout.getFluidStacks().init(0, true, 52, 24, 16, 16,
           recipeWrapper.recipe.getRecipeFluid().amount, false, null);
        recipeLayout.getFluidStacks().set(ingredients);
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        GuiTextures.PROGRESS_BAR_ARROW.drawSubArea(77, 22, 20, 20, 0.0,0.0, 1.0, 0.5);
        GuiTextures.FLUID_SLOT.draw(51, 23, 18, 18);
    }
}
