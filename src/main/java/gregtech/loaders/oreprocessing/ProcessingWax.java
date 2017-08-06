package gregtech.loaders.oreprocessing;

import gregtech.api.GT_Values;
import gregtech.api.material.Materials;
import gregtech.api.material.OrePrefixes;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingWax implements gregtech.api.interfaces.IOreRecipeRegistrator {
    public ProcessingWax() {
        OrePrefixes.wax.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        if (aOreDictName.equals("waxMagical"))
            GT_Values.RA.addFuel(GT_Utility.copyAmount(1L, new Object[]{aStack}), null, 6, 5);
    }
}
