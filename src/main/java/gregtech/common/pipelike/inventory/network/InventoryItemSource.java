package gregtech.common.pipelike.inventory.network;

import gregtech.api.util.ItemStackKey;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.EmptyHandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public abstract class InventoryItemSource extends ItemSource {

    protected final World world;
    private Runnable invalidationCallback = null;
    private StoredItemsChangeCallback changeCallback = null;
    protected IItemHandler itemHandler = EmptyHandler.INSTANCE;
    private Map<ItemStackKey, Integer> itemStackByAmountMap = new HashMap<>();
    private long lastItemHandlerUpdateTick = -1L;
    private long lastStoredItemListUpdateTick = -1L;

    public InventoryItemSource(World world) {
        this.world = world;
    }

    public static InventoryItemSource direct(World world, IItemHandler itemHandler) {
        return new InventoryItemSource(world) {
            @Override
            protected IItemHandler computeItemHandler() {
                return itemHandler;
            }
        };
    }

    protected abstract IItemHandler computeItemHandler();

    @Override
    public void setInvalidationCallback(Runnable invalidatedRunnable) {
        this.invalidationCallback = invalidatedRunnable;
    }

    @Override
    public void setStoredItemsChangeCallback(StoredItemsChangeCallback callback) {
        this.changeCallback = callback;
    }

    @Override
    public UpdateResult update() {
        this.lastItemHandlerUpdateTick = world.getTotalWorldTime();
        IItemHandler newItemHandler = computeItemHandler();
        if (newItemHandler == null) {
            if (invalidationCallback != null) {
                invalidationCallback.run();
            }
            return UpdateResult.INVALID;
        }
        if (!newItemHandler.equals(itemHandler) || newItemHandler.getSlots() != itemHandler.getSlots()) {
            this.itemHandler = newItemHandler;
            recomputeItemStackCount();
            return UpdateResult.CHANGED;
        }
        //update stored item list once a second
        if (lastItemHandlerUpdateTick - lastStoredItemListUpdateTick >= 20) {
            this.lastStoredItemListUpdateTick = lastItemHandlerUpdateTick;
            recomputeItemStackCount();
        }
        return UpdateResult.STANDBY;
    }

    private boolean ensureCachedInfoUpdated() {
        long currentUpdateTick = world.getTotalWorldTime();
        if (currentUpdateTick != lastItemHandlerUpdateTick) {
            UpdateResult result = update();
            return result != UpdateResult.INVALID;
        }
        return true;
    }

    /**
     * @return amount of items inserted into the inventory
     */
    public int insertItem(ItemStackKey itemStackKey, int amount, boolean simulate) {
        if (!ensureCachedInfoUpdated()) {
            return 0;
        }
        int itemsInserted = 0;
        ItemStack itemStack = itemStackKey.getItemStack();
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            ItemStack insertStack = itemStackKey.getItemStack();
            insertStack.setCount(amount);
            ItemStack remainderStack = itemHandler.insertItem(i, itemStack, simulate);
            itemsInserted += (insertStack.getCount() - remainderStack.getCount());
        }
        if (itemsInserted > 0 && !simulate) {
            recomputeItemStackCount();
        }
        return itemsInserted;
    }

    /**
     * @return amount of items extracted from the inventory
     */
    public int extractItem(ItemStackKey itemStackKey, int amount, boolean simulate) {
        if (!ensureCachedInfoUpdated()) {
            return 0;
        }
        int itemsExtracted = 0;
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            ItemStack stackInSlot = itemHandler.getStackInSlot(i);
            if (stackInSlot.isEmpty()) continue;
            if (!itemStackKey.isItemStackEqual(stackInSlot)) continue;
            ItemStack extractedStack = itemHandler.extractItem(i, amount, simulate);
            if (!extractedStack.isEmpty()) {
                itemsExtracted += extractedStack.getCount();
            }
        }
        if (itemsExtracted > 0 && !simulate) {
            recomputeItemStackCount();
        }
        return itemsExtracted;
    }

    @Override
    public Map<ItemStackKey, Integer> getStoredItems() {
        return Collections.unmodifiableMap(itemStackByAmountMap);
    }

    private boolean recomputeItemStackCount() {
        HashMap<ItemStackKey, Integer> amountMap = new HashMap<>();
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            ItemStack itemStack = itemHandler.getStackInSlot(i);
            if (itemStack.isEmpty()) continue;
            ItemStackKey stackKey = new ItemStackKey(itemStack);
            amountMap.put(stackKey, amountMap.getOrDefault(stackKey, 0) + itemStack.getCount());
        }
        if (amountMap.equals(itemStackByAmountMap)) {
            return false;
        }
        HashSet<ItemStackKey> removedItems = new HashSet<>(itemStackByAmountMap.keySet());
        removedItems.removeAll(amountMap.keySet());
        this.itemStackByAmountMap = amountMap;
        if (changeCallback != null) {
            changeCallback.onStoredItemsUpdated(amountMap, removedItems);
        }
        return true;
    }
}

