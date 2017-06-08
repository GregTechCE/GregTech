package gregtech.api.interfaces.tileentity;

/**
 * For Machines which have Progress
 */
public interface IMachineProgress extends IHasWorldObjectAndCoords {

    /**
     * returns the Progress this Machine has made. Warning, this can also be negative!
     */
    int getProgress();

    /**
     * returns the Progress the Machine needs to complete its task.
     */
    int getMaxProgress();

    /**
     * increases the Progress of the Machine
     */
    void increaseProgress(int progressAmountInTicks);

    /**
     * returns if the Machine currently does something.
     */
    boolean hasThingsToDo();

    /**
     * returns if the Machine just got enableWorking called after being disabled.
     * Used for Translocators, which need to check if they need to transfer immediately.
     */
    boolean hasWorkJustBeenEnabled();

    /**
     * allows Machine to work
     */
    void enableWorking();

    /**
     * disallows Machine to work
     */
    void disableWorking();

    /**
     * if the Machine is allowed to Work
     */
    boolean isAllowedToWork();

    /**
     * used to control Machines via Redstone Signal Strength by special Covers
     * In case of 0 the Machine is very likely doing nothing, or is just not being controlled at all.
     */
    byte getWorkDataValue();

    /**
     * used to control Machines via Redstone Signal Strength by special Covers
     * only Values between 0 and 15!
     */
    void setWorkDataValue(byte aValue);

    /**
     * gives you the Active Status of the Machine
     */
    boolean isActive();

    /**
     * sets the visible Active Status of the Machine
     */
    void setActive(boolean active);

}