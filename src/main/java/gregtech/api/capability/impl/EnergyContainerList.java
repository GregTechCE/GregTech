package gregtech.api.capability.impl;

import gregtech.api.capability.IEnergyContainer;
import net.minecraft.util.EnumFacing;

import java.util.List;

public class EnergyContainerList implements IEnergyContainer {

    private List<IEnergyContainer> energyContainerList;

    public EnergyContainerList(List<IEnergyContainer> energyContainerList) {
        this.energyContainerList = energyContainerList;
    }

    @Override
    public long acceptEnergyFromNetwork(EnumFacing side, long voltage, long amperage) {
        return (long) Math.floor(addEnergy(voltage * amperage) / (voltage * 1.0));
    }

    @Override
    public long addEnergy(long energyToAdd) {
        long energyAdded = 0L;
        for(IEnergyContainer energyContainer : energyContainerList) {
            energyAdded += energyContainer.addEnergy(energyToAdd - energyAdded);
            if(energyAdded == energyToAdd) break;
        }
        return energyAdded;
    }

    @Override
    public long getEnergyStored() {
        return energyContainerList.stream()
            .mapToLong(IEnergyContainer::getEnergyStored)
            .sum();
    }

    @Override
    public long getEnergyCapacity() {
        return energyContainerList.stream()
            .mapToLong(IEnergyContainer::getEnergyCapacity)
            .sum();
    }

    @Override
    public long getInputAmperage() {
        return energyContainerList.stream()
            .mapToLong(IEnergyContainer::getInputAmperage)
            .max().orElse(0L);
    }

    @Override
    public long getInputVoltage() {
        return energyContainerList.stream()
            .mapToLong(IEnergyContainer::getInputVoltage)
            .max().orElse(0L);
    }

    @Override
    public long getOutputVoltage() {
        return energyContainerList.stream()
            .mapToLong(IEnergyContainer::getOutputVoltage)
            .max().orElse(0L);
    }

    @Override
    public boolean inputsEnergy(EnumFacing side) {
        return true;
    }

    @Override
    public boolean outputsEnergy(EnumFacing side) {
        return true;
    }
}
