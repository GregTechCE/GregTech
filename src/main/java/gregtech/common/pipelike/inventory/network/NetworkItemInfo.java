package gregtech.common.pipelike.inventory.network;

import gregtech.api.util.ItemStackKey;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class NetworkItemInfo {

    private final ItemStackKey itemStackKey;
    private int totalItemAmount = 0;
    private Map<ItemSource, Integer> inventories = new HashMap<>();

    public NetworkItemInfo(ItemStackKey itemStackKey) {
        this.itemStackKey = itemStackKey;
    }

    public int getTotalItemAmount() {
        return totalItemAmount;
    }

    public ItemStackKey getItemStackKey() {
        return itemStackKey;
    }

    int extractItem(ItemStackKey itemStackKey, int amount, boolean simulate) {
        int amountToExtract = amount;
        for (ItemSource itemSource : inventories.keySet()) {
            amountToExtract -= itemSource.extractItem(itemStackKey, amountToExtract, simulate);
            if (amountToExtract == 0) break;
        }
        int extracted = amount - amountToExtract;
        if (!simulate && extracted > 0) {
            recomputeItemAmount();
        }
        return extracted;
    }

    int insertItem(ItemStackKey itemStackKey, int amount, boolean simulate) {
        int amountToInsert = amount;
        for (Entry<ItemSource, Integer> entry : inventories.entrySet()) {
            //skip inventories that are empty now anyways
            if (entry.getValue() == 0) continue;
            ItemSource itemSource = entry.getKey();
            amountToInsert = itemSource.insertItem(itemStackKey, amountToInsert, simulate);
            if (amountToInsert == 0) break;
        }
        int inserted = amount - amountToInsert;
        if (!simulate && inserted > 0) {
            recomputeItemAmount();
        }
        return inserted;
    }

    boolean addInventory(ItemSource inventory, int amount) {
        if (inventories.getOrDefault(inventory, 0) != amount) {
            this.inventories.put(inventory, amount);
            return recomputeItemAmount();
        }
        return false;
    }

    boolean removeInventory(ItemSource inventory) {
        if (inventories.containsKey(inventory)) {
            this.inventories.remove(inventory);
            return recomputeItemAmount();
        }
        return false;
    }

    private boolean recomputeItemAmount() {
        int oldTotalItemAmount = totalItemAmount;
        this.totalItemAmount = inventories.values().stream()
            .mapToInt(Integer::intValue).sum();
        return totalItemAmount != oldTotalItemAmount;
    }
}
