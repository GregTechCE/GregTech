package gregtech.common.blocks;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import gregtech.api.net.GT_Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class GT_Packet_Ores
        extends GT_Packet {
    private int mX;
    private int mZ;
    private short mY;
    private short mMetaData;

    public GT_Packet_Ores() {
        super(true);
    }

    public GT_Packet_Ores(int aX, short aY, int aZ, short aMetaData) {
        super(false);
        this.mX = aX;
        this.mY = aY;
        this.mZ = aZ;
        this.mMetaData = aMetaData;
    }

    public byte[] encode() {
        ByteArrayDataOutput tOut = ByteStreams.newDataOutput(12);

        tOut.writeInt(this.mX);
        tOut.writeShort(this.mY);
        tOut.writeInt(this.mZ);
        tOut.writeShort(this.mMetaData);

        return tOut.toByteArray();
    }

    public GT_Packet decode(ByteArrayDataInput aData) {
        return new GT_Packet_Ores(aData.readInt(), aData.readShort(), aData.readInt(), aData.readShort());
    }

    public void process(IBlockAccess aWorld) {
        if (aWorld != null) {
            TileEntity tTileEntity = aWorld.getTileEntity(this.mX, this.mY, this.mZ);
            if ((tTileEntity instanceof GT_TileEntity_Ores)) {
                ((GT_TileEntity_Ores) tTileEntity).mMetaData = this.mMetaData;
            }
            if (((aWorld instanceof World)) && (((World) aWorld).isRemote)) {
                ((World) aWorld).markBlockForUpdate(this.mX, this.mY, this.mZ);
            }
        }
    }

    public byte getPacketID() {
        return 3;
    }
}
