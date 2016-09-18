package gregtech.jei;

import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_Recipe;
import mezz.jei.Internal;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.gui.DrawableResource;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class JEIGregtehRecipeCategory implements IRecipeCategory<JEIGregtechRecipe> {

    protected final GT_Recipe.GT_Recipe_Map mRecipeMap;
    protected final IDrawable background;

    public JEIGregtehRecipeCategory(GT_Recipe.GT_Recipe_Map mRecipeMap) {
        this.mRecipeMap = mRecipeMap;

        this.background = Internal.getHelpers().getGuiHelper().createDrawable(
                new ResourceLocation(mRecipeMap.mNEIGUIPath),
                1, 3, 174, 78, -4, 0, -8, 0);
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
        return new DrawableResource(new ResourceLocation(mRecipeMap.mNEIGUIPath),
                1, 3, 174, 78, -7, 0, -0, 0) {


            @Override
            public int getHeight() {
                return super.getHeight() + 50;
            }
        };
    }

    @Override
    public void drawExtras(@Nonnull Minecraft minecraft) {}

    @Override
    public void drawAnimations(@Nonnull Minecraft minecraft) {}

    @Override
    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull JEIGregtechRecipe recipeWrapper) {
        recipeWrapper.init(recipeLayout);
    }

}
