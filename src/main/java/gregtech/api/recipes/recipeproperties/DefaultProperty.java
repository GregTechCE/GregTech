package gregtech.api.recipes.recipeproperties;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

public class DefaultProperty<T> extends RecipeProperty<T> {
    private final String key;

    public DefaultProperty(String key, Class<T> type) {
        super(type);
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("gregtech.recipe." + key,
                castValue(value)), x, y, color);
    }

}
