package gregtech.api.capability.internal;

import gregtech.api.gui.IUIHolder;
import gregtech.api.metatileentity.IMetaTileEntity;

import javax.annotation.Nullable;

/**
 * A simple compound Interface for all my TileEntities.
 * <p/>
 * Also delivers most of the Informations about TileEntities.
 * <p/>
 */
public interface IGregTechTileEntity extends IHasWorldObjectAndCoords, IUIHolder {

    @Nullable IMetaTileEntity getMetaTileEntity();

    void setMetaTileEntity(IMetaTileEntity metaTileEntity);
}