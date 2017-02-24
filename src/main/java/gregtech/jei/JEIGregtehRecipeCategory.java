package gregtech.jei;

import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_Recipe;
import mezz.jei.Internal;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.gui.DrawableBlank;
import mezz.jei.gui.DrawableResource;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiUtils;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

public class JEIGregtehRecipeCategory implements IRecipeCategory<JEIGregtechRecipe> {

    protected final GT_Recipe.GT_Recipe_Map mRecipeMap;
    protected final IDrawable background;

    public JEIGregtehRecipeCategory(GT_Recipe.GT_Recipe_Map mRecipeMap) {
        this.mRecipeMap = mRecipeMap;

        this.background = new DrawableResource(new ResourceLocation(mRecipeMap.mNEIGUIPath),
                3, 3, 170, 78, -7, 0, -0, 0) {


            @Override
            public int getHeight() {
                return super.getHeight() + 50;
            }
        };
    }

    @Nonnull
    @Override
    public String getUid() {
        return mRecipeMap.mUnlocalizedName;
    }

    @Nonnull
    @Override
    public String getTitle() {
        return GT_LanguageManager.getTranslation(mRecipeMap.mUnlocalizedName);
    }

    @Nonnull
    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void drawExtras(@Nonnull Minecraft minecraft) {}

    @Override
    public void drawAnimations(@Nonnull Minecraft minecraft) {}

    @Override
    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull JEIGregtechRecipe recipeWrapper) {
        recipeWrapper.init(recipeLayout);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, JEIGregtechRecipe recipeWrapper, IIngredients ingredients) {
        recipeWrapper.init(recipeLayout);
    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        return Collections.emptyList();
    }

    public IDrawable getIcon() {
        return null;
    }

}
