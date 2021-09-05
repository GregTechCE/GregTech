package gregtech.api.unification.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.PropertyKey;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenExpansion("mods.gregtech.material.Material")
@ZenRegister
@SuppressWarnings("unused")
public class MaterialPropertyExpansion {

    // Property Checkers and Setters
    @ZenMethod
    public static boolean hasBlastTemp(Material m) {
        return m.hasProperty(PropertyKey.BLAST);
    }

    // TODO
}
