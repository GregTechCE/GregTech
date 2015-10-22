package gregtech.api.interfaces.internal;

import gregtech.api.interfaces.tileentity.IHasWorldObjectAndCoords;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.tile.IEnergyStorage;

/**
 * A simple compound Interface for generic EnergyTileEntities. I don't want to have imports of the IC2-API in my main-code
 */
public interface IIC2TileEntity extends IEnergyStorage, IEnergySink, IEnergySource, IHasWorldObjectAndCoords {
    //
}