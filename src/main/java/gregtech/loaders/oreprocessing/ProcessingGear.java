package gregtech.loaders.oreprocessing;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import net.minecraft.item.ItemStack;

public class ProcessingGear implements gregtech.api.interfaces.IOreRecipeRegistrator {
    public ProcessingGear() {
        OrePrefixes.gearGt.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        GT_ModHandler.removeRecipeByOutput(aStack);
        if (aMaterial.mStandardMoltenFluid != null)
            GT_Values.RA.addFluidSolidifierRecipe(ItemList.Shape_Mold_Gear.get(0L, new Object[0]), aMaterial.getMolten(576L), GT_OreDictUnificator.get(aPrefix, aMaterial, 1L), 128, 8);
    }
}
