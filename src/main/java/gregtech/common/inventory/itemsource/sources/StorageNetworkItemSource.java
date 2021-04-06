package gregtech.common.inventory.itemsource.sources;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IItemInfo;
import gregtech.api.capability.IStorageNetwork;
import gregtech.api.capability.impl.EmptyStorageNetwork;
import gregtech.api.util.ItemStackKey;
import gregtech.common.inventory.itemsource.ItemSource;
import gregtech.common.pipelike.inventory.network.UpdateResult;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

// TODO Create an abstract class to merge some of this duplicate code with Inventory/TileItemSource
public class StorageNetworkItemSource extends ItemSource {

    protected final World world;
    private final BlockPos blockPos;
    private final EnumFacing accessSide;
    private final BlockPos accessedBlockPos;
    private Runnable invalidationCallback = null;
    private StoredItemsChangeCallback changeCallback = null;
    private Map<ItemStackKey, Integer> itemStackByAmountMap = new HashMap<>();
    private long lastItemHandlerUpdateTick = -1L;
    private long lastStoredItemListUpdateTick = -1L;
    private IStorageNetwork storageNetwork = EmptyStorageNetwork.INSTANCE;
    private WeakReference<TileEntity> cachedTileEntity = new WeakReference<>(null);
    private boolean cachedRefreshResult = false;

    public StorageNetworkItemSource(World world, BlockPos blockPos, EnumFacing accessSide) {
        this.world = world;
        this.blockPos = blockPos;
        this.accessSide = accessSide;
        this.accessedBlockPos = blockPos.offset(accessSide);
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

    @Override
    public int extractItem(ItemStackKey itemStackKey, int amount, boolean simulate) {
        if (!checkStorageNetworkValid(simulate)) {
            return 0;
        }
        int itemsExtracted = this.storageNetwork.extractItem(itemStackKey, amount, simulate);
        if (itemsExtracted > 0 && !simulate) {
            recomputeItemStackCount();
        }
        return itemsExtracted;
    }

    @Override
    public int insertItem(ItemStackKey itemStackKey, int amount, boolean simulate) {
        if (!checkStorageNetworkValid(simulate)) {
            return 0;
        }
        int itemsInserted = this.storageNetwork.insertItem(itemStackKey, amount, simulate);
        if (itemsInserted > 0 && !simulate) {
            recomputeItemStackCount();
        }
        return itemsInserted;
    }

    @Override
    public Map<ItemStackKey, Integer> getStoredItems() {
        return Collections.unmodifiableMap(itemStackByAmountMap);
    }

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
        //update stored item list once a second
        long currentTick = world.getTotalWorldTime();
        if (currentTick - lastStoredItemListUpdateTick >= 20) {
            return recomputeItemStackCount() ? UpdateResult.CHANGED : UpdateResult.STANDBY;
        }
        return UpdateResult.STANDBY;
    }

    private boolean checkStorageNetworkValid(boolean simulated) {
        long currentUpdateTick = this.world.getTotalWorldTime();
        if (currentUpdateTick != this.lastItemHandlerUpdateTick) {
            return refreshStorageNetwork(simulated);
        }
        return cachedRefreshResult;
    }

    private boolean refreshStorageNetwork(boolean simulated) {
        this.lastItemHandlerUpdateTick = world.getTotalWorldTime();
        IStorageNetwork newStorageNetwork = computeStorageNetwork();
        if (newStorageNetwork == null) {
            if (!simulated && this.invalidationCallback != null) {
                this.invalidationCallback.run();
            }
            this.cachedRefreshResult = false;
            return false;
        }
        if (!newStorageNetwork.equals(this.storageNetwork)) {
            this.storageNetwork = newStorageNetwork;
            if (!simulated) {
                recomputeItemStackCount();
            }
            this.cachedRefreshResult = false;
            return false;
        }
        this.cachedRefreshResult = true;
        return true;
    }

    private boolean recomputeItemStackCount() {
        if (!checkStorageNetworkValid(false)) {
            return false;
        }
        this.lastStoredItemListUpdateTick = world.getTotalWorldTime();
        HashMap<ItemStackKey, Integer> amountMap = new HashMap<>();
        for (ItemStackKey itemStackKey: this.storageNetwork.getStoredItems()) {
            IItemInfo itemInfo = this.storageNetwork.getItemInfo(itemStackKey);
            if (itemInfo == null || itemInfo.getTotalItemAmount() == 0)
                continue;
            amountMap.put(itemStackKey, itemInfo.getTotalItemAmount());
        }
        if (amountMap.equals(this.itemStackByAmountMap)) {
            return false;
        }
        HashSet<ItemStackKey> removedItems = new HashSet<>(this.itemStackByAmountMap.keySet());
        removedItems.removeAll(amountMap.keySet());
        this.itemStackByAmountMap = amountMap;
        if (this.changeCallback != null) {
            this.changeCallback.onStoredItemsUpdated(amountMap, removedItems);
        }
        return true;
    }

    private IStorageNetwork computeStorageNetwork() {
        if (!world.isBlockLoaded(accessedBlockPos)) {
            return EmptyStorageNetwork.INSTANCE;
        }
        TileEntity tileEntity = cachedTileEntity.get();
        if (tileEntity == null || tileEntity.isInvalid() || !tileEntity.getPos().equals(accessedBlockPos)) {
            tileEntity = world.getTileEntity(accessedBlockPos);
            if (tileEntity == null) {
                return null;
            }
            this.cachedTileEntity = new WeakReference<>(tileEntity);
        }
        return tileEntity.getCapability(GregtechCapabilities.CAPABILITY_STORAGE_NETWORK, accessSide.getOpposite());
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StorageNetworkItemSource)) return false;
        StorageNetworkItemSource that = (StorageNetworkItemSource) o;
        return blockPos.equals(that.blockPos) &&
            accessSide == that.accessSide;
    }

    @Override
    public int hashCode() {
        return Objects.hash(blockPos, accessSide);
    }
}
