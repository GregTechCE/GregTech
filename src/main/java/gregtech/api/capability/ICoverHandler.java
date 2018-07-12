package gregtech.api.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public interface ICoverHandler {

    @CapabilityInject(ICoverHandler.class)
    Capability<ICoverHandler> CAPABILITY_COVER_HANDLER = null;

}
