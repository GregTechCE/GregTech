package gregtech.loaders.oreprocessing;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingWire04 implements gregtech.api.interfaces.IOreRecipeRegistrator
{
  public ProcessingWire04()
  {
    OrePrefixes.wireGt04.add(this);
  }
  
  public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
  {
    GT_Values.RA.addBoxingRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Rubber, 2L), GT_OreDictUnificator.get(OrePrefixes.cableGt04, aMaterial, 1L), 200, 8);
    GT_Values.RA.addUnboxingRecipe(GT_OreDictUnificator.get(OrePrefixes.cableGt04, aMaterial, 1L), GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Rubber, 2L), 200, 8);
    GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.cableGt04, aMaterial, 1L), new Object[] { aOreDictName, OrePrefixes.plate.get(Materials.Rubber), OrePrefixes.plate.get(Materials.Rubber) });
    GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.wireGt01, aMaterial, 4L), new Object[] { aOreDictName });
    GT_ModHandler.addShapelessCraftingRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), new Object[] { OrePrefixes.wireGt02.get(aMaterial), OrePrefixes.wireGt02.get(aMaterial) });
  }
}


/* Location:              F:\Torrent\minecraft\jdgui test\gregtech_1.7.10-5.07.07-dev.jar!\gregtech\loaders\oreprocessing\ProcessingWire04.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */