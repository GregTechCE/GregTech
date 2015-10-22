package gregtech.api.threads;

import gregtech.api.util.GT_PlayedSound;
import gregtech.api.util.GT_Utility;
import net.minecraft.world.World;

public class GT_Runnable_Sound implements Runnable {
    private final int mX, mY, mZ, mTimeUntilNextSound;
    private final World mWorld;
    private final String mSoundName;
    private final float mSoundStrength, mSoundModulation;

    public GT_Runnable_Sound(World aWorld, int aX, int aY, int aZ, int aTimeUntilNextSound, String aSoundName, float aSoundStrength, float aSoundModulation) {
        mWorld = aWorld;
        mX = aX;
        mY = aY;
        mZ = aZ;
        mTimeUntilNextSound = aTimeUntilNextSound;
        mSoundName = aSoundName;
        mSoundStrength = aSoundStrength;
        mSoundModulation = aSoundModulation;
    }

    @Override
    public void run() {
        try {
            GT_PlayedSound tSound;
            if (GT_Utility.sPlayedSoundMap.keySet().contains(tSound = new GT_PlayedSound(mSoundName, mX, mY, mZ)))
                return;
            mWorld.playSound(mX, mY, mZ, mSoundName, mSoundStrength, mSoundModulation, false);
            GT_Utility.sPlayedSoundMap.put(tSound, mTimeUntilNextSound);
        } catch (Throwable e) {/**/}
    }
}