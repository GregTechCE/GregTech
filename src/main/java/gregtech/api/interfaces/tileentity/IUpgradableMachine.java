package gregtech.api.interfaces.tileentity;


/**
 * To access my Machines a bit easier
 */
public interface IUpgradableMachine extends IMachineProgress {

    /**
     * Accepts Muffler Upgrades
     */
    boolean isMufflerUpgradable();

    /**
     * Adds Muffler Upgrade
     */
    boolean addMufflerUpgrade();

    /**
     * Does this Machine have an Muffler
     */
    boolean hasMufflerUpgrade();

}
