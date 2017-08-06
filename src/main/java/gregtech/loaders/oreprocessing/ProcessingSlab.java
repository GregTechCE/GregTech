package gregtech.loaders.oreprocessing;

import gregtech.api.GT_Values;
import gregtech.api.items.ItemList;
import gregtech.api.material.Materials;
import gregtech.api.material.OrePrefixes;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingSlab implements gregtech.api.interfaces.IOreRecipeRegistrator {
    public ProcessingSlab() {
        OrePrefixes.slab.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        if (aOreDictName.startsWith("slabWood")) {
            GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(3L, new Object[]{aStack}), Materials.Creosote.getFluid(1000L), ItemList.RC_Tie_Wood.get(1L, new Object[0]), null, null, null, 200, 4);
        }
    }
}
