package gregtech.api.enums.material.builder;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import gregtech.api.enums.SubTag;
import gregtech.api.enums.material.FluidMaterial;
import gregtech.api.enums.material.Material;
import gregtech.api.enums.material.types.AbstractSolidMaterial;
import gregtech.api.enums.material.types.DustMaterial;
import gregtech.api.enums.material.types.MetalMaterial;
import gregtech.api.objects.MaterialStack;

import java.util.ArrayList;

public class DustMaterialBuilder<T extends DustMaterial, Self extends DustMaterialBuilder<T, Self>> extends MaterialBuilder<T, Self> {
    
    protected int oreValue = 0;
    protected int oreMultiplier = 1;
    protected int byProductMultiplier = 1;
    protected int smeltingMultiplier = 1;
    protected ArrayList<DustMaterial> oreByProducts = new ArrayList<>();
    protected AbstractSolidMaterial oreDirectSmelting = null;
    protected AbstractSolidMaterial smeltInto = null;
    protected MetalMaterial arcSmeltInto = null;
    protected FluidMaterial washedIn = null;
    protected DustMaterial separatedOnto = null;

    public DustMaterialBuilder(String name, String defaultLocalizedName) {
        super(name, defaultLocalizedName);
    }

    /**
     * Enables ore and all ore components for material
     * @param oreValue ore quality number (used for IC2 miner)
     * @return this
     */
    public Self enableOre(int oreValue) {
        Preconditions.checkArgument(oreValue > 0, "Ore value must be positive!");
        this.materialGenerationBits |= DustMaterial.MatFlags.GENERATE_ORE;
        this.oreValue = oreValue;
        return (Self) this;
    }

    /**
     * Specifies maceration (1-st step of processing) ore multiplier
     * By default, it is 1 (2 crushed ores from 1 ore block)
     * @param oreMultiplier maceration ore multiplier
     * @return this
     */
    public Self oreMultiplier(int oreMultiplier) {
        Preconditions.checkArgument(oreMultiplier > 0, "Ore multiplier must be positive!");
        this.oreMultiplier = oreMultiplier;
        return (Self) this;
    }

    /**
     * Specifies byproducts obtained in processing chain of this material's ore
     * TODO: specify which indexes delegates to which processing steps
     * @param byProducts byproducts list. Indexes are used for different processing steps.
     * @return this
     */
    public Self oreByProducts(DustMaterial... byProducts) {
        Preconditions.checkNotNull(byProducts);
        Preconditions.checkArgument(byProducts.length > 0, "Cannot add empty byproducts array!");
        this.oreByProducts = Lists.newArrayList(byProducts);
        return (Self) this;
    }

    /**
     * Specifies a primary byproduct multiplier obtained during ore maceration
     * @param byProductMultiplier primary byproduct multiplier
     * @return this
     */
    public Self byProductMultiplier(int byProductMultiplier) {
        Preconditions.checkArgument(byProductMultiplier > 0, "Byproducts multiplier must be positive!");
        this.byProductMultiplier = byProductMultiplier;
        return (Self) this;
    }

    /**
     * Specifies ore block smelting (and induction smelting) multiplier
     * @param smeltingMultiplier ore smelting multiplier
     * @return this
     */
    public Self smeltingMultiplier(int smeltingMultiplier) {
        Preconditions.checkArgument(smeltingMultiplier > 0, "Smelting multiplier must be positive!");
        this.smeltingMultiplier = smeltingMultiplier;
        return (Self) this;
    }

    /**
     * Specifies a solid material into which this ore smelts. Material should have ingot or gem
     * @param oreDirectSmelting smelting material
     * @return this
     */
    public Self oreDirectSmelting(AbstractSolidMaterial oreDirectSmelting) {
        Preconditions.checkNotNull(oreDirectSmelting);
        this.oreDirectSmelting = oreDirectSmelting;
        return (Self) this;
    }

    /**
     * Specifies a fluid in which this material's ore can be washed
     * @param washedIn washing liquid
     * @return this
     */
    public Self washedIn(FluidMaterial washedIn) {
        Preconditions.checkNotNull(washedIn);
        this.washedIn = washedIn;
        return (Self) this;
    }

    /**
     * Specifies a material which is given during this material's ore electromagnetic separation
     * @param separatedOnto separated material
     * @return this
     */
    public Self separatedOnto(DustMaterial separatedOnto) {
        Preconditions.checkNotNull(separatedOnto);
        this.separatedOnto = separatedOnto;
        return (Self) this;
    }

    /**
     * Specifies a material into this material's ore smelted
     * Specified material SHOULD have ingot or gem
     * @param smeltInto smelted material
     * @return this
     */
    public Self smeltInto(AbstractSolidMaterial smeltInto) {
        Preconditions.checkNotNull(smeltInto);
        this.smeltInto = smeltInto;
        return (Self) this;
    }

    /**
     * Specifies a material into this material's part will be melted in arc furnace
     * @param smeltInto arc furnace material
     * @return this
     */
    public Self arcSmeltInto(MetalMaterial smeltInto) {
        Preconditions.checkNotNull(smeltInto);
        this.arcSmeltInto = smeltInto;
        return (Self) this;
    }

    /**
     * Marks material as low-outputting in induction smelter from TE
     * @return this
     */
    public Self inductionSmeltingLowOutput() {
        this.subTags.add(DustMaterial.MatFlags.INDUCTION_SMELTING_LOW_OUTPUT);
        return (Self) this;
    }

    @Override
    protected T build() {
        return (T) new DustMaterial(
                defaultLocalizedName,
                rgbColor,
                iconSet,
                ImmutableList.copyOf(materialCompounds),
                ImmutableList.copyOf(oreReRegistrations),
                ImmutableList.copyOf(subTags),
                materialGenerationBits,
                densityMultiplier,
                directElement,
                oreValue,
                ImmutableList.copyOf(oreByProducts),
                oreMultiplier,
                byProductMultiplier,
                smeltingMultiplier,
                oreDirectSmelting,
                smeltInto,
                arcSmeltInto,
                washedIn,
                separatedOnto
        );
    }

}
