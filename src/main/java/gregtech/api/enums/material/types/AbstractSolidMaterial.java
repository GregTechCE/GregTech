package gregtech.api.enums.material.types;

import com.google.common.base.Preconditions;
import gregtech.api.enums.Element;
import gregtech.api.enums.SubTag;
import gregtech.api.enums.material.*;
import gregtech.api.objects.MaterialStack;
import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

import static gregtech.api.enums.SubTag.getNewSubTag;
import static gregtech.api.enums.material.Material.MatFlags.createFlag;

public class AbstractSolidMaterial extends DustMaterial implements gregtech.api.enums.material.FluidMaterial {

    public static final class MatFlags {

        public static final int GENERATE_DUST =           createFlag(10);
        public static final int GENERATE_INGOT =          createFlag(11); //plus plate + foil
        public static final int GENERATE_ROD =            createFlag(12);
        public static final int GENERATE_LONG_ROD =       createFlag(13);
        public static final int GENERATE_ORE_PROCESSING = createFlag(14);
        public static final int GENERATE_BOLT =           createFlag(15);
        public static final int GENERATE_SCREW =          createFlag(16);
        public static final int GENERATE_RING =           createFlag(17);
        public static final int GENERATE_SPRING =         createFlag(18);
        public static final int GENERATE_GEAR =           createFlag(19);
        public static final int GENERATE_ROTOR =          createFlag(20);

        public static final int GENERATE_PLASMA =          createFlag(21);
        public static final int GENERATE_FLUID =          createFlag(22);

        /**
         * This Material cannot be worked by any other means, than smashing or smelting. This is used for coated Materials.
         */
        public static final SubTag NO_WORKING = getNewSubTag("NO_WORKING");
        /**
         * This Material cannot be used for regular Metal working techniques since it is not possible to bend it. Already listed are:
         * Rubber, Plastic, Paper, Wood, Stone
         */
        public static final SubTag NO_SMASHING = getNewSubTag("NO_SMASHING");

        /**
         * This Material can be molten into a Fluid
         */
        public static final SubTag SMELTING_TO_FLUID = getNewSubTag("SMELTING_TO_FLUID");
        /**
         * This Ore should be molten directly into a Gem of this Material, if the Ingot is missing. Already listed are:
         * Cinnabar
         */
        public static final SubTag SMELTING_TO_GEM = getNewSubTag("SMELTING_TO_GEM");

        /**
         * Add this to your Material if you want to have its Ore Calcite heated in a Blast Furnace for more output. Already listed are:
         * Iron, Pyrite, PigIron, WroughtIron.
         */
        public static final SubTag BLAST_FURNACE_CALCITE_DOUBLE = getNewSubTag("BLASTFURNACE_CALCITE_DOUBLE");
        public static final SubTag BLAST_FURNACE_CALCITE_TRIPLE = getNewSubTag("BLASTFURNACE_CALCITE_TRIPLE");

        /**
         * If this Material is grindable with a simple Mortar
         */
        public static final SubTag MORTAR_GRINDABLE = getNewSubTag("MORTAR_GRINDABLE");
        /**
         * If this Material is usable for Soldering
         */
        public static final SubTag SOLDERING_MATERIAL = getNewSubTag("SOLDERING_MATERIAL");
        /**
         * If this Material is has extra Costs for Soldering, requires the Tag "SOLDERING_MATERIAL" too
         */
        public static final SubTag SOLDERING_MATERIAL_BAD = getNewSubTag("SOLDERING_MATERIAL_BAD");
        /**
         * If this Material is has a discount for Soldering, requires the Tag "SOLDERING_MATERIAL" too
         */
        public static final SubTag SOLDERING_MATERIAL_GOOD = getNewSubTag("SOLDERING_MATERIAL_GOOD");

    }

    /**
     * Speed of tools made from this material
     * Default value is 1.0f
     */
    public final float toolSpeed;

    /**
     * Quality (tier) of tools made from this material
     * Equal to 0 for materials that can't be used for tools
     */
    public final byte toolQuality;

    /**
     * Durability of tools made from this material
     * Equal to 0 for materials that can't be used for tools
     */
    public final int toolDurability;

    /**
     * Enchantment to be applied to tools made from this material
     */
    public final Enchantment toolEnchantment;

    /**
     * Level of enchantment applied to tools made from this material
     */
    public final int toolEnchantmentLevel;

    /**
     * Blast furnace melting temperature of this material
     * Equal to -1 if material doesn't use blast furnace
     */
    public final int blastFurnaceTemp;

    /**
     * Material specified here will be required as handle to make tool
     * from this material
     */
    public final AbstractSolidMaterial handleMaterial;

    /**
     * Macerating any item of this material will result material
     * specified in this field
     */
    public final DustMaterial macerateInto;

    private Fluid materialFluid;
    private Fluid materialPlasma;

    public AbstractSolidMaterial(String defaultLocalName, int materialRGB, MaterialIconSet materialIconSet, List<MaterialStack> materialComponents, List<Material> oreReRegistrations, List<SubTag> subTags, int materialGenerationFlags, float densityMultiplier, Element element, int mOreValue, int oreMultiplier, int mByProductMultiplier, int mSmeltingMultiplier, AbstractSolidMaterial directSmelting, AbstractSolidMaterial smeltInto, AbstractSolidMaterial arcSmeltInto, float toolSpeed, byte toolQuality, int toolDurability, Enchantment toolEnchantment, int toolEnchantmentLevel, int blastFurnaceTemp, AbstractSolidMaterial handleMaterial, DustMaterial macerateInto) {
        super(defaultLocalName, materialRGB, materialIconSet, materialComponents, oreReRegistrations, subTags, materialGenerationFlags, densityMultiplier, element, mOreValue, oreMultiplier, mByProductMultiplier, mSmeltingMultiplier, directSmelting, smeltInto, arcSmeltInto);
        this.toolSpeed = toolSpeed;
        this.toolQuality = toolQuality;
        this.toolDurability = toolDurability;
        this.toolEnchantment = toolEnchantment;
        this.toolEnchantmentLevel = toolEnchantmentLevel;
        this.blastFurnaceTemp = blastFurnaceTemp;
        this.handleMaterial = handleMaterial;
        this.macerateInto = macerateInto;
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
