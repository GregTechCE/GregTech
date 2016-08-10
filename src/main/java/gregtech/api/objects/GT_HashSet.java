package gregtech.api.objects;

import gregtech.api.GregTech_API;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

import java.util.*;

public class GT_HashSet<E extends GT_ItemStack> extends AbstractSet<E> {
    private static final Object OBJECT = new Object();
    private transient HashMap<GT_ItemStack, Object> map;

    public GT_HashSet() {
        map = new HashMap<>();
        GregTech_API.sItemStackMappings.add(map);
    }

    public GT_HashSet(Collection<? extends E> c) {
        map = new HashMap<>(Math.max((int) (c.size() / .75f) + 1, 16));
        addAll(c);
        GregTech_API.sItemStackMappings.add(map);
    }

    public GT_HashSet(int initialCapacity, float loadFactor) {
        map = new HashMap<>(initialCapacity, loadFactor);
        GregTech_API.sItemStackMappings.add(map);
    }

    public GT_HashSet(int initialCapacity) {
        map = new HashMap<>(initialCapacity);
        GregTech_API.sItemStackMappings.add(map);
    }

    GT_HashSet(int initialCapacity, float loadFactor, boolean dummy) {
        map = new LinkedHashMap<>(initialCapacity, loadFactor);
        GregTech_API.sItemStackMappings.add(map);
    }

    public HashMap getMap() {
        return map;
    }

    @Override
    public Iterator<E> iterator() {
        return (Iterator<E>) map.keySet().iterator();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return map.containsKey(o);
    }

    public boolean add(ItemStack aStack) {
        return !GT_Utility.isStackInvalid(aStack) && map.put(new GT_ItemStack(aStack), OBJECT) == null;
    }

    @Override
    public boolean add(E e) {
        return map.put(e, OBJECT) == null;
    }

    @Override
    public boolean remove(Object o) {
        return map.remove(o) == OBJECT;
    }

    @Override
    public void clear() {
        map.clear();
    }
}