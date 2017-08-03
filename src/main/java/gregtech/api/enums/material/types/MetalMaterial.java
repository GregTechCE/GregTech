package gregtech.api.enums.material.types;

import com.google.common.collect.ImmutableList;
import gregtech.api.enums.Element;
import gregtech.api.enums.material.MaterialIconSet;
import gregtech.api.objects.MaterialStack;
import gregtech.api.util.DelayedFunction;
import gregtech.api.util.EnchantmentData;
import gregtech.api.util.FPUtil;

import java.util.function.Function;

import static gregtech.api.enums.material.types.DustMaterial.MatFlags.GENERATE_PLATE;
import static gregtech.api.enums.material.types.DustMaterial.MatFlags.SMELT_INTO_FLUID;
import static gregtech.api.enums.material.types.Material.MatFlags.createFlag;
import static gregtech.api.enums.material.types.MetalMaterial.MatFlags.*;
import static gregtech.api.enums.material.types.SolidMaterial.MatFlags.GENERATE_ROD;

public class MetalMaterial extends SolidMaterial {

    public static final class MatFlags {

        public static final int GENERATE_FOIL = createFlag(25);
        public static final int GENERATE_BOLT_SCREW = createFlag(26);
        public static final int GENERATE_RING = createFlag(27);
        public static final int GENERATE_SPRING = createFlag(28);
        public static final int GENERATE_FINE_WIRE = createFlag(29);
        public static final int GENERATE_ROTOR = createFlag(30);
        public static final int GENERATE_DOUBLE = createFlag(31);
        public static final int GENERATE_TRIPLE = createFlag(32);
        public static final int GENERATE_QUADRUPLE = createFlag(33);
        public static final int GENERATE_SMALL_GEAR = createFlag(34);

        /**
         * Add this to your Material if you want to have its Ore Calcite heated in a Blast Furnace for more output. Already listed are:
         * Iron, Pyrite, PigIron, WroughtIron.
         */
        public static final int BLAST_FURNACE_CALCITE_DOUBLE = createFlag(35);
        public static final int BLAST_FURNACE_CALCITE_TRIPLE = createFlag(36);

    }

    /**
     * Specifies a material into which this material parts turn when heated
     */
    public MetalMaterial smeltInto;

    /**
     * Specifies a material into which this material parts turn when heated in arc furnace
     */
    public MetalMaterial arcSmeltInto;

    /**
     * Blast furnace temperature of this material
     * Equal to zero if material doesn't use blast furnace
     * If below 1000C, primitive blast furnace recipes will be also added
     */
    public final int blastFurnaceTemperature;

    public MetalMaterial(int metaItemSubId, String name, String defaultLocalName, int materialRGB, MaterialIconSet materialIconSet, ImmutableList<MaterialStack> materialComponents, int materialGenerationFlags, Element element, float toolSpeed, int toolQuality, int toolDurability, int blastFurnaceTemperature) {
        super(metaItemSubId, name, defaultLocalName, materialRGB, materialIconSet, materialComponents, materialGenerationFlags, element, toolSpeed, toolQuality, toolDurability);
        this.blastFurnaceTemperature = blastFurnaceTemperature;
        this.smeltInto = this;
        this.arcSmeltInto = this;
        add(SMELT_INTO_FLUID);
    }

    public MetalMaterial(int metaItemSubId, String name, String defaultLocalName, int materialRGB, MaterialIconSet materialIconSet, ImmutableList<MaterialStack> materialComponents, int materialGenerationFlags, Element element) {
        this(metaItemSubId, name, defaultLocalName, materialRGB, materialIconSet, materialComponents, materialGenerationFlags, element, 0, 0, 0, 0);
    }

    public MetalMaterial(int metaItemSubId, String name, String defaultLocalName, int materialRGB, MaterialIconSet materialIconSet, ImmutableList<MaterialStack> materialComponents, int materialGenerationFlags) {
        this(metaItemSubId, name, defaultLocalName, materialRGB, materialIconSet, materialComponents, materialGenerationFlags, null, 0, 0, 0, 0);
    }

    public MetalMaterial(int metaItemSubId, String name, String defaultLocalName, int materialRGB, MaterialIconSet materialIconSet, ImmutableList<MaterialStack> materialComponents, int materialGenerationFlags, Element element, int blastFurnaceTemperature) {
        this(metaItemSubId, name, defaultLocalName, materialRGB, materialIconSet, materialComponents, materialGenerationFlags, element, 0, 0, 0, blastFurnaceTemperature);
    }

    public MetalMaterial(int metaItemSubId, String name, String defaultLocalName, int materialRGB, MaterialIconSet materialIconSet, ImmutableList<MaterialStack> materialComponents, int materialGenerationFlags, Element element, float toolSpeed, int toolQuality, int toolDurability) {
        this(metaItemSubId, name, defaultLocalName, materialRGB, materialIconSet, materialComponents, materialGenerationFlags, element, toolSpeed, toolQuality, toolDurability, 0);
    }

    public MetalMaterial(int metaItemSubId, String name, String defaultLocalName, int materialRGB, MaterialIconSet materialIconSet, ImmutableList<MaterialStack> materialComponents, int materialGenerationFlags, float toolSpeed, int toolQuality, int toolDurability) {
        this(metaItemSubId, name, defaultLocalName, materialRGB, materialIconSet, materialComponents, materialGenerationFlags, null, toolSpeed, toolQuality, toolDurability, 0);
    }

    @Override
    protected int verifyMaterialBits(int generationBits) {
        if((generationBits & GENERATE_QUADRUPLE) > 0) {
            generationBits |= GENERATE_TRIPLE;
        }
        if((generationBits & GENERATE_TRIPLE) > 0) {
            generationBits |= GENERATE_DOUBLE;
        }
        if((generationBits & GENERATE_DOUBLE) > 0) {
            generationBits |= GENERATE_PLATE;
        }
        if((generationBits & GENERATE_ROTOR) > 0) {
            generationBits |= GENERATE_BOLT_SCREW;
            generationBits |= GENERATE_RING;
            generationBits |= GENERATE_PLATE;
        }
        if((generationBits & GENERATE_SMALL_GEAR) > 0) {
            generationBits |= GENERATE_PLATE;
        }
        if((generationBits & GENERATE_FOIL) > 0) {
            generationBits |= GENERATE_PLATE;
        }
        if((generationBits & GENERATE_RING) > 0) {
            generationBits |= GENERATE_ROD;
        }
        if((generationBits & GENERATE_BOLT_SCREW) > 0) {
            generationBits |= GENERATE_ROD;
        }
        return super.verifyMaterialBits(generationBits);
    }

    public MetalMaterial setSmeltingInto(MetalMaterial smeltInto) {
        this.smeltInto = smeltInto;
        return this;
    }

    public MetalMaterial setArcSmeltingInto(MetalMaterial arcSmeltingInto) {
        this.arcSmeltInto = arcSmeltingInto;
        return this;
    }

}
