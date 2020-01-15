package gregtech.common.pipelike.inventory.network;

import gregtech.api.util.ItemStackKey;
import net.minecraft.world.World;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class ItemSourceList {

    protected final World world;
    protected final List<ItemSource> handlerInfoList = new CopyOnWriteArrayList<>();
    protected final Map<ItemStackKey, NetworkItemInfo> itemInfoMap = new HashMap<>();
    protected Runnable itemListChangeCallback = null;
    private final Comparator<ItemSource> comparator = Comparator.comparing(ItemSource::getPriority).reversed();

    public ItemSourceList(World world) {
        this.world = world;
    }

    public World getWorld() {
        return world;
    }

    public void setItemListChangeCallback(Runnable changeCallback) {
        this.itemListChangeCallback = changeCallback;
    }

    public Collection<NetworkItemInfo> getStoredItems() {
        return Collections.unmodifiableCollection(itemInfoMap.values());
    }

    public Map<ItemStackKey, NetworkItemInfo> getStoredItemsMap() {
        return Collections.unmodifiableMap(itemInfoMap);
    }

    public void update() {
        this.handlerInfoList.forEach(ItemSource::update);
    }

    public int insertItem(ItemStackKey itemStack, int amount, boolean simulate) {
        int amountToInsert = amount;
        for (ItemSource itemSource : handlerInfoList) {
            int inserted = itemSource.insertItem(itemStack, amountToInsert, simulate);
            amountToInsert -= inserted;
            if (amountToInsert == 0) break;
        }
        return amount - amountToInsert;
    }

    public void notifyPriorityUpdated() {
        this.handlerInfoList.sort(comparator);
    }

    public void addItemHandler(ItemSource handlerInfo) {
        if (!handlerInfoList.contains(handlerInfo)) {
            handlerInfo.setStoredItemsChangeCallback((storedItems, removedItems) -> updateStoredItems(handlerInfo, storedItems, removedItems));
            if (handlerInfo.update() == UpdateResult.INVALID) return;
            handlerInfo.setInvalidationCallback(() -> removeItemHandler(handlerInfo));
            this.handlerInfoList.add(handlerInfo);
            addItemHandlerPost(handlerInfo);
            notifyPriorityUpdated();
        }
    }

    public void removeItemHandler(ItemSource handlerInfo) {
        if(this.handlerInfoList.remove(handlerInfo)) {
            handlerInfo.setStoredItemsChangeCallback(null);
            handlerInfo.setInvalidationCallback(null);
            for (ItemStackKey itemStackKey : itemInfoMap.keySet()) {
                NetworkItemInfo itemInfo = itemInfoMap.get(itemStackKey);
                itemInfo.removeInventory(handlerInfo);
            }
            removeItemHandlerPost(handlerInfo);
        }
    }

    protected void addItemHandlerPost(ItemSource handlerInfo) {
    }

    protected void removeItemHandlerPost(ItemSource handlerInfo) {
    }

    void updateStoredItems(ItemSource handlerInfo, Map<ItemStackKey, Integer> itemAmount, Set<ItemStackKey> removedItems) {
        boolean updatedItemAmount = false;
        for (ItemStackKey itemStackKey : itemAmount.keySet()) {
            NetworkItemInfo itemInfo = itemInfoMap.computeIfAbsent(itemStackKey, NetworkItemInfo::new);
            if (itemInfo != null) {
                updatedItemAmount |= itemInfo.addInventory(handlerInfo, itemAmount.get(itemStackKey));
            }
        }
        for (ItemStackKey removedItem : removedItems) {
            NetworkItemInfo itemInfo = itemInfoMap.get(removedItem);
            if (itemInfo != null) {
                updatedItemAmount |= itemInfo.removeInventory(handlerInfo);
            }
        }
        if (updatedItemAmount && itemListChangeCallback != null) {
            itemListChangeCallback.run();
        }
    }

}
