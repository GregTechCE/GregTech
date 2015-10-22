package gregtech.api.net;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;

/**
 * Used to transfer Block Events in a much better fashion
 */
public class GT_Packet_Block_Event extends GT_Packet {
    private int mX, mZ;
    private short mY;
    private byte mID, mValue;

    public GT_Packet_Block_Event() {
        super(true);
    }

    public GT_Packet_Block_Event(int aX, short aY, int aZ, byte aID, byte aValue) {
        super(false);
        mX = aX;
        mY = aY;
        mZ = aZ;
        mID = aID;
        mValue = aValue;
    }

    @Override
    public byte[] encode() {
        ByteArrayDataOutput tOut = ByteStreams.newDataOutput(10);
        tOut.writeInt(mX);
        tOut.writeShort(mY);
        tOut.writeInt(mZ);
        tOut.writeByte(mID);
        tOut.writeByte(mValue);
        return tOut.toByteArray();
    }

    @Override
    public GT_Packet decode(ByteArrayDataInput aData) {
        return new GT_Packet_Block_Event(aData.readInt(), aData.readShort(), aData.readInt(), aData.readByte(), aData.readByte());
    }

    @Override
    public void process(IBlockAccess aWorld) {
        if (aWorld != null) {
            TileEntity tTileEntity = aWorld.getTileEntity(mX, mY, mZ);
            if (tTileEntity != null) tTileEntity.receiveClientEvent(mID, mValue);
        }
    }

    @Override
    public byte getPacketID() {
        return 2;
    }
}