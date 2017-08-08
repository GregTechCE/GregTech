package gregtech.api.interfaces.metatileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

import java.util.ArrayList;

public interface IMetaTileEntityCable extends IMetaTileEntity {

    /**
     * Recursively transfers electricity trough network
     *
     * @param sourceSide source side of electricity.
     * @param alreadyPassedTileEntityList list of tile entities which this network tick already passed.
     * @return electricity transferred
     */
    long transferElectricity(byte sourceSide, long voltage, long amperage, ArrayList<TileEntity> alreadyPassedTileEntityList);

}