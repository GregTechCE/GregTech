package gregtech.loaders.oreprocessing;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingSlab implements gregtech.api.interfaces.IOreRecipeRegistrator
{
  public ProcessingSlab()
  {
    OrePrefixes.slab.add(this);
  }
  
  public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
  {
    if (aOreDictName.startsWith("slabWood")) {
      GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(3L, new Object[] { aStack }), Materials.Creosote.getFluid(1000L), ItemList.RC_Tie_Wood.get(1L, new Object[0]), null, null, null, 200, 4);
    }
  }
}


/* Location:              F:\Torrent\minecraft\jdgui test\gregtech_1.7.10-5.07.07-dev.jar!\gregtech\loaders\oreprocessing\ProcessingSlab.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */