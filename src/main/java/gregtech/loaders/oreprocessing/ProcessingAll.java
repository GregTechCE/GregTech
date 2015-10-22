package gregtech.loaders.oreprocessing;

import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ProcessingAll implements gregtech.api.interfaces.IOreRecipeRegistrator
{
  public ProcessingAll()
  {
    for (OrePrefixes tPrefix : OrePrefixes.values()) tPrefix.add(this);
  }
  
  public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
  {
    if (((aStack.getItem() instanceof net.minecraft.item.ItemBlock)) && (aPrefix.mDefaultStackSize < aStack.getItem().getItemStackLimit(aStack))) aStack.getItem().setMaxStackSize(aPrefix.mDefaultStackSize);
  }
}


/* Location:              F:\Torrent\minecraft\jdgui test\gregtech_1.7.10-5.07.07-dev.jar!\gregtech\loaders\oreprocessing\ProcessingAll.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */