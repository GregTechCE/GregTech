package gregtech.api.threads;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GT_Runnable_Sound implements Runnable {

    private final int mX;
    private final int mY;
    private final int mZ;
    private final World mWorld;
    private final ResourceLocation mSoundLocation;
    private final float mSoundStrength, mSoundModulation;

    public GT_Runnable_Sound(World aWorld, int aX, int aY, int aZ, String aSoundName, float aSoundStrength, float aSoundModulation) {
        mWorld = aWorld;
        mX = aX;
        mY = aY;
        mZ = aZ;
        mSoundLocation = new ResourceLocation(aSoundName);
        mSoundStrength = aSoundStrength;
        mSoundModulation = aSoundModulation;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void run() {
        SoundEvent soundEvent = SoundEvent.REGISTRY.getObject(mSoundLocation);
        if(soundEvent != null) {
            mWorld.playSound((double)mX, (double)mY, (double)mZ, soundEvent, SoundCategory.BLOCKS, mSoundStrength, mSoundModulation, false);
        }
    }


}