package gregtech.api.interfaces;

import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import net.minecraft.item.ItemStack;

public interface IOreRecipeRegistrator {

    /**
     * Contains a Code Fragment, used in the
     *
     * @param prefix   always != null
     * @param material always != null, and can be == _NULL if the Prefix is Self Referencing or not Material based!
     * @param stack    always != null
     */
    void registerOre(OrePrefixes prefix, Materials material, String oreDictName, String modName, ItemStack stack);

}