package gregtech.api.enums.material.types;

import com.google.common.collect.ImmutableList;
import gregtech.api.enums.SubTag;
import gregtech.api.enums.material.Material;
import gregtech.api.enums.material.MaterialIconSet;
import gregtech.api.objects.MaterialStack;

/**
 * MarkerMaterial is type of material used for generic things like material re-registration and use in recipes
 * Marker material cannot be used to generate any meta items
 * Marker material can be used only for marking other materials (re-registeoring) equal to it and then using it in recipes or in getting items v
 */
public final class MarkerMaterial extends Material {

    public MarkerMaterial(String defaultLocalName, int materialRGB) {
        super(defaultLocalName,
                materialRGB,
                MaterialIconSet.NONE,
                ImmutableList.of(),
                ImmutableList.of(),
                ImmutableList.of(),
                0,
                1.0f,
                null);
    }

}
