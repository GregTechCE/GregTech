package gregtech.api.recipes.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.recipes.CokeOvenRecipe;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenExpansion("mods.gregtech.recipe.CokeOvenRecipe")
@ZenRegister
public class CokeOvenRecipeExpansion {

    @ZenMethod
    public static void remove(CokeOvenRecipe recipe) {
        RecipeMaps.getCokeOvenRecipes().remove(recipe);
    }
}
