package gregtech.api.interfaces.tileentity;

import gregtech.api.interfaces.ITexture;

public interface ITexturedTileEntity {
    /**
     * @return the Textures rendered by the GT Rendering
     */
    public ITexture[] getTexture(byte aSide);
}