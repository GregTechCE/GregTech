package gregtech.api.unification.material.type;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import gregtech.api.unification.Element;
import gregtech.api.unification.material.MaterialIconSet;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.util.GTUtility;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FluidMaterial extends Material {

    public static final class MatFlags {

        /**
         * Add this flag to enable plasma generation for this material
         */
        public static final long GENERATE_PLASMA = GTUtility.createFlag(10);

        /**
         * Marks material state as gas
         * Examples: Air, Argon, Refinery Gas, Oxygen, Hydrogen
         */
        public static final long STATE_GAS = GTUtility.createFlag(11);

    }

    /**
     * Internal material fluid field
     */
    @Nullable
    private Fluid materialFluid;

    /**
     * Internal material plasma fluid field
     */
    @Nullable
    private Fluid materialPlasma;

    public FluidMaterial(int metaItemSubId, String name, int materialRGB, MaterialIconSet materialIconSet, ImmutableList<MaterialStack> materialComponents, long materialGenerationFlags, Element element) {
        super(metaItemSubId, name, materialRGB, materialIconSet, materialComponents, materialGenerationFlags, element);
    }

    public FluidMaterial(int metaItemSubId, String name, int materialRGB, MaterialIconSet materialIconSet, ImmutableList<MaterialStack> materialComponents, long materialGenerationFlags) {
        super(metaItemSubId, name, materialRGB, materialIconSet, materialComponents, materialGenerationFlags, null);
    }

    public boolean shouldGenerateFluid() {
        return true;
    }

    public boolean isGas() {
        return hasFlag(MatFlags.STATE_GAS);
    }

    public boolean isFluid() {
        return !hasFlag(MatFlags.STATE_GAS) && !isGas();
    }

    public boolean shouldGeneratePlasma() {
        return shouldGenerateFluid() && hasFlag(MatFlags.GENERATE_PLASMA);
    }

    /**
     * @deprecated internal usage only
     */
    @Deprecated
    public final void setMaterialFluid(@Nonnull Fluid materialFluid) {
        Preconditions.checkNotNull(materialFluid);
        this.materialFluid = materialFluid;
    }

    /**
     * @deprecated internal usage only
     */
    @Deprecated
    public final void setMaterialPlasma(@Nonnull Fluid materialPlasma) {
        Preconditions.checkNotNull(materialPlasma);
        this.materialPlasma = materialPlasma;
    }

    public final @Nullable Fluid getMaterialFluid() {
        return materialFluid;
    }

    public final @Nullable Fluid getMaterialPlasma() {
        return materialPlasma;
    }

    public final @Nullable FluidStack getFluid(int amount) {
        return materialFluid == null ? null : new FluidStack(materialFluid, amount);
    }

    public final @Nullable FluidStack getPlasma(int amount) {
        return materialPlasma == null ? null : new FluidStack(materialPlasma, amount);
    }

}
