package gregtech.api.interfaces.tileentity;

import net.minecraft.util.EnumFacing;

public interface IRedstoneTileEntity {

    /**
     * Return whether the TileEntity can output redstone to the given side. Used to visually connect
     * vanilla redstone wires.
     */
    boolean canConnectRedstone(EnumFacing side);

}