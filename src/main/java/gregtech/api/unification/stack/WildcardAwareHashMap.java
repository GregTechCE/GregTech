package gregtech.api.unification.stack;

import gregtech.api.GTValues;

import java.util.HashMap;
import java.util.Map;

/**
 * HashMap-based implementation of map with key type {@link gregtech.api.unification.stack.SimpleItemStack}
 * This map automatically takes care of wildcard item stacks as keys
 * @param <V> value type
 */
public class WildcardAwareHashMap<V> extends HashMap<SimpleItemStack, V> {

    public WildcardAwareHashMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public WildcardAwareHashMap(int initialCapacity) {
        super(initialCapacity);
    }

    public WildcardAwareHashMap() {
    }

    public WildcardAwareHashMap(Map<? extends SimpleItemStack, ? extends V> m) {
        super(m);
    }

    @Override
    public V get(Object key) {
        SimpleItemStack itemStack = (SimpleItemStack) key;
        V resultValue = super.get(key);
        if(resultValue == null && itemStack.itemDamage != GTValues.W) {
            SimpleItemStack wildcardStack = new SimpleItemStack(
                itemStack.item, GTValues.W, itemStack.stackSize);
            resultValue = super.get(wildcardStack);
        }
        return resultValue;
    }

    @Override
    public boolean containsKey(Object key) {
        SimpleItemStack itemStack = (SimpleItemStack) key;
        boolean resultValue = super.containsKey(key);
        if(!resultValue && itemStack.itemDamage != GTValues.W) {
            SimpleItemStack wildcardStack = new SimpleItemStack(
                itemStack.item, GTValues.W, itemStack.stackSize);
            resultValue = super.containsKey(wildcardStack);
        }
        return resultValue;
    }

    @Override
    public V getOrDefault(Object key, V defaultValue) {
        V value = get(key);
        return value == null ? defaultValue : value;
    }
}
