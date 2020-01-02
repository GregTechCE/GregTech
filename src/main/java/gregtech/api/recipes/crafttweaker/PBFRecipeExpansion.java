package gregtech.api.recipes.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.recipes.PrimitiveBlastFurnaceRecipe;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenExpansion("mods.gregtech.recipe.PrimitiveBlastFurnaceRecipe")
@ZenRegister
public class PBFRecipeExpansion {

    @ZenMethod
    public static void remove(PrimitiveBlastFurnaceRecipe recipe) {
        RecipeMaps.getPrimitiveBlastFurnaceRecipes().remove(recipe);
    }
}
