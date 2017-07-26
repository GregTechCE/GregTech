package gregtech.api.enums.material.types;

import com.google.common.collect.ImmutableList;
import gregtech.api.enums.Element;
import gregtech.api.enums.material.MaterialIconSet;
import gregtech.api.objects.MaterialStack;
import gregtech.api.util.EnchantmentData;

import static gregtech.api.enums.material.types.Material.MatFlags.createFlag;

public class GemMaterial extends SolidMaterial {

    public static final class MatFlags {

        /**
         * If this material is crystallisable
         */
        public static final int CRYSTALLISABLE = createFlag(25);

    }

    public GemMaterial(int metaItemSubId, String name, String defaultLocalName, int materialRGB, MaterialIconSet materialIconSet, ImmutableList<MaterialStack> materialComponents, int materialGenerationFlags, Element element, float densityMultiplier, float toolSpeed, int toolQuality, int toolDurability) {
        super(metaItemSubId, name, defaultLocalName, materialRGB, materialIconSet, materialComponents, materialGenerationFlags, element, densityMultiplier, toolSpeed, toolQuality, toolDurability);
    }

}

