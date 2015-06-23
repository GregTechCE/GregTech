package gregtech.common.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.items.GT_Generic_Item;
import gregtech.api.util.GT_Utility;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class GT_FluidDisplayItem
  extends GT_Generic_Item
{
  public GT_FluidDisplayItem()
  {
    super("GregTech_FluidDisplay", "Fluid Display", null);
    ItemList.Display_Fluid.set(this);
  }
  
  protected void addAdditionalToolTips(List aList, ItemStack aStack)
  {
    NBTTagCompound aNBT = aStack.getTagCompound();
    if (GT_Values.D1)
    {
      Fluid tFluid = FluidRegistry.getFluid(aStack.getItemDamage());
      if (tFluid != null) {
        aList.add("Registry: " + tFluid.getName());
      }
    }
    if (aNBT != null)
    {
      long tToolTipAmount = aNBT.getLong("mFluidDisplayAmount");
      if (tToolTipAmount > 0L) {
        aList.add(EnumChatFormatting.BLUE + "Amount: " + tToolTipAmount + EnumChatFormatting.GRAY);
      }
      aList.add(EnumChatFormatting.RED + "Temperature: " + aNBT.getLong("mFluidDisplayHeat") + " K" + EnumChatFormatting.GRAY);
      aList.add(EnumChatFormatting.GREEN + "State: " + (aNBT.getBoolean("mFluidState") ? "Gas" : "Liquid") + EnumChatFormatting.GRAY);
    }
  }
  
  @SideOnly(Side.CLIENT)
  public void registerIcons(IIconRegister aIconRegister) {}
  
  public IIcon getIconFromDamage(int aMeta)
  {
    Fluid tFluid = FluidRegistry.getFluid(aMeta);
    return tFluid == null ? FluidRegistry.WATER.getStillIcon() : tFluid.getStillIcon();
  }
  
  @SideOnly(Side.CLIENT)
  public int getColorFromItemStack(ItemStack aStack, int aRenderPass)
  {
    Fluid tFluid = FluidRegistry.getFluid(aStack.getItemDamage());
    return tFluid == null ? 16777215 : tFluid.getColor();
  }
  
  public int getSpriteNumber()
  {
    return 0;
  }
  
  public String getUnlocalizedName(ItemStack aStack)
  {
    if (aStack != null) {
      return GT_Utility.getFluidName(FluidRegistry.getFluid(aStack.getItemDamage()), false);
    }
    return "";
  }
  
  public String getItemStackDisplayName(ItemStack aStack)
  {
    if (aStack != null) {
      return GT_Utility.getFluidName(FluidRegistry.getFluid(aStack.getItemDamage()), true);
    }
    return "";
  }
  
  @SideOnly(Side.CLIENT)
  public void getSubItems(Item aItem, CreativeTabs aTab, List aList)
  {
    if (GT_Values.D1)
    {
      int i = 0;
      for (int j = FluidRegistry.getMaxID(); i < j; i++)
      {
        ItemStack tStack = GT_Utility.getFluidDisplayStack(FluidRegistry.getFluid(i));
        if (tStack != null) {
          aList.add(tStack);
        }
      }
    }
  }
}


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.items.GT_FluidDisplayItem
 * JD-Core Version:    0.7.0.1
 */