package gregtech.common.pipelike.inventory.network;

import gregtech.api.util.ItemStackKey;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.EmptyHandler;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class ItemHandlerInfo {

    private final BlockPos blockPos;
    private final EnumFacing accessSide;
    private final BlockPos accessedBlockPos;
    private WeakReference<TileEntity> cachedTileEntity = new WeakReference<>(null);
    private IItemHandler itemHandler = EmptyHandler.INSTANCE;
    private Map<ItemStackKey, Integer> itemStackByAmountMap = new HashMap<>();
    private ItemStorageNetwork storageNetwork;

    public ItemHandlerInfo(BlockPos blockPos, EnumFacing accessSide) {
        this.blockPos = blockPos;
        this.accessSide = accessSide;
        this.accessedBlockPos = blockPos.offset(accessSide);
    }

    public void setStorageNetwork(ItemStorageNetwork storageNetwork) {
        this.storageNetwork = storageNetwork;
    }

    public BlockPos getBlockPos() {
        return blockPos;
    }

    public EnumFacing getAccessSide() {
        return accessSide;
    }

    public BlockPos getAccessedBlockPos() {
        return accessedBlockPos;
    }

    private IItemHandler getItemHandler() {
        if (storageNetwork.getWorld().isBlockLoaded(accessedBlockPos)) {
            //we handle unloaded blocks as empty item handlers
            //so when they are loaded, they are refreshed and handled correctly
            return EmptyHandler.INSTANCE;
        }
        //use cached tile entity as long as it's valid and has same position (just in case of frames etc)
        TileEntity tileEntity = cachedTileEntity.get();
        if (tileEntity == null || tileEntity.isInvalid() || !tileEntity.getPos().equals(accessedBlockPos)) {
            tileEntity = storageNetwork.getWorld().getTileEntity(accessedBlockPos);
            if (tileEntity == null) {
                //if tile entity doesn't exist anymore, we are invalid now
                //return null which will be handled as INVALID
                return null;
            }
            //update cached tile entity
            this.cachedTileEntity = new WeakReference<>(tileEntity);
        }
        //fetch capability from tile entity
        //if it returns null, item handler info will be removed
        //block should emit block update once it obtains capability again,
        //so handler info will be recreated accordingly
        return tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, accessSide.getOpposite());
    }

    public UpdateResult updateCachedInfo() {
        IItemHandler newItemHandler = getItemHandler();
        if (newItemHandler == null) {
            return UpdateResult.INVALID;
        }
        if (!newItemHandler.equals(itemHandler)) {
            this.itemHandler = newItemHandler;
            recomputeItemStackCount();
            return UpdateResult.CHANGED;
        }
        if (recomputeItemStackCount()) {
            return UpdateResult.CHANGED;
        }
        return UpdateResult.STANDBY;
    }

    /**
     * @return amount of items inserted into the inventory
     */
    public int insertItem(ItemStackKey itemStackKey, int amount, boolean simulate) {
        int itemsInserted = 0;
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            ItemStack insertStack = itemStackKey.getItemStack();
            insertStack.setCount(amount);
            ItemStack remainderStack = itemHandler.insertItem(i, itemStackKey.getItemStack(), simulate);
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
        this.storageNetwork.updateStoredItems(this, amountMap, removedItems);
        return true;
    }
}
