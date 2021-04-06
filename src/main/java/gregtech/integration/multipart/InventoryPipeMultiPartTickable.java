package gregtech.integration.multipart;

import net.minecraft.util.ResourceLocation;

public class InventoryPipeMultiPartTickable extends InventoryPipeMultiPart {

    @Override
    public ResourceLocation getType() {
        return GTMultipartFactory.INVENTORY_PIPE_TICKABLE_PART_KEY;
    }
}
