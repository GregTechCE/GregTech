package gregtech.loaders.oreprocessing;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingIngotHot implements gregtech.api.interfaces.IOreRecipeRegistrator {
    public ProcessingIngotHot() {
        OrePrefixes.ingotHot.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        GT_Values.RA.addVacuumFreezerRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial, 1L), (int) Math.max(aMaterial.getMass() * 3L, 1L));
    }
}
