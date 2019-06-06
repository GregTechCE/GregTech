package gregtech.common.covers.filter;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import gregtech.api.unification.stack.ItemAndMetadata;
import gregtech.api.util.GTLog;
import gregtech.common.items.MetaItems;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class FilterTypeRegistry {

    private static final Map<ItemAndMetadata, Integer> itemFilterIdByStack = new HashMap<>();
    private static final Map<ItemAndMetadata, Integer> fluidFilterIdByStack = new HashMap<>();
    private static final BiMap<Integer, Class<? extends AbstractItemFilter>> itemFilterById = HashBiMap.create();
    private static final BiMap<Integer, Class<? extends AbstractFluidFilter>> fluidFilterById = HashBiMap.create();

    public static void init() {
        registerFluidFilter(1, SimpleFluidFilter.class, MetaItems.FLUID_FILTER.getStackForm());
        registerItemFilter(2, SimpleItemFilter.class, MetaItems.ITEM_FILTER.getStackForm());
        registerItemFilter(3, OreDictionaryItemFilter.class, MetaItems.ORE_DICTIONARY_FILTER.getStackForm());
    }

    public static void registerFluidFilter(int id, Class<? extends AbstractFluidFilter> fluidFilterClass, ItemStack itemStack) {
        if(fluidFilterById.containsKey(id)) {
            throw new IllegalArgumentException("Id is already occupied: " + id);
        }
        fluidFilterIdByStack.put(new ItemAndMetadata(itemStack), id);
        fluidFilterById.put(id, fluidFilterClass);
    }

    public static void registerItemFilter(int id, Class<? extends AbstractItemFilter> itemFilterClass, ItemStack itemStack) {
        if(itemFilterById.containsKey(id)) {
            throw new IllegalArgumentException("Id is already occupied: " + id);
        }
        itemFilterIdByStack.put(new ItemAndMetadata(itemStack), id);
        itemFilterById.put(id, itemFilterClass);
    }

    public static int getIdForItemFilter(AbstractItemFilter itemFilter) {
        Integer filterId = itemFilterById.inverse().get(itemFilter.getClass());
        if(filterId == null) {
            throw new IllegalArgumentException("Unknown filter type " + itemFilter.getClass());
        }
        return filterId;
    }

    public static int getIdForFluidFilter(AbstractFluidFilter fluidFilter) {
        Integer filterId = fluidFilterById.inverse().get(fluidFilter.getClass());
        if(filterId == null) {
            throw new IllegalArgumentException("Unknown filter type " + fluidFilter.getClass());
        }
        return filterId;
    }

    public static AbstractItemFilter createItemFilterById(int filterId) {
        Class<? extends AbstractItemFilter> filterClass = itemFilterById.get(filterId);
        if(filterClass == null) {
            throw new IllegalArgumentException("Unknown filter id: " + filterId);
        }
        return createNewFilterInstance(filterClass);
    }

    public static AbstractFluidFilter createFluidFilterById(int filterId) {
        Class<? extends AbstractFluidFilter> filterClass = fluidFilterById.get(filterId);
        if(filterClass == null) {
            throw new IllegalArgumentException("Unknown filter id: " + filterId);
        }
        return createNewFilterInstance(filterClass);
    }

    public static AbstractItemFilter getItemFilterForStack(ItemStack itemStack) {
        Integer filterId = itemFilterIdByStack.get(new ItemAndMetadata(itemStack));
        if(filterId == null) {
            return null;
        }
        Class<? extends AbstractItemFilter> filterClass = itemFilterById.get(filterId);
        return createNewFilterInstance(filterClass);
    }

    public static AbstractFluidFilter getFluidFilterForStack(ItemStack itemStack) {
        Integer filterId = fluidFilterIdByStack.get(new ItemAndMetadata(itemStack));
        if(filterId == null) {
            return null;
        }
        Class<? extends AbstractFluidFilter> filterClass = fluidFilterById.get(filterId);
        return createNewFilterInstance(filterClass);
    }

    private static <T> T createNewFilterInstance(Class<T> filterClass) {
        try {
            return filterClass.newInstance();
        } catch (ReflectiveOperationException exception) {
            GTLog.logger.error("Failed to create filter instance for class {}", filterClass, exception);
            return null;
        }
    }
}
