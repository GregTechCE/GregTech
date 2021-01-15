package gregtech.integration.theoneprobe.provider;

import gregtech.api.GTValues;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.util.GTUtility;
import gregtech.common.metatileentities.electric.MetaTileEntityTransformer;
import mcjty.theoneprobe.api.ElementAlignment;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.TextStyleClass;
import net.minecraft.client.resources.I18n;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class ElectricContainerInfoProvider extends CapabilityInfoProvider<IEnergyContainer> {

    @Override
    protected Capability<IEnergyContainer> getCapability() {
        return GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER;
    }

    @Override
    public String getID() {
        return "gregtech:energy_container_provider";
    }

    @Override
    protected boolean allowDisplaying(IEnergyContainer capability) {
        return !capability.isOneProbeHidden();
    }

    @Override
    protected void addProbeInfo(IEnergyContainer capability, IProbeInfo probeInfo, TileEntity tileEntity, EnumFacing sideHit) {
        long energyStored = capability.getEnergyStored();
        long maxStorage = capability.getEnergyCapacity();
        if (maxStorage == 0) return; //do not add empty max storage progress bar
        IProbeInfo horizontalPane = probeInfo.horizontal(probeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER));
        String additionalSpacing = tileEntity.hasCapability(GregtechTileCapabilities.CAPABILITY_WORKABLE, sideHit) ? "   " : "";
        horizontalPane.text(TextStyleClass.INFO + "{*gregtech.top.energy_stored*} " + additionalSpacing);
        horizontalPane.progress(energyStored, maxStorage, probeInfo.defaultProgressStyle()
            .suffix("/" + maxStorage + " EU")
            .borderColor(0x00000000)
            .backgroundColor(0x00000000)
            .filledColor(0xFFFFE000)
            .alternateFilledColor(0xFFEED000));
        if (tileEntity instanceof MetaTileEntityHolder) {
            MetaTileEntity metaTileEntity = ((MetaTileEntityHolder)tileEntity).getMetaTileEntity();
            if (metaTileEntity instanceof MetaTileEntityTransformer) {
                MetaTileEntityTransformer mteTransformer = ((MetaTileEntityTransformer)metaTileEntity);
                String inputVoltageN = GTValues.VN[GTUtility.getTierByVoltage(capability.getInputVoltage())];
                String outputVoltageN = GTValues.VN[GTUtility.getTierByVoltage(capability.getOutputVoltage())];
                long inputAmperage = capability.getInputAmperage();
                long outputAmperage = capability.getOutputAmperage();
                String transformInfo;

                // Step Up/Step Down line
                if (mteTransformer.isInverted())
                    transformInfo = I18n.format("gregtech.top.transform_up",
                        inputVoltageN, inputAmperage, outputVoltageN, outputAmperage);
                else
                    transformInfo = I18n.format("gregtech.top.transform_down",
                        inputVoltageN, inputAmperage, outputVoltageN, outputAmperage);
                horizontalPane = probeInfo.vertical(probeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER));
                horizontalPane.text(TextStyleClass.INFO + transformInfo);

                // Input/Output side line
                horizontalPane = probeInfo.vertical(probeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER));
                if (capability.inputsEnergy(sideHit)) {
                    transformInfo = I18n.format("gregtech.top.transform_input", inputVoltageN, inputAmperage);
                    horizontalPane.text(TextStyleClass.INFO + transformInfo);
                } else if(capability.outputsEnergy(sideHit)) {
                    transformInfo = I18n.format("gregtech.top.transform_output", outputVoltageN, outputAmperage);
                    horizontalPane.text(TextStyleClass.INFO + transformInfo);
                }
            }
        }
    }
}
