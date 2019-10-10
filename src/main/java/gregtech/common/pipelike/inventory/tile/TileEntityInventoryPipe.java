package gregtech.common.pipelike.inventory.tile;

import gregtech.api.pipenet.block.simple.EmptyNodeData;
import gregtech.api.pipenet.tile.TileEntityPipeBase;
import gregtech.common.pipelike.inventory.InventoryPipeType;

public class TileEntityInventoryPipe extends TileEntityPipeBase<InventoryPipeType, EmptyNodeData> {

    @Override
    public Class<InventoryPipeType> getPipeTypeClass() {
        return InventoryPipeType.class;
    }

    @Override
    public boolean supportsTicking() {
        return false;
    }
}
