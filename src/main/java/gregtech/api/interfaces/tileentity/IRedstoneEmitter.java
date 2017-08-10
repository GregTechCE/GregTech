package gregtech.api.interfaces.tileentity;

import net.minecraft.util.EnumFacing;

/**
 * This File has just internal Information about the Redstone State of a TileEntity
 */
public interface IRedstoneEmitter extends IHasWorldObjectAndCoords, IRedstoneTileEntity {

    int getOutputRedstoneSignal(EnumFacing side);

    void setOutputRedstoneSignal(EnumFacing side, int strength);

    /**
     * Gets the Output for the comparator on the given Side
     */
    int getComparatorValue(EnumFacing side);

}