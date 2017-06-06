package gregtech.api.interfaces.metatileentity;

import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;

public interface IMetaTileEntityCable extends IMetaTileEntity {

    long transferElectricity(byte aSide, long aVoltage, long aAmperage, ArrayList<TileEntity> aAlreadyPassedTileEntityList);

}