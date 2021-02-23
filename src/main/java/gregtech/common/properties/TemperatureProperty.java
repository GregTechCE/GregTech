package gregtech.common.properties;

import gregtech.api.recipes.RecipeProperty;
import gregtech.common.blocks.BlockWireCoil.CoilType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

public class TemperatureProperty extends RecipeProperty {

    private String key = "blast_furnace_temperature";

    public TemperatureProperty(RecipeProperty property, Object value) {
        super(property, value);
    }

    public TemperatureProperty(String key, Object value) {
        super(key, value);
    }

    @Override
    public void drawInfo(Minecraft mc, int x, int y, int color) {
        mc.fontRenderer.drawString(I18n.format("gregtech.recipe." + key,
                value, getCoilForTemperature()), x, y, color);

    }

    private String getCoilForTemperature() {
        for(CoilType coil : CoilType.values()) {
            if((Integer) this.value <= coil.getCoilTemperature()) {
                return coil.getMaterial().getLocalizedName();
            }
        }

        return "";
    }

    @Override
    public RecipeProperty getRecipeProperty() {
        return this;
    }
}
