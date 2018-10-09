package gregtech.api.capability.impl;

import gregtech.api.capability.IEnergyContainer;
import net.minecraft.util.EnumFacing;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToLongFunction;

import static gregtech.api.util.GTUtility.*;

public class EnergyContainerList implements IEnergyContainer.IEnergyContainerOverflowSafe {

    private List<IEnergyContainer> energyContainerList;

    public EnergyContainerList(List<IEnergyContainer> energyContainerList) {
        this.energyContainerList = energyContainerList;
    }

    @Override
    public long acceptEnergyFromNetwork(EnumFacing side, long voltage, long amperage) {
        long amperesUsed = 0L;
        for (IEnergyContainer energyContainer : energyContainerList) {
            amperesUsed += energyContainer.acceptEnergyFromNetwork(null, voltage, amperage);
            if (amperage == amperesUsed) break;
        }
        return amperesUsed;
    }

    @Override
    public long changeEnergy(long energyToAdd) {
        long energyAdded = 0L;
        for(IEnergyContainer energyContainer : energyContainerList) {
            energyAdded += energyContainer.changeEnergy(energyToAdd - energyAdded);
            if(energyAdded == energyToAdd) break;
        }
        return energyAdded;
    }

    private long getCastedSum(ToLongFunction<IEnergyContainer> toLong, Function<IEnergyContainer, BigInteger> toBigInteger) {
        List<IEnergyContainer> overflowSafe = new ArrayList<>();
        List<IEnergyContainer> overflowUnsafe = new ArrayList<>();
        for (IEnergyContainer energyContainer : energyContainerList) {
            (energyContainer.isSummationOverflowSafe() ? overflowSafe : overflowUnsafe).add(energyContainer);
        }
        long[] values = overflowSafe.stream()
            .mapToLong(toLong)
            .toArray();
        if (overflowUnsafe.isEmpty()) {
            return castedSum(values);
        } else {
            BigInteger result = sum(values);
            for (IEnergyContainer energyContainer : overflowUnsafe) {
                result = result.add(toBigInteger.apply(energyContainer));
            }
            return castToLong(result);
        }
    }

    private BigInteger getActualSum(ToLongFunction<IEnergyContainer> toLong, Function<IEnergyContainer, BigInteger> toBigInteger) {
        List<IEnergyContainer> overflowSafe = new ArrayList<>();
        List<IEnergyContainer> overflowUnsafe = new ArrayList<>();
        for (IEnergyContainer energyContainer : energyContainerList) {
            (energyContainer.isSummationOverflowSafe() ? overflowSafe : overflowUnsafe).add(energyContainer);
        }
        BigInteger result = sum(overflowSafe.stream()
            .mapToLong(toLong)
            .toArray());
        if (!overflowUnsafe.isEmpty()) for (IEnergyContainer energyContainer : overflowUnsafe) {
            result = result.add(toBigInteger.apply(energyContainer));
        }
        return result;
    }

    @Override
    public long getEnergyStored() {
        return getCastedSum(IEnergyContainer::getEnergyStored, IEnergyContainer::getEnergyStoredActual);
    }

    @Override
    public BigInteger getEnergyStoredActual() {
        return getActualSum(IEnergyContainer::getEnergyStored, IEnergyContainer::getEnergyStoredActual);
    }

    @Override
    public long getEnergyCapacity() {
        return getCastedSum(IEnergyContainer::getEnergyCapacity, IEnergyContainer::getEnergyCapacityActual);
    }

    @Override
    public BigInteger getEnergyCapacityActual() {
        return getActualSum(IEnergyContainer::getEnergyCapacity, IEnergyContainer::getEnergyCapacityActual);
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
