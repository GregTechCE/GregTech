package gregtech.api.enums.material.types;

import com.google.common.collect.ImmutableList;
import gregtech.api.enums.Element;
import gregtech.api.enums.SubTag;
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
         * If this Material is some kind of Wood
         */
        public static final SubTag WOOD = getNewSubTag("WOOD");
        /**
         * If this Material is some kind of Food (or edible at all)
         */
        public static final SubTag FOOD = getNewSubTag("FOOD");
        /**
         * If this Material is some kind of Stone
         */
        public static final SubTag STONE = getNewSubTag("STONE");
        /**
         * If this Material is some kind of Pearl
         */
        public static final SubTag PEARL = getNewSubTag("PEARL");
        /**
         * If this Material is some kind of Quartz
         */
        public static final SubTag QUARTZ = getNewSubTag("QUARTZ");
        /**
         * If this Material is Crystallisable
         */
        public static final SubTag CRYSTALLISABLE = getNewSubTag("CRYSTALLISABLE");

        /**
         * If this Material is some kind of Magical
         */
        public static final SubTag MAGICAL = getNewSubTag("MAGICAL");

        /**
         * If this Material is some kind of Paper
         */
        public static final SubTag PAPER = getNewSubTag("PAPER");
        /**
         * If this Material is having a constantly burning Aura
         */
        public static final SubTag BURNING = getNewSubTag("BURNING");
        /**
         * If this Material is some kind of flammable
         */
        public static final SubTag FLAMMABLE = getNewSubTag("FLAMMABLE");
        /**
         * If this Material is not burnable at all
         */
        public static final SubTag UNBURNABLE = getNewSubTag("UNBURNABLE");
        /**
         * If this Material is some kind of explosive
         */
        public static final SubTag EXPLOSIVE = getNewSubTag("EXPLOSIVE");
        /**
         * If this Material is bouncy
         */
        public static final SubTag BOUNCY = getNewSubTag("BOUNCY");

        /**
         * This Material cannot be used in any Furnace alike Structure. Already listed are:
         * Paper, Wood, Gunpowder, Stone
         */
        public static final SubTag NO_SMELTING = getNewSubTag("NO_SMELTING");

        /**
         * Materials which are outputting less in an Induction Smelter. Already listed are:
         * Pyrite, Tetrahedrite, Sphalerite, Cinnabar
         */
        public static final SubTag INDUCTIONSMELTING_LOW_OUTPUT = getNewSubTag("INDUCTIONSMELTING_LOW_OUTPUT");

        /**
         * Add this to your Material if you want to have its Ore Sodium Persulfate washed. Already listed are:
         * Zinc, Nickel, Copper, Cobalt, Cobaltite and Tetrahedrite.
         */
        public static final SubTag WASHING_SODIUMPERSULFATE = getNewSubTag("WASHING_SODIUMPERSULFATE");

        /**
         * Add this to your Material if you want to have its Ore Mercury washed. Already listed are:
         * Gold, Silver, Osmium, Platinum, Cooperite.
         */
        public static final SubTag WASHING_MERCURY = getNewSubTag("WASHING_MERCURY");

        /**
         * Add this to your Material if you want to have its Ore electromagnetically separated to give Gold.
         */
        public static final SubTag ELECTROMAGNETIC_SEPERATION_GOLD = getNewSubTag("ELECTROMAGNETIC_SEPERATION_GOLD");

        /**
         * Add this to your Material if you want to have its Ore electromagnetically separated to give Iron.
         */
        public static final SubTag ELECTROMAGNETIC_SEPERATION_IRON = getNewSubTag("ELECTROMAGNETIC_SEPERATION_IRON");

        /**
         * Add this to your Material if you want to have its Ore electromagnetically separated to give Neodymium.
         */
        public static final SubTag ELECTROMAGNETIC_SEPERATION_NEODYMIUM = getNewSubTag("ELECTROMAGNETIC_SEPERATION_NEODYMIUM");

        /**
         * Add this to your Material if you want to have its Ore giving Cinnabar Crystals on Pulverization. Already listed are:
         * Redstone
         */
        public static final SubTag PULVERIZING_CINNABAR = getNewSubTag("PULVERIZING_CINNABAR");

    }

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
    public final AbstractSolidMaterial directSmelting;

    /**
     * Smelting any item of this material will result material
     * specified in this field
     */
    public final AbstractSolidMaterial smeltInto;

    /**
     * Arc Smelting any item of this material will result material
     * specified in this field
     */
    public final AbstractSolidMaterial arcSmeltInto;

    public DustMaterial(String defaultLocalName, int materialRGB, String chemicalFormula, MaterialIconSet materialIconSet, ImmutableList<MaterialStack> materialComponents, ImmutableList<Material> oreReRegistrations, ImmutableList<SubTag> subTags, int materialGenerationFlags, float densityMultiplier, Element element, int mOreValue, int oreMultiplier, int mByProductMultiplier, int mSmeltingMultiplier, AbstractSolidMaterial directSmelting, AbstractSolidMaterial smeltInto, AbstractSolidMaterial arcSmeltInto) {
        super(defaultLocalName, materialRGB, chemicalFormula, materialIconSet, materialComponents, oreReRegistrations, subTags, materialGenerationFlags, densityMultiplier, element);
        this.mOreValue = mOreValue;
        this.oreMultiplier = oreMultiplier;
        this.mByProductMultiplier = mByProductMultiplier;
        this.mSmeltingMultiplier = mSmeltingMultiplier;
        this.directSmelting = directSmelting;
        this.smeltInto = smeltInto;
        this.arcSmeltInto = arcSmeltInto;
    }

}
