package gregtech.api.interfaces.tileentity;

import net.minecraft.util.EnumFacing;

/**
 * This File has just internal Information about the Redstone State of a TileEntity
 */
public interface IRedstoneReceiver extends IHasWorldObjectAndCoords {
    /**
     * gets the Redstone Level of the TileEntity to the given Input Side
     * <p/>
     * Do not use this if ICoverable is implemented. ICoverable has @getInternalInputRedstoneSignal for Machine internal Input Redstone
     * This returns the true incoming Redstone Signal. Only Cover Behaviors should check it, not MetaTileEntities.
     */
    public byte getInputRedstoneSignal(EnumFacing aSide);

    /**
     * gets the strongest Redstone Level the TileEntity receives
     */
    public byte getStrongestRedstone();

    /**
     * gets if the TileEntity receives Redstone
     */
    public boolean getRedstone();

    /**
     * gets if the TileEntity receives Redstone at this Side
     */
    public boolean getRedstone(EnumFacing aSide);
}