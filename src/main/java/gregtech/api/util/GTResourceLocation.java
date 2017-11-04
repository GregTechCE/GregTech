package gregtech.api.util;

import gregtech.api.GTValues;
import net.minecraft.util.ResourceLocation;

public class GTResourceLocation extends ResourceLocation {

    public GTResourceLocation(String resourcePathIn) {
        super(GTValues.MODID, resourcePathIn);
    }
}
