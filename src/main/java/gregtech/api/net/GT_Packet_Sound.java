package gregtech.api.net;

import gregtech.api.util.GT_Utility;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class GT_Packet_Sound extends GT_Packet {

    private Vec3d mPosition;
    private String mSoundName;
    private float mSoundStrength, mSoundPitch;

    public GT_Packet_Sound() {}

    public GT_Packet_Sound(String soundName, float soundStrength, float soundPitch, Vec3d soundPosition) {
        this.mPosition = soundPosition;
        this.mSoundName = soundName;
        this.mSoundStrength = soundStrength;
        this.mSoundPitch = soundPitch;
    }

    @Override
    public void encode(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, mSoundName);
        buf.writeFloat(mSoundStrength);
        buf.writeFloat(mSoundPitch);
        buf.writeDouble(mPosition.xCoord);
        buf.writeDouble(mPosition.yCoord);
        buf.writeDouble(mPosition.zCoord);
    }

    @Override
    public void decode(ByteBuf buf) {
        mSoundName = ByteBufUtils.readUTF8String(buf);
        mSoundStrength = buf.readFloat();
        mSoundPitch = buf.readFloat();
        mPosition = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
    }

    @Override
    public void process(World aWorld) {
        GT_Utility.doSoundAtClient(mSoundName, 1, mSoundStrength, mSoundPitch, mX, mY, mZ);
    }

}