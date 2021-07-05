package gregtech.api.recipes.recipeproperties;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

public class FusionEUToStartProperty extends RecipeProperty<Long>{


    private static final String KEY = "eu_to_start";

    private static FusionEUToStartProperty INSTANCE;

    protected FusionEUToStartProperty() {
        super(KEY, Long.class);
    }

    public static FusionEUToStartProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FusionEUToStartProperty();
        }

        return INSTANCE;
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("gregtech.recipe.eu_to_start",
                value), x, y, color);
    }
}
