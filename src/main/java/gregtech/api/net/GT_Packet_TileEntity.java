package gregtech.api.net;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import gregtech.api.GregTech_API;
import gregtech.api.metatileentity.BaseMetaPipeEntity;
import gregtech.api.metatileentity.BaseMetaTileEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class GT_Packet_TileEntity extends GT_Packet {
    private int mX, mZ, mC0, mC1, mC2, mC3, mC4, mC5;
    private short mY, mID;
    private byte mTexture, mUpdate, mRedstone, mColor;
    private boolean isPipeBaseTile;

    public GT_Packet_TileEntity() {}

    public GT_Packet_TileEntity(int aX, short aY, int aZ, short aID, int aC0, int aC1, int aC2, int aC3, int aC4, int aC5, byte aTexture, byte aUpdate, byte aRedstone, byte aColor, boolean isPipeBaseTile) {
        mX = aX;
        mY = aY;
        mZ = aZ;
        mC0 = aC0;
        mC1 = aC1;
        mC2 = aC2;
        mC3 = aC3;
        mC4 = aC4;
        mC5 = aC5;
        mID = aID;
        mTexture = aTexture;
        mUpdate = aUpdate;
        mRedstone = aRedstone;
        mColor = aColor;
        this.isPipeBaseTile = isPipeBaseTile;
    }

    @Override
    public void encode(ByteBuf tOut) {
        tOut.writeInt(mX);
        tOut.writeShort(mY);
        tOut.writeInt(mZ);
        tOut.writeShort(mID);

        tOut.writeInt(mC0);
        tOut.writeInt(mC1);
        tOut.writeInt(mC2);
        tOut.writeInt(mC3);
        tOut.writeInt(mC4);
        tOut.writeInt(mC5);

        tOut.writeByte(mTexture);
        tOut.writeByte(mUpdate);
        tOut.writeByte(mRedstone);
        tOut.writeByte(mColor);
        tOut.writeBoolean(isPipeBaseTile);
    }

    @Override
    public void decode(ByteBuf buf) {
        mX = buf.readInt();
        mY = buf.readShort();
        mZ = buf.readInt();
        mID = buf.readShort();

        mC0 = buf.readInt();
        mC1 = buf.readInt();
        mC2 = buf.readInt();
        mC3 = buf.readInt();
        mC4 = buf.readInt();
        mC5 = buf.readInt();

        mTexture = buf.readByte();
        mUpdate = buf.readByte();
        mRedstone = buf.readByte();
        mColor = buf.readByte();
        isPipeBaseTile = buf.readBoolean();
    }

    @Override
    public void process(World aWorld) {
        if (aWorld != null) {
            TileEntity tTileEntity = aWorld.getTileEntity(new BlockPos(mX, mY, mZ));
            if(tTileEntity == null) {
                tTileEntity = isPipeBaseTile ? new BaseMetaPipeEntity() : new BaseMetaTileEntity();
                tTileEntity.setWorldObj(aWorld);
                tTileEntity.setPos(new BlockPos(mX, mY, mZ));
                aWorld.setTileEntity(new BlockPos(mX, mY, mZ), tTileEntity);
            }
            //System.out.println("GTTileEntity process(World) " + tTileEntity);
            if (tTileEntity instanceof BaseMetaTileEntity)
                ((BaseMetaTileEntity) tTileEntity).receiveMetaTileEntityData(mID, mC0, mC1, mC2, mC3, mC4, mC5, mTexture, mUpdate, mRedstone, mColor);
            else if (tTileEntity instanceof BaseMetaPipeEntity)
                ((BaseMetaPipeEntity) tTileEntity).receiveMetaTileEntityData(mID, mC0, mC1, mC2, mC3, mC4, mC5, mTexture, mUpdate, mRedstone, mColor);
        }
    }

}