package gregtech.api.unification.ore;

import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.unification.stack.SimpleItemStack;

public interface IOreRegistrationHandler {

    /**
     * Contains a code fragment, used in the recipe registration
     */
    void registerOre(UnificationEntry entry, String modName, SimpleItemStack itemStack);

}