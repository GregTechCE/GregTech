package gregtech.api.objects;

import gregtech.api.GregTech_API;
import net.minecraftforge.fluids.Fluid;

import static gregtech.api.enums.GT_Values.RES_PATH_BLOCK;

public class GT_Fluid extends Fluid implements Runnable {
    public final String mTextureName;
    private final short[] mRGBa;

    public GT_Fluid(String aName, String aTextureName, short[] aRGBa) {
        super(aName);
        mRGBa = aRGBa;
        mTextureName = aTextureName;
        GregTech_API.sGTBlockIconload.add(this);
    }

    @Override
    public int getColor() {
        return (Math.max(0, Math.min(255, mRGBa[0])) << 16) | (Math.max(0, Math.min(255, mRGBa[1])) << 8) | Math.max(0, Math.min(255, mRGBa[2]));
    }

    @Override
    public void run() {
        setIcons(GregTech_API.sBlockIcons.registerIcon(RES_PATH_BLOCK + "fluids/fluid." + mTextureName));
    }
}