package gregtech.api.capability.impl;

import java.util.Collections;
import java.util.Set;

import gregtech.api.capability.IItemInfo;
import gregtech.api.capability.IStorageNetwork;
import gregtech.api.util.ItemStackKey;

public class EmptyStorageNetwork implements IStorageNetwork {
    
    public static IStorageNetwork INSTANCE = new EmptyStorageNetwork();

    @Override
    public Set<ItemStackKey> getStoredItems() {
        return Collections.emptySet();
    }

    @Override
    public IItemInfo getItemInfo(ItemStackKey stackKey) {
        return null;
    }

    @Override
    public int insertItem(ItemStackKey itemStack, int amount, boolean simulate) {
        return 0;
    }

    @Override
    public int extractItem(ItemStackKey itemStack, int amount, boolean simulate) {
        return 0;
    }
}