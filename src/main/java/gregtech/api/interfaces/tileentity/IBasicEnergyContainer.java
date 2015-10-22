package gregtech.api.interfaces.tileentity;

/**
 * Interface for internal Code, which is mainly used for independent Energy conversion.
 */
public interface IBasicEnergyContainer extends IEnergyConnected {
    /**
     * Gets if that Amount of Energy is stored inside the Machine.
     * It is used for checking the contained Energy before consuming it.
     * If this returns false, it will also give a Message inside the Scanner, that this Machine doesn't have enough Energy.
     */
    public boolean isUniversalEnergyStored(long aEnergyAmount);

    /**
     * Gets the stored electric, kinetic or steam Energy (with EU as reference Value)
     * Always returns the largest one.
     */
    public long getUniversalEnergyStored();

    /**
     * Gets the largest electric, kinetic or steam Energy Capacity (with EU as reference Value)
     */
    public long getUniversalEnergyCapacity();

    /**
     * Gets the amount of Energy Packets per tick.
     */
    public long getOutputAmperage();

    /**
     * Gets the Output in EU/p.
     */
    public long getOutputVoltage();

    /**
     * Gets the amount of Energy Packets per tick.
     */
    public long getInputAmperage();

    /**
     * Gets the maximum Input in EU/p.
     */
    public long getInputVoltage();

    /**
     * Decreases the Amount of stored universal Energy. If ignoring too less Energy, then it just sets the Energy to 0 and returns false.
     */
    public boolean decreaseStoredEnergyUnits(long aEnergy, boolean aIgnoreTooLessEnergy);

    /**
     * Increases the Amount of stored electric Energy. If ignoring too much Energy, then the Energy Limit is just being ignored.
     */
    public boolean increaseStoredEnergyUnits(long aEnergy, boolean aIgnoreTooMuchEnergy);

    /**
     * Drain Energy Call for Electricity.
     */
    public boolean drainEnergyUnits(byte aSide, long aVoltage, long aAmperage);

    /**
     * returns the amount of Electricity, accepted by this Block the last 5 ticks as Average.
     */
    public long getAverageElectricInput();

    /**
     * returns the amount of Electricity, outputted by this Block the last 5 ticks as Average.
     */
    public long getAverageElectricOutput();

    /**
     * returns the amount of electricity contained in this Block, in EU units!
     */
    public long getStoredEU();

    /**
     * returns the amount of electricity containable in this Block, in EU units!
     */
    public long getEUCapacity();

    /**
     * returns the amount of Steam contained in this Block, in EU units!
     */
    public long getStoredSteam();

    /**
     * returns the amount of Steam containable in this Block, in EU units!
     */
    public long getSteamCapacity();

    /**
     * Increases stored Energy. Energy Base Value is in EU, even though it's Steam!
     *
     * @param aEnergy              The Energy to add to the Machine.
     * @param aIgnoreTooMuchEnergy if it shall ignore if it has too much Energy.
     * @return if it was successful
     * <p/>
     * And yes, you can't directly decrease the Steam of a Machine. That is done by decreaseStoredEnergyUnits
     */
    public boolean increaseStoredSteam(long aEnergy, boolean aIgnoreTooMuchEnergy);
}