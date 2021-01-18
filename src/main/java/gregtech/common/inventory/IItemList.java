package gregtech.common.inventory;

import gregtech.api.capability.IStorageNetwork;
import gregtech.api.util.ItemStackKey;

// Review: Partially moved into the api
public interface IItemList extends IStorageNetwork {

    void addItemListChangeCallback(Runnable changeCallback);

    default int insertItem(ItemStackKey itemStack, int amount, boolean simulate) {
        return insertItem(itemStack, amount, simulate, InsertMode.LOWEST_PRIORITY);
    };

    int insertItem(ItemStackKey itemStack, int amount, boolean simulate, InsertMode insertMode);

    int extractItem(ItemStackKey itemStack, int amount, boolean simulate);

    enum InsertMode {
        LOWEST_PRIORITY,
        HIGHEST_PRIORITY,
    }
}
