package gregtech.common.blocks;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class GT_Material_Casings
  extends Material
{
  public static final Material INSTANCE = new GT_Material_Casings();
  
  private GT_Material_Casings()
  {
    super(MapColor.ironColor);
    setRequiresTool();
  }
  
  public boolean isOpaque()
  {
    return false;
  }
}


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.blocks.GT_Material_Casings
 * JD-Core Version:    0.7.0.1
 */