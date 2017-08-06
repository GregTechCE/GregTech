package gregtech.api.interfaces;

import gregtech.api.objects.SimpleItemStack;
import gregtech.api.objects.UnificationEntry;

public interface IOreRecipeRegistrator {

    /**
     * Contains a Code Fragment, used in the recipe registration
     */
    void registerOre(UnificationEntry unificationEntry, String modName, SimpleItemStack itemStack);

}