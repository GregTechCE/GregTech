package gregtech.integration.multipart;

import net.minecraft.util.ResourceLocation;

public class CableMultiPart extends PipeMultiPart {

    CableMultiPart() {
    }

    @Override
    public ResourceLocation getType() {
        return GTMultipartFactory.CABLE_PART_KEY;
    }
}
