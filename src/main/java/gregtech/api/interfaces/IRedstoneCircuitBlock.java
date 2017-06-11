package gregtech.api.interfaces;

import gregtech.api.interfaces.tileentity.ICoverable;
import gregtech.api.util.GT_CoverBehavior;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

/**
 * Implemented by the MetaTileEntity of the Redstone Circuit Block
 */
public interface IRedstoneCircuitBlock {

    /**
     * The Output Direction the Circuit Block is Facing
     */
    EnumFacing getOutputFacing();

    /**
     * sets Output Redstone State at Side
     */
    boolean setRedstone(byte strength, EnumFacing side);

    /**
     * returns Output Redstone State at Side
     * Note that setRedstone checks if there is a Difference between the old and the new Setting before consuming any Energy
     */
    byte getOutputRedstone(EnumFacing side);

    /**
     * returns Input Redstone Signal at Side
     */
    byte getInputRedstone(EnumFacing side);

    int getCoverID(EnumFacing side);

    int getCoverData(EnumFacing side);

    /**
     * returns whatever TileEntity is used by the Redstone Circuit Block
     */
    ICoverable getOwnTileEntity();

    int getRandom(int range);

}
