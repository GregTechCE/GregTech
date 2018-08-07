package gregtech.integration.theoneprobe.provider;

import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.IWorkable;
import gregtech.common.pipelike.cables.CableEnergyContainer;
import gregtech.common.pipelike.cables.EnergyNet;
import gregtech.integration.theoneprobe.element.ElementProgressExtended;
import gregtech.integration.theoneprobe.element.ElementTextAdvanced;
import mcjty.theoneprobe.api.ElementAlignment;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.TextStyleClass;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import java.math.BigInteger;

public class ElectricContainerInfoProvider extends CapabilityInfoProvider<IEnergyContainer> {

    @Override
    protected Capability<IEnergyContainer> getCapability() {
        return IEnergyContainer.CAPABILITY_ENERGY_CONTAINER;
    }

    @Override
    public String getID() {
        return "gregtech:energy_container_provider";
    }

    private static int shade(double ratio, int from, int to) {
        int result = 0;
        for (int n = 0; n < 32; n += 8) {
            result |= ((int) ((0xFF & (from >> n)) * (1.0 - ratio) + (0xFF & (to >> n)) * ratio)) << n;
        }
        return result;
    }

    private static void setCableProgressBar(IProbeInfo probeInfo, IProbeInfo pane, double average, long total, String infixFormat, double threshold1, double threshold2) {
        double ratio = average * 4.0 / total;
        int color1, color2;
        if (ratio < 2.0) {
            color1 = 0xFF0000D0;
            color2 = 0xFF0000E0;
        } else if (ratio < 3.2) {
            double a = (ratio - 2.0) / 1.2;
            color1 = shade(a, 0xFF0000D0, 0xFF00D000);
            color2 = shade(a, 0xFF0000E0, 0xFF00E000);
        } else if (ratio < 3.95) {
            double a = (ratio - 3.2) / 0.75;
            color1 = shade(a, 0xFF00D000, 0xFFEED000);
            color2 = shade(a, 0xFF00E000, 0xFFFFE000);
        } else if (ratio < 4.0) {
            double a = (ratio - 3.95) / 0.05;
            color1 = shade(a, 0xFFEED000, 0xFFD00000);
            color2 = shade(a, 0xFFFFE000, 0xFFF00000);
        } else {
            color1 = 0xFFD00000;
            color2 = 0xFFF00000;
        }
        ElementProgressExtended.Builder builder = ElementProgressExtended.start().setCurrent(average).setMax(total).setFormatForMax("#").setInfixWithFormat(infixFormat)
            .setStyle(probeInfo.defaultProgressStyle()
            .borderColor(0x00000000)
            .backgroundColor(0x00000000)
            .filledColor(color2)
            .alternateFilledColor(color1));
        if (average < threshold1) builder.setFormatForCurrent("0.00");
        else if (average < threshold2) builder.setFormatForCurrent("0.0");
        else builder.setFormatForCurrent("#");
        pane.element(builder.build());
    }

    @Override
    protected void addProbeInfo(IEnergyContainer capability, IProbeInfo probeInfo, TileEntity tileEntity, EnumFacing sideHit) {
        if (capability instanceof CableEnergyContainer) {
            long[] data = ((CableEnergyContainer) capability).getAverageData();
            long amperage = capability.getInputAmperage();
            long voltage = capability.getInputVoltage();
            if (data[0] == 0 || amperage == 0 || voltage == 0) return;
            IProbeInfo horizontalPane = probeInfo.horizontal(probeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER));
            horizontalPane.element(new ElementTextAdvanced(TextStyleClass.INFO + "{*gregtech.top.cable_average_voltage{%"+ EnergyNet.STATISTIC_COUNT +"%}*} "));
            setCableProgressBar(probeInfo, horizontalPane, (double) data[1] / (double) data[0], voltage, "%s / %s V", 5.0, 100.0);
            horizontalPane = probeInfo.horizontal(probeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER));
            horizontalPane.element(new ElementTextAdvanced(TextStyleClass.INFO + "{*gregtech.top.cable_average_amperage{%"+ EnergyNet.STATISTIC_COUNT +"%}*} "));
            setCableProgressBar(probeInfo, horizontalPane, (double) data[0] / (double) EnergyNet.STATISTIC_COUNT, amperage, "%s / %s A", 3.0, 30.0);
        } else {
            BigInteger energyStored = capability.getEnergyStoredActual();
            BigInteger maxStorage = capability.getEnergyCapacityActual();
            if(maxStorage.compareTo(BigInteger.ZERO) == 0) return; //do not add empty max storage progress bar
            IProbeInfo horizontalPane = probeInfo.horizontal(probeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER));
            String additionalSpacing = tileEntity.hasCapability(IWorkable.CAPABILITY_WORKABLE, sideHit) ? "   " : "";
            horizontalPane.text(TextStyleClass.INFO + "{*gregtech.top.energy_stored*} " + additionalSpacing);
            horizontalPane.element(ElementProgressExtended.start()
                .setCurrent(energyStored)
                .setMax(maxStorage)
                .setFormatForCurrent("#")
                .setFormatForMax("#")
                .setInfixWithFormat("%s / %s EU")
                .setStyle(probeInfo.defaultProgressStyle()
                    .borderColor(0x00000000)
                    .backgroundColor(0x00000000)
                    .filledColor(0xFFFFE000)
                    .alternateFilledColor(0xFFEED000))
                .build());
        }
    }

}
