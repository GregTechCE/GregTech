package gregtech.loaders.oreprocessing;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingWax implements gregtech.api.interfaces.IOreRecipeRegistrator
{
  public ProcessingWax()
  {
    OrePrefixes.wax.add(this);
  }
  
  public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
  {
    if (aOreDictName.equals("waxMagical")) GT_Values.RA.addFuel(GT_Utility.copyAmount(1L, new Object[] { aStack }), null, 6, 5);
  }
}


/* Location:              F:\Torrent\minecraft\jdgui test\gregtech_1.7.10-5.07.07-dev.jar!\gregtech\loaders\oreprocessing\ProcessingWax.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */