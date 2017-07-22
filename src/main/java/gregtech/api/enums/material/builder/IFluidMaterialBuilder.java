package gregtech.api.enums.material.builder;

import gregtech.api.enums.material.FluidMaterial;
import gregtech.api.enums.material.Material;

//should be T extends FluidMaterial, but it is interface
public interface IFluidMaterialBuilder<T extends Material, Self extends MaterialBuilder<T, Self>> {

    /**
     * Enables a cell meta-item generation for this material
     */
    default Self enableCell() {
        ((Self) this).materialGenerationBits |= FluidMaterial.MatFlags.GENERATE_CELL;
        return (Self) this;
    }

    /**
     * Enables plasma fluid generation for this material
     *
     */
    default Self enablePlasma() {
        ((Self) this).materialGenerationBits |= FluidMaterial.MatFlags.GENERATE_PLASMA;
        return (Self) this;
    }

}
