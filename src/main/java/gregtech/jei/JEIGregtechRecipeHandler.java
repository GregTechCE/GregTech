package gregtech.jei;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

import javax.annotation.Nonnull;

public class JEIGregtechRecipeHandler implements IRecipeHandler<JEIGregtechRecipe> {
    @Nonnull
    @Override
    public Class<JEIGregtechRecipe> getRecipeClass() {
        return JEIGregtechRecipe.class;
    }

    @Nonnull
    @Override
    public String getRecipeCategoryUid() {
        return null;
    }

    @Nonnull
    @Override
    public String getRecipeCategoryUid(@Nonnull JEIGregtechRecipe recipe) {
        return recipe.mRecipeMap.mUnlocalizedName;
    }

    @Nonnull
    @Override
    public IRecipeWrapper getRecipeWrapper(@Nonnull JEIGregtechRecipe recipe) {
        return recipe;
    }

    @Override
    public boolean isRecipeValid(@Nonnull JEIGregtechRecipe recipe) {
        return true;
    }
}
