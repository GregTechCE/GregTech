package gregtech.api.capability;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public interface IEnergyContainer {

    @CapabilityInject(IEnergyContainer.class)
    public static final Capability<IEnergyContainer> CAPABILITY_ENERGY_CONTAINER = null;

    /**
     * @return amount of used amperes. 0 if not accepted anything.
     */
    long acceptEnergyFromNetwork(EnumFacing side, long voltage, long amperage);

    boolean inputsEnergy(EnumFacing side);

    boolean outputsEnergy(EnumFacing side);

    /**
     * Gets if that amount of electric energy is stored inside the machine.
     * It is used for checking the contained energy before consuming it.
     */
    default boolean isEnergyStored(long amount) {
        return getEnergyStored() >= amount;
    }

    /**
     * Gets the stored electric energy
     */
    long getEnergyStored();

    void setEnergyStored(long energyStored);

    /**
     * Gets the largest electric energy capacity
     */
    long getEnergyCapacity();

    /**
     * Gets the amount of energy packets per tick.
     */
    long getOutputAmperage();

    /**
     * Gets the output in energy units per energy packet.
     */
    long getOutputVoltage();

    /**
     * Gets the amount of energy packets this machine can receive
     */
    long getInputAmperage();

    /**
     * Gets the maximum voltage this machine can receive in one energy packet.
     * Overflowing this value will explode machine.
     */
    long getInputVoltage();

}