package gregtech.common.pipelike.inventory.net;

import gregtech.api.capability.IEnergyContainer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.util.INBTSerializable;

public class InventoryPipeNetEnergyContainer implements IEnergyContainer, INBTSerializable<NBTTagCompound> {

    private final long maxCapacity = 1024L;
    private long energyStored = 0L;

    public void setEnergyStored(long energyStored) {
        this.energyStored = energyStored;
    }

    @Override
    public long acceptEnergyFromNetwork(EnumFacing side, long voltage, long amperage) {
        long canAccept = getEnergyCapacity() - getEnergyStored();
        if (voltage > 0L && amperage > 0L && (side == null || inputsEnergy(side))) {
            if (voltage > getInputVoltage()) {
                //GTUtility.doOvervoltageExplosion(metaTileEntity, voltage);
                return Math.min(amperage, getInputAmperage());
            }
            if (canAccept >= voltage) {
                long amperesAccepted = Math.min(canAccept / voltage, Math.min(amperage, getInputAmperage()));
                if (amperesAccepted > 0) {
                    setEnergyStored(getEnergyStored() + voltage * amperesAccepted);
                    return amperesAccepted;
                }
            }
        }
        return 0L;
    }

    @Override
    public boolean inputsEnergy(EnumFacing side) {
        return true;
    }

    @Override
    public long changeEnergy(long differenceAmount) {
        long oldEnergyStored = getEnergyStored();
        long newEnergyStored = (maxCapacity - oldEnergyStored < differenceAmount) ? maxCapacity : (oldEnergyStored + differenceAmount);
        if (newEnergyStored < 0)
            newEnergyStored = 0;
        setEnergyStored(newEnergyStored);
        return newEnergyStored - oldEnergyStored;
    }

    @Override
    public long getEnergyStored() {
        return energyStored;
    }

    @Override
    public long getEnergyCapacity() {
        return maxCapacity;
    }

    @Override
    public long getInputAmperage() {
        return 1L;
    }

    @Override
    public long getInputVoltage() {
        return 32L;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setLong("EnergyStored", energyStored);
        return compound;
    }

    @Override
    public void deserializeNBT(NBTTagCompound compound) {
        this.energyStored = compound.getLong("EnergyStored");
    }
}
