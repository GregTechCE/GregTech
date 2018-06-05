package gregtech.api.unification.material.type;

import com.google.common.collect.ImmutableList;
import gregtech.api.unification.Element;
import gregtech.api.unification.material.MaterialIconSet;
import gregtech.api.unification.stack.MaterialStack;

import java.util.ArrayList;
import java.util.Arrays;

import static gregtech.api.util.GTUtility.createFlag;

public class DustMaterial extends FluidMaterial {

    public static final class MatFlags {

        static {
            Material.MatFlags.registerMaterialFlagsHolder(MatFlags.class, DustMaterial.class);
        }

        public static final long GENERATE_ORE = createFlag(11);

        /**
         * Generate a plate for this material
         * If it's dust material, dust compressor recipe into plate will be generated
         * If it's metal material, bending machine recipes will be generated
         * If block is found, cutting machine recipe will be also generated
         */
        public static final long GENERATE_PLATE = createFlag(12);

        /**
         * Add to material if it cannot be worked by any other means, than smashing or smelting. This is used for coated Materials.
         */
        public static final long NO_WORKING = createFlag(13);
        /**
         * Add to material if it cannot be used for regular Metal working techniques since it is not possible to bend it.
         */
        public static final long NO_SMASHING = createFlag(14);

        /**
         * Add to material if it's impossible to smelt it
         */
        public static final long NO_SMELTING = createFlag(15);

        /**
         * Add to material if it is outputting less in an Induction Smelter.
         */
        public static final long INDUCTION_SMELTING_LOW_OUTPUT = createFlag(16);

        /**
         * Add to material if it melts into fluid (and it will also generate fluid for this material)
         */
        public static final long SMELT_INTO_FLUID = createFlag(17);

    }


    /**
     * List of ore by products
     */
    public final ArrayList<FluidMaterial> oreByProducts = new ArrayList<>();

    /**
     * Crushed ore output amount multiplier during maceration
     */
    public int oreMultiplier = 1;

    /**
     * Byproducts output amount multiplier during pulverization
     */
    public int byProductMultiplier = 1;

    /**
     * Smelting item amount multiplier during vanilla item smelting
     */
    public int smeltingMultiplier = 1;

    /**
     * Tool level needed to harvest block of this material
     */
    public int harvestLevel;

    /**
     * Material to which smelting of this material ore will result
     */
    public SolidMaterial directSmelting;

    /**
     * Material in which this material's ore should be washed to give additional output
     */
    public FluidMaterial washedIn;

    /**
     * During electromagnetic separation, this material ore will be separated onto this material and material specified by this field
     */
    public DustMaterial separatedOnto;

    public DustMaterial(int metaItemSubId, String name, int materialRGB, MaterialIconSet materialIconSet, int harvestLevel, ImmutableList<MaterialStack> materialComponents, long materialGenerationFlags, Element element) {
        super(metaItemSubId, name, materialRGB, materialIconSet, materialComponents, materialGenerationFlags, element);
        this.harvestLevel = harvestLevel;
    }

    public DustMaterial(int metaItemSubId, String name, int materialRGB, MaterialIconSet materialIconSet, int harvestLevel, ImmutableList<MaterialStack> materialComponents, long materialGenerationFlags) {
        super(metaItemSubId, name, materialRGB, materialIconSet, materialComponents, materialGenerationFlags, null);
        this.harvestLevel = harvestLevel;
    }

    @Override
    protected void initializeMaterial() {
        super.initializeMaterial();
        if(shouldGenerateFluid()) {
            setFluidTemperature(1200); //default value for dusts
        }
    }

    @Override
    public boolean shouldGenerateFluid() {
        return hasFlag(MatFlags.SMELT_INTO_FLUID);
    }

    public void addOreByProducts(FluidMaterial... byProducts) {
        this.oreByProducts.addAll(Arrays.asList(byProducts));
    }

    public DustMaterial setDirectSmelting(SolidMaterial directSmelting) {
        this.directSmelting = directSmelting;
        return this;
    }

    public DustMaterial setOreMultiplier(int oreMultiplier) {
        this.oreMultiplier = oreMultiplier;
        return this;
    }

    public DustMaterial setSmeltingMultiplier(int smeltingMultiplier) {
        this.smeltingMultiplier = smeltingMultiplier;
        return this;
    }

    public DustMaterial setByProductMultiplier(int byProductMultiplier) {
        this.byProductMultiplier = byProductMultiplier;
        return this;
    }
}
