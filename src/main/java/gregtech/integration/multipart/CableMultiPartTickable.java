package gregtech.integration.multipart;

import net.minecraft.util.ResourceLocation;

public class CableMultiPartTickable extends CableMultiPart {

    CableMultiPartTickable() {
    }

    @Override
    public ResourceLocation getType() {
        return GTMultipartFactory.CABLE_PART_TICKABLE_KEY;
    }
}
