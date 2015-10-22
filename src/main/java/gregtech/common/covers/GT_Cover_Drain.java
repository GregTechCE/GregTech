package gregtech.common.covers;

import gregtech.api.enums.Materials;
import gregtech.api.interfaces.tileentity.ICoverable;
import gregtech.api.interfaces.tileentity.IMachineProgress;
import gregtech.api.util.GT_CoverBehavior;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fluids.IFluidHandler;

public class GT_Cover_Drain
  extends GT_CoverBehavior
{
  public int doCoverThings(byte aSide, byte aInputRedstone, int aCoverID, int aCoverVariable, ICoverable aTileEntity, long aTimer)
  {
    if ((aCoverVariable % 3 > 1) && ((aTileEntity instanceof IMachineProgress))) {
      if (((IMachineProgress)aTileEntity).isAllowedToWork() != aCoverVariable % 3 < 2) {
        return aCoverVariable;
      }
    }
    if (aSide != 6)
    {
      Block tBlock = aTileEntity.getBlockAtSide(aSide);
      if ((aCoverVariable < 3) && ((aTileEntity instanceof IFluidHandler)))
      {
        if ((aSide == 1) && 
          (aTileEntity.getWorld().isRaining()) && 
          (aTileEntity.getWorld().getPrecipitationHeight(aTileEntity.getXCoord(), aTileEntity.getZCoord()) - 2 < aTileEntity.getYCoord()))
        {
          int tAmount = (int)(aTileEntity.getBiome().rainfall * 10.0F);
          if (tAmount > 0) {
            ((IFluidHandler)aTileEntity).fill(ForgeDirection.getOrientation(aSide), Materials.Water.getFluid(aTileEntity.getWorld().isThundering() ? tAmount * 2 : tAmount), true);
          }
        }
        FluidStack tLiquid = null;
        if (tBlock != null)
        {
          if (((tBlock == Blocks.water) || (tBlock == Blocks.flowing_water)) && (aTileEntity.getMetaIDAtSide(aSide) == 0)) {
            tLiquid = Materials.Water.getFluid(1000L);
          } else if (((tBlock == Blocks.lava) || (tBlock == Blocks.flowing_lava)) && (aTileEntity.getMetaIDAtSide(aSide) == 0)) {
            tLiquid = Materials.Lava.getFluid(1000L);
          } else if ((tBlock instanceof IFluidBlock)) {
            tLiquid = ((IFluidBlock)tBlock).drain(aTileEntity.getWorld(), aTileEntity.getOffsetX(aSide, 1), aTileEntity.getOffsetY(aSide, 1), aTileEntity.getOffsetZ(aSide, 1), false);
          }
          if ((tLiquid != null) && (tLiquid.getFluid() != null) && ((aSide > 1) || ((aSide == 0) && (tLiquid.getFluid().getDensity() <= 0)) || ((aSide == 1) && (tLiquid.getFluid().getDensity() >= 0))) && 
            (((IFluidHandler)aTileEntity).fill(ForgeDirection.getOrientation(aSide), tLiquid, false) == tLiquid.amount))
          {
            ((IFluidHandler)aTileEntity).fill(ForgeDirection.getOrientation(aSide), tLiquid, true);
            aTileEntity.getWorld().setBlockToAir(aTileEntity.getXCoord() + ForgeDirection.getOrientation(aSide).offsetX, aTileEntity.getYCoord() + ForgeDirection.getOrientation(aSide).offsetY, aTileEntity.getZCoord() + ForgeDirection.getOrientation(aSide).offsetZ);
          }
        }
      }
      if ((aCoverVariable >= 3) && (tBlock != null) && (
        (tBlock == Blocks.lava) || (tBlock == Blocks.flowing_lava) || (tBlock == Blocks.water) || (tBlock == Blocks.flowing_water) || ((tBlock instanceof IFluidBlock)))) {
        aTileEntity.getWorld().setBlock(aTileEntity.getOffsetX(aSide, 1), aTileEntity.getOffsetY(aSide, 1), aTileEntity.getOffsetZ(aSide, 1), Blocks.air, 0, 0);
      }
    }
    return aCoverVariable;
  }
  
  public int onCoverScrewdriverclick(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, EntityPlayer aPlayer, float aX, float aY, float aZ)
  {
    aCoverVariable = (aCoverVariable + 1) % 6;
    if (aCoverVariable == 0) {
      GT_Utility.sendChatToPlayer(aPlayer, "Import");
    }
    if (aCoverVariable == 1) {
      GT_Utility.sendChatToPlayer(aPlayer, "Import (conditional)");
    }
    if (aCoverVariable == 2) {
      GT_Utility.sendChatToPlayer(aPlayer, "Import (invert cond)");
    }
    if (aCoverVariable == 3) {
      GT_Utility.sendChatToPlayer(aPlayer, "Keep Liquids Away");
    }
    if (aCoverVariable == 4) {
      GT_Utility.sendChatToPlayer(aPlayer, "Keep Liquids Away (conditional)");
    }
    if (aCoverVariable == 5) {
      GT_Utility.sendChatToPlayer(aPlayer, "Keep Liquids Away (invert cond)");
    }
    return aCoverVariable;
  }
  
  public boolean letsFluidIn(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity)
  {
    if ((aCoverVariable > 1) && ((aTileEntity instanceof IMachineProgress))) {}
    return ((IMachineProgress)aTileEntity).isAllowedToWork() == aCoverVariable < 2;
  }
  
  public boolean alwaysLookConnected(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
  {
    return true;
  }
  
  public int getTickRate(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
  {
    return aCoverVariable < 3 ? 50 : 1;
  }
}


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.covers.GT_Cover_Drain
 * JD-Core Version:    0.7.0.1
 */