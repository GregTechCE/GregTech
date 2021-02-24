package gregtech.api.recipes.recipeproperties;

import gregtech.api.recipes.Recipe;
import net.minecraft.client.Minecraft;

public abstract class RecipeProperty<T> {
    public final Class<T> type;

    protected RecipeProperty(Class<T> type) {
        this.type = type;
    }

    public abstract void drawInfo(Minecraft minecraft, int x, int y, int color, Recipe recipe);

    public abstract String getKey();

}
