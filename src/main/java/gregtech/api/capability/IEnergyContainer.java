package gregtech.api.capability;

import net.minecraft.util.EnumFacing;

import java.math.BigInteger;

import static gregtech.api.util.GTUtility.castToLong;

public interface IEnergyContainer {

    /**
     * Use this in case of overflow
     */
    interface IEnergyContainerOverflowSafe extends IEnergyContainer {

        @Override
        BigInteger getEnergyStoredActual();

        @Override
        BigInteger getEnergyCapacityActual();

        @Override
        default long getEnergyStored() {
            return castToLong(getEnergyStoredActual());
        }

        @Override
        default long getEnergyCapacity() {
            return castToLong(getEnergyCapacityActual());
        }

        @Override
        default boolean canUse(long energy) {
            return getEnergyStoredActual().compareTo(BigInteger.valueOf(energy)) >= 0;
        }

        @Override
        default long getEnergyCanBeInserted() {
            return castToLong(getEnergyCapacityActual().subtract(getEnergyStoredActual()));
        }

        @Override
        default boolean isSummationOverflowSafe() {
            return false;
        }
    }

    /**
     * @return amount of used amperes. 0 if not accepted anything.
     */
    long acceptEnergyFromNetwork(EnumFacing side, long voltage, long amperage);

    boolean inputsEnergy(EnumFacing side);

    default boolean outputsEnergy(EnumFacing side) {
        return false;
    }

    long changeEnergy(long differenceAmount);

    default long addEnergy(long energyToAdd) {
        return changeEnergy(energyToAdd);
    }

    default long removeEnergy(long energyToRemove) {
        return changeEnergy(-energyToRemove);
    }

    default boolean canUse(long energy) {
        return getEnergyStored()  >= energy;
    }

    default long getEnergyCanBeInserted() {
        return getEnergyCapacity() - getEnergyStored();
    }

    /**
     * Gets the stored electric energy, casted to {@link Long#MAX_VALUE} if overflowed
     */
    long getEnergyStored();

    /**
     * Gets the largest electric energy capacity, casted to {@link Long#MAX_VALUE} if overflowed
     */
    long getEnergyCapacity();

    /**
     * Gets the actual stored electric energy, in case of overflow
     */
    default BigInteger getEnergyStoredActual() {
        return BigInteger.valueOf(getEnergyStored());
    }

    /**
     * Gets the largest electric energy capacity, in case of overflow
     */
    default BigInteger getEnergyCapacityActual() {
        return BigInteger.valueOf(getEnergyCapacity());
    }

    /**
     * Gets the amount of energy packets per tick.
     */
    default long getOutputAmperage() {
        return 0L;
    }

    /**
     * Gets the output in energy units per energy packet.
     */
    default long getOutputVoltage() {
        return 0L;
    }

    /**
     * Gets the amount of energy packets this machine can receive
     */
    long getInputAmperage();

    /**
     * Gets the maximum voltage this machine can receive in one energy packet.
     * Overflowing this value will explode machine.
     */
    long getInputVoltage();

    /**
     * Return true if this container won't overflow when computing {@link #getEnergyStored()} or {@link #getEnergyCapacity()}
     */
    default boolean isSummationOverflowSafe() {
        return true;
    }

    default boolean isOneProbeHidden() {
        return false;
    }

}