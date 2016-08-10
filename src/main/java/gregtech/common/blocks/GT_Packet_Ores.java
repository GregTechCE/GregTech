package gregtech.common.blocks;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import gregtech.api.net.GT_Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class GT_Packet_Ores
        extends GT_Packet {
    private int mX;
    private int mZ;
    private int mY;
    private short mMetaData;

    public GT_Packet_Ores() {
        super(true);
    }

    public GT_Packet_Ores(BlockPos blockPos, short aMetaData) {
        super(false);
        this.mX = blockPos.getX();
        this.mY = blockPos.getY();
        this.mZ = blockPos.getZ();
        this.mMetaData = aMetaData;
    }

    @Override
    public byte[] encode() {
        ByteArrayDataOutput tOut = ByteStreams.newDataOutput(12);

        tOut.writeInt(this.mX);
        tOut.writeShort(this.mY);
        tOut.writeInt(this.mZ);
        tOut.writeShort(this.mMetaData);

        return tOut.toByteArray();
    }

    @Override
    public GT_Packet decode(ByteArrayDataInput aData) {
        return new GT_Packet_Ores(new BlockPos(aData.readInt(), aData.readShort(), aData.readInt()), aData.readShort());
    }

    @Override
    public void process(IBlockAccess aWorld) {
        if (aWorld != null) {
            BlockPos blockPos = new BlockPos(this.mX, this.mY, this.mZ);
            TileEntity tTileEntity = aWorld.getTileEntity(blockPos);
            if ((tTileEntity instanceof GT_TileEntity_Ores)) {
                ((GT_TileEntity_Ores) tTileEntity).mMetaData = this.mMetaData;
            }
            if (((aWorld instanceof World)) && (((World) aWorld).isRemote)) {
                ((World) aWorld).markBlockRangeForRenderUpdate(blockPos, blockPos);
            }
        }
    }

    @Override
    public byte getPacketID() {
        return 3;
    }
}
