package gregtech.api.interfaces.tileentity;

/**
 * This File has just internal Information about the Redstone State of a TileEntity
 */
public interface IRedstoneTileEntity extends IRedstoneEmitter, IRedstoneReceiver {

    /**
     * enables/disables Redstone Output in general.
     */
    void setGenericRedstoneOutput(boolean onOff);

    /**
     * Causes a general Block update.
     * Sends nothing to Client, just causes a Block Update.
     */
    void issueBlockUpdate();

}