package gregtech.common.inventory.itemsource;

import gregtech.api.util.GTUtility;
import gregtech.api.util.ItemStackKey;
import gregtech.common.inventory.IItemInfo;
import gregtech.common.inventory.IItemList;
import gregtech.common.pipelike.inventory.network.UpdateResult;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class ItemSourceList implements IItemList, ITickable {

    protected final World world;
    protected final List<ItemSource> handlerInfoList = new CopyOnWriteArrayList<>();
    protected final Map<ItemStackKey, NetworkItemInfo> itemInfoMap = new HashMap<>();
    protected final List<ItemStackKey> storedItemsList = new ArrayList<>();
    private final Comparator<ItemSource> comparator = Comparator.comparing(ItemSource::getPriority).reversed();
    private final List<ItemStackKey> storedItemsView = Collections.unmodifiableList(storedItemsList);
    protected Runnable itemListChangeCallback = null;

    public ItemSourceList(World world) {
        this.world = world;
    }

    public World getWorld() {
        return world;
    }

    @Override
    public void addItemListChangeCallback(Runnable changeCallback) {
        this.itemListChangeCallback = GTUtility.combine(itemListChangeCallback, changeCallback);
    }

    @Override
    public List<ItemStackKey> getStoredItems() {
        return storedItemsView;
    }

    @Nullable
    @Override
    public IItemInfo getItemInfo(ItemStackKey stackKey) {
        return itemInfoMap.get(stackKey);
    }

    @Override
    public void update() {
        this.handlerInfoList.forEach(ItemSource::update);
    }

    @Override
    public int insertItem(ItemStackKey itemStack, int amount, boolean simulate) {
        int amountToInsert = amount;
        for (ItemSource itemSource : handlerInfoList) {
            int inserted = itemSource.insertItem(itemStack, amountToInsert, simulate);
            amountToInsert -= inserted;
            if (amountToInsert == 0) break;
        }
        return amount - amountToInsert;
    }

    @Override
    public int extractItem(ItemStackKey itemStack, int amount, boolean simulate) {
        NetworkItemInfo itemInfo = (NetworkItemInfo) getItemInfo(itemStack);
        if (itemInfo == null) {
            return 0;
        }
        return itemInfo.extractItem(amount, simulate);
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
