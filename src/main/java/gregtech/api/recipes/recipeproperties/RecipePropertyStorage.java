package gregtech.api.recipes.recipeproperties;

import gregtech.api.util.GTLog;

import java.util.HashMap;
import java.util.Map;

public class RecipePropertyStorage {
    private final Map<RecipeProperty<?>, Object> recipeProperties;

    public RecipePropertyStorage() {
        recipeProperties = new HashMap<>();
    }

    public boolean store(RecipeProperty<?> recipeProperty, Object value) {
        boolean success = true;
        String key = recipeProperty.getKey();

        for (RecipeProperty<?> existingRecipeProperty : recipeProperties.keySet()) {
            if (existingRecipeProperty.getKey().equals(key)) {
                GTLog.logger.warn("Unable to add RecipeProperty with key {} as it already exists", key);
                success = false;
            }
        }

        if (value == null) {
            GTLog.logger.warn("Provided value is null for RecipeProperty with key {}", key);
            success = false;
        }

        try {
            recipeProperty.castValue(value);
        } catch (ClassCastException ex) {
            GTLog.logger.warn("Provided incorrect value for RecipeProperty with key {}", key);
            GTLog.logger.warn("Full exception:", ex);
            success = false;
        }

        if (success) {
            recipeProperties.put(recipeProperty, value);
        } else {
            GTLog.logger.warn("Stacktrace:", new IllegalArgumentException());
        }

        return success;
    }

    public boolean store(Map<RecipeProperty<?>, Object> recipeProperties) {
        boolean success = true;
        for (Map.Entry<RecipeProperty<?>, Object> recipePropertyEntry : recipeProperties.entrySet()) {
            if (!store(recipePropertyEntry.getKey(), recipePropertyEntry.getValue())) {
                success = false;
            }
        }

        return success;
    }

    public boolean storeOldFormat(Map<String, Object> recipeProperties) {
        boolean success = true;
        for (Map.Entry<String, Object> recipePropertyEntry : recipeProperties.entrySet()) {
            Object value = recipePropertyEntry.getValue();
            String key = recipePropertyEntry.getKey();

            if (value != null) {
                DefaultProperty<?> recipeProperty = new DefaultProperty<>(key, value.getClass());
                if (!store(recipeProperty, value)) {
                    success = false;
                }
            } else {
                GTLog.logger.warn("Provided value is null for old RecipeProperty with key {}", key);
                GTLog.logger.warn("Stacktrace:", new IllegalArgumentException());
                success = false;
            }
        }

        return success;
    }


}
