package gregtech.common.inventory;

import gregtech.api.util.ItemStackKey;

import javax.annotation.Nullable;
import java.util.List;

public interface IItemList {

    void addItemListChangeCallback(Runnable changeCallback);

    List<ItemStackKey> getStoredItems();

    @Nullable
    IItemInfo getItemInfo(ItemStackKey stackKey);

    default boolean hasItemStored(ItemStackKey itemStackKey) {
        return getItemInfo(itemStackKey) != null;
    }

    int insertItem(ItemStackKey itemStack, int amount, boolean simulate);

    int extractItem(ItemStackKey itemStack, int amount, boolean simulate);
}
