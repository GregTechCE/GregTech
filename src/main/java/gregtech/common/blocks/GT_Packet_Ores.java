package gregtech.common.blocks;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicMachine;
import gregtech.api.net.GT_Packet;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;

public class GT_Packet_Ores extends GT_Packet {

    private ArrayList<OreInfo> ores = new ArrayList<>();

    private static class OreInfo {
        private int mX;
        private int mZ;
        private int mY;
        private short mMetaData;

        public OreInfo(BlockPos pos, short metadata) {
            mX = pos.getX();
            mY = pos.getY();
            mZ = pos.getZ();
            mMetaData = metadata;
        }

        private OreInfo() {}

        public void encode(ByteBuf tOut) {
            tOut.writeInt(this.mX);
            tOut.writeShort(this.mY);
            tOut.writeInt(this.mZ);
            tOut.writeShort(this.mMetaData);
        }

        public void decode(ByteBuf buf) {
            mX = buf.readInt();
            mY = buf.readShort();
            mZ = buf.readInt();
            mMetaData = buf.readShort();
        }

        public static OreInfo decodeNew(ByteBuf buf) {
            OreInfo oreInfo = new OreInfo();
            oreInfo.decode(buf);
            return oreInfo;
        }

        public void process(World aWorld) {
            if (aWorld != null && mMetaData > 0) {
                BlockPos blockPos = new BlockPos(this.mX, this.mY, this.mZ);
                GT_TileEntity_Ores tileEntity_ores = (GT_TileEntity_Ores) aWorld.getTileEntity(blockPos);
                if(tileEntity_ores != null) {
                    tileEntity_ores.mMetaData = mMetaData;
                    tileEntity_ores.causeChunkUpdate();
                }
            }
        }


    }

    public GT_Packet_Ores() {}

    public void addPos(GT_TileEntity_Ores tileEntity_ores) {
        ores.add(new OreInfo(tileEntity_ores.getPos(), tileEntity_ores.mMetaData));
    }

    public boolean isEmpty() {
        return ores.isEmpty();
    }

    @Override
    public void encode(ByteBuf buf) {
        buf.writeInt(ores.size());
        for(int i = 0; i < ores.size(); i++) {
            ores.get(i).encode(buf);
        }
    }

    @Override
    public void decode(ByteBuf buf) {
        int size = buf.readInt();
        for(int i = 0; i < size; i++) {
            ores.add(OreInfo.decodeNew(buf));
        }
    }

    @Override
    public void process(World aWorld) {
        for(OreInfo oreInfo : ores) {
            oreInfo.process(aWorld);
        }
    }

}
