package gregtech.api.recipes;

import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class RecipeProperty<T> {

    protected final Object value;
    protected final String key;
    protected RecipeProperty<T> defaultProperty;
    private static List<RecipeProperty> propertyList = new ArrayList<>();

    public RecipeProperty(RecipeProperty<T> property, Object value) {
        this.defaultProperty = property;
        this.key = null;
        this.value = value;
        addProperty(this);
    }

    public RecipeProperty(String key, Object value) {
        this.key = key;
        this.value = value;
        Map<String, Object> prop = new HashMap<String, Object>();
        prop.put(key, value);
        List<RecipeProperty> propList = makeProperties(prop);
        propList.forEach(this::addProperty);
    }

    //Called for the DefaultRecipeProperty, which will never use the value for any display
    public RecipeProperty() {
        this.key = "";
        this.value = null;
    }

    //Used to Transform the old Map<String, Object> parameter for Properties into the new format
    public static List<RecipeProperty> makeProperties(Map<String, Object> recipePropertiesOld) {
        List<RecipeProperty> appliedProperties = new ArrayList<>();

        if(recipePropertiesOld.isEmpty()) {
            appliedProperties.add(new DefaultRecipeProperty());
        }
        else {
            for(Map.Entry<String, Object> entry : recipePropertiesOld.entrySet()) {
                RecipeProperty property = Recipe.getPropertyByKey(entry.getKey());
                if(property != null) {
                    appliedProperties.add(property);
                }
            }
        }

        return appliedProperties;
    }

    public Object getValue() {
        return value;
    }

    public String getKey() {
        return key;
    }

    public static List<RecipeProperty> getPropertyList() {
        return propertyList;
    }

    public RecipeProperty getRecipeProperty() {
        return null;
    }

    public void addProperty(RecipeProperty property) {
        propertyList.add(property);
    }

    public abstract void drawInfo(Minecraft mc, int x, int y, int color);


}
