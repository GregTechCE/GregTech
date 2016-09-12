package gregtech.common.blocks;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicMachine;
import gregtech.api.net.GT_Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
            GT_TileEntity_Ores tileEntity_ores = (GT_TileEntity_Ores) aWorld.getTileEntity(blockPos);
            if(tileEntity_ores != null) {
                tileEntity_ores.mMetaData = mMetaData;
                tileEntity_ores.causeChunkUpdate();
            }
        }
    }

    @Override
    public byte getPacketID() {
        return 3;
    }
}
