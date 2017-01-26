package gregtech.api.net;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * Used to transfer Block Events in a much better fashion
 */
public class GT_Packet_Block_Event extends GT_Packet {
    private int mX, mZ;
    private short mY;
    private byte mID, mValue;

    public GT_Packet_Block_Event() {}

    public GT_Packet_Block_Event(int aX, short aY, int aZ, byte aID, byte aValue) {
        mX = aX;
        mY = aY;
        mZ = aZ;
        mID = aID;
        mValue = aValue;
    }

    @Override
    public void encode(ByteBuf buf) {
        buf.writeInt(mX);
        buf.writeShort(mY);
        buf.writeInt(mZ);
        buf.writeByte(mID);
        buf.writeByte(mValue);
    }

    @Override
    public void decode(ByteBuf buf) {
        mX = buf.readInt();
        mY = buf.readShort();
        mZ = buf.readInt();
        mID = buf.readByte();
        mValue = buf.readByte();
    }

    @Override
    public void process(World aWorld) {
        if (aWorld != null) {
            TileEntity tTileEntity = aWorld.getTileEntity(new BlockPos(mX, mY, mZ));
            if (tTileEntity != null) tTileEntity.receiveClientEvent(mID, mValue);
        }
    }
}