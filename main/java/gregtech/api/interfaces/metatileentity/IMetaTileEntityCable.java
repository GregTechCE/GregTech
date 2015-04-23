package gregtech.api.interfaces.metatileentity;

import java.util.ArrayList;

import net.minecraft.tileentity.TileEntity;

public interface IMetaTileEntityCable extends IMetaTileEntity {
    public long transferElectricity(byte aSide, long aVoltage, long aAmperage, ArrayList<TileEntity> aAlreadyPassedTileEntityList);
}