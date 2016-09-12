package gregtech.jei;

import gregtech.api.util.GT_Recipe;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;

import javax.annotation.Nonnull;
import java.util.stream.Collectors;

@JEIPlugin
public class JEI_GT_Plugin implements IModPlugin {

    @Override
    public void register(@Nonnull IModRegistry registry) {
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

}
