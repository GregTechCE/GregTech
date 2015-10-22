package gregtech.api.net;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import gregtech.api.util.GT_Utility;
import net.minecraft.world.IBlockAccess;

public class GT_Packet_Sound extends GT_Packet {
    private int mX, mZ;
    private short mY;
    private String mSoundName;
    private float mSoundStrength, mSoundPitch;

    public GT_Packet_Sound() {
        super(true);
    }

    public GT_Packet_Sound(String aSoundName, float aSoundStrength, float aSoundPitch, int aX, short aY, int aZ) {
        super(false);
        mX = aX;
        mY = aY;
        mZ = aZ;
        mSoundName = aSoundName;
        mSoundStrength = aSoundStrength;
        mSoundPitch = aSoundPitch;
    }

    @Override
    public byte[] encode() {
        ByteArrayDataOutput tOut = ByteStreams.newDataOutput(10);
        tOut.writeUTF(mSoundName);
        tOut.writeFloat(mSoundStrength);
        tOut.writeFloat(mSoundPitch);
        tOut.writeInt(mX);
        tOut.writeShort(mY);
        tOut.writeInt(mZ);
        return tOut.toByteArray();
    }

    @Override
    public GT_Packet decode(ByteArrayDataInput aData) {
        return new GT_Packet_Sound(aData.readUTF(), aData.readFloat(), aData.readFloat(), aData.readInt(), aData.readShort(), aData.readInt());
    }

    @Override
    public void process(IBlockAccess aWorld) {
        GT_Utility.doSoundAtClient(mSoundName, 1, mSoundStrength, mSoundPitch, mX, mY, mZ);
    }

    @Override
    public byte getPacketID() {
        return 1;
    }
}