package gregtech.loaders.oreprocessing;

import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.objects.MaterialStack;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingPipeRestrictive implements gregtech.api.interfaces.IOreRecipeRegistrator
{
  public ProcessingPipeRestrictive()
  {
    for (OrePrefixes tPrefix : OrePrefixes.values()) if (tPrefix.name().startsWith("pipeRestrictive")) tPrefix.add(this);
  }
  
  public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
  {
    gregtech.api.enums.GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(aOreDictName.replaceFirst("Restrictive", ""), null, 1L, false, true), GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Steel, aPrefix.mSecondaryMaterial.mAmount / OrePrefixes.ring.mMaterialAmount), GT_Utility.copyAmount(1L, new Object[] { aStack }), (int)(aPrefix.mSecondaryMaterial.mAmount * 400L / OrePrefixes.ring.mMaterialAmount), 4);
  }
}


/* Location:              F:\Torrent\minecraft\jdgui test\gregtech_1.7.10-5.07.07-dev.jar!\gregtech\loaders\oreprocessing\ProcessingPipeRestrictive.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */