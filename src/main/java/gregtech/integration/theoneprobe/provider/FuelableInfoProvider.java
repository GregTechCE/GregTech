package gregtech.integration.theoneprobe.provider;

import java.util.Collection;

import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IFuelInfo;
import gregtech.api.capability.IFuelable;
import mcjty.theoneprobe.api.ElementAlignment;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.TextStyleClass;
import net.minecraft.client.resources.I18n;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class FuelableInfoProvider extends CapabilityInfoProvider<IFuelable> {

    @Override
    protected Capability<IFuelable> getCapability() {
        return GregtechCapabilities.CAPABILITY_FUELABLE;
    }

    @Override
    public String getID() {
        return "gregtech:fuelable_provider";
    }

    @Override
    protected boolean allowDisplaying(IFuelable capability) {
        return !capability.isOneProbeHidden();
    }

    @Override
    protected void addProbeInfo(IFuelable capability, IProbeInfo probeInfo, TileEntity tileEntity, EnumFacing sideHit) {
        Collection<IFuelInfo> fuels = capability.getFuels();
        if (fuels == null || fuels.isEmpty()) {
            probeInfo.text(TextStyleClass.WARNING + I18n.format("gregtech.top.fuel_none"));
            return;
        }
        for (IFuelInfo fuelInfo : fuels) {
            final String fuelName = fuelInfo.getFuelName();
            final int fuelRemaining = fuelInfo.getFuelRemaining();
            final int fuelCapacity = fuelInfo.getFuelCapacity();
            final int fuelMinConsumed = fuelInfo.getFuelMinConsumed();
            final int burnTime = fuelInfo.getFuelBurnTime()/20;
            final int burnTimeMins = burnTime/60;
            final int burnTimeSecs = burnTime%60;

            IProbeInfo horizontalPane = probeInfo.horizontal(probeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER));
            horizontalPane.text(TextStyleClass.INFO + I18n.format("gregtech.top.fuel_name", fuelName));

            horizontalPane = probeInfo.horizontal(probeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER));
            String suffix;
            if (fuelRemaining < fuelMinConsumed)
                suffix = I18n.format("gregtech.top.fuel_min_consume", fuelCapacity, fuelMinConsumed);
            else
                suffix = I18n.format("gregtech.top.fuel_info", fuelCapacity, burnTimeMins, burnTimeSecs);
            horizontalPane.progress(fuelRemaining, fuelCapacity, probeInfo.defaultProgressStyle()
                .suffix(suffix)
                .borderColor(0x00000000)
                .backgroundColor(0x00000000)
                .filledColor(0xFFFFE000)
                .alternateFilledColor(0xFFEED000));
        }
    }

}
