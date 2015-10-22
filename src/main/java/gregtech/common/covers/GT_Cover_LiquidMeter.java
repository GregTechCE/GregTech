package gregtech.common.covers;

import gregtech.api.interfaces.tileentity.ICoverable;
import gregtech.api.util.GT_CoverBehavior;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class GT_Cover_LiquidMeter
        extends GT_CoverBehavior {
    public int doCoverThings(byte aSide, byte aInputRedstone, int aCoverID, int aCoverVariable, ICoverable aTileEntity, long aTimer) {
        if ((aTileEntity instanceof IFluidHandler)) {
            FluidTankInfo[] tTanks = ((IFluidHandler) aTileEntity).getTankInfo(ForgeDirection.UNKNOWN);
            long tAll = 0L;
            long tFull = 0L;
            if (tTanks != null) {
                for (FluidTankInfo tTank : tTanks) {
                    if (tTank != null) {
                        tAll += tTank.capacity;
                        FluidStack tLiquid = tTank.fluid;
                        if (tLiquid != null) {
                            tFull += tLiquid.amount;
                        }
                    }
                }
            }
            tAll /= 14L;
            if (tAll > 0L) {
                aTileEntity.setOutputRedstoneSignal(aSide, aCoverVariable != 0 ? (byte) (int) (15L - (tFull <= 0L ? 0L : tFull / tAll + 1L)) : tFull <= 0L ? 0 : (byte) (int) (tFull / tAll + 1L));
            } else {
                aTileEntity.setOutputRedstoneSignal(aSide, ((byte) (aCoverVariable != 0 ? 15 : 0)));
            }
        } else {
            aTileEntity.setOutputRedstoneSignal(aSide, (byte) 0);
        }
        return aCoverVariable;
    }

    public int onCoverScrewdriverclick(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        if (aCoverVariable == 0) {
            GT_Utility.sendChatToPlayer(aPlayer, "Inverted");
        } else {
            GT_Utility.sendChatToPlayer(aPlayer, "Normal");
        }
        return aCoverVariable == 0 ? 1 : 0;
    }

    public boolean letsEnergyIn(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return true;
    }

    public boolean letsEnergyOut(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return true;
    }

    public boolean letsFluidIn(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity) {
        return true;
    }

    public boolean letsFluidOut(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity) {
        return true;
    }

    public boolean letsItemsIn(byte aSide, int aCoverID, int aCoverVariable, int aSlot, ICoverable aTileEntity) {
        return true;
    }

    public boolean letsItemsOut(byte aSide, int aCoverID, int aCoverVariable, int aSlot, ICoverable aTileEntity) {
        return true;
    }

    public boolean manipulatesSidedRedstoneOutput(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return true;
    }

    public int getTickRate(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return 5;
    }
}