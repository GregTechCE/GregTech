package gregtech.common.crafting;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeProperty;
import gregtech.common.blocks.BlockWireCoil.CoilType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

public class TemperatureProperty extends RecipeProperty {

    public TemperatureProperty(String key, Object value) {
        super(key, value);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color) {
        minecraft.fontRenderer.drawString(I18n.format("gregtech.recipe." + key,
            value, getMinTierForTemperature()), x, y, color);
    }

    private String getMinTierForTemperature() {
        for (CoilType values : CoilType.values()) {
            if ((Integer) this.value <= values.getCoilTemperature()) {
                return values.getMaterial().getLocalizedName();
            }
        }

        return "";
    }

    public static void register() {
        Recipe.registerProperty(new TemperatureProperty("blast_furnace_temperature", 0));
    }

}
