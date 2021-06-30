package gregtech.api.unification.material;

import com.google.common.base.CaseFormat;
import com.google.common.collect.ImmutableList;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.util.GTControlledRegistry;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public interface IMaterial <T> {

    default String toCamelCaseString() {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, toString());
    }

    default String getUnlocalizedName() {
        return String.format("material.%s", toString());
    }

    @SideOnly(Side.CLIENT)
    default String getLocalizedName() {
        return I18n.format(getUnlocalizedName());
    }

    GTControlledRegistry<String, T> getRegistry();

    Class<T> getMaterialClass();

    String getChemicalFormula();

    ImmutableList<MaterialStack> getMaterialComponents();

    int getMaterialRGB();

    long getAverageMass();

    long getAverageNeutrons();

    long getAverageProtons();

    long getMass();

    long getNeutrons();

    long getProtons();

    boolean isRadioactive();

    boolean hasFlag(long flag);

    long verifyMaterialBits(long materialBits);

    static int getIntValueOfFlag(long value) {
        int index = 0;
        while (value != 1) {
            value >>= 1;
            index++;
        }
        return index;
    }

    @Nullable
    FluidStack getFluid(int amount);
}
