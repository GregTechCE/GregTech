package gregtech.integration.theoneprobe;

import gregtech.api.capability.IEnergyContainer;
import mcjty.theoneprobe.api.IProbeInfo;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class ElectricContainerInfoProvider extends CapabilityInfoProvider<IEnergyContainer> {

    @Override
    protected Capability<IEnergyContainer> getCapability() {
        return IEnergyContainer.CAPABILITY_ENERGY_CONTAINER;
    }

    @Override
    public String getID() {
        return "gregtech:energy_container_provider";
    }

    @Override
    protected void addProbeInfo(IEnergyContainer capability, IProbeInfo probeInfo, TileEntity tileEntity, EnumFacing sideHit) {
        long energyStored = capability.getEnergyStored();
        long maxStorage = capability.getEnergyCapacity();
        probeInfo.progress(energyStored, maxStorage, probeInfo.defaultProgressStyle()
            .showText(energyStored > 0)
            .borderColor(0x550000)
            .backgroundColor(0x555555)
            .filledColor(0xFF0000));
    }

}
