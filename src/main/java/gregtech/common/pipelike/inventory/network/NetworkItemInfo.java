package gregtech.common.pipelike.inventory.network;

import gregtech.api.util.ItemStackKey;

import java.util.HashMap;
import java.util.Map;

public class NetworkItemInfo {

    private final ItemStackKey itemStackKey;
    private int totalItemAmount = 0;
    private Map<ItemHandlerInfo, Integer> inventories = new HashMap<>();

    public NetworkItemInfo(ItemStackKey itemStackKey) {
        this.itemStackKey = itemStackKey;
    }

    public int getTotalItemAmount() {
        return totalItemAmount;
    }

    public ItemStackKey getItemStackKey() {
        return itemStackKey;
    }

    void addInventory(ItemHandlerInfo inventory, int amount) {
        if (inventories.getOrDefault(inventory, 0) != amount) {
            this.inventories.put(inventory, amount);
            recomputeItemAmount();
        }
    }

    void removeInventory(ItemHandlerInfo inventory) {
        if (inventories.containsKey(inventory)) {
            this.inventories.remove(inventory);
            recomputeItemAmount();
        }
    }

    private void recomputeItemAmount() {
        this.totalItemAmount = inventories.values().stream()
            .mapToInt(Integer::intValue).sum();
    }
}
