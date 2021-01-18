package gregtech.api.capability;

import java.util.Set;

import javax.annotation.Nullable;

import gregtech.api.util.ItemStackKey;

/**
 * A consolidated inventory
 */
public interface IStorageNetwork {

    Set<ItemStackKey> getStoredItems();

    @Nullable
    IItemInfo getItemInfo(ItemStackKey stackKey);

    default boolean hasItemStored(ItemStackKey itemStackKey) {
        return getItemInfo(itemStackKey) != null;
    }

    int insertItem(ItemStackKey itemStack, int amount, boolean simulate);

    int extractItem(ItemStackKey itemStack, int amount, boolean simulate);
}