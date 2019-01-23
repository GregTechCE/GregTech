package gregtech.integration.theoneprobe;

import gregtech.integration.theoneprobe.provider.DebugPipeNetInfoProvider;
import gregtech.integration.theoneprobe.provider.ElectricContainerInfoProvider;
import gregtech.integration.theoneprobe.provider.WorkableInfoProvider;
import mcjty.theoneprobe.TheOneProbe;
import mcjty.theoneprobe.api.ITheOneProbe;

public class TheOneProbeCompatibility {

    public static void registerCompatibility() {
        ITheOneProbe oneProbe = TheOneProbe.theOneProbeImp;
        oneProbe.registerProvider(new ElectricContainerInfoProvider());
        oneProbe.registerProvider(new WorkableInfoProvider());
        oneProbe.registerProvider(new DebugPipeNetInfoProvider());
    }

}
