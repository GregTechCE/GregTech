package gregtech.loaders.oreprocessing;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingBattery implements gregtech.api.interfaces.IOreRecipeRegistrator {
    public ProcessingBattery() {
        OrePrefixes.battery.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        if (aMaterial == Materials.Lithium) {
            GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_ModHandler.getIC2Item("cropnalyzer", 1L, 32767), ItemList.Tool_Scanner.getAlmostBroken(1L, new Object[0]), 12800, 16);
        }
    }
}
