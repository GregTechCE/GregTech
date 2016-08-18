package gregtech.api.threads;

import gregtech.api.util.GT_PlayedSound;
import gregtech.api.util.GT_Utility;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.Sound;
import net.minecraft.client.audio.SoundEventAccessor;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class GT_Runnable_Sound implements Runnable, ISound {
    private final int mX, mY, mZ, mTimeUntilNextSound;
    private final World mWorld;
    private final ResourceLocation mSoundLocation;
    private final float mSoundStrength, mSoundModulation;

    public GT_Runnable_Sound(World aWorld, int aX, int aY, int aZ, int aTimeUntilNextSound, String aSoundName, float aSoundStrength, float aSoundModulation) {
        mWorld = aWorld;
        mX = aX;
        mY = aY;
        mZ = aZ;
        mTimeUntilNextSound = aTimeUntilNextSound;
        mSoundLocation = new ResourceLocation(aSoundName);
        mSoundStrength = aSoundStrength;
        mSoundModulation = aSoundModulation;
    }

    @Override
    public void run() {
        Minecraft.getMinecraft().getSoundHandler().playSound(this);
    }

    @Override
    public ResourceLocation getSoundLocation() {
        return mSoundLocation;
    }

    @Nullable
    @Override
    public SoundEventAccessor createAccessor(SoundHandler handler) {
        return new SoundEventAccessor(mSoundLocation, "metal sound");
    }

    @Override
    public Sound getSound() {
        return new Sound(mSoundLocation.toString(), getVolume(), getPitch(), 1, Sound.Type.FILE, true);
    }

    @Override
    public SoundCategory getCategory() {
        return SoundCategory.AMBIENT;
    }

    @Override
    public boolean canRepeat() {
        return true;
    }

    @Override
    public int getRepeatDelay() {
        return mTimeUntilNextSound;
    }

    @Override
    public float getVolume() {
        return mSoundStrength;
    }

    @Override
    public float getPitch() {
        return mSoundModulation;
    }

    @Override
    public float getXPosF() {
        return mX;
    }

    @Override
    public float getYPosF() {
        return mY;
    }

    @Override
    public float getZPosF() {
        return mZ;
    }

    @Override
    public AttenuationType getAttenuationType() {
        return AttenuationType.NONE;
    }

}