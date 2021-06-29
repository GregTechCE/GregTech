package gregtech.api.unification.material;

import com.google.common.base.CaseFormat;
import com.google.common.collect.ImmutableList;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.util.GTControlledRegistry;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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

    long getAverageMass();

    long getAverageNeutrons();

    long getAverageProtons();

    long getMass();

    long getNeutrons();

    long getProtons();

    boolean isRadioactive();

    boolean hasFlag(long flag);
}
