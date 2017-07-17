package gregtech.api.enums.material.types;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import gregtech.api.enums.Element;
import gregtech.api.enums.SubTag;
import gregtech.api.enums.material.Material;
import gregtech.api.enums.material.MaterialIconSet;
import gregtech.api.objects.MaterialStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class FluidMaterial extends Material implements gregtech.api.enums.material.FluidMaterial {

    private Fluid materialFluid;
    private Fluid materialPlasma;

    public FluidMaterial(String defaultLocalName, int materialRGB, String chemicalFormula, MaterialIconSet materialIconSet, ImmutableList<MaterialStack> materialComponents, ImmutableList<Material> oreReRegistrations, ImmutableList<SubTag> subTags, int materialGenerationFlags, float densityMultiplier, Element element) {
        super(defaultLocalName, materialRGB, chemicalFormula, materialIconSet, materialComponents, oreReRegistrations, subTags, materialGenerationFlags, densityMultiplier, element);
    }

    /**
     * @deprecated internal usage only
     */
    @Deprecated
    public void setMaterialFluid(Fluid materialFluid) {
        Preconditions.checkNotNull(materialFluid);
        this.materialFluid = materialFluid;
    }

    /**
     * @deprecated internal usage only
     */
    @Deprecated
    public void setMaterialPlasma(Fluid materialPlasma) {
        this.materialPlasma = materialPlasma;
    }

    @Override
    public FluidStack getFluid(int amount) {
        return materialFluid == null ? null : new FluidStack(materialFluid, amount);
    }

    @Override
    public FluidStack getPlasma(int amount) {
        return materialPlasma == null ? null : new FluidStack(materialPlasma, amount);
    }

}
