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
        this.background = helper.createDrawable(GuiTextures.BACKGROUND.imageLocation, 0, 0, 176, 112);
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
        recipeLayout.getFluidStacks().init(0, true, 34, 24, 15, 15, 100, false, null);
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        GuiTextures.PROGRESS_BAR_ARROW.draw(77, 22, 20, 20);
        GuiTextures.FLUID_SLOT.draw(33, 23, 16, 16);
    }
}
