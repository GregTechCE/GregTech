package gregtech.integration.theoneprobe;

import gregtech.api.capability.IWorkable;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.TextStyleClass;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class WorkableInfoProvider extends CapabilityInfoProvider<IWorkable> {

    @Override
    protected Capability<IWorkable> getCapability() {
        return IWorkable.CAPABILITY_WORKABLE;
    }

    @Override
    public String getID() {
        return "gregtech:workable_provider";
    }

    @Override
    protected void addProbeInfo(IWorkable capability, IProbeInfo probeInfo, TileEntity tileEntity, EnumFacing sideHit) {
        int currentProgress = capability.getProgress();
        int maxProgress = capability.getMaxProgress();

        probeInfo.progress(currentProgress, maxProgress, probeInfo.defaultProgressStyle()
            .showText(maxProgress > 0)
            .backgroundColor(0x000086)
            .borderColor(0x000044)
            .filledColor(0x000078));

        if(!capability.isWorkingEnabled()) {
            probeInfo.text(TextStyleClass.ERROR + "{*gregtech.top.working_disabled*}");
        }
    }
}
