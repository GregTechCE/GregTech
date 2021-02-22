package gregtech.api.capability;

import gregtech.api.cover.ICoverable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class GregtechTileCapabilities {

    @CapabilityInject(IWorkable.class)
    public static Capability<IWorkable> CAPABILITY_WORKABLE = null;

    @CapabilityInject(ICoverable.class)
    public static Capability<ICoverable> CAPABILITY_COVERABLE = null;

    @CapabilityInject(IControllable.class)
    public static Capability<IControllable> CAPABILITY_CONTROLLABLE = null;

    @CapabilityInject(IOutputInformation.class)
    public static Capability<IOutputInformation> CAPABILITY_OUTPUT_INFORMATION = null;

}
