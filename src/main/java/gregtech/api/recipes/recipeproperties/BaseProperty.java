package gregtech.api.recipes.recipeproperties;

import gregtech.api.recipes.Recipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

public class BaseProperty<T> extends RecipeProperty<T> {
    private final String key;

    public BaseProperty(String key, Class<T> type) {
        super(type);
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void drawInfo(Minecraft minecraft, int x, int y, int color, Recipe recipe) {
        minecraft.fontRenderer.drawString(I18n.format("gregtech.recipe." + key,
            recipe.getPropertyValue(this)), x, y, color);
    }

}
