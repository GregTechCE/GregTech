package gregtech.api.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public interface IEnergyContainer {

    @CapabilityInject(IEnergyContainer.class)
    public static final Capability<IEnergyContainer> CAPABILITY_ENERGY_CONTAINER = null;

    /**
     * @return amount of used amperes. 0 if not accepted anything.
     */
    long acceptEnergyFromNetwork(long voltage, long amperage);

    boolean inputsEnergy();

    boolean outputsEnergy();


    /**
     * Gets if that amount of electric energy is stored inside the machine.
     * It is used for checking the contained energy before consuming it.
     */
    boolean isEnergyStored(long amount);

    /**
     * Gets the stored electric energy
     */
    long getEnergyStored();

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
     * Overflowing this value WILL NOT blow this machine.
     */
    long getInputAmperage();

    /**
     * Gets the maximum voltage this machine can receive in one energy packet.
     * Overflowing this value WILL explode machine.
     */
    long getInputVoltage();

    /**
     * Decreases the energy stored inside the machine
     * @param ignoreTooLessEnergy if true, machine should draw all of it's entire buffer even if there isn't enough EU in buffer
     * @return true if there is enough energy units and they were removed from machine, false otherwise
     */
    boolean decreaseStoredEnergyUnits(long energy, boolean ignoreTooLessEnergy);

    /**
     * Increases the energy stored inside the machine
     * @param ignoreTooMuchEnergy if true, machine should fill all of it's entire buffer even if there is more EU than it can receive
     * @return true if there is enough free space in internal buffer and EU was injected, false otherwise
     */
    boolean increaseStoredEnergyUnits(long energy, boolean ignoreTooMuchEnergy);

    /**
     * @return average amount of EU/t this block received in last 5 ticks
     */
    long getAverageElectricInput();

    /**
     * @return average amount of EU/t this block output in last 5 ticks
     */
    long getAverageElectricOutput();



}