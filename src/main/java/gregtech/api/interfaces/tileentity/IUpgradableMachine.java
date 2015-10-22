package gregtech.api.interfaces.tileentity;


/**
 * To access my Machines a bit easier
 */
public interface IUpgradableMachine extends IMachineProgress {
    /**
     * Accepts Upgrades. Some Machines have an Upgrade Limit.
     */
    boolean isUpgradable();

    /**
     * Accepts Muffler Upgrades
     */
    boolean isMufflerUpgradable();

    /**
     * Accepts Steam-Converter Upgrades
     */
    boolean isSteamEngineUpgradable();

    /**
     * Adds Muffler Upgrade
     */
    boolean addMufflerUpgrade();

    /**
     * Adds MJ-Converter Upgrade
     */
    boolean addSteamEngineUpgrade();

    /**
     * Does this Machine have an Muffler
     */
    boolean hasMufflerUpgrade();

    /**
     * Does this Machine have a Steam-Converter
     */
    boolean hasSteamEngineUpgrade();
}
