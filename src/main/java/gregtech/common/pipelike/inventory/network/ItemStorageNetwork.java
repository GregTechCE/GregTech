package gregtech.common.pipelike.inventory.network;

import gregtech.api.util.ItemStackKey;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.*;
import java.util.stream.Collectors;

public class ItemStorageNetwork {

    private final World world;
    private final List<ItemHandlerInfo> handlerInfoList = new ArrayList<>();
    private final Map<SidedBlockPos, ItemHandlerInfo> handlerInfoMap = new HashMap<>();
    private final Map<ItemStackKey, NetworkItemInfo> itemInfoMap = new HashMap<>();

    public ItemStorageNetwork(World world) {
        this.world = world;
    }

    public World getWorld() {
        return world;
    }

    public Collection<NetworkItemInfo> getStoredItems() {
        return Collections.unmodifiableCollection(itemInfoMap.values());
    }

    public void update() {
        this.handlerInfoList.removeIf(itemHandler -> itemHandler.updateCachedInfo() == UpdateResult.INVALID);
    }

    public void transferItemHandlers(Collection<BlockPos> nodePositions, ItemStorageNetwork destNetwork) {
        List<ItemHandlerInfo> movedHandlerInfo = handlerInfoList.stream()
            .filter(handlerInfo -> nodePositions.contains(handlerInfo.getBlockPos()))
            .collect(Collectors.toList());
        movedHandlerInfo.forEach(this::removeItemHandler);
        movedHandlerInfo.forEach(destNetwork::addItemHandler);
    }

    public void handleBlockedConnectionChange(BlockPos nodePos, EnumFacing side, boolean isBlockedNow) {
        if (isBlockedNow) {
            SidedBlockPos blockPos = new SidedBlockPos(nodePos, side);
            ItemHandlerInfo handlerInfo = handlerInfoMap.get(blockPos);
            if (handlerInfo != null) {
                removeItemHandler(handlerInfo);
            }
        } else {
            ItemHandlerInfo handlerInfo = new ItemHandlerInfo(nodePos, side);
            //just add unchecked item handler, addItemHandler will refuse
            //to add item handler if it's updateCache will return UpdateResult.INVALID
            //avoids duplicating logic here
            addItemHandler(handlerInfo);
        }
    }

    public void checkForItemHandlers(BlockPos nodePos, int blockedConnections) {
        for (EnumFacing accessSide : EnumFacing.VALUES) {
            //skip sides reported as blocked by pipe network
            if ((blockedConnections & 1 << accessSide.getIndex()) > 0) continue;
            //check for existing item handler
            SidedBlockPos blockPos = new SidedBlockPos(nodePos, accessSide);
            if (handlerInfoMap.containsKey(blockPos)) {
                ItemHandlerInfo handlerInfo = handlerInfoMap.get(blockPos);
                if (handlerInfo.updateCachedInfo() == UpdateResult.INVALID) {
                    removeItemHandler(handlerInfo);
                }
            } else {
                ItemHandlerInfo handlerInfo = new ItemHandlerInfo(nodePos, accessSide);
                //just add unchecked item handler, addItemHandler will refuse
                //to add item handler if it's updateCache will return UpdateResult.INVALID
                //avoids duplicating logic here
                addItemHandler(handlerInfo);
            }
        }
    }

    private void addItemHandler(ItemHandlerInfo handlerInfo) {
        if (!handlerInfoList.contains(handlerInfo)) {
            handlerInfo.setStorageNetwork(this);
            if (handlerInfo.updateCachedInfo() == UpdateResult.INVALID) return;
            this.handlerInfoList.add(handlerInfo);
            this.handlerInfoMap.put(handlerPosition(handlerInfo), handlerInfo);
        }
    }

    private void removeItemHandler(ItemHandlerInfo handlerInfo) {
        this.handlerInfoList.remove(handlerInfo);
        this.handlerInfoMap.remove(handlerPosition(handlerInfo));
        handlerInfo.setStorageNetwork(null);
        for (ItemStackKey itemStackKey : itemInfoMap.keySet()) {
            NetworkItemInfo itemInfo = itemInfoMap.get(itemStackKey);
            itemInfo.removeInventory(handlerInfo);
        }
    }

    private static SidedBlockPos handlerPosition(ItemHandlerInfo handlerInfo) {
        return new SidedBlockPos(handlerInfo.getBlockPos(), handlerInfo.getAccessSide());
    }

    void updateStoredItems(ItemHandlerInfo handlerInfo, Map<ItemStackKey, Integer> itemAmount, Set<ItemStackKey> removedItems) {
        for (ItemStackKey itemStackKey : itemAmount.keySet()) {
            NetworkItemInfo itemInfo = itemInfoMap.get(itemStackKey);
            if (itemInfo != null) {
                itemInfo.addInventory(handlerInfo, itemAmount.get(itemStackKey));
            }
        }
        for (ItemStackKey removedItem : removedItems) {
            NetworkItemInfo itemInfo = itemInfoMap.get(removedItem);
            if (itemInfo != null) {
                itemInfo.removeInventory(handlerInfo);
            }
        }
    }

    private static class SidedBlockPos {
        private final BlockPos blockPos;
        private final EnumFacing accessSide;

        public SidedBlockPos(BlockPos blockPos, EnumFacing accessSide) {
            this.blockPos = blockPos;
            this.accessSide = accessSide;
        }

        public BlockPos getBlockPos() {
            return blockPos;
        }

        public EnumFacing getAccessSide() {
            return accessSide;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof SidedBlockPos)) return false;
            SidedBlockPos that = (SidedBlockPos) o;
            return Objects.equals(blockPos, that.blockPos) &&
                accessSide == that.accessSide;
        }

        @Override
        public int hashCode() {
            return Objects.hash(blockPos, accessSide);
        }
    }
}
