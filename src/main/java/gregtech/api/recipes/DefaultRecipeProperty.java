package gregtech.api.recipes;

import net.minecraft.client.Minecraft;

/*
    This Class is used to deal with cases where there was no Recipe Property provided
 */
public class DefaultRecipeProperty extends RecipeProperty {

    public DefaultRecipeProperty() {
    }

    public void drawInfo(Minecraft mc, int x, int y, int color) {
        mc.fontRenderer.drawString("", x, y, color);
    }

    @Override
    public RecipeProperty getRecipeProperty() {
        return this;
    }
}
