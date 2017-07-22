package gregtech.api.enums.material.builder;

import com.google.common.collect.ImmutableList;
import gregtech.api.enums.material.types.FluidMaterialType;

public class FluidMaterialBuilder<T extends FluidMaterialType, Self extends FluidMaterialBuilder<T, Self>> extends MaterialBuilder<T, Self> implements IFluidMaterialBuilder<T, Self> {

    public FluidMaterialBuilder(String name, String defaultLocalizedName) {
        super(name, defaultLocalizedName);
    }

    @Override
    protected T build() {
        return (T) new FluidMaterialType(
                defaultLocalizedName,
                rgbColor,
                iconSet,
                ImmutableList.copyOf(materialCompounds),
                ImmutableList.copyOf(oreReRegistrations),
                ImmutableList.copyOf(subTags),
                materialGenerationBits,
                densityMultiplier,
                directElement
        );
    }

}
