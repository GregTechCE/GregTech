package gregtech.api.enums;

import gregtech.api.interfaces.IColorModulationContainer;
import gregtech.api.objects.GT_ArrayList;
import gregtech.api.util.GT_Utility;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;

public enum Dyes implements IColorModulationContainer {
    /**
     * The valid Colors, see VALUES Array below
     */
    dyeBlack(0, 32, 32, 32, "Black"),
    dyeRed(1, 255, 0, 0, "Red"),
    dyeGreen(2, 0, 255, 0, "Green"),
    dyeBrown(3, 96, 64, 0, "Brown"),
    dyeBlue(4, 0, 0, 255, "Blue"),
    dyePurple(5, 128, 0, 128, "Purple"),
    dyeCyan(6, 0, 255, 255, "Cyan"),
    dyeLightGray(7, 192, 192, 192, "Light Gray"),
    dyeGray(8, 128, 128, 128, "Gray"),
    dyePink(9, 255, 192, 192, "Pink"),
    dyeLime(10, 128, 255, 128, "Lime"),
    dyeYellow(11, 255, 255, 0, "Yellow"),
    dyeLightBlue(12, 128, 128, 255, "Light Blue"),
    dyeMagenta(13, 255, 0, 255, "Magenta"),
    dyeOrange(14, 255, 128, 0, "Orange"),
    dyeWhite(15, 255, 255, 255, "White"),
    /**
     * The NULL Color
     */
    _NULL(-1, 255, 255, 255, "INVALID COLOR"),
    /**
     * Additional Colors only used for direct Color referencing
     */
    CABLE_INSULATION(-1, 64, 64, 64, "Cable Insulation"),
    CONSTRUCTION_FOAM(-1, 64, 64, 64, "Construction Foam"),
    MACHINE_METAL(-1, 220, 220, 255, "Machine Metal");

    public static final Dyes VALUES[] = {dyeBlack, dyeRed, dyeGreen, dyeBrown, dyeBlue, dyePurple, dyeCyan, dyeLightGray, dyeGray, dyePink, dyeLime, dyeYellow, dyeLightBlue, dyeMagenta, dyeOrange, dyeWhite};

    public final byte mIndex;
    public final String mName;
    public final short[] mRGBa;
    private final ArrayList<Fluid> mFluidDyes = new GT_ArrayList<Fluid>(false, 1);

    private Dyes(int aIndex, int aR, int aG, int aB, String aName) {
        mIndex = (byte) aIndex;
        mName = aName;
        mRGBa = new short[]{(short) aR, (short) aG, (short) aB, 0};
    }

    public static Dyes get(int aColor) {
        if (aColor >= 0 && aColor < 16) return VALUES[aColor];
        return _NULL;
    }

    public static short[] getModulation(int aColor, short[] aDefaultModulation) {
        if (aColor >= 0 && aColor < 16) return VALUES[aColor].mRGBa;
        return aDefaultModulation;
    }

    public static Dyes get(String aColor) {
        Object tObject = GT_Utility.getFieldContent(Dyes.class, aColor, false, false);
        if (tObject instanceof Dyes) return (Dyes) tObject;
        return _NULL;
    }

    public static boolean isAnyFluidDye(FluidStack aFluid) {
        return aFluid != null && isAnyFluidDye(aFluid.getFluid());
    }

    public static boolean isAnyFluidDye(Fluid aFluid) {
        if (aFluid != null) for (Dyes tDye : VALUES) if (tDye.isFluidDye(aFluid)) return true;
        return false;
    }

    public boolean isFluidDye(FluidStack aFluid) {
        return aFluid != null && isFluidDye(aFluid.getFluid());
    }

    public boolean isFluidDye(Fluid aFluid) {
        return aFluid != null && mFluidDyes.contains(aFluid);
    }

    public boolean addFluidDye(Fluid aDye) {
        if (aDye == null || mFluidDyes.contains(aDye)) return false;
        mFluidDyes.add(aDye);
        return true;
    }

    public int getSizeOfFluidList() {
        return mFluidDyes.size();
    }

    /**
     * @param aAmount 1 Fluid Material Unit (144) = 1 Dye Item
     */
    public FluidStack getFluidDye(int aIndex, long aAmount) {
        if (aIndex >= mFluidDyes.size() || aIndex < 0) return null;
        return new FluidStack(mFluidDyes.get(aIndex), (int) aAmount);
    }

    @Override
    public short[] getRGBA() {
        return mRGBa;
    }
}