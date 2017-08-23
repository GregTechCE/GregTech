package gregtech.api.capability.internal;

import net.minecraft.util.EnumFacing;

/**
 * This File has just internal Information about the Redstone State of a TileEntity
 */
public interface IRedstoneEmitter extends IHasWorldObjectAndCoords, IRedstoneTileEntity {

    int getOutputRedstoneSignal(EnumFacing side);

    void setOutputRedstoneSignal(EnumFacing side, int strength);

    int getComparatorValue();

}