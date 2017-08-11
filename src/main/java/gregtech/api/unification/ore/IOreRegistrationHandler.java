package gregtech.api.unification.ore;

import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.unification.stack.SimpleItemStack;

public interface IOreRegistrationHandler {

    /**
     * Contains a Code Fragment, used in the recipe registration
     */
    void registerOre(UnificationEntry unificationEntry, String modName, SimpleItemStack itemStack);

}