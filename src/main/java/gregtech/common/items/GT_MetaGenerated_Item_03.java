package gregtech.common.items;

import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.items.GT_MetaGenerated_Item_X32;

public class GT_MetaGenerated_Item_03
  extends GT_MetaGenerated_Item_X32
{
  public static GT_MetaGenerated_Item_03 INSTANCE;
  
  public GT_MetaGenerated_Item_03()
  {
    super("metaitem.03", new OrePrefixes[] { OrePrefixes.crateGtDust, OrePrefixes.crateGtIngot, OrePrefixes.crateGtGem, OrePrefixes.crateGtPlate });
    INSTANCE = this;
  }
  
  public boolean doesShowInCreative(OrePrefixes aPrefix, Materials aMaterial, boolean aDoShowAllItems)
  {
    return aDoShowAllItems;
  }
}


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.items.GT_MetaGenerated_Item_03
 * JD-Core Version:    0.7.0.1
 */