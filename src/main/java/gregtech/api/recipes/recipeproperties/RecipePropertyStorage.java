package gregtech.api.recipes.recipeproperties;

import gregtech.api.util.GTLog;

import java.util.*;

public class RecipePropertyStorage {
    private static final String STACKTRACE = "Stacktrace:";

    private final Map<RecipeProperty<?>, Object> recipeProperties;

    public RecipePropertyStorage() {
        recipeProperties = new HashMap<>();
    }

    /**
     * Stores new {@link RecipeProperty} with value
     *
     * @param recipeProperty {@link RecipeProperty}
     * @param value          value
     * @return <code>true</code> if store succeeds; otherwise <code>false</code>
     */
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
            GTLog.logger.warn(STACKTRACE, new IllegalArgumentException());
        }

        return success;
    }

    /**
     * Stores all {@link RecipeProperty} with values in provided {@link Map}
     *
     * @param recipeProperties {@link Map} of {@link RecipeProperty} and values
     * @return <code>true</code> if store of all succeeds; otherwise <code>false</code>
     */
    public boolean store(Map<RecipeProperty<?>, Object> recipeProperties) {
        boolean success = true;
        for (Map.Entry<RecipeProperty<?>, Object> recipePropertyEntry : recipeProperties.entrySet()) {
            if (!store(recipePropertyEntry.getKey(), recipePropertyEntry.getValue())) {
                success = false;
            }
        }

        return success;
    }

    /**
     * @param recipeProperties {@link Map} of {@link String} property key and values
     * @return <code>true</code> if conversion and store of all succeeds; otherwise <code>false</code>
     * @deprecated use {@link #store(Map recipeProperties)}
     * Converts recipe properties from old format to new and stores them
     */
    @Deprecated
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
                GTLog.logger.warn(STACKTRACE, new IllegalArgumentException());
                success = false;
            }
        }

        return success;
    }

    /**
     * Provides information how many {@link RecipeProperty} are stored
     *
     * @return number of stored {@link RecipeProperty}
     */
    public int getSize() {
        return recipeProperties.size();
    }

    /**
     * Provides all stored {@link RecipeProperty}
     *
     * @return all stored {@link RecipeProperty} and values
     */
    @SuppressWarnings("java:S1452")
    public Set<Map.Entry<RecipeProperty<?>, Object>> getRecipeProperties() {
        return this.recipeProperties.entrySet();
    }

    /**
     * Provides casted value for one specific {@link RecipeProperty} if is stored or defaultValue
     *
     * @param recipeProperty {@link RecipeProperty}
     * @param defaultValue   Default value if recipeProperty is not found
     * @param <T>            Type of returned value
     * @return value tied with provided recipeProperty on success; otherwise defaultValue
     */
    public <T> T getRecipePropertyValue(RecipeProperty<T> recipeProperty, T defaultValue) {
        Object value = recipeProperties.get(recipeProperty);

        if (value == null) {
            GTLog.logger.warn("There is no property with key {}", recipeProperty.getKey());
            GTLog.logger.warn(STACKTRACE, new IllegalArgumentException());
            return defaultValue;
        }

        return recipeProperty.castValue(value);
    }

    /**
     * @param key Key of {@link RecipeProperty}
     * @return {@link AbstractMap.SimpleEntry} containing {@link RecipeProperty} and value on success; otherwise <code>null</code>
     * @deprecated use {@link #getRecipePropertyValue(RecipeProperty recipeProperty, Object defaultValue)} instead
     * Compatibility way of getting {@link RecipeProperty} and value via key
     */
    @Deprecated
    @SuppressWarnings("java:S1452")
    public AbstractMap.SimpleEntry<RecipeProperty<?>, Object> getRecipeProperty(String key) {
        RecipeProperty<?> recipeProperty = getRecipePropertyValue(key);
        if (recipeProperty != null) {
            return new AbstractMap.SimpleEntry<>(recipeProperty, recipeProperties.get(recipeProperty));
        }

        return null;
    }

    /**
     * Provides keys of all stored {@link RecipeProperty}
     *
     * @return {@link Set} of keys
     */
    public Set<String> getRecipePropertyKeys() {
        HashSet<String> keys = new HashSet<>();

        recipeProperties.keySet().forEach(recipeProperty -> keys.add(recipeProperty.getKey()));

        return keys;
    }

    /**
     * Provides un-casted value for one specific {@link RecipeProperty} searched by key
     *
     * @param key Key of stored {@link RecipeProperty}
     * @return {@link Object} value on success; otherwise <code>null</code>
     */
    public Object getRawRecipePropertyValue(String key) {
        RecipeProperty<?> recipeProperty = getRecipePropertyValue(key);
        if (recipeProperty != null) {
            return recipeProperties.get(recipeProperty);
        }

        return null;
    }

    private RecipeProperty<?> getRecipePropertyValue(String key) {
        for (RecipeProperty<?> recipeProperty : recipeProperties.keySet()) {
            if (recipeProperty.getKey().equals(key))
                return recipeProperty;
        }

        GTLog.logger.warn("There is no property with key {}", key);
        GTLog.logger.warn(STACKTRACE, new IllegalArgumentException());

        return null;
    }
}
