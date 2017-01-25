package gregtech.api.threads;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class GT_Runnable_Sound implements Runnable {
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
    @SideOnly(Side.CLIENT)
    public void run() {
        SoundEvent soundEvent = SoundEvent.REGISTRY.getObject(mSoundLocation);
        if(soundEvent != null) {
            mWorld.playSound((double)mX, (double)mY, (double)mZ, soundEvent, SoundCategory.BLOCKS, 1.0f, 1.0f, false);
        }
    }


}