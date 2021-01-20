package gregtech.common.pipelike.inventory.network;

import gregtech.api.util.ItemStackKey;
import gregtech.common.inventory.itemsource.ItemSource;
import gregtech.common.inventory.itemsource.ItemSourceList;
import gregtech.common.inventory.itemsource.sources.TileItemSource;
import gregtech.common.pipelike.inventory.net.InventoryPipeNet;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ItemStorageNetwork extends ItemSourceList {

    // Review: stop CCME
    private final Map<SidedBlockPos, TileItemSource> handlerInfoMap = new ConcurrentHashMap<>();

    private final InventoryPipeNet pipeNet;
    
    public ItemStorageNetwork(InventoryPipeNet pipeNet) {
        super(pipeNet.getWorldData());
        this.pipeNet = pipeNet;
    }

    // Review: Exposure for TOP debugging
    public Collection<TileItemSource> getHandlerInfos()
    {
        return Collections.unmodifiableCollection(handlerInfoMap.values());
    }

    public void transferItemHandlers(Collection<BlockPos> nodePositions, ItemStorageNetwork destNetwork) {
        List<ItemSource> movedHandlerInfo = handlerInfoList.stream()
            .filter(handlerInfo -> handlerInfo instanceof TileItemSource)
            .filter(handlerInfo -> nodePositions.contains(((TileItemSource) handlerInfo).getBlockPos()))
            .collect(Collectors.toList());
        movedHandlerInfo.forEach(this::removeItemHandler);
        movedHandlerInfo.forEach(destNetwork::addItemHandler);
    }

    public void handleBlockedConnectionChange(BlockPos nodePos, EnumFacing side, boolean isBlockedNow) {
        if (isBlockedNow) {
            SidedBlockPos blockPos = new SidedBlockPos(nodePos, side);
            TileItemSource handlerInfo = handlerInfoMap.get(blockPos);
            if (handlerInfo != null) {
                removeItemHandler(handlerInfo);
            }
        } else {
            TileItemSource handlerInfo = new TileItemSource(getWorld(), nodePos, side);
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
                TileItemSource handlerInfo = handlerInfoMap.get(blockPos);
                if (handlerInfo.update() == UpdateResult.INVALID) {
                    removeItemHandler(handlerInfo);
                }
            } else {
                TileItemSource handlerInfo = new TileItemSource(getWorld(), nodePos, accessSide);
                //just add unchecked item handler, addItemHandler will refuse
                //to add item handler if it's updateCache will return UpdateResult.INVALID
                //avoids duplicating logic here
                addItemHandler(handlerInfo);
            }
        }
    }

    public void removeItemHandlers(BlockPos nodePos) {
        for (EnumFacing accessSide : EnumFacing.VALUES) {
            ItemSource handlerInfo = handlerInfoMap.get(new SidedBlockPos(nodePos, accessSide));
            if (handlerInfo != null)
                removeItemHandler(handlerInfo);
        }
    }

    @Override
    protected void addItemHandlerPost(ItemSource handlerInfo) {
        if (handlerInfo instanceof TileItemSource) {
            this.handlerInfoMap.put(handlerPosition((TileItemSource) handlerInfo), (TileItemSource) handlerInfo);
        }
    }

    @Override
    protected void removeItemHandlerPost(ItemSource handlerInfo) {
        if (handlerInfo instanceof TileItemSource) {
            this.handlerInfoMap.remove(handlerPosition((TileItemSource) handlerInfo));
        }
    }

    @Override
    protected int preInsert(ItemStackKey key, int amount, boolean simulate, InsertMode insertMode) {
        return checkEnergy(amount);
    }

    @Override
    protected int postInsert(ItemStackKey key, int amount, boolean simulate, InsertMode insertMode) {
        if (!simulate)
            drainEnergy(amount);
        return amount;
    }

    @Override
    protected int preExtract(ItemStackKey key, int amount, boolean simulate) {
        return checkEnergy(amount);
    }

    @Override
    protected int postExtract(ItemStackKey key, int amount, boolean simulate) {
        if (!simulate)
            drainEnergy(amount);
        return amount;
    }

    private final long energyPerOp = 1L;

    protected int checkEnergy(int amount) {
        if (pipeNet.getEnergyContainer().getEnergyStored() < energyPerOp)
            return 0;
        return amount;
    }

    protected void drainEnergy(int amount) {
        pipeNet.getEnergyContainer().removeEnergy(energyPerOp);
    }

    private static SidedBlockPos handlerPosition(TileItemSource handlerInfo) {
        return new SidedBlockPos(handlerInfo.getBlockPos(), handlerInfo.getAccessSide());
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
