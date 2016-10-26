package gregtech.common.covers;

import gregtech.api.interfaces.tileentity.ICoverable;
import gregtech.api.util.GT_CoverBehavior;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;

public class GT_Cover_FluidRegulator extends GT_CoverBehavior {
	public final int mTransferRate;

	public GT_Cover_FluidRegulator(int aTransferRate) {
		this.mTransferRate = aTransferRate;
	}

	public int doCoverThings(byte aSide, byte aInputRedstone, int aCoverID, int aCoverVariable, ICoverable aTileEntity,
			long aTimer) {
		if (aCoverVariable == 0) {
			return aCoverVariable;
		}
		if ((aTileEntity instanceof IFluidHandler)) {
			IFluidHandler tTank1;
			IFluidHandler tTank2;
			if (aCoverVariable > 0) {
				tTank2 = aTileEntity.getITankContainerAtSide(aSide);
				tTank1 = (IFluidHandler) aTileEntity;
			} else {
				tTank1 = aTileEntity.getITankContainerAtSide(aSide);
				tTank2 = (IFluidHandler) aTileEntity;
			}
			if (tTank1 != null && tTank2 != null) {
				FluidStack tLiquid = tTank1.drain(ForgeDirection.UNKNOWN, Math.abs(aCoverVariable), false);
				if (tLiquid != null) {
					tLiquid = tLiquid.copy();
					tLiquid.amount = tTank2.fill(ForgeDirection.getOrientation(aSide).getOpposite(), tLiquid, false);
					if (tLiquid.amount > 0) {
						if (aTileEntity.getUniversalEnergyCapacity() >= Math.min(1, tLiquid.amount / 10)) {
							if (aTileEntity.isUniversalEnergyStored(Math.min(1, tLiquid.amount / 10))) {
								aTileEntity.decreaseStoredEnergyUnits(Math.min(1, tLiquid.amount / 10), true);
								tTank2.fill(ForgeDirection.getOrientation(aSide).getOpposite(),	tTank1.drain(ForgeDirection.UNKNOWN, tLiquid.amount, true), true);
							}
						} else {
							tTank2.fill(ForgeDirection.getOrientation(aSide).getOpposite(), tTank1.drain(ForgeDirection.UNKNOWN, tLiquid.amount, true), true);
						}
					}
				}
			}
		}
		return aCoverVariable;
	}

	public int onCoverScrewdriverclick(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity,
			EntityPlayer aPlayer, float aX, float aY, float aZ) {
		if (GT_Utility.getClickedFacingCoords(aSide, aX, aY, aZ)[0] >= 0.5F) {
			aCoverVariable += 16;
		} else {
			aCoverVariable -= 16;
		}
		if (aCoverVariable > mTransferRate) {
			aCoverVariable = mTransferRate;
		}
		if (aCoverVariable < (0 - mTransferRate)) {
			aCoverVariable = (0 - mTransferRate);
		}
		GT_Utility.sendChatToPlayer(aPlayer,
				"Pump speed: " + aCoverVariable + "L/tick " + aCoverVariable * 20 + "L/sec");
		return aCoverVariable;
	}

	public boolean onCoverRightclick(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity,
			EntityPlayer aPlayer, float aX, float aY, float aZ) {
		if (GT_Utility.getClickedFacingCoords(aSide, aX, aY, aZ)[0] >= 0.5F) {
			aCoverVariable++;
		} else {
			aCoverVariable--;
		}
		if (aCoverVariable > mTransferRate) {
			aCoverVariable = mTransferRate;
		}
		if (aCoverVariable < (0 - mTransferRate)) {
			aCoverVariable = (0 - mTransferRate);
		}
		GT_Utility.sendChatToPlayer(aPlayer,
				"Pump speed: " + aCoverVariable + "L/tick " + aCoverVariable * 20 + "L/sec");
		aTileEntity.setCoverDataAtSide(aSide, aCoverVariable);
		return true;
	}

	public boolean letsRedstoneGoIn(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
		return true;
	}

	public boolean letsRedstoneGoOut(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
		return true;
	}

	public boolean letsEnergyIn(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
		return true;
	}

	public boolean letsEnergyOut(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
		return true;
	}

	public boolean letsItemsIn(byte aSide, int aCoverID, int aCoverVariable, int aSlot, ICoverable aTileEntity) {
		return true;
	}

	public boolean letsItemsOut(byte aSide, int aCoverID, int aCoverVariable, int aSlot, ICoverable aTileEntity) {
		return true;
	}

	public boolean letsFluidIn(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity) {
		return false;
	}

	public boolean letsFluidOut(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity) {
		return false;
	}

	public boolean alwaysLookConnected(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
		return true;
	}

	public int getTickRate(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
		return 1;
	}
}
