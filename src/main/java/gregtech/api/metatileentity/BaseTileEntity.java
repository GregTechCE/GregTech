package gregtech.api.metatileentity;

import gregtech.api.interfaces.tileentity.IHasWorldObjectAndCoords;
import gregtech.api.net.GT_Packet_Block_Event;
import gregtech.api.util.GT_Utility;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static gregtech.api.GT_Values.NW;

public abstract class BaseTileEntity extends TileEntity implements IHasWorldObjectAndCoords, ITickable {

    private long timer = 0L;

    @Override
    public World getWorldObj() {
        return worldObj;
    }

    @Override
    public BlockPos getWorldPos() {
        return pos;
    }

    @Override
    public long getTimer() {
        return timer;
    }

    @Override
    public void update() {
        timer++;
    }

}
