package gregtech.api.capability.impl;

import gregtech.api.capability.IEnergyContainer;
import gregtech.api.util.GTUtility;
import net.minecraft.util.EnumFacing;

import java.math.BigInteger;
import java.util.List;

public class EnergyContainerList implements IEnergyContainer.IEnergyContainerOverflowSafe {

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
    public BigInteger getEnergyStoredActual() {
        BigInteger result = GTUtility.sum(energyContainerList.stream()
            .filter(IEnergyContainer::noLongOverflowInSummation)
            .mapToLong(IEnergyContainer::getEnergyStored)
            .sorted().toArray());
        for (IEnergyContainer energyContainer : energyContainerList) if (!energyContainer.noLongOverflowInSummation()) {
            result = result.add(energyContainer.getEnergyStoredActual());
        }
        return result;
    }

    @Override
    public BigInteger getEnergyCapacityActual() {
        BigInteger result = GTUtility.sum(energyContainerList.stream()
            .filter(IEnergyContainer::noLongOverflowInSummation)
            .mapToLong(IEnergyContainer::getEnergyCapacity)
            .sorted().toArray());
        for (IEnergyContainer energyContainer : energyContainerList) if (!energyContainer.noLongOverflowInSummation()) {
            result = result.add(energyContainer.getEnergyCapacityActual());
        }
        return result;
    }

    @Override
    public long getInputAmperage() {
        return 1L;
    }

    @Override
    public long getOutputAmperage() {
        return 1L;
    }

    @Override
    public long getInputVoltage() {
        return energyContainerList.stream()
            .mapToLong(v -> v.getInputVoltage() * v.getInputAmperage())
            .sum();
    }

    @Override
    public long getOutputVoltage() {
        return energyContainerList.stream()
            .mapToLong(v -> v.getOutputVoltage() * v.getOutputAmperage())
            .sum();
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
