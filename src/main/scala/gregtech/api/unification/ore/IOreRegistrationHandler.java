package gregtech.api.unification.ore;

import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.unification.stack.SimpleItemStack;

@FunctionalInterface
public interface IOreRegistrationHandler {

    void processMaterial(OrePrefix orePrefix, Material material);

}