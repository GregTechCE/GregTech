package gregtech.api.capability.internal;

import net.minecraft.util.EnumFacing;

/**
 * This File has just internal Information about the Redstone State of a TileEntity
 */
public interface IRedstoneReceiver extends IHasWorldObjectAndCoords, IRedstoneTileEntity {
    
    int getInputRedstoneSignal(EnumFacing side);

}