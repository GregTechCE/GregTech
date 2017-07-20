package gregtech.api.enums.material.types;

import com.google.common.collect.ImmutableList;
import gregtech.api.enums.Element;
import gregtech.api.enums.SubTag;
import gregtech.api.enums.material.FluidMaterial;
import gregtech.api.enums.material.Material;
import gregtech.api.enums.material.MaterialIconSet;
import gregtech.api.objects.MaterialStack;

import static gregtech.api.enums.SubTag.getNewSubTag;
import static gregtech.api.enums.material.Material.MatFlags.createFlag;

public class DustMaterial extends Material {

    public static final class MatFlags {

        /**
         * Add this to your Material if you want to have it's ore generated
         */
        public static final int GENERATE_ORE = createFlag(9);

        /**
         * Materials which are outputting less in an Induction Smelter. Already listed are:
         * Pyrite, Tetrahedrite, Sphalerite, Cinnabar
         */
        public static final SubTag INDUCTION_SMELTING_LOW_OUTPUT = getNewSubTag("INDUCTIONSMELTING_LOW_OUTPUT");

    }

    /**
     * Ore value of ore of this material
     * Used for IC2 miner, as example
     */
    public final int mOreValue;

    /**
     * List of ore by products
     */
    public final ImmutableList<DustMaterial> oreByProducts;

    /**
     * Crushed ore output amount multiplier during maceration
     */
    public final int oreMultiplier;

    /**
     * Byproducts output amount multiplier during pulverization
     */
    public final int mByProductMultiplier;

    /**
     * Smelting item amount multiplier during vanilla item smelting
     */
    public final int mSmeltingMultiplier;

    /**
     * Material to which smelting of this material ORE will result
     * Can point to this, and then material ore will just smelt into this material
     * Can be null
     */
    public final AbstractSolidMaterial directSmelting;

    /**
     * Smelting any item of this material will result material
     * specified in this field
     * Can be null
     */
    public final AbstractSolidMaterial smeltInto;

    /**
     * Arc Smelting any item of this material will result material
     * specified in this field
     * Can be null
     */
    public final AbstractSolidMaterial arcSmeltInto;

    /**
     * Material in which this material's ore should be washed to give additional output
     * Can be null
     */
    public final FluidMaterial washedIn;

    /**
     * During electromagnetic separation, this material ore will be separated onto this material and material specified by this field
     * Can be null
     */
    public final DustMaterial separatedOnto;

    public DustMaterial(String defaultLocalName, int materialRGB, MaterialIconSet materialIconSet, ImmutableList<MaterialStack> materialComponents, ImmutableList<Material> oreReRegistrations, ImmutableList<SubTag> subTags, int materialGenerationFlags, float densityMultiplier, Element element, int mOreValue, ImmutableList<DustMaterial> oreByProducts, int oreMultiplier, int mByProductMultiplier, int mSmeltingMultiplier, AbstractSolidMaterial directSmelting, AbstractSolidMaterial smeltInto, AbstractSolidMaterial arcSmeltInto, FluidMaterial washedIn, DustMaterial separatedOnto) {
        super(defaultLocalName, materialRGB, materialIconSet, materialComponents, oreReRegistrations, subTags, materialGenerationFlags, densityMultiplier, element);
        this.mOreValue = mOreValue;
        this.oreByProducts = oreByProducts;
        this.oreMultiplier = oreMultiplier;
        this.mByProductMultiplier = mByProductMultiplier;
        this.mSmeltingMultiplier = mSmeltingMultiplier;
        this.directSmelting = directSmelting;
        this.smeltInto = smeltInto;
        this.arcSmeltInto = arcSmeltInto;
        this.washedIn = washedIn;
        this.separatedOnto = separatedOnto;
    }

}
