package gregtech.api.enums.material;

import com.google.common.collect.ImmutableList;
import gregtech.api.enums.Element;
import gregtech.api.enums.SubTag;
import gregtech.api.objects.MaterialStack;
import gregtech.common.GT_Proxy;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class SolidMaterial extends Material {

    public static final class MatFlags {
        /*
        Material sub items generation flags
        Specifying one of this will also result all required sub items generated
        As example, if you will specify GENERATE_INGOT, it will automatically add GENERATE_DUST and recipes for smelting.
         */
        public static final int GENERATE_DUST =           Material.MatFlags.createFlag(10);
        public static final int GENERATE_INGOT =          Material.MatFlags.createFlag(11); //plus plate + foil
        public static final int GENERATE_ROD =            Material.MatFlags.createFlag(12);
        public static final int GENERATE_LONG_ROD =       Material.MatFlags.createFlag(13);
        public static final int GENERATE_ORE_PROCESSING = Material.MatFlags.createFlag(14);
        public static final int GENERATE_BOLT =           Material.MatFlags.createFlag(15);
        public static final int GENERATE_SCREW =          Material.MatFlags.createFlag(16);
        public static final int GENERATE_RING =           Material.MatFlags.createFlag(17);
        public static final int GENERATE_SPRING =         Material.MatFlags.createFlag(18);
        public static final int GENERATE_GEAR =           Material.MatFlags.createFlag(19);
        public static final int GENERATE_ROTOR =          Material.MatFlags.createFlag(20);

        /**
         * When specified, this material will have molten fluid generated for it
         * Note that it will not make material have plasma. For plasma, see {@link gregtech.api.enums.material.Material.MatFlags#GENERATE_PLASMA}
         */
        public static final int GENERATE_FLUID =          Material.MatFlags.createFlag(21);
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
     * Ore value of ore of this material
     * Used for IC2 miner, as example
     */
    public final int mOreValue;

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
     */
    public final SolidMaterial directSmelting;

    /**
     * When value of this field is not "this", ore processing of this material
     * will result into crushed ores and byproducts of specified material
     */
    public final SolidMaterial oreReplacement;

    /**
     * Macerating any item of this material will result material
     * specified in this field
     */
    public final SolidMaterial macerateInto;

    /**
     * Smelting any item of this material will result material
     * specified in this field
     */
    public final SolidMaterial smeltInto;

    /**
     * Arc Smelting any item of this material will result material
     * specified in this field
     */
    public final SolidMaterial arcSmeltInto;

    /**
     * Material specified here will be required as handle to make tool
     * from this material
     */
    public final SolidMaterial handleMaterial;

    /**
     * Material kind
     */
    private final MaterialKind materialKind;

    /**
     * Liquid form of this material
     */
    public Fluid liquidForm;
    /**
     * Plasma form of this material
     */
    public Fluid plasmaForm;

    public SolidMaterial(String defaultLocalName, int materialRGB, MaterialIconSet materialIconSet, boolean unifiable, ImmutableList<MaterialStack> materialComponents, ImmutableList<Material> oreReRegistrations, ImmutableList<SubTag> subTags, float densityMultiplier, Element element, boolean mHasParentMod, String chemicalFormula, float toolSpeed, byte toolQuality, int toolDurability, Enchantment toolEnchantment, int toolEnchantmentLevel, int blastFurnaceTemp, int mOreValue, int oreMultiplier, int mByProductMultiplier, int mSmeltingMultiplier, SolidMaterial directSmelting, SolidMaterial oreReplacement, SolidMaterial macerateInto, SolidMaterial smeltInto, SolidMaterial arcSmeltInto, SolidMaterial handleMaterial, MaterialKind materialKind) {
        super(defaultLocalName, materialRGB, materialIconSet, unifiable, materialComponents, oreReRegistrations, subTags, densityMultiplier, element, mHasParentMod, chemicalFormula);
        this.toolSpeed = toolSpeed;
        this.toolQuality = toolQuality;
        this.toolDurability = toolDurability;
        this.toolEnchantment = toolEnchantment;
        this.toolEnchantmentLevel = toolEnchantmentLevel;
        this.blastFurnaceTemp = blastFurnaceTemp;
        this.mOreValue = mOreValue;
        this.oreMultiplier = oreMultiplier;
        this.mByProductMultiplier = mByProductMultiplier;
        this.mSmeltingMultiplier = mSmeltingMultiplier;
        this.directSmelting = directSmelting;
        this.oreReplacement = oreReplacement;
        this.macerateInto = macerateInto;
        this.smeltInto = smeltInto;
        this.arcSmeltInto = arcSmeltInto;
        this.handleMaterial = handleMaterial;
        this.materialKind = materialKind;
        if(!materialKind.isSolid()) {
            throw new IllegalArgumentException("SolidMaterial is class for Solid Kind of materials, not Liquid!");
        }
    }

    @Override
    protected void initMaterial(ResourceLocation resourceLocation) {
        if(hasGenerationFlag(MatFlags.GENERATE_FLUID)) {
            liquidForm = FluidRegistry.getFluid(resourceLocation.getResourcePath());
            if(liquidForm == null) {
                GT_Proxy
                liquidForm = new Fluid(resourceLocation.getResourcePath(),)
            }
        }
    }

    @Override
    public MaterialKind getKind() {
        return materialKind;
    }

    @Override
    public FluidStack getFluid(int amount) {
        return liquidForm == null ? null : new FluidStack(liquidForm, amount);
    }

    @Override
    public FluidStack getPlasma(int amount) {
        return plasmaForm == null ? null : new FluidStack(plasmaForm, amount);
    }

}
