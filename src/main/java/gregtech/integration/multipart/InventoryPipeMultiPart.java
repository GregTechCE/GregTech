package gregtech.integration.multipart;

import gregtech.api.pipenet.block.simple.EmptyNodeData;
import gregtech.common.pipelike.inventory.InventoryPipeType;
import net.minecraft.util.ResourceLocation;

public class InventoryPipeMultiPart extends PipeMultiPart<InventoryPipeType, EmptyNodeData> {

    @Override
    public ResourceLocation getType() {
        return GTMultipartFactory.INVENTORY_PIPE_PART_KEY;
    }

}
