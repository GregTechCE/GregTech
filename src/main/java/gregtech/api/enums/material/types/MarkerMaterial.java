package gregtech.api.enums.material.types;

import com.google.common.collect.ImmutableList;
import gregtech.api.enums.material.MaterialIconSet;

/**
 * MarkerMaterial is type of material used for generic things like material re-registration and use in recipes
 * Marker material cannot be used to generate any meta items
 * Marker material can be used only for marking other materials (re-registeoring) equal to it and then using it in recipes or in getting items v
 */
public final class MarkerMaterial extends Material {

    public MarkerMaterial(String name, String defaultLocalName) {
        super(-1, name, defaultLocalName,
                0xFFFFFF,
                MaterialIconSet.NONE,
                ImmutableList.of(),
                0,
                1.0f);
    }

}
