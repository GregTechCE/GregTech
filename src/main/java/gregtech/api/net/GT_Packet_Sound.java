package gregtech.api.net;

import gregtech.api.util.GT_Utility;
import io.netty.buffer.ByteBuf;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class GT_Packet_Sound extends GT_Packet {

    private int mX, mZ, mY;
    private String mSoundName;
    private float mSoundStrength, mSoundPitch;

    public GT_Packet_Sound() {}

    public GT_Packet_Sound(String aSoundName, float aSoundStrength, float aSoundPitch, int aX, short aY, int aZ) {
        mX = aX;
        mY = aY;
        mZ = aZ;
        mSoundName = aSoundName;
        mSoundStrength = aSoundStrength;
        mSoundPitch = aSoundPitch;
    }

    @Override
    public void encode(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, mSoundName);
        buf.writeFloat(mSoundStrength);
        buf.writeFloat(mSoundPitch);
        buf.writeInt(mX);
        buf.writeShort(mY);
        buf.writeInt(mZ);
    }

    @Override
    public void decode(ByteBuf buf) {
        mSoundName = ByteBufUtils.readUTF8String(buf);
        mSoundStrength = buf.readFloat();
        mSoundPitch = buf.readFloat();
        mX = buf.readInt();
        mY = buf.readShort();
        mZ = buf.readInt();
    }

    @Override
    public void process(World aWorld) {
        GT_Utility.doSoundAtClient(mSoundName, 1, mSoundStrength, mSoundPitch, mX, mY, mZ);
    }

}