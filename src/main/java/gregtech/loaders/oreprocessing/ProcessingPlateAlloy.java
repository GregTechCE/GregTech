package gregtech.loaders.oreprocessing;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class ProcessingPlateAlloy implements gregtech.api.interfaces.IOreRecipeRegistrator {
    public ProcessingPlateAlloy() {
        OrePrefixes.plateAlloy.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        if (aOreDictName.equals("plateAlloyCarbon")) {
            GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getIC2Item("generator", 1), GT_Utility.copyAmount(4L, aStack), GT_ModHandler.getIC2Item("windMill", 1), 6400, 8);
        } else if (aOreDictName.equals("plateAlloyAdvanced")) {
            GT_ModHandler.addAlloySmelterRecipe(GT_Utility.copyAmount(1, new Object[]{aStack}), new ItemStack(Blocks.GLASS, 3, 32767), GT_ModHandler.getIC2Item("reinforcedGlass", 4), 400, 4, false);
            GT_ModHandler.addAlloySmelterRecipe(GT_Utility.copyAmount(1, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Glass, 3L), GT_ModHandler.getIC2Item("reinforcedGlass", 4), 400, 4, false);
        } else if (aOreDictName.equals("plateAlloyIridium")) {
            GT_ModHandler.removeRecipeByOutput(aStack);
        }
    }
}
