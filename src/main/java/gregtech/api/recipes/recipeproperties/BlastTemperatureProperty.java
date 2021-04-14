package gregtech.api.recipes.recipeproperties;

import gregtech.api.unification.material.type.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import org.apache.commons.lang3.Validate;

import java.util.Map;
import java.util.TreeMap;

public class BlastTemperatureProperty extends RecipeProperty<Integer> {
    private static final TreeMap<Integer, Object> registeredCoilTypes = new TreeMap<>((x, y) -> y - x);
    public static final BlastTemperatureProperty INSTANCE = new BlastTemperatureProperty();
    private static final String key = "blast_furnace_temperature";
    private String cachedName = null;

    private BlastTemperatureProperty() {
        super(Integer.class);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("gregtech.recipe.blast_furnace_temperature",
                value, cachedName == null ? getMinTierForTemperature(castValue(value)) : cachedName), x, y, color);
    }

    @Override
    public String getKey() {
        return key;
    }

    private String getMinTierForTemperature(Integer value) {
        String name = "";
        for (Map.Entry<Integer, Object> coil : registeredCoilTypes.entrySet()) {
            if (value <= coil.getKey()) {
                Object mapValue = coil.getValue();
                if (mapValue instanceof Material) {
                    name = ((Material) mapValue).getLocalizedName();
                } else if (mapValue instanceof String) {
                    name = I18n.format((String) mapValue);
                }
            }
        }
        if (name.length() >= 13) {
            name = name.substring(0, 10) + "..";
        }
        return cachedName = name;
    }

    /**
     * This Maps coil Materials to its Integer temperatures.
     * In case the coil was not constructed with a Material you can pass a String name,
     * ideally an unlocalized name
     */
    public static void registerCoilType(int temperature, Material coilMaterial, String coilName) {
        Validate.notNull(coilName);
        if (coilMaterial == null) {
            registeredCoilTypes.put(temperature, coilName);
        } else {
            registeredCoilTypes.put(temperature, coilMaterial);
        }
    }

}
