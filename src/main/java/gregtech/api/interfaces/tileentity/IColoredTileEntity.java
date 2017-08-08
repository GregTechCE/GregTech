package gregtech.api.interfaces.tileentity;

import gregtech.api.material.Dyes;

public interface IColoredTileEntity {

    Dyes getColorization();

    byte setColorization(Dyes color);

}
