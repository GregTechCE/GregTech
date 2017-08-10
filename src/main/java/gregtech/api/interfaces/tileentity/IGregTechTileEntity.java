package gregtech.api.interfaces.tileentity;

import gregtech.api.interfaces.metatileentity.IMetaTileEntity;

import javax.annotation.Nullable;

/**
 * A simple compound Interface for all my TileEntities.
 * <p/>
 * Also delivers most of the Informations about my TileEntities.
 * <p/>
 */
public interface IGregTechTileEntity extends IHasWorldObjectAndCoords {

    @Nullable IMetaTileEntity getMetaTileEntity();

    void setMetaTileEntity(@Nullable IMetaTileEntity metaTileEntity);

}