package gregtech.api.recipes.recipeproperties;

import net.minecraft.client.Minecraft;

public abstract class RecipeProperty<T> {
    private final Class<T> type;

    protected RecipeProperty(Class<T> type) {
        this.type = type;
    }

    public abstract void drawInfo(Minecraft minecraft, int x, int y, int color, Object value);

    public abstract String getKey();

    public boolean isOfType(Class<?> otherType) {
        return this.type == otherType;
    }

    public T castValue(Object value) {
        return this.type.cast(value);
    }

}
