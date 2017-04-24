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
    public EnumFacing getOutputFacing();

    /**
     * sets Output Redstone State at Side
     */
    public boolean setRedstone(byte aStrength, EnumFacing aSide);

    /**
     * returns Output Redstone State at Side
     * Note that setRedstone checks if there is a Difference between the old and the new Setting before consuming any Energy
     */
    public byte getOutputRedstone(EnumFacing aSide);

    /**
     * returns Input Redstone Signal at Side
     */
    public byte getInputRedstone(EnumFacing aSide);

    /**
     * If this Side is Covered up and therefor not doing any Redstone
     */
    public GT_CoverBehavior getCover(EnumFacing aSide);

    public int getCoverID(EnumFacing aSide);

    public int getCoverVariable(EnumFacing aSide);

    /**
     * returns whatever TileEntity is used by the Redstone Circuit Block
     */
    public ICoverable getOwnTileEntity();

    /**
     * returns worldObj.rand.nextInt(aRange)
     */
    public int getRandom(int aRange);
}
