package gregtech.api.objects;

import gregtech.api.GregTech_API;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

import static gregtech.api.GT_Values.RES_PATH_BLOCK;

public class GT_Fluid extends Fluid implements Runnable {

    private final short[] mRGBa;

    public GT_Fluid(String aName, String aTextureName, short[] aRGBa) {
        super(aName,
                new ResourceLocation(RES_PATH_BLOCK + "fluids/fluid." + aTextureName),
                new ResourceLocation(RES_PATH_BLOCK + "fluids/fluid." + aTextureName));
        mRGBa = aRGBa;
    }

    @Override
    public int getColor() {
        return (Math.max(0, Math.min(255, mRGBa[0])) << 16) | (Math.max(0, Math.min(255, mRGBa[1])) << 8) | Math.max(0, Math.min(255, mRGBa[2]));
    }

    @Override
    public void run() {
        GregTech_API.sBlockIcons.registerSprite(getStill());
        GregTech_API.sBlockIcons.registerSprite(getFlowing());
    }
}