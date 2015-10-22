package gregtech.api.interfaces.tileentity;

import gregtech.api.interfaces.ITexture;

public interface IPipeRenderedTileEntity extends ICoverable, ITexturedTileEntity {
    public float getThickNess();

    public byte getConnections();

    public ITexture[] getTextureUncovered(byte aSide);
}