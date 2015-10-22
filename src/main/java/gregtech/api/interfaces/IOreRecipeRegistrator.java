package gregtech.api.interfaces;

import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import net.minecraft.item.ItemStack;

public interface IOreRecipeRegistrator {
    /**
     * Contains a Code Fragment, used in the OrePrefix to register Recipes. Better than using a switch/case, like I did before.
     *
     * @param aPrefix   always != null
     * @param aMaterial always != null, and can be == _NULL if the Prefix is Self Referencing or not Material based!
     * @param aStack    always != null
     */
    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack);
}