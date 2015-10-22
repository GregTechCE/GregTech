package gregtech.loaders.oreprocessing;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.interfaces.internal.IGT_RecipeAdder;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingToolHeadArrow implements gregtech.api.interfaces.IOreRecipeRegistrator
{
  public ProcessingToolHeadArrow()
  {
    OrePrefixes.toolHeadArrow.add(this);
  }
  
  public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
  {
    if (aMaterial.mStandardMoltenFluid != null) GT_Values.RA.addFluidSolidifierRecipe(ItemList.Shape_Mold_Arrow.get(0L, new Object[0]), aMaterial.getMolten(36L), GT_Utility.copyAmount(1L, new Object[] { aStack }), 16, 4);
  }
}


/* Location:              F:\Torrent\minecraft\jdgui test\gregtech_1.7.10-5.07.07-dev.jar!\gregtech\loaders\oreprocessing\ProcessingToolHeadArrow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */