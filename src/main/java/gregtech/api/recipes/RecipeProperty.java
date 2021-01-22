package gregtech.api.recipes;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

public abstract class RecipeProperty {
    protected final String key;
    protected final Object value;

    public RecipeProperty(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public abstract void drawInfo(Minecraft minecraft, int x, int y, int color);

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

    public static class BaseProperty extends RecipeProperty {

        public BaseProperty(String key, Object value) {
            super(key, value);
        }

        public void drawInfo(Minecraft minecraft, int x, int y, int color) {
            minecraft.fontRenderer.drawString(I18n.format("gregtech.recipe." + key,
                value), x, y, color);
        }

    }

}
