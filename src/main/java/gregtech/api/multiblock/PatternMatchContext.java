package gregtech.api.multiblock;

import java.util.HashMap;
import java.util.function.Supplier;

/**
 * Contains an context used for storing temporary data
 * related to current check and shared between all predicates doing it
 */
public class PatternMatchContext {

    private HashMap<String, Object> data = new HashMap<>();

    public void reset() {
        this.data.clear();
    }

    public void set(String key, Object value) {
        this.data.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return (T) data.get(key);
    }

    public <T> T get(String key, Supplier<T> creator) {
        T result = get(key);
        if(result == null) {
            result = creator.get();
            set(key, result);
        }
        return result;
    }

    public <T> T get(String key, T initialValue) {
        T result = get(key);
        if(result == null) {
            result = initialValue;
            set(key, result);
        }
        return result;
    }

}
