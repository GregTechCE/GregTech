package gregtech.api.enums;

import gregtech.api.util.GT_Utility;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.awt.*;
import java.util.ArrayList;

public enum Dyes {
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
     * The Clear White Color
     */
    CLEAR(-1, 255, 255, 255, "Clear White"),
    /**
     * Additional Colors only used for direct Color referencing
     */
    INSULATION(-1, 64, 64, 64, "Cable Insulation"),
    CONSTRUCTION_FOAM(-1, 64, 64, 64, "Construction Foam"),
    MACHINE_METAL(-1, 230, 230, 255, "Machine Metal");

    public static final Dyes VALUES[] = {MACHINE_METAL, dyeRed, dyeGreen, dyeBrown, dyeBlue, dyePurple, dyeCyan, dyeLightGray, dyeGray, dyePink, dyeLime, dyeYellow, dyeLightBlue, dyeMagenta, dyeOrange, dyeWhite};

    public final byte mIndex;
    public final String mName;
    public final int mRGBa;
    private final ArrayList<Fluid> mFluidDyes = new ArrayList<>(1);

    private Dyes(int aIndex, int r, int g, int b, String name) {
        mIndex = (byte) aIndex;
        mName = name;
        mRGBa = ((r & 0xFF) << 16) |
                ((g & 0xFF) << 8)  |
                ((b & 0xFF));
    }

    public static Dyes get(int color) {
        if (color >= 0 && color < 16) return VALUES[color];
        return _NULL;
    }

    public static int getModulation(int colorIndex, Dyes defaultModulation) {
        return getModulation(colorIndex, defaultModulation.mRGBa);
    }

    public static int getModulation(int colorIndex, int defaultModulation) {
        if (colorIndex >= 0 && colorIndex < VALUES.length)
            return VALUES[colorIndex].mRGBa;
        return defaultModulation;
    }

    public static Dyes get(String color) {
        Object tObject = GT_Utility.getFieldContent(Dyes.class, color, false, false);
        if (tObject instanceof Dyes) return (Dyes) tObject;
        return _NULL;
    }

    public static boolean isAnyFluidDye(FluidStack fluid) {
        return fluid != null && isAnyFluidDye(fluid.getFluid());
    }

    public static boolean isAnyFluidDye(Fluid fluid) {
        if (fluid != null) for (Dyes tDye : VALUES) if (tDye.isFluidDye(fluid)) return true;
        return false;
    }

    public boolean isFluidDye(FluidStack fluid) {
        return fluid != null && isFluidDye(fluid.getFluid());
    }

    public boolean isFluidDye(Fluid fluid) {
        return fluid != null && mFluidDyes.contains(fluid);
    }

    public boolean addFluidDye(Fluid dye) {
        if (dye == null || mFluidDyes.contains(dye)) return false;
        mFluidDyes.add(dye);
        return true;
    }

    public int getSizeOfFluidList() {
        return mFluidDyes.size();
    }

    /**
     * @param amount 1 Fluid Material Unit (144) = 1 Dye Item
     */
    public FluidStack getFluidDye(int index, int amount) {
        if (index >= mFluidDyes.size() || index < 0) return null;
        return new FluidStack(mFluidDyes.get(index), amount);
    }

}