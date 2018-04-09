package gregtech.common.cable.tile;

import gregtech.api.capability.IEnergyContainer;
import net.minecraft.util.EnumFacing;

public class CableEnergyContainer implements IEnergyContainer {
    @Override
    public long acceptEnergyFromNetwork(EnumFacing side, long voltage, long amperage) {
        return 0;
    }

    @Override
    public long getInputAmperage() {
        return 0;
    }

    @Override
    public long getInputVoltage() {
        return 0;
    }

    @Override
    public boolean inputsEnergy(EnumFacing side) {
        return true;
    }

    @Override
    public long getEnergyStored() {
        return 0;
    }

    @Override
    public long getEnergyCapacity() {
        return 0;
    }

    @Override
    public void addEnergy(long energyToAdd) {
    }

}
