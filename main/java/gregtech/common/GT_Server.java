package gregtech.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GT_Server
  extends GT_Proxy
{
  public boolean isServerSide()
  {
    return true;
  }
  
  public boolean isClientSide()
  {
    return false;
  }
  
  public boolean isBukkitSide()
  {
    return false;
  }
  
  public void doSonictronSound(ItemStack aStack, World aWorld, double aX, double aY, double aZ) {}
  
  public int addArmor(String aPrefix)
  {
    return 0;
  }
  
  public EntityPlayer getThePlayer()
  {
    return null;
  }
}


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.GT_Server
 * JD-Core Version:    0.7.0.1
 */