package gregtech.api.enums.material.types;

import com.google.common.collect.ImmutableList;
import gregtech.api.enums.Element;
import gregtech.api.enums.material.MaterialIconSet;
import gregtech.api.objects.MaterialStack;
import gregtech.api.util.DelayedFunction;
import gregtech.api.util.EnchantmentData;
import gregtech.api.util.FPUtil;
import net.minecraft.enchantment.Enchantment;

import java.util.ArrayList;
import java.util.function.Function;

import static gregtech.api.enums.material.types.DustMaterial.MatFlags.GENERATE_PLATE;
import static gregtech.api.enums.material.types.Material.MatFlags.createFlag;
import static gregtech.api.enums.material.types.SolidMaterial.MatFlags.GENERATE_GEAR;
import static gregtech.api.enums.material.types.SolidMaterial.MatFlags.GENERATE_LONG_ROD;
import static gregtech.api.enums.material.types.SolidMaterial.MatFlags.GENERATE_ROD;

public class SolidMaterial extends DustMaterial {

    public static final Function<String, SolidMaterial> RESOLVE_MATERIAL_SOLID = FPUtil.wrapCasting(RESOLVE_MATERIAL, SolidMaterial.class);

    public static final class MatFlags {

        public static final int GENERATE_ROD = createFlag(20);
        public static final int GENERATE_GEAR = createFlag(21);
        public static final int GENERATE_LONG_ROD = createFlag(22);

        /**
         * If this Material is grindable with a simple Mortar
         */
        public static final int MORTAR_GRINDABLE = createFlag(24);

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
    public final int toolQuality;

    /**
     * Durability of tools made from this material
     * Equal to 0 for materials that can't be used for tools
     */
    public final int toolDurability;

    /**
     * Enchantment to be applied to tools made from this material
     */
    public final ArrayList<EnchantmentData> toolEnchantments = new ArrayList<>();

    /**
     * Material specified here will be required as handle to make tool
     * from this material
     */
    public SolidMaterial handleMaterial;

    /**
     * Macerating any item of this material will result material
     * specified in this field
     */
    public DustMaterial macerateInto = this;

    /**
     * Associated chemical constants of this material
     * Note that all of them are in Kelvins
     */
    public final int meltingPoint, boilingPoint;

    public SolidMaterial(int metaItemSubId, String name, String defaultLocalName, int materialRGB, MaterialIconSet materialIconSet, ImmutableList<MaterialStack> materialComponents, int materialGenerationFlags, Element element, float densityMultiplier, float toolSpeed, int toolQuality, int toolDurability, int meltingPoint, int boilingPoint) {
        super(metaItemSubId, name, defaultLocalName, materialRGB, materialIconSet, materialComponents, materialGenerationFlags, element, densityMultiplier);
        this.toolSpeed = toolSpeed;
        this.toolQuality = toolQuality;
        this.toolDurability = toolDurability;
        this.directSmelting = this;
        this.meltingPoint = meltingPoint;
        this.boilingPoint = boilingPoint;
    }

    @Override
    protected int verifyMaterialBits(int generationBits) {
        if((generationBits & GENERATE_GEAR) > 0) {
            generationBits |= GENERATE_PLATE;
            generationBits |= GENERATE_ROD;
        }
        if((generationBits & GENERATE_LONG_ROD) > 0) {
            generationBits |= GENERATE_ROD;
        }
        return super.verifyMaterialBits(generationBits);
    }

}
