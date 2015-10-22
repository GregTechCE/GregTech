package gregtech.api.interfaces.tileentity;

public interface IColoredTileEntity {
    /**
     * @return 0 - 15 are Colors, while -1 means uncolored
     */
    public byte getColorization();

    /**
     * Sets the Color Modulation of the Block
     *
     * @param aColor the Color you want to set it to. -1 for reset.
     */
    public byte setColorization(byte aColor);
}
