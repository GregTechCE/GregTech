package gregtech.jei;

import gregtech.api.util.GT_Recipe;
import mezz.jei.api.*;

import javax.annotation.Nonnull;
import java.util.stream.Collectors;

@JEIPlugin
public class JEI_GT_Plugin implements IModPlugin {

    private static IJeiHelpers jeiHelpers;

    @Override
    public void register(@Nonnull IModRegistry registry) {
        jeiHelpers = registry.getJeiHelpers();
        registry.addRecipeHandlers(new JEIGregtechRecipeHandler());
        for(GT_Recipe.GT_Recipe_Map recipe_map : GT_Recipe.GT_Recipe_Map.sMappings) {
            if(recipe_map.mNEIAllowed) {
                registry.addRecipeCategories(new JEIGregtehRecipeCategory(recipe_map));
                registry.addRecipes(recipe_map.mRecipeList.stream()
                        .map(recipe -> new JEIGregtechRecipe(recipe_map, recipe))
                        .collect(Collectors.toList()));
            }
        }
    }

    @Override
    public void onRuntimeAvailable(@Nonnull IJeiRuntime jeiRuntime) {}

    public static IJeiHelpers getJeiHelpers() {
        return jeiHelpers;
    }
}
