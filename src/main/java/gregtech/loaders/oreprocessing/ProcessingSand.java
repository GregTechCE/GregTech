package gregtech.loaders.oreprocessing;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class ProcessingSand implements gregtech.api.interfaces.IOreRecipeRegistrator {
    public ProcessingSand() {
        OrePrefixes.sand.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        if (aOreDictName.equals("sandCracked")) {
            GT_Values.RA.addCentrifugeRecipe(GT_Utility.copyAmount(16L, new Object[]{aStack}), -1, gregtech.api.util.GT_ModHandler.getFuelCan(25000), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Saltpeter, 8L), null, null, null, new ItemStack(Blocks.sand, 10), 2500);
        } else if (aOreDictName.equals("sandOil")) {
            GT_Values.RA.addCentrifugeRecipe(GT_Utility.copyAmount(2L, new Object[]{aStack}), 1, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.Oil, 1L), new ItemStack(Blocks.sand, 1, 0), null, null, null, null, 1000);
        }
    }
}
