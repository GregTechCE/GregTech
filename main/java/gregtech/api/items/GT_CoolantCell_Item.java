package gregtech.api.items;

import gregtech.api.GregTech_API;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class GT_CoolantCell_Item
  extends GT_Generic_Item
{
  protected int heatStorage;
  
  public GT_CoolantCell_Item(String aUnlocalized, String aEnglish, int aMaxStore)
  {
    super(aUnlocalized, aEnglish, null);
    this.setMaxStackSize(1);
    this.setMaxDamage(100);
    setNoRepair();
    this.heatStorage = aMaxStore;
    this.setCreativeTab(GregTech_API.TAB_GREGTECH);
  }
  
  protected void setHeatForStack(ItemStack aStack, int aHeat)
  {
    NBTTagCompound tNBT = aStack.getTagCompound();
    if (tNBT == null)
    {
      tNBT = new NBTTagCompound();
      aStack.setTagCompound(tNBT);
    }
    tNBT.setInteger("heat", aHeat);
    if (this.heatStorage > 0)
    {
      double var4 = (double)aHeat / (double)this.heatStorage;
      int var6 = (int)(aStack.getMaxDamage() * var4);
      if (var6 >= aStack.getMaxDamage()) {
        var6 = aStack.getMaxDamage() - 1;
      }
      aStack.setItemDamage(var6);
    }
  }
  
  public void addAdditionalToolTips(List aList, ItemStack aStack)
  {
    super.addAdditionalToolTips(aList, aStack);
    aList.add("Stored Heat: " + getHeatOfStack(aStack));
  }
  
  protected static int getHeatOfStack(ItemStack aStack)
  {
    NBTTagCompound tNBT = aStack.getTagCompound();
    if (tNBT == null)
    {
      tNBT = new NBTTagCompound();
      aStack.setTagCompound(tNBT);
    }
    return tNBT.getInteger("heat");
  }
}
