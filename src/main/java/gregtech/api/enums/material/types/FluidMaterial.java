package gregtech.api.enums.material.types;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import gregtech.api.enums.Element;
import gregtech.api.enums.material.MaterialIconSet;
import gregtech.api.objects.MaterialStack;
import gregtech.api.util.FPUtil;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Function;

public class FluidMaterial extends Material {

    public static final Function<String, FluidMaterial> RESOLVE_MATERIAL_FLUID = FPUtil.wrapCasting(RESOLVE_MATERIAL, FluidMaterial.class);

    public static final class MatFlags {

        /**
         * Add this flag to enable plasma generation for this material
         */
        public static final int GENERATE_PLASMA = Material.MatFlags.createFlag(10);

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

    public FluidMaterial(int metaItemSubId, String name, String defaultLocalName, int materialRGB, MaterialIconSet materialIconSet, ImmutableList<MaterialStack> materialComponents, int materialGenerationFlags, Element element, float densityMultiplier) {
        super(metaItemSubId, name, defaultLocalName, materialRGB, materialIconSet, materialComponents, materialGenerationFlags, element, densityMultiplier);
    }

    public boolean shouldGenerateFluid() {
        return true;
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
